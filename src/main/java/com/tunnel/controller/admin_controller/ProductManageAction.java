package com.tunnel.controller.admin_controller;

import com.github.pagehelper.PageInfo;
import com.tunnel.bean.ProductInfo;
import com.tunnel.bean.ProductInfoQuery;
import com.tunnel.common.bean.BasicRet;
import com.tunnel.common.bean.PageRet;
import com.tunnel.common.constant.AppConstant;
import com.tunnel.common.utils.StringUtils;
import com.tunnel.service.ProductInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "后台商品管理接口")
@RequestMapping("/rest/admin/productmanage")
@SessionAttributes({AppConstant.ADMIN_SESSION_NAME})
@Transactional(rollbackFor = Exception.class)
public class ProductManageAction {

    @Autowired
    private ProductInfoService productInfoService;

    @RequestMapping(value = "/listProduct", method = RequestMethod.POST)
    @ApiOperation("商品分页列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "第几页", name = "pageNo", paramType = "query", dataType = "int", required = true, defaultValue = "1"),
            @ApiImplicitParam(value = "每页显示的条数", name = "pageSize", paramType = "query", dataType = "int", required = true, defaultValue = "20"),
            @ApiImplicitParam(value = "品名", name = "productname", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(value = "类别 1=隧道设备", name = "type", paramType = "query", dataType = "short", required = false,defaultValue = "0"),
            @ApiImplicitParam(value = "分类 1=全新 2=二手", name = "mold", paramType = "query", dataType = "short", required = false,defaultValue = "0"),
            @ApiImplicitParam(value = "品牌", name = "brand", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(value = "型号", name = "model", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(value = "是否跨境货源 0=否，1=是", name = "crossborder", paramType = "query", dataType = "short", required = false,defaultValue = "-1"),
            @ApiImplicitParam(value = "状态 1=上架 2=下架 3=待发布 4=删除", name = "state", paramType = "query", dataType = "short", required = false,defaultValue = "0"),
    })
    public PageRet listProduct(@RequestParam(required = true, defaultValue = "1") int pageNo,
                               @RequestParam(required = true, defaultValue = "20") int pageSize,
                               @RequestParam(required = false) String productname,
                               @RequestParam(required = false,defaultValue = "0") short type,
                               @RequestParam(required = false,defaultValue = "0") short mold,
                               @RequestParam(required = false) String brand,
                               @RequestParam(required = false) String model,
                               @RequestParam(required = false,defaultValue = "-1") short crossborder,
                               @RequestParam(required = false,defaultValue = "0") short state
    ) {
        PageRet pageRet = new PageRet();
        ProductInfoQuery productInfo = new ProductInfoQuery();
        if(StringUtils.hasText(productname)) {
            productInfo.setProductname(productname);
        }
        if(StringUtils.hasText(brand)){
            productInfo.setBrand(brand);
        }
        if(StringUtils.hasText(model)){
            productInfo.setModel(model);
        }
        productInfo.setType(type);
        productInfo.setMold(mold);
        productInfo.setCrossborder(crossborder);
        productInfo.setState(state);

        PageInfo pageInfo = productInfoService.listProduct(pageNo, pageSize, productInfo);
        pageRet.data.setPageInfo(pageInfo);
        pageRet.setResult(BasicRet.SUCCESS);
        return pageRet;
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ApiOperation("商品发布")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "品名", name = "productname", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(value = "类别 1=隧道设备", name = "type", paramType = "query", dataType = "short", required = false,defaultValue = "0"),
            @ApiImplicitParam(value = "分类 1=全新 2=二手", name = "mold", paramType = "query", dataType = "short", required = false,defaultValue = "0"),
            @ApiImplicitParam(value = "品牌", name = "brand", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(value = "型号", name = "model", paramType = "query", dataType = "string", required = false),
            @ApiImplicitParam(value = "是否跨境货源 0=否，1=是", name = "crossborder", paramType = "query", dataType = "short", required = false,defaultValue = "-1"),
            @ApiImplicitParam(value = "状态 1=上架 2=下架 3=待发布 4=删除", name = "state", paramType = "query", dataType = "short", required = false,defaultValue = "0"),
    })
    public BasicRet addProduct(ProductInfo productInfo){

        BasicRet basicRet = new BasicRet();
        return basicRet;

    }
}

package com.tunnel.controller.admin_controller;

import com.github.pagehelper.PageInfo;
import com.tunnel.bean.Admin;
import com.tunnel.bean.Advertisingplace;
import com.tunnel.common.bean.BasicRet;
import com.tunnel.common.constant.AppConstant;
import com.tunnel.service.AdvertisementService;
import com.tunnel.service.AdvertisingPlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/rest/admin/adtPlace")
@SessionAttributes({AppConstant.ADMIN_SESSION_NAME})
@Api(tags = "广告位相关", description = "广告位")
@Transactional(rollbackFor = Exception.class)
public class AdvertisingPlaceAction {

    @Autowired
    AdvertisingPlaceService advertisingPlaceService;

    @Autowired
    AdvertisementService advertisementService;


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = " 广告位list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "第几页", required = true, paramType = "query", dataType = "short", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "一页几条数据", required = true, paramType = "query", dataType = "short", defaultValue = "20"),
            @ApiImplicitParam(name = "stop", value = "是否启用", required = false, paramType = "query", dataType = "short"),
            @ApiImplicitParam(name = "showtype", value = "广告位展示方式", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "advtype", value = "广告种类", required = false, paramType = "query", dataType = "string"),
    })
    public BasicRet getAdvertisingPlaceList(@RequestParam(required = true) int pageNo,
                                            @RequestParam(required = true) int pageSize,
                                            @RequestParam(required = false) Short stop,
                                            @RequestParam(required = false, defaultValue = "") String showtype,
                                            @RequestParam(required = false, defaultValue = "") String advtype) {
        AdvertisingPlaceListRet basicRet = new AdvertisingPlaceListRet();
        PageInfo pageInfo = advertisingPlaceService.getAdvertisingPlaceList(pageNo, pageSize, stop, showtype, advtype);
        basicRet.data.pageInfo = pageInfo;
        basicRet.setResult(BasicRet.SUCCESS);
        basicRet.setMessage("成功");
        return basicRet;
    }



    private class AdvertisingPlaceListRet extends BasicRet {
        private class Data {
            private PageInfo pageInfo;

            public PageInfo getPageInfo() {
                return pageInfo;
            }

            public void setPageInfo(PageInfo pageInfo) {
                this.pageInfo = pageInfo;
            }
        }

        AdvertisingPlaceAction.AdvertisingPlaceListRet.Data data = new AdvertisingPlaceAction.AdvertisingPlaceListRet.Data();

        public AdvertisingPlaceAction.AdvertisingPlaceListRet.Data getData() {
            return data;
        }

        public void setData(AdvertisingPlaceAction.AdvertisingPlaceListRet.Data data) {
            this.data = data;
        }
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = " 广告位删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "广告位id", required = true, paramType = "query", dataType = "long"),
    })
    public BasicRet addAdvertisingPlace(@RequestParam(value = "id") Long id, Model model, HttpServletRequest request) {
        Admin admin = (Admin) model.asMap().get(AppConstant.ADMIN_SESSION_NAME);
        BasicRet basicRet = new BasicRet();
        Advertisingplace advertisingPlace = advertisingPlaceService.getDetailByPrimaryId(id);
        if (advertisingPlace == null) {
            basicRet.setResult(BasicRet.ERR);
            basicRet.setMessage("不存在该广告位");
            return basicRet;
        }
        int advertisements = advertisementService.getAdvertisementCountByAdvertisingPlaceId(id);
        if (advertisements == 0) {
            advertisingPlaceService.deleteAdvertisingPlaceById(id);
            basicRet.setResult(BasicRet.SUCCESS);
            //memberLogOperator.saveMemberLog(null, admin, "删除广告位：" + advertisingPlace.getPosition(), "/delete", request, memberOperateLogService);
            basicRet.setMessage("删除成功");
        } else {
            basicRet.setResult(BasicRet.ERR);
            basicRet.setMessage("该广告位下存在广告，不能删除");
        }
        return basicRet;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = " 广告位新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "position", value = "广告位置名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "describe", value = "广告简介", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "advtype", value = "广告种类", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "showtype", value = "展示方式", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "gettag", value = "获取标记", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "stop", value = "是否停用 1=停用", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "weight", value = "广告位宽度", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "height", value = "广告位高度", required = true, paramType = "query", dataType = "long"),
    })
    public BasicRet addAdvertisingPlace(Advertisingplace advertisingPlace, Model model, HttpServletRequest request) {
        Admin admin = (Admin) model.asMap().get(AppConstant.ADMIN_SESSION_NAME);
        BasicRet basicRet = new BasicRet();
        advertisingPlace.setCreatetime(new Date());
        advertisingPlaceService.addAdvertisingPlace(advertisingPlace);
        basicRet.setResult(BasicRet.SUCCESS);
        basicRet.setMessage("新增成功");
        //memberLogOperator.saveMemberLog(null, admin, "新增广告位：" + advertisingPlace.getPosition(), "/add", request, memberOperateLogService);
        return basicRet;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = " 广告位修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "广告位id", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "position", value = "广告位置名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "desc", value = "广告简介", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "advtype", value = "广告种类", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "showtype", value = "展示方式", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "gettag", value = "获取标记", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "stop", value = "是否停用 1=停用", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "weight", value = "广告位宽度", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "height", value = "广告位高度", required = true, paramType = "query", dataType = "long"),
    })
    public BasicRet editAdvertisingPlace(Advertisingplace advertisingPlace, Model model, HttpServletRequest request) {
        Admin admin = (Admin) model.asMap().get(AppConstant.ADMIN_SESSION_NAME);
        BasicRet basicRet = new BasicRet();
        advertisingPlaceService.updateAdvertisingPlace(advertisingPlace);
        basicRet.setResult(BasicRet.SUCCESS);
        basicRet.setMessage("修改成功");
        //memberLogOperator.saveMemberLog(null, admin, "修改广告位：" + advertisingPlace.getPosition(), "/edit", request, memberOperateLogService);
        return basicRet;
    }
}

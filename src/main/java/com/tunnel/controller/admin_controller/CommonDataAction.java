package com.tunnel.controller.admin_controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tunnel.bean.CommonData;
import com.tunnel.bean.CommonValue;
import com.tunnel.common.bean.BasicRet;
import com.tunnel.common.bean.PageRet;
import com.tunnel.common.utils.CommonUtils;
import com.tunnel.service.CommonDataService;
import com.tunnel.service.CommonValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * create : linjl
 * date : 2018/06/22
 */
@RestController
@RequestMapping("/rest/admin/commondata")
@Api(tags = "后台公共数据接口")
public class CommonDataAction {

    @Autowired
    private CommonValueService commonValueService;

    @Autowired
    private CommonDataService commonDataService;

    @PostMapping("/updateValue")
    @ApiOperation("修改公共数据值")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "id",name = "id",required = true,paramType = "query",dataType = "int"),
            @ApiImplicitParam(value = "值",name = "value",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(value = "序号",name = "sort",required = true,paramType = "query",dataType = "string"),
    })
    //@PreAuthorize("hasAuthority('" + AdminAuthorityConst.PUBLICDATAMANAGEMENT + "')")
    public BasicRet updateValue(@RequestParam(required = true) long id, @RequestParam(required = true) String value,
                                @RequestParam(required = true) int sort){
        BasicRet basicRet =  new BasicRet();

        CommonValue commonValue = commonValueService.getById(id);

        if(commonValue == null){
            return  new BasicRet(BasicRet.ERR,"不存在该值");
        }

        commonValue.setValue(value);
        commonValue.setSort(sort);
        commonValue.setId(id);
        commonValueService.updateByPrimaryKey(commonValue);
        basicRet.setResult(BasicRet.SUCCESS);
        basicRet.setMessage("修改成功");
        return  basicRet;
    }

    @PostMapping("/addValue")
    @ApiOperation("添加公共数据值")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "公共数据id",name = "commonid",required = true,paramType = "query",dataType = "int"),
            @ApiImplicitParam(value = "值",name = "value",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(value = "序号",name = "sort",required = true,paramType = "query",dataType = "string"),
            //@ApiImplicitParam(value = "图片",name = "pic",required = true,paramType = "query",dataType = "string"),
    })
    //@PreAuthorize("hasAuthority('" + AdminAuthorityConst.PUBLICDATAMANAGEMENT + "')")
    public  BasicRet addValue(@RequestParam(required = true) long commonid,@RequestParam(required = true) String value,
                              @RequestParam(required = true) int sort/*,@RequestParam(required = true) String pic*/){
        BasicRet basicRet =  new BasicRet();

        CommonValue commonValue = new CommonValue();
        commonValue.setValue(value);
        commonValue.setSort(sort);
        commonValue.setCommonId(commonid);
        //commonValue.setPic(pic);
        commonValueService.insertSelective(commonValue);
        basicRet.setResult(BasicRet.SUCCESS);
        basicRet.setMessage("添加成功");
        return  basicRet;
    }

    @PostMapping("/deleteValue")
    @ApiOperation("删除公共数据值")
    //@PreAuthorize("hasAuthority('" + AdminAuthorityConst.PUBLICDATAMANAGEMENT + "')")
    public BasicRet deleteValue(long id){
        BasicRet basicRet =  new BasicRet();
        commonValueService.deleteById(id);
        basicRet.setMessage("删除成功");
        basicRet.setResult(BasicRet.SUCCESS);
        return  basicRet;
    }

    @PostMapping("/detailData")
    @ApiOperation("公共数据详情")
    //@PreAuthorize("hasAuthority('" + AdminAuthorityConst.PUBLICDATAMANAGEMENT + "')")
    public CommonDataDetailRet detailData(long id){
        CommonDataDetailRet commonDataDetailRet =  new CommonDataDetailRet();

        CommonData commonData = commonDataService.getById(id);
        if(commonData == null){
            commonDataDetailRet.setMessage("公共数据不存在");
            commonDataDetailRet.setResult(BasicRet.ERR);
            return  commonDataDetailRet;
        }

        List<CommonValue> commonValues = commonValueService.getValueById(id);

        commonDataDetailRet.commonData =  commonData;
        commonDataDetailRet.commonValues =  commonValues;
        commonDataDetailRet.setResult(BasicRet.SUCCESS);
        commonDataDetailRet.setMessage("查询成功");
        return  commonDataDetailRet;
    }

    private  class  CommonDataDetailRet extends  BasicRet{
        private  CommonData commonData;
        private List<CommonValue> commonValues;

        public CommonData getCommonData() {
            return commonData;
        }

        public void setCommonData(CommonData commonData) {
            this.commonData = commonData;
        }

        public List<CommonValue> getCommonValues() {
            return commonValues;
        }

        public void setCommonValues(List<CommonValue> commonValues) {
            this.commonValues = commonValues;
        }
    }

    @PostMapping("/delData")
    @ApiOperation("删除公共数据")
    @ApiImplicitParams({

    })
    //@PreAuthorize("hasAuthority('" + AdminAuthorityConst.PUBLICDATAMANAGEMENT + "')")
    public BasicRet delData(@RequestParam(required = true) long id){
        //BasicRet basicRet =  new BasicRet();
        commonValueService.deleteByCommonId(id);
        commonDataService.deleteById(id);
        return  new BasicRet(BasicRet.SUCCESS,"删除成功");
    }

    @PostMapping("/listData")
    @ApiOperation("公共数据列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "名称",name = "name",required = false,paramType = "query",dataType = "string"),
    })
    public PageRet listData(@RequestParam(required = true,defaultValue = "1") int pageNo,
                            @RequestParam(required = true,defaultValue = "20") int pageSize,
                            @RequestParam(required = false) String name){

        PageRet pageRet = new PageRet();
        PageInfo pageInfo =  commonDataService.listByPage(name,pageNo,pageSize);
        pageRet.data.setPageInfo(pageInfo);
        pageRet.setResult(BasicRet.SUCCESS);
        return  pageRet;
    }

    @PostMapping("/updateDate")
    @ApiOperation("修改公共数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "名称", name = "name", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(value = "备注", name = "remark", required = true, paramType = "query", dataType = "string"),
    })
    //@PreAuthorize("hasAuthority('" + AdminAuthorityConst.PUBLICDATAMANAGEMENT + "')")
    public BasicRet updateData(
            @RequestParam(required = true) long id,
            @RequestParam(required = true) String name,
            @RequestParam(required = true,defaultValue = "") String remark) {
        BasicRet basicRet = new BasicRet();

        CommonData commonData = commonDataService.getByName(name);
        if (commonData != null && !commonData.getId().equals(id)) {
            return new BasicRet(BasicRet.ERR, "已经存在该名称的公共数据了");
        }

        commonData = new CommonData();
        commonData.setId(id);
        commonData.setName(name);
        commonData.setRemark(remark);
        commonDataService.updateByPrimaryKeySelective(commonData);

        basicRet.setResult(BasicRet.SUCCESS);
        basicRet.setMessage("修改成功");
        return  basicRet;
    }

    @PostMapping("/addData")
    @ApiOperation("添加公共数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "名称",name = "name",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(value = "备注",name = "remark",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(value = "数据值json [{\"value\":\"10\",\"sort\":1},{\"value\":\"20\",\"sort\":2}]",name = "valueJson",required = true,paramType = "query",dataType = "string"),
            @ApiImplicitParam(value = "图片",name = "pic",required = false,paramType = "query",dataType = "string"),
    })
    //@PreAuthorize("hasAuthority('" + AdminAuthorityConst.PUBLICDATAMANAGEMENT + "')")
    public BasicRet addData(@RequestParam(required = true) String name,
                            @RequestParam(required = true) String remark,
                            @RequestParam(required = true) String valueJson,
                            @RequestParam(required = false) String pic){
        BasicRet basicRet = new BasicRet();

        CommonData commonData =  commonDataService.getByName(name);
        if(commonData != null){
            return  new BasicRet(BasicRet.ERR,"已经存在该名称的公共数据了");
        }

        if(!CommonUtils.isGoodJson(valueJson)){
            return  new BasicRet(BasicRet.ERR,"json数据不合法");
        }

        Gson gson = new Gson();
        List<CommonValue> valueList =  gson.fromJson(valueJson,new TypeToken<ArrayList<CommonValue>>() {}.getType());

        commonData = new CommonData();
        commonData.setRemark(remark);
        commonData.setName(name);
        commonDataService.insertSelective(commonData);
        CommonData commonData1 =  commonDataService.getByName(name);
        if(valueList != null && valueList.size()>0){
            for(CommonValue value : valueList){
                if(!StringUtils.hasText(value.getValue())) continue;
                value.setCommonId(commonData1.getId());
                value.setPic(pic);

                commonValueService.insertSelective(value);
            }
        }

        return  new BasicRet(BasicRet.SUCCESS,"添加成功");
    }
}

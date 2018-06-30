package com.tunnel.controller.member_controller;

import com.tunnel.bean.*;
import com.tunnel.bean.dto.CategoryViewDto;
import com.tunnel.bean.dto.TodayRecommProduct;
import com.tunnel.common.bean.BasicRet;
import com.tunnel.common.constant.AppConstant;
import com.tunnel.common.constant.Quantity;
import com.tunnel.service.AdvertisementService;
import com.tunnel.service.AdvertisingPlaceService;
import com.tunnel.service.FrontService;
import com.tunnel.service.RedisCacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前台Aaction
 *
 * @author xiazy
 * @create 2018-06-30 13:52
 **/
@RestController
@RequestMapping("/rest/front")
@SessionAttributes({AppConstant.MEMBER_SESSION_NAME})
@Api(tags = "前台模块")
@Transactional(rollbackFor = Exception.class)
public class FrontAction {
    @Autowired
    private FrontService frontService;
    @Autowired
    private AdvertisingPlaceService advertisingPlaceService;
    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
    private RedisCacheService redisCacheService;


    @RequestMapping(value = "/getTodayRecommList",method = RequestMethod.POST)
    @ApiOperation(value = "获取今日推荐产品")
    public TodayRecommRet getTodayRecommList(@RequestParam(required = true) int count, Model model){
        TodayRecommRet ret=new TodayRecommRet();
        List<TodayRecommProduct> list=frontService.getTodayRecommProduct(count, Quantity.INT_1);
        ret.data=list;
        ret.setResult(BasicRet.SUCCESS);
        ret.setMessage("获取今日推荐产品成功");
        return ret;
    }

    @RequestMapping(value = "/getAdvertisment",method = RequestMethod.POST)
    @ApiOperation("获取广告")
    public AdvertismentListRet getAdvertisment(@RequestParam(required = true) String position,
                                               @RequestParam(required = false, defaultValue = "1") int count) {
        AdvertismentListRet advertismentListRet = new AdvertismentListRet();
        Advertisingplace advertisingPlace = advertisingPlaceService.getAdvertisingPlaceByPosition(position);
        if (advertisingPlace == null) {
            advertismentListRet.setResult(BasicRet.ERR);
            advertismentListRet.setMessage("不存在该广告位");
            return advertismentListRet;
        } else if (advertisingPlace.getStop() == 1) {
            advertismentListRet.setResult(BasicRet.SUCCESS);
            advertismentListRet.setMessage("该广告位已停用");
            advertismentListRet.setShow(false);
            return advertismentListRet;
        }
        advertismentListRet.setWeight(advertisingPlace.getWeight());
        advertismentListRet.setHeight(advertisingPlace.getHeight());
        List<Advertisement> list = advertisementService.getAdvertisment(position, count);
        advertismentListRet.setShow(true);
        advertismentListRet.data = list;
        advertismentListRet.setMessage("成功");
        advertismentListRet.setResult(BasicRet.SUCCESS);
        return advertismentListRet;
    }



    @RequestMapping(value = "/listFloor",method = RequestMethod.POST)
    @ApiOperation("首页楼层")
    public  FloorListRet listFloor(Model model){
        FloorListRet floorListRet  = new FloorListRet();
        User user = (User) model.asMap().get(AppConstant.MEMBER_SESSION_NAME);
//        if(user == null){
//            floorListRet.data.showPrice = false;
//        }else{
//            floorListRet.data.showPrice = true;
//        }

//        List<FloorViewDto> dtoList =  floorService.getFloor();

        List<Floor> dtoList = redisCacheService.getIndexFloor();
        //dtoList = null;
        if(dtoList == null){
            dtoList = redisCacheService.addIndexFloor();
        }

        floorListRet.data.floorList =  dtoList;
        floorListRet.setMessage("查询成功");
        floorListRet.setResult(BasicRet.SUCCESS);
        return  floorListRet;
    }





    @RequestMapping(value = "/listShowCate",method = RequestMethod.POST)
    @ApiOperation("获取展示类目")
    public CategoryRet listShowCate(Model model){
        CategoryRet categoryRet = new CategoryRet();

        List<CategoryViewDto> list = redisCacheService.getShowCate();
        //list=null;
        if(list == null){
            list = redisCacheService.addShowCate();
        }

        categoryRet.setData(list);
        categoryRet.setResult(BasicRet.SUCCESS);
        categoryRet.setMessage("获取展示类目成功");
        return  categoryRet;
    }


    private static  class TodayRecommRet extends BasicRet{
        public List<TodayRecommProduct>  data;

        public List<TodayRecommProduct> getData() {
            return data;
        }

        public void setData(List<TodayRecommProduct> data) {
            this.data = data;
        }
    }

    private class AdvertismentListRet extends BasicRet {
        private List<Advertisement> data;
        private boolean show;
        private Float weight;
        private Float height;

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

        public List<Advertisement> getData() {
            return data;
        }

        public void setData(List<Advertisement> data) {
            this.data = data;
        }

        public Float getWeight() {
            return weight;
        }

        public void setWeight(Float weight) {
            this.weight = weight;
        }

        public Float getHeight() {
            return height;
        }

        public void setHeight(Float height) {
            this.height = height;
        }
    }



    private  static  class  FloorListRet extends BasicRet {

        private  class  FloorData{
            private List<Floor> floorList;


            public List<Floor> getFloorList() {
                return floorList;
            }

            public void setFloorList(List<Floor> floorList) {
                this.floorList = floorList;
            }

        }


        private  FloorData data = new FloorData();

        public FloorData getData() {
            return data;
        }

        public void setData(FloorData data) {
            this.data = data;
        }
    }


    private static class CategoryRet extends BasicRet{
        public List<CategoryViewDto> data;

        public List<CategoryViewDto> getData() {
            return data;
        }

        public void setData(List<CategoryViewDto> data) {
            this.data = data;
        }
    }
}

package com.tunnel.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tunnel.bean.Category;
import com.tunnel.bean.Floor;
import com.tunnel.bean.dto.CategoryViewDto;
import com.tunnel.bean.dto.FloorViewDto;
import com.tunnel.common.utils.CommonUtils;
import com.tunnel.mapper.CategoryMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create : wyh
 * date : 2018/2/27
 */
@Service
public class IndexService {

    @Autowired
    private FloorService floorService;
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private Gson gson;

    /**
     * 首页楼层
     * @return
     */
    public  List getFloor(){
        List<FloorViewDto> list =  floorService.getShowFloor();
//        List<FloorViewDto> dtoList =  new ArrayList<>();
//
//        for(Floor floor : list){
//            FloorViewDto floorViewDto = new FloorViewDto();
//            BeanUtils.copyProperties(floor,floorViewDto);
//
//            dtoList.add(floorViewDto);
//        }

        return  list;
    }




    /**
     * 展示类目
     * @return
     */
    public List<CategoryViewDto> getShowCate(){

        List<Category> showCateList = categoryMapper.listAll();
        List<CategoryViewDto> result=this.getCategoryList(showCateList,0L);
        return  result;
    }


    /**
     * 获取2级分类
     */
//    private  List<Long> getLevel2List(List<ShowCatedetail> showCatedetailList){
//        List<Long> level2List = new ArrayList<>();
//        for(ShowCatedetail detail : showCatedetailList){
//            if(!level2List.contains(detail.getLevel2id())){
//                level2List.add(detail.getLevel2id());
//            }
//        }
//
//        return  level2List;
//    }




    private List<CategoryViewDto> getCategoryList(List<Category> categoryList, Long parentId){
        List<CategoryViewDto> categoryViewDtos=new ArrayList<>();
//        BeanUtils.copyProperties(category,categoryViewDto);
        if (categoryList!=null&&categoryList.size()>0){
            for (Category category1:categoryList){
                CategoryViewDto categoryViewDto=new CategoryViewDto();
                if (category1.getParentId().longValue()==parentId){
                    BeanUtils.copyProperties(category1,categoryViewDto);
                    categoryViewDtos.add(categoryViewDto);
                    categoryViewDto.setCategoryList(getCategoryList(categoryList,categoryViewDto.getId()));
                }
            }
        }
        return categoryViewDtos;
    }
//
//
//
//    /**
//     * 获取材质集合
//     * @param showCatedetailList
//     * @return
//     */
//    private  List<String> getMaterialList(List<ShowCatedetail> showCatedetailList){
//        List<String> materialList = new ArrayList<>();
//
//        for(ShowCatedetail showCatedetail : showCatedetailList){
//            if(!materialList.contains(showCatedetail.getMaterial())){
//                materialList.add(showCatedetail.getMaterial());
//            }
//        }
//        return  materialList;
//    }
//
//
//    /**
//     *
//     * @param list
//     * @param material
//     * @return
//     */
//    private  List<ShowCatedetail> getByMaterial(List<ShowCatedetail> list ,String material){
//        List<ShowCatedetail>  resList = new ArrayList<>();
//        for(ShowCatedetail showCatedetail : list){
//            if(material.equals(showCatedetail.getMaterial())){
//                resList.add(showCatedetail);
//            }
//        }
//        return  resList;
//    }
//
//
//
//    private  List<ShowCatedetail> getByMaterialAndStand(List<ShowCatedetail> list ,String material,Long level2id){
//        List<ShowCatedetail>  resList = new ArrayList<>();
//        for(ShowCatedetail showCatedetail : list){
//            if(material.equals(showCatedetail.getMaterial()) && level2id.equals(showCatedetail.getLevel2id())){
//                resList.add(showCatedetail);
//            }
//        }
//        return  resList;
//    }


}

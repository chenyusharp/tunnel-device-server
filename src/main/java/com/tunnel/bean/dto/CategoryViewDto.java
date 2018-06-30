package com.tunnel.bean.dto;

import com.tunnel.bean.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * CategoryViewDto
 *
 * @author xiazy
 * @create 2018-06-30 18:04
 **/
public class CategoryViewDto extends Category{
    List<CategoryViewDto> categoryList=new ArrayList<>();

    public List<CategoryViewDto> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryViewDto> categoryList) {
        this.categoryList = categoryList;
    }
}

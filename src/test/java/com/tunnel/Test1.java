package com.tunnel;

import com.tunnel.bean.Category;
import com.tunnel.bean.dto.CategoryViewDto;
import com.tunnel.bean.dto.FloorViewDto;
import com.tunnel.mapper.CategoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test
 *
 * @author xiazy
 * @create 2018-06-30 18:35
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {
    @Autowired
    private CategoryMapper categoryMapper;
    @Test
    public void test() throws IOException {
        List<Category> showCateList = categoryMapper.listAll();
        List<CategoryViewDto> result=this.getCategoryList(showCateList,0L);
        System.out.println(result.toString());
    }

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
}

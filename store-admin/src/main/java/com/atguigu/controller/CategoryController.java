package com.atguigu.controller;

import com.atguigu.param.PageParam;
import com.atguigu.pojo.Category;
import com.atguigu.service.CategoryService;
import com.atguigu.utils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {


    final
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/list1")
    public R list(PageParam pageParam){
        R list = categoryService.list(pageParam);
        return list ;
    }

    @PostMapping("/save")
    public R save(String categoryName){
        return categoryService.save(categoryName);
    }

    @PostMapping("/remove")
    public R remove(Integer categoryId){
        return categoryService.remove(categoryId);
    }

    @PostMapping("/update")
    public R update(Category category){
        return categoryService.update(category);
    }

}

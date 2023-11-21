package com.atguigu.clients;


import com.atguigu.param.ProductHotParam;
import com.atguigu.param.PageParam;
import com.atguigu.pojo.Category;
import com.atguigu.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "category-service")
@RequestMapping("/category")
public interface CategoryClient {

    @GetMapping("/{categoryName}")
    R getCategoryByName(@PathVariable String categoryName);

    @PostMapping("/names")
    R getCategoryByNames(@RequestBody ProductHotParam productHotParam);

    @GetMapping("/list")
    R list();

    @PostMapping("/admin/list")
    R adminCategoryList( PageParam pageParam);

    @PostMapping("/admin/save")
    R adminCategorySave(String categoryName);

    @PostMapping("/admin/remove")
    R adminCategoryRemove(Integer categoryId);

    @PostMapping("/admin/update")
    R adminCategoryUpdate(Category category);



}

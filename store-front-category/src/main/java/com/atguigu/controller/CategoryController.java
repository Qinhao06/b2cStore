package com.atguigu.controller;


import com.alibaba.cloud.commons.lang.StringUtils;
import com.atguigu.param.ProductHotParam;
import com.atguigu.param.PageParam;
import com.atguigu.pojo.Category;
import com.atguigu.service.CategoryService;
import com.atguigu.utils.R;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/category")
@RestController
public class CategoryController {


    final
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

        /**
     * 获取所有类别的列表
     *
     * @return 返回R对象，包含类别列表
     */
    @GetMapping("/list")
    public R list(){
        return categoryService.list();
    }

        /**
     * 根据分类名称获取分类信息
     * @param categoryName 分类名称
     * @return 返回R对象，包含分类信息
     */
    @GetMapping("/{categoryName}")
    public R getCategoryByName(@PathVariable String categoryName){
        if(StringUtils.isEmpty(categoryName)){
            return R.fail("categoryName is null");
        }
        return categoryService.getCategory(categoryName);
    }

        /**
     * 获取指定分类名对应的分类信息
     *
     * @param productHotParam 分类名数组
     * @return 分类信息
     */
    @PostMapping("/names")
    public R getCategoryByNames(@RequestBody @Validated ProductHotParam productHotParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("categoryNames is null");
        }
        return categoryService.getCategoryByNames(productHotParam.getCategoryName());
    }

    @PostMapping("/admin/list")
    public R adminCategoryList(@RequestBody PageParam pageParam){
        return categoryService.page(pageParam);
    }

    @PostMapping("/admin/save")
    public R adminCategorySave(@RequestBody  String categoryParam) {
        return categoryService.save(categoryParam);
    }

    @PostMapping("/admin/remove")
    public R adminCategoryRemove(@RequestBody Integer categoryId) {
        return categoryService.remove(categoryId);
    }

    @PostMapping("/admin/update")
    public R adminCategoryUpdate(@RequestBody Category category) {
        return categoryService.update(category);
    }



}

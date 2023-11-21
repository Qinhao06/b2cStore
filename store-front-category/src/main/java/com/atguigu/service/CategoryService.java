package com.atguigu.service;

import com.atguigu.param.PageParam;
import com.atguigu.pojo.Category;
import com.atguigu.utils.R;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CategoryService {

    /**
     * 查询所有分类
     * @return
     */
    R list();

    R getCategory(String categoryName);

    R getCategoryByNames(@NotNull List<String> categoryNames);

    R page(PageParam pageParam);

    R save(String categoryParam);

    R remove(Integer categoryId);

    R update(Category category);
}

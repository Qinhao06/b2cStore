package com.atguigu.service;

import com.atguigu.param.PageParam;
import com.atguigu.pojo.Category;
import com.atguigu.utils.R;

public interface CategoryService {
    R list(PageParam pageParam);

    R save(String categoryName);

    R remove(Integer categoryId);

    R update(Category category);
}

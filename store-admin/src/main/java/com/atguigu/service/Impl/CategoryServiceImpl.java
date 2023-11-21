package com.atguigu.service.Impl;

import com.atguigu.clients.CategoryClient;
import com.atguigu.param.PageParam;
import com.atguigu.pojo.Category;
import com.atguigu.service.CategoryService;
import com.atguigu.utils.R;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    final
    CategoryClient categoryClient;

    public CategoryServiceImpl(CategoryClient categoryClient) {
        this.categoryClient = categoryClient;
    }

    /**
     * @param pageParam
     * @return
     */
    @Override
    @Caching(
            cacheable = @Cacheable(value = "list.category", key = "#pageParam.currentPage" + "-" + "#pageParam.pageSize")
    )
    public R list(PageParam pageParam) {
        return categoryClient.adminCategoryList(pageParam);
    }

    /**
     * @param categoryName
     * @return
     */
    @Override
    @Caching(
        evict = {
                @CacheEvict(value = "list.category", allEntries = true)
        }
    )
    public R save(String categoryName) {
        return categoryClient.adminCategorySave(categoryName);
    }

    /**
     * @param categoryId
     * @return
     */
    @CacheEvict(value = "list.category", allEntries = true)
    @Override
    public R remove(Integer categoryId) {
        return categoryClient.adminCategoryRemove(categoryId);
    }

    /**
     * @param category
     * @return
     */
    @CacheEvict(value = "list.category", allEntries = true)
    @Override
    public R update(Category category) {
        return categoryClient.adminCategoryUpdate(category);
    }


}

package com.atguigu.service.Impl;

import com.atguigu.mapper.CategoryMapper;
import com.atguigu.param.PageParam;
import com.atguigu.pojo.Category;
import com.atguigu.service.CategoryService;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    
    final
    CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    /**
     * 查询所有分类
     *
     * @return
     */
    @Override
    public R list() {
        List<Category> categories = categoryMapper.selectList(null);
        return R.ok(categories);

    }

    /**
     * 通过名字查询单个类别
     * @param categoryName
     * @return
     */
    @Override
    public R getCategory(String categoryName) {

        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_name",categoryName);
        Category category = categoryMapper.selectOne(queryWrapper);

        return R.ok(category);
    }

    /**
     * 通过名字获取类别
     * @param categoryNames
     * @return
     */
    @Override
    public R getCategoryByNames(List<String> categoryNames) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("category_name", categoryNames);
        queryWrapper.select("category_id");
        List<Object> ids = categoryMapper.selectObjs(queryWrapper);
        return R.ok(ids);
    }

    /**
     * @return
     */
    @Override
    public R page(PageParam pageParam) {
        IPage<Category> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        page = categoryMapper.selectPage(page, null);
        return R.ok("查询成功", page.getRecords(), page.getTotal());
    }

    /**
     * @param categoryParam
     * @return
     */
    @Override
    public R save(String categoryParam) {
        if(categoryParam.isEmpty()){
            return R.fail("类别名不能为空");
        }
        Category category = new Category();
        category.setCategoryName(categoryParam);
        int insert = categoryMapper.insert(category);
        if (insert == 0){
            return R.fail("保存失败");
        }
        return R.ok("保存成功");
    }

    /**
     * @param categoryId
     * @return
     */
    @Override
    public R remove(Integer categoryId) {
        if(categoryId == null){
            return R.fail("类别id不能为空");
        }
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);
        int delete = categoryMapper.delete(queryWrapper);
        if (delete == 0){
            return R.fail("删除失败");
        }
        return R.ok("删除成功");
    }

    /**
     * @param category
     * @return
     */
    @Override
    public R update(Category category) {
        if(category == null || category.getCategoryId() == null || category.getCategoryName().isEmpty()){
            return R.fail("类别数据存在空数据");
        }
        int update = categoryMapper.updateById(category);
        if (update == 0){
            return R.fail("更新失败");
        }
        return R.ok("更新成功");
    }


}

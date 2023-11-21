package com.atguigu.service.Impl;

import com.atguigu.clients.CategoryClient;
import com.atguigu.mapper.PictureMapper;
import com.atguigu.mapper.ProductMapper;
import com.atguigu.param.*;
import com.atguigu.pojo.Category;
import com.atguigu.pojo.Picture;
import com.atguigu.pojo.Product;
import com.atguigu.service.ProductService;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    final
    CategoryClient categoryClient;

    final
    ProductMapper productMapper;

    final
    PictureMapper pictureMapper;

    public ProductServiceImpl(CategoryClient categoryClient, ProductMapper productMapper, PictureMapper pictureMapper) {
        this.categoryClient = categoryClient;
        this.productMapper = productMapper;
        this.pictureMapper = pictureMapper;
    }

    /**
     * 返回指定类别的商品列表
     *
     * @param param
     * @return
     */
    @Cacheable(value = "list.product", key = "#param.categoryName", cacheManager = "cacheManagerHour")
    @Override
    public R promo(ProductPromoParam param) {

        R categoryR = categoryClient.getCategoryByName(param.getCategoryName());
        if(Objects.equals(categoryR.getCode(), R.FAIL_CODE)){
            return categoryR;
        }
        LinkedHashMap<String, Object> map
                = (LinkedHashMap<String, Object>) categoryR.getData();

        Integer categoryId = (Integer) map.get("category_id");

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id",categoryId);
        queryWrapper.orderByDesc("product_sales");

        IPage<Product> page = new Page<>(1, 7);

        page = productMapper.selectPage(page, queryWrapper);

        return R.ok(page.getRecords());
    }

    /**
     * 返回热销商品列表
     *
     * @param param
     * @return
     */
    @Cacheable(value = "list.product", key = "#param.categoryName", cacheManager = "cacheManagerHour")
    @Override
    public R hots(ProductHotParam param) {
        R categoryR = categoryClient.getCategoryByNames(param);
        if(Objects.equals(categoryR.getCode(), R.FAIL_CODE) ){
            return categoryR;
        }
        List<Integer> categoryIds = (List<Integer>) categoryR.getData();

        if(categoryIds.size() == 0){
            return R.fail("no categoryIds");
        }

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("category_id", categoryIds);
        queryWrapper.orderByDesc("product_sales");
        IPage<Product> page = new Page<>(1, 7);
        page = productMapper.selectPage(page, queryWrapper);

        return R.ok(page.getRecords());
    }

    /**
     * @return
     */
    @Cacheable(value = "list.category", key = "#root.methodName",
            cacheManager = "cacheManagerDay")
    @Override
    public R list() {
        R categoryList = categoryClient.list();
        return  categoryList;
    }

    /**
     * @param byCategoryParam
     * @return
     */
    @Cacheable(value = "list.product", key ="#byCategoryParam.toString()" , cacheManager = "cacheManagerHour")
    @Override
    public R byCategory(ByCategoryParam byCategoryParam) {

        List<Integer> categoryId = byCategoryParam.getCategoryID();
        Integer currentPage = byCategoryParam.getCurrentPage();
        Integer pageSize = byCategoryParam.getPageSize();

        // 构建查询条件
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        if(categoryId!= null && categoryId.size() > 0){
            queryWrapper.in("category_id", categoryId);
        }
        IPage<Product> page = new Page<>(currentPage, pageSize);

        //查询数据
        page = productMapper.selectPage(page, queryWrapper);

        List<Product> records = page.getRecords();
        long total = page.getTotal();

        return R.ok(null, records, total);
    }

    /**
     * @param byCategoryParam
     * @return
     */
    @Override
    public R all(ByCategoryParam byCategoryParam) {
        return byCategory(byCategoryParam);
    }

    /**
     * @param productId
     * @return
     */
    @Cacheable(value = "product", key = "#productId", cacheManager = "cacheManagerDay")
    @Override
    public R detail(Integer productId) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        Product product = productMapper.selectOne(queryWrapper);
        if(product == null){
            return R.fail("no product");
        }
        return R.ok(product);
    }

    /**
     * @param productId
     * @return
     */
    @Cacheable(value = "pictures", key = "#productId", cacheManager = "cacheManagerDay")
    @Override
    public R pictures(Integer productId) {
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        List<Picture> pictures = pictureMapper.selectList(queryWrapper);
        if(pictures == null || pictures.isEmpty()){
            return R.fail("no pictures");
        }

        return R.ok(pictures);
    }

    /**
     * @param searchParam
     * @return
     */

    @Override
    public R search(ProductSearchParam searchParam) {
        // 获取参数
        String search = searchParam.getSearch();
        Integer currentPage = searchParam.getCurrentPage();
        Integer pageSize = searchParam.getPageSize();

        // 构造查询条件
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("product_name", search);
        IPage<Product> page = new Page<>(currentPage, pageSize);


        // 查询数据库
        page = productMapper.selectPage(page, queryWrapper);

        return R.ok(page.getRecords());
    }

    /**
     * @param productIdsParam
     * @return
     */
    @Override
    public List<Product> ids(ProductIdsParam productIdsParam) {
        List<Integer> productIds = productIdsParam.getProductIds();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id", productIds);
        return productMapper.selectList(queryWrapper);
    }

    /**
     * @param productNumberParams
     */
    @Override
    public void subNumber(List<ProductNumberParam> productNumberParams) {
        Map<Integer, ProductNumberParam> collect = productNumberParams.
                stream().collect(Collectors.toMap(ProductNumberParam::getProductId, v -> v));
        Set<Integer> productIds = collect.keySet();
        List<Product> productList = productMapper.selectBatchIds(productIds);
        for (Product product : productList) {
            Integer productNum = collect.get(product.getProductId()).getProductNum();
            product.setProductNum(product.getProductNum() - productNum);
            product.setProductSales(product.getProductSales() + productNum);
        }

        for (Product product : productList) {
            productMapper.updateById(product);
        }

    }

    /**
     * @param listParam
     * @return
     */
    @Override
    public R adminList(AdminProductListParam listParam) {
        if(listParam.getSearch() == null){
            return R.ok(productMapper.selectList(null));
        }
        ProductSearchParam searchParam = new ProductSearchParam();
        searchParam.setSearch(listParam.getSearch());
        searchParam.setCurrentPage(listParam.getCurrentPage());
        searchParam.setPageSize(listParam.getPageSize());
        return R.ok(search(searchParam));
    }

    /**
     * @param productId
     * @return
     */
    @Override
    public R remove(Integer productId) {
        if(productId == null ){
            return R.fail("no product");
        }
        productMapper.deleteById(productId);
        return R.ok("删除成功");
    }

    /**
     * @param product
     * @return
     */
    @Override
    public R update(Product product) {
        if(product == null || product.getProductId() == null){
            return R.fail("no product");
        }
        productMapper.updateById(product);
        return R.ok("修改成功");
    }

    /**
     * @param param
     * @return
     */
    @Override
    public R save(ProductSaveParam param) {
        Product product = new Product();
        BeanUtils.copyProperties(param, product);
        int insert = productMapper.insert(product);

        if(insert == 0){
            return R.fail("添加失败");
        }
        return R.ok("添加成功");
    }


}

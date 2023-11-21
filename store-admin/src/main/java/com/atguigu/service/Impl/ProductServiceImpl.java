package com.atguigu.service.Impl;

import com.atguigu.clients.ProductClient;
import com.atguigu.param.AdminProductListParam;
import com.atguigu.param.PageParam;
import com.atguigu.param.ProductSaveParam;
import com.atguigu.pojo.Product;
import com.atguigu.service.ProductService;
import com.atguigu.utils.R;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService {

    final
    ProductClient productClient;

    public ProductServiceImpl(ProductClient productClient) {
        this.productClient = productClient;
    }


    @Cacheable(value = "list.product", key = "#listParam.currentPage" +"-" +
            "#listParam.pageSize")
    /**
     * @param listParam
     * @return
     */
    @Override
    public R list(AdminProductListParam listParam) {
        return productClient.adminList(listParam);
    }

    /**
     * @param productId
     * @return
     */
    @Override
    @CacheEvict(value = "list.product", allEntries = true)
    public R remove(Integer productId) {
        return productClient.adminRemove(productId);
    }

    /**
     * @param product
     * @return
     */
    @CacheEvict(value = "list.product", allEntries = true)
    @Override
    public R update(Product product) {
        return productClient.adminUpdate(product);
    }

    /**
     * @param img
     * @return
     */
    @CacheEvict(value = "list.product", allEntries = true)
    @Override
    public R upload(MultipartFile img) {
        String filename = img.getOriginalFilename();
        String contextType = img.getContentType();
        long mills = System.currentTimeMillis();
        filename = mills + filename;
        return null;
    }

    /**
     * @param param
     * @return
     */
    @CacheEvict(value = "list.product", allEntries = true)
    @Override
    public R save(ProductSaveParam param) {
        return productClient.adminSave(param);
    }
}

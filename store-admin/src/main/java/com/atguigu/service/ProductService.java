package com.atguigu.service;

import com.atguigu.param.AdminProductListParam;
import com.atguigu.param.PageParam;
import com.atguigu.param.ProductSaveParam;
import com.atguigu.pojo.Product;
import com.atguigu.utils.R;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    R list(AdminProductListParam listParam);

    R remove(Integer productId);

    R update(Product product);

    R upload(MultipartFile img);

    R save(ProductSaveParam param);
}

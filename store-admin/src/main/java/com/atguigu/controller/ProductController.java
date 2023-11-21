package com.atguigu.controller;


import com.atguigu.param.AdminProductListParam;
import com.atguigu.param.PageParam;
import com.atguigu.param.ProductSaveParam;
import com.atguigu.pojo.Product;
import com.atguigu.service.ProductService;
import com.atguigu.utils.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("product")
public class ProductController {


    final
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("list")
    public R list(AdminProductListParam pageParam) {
        return productService.list(pageParam);
    }

    @PostMapping("upload")
    public R upload( MultipartFile img) {

        return productService.upload(img);
    }

    @PostMapping("remove")
    public R remove( Integer productId) {
        return productService.remove(productId);
    }

    @PostMapping("update")
    public R update( Product product) {
        return productService.update(product);
    }

    @PostMapping("save")
    public R save( ProductSaveParam param) {
        return productService.save(param);
    }


}

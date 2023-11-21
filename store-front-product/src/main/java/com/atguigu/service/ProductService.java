package com.atguigu.service;

import com.atguigu.param.*;
import com.atguigu.pojo.Product;
import com.atguigu.utils.R;

import java.util.List;

public interface ProductService {

    /**
     * 返回指定类别的商品列表
     * @param param
     * @return
     */
    R promo(ProductPromoParam param);

    /**
     * 返回热销商品列表
     * @param param
     * @return
     */
    R hots(ProductHotParam param);

    /**
     * 返回商品列表
     * @return
     *
     */
    R list();

    R byCategory(ByCategoryParam byCategoryParam);

    R all(ByCategoryParam byCategoryParam);

    R detail(Integer productId);

    R pictures(Integer productId);

    R search(ProductSearchParam searchParam);

    List<Product> ids(ProductIdsParam productIdsParam);


    void subNumber(List<ProductNumberParam> productNumberParams);

    R adminList(AdminProductListParam listParam);

    R remove(Integer productId);

    R update(Product product);

    R save(ProductSaveParam param);
}

package com.atguigu.clients;


import com.atguigu.param.AdminProductListParam;
import com.atguigu.param.ProductIdsParam;
import com.atguigu.param.ProductSaveParam;
import com.atguigu.pojo.Product;
import com.atguigu.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "product-service")
@RequestMapping("/product")
public interface ProductClient {

    /**
     * 供收藏服务使用,根据传入的id,查询商品集合!
     * @return
     */
    @PostMapping("ids")
    List<Product> list(ProductIdsParam productIdsParam);

    @GetMapping("admin/list")
    R adminList(AdminProductListParam listParam);

    @PostMapping("admin/remove")
    R adminRemove(Integer productId);

    @PostMapping("admin/update")
    R adminUpdate(Product product);

    @PostMapping("admin/save")
    R adminSave(ProductSaveParam param);
}

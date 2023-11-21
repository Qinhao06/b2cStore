package com.atguigu.param;

import com.atguigu.pojo.Product;
import lombok.Data;

@Data
public class ProductSaveParam extends Product {

    private String pictures;
}

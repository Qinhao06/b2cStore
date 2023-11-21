package com.atguigu.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductDetailParam {

    @NotNull
    private Integer productID;

}

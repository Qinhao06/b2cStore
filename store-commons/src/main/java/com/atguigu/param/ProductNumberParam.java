package com.atguigu.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductNumberParam implements Serializable {

    static final long serialVersionUID = 1L;

    //商品id
    private Integer productId;
    //购买数量
    private Integer productNum;
}
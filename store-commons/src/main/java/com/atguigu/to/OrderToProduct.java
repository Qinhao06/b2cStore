package com.atguigu.to;


import lombok.Data;

import java.io.Serializable;

@Data
public class OrderToProduct implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer productId;
    private Integer num;
}

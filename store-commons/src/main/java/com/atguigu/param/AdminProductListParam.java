package com.atguigu.param;

import lombok.Data;

@Data
public class AdminProductListParam {

    private int  currentPage = 1;
    private int  pageSize = 15;
    private String search;

}

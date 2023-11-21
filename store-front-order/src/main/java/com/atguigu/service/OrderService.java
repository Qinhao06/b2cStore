package com.atguigu.service;

import com.atguigu.param.OrderParam;
import com.atguigu.param.PageParam;
import com.atguigu.utils.R;

public interface OrderService {
    R save(OrderParam orderParam);

    R list(Integer userId);

    R adminList(PageParam orderParam);
}

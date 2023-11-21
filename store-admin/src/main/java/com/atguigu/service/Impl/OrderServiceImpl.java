package com.atguigu.service.Impl;

import com.atguigu.clients.OrderClient;
import com.atguigu.param.PageParam;
import com.atguigu.service.OrderService;
import com.atguigu.utils.R;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {

    final
    OrderClient orderClient;

    public OrderServiceImpl(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    /**
     * @param pageParam
     * @return
     */
    @Override
    public R list(PageParam pageParam) {
        return orderClient.list(pageParam);
    }
}

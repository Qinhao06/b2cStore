package com.atguigu.controller;


import com.atguigu.param.PageParam;
import com.atguigu.service.OrderService;
import com.atguigu.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    final
    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public R list(PageParam pageParam){
        return orderService.list(pageParam);
    }

}

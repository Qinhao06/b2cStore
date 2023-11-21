package com.atguigu.cotroller;


import com.atguigu.param.OrderParam;
import com.atguigu.param.PageParam;
import com.atguigu.service.OrderService;
import com.atguigu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/save")
    public R save(@RequestBody  OrderParam orderParam){
        return orderService.save(orderParam);
    }


    @PostMapping("/list")
    public R list(@RequestBody OrderParam orderParam){
        return orderService.list(orderParam.getUserId());
    }

    @GetMapping("/admin/list")
    public R adminList(@RequestBody PageParam PageParam){
        return orderService.adminList(PageParam);
    }

}

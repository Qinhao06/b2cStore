package com.atguigu.clients;

import com.atguigu.param.PageParam;
import com.atguigu.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "order-service")
@RequestMapping("/order")
public interface OrderClient {

    @GetMapping("/admin/list")
    public R list(PageParam pageParam);


}

package com.atguigu.clients;


import com.atguigu.param.PageParam;
import com.atguigu.pojo.User;
import com.atguigu.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "user-service")
@RequestMapping("/user")
public interface UserClient {

    @PostMapping("admin/list")
    R listPage(@RequestBody PageParam pageParam);

    @PostMapping("admin/remove")
    R remove(@RequestBody Integer userId);

    @PostMapping("admin/update")
    R update(@RequestBody User user);

    @PostMapping("/register")
    R register(@RequestBody User user);
}

package com.atguigu.controller;


import com.atguigu.param.PageParam;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("list")
    @ResponseBody
    public Object list(PageParam pageParam){

        return userService.listPage(pageParam);
    }

    /**
     * 后台管理调用,删除用户数据
     * @param userId
     * @return
     */
    @PostMapping("/remove")
    @ResponseBody
    public Object remove( Integer userId){

        return userService.remove(userId);
    }

    @PostMapping("/update")
    @ResponseBody
    public Object update( User user){

        return userService.update(user);
    }


    @PostMapping("/save")
    public R save( User user){
        return userService.save(user);
    }



}

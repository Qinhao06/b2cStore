package com.atguigu.user.controller;


import com.atguigu.param.UserCheckParam;
import com.atguigu.param.UserLoginParam;
import com.atguigu.param.PageParam;
import com.atguigu.pojo.User;
import com.atguigu.user.service.UserService;
import com.atguigu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/check")
    public R check(@RequestBody @Validated UserCheckParam userCheckParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("username is null");
        }

        return userService.check(userCheckParam);

    }

    @PostMapping("/register")
    public R register(@RequestBody @Validated User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("something worry in params");
        }
        return  userService.register(user);
    }


    @PostMapping("/login")
    public R login(@RequestBody @Validated UserLoginParam userLoginParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("something worry in params");
        }

        return userService.login(userLoginParam);
    }

    /**
     * 后台管理调用
     * @param pageParam
     * @return
     */
    @PostMapping("/admin/list")
    public R listPage(@RequestBody PageParam pageParam){

        return userService.listPage(pageParam);
    }

    /**
     * 后台管理调用,删除用户数据
     * @param userId
     * @return
     */
    @PostMapping("admin/remove")
    public R remove(@RequestBody Integer userId){

        return userService.remove(userId);
    }

    @PostMapping("admin/update")
    public R update(@RequestBody User user){
        return userService.update(user);
    }



}

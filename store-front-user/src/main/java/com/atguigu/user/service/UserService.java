package com.atguigu.user.service;

import com.atguigu.param.UserCheckParam;
import com.atguigu.param.UserLoginParam;
import com.atguigu.param.PageParam;
import com.atguigu.pojo.User;
import com.atguigu.utils.R;

public interface UserService {


    /**
     *
     * @param userCheckParam
     * @return R
     * ok 001 check name  success
     * error 004 check name fail
     */
    R check(UserCheckParam userCheckParam);

    /**
     *
     * @param user
     * @return R
     * code
     * ok 001 register success
     * error 004 register fail
     */
    R register(User user);

    /**
     *
     * @param userLoginParam
     * @return R
     * code 001 004
     */
    R login(UserLoginParam userLoginParam);


    R listPage(PageParam pageParam);

    R remove(Integer userId);

    R update(User user);

}

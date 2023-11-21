package com.atguigu.service;

import com.atguigu.param.PageParam;
import com.atguigu.pojo.User;
import com.atguigu.utils.R;

public interface UserService {
    Object listPage(PageParam pageParam);

    Object remove(Integer userId);

    Object update(User user);

    R save(User user);
}

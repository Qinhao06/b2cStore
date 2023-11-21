package com.atguigu.service.Impl;

import com.atguigu.constants.UserConstants;
import com.atguigu.mapper.AdminMapper;
import com.atguigu.param.AdminUserParam;
import com.atguigu.pojo.AdminUser;
import com.atguigu.service.AdminUserService;
import com.atguigu.utils.MD5Util;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    final
    AdminMapper adminMapper;

    public AdminUserServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    /**
     * @param adminUserParam
     * @return
     */
    @Override
    public R login(AdminUserParam adminUserParam) {
        //密码加密处理
        //代码加密处理,注意加盐,生成常量
        String newPwd = MD5Util.encode(adminUserParam.getUserPassword() +
                UserConstants.USER_SALT);

        //数据库登录查询
        QueryWrapper<AdminUser> adminUserQueryWrapper =
                new QueryWrapper<>();

        adminUserQueryWrapper.eq("user_account",adminUserParam.getUserAccount());
        adminUserQueryWrapper.eq("user_password",newPwd);

        AdminUser adminUser = adminMapper.selectOne(adminUserQueryWrapper);
        //结果封装

        if (adminUser == null) {
            return R.fail("账号或者密码错误!");
        }

        R ok = R.ok("用户登录成功!", adminUser);

        return ok;
    }
}

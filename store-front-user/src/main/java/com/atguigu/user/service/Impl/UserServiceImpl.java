package com.atguigu.user.service.Impl;

import com.atguigu.constants.UserConstants;
import com.atguigu.param.UserCheckParam;
import com.atguigu.param.UserLoginParam;
import com.atguigu.param.PageParam;
import com.atguigu.pojo.User;
import com.atguigu.user.mapper.UserMapper;
import com.atguigu.user.service.UserService;
import com.atguigu.utils.MD5Util;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * check username
     * @param userCheckParam
     * @return
     */
    @Override
    public R check(UserCheckParam userCheckParam) {
        // 封装查询参数
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userCheckParam.getUserName());

        // 数据库查询
        Long count = userMapper.selectCount(queryWrapper);

        //校验返回结果
        return count == 0 ? R.ok("用户名可用") : R.fail("用户名不可用");

    }

    /**
     * register
     * @param user
     * @return R
     * code
     * ok 001 register success
     * error 004 register fail
     */
    @Override
    public R register(User user) {
        // 账号是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", user.getUserName());
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            log.info("user is already existed");
            return R.fail("user is already existed");
        }

        // 密码加密
        String password = user.getPassword();
        user.setPassword(MD5Util.encode(password + UserConstants.USER_SALT));

        // 数据库操作并返回结果
        int insert = userMapper.insert(user);

        if(insert == 0){
            log.info("register fail");
            return R.fail("register fail");
        }

        return R.ok("register successs");

    }

    /**
     * 登录检查
     * @param userLoginParam
     * @return R
     * code 001 004
     */
    @Override
    public R login(UserLoginParam userLoginParam) {
        // 加盐
        String password = userLoginParam.getPassword();
        String encodePassword = MD5Util.encode(password + UserConstants.USER_SALT);

        // 数据库查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userLoginParam.getUserName());
        queryWrapper.eq("password", encodePassword);
        User user = userMapper.selectOne(queryWrapper);

        if(user == null){
            log.info("用户不存在");
            return  R.fail("用户不存在");
        }

        user.setPassword(null);
        return R.ok("登录成功", user);
    }

    /**
     * @param pageParam
     * @return
     */
    @Override
    public R listPage(PageParam pageParam) {
        int currentPage = pageParam.getCurrentPage();
        int pageSize = pageParam.getPageSize();

        IPage<User> page = new Page<>(currentPage, pageSize);
        page = userMapper.selectPage(page, null);

        return R.ok("查询成功！", page.getRecords(), page.getTotal());


    }

    /**
     * @param userId
     * @return
     */
    @Override
    public R remove(Integer userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        int i = userMapper.delete(queryWrapper);
        if(i > 0){
            return R.ok("删除成功");
        }
        return R.fail("删除失败");
    }

    /**
     * @param user
     * @return
     */
    @Override
    public R update(User user) {

        Integer userId = user.getUserId();
        String password = user.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Long count = userMapper.selectCount(queryWrapper);

        if(count == 0){
            user.setPassword(MD5Util.encode(password + UserConstants.USER_SALT));
            
        }

        int rows = userMapper.updateById(user);

        if(rows > 0){
            return R.ok("更新成功");
        }

        return R.fail("更新失败");
    }



}

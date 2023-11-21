package com.atguigu.service.Impl;

import com.atguigu.clients.UserClient;
import com.atguigu.param.PageParam;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.utils.R;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    final
    UserClient userClient;

    public UserServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    /**
     * @param pageParam
     * @return
     */
    @Cacheable(value = "list.user", key = "#pageParam.currentPage" + "-" + "#pageParam.pageSize")
    @Override
    public Object listPage(PageParam pageParam) {
        return userClient.listPage(pageParam);
    }

    /**
     * @param userId
     * @return
     */
    @CacheEvict(value = "list.user", allEntries = true)
    @Override
    public Object remove(Integer userId) {
        return userClient.remove(userId);
    }

    /**
     * @param user
     * @return
     */
    @CacheEvict(value = "list.user", allEntries = true)
    @Override
    public Object update(User user) {
        return userClient.update(user);
    }


    /**
     * @param user
     * @return
     */
    @Caching(
            evict = {
                    @CacheEvict(value = "list.user", allEntries = true)
            }
    )
    @Override
    public R save(User user) {
        return userClient.register(user);
    }
}

package com.atguigu.service.Impl;

import com.atguigu.mapper.CarouselMapper;
import com.atguigu.pojo.Carousel;
import com.atguigu.service.CarouselService;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {


    final
    CarouselMapper carouselMapper;

    public CarouselServiceImpl(CarouselMapper carouselMapper) {
        this.carouselMapper = carouselMapper;
    }

    /**
     * 查询轮播图
     *
     * @return 包含查询结果的 R 对象
     */
    @Cacheable(value = "list.carousel", key = "#root.methodName")
    @Override
    public R list() {
        // 查询前五条
        int limit = 5;
        //查询数据库
        QueryWrapper<Carousel> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("priority");
        queryWrapper.last("limit " + limit);
        List<Carousel> records = carouselMapper.selectList(queryWrapper);
        return R.ok(records);
    }
}

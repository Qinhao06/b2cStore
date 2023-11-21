package com.atguigu.controller;


import com.atguigu.service.CarouselService;
import com.atguigu.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/carousel")
public class CarouselController {

    final
    CarouselService carouselService;

    public CarouselController(CarouselService carouselService) {
        this.carouselService = carouselService;
    }


    /**
     * 获取轮播图列表
     *
     * @return 返回一个包含列表信息的 R 对象
     */
    @PostMapping("/list")
    public R list(){
        return carouselService.list();
    }



}

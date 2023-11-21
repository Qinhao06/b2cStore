package com.atguigu.service;

import com.atguigu.param.CartParam;
import com.atguigu.param.CollectSaveOrRemoveParam;
import com.atguigu.utils.R;

import java.util.List;

public interface CartService {
    R save(CartParam cartParam);

    R list(CartParam cartParam);

    R update(CartParam cartParam);

    R remove(CartParam cartParam);

    void clear(List<Integer> cartIds);
}

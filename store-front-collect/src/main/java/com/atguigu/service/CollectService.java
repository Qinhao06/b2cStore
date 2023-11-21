package com.atguigu.service;

import com.atguigu.param.CollectListParam;
import com.atguigu.param.CollectSaveOrRemoveParam;
import com.atguigu.utils.R;

public interface CollectService {
    R save(CollectSaveOrRemoveParam collectSaveOrRemoveParam);

    R list(CollectListParam collectListParam);

    R remove(CollectSaveOrRemoveParam collectSaveOrRemoveParam);
}

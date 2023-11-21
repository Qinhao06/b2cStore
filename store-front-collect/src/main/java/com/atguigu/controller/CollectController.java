package com.atguigu.controller;


import com.atguigu.param.CollectListParam;
import com.atguigu.param.CollectSaveOrRemoveParam;
import com.atguigu.service.CollectService;
import com.atguigu.utils.R;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collect")
public class CollectController {

    final
    CollectService collectService;

    public CollectController(CollectService collectService) {
        this.collectService = collectService;
    }

        /**
     * 保存购物车数据
     *
     * @param collectSaveOrRemoveParam 保存的参数
     * @return 保存结果
     */
    @PostMapping("/save")
    public R save(@RequestBody @Validated CollectSaveOrRemoveParam collectSaveOrRemoveParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail(bindingResult.getFieldError().getDefaultMessage());
        }
        return collectService.save(collectSaveOrRemoveParam);
    }


    /**
     * 查询收藏列表
     *
     * @param collectListParam 收藏列表参数
     * @return 返回查询结果
     */
    @PostMapping("/list")
    public R list(@RequestBody @Validated CollectListParam collectListParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail(bindingResult.getFieldError().getDefaultMessage());
        }
        return collectService.list(collectListParam);
    }


    @PostMapping("/remove")
    public R remove(@RequestBody @Validated CollectSaveOrRemoveParam collectSaveOrRemoveParam,
                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail(bindingResult.getFieldError().getDefaultMessage());
        }
        return collectService.remove(collectSaveOrRemoveParam);
    }

}

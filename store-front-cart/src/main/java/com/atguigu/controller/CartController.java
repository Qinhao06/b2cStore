package com.atguigu.controller;


import com.atguigu.param.CartParam;
import com.atguigu.param.CollectSaveOrRemoveParam;
import com.atguigu.service.CartService;
import com.atguigu.utils.R;
import org.checkerframework.checker.units.qual.C;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    final
    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/save")
    public R save(@RequestBody  CartParam cartParam) {

        return cartService.save(cartParam);
    }

    @PostMapping("/list")
    public R list(@RequestBody CartParam cartParam) {
        return cartService.list(cartParam);
    }

    @PostMapping("/update")
    public R update(@RequestBody CartParam cartParam) {
        return cartService.update(cartParam);
    }

    @PostMapping("/remove")
    public R remove(@RequestBody CartParam cartParam) {
        return cartService.remove(cartParam);
    }

}

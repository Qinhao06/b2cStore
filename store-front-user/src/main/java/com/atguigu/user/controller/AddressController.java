package com.atguigu.user.controller;


import com.atguigu.param.AddressListParam;
import com.atguigu.param.AddressRemoveParam;
import com.atguigu.param.AddressSaveParam;
import com.atguigu.pojo.Address;
import com.atguigu.user.service.AddressService;
import com.atguigu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/address")
public class AddressController {

    final
    AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/list")
    public R addressList(@RequestBody @Validated AddressListParam addressListParam, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return R.fail("userId is null");
        }

        return addressService.addressList(addressListParam);
    }

    @PostMapping("/save")
    public R save(@RequestBody @Validated AddressSaveParam addressSaveParam, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return R.fail("The address format is incorrect");
        }
        Address address = new Address();
        address.setUserId(addressSaveParam.getUserId());
        address.setLinkman(addressSaveParam.getAdd().getLinkman());
        address.setPhone(addressSaveParam.getAdd().getPhone());
        address.setAddress(addressSaveParam.getAdd().getAddress());
        return addressService.save(address);
    }

    @PostMapping("/remove")
    public R remove(@RequestBody @Validated AddressRemoveParam addressRemoveParam, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return R.fail("id is null");
        }
        return addressService.remove(addressRemoveParam);
    }



}

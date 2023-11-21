package com.atguigu.user.service;


import com.atguigu.param.AddressListParam;
import com.atguigu.param.AddressRemoveParam;
import com.atguigu.pojo.Address;
import com.atguigu.utils.R;

public interface AddressService {


    /**
     * 返回对应用户的地址
     * @param addressListParam
     * @return
     */
    R addressList(AddressListParam addressListParam);

    /**
     * 保存用户地址
     * @param address
     * @return
     */
    R save(Address address);

    /**
     * 删除指定 id 的地址
     * @param addressRemoveParam
     * @return
     */
    R remove(AddressRemoveParam addressRemoveParam);
}

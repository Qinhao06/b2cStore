package com.atguigu.user.service.Impl;

import com.atguigu.param.AddressListParam;
import com.atguigu.param.AddressRemoveParam;
import com.atguigu.pojo.Address;
import com.atguigu.user.mapper.AddressMapper;
import com.atguigu.user.service.AddressService;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    private List<Address> getAddressList(Integer userId) {
        // 构造查询语句
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Address> addressList = addressMapper.selectList(queryWrapper);
        return addressList;
    }

    /**
     * 返回指定用户的全部地址
     *
     * @param addressListParam
     * @return
     */
    @Override
    public R addressList(AddressListParam addressListParam) {

        // 查询数据库
        List<Address> addressList = getAddressList(addressListParam.getUserId());
        if(addressList == null || addressList.isEmpty()){
            return R.fail("no address exists");
        }
        return R.ok("get address", addressList);
    }

    /**
     * 保存用户地址
     *
     * @param address
     * @return
     */
    @Override
    public R save(Address address) {
        // 将地址插入数据库
        int insert = addressMapper.insert(address);

        if(insert < 1){
            return R.fail("something wrong in creating address");
        }

        // 查询当前用户所有地址并返回
        List<Address> addressList = getAddressList(address.getUserId());
        if(addressList == null || addressList.isEmpty()){
            return
                R.fail("no address exists, something wrong");
        }

        return R.ok(addressList);
    }

    /**
     * 删除指定 id 的地址
     *
     * @param addressRemoveParam
     * @return
     */
    @Override
    public R remove(AddressRemoveParam addressRemoveParam) {
        // 删除对应地址
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", addressRemoveParam.getId());
        int delete = addressMapper.delete(queryWrapper);
        if(delete < 1){
            return R.fail("delete address failed");
        }
        return R.ok("delete address success");
    }
}

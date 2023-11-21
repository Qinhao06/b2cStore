package com.atguigu.service.Impl;

import com.atguigu.clients.ProductClient;
import com.atguigu.mapper.CartMapper;
import com.atguigu.param.CartParam;
import com.atguigu.param.CollectSaveOrRemoveParam;
import com.atguigu.param.ProductIdsParam;
import com.atguigu.pojo.Cart;
import com.atguigu.pojo.Product;
import com.atguigu.service.CartService;
import com.atguigu.utils.R;
import com.atguigu.vo.CartVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class CartServiceImpl implements CartService {


    final
    CartMapper cartMapper;

    final
    ProductClient productClient;

    public CartServiceImpl(CartMapper cartMapper, ProductClient productClient) {
        this.cartMapper = cartMapper;
        this.productClient = productClient;
    }

    /**
     * @param cartParam
     * @return
     */
    @Override
    public R save(CartParam cartParam) {

        // 获取商品信息
        List<Integer> ids = new ArrayList<>();
        ids.add(cartParam.getProductId());
        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(ids);
        List<Product> productList = productClient.list(productIdsParam);

        if(productList == null ||  productList.isEmpty())
        {
            return R.fail("商品已经被删除,无法添加!");
        }

        // 检查是否可以存储
        Product product = productList.get(0);
        if(product.getProductNum() == 0)
        {
            R fail = R.fail("库存不足");
            fail.setCode("003");
            return fail;
        }

        // 检查是否重复添加
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cartParam.getUserId())
               .eq("product_id",cartParam.getProductId());
        Cart cart = cartMapper.selectOne(queryWrapper);
        if(cart != null){
            cart.setNum(cart.getNum()+1);
            cartMapper.updateById(cart);
            R ok = R.ok("商品已经在购物车,数量+1!");
            ok.setCode("002");
            return ok;
        }

        // 新添加
        cart = new Cart();
        cart.setNum(1);
        cart.setProductId(cartParam.getProductId());
        cart.setUserId(cartParam.getUserId());

        cartMapper.insert(cart);

        // 封装返回结果
        CartVo cartVo = new CartVo(product,cart);
        return R.ok(cartVo);
    }

    /**
     * @param cartParam
     * @return
     */
    @Override
    public R list(CartParam cartParam) {
        Integer userId = cartParam.getUserId();
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);

        List<CartVo> cartVoList = new ArrayList<>();
        if(cartList ==null || cartList.isEmpty())
        {
           return R.ok("购物车为空", cartVoList);
        }

        List<Integer> ids = new ArrayList<>();
        for(Cart cart : cartList)
        {
            ids.add(cart.getProductId());
        }

        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(ids);

        List<Product> productList = productClient.list(productIdsParam);

        Map<Integer, Product> map = productList.stream().collect(
                java.util.stream.Collectors.toMap(Product::getProductId, product -> product, (oldValue, newValue) -> oldValue)
        );


        for(Cart cart : cartList){
            cartVoList.add(new CartVo(map.get(cart.getProductId()),cart));
        }
        return R.ok(cartVoList);
    }

    /**
     * @param cartParam
     * @return
     */
    @Override
    public R update(CartParam cartParam) {

        List<Integer> ids = new ArrayList<>();
        ids.add(cartParam.getProductId());
        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(ids);
        List<Product> productList = productClient.list(productIdsParam);
        if(productList == null ||  productList.isEmpty())
        {
            return R.fail("商品已经被删除,无法修改!");
        }
        if(productList.get(0).getProductNum() < cartParam.getNum())
        {
            R fail = R.fail("库存不足");
            fail.setCode("003");
            return fail;
        }
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cartParam.getUserId())
               .eq("product_id",cartParam.getProductId());
        Cart cart = cartMapper.selectOne(queryWrapper);
        if(cart == null){
            return R.fail("用户未添加该商品,无法修改!");
        }
        cart.setNum(cartParam.getNum());
        cartMapper.updateById(cart);
        return R.ok("修改成功");

    }

    /**
     * @param cartParam
     * @return
     */
    @Override
    public R remove(CartParam cartParam) {
        if(cartParam.getProductId() == null)
        {
            return R.fail("商品id不能为空!");
        }
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cartParam.getUserId());
        queryWrapper.eq("product_id",cartParam.getProductId());
        //删除数据
        cartMapper.delete(queryWrapper);
        return R.ok("删除数据成功!");
    }

    /**
     * @param cartIds
     */
    @Override
    public void clear(List<Integer> cartIds) {
        cartMapper.deleteBatchIds(cartIds);
    }
}

package com.atguigu.service.Impl;

import com.atguigu.clients.ProductClient;
import com.atguigu.mapper.OrderMapper;
import com.atguigu.param.OrderParam;
import com.atguigu.param.PageParam;
import com.atguigu.param.ProductIdsParam;
import com.atguigu.param.ProductNumberParam;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.Product;
import com.atguigu.service.OrderService;
import com.atguigu.utils.R;
import com.atguigu.vo.AdminOrderVo;
import com.atguigu.vo.CartVo;
import com.atguigu.vo.OrderVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    final
    OrderMapper orderMapper;

    final
    RabbitTemplate rabbitTemplate;

    final
    ProductClient productClient;

    public OrderServiceImpl(OrderMapper orderMapper, RabbitTemplate rabbitTemplate, ProductClient productClient) {
        this.orderMapper = orderMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.productClient = productClient;
    }


    /**
     * @param orderParam
     * @return
     */
    @Override
    @Transactional
    public R save(OrderParam orderParam) {


        List<CartVo> cartVoList = orderParam.getProducts();
        Integer userId = orderParam.getUserId();
        long time = System.currentTimeMillis();

        List<Order> orderList = new ArrayList<>();
        List<Integer> cartIds = new ArrayList<>();
        List<ProductNumberParam> productNumberParams = new ArrayList<>();

        for (CartVo cartVo : cartVoList) {
            cartIds.add(cartVo.getId());

            Order order = new Order();
            order.setOrderTime(time);
            order.setUserId(userId);
            order.setOrderId(time);
            order.setProductNum(cartVo.getNum());
            order.setProductPrice(cartVo.getPrice());
            order.setProductId(cartVo.getProductID());
            orderList.add(order);

            ProductNumberParam productNumberParam = new ProductNumberParam();
            productNumberParam.setProductNum(cartVo.getNum());
            productNumberParam.setProductId(cartVo.getProductID());
            productNumberParams.add(productNumberParam);
        }

        for (Order order : orderList) {
            orderMapper.insert(order);
        }

        //修改商品库存 [product-service] [异步通知]
        /**
         *  交换机: topic.ex
         *  routingkey: sub.number
         *  消息: 商品id和减库存数据集合
         */
        rabbitTemplate.convertAndSend("topic.ex","sub.number",productNumberParams);
        //清空对应购物车数据即可 [注意: 不是清空用户所有的购物车数据] [cart-service] [异步通知]
        /**
         * 交换机:topic.ex
         * routingkey: clear.cart
         * 消息: 要清空的购物车id集合
         */
        rabbitTemplate.convertAndSend("topic.ex","clear.cart",cartIds);


        return R.ok("订单生成成功");
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Order> orderList = orderMapper.selectList(queryWrapper);

        List<Integer> productIds = new ArrayList<>();
        for (Order order : orderList) {
            productIds.add(order.getProductId());
        }

        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(productIds);
        List<Product> productList = productClient.list(productIdsParam);

        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, product -> product));


        Map<Long, List<Order>> collect = orderList.stream().
                collect(Collectors.groupingBy(order -> order.getOrderId()));
        Collection<List<Order>> values = collect.values();
        List<List<OrderVo>> results  = new ArrayList<>();
        for (List<Order> value : values) {
            List<OrderVo> orderVos = new ArrayList<>();
            for (Order order : value) {
                OrderVo orderVo = new OrderVo();
                Product pr = productMap.get(order.getProductId());
                orderVo.setProductPicture(pr.getProductPicture());
                orderVo.setProductName(pr.getProductName());
                orderVo.setId(order.getId());
                orderVo.setOrderId(order.getOrderId());
                orderVo.setOrderTime(order.getOrderTime());
                orderVo.setUserId(order.getUserId());
                orderVo.setProductNum(order.getProductNum());
                orderVo.setProductId(order.getProductId());
                orderVo.setProductPrice(order.getProductPrice());
                orderVos.add(orderVo);

            }
            results.add(orderVos);

        }
        return R.ok(results);
    }

    /**
     * @param orderParam
     * @return
     */
    @Override
    public R adminList(PageParam orderParam) {
        List<Order> orderList = orderMapper.selectList(null);
        List<AdminOrderVo> orderVoList = new ArrayList<>();
        Map<Long, List<Order>> collect = orderList.stream().collect(
                Collectors.groupingBy(
                        Order::getOrderId,
                        Collectors.toList()
                )
        );
        for (List<Order> value : collect.values()) {
            AdminOrderVo orderVo  = new AdminOrderVo();
            Order order1 = value.get(0);
            orderVo.setOrderTime(order1.getOrderTime());
            orderVo.setOrderId(order1.getOrderId());
            orderVo.setUserId(order1.getUserId());
            orderVo.setProductNum(0);
            orderVo.setOrderPrice(0.0);
            orderVo.setOrderNum(0);

            for (Order order : value) {
                orderVo.setOrderNum(orderVo.getOrderNum() + order.getProductNum());
                orderVo.setOrderPrice(orderVo.getOrderPrice()
                        + order.getProductPrice()*order.getProductNum());
                orderVo.setProductNum(orderVo.getProductNum() + 1);
            }
            orderVoList.add(orderVo);
        }

        return R.ok(orderVoList);
    }
}

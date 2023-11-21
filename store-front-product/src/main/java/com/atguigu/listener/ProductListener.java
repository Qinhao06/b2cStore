package com.atguigu.listener;

import com.atguigu.param.ProductNumberParam;
import com.atguigu.service.ProductService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductListener {
    final
    ProductService productService;

    public ProductListener(ProductService productService) {
        this.productService = productService;
    }


    @RabbitListener( bindings = @QueueBinding(
            value = @Queue(name = "sub.queue"),
            exchange = @Exchange(value = "topic.ex"),
            key = "sub.number"
    ))
    public void subNumber(List<ProductNumberParam> productNumberParams){
        productService.subNumber(productNumberParams);
    }
}

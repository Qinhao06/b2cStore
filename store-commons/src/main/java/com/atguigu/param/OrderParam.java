package com.atguigu.param;

import com.atguigu.vo.CartVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderParam implements Serializable {

    static final long serialVersionUID = 1L;

    @JsonProperty("user_id")
    private Integer userId;

    private List<CartVo> products;
}

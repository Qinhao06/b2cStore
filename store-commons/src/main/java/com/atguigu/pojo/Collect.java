package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@TableName("collect")
public class Collect {

    public static final Long serialVersionUID = 1L;

    @TableId(type= IdType.AUTO)
    private Integer id;

    @JsonProperty("collect_time")
    private Long collectTime;

    @JsonProperty("user_id")
    @NotNull
    private  Integer userId;

    @JsonProperty("product_id")
    @NotNull
    private Integer productId;


}

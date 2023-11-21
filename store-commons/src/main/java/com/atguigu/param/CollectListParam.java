package com.atguigu.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CollectListParam {

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

}


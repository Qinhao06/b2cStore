package com.atguigu.param;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressSaveParam {

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("add")
    private AddressAddParam add;
}

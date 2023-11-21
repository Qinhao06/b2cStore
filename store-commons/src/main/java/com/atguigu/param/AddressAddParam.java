package com.atguigu.param;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddressAddParam {

    @NotBlank
    private String linkman;
    @NotBlank
    private String phone;
    @NotBlank
    private String address;
}

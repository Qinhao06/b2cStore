package com.atguigu.param;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginParam {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;


}

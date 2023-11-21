package com.atguigu.param;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
public class UserCheckParam {

    @NotBlank
    private String userName;

}

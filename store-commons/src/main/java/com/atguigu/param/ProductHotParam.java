package com.atguigu.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductHotParam {

    @NotNull
    private List<String> categoryName;

}

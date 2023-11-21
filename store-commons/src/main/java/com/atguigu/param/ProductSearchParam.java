package com.atguigu.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ProductSearchParam {

    @NotBlank
    private String search;
    private Integer currentPage = 1;
    private Integer pageSize = 15;

}

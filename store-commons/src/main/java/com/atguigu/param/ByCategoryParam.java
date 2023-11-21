package com.atguigu.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ByCategoryParam{
    private List<Integer> categoryID;
    private Integer currentPage = 1;
    private Integer pageSize = 15;

    @Override
    public String toString() {
        return "ByCategoryParam{" +
                "categoryID=" + categoryID.toString() +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }
}

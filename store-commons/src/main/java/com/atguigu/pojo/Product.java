package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@TableName("product")
public class Product implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(value = "product_id")
    @JsonProperty("product_id")
    @NotNull
    private Integer productId;

    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("category_id")
    private Integer categoryId;
    @JsonProperty("product_title")
    private  String productTitle;
    @JsonProperty("product_intro")
    private  String productIntro;
    @JsonProperty("product_picture")
    private  String productPicture;
    @JsonProperty("product_price")
    @NotNull
    private Double productPrice;
    @JsonProperty("product_selling_price")
    @NotNull
    private Double productSellingPrice;
    @JsonProperty("product_num")
    private Integer productNum;
    @JsonProperty("product_sales")
    private Integer productSales;
}

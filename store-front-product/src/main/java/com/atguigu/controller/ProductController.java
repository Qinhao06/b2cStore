package com.atguigu.controller;

import com.atguigu.param.*;
import com.atguigu.pojo.Product;
import com.atguigu.service.ProductService;
import com.atguigu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    final
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 返回指定类别的商品列表
     * @param param
     * @param bindingResult
     * @return
     */
    @PostMapping("/promo")
    public R promo(@RequestBody @Validated ProductPromoParam param, BindingResult  bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("categoryName param is null");
        }
        return productService.promo(param);
    }

        /**
     * 发布热门商品请求处理方法
     * @param param 参数对象，包含热门商品的类别名称
     * @param bindingResult 用于检查参数是否合法的结果对象
     * @return 返回热门商品信息的对象
     */
    @PostMapping("/hots")
    public R hots(@RequestBody @Validated ProductHotParam param, BindingResult  bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("categoryName参数为空");
        }
        return productService.hots(param);
    }


        /**
     * 分类列表
     * 使用POST方法获取分类列表
     * 请求路径：/category/list
     * 返回一个R对象，包含分类列表信息
     */
    @PostMapping("/category/list")
    public R cList(){
        return productService.list();
    }


        /**
     * 通过分类获取商品列表
     *
     * @param byCategoryParam 分类参
     * @return 商品列表或失败结果
     */
    @PostMapping("/bycategory")
    public R byCategory(@RequestBody ByCategoryParam byCategoryParam){
        return productService.byCategory(byCategoryParam);
    }

        /**
     * 根据分类获取所有产品信息
     *
     * @param byCategoryParam 分类参数
     * @return 返回所有产品信息
     */
    @PostMapping("/all")
    public R all(@RequestBody ByCategoryParam byCategoryParam){
        return productService.all(byCategoryParam);
    }


        /**
     * 根据产品ID获取产品详细信息
     *
     * @param detailParam 产品ID
     * @param bindingResult 绑定结果
     * @return 根据产品ID获取产品详细信息的结果
     */
    @PostMapping("/detail")
    public R detail(@RequestBody @Validated ProductDetailParam detailParam, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return R.fail("productId is null");
        }
        return productService.detail(detailParam.getProductID());
    }

        /**
     * 根据产品ID获取图片列表
     *
     * @param detailParam 产品详情参数
     * @param bindingResult 绑定结果
     * @return 返回图片列表结果
     */
    @PostMapping("/pictures")
    public R pictures(@RequestBody @Validated ProductDetailParam detailParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("productId is null");
        }
        return productService.pictures(detailParam.getProductID());
    }


        /**
     * 根据产品搜索参数搜索产品
     *
     * @param searchParam 搜索参数
     * @param bindingResult 绑定结果
     * @return 搜索结果
     */
    @PostMapping("/search")
    public R search(@RequestBody @Validated ProductSearchParam searchParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("productId is null");
        }
        return productService.search(searchParam);
    }


    /**
     * 供收藏服务使用,根据传入的id,查询商品集合!
     * @return
     */
    @PostMapping("ids")
    public List<Product> list(@RequestBody ProductIdsParam productIdsParam){

        return productService.ids(productIdsParam);
    }

    @GetMapping("admin/list")
    public R adminList(@RequestBody AdminProductListParam listParam){
        return productService.adminList(listParam);
    }


    @PostMapping("admin/remove")
    public R adminRemove(@RequestBody Integer product){
        return productService.remove(product);
    }

    @PostMapping("admin/update")
    public R adminUpdate(@RequestBody Product product){
        return productService.update(product);
    }

    @PostMapping("admin/save")
    public R adminSave(@RequestBody ProductSaveParam param){
        return productService.save(param);
    }


}

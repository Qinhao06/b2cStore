package com.atguigu.service.Impl;

import com.atguigu.clients.ProductClient;
import com.atguigu.mapper.CollectMapper;
import com.atguigu.param.CollectListParam;
import com.atguigu.param.CollectSaveOrRemoveParam;
import com.atguigu.param.ProductIdsParam;
import com.atguigu.pojo.Collect;
import com.atguigu.service.CollectService;
import com.atguigu.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class CollectServiceImpl implements CollectService {


    final
    ProductClient productClient;

    private final CollectMapper collectMapper;

    public CollectServiceImpl(CollectMapper collectMapper, ProductClient productClient) {
        this.collectMapper = collectMapper;
        this.productClient = productClient;
    }


    /**
     * @param collectSaveOrRemoveParam
     * @return
     */
    @Override
    public R save(CollectSaveOrRemoveParam collectSaveOrRemoveParam) {

        // 获取数据
        Integer productId = collectSaveOrRemoveParam.getProductId();
        Integer userId = collectSaveOrRemoveParam.getUserId();


        // 构造查询条件，如未加入，则加入其中
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        queryWrapper.eq("user_id", userId);

        Long count = collectMapper.selectCount(queryWrapper);

        if (count != 0) {
            return R.fail("该商品已经添加收藏，请到我的收藏查看");
        }

        Collect collect = new Collect();
        collect.setProductId(productId);
        collect.setUserId(userId);
        collect.setCollectTime(System.currentTimeMillis());

        int insert = collectMapper.insert(collect);

        if (insert == 0) {
            return R.fail("商品添加失败");
        }
        return R.ok("商品添加成功!");
    }

    /**
     * @param collectListParam
     * @return
     */
    @Override
    public R list(CollectListParam collectListParam) {

        Integer userId = collectListParam.getUserId();
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("collect_time");
        queryWrapper.select("product_id");

        List<Object> objects = collectMapper.selectObjs(queryWrapper);

        Integer[] idsArray = objects.toArray(new Integer[]{});

        List<Integer> productIdsList = Arrays.asList(idsArray);

        if (productIdsList == null || productIdsList.isEmpty()) {
            return R.fail("未收藏任何商品");
        }

        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(productIdsList);

        return R.ok(productClient.list(productIdsParam));
    }

    /**
     * @param collectSaveOrRemoveParam
     * @return
     */
    @Override
    public R remove(CollectSaveOrRemoveParam collectSaveOrRemoveParam) {

        Integer productId = collectSaveOrRemoveParam.getProductId();
        Integer userId = collectSaveOrRemoveParam.getUserId();

        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("product_id", productId);
        int delete = collectMapper.delete(queryWrapper);
        if (delete == 0) {
            return R.fail("商品未收藏");
        }
        return R.ok("删除成功");
    }
}

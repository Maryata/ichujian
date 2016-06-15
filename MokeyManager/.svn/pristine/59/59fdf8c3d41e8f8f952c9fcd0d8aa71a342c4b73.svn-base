package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 商品分类管理
 */
public interface EKShopProductCateDao {

    // 分页显示商品分类
    Map<String, Object> productCateList(int page);

    // 查询非当前商品分类的其他所有商品
    Map<String, Object> getAllProduct(int page, String cid, String name);

    // 查询当前商品分类的商品
    Map<String, Object> getCurrCateProduct(int page, String cid, String name);

    // 查询商品是否已经存在于当前分类
    int isExist(String cid, String pid);

    // 新增商品到当前分类
    void add(String cid, String pid, String order);

    // 更新当前分类中的商品
    void update(String cid, String pid, String order);

    // 将指定商品移除出当前分类
    void remove(String cid, String pid);

}

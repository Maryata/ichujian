package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 商品分类管理
 */
public interface EKShopProductCateService {

    /**
     * 分页显示游戏攻略分类
     *
     * @param page 分页
     * @return
     */
    Map<String, Object> productCateList(int page);

    /**
     * 查询非当前游戏攻略分类的其他所有游戏
     *
     * @param page             分页
     * @param cid              分类id
     * @param name             游戏名称
     * @return
     */
    Map<String, Object> getAllProduct(int page, String cid, String name);

    /**
     * 查询当前游戏攻略分类的游戏
     *
     * @param page 分页
     * @param cid  分类id
     * @param name 游戏名称
     * @return
     */
    Map<String, Object> getCurrCateProduct(int page, String cid, String name);

    /**
     * 游戏攻略分类维护
     *
     * @param cid       分类id
     * @param pid       游戏id
     * @param removePid 移除出当前分类的游戏id
     * @param order     排序
     */
    void handleCate(String cid, String pid, String removePid,
                    String order);

}

package com.sys.ekey.tbk.service;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/8/15.
 */
public interface TbkService {
    /**
     * 广告位
     *
     * @param type
     * @return
     */
    List<Map<String, Object>> advert(String type);

    /**
     * 分类列表
     *
     * @return
     */
    List<Map<String, Object>> cateList();

    /**
     * 主题查询
     *
     * @return
     */
    List<Map<String, Object>> theme(String day);

    /**
     * 商品查询
     *
     * @return
     */
    List<Map<String, Object>> product(String day);

    /**
     * 主题详情
     *
     * @return
     */
    List<Map<String, Object>> themeDetail(String aid);

    /**
     * 主题商品详情
     *
     * @param aid
     * @return
     */
    List<Map<String, Object>> themeProductDetail(String aid);

    /**
     * 商品列表
     *
     * @param aid
     * @return
     */
    List<Map<String, Object>> productList(String aid);

    /**
     * 添加主题收藏
     *
     * @param aid
     * @param uid
     */
    void collect(String aid, String uid);

    /**
     * 查询是否收藏牧歌主题
     *
     * @param aid
     * @param uid
     * @return
     */
    List<Map<String, Object>> collectList(String aid, String uid);

    /**
     * 收藏列表查询
     *
     * @param uid
     * @return
     */
    List<Map<String, Object>> collects(String uid);


}

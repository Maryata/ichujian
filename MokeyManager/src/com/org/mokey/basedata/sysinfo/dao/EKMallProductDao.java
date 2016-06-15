package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/4/25.
 */
public interface EKMallProductDao {
    /**
     * 查询列表信息
     *
     * @param page
     * @param title
     * @param type
     * @return
     */
    Map<String, Object> list(int page, String title, String type);

    /**
     * 添加商品信息
     *
     * @param id
     * @param
     * @param logo
     * @param
     * @param
     */
    void addProduct(String id, String title, String subTitle, String function, String anAbstract, String cost, String total, String type, String carriage, String logo, String amount, String islive, String detail, String illustr, String modifier);

    /**
     * 删除商品信息
     *
     * @param id
     */
    void toDel(String id);

    /**
     * 更新商品信息
     *
     * @param id
     * @param title
     * @param logo
     * @param function
     * @param anAbstract
     */
    void updateProduct(String id, String title, String subTitle, String function, String anAbstract, String cost, String total, String type, String carriage, String logo, String amount, String islive, String detail, String illustr, String modifier);

    /**
     * 查询商品详情
     *
     * @param id
     * @return
     */
    List<Map<String, Object>> selectOne(String id);

    /**
     * 查询商品吗列表
     *
     * @param page
     * @param type
     * @return
     */
    Map<String, Object> getCodeList(int page, String pid, String type);

    /**
     * 上传商品码
     *
     * @param list
     */
    void upload(List<Object[]> list);
}

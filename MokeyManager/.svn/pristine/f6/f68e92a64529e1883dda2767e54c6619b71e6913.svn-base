package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * 广告位维护
 * Created by vpc on 2016/4/20.
 */
public interface EKMallAdvertService {
    /**
     * 加载列表
     *
     * @param page
     * @return
     */
    Map<String, Object> list(int page);

    /**
     * 查询商品信息
     *
     * @return
     */
    List<Map<String, Object>> getList();

    /**
     * 添加广告位
     *
     * @param id
     * @param name
     * @param logo
     * @param pid
     * @param order
     */
    void addAdvert(String id, String name, String logo, String pid, String order);

    /**
     * 删除广告位
     *
     * @param id
     */
    void toDel(String id);

    /**
     * 更新广告位
     *
     * @param id
     * @param name
     * @param logo
     * @param pid
     * @param order
     */
    void updateAdvert(String id, String name, String logo, String pid, String order);
}

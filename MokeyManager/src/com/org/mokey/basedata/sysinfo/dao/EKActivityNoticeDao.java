package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ： 活动注意事项
 */
public interface EKActivityNoticeDao {
    /**
     * e键 ：活动注意事项　：　获取列表
     *
     * @param page
     * @return
     */
    Map<String, Object> ekActivityNoticeList(int page);

    /**
     * 添加 活动注意事项
     *
     * @param id
     * @param name
     * @param logo
     */
    void addTag(String id, String name, String logo);

    /**
     * 删除  ：活动注意事项
     *
     * @param id
     */
    void toDel(String id);

    /**
     * 更新  ： 活动注意事项
     *
     * @param id
     * @param name
     * @param logo
     * @param
     */
    void updateTag(String id, String name, String logo);

    /**
     * 查询  ： 注意事项列表
     *
     * @return
     */

    List<Map<String, Object>> noticeList();
}

package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 首页  ： 广告位
 */
public interface EKIndexAdvertDao {
    /**
     * e键 ： 首页  ： 广告位  ：列表
     * @param page
     * @return
     */
    Map<String,Object> ekIndexAdvertList(int page);

    /**
     * e键 ： 首页  ： 广告位  ：  添加
     * @param argsList
     */
    void addAdvert(List<Object> argsList);

    /**
     * e键 ： 首页  ： 广告位  :  删除
     * @param id
     */
    void toDel(String id);

    /**
     * e键 ： 首页  ： 广告位   ：更新
     * @param argsList
     */
    void updateAdvert(List<Object> argsList);

    /**
     * e键 ： 首页  ： 广告位   ：获取app应用
     * @return
     */
    List<Map<String,Object>> getList(String name);
}

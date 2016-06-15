package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 *  e键 ： 首页  ： 广告位
 */
public interface EKIndexAdvertService {

    /**
     * e键 ： 首页  ：  广告位  ： 获取列表
     * @param page
     * @return
     */
    Map<String,Object> ekIndexAdvertList(int page);

    /**
     * 添加  首页  ：  广告位
     * @param id
     * @param name
     * @param logo
     */
    void addAdvert(String id, String name, String logo,String aid,String order,String type);

    /**
     * 删除  首页  ：  广告位
     * @param id
     */
    void toDel(String id);

    /**
     * 更新 ： 首页  ：  广告位
     * @param id
     * @param name
     * @param logo
     * @param
     */
    void updateAdvert(String id, String name, String logo,String aid,String order,String type);

    /**
     * 获取全部app应用
     * @return
     */
    List<Map<String,Object>> getList(String name);
}

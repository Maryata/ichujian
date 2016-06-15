package com.org.mokey.basedata.sysinfo.service;

import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 首页  ： 系统应用模板维护
 */
public interface EKIndexModelService {
    /**
     * e键 ： 首页 ： 查询所有的app应用
     * @param page
     * @param name
     * @return
     */
    Map<String,Object> getAllApp(int page, String name);

    /**
     * e键 ： 首页 ： 查询当前app应用
     * @param page
     * @param name
     * @return
     */
    Map<String,Object> getCurrApp(int page, String name);

    /**
     * e键 ： 首页 ： 维护
     * @param cid
     * @param isEdit
     * @param removeGid
     * @param order
     */
    void handleCate(String cid, String isEdit, String removeGid, String order);
}

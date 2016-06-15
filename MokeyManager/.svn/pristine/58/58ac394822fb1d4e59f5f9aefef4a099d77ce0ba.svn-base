package com.org.mokey.system.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/1/11.
 */
public interface DataPermissionService {

    /**
     * 查询后台供应商用户
     * @return
     */
    List<Map<String,Object>> selectSuppliers();

    /**
     * 获取所有非当前用户可见的供应商
     * @param uid 当前用户id
     * @param page 分页页码
     * @param supCode 供应商code
     * @param supName 供应商名称
     * @return
     */
    Map<String,Object> getAllSuppliers(String uid, int page, String supCode, String supName);

    /**
     * 获取当前用户可见的供应商
     * @param uid 当前用户id
     * @param page 分页页码
     * @param supCode 供应商code
     * @param supName 供应商名称
     * @return
     */
    Map<String,Object> getCurrSup(String uid, int page, String supCode, String supName);

    /**
     * 维护当前用户可见供应商
     * @param userId 当前用户id
     * @param supIds 当前用户可见的供应商id
     * @param supCodes 当前用户可见的供应商code
     * @param removeSupIds 修改为当前当前用户不可见的供应商id
     */
    void handle(String userId, String[] supIds, String[] supCodes, String[] removeSupIds);
}

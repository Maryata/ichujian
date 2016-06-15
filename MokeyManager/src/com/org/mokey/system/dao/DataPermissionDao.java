package com.org.mokey.system.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/1/11.
 */
public interface DataPermissionDao {

    List<Map<String,Object>> selectSuppliers();

    Map<String,Object> getAllSuppliers(String uid, int page, String supCode, String supName);

    Map<String,Object> getCurrSup(String uid, int page, String supCode, String supName);

    int isExist(String userId, String supId);

    void handle(String userId, String supId, String supCode);

    void removeSup(String userId, String supId);
}


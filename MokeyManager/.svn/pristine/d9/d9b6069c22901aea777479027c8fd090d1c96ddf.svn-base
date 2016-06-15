package com.org.mokey.system.service.impl;

import com.org.mokey.system.dao.DataPermissionDao;
import com.org.mokey.system.service.DataPermissionService;
import com.org.mokey.util.StrUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/1/11.
 */
public class DataPermissionServiceImpl implements DataPermissionService {

    private DataPermissionDao dataPermissionDao;

    public DataPermissionDao getDataPermissionDao() {
        return dataPermissionDao;
    }

    public void setDataPermissionDao(DataPermissionDao dataPermissionDao) {
        this.dataPermissionDao = dataPermissionDao;
    }

    @Override
    public List<Map<String, Object>> selectSuppliers() {
        return dataPermissionDao.selectSuppliers();
    }

    @Override
    public Map<String, Object> getAllSuppliers(String uid, int page, String supCode, String supName) {
        return dataPermissionDao.getAllSuppliers(uid, page, supCode, supName);
    }

    @Override
    public Map<String, Object> getCurrSup(String uid, int page, String supCode, String supName) {
        return dataPermissionDao.getCurrSup(uid, page, supCode, supName);
    }

    @Override
    public void handle(String userId, String[] supIds, String[] supCodes, String[] removeSupIds) {
        // 添加供应商
        if(null!=supIds && supIds.length>0){
            for (int i = 0; i < supIds.length; i++) {
                // 查询供应商是否被当前用户可见
                int count = dataPermissionDao.isExist(userId, supIds[i]);
                if(count==0){
                    // 如果不可见，添加
                    dataPermissionDao.handle(userId, supIds[i], supCodes[i]);
                }
            }
        }
        // 移除供应商
        if(null!=removeSupIds && removeSupIds.length>0){
            for (String sid : removeSupIds) {
                if(StrUtils.isEmpty(sid)){
                    continue;
                }
                dataPermissionDao.removeSup(userId, sid);
            }
        }
    }
}

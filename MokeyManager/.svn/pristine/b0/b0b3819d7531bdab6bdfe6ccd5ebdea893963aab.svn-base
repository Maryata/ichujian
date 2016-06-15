package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKIndexModelDao;
import com.org.mokey.basedata.sysinfo.service.EKIndexModelService;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 首页  ： 系统应用模板维护
 */
@Service
public class EKIndexModelServiceImpl implements EKIndexModelService{
    @Autowired
    private EKIndexModelDao eKIndexModelDao;

    /**
     * e键 ： 首页  ：查询所有的app应用
     * @param page
     * @param name
     * @return
     */
    @Override
    public Map<String, Object> getAllApp(int page, String name) {
        return eKIndexModelDao.getAllApp(page,name);
    }

    /**
     * e键 ： 首页  ： 查询当前app应用
     * @param page
     * @param name
     * @return
     */
    @Override
    public Map<String, Object> getCurrApp(int page, String name) {
        return eKIndexModelDao.getCurrApp(page,name);
    }

    /**
     * e键 ： 首页  ： 维护
     * @param cid
     * @param isEdit
     * @param removeGid
     * @param order
     */
    @Override
    public void handleCate(String cid, String isEdit, String removeGid, String order) {
        String[] cids = cid.split(",");
        String[] removeGids = removeGid.split(",");
        String[] orders = order.split(",");
        String[] isEdits = isEdit.split(",");
        // 替换所有的“n”为空
        if (StrUtils.isNotEmpty(orders)) {
            for (int i = 0; i < orders.length; i++) {
                if ("n".equals(orders[i])) {
                    orders[i] = "";
                }
            }
        }
        // 添加app应用到当前
        if (null != cids && cids.length > 0) {
            for (int i = 0; i < cids.length; i++) {
                if (StrUtils.isEmpty(cids[i])) {
                    continue;
                }
                // 查询app应用是否已经存在
                int cateCount = eKIndexModelDao.isExist(cids[i]);
                if (cateCount == 0) {
                    // 如果不存在，新增
                    eKIndexModelDao.add(cids[i], orders[i],isEdits[i]);
                } else {
                    // 如果存在，更新排序
                    eKIndexModelDao.update(cids[i], orders[i],isEdits[i]);
                }
            }
        }
        // 移除出分类
        if (null != removeGids && removeGids.length > 0) {
            for (String id : removeGids) {
                if (StrUtils.isEmpty(id)) {
                    continue;
                }
                eKIndexModelDao.remove(id);
            }
        }
    }
}

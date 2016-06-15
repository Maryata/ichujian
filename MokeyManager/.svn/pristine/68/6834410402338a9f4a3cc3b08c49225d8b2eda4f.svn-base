package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKActivityIndexCategoryDao;
import com.org.mokey.basedata.sysinfo.service.EKActivityIndexCategoryService;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ：首页分类管理
 */
@Service
public class EKActivityIndexCategoryServiceImpl implements EKActivityIndexCategoryService {
    @Autowired
    private EKActivityIndexCategoryDao ekActivityIndexCategoryDao;

    /**
     * 查询 所有活动分类信息
     *
     * @param page
     * @param title
     * @return
     */
    @Override
    public Map<String, Object> getAllInfo(int page, String title) {
        return ekActivityIndexCategoryDao.getAllInfo(page, title);
    }

    /**
     * 查询 当前已添加的活动分类信息
     *
     * @param page
     * @param title
     * @return
     */
    @Override
    public Map<String, Object> getCurrInfo(int page, String title) {
        return ekActivityIndexCategoryDao.getCurrInfo(page, title);
    }

    /**
     * 首页活动分类维护
     *
     * @param sid
     * @param removeGid
     * @param order
     */
    @Override
    public void handleIndexCategory(String sid, String removeGid, String order) {
        String[] sids = sid.split(",");
        String[] removeGids = removeGid.split(",");
        String[] orders = order.split(",");
        if (StrUtils.isNotEmpty(orders)) {
            for (int i = 0; i < orders.length; i++) {
                if ("n".equals(orders[i])) {
                    orders[i] = "";
                }
            }
        }
        if (null != sids && sids.length > 0) {
            for (int i = 0; i < sids.length; i++) {
                int count = 0;
                count = ekActivityIndexCategoryDao.isExitIndexCategory(sids[i]);
                if (count == 0) {
                    ekActivityIndexCategoryDao.addIndexCategory(sids[i], orders[i]);
                } else {
                    ekActivityIndexCategoryDao.updateIndexCategory(sids[i], orders[i]);
                }
            }
        }
        if (null != removeGids && removeGids.length > 0) {
            for (String id : removeGids) {
                if (StrUtils.isEmpty(id)) {
                    continue;
                }
                ekActivityIndexCategoryDao.removeIndexCategory(id);
            }
        }


    }
}

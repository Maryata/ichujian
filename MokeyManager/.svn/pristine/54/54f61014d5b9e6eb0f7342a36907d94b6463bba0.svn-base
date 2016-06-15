package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKActivityHeadLineDao;
import com.org.mokey.basedata.sysinfo.service.EKActivityHeadLineService;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ：活动头条
 */
@Service
public class EKActivityHeadLineServiceImpl implements EKActivityHeadLineService {
    @Autowired
    private EKActivityHeadLineDao ekActivityHeadLineDao;

    /**
     * 活动头条   查询活动分类   所有信息
     *
     * @param page
     * @param title
     * @return
     */
    @Override
    public Map<String, Object> getAllInfo(int page, String title) {
        return ekActivityHeadLineDao.getAllInfo(page, title);
    }

    /**
     * 活动头条   查询活动头条中间表   所有活动分类信息
     *
     * @param page
     * @param title
     * @return
     */
    @Override
    public Map<String, Object> getCurrInfo(int page, String title, String type) {
        return ekActivityHeadLineDao.getCurrInfo(page, title, type);
    }

    /**
     * 活动头条   查询活动   所有信息
     *
     * @param page
     * @param title
     * @return
     */
    @Override
    public Map<String, Object> getAllActivityInfo(int page, String title) {
        return ekActivityHeadLineDao.getAllActivityInfo(page, title);
    }

    /**
     * 活动头条  信息维护
     *
     * @param sid
     * @param name
     * @param img
     * @param removeGid
     * @param order
     */
    @Override
    public void handleActivityHeadLine(String sid, String name, String img, String removeGid, String order,String type) {
        String[] sids = sid.split(",");
        String[] names = name.split(",");
        String[] imgs = img.split(",");
        String[] removeGids = removeGid.split(",");
        String[] orders = order.split(",");
        if (StrUtils.isNotEmpty(orders)) {
            for (int i = 0; i < orders.length; i++) {
                if ("n".equals(orders[i])) {
                    orders[i] = "";
                }
            }
        }
        if (StrUtils.isNotEmpty(names)) {
            for (int i = 0; i < names.length; i++) {
                if ("n".equals(names[i])) {
                    names[i] = "";
                }
            }
        }
        if (StrUtils.isNotEmpty(imgs)) {
            for (int i = 0; i < imgs.length; i++) {
                if ("n".equals(imgs[i])) {
                    imgs[i] = "";
                }
            }
        }
        if (null != sids && sids.length > 0) {
            for (int i = 0; i < sids.length; i++) {
                int count = 0;
                count = ekActivityHeadLineDao.isExitHeadLine(sids[i],type);
                if (count == 0) {
                    ekActivityHeadLineDao.addHeadLine(sids[i], orders[i], names[i], imgs[i] ,type);
                } else {
                    ekActivityHeadLineDao.updateHeadLine(sids[i], orders[i], names[i], imgs[i] ,type);
                }
            }
        }
        if (null != removeGids && removeGids.length > 0) {
            for (String id : removeGids) {
                if (StrUtils.isEmpty(id)) {
                    continue;
                }
                ekActivityHeadLineDao.removeHeadLine(id,type);
            }
        }


    }
}

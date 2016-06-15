package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKHeadLineDao;
import com.org.mokey.basedata.sysinfo.service.EKHeadLineService;

import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ：活动头条
 */
public class EKHeadLineServiceImpl implements EKHeadLineService {
    public EKHeadLineDao getEkHeadLineDao() {
        return ekHeadLineDao;
    }

    public void setEkHeadLineDao(EKHeadLineDao ekHeadLineDao) {
        this.ekHeadLineDao = ekHeadLineDao;
    }

    private EKHeadLineDao ekHeadLineDao;



    public Map<String, Object> ekHeadLineList(int page) {
        return ekHeadLineDao.ekHeadLineList(page);
    }

    @Override
    public void toDel(String id) {
        ekHeadLineDao.toDel(id);
    }

    @Override
    public void updateHeadLine(String id, String name, String order, String type, String logo) {
        ekHeadLineDao.updateHeadLine(id,name,order,type,logo);
    }

    @Override
    public void addHeadLine(String id, String name, String order, String type, String logo) {
        ekHeadLineDao.addHeadLine(id,name,order,type,logo);
    }


}

package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKActivityNoticeDao;
import com.org.mokey.basedata.sysinfo.service.EKActivityNoticeService;

import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ：活动注意事项
 */
public class EKActivityNoticeServiceImpl implements EKActivityNoticeService {
    public EKActivityNoticeDao getEkActivityNoticeDao() {
        return ekActivityNoticeDao;
    }

    public void setEkActivityNoticeDao(EKActivityNoticeDao ekActivityNoticeDao) {
        this.ekActivityNoticeDao = ekActivityNoticeDao;
    }

    private EKActivityNoticeDao ekActivityNoticeDao;



    public Map<String, Object> ekActivityNoticeList(int page) {
        return ekActivityNoticeDao.ekActivityNoticeList(page);
    }

    @Override
    public void addTag(String id, String name, String logo) {
        ekActivityNoticeDao.addTag(id , name , logo);
    }

    @Override
    public void toDel(String id) {
        ekActivityNoticeDao.toDel(id);
    }

    @Override
    public void updateTag(String id, String name, String logo) {
        ekActivityNoticeDao.updateTag(id , name , logo);
    }

}

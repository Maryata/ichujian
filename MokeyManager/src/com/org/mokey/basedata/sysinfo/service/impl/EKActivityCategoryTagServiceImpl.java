package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKActivityCategoryTagDao;
import com.org.mokey.basedata.sysinfo.service.EKActivityCategoryTagService;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ：活动标签
 */
public class EKActivityCategoryTagServiceImpl implements EKActivityCategoryTagService {
    public EKActivityCategoryTagDao getEkActivityCategoryTagDao() {
        return ekActivityCategoryTagDao;
    }

    public void setEkActivityCategoryTagDao(EKActivityCategoryTagDao ekActivityCategoryTagDao) {
        this.ekActivityCategoryTagDao = ekActivityCategoryTagDao;
    }

    private EKActivityCategoryTagDao ekActivityCategoryTagDao;



    public Map<String, Object> ekActivityCategoryTagList(int page) {
        return ekActivityCategoryTagDao.ekActivityCategoryTagList(page);
    }

    @Override
    public void addTag(String id, String name, String logo) {
        ekActivityCategoryTagDao.addTag(id , name , logo);
    }

    @Override
    public void toDel(String id) {
        ekActivityCategoryTagDao.toDel(id);
    }

    @Override
    public void updateTag(String id, String name, String logo) {
        ekActivityCategoryTagDao.updateTag(id , name , logo);
    }

    @Override
    public List<Map<String, Object>> getList() {
        return ekActivityCategoryTagDao.getList();
    }

}

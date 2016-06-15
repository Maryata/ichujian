package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKIndexAdvertDao;
import com.org.mokey.basedata.sysinfo.service.EKIndexAdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 首页  ： 广告位
 */
@Service
public class EKIndexAdvertServiceImpl implements EKIndexAdvertService{
    @Autowired
    private EKIndexAdvertDao eKIndexAdvertDao;

    @Override
    public Map<String, Object> ekIndexAdvertList(int page) {
        return eKIndexAdvertDao.ekIndexAdvertList(page);
    }

    @Override
    public void addAdvert(String id, String name, String logo,String aid,String order,String type) {
        List<Object> argsList = new ArrayList<Object>();
        argsList.add(id);
        argsList.add(name);
        argsList.add(logo);
        argsList.add(aid);
        argsList.add(order);
        argsList.add(type);
        eKIndexAdvertDao.addAdvert(argsList);
    }

    @Override
    public void toDel(String id) {
        eKIndexAdvertDao.toDel(id);
    }

    @Override
    public void updateAdvert(String id, String name, String logo,String aid,String order,String type) {
        List<Object> argsList = new ArrayList<Object>();
        argsList.add(name);
        argsList.add(logo);
        argsList.add(aid);
        argsList.add(order);
        argsList.add(type);
        argsList.add(id);
        eKIndexAdvertDao.updateAdvert(argsList);
    }

    @Override
    public List<Map<String, Object>> getList(String name) {
        return eKIndexAdvertDao.getList(name);
    }
}

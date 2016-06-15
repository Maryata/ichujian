package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKIndexKeyAppDao;
import com.org.mokey.basedata.sysinfo.service.EKIndexKeyAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/15.
 */
@Service
public class EKIndexKeyAppServiceImpl implements EKIndexKeyAppService {
    @Autowired
    private EKIndexKeyAppDao eKIndexKeyAppDao;

    @Override
    public Map<String, Object> ekIndexKeyAppList(int page) {
        return eKIndexKeyAppDao.ekIndexKeyAppList(page);
    }

    @Override
    public void addKeyApp(List<Object> argsList) {
        eKIndexKeyAppDao.addKeyApp(argsList);
    }

    @Override
    public void toDel(String id) {
        eKIndexKeyAppDao.toDel(id);
    }

    @Override
    public void updateKeyApp(List<Object> argsList) {
        eKIndexKeyAppDao.updateKeyApp(argsList);
    }

    @Override
    public List<Map<String, Object>> getList() {
        return eKIndexKeyAppDao.getList();
    }

    @Override
    public List<Map<String, Object>> selectOne(String id) {

        return eKIndexKeyAppDao.selectOne(id);
    }
}

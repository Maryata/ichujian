package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKMallAdvertDao;
import com.org.mokey.basedata.sysinfo.service.EKMallAdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/4/20.
 */
@Service
public class EKMallAdvertServiceImpl implements EKMallAdvertService {
    @Autowired
    private EKMallAdvertDao eKMallAdvertDao;

    /**
     * 加载列表信息
     *
     * @param page
     * @return
     */
    @Override
    public Map<String, Object> list(int page) {
        return eKMallAdvertDao.list(page);
    }

    /**
     * 查询商品信息
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getList() {
        return eKMallAdvertDao.getList();
    }

    /**
     * 添加广告位
     *
     * @param id
     * @param name
     * @param logo
     * @param pid
     * @param order
     */
    @Override
    public void addAdvert(String id, String name, String logo, String pid, String order) {
        eKMallAdvertDao.addAdvert(id,name,logo,pid,order);
    }

    /**
     * 删除广告位
     *
     * @param id
     */
    @Override
    public void toDel(String id) {
        eKMallAdvertDao.toDel(id);
    }

    /**
     * 更新广告位
     *
     * @param id
     * @param name
     * @param logo
     * @param pid
     * @param order
     */
    @Override
    public void updateAdvert(String id, String name, String logo, String pid, String order) {
        eKMallAdvertDao.updateAdvert(id,name,logo,pid,order);
    }
}

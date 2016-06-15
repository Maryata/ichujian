package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKIndexSuppIndexAppDao;
import com.org.mokey.basedata.sysinfo.service.EKIndexSuppIndexAppService;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/22.
 * 首页   ：    APP 定制管理
 */
@Service
public class EKIndexSuppIndexAppServiceImpl implements EKIndexSuppIndexAppService {

    @Autowired
    private EKIndexSuppIndexAppDao eKIndexSuppIndexAppDao;

    /**
     * APP 定制管理 : list
     *
     * @param page
     * @return
     */
    @Override
    public Map<String, Object> list(int page, String code, String aid) {
        return eKIndexSuppIndexAppDao.list(page, code, aid);
    }

    /**
     * APP 定制管理 : 供应商查询
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getSuppList() {
        return eKIndexSuppIndexAppDao.getSuppList();
    }

    /**
     * APP 定制管理 :APP查询
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getAppLsit() {
        return eKIndexSuppIndexAppDao.getAppLsit();
    }

    /**
     * APP 定制管理 : 添加list
     *
     * @param page
     * @return
     */
    @Override
    public Map<String, Object> addList(int page, String code) {
        return eKIndexSuppIndexAppDao.addList(page, code);
    }

    /**
     * APP 定制管理 : 添加
     *
     * @param
     * @return
     */
    @Override
    public void add(String aid, String code, String order) {
        String[] aids = aid.split(",");
        String[] codes = code.split(",");
        String[] orders = order.split(",");
        if (StrUtils.isNotEmpty(orders)) {
            for (int i = 0; i < orders.length; i++) {
                if ("n".equals(orders[i])) {
                    orders[i] = "";
                }
            }
        }
        if (StrUtils.isNotEmpty(aids)) {
            for (int i = 0; i < aids.length; i++) {
                if ("n".equals(aids[i])) {
                    aids[i] = "";
                }
            }
        }

        if (StrUtils.isNotEmpty(aids)) {
            for (int i = 0; i < aids.length; i++) {
                if (StrUtils.isNotEmpty(aids[i])) {//不为空添加

                    // 查询是否存在
                    int exists = eKIndexSuppIndexAppDao.isExists(codes[i], aids[i]);

                    // 不存在增加，存在更新
                    if (exists == 0) {
                        eKIndexSuppIndexAppDao.add(codes[i], aids[i], orders[i]);
                    } else {
                        eKIndexSuppIndexAppDao.updateExists(codes[i], aids[i], orders[i]);
                    }
                }

            }

        }

    }

    /**
     * APP 定制管理 :删除
     *
     * @param id
     */
    @Override
    public void toDel(String id) {
        eKIndexSuppIndexAppDao.toDel(id);
    }

    /**
     * APP 定制管理 :查询一条信息
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> selectOne(String id) {
        return eKIndexSuppIndexAppDao.selectOne(id);
    }

    /**
     * APP 定制管理 :更新
     *
     * @param args
     */
    @Override
    public void update(List<Object> args) {
        eKIndexSuppIndexAppDao.update(args);
    }
}

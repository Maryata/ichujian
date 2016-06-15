package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.PhoneModelDao;
import com.org.mokey.basedata.sysinfo.service.PhoneModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 手机型号管理
 *
 * Created by Maryn on 2016/4/26.
 */
@Service
public class PhoneModelServiceImpl implements PhoneModelService {

    @Autowired
    private PhoneModelDao phoneModelDao;

    @Override
    // 分页查询
    public Map<String, Object> phoneList(int page, int rows, String brandName, String islive) {
        return phoneModelDao.phoneList(page, rows, brandName, islive);
    }

    @Override
    // 新增手机品牌
    public void addBrand(String name, String userName) {
        phoneModelDao.addBrand(name, userName);
    }

    @Override
    // 编辑品牌
    public void editBrand(String id, String name, String userName) {
        phoneModelDao.editBrand(id, name, userName);
    }

    @Override
    // 手机品牌上下架
    public void onShelf(String id, String act, String type, String userName) {
        phoneModelDao.onShelf(id, act, type, userName);
    }

    @Override
    // 分页查询品牌下的型号
    public Map<String, Object> modelList(Integer page, Integer rows, String brandId, String phoneName, String islive) {
        return phoneModelDao.modelList(page, rows, brandId, phoneName, islive);
    }

    @Override
    // 新增指定手机品牌下的型号
    public void addModel(String name, String code, String brandId, String userName) {
        phoneModelDao.addModel(name, code, brandId, userName);
    }

    @Override
    // 编辑型号
    public void editModel(String name, String code, String brandId, String userName, String id) {
        phoneModelDao.editModel(name, code, brandId, userName, id);
    }
}

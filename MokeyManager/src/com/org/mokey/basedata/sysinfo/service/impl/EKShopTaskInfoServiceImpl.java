package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKShopTaskInfoDao;
import com.org.mokey.basedata.sysinfo.service.EKShopTaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商城任务
 * Created by vpc on 2016/4/19.
 */
@Service
public class EKShopTaskInfoServiceImpl implements EKShopTaskInfoService {
    @Autowired
    private EKShopTaskInfoDao eKShopTaskInfoDao;

    /**
     * 查询 商城任务列表
     *
     * @param page
     * @param type
     * @param subType
     * @return
     */
    @Override
    public Map<String, Object> list(int page, String type, String subType) {
        return eKShopTaskInfoDao.list(page, type, subType);
    }

    /**
     * 商城任务 保存
     *
     * @param map
     */
    @Override
    public void save(Map<String, Object> map) {
        eKShopTaskInfoDao.save(map);
    }

    /**
     * 加载 查询条件数据
     *
     * @return
     */
    @Override
    public Map<String, Object> loadInfo() {
        return eKShopTaskInfoDao.loadInfo();
    }

    /**
     * 查询 子类型
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> subTypeInfo() {
        return eKShopTaskInfoDao.subTypeInfo();
    }

    /**
     * 查询一条数据
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> taskList(String id) {
        return eKShopTaskInfoDao.taskList(id);
    }

    @Override
    public Map<String, Object> loadInfos(String type) {
        return eKShopTaskInfoDao.loadInfos(type);
    }

    /**
     * 添加任务
     *
     * @param args
     */
    @Override
    public void addTask(List<Object> args) {
        eKShopTaskInfoDao.addTask(args);
    }

    /**
     * 更新
     *
     * @param args
     */
    @Override
    public void updateTask(List<Object> args) {
        eKShopTaskInfoDao.updateTask(args);
    }


    /**
     * 获取特殊任务列表
     *
     * @param page
     * @return
     */
    @Override
    public Map<String, Object> getTSTask(int page) {
        return eKShopTaskInfoDao.getTSTask(page);
    }

    /**
     * 查询特殊任务中间表的一条数据
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> selectOne(String id,String tid) {
        return eKShopTaskInfoDao.selectOne(id,tid);
    }

    /**
     * 查询指定 的APP
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getAppList() {
        return eKShopTaskInfoDao.getAppList();
    }

    /**
     * 查询活动
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getActList() {
        return eKShopTaskInfoDao.getActList();
    }

    /**
     * 修改特殊任务
     *
     * @param id
     * @param sdate
     * @param edate
     * @param gid
     */
    @Override
    public void updateInfo(String id, String sdate, String edate, String gid, String tid) {
        eKShopTaskInfoDao.updateInfo(id, sdate, edate, gid, tid);
    }

    /**
     * 游戏列表
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getGameList() {
        return eKShopTaskInfoDao.getGameList();
    }

    /**
     * 按键设置列表
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getSettingList() {

        return eKShopTaskInfoDao.getSettingList();
    }

}

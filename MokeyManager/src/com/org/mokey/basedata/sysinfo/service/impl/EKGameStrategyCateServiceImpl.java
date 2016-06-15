package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKGameStrategyCateDao;
import com.org.mokey.basedata.sysinfo.service.EKGameStrategyCateService;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 游戏  ：  游戏攻略分类
 */
@Service
public class EKGameStrategyCateServiceImpl implements EKGameStrategyCateService {
    @Autowired
    private EKGameStrategyCateDao eKGameStrategyCateDao;

    @Override
    // 获取游戏攻略分类（下拉菜单）
    public List<Map<String, Object>> getGameStrategyCateList() {
        return eKGameStrategyCateDao.getGameStrategyCateList();
    }

    @Override
    // 分页显示游戏攻略分类
    public Map<String, Object> gameStrategyCateList(int page) {
        return eKGameStrategyCateDao.gameGiftStrategyCateList(page);
    }

    @Override
    // 新增游戏游戏攻略分类
    public void addGameStrategyCate(String id, String name, String logo, String order,
                            String modifier) {
        eKGameStrategyCateDao.addGameGiftStrategyCate(id, name, logo, order, modifier);
    }

    @Override
    // 删除分类
    public void delGameStrategyCate(String id) {
        eKGameStrategyCateDao.delGameGiftStrategyCate(id);
    }

    @Override
    // 更新游戏游戏攻略分类
    public void updateGameStrategyCate(String id, String name, String logo,
                               String order, String modifier) {
        eKGameStrategyCateDao.updateGameGiftStrategyCate(id, name, logo, order, modifier);
    }

    @Override
    // 查询非当前游戏攻略分类的其他所有游戏
    public Map<String, Object> getAllGame(int page, String cid, String name,
                                          String gameCate) {
        return eKGameStrategyCateDao.getAllGame(page, cid, name, gameCate);
    }

    @Override
    // 查询当前游戏攻略分类的游戏
    public Map<String, Object> getCurrStrategyCateGame(int page, String cid, String name) {
        return eKGameStrategyCateDao.getCurrStrategyCateGame(page, cid, name);
    }

    @Override
    // 游戏攻略分类维护
    public void handleStrategyCate(String cid, String sid, String removeGid,
                           String order) {
        String[] sids = sid.split(",");
        String[] removeGids = removeGid.split(",");
        String[] orders = order.split(",");
        // 替换所有的“n”为空
        if (StrUtils.isNotEmpty(orders)) {
            for (int i = 0; i < orders.length; i++) {
                if ("n".equals(orders[i])) {
                    orders[i] = "";
                }
            }
        }
        // 添加礼包到当前分类
        if (null != sids && sids.length > 0) {
            for (int i = 0; i < sids.length; i++) {
                if (StrUtils.isEmpty(sids[i])) {
                    continue;
                }
                // 查询礼包是否已经存在
                int StrategyCateCount = eKGameStrategyCateDao.isExist(cid, sids[i]);
                if (StrategyCateCount == 0) {
                    // 如果不存在，新增
                    eKGameStrategyCateDao.add(cid, sids[i], orders[i]);
                } else {
                    // 如果存在，更新排序
                    eKGameStrategyCateDao.update(cid, sids[i], orders[i]);
                }
            }
        }
        // 移除出分类
        if (null != removeGids && removeGids.length > 0) {
            for (String id : removeGids) {
                if (StrUtils.isEmpty(id)) {
                    continue;
                }
                eKGameStrategyCateDao.remove(cid, id);
            }
        }
    }

}

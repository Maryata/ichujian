package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKGameCollectionDao;
import com.org.mokey.basedata.sysinfo.dao.GameCollectionDao;
import com.org.mokey.basedata.sysinfo.service.EKGameCollectionService;
import com.org.mokey.basedata.sysinfo.service.GameCollectionService;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏合集
 */
@Service
public class EKGameCollectionServiceImpl implements EKGameCollectionService {

    @Autowired
    private EKGameCollectionDao eKGameCollectionDao;

    @Override
    // 获取游戏合集
    public List<Map<String, Object>> gameCol(String cid) {
        return eKGameCollectionDao.gameCol(cid);
    }

    @Override
    // 添加游戏合集
    public void addCol(String name, String type) {
        eKGameCollectionDao.addCol(name, type);
    }

    @Override
    // 删除游戏合集
    public void deleteCol(String cid) {
        // 删除合集游戏中间表数据
        eKGameCollectionDao.deleteColGame(cid);
        // 删除游戏合集
        eKGameCollectionDao.deleteCol(cid);
    }

    @Override
    // 修改合集
    public void modifyCol(String cid, String cname, String islive) {
        eKGameCollectionDao.modifyCol(cid, cname, islive);
    }

    @Override
    // 使合集不可用/可用
    public void isvalid(String cid, String islive) {
        eKGameCollectionDao.isvalid(cid, islive);
    }

    @Override
    // 查询不属于当前合集的游戏
    public List<Map<String, Object>> getGameList(int page, String cid, String type) {
        return eKGameCollectionDao.getGameList(page, cid, type);
    }

    @Override
    // 对指定id的游戏进行合集分类
    public void handleCol(String cid, String[] gids, String[] removeGids, String[] orders) {
        // 替换所有“n”
        if (null != orders && orders.length > 0) {
            for (int i = 0; i < orders.length; i++) {
                if ("n".equals(orders[i])) {
                    orders[i] = "";
                }
            }
        }
        // 添加游戏
        if (null != gids && gids.length > 0) {
            for (int i = 0; i < gids.length; i++) {
                // 查询游戏是否已经存在于当前合集中
                int count = eKGameCollectionDao.isExist(cid, gids[i]);
                if (count == 0) {
                    // 如果不存在，则添加到当前合集
                    eKGameCollectionDao.handleCol(cid, gids[i], orders[i]);
                } else {
                    // 如果存在，则更新顺序
                    eKGameCollectionDao.updateOrder(cid, gids[i], orders[i]);
                }
            }
        }
        // 移出游戏
        if (null != removeGids && removeGids.length > 0) {
            for (String gid : removeGids) {
                if (StrUtils.isEmpty(gid)) {
                    continue;
                }
                eKGameCollectionDao.removeGame(cid, gid);
            }
        }
    }

    @Override
    // 查询游戏总数
    public Integer getTotal(String cid, String type) {
        return eKGameCollectionDao.getTotal(cid, type);
    }

    @Override
    // 条件查询游戏APP
    public List<Map<String, Object>> queryGameCondition(String cid,
                                                        String name, String type, String minSize, String maxSize, int page) {
        return eKGameCollectionDao.queryGameCondition(cid, name, type, minSize, maxSize, page);
    }

    @Override
    // 条件查询游戏总数
    public Integer getTotalCondition(String cid, String name,
                                     String type, String minSize, String maxSize) {
        return eKGameCollectionDao.getTotalCondition(cid, name, type, minSize, maxSize);
    }

    @Override
    // 根据合集id查询游戏
    public List<Map<String, Object>> getGamePageByColId(String cid, int page, String type) {
        return eKGameCollectionDao.getGamePageByColId(cid, page, type);
    }

    @Override
    // 根据合集id查询合集对应的游戏总数
    public Integer getTotalCol(String cid, String type) {
        return eKGameCollectionDao.getTotalCol(cid, type);
    }

    @Override
    // 条件查询合集中游戏总数
    public Integer getColTotalCondition(String cid, String name, String type) {
        return eKGameCollectionDao.getColTotalCondition(cid, name, type);
    }

    @Override
    // 条件查询合集中游戏
    public List<Map<String, Object>> queryColGameCondition(String cid,
                                                           String name, int page, String type) {
        return eKGameCollectionDao.queryColGameCondition(cid, name, page, type);
    }

}

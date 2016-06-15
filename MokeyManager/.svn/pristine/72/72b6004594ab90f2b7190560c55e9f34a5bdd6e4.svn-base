package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKGameCategoryDao;
import com.org.mokey.basedata.sysinfo.dao.GameCategoryDao;
import com.org.mokey.basedata.sysinfo.service.EKGameCategoryService;
import com.org.mokey.basedata.sysinfo.service.GameCategoryService;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏分类
 */
@Service
public class EKGameCategoryServiceImpl implements EKGameCategoryService {

    @Autowired
    private EKGameCategoryDao eKGameCategoryDao;

    @Override
    // 获取游戏分类（下拉菜单）
    public List<Map<String, Object>> getGameCategoryList() {
        return eKGameCategoryDao.getGameCategoryList();
    }

    @Override
    // 分页显示游戏分类
    public Map<String, Object> gameCategoryList(int page) {
        return eKGameCategoryDao.gameGiftCateList(page);
    }

    @Override
    // 新增游戏游戏分类
    public void addGameCate(String id, String name, String logo, String order,
                            String modifier) {
        eKGameCategoryDao.addGameGiftCate(id, name, logo, order, modifier);
    }

    @Override
    // 删除分类
    public void delGameCate(String id) {
        eKGameCategoryDao.delGameGiftCate(id);
    }

    @Override
    // 更新游戏游戏分类
    public void updateGameCate(String id, String name, String logo,
                               String order, String modifier) {
        eKGameCategoryDao.updateGameGiftCate(id, name, logo, order, modifier);
    }

    @Override
    // 查询非当前游戏分类的其他所有游戏
    public Map<String, Object> getAllGame(int page, String cid, String name,
                                          String gameCate) {
        return eKGameCategoryDao.getAllGame(page, cid, name, gameCate);
    }

    @Override
    // 查询当前游戏分类的游戏
    public Map<String, Object> getCurrCateGame(int page, String cid, String name) {
        return eKGameCategoryDao.getCurrCateGame(page, cid, name);
    }

    @Override
    // 游戏分类维护
    public void handleCate(String cid, String gid, String removeGid,
                           String order) {
        String[] gids = gid.split(",");
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
        if (null != gids && gids.length > 0) {
            for (int i = 0; i < gids.length; i++) {
                if (StrUtils.isEmpty(gids[i])) {
                    continue;
                }
                // 查询礼包是否已经存在
                int cateCount = eKGameCategoryDao.isExist(cid, gids[i]);
                if (cateCount == 0) {
                    // 如果不存在，新增
                    eKGameCategoryDao.add(cid, gids[i], orders[i]);
                } else {
                    // 如果存在，更新排序
                    eKGameCategoryDao.update(cid, gids[i], orders[i]);
                }
            }
        }
        // 移除出分类
        if (null != removeGids && removeGids.length > 0) {
            for (String id : removeGids) {
                if (StrUtils.isEmpty(id)) {
                    continue;
                }
                eKGameCategoryDao.remove(cid, id);
            }
        }
    }

}

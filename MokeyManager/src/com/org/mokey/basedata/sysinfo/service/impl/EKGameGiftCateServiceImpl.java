package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKGameGiftCateDao;
import com.org.mokey.basedata.sysinfo.dao.GameGiftCateDao;
import com.org.mokey.basedata.sysinfo.service.EKGameGiftCateService;
import com.org.mokey.basedata.sysinfo.service.GameGiftCateService;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏礼包分类
 */
@Service
public class EKGameGiftCateServiceImpl implements EKGameGiftCateService {

    @Autowired
    private EKGameGiftCateDao eKGameGiftCateDao;


    @Override
    // 分页显示礼包分类
    public Map<String, Object> gameGiftCateList(int page) {
        return eKGameGiftCateDao.gameGiftCateList(page);
    }

    @Override
    // 新增游戏礼包分类
    public void addGameGiftCate(String id, String name, String logo,
                                String order, String modifier) {
        eKGameGiftCateDao.addGameGiftCate(id, name, logo, order, modifier);
    }

    @Override
    // 删除分类
    public void delGameGiftCate(String id) {
        eKGameGiftCateDao.delGameGiftCate(id);
    }

    @Override
    // 更新游戏礼包分类
    public void updateGameGiftCate(String id, String name, String logo,
                                   String order, String modifier) {
        eKGameGiftCateDao.updateGameGiftCate(id, name, logo, order, modifier);
    }

    @Override
    // 查询非当前礼包分类的其他所有礼包
    public Map<String, Object> getAllGameGift(int page, String cid, String name, String giftCate) {
        return eKGameGiftCateDao.getAllGameGift(page, cid, name, giftCate);
    }

    @Override
    // 查询当前礼包分类的礼包
    public Map<String, Object> getCurrCateGift(int page, String cid, String name) {
        return eKGameGiftCateDao.getCurrCateGift(page, cid, name);
    }

    @Override
    // 所有礼包分类
    public List<Map<String, Object>> getGiftCateList() {
        return eKGameGiftCateDao.getGiftCateList();
    }

    @Override
    // 分类维护
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
                // 查询礼包是否已经存在
                int cateCount = eKGameGiftCateDao.isExist(cid, gids[i]);
                if (cateCount == 0) {
                    // 如果不存在，新增
                    eKGameGiftCateDao.add(cid, gids[i], orders[i]);
                } else {
                    // 如果存在，更新排序
                    eKGameGiftCateDao.update(cid, gids[i], orders[i]);
                }
            }
        }
        // 移除出分类
        if (null != removeGids && removeGids.length > 0) {
            for (String id : removeGids) {
                if (StrUtils.isEmpty(id)) {
                    continue;
                }
                eKGameGiftCateDao.remove(cid, id);
            }
        }
    }


}

package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKShopProductCateDao;
import com.org.mokey.basedata.sysinfo.service.EKShopProductCateService;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 商品分类管理
 */
@Service
public class EKShopProductCateServiceImpl implements EKShopProductCateService {
    @Autowired
    private EKShopProductCateDao eKShopProductCateDao;

    @Override
    // 分页显示商品分类
    public Map<String, Object> productCateList(int page) {
        return eKShopProductCateDao.productCateList(page);
    }

    @Override
    // 查询非当前商品分类的其他所有游戏
    public Map<String, Object> getAllProduct(int page, String cid, String name) {
        return eKShopProductCateDao.getAllProduct(page, cid, name);
    }

    @Override
    // 查询当前商品分类的游戏
    public Map<String, Object> getCurrCateProduct(int page, String cid, String name) {
        return eKShopProductCateDao.getCurrCateProduct(page, cid, name);
    }

    @Override
    // 商品分类维护
    public void handleCate(String cid, String pid, String removePid, String order) {
        String[] pids = pid.split(",");
        String[] removePids = removePid.split(",");
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
        if (null != pids && pids.length > 0) {
            for (int i = 0; i < pids.length; i++) {
                if (StrUtils.isEmpty(pids[i])) {
                    continue;
                }
                // 查询礼包是否已经存在
                int StrategyCateCount = eKShopProductCateDao.isExist(cid, pids[i]);
                if (StrategyCateCount == 0) {
                    // 如果不存在，新增
                    eKShopProductCateDao.add(cid, pids[i], orders[i]);
                } else {
                    // 如果存在，更新排序
                    eKShopProductCateDao.update(cid, pids[i], orders[i]);
                }
            }
        }
        // 移除出分类
        if (null != removePids && removePids.length > 0) {
            for (String id : removePids) {
                if (StrUtils.isEmpty(id)) {
                    continue;
                }
                eKShopProductCateDao.remove(cid, id);
            }
        }
    }

}

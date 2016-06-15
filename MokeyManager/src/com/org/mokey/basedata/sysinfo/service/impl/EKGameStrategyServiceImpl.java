package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.baseinfo.common.HtmlUtil;
import com.org.mokey.basedata.sysinfo.dao.EKGameStrategyDao;
import com.org.mokey.basedata.sysinfo.dao.GameStrategyDao;
import com.org.mokey.basedata.sysinfo.service.EKGameStrategyService;
import com.org.mokey.basedata.sysinfo.service.GameStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 游戏帮：游戏活动
 */
@Service
public class EKGameStrategyServiceImpl implements EKGameStrategyService {

    @Autowired
    private EKGameStrategyDao eKGameStrategyDao;

    @Override
    // 查询活动列表
    public List<Map<String, Object>> gameStrategyList(String gid, String name, int page, String gName) {
        return eKGameStrategyDao.gameStrategyList(gid, name, page, gName);
    }

    @Override
    // 获取活动总数
    public Integer getTotal(String gid, String name, String gName) {
        return eKGameStrategyDao.getTotal(gid, name, gName);
    }

    @Override
    // 根据id删除游戏活动
    public void deleteGameStrategy(String id) {
        eKGameStrategyDao.deleteGameStrategy(id);
    }

    @Override
    // 根据id查询活动
    public List<Map<String, Object>> queryStrategyById(String editId) {
        return eKGameStrategyDao.queryStrategyById(editId);
    }

    @Override
    // 保存活动
    public void saveGameStrategy(String id, String gid, String order, String name, String depict, String logourl) {
        // 封装请求参数
        List<Object> args = new ArrayList<Object>();
        args.add(id);
        args.add(gid);
        args.add(name);
        args.add(depict);
        Date date = new Date();
        args.add(date);
        args.add("1");
        args.add(order);

        // 查询浏览人数
//		Integer num = eKGameStrategyDao.scanedNum(id,gid);
        // 封装html文件需要的参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("c_id", id);
        map.put("c_name", name);
        map.put("c_date", date);
//		map.put("c_num", num);
        map.put("c_depict", depict);
        // 生成HTML
        HtmlUtil htmlUtil = new HtmlUtil();
        String shareurl = htmlUtil.createGameHtmlV2("ek-gameStrategyShare", map);
        args.add(shareurl);

        args.add(logourl);

        // 查询id是否存在
        boolean isExists = eKGameStrategyDao.existId(id);

        if (!isExists) {// id不存在，保存
            eKGameStrategyDao.saveGameStrategy(args);
        } else {// id存在，更新
            args.remove(0);
            args.add(args.size(), id);
            eKGameStrategyDao.updateGameStrategy(args);
        }
    }

    @Override
    // 查询所有攻略id和其对应的攻略内容（仅用于给所有图片设置尺寸）
    public List<Map<String, Object>> selectDepict() {
        return eKGameStrategyDao.selectDepict();
    }

    @Override
    // 给所有图片设置尺寸
    public int setImgSize(String id, String depict, String flag) {
        return eKGameStrategyDao.setImgSize(id, depict, flag);
    }

}
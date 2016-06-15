package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.baseinfo.common.HtmlUtil;
import com.org.mokey.basedata.sysinfo.dao.EKGameActDao;
import com.org.mokey.basedata.sysinfo.dao.GameActDao;
import com.org.mokey.basedata.sysinfo.service.EKGameActService;
import com.org.mokey.basedata.sysinfo.service.GameActService;
import com.org.mokey.common.util.ApDateTime;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * 游戏帮：游戏活动
 */
@Service
public class EKGameActServiceImpl implements EKGameActService {

    private static final Logger LOGGER = Logger.getLogger(EKGameActServiceImpl.class);

    @Autowired
    private EKGameActDao eKGameActDao;

    @Override
    // 查询活动列表
    public List<Map<String, Object>> gameActList(String gid, String name, int page, String gName) {
        return eKGameActDao.gameActList(gid, name, page, gName);
    }

    @Override
    // 获取活动总数
    public Integer getTotal(String gid, String name, String gName) {
        return eKGameActDao.getTotal(gid, name, gName);
    }

    @Override
    // 根据id删除游戏活动
    public void deleteGameAct(String id) {
        eKGameActDao.deleteGameAct(id);
    }

    @Override
    // 根据id查询活动
    public List<Map<String, Object>> queryActById(String editId) {
        return eKGameActDao.queryActById(editId);
    }

    @Override
    public Map<String, Object> listUser(int start, int limit, int aid, int flag) {
        return eKGameActDao.listUser(start, limit, aid, flag);
    }

    @Override
    public void updateUser(Map<String, Object> map) {
        try {
            eKGameActDao.updateUser(map);
        } catch (Exception e) {
            LOGGER.error("发放活动积分错误", e);
        }
    }

    @Override
    // 保存活动
    public void saveGameAct(String id, String gid, String order,
                            String name, String depict, String logourl,
                            String sDate, String eDate, String reward) {
        Date sdate = null;
        Date edate = null;
        try {
            sdate = ApDateTime.getDate(sDate, "yyyy-MM-dd HH:mm:ss");
            edate = ApDateTime.getDate(eDate, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            LOGGER.error("Parsing Date in GameGiftServiceImpl.saveGameGift failed, e : {}", e);
        }

        // 封装请求参数
        List<Object> args = new ArrayList<Object>();
        args.add(id);
        args.add(gid);
        args.add(name);
        args.add(depict);
//		Date date = new Date();
//		args.add(date);
        args.add("1");
        args.add(order);

        // 查询浏览人数
//		Integer num = eKGameActDao.scanedNum(id,gid);
        // 封装html文件需要的参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("c_id", id);
        map.put("c_name", name);
        map.put("c_sDate", sdate);
        map.put("c_eDate", edate);
//		map.put("c_date", date);
//		map.put("c_num", num);
        map.put("c_depict", depict);
        // 生成HTML
        HtmlUtil htmlUtil = new HtmlUtil();
        String shareurl = htmlUtil.createGameHtmlV2("ek-gameActivityShare", map);
        args.add(shareurl);

        args.add(logourl);
        args.add(sdate);
        args.add(edate);
        args.add(reward);

        // 查询id是否存在
        boolean isExists = eKGameActDao.existId(id);

        if (!isExists) {// id不存在，保存
            eKGameActDao.saveGameAct(args);
        } else {// id存在，更新
            args.remove(0);
            args.add(args.size(), id);
            eKGameActDao.updateGameAct(args);
        }
    }

}

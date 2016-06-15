package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.baseinfo.common.HtmlUtil;
import com.org.mokey.basedata.sysinfo.dao.EKGameInfoDao;
import com.org.mokey.basedata.sysinfo.dao.GameInfoDao;
import com.org.mokey.basedata.sysinfo.service.EKGameInfoService;
import com.org.mokey.basedata.sysinfo.service.GameInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 游戏帮：游戏资讯
 */
@Service
public class EKGameInfoServiceImpl implements EKGameInfoService {

    @Autowired
    private EKGameInfoDao eKGameInfoDao;

    @Override
    // 查询资讯列表
    public List<Map<String, Object>> gameInfoList(String gid, String name, int page, String gName) {
        return eKGameInfoDao.gameInfoList(gid, name, page, gName);
    }

    @Override
    // 获取资讯总数
    public Integer getTotal(String gid, String name, String gName) {
        return eKGameInfoDao.getTotal(gid, name, gName);
    }

    @Override
    // 根据id删除游戏资讯
    public void deleteGameInfo(String id) {
        eKGameInfoDao.deleteGameInfo(id);
    }

    @Override
    // 根据id查询资讯
    public List<Map<String, Object>> queryInfoById(String editId) {
        return eKGameInfoDao.queryInfoById(editId);
    }

    @Override
    // 保存资讯
    public void saveGameInfo(String id, String gid, String order, String name, String depict, String logourl) {
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
//		Integer num = eKGameInfoDao.scanedNum(id,gid);
        // 封装html文件需要的参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("c_id", id);
        map.put("c_name", name);
        map.put("c_date", date);
//		map.put("c_num", num);
        map.put("c_depict", depict);
        // 生成HTML
        HtmlUtil htmlUtil = new HtmlUtil();
        String shareurl = htmlUtil.createGameHtmlV2("ek-gameInformationShare", map);
        args.add(shareurl);

        args.add(logourl);

        // 查询id是否存在
        boolean isExists = eKGameInfoDao.existId(id);

        if (!isExists) {// id不存在，保存
            eKGameInfoDao.saveGameInfo(args);
        } else {// id存在，更新
            args.remove(0);
            args.add(args.size(), id);
            eKGameInfoDao.updateGameInfo(args);
        }
    }

}

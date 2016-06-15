package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKGameGiftDao;
import com.org.mokey.basedata.sysinfo.dao.GameGiftDao;
import com.org.mokey.basedata.sysinfo.service.EKGameGiftService;
import com.org.mokey.basedata.sysinfo.service.GameGiftService;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏礼包
 */
@Service
public class EKGameGiftServiceImpl implements EKGameGiftService {

    private static final Logger LOGGER = Logger.getLogger(EKGameGiftServiceImpl.class);

    @Autowired
    private EKGameGiftDao eKGameGiftDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    // 查询礼包列表
    public List<Map<String, Object>> gameGiftList(String gid, String name, int page, String gName) {
        return eKGameGiftDao.gameGiftList(gid, name, page, gName);
    }

    @Override
    // 获取礼包总数
    public Integer getTotal(String gid, String name, String gName) {
        return eKGameGiftDao.getTotal(gid, name, gName);
    }

    @Override
    // 根据礼包id删除游戏礼包
    public void deleteGameGift(String id) {
        // 删除礼包码
        eKGameGiftDao.deleteGameGiftCode(id);
        // 根据礼包id删除游戏礼包
        eKGameGiftDao.deleteGameGift(id);
    }

    @Override
    // 根据id查询礼包
    public List<Map<String, Object>> queryGiftById(String editId) {
        return eKGameGiftDao.queryGiftById(editId);
    }

    @Override
    // 保存礼包
    public void saveGameGift(String id, String gid, String name, String depict,
                             String sdate, String edate, String method, String count,
                             String order, String cate) {
        List<Object> args = new ArrayList<Object>();
        args.add(id);
        args.add(gid);
        args.add(name);
        args.add(depict);
        try {
            Date sD = ApDateTime.getDate(sdate, "yyyy-MM-dd HH:mm:ss");
            Date eD = ApDateTime.getDate(edate, "yyyy-MM-dd HH:mm:ss");
            args.add(sD);
            args.add(eD);
        } catch (ParseException e) {
            LOGGER.error("Parsing Date in GameGiftServiceImpl.saveGameGift failed, e : {}", e);
        }
        args.add(method);
        args.add(count);
        args.add(order);
        args.add(cate);

        // 查询id是否存在
        boolean isExists = eKGameGiftDao.existId(id);

        if (!isExists) {// id不存在，保存
            eKGameGiftDao.saveGameGift(args);
        } else {// id存在，更新
            args.remove(0);
            args.add(args.size(), id);
            eKGameGiftDao.updateGameGift(args);
        }
    }

    @Override
    // 保存上传文件中的礼包码
    public void upload(List<String> list, String id) {
        List<Object[]> codes = new ArrayList<Object[]>();
        if (StrUtils.isNotEmpty(list)) {
            for (String code : list) {
                Object[] row = new Object[5];
                String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_GAME_GIFTS_CODE_INFO");
                row[0] = newId;// 主键
                row[1] = id;// 礼包id
                row[2] = code;// 礼包码
                row[3] = "1";// 是否有效
                row[4] = "0";// 状态 0：未领取  1：已领取
                codes.add(row);
            }
        }

        eKGameGiftDao.upload(codes);
    }

    @Override
    // 根据礼包id查询相关礼包码
    public Map<String, Object> getCodesListByGid(int page, String gid,
                                                 String state, String sDate, String eDate) {
        return eKGameGiftDao.getCodesListByGid(page, gid, state, sDate, eDate);
    }

    @Override
    // 删除礼包码
    public void delCode(String gid, String id) {
        eKGameGiftDao.delCode(gid, id);
    }

    @Override
    // 批量删除礼包码
    public void batchDel(String gid, String id) {
        String[] ids = id.split(",");
        List<Object[]> list = new ArrayList<Object[]>();
        if (StrUtils.isNotEmpty(ids)) {
            for (String s : ids) {
                Object[] row = new Object[2];
                row[0] = gid;
                row[1] = s;
                list.add(row);
            }
        }
        eKGameGiftDao.batchDel(list);
    }

    @Override
    // 按“领取状态”查询所有指定礼包的礼包码
    public List<Map<String, Object>> getCodesByGidNState(String gid,
                                                         String state) {
        return eKGameGiftDao.getCodesByGidNState(gid, state);
    }

}

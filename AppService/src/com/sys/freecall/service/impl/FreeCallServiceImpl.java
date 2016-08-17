package com.sys.freecall.service.impl;

import com.sys.freecall.service.FreeCallService;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/7/8.
 */
@Service
public class FreeCallServiceImpl extends SqlMapClientDaoSupport implements FreeCallService {

    // 走马灯广告
    @Override
    public List<Map<String, Object>> marqueeBanner() {
        return getSqlMapClientTemplate().queryForList("freeCall.marqueeBanner");
    }

    @Override
    // 轮播广告
    public List<Map<String, Object>> advert() {
        return getSqlMapClientTemplate().queryForList("freeCall.advert");
    }

    /**
     * 套餐列表
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> meal() {
        return getSqlMapClientTemplate().queryForList("freeCall.meal");
    }

    /**
     * 充值到期时间
     *
     * @param uid
     * @return
     */
    @Override
    public String term(String uid) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        String c_date="";
        try {
            c_date = (String) this.getSqlMapClientTemplate().queryForObject("freeCall.term", paramMap);
        } catch (DataAccessException e) {
            logger.info(e);
        }
        return c_date;
    }
}

package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKRewardDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/4/20.
 */
@Repository
public class EKRewardDaoImpl implements EKRewardDao {
    private static final Logger LOGGER = Logger.getLogger(EKRewardDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 加载列表页面
     *
     * @param page
     * @param type
     * @return
     */
    @Override
    public Map<String, Object> list(int page, String type) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            if (StrUtils.isNotEmpty(page)) {
                StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_EK_REWARD T WHERE 1=1");
                List<Object> args = new ArrayList<Object>();
                if (StrUtils.isNotEmpty(type)) {
                    sql.append(" AND T.C_TYPE= ?");
                    args.add(type);
                }
                int count = jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
                // 查询
                sql.append(" ORDER BY C_ID");
                String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 5, 5, args).toString().replace("COUNT(1)", "T.C_ID,T.C_DAYS,T.C_SCORE,T.C_TYPE," +
                        "CASE WHEN T.C_TYPE = '2' THEN '连续天数' ELSE '累计天数' END AS TYPE");
                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, args.toArray());
                ret.put("count", count);
                ret.put("list", list);
            }
        } catch (Exception e) {
            LOGGER.error("EKRewardDaoImpl.list failed, e : " + e);
        }
        return ret;
    }

    /**
     * 修改
     *
     * @param args
     */
    @Override
    public void updateReward(List<Object> args) {
        try {
            String sql = "UPDATE T_EK_REWARD SET C_DAYS = ?,C_SCORE=?,C_TYPE=? WHERE C_ID = ?";
            this.jdbcTemplate.update(sql, args.toArray());
        } catch (Exception e) {
            LOGGER.error("EKRewardDaoImpl.updateReward failed, e : " + e);
        }
    }
}

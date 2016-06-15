package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.ExchangeDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

/**
 * Created by Maryn on 2016/1/13.
 */
public class ExchangeDaoImpl implements ExchangeDao {

    private static final Log LOGGER = LogFactory.getLog(ExchangeDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Map<String, Object> uncompletedExchange(int page, String pname, String state) {
        Map<String, Object> ret = new HashMap<String, Object>();
        try {
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM" +
                    " T_GAME_MEMBER_EXCHANGE T, T_GAME_MALL_PRODUCT P WHERE" +
                    " T.C_ISLIVE = '1' AND P.C_ISLIVE = '1' AND T.C_PID = P.C_ID");
            List<Object> argsList = new ArrayList<Object>();
            if (StrUtils.isNotEmpty(state)) {
                sql.append(" AND T.C_STATE = ?");
                argsList.add(state);
            }
            if (StrUtils.isNotEmpty(pname)) {
                sql.append(" AND P.C_NAME like ?");
                argsList.add("%" + pname + "%");
            }
            // 查询总数
            int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

            sql.append(" ORDER BY T.C_CDATE DESC ");
            String sql1 = DaoUtil.addfy_oracle(sql, (page - 1) * 10, 10, argsList)
                    .toString().replace("COUNT(1)", "T.*, P.C_NAME");
            // 查询数据
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());

            // 取出所有的用户id，要兑换的商品的id存入集合
            List<String> uidList = new ArrayList<>();
            List<String> pidList = new ArrayList<>();

            if (StrUtils.isNotEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> m = list.get(i);
                    String uid = m.get("C_UID").toString();
                    String pid = m.get("C_PID").toString();
                    uidList.add(uid);
                    pidList.add(pid);
                }
            }
            // 查询所有用户id对应的积分
            Map<String,Object> parameterMap = new HashMap<String,Object>();
            parameterMap.put("uidList", uidList);
            sql.delete(0, sql.length());
            sql.append("SELECT T.C_UID, T.C_SCORE FROM T_GAME_MEMBER_SCORE T WHERE T.C_UID IN (:uidList)");
            // 根据用户id查询用户的积分
            List<Map<String, Object>> userScores = namedParameterJdbcTemplate.queryForList(sql.toString(), parameterMap);


            parameterMap.remove("uidList");
            parameterMap.put("pidList", pidList);
            sql.delete(0, sql.length());
            sql.append("SELECT T.C_ID, T.C_COST, T.C_TOTAL FROM T_GAME_MALL_PRODUCT T WHERE T.C_ID IN (:pidList)");
            // 根据产品id查询兑换商品的需要的积分
            List<Map<String, Object>> costList = namedParameterJdbcTemplate.queryForList(sql.toString(), parameterMap);

            if (StrUtils.isNotEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> m = list.get(i);
                    String uid = m.get("C_UID").toString();
                    String pid = m.get("C_PID").toString();
                    for (int j = 0; j < userScores.size(); j++) {
                        Map<String, Object> scoreMap = userScores.get(j);
                        String c_uid = scoreMap.get("C_UID").toString();
                        if (uid.equals(c_uid)) {
                            String score = scoreMap.get("C_SCORE").toString();
                            m.put("SCORE", score);
                        }
                    }
                    for (int j = 0; j < costList.size(); j++) {
                        Map<String, Object> proMap = costList.get(j);
                        String c_id = proMap.get("C_ID").toString();
                        if (pid.equals(c_id)) {
                            String cost = proMap.get("C_COST").toString();
                            String total = proMap.get("C_TOTAL").toString();
                            m.put("COST", cost);
                            m.put("TOTAL", total);
                        }
                    }
                }
            }

            ret.put("count", count);
            ret.put("list", list);
        } catch (Exception e) {
            LOGGER.error("ExchangeDaoImpl.uncompletedExchange failed, e : ", e);
        }
        return ret;
    }

    @Override
    public void complete(String id, String userName) {
        try {
            String sql = "UPDATE T_GAME_MEMBER_EXCHANGE SET C_STATE = '1'" +
                    ", C_EDATE = ?, C_EDITOR = ? WHERE C_ID = ?";
            jdbcTemplate.update(sql, new Object[]{new Date(), userName, id});
        } catch (Exception e) {
            LOGGER.error("ExchangeDaoImpl.complete failed, e : ", e);
        }
    }

    @Override
    public void updateUserScore(String pid, String uid, String id, String cost) {
        try {
            // 查询产品花费的积分
            //String sql = "SELECT C_COST FROM T_GAME_MALL_PRODUCT WHERE C_ID = ?";
            //Integer cost = jdbcTemplate.queryForObject(sql, new Object[]{pid}, Integer.class);
            // 更新用户积分
            String sql = "UPDATE T_GAME_MEMBER_SCORE SET C_SCORE = C_SCORE - ?," +
                    " C_DATE = ? WHERE C_UID = ?";
            jdbcTemplate.update(sql, new Object[]{cost, new Date(), uid});
        } catch (Exception e) {
            LOGGER.error("ExchangeDaoImpl.updateUserScore failed, e : ", e);
        }
    }

    @Override
    public void updateProductCount(String pid, String userName) {
        try {
            String sql = "UPDATE T_GAME_MALL_PRODUCT SET" +
                    " C_TOTAL = C_TOTAL - 1, C_EDITOR = ? WHERE C_ID = ?";
            jdbcTemplate.update(sql, new Object[]{userName, pid});
        } catch (Exception e) {
            LOGGER.error("ExchangeDaoImpl.complete failed, e : ", e);
        }
    }
}

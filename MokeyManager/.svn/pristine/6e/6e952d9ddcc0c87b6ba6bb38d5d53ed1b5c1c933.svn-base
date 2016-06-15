package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.MallProductDao;
import com.org.mokey.basedata.sysinfo.dao.MessageDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.*;

public class MessageDaoImpl implements MessageDao {
    private JdbcTemplate jdbcTemplate;

    @Override
    public void delete (String id) { // 物理删除
        String sql = "delete from  T_EK_GAME_MEMBER_MESSAGE where C_ID=?";

        jdbcTemplate.update(sql, new Object[]{id});
    }

    @Override
    public Map<String, Object> listByUid (long uid, int start, int limit) {
        Map<String, Object> ret = new HashMap<String, Object>();

        StringBuilder sql = new StringBuilder(
                "select count(1) from T_EK_GAME_MEMBER_MESSAGE t where 1=1 "); // t.C_ISLIVE = 1
        StringBuilder sql0 = new StringBuilder("SELECT T.C_ID, T.C_UID, T.C_MESS, T.C_IMG, T.C_TYPE, T.C_DATE, T.C_REPLIED,T1.C_NICKNAME FROM T_EK_GAME_MEMBER_MESSAGE T LEFT JOIN T_EK_MEMBER_INFO T1 ON T.C_UID = T1.C_ID WHERE 1=1 AND T1.C_STATE = '1'");

        List<Object> argsList = new ArrayList<Object>();

        sql.append(" and t.c_uid = ?");
        sql0.append(" and t.c_uid = ?");
        argsList.add(uid);

        int count = jdbcTemplate.queryForObject(sql.toString(), argsList
                .toArray(), Integer.class);

        sql0.append(" ORDER BY T.C_REPLIED DESC, T.C_DATE DESC ");


        String sql1 = DaoUtil.addfy_oracle(sql0, start, limit, argsList)
                .toString();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
        Collections.reverse(list);
        ret.put("count", count);
        ret.put("list", list);
        return ret;
    }

    @Override
    public Map<String, Object> list (String name, int start, int limit) {
        Map<String, Object> ret = new HashMap<String, Object>();

        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(T.C_ID) FROM T_EK_GAME_MEMBER_MESSAGE T LEFT JOIN T_EK_MEMBER_INFO T1 ON T.C_UID = T1.C_ID WHERE T1.C_STATE = '1'AND T.C_DATE IN (SELECT MAX(C_DATE) FROM T_EK_GAME_MEMBER_MESSAGE GROUP BY C_UID) "); // t.C_ISLIVE = 1
        StringBuilder sql0 = new StringBuilder("SELECT T.C_ID, T.C_UID, T.C_MESS, T.C_IMG, T.C_TYPE, T.C_DATE, T.C_REPLIED,T1.C_NICKNAME FROM T_EK_GAME_MEMBER_MESSAGE T LEFT JOIN T_EK_MEMBER_INFO T1 ON T.C_UID = T1.C_ID WHERE T1.C_STATE = '1' AND T.C_DATE IN ( SELECT MAX(C_DATE) FROM T_EK_GAME_MEMBER_MESSAGE GROUP BY C_UID)");

        List<Object> argsList = new ArrayList<Object>();

        if (StrUtils.isNotEmpty(name)) {
            sql.append(" and t.C_MESS like ?");
            sql0.append(" AND T.C_MESS LIKE ?");
            argsList.add("%" + name + "%");
        }
        int count = jdbcTemplate.queryForObject(sql.toString(), argsList
                .toArray(), Integer.class);

        sql0.append(" ORDER BY T.C_REPLIED , T.C_DATE DESC ");


        String sql1 = DaoUtil.addfy_oracle(sql0, start, limit, argsList)
                .toString();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
        ret.put("count", count);
        ret.put("list", list);
        return ret;
    }

    @Override
    public void save (Map<String, Object> map) {
        final String tableName = "T_EK_GAME_MEMBER_MESSAGE";
        final String seqName = "seq_ek_game_member_message";
        final String idName = "C_ID";
        final String uidName = "C_UID";
        final String updateSql = "update " + tableName + " set C_REPLIED=1 where c_uid = ?";
        Object id_old = map.get(idName);
        Object uid = map.get(uidName);

        if (!StringUtils.isEmpty(id_old)) {
            String sql = "select count(1) from T_EK_GAME_MEMBER_MESSAGE t where t.c_id=?";
            int count = jdbcTemplate.queryForObject(sql, new Object[]{id_old}, Integer.class);

            if (count >= 1) {
                Map<String, Object> wMap = new HashMap<String, Object>();
                wMap.put(idName, id_old);
                JdbcTemplateUtils.updateDataByMap(jdbcTemplate, map, wMap,
                        tableName);
            } else {
                String sqlid = JdbcTemplateUtils.getSeqId(jdbcTemplate,
                        seqName);
                map.put(idName, sqlid);
                JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map,
                        tableName);
            }
        } else {
            String sqlid = JdbcTemplateUtils.getSeqId(jdbcTemplate,
                    seqName);
            map.put(idName, sqlid);
            JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map,
                    tableName);
        }

        getJdbcTemplate().update(updateSql,new Object[] {uid});
    }

    public JdbcTemplate getJdbcTemplate () {
        return jdbcTemplate;
    }

    public void setJdbcTemplate (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}

package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.McrAppDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class McrAppDaoImpl implements McrAppDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate () {
        return jdbcTemplate;
    }

    public void setJdbcTemplate (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<String, Object> list (String name, int start, int limit) {
        Map<String, Object> ret = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer("SELECT COUNT(1)" +
                " FROM T_MCRAPP_INFO T WHERE 1=1 "); // t.C_ISLIVE = 1
        List<Object> argsList = new ArrayList<Object>();

        if (StrUtils.isNotEmpty(name)) {
            sql.append(" AND T.C_NAME LIKE ?");
            argsList.add("%" + name + "%");
        }
        int count = jdbcTemplate.queryForObject(sql.toString(), argsList.toArray(), Integer.class);

        //sql.append(" ORDER BY T.C_LAST_UPDATE ");
        String sql1 = DaoUtil.addfy_oracle(sql, start, limit, argsList)
                .toString().replace("COUNT(1)", "*");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());

        List<Map<String, Object>> categories = jdbcTemplate.queryForList("select c_id,c_name from t_mcrapp_category t where t.c_islive='1' order by c_order");
        ret.put("count", count);
        ret.put("list", list);
        ret.put("categories", categories);
        return ret;
    }


    @Override
    public void save (Map<String, Object> map) {
        Object id_old = map.get("C_ID");
        map.put("C_PUBLISH_DATE", new Date());
        map.put("C_LAST_UPDATE", new Date());
        if (null != id_old && !id_old.toString().isEmpty()) {
            String sql = "select count(1) from T_MCRAPP_INFO t where t.c_id=?";
            int count = jdbcTemplate.queryForObject(sql, new Object[]{id_old}, Integer.class);

            if (count >= 1) {
                Map<String, Object> wMap = new HashMap<String, Object>();
                wMap.put("C_ID", id_old);
                JdbcTemplateUtils.updateDataByMap(jdbcTemplate, map, wMap,
                        "T_MCRAPP_INFO");
            } else {
                String sqlid = JdbcTemplateUtils.getSeqId(jdbcTemplate,
                        "SEQ_MCRAPP_INFO");
                map.put("C_ID", sqlid);
                JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map,
                        "T_MCRAPP_INFO");
            }
        } else {
            String sqlid = JdbcTemplateUtils.getSeqId(jdbcTemplate,
                    "SEQ_MCRAPP_INFO");
            map.put("C_ID", sqlid);
            JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map,
                    "T_MCRAPP_INFO");
        }
    }

    @Override
    public void delete (String id) {
        String sql = "delete from T_MCRAPP_INFO where C_ID=?";


        final String delSql0 = "delete t_mcrapp_category_mcrapp where c_aid = ?";
        final String delSql1 = "delete t_mcrapp_category_mcrapp where c_aid = ?";

        jdbcTemplate.update(sql, new Object[]{id});

        // 删除app的同时，删除其在分类或者合集下面的记录
        jdbcTemplate.update(delSql0, new Object[]{id});
        jdbcTemplate.update(delSql1, new Object[]{id});
    }

}

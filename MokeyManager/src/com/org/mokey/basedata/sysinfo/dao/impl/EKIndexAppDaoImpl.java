package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.EKIndexAppDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.*;

@Repository
public class EKIndexAppDaoImpl implements EKIndexAppDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void delete (String id) { // 物理删除
        String sql = "delete from  T_EK_INDEX_APP_INFO where C_ID=?";

        jdbcTemplate.update(sql, new Object[]{id});
    }

    @Override
    public Map<String, Object> list (String name, int start, int limit,String isLive) {
        Map<String, Object> ret = new HashMap<String, Object>();

        StringBuilder sql = new StringBuilder(
                "select count(1) from T_EK_INDEX_APP_INFO ");
        StringBuilder sql0 = new StringBuilder("select c_id, c_name, c_img, c_url, c_type,c_isLive from t_ek_index_app_info ");

        List<Object> argsList = new ArrayList<Object>();

        if("0".equals(isLive)) {
            sql.append("  where c_isLive='0' ");
            sql0.append("  where c_isLive='0' ");
        } else {
            sql.append("  where c_isLive='1' ");
            sql0.append("  where c_isLive='1' ");
        }

        if (StrUtils.isNotEmpty(name)) {
            sql.append(" and C_NAME like ?");
            sql0.append(" AND c_name LIKE ?");
            argsList.add("%" + name + "%");
        }


        int count = jdbcTemplate.queryForObject(sql.toString(), argsList
                .toArray(), Integer.class);

        sql0.append(" ORDER BY c_cTime DESC ");


        String sql1 = DaoUtil.addfy_oracle(sql0, start, limit, argsList)
                .toString();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
        ret.put("count", count);
        ret.put("list", list);
        return ret;
    }

    @Override
    public void save (Map<String, Object> map) {
        final String tableName = "T_EK_INDEX_APP_INFO";
        final String seqName = "seq_ek_index_app_info";
        final String idName = "C_ID";
        Object id_old = map.get(idName);

        map.put("c_cTime",new Date());

        if (!StringUtils.isEmpty(id_old)) {
            String sql = "select count(1) from T_EK_INDEX_APP_INFO t where t.c_id=?";
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
            String sqlId = JdbcTemplateUtils.getSeqId(jdbcTemplate,
                    seqName);
            map.put(idName, sqlId);
            JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map,
                    tableName);
        }
    }
}

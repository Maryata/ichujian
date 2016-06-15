package com.org.mokey.basedata.sysinfo.dao.impl;

import com.org.mokey.basedata.sysinfo.dao.MallProductDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MallProductDaoImpl implements MallProductDao {
    private JdbcTemplate jdbcTemplate;

    @Override
    public void delete (String id) { // 物理删除
        String sql = "delete from  T_GAME_MALL_PRODUCT where C_ID=?";

        jdbcTemplate.update(sql, new Object[]{id});
    }

    @Override
    public Map<String, Object> listGame (int start, int limit) {
        Map<String, Object> ret = new HashMap<String, Object>();

        StringBuilder sql = new StringBuilder(
                "select C_ID as value,C_NAME as text from t_game_app_info where C_ISLIVE = '1'");
        List<Object> argsList = new ArrayList<Object>();


        String sql1 = DaoUtil.addfy_oracle(sql, start, limit, argsList)
                .toString();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());

        ret.put("list", list);
        return ret;
    }

    @Override
    public Map<String, Object> list (String name, int start, int limit) {
        Map<String, Object> ret = new HashMap<String, Object>();

        StringBuilder sql = new StringBuilder(
                "select count(1) from T_GAME_MALL_PRODUCT t where 1=1  "); //and t.c_isLive='1' // t.C_ISLIVE = 1
        List<Object> argsList = new ArrayList<Object>();

        if (StrUtils.isNotEmpty(name)) {
            sql.append(" and t.C_NAME like ?");
            argsList.add("%" + name + "%");
        }
        int count = jdbcTemplate.queryForObject(sql.toString(), argsList
                .toArray(), Integer.class);

        sql.append(" order by t.c_order desc, t.c_date desc,t.c_isLive desc ");
        String sql1 = DaoUtil.addfy_oracle(sql, start, limit, argsList)
                .toString().replace("count(1)", "*");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, argsList.toArray());
        ret.put("count", count);
        ret.put("list", list);
        return ret;
    }

    @Override
    public void save (Map<String, Object> map) {
        final String tableName = "T_GAME_MALL_PRODUCT";
        final String seqName = "SEQ_GAME_MALL_PRODUCT";
        final String idName = "C_ID";
        Object id_old = map.get(idName);


        if (!StringUtils.isEmpty(id_old)) {
            String sql = "select count(1) from T_GAME_MALL_PRODUCT t where t.c_id=?";
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
    }

    public JdbcTemplate getJdbcTemplate () {
        return jdbcTemplate;
    }

    public void setJdbcTemplate (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}

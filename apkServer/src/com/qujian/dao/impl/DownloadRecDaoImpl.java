package com.qujian.dao.impl;

import com.qujian.dao.DownloadRecDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Maryn on 2016/1/4.
 */
@Repository
public class DownloadRecDaoImpl extends JdbcDaoSupport implements DownloadRecDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initSqlMapClient() { super.setJdbcTemplate(jdbcTemplate); }

    @Override
    public void recode(String cilentIp, String licenseCn) {

        List<Object> args = new ArrayList<Object>();
        args.add(licenseCn);// 激活码
        args.add(licenseCn.substring(5,11));// 供应商
        args.add(cilentIp);// ip
        args.add(new Date());

        String sql = "INSERT INTO T_SYS_DOWNLOAD_REC " +
                "(C_ID, C_CODE, C_SUPCODE, C_IP, C_DATE) " +
                "VALUES(SEQ_SYS_DOWNLOAD_REC.NEXTVAL, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, args.toArray());
    }

    @Override
    public void openRec(String cilentIp, String licenseCn) {

        List<Object> args = new ArrayList<Object>();
        args.add(StringUtils.isEmpty(licenseCn) ? "" : licenseCn);// 激活码
        args.add(StringUtils.isEmpty(licenseCn) ? "" : (licenseCn.length()<12 ? licenseCn : licenseCn.substring(5,11)));// 供应商
        args.add(cilentIp);// ip
        args.add(new Date());

        String sql = "INSERT INTO T_SYS_OPENPAGE_REC " +
                "(C_ID, C_CODE, C_SUPCODE, C_IP, C_DATE) " +
                "VALUES(SEQ_SYS_OPENPAGE_REC.NEXTVAL, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, args.toArray());
    }
}

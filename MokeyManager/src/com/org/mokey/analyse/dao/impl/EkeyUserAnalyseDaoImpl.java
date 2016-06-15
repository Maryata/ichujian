package com.org.mokey.analyse.dao.impl;

import com.org.mokey.analyse.dao.EkeyUserAnalyseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Maryn on 2016/4/5.
 */
@Repository
public class EkeyUserAnalyseDaoImpl implements EkeyUserAnalyseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
}

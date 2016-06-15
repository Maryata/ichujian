package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/1/13.
 */
public interface ExchangeDao {

    Map<String,Object> uncompletedExchange(int page, String pname, String state);

    void complete(String id, String userName);

    void updateUserScore(String pid, String uid, String id, String cost);

    void updateProductCount(String pid, String userName);
}

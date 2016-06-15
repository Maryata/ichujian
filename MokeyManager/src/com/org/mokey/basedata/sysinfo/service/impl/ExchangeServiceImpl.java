package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.ExchangeDao;
import com.org.mokey.basedata.sysinfo.service.ExchangeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * Created by Maryn on 2016/1/13.
 */
public class ExchangeServiceImpl implements ExchangeService {

    private static final Log LOGGER = LogFactory.getLog(ExchangeServiceImpl.class);

    private ExchangeDao exchangeDao;

    public ExchangeDao getExchangeDao() {
        return exchangeDao;
    }

    public void setExchangeDao(ExchangeDao exchangeDao) {
        this.exchangeDao = exchangeDao;
    }

    @Override
    public Map<String, Object> uncompletedExchange(int page, String pname, String state) {
        return exchangeDao.uncompletedExchange(page, pname, state);
    }

    @Override
    public void complete(String id, String uid, String pid, String cost, String userName) {
        try {
            // 修改审核状态
            exchangeDao.complete(id, userName);
            // 修改用户积分
            exchangeDao.updateUserScore(pid, uid, id, cost);
            // 修改商品数量
            exchangeDao.updateProductCount(pid, userName);
        } catch (Exception e) {
            LOGGER.error("ExchangeServiceImpl.complete failed, e : ", e);
        }
    }
}

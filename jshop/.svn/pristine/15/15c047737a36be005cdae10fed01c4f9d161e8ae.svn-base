package net.jeeshop.services.front.accountAdapter.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.accountAdapter.AccountAdapterService;
import net.jeeshop.services.front.accountAdapter.bean.AccountAdapter;
import net.jeeshop.services.front.accountAdapter.dao.AccountAdapterDao;
/**
 * 适配清单 service实现层
 * @author wn
 *
 */
@Service("AccountAdapterServiceFront")
public class AccountAdapterServiceImpl extends ServersManager<AccountAdapter,AccountAdapterDao> implements AccountAdapterService{
    @Resource(name="accountAdapterDaoFront")
	@Override
	public void setDao(AccountAdapterDao accountAdapterDao) {
		this.dao=accountAdapterDao;
	}

}

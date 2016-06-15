package net.jeeshop.services.manage.accountAdapter.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.manage.accountAdapter.AccountAdapterService;
import net.jeeshop.services.manage.accountAdapter.bean.AccountAdapter;
import net.jeeshop.services.manage.accountAdapter.dao.AccountAdapterDao;
/**
 * 适配清单service层
 * @author wn
 *
 */
@Service("accountAdapterServiceManage")
public class AccountAdapterServiceImpl extends ServersManager<AccountAdapter,AccountAdapterDao> implements AccountAdapterService{
	@Resource(name="accountAdapterDaoManage")
	@Override
	public void setDao(AccountAdapterDao accountAdapterDao) {
	this.dao=accountAdapterDao;
		
	}

}

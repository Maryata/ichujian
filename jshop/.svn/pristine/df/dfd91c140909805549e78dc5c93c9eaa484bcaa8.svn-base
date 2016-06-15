package net.jeeshop.web.action.manage.accountAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.jeeshop.core.Services;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.accountAdapter.AccountAdapterService;
import net.jeeshop.services.manage.accountAdapter.bean.AccountAdapter;
import net.jeeshop.web.action.BaseController;
import net.jeeshop.web.action.manage.account.AccountAction;
/**
 * 适配清单 控制层
 * @author wn
 *
 */
	@Controller
	@RequestMapping("/manage/accountAdapter/")
public class AccountAdapterAction extends BaseController<AccountAdapter>{
	 private static final Logger logger = LoggerFactory.getLogger(AccountAdapterAction.class);
	 private static final String page_toList = "/manage/accountAdapter/accountAdapterList";
	 private AccountAdapterAction(){
	        super.page_toList = page_toList;
	    }
	@Autowired
    private AccountAdapterService accountAdapterService;
	
	@Override
	public Services<AccountAdapter> getService() {
		return accountAdapterService;
	}
	
	public void setAccountAdapterService(AccountAdapterService accountAdapterService) {
		this.accountAdapterService = accountAdapterService;
	}
	
	   @Override
	protected void selectListAfter(PagerModel pager) {
		pager.setPagerUrl("selectList");
		}

}

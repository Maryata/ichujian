package net.jeeshop.web.action.front.accountAdapter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.jeeshop.core.Services;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.services.front.accountAdapter.AccountAdapterService;
import net.jeeshop.services.front.accountAdapter.bean.AccountAdapter;
import net.jeeshop.web.action.BaseController;
import net.jeeshop.web.util.LoginUserHolder;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Controller("frontAccountAdapterAction")
@RequestMapping("accountAdapter")
public class AccountAdapterAction extends BaseController<AccountAdapter> {
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(AccountAdapterAction.class);
	@Autowired
	private AccountAdapterService accountAdapterService;

	@Override
	public Services<AccountAdapter> getService() {
		// TODO Auto-generated method stub
		return accountAdapterService;
	}

	public void setAccountAdapterService(
			AccountAdapterService accountAdapterService) {
		this.accountAdapterService = accountAdapterService;
	}

	/**
	 * 适配清单 添加toAdd
	 * 
	 * @return page_toAdd---->null
	 */
	@RequestMapping(value = "toAdd", method = RequestMethod.POST)
	@ResponseBody
	public String toAdd(HttpServletRequest request) {
	   String str=request.getParameter("accountAdapters"); 
		int row = 0;
		//String str = "[{'brand':'三星' , 'models':'A7','buyNumber':'20'} , {'brand':'三星' , 'models':'A8','buyNumber':'30'}]";
		if(StrUtils.isEmpty(LoginUserHolder.getLoginAccount())){
			return returnMsg("k","你好，请登录!");
		}
		if (StrUtils.isEmpty(str)) {
			return returnMsg("j","内容不为空!");
		} else {
			List<AccountAdapter> list = this.getList(str, new AccountAdapter());
			AccountAdapter e=new AccountAdapter();
			for (int i=0;i<list.size();i++) {
				e.setBrand(list.get(i).getBrand());
				e.setBuyNumber(list.get(i).getBuyNumber());
				e.setModels(list.get(i).getModels());
				e.setAccountId(LoginUserHolder.getLoginAccount().getId());// LoginUserHolder.getLoginAccount()
				row = accountAdapterService.insert(e);
			}
			if (row > 0) {
				return returnMsg("n","添加成功");
			} else {
				return returnMsg("m","添加失败!");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String str, T t) {
		JSONArray array = JSONArray.fromObject(str);// 将str转换成json对象
		JsonConfig jsonConfig = new JsonConfig();// 参数设置
		jsonConfig.setRootClass(t.getClass());// 设置array中的对象类型
		List<T> list = (List<T>) JSONArray.toCollection(array, jsonConfig);// 将数组转换成T类型的集合
		return list;
	}

}

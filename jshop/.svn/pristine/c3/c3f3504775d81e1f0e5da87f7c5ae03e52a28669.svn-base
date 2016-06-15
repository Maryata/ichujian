package net.jeeshop.web.action.front.findpwd;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.jeeshop.core.Services;
import net.jeeshop.core.util.MD5;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.services.front.account.AccountService;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.web.action.BaseController;
import net.jeeshop.web.util.LoginUserHolder;
@Controller("frontFindPwdAction")
@RequestMapping("find")
public class PasswordAction extends BaseController<Account>{
	@Autowired
	private AccountService accountService;
	@Override
	public Services<Account> getService() {
		return null;
	}
	//public static final String toFindpwd = "/account/findpwd";//转到密码修改界面,forword方式 地址不变
	public static final String toFindpwd = "/account/updatepwd1";
	public static final String toLogin = "/account/login";//转到登陆界面,forword方式 地址不变
	public AccountService getAccountService() {
		return accountService;
	}
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	/**
	 * 转到修改密码页面
	 * @return
	 */
	/*@RequestMapping("findpwd")
	public String topwd(ModelMap model, @ModelAttribute("e") Account e){
		if (LoginUserHolder.getLoginAccount() == null) {//如果是空值时，跳转到修改页面
			return toFindpwd;
		}
		return "/account/topwd";
	}*/
	/**
	 * 转到修改密码页面
	 * @return
	 */
	@RequestMapping("findpwd")
	public String topwd(ModelMap model, @ModelAttribute("e") Account e){
		if (LoginUserHolder.getLoginAccount() == null) {//如果是空值时，跳转到修改页面
			return toFindpwd;
		}
		return "/account/topwd";
	}
	/**
	 * 判断用户账号是否存在
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("doCheckAjax")
	public String doRegister(Account e, ModelMap model,HttpServletRequest request) throws IOException {
		//手机号判断
		String phone = request.getParameter("phone");
		Account ac;
		 e = new Account();
		e.setPhone(phone);
		if (StrUtils.isEmpty(e.getPhone()) || e.getPhone().length()<11){
			return returnMsg("n","手机号格式错误");
		}

		if (accountService.selectCount(e)==0){
			return returnMsg("n","手机号不存在");
		}
		if (StrUtils.isEmpty(e.getPhone()) || e.getPhone().length()==11){
			 ac= accountService.selectByPhone(e);
			 model.addAttribute("e",ac);
			}
		return "/account/updatepwd2";
		
	}	
	
	/**
	 * 判断手机验证码存在
	 * 
	 * @return
	 * @throws IOException 
	 */
	/*@RequestMapping("doCheckAjax")*/
	/*public String doCheck(Account e, ModelMap model,HttpServletRequest request) throws IOException {
		//手机号判断
		Account ac;
		String phone = request.getParameter("phone");
		 e = new Account();
		 e.setPhone(phone);
		 if (StrUtils.isEmpty(e.getPhone()) || e.getPhone().length()==11){
			 ac= accountService.selectByPhone(e);
			 model.addAttribute("e",ac);
			}
		
		return "/account/updatepwd2";
		
	}	*/
	
	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("doCheckPwd")
	public String doUpdatePassword(Account e, ModelMap model,HttpServletRequest request, RedirectAttributes flushAttrs) throws IOException {
		String id = request.getParameter("id");
		String newPassword = request.getParameter("newPassword");
		String newPassword2=request.getParameter("newPassword2");
		if(!newPassword.equals(newPassword2)){
			throw new RuntimeException("两次输入的密码不一致！");
		}
	 	e  = accountService.selectById(id);
		e.setPassword(MD5.md5(newPassword));
		accountService.updatePasswordByAccount(e);
		return "/account/updatepwd3";
	}	
		
}

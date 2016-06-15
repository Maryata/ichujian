package net.jeeshop.web.action.manage.commision;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.jeeshop.core.ManageContainer;
import net.jeeshop.core.Services;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.core.util.ApDateTime;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.services.manage.account.bean.Account;
import net.jeeshop.services.manage.commision.CommisionService;
import net.jeeshop.services.manage.commision.bean.Commision;
import net.jeeshop.services.manage.order.OrderService;
import net.jeeshop.services.manage.order.bean.Order;
import net.jeeshop.web.action.BaseController;
import net.sf.json.JSONArray;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/*佣金管理*/
@Controller
@RequestMapping("/manage/commision/")
public class CommisionAction extends BaseController<Commision> {
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(CommisionAction.class);
	private static final String page_toList = "/manage/commision/commisionList";
	private static final String page_toEdit = "/manage/commision/commisionEdit";
	@Autowired
	private CommisionService commisionService;

	
	private CommisionAction() {
		super.page_toList = page_toList;
		super.page_toEdit = page_toEdit;
	}

	@Override
	protected void selectListAfter(PagerModel pager) {
		super.selectListAfter(pager);
	}

	@Override
	public Services<Commision> getService() {
		return commisionService;
	}

	@RequestMapping(value = "toUpdate")
	@ResponseBody
	public String toUpdate(Commision e, HttpSession session,HttpServletRequest request) {
		String id=request.getParameter("id");
		String commisionStatus=request.getParameter("commisionStatus");
		String ptotal=request.getParameter("ptotal");
		User u = (User) session.getAttribute(ManageContainer.manage_session_user_info);
		e.setId(id);
		if("1".equals(commisionStatus)){//申请
			e.setCommisionStatus("1");
			e.setCommisionSale(u.getUsername());
			e.setCommisionTime(ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_SSS));
		}else if("2".equals(commisionStatus)){
			DecimalFormat df = new DecimalFormat("0.00");
			e.setCommisionStatus("2");
			e.setCommisionCheckUser(u.getUsername());
			e.setCommisionCheckTime(ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_SSS));
			e.setCommision(df.format(Double.valueOf(ptotal) * 0.06));
		}
		
		int row=commisionService.update(e);
		JSONObject json = new JSONObject();
		if(row>0){
			json.put("status", 1);
			json.put("e", e);
		}else{
			json.put("status", 0);
		}
		return json.toJSONString();
	}

}

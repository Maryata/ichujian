package net.jeeshop.web.action.manage.dataApply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.jeeshop.core.Services;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.dataApply.DataApplyService;
import net.jeeshop.services.manage.dataApply.bean.DataApply;
import net.jeeshop.web.action.BaseController;
/**
 * 会员资料下载
 * @author wn
 *
 */
@Controller
@RequestMapping("/manage/dataApply/")
public class DataApplyAction extends BaseController<DataApply>{
	private static final String page_toList = "/manage/dataApply/dataApplyList";
	private DataApplyAction(){
		super.page_toList=page_toList;
	}
	@Autowired
	private DataApplyService dataApplyService;
	@Override
	public Services<DataApply> getService() {
		// TODO Auto-generated method stub
		return dataApplyService;
	}
	
	
	protected void selectListAfter(PagerModel pager) {
		pager.setPagerUrl("selectList");
		}
}

package net.jeeshop.web.action.manage.dataCenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.jeeshop.core.ManageContainer;
import net.jeeshop.core.Services;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.core.util.DateTimeUtil;
import net.jeeshop.services.manage.dataCenter.DataCenterService;
import net.jeeshop.services.manage.dataCenter.bean.DataCenter;
import net.jeeshop.services.manage.dataCenter.bean.DataFile;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntDetail;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntSolutn;
import net.jeeshop.web.action.BaseController;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manage/dataCenter/")
public class DataCenterAction extends BaseController<DataCenter> {

	@Autowired
	private DataCenterService dataCenterService;

	@Override
	public Services<DataCenter> getService() {
		return dataCenterService;
	}

	private static final String page_toList = "/manage/dataCenter/dataCenterList";
	private static final String page_toEdit = "/manage/dataCenter/dataCenterEdit";
	private static final String page_toAdd = "/manage/dataCenter/dataCenterEdit";

	private DataCenterAction() {
		super.page_toList = page_toList;
		super.page_toAdd = page_toAdd;
		super.page_toEdit = page_toEdit;
	}

	/**
	 * 资料中心查询方法
	 */
	protected void selectListAfter(PagerModel pager) {
		pager.setPagerUrl("selectList");
	}

	/**
	 * 添加资料中心
	 * 
	 * @param ds
	 * @param dd
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "addDataCenter", method = RequestMethod.POST)
	public String addDataCenter(DataCenter ds, DataFile dd, HttpSession session) {
		// 获取当前登录人的id
		User u = (User) session
				.getAttribute(ManageContainer.manage_session_user_info);
		ds.setUserId(u.getId());
		dataCenterService.insert(ds);
		dataCenterService.inserDetail(ds, dd, u);
		return "redirect:selectList";
	}

	/**
	 * 更新方法
	 * 
	 * @param ds
	 * @param model
	 * @return
	 */
	@RequestMapping("toDataCenterEdit")
	public String toEdit(@ModelAttribute("e") DataCenter ds, ModelMap model) {
		// 查询资源
		ds = dataCenterService.selectOne(ds);
		model.addAttribute("e", ds);
		// 查询资源详情
		DataFile dd = new DataFile();
		dd.setcId(ds.getId());
		List<DataFile> list = dataCenterService.selectDataDetail(dd);
		model.addAttribute("list", list);
		return page_toEdit;
	}

	/**
	 * 根据id删除资源详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delDetailById", method = RequestMethod.POST)
	@ResponseBody
	public String delDetailById(HttpServletRequest request) {
		String id = request.getParameter("id");
		DataFile dd = new DataFile();
		dd.setId(id);
		int i = dataCenterService.delDetailById(dd);
		return String.valueOf(i);
	}

	/**
	 * 更新资源中心及资源详情详情
	 * 
	 * @param ds
	 * @param dd
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "updateDataCenter", method = RequestMethod.POST)
	public String updateDiscnt(DataCenter ds, DataFile dd, HttpSession session) {
		// 获取当前登录人
		User u = (User) session
				.getAttribute(ManageContainer.manage_session_user_info);
		ds.setUserId(u.getId());
		dataCenterService.update(ds);
		dataCenterService.updateDetail(ds, dd, u);
		return "redirect:selectList";
	}
	/**
	 * 删除id指定的资源，以及删除资源下面所有的资源详情
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	 @RequestMapping(value = "delete", method = RequestMethod.POST)
	 @ResponseBody
	public String delete(String id) throws Exception{
		 if (StringUtils.isBlank(id)) {
				throw new NullPointerException("参数不正确！");
			}
	   boolean isSuccess= dataCenterService.deleteId(id);
	   return String.valueOf(isSuccess);
	}

}

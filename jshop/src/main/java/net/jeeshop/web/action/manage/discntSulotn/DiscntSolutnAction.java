package net.jeeshop.web.action.manage.discntSulotn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.jeeshop.core.ManageContainer;
import net.jeeshop.core.Services;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.services.manage.discntSolutn.DiscntSolutnService;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntDetail;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntSolutn;
import net.jeeshop.web.action.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manage/discntSolutn/")
public class DiscntSolutnAction extends BaseController<DiscntSolutn>{

	private static final String page_toList = "/manage/discntSolutn/discntSolutnList";
    private static final String page_toEdit = "/manage/discntSolutn/discntSolutnEdit";
    private static final String page_toAdd = "/manage/discntSolutn/discntSolutnEdit";
    
    private DiscntSolutnAction(){
    	super.page_toList = page_toList;
        super.page_toAdd = page_toAdd;
        super.page_toEdit = page_toEdit;
    }
    
	@Autowired
	private DiscntSolutnService dsService;
	
	@Override
	public Services<DiscntSolutn> getService() {
		return dsService;
	}
	
	/**
	 * 添加折扣方案及方案详情
	 * @param ds 折扣方案
	 * @param dd 方案详情
	 * @param session 
	 * @return
	 */
	@RequestMapping(value="addDiscnt",method=RequestMethod.POST)
	public String addDiscnt(DiscntSolutn ds, DiscntDetail dd, HttpSession session){
		// 获取当前登录人
		User u = (User) session.getAttribute(ManageContainer.manage_session_user_info);
		// 设置操作人
		ds.setUpdatePerson(u.getUsername());
		ds.setStatus("y");
		// 保存方案
		dsService.insert(ds);
		// 保存方案详情
		dsService.inserDetail(ds,dd);
		return "redirect:selectList";
	}
	
	
	@RequestMapping("toDiscntEdit")
	public String toEdit(@ModelAttribute("e")DiscntSolutn ds, ModelMap model){
		// 查询折扣方案
		ds = dsService.selectOne(ds);
		model.addAttribute("e", ds);
		// 查询方案详情
		DiscntDetail dd = new DiscntDetail();
		dd.setSid(ds.getId());
		List<DiscntDetail> list = dsService.selectDiscntDetail(dd);
		model.addAttribute("list", list);
		return page_toEdit;
	}
	
	/**
	 * 更新折扣方案及方案详情
	 * @param ds
	 * @param dd
	 * @param session
	 * @return
	 */
	@RequestMapping(value="updateDiscnt",method=RequestMethod.POST)
	public String updateDiscnt(DiscntSolutn ds, DiscntDetail dd, HttpSession session){
		// 获取当前登录人
		User u = (User) session.getAttribute(ManageContainer.manage_session_user_info);
		ds.setUpdatePerson(u.getUsername());
		// 更新折扣方案
		dsService.update(ds);
		// 更新方案详情
		dsService.updateDetail(ds, dd);
		return "redirect:selectList";
	}
	
	/**
	 * 根据id删除方案详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value="delDetailById",method=RequestMethod.POST)
	@ResponseBody
	public String delDetailById(HttpServletRequest request){
		String id = request.getParameter("id");
		DiscntDetail dd = new DiscntDetail();
		dd.setId(id);
		int i = dsService.delDetailById(dd);
		return String.valueOf(i);
	}
}

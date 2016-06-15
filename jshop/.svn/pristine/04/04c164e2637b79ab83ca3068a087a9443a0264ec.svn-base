package net.jeeshop.web.action.manage.keyvalue;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.jeeshop.core.Services;
import net.jeeshop.core.oscache.ManageCache;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.services.manage.systemDict.SystemDictService;
import net.jeeshop.services.manage.systemDict.bean.SystemDict;
import net.jeeshop.web.action.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manage/systemDict/")
public class SystemDictAction extends BaseController<SystemDict> {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
    @Autowired
	private SystemDictService systemDictService;
    
    //@Autowired
    //private SystemManager systemManager;
    
    @Autowired
    private ManageCache manageCache;

    private static final String page_toList = "/manage/systemDict/systemDictList";
    private static final String page_toAdd = "/manage/systemDict/systemDictEdit";
    private static final String page_toEdit = "/manage/systemDict/systemDictEdit";
    
    public SystemDictAction(){
        super.page_toList = page_toList;
        super.page_toAdd = page_toAdd;
        super.page_toEdit = page_toEdit;
    }
	
	@Override
	public Services<SystemDict> getService() {
		return systemDictService;
	}

	/**
	 * 查询所有数据
	 * @param request
	 * @param e
	 * @return
	 */
	@RequestMapping("systemDictList")
	public String systemDictList(HttpServletRequest request, @ModelAttribute("e") SystemDict e){
		if(null==e){
			e = new SystemDict();
		}
		String ddlKey = request.getParameter("ddlKey");
		if(StrUtils.isNotEmpty(ddlKey)){
			e.setDdlKey(ddlKey);
		}
		List<SystemDict> list = systemDictService.selectList(e);
		
		request.setAttribute("list", list);
		request.setAttribute("e", e);
		return page_toList;
	}
	
	/**
	 * 跳转到类别编辑页面
	 * @param e
	 * @param model
	 * @return
	 */
	@RequestMapping("toSystemDictEdit")
	public String toSystemDictEdit(SystemDict e, ModelMap model){
		// 查询大类
		SystemDict systemDict = systemDictService.selectOne(e);
		// 查询大类下的所有子类
		String id = e.getId();
		e.clear();
		e.setPid(id);
		List<SystemDict> datas = systemDictService.selectByPid(e);
		model.addAttribute("e", systemDict);
		model.addAttribute("datas", datas);
		return page_toEdit;
	}
	
	/**
	 * 根据id删除字典分类数据
	 * @param e
	 * @return
	 */
	@RequestMapping("delSystemDictById")
	@ResponseBody
	public String delSystemDictById(SystemDict e){
		int result = systemDictService.delete(e);
		try {
			this.reset();
		} catch (Exception e2) {
			logger.error("reset Redis failed in DeptAction.delSystemDictById, e :" + e2);
		}
		return String.valueOf(result);
	}
	
	/**
	 * 更新分类及子类
	 * @param e
	 * @param flushAttrs
	 * @return
	 */
	@RequestMapping("updateSystemDict")
	public String updateSystemDict(SystemDict e){
		// 更新大类
		systemDictService.update(e);
		// 更新子类
		systemDictService.updateSub(e);
		e.clear();
		try {
			this.reset();
		} catch (Exception e2) {
			logger.error("reset Redis failed in DeptAction.updateSystemDict, e :" + e2);
		}
		return "redirect:systemDictList";
	}
	
	/**
	 * 添加分类
	 * @param e
	 * @return
	 */
	@RequestMapping("addSystemDict")
	public String addSystemDict(SystemDict e){
		// 添加大类
		e.setPid("0");
		systemDictService.insert(e);
		// 添加子类
		systemDictService.addSub(e);
		try {
			this.reset();
		} catch (Exception e2) {
			logger.error("reset Redis failed in DeptAction.addSystemDict, e :" + e2);
		}
		return "redirect:systemDictList";
	}
	
	// 刷新缓存
	private void reset() {
		manageCache.loadSystemDict();
	}
}

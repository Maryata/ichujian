package net.jeeshop.web.action.manage.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jeeshop.core.Services;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.oscache.ManageCache;
import net.jeeshop.core.system.bean.Dept;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.services.manage.system.DeptService;
import net.jeeshop.web.action.BaseController;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;

/**
 * 后台部门管理
 * @author Maryn
 *
 */
@Controller
@RequestMapping("/manage/dept")
public class DeptAction extends BaseController<Dept> {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DeptAction.class);

	private static final long serialVersionUID = 1L;

	private static final String page_toList = "/manage/system/dept/deptList";
	private static final String page_toAdd = "/manage/system/dept/deptEdit";
	
    public DeptAction() {
        super.page_toEdit = page_toEdit;
        super.page_toList = page_toList;
        super.page_toAdd = page_toAdd;
    }
    @Autowired
	private DeptService deptService;
    
    @Autowired
    private SystemManager systemManager;
    
    @Autowired
    private ManageCache manageCache;

    @Override
	public Services<Dept> getService() {
		return deptService;
	}
    
	@Override
	public void insertAfter(Dept e) {
		e.clear();
	}
	
	/**
	 * 查询所有部门信息
	 * @param request
	 * @param e 查询条件
	 * @return
	 */
	@RequestMapping("/deptList")
	public String deptList(HttpServletRequest request, @ModelAttribute("e") Dept e){
		// 查询所有部门
		List<Dept> result = this.getDeptList(e);
		
		request.setAttribute("list", result);
		request.setAttribute("e", e);
		return page_toList;
	}
	
	// 查询所有部门
	public List<Dept> getDeptList(Dept e) {
		List<Dept> root = deptService.loadRoot(e);
		List<Dept> result = Lists.newArrayList();
		for(Dept dept : root){
			// 将查询结果存入List
			appendChildren(dept, result);
		}
		return result;
	}

	/**
	 * 跳转到添加部门页面
	 * @param request
	 * @param e
	 * @return
	 */
	@RequestMapping("/toDeptAdd")
	public String addDept(Dept e, ModelMap model){
		e.clear();
		model.addAttribute("e", e);
		List<Dept> depts = new ArrayList<Dept>();
		try {
			depts = systemManager.getDepts();
			model.addAttribute("list", depts);
		} catch (Exception e1) {
			logger.error("loading from Redis failed in DeptAction.addDept, e :" + e1);
		}
		// 如果缓存中没有，就从数据库中查询
		if(depts.isEmpty() || depts.size()<=0){
			depts = deptService.loadRoot(e);
			model.addAttribute("list", depts);
			try {
				// 刷新缓存
				this.reset();
			} catch (Exception e2) {
				logger.error("reset Redis failed in DeptAction.addDept, e :" + e2);
			}
		}
		return page_toAdd;
	}
	
	/**
	 * 添加部门
	 * @param request
	 * @param e	新增部门信息
	 * @param flushAttrs 跳转携带的信息
	 * @return
	 */
	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, Dept e, RedirectAttributes flushAttrs){
		if(StringUtils.isBlank(e.getPid())){
			e.setPid("1");
		}
		try {
			deptService.insert(e);
			e.clear();
		} catch (Exception ex) {
			logger.error("DeptAction.insert failed,e :" + ex);
			addMessage(flushAttrs, "新增失败！");
			return page_toAdd;
		}
		try {
			reset();
		} catch (Exception e1) {
			logger.error("reset Redis failed in DeptAction.insert, e :" + e1);
		}
		addMessage(flushAttrs, "新增成功！");
		return "redirect:deptList";
	}
	
	/**
	 * 跳转到编辑页面
	 * @param request
	 * @param e 请求参数，id属性值不为空
	 * @return
	 */
	@RequestMapping("/toDeptEdit")
	public String toEdit(HttpServletRequest request, Dept e){
		String id = e.getId();
		if(StrUtils.isEmpty(id)){
			logger.error("DeptAction.toEdit failed, id is null!");
			throw new RuntimeException();
		}
		// 加载所有部门
		List<Dept> depts = new ArrayList<Dept>();
		try {
			depts = systemManager.getDepts();
		} catch (Exception e1) {
			logger.error("loading from Redis failed in DeptAction.toEdit, e :" + e1);
		}
		request.setAttribute("list", depts);
		// 如果缓存中没有，从数据库中查询
		if(depts.isEmpty() || depts.size()<=0){
			e.clear();
			List<Dept> result = getDeptList(e);
			request.setAttribute("list", result);
			try {
				// 刷新缓存
				this.reset();
			} catch (Exception e2) {
				logger.error("reset Redis failed in DeptAction.toEdit, e :" + e2);
			}
		}
		e.clear();
		e.setId(id);
		// 根据id查询数据
		e = deptService.selectOne(e);
		request.setAttribute("e", e);
		return page_toAdd;
	}
	
	/**
	 * 更新部门信息
	 * @param request
	 * @param e
	 * @param flushAttrs
	 * @return
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Dept e, RedirectAttributes flushAttrs){
		try {
			if(StrUtils.isEmpty(e.getPid())){
				e.setPid("1");
			}
			// 更新数据库
			deptService.update(e);
			e.clear();
		} catch (Exception e1) {
			logger.error("DeptAction.update failed,e :" + e1);
			addMessage(flushAttrs, "更新失败！");
			return page_toAdd;
		}
		// 更新缓存
		try {
			this.reset();
		} catch (Exception e2) {
			logger.error("reset Redis failed in DeptAction.update, e :" + e2);
		}
		return "redirect:deptList";
	}
	
	/**
	 * 根据id删除
	 * @param e
	 * @return
	 */
	@RequestMapping("deleteById")
	@ResponseBody
	public String deleteById(Dept e){
		String id = e.getId();
		if(StrUtils.isEmpty(id)){
			logger.error("DeptAction.deleteById failed, id is null!");
			return "0";
		}
		// 删除
		String result = deptService.deleteById(e);
		// 刷新缓存
		try {
			this.reset();
		} catch (Exception e1) {
			logger.error("reset Redis failed in DeptAction.deleteById, e :" + e1);
		}
		return result;
	}
	
	/**
	 * 部门名称和部门代码的校验
	 * @param e 
	 * @param response
	 * @return
	 */
	@RequestMapping("/unique")
	@ResponseBody
	public String unique(@ModelAttribute("e") Dept e, HttpServletResponse response){
		logger.error("验证输入的字符的唯一性"+e);
		response.setCharacterEncoding("utf-8");
		if(StrUtils.isNotEmpty(e.getName())){
			logger.error("验证“部门名称”的唯一性"+e);
			Dept dept = new Dept();
			dept.setName(e.getName());
			dept = deptService.selectOne(dept);
			if(null == dept){
				//数据库中部存在此部门名称
				return "{\"ok\":\"部门名称可以使用!\"}";
			}else{
				if(StrUtils.isNotEmpty(e.getId()) && e.getId().equals(dept.getId())){
					// 如果id存在，则是更新
					return "{\"ok\":\"部门名称可以使用!\"}";
				}else{
					// 如果id不存在，则是新增
					return "{\"error\":\"部门名称已经存在!\"}";
				}
			}
		}else if(StrUtils.isNotEmpty(e.getCode())){
			logger.error("验证“部门代码”的唯一性"+e);
			Dept dept = new Dept();
			dept.setCode(e.getCode());
			dept = deptService.selectOne(dept);
			if(null == dept){
				//数据库中部存在此部门代码
				return "{\"ok\":\"部门代码可以使用!\"}";
			}else{
				if(StrUtils.isNotEmpty(e.getId()) && e.getId().equals(dept.getId())){
					// 如果id存在，则是更新
					return "{\"ok\":\"部门代码可以使用!\"}";
				}else{
					// 如果id不存在，则是新增
					return "{\"error\":\"部门代码已经存在!\"}";
				}
			}
		}
		return null;
	}
	
	/**
	 * 查询是否有下属部门
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/hasSubDept",method=RequestMethod.POST)
	@ResponseBody
	public String hasSubDept(HttpServletRequest request){
		String pid = request.getParameter("id");
		Dept dept = new Dept();
		dept.setPid(pid);
		List<Dept> list = deptService.selectList(dept);
		if(null!=list && list.size()>0){
			return "1";
		}
		return "0";
	}
	
	// 将部门存入List集合
	private void appendChildren(Dept dept, List<Dept> result) {
		if(null==dept){
			return;
		}
		// 将当前部门存入集合
		result.add(dept);
		if(null!=dept.getChildren() && dept.getChildren().size()>0){
			// 遍历所有子部门，递归调用本方法。将所有部门存入集合。
			for (Dept subDept : dept.getChildren()) {
				appendChildren(subDept,result);
			}
		}
	}
	
	/**
	 * 添加/修改/删除 某一个类别后，需要重新加载缓存数据。并且清除JSON字符串缓存，以便重新生成新的。
	 * @throws Exception
	 */
	private void reset() throws Exception {
        manageCache.loadDept();//同步更新缓存
	}
	
	
	

}

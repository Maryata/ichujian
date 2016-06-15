package com.org.mokey.basedata.sysinfo.action;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.service.McrAppCateService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;

/**
 * 微用帮：应用分类
 */
@SuppressWarnings("serial")
public class McrAppCateAction extends AbstractAction{
	
	private McrAppCateService mcrAppCateService;
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public McrAppCateService getMcrAppCateService() {
		return mcrAppCateService;
	}

	public void setMcrAppCateService(McrAppCateService mcrAppCateService) {
		this.mcrAppCateService = mcrAppCateService;
	}
	
	// 跳转到分类列表页
	public String toCateList(){
		return "mcrAppCateList";
	}
	
	// 查询所有分类总数
	public String getAllCateTotal(){
		log.info("into McrAppCateAction.getCateTotal");
		try{
			// 查询总数
			Integer total = mcrAppCateService.getAllCateTotal();
			Integer rows = 5;// 每页显示条数
			Integer totalPage = 0;
			if(total == 0){
				totalPage = 0;
			}else{
				totalPage = (total-1)/rows + 1;
			}
			// 回写查询结果
			this.writeJSONToResponse(totalPage);
		}catch(Exception e){
			log.error("McrAppCateAction.getAllCateTotal failed,",e);
		}
		return NONE;
	}
	
	// 获取分类列表
	public String getAllCateList(){
		// 获取page
		String sPage = getParameter("page"); 
		log.info("into McrAppCateAction.getAllCateList");
		log.info("page=" + sPage);
		try{
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			// 所有分类
			List<Map<String,Object>> list = mcrAppCateService.getAllCateList(page);
			// 回写查询结果
			this.writeJSONToResponse(list);
		}catch(Exception e){
			log.error("McrAppCateAction.getAllCateList failed,",e);
		}
		return NONE;
	}
	
	// 跳转到新增页面
	public String toCateAdd(){
		log.info("into McrAppCateAction.toCateAdd");
		try{
			// 获取主键
			String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_MCRAPP_CATEGORY");
			getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
			getRequest().setAttribute("cid", newId);
		}catch(Exception e){
			log.error("McrAppCateAction.toUpdate failed,",e);
		}
		return "toUpdate";
	}
	
	// 新增分类
	public String mcrAppCateAdd(){
		// 获取请求参数
		String id = getParameter("cid");
		String name = getParameter("cname");
		String order = getParameter("order");
		String logo = getParameter("logo");
		log.info("into McrAppCateAction.mcrAppCateAdd");
		log.info("id=" + id + "name=" + name + ", order=" + order + ", logo=" + logo);
		try{
			// 获取当前登录人
			String modifier = super.getSessionLoginUser().getUserName();
			// 新增
			mcrAppCateService.mcrAppCateAdd(id, name, logo, order, modifier);
		}catch(Exception e){
			log.error("McrAppCateAction.mcrAppCateAdd failed,",e);
		}
		return "update";
	}
	
	// 删除分类
	public String mcrAppCateDel(){
		// 获取cid
		String cid = getParameter("cid");
		log.info("into McrAppCateAction.mcrAppCateDel");
		log.info("cid=" + cid);
		try{
			// 删除
			mcrAppCateService.mcrAppCateDel(cid);
		}catch(Exception e){
			log.error("McrAppCateAction.mcrAppCateDel failed,",e);
		}
		return NONE;
	}
	
	// 跳转到分类修改页面
	public String toUpdate(){
		// 获取cid
		String cid = getParameter("editId");
		String cname = getParameter("editName");
		String editOrder = getParameter("editOrder");
		String editLogo = getParameter("editLogo");
		log.info("into McrAppCateAction.mcrAppCateDel");
		log.info("cid=" + cid + ", cname=" + cname +
				", editOrder=" + editOrder + ", editLogo=" + editLogo);
		try{
			getRequest().setAttribute("cid", cid);
			getRequest().setAttribute("cname", cname);
			if(editOrder==null || editOrder.equalsIgnoreCase("null")){
				editOrder = "";
			}
			getRequest().setAttribute("order", editOrder);
			getRequest().setAttribute("logo", editLogo);
		}catch(Exception e){
			log.error("McrAppCateAction.toUpdate failed,",e);
		}
		return "toUpdate";
	}
	
	// 更新分类
	public String update(){
		// 获取cid
		String cid = getParameter("cid");
		String cname = getParameter("cname");
		String order = getParameter("order");
		String logo = getParameter("logo");
		log.info("into McrAppCateAction.update");
		log.info("cid=" + cid + ", cname=" + cname + ", order="
					+ order + ", logo=" + logo);
		try{
			// 获取当前登录人
			String modifier = super.getSessionLoginUser().getUserName();
			// 更新
			mcrAppCateService.update(cid, cname, modifier, logo, order);
		}catch(Exception e){
			log.error("McrAppCateAction.update failed,",e);
		}
		return "update";
	}
	
	// 跳转到分类修改页面
	public String toHandle(){
		// 获取cid
		String cid = getParameter("editId");
		String name = getParameter("editName");
		log.info("into McrAppCateAction.toHandle");
		log.info("cid=" + cid + ", name=" + name);
		try{
			// 查询所有分类名称和id
			List<Map<String,Object>> appCate = mcrAppCateService.cateList();
			getRequest().setAttribute("appCate", appCate);
			getRequest().setAttribute("cid", cid);
			getRequest().setAttribute("name", name);
		}catch(Exception e){
			log.error("McrAppCateAction.toHandle failed,",e);
		}
		return "toHandle";
	}
	
	// 查询所有应用总数
	public String getAppTotal(){
		// 获取cid
		String cid = getParameter("cid");
		log.info("into McrAppCateAction.getAppTotal");
		log.info("cid=" + cid);
		try{
			// 查询总数
			Integer total = mcrAppCateService.getAppTotal(cid);
			Integer rows = 5;// 每页显示条数
			Integer totalPage = 0;
			if(total == 0){
				totalPage = 0;
			}else{
				totalPage = (total-1)/rows + 1;
			}
			// 回写查询结果
			this.writeJSONToResponse(totalPage);
		}catch(Exception e){
			log.error("McrAppCateAction.getAppTotal failed,",e);
		}
		return NONE;
	}
	
	// 查询所有应用
	public String getAppList(){
		// 获取page
		String sPage = getParameter("page"); 
		// 获取cid
		String cid = getParameter("cid");
		log.info("into McrAppCateAction.getAppList");
		log.info("cid=" + cid);
		try{
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			// 查询
			List<Map<String,Object>> list = mcrAppCateService.getAppList(page, cid);
			// 回写查询结果
			this.writeJSONToResponse(list);
		}catch(Exception e){
			log.error("McrAppCateAction.getAppList failed,",e);
		}
		return NONE;
	}
	
	// 查询当前分类的应用总数 
	public String getCateTotal(){
		// 获取cid
		String cid = getParameter("cid");
		log.info("into McrAppCateAction.getCateTotal");
		log.info("cid=" + cid);
		try{
			// 查询总数
			Integer total = mcrAppCateService.getCateTotal(cid);
			Integer rows = 5;// 每页显示条数
			Integer totalPage = 0;
			if(total == 0){
				totalPage = 0;
			}else{
				totalPage = (total-1)/rows + 1;
			}
			// 回写查询结果
			this.writeJSONToResponse(totalPage);
		}catch(Exception e){
			log.error("McrAppCateAction.getCateTotal failed,",e);
		}
		return NONE;
	}
	
	// 查询当前分类的应用 
	public String getCateList(){
		// 获取page
		String sPage = getParameter("page"); 
		// 获取cid
		String cid = getParameter("cid");
		log.info("into McrAppCateAction.getCateList");
		log.info("cid=" + cid);
		try{
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			// 查询
			List<Map<String,Object>> list = mcrAppCateService.getCateList(page, cid);
			// 回写查询结果
			this.writeJSONToResponse(list);
		}catch(Exception e){
			log.error("McrAppCateAction.getCateList failed,",e);
		}
		return NONE;
	}
	
	// 条件查询应用数量
	public String getTotalCondition(){
		String cid = getParameter("cid");// 分类id
		String name = getParameter("name");// 应用名称
		String appCate = getParameter("appCate");// 应用初始分类
		log.info("into McrAppCateAction.getTotalCondition");
		log.info("cid=" + cid + ", name=" + name + ", appCate=" + appCate);
		try{
			// 查询总数
			Integer total = mcrAppCateService.getTotalCondition(cid, name, appCate);
			Integer rows = 5;// 每页显示条数
			Integer totalPage = 0;
			if(total == 0){
				totalPage = 0;
			}else{
				totalPage = (total-1)/rows + 1;
			}
			// 回写查询结果
			this.writeJSONToResponse(totalPage);
		}catch(Exception e){
			log.error("McrAppCateAction.getTotalCondition failed,",e);
		}
		return NONE;
	}
	
	// 条件查询应用  
	public String getListCondition(){
		String sPage = getParameter("page");// 获取page
		String cid = getParameter("cid");// 分类id
		String name = getParameter("name");// 应用名称
		String appCate = getParameter("appCate");// 应用初始分类
		log.info("into McrAppCateAction.getListCondition");
		log.info("page=" + sPage + ", cid=" + cid + ", name=" + name + ", appCate=" + appCate);
		try{
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			// 查询
			List<Map<String,Object>> list = mcrAppCateService.getListCondition(page, cid, name, appCate);
			// 回写查询结果
			this.writeJSONToResponse(list);
		}catch(Exception e){
			log.error("McrAppCateAction.getListCondition failed,",e);
		}
		return NONE;
	}
	
	// 条件查询当前分类的应用数量
	public String getCateTotalCondition(){
		String cid = getParameter("cid");// 分类id
		String name = getParameter("name");// 应用名称
		log.info("into McrAppCateAction.getCateTotalCondition");
		log.info("cid=" + cid + ", name=" + name);
		try{
			// 查询总数
			Integer total = mcrAppCateService.getCateTotalCondition(cid, name);
			Integer rows = 5;// 每页显示条数
			Integer totalPage = 0;
			if(total == 0){
				totalPage = 0;
			}else{
				totalPage = (total-1)/rows + 1;
			}
			// 回写查询结果
			this.writeJSONToResponse(totalPage);
		}catch(Exception e){
			log.error("McrAppCateAction.getCateTotalCondition failed,",e);
		}
		return NONE;
	}
	
	// 条件查询当前分类的应用  
	public String getCateListCondition(){
		String sPage = getParameter("page");// 获取page
		String cid = getParameter("cid");// 分类id
		String name = getParameter("name");// 应用名称
		log.info("into McrAppCateAction.getCateListCondition");
		log.info("page=" + sPage + ", cid=" + cid + ", name=" + name);
		try{
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			// 查询
			List<Map<String,Object>> list = mcrAppCateService.getCateListCondition(page, cid, name);
			// 回写查询结果
			this.writeJSONToResponse(list);
		}catch(Exception e){
			log.error("McrAppCateAction.getCateListCondition failed,",e);
		}
		return NONE;
	}
	
	// 对应用进行分类
	public String handleCate(){
		// 获取分类id
		String cid = getParameter("cid");
		// 获取应用id
		String aid = getRequest().getParameter("aid");
		String[] aids = aid.split(",");// 切割
		// 获取要移除出当前分类的应用id
		String removeAid = getRequest().getParameter("removeAid");
		String[] removeAids = removeAid.split(",");// 切割
		// 获取顺序
		String order = getRequest().getParameter("order");
		String[] orders = order.split(",");// 切割
		log.info("into McrAppCateAction.handleCate");
		log.info("cid=" + cid + ", aids=" + aids + ", removeAids=" + removeAids + ", orders=" + orders);
		try{
			// 集合分类
			mcrAppCateService.handleCate(cid,aids,removeAids,orders);
		}catch(Exception e){
			log.error("McrAppCateAction.handleCate failed,",e);
		}
		return NONE;
	}
}

package com.org.mokey.basedata.sysinfo.action;

import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.service.McrAppColService;
import com.org.mokey.common.AbstractAction;

/**
 * 微用帮：应用合集
 */
@SuppressWarnings("serial")
public class McrAppColAction extends AbstractAction{
	
	private McrAppColService mcrAppColService;

	public McrAppColService getMcrAppColService() {
		return mcrAppColService;
	}

	public void setMcrAppColService(McrAppColService mcrAppColService) {
		this.mcrAppColService = mcrAppColService;
	}
	
	// 获取合集列表
	public String mcrAppColList(){
		// 获取cid
		log.info("into McrAppColAction.mcrAppColList");
		try{
			// 所有合集
			List<Map<String,Object>> list = mcrAppColService.mcrAppColList();
			// 存入值栈
			getValueStack().set("mcrAppCol", list);
		}catch(Exception e){
			log.error("McrAppColAction.mcrAppColList failed,",e);
		}
		return "mcrAppColList";
	}
	
	// 新增合集
	public String mcrAppColAdd(){
		// 获取cid
		String addName = getParameter("addName");
		log.info("into McrAppColAction.mcrAppColAdd");
		log.info("addName=" + addName);
		try{
			// 获取当前登录人
			String modifier = super.getSessionLoginUser().getUserName();
			// 新增
			mcrAppColService.mcrAppColAdd(addName, modifier);
		}catch(Exception e){
			log.error("McrAppColAction.mcrAppColAdd failed,",e);
		}
		return NONE;
	}
	
	// 删除合集
	public String mcrAppColDel(){
		// 获取cid
		String cid = getParameter("cid");
		log.info("into McrAppColAction.mcrAppColDel");
		log.info("cid=" + cid);
		try{
			// 删除
			mcrAppColService.mcrAppColDel(cid);
		}catch(Exception e){
			log.error("McrAppColAction.mcrAppColDel failed,",e);
		}
		return NONE;
	}
	
	// 跳转到合集修改页面
	public String toUpdate(){
		// 获取cid
		String cid = getParameter("editId");
		String cname = getParameter("editName");
		log.info("into McrAppColAction.mcrAppColDel");
		log.info("cid=" + cid + ", cname=" + cname);
		try{
			getRequest().setAttribute("cid", cid);
			getRequest().setAttribute("cname", cname);
		}catch(Exception e){
			log.error("McrAppColAction.toUpdate failed,",e);
		}
		return "toUpdate";
	}
	
	// 更新合集
	public String update(){
		// 获取cid
		String cid = getParameter("cid");
		String cname = getParameter("cname");
		log.info("into McrAppColAction.update");
		log.info("cid=" + cid + ", cname=" + cname);
		try{
			// 获取当前登录人
			String modifier = super.getSessionLoginUser().getUserName();
			// 更新
			mcrAppColService.update(cid, cname, modifier);
		}catch(Exception e){
			log.error("McrAppColAction.update failed,",e);
		}
		return "update";
	}
	
	// 跳转到合集修改页面
	public String toHandle(){
		// 获取cid
		String cid = getParameter("editId");
		String name = getParameter("editName");
		log.info("into McrAppColAction.toHandle");
		log.info("cid=" + cid + ", name=" + name);
		try{
			getRequest().setAttribute("cid", cid);
			getRequest().setAttribute("name", name);
		}catch(Exception e){
			log.error("McrAppColAction.toHandle failed,",e);
		}
		return "toHandle";
	}
	
	// 查询所有应用总数
	public String getAppTotal(){
		// 获取cid
		String cid = getParameter("cid");
		log.info("into McrAppColAction.getAppTotal");
		log.info("cid=" + cid);
		try{
			// 查询总数
			Integer total = mcrAppColService.getAppTotal(cid);
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
			log.error("McrAppColAction.getAppTotal failed,",e);
		}
		return NONE;
	}
	
	// 查询所有应用
	public String getAppList(){
		// 获取page
		String sPage = getParameter("page"); 
		// 获取cid
		String cid = getParameter("cid");
		log.info("into McrAppColAction.getAppList");
		log.info("cid=" + cid);
		try{
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			// 查询
			List<Map<String,Object>> list = mcrAppColService.getAppList(page, cid);
			// 回写查询结果
			this.writeJSONToResponse(list);
		}catch(Exception e){
			log.error("McrAppColAction.getAppList failed,",e);
		}
		return NONE;
	}
	
	// 查询当前合集的应用总数 
	public String getColTotal(){
		// 获取cid
		String cid = getParameter("cid");
		log.info("into McrAppColAction.getColTotal");
		log.info("cid=" + cid);
		try{
			// 查询总数
			Integer total = mcrAppColService.getColTotal(cid);
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
			log.error("McrAppColAction.getColTotal failed,",e);
		}
		return NONE;
	}
	
	// 查询当前合集的应用 
	public String getColList(){
		// 获取page
		String sPage = getParameter("page"); 
		// 获取cid
		String cid = getParameter("cid");
		log.info("into McrAppColAction.getColList");
		log.info("cid=" + cid);
		try{
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			// 查询
			List<Map<String,Object>> list = mcrAppColService.getColList(page, cid);
			// 回写查询结果
			this.writeJSONToResponse(list);
		}catch(Exception e){
			log.error("McrAppColAction.getColList failed,",e);
		}
		return NONE;
	}
	
	// 条件查询应用数量
	public String getTotalCondition(){
		String cid = getParameter("cid");// 合集id
		String name = getParameter("name");// 应用名称
		log.info("into McrAppColAction.getTotalCondition");
		log.info("cid=" + cid + ", name=" + name);
		try{
			// 查询总数
			Integer total = mcrAppColService.getTotalCondition(cid, name);
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
			log.error("McrAppColAction.getTotalCondition failed,",e);
		}
		return NONE;
	}
	
	// 条件查询应用  
	public String getListCondition(){
		String sPage = getParameter("page");// 获取page
		String cid = getParameter("cid");// 合集id
		String name = getParameter("name");// 应用名称
		log.info("into McrAppColAction.getListCondition");
		log.info("page=" + sPage + ", cid=" + cid + ", name=" + name);
		try{
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			// 查询
			List<Map<String,Object>> list = mcrAppColService.getListCondition(page, cid, name);
			// 回写查询结果
			this.writeJSONToResponse(list);
		}catch(Exception e){
			log.error("McrAppColAction.getListCondition failed,",e);
		}
		return NONE;
	}
	
	// 条件查询当前合集的应用数量
	public String getColTotalCondition(){
		String cid = getParameter("cid");// 合集id
		String name = getParameter("name");// 应用名称
		log.info("into McrAppColAction.getColTotalCondition");
		log.info("cid=" + cid + ", name=" + name);
		try{
			// 查询总数
			Integer total = mcrAppColService.getColTotalCondition(cid, name);
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
			log.error("McrAppColAction.getColTotalCondition failed,",e);
		}
		return NONE;
	}
	
	// 条件查询当前合集的应用  
	public String getColListCondition(){
		String sPage = getParameter("page");// 获取page
		String cid = getParameter("cid");// 合集id
		String name = getParameter("name");// 应用名称
		log.info("into McrAppColAction.getColListCondition");
		log.info("page=" + sPage + ", cid=" + cid + ", name=" + name);
		try{
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			// 查询
			List<Map<String,Object>> list = mcrAppColService.getColListCondition(page, cid, name);
			// 回写查询结果
			this.writeJSONToResponse(list);
		}catch(Exception e){
			log.error("McrAppColAction.getColListCondition failed,",e);
		}
		return NONE;
	}
	
	// 对应用进行合集分类
	public String handleCol(){
		// 获取合集id
		String cid = getParameter("cid");
		// 获取应用id
		String aid = getRequest().getParameter("aid");
		String[] aids = aid.split(",");// 切割
		// 获取要移除出当前合集的应用id
		String removeAid = getRequest().getParameter("removeAid");
		String[] removeAids = removeAid.split(",");// 切割
		// 获取顺序
		String order = getRequest().getParameter("order");
		String[] orders = order.split(",");// 切割
		log.info("into McrAppColAction.handleCol");
		log.info("cid=" + cid + ", aids=" + aids + ", removeAids=" + removeAids + ", orders=" + orders);
		try{
			// 集合分类
			mcrAppColService.handleCol(cid,aids,removeAids,orders);
		}catch(Exception e){
			log.error("McrAppColAction.handleCol failed,",e);
		}
		return NONE;
	}
}

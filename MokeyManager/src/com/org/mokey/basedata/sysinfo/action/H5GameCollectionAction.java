package com.org.mokey.basedata.sysinfo.action;

import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.service.H5GameCollectionService;
import com.org.mokey.common.AbstractAction;

/**
 * 游戏帮：H5游戏合集
 */
@SuppressWarnings("serial")
public class H5GameCollectionAction extends AbstractAction{
	
	private H5GameCollectionService h5GameCollectionService;

	public H5GameCollectionService getH5GameCollectionService() {
		return h5GameCollectionService;
	}

	public void setH5GameCollectionService(H5GameCollectionService h5GameCollectionService) {
		this.h5GameCollectionService = h5GameCollectionService;
	}
	
	// 获取合集列表
	public String h5GameCol(){
		// 获取cid
		log.info("into H5GameCollectionAction.h5GameCol");
		try{
			// 所有合集
			List<Map<String,Object>> list = h5GameCollectionService.h5GameCol();
			// 存入值栈
			getValueStack().set("h5GameCol", list);
		}catch(Exception e){
			log.error("H5GameCollectionAction.h5GameCol failed,",e);
		}
		return "h5GameCol";
	}
	
	// 添加合集
	public String h5AddCol(){
		// 获取合集名
		String name = getParameter("addName");
		log.info("into H5GameCollectionAction.h5AddCol");
		log.info("name=" + name);
		try{
			// 添加
			h5GameCollectionService.h5AddCol(name);
		}catch(Exception e){
			log.error("H5GameCollectionAction.h5AddCol failed,",e);
		}
		return NONE;
	}
	
	// 删除合集
	public String h5DeleteCol(){
		// 获取合集id
		String cid = getParameter("cid");
		log.info("into H5GameCollectionAction.h5DeleteCol");
		log.info("cid=" + cid);
		try{
			// 删除
			h5GameCollectionService.h5DeleteCol(cid);
		}catch(Exception e){
			log.error("H5GameCollectionAction.h5DeleteCol failed,",e);
		}
		return NONE;
	}
	
	// 使合集不可用/可用
	public String h5IsValid(){
		// 获取合集id
		String cid = getParameter("cid");
		// 获取有效性
		String islive = getParameter("islive");
		log.info("into H5GameCollectionAction.h5IsValid");
		log.info("cid=" + cid + ", islive=" + islive);
		try{
			// 失效
			h5GameCollectionService.h5IsValid(cid,islive);
		}catch(Exception e){
			log.error("H5GameCollectionAction.h5IsValid failed,",e);
		}
		return NONE;
	}
	
	// 修改合集
	public String h5ModifyCol(){
		String cid = getParameter("cid");// 获取合集id
		String cname = getParameter("cname"); // 获取合集名称
		String islive = getParameter("islive"); // 是否有效
		log.info("into H5GameCollectionAction.h5ModifyCol");
		log.info("cid=" + cid + ", cname=" + cname + ", islive=" + islive);
		try{
			// 修改
			h5GameCollectionService.h5ModifyCol(cid,cname,islive);
		}catch(Exception e){
			log.error("H5GameCollectionAction.h5ModifyCol failed,",e);
		}
		return NONE;
	}
	
	// 查询不属于当前合集的游戏总页数
	public String getH5Total(){
		// 获取合集id
		String cid = getParameter("cid");
		log.info("into H5GameCollectionAction.getH5Total");
		log.info("cid=" + cid);
		try {
			// 总条数
			Integer total = h5GameCollectionService.getTotal(cid);
			// 总页数
			Integer rows = 5;
			Integer totalPage = 0;
			if(total == 0){
				totalPage = 0;
			}else{
				totalPage = (total-1)/rows + 1;
			}
			// 回写查询结果
			writeJSONToResponse(totalPage);
		} catch (Exception e) {
			log.error("H5GameCollectionAction.getH5Total failed,",e);
		}
		return NONE;
	}
	
	// 查询不属于当前合集的游戏
	public String getH5GameList(){
		// 获取page
		String sPage = getParameter("page"); 
		// 获取合集id
		String cid = getParameter("cid");
		log.info("into H5GameCollectionAction.getH5GameList");
		log.info("page=" + sPage + ", cid=" + cid);
		try {
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			// 查询不属于当前合集的游戏
			List<Map<String,Object>> gameList = h5GameCollectionService.getGameList(page,cid);
			// 回写查询结果
			writeJSONToResponse(gameList);
		} catch (Exception e) {
			log.error("H5GameCollectionAction.getH5GameList failed,",e);
		}
		return NONE;
	}
	
	// 根据合集id查询合集对应的游戏页数
	public String getH5TotalCol(){
		// 获取合集id
		String cid = getParameter("cid");
		log.info("into H5GameCollectionAction.getH5TotalCol");
		log.info("cid=" + cid);
		try {
			// 总条数
			Integer total = h5GameCollectionService.getTotalCol(cid);
			// 总页数
			Integer rows = 5;
			Integer totalPage = 0;
			if(total == 0){
				totalPage = 0;
			}else{
				totalPage = (total-1)/rows + 1;
			}
			// 回写查询结果
			this.writeJSONToResponse(totalPage);
		} catch (Exception e) {
			log.error("H5GameCollectionAction.getH5TotalCol failed,",e);
		}
		return NONE;
	}
	
	// 根据合集id查询游戏
	public String getH5GamePageByColId(){
		// 获取cid
		String cid = getParameter("cid");
		// 获取page
		String sPage = getParameter("page"); 
		log.info("into H5GameCollectionAction.getH5GamePageByColId");
		log.info("cid=" + cid + ", page=" + sPage);
		try{
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			List<Map<String,Object>> list = h5GameCollectionService.getGamePageByColId(cid,page);
			if(null!=list && list.size()>0){
				for (Map<String, Object> map : list) {
					Object obj = map.get("C_ORDER");
					String order = (obj==null)?"":obj.toString();
					map.put("C_ORDER", order);
				}
			}
			this.writeJSONToResponse(list);
		}catch(Exception e){
			log.error("H5GameCollectionAction.getH5GamePageByColId failed,",e);
		}
		return NONE;
	}
	
	// 条件查询游戏总数
	public String getH5TotalCondition(){
		// 获取请求参数
		String cid = getParameter("cid");
		String name = getParameter("name");
		log.info("into H5GameCollectionAction.getH5TotalCondition");
		log.info("cid=" + cid + ", name=" + name);
		try {
			// 总条数
			Integer total = h5GameCollectionService.getTotalCondition(cid, name);
			// 总页数
			Integer rows = 5;
			Integer totalPage = 0;
			if(total == 0){
				totalPage = 0;
			}else{
				totalPage = (total-1)/rows + 1;
			}
			// 回写查询结果
			this.writeJSONToResponse(totalPage);
		} catch (Exception e) {
			log.error("H5GameCollectionAction.getH5TotalCondition failed,",e);
		}
		return NONE;
	}
	
	// 条件查询游戏APP
	public String queryH5GameCondition(){
		// 获取请求参数
		String cid = getParameter("cid");
		String name = getParameter("name");
		String sPage = getParameter("page"); 
		log.info("into H5GameCollectionAction.queryH5GameCondition");
		log.info("cid=" + cid + ", name=" + name + ", page=" + sPage);
		try {
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			// 条件查询
			List<Map<String,Object>> list = h5GameCollectionService.queryGameCondition(cid, name, page);
			// 返回json
			this.writeJSONToResponse(list);
		} catch (Exception e) {
			log.error("H5GameCollectionAction.queryH5GameCondition failed,",e);
		}
		return NONE;
	}
	
	// 对游戏进行合集分类
	public String h5HandleCol(){
		// 获取合集id
		String cid = getParameter("cid");
		// 获取游戏id
		String gid = getRequest().getParameter("gid");
		String[] gids = gid.split(",");// 切割
		// 获取要移除出当前合集的游戏id
		String removeGid = getRequest().getParameter("removeGid");
		String[] removeGids = removeGid.split(",");// 切割
		// 获取顺序
		String order = getRequest().getParameter("order");
		String[] orders = order.split(",");// 切割
		log.info("into GameCollectionAction.h5HandleCol");
		log.info("cid=" + cid + ", gids=" + gids + ", removeGids=" + removeGids + ", orders=" + orders);
		try{
			// 分类
			h5GameCollectionService.handleCol(cid,gids,removeGids,orders);
			
		}catch(Exception e){
			log.error("H5GameCollectionAction.h5HandleCol failed,",e);
		}
		return NONE;
	}
	
	// 条件查询合集游戏总数
	public String getH5ColTotalCondition(){
		// 获取请求参数
		String cid = getParameter("cid");
		String name = getParameter("name");
		log.info("into H5GameCollectionAction.getH5ColTotalCondition");
		log.info("cid=" + cid + ", name=" + name);
		try {
			// 总条数
			Integer total = h5GameCollectionService.getColTotalCondition(cid,name);
			// 总页数
			Integer rows = 5;
			Integer totalPage = 0;
			if(total == 0){
				totalPage = 0;
			}else{
				totalPage = (total-1)/rows + 1;
			}
			// 回写查询结果
			this.writeJSONToResponse(totalPage);
		} catch (Exception e) {
			log.error("H5GameCollectionAction.getH5ColTotalCondition failed,",e);
		}
		return NONE;
	}
	
	// 条件查询游戏APP
	public String queryH5ColGameCondition(){
		// 获取请求参数
		String cid = getParameter("cid");
		String name = getParameter("name");
		String sPage = getParameter("page"); 
		log.info("into H5GameCollectionAction.queryH5ColGameCondition");
		log.info("cid=" + cid + ", name=" + name + ", page=" + sPage);
		try {
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			// 条件查询
			List<Map<String,Object>> list = h5GameCollectionService.queryColGameCondition(cid,name,page);
			// 返回json
			this.writeJSONToResponse(list);
		} catch (Exception e) {
			log.error("H5GameCollectionAction.queryH5ColGameCondition failed,",e);
		}
		return NONE;
	}
}

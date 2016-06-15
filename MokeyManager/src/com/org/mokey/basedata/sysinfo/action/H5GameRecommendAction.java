package com.org.mokey.basedata.sysinfo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.service.H5GameCategoryService;
import com.org.mokey.basedata.sysinfo.service.H5GameRecommendService;
import com.org.mokey.common.AbstractAction;

/**
 * 游戏帮：H5游戏推荐
 */
@SuppressWarnings("serial")
public class H5GameRecommendAction extends AbstractAction{
	
	private H5GameRecommendService h5GameRecommendService;
	
	private H5GameCategoryService h5GameCategoryService;
	
	public H5GameCategoryService getH5GameCategoryService() {
		return h5GameCategoryService;
	}

	public void setH5GameCategoryService(H5GameCategoryService h5GameCategoryService) {
		this.h5GameCategoryService = h5GameCategoryService;
	}

	public H5GameRecommendService getH5GameRecommendService() {
		return h5GameRecommendService;
	}

	public void setH5GameRecommendService(H5GameRecommendService h5GameRecommendService) {
		this.h5GameRecommendService = h5GameRecommendService;
	}

	// 跳转到分类列表首页
	public String toH5GameRecommend(){
		List<Map<String,Object>> list = h5GameCategoryService.getGameCategoryList();
		getRequest().setAttribute("gameCate", list);
		return "toH5GameRecommend";
	}
	
	// 查询未作为推荐的H5游戏
	public String getAllGame(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		String name = getParameter("name");// 名称
		String gameCate = getParameter("gameCate");// 名称
		log.info("into GameRecommendAction.getAllGame");
		log.info("page=" + page + ", name=" + name + ", gameCate=" + gameCate);
		try{
			// 分页查询
			retmap = h5GameRecommendService.getAllGame(page, name, gameCate);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameRecommendAction.getAllGame failed, e : " + e);
		}
		return NONE;
	}
	
	// 查询推荐的H5游戏
	public String recommendGameList(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		String name = getParameter("name");// 名称
		log.info("into GameRecommendAction.recommendGameList");
		log.info("page=" + page + ", name=" + name);
		try{
			// 分页查询
			retmap = h5GameRecommendService.recommendGameList(page, name);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameRecommendAction.recommendGameList failed, e : " + e);
		}
		return NONE;
	}
	
	// 推荐维护
	public String handle(){
		String gid = getRequest().getParameter("gid");// 获取应用id
		String removeGid = getRequest().getParameter("removeGid");// 获取要移除出当前分类的应用id
		String order = getRequest().getParameter("order");// 获取顺序
		log.info("into GameRecommendAction.handle");
		log.info(" ---- gid=" + gid + " ---- removeGid=" + removeGid + " ---- order=" + order);
		try{
			// 维护
			h5GameRecommendService.handle(gid,removeGid,order);
		}catch(Exception e){
			log.error("GameRecommendAction.handle failed,",e);
		}
		return NONE;
	}
	
}

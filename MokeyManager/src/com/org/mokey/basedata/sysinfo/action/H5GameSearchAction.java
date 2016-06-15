package com.org.mokey.basedata.sysinfo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.service.GameCategoryService;
import com.org.mokey.basedata.sysinfo.service.H5GameHotSearchService;
import com.org.mokey.common.AbstractAction;

/**
 * 游戏帮：游戏推荐
 */
public class H5GameSearchAction extends AbstractAction{
	
	private static final long serialVersionUID = -7819052219332569650L;

	private H5GameHotSearchService h5GameHotSearchService;
	
	private GameCategoryService gameCategoryService;
	
	public GameCategoryService getGameCategoryService() {
		return gameCategoryService;
	}

	public void setGameCategoryService(GameCategoryService gameCategoryService) {
		this.gameCategoryService = gameCategoryService;
	}

	public H5GameHotSearchService getH5GameHotSearchService() {
		return h5GameHotSearchService;
	}

	public void setH5GameHotSearchService(
			H5GameHotSearchService h5GameHotSearchService) {
		this.h5GameHotSearchService = h5GameHotSearchService;
	}

	// 跳转到分类列表首页
	public String toH5GameSearch(){
		List<Map<String,Object>> list = gameCategoryService.getGameCategoryList();
		getRequest().setAttribute("gameCate", list);
		return "toH5GameSearch";
	}
	
	// 查询未作为推荐的游戏
	public String getAllGame(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		String name = getParameter("name");// 名称
		String gameCate = getParameter("gameCate");// 名称
		log.info("into GameH5SearchAction.getAllGame");
		log.info("page=" + page + ", name=" + name + ", gameCate=" + gameCate);
		try{
			// 分页查询
			retmap = h5GameHotSearchService.getAllGame(page, name, gameCate);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameH5SearchAction.getAllGame failed, e : " + e);
		}
		return NONE;
	}
	
	// 查询推荐的游戏
	public String recommendGameList(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		String name = getParameter("name");// 名称
		log.info("into GameH5SearchAction.recommendGameList");
		log.info("page=" + page + ", name=" + name);
		try{
			// 分页查询
			retmap = h5GameHotSearchService.recommendGameList(page, name);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameH5SearchAction.recommendGameList failed, e : " + e);
		}
		return NONE;
	}
	
	// 推荐维护
	public String handle(){
		String gid = getRequest().getParameter("gid");// 获取应用id
		String removeGid = getRequest().getParameter("removeGid");// 获取要移除出当前分类的应用id
		String order = getRequest().getParameter("order");// 获取顺序
		log.info("into GameH5SearchAction.handle");
		log.info(" ---- gid=" + gid + " ---- removeGid=" + removeGid + " ---- order=" + order);
		try{
			// 维护
			h5GameHotSearchService.handle(gid,removeGid,order);
		}catch(Exception e){
			log.error("GameH5SearchAction.handle failed,",e);
		}
		return NONE;
	}
	
}

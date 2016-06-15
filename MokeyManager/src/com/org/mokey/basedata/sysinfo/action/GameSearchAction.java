package com.org.mokey.basedata.sysinfo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.service.GameCategoryService;
import com.org.mokey.basedata.sysinfo.service.GameHotSearchService;
import com.org.mokey.common.AbstractAction;

/**
 * 游戏帮：热门搜索游戏
 */
@SuppressWarnings("serial")
public class GameSearchAction extends AbstractAction{
	
	private GameHotSearchService gameHotSearchService;
	
	private GameCategoryService gameCategoryService;
	
	public GameCategoryService getGameCategoryService() {
		return gameCategoryService;
	}

	public void setGameCategoryService(GameCategoryService gameCategoryService) {
		this.gameCategoryService = gameCategoryService;
	}

	public GameHotSearchService getGameHotSearchService() {
		return gameHotSearchService;
	}

	public void setGameHotSearchService(GameHotSearchService gameHotSearchService) {
		this.gameHotSearchService = gameHotSearchService;
	}

	// 跳转到分类列表首页
	public String toGameSearch(){
		List<Map<String,Object>> list = gameCategoryService.getGameCategoryList();
		getRequest().setAttribute("gameCate", list);
		return "toGameSearch";
	}
	
	// 查询未作为查询的游戏
	public String getAllGame(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		String name = getParameter("name");// 名称
		String gameCate = getParameter("gameCate");// 名称
		log.info("into GameSearchAction.getAllGame");
		log.info("page=" + page + ", name=" + name + ", gameCate=" + gameCate);
		try{
			// 分页查询
			retmap = gameHotSearchService.getAllGame(page, name, gameCate);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameSearchAction.getAllGame failed, e : " + e);
		}
		return NONE;
	}
	
	// 查询推荐的游戏
	public String recommendGameList(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		String name = getParameter("name");// 名称
		log.info("into GameSearchAction.recommendGameList");
		log.info("page=" + page + ", name=" + name);
		try{
			// 分页查询
			retmap = gameHotSearchService.recommendGameList(page, name);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameSearchAction.recommendGameList failed, e : " + e);
		}
		return NONE;
	}
	
	// 推荐维护
	public String handle(){
		String gid = getRequest().getParameter("gid");// 获取应用id
		String removeGid = getRequest().getParameter("removeGid");// 获取要移除出当前分类的应用id
		String order = getRequest().getParameter("order");// 获取顺序
		log.info("into GameSearchAction.handle");
		log.info(" ---- gid=" + gid + " ---- removeGid=" + removeGid + " ---- order=" + order);
		try{
			// 维护
			gameHotSearchService.handle(gid,removeGid,order);
		}catch(Exception e){
			log.error("GameSearchAction.handle failed,",e);
		}
		return NONE;
	}
	
}

package com.org.mokey.basedata.sysinfo.action;

import java.util.HashMap;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.service.GameGiftIndexService;
import com.org.mokey.common.AbstractAction;

/**
 * 游戏帮：礼包首页排版
 */
@SuppressWarnings("serial")
public class GameGiftIndexAction extends AbstractAction{
	
	private GameGiftIndexService gameGiftIndexService;
	
	public GameGiftIndexService getGameGiftIndexService() {
		return gameGiftIndexService;
	}

	public void setGameGiftIndexService(GameGiftIndexService gameGiftIndexService) {
		this.gameGiftIndexService = gameGiftIndexService;
	}
	
	// 跳转到礼包首页排版页面
	public String gameGiftIndex(){
		return "gameGiftIndex";
	}
	
	// 分页查询首页中未显示的分类
	public String cateList(){
		// 获取page
		int page = getParameter2Int("page", 1);
		// 应用名称
		String name = getParameter("name");
		Map<String, Object> retmap = new HashMap<String, Object>();
		log.info("into GameGiftIndexAction.cateList");
		log.info("page=" + page);
		try{
			retmap = gameGiftIndexService.cateList(page,name);// 分页查询首页中未显示的分类
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameGiftIndexAction.cateList failed, e : " + e);
		}
		return NONE;
	}
	
	// 查询首页显示的分类
	public String indexCateList(){
		// 获取page
		int page = getParameter2Int("page", 1);
		// 应用名称
		String name = getParameter("name");
		Map<String, Object> retmap = new HashMap<String, Object>();
		log.info("into GameGiftIndexAction.indexCateList");
		log.info("page=" + page);
		try{
			retmap = gameGiftIndexService.indexCateList(name, (page-1)*5, 5);
			retmap.put( "status", "Y" );
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put( "status", "N" );
			log.error("GameGiftIndexAction.indexCateList failed, e : " + e);
		}
		return NONE;
	}
	
	// 首页排版
	public String handle(){
		// 获取请求参数
		String id = getParameter("id");// 分类id
		String removeId = getParameter("removeId");// 移除出的分类id
		String order = getParameter("order");// 分类的排序
		String number = getParameter("number");// 分类中的应用的数量
		log.info("into GameGiftIndexAction.handle");
		log.info("id=" + id + "," + " removeId=" + removeId +
				", order=" + order + ", number=" + number);
		try{
			gameGiftIndexService.handle(id,removeId,order,number);
		}catch(Exception e){
			log.error("GameGiftIndexAction.handle failed, e : " + e);
		}
		return NONE;
	}
	
}

package com.org.mokey.basedata.sysinfo.action;

import java.util.HashMap;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.service.GameBoutiqueIndexService;
import com.org.mokey.common.AbstractAction;

/**
 * 游戏帮：精品游戏首页排版
 */
@SuppressWarnings("serial")
public class GameBoutiqueIndexAction extends AbstractAction{
	
	private GameBoutiqueIndexService gameBoutiqueIndexService;
	
	public GameBoutiqueIndexService getGameBoutiqueIndexService() {
		return gameBoutiqueIndexService;
	}

	public void setGameBoutiqueIndexService(GameBoutiqueIndexService gameBoutiqueIndexService) {
		this.gameBoutiqueIndexService = gameBoutiqueIndexService;
	}
	
	// 跳转到精品游戏首页排版页面
	public String gameBoutiqueIndex(){
		return "gameBoutiqueIndex";
	}
	
	// 分页查询首页中未显示的分类
	public String cateList(){
		// 获取page
		int page = getParameter2Int("page", 1);
		// 应用名称
		String name = getParameter("name");
		Map<String, Object> retmap = new HashMap<String, Object>();
		log.info("into GameBoutiqueIndexAction.cateList");
		log.info("page=" + page);
		try{
			retmap = gameBoutiqueIndexService.cateList(page,name);// 分页查询首页中未显示的分类
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameBoutiqueIndexAction.cateList failed, e : " + e);
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
		log.info("into GameBoutiqueIndexAction.indexCateList");
		log.info("page=" + page);
		try{
			retmap = gameBoutiqueIndexService.indexCateList(name, (page-1)*5, 5);
			retmap.put( "status", "Y" );
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put( "status", "N" );
			log.error("GameBoutiqueIndexAction.indexCateList failed, e : " + e);
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
		log.info("into GameBoutiqueIndexAction.handle");
		log.info("id=" + id + "," + " removeId=" + removeId +
				", order=" + order + ", number=" + number);
		try{
			gameBoutiqueIndexService.handle(id,removeId,order,number);
		}catch(Exception e){
			log.error("GameBoutiqueIndexAction.handle failed, e : " + e);
		}
		return NONE;
	}
	
}

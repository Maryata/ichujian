package com.org.mokey.basedata.sysinfo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.service.GameGiftCateService;
import com.org.mokey.basedata.sysinfo.service.GameGiftRecommendService;
import com.org.mokey.common.AbstractAction;

/**
 * 游戏帮：游戏礼包推荐
 */
@SuppressWarnings("serial")
public class GameGiftRecommendAction extends AbstractAction{
	
	private GameGiftRecommendService gameGiftRecommendService;
	
	private GameGiftCateService gameGiftCateService;
	
	public GameGiftCateService getGameGiftCateService() {
		return gameGiftCateService;
	}

	public void setGameGiftCateService(GameGiftCateService gameGiftCateService) {
		this.gameGiftCateService = gameGiftCateService;
	}

	public GameGiftRecommendService getGameGiftRecommendService() {
		return gameGiftRecommendService;
	}

	public void setGameGiftRecommendService(GameGiftRecommendService gameGiftRecommendService) {
		this.gameGiftRecommendService = gameGiftRecommendService;
	}

	// 跳转到礼包分类列表首页
	public String toGameGiftRecommend(){
		List<Map<String,Object>> list = gameGiftCateService.getGiftCateList();
		getRequest().setAttribute("giftCate", list);
		return "toGameGiftRecommend";
	}
	
	// 查询未作为推荐的礼包
	public String getAllGameGift(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		String name = getParameter("name");// 礼包名称
		String giftCate = getParameter("giftCate");// 礼包分类
		log.info("into GameGiftRecommendAction.getAllGameGift");
		log.info("page=" + page + ", name="
				+ name + ", giftCate=" + giftCate);
		try{
			// 分页查询
			retmap = gameGiftRecommendService.getAllGameGift(page, name, giftCate);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameGiftRecommendAction.getAllGameGift failed, e : " + e);
		}
		return NONE;
	}
	
	// 查询推荐的礼包
	public String recommendGiftList(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		String name = getParameter("name");// 礼包名称
		log.info("into GameGiftRecommendAction.recommendGiftList");
		log.info("page=" + page + ", name=" + name);
		try{
			// 分页查询
			retmap = gameGiftRecommendService.recommendGiftList(page, name);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameGiftRecommendAction.recommendGiftList failed, e : " + e);
		}
		return NONE;
	}
	
	// 推荐维护
	public String handle(){
		String gid = getRequest().getParameter("gid");// 获取应用id
		String removeGid = getRequest().getParameter("removeGid");// 获取要移除出当前分类的应用id
		String order = getRequest().getParameter("order");// 获取顺序
		log.info("into GameGiftRecommendAction.handle");
		log.info(" ---- gid=" + gid + " ---- removeGid=" + removeGid + " ---- order=" + order);
		try{
			// 维护
			gameGiftRecommendService.handle(gid,removeGid,order);
		}catch(Exception e){
			log.error("GameGiftRecommendAction.handle failed,",e);
		}
		return NONE;
	}
	
}

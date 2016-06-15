package com.org.mokey.basedata.sysinfo.action;

import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.service.GameCueService;
import com.org.mokey.common.AbstractAction;

/**
 * 游戏帮：游戏提示语
 */
@SuppressWarnings("serial")
public class GameCueAction extends AbstractAction{
	
	private GameCueService gameCueService;

	public GameCueService getGameCueService() {
		return gameCueService;
	}

	public void setGameCueService(GameCueService gameCueService) {
		this.gameCueService = gameCueService;
	}
	
	// 获取提示语列表
	public String gameCue(){
		log.info("into GameCueAction.gameCue");
		try{
			// 所有合集
			List<Map<String,Object>> list = gameCueService.gameCue();
			// 存入值栈
			getValueStack().set("gameCue", list);
		}catch(Exception e){
			log.error("GameCueAction.gameCue failed,",e);
		}
		return "gameCue";
	}
	
	// 新增提示语
	public String addCue(){
		// 获取请求参数
		String addTitle = getParameter("addTitle");
		log.info("into GameCueAction.addCue");
		log.info("addTitle=" + addTitle);
		try{
			// 添加提示语
			gameCueService.addCue(addTitle);
		}catch(Exception e){
			log.error("GameCueAction.addCue failed,",e);
		}
		return NONE;
	}
	
	// 修改提示语
	public String modifyCue(){
		// 获取请求参数
		String cid = getParameter("cid");// 获取提示id
		String title = getParameter("title"); // 获取提示内容
		String islive = getParameter("islive"); // 是否有效
		log.info("into GameCueAction.modifyCue");
		log.info("cid=" + cid + " ,title=" + title + " ,islive=" + islive);
		try{
			// 修改
			gameCueService.modifyCue(cid,title,islive);
		}catch(Exception e){
			log.error("GameCueAction.modifyCue failed,",e);
		}
		return NONE;
	}
	
	// 使合集不可用/可用
	public String isvalid(){
		// 获取提示id
		String cid = getParameter("cid");
		// 获取有效性
		String islive = getParameter("islive");
		log.info("into GameCueAction.isvalid");
		log.info("cid=" + cid + " ,islive=" + islive);
		try{
			// 失效
			gameCueService.isvalid(cid,islive);
		}catch(Exception e){
			log.error("GameCueAction.isvalid failed,",e);
		}
		return NONE;
	}
	
	// 删除提示内容
	public String deleteCue(){
		// 获取提示id
		String cid = getParameter("cid");
		log.info("into GameCueAction.deleteCue");
		log.info("cid=" + cid);
		try{
			// 删除
			gameCueService.deleteCol(cid);
		}catch(Exception e){
			log.error("GameCueAction.deleteCue failed,",e);
		}
		return NONE;
	}
	
}

package com.org.mokey.basedata.sysinfo.action;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.service.AppInfoService;
import com.org.mokey.basedata.sysinfo.service.GameActService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

/**
 * 游戏帮：游戏活动
 */
@SuppressWarnings("serial")
public class GameActAction extends AbstractAction{
	
	// 游戏活动
	private GameActService gameActService;
	
	// 游戏
	private AppInfoService appInfoService;

	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public GameActService getGameActService() {
		return gameActService;
	}

	public void setGameActService(GameActService gameActService) {
		this.gameActService = gameActService;
	}
	
	public AppInfoService getAppInfoService() {
		return appInfoService;
	}

	public void setAppInfoService(AppInfoService appInfoService) {
		this.appInfoService = appInfoService;
	}

	// 跳转到活动列表页面
	public String toActList(){
		log.info("into GameActAction.toActList");
		try {
			// 查询所有游戏
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> games = appInfoService.getGameAppInfoList();
			getRequest().setAttribute("games", games);
		} catch (Exception e) {
			log.error("GameActAction.toActList failed, e : " + e);
		}
		return "gameActList";
	}
	
	// 查询活动列表
	public String GameActList(){
		String gid = getParameter( "gid" );// 游戏id
		String name = getParameter( "name" );// 活动名
		String gName = getParameter( "gName" );// 游戏名
		String sPage = getParameter("page");// 获取page
		log.info("into GameActAction.GameActList");
		log.info("gid = " + gid + ", name = " + name + ", page = " + sPage);
		try {
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			List<Map<String,Object>> list = gameActService.gameActList(gid, name, page, gName);
			super.writeJSONToResponse(list);
		} catch (Exception e) {
			log.error("GameActAction.GameActList failed, e : " + e);
		}
		return NONE;
	}
	
	// 获取活动总数
	public String getTotal(){
		String gid = getParameter("gid");// 游戏id
		String gName = getParameter("gName");// 游戏名
		String name = getParameter("name");// 活动名
		log.info("into GameActAction.getTotalCol");
		log.info("gid = " + gid + ", name = " + name);
		try {
			// 总条数
			Integer total = gameActService.getTotal(gid, name, gName);
			// 计算总页数
			Integer rows = 10;// 每页10条
			Integer totalPage = (total-1)/rows + 1;
			// 回写查询结果
			super.writeJSONToResponse(totalPage);
		} catch (Exception e) {
			log.error("GameActAction.getTotal failed,",e);
		}
		return NONE;
	}
	
	// 根据id删除游戏活动
	public String deleteGameAct(){
		String id = getParameter("id");// 活动id
		log.info("into GameActAction.GameActAction");
		log.info("id = " + id);
		try {
			// 删除
			gameActService.deleteGameAct(id);
		} catch (Exception e) {
			log.error("GameCollectionAction.getTotal failed,",e);
		}
		return NONE;
	}
	
	// 跳转到活动添加页面
	public String toGameActAdd(){
		log.info("into GameActAction.toGameActAdd");
		try {
			// 查询所有游戏
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> games = appInfoService.getGameAppInfoList();
			String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_GAME_ACTIVITY_INFO");
			getRequest().setAttribute("games", games);
			getRequest().setAttribute("newId", newId);
			getRequest().setAttribute("flag", 1);
		} catch (Exception e) {
			log.error("GameActAction.toGameActAdd failed, e : " + e);
		}
		return "toGameActAdd";
	}
	// 跳转到活动编辑页面
	public String toGameActEdit(){
		String editId = getParameter("editId");
		log.info("into GameActAction.toGameActEdit");
		log.info("editId = " + editId);
		try {
			// 查询所有游戏
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> games = appInfoService.getGameAppInfoList();
			getRequest().setAttribute("games", games);
			// 根据id查询活动
			List<Map<String, Object>> list = gameActService.queryActById(editId);
			String editGid = "";
			String editOrder = "";
			String editName = "";
			String editDepict = "";
			String editLogourl = "";
			String sDate = "";
			String eDate = "";
			String reward = "";
			if(StrUtils.isNotEmpty(list)){
				editGid = (list.get(0).get("C_GID"))==null?"":list.get(0).get("C_GID").toString();
				editOrder = (list.get(0).get("C_ORDER"))==null?"":list.get(0).get("C_ORDER").toString();
				editName = (list.get(0).get("C_NAME"))==null?"":list.get(0).get("C_NAME").toString();
				editDepict = (list.get(0).get("C_DEPICT"))==null?"":list.get(0).get("C_DEPICT").toString();
				editLogourl = (list.get(0).get("C_LOGOURL"))==null?"":list.get(0).get("C_LOGOURL").toString();
				sDate = (list.get(0).get("C_SDATE"))==null?"":list.get(0).get("C_SDATE").toString();
				eDate = (list.get(0).get("C_EDATE"))==null?"":list.get(0).get("C_EDATE").toString();
				reward = (list.get(0).get("C_REWARD"))==null?"0":list.get(0).get("C_REWARD").toString();
			}
			getRequest().setAttribute("newId", editId);
			getRequest().setAttribute("editGid", editGid);
			getRequest().setAttribute("editOrder", editOrder);
			getRequest().setAttribute("editName", editName);
			getRequest().setAttribute("editDepict", editDepict);
			getRequest().setAttribute("logourl", editLogourl);
			getRequest().setAttribute("sDate", sDate.substring(0, sDate.lastIndexOf(".")));
			getRequest().setAttribute("eDate", eDate.substring(0, eDate.lastIndexOf(".")));
			getRequest().setAttribute("reward", reward);
		} catch (Exception e) {
			log.error("GameActAction.toGameActEdit failed, e : " + e);
		}
		return "toGameActAdd";
	}
	
	// 保存活动
	public String saveGameAct(){
		String id = getParameter("id");// 活动id
		String gid = getParameter("gid");// 游戏id
		String order = getParameter("order");// 游戏id
		String name = getParameter("name");// 活动标题
		String sDate = getParameter("sDate");// 活动开始时间
		String eDate = getParameter("eDate");// 活动结束时间
		String reward = getParameter("reward");// 活动奖励
		String depict = getParameter("depict");// 活动详情
		String logourl = getParameter("logourlScan");// 活动LOGO
		log.info("into GameActAction.saveGameAct");
		log.info("id = " + id + ", gid = " + gid + ", order = " + order + ", name = "
				+ name + ", depict = " + depict + ", logourl = " + logourl +
				", sDate = " + sDate + ", eDate = " + eDate + ", reward = " + reward);
		try {
			gameActService.saveGameAct(id, gid, order, name, depict, logourl, sDate, eDate, reward);
		} catch (Exception e) {
			log.error("GameActAction.saveGameAct failed, e : " + e);
		}
		return "saveGameAct";
	}

	public String users() {
		int aid = getParameter2Int( "aid", -1 );
		getRequest().setAttribute("aid",aid);
		return "users";
	}

	public String ajaxList() {
		log.info( "into ajaxList" );
		Map<String, Object> retMap = new HashMap<String, Object>();
		int start = getParameter2Int( "start", 0 );
		int limit = getParameter2Int( "limit", 10 );
        int aid = getParameter2Int( "aid", -1 );
		int flag = getParameter2Int( "flag", 0 );
		try {
			retMap = gameActService.listUser(  start, limit, aid,flag );
			retMap.put( "status", "Y" );
		} catch ( Exception e ) {
			retMap.put( "status", "N" );
			log.error( "ajaxList failed", e );
		}
		try {
			getResponse().setContentType( "text/html;charset=UTF-8" );
			getResponse().getWriter().write(
					JSONObject.fromObject( retMap ).toString() );
		} catch ( IOException e ) {
			log.error( "ajaxList failed", e );
		}

		return NONE;
	}

	public String ajaxSave() {
		log.info( "into ajaxSave" );
		Map<String, Object> map = new HashMap<String, Object>();

		Enumeration<String> keys = getRequest().getParameterNames();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			map.put( key, getRequest().getParameter( key ));
		}

		try {
			gameActService.updateUser(map);
			map.put( "status", "Y" );
			map.put( "success", true );
		} catch ( Exception ex ) {
			map.put( "status", "N" );
			log.error( "ajaxSave failed", ex );
		}

		try {
			getResponse().setContentType( "text/html;charset=UTF-8" );
			getResponse().getWriter().write(
					JSONObject.fromObject( map ).toString() );
		} catch ( IOException ex ) {
			log.error( "ajaxSave failed", ex );
		}

		return NONE;
	}
}

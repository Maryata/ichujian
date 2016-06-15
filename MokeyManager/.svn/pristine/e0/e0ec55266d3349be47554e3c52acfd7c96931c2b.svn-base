package com.org.mokey.basedata.sysinfo.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.baseinfo.common.HtmlUtil;
import com.org.mokey.common.util.str.StrUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.service.AppInfoService;
import com.org.mokey.basedata.sysinfo.service.GameStrategyService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

/**
 * 游戏帮：游戏攻略
 */
@SuppressWarnings("serial")
public class GameStrategyAction extends AbstractAction{
	
	// 游戏攻略
	private GameStrategyService gameStrategyService;
	
	// 游戏
	private AppInfoService appInfoService;

	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public GameStrategyService getGameStrategyService() {
		return gameStrategyService;
	}

	public void setGameStrategyService(GameStrategyService gameStrategyService) {
		this.gameStrategyService = gameStrategyService;
	}
	
	public AppInfoService getAppInfoService() {
		return appInfoService;
	}

	public void setAppInfoService(AppInfoService appInfoService) {
		this.appInfoService = appInfoService;
	}

	// 跳转到攻略列表页面
	public String toStrategyList(){
		log.info("into GameStrategyAction.toActList");
		try {
			// 查询所有游戏
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> games = appInfoService.getGameAppInfoList();
			getRequest().setAttribute("games", games);
		} catch (Exception e) {
			log.error("GameStrategyAction.toActList failed, e : " + e);
		}
		return "gameStrategyList";
	}
	
	// 查询攻略列表
	public String gameStrategyList(){
		String gid = getParameter( "gid" );// 游戏id
		String name = getParameter( "name" );// 攻略名
		String gName = getParameter( "gName" );// 游戏名
		String sPage = getParameter("page");// 获取page
		log.info("into GameStrategyAction.gameStrategyList");
		log.info("gid = " + gid + ", name = " + name + ", page = " + sPage);
		try {
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			List<Map<String,Object>> list = gameStrategyService.gameStrategyList(gid, name, page, gName);
			super.writeJSONToResponse(list);
		} catch (Exception e) {
			log.error("GameStrategyAction.gameStrategyList failed, e : " + e);
		}
		return NONE;
	}
	
	// 获取攻略总数
	public String getTotal(){
		String gid = getParameter("gid");// 游戏id
		String gName = getParameter("gName");// 游戏名
		String name = getParameter("name");// 攻略名
		log.info("into GameStrategyAction.getTotalCol");
		log.info("gid = " + gid + ", name = " + name);
		try {
			// 总条数
			Integer total = gameStrategyService.getTotal(gid, name, gName);
			// 计算总页数
			Integer rows = 10;// 每页10条
			Integer totalPage = (total-1)/rows + 1;
			// 回写查询结果
			super.writeJSONToResponse(totalPage);
		} catch (Exception e) {
			log.error("GameStrategyAction.getTotal failed,",e);
		}
		return NONE;
	}
	
	// 根据id删除游戏攻略
	public String deleteGameStrategy(){
		String id = getParameter("id");// 攻略id
		log.info("into GameStrategyAction.GameStrategyAction");
		log.info("id = " + id);
		try {
			// 删除
			gameStrategyService.deleteGameStrategy(id);
		} catch (Exception e) {
			log.error("GameCollectionAction.getTotal failed,",e);
		}
		return NONE;
	}
	
	// 跳转到攻略添加页面
	public String toGameStrategyAdd(){
		log.info("into GameStrategyAction.toGameStrategyAdd");
		try {
			// 查询所有游戏
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> games = appInfoService.getGameAppInfoList();
			String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_GAME_STRATEGY_INFO");
			getRequest().setAttribute("games", games);
			getRequest().setAttribute("newId", newId);
			getRequest().setAttribute("flag", 1);
		} catch (Exception e) {
			log.error("GameStrategyAction.toGameStrategyAdd failed, e : " + e);
		}
		return "toGameStrategyAdd";
	}
	// 跳转到攻略编辑页面
	public String toGameStrategyEdit(){
		String editId = getParameter("editId");
		log.info("into GameStrategyAction.toGameStrategyEdit");
		log.info("editId = " + editId);
		try {
			// 查询所有游戏
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> games = appInfoService.getGameAppInfoList();
			getRequest().setAttribute("games", games);
			// 根据id查询攻略
			List<Map<String, Object>> list = gameStrategyService.queryStrategyById(editId);
			String editGid = "";
			String editOrder = "";
			String editName = "";
			String editDepict = "";
			String editLogourl = "";
			if(StrUtils.isNotEmpty(list)){
				editGid = (list.get(0).get("C_GID"))==null?"":list.get(0).get("C_GID").toString();
				editOrder = (list.get(0).get("C_ORDER"))==null?"":list.get(0).get("C_ORDER").toString();
				editName = (list.get(0).get("C_NAME"))==null?"":list.get(0).get("C_NAME").toString();
				editDepict = (list.get(0).get("C_DEPICT"))==null?"":list.get(0).get("C_DEPICT").toString();
				editLogourl = (list.get(0).get("C_LOGOURL"))==null?"":list.get(0).get("C_LOGOURL").toString();
			}
			getRequest().setAttribute("newId", editId);
			getRequest().setAttribute("editGid", editGid);
			getRequest().setAttribute("editOrder", editOrder);
			getRequest().setAttribute("editName", editName);
			getRequest().setAttribute("editDepict", editDepict);
			getRequest().setAttribute("logourl", editLogourl);
		} catch (Exception e) {
			log.error("GameStrategyAction.toGameStrategyEdit failed, e : " + e);
		}
		return "toGameStrategyAdd";
	}
	
	// 保存攻略
	public String saveGameStrategy(){
		String id = getParameter("id");// 攻略id
		String gid = getParameter("gid");// 游戏id
		String order = getParameter("order");// 游戏id
		String name = getParameter("name");// 攻略标题
		String depict = getParameter("depict");// 攻略详情
		String logourl = getParameter("logourlScan");// 攻略LOGO
		log.info("into GameStrategyAction.saveGameStrategy");
		log.info("id = " + id + ", gid = " + gid + ", order = " + order + ", name = "
				+ name + ", depict = " + depict + ", logourl = " + logourl);
		try {
			gameStrategyService.saveGameStrategy(id, gid, order, name, depict, logourl);
		} catch (Exception e) {
			log.error("GameStrategyAction.saveGameStrategy failed, e : " + e);
		}
		return "saveGameStrategy";
	}
	
	// 查询所有攻略id和其对应的攻略内容（仅用于给所有图片设置尺寸）
	public String selectDepict(){
		try {
//			List<Map<String,Object>> list = gameStrategyService.selectDepict();
//			if (StrUtils.isNotEmpty(list)) {
//				for (Map<String, Object> map1 : list) {
//					String id = StrUtils.emptyOrString(map1.get("ID"));
//					String decipt = StrUtils.emptyOrString(map1.get("DECIPT"));
//					String name = StrUtils.emptyOrString(map1.get("C_NAME"));
//					Map<String, Object> map = new HashMap<String, Object>();
//					map.put("c_id", id);
//					map.put("c_name", name);
//					map.put("c_date", new Date());
//					map.put("c_depict", decipt);
//					// 生成HTML
//					HtmlUtil htmlUtil = new HtmlUtil();
//					htmlUtil.createGameHtmlV2("ek-gameStrategyShare", map);
//					break;
//				}
//			}
			//super.writeJSONToResponse(list);
		} catch (Exception e) {
			log.error("GameStrategyAction.selectDepict failed, e : " + e);
		}
		return NONE;
	}
	// 给所有图片设置尺寸
	public String setImgSize(){
		String id = getRequest().getParameter("id");
		String depict = getRequest().getParameter("depict");
		String flag = getRequest().getParameter("flag");
		try {
			int result = gameStrategyService.setImgSize(id,depict,flag);
			super.writeJSONToResponse(result);
			log.info("res:"+result);
		} catch (Exception e) {
			log.error("GameStrategyAction.setImgSize failed, e : " + e);
		}
		return NONE;
	}
}

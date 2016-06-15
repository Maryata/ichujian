package com.org.mokey.basedata.sysinfo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.service.GameCategoryService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;

/**
 * 游戏帮：游戏分类
 */
@SuppressWarnings("serial")
public class GameCategoryAction extends AbstractAction{
	
	private GameCategoryService gameCategoryService;

	private String out;
	
	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
			
	private JdbcTemplate jdbcTemplate;

	public GameCategoryService getGameCategoryService() {
		return gameCategoryService;
	}

	public void setGameCategoryService(GameCategoryService gameCategoryService) {
		this.gameCategoryService = gameCategoryService;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	// 获取游戏分类（下拉菜单）
	public String getGameCategoryList(){
		Map<String,Object> retMap = new HashMap<String,Object>();
		log.info("into GameCategoryAction.getGameCategoryList");
		try{
			List<Map<String, Object>> list = gameCategoryService.getGameCategoryList();
			
			retMap.put("list", list);
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getAppIfoListByType failed");
			log.error("getGameCategoryList failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.info("out GameCategoryAction.getGameCategoryList");
		return SUCCESS;
	}
	
	// 跳转到游戏分类首页
	public String toGameCategoryList(){
		return "toGameCategoryList";
	}
	
	// 首页显示的列表信息
	public String gameCategoryList(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		log.info("into GameCategoryAction.gameCategoryList");
		log.info("page=" + page);
		try{
			// 分页显示游戏分类
			retmap = gameCategoryService.gameCategoryList(page);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameCategoryAction.gameCategoryList failed, e : " + e);
		}
		return NONE;
	}
	
	// 跳转到添加页面
	public String toCateAdd(){
		log.info("into GameCategoryAction.toCateAdd");
		try{
			// 获取主键
			String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_GAME_APP_CATEGORY");
			getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
			getRequest().setAttribute("cid", newId);
		}catch(Exception e){
			log.error("GameCategoryAction.toCateAdd failed,",e);
		}
		return "toUpdate";
	}
	
	// 新增分类
	public String addGameCate(){
		String id = getParameter("cid");// 获取cid
		String name = getParameter("cname");// 分类名称
		String order = getParameter("order");// 排序
		String logo = getParameter("logo");// logo
		log.info("into GameCategoryAction.addGameCate");
		log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
		try{
			// 获取当前登录人
			String modifier = super.getSessionLoginUser().getUserName();
			// 新增
			gameCategoryService.addGameCate(id, name, logo, order, modifier);
			}catch(Exception e){
				log.error("GameCategoryAction.addGameCate failed,",e);
			}
			return "reload";
		}
		
	// 删除分类
	public String delGameCate(){
		// 获取cid
		String id = getParameter("cid");
		log.info("into GameCategoryAction.delGameCate");
		log.info("id=" + id);
		try{
			// 删除
			gameCategoryService.delGameCate(id);
		}catch(Exception e){
			log.error("GameCategoryAction.delGameCate failed,",e);
		}
		return NONE;
	}
	
	// 跳转到分类修改页面
	public String toUpdate(){
		// 获取请求参数
		String id = getParameter("editId");
		String name = getParameter("editName");
		String order = getParameter("editOrder");
		String logo = getParameter("editLogo");
		log.info("into GameCategoryAction.AddGameCate");
		log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
		try{
			getRequest().setAttribute("cid", id);
			getRequest().setAttribute("cname", name);
			getRequest().setAttribute("order", order);
			getRequest().setAttribute("logo", logo);
		}catch(Exception e){
			log.error("GameCategoryAction.toUpdate failed,",e);
		}
		return "toUpdate";
	}
	
	// 更新分类
	public String updateGameCate(){
		// 获取请求参数
		String id = getParameter("cid");
		String name = getParameter("cname");
		String order = getParameter("order");
		String logo = getParameter("logo");
		log.info("into GameCategoryAction.updateGameCate");
		log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
		try{
			// 获取当前登录人
			String modifier = super.getSessionLoginUser().getUserName();
			// 更新
			gameCategoryService.updateGameCate(id, name, logo, order, modifier);
		}catch(Exception e){
			log.error("GameCategoryAction.toUpdate failed,",e);
		}
		return "reload";
	}
	
	// 跳转到分类修改页面
	public String toHandle(){
		// 获取请求参数
		String id = getParameter("editId");// 游戏id
		String name = getParameter("editName");// 游戏名称
		log.info("into GameCategoryAction.AddGameCate");
		log.info("id=" + id + ", name=" + name);
		try{
			getRequest().setAttribute("cid", id);// 分类id
			getRequest().setAttribute("cname", name);
			// 所有游戏分类
			getRequest().setAttribute("gameCate", gameCategoryService.getGameCategoryList());
		}catch(Exception e){
			log.error("GameCategoryAction.toUpdate failed,",e);
		}
		return "toHandle";
	}
	
	// 查询非当前游戏分类的其他所有游戏
	public String getAllGame(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		String cid = getParameter("cid");// 当前分类id
		String name = getParameter("name");// 游戏名称
		String gameCate = getParameter("gameCate");// 游戏初始分类
		log.info("into GameCategoryAction.getAllGame");
		log.info("page=" + page + ", cid=" + cid + ", name="
				+ name + ", gameCate=" + gameCate);
		try{
			// 分页查询
			retmap = gameCategoryService.getAllGame(page, cid, name, gameCate);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameCategoryAction.getAllGame failed, e : " + e);
		}
		return NONE;
	}
	
	// 查询当前游戏分类的游戏
	public String getCurrCateGame(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		String cid = getParameter("cid");// 当前分类id
		String name = getParameter("name");// 游戏名称
		log.info("into GameCategoryAction.getCurrCateGame");
		log.info("page=" + page + ", cid=" + cid + ", name=" + name);
		try{
			// 分页查询
			retmap = gameCategoryService.getCurrCateGame(page, cid, name);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameCategoryAction.getCurrCateGame failed, e : " + e);
		}
		return NONE;
	}
	
	// 分类维护
	public String handleCate(){
		// 获取请求参数
		String cid = getParameter("cid");// 获取分类id
		String gid = getRequest().getParameter("gid");// 获取应用id
		String removeGid = getRequest().getParameter("removeGid");// 获取要移除出当前分类的应用id
		String order = getRequest().getParameter("order");// 获取顺序
		log.info("into GameCategoryAction.handleCate");
		log.info("cid=" + cid + " ---- gid=" + gid + " ---- removeGid=" + removeGid + " ---- order=" + order);
		try{
			// 维护
			gameCategoryService.handleCate(cid,gid,removeGid,order);
		}catch(Exception e){
			log.error("GameCategoryAction.handleCate failed,",e);
		}
		return NONE;
	}
	
}

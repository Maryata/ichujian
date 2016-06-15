package com.org.mokey.basedata.sysinfo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.service.GameBoutiqueCategoryService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;

/**
 * 游戏帮：游戏精品分类
 */
public class GameBoutiqueCategoryAction extends AbstractAction{
	private static final long serialVersionUID = -2820446070914651473L;

	private GameBoutiqueCategoryService gameBoutiqueCategoryService;

	private String out;
	
	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
			
	private JdbcTemplate jdbcTemplate;

	public GameBoutiqueCategoryService getGameBoutiqueCategoryService() {
		return gameBoutiqueCategoryService;
	}

	public void setGameBoutiqueCategoryService(GameBoutiqueCategoryService gameBoutiqueCategoryService) {
		this.gameBoutiqueCategoryService = gameBoutiqueCategoryService;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	// 获取游戏分类（下拉菜单）
	public String getGameBoutiqueCategoryList(){
		Map<String,Object> retMap = new HashMap<String,Object>();
		log.info("into GameBoutiqueCategoryAction.getGameBoutiqueCategoryList");
		try{
			List<Map<String, Object>> list = gameBoutiqueCategoryService.getGameBoutiqueCategoryList();
			
			retMap.put("list", list);
			retMap.put("status", "Y");
		}catch(Exception e){
			retMap.put("status", "N");
			retMap.put("info", "getAppIfoListByType failed");
			log.error("getGameBoutiqueCategoryList failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.info("out GameBoutiqueCategoryAction.getGameBoutiqueCategoryList");
		return SUCCESS;
	}
	
	// 跳转到游戏分类首页
	public String toGameBoutiqueCategoryList(){
		return "toGameBoutiqueCategoryList";
	}
	
	// 首页显示的列表信息
	public String gameBoutiqueCategoryList(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		log.info("into GameBoutiqueCategoryAction.gameBoutiqueCategoryList");
		log.info("page=" + page);
		try{
			// 分页显示游戏分类
			retmap = gameBoutiqueCategoryService.gameBoutiqueCategoryList(page);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameBoutiqueCategoryAction.gameBoutiqueCategoryList failed, e : " + e);
		}
		return NONE;
	}
	
	// 跳转到添加页面
	public String toCateAdd(){
		log.info("into GameBoutiqueCategoryAction.toCateAdd");
		try{
			// 获取主键
			String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_GAME_APP_CATEGORY");
			getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
			getRequest().setAttribute("cid", newId);
		}catch(Exception e){
			log.error("GameBoutiqueCategoryAction.toCateAdd failed,",e);
		}
		return "toUpdate";
	}
	
	// 新增分类
	public String addGameBoutiqueCate(){
		String id = getParameter("cid");// 获取cid
		String name = getParameter("cname");// 分类名称
		String order = getParameter("order");// 排序
		String logo = getParameter("logo");// logo
		log.info("into GameBoutiqueCategoryAction.addGameBoutiqueCate");
		log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
		try{
			// 获取当前登录人
			String modifier = super.getSessionLoginUser().getUserName();
			// 新增
			gameBoutiqueCategoryService.addGameBoutiqueCate(id, name, logo, order, modifier);
			}catch(Exception e){
				log.error("GameBoutiqueCategoryAction.addGameBoutiqueCate failed,",e);
			}
			return "reload";
		}
		
	// 删除分类
	public String delGameBoutiqueCategory(){
		// 获取cid
		String id = getParameter("cid");
		log.info("into GameBoutiqueCategoryAction.delGameBoutiqueCate");
		log.info("id=" + id);
		try{
			// 删除
			gameBoutiqueCategoryService.delGameBoutiqueCate(id);
		}catch(Exception e){
			log.error("GameBoutiqueCategoryAction.delGameBoutiqueCate failed,",e);
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
		log.info("into GameBoutiqueCategoryAction.AddGameCate");
		log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
		try{
			getRequest().setAttribute("cid", id);
			getRequest().setAttribute("cname", name);
			getRequest().setAttribute("order", order);
			getRequest().setAttribute("logo", logo);
		}catch(Exception e){
			log.error("GameBoutiqueCategoryAction.toUpdate failed,",e);
		}
		return "toUpdate";
	}
	
	// 更新分类
	public String updateGameBoutiqueCate(){
		// 获取请求参数
		String id = getParameter("cid");
		String name = getParameter("cname");
		String order = getParameter("order");
		String logo = getParameter("logo");
		log.info("into GameBoutiqueCategoryAction.updateGameCate");
		log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
		try{
			// 获取当前登录人
			String modifier = super.getSessionLoginUser().getUserName();
			// 更新
			gameBoutiqueCategoryService.updateGameBoutiqueCate(id, name, logo, order, modifier);
		}catch(Exception e){
			log.error("GameBoutiqueCategoryAction.toUpdate failed,",e);
		}
		return "reload";
	}
	
	// 跳转到分类修改页面
	public String toHandle(){
		// 获取请求参数
		String id = getParameter("editId");// 游戏id
		String name = getParameter("editName");// 游戏名称
		log.info("into GameBoutiqueCategoryAction.AddGameCate");
		log.info("id=" + id + ", name=" + name);
		try{
			getRequest().setAttribute("cid", id);// 分类id
			getRequest().setAttribute("cname", name);
			// 所有游戏分类
			getRequest().setAttribute("gameCate", gameBoutiqueCategoryService.getGameBoutiqueCategoryList());
		}catch(Exception e){
			log.error("GameBoutiqueCategoryAction.toUpdate failed,",e);
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
		log.info("into GameBoutiqueCategoryAction.getAllGame");
		log.info("page=" + page + ", cid=" + cid + ", name="
				+ name + ", gameCate=" + gameCate);
		try{
			// 分页查询
			retmap = gameBoutiqueCategoryService.getAllGame(page, cid, name, gameCate);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameBoutiqueCategoryAction.getAllGame failed, e : " + e);
		}
		return NONE;
	}
	
	// 查询当前游戏分类的游戏
	public String getCurrCateGame(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		String cid = getParameter("cid");// 当前分类id
		String name = getParameter("name");// 游戏名称
		log.info("into GameBoutiqueCategoryAction.getCurrCateGame");
		log.info("page=" + page + ", cid=" + cid + ", name=" + name);
		try{
			// 分页查询
			retmap = gameBoutiqueCategoryService.getCurrCateGame(page, cid, name);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameBoutiqueCategoryAction.getCurrCateGame failed, e : " + e);
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
		log.info("into GameBoutiqueCategoryAction.handleCate");
		log.info("cid=" + cid + " ---- gid=" + gid + " ---- removeGid=" + removeGid + " ---- order=" + order);
		try{
			// 维护
			gameBoutiqueCategoryService.handleCate(cid,gid,removeGid,order);
		}catch(Exception e){
			log.error("GameBoutiqueCategoryAction.handleCate failed,",e);
		}
		return NONE;
	}
	
}

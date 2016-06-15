package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import com.org.mokey.basedata.sysinfo.service.EKH5GameCategoryService;

import java.util.HashMap;
import java.util.Map;

/**
 * 游戏帮：h5游戏分类
 */
@SuppressWarnings("serial")
@Controller("eKH5GameCategoryAction")
public class EKH5GameCategoryAction extends AbstractAction{
	
	@Autowired
	private EKH5GameCategoryService eKH5GameCategoryService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 跳转到游戏分类首页
	public String toH5GameCategoryList(){
		return "toH5GameCategoryList";
	}
	
	// 首页显示的列表信息
	public String gameCategoryList(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		log.info("into EKH5GameCategoryAction.gameCategoryList");
		log.info("page=" + page);
		try{
			// 分页显示游戏分类
			retmap = eKH5GameCategoryService.gameCategoryList(page);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("EKH5GameCategoryAction.gameCategoryList failed, e : " + e);
		}
		return NONE;
	}

	// 跳转到添加页面
	public String toCateAdd(){
		log.info("into EKH5GameCategoryAction.toCateAdd");
		try{
			// 获取主键
			String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_GAME_APP_CATEGORY");
			getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
			getRequest().setAttribute("cid", newId);
		}catch(Exception e){
			log.error("EKH5GameCategoryAction.toCateAdd failed,",e);
		}
		return "toUpdate";
	}
	
	// 新增分类
	public String addGameCate(){
		String id = getParameter("cid");// 获取cid
		String name = getParameter("cname");// 分类名称
		String order = getParameter("order");// 排序
		String logo = getParameter("logo");// logo
		log.info("into EKH5GameCategoryAction.addGameCate");
		log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
		try{
			// 获取当前登录人
			String modifier = super.getSessionLoginUser().getUserName();
			// 新增
			eKH5GameCategoryService.addGameCate(id, name, logo, order, modifier);
			}catch(Exception e){
				log.error("EKH5GameCategoryAction.addGameCate failed,",e);
			}
			return "reload";
		}
		
	// 删除分类
	public String delGameCate(){
		// 获取cid
		String id = getParameter("cid");
		log.info("into EKH5GameCategoryAction.delGameCate");
		log.info("id=" + id);
		try{
			// 删除
			eKH5GameCategoryService.delGameCate(id);
		}catch(Exception e){
			log.error("EKH5GameCategoryAction.delGameCate failed,",e);
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
		log.info("into EKH5GameCategoryAction.AddGameCate");
		log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
		try{
			getRequest().setAttribute("cid", id);
			getRequest().setAttribute("cname", name);
			getRequest().setAttribute("order", order);
			getRequest().setAttribute("logo", logo);
		}catch(Exception e){
			log.error("EKH5GameCategoryAction.toUpdate failed,",e);
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
		log.info("into EKH5GameCategoryAction.updateGameCate");
		log.info("id=" + id + ", name=" + name + ", order=" + order + ", logo=" + logo);
		try{
			// 获取当前登录人
			String modifier = super.getSessionLoginUser().getUserName();
			// 更新
			eKH5GameCategoryService.updateGameCate(id, name, logo, order, modifier);
		}catch(Exception e){
			log.error("EKH5GameCategoryAction.toUpdate failed,",e);
		}
		return "reload";
	}
	
	// 跳转到分类修改页面
	public String toHandle(){
		// 获取请求参数
		String id = getParameter("editId");// 游戏id
		String name = getParameter("editName");// 游戏名称
		log.info("into EKH5GameCategoryAction.AddGameCate");
		log.info("id=" + id + ", name=" + name);
		try{
			getRequest().setAttribute("cid", id);// 分类id
			getRequest().setAttribute("cname", name);
			// 所有游戏分类
			//getRequest().setAttribute("gameCate", eKH5GameCategoryService.getGameCategoryList());
		}catch(Exception e){
			log.error("EKH5GameCategoryAction.toUpdate failed,",e);
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
		log.info("into EKH5GameCategoryAction.getAllGame");
		log.info("page=" + page + ", cid=" + cid + ", name="
				+ name + ", gameCate=" + gameCate);
		try{
			// 分页查询
			retmap = eKH5GameCategoryService.getAllGame(page, cid, name, gameCate);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("EKH5GameCategoryAction.getAllGame failed, e : " + e);
		}
		return NONE;
	}
	
	// 查询当前游戏分类的游戏
	public String getCurrCateGame(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		String cid = getParameter("cid");// 当前分类id
		String name = getParameter("name");// 游戏名称
		log.info("into EKH5GameCategoryAction.getCurrCateGame");
		log.info("page=" + page + ", cid=" + cid + ", name=" + name);
		try{
			// 分页查询
			retmap = eKH5GameCategoryService.getCurrCateGame(page, cid, name);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("EKH5GameCategoryAction.getCurrCateGame failed, e : " + e);
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
		log.info("into EKH5GameCategoryAction.handleCate");
		log.info("cid=" + cid + " ---- gid=" + gid + " ---- removeGid=" + removeGid + " ---- order=" + order);
		try{
			// 维护
			eKH5GameCategoryService.handleCate(cid,gid,removeGid,order);
		}catch(Exception e){
			log.error("EKH5GameCategoryAction.handleCate failed,",e);
		}
		return NONE;
	}
	
}

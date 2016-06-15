package com.org.mokey.basedata.sysinfo.action;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.org.mokey.basedata.sysinfo.service.GameTaskService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.util.Cn2Spell;

/**
 * 任务维护
 * @author vpc
 */
public class GameTaskAction extends AbstractAction {
	private static final long serialVersionUID = 4669469491882423171L;
	private GameTaskService gameTaskService;

	public String ajaxList() {
		log.info( "into ajaxList" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String name = getParameter( "c_name" );
		int start = getParameter2Int( "start", 0 );
		int limit = getParameter2Int( "limit", 10 );
		try {
			retmap = gameTaskService.list( name, start, limit );
			retmap.put( "status", "Y" );
		} catch ( Exception e ) {
			retmap.put( "status", "N" );
			log.error( "ajaxList failed", e );
		}
		try {
			getResponse().setContentType( "text/html;charset=UTF-8" );
			getResponse().getWriter().write(
					JSONObject.fromObject( retmap ).toString() );
		} catch ( IOException e ) {
			log.error( "ajaxList failed", e );
		}

		return NONE;
	}
	
	@SuppressWarnings("unchecked")
	public String ajaxSave() {
		log.info( "into ajaxSave" );
		Map<String, Object> map = new HashMap<String, Object>();
		MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) getRequest();
		Enumeration<String> fileParameterNames = multiWrapper.getFileParameterNames();
		Enumeration<String> keys = getRequest().getParameterNames();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			map.put( key, getRequest().getParameter( key ));
		}
		
		String currentTimeMillis = "_" + System.currentTimeMillis();
		while ( fileParameterNames.hasMoreElements() ) {
			String fieldName = fileParameterNames.nextElement();
			String[] filenames = multiWrapper.getFileNames( fieldName );
			File[] files = multiWrapper.getFiles( fieldName );
			
			for ( int i = 0; i < files.length; ++i ) {
				String filename = filenames[i];
				filename = filename.substring( 0, filename.lastIndexOf( '.' ) ) + currentTimeMillis + filename.substring( filename.lastIndexOf( '.' ) );
				map
						.put(
								fieldName,
								FileServices
										.saveFile(
												files[i],
												"game/task/logo/"+ Cn2Spell
																.converterToSpellTrim( filename ) ) ); // uri
			}
		}
		;

		try {
			gameTaskService.save(map);
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
	
	public String ajaxDelete() {
		log.info( "into ajaxDelete" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String id = getParameter( "c_id" );
		try {
			gameTaskService.delete(id);
			
			retmap.put( "status", "Y" );
		} catch ( Exception e ) {
			retmap.put( "status", "N" );
			log.error( "ajaxDelete failed", e );
		}

		try {
			getResponse().setContentType( "text/html;charset=UTF-8" );
			getResponse().getWriter().write(
					JSONObject.fromObject( retmap ).toString() );
		} catch ( IOException ex ) {
			log.error( "ajaxDelete failed", ex );
		}

		return NONE;
	}

	// 跳转到签到奖励页面
	public String toReward(){
		try {
			List<Map<String, Object>> rewards = gameTaskService.getAllRewards();
			getRequest().setAttribute("rewards", rewards);
		} catch (Exception ex) {
			log.error("toReward failed", ex);
		}
		return "toReward";
	}
	
	// 新增签到奖励
	public String addReward(){
		String days = getParameter("days");
		String score = getParameter("score");
		log.info("into addReward");
		log.info("days = " + days + ", score = " + score);
		try {
			gameTaskService.addReward(days, score);
		} catch (Exception ex) {
			log.error("addReward failed", ex);
		}
		return NONE;
	}
	
	// 删除签到奖励
	public String delReward(){
		String id = getParameter("id");
		log.info("into toHandle");
		log.info("id = " + id);
		try {
			gameTaskService.delReward(id);
		} catch (Exception ex) {
			log.error("delReward failed", ex);
		}
		return NONE;
	}
	
	// 跳转到修改签到奖励页面
	public String toHandle(){
		String id = getParameter("editId");
		String days = getParameter("editDays");
		String score = getParameter("editScore");
		log.info("into toHandle");
		log.info("id = " + id + ", days = " + days + ", score = " + score);
		try {
			getRequest().setAttribute("id", id);
			getRequest().setAttribute("days", days);
			getRequest().setAttribute("score", score);
		} catch (Exception ex) {
			log.error("toHandle failed", ex);
		}
		return "toHandle";
	}
	
	// 修改奖励
	public String handleReward(){
		String id = getParameter("id");
		String days = getParameter("days");
		String score = getParameter("score");
		log.info("into handleReward");
		log.info("id = " + id + ", days = " + days + ", score = " + score);
		try {
			gameTaskService.handleReward(id, days, score);
		} catch (Exception ex) {
			log.error("handleReward failed", ex);
		}
		return "handleReward";
	}
	
	public GameTaskService getGameTaskService () {
		return gameTaskService;
	}

	public void setGameTaskService (GameTaskService gameTaskService) {
		this.gameTaskService = gameTaskService;
	}
}
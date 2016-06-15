package com.org.mokey.basedata.sysinfo.action;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.org.mokey.basedata.sysinfo.service.H5GameService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.util.Cn2Spell;

/**
 * H5游戏管理
 * @author vpc
 */
public class H5GameAction extends AbstractAction {
	private static final long serialVersionUID = 4669469491882423171L;
	private H5GameService h5GameService;

	public String ajaxList() {
		log.info( "into ajaxList" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String name = getParameter( "c_name" );
		int start = getParameter2Int( "start", 0 );
		int limit = getParameter2Int( "limit", 10 );
		try {
			retmap = h5GameService.list( name, start, limit );
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
												"game/h5/logo/"+ Cn2Spell
																.converterToSpellTrim( filename ) ) ); // uri
			}
		}
		;

		try {
			h5GameService.save( map );
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
			h5GameService.delete( id );
			
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
	
	public String ajaxCheck() {
		Map<String, Object> retmap = new HashMap<String, Object>();
		String jarname = getParameter( "C_JARNAME" );
		String id = getParameter( "C_ID" );
		String result = "false"; // 默认是存在的
		try {
			if ( id.isEmpty() ) {
				retmap = h5GameService.checkJarname( jarname );
			} else {
				retmap = h5GameService.checkJarname( jarname, id );
			}
		} catch ( Exception e ) {
			log.error( "ajaxCheckCode failed", e );
		}

		if ( retmap.size() != 0 ) {
			String isExits = String.valueOf( retmap.get( "isExits" ) );
			if ( "0".equals( isExits ) ) { // 到查询正确返回，无此记录，返回不存在信息
				result = "true";
			}
		}

		try {
			getResponse().getWriter().write( result );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return NONE;
	}

	public H5GameService getH5GameService() {
		return h5GameService;
	}

	public void setH5GameService(H5GameService h5GameService) {
		this.h5GameService = h5GameService;
	}

}
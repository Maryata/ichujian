package com.org.mokey.basedata.sysinfo.action;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.org.mokey.basedata.sysinfo.service.McrAppService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.util.Cn2Spell;

/**
 * 微用帮
 */
public class McrAppAction extends AbstractAction {
	private static final long serialVersionUID = -7183467398973045476L;
	private McrAppService mcrAppService;
	
	public String ajaxList() {
		log.info( "into ajaxList" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String name = getParameter( "c_name" );
		int start = getParameter2Int( "start", 0 );
		int limit = getParameter2Int( "limit", 10 );
		try {
			retmap = mcrAppService.list( name, start, limit );
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
	
	public String ajaxDelete() {
		log.info( "into ajaxDelete" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String id = getParameter( "c_id" );
		try {
			mcrAppService.delete( id );
			
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
												"mcrapp/logo/"+ Cn2Spell
																.converterToSpellTrim( filename ) ) ); // uri
			}
		}
		;

		map.put( "C_MODIFIER", getSessionLoginUser().getUserName() );
		map.put( "C_NAME_CN", Cn2Spell.converterToSpellTrim( String.valueOf( map.get( "C_NAME" ).toString().charAt( 0 ) ) ) );
		
		try {
			mcrAppService.save( map );
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

	public McrAppService getMcrAppService() {
		return mcrAppService;
	}

	public void setMcrAppService(McrAppService mcrAppService) {
		this.mcrAppService = mcrAppService;
	}
	
}

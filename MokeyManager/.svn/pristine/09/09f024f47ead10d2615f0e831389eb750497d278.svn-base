package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.GameTaskService;
import com.org.mokey.basedata.sysinfo.service.MallProductService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.util.Cn2Spell;
import net.sf.json.JSONObject;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品维护
 * @author vpc
 */
public class MallProductAction extends AbstractAction {
	private static final long serialVersionUID = 4669469491882423171L;
	private MallProductService mallProductService;

	public String ajaxList() {
		log.info( "into ajaxList" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String name = getParameter( "c_name" );
		int start = getParameter2Int( "start", 0 );
		int limit = getParameter2Int( "limit", 10 );
		try {
			retmap = mallProductService.list( name, start, limit );
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
												"game/mallProduct/img/"+ Cn2Spell
																.converterToSpellTrim( filename ) ) ); // uri
			}
		}
		;

		try {
			mallProductService.save(map);
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
			mallProductService.delete(id);
			
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

	public String ajaxListGame() {
		log.info( "into ajaxListGame" );
		Map<String, Object> map = new HashMap<String, Object>();

		int start = getParameter2Int( "start", 0 );
		int limit = getParameter2Int( "limit", 1000 );
		try {
			map = mallProductService.listGame(start, limit);
			map.put("status", "Y");
		} catch ( Exception e ) {
			map.put("status", "N");
			log.error( "ajaxListGame failed", e );
		}
		try {
			getResponse().setContentType( "text/html;charset=UTF-8" );
			getResponse().getWriter().write(
					JSONObject.fromObject( map ).toString() );
		} catch ( IOException e ) {
			log.error( "ajaxListGame failed", e );
		}

		return NONE;
	}

	public MallProductService getMallProductService () {
		return mallProductService;
	}

	public void setMallProductService (MallProductService mallProductService) {
		this.mallProductService = mallProductService;
	}
}
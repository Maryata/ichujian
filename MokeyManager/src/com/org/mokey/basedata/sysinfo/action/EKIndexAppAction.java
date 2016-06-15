package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKIndexAppService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.util.Cn2Spell;
import net.sf.json.JSONObject;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 留言维护
 * @author vpc
 */
@Controller("eKIndexAppAction")
public class EKIndexAppAction extends AbstractAction {

	@Autowired
	private EKIndexAppService ekIndexAppService;

	public String ajaxList() {
		log.info( "异步加载首页app..." );
		Map<String, Object> retMap = new HashMap<String, Object>();
		String name = getParameter( "c_name" );
		String isLive = getParameter("c_isLive");
		int start = getParameter2Int( "start", 0 );
		int limit = getParameter2Int( "limit", 10 );
		try {
			retMap = ekIndexAppService.list( name, start, limit,isLive );
			retMap.put( "status", "Y" );
		} catch ( Exception e ) {
			retMap.put( "status", "N" );
			log.error( "加载app出错", e );
		}
		try {
			getResponse().setContentType( "text/html;charset=UTF-8" );
			getResponse().getWriter().write(
					JSONObject.fromObject( retMap ).toString() );
		} catch ( IOException e ) {
			log.error( "回写app数据出错", e );
		}

		return NONE;
	}

	public String ajaxSave() {
		log.info( "保存app开始..." );

		Map<String, Object> map = new HashMap<String, Object>();
		MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) getRequest();
		Enumeration<String> fileParameterNames = multiWrapper.getFileParameterNames();
		Enumeration<String> keys = getRequest().getParameterNames();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			map.put( key, getRequest().getParameter( key ));

			if("C_URL".equals(key) &&  StringUtils.isEmpty(map.get(key))) {
				map.put(key,"www.ichujian.com");
			}
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
												"ek-index/app/logo/"+ Cn2Spell
														.converterToSpellTrim( filename ) ) ); // uri
			}
		}
		;
		map.put("c_creater",getSessionLoginUser().getUserName());
		try {
			ekIndexAppService.save(map);
			map.put( "status", "Y" );
			map.put( "success", true );
		} catch ( Exception ex ) {
			map.put( "status", "N" );
			log.error( "app保存失败", ex );
		}

		try {
			getResponse().setContentType( "text/html;charset=UTF-8" );
			getResponse().getWriter().write(
					JSONObject.fromObject( map ).toString() );
		} catch ( IOException ex ) {
			log.error( "回写数据出错", ex );
		}

		return NONE;
	}
	
	public String ajaxDelete() {
		log.info( "删除app开始..." );
		Map<String, Object> retMap = new HashMap<String, Object>();
		String id = getParameter( "c_id" );
		try {
			ekIndexAppService.delete(id);
			
			retMap.put( "status", "Y" );
		} catch ( Exception e ) {
			retMap.put( "status", "N" );
			log.error( "删除数据出错", e );
		}

		try {
			getResponse().setContentType( "text/html;charset=UTF-8" );
			getResponse().getWriter().write(
					JSONObject.fromObject( retMap ).toString() );
		} catch ( IOException ex ) {
			log.error( "回写删除标识出错", ex );
		}

		return NONE;
	}
}
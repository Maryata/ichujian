package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.MallProductService;
import com.org.mokey.basedata.sysinfo.service.MessageService;
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
 * 留言维护
 * @author vpc
 */
public class MessageAction extends AbstractAction {
	private static final long serialVersionUID = 4669469491882423171L;
	private MessageService messageService;

	public String ajaxList() {
		log.info( "into ajaxList" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String name = getParameter( "c_name" );
		int start = getParameter2Int( "start", 0 );
		int limit = getParameter2Int( "limit", 10 );
		try {
			retmap = messageService.list( name, start, limit );
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

		Enumeration<String> keys = getRequest().getParameterNames();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			map.put( key, getRequest().getParameter( key ));
		}

		try {
			messageService.save(map);
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
			messageService.delete(id);
			
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

	public String toEdit() {
		String sUid = getParameter("uid");
		int start = 0, limit = 10;

		try {
			getRequest().setAttribute("message",messageService.listByUid(sUid, start, limit));
			getRequest().setAttribute("uid",sUid);
		} catch (Exception e) {
			log.error(e);
		}

		return INPUT;
	}

	public String ajaxListByUid() {
		log.info( "into ajaxListByUid" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String sUid = getParameter( "uid" );
		int start = getParameter2Int( "start", 0 );
		int limit = getParameter2Int( "limit", 10 );
		try {
			retmap = messageService.listByUid(sUid, start, limit );
			retmap.put( "status", "Y" );
		} catch ( Exception e ) {
			retmap.put( "status", "N" );
			log.error( "ajaxListByUid failed", e );
		}
		try {
			getResponse().setContentType( "text/html;charset=UTF-8" );
			getResponse().getWriter().write(
					JSONObject.fromObject( retmap ).toString() );
		} catch ( IOException e ) {
			log.error( "ajaxListByUid failed", e );
		}

		return NONE;
	}

	public MessageService getMessageService () {
		return messageService;
	}

	public void setMessageService (MessageService messageService) {
		this.messageService = messageService;
	}
}
package com.org.mokey.basedata.sysinfo.action;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.org.mokey.basedata.sysinfo.service.SupplierService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.util.Cn2Spell;
import com.org.mokey.util.JSONUtil;

/**
 * 代理商管理
 * 
 * @author lenovo
 */
public class SupplierAction extends AbstractAction {
	private static final long serialVersionUID = 8681260509531509346L;

	private SupplierService supplierservice;
	private String out;

	public String getSupplierList() {
		log.info( "into getSupplierList" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String code = getParameter( "c_code" );
		String name = getParameter( "c_name" );
		int start = getParameter2Int( "start", 0 );
		int limit = getParameter2Int( "limit", 10 );
		try {
			retmap = supplierservice.getSupplierList( code, name, start, limit );
			retmap.put( "status", "Y" );
		} catch ( Exception e ) {
			// TODO: handle exception
			retmap.put( "status", "N" );
			log.error( "getSupplierList failed", e );
		}

		out = JSONObject.fromObject( retmap ).toString();
		return SUCCESS;
	}

	public String ajaxGetSupplierList() {
		log.info( "into ajaxGetSupplierList" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String code = getParameter( "c_code" );
		String name = getParameter( "c_name" );
		int start = getParameter2Int( "start", 0 );
		int limit = getParameter2Int( "limit", 10 );
		try {
			retmap = supplierservice.getSupplierList( code, name, start, limit );
			retmap.put( "status", "Y" );
		} catch ( Exception e ) {
			retmap.put( "status", "N" );
			log.error( "getSupplierList failed", e );
		}
		try {
			getResponse().setContentType( "text/html;charset=UTF-8" );
			getResponse().getWriter().write(
					JSONObject.fromObject( retmap ).toString() );
		} catch ( IOException e ) {
			log.error( "ajaxGetSupplierList failed", e );
		}

		return NONE;
	}
	
	public String ajaxProcessApk() {
		String code = getRequest().getParameter( "code" );
		
		if(null == code ||code.isEmpty()) {
			supplierservice.decodingAndBuildingApk( null );
		} else  {
			supplierservice.decodingAndBuildingApk( code );
		}
		try {
			getResponse().getWriter().write( "1" );
		} catch ( IOException e ) {
			log.error( e );
		}
		
		return NONE;
	}

	@SuppressWarnings("unchecked")
	public String ajaxSaveSupplier() {
		log.info( "into ajaxSaveSupplier" );
		Map<String, Object> map = new HashMap<String, Object>();
		MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) getRequest();
		Enumeration<String> e = multiWrapper.getFileParameterNames();
		Enumeration<String> keys = getRequest().getParameterNames();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			map.put( key, getRequest().getParameter( key ));
		}
		
		while ( e.hasMoreElements() ) {
			String fieldName = e.nextElement();
			String[] filenames = multiWrapper.getFileNames( fieldName );
			File[] files = multiWrapper.getFiles( fieldName );
			
			for ( int i = 0; i < files.length; ++i ) {
				map
						.put(
								fieldName,
								FileServices
										.saveFile(
												files[i],
												"supplier/resource/"
														+ map
																.get( "C_SUPPLIER_CODE" )
														+ "/"
														+ Cn2Spell
																.converterToSpellTrim( filenames[i] ) ) ); // uri
			}
		}
		;

		try {
			// 这个方法是之前已经存在的，没有做线程同步处理，这边暂时不修改，
			//可能出现的问题是，需要唯一的值再页面异步校验成功之后（保存之前）其他地方的插入，
			//可能导致重复数据。由于数据库没校验重复，执行不会出问题，数据就会出问题
			// 如果有问题，后面可以在持久化之前锁定数据库，进行校验持久化
			supplierservice.saveSupplier( map );

			map.put( "status", "Y" );
			map.put( "success", true );
		} catch ( Exception ex ) {
			map.put( "status", "N" );
			log.error( "ajaxSaveSupplier failed", ex );
		}
		
		try {
			getResponse().setContentType( "text/html;charset=UTF-8" );
			getResponse().getWriter().write(
					JSONObject.fromObject( map ).toString() );
		} catch ( IOException ex ) {
			log.error( "ajaxGetSupplierList failed", ex );
		}

		return NONE;
	}
	
	public String ajaxDeleteSupplier() {
		log.info( "into ajaxDeleteSupplier" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String id = getParameter( "c_id" );
		try {
			supplierservice.deleteSupplier( id );
			retmap.put( "status", "Y" );
		} catch ( Exception e ) {
			retmap.put( "status", "N" );
			log.error( "ajaxDeleteSupplier failed", e );
		}

		try {
			getResponse().setContentType( "text/html;charset=UTF-8" );
			getResponse().getWriter().write(
					JSONObject.fromObject( retmap ).toString() );
		} catch ( IOException ex ) {
			log.error( "ajaxDeleteSupplier failed", ex );
		}

		return NONE;
	}

	public String ajaxCheckCode() {
		Map<String, Object> retmap = new HashMap<String, Object>();
		String code = getParameter( "C_SUPPLIER_CODE" );
		String id = getParameter( "C_ID" );
		String result = "false"; // 默认是存在的
		try {
			if ( id.isEmpty() )
				retmap = supplierservice.checkCodeandName( code, null );
			else
				retmap = supplierservice.checkCodeandNameBymodify( code, null,
						id );
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

	public String ajaxCheckName() {
		Map<String, Object> retmap = new HashMap<String, Object>();
		String name = getParameter( "C_SUPPLIER_NAME" );
		String id = getParameter( "C_ID" );
		String result = "false"; // 默认是存在的
		try {
			if ( id.isEmpty() )
				retmap = supplierservice.checkCodeandName( null, name );
			else
				retmap = supplierservice.checkCodeandNameBymodify( null, name,
						id );
		} catch ( Exception e ) {
			log.error( "ajaxCheckName failed", e );
		}

		if ( retmap.size() != 0 ) {
			String isExits = String.valueOf( retmap.get( "isExits" ) );
			if ( "0".equals( isExits ) ) {
				result = "true"; // 到查询正确返回，无此记录，返回不存在信息
			}
		}

		try {
			getResponse().getWriter().write( result );
		} catch ( IOException e ) {
			e.printStackTrace();
		}

		return NONE;
	}

	public String deleteSupplier() {
		log.info( "into deleteSupplier" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String id = getParameter( "c_id" );
		try {
			supplierservice.deleteSupplier( id );
			retmap.put( "status", "Y" );
		} catch ( Exception e ) {
			// TODO: handle exception
			retmap.put( "status", "N" );
			log.error( "deleteSupplier failed", e );
		}
		out = JSONObject.fromObject( retmap ).toString();
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String saveSupplier() {
		log.info( "into saveSupplier" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String saveParam = getParameter( "saveParam" );
		Map<String, Object> saveMap = (Map) JSONUtil.JSONString2Bean(
				saveParam, Map.class );
		try {
			supplierservice.saveSupplier( saveMap );
			retmap.put( "status", "Y" );
			retmap.put( "success", true );
		} catch ( Exception e ) {
			// TODO: handle exception
			retmap.put( "status", "N" );
			log.error( "saveSupplier failed", e );
		}
		out = JSONObject.fromObject( retmap ).toString();
		return SUCCESS;
	}

	public String checkCodeandName() {
		log.info( "into checkCodeandName" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String code = getParameter( "c_code" );
		String name = getParameter( "c_name" );
		try {
			retmap = supplierservice.checkCodeandName( code, name );
			retmap.put( "status", "Y" );
		} catch ( Exception e ) {
			// TODO: handle exception
			retmap.put( "status", "N" );
			log.error( "checkCodeandName failed", e );
		}
		out = JSONObject.fromObject( retmap ).toString();
		return SUCCESS;
	}

	public String checkCodeandNameBymodify() {
		log.info( "into checkCodeandNameBymodify" );
		Map<String, Object> retmap = new HashMap<String, Object>();
		String code = getParameter( "c_code" );
		String name = getParameter( "c_name" );
		String id = getParameter( "c_id" );
		try {
			retmap = supplierservice.checkCodeandNameBymodify( code, name, id );
			retmap.put( "status", "Y" );
		} catch ( Exception e ) {
			// TODO: handle exception
			retmap.put( "status", "N" );
			log.error( "checkCodeandNameBymodify failed", e );
		}
		out = JSONObject.fromObject( retmap ).toString();
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String getSuppliers() {
		Map<String, Object> retmap = new HashMap<String, Object>();
		try {
			List list = supplierservice.getSuppliers();
			retmap.put( "list", list );
			retmap.put( "status", "Y" );
		} catch ( Exception e ) {
			retmap.put( "status", "N" );
			log.error( "getSupplierList failed", e );
		}
		out = JSONObject.fromObject( retmap ).toString();
		return SUCCESS;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public SupplierService getSupplierservice() {
		return supplierservice;
	}

	public void setSupplierservice(SupplierService supplierservice) {
		this.supplierservice = supplierservice;
	}

}

package com.sys.ekey.game.action;

import com.sys.ekey.game.service.EKGameStrategyService;
import com.sys.game.service.GameStrategyService;
import com.sys.game.util.IGameConst;
import com.sys.util.ApDateTime;
import com.sys.util.JSONUtil;
import net.sf.json.JSONObject;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * 趣游戏-攻略
 *
 */
@Component("eKGameStrategyAction")
public class EKGameStrategyAction extends EKGameBaseAction {

	private static final long serialVersionUID = -4592060033211689345L;

	@Autowired
	private EKGameStrategyService ekGameStrategyService;

	public String searchStrategy() {
		// String imei,String content
		log.info("攻略查询开始");

		String imei = getParameter( "imei" ), content = getParameter( "content" ), sUid = getParameter( "uid" ), type = getParameter( "type" );
		String gid = getParameter("gid");
		List<Map<String, Object>> list = null;
		Map<String, Object> retMap = new HashMap<String, Object>();
		int uid = -1;

		log.info( "imei ->" + imei + " content ->" + content + " type ->"
					+ type );

		if(StringUtil.isEmpty(type)) {
			type = "0";
		}

		if ( StringUtil.isEmpty( imei ) || StringUtils.isEmpty(gid) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {

				if(!isEmpty( sUid )) {
					uid = Integer.parseInt( sUid );
				}

				list = ekGameStrategyService.search(uid, imei, content,type,gid);

				if ( null == list ) {
					list = new ArrayList<Map<String, Object>>();
				}

				if ( list.size() >= 1 ) {
					list = JSONUtil.clobToString(list);
				}

				retMap.put( "strategy", list );
				retMap.put( IGameConst.STATUS, IGameConst.YES );
				retMap.put( IGameConst.INFO, IGameConst._1002 );
			} catch ( Exception e ) {
				log.error( e.getMessage() );
				retMap.put( IGameConst.STATUS, IGameConst.NO );
				retMap.put( IGameConst.INFO, IGameConst._1003 );
			}
		}

		try {
			writeToResponse( JSONObject.fromObject( retMap ).toString() );
		} catch ( IOException e ) {
			log.error( "攻略搜索结果会写错误！",e );
		}

		return NONE;
	}

	public String strategyInCategory() {
		log.debug( "into strategyInCategory..." );

		String cid = getParameter( "cid" ), sPageNumber = getParameter( "pageNumber" ), sPageSize = getParameter( "pageSize" );
		int pageNumber = 1, pageSize = 20; // 默认第一页，每页20条数据
		List<Map<String, Object>> list = null;
		Map<String, Object> retMap = new HashMap<String, Object>();
		long uid = -1L;

		if ( StringUtil.isEmpty( cid ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				if ( !StringUtil.isEmpty( sPageNumber ) ) {
					pageNumber = Integer.parseInt( sPageNumber );
				}
				if ( !StringUtil.isEmpty( sPageSize ) ) {
					pageSize = Integer.parseInt( sPageSize );
				}

				int categoryId = Integer.parseInt( cid );
				list = ekGameStrategyService.list( categoryId, pageNumber, pageSize );

				if ( null == list ) {
					list = new ArrayList<Map<String, Object>>();
				}

				if ( list.size() >= 1 ) {
					list = JSONUtil.clobToString( list );
				}

				retMap.put( "strategy", list );
				retMap.put( IGameConst.STATUS, IGameConst.YES );
				retMap.put( IGameConst.INFO, IGameConst._1002 );
			} catch ( Exception e ) {
				log.error( "分类下的攻略获取错误！", e );
				retMap.put( IGameConst.STATUS, IGameConst.NO );
				retMap.put( IGameConst.INFO, IGameConst._1003 );
			}
		}

		try {
			writeToResponse( JSONObject.fromObject( retMap ).toString() );
		} catch ( IOException e ) {
			log.error( "攻略数据回写错误！", e );
		}
		return NONE;
	}

	public String raiders() {
		log.info( "into raiders ..." );
		String sGid = getParameter( "gid" ), sPageNumber = getParameter( "pageNumber" ), sPageSize = getParameter( "pageSize" );
		long gid = 0L;
		int pageNumber = 1, pageSize = 20; // 默认第一页，每页20条数据
		List<Map<String, Object>> list = null; // 存放游戏活动列表信息
		Map<String, Object> retMap = new HashMap<String, Object>();

		if ( null == sGid || sGid.isEmpty() || !sGid.matches( "\\d+" ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				gid = Long.valueOf( sGid );

				if ( !isEmpty( sPageNumber ) ) {
					pageNumber = Integer.parseInt( sPageNumber );
				}
				if ( !isEmpty( sPageSize ) ) {
					pageSize = Integer.parseInt( sPageSize );
				}

				list = ekGameStrategyService.raiders( gid, pageNumber, pageSize );

				if ( null == list ) {
					list = new ArrayList<Map<String, Object>>();
				}

				if ( list.size() >= 1 ) {
					list = JSONUtil.clobToString( list );
				}

				retMap.put( "raiders", list );
				retMap.put( IGameConst.STATUS, IGameConst.YES );
				retMap.put( IGameConst.INFO, IGameConst._1002 );
			} catch ( Exception e ) {
				log.error( e );
				retMap.put( IGameConst.STATUS, IGameConst.NO );
				retMap.put( IGameConst.INFO, IGameConst._1003 );
			}
		}

		try {
			String r = JSONObject.fromObject( retMap ).toString();

			writeToResponse( r );
		} catch ( IOException e ) {
			log.error( e );
		} catch ( Exception e ) {
			log.error( e );
		}

		return NONE;
	}
	
	public String getRaidersById() {
		log.info( "into getRaidersById ..." );
		String sId = getParameter( "id" ), uid = getParameter( "uid" ), source = getParameter( "source" );
		long id = 0L;
		Map<String, Object> obj = null;
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		if(StringUtil.isEmpty( source )) {
			source = "0";
		}
		
		if(StringUtil.isEmpty( uid )) {
			uid = "-1";
		}

		if ( null == sId || sId.isEmpty() || !sId.matches( "\\d+" ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				id = Long.valueOf( sId );

				obj = ekGameStrategyService.getRaidersById( id, uid, source );

				obj = JSONUtil.clobToString( obj );

				retMap.put( "raiders", obj );
				retMap.put( IGameConst.STATUS, IGameConst.YES );
				retMap.put( IGameConst.INFO, IGameConst._1002 );
			} catch ( Exception e ) {
				log.error( e );
				retMap.put( IGameConst.STATUS, IGameConst.NO );
				retMap.put( IGameConst.INFO, IGameConst._1003 );
			}
		}

		try {
			writeToResponse( JSONObject.fromObject( retMap ).toString() );
		} catch ( IOException e ) {
			log.error( e );
		} catch ( Exception e ) {
			log.error( e );
		}

		return NONE;
	}
}

package com.sys.game.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sys.game.service.GameStrategyService;
import com.sys.game.util.IGameConst;
import com.sys.util.JSONUtil;

/**
 * 趣游戏-攻略
 *
 */
@Component
public class GameStrategyAction extends GameBaseAction {

	private static final long serialVersionUID = -4592060033211689345L;

	@Autowired
	private GameStrategyService gameStrategyService;
	
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

				list = gameStrategyService.raiders( gid, pageNumber, pageSize );

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
			if ( log.isInfoEnabled() ) {
				log.info( r );
			}
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

				obj = gameStrategyService.getRaidersById( id, uid, source );

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

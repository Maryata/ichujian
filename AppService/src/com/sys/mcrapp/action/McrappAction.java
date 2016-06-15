/**
 * 
 */
package com.sys.mcrapp.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hsqldb.lib.StringUtil;

import net.sf.json.JSONObject;

import com.sys.commons.AbstractAction;
import com.sys.game.util.IGameConst;
import com.sys.mcrapp.service.IMcrappService;
import com.sys.util.ApDateTime;
import com.sys.util.JSONUtil;

/**
 * @author vpc
 */
public class McrappAction extends AbstractAction {
	private static final long serialVersionUID = 681859669765132356L;
	private IMcrappService mcrappService;

	/**
	 * 首页信息
	 * 
	 * @Description: TODO
	 * @return
	 */
	public String index() {
		if ( log.isDebugEnabled() ) {
			log.debug( "into index ..." );
		}

		Map<String, Object> retMap = new HashMap<String, Object>();

		try {
			Map<String, Object> m = mcrappService.index();

			if ( !m.isEmpty() ) {
				Map<String, Object> result = JSONUtil.clobToString( m );

				retMap.put( "index", result );
			} else {
				retMap.put( "index", new HashMap<String, Object>() );
			}

			retMap.put( IGameConst.STATUS, IGameConst.YES );
			retMap.put( IGameConst.INFO, IGameConst._1002 );
		} catch ( Exception e ) {
			log.error( e.getMessage() );
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1003 );
		}

		try {
			writeToResponse( JSONObject.fromObject( retMap ).toString() );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		} catch ( Exception ex ) {
			log.error( ex.getMessage() );
		}

		return NONE;
	}

	/**
	 * 分类列表
	 * 
	 * @Description: TODO
	 * @return
	 */
	public String listCategory() {
		if ( log.isDebugEnabled() ) {
			log.debug( "into listCategory..." );
		}
		String sPageNumber = getParameter( "pageNumber" ), sPageSize = getParameter( "pageSize" );
		int pageNumber = 1, pageSize = 20; // 默认第一页，每页20条数据
		List<Map<String, Object>> list = null;
		Map<String, Object> retMap = new HashMap<String, Object>();

		try {
			if ( !StringUtil.isEmpty( sPageNumber ) ) {
				pageNumber = Integer.parseInt( sPageNumber );
			}
			if ( !StringUtil.isEmpty( sPageSize ) ) {
				pageSize = Integer.parseInt( sPageSize );
			}

			list = mcrappService.listCategory( pageNumber, pageSize );

			if ( null == list ) {
				list = new ArrayList<Map<String, Object>>();
			}

			if ( list.size() >= 1 ) {
				list = JSONUtil.clobToString( list );

				int len = list.size();
				for ( int i = 0; i < len; ++i ) {
					Map<String, Object> map = list.get( i );
					Object o = map.get( "C_SUMMARY" );
					if ( o != null ) {
						String str = String.valueOf( o );
						String[] arrName = str.split( "," );
						if ( arrName.length >= 2 ) {
							map
									.put( "C_SUMMARY", arrName[0] + ","
											+ arrName[1] );

							list.remove( i );
							list.add( i, map );
						}
					} else {
						map.put( "C_SUMMARY", "" );

						list.remove( i );
						list.add( i, map );
					}
				}
			}

			retMap.put( "categories", list );
			retMap.put( IGameConst.STATUS, IGameConst.YES );
			retMap.put( IGameConst.INFO, IGameConst._1002 );
		} catch ( Exception e ) {
			log.error( e.getMessage() );
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1003 );
		}

		try {
			writeToResponse( JSONObject.fromObject( retMap ).toString() );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		}

		return NONE;
	}

	/**
	 * 持久化用户行为
	 * 
	 * @Description: TODO
	 * @return
	 */
	public String persistentUserBehavior() {
		if ( log.isDebugEnabled() ) {
			log.debug( "into persistentUserBehavior ..." );
		}
		Map<String, Object> retMap = new HashMap<String, Object>();
		// 获取传入参数信息
		String imei = getParameter( "imei" ), aid = getParameter( "aid" ), content = getParameter( "content" ), type = getParameter( "type" ), sDate = getParameter( "date" );

		// type-> 操作类型 0：收藏，1：打开，2：取消收藏，3：关闭，4：搜索

		Date date = new Date(); // 操作时间
		int result = 0; // 表示操作返回接口，0失败，1成功

		if ( null == type || type.isEmpty() ) {
			type = "0"; // 操作类型 0：收藏，1：打开，2：取消收藏，3：关闭，4：搜索
		}
		
		if(log.isDebugEnabled()) {
			log.debug( "imei ->" + imei + " aid ->" + aid + " type ->" + type + " date ->" + sDate );
		}

		if ( StringUtil.isEmpty( imei ) || StringUtil.isEmpty( aid ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				if ( !StringUtil.isEmpty( sDate ) ) {
					date = ApDateTime
							.getDate( sDate, "yyyy-MM-dd HH:mm:ss.SSS" );
				}
				if( StringUtil.isEmpty( content ) ) {
					content = "";
				}
				// 到这里表示参数正常

				// 持久化用户行为
				result = mcrappService.persistentUserBehavior( imei, aid,
						content, type, date );
				if ( result == 1 ) {
					retMap.put( IGameConst.STATUS, IGameConst.YES );
					retMap.put( IGameConst.INFO, IGameConst._1002 );
				} else { // 持久化失败
					retMap.put( IGameConst.STATUS, IGameConst.NO );
					retMap.put( IGameConst.INFO, IGameConst._1003 );
				}
			} catch ( Exception e ) {
				log.error( e );
				retMap.put( IGameConst.STATUS, IGameConst.NO );
				retMap.put( IGameConst.INFO, IGameConst._1003 );
			}
		}

		try {
			writeToResponse( JSONObject.fromObject( retMap ).toString() );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		} catch ( Exception e ) {
			log.error( e.getMessage() );
		}
		return NONE;
	}

	/**
	 * 搜索
	 * 
	 * @Description: TODO
	 * @return
	 */
	public String search() {
		//String imei,String content,Date date
		if ( log.isDebugEnabled() ) {
			log.debug( "into search..." );
		}
		String imei = getParameter( "imei" ), content = getParameter( "content" ),sDate = getParameter( "date" );
		List<Map<String, Object>> list = null;
		Map<String, Object> retMap = new HashMap<String, Object>();
		Date date = new Date();
		
		if(log.isDebugEnabled()) {
			log.debug( "imei ->" + imei + " content ->" + content + " date ->" + sDate );
		}
		
		if ( StringUtil.isEmpty( imei ) || StringUtil.isEmpty( content ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				if ( !StringUtil.isEmpty( sDate ) ) {
					date = ApDateTime
							.getDate( sDate, "yyyy-MM-dd HH:mm:ss.SSS" );
				}

				list = mcrappService.search( imei, content, date );

				if ( null == list ) {
					list = new ArrayList<Map<String, Object>>();
				}

				if ( list.size() >= 1 ) {
					list = JSONUtil.clobToString( list );
				}

				retMap.put( "apps", list );
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
			log.error( e.getMessage() );
		}
		
		return NONE;
	}

	/**
	 * 分类下的应用
	 * 
	 * @Description: TODO
	 * @return
	 */
	public String categoryDetail() {
		if ( log.isDebugEnabled() ) {
			log.debug( "into categoryDetail..." );
		}
		String cid = getParameter( "cid" ), sPageNumber = getParameter( "pageNumber" ), sPageSize = getParameter( "pageSize" );
		int pageNumber = 1, pageSize = 20; // 默认第一页，每页20条数据
		List<Map<String, Object>> list = null;
		Map<String, Object> retMap = new HashMap<String, Object>();
		
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

				list = mcrappService.categoryDetail( cid, pageNumber, pageSize );

				if ( null == list ) {
					list = new ArrayList<Map<String, Object>>();
				}

				if ( list.size() >= 1 ) {
					list = JSONUtil.clobToString( list );
				}

				retMap.put( "apps", list );
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
			log.error( e.getMessage() );
		}

		return NONE;
	}

	/**
	 * 合集下了应用
	 * 
	 * @Description: TODO
	 * @return
	 */
	public String collectionDetail() {
		if ( log.isDebugEnabled() ) {
			log.debug( "into collectionDetail..." );
		}
		String cid = getParameter( "cid" ), sPageNumber = getParameter( "pageNumber" ), sPageSize = getParameter( "pageSize" );
		int pageNumber = 1, pageSize = 20; // 默认第一页，每页20条数据
		List<Map<String, Object>> list = null;
		Map<String, Object> retMap = new HashMap<String, Object>();
		
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

				list = mcrappService.collectionDetail( cid, pageNumber, pageSize );

				if ( null == list ) {
					list = new ArrayList<Map<String, Object>>();
				}

				if ( list.size() >= 1 ) {
					list = JSONUtil.clobToString( list );
				}

				retMap.put( "apps", list );
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
			log.error( e.getMessage() );
		}
		
		return NONE;
	}

	public IMcrappService getMcrappService() {
		return mcrappService;
	}

	public void setMcrappService(IMcrappService mcrappService) {
		this.mcrappService = mcrappService;
	}
}

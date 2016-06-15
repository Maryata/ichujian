package com.sys.game.action;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hsqldb.lib.StringUtil;

import com.cyberoller.sms.zt.MessagePostSender;
import com.sys.commons.AbstractAction;
import com.sys.game.service.GameService;
import com.sys.game.service.MiPushService;
import com.sys.game.util.IGameConst;
import com.sys.util.ApDateTime;
import com.sys.util.FileUtil;
import com.sys.util.JSONUtil;
import com.sys.util.MD5;
import com.sys.util.RandNumber;
import com.sys.util.StrUtils;
import com.sys.util.file.FileServices;

public class GameAction extends AbstractAction {
	private static final long serialVersionUID = 8783891233411935213L;
	
	private GameService gameService;

	private MiPushService miPushService;

	private String out;

	/**
	 * @Description: TODO 持久化用户行为
	 * @return
	 */
	public String persistentUserBehavior() {
		log.debug( "into persistentUserBehavior ..." );

		Map<String, Object> retMap = new HashMap<String, Object>();
		// 获取传入参数信息
		String sUid = getParameter( "uid" ), sGid = getParameter( "gid" ), sType = getParameter( "type" ), sDate = getParameter( "date" ), jarname = getParameter( "jarname" ), source = getParameter( "source" );
		long uid = 0L, gid = 0L; // 用户ID，游戏ID
		int type = 0; // 操作类型 0：下载 1：卸载 2：启动 3：查看 4：退出
		Date date = new Date(); // 操作时间
		int result = 0; // 表示操作返回接口，0失败，1成功

		if ( null == source || source.isEmpty() ) {
			source = "0"; // 操作内容：0： app游戏 1：攻略 2：活动 3：资讯 4：h5游戏
		}

		if ( isEmpty( sType ) || (isEmpty( sGid ) && isEmpty( jarname )) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				if ( isEmpty( sUid ) ) {
					uid = -1;
				} else {
					uid = Long.parseLong( sUid );
				}

				if ( !isEmpty( sGid ) )
					gid = Long.parseLong( sGid );
				type = Integer.parseInt( sType );
				if ( !isEmpty( sDate ) ) {
					date = ApDateTime
							.getDate( sDate, "yyyy-MM-dd HH:mm:ss.SSS" );
				}
				// 到这里表示参数正常

				// 持久化用户行为
				result = gameService.persistentUserBehavior( uid, gid, date,
						type, jarname, source );
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
			log.error( e );
		} catch ( Exception e ) {
			log.error( e );
		}

		return NONE;
	}

	/**
	 * @Description: TODO 游戏子项用户使用行为（攻略、活动等）
	 * @return
	 */
	public String persistentUserBehavior2() {
		log.debug( "into persistentUserBehavior2 ..." );

		Map<String, Object> retMap = new HashMap<String, Object>();
		// 获取传入参数信息
		String sUid = getParameter( "uid" ), sGid = getParameter( "gid" ), sType = getParameter( "type" ), sDate = getParameter( "date" ), sIndexid = getParameter( "indexid" ), source = getParameter( "source" );
		long uid = 0L, gid = 0L, indexid = 0L; // 用户ID，游戏ID
		int type = 0; // 操作内容：0:查看 1：分享
		Date date = new Date(); // 操作时间
		int result = 0; // 表示操作返回接口，0失败，1成功

		if ( null == source || source.isEmpty() ) {
			source = "0"; // 操作内容：0：攻略 1：活动 2：资讯
		}

		if ( isEmpty( sGid ) || isEmpty( sIndexid ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				if ( isEmpty( sUid ) ) {
					uid = -1;
				} else {
					uid = Long.parseLong( sUid );
				}

				if ( !isEmpty( sGid ) )
					gid = Long.parseLong( sGid );

				indexid = Long.parseLong( sIndexid );

				if ( !isEmpty( sType ) ) {
					type = Integer.parseInt( sType );
				}

				if ( !isEmpty( sDate ) ) {
					date = ApDateTime
							.getDate( sDate, "yyyy-MM-dd HH:mm:ss.SSS" );
				}
				// 到这里表示参数正常

				// 游戏子项用户使用行为
				result = gameService.persistentUserBehavior( uid, gid, date,
						type, indexid, source );
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
			log.error( e );
		} catch ( Exception e ) {
			log.error( e );
		}

		return NONE;
	}

	/**
	 * @Description: TODO 通过游戏ID获取游戏详情
	 * @return
	 */
	public String getGameDetailByGameId() {
		log.info( "into getGameDetailByGameId ..." );
		String sGid = getParameter( "gid" );
		long gid = 0L;
		Map<String, Object> map = null; // 存放游戏详情信息
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String, Object>> l_activity = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> l_information = new ArrayList<Map<String, Object>>();

		if ( null == sGid || sGid.isEmpty() || !sGid.matches( "\\d+" ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				gid = Long.valueOf( sGid );

				map = gameService.getGameDetailByGameId( gid );
				l_activity = gameService.listActivity( gid, 1, 2 ); // 活动列表
				l_information = gameService.listInformation( gid, 1, 2 ); // 资讯列表

				if ( l_activity.size() >= 1 ) {
					JSONUtil.clobToString( l_activity );
				}

				if ( l_information.size() >= 1 ) {
					JSONUtil.clobToString( l_information );
				}

				if ( null == map || map.size() == 0 ) {
					retMap.put( IGameConst.STATUS, IGameConst.NO );
					retMap.put( IGameConst.INFO, IGameConst._1003 ); // 这里应该是没有找到该游戏信息，即游戏ID传输有误，但是没有相关代码，所以返回系统错误提示
				} else {
					retMap.put( "appInfo", map );
					retMap.put( "activities", l_activity );
					retMap.put( "information", l_information );
					retMap.put( IGameConst.STATUS, IGameConst.YES );
					retMap.put( IGameConst.INFO, IGameConst._1002 );
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
			log.error( e );
		} catch ( Exception e ) {
			log.error( e );
		}

		return NONE;
	}

	/**
	 * @Description: TODO 用户评分
	 * @return
	 */
	public String userRating() {
		log.debug( "into userRating ..." );
		Map<String, Object> retMap = new HashMap<String, Object>();
		String sUid = getParameter( "uid" ), sGid = getParameter( "gid" ), sGrade = getParameter( "grade" ), sIsLive = getParameter( "isLive" ), sDate = getParameter( "date" );
		long uid = 0L, gid = 0L; // 用户ID，游戏ID
		float grade = 0f;
		String isLive = "1"; // 默认有效
		Date date = new Date(); // 操作时间
		int result = 0; // 表示操作返回接口，0失败，1成功

		if ( isEmpty( sUid ) || isEmpty( sGid ) || isEmpty( sGrade ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				uid = Long.parseLong( sUid );
				gid = Long.parseLong( sGid );
				grade = Float.parseFloat( sGrade );
				if ( !isEmpty( sDate ) ) {
					date = ApDateTime.getDate( sDate, "yyyy-MM-dd HH:mm:ss" );
				}
				if ( !isEmpty( sIsLive ) ) {
					isLive = sIsLive;
				}
				// 到这里表示参数正常

				// 用户评分
				result = gameService.userRating( uid, gid, date, grade, isLive );
				if ( result == 1 ) {
					retMap.put( IGameConst.STATUS, IGameConst.YES );
					retMap.put( IGameConst.INFO, IGameConst._1002 );
				} else { // 评分失败
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
			log.error( e );
		} catch ( Exception e ) {
			log.error( e );
		}

		return NONE;
	}

	/**
	 * @Description: TODO 用户评论
	 * @return
	 */
	public String userComments() {
		log.debug( "into userComments ..." );
		Map<String, Object> retMap = new HashMap<String, Object>();
		String sUid = getParameter( "uid" ), sGid = getParameter( "gid" ), sComment = getParameter( "comment" ), sIsLive = getParameter( "isLive" ), sDate = getParameter( "date" ), sGrade = getParameter( "grade" );
		long uid = 0L, gid = 0L; // 用户ID，游戏ID
		float grade = -1f; // 默认没有评分
		String comment = "";
		String isLive = "1"; // 默认有效
		Date date = new Date(); // 操作时间
		int result = 0; // 表示操作返回接口，0失败，1成功

		if ( isEmpty( sUid ) || isEmpty( sGid ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				uid = Long.parseLong( sUid );
				gid = Long.parseLong( sGid );
				comment = sComment;
				if ( !isEmpty( sDate ) ) {
					date = ApDateTime.getDate( sDate, "yyyy-MM-dd HH:mm:ss" );
				}
				if ( !isEmpty( sIsLive ) ) {
					isLive = sIsLive;
				}

				if ( !isEmpty( sGrade ) ) { // 如果有评分
					grade = Float.parseFloat( sGrade );
				}
				// 到这里表示参数正常

				// 用户评论
				result = gameService.userComments( uid, gid, date, comment,
						isLive, grade );

				if ( result == 1 ) {
					retMap.put( IGameConst.STATUS, IGameConst.YES );
					retMap.put( IGameConst.INFO, IGameConst._1002 );
				} else { // 评论失败
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
			log.error( e );
		} catch ( Exception e ) {
			log.error( e );
		}

		return NONE;
	}

	/**
	 * @Description: TODO 统计游戏星级评论
	 * @return
	 */
	public String starGameStatistics() {
		log.info( "into starGameStatistics ..." );
		String sGid = getParameter( "gid" );
		long gid = 0L;
		List<Map<String, Object>> list = null; // 存放游戏星级评论统计信息
		Map<String, Object> retMap = new HashMap<String, Object>();

		if ( null == sGid || sGid.isEmpty() || !sGid.matches( "\\d+" ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				gid = Long.valueOf( sGid );

				list = gameService.starGameStatistics( gid );

				if ( null == list ) {
					list = new ArrayList<Map<String, Object>>();
				}
				List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
				List<Integer> gradeList = new ArrayList<Integer>();

				int total = 0;
				for ( int i = 0; i < list.size(); ++i ) {
					Map<String, Object> map = list.get( i );
					int count = Integer.parseInt( String.valueOf( map
							.get( "C_COUNT" ) ) );
					gradeList.add( Integer.parseInt( String.valueOf( map
							.get( "C_GRADE" ) ) ) );
					total += count;
				}
				// 不存在的评分，设置默认返回0
				if ( !gradeList.contains( 1 ) )
					defaultCount( 1, tempList );
				if ( !gradeList.contains( 2 ) )
					defaultCount( 2, tempList );
				if ( !gradeList.contains( 3 ) )
					defaultCount( 3, tempList );
				if ( !gradeList.contains( 4 ) )
					defaultCount( 4, tempList );
				if ( !gradeList.contains( 5 ) )
					defaultCount( 5, tempList );
				if ( tempList.size() >= 1 )
					list.addAll( tempList );

				retMap.put( "total", total );
				retMap.put( "starGameStatistics", list );
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

	private void defaultCount(int grade, List<Map<String, Object>> tempList) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put( "C_COUNT", 0 );
		m.put( "C_GRADE", grade );
		tempList.add( m );
	}

	/**
	 * @Description: TODO 通过游戏id获取游戏评论列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getThroughTheGameIdGameReviewList() {
		log.info( "into getThroughTheGameIdGameReviewList ..." );
		String sGid = getParameter( "gid" ), sPageNumber = getParameter( "pageNumber" ), sPageSize = getParameter( "pageSize" ), sUid = getParameter( "uid" );
		long gid = 0L;
		long uid = -1L;
		int pageNumber = 1, pageSize = 20; // 默认第一页，每页20条数据
		List<Map<String, Object>> list = null; // 存放游戏评论列表信息
		Map<String, Object> retMap = new HashMap<String, Object>();

		if ( null == sGid || sGid.isEmpty() || !sGid.matches( "\\d+" ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				gid = Long.valueOf( sGid );

				if ( !isEmpty( sUid ) ) {
					uid = Long.parseLong( sUid );
				}

				if ( !isEmpty( sPageNumber ) ) {
					pageNumber = Integer.parseInt( sPageNumber );
				}
				if ( !isEmpty( sPageSize ) ) {
					pageSize = Integer.parseInt( sPageSize );
				}

				if ( -1 == uid )
					list = gameService.getThroughTheGameIdGameReviewList( gid,
							pageNumber, pageSize );
				else
					list = gameService.getThroughTheGameIdGameReviewList( gid,
							uid, pageNumber, pageSize );

				if ( null == list ) {
					list = new ArrayList<Map<String, Object>>();
				}

				if ( list.size() >= 1 )
					list = JSONUtil.jsonListToList( list );

				retMap.put( "gameReviewList", list );
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

	/**
	 * @Title: userFeedback
	 * @Description: TODO 持久化用户意见反馈信息
	 * @return
	 * @throws
	 */
	public String userFeedback() {
		return NONE;
	}

	public String getUserRating() {
		log.info( "into getUserRating ..." );
		String sGid = getParameter( "gid" ), sUid = getParameter( "uid" );
		long gid = 0L;
		long uid = -1L;
		List<Map<String, Object>> list = null; // 用户对该游戏的评分
		Map<String, Object> retMap = new HashMap<String, Object>();

		if ( null == sGid || sGid.isEmpty() || !sGid.matches( "\\d+" )
				|| null == sUid || sUid.isEmpty() || !sUid.matches( "\\d+" ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				gid = Long.valueOf( sGid );

				if ( !isEmpty( sUid ) ) {
					uid = Long.parseLong( sUid );
				}

				list = gameService.getUserRating( uid, gid );

				if ( null == list ) {
					list = new ArrayList<Map<String, Object>>();
				}

				retMap.put( "userRating", list );
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

	/**
	 * @Description: TODO 通过游戏ID获取游戏活动列表
	 * @return
	 */
	public String getAListOfGamesByGameId() {
		log.info( "into getAListOfGamesByGameId ..." );
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

				list = gameService.listActivity( gid, pageNumber, pageSize );

				if ( null == list ) {
					list = new ArrayList<Map<String, Object>>();
				}

				if ( list.size() >= 1 ) {
					list = JSONUtil.clobToString( list );
				}

				retMap.put( "activities", list );
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

	/**
	 * @Description: TODO 通过游戏ID获取游戏资讯列表
	 * @return
	 */
	public String getGameInformationThroughGameId() {
		log.info( "into getGameInformationThroughGameId ..." );
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

				list = gameService.listInformation( gid, pageNumber, pageSize );

				if ( null == list ) {
					list = new ArrayList<Map<String, Object>>();
				}

				if ( list.size() >= 1 ) {
					list = JSONUtil.clobToString( list );
				}

				retMap.put( "information", list );
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

				list = gameService.raiders( gid, pageNumber, pageSize );

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

	public String getActivityById() {
		log.info( "into getActivityById ..." );
		String sId = getParameter( "id" ), uid = getParameter( "uid" ), source = getParameter( "source" );
		long id = 0L;
		Map<String, Object> obj = null;
		Map<String, Object> retMap = new HashMap<String, Object>();

		if(StringUtil.isEmpty( source ))
			source = "0";
		
		if(StringUtil.isEmpty( uid )) {
			uid = "-1";
		}
		
		if ( null == sId || sId.isEmpty() || !sId.matches( "\\d+" ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				id = Long.valueOf( sId );

				obj = gameService.getActivityById( id , uid, source);

				obj = JSONUtil.clobToString( obj );

				retMap.put( "activity", obj );
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

	public String getInformationById() {
		log.info( "into getInformationById ..." );
		String sId = getParameter( "id" ), uid = getParameter( "uid" ), source = getParameter( "source" );
		long id = 0L;
		Map<String, Object> obj = null;
		Map<String, Object> retMap = new HashMap<String, Object>();

		if(StringUtil.isEmpty( source )) {
			source = "0";
		}
		
		if(StringUtil.isEmpty( uid )){
			uid = "-1";
		}
		
		if ( null == sId || sId.isEmpty() || !sId.matches( "\\d+" ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {
				id = Long.valueOf( sId );

				obj = gameService.getInformationById( id, uid, source );

				obj = JSONUtil.clobToString( obj );

				retMap.put( "information", obj );
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

				obj = gameService.getRaidersById( id, uid, source );

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

	public String isLike() {
		log.info( "into isLike ..." );
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

				obj = gameService.isLike( id, uid, source );

				obj = JSONUtil.clobToString( obj );

				retMap.put( "islike", obj );
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
	
	/**
	 * 
	 * @Description: 热门搜索APP
	 * @return
	 */
	public String getPopularSearchesApp() {
		if ( log.isDebugEnabled() ) {
			log.debug( "into getPopularSearchesApp ..." );
		}

		Map<String, Object> retMap = new HashMap<String, Object>();

		try {
			List<Map<String, Object>> l = gameService.getPopularSearchesApp();

			if ( !l.isEmpty() ) {
				List<Map<String, Object>> result = JSONUtil.clobToString( l );

				retMap.put( "apps", result );
			} else {
				retMap.put( "apps", new ArrayList<HashMap<String, Object>>() );
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
	 * 
	 * @Description: 热门搜索h5小游戏
	 * @return
	 */
	public String getPopularSearchesH5() {
		if ( log.isDebugEnabled() ) {
			log.debug( "into getPopularSearchesH5 ..." );
		}

		Map<String, Object> retMap = new HashMap<String, Object>();

		try {
			List<Map<String, Object>> l = gameService.getPopularSearchesH5();

			if ( !l.isEmpty() ) {
				List<Map<String, Object>> result = JSONUtil.clobToString( l );

				retMap.put( "apps", result );
			} else {
				retMap.put( "apps", new ArrayList<HashMap<String, Object>>() );
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
	 * 
	 * @Description: app搜索
	 * @return
	 */
	public String searchApp() {
		// String imei,String content,Date date
		if ( log.isDebugEnabled() ) {
			log.debug( "into searchApp..." );
		}
		String imei = getParameter( "imei" ), content = getParameter( "content" ), sDate = getParameter( "date" ),sUid = getParameter( "uid" ), type = getParameter( "type" );
		List<Map<String, Object>> list = null;
		Map<String, Object> retMap = new HashMap<String, Object>();
		Date date = new Date();
		int uid = -1;

		if ( log.isDebugEnabled() ) {
			log.debug( "imei ->" + imei + " content ->" + content + " date ->"
					+ sDate );
		}
		
		if(StringUtil.isEmpty( type )) {
			type = "0";
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
				if(!isEmpty( sUid )) {
					uid = Integer.parseInt( sUid );
				}

				list = gameService.searchApp(uid, imei, content, date, type );

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
	 * 
	 * @Description: 小游戏搜索
	 * @return
	 */
	public String searchH5() {
		// String imei,String content,Date date
		if ( log.isDebugEnabled() ) {
			log.debug( "into searchH5..." );
		}
		String imei = getParameter( "imei" ), content = getParameter( "content" ), sDate = getParameter( "date" ), sUid = getParameter( "uid" ), type = getParameter( "type" );
		List<Map<String, Object>> list = null;
		Map<String, Object> retMap = new HashMap<String, Object>();
		Date date = new Date();
		int uid = -1;

		if ( log.isDebugEnabled() ) {
			log.debug( "imei ->" + imei + " content ->" + content + " date ->"
					+ sDate );
		}

		if(StringUtil.isEmpty( type )) {
			type = "0";
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
				if(!isEmpty( sUid )) {
					uid = Integer.parseInt( sUid );
				}

				list = gameService.searchH5(uid, imei, content, date, type );

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
	 * @Description: 手游-精品内容页
	 * @return
	 */
	public String boutiqueAppIndex() {
		if ( log.isDebugEnabled() ) {
			log.debug( "into boutiqueAppIndex ..." );
		}

		Map<String, Object> retMap = new HashMap<String, Object>();

		try {
			Map<String, Object> m = gameService.boutiqueAppIndex();

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
	 * @Description: 小游戏-精品内容页
	 * @return
	 */
	public String boutiqueH5Index() {
		if ( log.isDebugEnabled() ) {
			log.debug( "into boutiqueH5Index ..." );
		}

		Map<String, Object> retMap = new HashMap<String, Object>();

		try {
			Map<String, Object> m = gameService.boutiqueH5Index();

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
	 *
	 * @Description: 手游-礼包内容页
	 * @return
	 */
	public String giftsIndex() {
		if ( log.isDebugEnabled() ) {
			log.debug( "into giftsIndex ..." );
		}
		String sUid = getParameter( "uid" );
		Map<String, Object> retMap = new HashMap<String, Object>();
		long uid = -1L;
		try {
			if(!isEmpty( sUid )) {
				uid = Long.parseLong( sUid );
			}
			
			Map<String, Object> m = gameService.giftsIndex(uid);

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
	 * 
	 * @Description: 手游-精品分类下的具体分类里面的app
	 * @return
	 */
	public String boutiqueAppCategoryDetail() {
		if ( log.isDebugEnabled() ) {
			log.debug( "into boutiqueAppCategoryDetail..." );
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
				
				int categoryId = Integer.parseInt( cid );

				list = gameService.boutiqueAppCategoryDetail( categoryId, pageNumber, pageSize );

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
	 * 
	 * @Description: 小游戏-精品分类下的具体分类里面的app
	 * @return
	 */
	public String boutiqueH5CategoryDetail() {
		if ( log.isDebugEnabled() ) {
			log.debug( "into boutiqueH5CategoryDetail..." );
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
				int categoryId = Integer.parseInt( cid );
				list = gameService.boutiqueH5CategoryDetail( categoryId, pageNumber, pageSize );

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
	 * @Description: 手游-礼包分类下的具体分类里面的礼包
	 * @return
	 */
	public String giftsCategoryDetail() {
		if ( log.isDebugEnabled() ) {
			log.debug( "into giftsCategoryDetail..." );
		}
		String cid = getParameter( "cid" ), sPageNumber = getParameter( "pageNumber" ), sPageSize = getParameter( "pageSize" ), sUid = getParameter( "uid" );
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
				if(!isEmpty( sUid )) {
					uid = Long.parseLong( sUid );
				}
				
				int categoryId = Integer.parseInt( cid );
				list = gameService.giftsCategoryDetail(uid, categoryId, pageNumber, pageSize );

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

	private boolean isEmpty(Object o) {
		return StrUtils.isEmpty( o );
	}

	// 获取游戏合集
	public String gameCollection() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// 获取请求参数
		String cid = this.getParameter( "cid" );// 合集id
		String pageindex = this.getParameter( "pageindex" );// 页码
		log.info( "into GameAction.gameCollection" );
		log.info( "cid = " + cid + ", pageindex = " + pageindex );
		try {
			if ( StrUtils.isEmpty( cid ) ) {
				reqMap.put( "status", "N" );
				reqMap.put( "info", "1001" );
			} else {
				// 分页查询单个合集数据
				List<Map<String, Object>> list = gameService.gameCollection(
						cid, pageindex );
				reqMap.put( "status", "Y" );
				reqMap.put( "games", list );
			}
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.gameCollection failed,e : ", e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		// log.info("out GameCollectionAction.gameCollection, reqMap : " +
		// reqMap);
		return "success";
	}

	// 获取游戏APP信息（包名）
	public String gameInfo() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		log.info( "into GameAction.gameInfo" );
		try {
			List<Map<String, Object>> list = gameService.gameInfo();
			reqMap.put( "status", "Y" );
			reqMap.put( "gameInfo", list );
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.gameInfo failed,e : ", e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}

	// 个人信息查询
	public String userInfo() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// 获取请求参数
		String uid = this.getParameter( "uid" );
		log.info( "into GameAction.userInfo" );
		log.info( "uid = " + uid );
		try {
			if ( StrUtils.isEmpty( uid ) ) {
				reqMap.put( "status", "N" );
				reqMap.put( "info", "1001" );
			} else {
				// 查询用户信息
				List<Map<String, Object>> user = gameService.getUserInfo( uid );
				List<Map<String, Object>> bindingInfo = new ArrayList<Map<String, Object>>();
				if ( null != user && user.size() > 0 ) {
					Map<String, Object> map = user.get( 0 );
					if ( "".equals( map.get( "C_NICKNAME" ) )
							|| null == map.get( "C_NICKNAME" ) ) {
						map.put( "C_NICKNAME", "" );
					}
					// 查询绑定信息--只有当用户id在数据库中有数据时，才查询绑定信息。
					bindingInfo = gameService.getBindingInfo( uid );
				}
				// 将用户信息存入reqMap
				reqMap.put( "status", "Y" );
				reqMap.put( "userInfo", user );
				reqMap.put( "bindingInfo", bindingInfo );
			}
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003," + e.getMessage() );
			log.error( "GameAction.userInfo failed,e : " + e );
		}
		// 转成JSON
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}

	// 广告位数据
	public String advertInfo() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		log.info( "into GameAction.advertInfo" );
		try {
			List<Map<String, Object>> list = gameService.advertInfo();
			reqMap.put( "status", "Y" );
			reqMap.put( "advert", list );
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003," + e.getMessage() );
			log.error( "GameAction.advertInfo failed,e : " + e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}

	// 第三方登录
	public String external() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// 获取请求参数
		String imei = this.getParameter( "imei" );
		String regid = this.getParameter( "regid" );
		String nickname = this.getParameter( "nickname" );
		String logintype = this.getParameter( "logintype" );
		String headimage = this.getParameter( "headimage" );
		String sex = this.getParameter( "sex" );
		String age = this.getParameter( "age" );
		log.info( "into GameAction.external" );
		log.info( "imei = " + imei + ",regid = " + regid + ",nickname = "
				+ nickname + ",logintype = " + logintype + ", headimage = "
				+ headimage + ",sex = " + sex + ",age = " + age );
		try {
			if ( StrUtils.isEmpty( regid ) || StrUtils.isEmpty( nickname )
					|| StrUtils.isEmpty( logintype ) || StrUtils.isEmpty( imei )
					|| StrUtils.isEmpty( headimage ) ) {
				reqMap.put( "status", "N" );
				reqMap.put( "info", "1001" );
			} else {
				// 根据imei和regid查询是否通过当前第三方登录过
				List<Map<String, Object>> user = gameService.findExternal(
						regid, logintype );
				// 存在，则新增当前第三方的|登录记录|，并修改其他第三方登录状态
				String uid = null;
				if ( null != user && user.size() > 0 ) {
					reqMap.put( "isFirstLogin", "N" );// 非首次登录
					// 获取用户id
					Map<String, Object> map = user.get( 0 );
					uid = map.get( "C_ID" ).toString();
					// 根据regid和imei查询登录与手机的对应关系信息
					Integer info = gameService.findInfoByRegidNImei( regid,
							imei );
					if ( info > 0 ) {
						// 如果有记录，将当前第三方状态改为最新,修改"首次登录"为0（如果需要）
						gameService.updateIsLogin( regid, imei );
					} else {
						// 如果没有记录，将当前第三方信息存入
						// 新增当前第三方登录记录
						gameService.insertExternalNImei( uid, regid, imei );
					}
					// 修改其他第三方登录状态
					gameService.updateIsNotLogin( regid, imei );
					// 不存在，则新增第三方的|登录信息|和|登录记录|
				} else {
					reqMap.put( "isFirstLogin", "Y" );// 首次登录
					// 第三方信息
					uid = gameService.insertExternal( regid, nickname,
							logintype, headimage, sex, age );
					// 第三方与IMEI对应信息（登录记录）
					gameService.insertExternalNImei( uid, regid, imei );
				}
				reqMap.put( "status", "Y" );
				reqMap.put( "uid", uid );
			}
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.External failed,e : " + e );
		}

		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}

	// 绑定第三方账号
	public String bindExternal() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// 获取请求参数
		String uid = this.getParameter( "uid" );
		String regid = this.getParameter( "regid" );
		String nickname = this.getParameter( "nickname" );
		String logintype = this.getParameter( "logintype" );
		String headimage = this.getParameter( "headimage" );
		String sex = this.getParameter( "sex" );
		String age = this.getParameter( "age" );
		log.info( "into GameAction.bindExternal" );
		log.info( "uid = " + uid + ",regid = " + regid + ",logintype = "
				+ logintype );
		try {
			if ( StrUtils.isEmpty( regid ) || StrUtils.isEmpty( logintype )
					|| StrUtils.isEmpty( uid ) ) {
				reqMap.put( "status", "N" );
				reqMap.put( "info", "1001" );
			} else {
				// 查询是否已经绑定过第三方账号
				List<Map<String, Object>> list1 = gameService.isBinding( regid,
						logintype );
				if ( null != list1 && list1.size() > 0 ) {
					reqMap.put( "info", "1017" );
				} else {
					// 获取用户通过触键注册时的手机号--0表示触键注册
					List<Map<String, Object>> list2 = gameService.isRegistered(
							uid, "0" );
					String phonenum = null;
					if ( null != list2 && list2.size() > 0 ) {
						phonenum = list2.get( 0 ).get( "C_PHONENUM" )
								.toString();
					}
					// 保存第三方数据
					gameService.bindExternal( uid, regid, nickname, logintype,
							phonenum, sex, age, headimage );
					reqMap.put( "info", "1016" );
				}
				reqMap.put( "status", "Y" );
			}
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.bindExternal failed,e : " + e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}

	// 解绑第三方账号
	public String unBindExternal() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// 获取请求参数
		String uid = this.getParameter( "uid" );
		String logintype = this.getParameter( "logintype" );
		log.info( "into GameAction.unBindExternal" );
		log.info( "uid = " + uid + " ,logintype = " + logintype );
		try {
			if ( StrUtils.isEmpty( uid ) || StrUtils.isEmpty( logintype ) ) {
				reqMap.put( "status", "N" );
				reqMap.put( "info", "1001" );
			} else {
				// 查询当前第三方账号是否是第一次登录的账号
				String state = gameService.isFirstInfo( uid, logintype );
				if ( "1".equals( state ) ) {
					reqMap.put( "status", "N" );
					reqMap.put( "info", "1018" );
				} else {
					// 解除绑定
					gameService.unBindExternal( uid, logintype );
					reqMap.put( "status", "Y" );
					reqMap.put( "info", "1016" );
				}
			}
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.unBindExternal failed,e : " + e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}

	// 用户注册
	public String register() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// 获取请求参数
		String imei = this.getParameter( "imei" );
		String phonenum = this.getParameter( "phonenum" );
		String pwd = this.getParameter( "pwd" );
		String valicode = this.getParameter( "valicode" );
		String type = this.getParameter( "type" );
		log.info( "into GameAction.register" );
		log.info( "imei = " + imei + ",phonenum = " + phonenum + ",pwd = "
				+ pwd + ",valicode = " + valicode + ",type = " + type );
		try {
			if ( StrUtils.isEmpty( imei ) || StrUtils.isEmpty( phonenum )
					|| StrUtils.isEmpty( pwd ) || StrUtils.isEmpty( valicode )
					|| StrUtils.isEmpty( type ) ) {
				reqMap.put( "status", "N" );
				reqMap.put( "info", "1001" );
				out = JSONObject.fromObject( reqMap ).toString();
				return "success";
			} else {
				// 获取30min之前的时间
				String s_date = ApDateTime.dateAdd( "mm", -30,
						new java.util.Date(), ApDateTime.DATE_TIME_Sec );
				// 校验验证码是否可用
				List<Map<String, Object>> list = gameService.isUsableCode(
						phonenum, valicode, s_date, type );
				if ( list.isEmpty() && list.size() < 1 ) {
					reqMap.put( "status", "N" );
					reqMap.put( "info", "1011" );
					out = JSONObject.fromObject( reqMap ).toString();
					return "success";
				}
				// 注册
				/** 2015-5-20 修改 ： 注册不上传头像 */
				/** 2015-8-11 修改 ： 保存密码之前进行MD5加密 */
				String regid = gameService.register( phonenum,
						MD5.md5Code( pwd ) );
				// 拼接完整的注册id
				regid = "ICJ" + regid;
				// 获取用户id
				String uid = gameService.getUid( regid, "0" );
				// 生成默认昵称
				gameService.defaultNickname( "ICJ" + uid, regid );

				// 新增注册用户IMEI信息
				gameService.RegImei( regid, imei, valicode, new Date(), uid );

				reqMap.put( "status", "Y" );
				reqMap.put( "regid", regid );
				reqMap.put( "uid", uid );
				reqMap.put( "info", "1012" );
			}
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.register failed,e : " + e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}

	// 手机号登录
	public String login() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String phonenum = getParameter( "phonenum" );
		String imei = getParameter( "imei" );
		String pwd = getParameter( "pwd" );
		log.info( "into GameAction.login" );
		log.info( "phonenum=" + phonenum + ", imei=" + imei + ", pwd=" + pwd );
		try {
			if ( StrUtils.isEmpty( phonenum ) || StrUtils.isEmpty( pwd )
					|| StrUtils.isEmpty( imei ) ) {
				reqMap.put( "status", "N" );
				reqMap.put( "info", "1001" );
			} else {
				/** 2015-8-11 修改 ： 密码进行MD5加密 */
				List<Map<String, Object>> user = gameService.login( phonenum,
						MD5.md5Code( pwd ) );
				if ( user.size() > 0 ) {
					reqMap.put( "status", "Y" );
					user.get( 0 ).put( "C_PASSWORD", pwd );// 返回前台的密码不使用加密
					reqMap.put( "userinfo", user );
					Map<String, Object> m = user.get( 0 );
					// 获取触键注册用户与Imei对应关系记录
					List<Map<String, Object>> userImei = gameService
							.getMemberImei( m.get( "C_REGID" ).toString(), imei );
					if ( userImei.size() > 0 ) {
						gameService.updateIsLogin( m.get( "C_REGID" )
								.toString(), imei ); // 当前为最新
					} else {
						// 新增触键注册用户登录记录（IMEI）
						gameService.insertImei( m.get( "C_ID" ).toString(), m
								.get( "C_REGID" ).toString(), imei );
					}
					gameService.updateIsNotLogin(
							m.get( "C_REGID" ).toString(), imei ); // 修改其他不是最新
				} else {
					reqMap.put( "status", "N" );
					reqMap.put( "info", "1008" );
				}
			}
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.login failed,", e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}

	// 修改密码
	public String modifyPwd() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String uid = getParameter( "uid" );
		String opwd = getParameter( "opwd" );
		String npwd = getParameter( "npwd" );
		log.info( "into GameAction.modifyPwd" );
		log.info( "uid=" + uid + ", opwd=" + opwd + ", npwd=" + npwd );
		try {
			if ( StrUtils.isEmpty( uid ) || StrUtils.isEmpty( opwd )
					|| StrUtils.isEmpty( npwd ) ) {
				reqMap.put( "status", "N" );
				reqMap.put( "info", "1001" );
			} else {
				/** 2015-8-11 修改 ： 密码进行MD5加密 */
				opwd = MD5.md5Code( opwd );
				npwd = MD5.md5Code( npwd );
				// 根据用户id和原密码查询用户
				List<Map<String, Object>> list = gameService.findUserByUidNPwd(
						uid, opwd );
				if ( null != list && list.size() > 0 ) {
					// 如果正确，修改新密码
					gameService.modifyPwd( uid, opwd, npwd );
					reqMap.put( "status", "Y" );
				} else {
					reqMap.put( "status", "N" );
					reqMap.put( "info", "1009" );
				}
			}
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.login failed,", e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}

	// 编辑用户信息（只修改昵称和性别）
	public String editUserInfo() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		String uid = getParameter( "uid" );
		String nickname = getParameter( "nickname" );
		String sex = getParameter( "sex" );
		log.info( "into GameAction.editUserInfo" );
		log.info( "uid=" + uid + ", nickname=" + nickname + ", sex=" + sex );
		try {
			if ( StrUtils.isEmpty( uid ) || StrUtils.isEmpty( nickname )
					|| StrUtils.isEmpty( sex ) ) {
				retMap.put( "status", "N" );
				retMap.put( "info", "1001" );
			} else {
				// 修改
				gameService.editUserInfo( uid, nickname, sex );
				retMap.put( "status", "Y" );
			}
		} catch ( Exception e ) {
			retMap.put( "status", "N" );
			retMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.editUserInfo failed,", e );
		}
		out = JSONObject.fromObject( retMap ).toString();
		return "success";
	}

	// 绑定手机
	public String bindPhone() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String uid = getParameter( "uid" );
		String logintype = getParameter( "logintype" );
		String type = getParameter( "type" );
		String phonenum = getParameter( "phonenum" );
		String valicode = getParameter( "valicode" );
		String pwd = getParameter( "pwd" );
		log.info( "into GameAction.bindPhone" );
		log.info( "uid=" + uid + ", logintype=" + logintype + ", type=" + type
				+ ", phonenum=" + phonenum + ", valicode=" + valicode
				+ " ,pwd=" + pwd );
		try {
			if ( StrUtils.isEmpty( uid ) || StrUtils.isEmpty( logintype )
					|| StrUtils.isEmpty( type ) || StrUtils.isEmpty( phonenum )
					|| StrUtils.isEmpty( valicode ) ) {
				reqMap.put( "status", "N" );
				reqMap.put( "info", "1001" );
			} else {
				// 注册过的手机无法绑定
				List<Map<String, Object>> reglist = gameService
						.findByPhone( phonenum );
				if ( null != reglist && reglist.size() > 0 ) {
					reqMap.put( "status", "N" );
					reqMap.put( "info", "1010" );
					out = JSONObject.fromObject( reqMap ).toString();
					return "success";
				}
				// 获取30min之前的时间
				String s_date = ApDateTime.dateAdd( "mm", -30,
						new java.util.Date(), ApDateTime.DATE_TIME_Sec );
				// 校验验证码是否可用
				List<Map<String, Object>> list = gameService.isUsableCode(
						phonenum, valicode, s_date, type );
				if ( list.isEmpty() && list.size() < 1 ) {
					reqMap.put( "status", "N" );
					reqMap.put( "info", "1011" );
					out = JSONObject.fromObject( reqMap ).toString();
					return "success";
				}
				if ( StrUtils.isNotEmpty( pwd ) ) {// 密码不为空，绑定手机
					// 第三方绑定手机号
					/** 2015-8-11 修改 ： 密码进行MD5加密 */
					pwd = MD5.md5Code( pwd );
					gameService.bindPhone( uid, logintype, phonenum, pwd );
					// 生成logintype=0的信息（类似触键注册信息）
					gameService.chujianUser( uid, "0", phonenum, pwd, "ICJ"
							+ uid );
				} else {// 密码为空，修改手机
					gameService.updatePhonenum( uid, phonenum );
				}
				reqMap.put( "status", "Y" );

			}
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.bindPhone failed,", e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}

	// 获取验证码
	public String getValiCode() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		String imei = getParameter( "imei" );
		String phonenum = getParameter( "phonenum" );
		String type = getParameter( "type" );
		log.info( "into GameAction.getValiCode" );
		log.info( "imei=" + imei + ", phonenum=" + phonenum + ", type=" + type );
		try {
			if ( StrUtils.isEmpty( imei ) || StrUtils.isEmpty( phonenum )
					|| StrUtils.isEmpty( type ) ) {
				retMap.put( "status", "N" );
				retMap.put( "info", "1001" );
			} else {
				if ( type.equals( "2" ) ) { // 修改密码 没有注册 则不发送验证码
					List<Map<String, Object>> list = gameService
							.findByPhone( phonenum );
					if ( list.size() < 1 ) {
						retMap.put( "status", "N" );
						retMap.put( "info", "1013" );
						out = JSONObject.fromObject( retMap ).toString();
						return "success";
					}
				}
				if ( type.equals( "1" ) ) { // 注册 手机号是否已经注册过
					List<Map<String, Object>> list = gameService
							.findByPhone( phonenum );
					if ( list.size() > 0 ) {
						retMap.put( "status", "N" );
						retMap.put( "info", "1010" );
						out = JSONObject.fromObject( retMap ).toString();
						return "success";
					}
				}
				// 格式化日期："年-月-日"
				SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
				// 获取当天发送的验证码
				List<Map<String, Object>> list = gameService.getValiCode(
						phonenum, type, sdf.format( new Date() ) );
				if ( list.size() >= 5 ) {
					retMap.put( "status", "N" );
					retMap.put( "info", "1015" );
					out = JSONObject.fromObject( retMap ).toString();
					return "success";
				} else {
					String host = FileUtil.getSmspath( "sms.zt.sender.host" );
					String username = FileUtil
							.getSmspath( "sms.zt.sender.username" );
					String password = FileUtil
							.getSmspath( "sms.zt.sender.password" );
					String productid = FileUtil
							.getSmspath( "sms.zt.sender.productid" );
					String sendnumber = FileUtil
							.getSmspath( "sms.zt.sender.sendnumber" );
					// 获取6位随机数验证码
					String code = RandNumber.getRandNumber( 6 );
					com.cyberoller.sms.MessageSender messageSender = new MessagePostSender(
							host, username, password, productid );
					String message = "亲爱的触键用户：手机验证码为" + code
							+ "，请提交完成验证。如非本人操作，请忽略本短信。【触键】";
					messageSender.send( phonenum, message ); // 发送验证码
					gameService.invalidatedOther( phonenum, type ); // 修改其他验证码状态
					gameService.insertValicode( imei, phonenum, type,
							new Date(), code );// 保存本次发送的验证码
					retMap.put( "status", "Y" );
					retMap.put( "sendnumber", sendnumber );
				}
			}
		} catch ( Exception e ) {
			retMap.put( "status", "N" );
			retMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.getValiCode failed,", e );
		}
		out = JSONObject.fromObject( retMap ).toString();
		return "success";
	}

	// 忘记密码-判断验证码的有效性
	public String isValidCode() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String phonenum = getParameter( "phonenum" );
		String valicode = getParameter( "valicode" );
		String type = getParameter( "type" );
		String imei = getParameter( "imei" );
		log.info( "into GameAction.isValidCode" );
		log.info( "phonenum=" + phonenum + ", valicode=" + valicode + ", type="
				+ type + ", imei=" + imei );
		try {
			if ( StrUtils.isEmpty( valicode ) || StrUtils.isEmpty( phonenum )
					|| StrUtils.isEmpty( type ) || StrUtils.isEmpty( imei ) ) {
				reqMap.put( "status", "N" );
				reqMap.put( "info", "1001" );
			} else {
				// 获取30min之前的时间
				String s_date = ApDateTime.dateAdd( "mm", -30,
						new java.util.Date(), ApDateTime.DATE_TIME_Sec );
				// 校验验证码是否可用
				List<Map<String, Object>> code = gameService.isUsableCode(
						phonenum, valicode, s_date, type );
				if ( code.isEmpty() && code.size() < 1 ) {
					reqMap.put( "status", "N" );
					reqMap.put( "info", "1011" );
					out = JSONObject.fromObject( reqMap ).toString();
					return "success";
				}
				// 查询手机号是否存在
				List<Map<String, Object>> user = gameService
						.findByPhone( phonenum );
				if ( user.size() > 0 ) {
					reqMap.put( "status", "Y" );
				} else {
					reqMap.put( "status", "N" );
					reqMap.put( "info", "1013" );
				}
				// 修改验证码状态为0
				gameService.invalidatedOther( phonenum, type );
			}
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.isValidCode failed,", e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}

	// 忘记密码-修改密码
	public String setPwdOfFgt() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String phonenum = getParameter( "phonenum" );
		String pwd = getParameter( "pwd" );
		log.info( "into GameAction.setPwdOfFgt" );
		log.info( "phonenum=" + phonenum + ", pwd=" + pwd );
		try {
			if ( StrUtils.isEmpty( phonenum ) || StrUtils.isEmpty( pwd ) ) {
				reqMap.put( "status", "N" );
				reqMap.put( "info", "1001" );
			} else {
				// 判断手机号是否已注册
				List<Map<String, Object>> list = gameService
						.findByPhone( phonenum );
				if ( list.size() > 0 ) {
					/** 2015-8-11 修改 ： 密码进行MD5加密 */
					pwd = MD5.md5Code( pwd );
					// 设置新密码
					gameService.setNewPwd( phonenum, pwd );
					reqMap.put( "status", "Y" );
					reqMap.put( "info", "1014" );
				} else {
					reqMap.put( "status", "Y" );
					reqMap.put( "info", "1013" );
				}
			}
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.setPwdOfFgt failed,", e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}

	// 修改用户头像
	public String userHead() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		String uid = getParameter( "uid" );
		InputStream userhead = getParameter3( "userhead" );// 将请求参数中的userhead读到流中
		log.info( "into GameAction.userHead" );
		log.info( "uid=" + uid );
		try {
			if ( StrUtils.isEmpty( uid ) ) {
				retMap.put( "status", "N" );
				retMap.put( "info", "1001" );
			} else {
				// String timestamp = "_" + System.currentTimeMillis();
				// 上传图片，并返回图片路径
				String fileurl = FileServices.saveHeadFile( userhead,
						"game/image/userHead/" + uid + ".png" );
				// 将头像路径保存到
				gameService.updateHead( uid, fileurl );
				retMap.put( "status", "Y" );
				retMap.put( "userhead", fileurl );
			}
		} catch ( Exception e ) {
			retMap.put( "status", "N" );
			retMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.userHead failed,", e );
		}
		out = JSONObject.fromObject( retMap ).toString();
		return "success";
	}

	// 获取游戏提示语
	public String gameCue() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// 获取用户id
		String uid = getParameter( "uid" );
		// 获取登陆状态
		String loginType = getParameter( "loginType" );
		log.info( "into GameAction.gameCue" );
		log.info( "uid=" + uid );
		try {
			if ( StrUtils.isEmpty( uid ) ) {
				reqMap.put( "status", "N" );
				reqMap.put( "info", "1001" );
			}
			Map<String, Object> item = new HashMap<String, Object>();
			// 获取游戏提示语
			List<Map<String, Object>> gameCue = gameService.gameCue();
			if ( null != gameCue && gameCue.size() > 0 ) {
				item.put( "C_TITLE", gameCue.get( 0 ).get( "C_TITLE" ) );
			} else {
				item.put( "C_TITLE", "" );
			}
			// 获取用户头像
			List<Map<String, Object>> headImg = gameService.userHeadImg( uid,
					loginType );
			if ( null != headImg && headImg.size() > 0 ) {
				Map<String, Object> map = headImg.get( 0 );
				if ( StrUtils.isNotEmpty( map.get( "C_NICKNAME" ) )
						&& !"null".equalsIgnoreCase( map.get( "C_NICKNAME" )
								.toString() ) ) {
					item.put( "C_NICKNAME", map.get( "C_NICKNAME" ) );
				} else if ( StrUtils.isNotEmpty( map.get( "C_REGID" ) ) ) {
					item.put( "C_NICKNAME", map.get( "C_REGID" ) );
				} else if ( StrUtils.isNotEmpty( map.get( "C_PHONENUM" ) ) ) {
					item.put( "C_NICKNAME", map.get( "C_PHONENUM" ) );
				} else {
					item.put( "C_NICKNAME", "" );
				}
				item.put( "C_HEADIMAGE", map.get( "C_HEADIMAGE" ) );
			} else {
				item.put( "C_NICKNAME", "" );
				item.put( "C_HEADIMAGE", "" );
			}
			reqMap.put( "gameCue", item );
			reqMap.put( "status", "Y" );

		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAction.editUserInfo failed,", e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
    }
    
    // 一键登录
   /* public String quickLogin(){
    	Map<String, Object> reqMap = new HashMap<String, Object>();
    	// 获取请求参数
    	String imei = getParameter("imei");
		log.info("into quickLogin");
		log.info("imei=" + imei);
		try {
			if(StrUtils.isEmpty(imei)){
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
	    	}else{
	    		// 判断是否使用过“一键登录”功能
	    		String uid = gameService.isLogined(imei);
	    		if(StrUtils.isEmpty(uid)){// 如果未使用过
	    			// 获取注册id
	    			List<Map<String, Object>> list = gameService.getRegid();
	    			if(null!=list && list.size()>0){
	    				String regid = list.get(0).get("REGID").toString();
	    				regid = "ICJ" + regid;
	    				// 生成新的游客信息，返回用户id
	    				uid = gameService.quickLogin(regid, "4");
	    				// 新增游客登录记录
	    				gameService.quickLoginImei(regid,imei,uid);
	    			}
	    		}
	    		reqMap.put("status", "Y");
	    		reqMap.put("uid", uid);
	    	}
			
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003, " + e.getMessage());
			log.error("UserAction.quickLogin failed,", e);
		}
		out = JSONObject.fromObject(reqMap).toString();
		return "success";
    }*/
    
    // 根据合集id获取h5游戏合集
    public String h5GameCollection(){
    	Map<String, Object> reqMap = new HashMap<String, Object>();
		// 获取请求参数
		String cid = this.getParameter( "cid" );// 合集id
		String pageindex = this.getParameter( "pageindex" );// 页码
		String callback = this.getParameter( "callback" );// 页码
		String pageSize = getParameter( "pageSize" );
		
		if(StringUtil.isEmpty( pageSize )) {
			pageSize = "20";
		}
		log.info( "into GameAction.h5GameCollection" );
		log.info( "cid = " + cid + ", pageindex = " + pageindex );
		try {
			if ( StrUtils.isEmpty( cid ) ) {
				reqMap.put( "status", "N" );
				reqMap.put( "info", "1001" );
			} else {
				// 分页查询单个合集数据
				List<Map<String, Object>> list = gameService.h5GameCollection(
						cid, pageindex,pageSize );
				reqMap.put( "status", "Y" );
				reqMap.put( "games", list );
			}
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003, " + e.getMessage());
			log.error("GameAction.h5GameCollection failed,e : ", e);
		}
		out = JSONObject.fromObject(reqMap).toString();
		if(StrUtils.isNotEmpty(callback)){
			out = callback + "(" + out + ")"; 
		}
		//log.info("out GameCollectionAction.gameCollection, reqMap : " + reqMap);
		return "success";
    }
    
    // H5广告位数据
 	public String h5AdvertInfo() {
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		log.info("into GameAction.h5AdvertInfo");
 		try {
 			List<Map<String, Object>> list = gameService.h5AdvertInfo();
 			reqMap.put("status", "Y");
 			reqMap.put("advert", list);
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameAction.h5AdvertInfo failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
    
 	// 获取所有游戏礼包
 	public String gameGiftList(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
		String uid = this.getParameter("uid");// 用户id
		String pageIndex = this.getParameter("pageIndex");// 页码
 		log.info("into GameAction.gameGiftList");
 		log.info("uid = " + uid + ", pageIndex = " + pageIndex);
 		try {
 			if (StrUtils.isEmpty(pageIndex)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			List<Map<String, Object>> list = gameService.gameGiftList(uid, pageIndex);
	 			reqMap.put("status", "Y");
	 			reqMap.put("list", list);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameAction.gameGiftList failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// 获取单个游戏礼包
 	public String gameGift(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String uid = this.getParameter("uid");// 用户id
		String gid = this.getParameter("gid");// 游戏id
 		log.info("into GameAction.gameGift");
 		log.info("uid = " + uid + ", gid = " + gid);
 		try {
 			if (StrUtils.isEmpty(gid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			List<Map<String, Object>> list = gameService.gameGift(uid, gid);
	 			reqMap.put("status", "Y");
	 			reqMap.put("gift", list);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameAction.gameGift failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// 礼包领取
 	public String getGift(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String uid = this.getParameter("uid");// 用户id
		String gid = this.getParameter("gid");// 礼包id
 		log.info("into GameAction.getGift");
 		log.info("uid = " + uid + ", gid = " + gid);
 		try {
 			if (StrUtils.isEmpty(gid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				String code = "";
				// 查询当前用户是否已经领取过礼包码，如果是，则返回礼包码
				String usersCode = gameService.usersGiftCode(uid, gid);
				if(StrUtils.isNotEmpty(usersCode)){
					code = usersCode;
				}else{
		 			// 获取一条礼包码
		 			List<Map<String, Object>> list = gameService.getGiftCode(gid);
		 			if(StrUtils.isNotEmpty(list)){
		 				// 如果获取成功，修改该礼包码状态
		 				Map<String, Object> map = list.get(0);
		 				code = map.get("C_CODE").toString();
		 				String cid = map.get("C_ID").toString();
		 				gameService.updateGiftCode(cid);
		 			}
		 			// 添加用户对礼包的操作行为
		 			gameService.addUserActionOfGift(uid,gid,"1",code);
				}
				reqMap.put("status", "Y");
				reqMap.put("code", code);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameAction.getGift failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// 礼包详情
 	public String giftDetail(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String uid = this.getParameter("uid");// 用户id
		String gid = this.getParameter("gid");// 礼包id
 		log.info("into GameAction.giftDetail");
 		log.info("uid = " + uid + ", gid = " + gid);
 		try {
 			if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(gid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			// 获取一条礼包码
	 			List<Map<String, Object>> list = gameService.giftDetail(uid, gid);
	 			reqMap.put("status", "Y");
	 			reqMap.put("gift", list);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameAction.giftDetail failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// 礼包淘号
 	public String drawGiftCode(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String uid = this.getParameter("uid");// 用户id
		String gid = this.getParameter("gid");// 礼包id
 		log.info("into GameAction.drawGiftCode");
 		log.info("uid = " + uid + ", gid = " + gid);
 		try {
 			if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(gid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				String code = "";
				// 礼包淘号
				List<Map<String, Object>> list = gameService.drawGiftCode(gid);
				if(StrUtils.isNotEmpty(list)){
					Map<String, Object> map = list.get(0);
					code =  map.get("C_CODE").toString();
					// 更新用户礼包操作行为
					gameService.addUserActionOfGift(uid,gid,"2",code);
				}
				reqMap.put("status", "Y");
				reqMap.put("code", code);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameAction.drawGiftCode failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// "我的礼包"
 	public String usersGifts(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String uid = this.getParameter("uid");// 用户id
 		log.info("into GameAction.usersGifts");
 		log.info("uid = " + uid);
 		try {
 			if (StrUtils.isEmpty(uid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			// 获取当前用户的礼包
	 			List<Map<String, Object>> list = gameService.usersGifts(uid);
	 			reqMap.put("status", "Y");
	 			reqMap.put("gift", list);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameAction.usersGifts failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// H5游戏弹幕
 	public String h5Barrage(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String uid = this.getParameter("uid");// 用户id
		String gid = this.getParameter("gid");// 礼包id
		String type = this.getParameter("type");// 操作类型  0：下载    1：卸载  2：启动   3：查看 4：退出 5：发弹幕
		String source = this.getParameter("source");// 操作内容：0： app游戏  1：h5游戏
		String comment = this.getParameter("comment");// 弹幕内容
 		log.info("into GameAction.h5Barrage");
 		log.info("uid = " + uid + ", gid = " + gid + ", type = "
 				+ type + ", source = " + source + ", comment = " + comment);
 		try {
 			if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(gid) || 
				StrUtils.isEmpty(type) || StrUtils.isEmpty(source) || 
				StrUtils.isEmpty(comment)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				// 保存用户行为
	 			gameService.saveH5Barrage(uid, gid, type, source);
	 			// 推送消息
	 			miPushService.sendMessage(comment, gid);
//	 			Result r = miPushService.sendMessage(comment, gid);
//	 			log.info("r---------" + r.getData().toString());
	 			reqMap.put("status", "Y");
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameAction.h5Barrage failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// 校验手机号是否可用
 	public String validatePhone(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String phoneNum = this.getParameter("phoneNum");
 		log.info("into GameAction.validatePhone");
 		log.info("phoneNum = " + phoneNum);
 		try {
 			if (StrUtils.isEmpty(phoneNum)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			// 校验
				List<Map<String,Object>> list = gameService.findByPhone(phoneNum);
	 			if(null!=list && list.size()>0){
	 				reqMap.put("info", "1010");// 不可用
	 				reqMap.put("status", "N");
	 			}else{
	 				reqMap.put("status", "Y");
	 			}
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameAction.validatePhone failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// 根据JAR包名获取游戏LOGO
 	public String getLogo(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String jarName = this.getParameter("jarName");
 		log.info("into GameAction.getLogo");
 		log.info("jarName = " + jarName);
 		try {
 			if (StrUtils.isEmpty(jarName)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			// 校验
				String logo = gameService.getLogo(jarName);
 				reqMap.put("logo", logo);
 				reqMap.put("status", "Y");
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameAction.getLogo failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// 礼包推荐
 	public String recommendGift(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String uid = this.getParameter("uid");
 		log.info("into GameAction.recommendGift");
 		log.info("uid = " + uid);
 		try {
 			if (StrUtils.isEmpty(uid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			// 礼包推荐
				List<Map<String, Object>> list = gameService.recommendGift(uid);
 				reqMap.put("list", list);
 				reqMap.put("status", "Y");
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameAction.recommendGift failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// 最近在玩的游戏
 	public String recentlyPlaying(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String uid = this.getParameter("uid");
 		String type = this.getParameter("type");// 1:首页显示的8个，2:全部
 		log.info("into GameAction.recentlyPlaying");
 		log.info("uid = " + uid + "type" + type);
 		try {
 			if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(uid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			// 最近在玩的游戏
				List<Map<String, Object>> list = gameService.recentlyPlaying(uid, type);
 				reqMap.put("list", list);
 				reqMap.put("status", "Y");
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameAction.recentlyPlaying failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// 游戏分类列表  
 	public String categoryList(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		log.info("into GameAction.categoryList");
 		try {
 			// 游戏分类列表
			List<Map<String, Object>> list = gameService.categoryList();
			reqMap.put("list", list);
			reqMap.put("status", "Y");
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameAction.categoryList failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// h5游戏分类列表  
  	public String h5CategoryList(){
  		Map<String, Object> reqMap = new HashMap<String, Object>();
  		log.info("into GameAction.h5CategoryList");
  		try {
  			// h5游戏分类列表
 			List<Map<String, Object>> list = gameService.h5CategoryList();
 			reqMap.put("list", list);
 			reqMap.put("status", "Y");
  		} catch (Exception e) {
  			reqMap.put("status", "N");
  			reqMap.put("info", "1003," + e.getMessage());
  			log.error("GameAction.h5CategoryList failed,e : " + e);
  		}
  		out = JSONObject.fromObject(reqMap).toString();
  		return "success";
  	}
 	
  	// 根据分类id获取具体某一分类中的游戏
   	public String gamesInCategory(){
   		Map<String, Object> reqMap = new HashMap<String, Object>();
   		// 获取请求参数
 		String cid = this.getParameter("cid");// 分类id
 		String pageindex = this.getParameter("pageindex");// 页码
 		log.info("into GameAction.gamesInCategory");
 		log.info("cid = " + cid + "pageindex" + pageindex);
   		try {
   			// 分类中的游戏
  			List<Map<String, Object>> list = gameService.gamesInCategory(cid,pageindex);
  			reqMap.put("list", list);
  			reqMap.put("status", "Y");
   		} catch (Exception e) {
   			reqMap.put("status", "N");
   			reqMap.put("info", "1003," + e.getMessage());
   			log.error("GameAction.gamesInCategory failed,e : " + e);
   		}
   		out = JSONObject.fromObject(reqMap).toString();
   		return "success";
   	} 
   	
   	// 根据分类id获取具体某一分类中的游戏(H5)
   	public String h5GamesInCategory(){
   		Map<String, Object> reqMap = new HashMap<String, Object>();
   		// 获取请求参数
   		String cid = this.getParameter("cid");// 分类id
   		String pageindex = this.getParameter("pageindex");// 页码
   		log.info("into GameAction.gamesInCategory");
   		log.info("cid = " + cid + "pageindex" + pageindex);
   		try {
   			// 分类中的游戏
   			List<Map<String, Object>> list = gameService.h5GamesInCategory(cid,pageindex);
   			reqMap.put("list", list);
   			reqMap.put("status", "Y");
   		} catch (Exception e) {
   			reqMap.put("status", "N");
   			reqMap.put("info", "1003," + e.getMessage());
   			log.error("GameAction.gamesInCategory failed,e : " + e);
   		}
   		out = JSONObject.fromObject(reqMap).toString();
   		return "success";
   	} 
  	
	// 游戏推荐
   	public String gameRecommend(){
   		Map<String, Object> reqMap = new HashMap<String, Object>();
 		log.info("into GameAction.gameRecommend");
   		try {
   			// 根据游戏id查询所在分类中推荐的游戏
  			List<Map<String, Object>> list = gameService.gameRecommend();
  			reqMap.put("list", list);
  			reqMap.put("status", "Y");
   		} catch (Exception e) {
   			reqMap.put("status", "N");
   			reqMap.put("info", "1003," + e.getMessage());
   			log.error("GameAction.gameRecommend failed,e : " + e);
   		}
   		out = JSONObject.fromObject(reqMap).toString();
   		return "success";
   	}
   	
   	// 游戏推荐(H5)
   	public String h5GameRecommend(){
   		Map<String, Object> reqMap = new HashMap<String, Object>();
 		log.info("into GameAction.h5GameRecommend");
   		try {
   			// 根据游戏id查询所在分类中推荐的游戏
  			List<Map<String, Object>> list = gameService.h5GameRecommend();
  			reqMap.put("list", list);
  			reqMap.put("status", "Y");
   		} catch (Exception e) {
   			reqMap.put("status", "N");
   			reqMap.put("info", "1003," + e.getMessage());
   			log.error("GameAction.h5GameRecommend failed,e : " + e);
   		}
   		out = JSONObject.fromObject(reqMap).toString();
   		return "success";
   	}
   	
	public GameService getGameService() {
		return gameService;
	}

	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public MiPushService getMiPushService() {
		return miPushService;
	}

	public void setMiPushService(MiPushService miPushService) {
		this.miPushService = miPushService;
	}
	
}
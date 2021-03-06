package com.sys.game.action;

import com.sys.ekey.task.service.EKTaskService;
import com.sys.game.service.*;
import com.sys.game.util.IGameConst;
import com.sys.util.ApDateTime;
import com.sys.util.JSONUtil;
import com.sys.util.StrUtils;
import net.sf.json.JSONObject;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * 趣游戏-游戏
 * @author Maryn
 *
 */
@Component
public class GameAppAction extends GameBaseAction {

	private static final long serialVersionUID = -6264606082896598785L;
	
	@Autowired
	private GameAppService gameAppService;
	
	@Autowired
	private GameActivityService gameActivityService;
	
	@Autowired
	private GameInfoService gameInfoService;

	@Autowired
	private GameGiftService gameGiftService;

	@Autowired
	private GameMallService gameMallService;


	@Resource
	private EKTaskService ekTaskService;

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

				map = gameAppService.getGameDetailByGameId( gid );
				l_activity = gameActivityService.listActivity( gid, 1, 2 ); // 活动列表
				l_information = gameInfoService.listInformation( gid, 1, 2 ); // 资讯列表

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
				result = gameAppService.userRating( uid, gid, date, grade, isLive );
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
	 * @Description: TODO 持久化用户行为
	 * @return
	 */
	public String persistentUserBehavior() {
		log.debug( "into persistentUserBehavior ..." );

		Map<String, Object> retMap = new HashMap<String, Object>();
		// 获取传入参数信息
		String sUid = getParameter( "uid" ), sGid = getParameter( "gid" ), sType = getParameter( "type" ), sDate = getParameter( "date" ), jarname = getParameter( "jarname" ), source = getParameter( "source" );
		String imei = getParameter("imei");
		long uid = 0L, gid = 0L; // 用户ID，游戏ID
		int type = 0; // 操作类型 0：下载 1：卸载 2：启动 3：查看 4：退出
		Date date = new Date(); // 操作时间
		int result = 0; // 表示操作返回接口，0失败，1成功

		if ( null == source || source.isEmpty() ) {
			source = "0"; // 操作内容：0： app游戏 1：攻略 2：活动 3：资讯 4：h5游戏
		}

		if(log.isInfoEnabled()) {
			log.info("imei : " + imei + "==");
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
				try {
					if(source.equals(0)){
                        if(type==0){
                            /**************通过趣游戏下载任意APP游戏**************/
                            ekTaskService.reward(StrUtils.emptyOrString(uid),"2","13",StrUtils.emptyOrString(gid));
                            /**************通过趣游戏下载指定APP游戏**************/
                            ekTaskService.reward(StrUtils.emptyOrString(uid),"3","20",StrUtils.emptyOrString(gid));
                        }else if(type==2){
                            /**************通过趣游戏启动任意APP游戏**************/
                            ekTaskService.reward(StrUtils.emptyOrString(uid),"2","14",StrUtils.emptyOrString(gid));
                            /**************通过趣游戏启动指定APP游戏**************/
                            ekTaskService.reward(StrUtils.emptyOrString(uid),"3","21",StrUtils.emptyOrString(gid));
                        }
                    }else if(source.equals(4)){
                        if(type==2){
                            /**************玩任意H5游戏1分钟**************/
                            ekTaskService.reward(StrUtils.emptyOrString(uid),"2","15",StrUtils.emptyOrString(gid));
                            /**************玩指定H5游戏**************/
                            ekTaskService.reward(StrUtils.emptyOrString(uid),"3","22",StrUtils.emptyOrString(gid));
                        }
                    }
				} catch (Exception e) {
					LOG.info("任务奖励出错！e : " + e.getMessage());
				}
				// 持久化用户行为
				result = gameAppService.persistentUserBehavior( uid, gid, date,
						type, jarname, source,imei );
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
				result = gameAppService.persistentUserBehavior( uid, gid, date,
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
				result = gameAppService.userComments( uid, gid, date, comment,
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

				list = gameAppService.starGameStatistics( gid );

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
					list = gameAppService.getThroughTheGameIdGameReviewList( gid,
							pageNumber, pageSize );
				else
					list = gameAppService.getThroughTheGameIdGameReviewList( gid,
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

				list = gameAppService.getUserRating( uid, gid );

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

				obj = gameAppService.isLike( id, uid, source );

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

				list = gameAppService.searchApp(uid, imei, content, date, type );

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
	 * @Description: 全局搜索
	 * @return
	 */
	public String search() {
		if ( log.isDebugEnabled() ) {
			log.debug( "into search..." );
		}
		// 查询设备IMEI，游戏ID，时间，用户ID，类型：0：游戏，1：礼包，2：活动，3：商品
		String imei = getParameter( "imei" ), gid = getParameter( "gid" ), sDate = getParameter( "date" ),sUid =
				getParameter( "uid" ), type = getParameter( "type" );
		List<Map<String, Object>> list = null;
		Map<String, Object> retMap = new HashMap<String, Object>();
		int uid = -1;

		if ( log.isDebugEnabled() ) {
			log.debug( "imei ->" + imei + " gid ->" + gid + " date ->"
					+ sDate );
		}

		if(StringUtil.isEmpty( type )) {
			type = "0";
		}

		if ( StringUtil.isEmpty( gid ) ) {
			retMap.put( IGameConst.STATUS, IGameConst.NO );
			retMap.put( IGameConst.INFO, IGameConst._1001 );
		} else {
			try {

				if(!isEmpty( sUid )) {
					uid = Integer.parseInt( sUid );
				}

				// 0：游戏，1：礼包，2：活动，3：商品
				if("0".equalsIgnoreCase(type)) {
					list = gameAppService.search(gid);
				} else if("1".equalsIgnoreCase(type)) {
					list = gameGiftService.gameGift(String.valueOf(uid),gid);
				} else if("2".equalsIgnoreCase(type)) {
					// 默认取500个活动，不可修改，假定该游戏下所有活动就这么多
					list = gameActivityService.listActivity(Long.valueOf(gid),1,500);
				} else if("3".equalsIgnoreCase(type)) {
					list = gameMallService.search(gid);
				}

				if ( null == list ) {
					list = new ArrayList<Map<String, Object>>();
				}

				if ( list.size() >= 1 ) {
					list = JSONUtil.clobToString( list );
				}

				retMap.put( "list", list );
				retMap.put( IGameConst.STATUS, IGameConst.YES);
				retMap.put(IGameConst.INFO, IGameConst._1002 );
			} catch ( Exception e ) {
				log.error( e.getMessage() );
				retMap.put( IGameConst.STATUS, IGameConst.NO);
				retMap.put(IGameConst.INFO, IGameConst._1003 );
			}
		}

		try {
			writeToResponse( JSONObject.fromObject( retMap ).toString() );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		}

		return NONE;
	}
	
	// 广告位数据
	public String advertInfo() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		log.info( "into GameAppAction.advertInfo" );
		try {
			List<Map<String, Object>> list = gameAppService.advertInfo();
			reqMap.put( "status", "Y" );
			reqMap.put( "advert", list );
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003," + e.getMessage() );
			log.error( "GameAppAction.advertInfo failed,e : " + e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}
	
	// 获取游戏合集
	public String gameCollection() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// 获取请求参数
		String cid = this.getParameter( "cid" );// 合集id
		String flag = this.getParameter( "flag" );// 显示的数量
		String pageindex = this.getParameter( "pageindex" );// 页码
		String pSize = this.getParameter( "pSize" );// 页码
		log.info( "into GameAppAction.gameCollection" );
		log.info( "cid = " + cid + ", pageindex = " + pageindex +
					", pSize = " + pSize);
		try {
			if ( StrUtils.isEmpty( cid ) ) {
				reqMap.put( "status", "N" );
				reqMap.put( "info", "1001" );
			} else {
				// 分页查询单个合集数据
				List<Map<String, Object>> list = gameAppService.
							gameCollection(cid, flag, pageindex, pSize);
				reqMap.put( "status", "Y" );
				reqMap.put( "games", JSONUtil.clobToString(list) );
			}
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAppAction.gameCollection failed,e : ", e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		// log.info("out GameCollectionAction.gameCollection, reqMap : " +
		// reqMap);
		return "success";
	}
	
	// 获取游戏APP信息（包名）
	public String gameInfo() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		log.info( "into GameAppAction.gameInfo" );
		try {
			List<Map<String, Object>> list = gameAppService.gameInfo();
			reqMap.put( "status", "Y" );
			reqMap.put( "gameInfo", list );
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003, " + e.getMessage() );
			log.error( "GameAppAction.gameInfo failed,e : ", e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}
	
	// 根据JAR包名获取游戏LOGO
 	public String getLogo(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String jarName = this.getParameter("jarName");
 		log.info("into GameAppAction.getLogo");
 		log.info("jarName = " + jarName);
 		try {
 			if (StrUtils.isEmpty(jarName)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			// 校验
				String logo = gameAppService.getLogo(jarName);
 				reqMap.put("logo", logo);
 				reqMap.put("status", "Y");
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameAppAction.getLogo failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// 游戏分类列表  
  	public String categoryList(){
  		Map<String, Object> reqMap = new HashMap<String, Object>();
  		log.info("into GameAppAction.categoryList");
  		try {
  			// 游戏分类列表
 			List<Map<String, Object>> list = gameAppService.categoryList();
 			reqMap.put("list", list);
 			reqMap.put("status", "Y");
  		} catch (Exception e) {
  			reqMap.put("status", "N");
  			reqMap.put("info", "1003," + e.getMessage());
  			log.error("GameAppAction.categoryList failed,e : " + e);
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
 		log.info("into GameAppAction.gamesInCategory");
 		log.info("cid = " + cid + "pageindex" + pageindex);
   		try {
   			// 分类中的游戏
  			List<Map<String, Object>> list = gameAppService.gamesInCategory(cid,pageindex);
  			reqMap.put("list", list);
  			reqMap.put("status", "Y");
   		} catch (Exception e) {
   			reqMap.put("status", "N");
   			reqMap.put("info", "1003," + e.getMessage());
   			log.error("GameAppAction.gamesInCategory failed,e : " + e);
   		}
   		out = JSONObject.fromObject(reqMap).toString();
   		return "success";
   	} 
   		
   	// 游戏推荐
   	public String gameRecommend(){
   		Map<String, Object> reqMap = new HashMap<String, Object>();
 		log.info("into GameAppAction.gameRecommend");
   		try {
   			// 根据游戏id查询所在分类中推荐的游戏
  			List<Map<String, Object>> list = gameAppService.gameRecommend();
  			reqMap.put("list", list);
  			reqMap.put("status", "Y");
   		} catch (Exception e) {
   			reqMap.put("status", "N");
   			reqMap.put("info", "1003," + e.getMessage());
   			log.error("GameAppAction.gameRecommend failed,e : " + e);
   		}
   		out = JSONObject.fromObject(reqMap).toString();
   		return "success";
   	}
}

package com.sys.game.action;

import com.sys.game.service.GameActivityService;
import com.sys.game.util.IGameConst;
import com.sys.util.JSONUtil;
import com.sys.util.StrUtils;

import net.sf.json.JSONObject;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 趣游戏-活动
 * @author Maryn
 *
 */
@Controller
public class GameActivityAction extends GameBaseAction {

	private static final long serialVersionUID = 348169546789719686L;

	@Autowired
	private GameActivityService gameActivityService;
	
	/**
	 * @Description: 通过游戏ID获取游戏活动列表
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

				list = gameActivityService.listActivity( gid, pageNumber, pageSize );

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
     * 活动列表
     * @return
     */
	public String eventsList() {
		if(log.isDebugEnabled()) {
			log.debug( "into eventsList ..." );
		}

        // 页码，每页显示数量
		String  sPageNumber = getParameter( "pageNumber" ),
				sPageSize = getParameter( "pageSize" );
		int pageNumber = 1, pageSize = 20; // 默认第一页，每页20条数据
		List<Map<String, Object>> list = null; // 存放游戏活动列表信息
		Map<String, Object> retMap = new HashMap<String, Object>();

        try {
            if ( !isEmpty( sPageNumber ) ) {
                pageNumber = Integer.parseInt( sPageNumber );
            }
            if ( !isEmpty( sPageSize ) ) {
                pageSize = Integer.parseInt( sPageSize );
            }

            list = gameActivityService.listActivity( pageNumber, pageSize );

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
     * 参加活动
     * @return
     */
	public String campusActivities() {
		// 用户id，活动id，回复内容，当前记录id标识
		String uid = getParameter("uid"),aid = getParameter("aid"), comment = getParameter("comment"),id = getParameter("id");
		// 回复图片数组
        String[] imgArr = getRequest().getParameterValues("imgArr");
		log.info("uid ==" + uid + "=== aid ==" + aid + "id===" + id);

		Map<String, Object> retMap = new HashMap<String, Object>();

		// 用户ID和活动ID是必须的
		if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(aid)) {
			retMap.put(IGameConst.STATUS, IGameConst.NO);
			retMap.put(IGameConst.INFO, IGameConst._1001);
		} else {
			try {
				gameActivityService.campusActivities(id, uid, aid, comment, imgArr);

				retMap.put(IGameConst.STATUS, IGameConst.YES);
				retMap.put(IGameConst.INFO, IGameConst._1002);
			} catch (Exception e) {
				log.error(e);
				retMap.put(IGameConst.STATUS, IGameConst.NO);
				retMap.put(IGameConst.INFO, IGameConst._1003);
			}
		}

		try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}

		return NONE;
	}

	/**
     * 活动详情
     * @return
     */
    public String eventDetails() {
        if(log.isDebugEnabled()) {
            log.debug( "into eventDetails ..." );
        }
        // 用户ID，活动ID
        String sAid = getParameter( "aid" ), uid = getParameter( "uid" );
        // 攻略
        final String source = "1";
        Map<String, Object> activity = null;
        Map<String, Object> reply = null;
        Map<String, Object> retMap = new HashMap<String, Object>();
        long id;

        if(StringUtil.isEmpty( uid )) {
            uid = "-1";
        }

        if ( StringUtils.isEmpty(sAid) ) {
            retMap.put( IGameConst.STATUS, IGameConst.NO );
            retMap.put( IGameConst.INFO, IGameConst._1001 );
        } else {
            try {
                id = Long.valueOf( sAid );

                // 获取活动信息
                activity = gameActivityService.getActivityById( id , uid, source);

                // 获取用户回复信息
                reply = gameActivityService.getReply(id, uid);

                activity = JSONUtil.clobToString( activity );

                retMap.put( "activity", activity );
                retMap.put( "reply", reply );
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
	
	public String getActivityById() {
		log.info( "into getActivityById ..." );
		String sId = getParameter( "id" ), uid = getParameter( "uid" ), source = getParameter( "source" );
		long id;
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

				obj = gameActivityService.getActivityById( id , uid, source);

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

	
	// 我的活动
	public String attendedAct(){
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// 获取请求参数
		String uid = this.getParameter("uid");
		String page = this.getParameter("page");// 页码
		String pSize = this.getParameter("pSize");// 每页显示数
		log.info("into GameUserAction.attendedAct");
		log.info("uid = " + uid + ", page = " + page + ", pSize = " + pSize);
		try {
			if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(page) ) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				// 查询用户信息
				List<Map<String, Object>> acts = gameActivityService.attendedAct(uid, page, pSize);
				reqMap.put("status", "Y");
				reqMap.put("acts", JSONUtil.clobToString(acts));
			}
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003," + e.getMessage());
			log.error("GameActivityAction.attendedAct failed,e : ", e);
		}
		// 转成JSON
		out = JSONObject.fromObject(reqMap).toString();
		return "success";
	}
}

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

import com.sys.game.service.GameGiftService;
import com.sys.game.util.IGameConst;
import com.sys.util.JSONUtil;
import com.sys.util.StrUtils;

/**
 * 趣游戏-礼包
 * @author Maryn
 *
 */
@Component
public class GameGiftAction extends GameBaseAction {

	private static final long serialVersionUID = -4609949027005045927L;

	@Autowired
	private GameGiftService gameGiftService;
	
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
			
			Map<String, Object> m = gameGiftService.giftsIndex(uid);

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
				list = gameGiftService.giftsCategoryDetail(uid, categoryId, pageNumber, pageSize );

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
	
	// 获取所有游戏礼包
 	public String gameGiftList(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
		String uid = this.getParameter("uid");// 用户id
		String pageIndex = this.getParameter("pageIndex");// 页码
 		log.info("into GameGiftAction.gameGiftList");
 		log.info("uid = " + uid + ", pageIndex = " + pageIndex);
 		try {
 			if (StrUtils.isEmpty(pageIndex)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			List<Map<String, Object>> list = gameGiftService.gameGiftList(uid, pageIndex);
	 			reqMap.put("status", "Y");
	 			reqMap.put("list", list);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameGiftAction.gameGiftList failed,e : " + e);
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
 		log.info("into GameGiftAction.gameGift");
 		log.info("uid = " + uid + ", gid = " + gid);
 		try {
 			if (StrUtils.isEmpty(gid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			List<Map<String, Object>> list = gameGiftService.gameGift(uid, gid);
	 			reqMap.put("status", "Y");
	 			reqMap.put("gift", list);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameGiftAction.gameGift failed,e : " + e);
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
 		log.info("into GameGiftAction.getGift");
 		log.info("uid = " + uid + ", gid = " + gid);
 		try {
 			if (StrUtils.isEmpty(gid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				String code = "";
				// 查询当前用户是否已经领取过礼包码，如果是，则返回礼包码
				List<Map<String, Object>> usersCode = gameGiftService.usersGiftCode(uid, gid);
				if(StrUtils.isNotEmpty(usersCode) && usersCode.size()>0){
					code = StrUtils.emptyOrString(usersCode.get(0).get("C_CODE"));
				}else{
		 			// 获取一条礼包码
		 			List<Map<String, Object>> list = gameGiftService.getGiftCode(gid);
		 			if(StrUtils.isNotEmpty(list)){
		 				// 如果获取成功，修改该礼包码状态
		 				Map<String, Object> map = list.get(0);
		 				code = map.get("C_CODE").toString();
		 				String cid = map.get("C_ID").toString();
		 				gameGiftService.updateGiftCode(cid);
		 			}
		 			// 添加用户对礼包的操作行为
		 			gameGiftService.addUserActionOfGift(uid,gid,"1",code);
				}
				reqMap.put("status", "Y");
				reqMap.put("code", code);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameGiftAction.getGift failed,e : " + e);
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
 		log.info("into GameGiftAction.giftDetail");
 		log.info("uid = " + uid + ", gid = " + gid);
 		try {
 			if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(gid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			// 获取一条礼包码
	 			List<Map<String, Object>> list = gameGiftService.giftDetail(uid, gid);
	 			reqMap.put("status", "Y");
	 			reqMap.put("gift", list);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameGiftAction.giftDetail failed,e : " + e);
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
 		log.info("into GameGiftAction.drawGiftCode");
 		log.info("uid = " + uid + ", gid = " + gid);
 		try {
 			if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(gid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				String code = "";
				// 礼包淘号
				List<Map<String, Object>> list = gameGiftService.drawGiftCode(gid);
				if(StrUtils.isNotEmpty(list)){
					Map<String, Object> map = list.get(0);
					code =  map.get("C_CODE").toString();
					// 更新用户礼包操作行为
					gameGiftService.addUserActionOfGift(uid,gid,"2",code);
				}
				reqMap.put("status", "Y");
				reqMap.put("code", code);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameGiftAction.drawGiftCode failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// "我的礼包"
 	public String usersGifts(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String uid = this.getParameter("uid");// 用户id
 		log.info("into GameGiftAction.usersGifts");
 		log.info("uid = " + uid);
 		try {
 			if (StrUtils.isEmpty(uid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			// 获取当前用户的礼包
	 			List<Map<String, Object>> list = gameGiftService.usersGifts(uid);
	 			reqMap.put("status", "Y");
	 			reqMap.put("gift", list);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("GameGiftAction.usersGifts failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// 礼包推荐
  	public String recommendGift(){
  		Map<String, Object> reqMap = new HashMap<String, Object>();
  		// 获取请求参数
  		String uid = this.getParameter("uid");
  		log.info("into GameGiftAction.recommendGift");
  		log.info("uid = " + uid);
  		try {
  			if (StrUtils.isEmpty(uid)) {
 				reqMap.put("status", "N");
 				reqMap.put("info", "1001");
 			} else {
 	 			// 礼包推荐
 				List<Map<String, Object>> list = gameGiftService.recommendGift(uid);
  				reqMap.put("list", list);
  				reqMap.put("status", "Y");
 			}
  		} catch (Exception e) {
  			reqMap.put("status", "N");
  			reqMap.put("info", "1003," + e.getMessage());
  			log.error("GameGiftAction.recommendGift failed,e : " + e);
  		}
  		out = JSONObject.fromObject(reqMap).toString();
  		return "success";
  	}
}

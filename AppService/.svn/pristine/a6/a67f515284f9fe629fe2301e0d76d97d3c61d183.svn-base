package com.sys.ekey.game.action;

import com.sys.ekey.game.service.EKGameGiftService;
import com.sys.game.util.IGameConst;
import com.sys.util.JSONUtil;
import com.sys.util.StrUtils;
import net.sf.json.JSONObject;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 趣游戏-礼包
 * @author Maryn
 *
 */
@Controller("eKGameGiftAction")
public class EKGameGiftAction extends EKGameBaseAction {

	private static final long serialVersionUID = -4609949027005045927L;

	@Autowired
	private EKGameGiftService eKGameGiftService;
	
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
			
			Map<String, Object> m = eKGameGiftService.giftsIndex(uid);

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
		int pageNumber = 1, pageSize = 50; // 默认第一页，每页20条数据
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
				list = eKGameGiftService.giftsCategoryDetail(uid, categoryId, pageNumber, pageSize );

				if ( null == list ) {
					list = new ArrayList<Map<String, Object>>();
				}

				if ( list.size() >= 1 ) {
					list = JSONUtil.clobToString( list );
				}

				retMap.put( "gifts", list );
				retMap.put( IGameConst.STATUS, IGameConst.YES );
				retMap.put( IGameConst.INFO, IGameConst._1002 );
			} catch ( Exception e ) {
				log.error( "分类下的礼包获取错误！", e );
				retMap.put( IGameConst.STATUS, IGameConst.NO );
				retMap.put( IGameConst.INFO, IGameConst._1003 );
			}
		}

		try {
			writeToResponse( JSONObject.fromObject( retMap ).toString() );
		} catch ( IOException e ) {
			log.error( "礼包数据回写错误！", e );
		}
		
		return NONE;
	}
	
	// 获取所有游戏礼包
 	public String gameGiftList(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
		String uid = this.getParameter("uid");// 用户id
		String pageIndex = this.getParameter("pageIndex");// 页码
 		log.info("into EKGameGiftAction.gameGiftList");
 		log.info("uid = " + uid + ", pageIndex = " + pageIndex);
 		try {
 			if (StrUtils.isEmpty(pageIndex)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			List<Map<String, Object>> list = eKGameGiftService.gameGiftList(uid, pageIndex);
	 			reqMap.put("status", "Y");
	 			reqMap.put("list", list);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("EKGameGiftAction.gameGiftList failed,e : " + e);
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
 		log.info("into EKGameGiftAction.gameGift");
 		log.info("uid = " + uid + ", gid = " + gid);
 		try {
 			if (StrUtils.isEmpty(gid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			List<Map<String, Object>> list = eKGameGiftService.gameGift(uid, gid);
	 			reqMap.put("status", "Y");
	 			reqMap.put("gift", list);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("EKGameGiftAction.gameGift failed,e : " + e);
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
 		log.info("into EKGameGiftAction.getGift");
 		log.info("uid = " + uid + ", gid = " + gid);
 		try {
 			if (StrUtils.isEmpty(gid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				String code = "";
				// 查询当前用户是否已经领取过礼包码，如果是，则返回礼包码
				List<Map<String, Object>> usersCode = eKGameGiftService.usersGiftCode(uid, gid);
				if(StrUtils.isNotEmpty(usersCode) && usersCode.size()>0){
					code = StrUtils.emptyOrString(usersCode.get(0).get("C_CODE"));
				}else{
		 			// 获取一条礼包码
		 			List<Map<String, Object>> list = eKGameGiftService.getGiftCode(gid);
		 			if(StrUtils.isNotEmpty(list)){
		 				// 如果获取成功，修改该礼包码状态
		 				Map<String, Object> map = list.get(0);
		 				code = map.get("C_CODE").toString();
		 				String cid = map.get("C_ID").toString();
		 				eKGameGiftService.updateGiftCode(cid);
		 			}
		 			// 添加用户对礼包的操作行为
		 			eKGameGiftService.addUserActionOfGift(uid,gid,"1",code);
				}
				reqMap.put("status", "Y");
				reqMap.put("code", code);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("EKGameGiftAction.getGift failed,e : " + e);
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
 		log.info("into EKGameGiftAction.giftDetail");
 		log.info("uid = " + uid + ", gid = " + gid);
 		try {
 			if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(gid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			// 获取一条礼包码
	 			List<Map<String, Object>> list = eKGameGiftService.giftDetail(uid, gid);
	 			reqMap.put("status", "Y");
	 			reqMap.put("gift", list);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("EKGameGiftAction.giftDetail failed,e : " + e);
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
 		log.info("into EKGameGiftAction.drawGiftCode");
 		log.info("uid = " + uid + ", gid = " + gid);
 		try {
 			if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(gid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				String code = "";
				// 礼包淘号
				List<Map<String, Object>> list = eKGameGiftService.drawGiftCode(gid);
				if(StrUtils.isNotEmpty(list)){
					Map<String, Object> map = list.get(0);
					code =  map.get("C_CODE").toString();
					// 更新用户礼包操作行为
					eKGameGiftService.addUserActionOfGift(uid,gid,"2",code);
				}
				reqMap.put("status", "Y");
				reqMap.put("code", code);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("EKGameGiftAction.drawGiftCode failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
 	
 	// "我的礼包"
 	public String usersGifts(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String uid = this.getParameter("uid");// 用户id
 		log.info("into EKGameGiftAction.usersGifts");
 		log.info("uid = " + uid);
 		try {
 			if (StrUtils.isEmpty(uid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
	 			// 获取当前用户的礼包
	 			List<Map<String, Object>> list = eKGameGiftService.usersGifts(uid);
	 			reqMap.put("status", "Y");
	 			reqMap.put("gift", list);
			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003," + e.getMessage());
 			log.error("EKGameGiftAction.usersGifts failed,e : " + e);
 		}
		try {
			writeToResponse(JSONObject.fromObject(reqMap).toString());
		} catch (Exception ex) {
			log.error("数据写出错误！", ex);
		}

		return NONE;
 	}
 	
 	// 礼包推荐
  	public String recommendGift(){
  		Map<String, Object> reqMap = new HashMap<String, Object>();
  		// 获取请求参数
  		String uid = this.getParameter("uid");
  		log.info("into EKGameGiftAction.recommendGift");
  		log.info("uid = " + uid);
  		try {
  			if (StrUtils.isEmpty(uid)) {
 				reqMap.put("status", "N");
 				reqMap.put("info", "1001");
 			} else {
 	 			// 礼包推荐
 				List<Map<String, Object>> list = eKGameGiftService.recommendGift(uid);
  				reqMap.put("list", list);
  				reqMap.put("status", "Y");
 			}
  		} catch (Exception e) {
  			reqMap.put("status", "N");
  			reqMap.put("info", "1003," + e.getMessage());
  			log.error("EKGameGiftAction.recommendGift failed,e : " + e);
  		}
  		out = JSONObject.fromObject(reqMap).toString();
  		return "success";
  	}
}

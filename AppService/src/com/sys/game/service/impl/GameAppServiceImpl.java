package com.sys.game.service.impl;

import com.sys.game.service.GameAppService;
import com.sys.game.service.GameBaseService;
import com.sys.util.FileUtil;
import com.sys.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@SuppressWarnings("deprecation")
public class GameAppServiceImpl extends GameBaseService implements GameAppService {

	private Logger log = Logger.getLogger( GameAppServiceImpl.class );
	
	@Override
	public Map<String, Object> getGameDetailByGameId(Long gid) {
		List<Map<String, Object>> list = _queryForList( "gameApp.getGameDetailByGameId", gid );
		
		if ( null == list || list.size() <= 0 )
			return new HashMap<String, Object>();

		return list.get( 0 );
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer userRating(Long uid, Long gid, Date date, Float grade,
			String isLive) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		Map<String, Object> _m = new HashMap<String, Object>();
		List<Long> ids = new ArrayList<Long>();
		
		parameterObject.put( "uid", uid );
		parameterObject.put( "gid", gid );
		parameterObject.put( "date", date );
		parameterObject.put( "grade", grade );
		parameterObject.put( "islive", isLive );
		
		ids.add( uid);
		
		_m.put( "gid", gid );
		_m.put( "uids", ids );
		
		try {
			List<Map<String,Object>> userGrade = getSqlMapClientTemplate().queryForList( "gameApp.getGrade", _m );
			
			if(null == userGrade || userGrade.size() <= 0)
			// 这边返回一个Object对象，可能为null， 所以假定语句执行不异常认为是成功，返回1.
			getSqlMapClientTemplate().insert( "gameApp.userRating",parameterObject );
			else
				getSqlMapClientTemplate().update( "gameApp.updateUserRating",parameterObject );

			return 1;
		} catch ( Exception e ) {
			log.error( e );
		}

		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer persistentUserBehavior(Long uid, Long gid, Date date,
			Integer type, String jarname,String source,String imei) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "uid", uid );
		parameterObject.put( "date", date );
		parameterObject.put( "type", type );
		parameterObject.put( "source", source );
		parameterObject.put( "imei", imei );

		try {
			if(gid == 0) { // 通过jar名获取游戏ID
				List<Map<String,Object>> l_gid = getSqlMapClientTemplate().queryForList( "gameApp.getGidByJarname", jarname );
				if( null == l_gid || l_gid.size() <= 0)
					return 0;
				gid = Long.parseLong( l_gid.get( 0 ).get( "C_ID" ).toString() );
			}
			
			parameterObject.put( "gid", gid );
			
			// 这边返回一个Object对象，可能为null， 所以假定语句执行不异常认为是成功，返回1.
			getSqlMapClientTemplate().insert( "gameApp.persistentUserBehavior",
					parameterObject );

			return 1;
		} catch ( Exception e ) {
			log.error( e );
		}

		return 0;
	}


	@SuppressWarnings("unchecked")
	@Override
	// 获取游戏APP信息（包名）
	public List<Map<String, Object>> gameInfo() {
		return this.getSqlMapClientTemplate().queryForList("gameApp.gameInfo");
	}

	@Override
	// 根据JAR包名获取游戏LOGO
	public String getLogo(String jarName) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("JARNAME", "%" + jarName + "%");
		return (String) this.getSqlMapClientTemplate().queryForObject("gameApp.getLogo", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 游戏分类列表
	public List<Map<String, Object>> categoryList() {
		return this.getSqlMapClientTemplate().queryForList("gameApp.categoryList");
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据分类id获取具体某一分类中的游戏
	public List<Map<String, Object>> gamesInCategory(String cid, String pageindex) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("CID", cid);
		paramMap.put("PAGE", Integer.parseInt(pageindex));
		List<Map<String, Object>> list = this.getSqlMapClientTemplate().queryForList("gameApp.gamesInCategory", paramMap);
		if(null!=list && list.size()>0){
			for (Map<String, Object> map : list) {
				String id = map.get("C_ID").toString();
				/** 下载量 */
				// 根据游戏id查询真实下载量
				String realDownload = (String) this.getSqlMapClientTemplate().queryForObject("gameApp.realDownload", id);
				// 如果真实下载量为空，设置值为0
				realDownload = StrUtils.isEmpty(realDownload) ? "0" : realDownload;
				map.put("C_DOWNLOAD", realDownload);
				/** 评分 */
				// 查询真实评分
				String realGrade = (String) this.getSqlMapClientTemplate().queryForObject("gameApp.realGrade", id);
				// 如果真实评分为空，设置值为0
				realGrade = StrUtils.isEmpty(realGrade) ? "0" : realGrade;
				map.put("C_GRADE", realGrade);
				/** 是否有礼包 */
				Integer has = (Integer) this.getSqlMapClientTemplate().queryForObject("gameApp.hasGift", id);
				if(has==0){
					map.put("C_HASGIFT", "0");
				}else{
					map.put("C_HASGIFT", "1");
				}
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据游戏id查询所在分类中推荐的游戏
	public List<Map<String, Object>> gameRecommend() {
		return this.getSqlMapClientTemplate().queryForList("gameApp.gameRecommend");
	}


	@SuppressWarnings("unchecked")
	@Override
	public Integer userComments(Long uid, Long gid, Date date, String comment,
			String isLive, float grade) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "uid", uid );
		parameterObject.put( "gid", gid );
		parameterObject.put( "date", date );
		parameterObject.put( "comment", comment.replaceAll( "\\n", "" ) );
		parameterObject.put( "islive", isLive );
		Map<String, Object> _m = new HashMap<String, Object>();
		List<Long> ids = new ArrayList<Long>();
		ids.add( uid);
		
		_m.put( "gid", gid );
		_m.put( "uids", ids );
		
		try {
			getSqlMapClientTemplate().queryForObject( "gameApp.procUserComments", parameterObject );
			
			if(grade != -1) {
				parameterObject.put( "grade", grade );
				List<Map<String,Object>> userGrade = getSqlMapClientTemplate().queryForList( "gameApp.getGrade", _m );
				
				if(null == userGrade || userGrade.size() <= 0)
				// 这边返回一个Object对象，可能为null， 所以假定语句执行不异常认为是成功，返回1.
				getSqlMapClientTemplate().insert( "gameApp.userRating",
						parameterObject );
				else
					getSqlMapClientTemplate().update( "gameApp.updateUserRating",
							parameterObject );
			}

			return 1;
		} catch ( Exception e ) {
			log.error( e );
		}

		return 0;
	}

	@Override
	public List<Map<String, Object>> starGameStatistics(Long gid) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "gid", gid );

		return _queryForList( "gameApp.starGameStatistics", parameterObject );
	}

	@Override
	public List<Map<String, Object>> getThroughTheGameIdGameReviewList(
			Long gid, Integer pageNumber, Integer pageSize) {
		int start = pageSize * (pageNumber - 1) + 1;
		int maxRows = start + pageSize - 1;
		List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
		Map<String, Object> param = null;
		List<Object> _uidList = new ArrayList<Object>(); 
		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		
		parameterObject.put( "gid", gid );
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );

		tempList = _queryForList( "gameApp.getThroughTheGameIdGameReviewList",  parameterObject);
		if(null != tempList) { // 如果评论列表有信息
			for ( int i = 0; i < tempList.size(); ++i ) {
				Map<String, Object> m = tempList.get( i );
				_uidList.add(m.get( "C_UID" )); // 其他用户ID
			}
		}
		
		if(_uidList.size() >= 1) {
			param = new HashMap<String, Object>();
			param.put( "gid", gid );
			param.put( "uids", _uidList ); //当前param中这时候有gid和uid集合
		}
		
		_processGrade(param, tempList, retList);
		
		return retList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getThroughTheGameIdGameReviewList(
			Long gid,Long uid, Integer pageNumber, Integer pageSize) {
		int start = pageSize * (pageNumber - 1) + 1;
		int maxRows = start + pageSize - 1;
		// 获取用户评论的参数对象
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		// 返回值
		List<Map<String, Object>> retList = new ArrayList<Map<String,Object>>();
		// 当前用户对该游戏的评论，理论上最多1条记录
		List<Map<String, Object>> list;
		//用户评论列表
		List<Map<String,Object>> _mapList;
		// 评分用户id列表
		List<Object> _uidList = new ArrayList<Object>(); 
		// 获取用户游戏评分参数对象
		Map<String,Object> param = new HashMap<String, Object>();
		// 临时列表对象，存放用户评论信息，用于和评分列表合并组成返回值
		List<Map<String, Object>> tempList = new ArrayList<Map<String,Object>>();
		
		parameterObject.put( "gid", gid ); // 游戏ID
		parameterObject.put( "uid", uid ); // 用户ID
		
		param.put( "gid", gid ); // 游戏 ID
		
		// 获取uid用户对gid游戏的评论信息
		// 理论上说，当获取第二页评论信息时，不需要再次查询，但是需要存储用户是否评论状态，和如果有评论的评论信息，这边简单处理
		list = getSqlMapClientTemplate().queryForList( "gameApp.getGameReviewsByUidAndGid", parameterObject );
		
		// 如果用户没有对当前游戏评论
		if(null == list || list.isEmpty() ) return getThroughTheGameIdGameReviewList( gid, pageNumber, pageSize );
		
		//retList.add( list.get(0) ); // 理论上只会有一条
		//retList.addAll( list ); // 但是数据没校验，现在可以有多条（一个用户对同一个游戏多次评分评论）
		tempList.add( list.get( 0 ) ); // 将当前用户评论列表放入临时列表
		
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows - 1 ); // 第一条显示是当前用户评论，分页少取一条
		
		_mapList = _queryForList( "gameApp.getThroughTheGameIdGameReviewList2",  parameterObject);
		if(null !=_mapList)
			tempList.addAll( _mapList ); //将其他用户评论列表追加到临时列表
		
		_uidList.add( uid ); // 当前用户ID
		if(null != _mapList) { // 如果评论列表有信息
			for ( int i = 0; i < _mapList.size(); ++i ) {
				Map<String, Object> m = _mapList.get( i );
				_uidList.add(m.get( "C_UID" )); // 其他用户ID
			}
		}
		
		param.put( "uids", _uidList ); //当前param中这时候有gid和uid集合
		
		_processGrade( param, tempList, retList );
		
		return retList;
	}

	@Override
	public List<Map<String, Object>> getUserRating(long uid, long gid) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		List<Long> ids = new ArrayList<Long>();
		ids.add( uid );
		parameterObject.put( "uids", ids );
		parameterObject.put( "gid", gid );
		
		return _queryForList( "gameApp.getGrade", parameterObject );
	}

	@Override
	public Map<String, Object> isLike(Long id, String uid, String source) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "id", id );
		parameterObject.put( "uid", uid );
		parameterObject.put( "source", source );
		
		List<Map<String, Object>> list = _queryForList( "gameApp.isLike", parameterObject );
		
		if ( null == list || list.size() <= 0 )
			return new HashMap<String, Object>();

		return list.get( 0 );
	}

	@Override
	public Integer persistentUserBehavior(Long uid, Long gid, Date date, Integer type, Long indexid, String source) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "uid", uid );
		parameterObject.put( "date", date );
		parameterObject.put( "type", type );
		parameterObject.put( "source", source );
		parameterObject.put( "indexid", indexid );

		try {
			parameterObject.put( "gid", gid );
			
			getSqlMapClientTemplate().insert( "gameApp.persistentUserBehavior2",
					parameterObject );

			return 1;
		} catch ( Exception e ) {
			log.error( e );
		}

		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> advertInfo() {
		try {
			return this.getSqlMapClientTemplate().queryForList("gameApp.advertInfo");
		} catch (Exception e) {
			log.error("GameAppServiceImpl.advertInfo failed, e : ", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> gameCollection(String cid, String flag, String pageindex, String pSize) {
		try {
			// 根据分类id读取表名
			String tbName = FileUtil.getConfig("tableName", "tbName.".concat(cid));
			log.info("tableName : " + tbName);
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("CID", cid);
			paramMap.put("TBNAME", tbName);
			paramMap.put("PAGE", pageindex);
			paramMap.put("PSIZE", StrUtils.isEmpty(pSize)?"4":pSize);
			return this.getSqlMapClientTemplate().queryForList("gameApp.gameColIndex", paramMap);
		} catch (Exception e) {
			log.error("GameAppServiceImpl.gameCollection failed, e : ", e);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> search(String gid) {
		return _queryForList( "gameApp.getGameDetailByGameId", Long.parseLong(gid) );
	}

	@Override
	public List<Map<String, Object>> searchApp(int uid, String imei, String content,
											   Date date, String type) {
		//操作类型  0：下载    1：卸载  2：启动   3：查看 4：退出 5：发弹幕 6：搜索
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "uid", uid );
		parameterObject.put( "imei", imei );
		parameterObject.put( "content", content );
		parameterObject.put( "type", "6" );
		parameterObject.put( "source", "0" ); // 操作来源：0： app游戏  1：攻略  2：活动  3：资讯  4：h5游戏
		parameterObject.put( "date", date );

		getSqlMapClientTemplate().insert( "gameApp.searchBehavior",parameterObject );

		if("0".equals( type )) {
			return _queryForList( "gameApp.searchApp1", content );
		}

		return _queryForList( "gameApp.searchApp", content );
	}
}

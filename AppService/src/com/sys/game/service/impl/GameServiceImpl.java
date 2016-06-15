package com.sys.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sys.game.service.GameService;
import com.sys.util.StrUtils;

//如果有可能，可以把ibatis升级成mybatis, 警告看着总不是很舒服的说^_^
@SuppressWarnings("deprecation")
public class GameServiceImpl extends SqlMapClientDaoSupport implements
		GameService {
	private JdbcTemplate jdbcTemplate;
	private Logger log = Logger.getLogger( GameServiceImpl.class );

	@Override
	public Map<String, Object> getGameDetailByGameId(Long gid) {
		List<Map<String, Object>> list = _queryForList( "game.getGameDetailByGameId", gid );
		
		if ( null == list || list.size() <= 0 )
			return new HashMap<String, Object>();

		return list.get( 0 );
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

		tempList = _queryForList( "game.getThroughTheGameIdGameReviewList",  parameterObject);
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
		list = getSqlMapClientTemplate().queryForList( "game.getGameReviewsByUidAndGid", parameterObject );
		
		// 如果用户没有对当前游戏评论
		if(null == list || list.isEmpty() ) return getThroughTheGameIdGameReviewList( gid, pageNumber, pageSize );
		
		//retList.add( list.get(0) ); // 理论上只会有一条
		//retList.addAll( list ); // 但是数据没校验，现在可以有多条（一个用户对同一个游戏多次评分评论）
		tempList.add( list.get( 0 ) ); // 将当前用户评论列表放入临时列表
		
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows - 1 ); // 第一条显示是当前用户评论，分页少取一条
		
		_mapList = _queryForList( "game.getThroughTheGameIdGameReviewList2",  parameterObject);
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

	@SuppressWarnings("unchecked")
	@Override
	public Integer persistentUserBehavior(Long uid, Long gid, Date date,
			Integer type, String jarname,String source) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "uid", uid );
		parameterObject.put( "date", date );
		parameterObject.put( "type", type );
		parameterObject.put( "source", source );

		try {
			if(gid == 0) { // 通过jar名获取游戏ID
				List<Map<String,Object>> l_gid = getSqlMapClientTemplate().queryForList( "game.getGidByJarname", jarname );
				if( null == l_gid || l_gid.size() <= 0)
					return 0;
				gid = Long.parseLong( l_gid.get( 0 ).get( "C_ID" ).toString() );
			}
			
			parameterObject.put( "gid", gid );
			
			// 这边返回一个Object对象，可能为null， 所以假定语句执行不异常认为是成功，返回1.
			getSqlMapClientTemplate().insert( "game.persistentUserBehavior",
					parameterObject );

			return 1;
		} catch ( Exception e ) {
			log.error( e );
		}

		return 0;
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
			
			getSqlMapClientTemplate().insert( "game.persistentUserBehavior2",
					parameterObject );

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

		return _queryForList( "game.starGameStatistics", parameterObject );
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer userComments(Long uid, Long gid, Date date, String comment,
			String isLive, Float grade) {
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
			getSqlMapClientTemplate().queryForObject( "game.procUserComments", parameterObject );
			
			if(grade != -1) {
				parameterObject.put( "grade", grade );
				List<Map<String,Object>> userGrade = getSqlMapClientTemplate().queryForList( "game.getGrade", _m );
				
				if(null == userGrade || userGrade.size() <= 0)
				// 这边返回一个Object对象，可能为null， 所以假定语句执行不异常认为是成功，返回1.
				getSqlMapClientTemplate().insert( "game.userRating",
						parameterObject );
				else
					getSqlMapClientTemplate().update( "game.updateUserRating",
							parameterObject );
			}

			return 1;
		} catch ( Exception e ) {
			log.error( e );
		}

		return 0;
	}

	@Override
	public Integer userFeedback(Long uid, String idea) {
		// TODO Auto-generated method stub
		return null;
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
			List<Map<String,Object>> userGrade = getSqlMapClientTemplate().queryForList( "game.getGrade", _m );
			
			if(null == userGrade || userGrade.size() <= 0)
			// 这边返回一个Object对象，可能为null， 所以假定语句执行不异常认为是成功，返回1.
			getSqlMapClientTemplate().insert( "game.userRating",
					parameterObject );
			else
				getSqlMapClientTemplate().update( "game.updateUserRating",
						parameterObject );

			return 1;
		} catch ( Exception e ) {
			log.error( e );
		}

		return 0;
	}
	
	@Override
	public List<Map<String, Object>> getUserRating(long uid, long gid) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		List<Long> ids = new ArrayList<Long>();
		ids.add( uid );
		parameterObject.put( "uids", ids );
		parameterObject.put( "gid", gid );
		
		return _queryForList( "game.getGrade", parameterObject );
	}
	
	@Override
	public List<Map<String, Object>> listActivity(long gid, int page, int rows) {
		int start = rows * (page - 1) + 1;
		int maxRows = start + rows - 1;
		
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		
		parameterObject.put( "gid", gid );
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );
		
		return _queryForList( "game.listActivity", parameterObject );
	}

	@Override
	public List<Map<String, Object>> listInformation(long gid, int page,
			int rows) {
		int start = rows * (page - 1) + 1;
		int maxRows = start + rows - 1;
		
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		
		parameterObject.put( "gid", gid );
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );
		
		return _queryForList( "game.listInformation", parameterObject );
	}
	
	@Override
	public List<Map<String, Object>> raiders(long gid, int page, int rows) {
		int start = rows * (page - 1) + 1;
		int maxRows = start + rows - 1;
		
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		
		parameterObject.put( "gid", gid );
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );
		
		return _queryForList( "game.raiders", parameterObject );
	}
	@Override
	public Map<String, Object> getActivityById(long id, String uid, String source) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "id", id );
		parameterObject.put( "uid", uid );
		parameterObject.put( "source", source );
		
		List<Map<String, Object>> list = _queryForList( "game.getActivityById", parameterObject );
		
		if ( null == list || list.size() <= 0 )
			return new HashMap<String, Object>();

		return list.get( 0 );
	}
	
	@Override
	public Map<String, Object> isLike(Long id, String uid, String source) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "id", id );
		parameterObject.put( "uid", uid );
		parameterObject.put( "source", source );
		
		List<Map<String, Object>> list = _queryForList( "game.isLike", parameterObject );
		
		if ( null == list || list.size() <= 0 )
			return new HashMap<String, Object>();

		return list.get( 0 );
	}


	@Override
	public Map<String, Object> getInformationById(long id, String uid, String source) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "id", id );
		parameterObject.put( "uid", uid );
		parameterObject.put( "source", source );
		
		List<Map<String, Object>> list = _queryForList( "game.getInformationById", parameterObject );
		
		if ( null == list || list.size() <= 0 )
			return new HashMap<String, Object>();

		return list.get( 0 );
	}

	@Override
	public Map<String, Object> getRaidersById(long id, String uid, String source) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "id", id );
		parameterObject.put( "uid", uid );
		parameterObject.put( "source", source );
		
		List<Map<String, Object>> list = _queryForList( "game.getRaidersById", parameterObject );
		
		if ( null == list || list.size() <= 0 )
			return new HashMap<String, Object>();

		return list.get( 0 );
	}
	
	@Override
	public List<Map<String, Object>> getPopularSearchesApp() {
		//1：app游戏 2：H5游戏
		String type = "1";
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "type", type );
		return _queryForList( "game.getPopularSearches", parameterObject );
	}

	@Override
	public List<Map<String, Object>> getPopularSearchesH5() {
		//1：app游戏 2：H5游戏
		String type = "2";
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "type", type );
		return _queryForList( "game.getPopularSearches", parameterObject );
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
				
		getSqlMapClientTemplate().insert( "game.searchBehavior",parameterObject );
				
		if("0".equals( type )) {
			return _queryForList( "game.searchApp1", content );
		}
		
		return _queryForList( "game.searchApp", content );
	}

	@Override
	public List<Map<String, Object>> searchH5(int uid, String imei, String content,
			Date date, String type) {
		//操作类型  0：下载    1：卸载  2：启动   3：查看 4：退出 5：发弹幕 6：搜索
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "uid", uid );
		parameterObject.put( "imei", imei );
		parameterObject.put( "content", content );
		parameterObject.put( "type", "6" );
		parameterObject.put( "source", "4" ); // 操作来源：0： app游戏  1：攻略  2：活动  3：资讯  4：h5游戏
		parameterObject.put( "date", date );
				
		getSqlMapClientTemplate().insert( "game.searchBehavior",parameterObject );

		if("0".equals( type )) {
			return _queryForList( "game.searchH51", content );
		}
		
		return _queryForList( "game.searchH5", content );
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> boutiqueAppIndex() {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		final String TYPE = "1"; //1：精品 2：H5精品 3：礼包
		
		List<Map<String,Object>> _l = getSqlMapClientTemplate().queryForList( "game.index", TYPE );
		
		if(null != _l && _l.size() >= 1) {
			Map<String, Object> parameterObject = new HashMap<String, Object>();
			Map<String,Object> _m = null;
			List<Map<String,Object>> categories = new ArrayList<Map<String,Object>>();
			for ( int i = 0; i < _l.size(); ++i ) { // 这个不会多，不然查询不适合放在循环内
				_m = _l.get( i );
				Object number = _m.get( "C_NUMBER" );
				Object cid = _m.get( "C_CID" );
				
				List<Map<String,Object>> l_category = _queryForList( "game.findCategoryById", cid );
				if(l_category.size() >= 1) {
					Map<String, Object> m_category = l_category.get( 0 );
					Map<String, Object> result_child_map = new HashMap<String, Object>();
					
					parameterObject = new HashMap<String, Object>();
					parameterObject.put( "startingIndex", 1 );
					parameterObject.put( "maxRows", number );
					parameterObject.put( "cid",  cid);
					
					result_child_map.put( "C_NAME", m_category.get( "C_NAME" ) );
					result_child_map.put( "C_CID", m_category.get( "C_ID" ) );
					result_child_map.put( "apps", _queryForList( "game.categoryDetail", parameterObject ) );
					
					categories.add( result_child_map );
				}
			}
			
			resultMap.put( "categories", categories );
		} else {
			resultMap.put( "categories", new ArrayList<Map<String,Object>>() );
		}
		
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> boutiqueH5Index() {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		final String TYPE = "2"; //1：精品 2：H5精品 3：礼包
		
		List<Map<String,Object>> _l = getSqlMapClientTemplate().queryForList( "game.index", TYPE );
		
		if(null != _l && _l.size() >= 1) {
			Map<String, Object> parameterObject = new HashMap<String, Object>();
			Map<String,Object> _m = null;
			List<Map<String,Object>> categories = new ArrayList<Map<String,Object>>();
			for ( int i = 0; i < _l.size(); ++i ) { // 这个不会多，不然查询不适合放在循环内
				_m = _l.get( i );
				Object number = _m.get( "C_NUMBER" );
				Object cid = _m.get( "C_CID" );
				
				List<Map<String,Object>> l_category = _queryForList( "game.findCategoryById", cid );
				if(l_category.size() >= 1) {
					Map<String, Object> m_category = l_category.get( 0 );
					Map<String, Object> result_child_map = new HashMap<String, Object>();
					
					parameterObject = new HashMap<String, Object>();
					parameterObject.put( "startingIndex", 1 );
					parameterObject.put( "maxRows", number );
					parameterObject.put( "cid",  cid);
					
					result_child_map.put( "C_NAME", m_category.get( "C_NAME" ) );
					result_child_map.put( "C_CID", m_category.get( "C_ID" ) );
					result_child_map.put( "apps", _queryForList( "game.h5CategoryDetail", parameterObject ) );
					
					categories.add( result_child_map );
				}
			}
			
			resultMap.put( "categories", categories );
		} else {
			resultMap.put( "categories", new ArrayList<Map<String,Object>>() );
		}
		
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> giftsIndex(Long uid) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		final String TYPE = "3"; //1：精品 2：H5精品 3：礼包
		
		List<Map<String,Object>> _l = getSqlMapClientTemplate().queryForList( "game.index", TYPE );
		
		if(null != _l && _l.size() >= 1) {
			Map<String, Object> parameterObject = new HashMap<String, Object>();
			Map<String,Object> _m = null;
			List<Map<String,Object>> categories = new ArrayList<Map<String,Object>>();
			for ( int i = 0; i < _l.size(); ++i ) { // 这个不会多，不然查询不适合放在循环内
				_m = _l.get( i );
				Object number = _m.get( "C_NUMBER" );
				Object cid = _m.get( "C_CID" );
				
				List<Map<String,Object>> l_category = _queryForList( "game.findCategoryById", cid );
				if(l_category.size() >= 1) {
					Map<String, Object> m_category = l_category.get( 0 );
					Map<String, Object> result_child_map = new HashMap<String, Object>();
					
					parameterObject = new HashMap<String, Object>();
					parameterObject.put( "startingIndex", 1 );
					parameterObject.put( "maxRows", number );
					parameterObject.put( "cid",  cid);
					
					result_child_map.put( "C_NAME", m_category.get( "C_NAME" ) );
					result_child_map.put( "C_CID", m_category.get( "C_ID" ) );
					List<Map<String,Object>> _list = _queryForList( "game.giftsDetail", parameterObject ) ;
					this.userAction( _list, String.valueOf( uid ) );
					
					result_child_map.put( "gifts",  _list);
					
					categories.add( result_child_map );
				}
			}
			
			resultMap.put( "categories", categories );
		} else {
			resultMap.put( "categories", new ArrayList<Map<String,Object>>() );
		}
		
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> boutiqueAppCategory(int page, int rows) {
		return null;
	}

	@Override
	public List<Map<String, Object>> boutiqueAppCategoryDetail(int categoryId,
			int page, int rows) {
		int start = rows * (page - 1) + 1;
		int maxRows = start + rows - 1;
		
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		
		parameterObject.put( "cid", categoryId );
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );
		
		return _queryForList( "game.categoryDetail", parameterObject );
	}

	@Override
	public List<Map<String, Object>> boutiqueH5Category(int page, int rows) {
		return null;
	}

	@Override
	public List<Map<String, Object>> boutiqueH5CategoryDetail(int categoryId,
			int page, int rows) {
		int start = rows * (page - 1) + 1;
		int maxRows = start + rows - 1;
		
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		
		parameterObject.put( "cid", categoryId );
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );
		
		return _queryForList( "game.h5CategoryDetail", parameterObject );
	}

	@Override
	public List<Map<String, Object>> giftsCategory(int page, int rows) {
		return null;
	}

	@Override
	public List<Map<String, Object>> giftsCategoryDetail(long uid, int categoryId,
			int page, int rows) {
		int start = rows * (page - 1) + 1;
		int maxRows = start + rows - 1;
		
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		
		parameterObject.put( "cid", categoryId );
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );
		
		List<Map<String,Object>> _list = _queryForList( "game.giftsDetail", parameterObject ) ;
		this.userAction( _list, String.valueOf( uid ) );
		return _list;
	}
	
	/**
	 * @Description: TODO 处理用户评分
	 * @param param map 包含gid和用户id集合
	 * @param tempList 用户评论临时列表
	 * @param retList 用户评分列表,比临时列表多了评分信息
	 */
	private void _processGrade(Map<String, Object> param,
			List<Map<String, Object>> tempList,
			List<Map<String, Object>> retList) {
		List<Map<String,Object>> _gradeList =  null;
		if(null != param) { // 用户没有评分
			// 当前页用户对游戏的评分列表
			_gradeList = _queryForList( "game.getGrade", param );	
		}
		
		if ( null != tempList && tempList.size() >= 1 ) { // 如果评论列表有数据
			for ( int i = 0; i < tempList.size(); ++i ) {
				Map<String, Object> _m = tempList.get( i );
				if(null != _gradeList) {
					Object o = _m.get( "C_UID" );
					boolean flag = false; // 标识用户是否有评分
					for ( int j = 0; j < _gradeList.size(); ++j ) {
						Map<String,Object> _gradeMap = _gradeList.get( j );
						if(o.equals( _gradeMap.get( "C_UID" ))) {
							_m.put( "C_GRADE", _gradeMap.get( "C_GRADE" ) ); // 将该用户对该游戏的评分加入集合
							_gradeList.remove( _gradeMap ); //当前map值已取，不需要放入下次循环
							// 这边需要注意的是，一个用户如果对一个游戏多次评分，多次评论，
							// 那么评论与评分的关系就是乱的，无法唯一标识了,因为评分和评论不是原子的操作，意味着就算从时间上也不能关联（或许可以用时间差，但是会很麻烦，实现上)
							
							flag = true; // 标识这个用户是有评分的
							
							break; //值已取，跳出本次循环
						}
					}
					
					if(!flag) { //如果当前用户没有评分
						_m.put( "C_GRADE", 0 ); // 默认分值0，这边没有用常量，需要改，直接找到这边改吧，比较乱
					}
					
				} else {
					_m.put( "C_GRADE", 0 );
				}
				retList.add( _m ); // 这个map已经包含用户评论所有信息，游戏评论 、用户头像和用户评分
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> _queryForList(String statementName, Object parameterObject) {
		List<Map<String, Object>> list = getSqlMapClientTemplate().queryForList( statementName, parameterObject );

		if ( null == list || list.size() == 0 )
			return new ArrayList<Map<String,Object>>();
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据合集id获取游戏合集
	/*public List<Map<String, Object>> gameCollection(String cid,String pageindex) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("CID", cid);
		int page = Integer.parseInt(pageindex);
		map.put("PAGE", page);
		return this.getSqlMapClientTemplate().queryForList("game.gameList", map);
	}*/
	public List<Map<String, Object>> gameCollection(String cid,String pageindex) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("CID", cid);
		int page = Integer.parseInt(pageindex);
		paramMap.put("PAGE", page);
		// 分页获取当前合集的所有游戏信息
		List<Map<String, Object>> list = this.getSqlMapClientTemplate().queryForList("game.gameCollection", paramMap);
		if(null!=list && list.size()>0){
			for (Map<String, Object> map : list) {
				String id = map.get("C_ID").toString();
				/** 下载量 */
				// 根据游戏id查询真实下载量
				String realDownload = (String) this.getSqlMapClientTemplate().queryForObject("game.realDownload", id);
				// 如果真实下载量为空，设置值为0
				realDownload = StrUtils.isEmpty(realDownload) ? "0" : realDownload;
				map.put("C_DOWNLOAD", realDownload);
				/** 评分 */
				// 查询真实评分
				String realGrade = (String) this.getSqlMapClientTemplate().queryForObject("game.realGrade", id);
				// 如果真实评分为空，设置值为0
				realGrade = StrUtils.isEmpty(realGrade) ? "0" : realGrade;
				map.put("C_GRADE", realGrade);
				/** 是否有礼包 */
				Integer has = (Integer) this.getSqlMapClientTemplate().queryForObject("game.hasGift", id);
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
	// 获取游戏APP信息（包名）
	public List<Map<String, Object>> gameInfo() {
		return this.getSqlMapClientTemplate().queryForList("game.gameInfo");
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取用户信息
	public List<Map<String,Object>> getUserInfo(String uid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("uid", uid);
		return this.getSqlMapClientTemplate().queryForList("game.getUserInfo", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取绑定信息
	public List<Map<String,Object>> getBindingInfo(String uid) {
		List<Map<String,Object>> bindingInfo =new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		// 查询绑定信息
		List<Map<String,Object>> list = this.getSqlMapClientTemplate().queryForList("game.getBindingInfo", map);
		
		Map<String,Object> reqMap = new HashMap<String,Object>();
		// 存入"登陆类型"，0表示没有绑定/注册
		reqMap.put("chujian", "0");
		reqMap.put("qq", "0");
		reqMap.put("weixin", "0");
		reqMap.put("weibo", "0");
		bindingInfo.add(reqMap);
		if(null!=list && list.size()>0){
			for (Map<String,Object> m : list) {
				// 获取登陆状态
				String type = (null==m.get("C_LOGINTYPE")) ? "" : m.get("C_LOGINTYPE").toString();
				// 修改当前遍历到的登陆状态为1
				if("0".equals(type)){
					reqMap.put("chujian", "1");
                }else if("1".equals(type)){
                	reqMap.put("qq", "1");
                	if("0".equals(m.get("C_STATE"))){
    					reqMap.put("unBindable_qq", "1");
    				}else{
    					reqMap.put("unBindable_qq", "0");
    				}
                }else if("2".equals(type)){
                	reqMap.put("weixin", "1");
                	if("0".equals(m.get("C_STATE"))){
    					reqMap.put("unBindable_weixin", "1");
    				}else{
    					reqMap.put("unBindable_weixin", "0");
    				}
                }else if("3".equals(type)){
                	reqMap.put("weibo", "1");
                	if("0".equals(m.get("C_STATE"))){
    					reqMap.put("unBindable_weibo", "1");
    				}else{
    					reqMap.put("unBindable_weibo", "0");
    				}
                }
			}
		}
		return bindingInfo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	// 广告位信息
	public List<Map<String, Object>> advertInfo() {
		return this.getSqlMapClientTemplate().queryForList("game.advertInfo");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	// 根据第三方登录状态和regid查询第三方信息
	public List<Map<String, Object>> findExternal(String regid, String logintype) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("LOGINTYPE", logintype);
		map.put("REGID", regid);
		return this.getSqlMapClientTemplate().queryForList("game.getExternal", map);
	}

	@Override
	// 根据regid和imei查询登录与手机对应关系信息
	public Integer findInfoByRegidNImei(String regid,
			String imei) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("IMEI", imei);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("game.findInfoByRegidNImei", map);
	}

	@Override
	// 更新当前第三方登录为最新，修改"首次登录"为0（如果需要）
	public void updateIsLogin(String regid, String imei) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("IMEI", imei);
		this.getSqlMapClientTemplate().update("game.updateIsLogin", map);
	}

	@Override
	// 新增当前第三方登录记录（IMEI）
	public void insertExternalNImei(String uid, String regid, String imei) {
		this.insertImei(uid, regid, imei);
	}

	@Override
	// 更新其他第三方登录非最新
	public void updateIsNotLogin(String regid, String imei) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("IMEI", imei);
		this.getSqlMapClientTemplate().update("game.updateIsNotLogin", map);
	}

	@Override
	// 新增第三方绑定信息
	public String insertExternal(String regid, String nickname,
			String logintype, String headimage, String sex, String age) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("NICKNAME", nickname);
		map.put("LOGINTYPE", logintype);
		map.put("HEADIMAGE", headimage);
		map.put("SEX", sex);
		map.put("AGE", age);
		map.put("ACTIONDATE", new Date());
		return (String) this.getSqlMapClientTemplate().insert("game.insertExternal", map);
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据用户id查询是否已注册
	public List<Map<String, Object>> isRegistered(String uid, String loginType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", loginType);
		return this.getSqlMapClientTemplate().queryForList("game.isRegistered", map);
	}

	@Override
	// 保存第三方账号
	public void bindExternal(String uid, String regid,
			String nickname, String logintype, String phonenum, String sex,
			String age, String headimage) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("REGID", regid);
		map.put("NICKNAME", nickname);
		map.put("LOGINTYPE", logintype);
		map.put("PHONENUM", phonenum);
		map.put("SEX", sex);
		map.put("AGE", age);
		map.put("HEADIMAGE", headimage);
		map.put("ACTIONDATE", new Date());
		if(StrUtils.isEmpty(map.get("PHONENUM"))){
			this.getSqlMapClientTemplate().insert("game.bindExternal", map);
		}else{
			this.getSqlMapClientTemplate().insert("game.bindExternalWithPhone", map);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据注册id和第三方登录状态查询是否已经绑定过第三方账号
	public List<Map<String, Object>> isBinding(String regid, String loginType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("LOGINTYPE", loginType);
		return this.getSqlMapClientTemplate().queryForList("game.isBinding", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 校验验证码是否正确
	public List<Map<String, Object>> isUsableCode(String phonenum,
			String valicode, String s_date, String type) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("DATE", s_date);
		map.put("VALICODE", valicode);
		map.put("TYPE", type);
		map.put("PHONENUM", phonenum);
		return this.getSqlMapClientTemplate().queryForList("game.isUsableCode", map);
	}

	@Override
	// 保存注册信息
	public String register(String phonenum, String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PHONENUM", phonenum);
		map.put("PWD", pwd);
		map.put("DATE", new Date());
		return (String) this.getSqlMapClientTemplate().insert("game.register", map);
	}

	@Override
	// 查询用户id
	public String getUid(String regid, String type) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("TYPE", type);
		return (String) this.getSqlMapClientTemplate().queryForObject("game.getUid", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据手机号和密码查询用户信息
	public List<Map<String, Object>> login(String phonenum, String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PHONENUM", phonenum);
		map.put("PWD", pwd);
		return this.getSqlMapClientTemplate().queryForList("game.login", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据注册id和imei查询IMEI是否有记录
	public List<Map<String, Object>> getMemberImei(String regid, String imei) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("IMEI", imei);
		return this.getSqlMapClientTemplate().queryForList("game.getMemberImei", map);
	}

	@Override
	// 新增触键注册用户imei
	public void insertImei(String uid, String regid, String imei) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("REGID", regid);
		map.put("IMEI", imei);
		// 查询是否是第一次登录
		List<Map<String,Object>> list = this.isFirstLogin(map);
		if(null!=list && list.size()>0){
			map.put("ISFIRSTLOGIN", "0");
		}else{
			map.put("ISFIRSTLOGIN", "1");
		}
		map.put("ACTIONDATE", new Date());
		this.getSqlMapClientTemplate().insert("game.insertImei", map);
	}

	// 查询是否是第一次登录
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> isFirstLogin(Map<String,Object> map){
		return this.getSqlMapClientTemplate().queryForList("game.isFirstLogin", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据用户id和密码查询用户信息
	public List<Map<String, Object>> findUserByUidNPwd(String uid, String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("PWD", pwd);
		return this.getSqlMapClientTemplate().queryForList("game.findUserByUidNPwd", map);
	}

	@Override
	// 修改密码
	public void modifyPwd(String uid, String oldpwd, String newpwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("OPWD", oldpwd);
		map.put("NPWD", newpwd);
		map.put("ACTIONDATE", new Date());
		this.getSqlMapClientTemplate().update("game.modifyPwd", map);
	}

	@Override
	// 编辑用户信息（只修改昵称和性别）
	public void editUserInfo(String uid, String nickname, String sex) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("NICKNAME", nickname);
		map.put("SEX", sex);
		map.put("ACTIONDATE", new Date());
		this.getSqlMapClientTemplate().update("game.editUserInfo", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据手机号查询用户信息
	public List<Map<String, Object>> findByPhone(String phonenum) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PHONENUM", phonenum);
		return this.getSqlMapClientTemplate().queryForList("game.findByPhone", map);
	}

	@Override
	// 绑定手机
	public void bindPhone(String uid, String logintype, String phonenum, String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", logintype);
		map.put("PHONENUM", phonenum);
		map.put("PWD", pwd);
		map.put("ACTIONDATE", new Date());
		this.getSqlMapClientTemplate().update("game.bindPhone", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取当天发送的验证码
	public List<Map<String, Object>> getValiCode(String phonenum, String type,
			String date) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PHONENUM", phonenum);
		map.put("TYPE", type);
		map.put("DATE", date);
		return this.getSqlMapClientTemplate().queryForList("game.getValiCode", map);
	}

	@Override
	// 将指定手机号和类型对应的其他验证码置为无效
	public void invalidatedOther(String phonenum, String type) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PHONENUM", phonenum);
		map.put("TYPE", type);
		this.getSqlMapClientTemplate().update("game.invalidatedOther", map);
	}

	@Override
	// 保存验证码
	public void insertValicode(String imei, String phonenum, String type,
			Date date, String code) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("IMEI", imei);
		map.put("DATE", date);
		map.put("CODE", code);
		map.put("TYPE", type);
		map.put("PHONENUM", phonenum);
		this.getSqlMapClientTemplate().insert("game.insertValicode", map);
	}

	@Override
	// 设置新密码
	public void setNewPwd(String phonenum, String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PHONENUM", phonenum);
		map.put("PWD", pwd);
		map.put("ACTIONDATE", new Date());
		this.getSqlMapClientTemplate().update("game.setNewPwd", map);
	}

	@Override
	// 修改用户头像
	public void updateHead(String uid, String fileurl) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("HEADIMAGE", fileurl);
		map.put("ACTIONDATE", new Date());
		this.getSqlMapClientTemplate().update("game.updateHead", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取游戏提示语
	public List<Map<String, Object>> gameCue() {
		return this.getSqlMapClientTemplate().queryForList("game.gameCue");
	}

	@Override
	// 是否使用过“一键登录”
	public String isLogined(String imei) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("IMEI", imei);
		return (String)this.getSqlMapClientTemplate().queryForObject("game.isLogined",map);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取注册ID
	public List<Map<String, Object>> getRegid() {
		return this.getSqlMapClientTemplate().queryForList("game.getRegid");
	}

	@Override
	// 生成新的游客信息，返回用户id
	public String quickLogin(String regid, String logintype) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("LOGINTYPE", logintype);
		map.put("ACTIONDATE", new Date());
		return (String) this.getSqlMapClientTemplate().insert("game.quickLogin", map);
	}
	
	@Override
	// 新增游客登录记录
	public void quickLoginImei(String regid, String imei, String uid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("IMEI", imei);
		map.put("ACTIONDATE", new Date());
		map.put("UID", uid);
		this.getSqlMapClientTemplate().update("game.quickLoginImei", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取用户id
	public List<Map<String, Object>> userHeadImg(String uid, String loginType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		/** 2015-6-1 修改：查询头像不再需要根据登录类型 */
//		map.put("LOGINTYPE", loginType);
		return this.getSqlMapClientTemplate().queryForList("game.userHeadImg", map);
	}

	@Override
	// 解绑第三方账号
	public void unBindExternal(String uid, String logintype) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", logintype);
		this.getSqlMapClientTemplate().update("game.unBindExternal", map);
	}

	@Override
	// 生成默认昵称
	public void defaultNickname(String nickname, String regid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("NICKNAME", nickname);
		map.put("REGID", regid);
		this.getSqlMapClientTemplate().update("game.defaultNickname", map);
	}

	@Override
	// 第三方注册账号绑定手机后，生成logintype为0的数据
	public void chujianUser(String uid, String logintype, String phonenum, String pwd, String nickname) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", logintype);
		map.put("PHONENUM", phonenum);
		map.put("PWD", pwd);
		map.put("NICKNAME", nickname);
		map.put("DATE", new Date());
		this.getSqlMapClientTemplate().update("game.chujianUser", map);
	}

	@Override
	// 查询是否存在logintype为0的信息
	public int existChujianUser(String uid, String logintype) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", logintype);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("game.existChujianUser", map);
	}

	@Override
	// 查询当前第三方账号是否是第一次登录的账号
	public String isFirstInfo(String uid, String logintype) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", logintype);
		return (String) this.getSqlMapClientTemplate().queryForObject("game.isFirstInfo", map);
	}

	@Override
	// 修改当前用户的手机号
	public void updatePhonenum(String uid, String phonenum) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("PHONENUM", phonenum);
		map.put("DATE", new Date());
		this.getSqlMapClientTemplate().update("game.updatePhonenum", map);
	}

	@Override
	// 新增注册用户IMEI信息
	public void RegImei(String regid, String imei, String valicode, Date date,
			String uid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("IMEI", imei);
		map.put("VALICODE", valicode);
		map.put("DATE", new Date());
		map.put("UID", uid);
		this.getSqlMapClientTemplate().update("game.RegImei", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据合集id获取相应的H5游戏合集
	public List<Map<String, Object>> h5GameCollection(String cid, String pageindex, String pageSize) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("CID", cid);
		int page = Integer.parseInt(pageindex);
		paramMap.put("PAGE", page);
		paramMap.put( "PAGESIZE", pageSize );
		// 分页获取当前合集的所有游戏信息
		List<Map<String, Object>> list = this.getSqlMapClientTemplate().queryForList("game.h5GameCollection", paramMap);
		if(StrUtils.isNotEmpty(list)){
			for (Map<String, Object> map : list) {
				String gid = map.get("C_ID").toString();// 游戏id
				// 查询指定H5游戏被启动的次数
				paramMap.clear();
				paramMap.put("GID", gid);
				Integer cnt = (Integer) this.getSqlMapClientTemplate().queryForObject("game.startingCountH5", paramMap);
				map.put("startCnts", (cnt==null)?"0":String.valueOf(cnt));
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 查询H5游戏广告信息
	public List<Map<String, Object>> h5AdvertInfo() {
		return this.getSqlMapClientTemplate().queryForList("game.h5AdvertInfo");
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取所有游戏礼包
	public List<Map<String, Object>> gameGiftList(String uid, String pageIndex) {
		// 查询所有游戏礼包
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("PAGE", pageIndex);
		List<Map<String, Object>> list = this.getSqlMapClientTemplate().queryForList("game.gameGiftList", paramMap);
		this.userAction(list, uid);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取单个游戏礼包
	public List<Map<String, Object>> gameGift(String uid, String gid) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("GID", gid);
		// 查询当前用户是否领取过当前游戏
		List<Map<String, Object>> list = this.getSqlMapClientTemplate().queryForList("game.gameGift", paramMap);
		this.userAction(list, uid);
		return list;
	}
	
	// 添加用户对游戏的操作行为
	private void userAction(List<Map<String, Object>> list,String uid){
		if(StrUtils.isNotEmpty(list)){
			for (Map<String, Object> map : list) {
				// 获取礼包id
				String gid = (map.get("ID")==null)?"":map.get("ID").toString();
				String remain = "0";// 剩余
				String got = "0";// 已领取
				map.put("REMAIN", remain);
				map.put("GOT", got);
				// 查询礼包码数量
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> cnt = this.getSqlMapClientTemplate().queryForList("game.giftCodesCnt",gid);
				if(StrUtils.isNotEmpty(cnt)){
					Map<String, Object> cntMap = cnt.get(0);
					remain = cntMap.get("REMAIN").toString();
					got = cntMap.get("GOT").toString();
					map.put("REMAIN", remain);
					map.put("GOT", got);
				}
				if(Integer.valueOf(remain)>0){
					// ACTION：用户可以对礼包的操作行为--1领取，2淘号，3查看
					// 如果剩余礼包数大于0，则可以领取
					map.put("ACTION", "1");
				}else{
					map.put("ACTION", "2");
				}
				if(StrUtils.isNotEmpty(gid)){
					Map<String,Object> paramMap = new HashMap<String,Object>();
					paramMap.put("UID", uid);
					paramMap.put("GID", gid);
					// 查询当前用户是否领取过当前游戏
					Integer hasGot = (Integer) this.getSqlMapClientTemplate().queryForObject("game.hasGotGame", paramMap);
					if(hasGot>0){
						map.put("ACTION", "3");
					}
				}
				// 非空处理
				map.put("METHOD", map.get("METHOD")==null?"":map.get("METHOD").toString());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取一条礼包码
	public List<Map<String, Object>> getGiftCode(String gid) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("GID", gid);
		return this.getSqlMapClientTemplate().queryForList("game.getGiftCode", paramMap);
	}

	@Override
	// 更新礼包码
	public void updateGiftCode(String cid) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("CID", cid);
		this.getSqlMapClientTemplate().update("game.updateGiftCode", paramMap);
	}

	@Override
	// 添加用户对礼包的操作行为
	public void addUserActionOfGift(String uid, String gid, String type, String code) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("UID", uid);
		paramMap.put("GID", gid);
		paramMap.put("TYPE", type);
		paramMap.put("CODE", code);
		this.getSqlMapClientTemplate().update("game.updateUserActionOfGift", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 查询礼包详情
	public List<Map<String, Object>> giftDetail(String uid, String gid) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("GID", gid);
		List<Map<String, Object>> list = this.getSqlMapClientTemplate().queryForList("game.giftDetail", paramMap);
		// 获取当前用户的礼包码
		String code = this.usersGiftCode(uid, gid);
		if(StrUtils.isNotEmpty(list)){
			Map<String, Object> map = list.get(0);
			map.put("CODE", code==null?"":code);
		}
		this.userAction(list, uid);
		return list;
	}

	@Override
	// 获取当前用户的指定礼包的礼包码
	public String usersGiftCode(String uid, String gid) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("UID", uid);
		paramMap.put("GID", gid);
		return (String) this.getSqlMapClientTemplate().queryForObject("game.usersGiftCode", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 礼包淘号
	public List<Map<String, Object>> drawGiftCode(String gid) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("GID", gid);
		paramMap.put("DATE", new Date());
		return this.getSqlMapClientTemplate().queryForList("game.drawGiftCode", paramMap);
	}

	@Override
	// 保存用户发弹幕的行为
	public void saveH5Barrage(String uid, String gid, String type, String source) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("UID", uid);
		paramMap.put("GID", gid);
		paramMap.put("TYPE", type);
		paramMap.put("DATE", new Date());
		paramMap.put("SOURCE", source);
		this.getSqlMapClientTemplate().update("game.saveH5Barrage", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取当前用户的礼包
	public List<Map<String, Object>> usersGifts(String uid) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("UID", uid);
		return this.getSqlMapClientTemplate().queryForList("game.usersGifts", paramMap);
	}

	@Override
	// 根据JAR包名获取游戏LOGO
	public String getLogo(String jarName) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("JARNAME", "%" + jarName + "%");
		return (String) this.getSqlMapClientTemplate().queryForObject("game.getLogo", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 礼包推荐
	public List<Map<String, Object>> recommendGift(String uid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("UID", uid);
		// 查询用户下载过的游戏的礼包
		List<Map<String, Object>> downloadedGameGifts = this.getSqlMapClientTemplate()
				.queryForList("game.downloadedGameGifts", paramMap);
		// 查询用户的礼包
		List<Map<String, Object>> usersGifts = this.getSqlMapClientTemplate()
				.queryForList("game.usersGifts", paramMap);
		// 查询后台维护的推荐礼包
		List<Map<String, Object>> recommendGifts = this.getSqlMapClientTemplate()
				.queryForList("game.recommendGifts");
		Set<String> removeId = new HashSet<String>();
		if (StrUtils.isNotEmpty(downloadedGameGifts)) {
			for (Map<String, Object> map : downloadedGameGifts) {
				// 下载过的游戏的礼包id
				String gid = StrUtils.emptyOrString(map.get("ID"));
				for (Map<String, Object> map2 : usersGifts) {
					// 用户的礼包的id
					String gid2 = StrUtils.emptyOrString(map2.get("C_ID"));
					if(StrUtils.isNotEmpty(gid) && StrUtils.isNotEmpty(gid2) && gid.equals(gid2)){
						// 如果用户下载的游戏对应的礼包已经存在于“我的礼包”，则不重复推荐
						removeId.add(gid);
					}
				}
				for (Map<String, Object> map3 : recommendGifts) {
					// 后台维护的推荐礼包的id
					String gid3 = StrUtils.emptyOrString(map3.get("ID"));
					if(StrUtils.isNotEmpty(gid) && StrUtils.isNotEmpty(gid3) && gid.equals(gid3)){
						// 如果后台维护的推荐礼包已经存在于“我的礼包”，则不重复推荐
						removeId.add(gid);
					}
				}
			}
		}
		int length = downloadedGameGifts.size();
		for (int i = 0; i < length; i++) {
			for (Map<String, Object> map : downloadedGameGifts) {
				// 下载过的游戏的礼包id
				String gid = StrUtils.emptyOrString(map.get("ID"));
				if(removeId.contains(gid)){
					downloadedGameGifts.remove(map);
					break;
				}
			}
		}
		recommendGifts.addAll(downloadedGameGifts);
		this.userAction(recommendGifts, uid);
		return recommendGifts;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 最近在玩
	public List<Map<String, Object>> recentlyPlaying(String uid, String type) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("UID", uid);
		if("1".equals(type)){
			list = this.getSqlMapClientTemplate().queryForList("game.recentlyPlaying", paramMap);
		}
		if("2".equals(type)){
			list = this.getSqlMapClientTemplate().queryForList("game.allRecentlyPlaying", paramMap);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 游戏分类列表
	public List<Map<String, Object>> categoryList() {
		return this.getSqlMapClientTemplate().queryForList("game.categoryList");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	// H5游戏分类列表
	public List<Map<String, Object>> h5CategoryList() {
		return this.getSqlMapClientTemplate().queryForList("game.h5CategoryList");
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据分类id获取具体某一分类中的游戏
	public List<Map<String, Object>> gamesInCategory(String cid, String pageindex) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("CID", cid);
		paramMap.put("PAGE", Integer.parseInt(pageindex));
		List<Map<String, Object>> list = this.getSqlMapClientTemplate().queryForList("game.gamesInCategory", paramMap);
		if(null!=list && list.size()>0){
			for (Map<String, Object> map : list) {
				String id = map.get("C_ID").toString();
				/** 下载量 */
				// 根据游戏id查询真实下载量
				String realDownload = (String) this.getSqlMapClientTemplate().queryForObject("game.realDownload", id);
				// 如果真实下载量为空，设置值为0
				realDownload = StrUtils.isEmpty(realDownload) ? "0" : realDownload;
				map.put("C_DOWNLOAD", realDownload);
				/** 评分 */
				// 查询真实评分
				String realGrade = (String) this.getSqlMapClientTemplate().queryForObject("game.realGrade", id);
				// 如果真实评分为空，设置值为0
				realGrade = StrUtils.isEmpty(realGrade) ? "0" : realGrade;
				map.put("C_GRADE", realGrade);
				/** 是否有礼包 */
				Integer has = (Integer) this.getSqlMapClientTemplate().queryForObject("game.hasGift", id);
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
	// 根据分类id获取具体某一分类中的游戏(H5)
	public List<Map<String, Object>> h5GamesInCategory(String cid,
			String pageindex) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("CID", cid);
		paramMap.put("PAGE", Integer.parseInt(pageindex));
		// 分页获取当前分类的所有游戏信息
		List<Map<String, Object>> list = this.getSqlMapClientTemplate()
				.queryForList("game.h5GamesInCategory", paramMap);
		if (StrUtils.isNotEmpty(list)) {
			for (Map<String, Object> map : list) {
				String gid = map.get("C_ID").toString();// 游戏id
				// 查询指定H5游戏被启动的次数
				paramMap.clear();
				paramMap.put("GID", gid);
				Integer cnt = (Integer) this.getSqlMapClientTemplate()
						.queryForObject("game.startingCountH5", paramMap);
				map.put("startCnts", (cnt == null) ? "0" : String.valueOf(cnt));
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据游戏id查询所在分类中推荐的游戏
	public List<Map<String, Object>> gameRecommend() {
		return this.getSqlMapClientTemplate().queryForList("game.gameRecommend");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	// 根据游戏id查询所在分类中推荐的游戏(H5)
	public List<Map<String, Object>> h5GameRecommend() {
		return this.getSqlMapClientTemplate().queryForList("game.h5GameRecommend");
	}

}

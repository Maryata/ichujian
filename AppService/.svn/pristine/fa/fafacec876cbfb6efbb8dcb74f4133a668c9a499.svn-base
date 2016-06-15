/**
 * 
 */
package com.sys.mcrapp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sys.mcrapp.service.IMcrappService;

/**
 * @author vpc
 *
 */
@SuppressWarnings("deprecation")
public class McrappService extends SqlMapClientDaoSupport implements IMcrappService {
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger( McrappService.class );
	/* (non-Javadoc)
	 * @see com.sys.mcrapp.service.IMcrappService#index()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> index() {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> _l = getSqlMapClientTemplate().queryForList( "mcrapp.index" );
		
		if(null != _l && _l.size() >= 1) {
			Map<String, Object> parameterObject = new HashMap<String, Object>();
			Map<String,Object> _m = _l.get( 0 ); // 微用帮 默认合集
			
			parameterObject.put( "startingIndex", 1 );
			parameterObject.put( "maxRows", _m.get( "C_NUMBER" ) );
			parameterObject.put( "cid", _m.get( "C_CID" ) );
			
			resultMap.put( "apps", _queryForList( "mcrapp.listAppByCollection", parameterObject ));
			
			//select t1.c_name,t1.c_id,t2.c_name,t2.c_logourl,t2.c_appurl from t_mcrapp_category_mcrapp t left join t_mcrapp_category t1 on t.c_cid= t1.c_id
		    //   left join t_mcrapp_info t2 on t.c_aid=t2.c_id where t1.c_id=3
			List<Map<String,Object>> categories = new ArrayList<Map<String,Object>>();
			for ( int i = 1; i < _l.size(); ++i ) { // 这个不会多，不然查询不适合放在循环内
				_m = _l.get( i );
				List<Map<String,Object>> l_category = _queryForList( "mcrapp.findCategoryById", _m.get( "C_CID" ) );
				if(l_category.size() >= 1) {
					Map<String, Object> m_category = l_category.get( 0 );
					Map<String, Object> result_child_map = new HashMap<String, Object>();
					
					parameterObject = new HashMap<String, Object>();
					parameterObject.put( "startingIndex", 1 );
					parameterObject.put( "maxRows", _m.get( "C_NUMBER" ) );
					parameterObject.put( "cid", _m.get( "C_CID" ) );
					
					result_child_map.put( "C_NAME", m_category.get( "C_NAME" ) );
					result_child_map.put( "C_CID", m_category.get( "C_ID" ) );
					result_child_map.put( "apps", _queryForList( "mcrapp.listAppByCategory", parameterObject ) );
					
					categories.add( result_child_map );
				}
			}
			
			resultMap.put( "categories", categories );
		} else {
			resultMap.put( "apps", new ArrayList<Map<String,Object>>() );
			resultMap.put( "categories", new ArrayList<Map<String,Object>>() );
		}
		
		return resultMap;
	}

	/* (non-Javadoc)
	 * @see com.sys.mcrapp.service.IMcrappService#listCategory(int, int)
	 */
	@Override
	public List<Map<String, Object>> listCategory(int page, int rows) {
		int start = rows * (page - 1) + 1;
		int maxRows = start + rows - 1;
		
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );
		
		return _queryForList( "mcrapp.listCategory", parameterObject );
	}

	/* (non-Javadoc)
	 * @see com.sys.mcrapp.service.IMcrappService#persistentUserBehavior(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Date)
	 */
	@Override
	public Integer persistentUserBehavior(String imei, String aid,
			String content, String type, Date date) {
		
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "imei", imei );
		parameterObject.put( "aid", aid );
		parameterObject.put( "content", content );
		parameterObject.put( "type", type );
		parameterObject.put( "date", date );

		try {
			getSqlMapClientTemplate().insert( "mcrapp.persistentUserBehavior",
					parameterObject );

			return 1;
		} catch ( Exception e ) {
			log.error( e );
		}

		return 0;
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> _queryForList(String statementName, Object parameterObject) {
		List<Map<String, Object>> list = getSqlMapClientTemplate().queryForList( statementName, parameterObject );

		if ( null == list || list.size() == 0 )
			return new ArrayList<Map<String,Object>>();
		
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.sys.mcrapp.service.IMcrappService#categoryDetail(java.lang.String, int, int)
	 */
	@Override
	public List<Map<String, Object>> categoryDetail(String cid, int page,
			int rows) {
		int start = rows * (page - 1) + 1;
		int maxRows = start + rows - 1;
		
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );
		parameterObject.put( "cid", cid );
		
		return _queryForList( "mcrapp.listAppByCategory", parameterObject );
	}

	/* (non-Javadoc)
	 * @see com.sys.mcrapp.service.IMcrappService#collectionDetail(java.lang.String, int, int)
	 */
	@Override
	public List<Map<String, Object>> collectionDetail(String cid, int page,
			int rows) {
		int start = rows * (page - 1) + 1;
		int maxRows = start + rows - 1;
		
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );
		parameterObject.put( "cid", cid );
		
		return _queryForList( "mcrapp.listAppByCollection", parameterObject );
	}

	/* (non-Javadoc)
	 * @see com.sys.mcrapp.service.IMcrappService#search(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> search(String imei,String content,Date date) {
		// 0：收藏，1：打开，2：取消收藏，3：关闭，4：搜索
		persistentUserBehavior( imei, "", content, "4", date );
		
		return _queryForList( "mcrapp.search", content );
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}

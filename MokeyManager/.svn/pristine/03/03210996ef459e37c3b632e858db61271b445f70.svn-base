package com.org.mokey.basedata.baseinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.net.aso.e;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.org.mokey.basedata.baseinfo.dao.ActiveCodeDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class ActiveCodeDaoImpl implements ActiveCodeDao {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	private JdbcTemplate jdbcTemplate;	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Map<String, Object> getActiveListMap(String isActive, String code,
			String supplier,String isSample, String isRemark,
			int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer("select count(*) from T_BASE_ACTIVE_CODE c left join T_ACTION_ACTIVE a on a.C_ACTIVECODE=c.C_CODE where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		if(StrUtils.isNotEmpty(isActive)){
			sql.append(" and c.C_ISVALID =? ");
			args.add(isActive);
		}
		
		if(StrUtils.isNotEmpty(code)){
			sql.append(" and c.C_CODE like ? ");
			args.add("%"+code+"%");
		}
		if(StrUtils.isNotEmpty(supplier)){
			sql.append(" and substr(c.C_CODE,6,2)=? ");
			args.add(supplier);
		}
		if(StrUtils.isNotEmpty(isSample)){
		/*	if("1".equals(isSample)){
				sql.append(" and (c.C_ISSAMPLE is not null) "); // and c.C_REMARK<>''
			}else{
				sql.append(" and (c.C_ISSAMPLE is null or c.C_ISSAMPLE='') ");
			}*/
			if("1".equals(isSample)){
				sql.append(" and (c.C_ISSAMPLE =1) "); // and c.C_REMARK<>''
			}else{
				sql.append(" and (c.C_ISSAMPLE =0) ");
			}
		}
		if(StrUtils.isNotEmpty(isRemark)){
			if("1".equals(isRemark)){
				sql.append(" and (c.C_REMARK is not null) "); // and c.C_REMARK<>''
			}else{
				sql.append(" and (c.C_REMARK is null or c.C_REMARK='') ");
			}
		}
		
		int count = jdbcTemplate.queryForObject(sql.toString(),args.toArray(),Integer.class);
		String sql1 = DaoUtil.addfy_oracle(sql, " c.C_CODE ", start, limit, args).toString().replace("count(*)", "c.C_ID,c.C_CODE,c.C_SYSDATE,c.C_ISSAMPLE,c.C_BATCH,c.C_REMARK,a.C_IMEI,a.C_SYSDATE as ACTIONDATE");
		//log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql1, args));
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1,args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}
	@Override
	public String saveActiveRemark(Map<String, Object> map) {
		
/*		String seqId=null;
		if(StrUtils.isEmpty(map.get("c_id"))){
			seqId=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_BASE_ACTIVE_CODE	");
			map.put("c_id", seqId);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map, "T_BASE_ACTIVE_CODE");
		}else{
			seqId = (String) map.get("c_id");
			Map<String,Object> whereMap = new HashMap<String,Object>();
			whereMap.put("c_id", seqId);
			
			map.remove("c_id");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, map, whereMap, "T_BASE_ACTIVE_CODE");		
		}
		return seqId;*/
		//String id =null;
		String id = (String) map.get("C_ID");
		if(id==null){
			id=JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_BASE_ACTINE_CODE1");
			map.put("c_id", id);
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, map, "T_BASE_ACTIVE_CODE");
		}else{
			Map<String,Object> wMap=new HashMap<String,Object>();
			wMap.put("C_ID", id);
			map.remove("C_ID");
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, map, wMap, "T_BASE_ACTIVE_CODE");
		}
		return id;
	}
	@Override
	public int findActive(String cCode) {
		// TODO Auto-generated method stub
		
		Map<String, Object> ret = new HashMap<String, Object>();
		String sql="select count(*) from T_BASE_ACTIVE_CODE t where t.C_CODE = ?";
		List args = new ArrayList();
		args.add(cCode);
		
		int count=jdbcTemplate.queryForObject(sql, args.toArray(), Integer.class);
		
		//ret.put("list", list);
		return count;
	}
	/* (non-Javadoc)
	 * @see com.org.mokey.basedata.baseinfo.dao.ActiveCodeDao#batch(java.util.List)
	 */
	@Override
	public void batch(List<Map<String, Object>> list) {
		StringBuffer sql = new StringBuffer();
		StringBuffer valueSql = new StringBuffer();
		
		sql.append( "insert into t_base_active_code(c_id");
		List<Object[]> values = new ArrayList<Object[]>();
		for ( int i = 0; i < list.size(); ++i ) {
			Map<String,Object> map = list.get( i );
			List<Object> v = new ArrayList<Object>();
			for(Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
				String key = it.next(); 
				if(i == 0) {
					sql.append(",").append( key );
					valueSql.append( "," ).append( "?" );
				}
				v.add( map.get( key ) );
			}
			values.add( v.toArray() );
			if(i == 0) {
				sql.append( ") values( SEQ_BASE_ACTINE_CODE1.nextval" ).append( valueSql ).append(")");
			}
		}
		
		//log.info( sql.toString() );
		jdbcTemplate.batchUpdate( sql.toString(), values );
	}
	/* (non-Javadoc)
	 * @see com.org.mokey.basedata.baseinfo.dao.ActiveCodeDao#list(java.util.List)
	 */
	@Override
	public List<String> list(List<String> codeList) {
		String sql = "select c_code from t_base_active_code where c_code in(:codes)";
		List<String> result = new ArrayList<String>();

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		List<String> _code = new ArrayList<String>();
		int len = codeList.size();
		for(int i = 0; i < len; ++i) {
			MapSqlParameterSource parameters = new MapSqlParameterSource();

			_code.add(codeList.get(i));
			if(i != 0 && i % 999 == 0) {
				parameters.addValue("codes", _code);

				List<String> _result = namedParameterJdbcTemplate.query(sql, parameters, new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getNString(1);
					}
				});

				result.addAll(_result);

				_code = new ArrayList<String>();
			}

			if(i == len && i % 999 != 0) {
				parameters.addValue("codes", _code);

				List<String> _result = namedParameterJdbcTemplate.query(sql, parameters, new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getNString(1);
					}
				});

				result.addAll(_result);
			}
		}


		return result;
	}

}

package com.org.mokey.common.util;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import com.org.mokey.util.StrUtils;

/**
 * 保存数据处理
 * @author
 */
public class JdbcTemplateUtils {
	
	private static final Logger log = (Logger.getLogger(JdbcTemplateUtils.class));

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
	/**
	 * SeqId Sequence
	 * @param jdbcTemplate
	 * @param seqName
	 * @return
	 */
	public static String getSeqId(JdbcTemplate jdbcTemplate,String seqName){
		String seqNexId = (String) jdbcTemplate.queryForObject("select "+seqName+".nextval FROM DUAL", String.class);
		return seqNexId;
	}
	
	/**
	 * <pre>
	 * 批处理保存数据
	 * </pre>
	 * @param jdbcTemplate
	 * @param datas :List<Map<String,Object>>
	 * @param updateSql :?Sql
	 */
	@SuppressWarnings("rawtypes")
	public static void batchUpdateMapsData(JdbcTemplate jdbcTemplate,final List<Map<String,?>> datas,String updateSql){
		if(StrUtils.isEmpty(datas) || StrUtils.isEmpty(updateSql)){
			log.debug("params.datas is null,so batchUpdateData no runing.");
			return;
		}
		//log.debug("batchUpdateData.datas.size:"+datas.size()+"\n\n");
		jdbcTemplate.batchUpdate(updateSql,new BatchPreparedStatementSetter(){
			@SuppressWarnings("unchecked")
			public void setValues(PreparedStatement ps,int i) throws SQLException {
				Map bean = (Map) datas.get(i);
				//System.out.println(bean);
				 if(bean!=null){
					 Set<String> keySet = bean.keySet(); // 
					 int index = 1;
					 for (String key : keySet) {
						 if(bean.get(key) instanceof String){//字符串类型
							 if("#newId".equals(bean.get(key))){//自动成成uuid
								 ps.setString(index,java.util.UUID.randomUUID().toString());
							 }else{
								 ps.setString(index, "".equals(bean.get(key))?null:bean.get(key).toString());
							 }
						 }else if(bean.get(key) instanceof Double){
							 ps.setDouble(index, (Double)bean.get(key));
						 }else if(bean.get(key) instanceof Long){
							 ps.setLong(index, (Long) bean.get(key));
						 }else if(bean.get(key) instanceof Integer){
							 ps.setInt(index, (Integer)bean.get(key));
						 }else if(bean.get(key) instanceof Boolean){
							 ps.setBoolean(index, (Boolean) bean.get(key));
						 }else{//空值
							 ps.setString(index, bean.get(key)!=null?bean.get(key).toString():null);
						 }
						 index++;
					 } 
				 }
				 //bean = null;
			 }
			public int getBatchSize() {
				if(datas!=null){
					return datas.size();
				}else{
					return 0;
				}
			}
		});
	}
	
	/**
	 * <pre>
	 * 批处理保存数据
	 * </pre>
	 * @param jdbcTemplate
	 * @param datas :List<List<Object>>
	 * @param updateSql ?Sql
	 */
	@SuppressWarnings("rawtypes")
	public static void batchUpdateListsData(JdbcTemplate jdbcTemplate,final List<List> datas,String updateSql){
		if(StrUtils.isEmpty(datas) || StrUtils.isEmpty(updateSql)){
			log.debug("params.datas is null,so batchUpdateListsData no runing.");
			return;
		}
		log.debug("batchUpdateListsData.datas.size:"+datas.size());
		jdbcTemplate.batchUpdate(updateSql,new BatchPreparedStatementSetter(){
			public void setValues(PreparedStatement ps,int i) throws SQLException {
				List bean = datas.get(i);
				//System.out.println(bean);
				 if(bean!=null){
					 int index=1;
					 for (int key=0;key<bean.size();key++) {
						 if(bean.get(key) instanceof String){//字符串类型
							 ps.setString(index, "".equals(bean.get(key))?null:bean.get(key).toString());
						 }else if(bean.get(key) instanceof Double){
							 ps.setDouble(index, (Double)bean.get(key));
						 }else if(bean.get(key) instanceof Long){
							 ps.setLong(index, (Long) bean.get(key));
						 }else if(bean.get(key) instanceof Integer){
							 ps.setInt(index, (Integer)bean.get(key));
						 }else if(bean.get(key) instanceof Boolean){
							 ps.setBoolean(index, (Boolean) bean.get(key));
						 }else{//空值
							 ps.setString(index, bean.get(key)!=null?bean.get(key).toString():null);
						 }
						 index++;
					 } 
				 }
				 //bean = null;
			 }
			@SuppressWarnings("unused")
			public int getBatchSize() {
				if(datas!=null){
					return datas.size();
				}else{
					return 0;
				}
			}
		});
	}
	
	/**
	 * <pre>
	 * 批处理保存数据
	 * </pre>
	 * @param jdbcTemplate
	 * @param datas :List<List<Object>>
	 * @param updateSql ?Sql
	 */
	public static void batchUpdateListArrsData(JdbcTemplate jdbcTemplate,final List<Object[]> datas,final String updateSql){
		if(StrUtils.isEmpty(datas) || StrUtils.isEmpty(updateSql)){
			log.debug("params.datas is null,so batchUpdateListsData no runing.");
			return;
		}
		log.debug("batchUpdateListArrsData.datas.size:"+datas.size());
		jdbcTemplate.batchUpdate(updateSql,new BatchPreparedStatementSetter(){
			public void setValues(PreparedStatement ps,int i) throws SQLException {
				Object[] bean = datas.get(i);
				//System.out.println(bean);
				// log.debug(DaoUtil.converseQesmarkSQL(updateSql,bean));
				 if(bean!=null){
					 int index=1;
					 for (int key=0;key<bean.length;key++) {
						 if(bean[key] instanceof String){//字符串类型
							 if("#newId".equals(bean[key])){//自动成成uuid
								 ps.setString(index,java.util.UUID.randomUUID().toString());
							 }else{
								 ps.setString(index, "".equals(bean[key])?null:bean[key].toString());
							 }
						 }else if(bean[key] instanceof Double){
							 ps.setDouble(index, (Double)bean[key]);
						 }else if(bean[key] instanceof Long){
							 ps.setLong(index, (Long) bean[key]);
						 }else if(bean[key] instanceof Integer){
							 ps.setInt(index, (Integer)bean[key]);
						 }else if(bean[key] instanceof Boolean){
							 ps.setBoolean(index, (Boolean) bean[key]);
						 }else{//空值
							 ps.setString(index, bean[key]!=null?bean[key].toString():null);
						 }
						 index++;
					 } 
				 }
				 //bean = null;
			 }
			@SuppressWarnings("unused")
			public int getBatchSize() {
				if(datas!=null){
					return datas.size();
				}else{
					return 0;
				}
			}
		});
	}
	
	/**
	 * 根据sql查询,返回map数据
	 * @param jdbc
	 * @param sql
	 * @param params
	 * @return
	 */
	public static Map<String,String> queryForMap(JdbcTemplate jdbc,String sql,Object... params){
		Map<String,String> map = new HashMap<String, String>(); 
		SqlRowSet rs = jdbc.queryForRowSet(sql, params);
		while(rs.next()){
			String key = rs.getString(1);
			String value = rs.getString(2);
			
			map.put(key, value);
		}
		return map;
	}
	/**
	 * 
	 * @param jdbc
	 * @param sql
	 * @param params
	 * @return Map<String,Object>
	 */
	public static Map<String,Object> queryForMapObj(JdbcTemplate jdbc,String sql,Object... params){
		Map<String,Object> map = new HashMap<String, Object>();
		SqlRowSet rs = jdbc.queryForRowSet(sql, params);
		while(rs.next()){
			String key = rs.getString(1);
			
			map.put(key, rs.getObject(2));
		}
		return map;
	}
	
	/**
	 * 根据sql查询,返回map数据，以其中某个字段为key
	 * @param jdbc
	 * @param fieldKey sql字段的名称，表示map的key,这个字段必须是String类型的
	 * @param sql
	 * @param params
	 * @return
	 */
	public static Map<String,Map<String,Object>> queryForKeyMap(JdbcTemplate jdbc,String fieldKey,String sql,Object... params){
		Map<String,Map<String,Object>> map = new HashMap<String, Map<String,Object>>();
		SqlRowSet rs = jdbc.queryForRowSet(sql, params);
		
		String[] columns = JdbcTemplateUtils.getColumns(rs);
		String keyValue = null;
		while(rs.next()){
			Map<String,Object> bean = new HashMap<String, Object>();
			
			for(String column : columns){
				if(fieldKey.equals(column)){
					keyValue = rs.getString(column);
				}
				bean.put(column, rs.getObject(column));
			}
			
			map.put(keyValue, bean);
		}
		return map;
	}
	/**
	 * 获取sql中所有列名
	 * @param rs
	 * @return
	 */
	public static String[] getColumns(SqlRowSet rs){
		SqlRowSetMetaData ssmd = rs.getMetaData();
		int count = ssmd.getColumnCount();
		String[] columns = new String[count];
		for(int i=0;i<count;i++){
			columns[i] = ssmd.getColumnLabel(i+1);
		}
		return columns;
	}
	
	public void batcheSaveDataByListMap(final List<Map> addList,final String sql,JdbcTemplate orderJdbcTemplate){
		log.debug("into DAO batcheSaveDataByListMap Method: ");
		try {
			if(sql!=null && !"".equals(sql)){
				orderJdbcTemplate.batchUpdate(sql.toString(),new BatchPreparedStatementSetter(){
					 public void setValues(PreparedStatement ps,int i) throws SQLException {
						 Map bean = addList.get(i);
						 if(bean!=null){
							 Set<String> keySet = bean.keySet(); // 
							 int index = 1;
							 List paramList = new ArrayList();
							 for (String key : keySet) {
								 if(bean.get(key) instanceof String){//字符串类型
									 ps.setString(index, "".equals(bean.get(key))?null:bean.get(key).toString());
									 paramList.add("".equals(bean.get(key))?null:bean.get(key).toString());
									 
								 }else if(bean.get(key) instanceof Double){
									 ps.setDouble(index, (Double)bean.get(key));
									 paramList.add((Double)bean.get(key));
								 }else if(bean.get(key) instanceof Integer){
									 ps.setInt(index, (Integer)bean.get(key));
									 paramList.add((Integer)bean.get(key));
								 }else{//空值
									 ps.setString(index, bean.get(key)!=null?bean.get(key).toString():null);
									 paramList.add("NULL");
								 }
								 index++;
							 } 
							 //log.debug(DaoUtil.converseQesmarkSQL(sql,paramList));
						 }
					 }
					 
					public int getBatchSize() {
						if(addList!=null){
							return addList.size();
						}else{
							return 0;
						}
					}  

				});
			}else{
				log.error(" sql is null  ");
			}
			
		} catch (RuntimeException re) {
			log.error("re:",re);
			log.debug("out DAO batcheSaveDataByListMap Method");
			throw re;
		}
		log.debug("out DAO batcheSaveDataByListMap Method");
	}
	
	
	@SuppressWarnings("unchecked")
	public void saveDataByListMap(JdbcTemplate jdbcTemplate,
			List<Map> addList, String tableName){
		log.debug("into saveDataByListMap()");
		if (StrUtils.isEmpty(addList)) {
			log.debug("addList is null return");
			return;
		}
		StringBuffer sql = null;
		StringBuffer valuesSql = null;
		List<Object> wList = null;
		Iterator<String> it = null;
		boolean f = false;
		for(Map map:addList){
			f = false;
			sql = new StringBuffer();
			valuesSql = new StringBuffer();
			sql.append("insert into "+tableName+" (");
			wList = new ArrayList<Object>();
			it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				if(map.get(key) instanceof JSONObject && ((JSONObject)map.get(key)).isNullObject()){
					continue;
				}
				if (f) {
					sql.append(",");
					valuesSql.append(",");
				}
				sql.append(key);
				wList.add(map.get(key));
				valuesSql.append("?");
				f = true;
			}
			sql.append(") values (");
			sql.append(valuesSql.toString());
			sql.append(") ");
			jdbcTemplate.update(sql.toString(), wList.toArray());
		}
		wList = null;
		it = null;
		valuesSql = null;
		//sql = null;
		//log.debug("---param---sql=[" + sql.toString() + "]");
		// 写debug信息
		//log.debug("out saveDataByListMap()");
	}
	@SuppressWarnings("unchecked")
	public void updateDataByListMap(JdbcTemplate jdbcTemplate,
			List<Map> saveList,List<Map> whereList, String tableName){
		log.debug("init updateDataByMap()");
		if (StrUtils.isEmpty(saveList)) {
			log.debug("saveList is null return");
			return;
		}
		boolean f = false;
		StringBuffer sql = null;
		List<Object> wList = null;
		Iterator<String> sit = null;
		Iterator<String> wit = null;
		Map saveMap=null;
		Map whereMap=null;
		for(int i=0;i<saveList.size();i++){
			f = false;
			sql = new StringBuffer();
			sql.append("update "+tableName+" set ");
			wList = new ArrayList<Object>();
			saveMap = saveList.get(i);
			whereMap = whereList.get(i);
			sit = saveMap.keySet().iterator();
			wit = whereMap.keySet().iterator();
			while (sit.hasNext()) {
				String key = sit.next();
				if (f) {
					sql.append(", ");
				}
				sql.append(key+"=?");
				f = true;
				if(saveMap.get(key) instanceof JSONObject && ((JSONObject)saveMap.get(key)).isNullObject()){
					wList.add(null);
				}else
					wList.add(saveMap.get(key));
			}
			//条件
			sql.append(" where ");
			f = false;
			while (wit.hasNext()) {
				String key = wit.next();
				if (f) {
					sql.append(" and ");
				}
				sql.append(key+"=?");
				f = true;
				wList.add(whereMap.get(key));
			}
			log.debug("---param---sql=[" + sql.toString() + "]");
			log.debug("---param---wList=[" + wList + "]");
			jdbcTemplate.update(sql.toString(), wList.toArray());
		}
		wList = null;
		sit = null;
		wit = null;
		saveMap=null;
		whereMap=null;
		log.debug("---param---sql=[" + sql.toString() + "]");
		log.debug("out updateDataByMap()");
	}
	
	public static void updateDataByMap(JdbcTemplate jdbcTemplate,
			Map saveMap,Map whereMap, String tableName){
		//log.debug("init updateDataByMap()");
		if (StrUtils.isEmpty(saveMap)) {
			log.debug("saveMap is null return");
			return;
		}
		boolean f = false;
		StringBuffer sql = null;
		List<Object> wList = null;
		Iterator<String> sit = null;
		Iterator<String> wit = null;
		
		f = false;
		sql = new StringBuffer();
		sql.append("update "+tableName+" set ");
		wList = new ArrayList<Object>();
		sit = saveMap.keySet().iterator();
		wit = whereMap.keySet().iterator();
		while (sit.hasNext()) {
			String key = sit.next();
			if (f) {
				sql.append(", ");
			}
			sql.append(key+"=?");
			f = true;
			Object object = saveMap.get(key);
			//log.debug(":"+saveMap.get(key));
			if(saveMap.get(key) instanceof JSONObject && ((JSONObject)saveMap.get(key)).isNullObject()){
				wList.add(null);
			}else if("null".equals(object.toString())){
				wList.add(null);
			}else
				wList.add(saveMap.get(key));
		}
		//条件
		sql.append(" where ");
		f = false;
		while (wit.hasNext()) {
			String key = wit.next();
			if (f) {
				sql.append(" and ");
			}
			sql.append(key+"=?");
			f = true;
			wList.add(whereMap.get(key));
		}
		//log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql.toString(), wList.toArray()));
		jdbcTemplate.update(sql.toString(), wList.toArray());
		wList = null;
		sit = null;
		wit = null;
		saveMap=null;
		whereMap=null;
		//log.debug("---param---sql=[" + sql.toString() + "]");
		//log.debug("out updateDataByMap()");
	}
	
	public static void saveDataByMap(JdbcTemplate jdbcTemplate,
			Map map, String tableName){
		//log.debug("into saveDataByListMap()");
		if (StrUtils.isEmpty(map)) {
			log.debug("map is null return");
			return;
		}
		StringBuffer sql = new StringBuffer();
		StringBuffer valuesSql = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
		@SuppressWarnings("unchecked")
		Iterator<String> it = map.keySet().iterator();
		boolean f = false;
		
		sql.append("insert into "+tableName+" (");
		while (it.hasNext()) {
			String key = it.next();
			if(key==null || "".equals(key) || ( map.get(key) instanceof JSONObject && ((JSONObject)map.get(key)).isNullObject())){
				continue;
			}
			if (f) {
				sql.append(",");
				valuesSql.append(",");
			}
			if(key.startsWith("#SEQ#")){//使用Oracle SEQ
				valuesSql.append(map.get(key));
				sql.append(key.replace("#SEQ#", ""));
			}else{
				sql.append(key);
				args.add(map.get(key));
				valuesSql.append("?");
			}
			f = true;
		}
		sql.append(") values (");
		sql.append(valuesSql.toString());
		sql.append(") ");
		log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql.toString(), args.toArray()));
		jdbcTemplate.update(sql.toString(), args.toArray());
		args = null;
		it = null;
		valuesSql = null;
		//log.debug("out saveDataByListMap()");
	}

	@SuppressWarnings("rawtypes")
	public static Map getMap(String sql,JdbcTemplate jdbcTemplate){
		List list = jdbcTemplate.queryForList(sql);
		if(StrUtils.isNotEmpty(list)){
			Map m = (Map) list.get(0);
			return m;
		}else{
			return null;
		}
	}
	
	public static Map getMap(JdbcTemplate jdbcTemplate,String sql, Object... args) {
		List list = jdbcTemplate.queryForList(sql,args);
		if(StrUtils.isNotEmpty(list)){
			Map m = (Map) list.get(0);
			return m;
		}else{
			return new HashMap();
		}
	}

	@SuppressWarnings("rawtypes")
	public static Map getMap(String sql, Object[] args,
			JdbcTemplate jdbcTemplate) {
		List list = jdbcTemplate.queryForList(sql,args);
		if(StrUtils.isNotEmpty(list)){
			Map m = (Map) list.get(0);
			return m;
		}else{
			return null;
		}
	}
	
	
	
}

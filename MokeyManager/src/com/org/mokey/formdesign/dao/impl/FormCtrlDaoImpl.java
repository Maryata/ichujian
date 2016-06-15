package com.org.mokey.formdesign.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.org.mokey.formdesign.dao.FormCtrlDao;
import com.org.mokey.formdesign.entiy.FormCtrlListData;

public class FormCtrlDaoImpl implements FormCtrlDao {
	protected  Logger log = (Logger.getLogger(getClass()));
	private JdbcTemplate jdbcTemplate;	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public long findBySql(String sql, Object[] paramsArr, String jdbc) {
		return 0;
	}

	@Override
	public List<?> findBySql(String sql, Object[] paramsArr, JSONObject cols,
			int currentRow, int pageSize, String jdbc) {
		return null;
	}
	
	@SuppressWarnings("unused")
	private class JsonObjectListRowMapper implements RowMapper<JSONObject> { 
		
		private JSONObject cols;
		public JsonObjectListRowMapper(JSONObject cols)
		{
			this.cols = cols;
		}
	    public JSONObject mapRow(ResultSet rs, int index) throws SQLException { 	    	
	    	JSONObject row = new JSONObject();
	    	ResultSetMetaData rsmd = rs.getMetaData();
	    	for (int i=0; i<rsmd.getColumnCount() ; i++) {
	    		if(index<1)
	    		{
	    			cols.put(rsmd.getColumnName(i+1), rsmd.getColumnName(i+1));
	    		}
	    		//log.debug(rsmd.getColumnName(i+1) + " : "+ rsmd.getColumnTypeName(i+1));
	    		row.put(rsmd.getColumnName(i+1), rs.getString(rsmd.getColumnName(i+1)));
					
			}    	  		  
	        return row;  
	    }  
	}

	@Override
	public List<?> findBySql(String sql, Object[] paramsArr,
			String displayfield, String valuefield) {
		return jdbcTemplate.query(sql, paramsArr,new FormCtrlListRowMapper(displayfield,valuefield)) ;
	}
	
	private class FormCtrlListRowMapper implements RowMapper<FormCtrlListData> {  
		
		private String displaycol;
		private String valuecol;
		public FormCtrlListRowMapper(String displaycol ,String valuecol)
		{
			this.displaycol=displaycol.trim();
			this.valuecol=valuecol.trim();
		}
	    public FormCtrlListData mapRow(ResultSet rs, int index) throws SQLException { 	    	
	    	FormCtrlListData ctrldata = new FormCtrlListData();  	  
	    	ctrldata.setId(index);
	    	ctrldata.setDisplayData(rs.getString(this.displaycol));	    	
	    	ctrldata.setValueData(rs.getString(this.valuecol));	    	  		  
	        return ctrldata;  
	    }  
	}

	@Override
	public List<?> findSeletData(String sql) {
		return jdbcTemplate.queryForList(sql);
	}

}

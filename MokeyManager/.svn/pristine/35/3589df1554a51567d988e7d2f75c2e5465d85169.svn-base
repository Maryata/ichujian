package com.org.mokey.formdesign.dao;

import java.util.List;

import net.sf.json.JSONObject;

public interface FormCtrlDao {

	long findBySql(String sql, Object[] paramsArr, String jdbc);

	List<?> findBySql(String sql, Object[] paramsArr, JSONObject cols,
			int currentRow, int pageSize, String jdbc);

	List<?> findBySql(String sql, Object[] paramsArr, String displayfield,
			String valuefield);

	List<?> findSeletData(String sql);

}

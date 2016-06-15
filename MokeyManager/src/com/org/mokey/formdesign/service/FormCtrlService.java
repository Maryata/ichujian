package com.org.mokey.formdesign.service;

import java.util.List;

import net.sf.json.JSONObject;

public interface FormCtrlService {

	long findDataList(String sql, Object[] paramsArr, String jdbc);

	List<?> findDataList(String sql, Object[] paramsArr, JSONObject cols,
			int currentRow, int pageSize, String jdbc);

	List<?> findDataList(String sql, Object[] paramsArr, String displayfield,
			String valuefield);
	
	List<?> findSeletData(String type);

}

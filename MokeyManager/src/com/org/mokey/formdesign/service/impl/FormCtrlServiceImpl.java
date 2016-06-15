package com.org.mokey.formdesign.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import com.org.mokey.formdesign.dao.FormCtrlDao;
import com.org.mokey.formdesign.service.FormCtrlService;

public class FormCtrlServiceImpl implements FormCtrlService {
	
	private FormCtrlDao formCtrlDao;
	

	public FormCtrlDao getFormCtrlDao() {
		return formCtrlDao;
	}

	public void setFormCtrlDao(FormCtrlDao formCtrlDao) {
		this.formCtrlDao = formCtrlDao;
	}

	@Override
	public long findDataList(String sql, Object[] paramsArr, String jdbc) {
		return formCtrlDao.findBySql(sql, paramsArr,jdbc);
	}

	@Override
	public List<?> findDataList(String sql, Object[] paramsArr, JSONObject cols,
			int currentRow, int pageSize, String jdbc) {
		return formCtrlDao.findBySql(sql, paramsArr , cols ,currentRow, pageSize,jdbc);
	}

	@Override
	public List<?> findDataList(String sql, Object[] paramsArr,
			String displayfield, String valuefield) {
		return formCtrlDao.findBySql(sql, paramsArr,displayfield,valuefield);
	}

	@Override
	public List<?> findSeletData(String sql) {
		return formCtrlDao.findSeletData(sql);
	}

}

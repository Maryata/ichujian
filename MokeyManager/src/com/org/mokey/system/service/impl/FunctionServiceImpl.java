package com.org.mokey.system.service.impl;

import java.util.Map;

import com.org.mokey.system.dao.FunctionDao;
import com.org.mokey.system.service.FunctionService;

public class FunctionServiceImpl implements FunctionService {

	private FunctionDao functionDao;
	public FunctionDao getFunctionDao() {
		return functionDao;
	}

	public void setFunctionDao(FunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	@Override
	public void DeleteFunction(String id) {
		// TODO Auto-generated method stub
		functionDao.DeleteFunction(id);
	}

	@Override
	public Map<String, Object> GetFunctionList(String name, int start, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> map=functionDao.GetFunctionList(name, start, limit);
		return map;
	}

	@Override
	public void SaveFunction(Map<String, Object> map) {
		// TODO Auto-generated method stub
		functionDao.SaveFunction(map);
	}

	@Override
	public Map<String, Object> CheckName(String name, String id) {
		// TODO Auto-generated method stub
		Map<String, Object> map=functionDao.CheckName(name, id);
		return map;
	}

	@Override
	public Map<String, Object> findName() {
		// TODO Auto-generated method stub
		Map<String, Object> map=functionDao.findName();
		return map;
	}

}

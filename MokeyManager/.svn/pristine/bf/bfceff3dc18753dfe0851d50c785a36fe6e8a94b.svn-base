package com.org.mokey.basedata.sysinfo.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.org.mokey.basedata.sysinfo.dao.SupplierDao;
import com.org.mokey.basedata.sysinfo.service.SupplierService;

public class SupplierServiceImpl implements SupplierService {
	private static final Logger log = Logger.getLogger( SupplierServiceImpl.class );
	 private SupplierDao supplierdao;

	@Override
	public Map<String, Object> checkCodeandName(String code, String name) {
		// TODO Auto-generated method stub
		Map<String,Object> map=supplierdao.checkCodeandName(code, name);
		return map;
	}

	@Override
	public void deleteSupplier(String id) {
		// TODO Auto-generated method stub
		supplierdao.deleteSupplier(id);
	}

	@Override
	public Map<String, Object> getSupplierList(String code, String name,
			int start, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> map=supplierdao.getSupplierList(code, name, start, limit);
		return map;
	}

	@Override
	public void saveSupplier(Map<String, Object> map) {
		// TODO Auto-generated method stub
		supplierdao.saveSupplier(map);
	}
	
	@Override
	public void decodingAndBuildingApk(String code) {
		try {
			supplierdao.decodingAndBuildingApk(code);
		} catch (Exception e) {
			log.info( "decodingAndBuildingApk failed ...." + e.getLocalizedMessage() );
		}
	}

	public SupplierDao getSupplierdao() {
		return supplierdao;
	}

	public void setSupplierdao(SupplierDao supplierdao) {
		this.supplierdao = supplierdao;
	}

	@Override
	public Map<String, Object> checkCodeandNameBymodify(String code,
			String name, String id) {
		// TODO Auto-generated method stub
		Map<String, Object> retMap=supplierdao.checkCodeandNameBymodify(code, name, id);
		return retMap;
	}

	@Override
	public List getSuppliers() {
		return supplierdao.getSuppliers();
	}
	
}

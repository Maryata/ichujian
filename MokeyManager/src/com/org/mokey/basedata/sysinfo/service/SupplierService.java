package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

public interface SupplierService {

	Map<String, Object> getSupplierList(String code,String name,int start,int limit);
	
    void deleteSupplier(String id);
    
    void saveSupplier(Map<String, Object> map);
    
    Map<String, Object> checkCodeandName(String code,String name);
    
    Map<String, Object> checkCodeandNameBymodify(String code,String name,String id);

	List getSuppliers();
	
	void decodingAndBuildingApk(String code);
}


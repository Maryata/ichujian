package com.org.mokey.basedata.sysinfo.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

public interface SupplierDao {
	Map<String, Object> getSupplierList(String code,String name,int start,int limit);
	
    void deleteSupplier(String id);
    
    void saveSupplier(Map<String, Object> map);
    
    Map<String, Object> checkCodeandName(String code,String name);
    
    Map<String, Object> checkCodeandNameBymodify(String code,String name,String id);

	List<Map<String,Object>> getSuppliers();
	
	void decodingAndBuildingApk(String code) throws IOException, InterruptedException, DocumentException;
}

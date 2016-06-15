package com.org.mokey.basedata.baseinfo.service;

import java.util.List;
import java.util.Map;

public interface ActivityFollowSchemeService {
	//查询（模糊查询）
	public Map<String, Object> getActivityFollowSchemeListMap(String c_name,String c_cityid,
		int start, int limit);
	//添加
	public String savaActivityFollowScheme(Map<String, Object> saveMap);
	
	public void deleteActivityFollowScheme(String c_id);
	
	public Map<String, Object> checkActivityFollowScheme(String c_id,String c_name,String value);
	//查询所有行业
	public Map<String, Object> findAllIndustryName();
	
	//查询所有的省
	public Map<String, Object> findAllProvince();
	//根据对应的省查询下面所有的城市
	public Map<String, Object> findAllCityById(String provinceId);
	//查询品牌对应行业的信息
	public Map<String, Object> findIndustryBrand(String c_industryid,String c_cname,int start, int limit);
	//模糊匹配下拉框中的城市
	public Map<String, Object> findByName(String name);
	//获得添加后的id
	int addCourse(Map<String, Object> saveParam);
	//查询所有的城市
	public Map<String, Object> findAllCity();
	public Map returnProvince(String id);
	public Map getSelectCity(String parameter);
	public Map getNotSelectCity(String parameter, String parameter2);
	public List getSaveItemsForUpdate(String cityID);
	public void deleteTwoTablesByProvinceID(String provinceID);
	public List getSaveItemsForUpdateZero();
	public List getSaveItemsForUpdateMoRen(String mainId);
	public void deleteTwoTablesByMoRenCityID(String mainID);     

}

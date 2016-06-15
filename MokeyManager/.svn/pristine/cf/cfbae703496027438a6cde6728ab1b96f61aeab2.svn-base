package com.org.mokey.basedata.baseinfo.service;

import java.util.List;
import java.util.Map;

public interface ActionRecommendSchemeService {
	//查询（模糊查询）
	public Map<String, Object> getActionRecommendScheme(String c_name,String c_cityid,String c_type,
			int start, int limit);
	//添加
	public String savaActionRecommendScheme(Map<String, Object> saveMap);
	
	public void deleteActionRecommendScheme(String c_id);
	
//	public Map<String, Object> checkActionRecommendScheme(String c_id,String c_name,String value);
//	int addCourse(Map<String, Object> saveParam);
	//查询广告位
	public Map<String, Object> findAllAdvertise();
	//查询所有的推荐内容
	public Map<String, Object> findAllRecommend();
	//根据菜单id和城市id查询对应的广告图片
	public Map<String, Object> findAdvertiseImages(String cityid,String meunid);
	//保存首页推荐方案表详细
	public String savaRecommendSchemeDetail(Map<String, Object> Map);
	public int getNextID();
	//根据id查询是否有广告位
	public Map<String, Object> findById(String c_id);
	//根据cid删除详细表
	public void deleteById(String cid) ;
	
	//查询方案详情列表
	public List findContentTypeById(String cId);

	public List queryRecommendScheme(String cityid,String typeid);
	
	public void deleteRecommendScheme(String cityid,String typeid);
	
	//public void inserRecommendScheme(String cid,String cname,String cityid,String typeid,)
	public void deleteSchemeDetail(String schemeId);
	
	public int querySchenmeList(String cityid ,String contentid);
	
	//根据城市id查询对应的contentid
	public List findContentId(String cityid);
	
}

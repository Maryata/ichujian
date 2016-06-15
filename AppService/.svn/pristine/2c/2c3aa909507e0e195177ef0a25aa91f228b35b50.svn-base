package com.sys.service;

import java.util.List;

public interface ClassifiedService {
	//分类热门品牌更多
	public List getHotBrand(String industryid,String cityid,String type);
	
	//分类活动性质类别
	public List getPropertyType();
	
	//按行业内容类别首页接口
	public List getBaseInfo(String industryid,String cityid,String propertyid);
	
	//分类列表
	public List ClassiFiedDetail(String industyid,String pageindex);
	
	//用户查询 记录保存
	public  void saveSearchRecord(String uid,String type,String indexid);  
	
	//分类区域查询
	public List getBusinessArea(String cityid);
	
	//分类商圈查询
	public List getBusinessZone(String areaid);
}

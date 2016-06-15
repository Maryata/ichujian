package com.sys.service;

import java.util.List;

public interface RecommendService {
	
	public int getRecommendCity(String cityname);     //查询是否有城市

	public List getRecommendFollow(String cityname);  //注册后推荐
	
	public List getRecommendFollowNull();            //找不到城市对应的方案 默认为0
	
	public int getMainIndustry(String cityid);     //查询是否有推荐行业
	public int getMainAdvert(String cityid);     //查询是否有推荐轮播广告
	public int getMainAdvertMin(String cityid);     //查询是否有推荐小广告
	
	public List getRecommendMainIndustry(String cityid);   //首页推荐的行业
	
	public List getRecommendMainAdvert(String cityid);   //首页推荐轮播广告
	
	public List getRecommendMainAdvertMin(String cityid);   //首页推荐小广告
	
	public int getRecommend(String cityid,String type);   //查询是否有热门或者推荐方案
	
	public List getRecommendMainHot(String cityid,String pageindex);   //首页热门
	
	public List getRecommendMainPageTitle(String cityid,String pageindex);   //首页推荐中的小标题
	public List getRecommendMainPage(String activityid);                  //首页推荐标题中的活动
	
	public List getCityList();   //城市列表
	
	public List getHotCityList();   //热门城市列表
	
}

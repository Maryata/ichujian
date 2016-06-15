package com.sys.service;

import java.util.List;

public interface SearchService {
	//关键字接口
	public List getSearch();
	//品牌搜索
	public List getSearchBrand(String textMsg,String imei,String pageIndex);
	//活动搜索
	public List getSearchActivity(String textMsg,String cityid,String uid,String pageIndex);
	//搜索记录表
	public void setSearchRecord(String textMsg,String imei);
	//品牌关注的人数
	public int brandAttentionNum(String brandid,String brandType);
	//是否关注该品牌
	public int isAttention(String brandid,String uid,String brandType);
}

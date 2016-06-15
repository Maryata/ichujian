package com.sys.service;

import java.util.List;
import java.util.Map;

public interface ActivityDetailService {
	
	
	//活动详情接口
	public List getActivityDetail(String activityid);
	//我要参加接口
	public void joinActivityAndCollectionToShare(String uid,String activityid,String type);
	//参加该活动的、收藏，评论人数,
	public int jionNum(String activityid);
	//取消收藏
	public void activityCancle(String uid,String activityid,String activityType,String state);
	//关注该品牌的人
	public int attentionNum(String brandid);
	//品牌详情
	public List brandDetail(String brandid);
	//是否参入，收藏该活动
	public List isJionOrCollect(String activityid,String uid);
	//品牌关注
	public void brandAttention(String uid,String brandid,String type);
	//是否关注品牌
	public int isAttention(String uid,String brandid);
	//品牌取消关注
	public void brandCancelAttention(String uid,String brandid,String type,String state);
	//查询是否存在收藏记录
	public int isExitCollection(String uid,String activityid,String type);
	//品牌相关活动及分页接口
//	public List brandAboutActivity(String cityid,String brandid,int pageIndex);
	public List brandAboutActivity(String brandid,int pageIndex);
	//查询是否存在关注记录
	public int isExit(String uid,String brandid,String type);
	//记录查看(活动)
	public void record(String uid,String activityid);
	//记录查看（品牌）
	public void record2(String imei,String brandid);
	//活动分类接口
	public List activitySort();
	//是否收藏活动
	public int isFavoriteAct(String uid, String activityid, String type);

	
 }

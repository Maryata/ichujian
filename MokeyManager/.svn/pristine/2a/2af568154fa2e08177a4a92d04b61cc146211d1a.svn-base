package com.org.mokey.basedata.baseinfo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ActionFourMainTainAdService {
	//获得城市下拉框列表
	public  List getCityList();
//获得菜单下拉列表
	public List getMenuList();
//获得 概要信息
	public Map getSummaryMessage(String cityID, String menuID,int start,int limit);
//	根基ID删除
	public void deleteById(String cId,String MID); 
	//获得行业类别
	public List getIndustryCat();
	//获得活动内容类别
	public List getActivityCat();
//	public List getInnerSummaryMessage(String cityid, String industyid,
//			String activityid, String title);
	public void saveAll(String menuID, String cityID, String pic, Date date,
			String id);
	public List getUpdateCityList(String id);
	public List getUpdateMenuList(String updateId);
	public void saveUpdateAll(String menuID, String cityID, String pic, 
			Date date, String id, String saveID);
	 public Map getInnerSummaryMessage(String cityid, String industyid,
			String activityid, String title, int start, int limit);
	public Map getPicsAndIds(String id);
	public Map getTitle(String string);
	public void update(String iD, String pics, String ids);
	public int isExist(String cityID, String menuID);
	public String getNextID();
	public void saveNewTable(String cityID, String menuID, String nextID);           
	
} 
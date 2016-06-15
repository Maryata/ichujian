package com.sys.service;

import java.util.Date;
import java.util.List;

import javax.activation.DataSource;

public interface SetService {
	
	
	   //先查询
	   public List FindClick(String imei,String clicktype,String key,String choosetype);
	   public List FindHold(String imei,String holdtype);
	   public List FindHold(String imei,String holdtype,String key,String choosetype);
	   
	   public List FindSos(String userid,String imei);
	   //有记录则更新 
	   public void UpdateClick(String imei,String clicktype,String name_package,Date actiondate,String key,String choosetype);
	   
	   public void UpdateOtherClick(String imei,String clicktype,String key,String choosetype); //更新其他newest为0
	   
	   //修改当前设置为最新
	   public void UpdateHold(String imei,String holdtype);
	   public void UpdateHold(String imei,String holdtype,String key,String choosetype);
	   
	   //修改其他设置不是最新
	   public void UpdateOtherHold(String imei,String holdtype); //更新其他newest为0
	   public void UpdateOtherHold(String imei,String holdtype,String key,String choosetype); //更新其他newest为0
	   
	   public void UpdateSos(String userid,String imei,String sostype,String behavior,String soscontent,String objective,Date actiondate);
	
	   //无记录则新增
	   //点击
       public void Click(String imei,String clicktype,String name_package,Date actiondate,String key,String choosetype);
       //长按
       public void Hold(String imei,String holdtype);
       public void Hold(String imei,String holdtype,Date actiondate,String key,String choosetype);
       //SOS
       public void Sos(String userid,String imei,String sostype,String behavior,String soscontent,String objective,Date actiondate);
   
       //单击设置历史
       public void UpdateClickHis(String imei,String clicktype,String key,String choosetype);     //更改其他newest=0
       public void ClickHis(String imei,String clicktype,String key,String package_name,String app_name,Date actiondate,String choosetype);    //新增当前设置
       
       //长按设置历史
       public void UpdateHoldHis(String imei,String holdtype,String key,String choosetype);     //更改其他newest=0
       public void HoldHis(String imei,String holdtype,String key,Date actiondate,String choosetype,String appname,String apppackage); 
}

package com.sys.service;

import java.util.Date;
import java.util.List;

public interface NewsService {

	  //精品推荐
      public List GoodNews();
      
      //新版本的
      public List GoodNews(String type,String category);
      
      //已安装
      public void Installed(String imei,String type,String package_name,String app_name,Date actiondate);
      
      //查询是否有记录
      public List findList(String imei,String type,String package_name,String app_name);
      
      //有记录更新
      public void updateList(String imei,String type,String package_name,String app_name,Date actiondate,String ID);
      
      //详情页面
      public List NewsDetails(String id);
      //下载的app
      public void DownApp(String imei,String type,String key,String package_name,String app_name,Date actiondate);
      //卸载的app
      public void UnloadApp(String imei,String type,String key,String package_name,String app_name,Date actiondate);
      
      //删除安装表记录
      public void deleteAppInfo(String id);
      
      //广告信息
      public List Advert(String type);
      
      //首页广告信息
      public List MainAdvert(String type);
      
      
}

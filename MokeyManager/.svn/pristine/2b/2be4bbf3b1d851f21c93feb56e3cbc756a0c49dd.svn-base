package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKActivityIndexDao;
import com.org.mokey.basedata.sysinfo.service.EKActivityIndexService;
import com.org.mokey.basedata.sysinfo.util.JsonTools;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/7.
 * e键 ： 活动首页
 */
public class EKActivityIndexServiceImpl implements EKActivityIndexService{
    private EKActivityIndexDao ekActivityIndexDao;

    public EKActivityIndexDao getEkActivityIndexDao() {
        return ekActivityIndexDao;
    }

    public void setEkActivityIndexDao(EKActivityIndexDao ekActivityIndexDao) {
        this.ekActivityIndexDao = ekActivityIndexDao;
    }

    @Override
    public void save(String headLine, String activityInfo, String activity) {
        //------------------------活动头条-----------------
        List<String> eidList = new ArrayList();
        if(!"".equals(headLine)){
            List<Map<String, Object>> headLineList =new ArrayList();
            headLineList = JsonTools.parseJSON2List(headLine);
            for(Map<String, Object> item1 : headLineList){
                String id = String.valueOf(item1.get("id"));
                if(!"".equals(id)){
                    eidList.add(id);
                }
            }
        }
        //查询原数据  是否包含id  如果不包含  删除
        if(eidList.size()>0){
            List<Map<String, Object>> oldheadLineList=ekActivityIndexDao.getoldheadLineList();
            for(Map itemeid : oldheadLineList){
                if(!eidList.contains(String.valueOf(itemeid.get("C_ID")))){//不包含 删除
                    ekActivityIndexDao.deleteHeadLine(String.valueOf(itemeid.get("C_ID")));
                }
            }
        }
        if(!"".equals(headLine)){
            List<Map<String, Object>> headLineList =new ArrayList();
            headLineList = JsonTools.parseJSON2List(headLine);
            for(Map<String, Object> itemeid : headLineList){
                String id = String.valueOf(itemeid.get("id"));
                String eid = String.valueOf(itemeid.get("eid"));
                String name = String.valueOf(itemeid.get("name"));
                String type = String.valueOf(itemeid.get("type"));
                String logo = String.valueOf(itemeid.get("logo"));
                String order = String.valueOf(itemeid.get("order"));
                if(StringUtils.isEmpty(id)){//添加
                    ekActivityIndexDao.addHeadLine(eid,name,type,logo,order);
                }else{//更新
                    ekActivityIndexDao.updateHeadLine(id,eid,name,type,logo,order);
                }
            }
        }


        //------------------------活动分类-----------------
        List<String> cidList = new ArrayList();
        if(!"".equals(activityInfo)){
            List<Map<String, Object>> activityInfoList =new ArrayList();
            activityInfoList = JsonTools.parseJSON2List(activityInfo);
            for(Map<String, Object> item2 : activityInfoList){
                String id = String.valueOf(item2.get("id"));
                if(!"".equals(id)) {
                    cidList.add(id);
                }
            }
        }

        //查询原数据  是否包含id  如果不包含  删除
        if(cidList.size()>0){
            List<Map<String, Object>> oldActivityInfoList=ekActivityIndexDao.getoldActivityInfoList();
            for(Map itemcid : oldActivityInfoList){
                if(!cidList.contains(String.valueOf(itemcid.get("C_ID")))){//不包含 删除
                    ekActivityIndexDao.deleteActivityInfo(String.valueOf(itemcid.get("C_ID")));
                }
            }
        }
        if(!"".equals(activityInfo)){
            List<Map<String, Object>> activityInfoList =new ArrayList();
            activityInfoList = JsonTools.parseJSON2List(activityInfo);
            for(Map<String, Object> itemcid : activityInfoList){
                String id = String.valueOf(itemcid.get("id"));
                String cid = String.valueOf(itemcid.get("eid"));
                String order = String.valueOf(itemcid.get("order"));
                if(StringUtils.isEmpty(id)){//添加
                    ekActivityIndexDao.addActivityInfo(cid,order);
                }else{//更新
                    ekActivityIndexDao.updateActivityInfo(id,cid,order);
                }
            }
        }


        //------------------------活动详情-----------------
        List<String> aidList = new ArrayList();
        if(!"".equals(activity)){
            List<Map<String, Object>> activityList =new ArrayList();
            activityList = JsonTools.parseJSON2List(activity);
            for(Map<String, Object> item3 : activityList){
                String id = String.valueOf(item3.get("id"));
                if(!"".equals(id)) {
                    aidList.add(id);
                }
            }
        }

        //查询原数据  是否包含id  如果不包含  删除
        if(aidList.size()>0){
            List<Map<String, Object>> oldActivityList=ekActivityIndexDao.getoldActivityList();
            for(Map itemaid : oldActivityList){
                if(!aidList.contains(String.valueOf(itemaid.get("C_ID")))){//不包含 删除
                    ekActivityIndexDao.deleteActivity(String.valueOf(itemaid.get("C_ID")));
                }
            }
        }
        if(!"".equals(activity)){
            List<Map<String, Object>> activityList =new ArrayList();
            activityList = JsonTools.parseJSON2List(activity);
            for(Map<String, Object> itemaid : activityList){
                String id = String.valueOf(itemaid.get("id"));
                String aid = String.valueOf(itemaid.get("aid"));
                String ccid = String.valueOf(itemaid.get("ccid"));
                String tagid = String.valueOf(itemaid.get("tagid"));
                String order = String.valueOf(itemaid.get("order"));
                if(StringUtils.isEmpty(id)){//添加
                    ekActivityIndexDao.addActivity(aid,ccid,tagid,order);
                }else{//更新
                    ekActivityIndexDao.updateActivity(id,aid,ccid,tagid,order);
                }
            }
        }
    }

    @Override
    public List<Map<String, Object>> getoldheadLineList() {
        return  ekActivityIndexDao.getoldheadLineList();
    }

    @Override
    public List<Map<String, Object>> getoldActivityInfoList() {
        return ekActivityIndexDao.getoldActivityInfoList();
    }

    @Override
    public List<Map<String, Object>> getoldActivityList() {
        return ekActivityIndexDao.getoldActivityList();
    }
}

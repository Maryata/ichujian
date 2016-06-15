package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKActivityCategoryTagService;
import com.org.mokey.basedata.sysinfo.service.EKActivityIndexService;
import com.org.mokey.basedata.sysinfo.util.Singleton;
import com.org.mokey.common.AbstractAction;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/7.
 * e键 ： 活动首页
 */
public class EKActivityIndexAction extends AbstractAction {
    private EKActivityIndexService ekActivityIndexService;
    private String out;
    private JdbcTemplate jdbcTemplate;
    public EKActivityIndexService getEkActivityIndexService() {
        return ekActivityIndexService;
    }
    public EKActivityCategoryTagService getEkActivityCategoryTagService() {
        return ekActivityCategoryTagService;
    }

    public void setEkActivityCategoryTagService(EKActivityCategoryTagService ekActivityCategoryTagService) {
        this.ekActivityCategoryTagService = ekActivityCategoryTagService;
    }

    private EKActivityCategoryTagService ekActivityCategoryTagService;
    public void setEkActivityIndexService(EKActivityIndexService ekActivityIndexService) {
        this.ekActivityIndexService = ekActivityIndexService;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 跳转到 活动首页
     * @return
     */
    public String toEKActivityIndex(){
        return "toActivityIndex";
    }

    /**
     * 跳转到  活动头条页面
     * @return
     */
    public String toEKActivityHeadLineList(){
        String headLine = getParameter("headLine");
        String flag = getParameter("flag");
        Map<String, String> map=new HashMap<String, String>();
        if("[]".equals(headLine) || "".equals(headLine) || "null".equals(headLine) || headLine==null){
             if("1".equals(flag)){
                 Singleton.getInstance().setMap(map);
             }
            headLine = Singleton.getInstance().getMap().get("key");
        }else{
            map.put("key",headLine);
           Singleton.getInstance().setMap(map);
        }

        getRequest().setAttribute("headLine", headLine);
        return "toEKActivityHeadLineList";
    }

    /**
     * 跳转到活动分类  选择页面   toEKActivityCagetoryInfoList
     */
    public String toEKActivityCagetoryInfoList(){
        String activityInfos = getParameter("activityInfos");
        String flag = getParameter("flag");
        Map<String, String> acmap=new HashMap<String, String>();
        if("[]".equals(activityInfos) || "".equals(activityInfos) || "null".equals(activityInfos) || activityInfos==null){
            if("1".equals(flag)){
                Singleton.getInstance().setMap(acmap);
            }
            activityInfos = Singleton.getInstance().getMap().get("key1");
        }else{
            acmap.put("key1",activityInfos);
            Singleton.getInstance().setMap(acmap);
        }

        getRequest().setAttribute("activityInfos", activityInfos);
        return "toEKActivityCagetoryInfoList";
    }

    /**
     * 跳转活动首页  -----》活动详情
     * @return
     */
    public String toEKActivityInfo(){
        String activity = getParameter("activity");
        String flag = getParameter("flag");
        Map<String, String> activitymap=new HashMap<String, String>();
        if("[]".equals(activity) || "".equals(activity) || "null".equals(activity) || activity==null){
            if("1".equals(flag)){
                Singleton.getInstance().setMap(activitymap);
            }
            activity = Singleton.getInstance().getMap().get("key2");
        }else{
            activitymap.put("key2",activity);
            Singleton.getInstance().setMap(activitymap);
        }
       //角标 list
        List<Map<String, Object>> List = ekActivityCategoryTagService.getList();
        getRequest().setAttribute("activity", activity);
        getRequest().setAttribute("List", List);
        return "toEKActivityInfo";
    }

    /**
     * 活动首页  ：  添加
     * @return
     */
    public String save (){
        String headLine = getParameter("headLine");
        String activityInfo = getParameter("activityInfo");
        String activity = getParameter("activity");
        ekActivityIndexService.save(headLine,activityInfo,activity);

    return "toActivityIndex";
    }

    /**
     * 活动首页  数据查询
     */
    public String selectActivityIndexList0(){
        List<Map<String, Object>> getoldheadLineList= ekActivityIndexService.getoldheadLineList();
        try {
            this.writeJSONToResponse(getoldheadLineList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }
    public String selectActivityIndexList1(){
        List<Map<String, Object>> getoldActivityInfoList=ekActivityIndexService.getoldActivityInfoList();
        try {
            this.writeJSONToResponse(getoldActivityInfoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }
    public String selectActivityIndexList2(){
        List<Map<String, Object>> getoldActivityList=ekActivityIndexService.getoldActivityList();
        try {
             this.writeJSONToResponse(getoldActivityList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

}

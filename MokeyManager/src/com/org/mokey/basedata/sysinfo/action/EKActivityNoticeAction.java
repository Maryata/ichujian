package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKActivityNoticeService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ： 活动注意事项
 */
public class EKActivityNoticeAction extends AbstractAction {


    public EKActivityNoticeService getEkActivityNoticeService() {
        return ekActivityNoticeService;
    }

    public void setEkActivityNoticeService(EKActivityNoticeService ekActivityNoticeService) {
        this.ekActivityNoticeService = ekActivityNoticeService;
    }

    private EKActivityNoticeService ekActivityNoticeService;
    private String out;
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }


    // 跳转到活动注意事项列表首页
    public String toEKActivityNoticeList(){
        return "ekActivityNoticeList";
    }

    /**
     * 获取 e键 ： 活动注意事项   ： 列表
     * @return
     */
    public String  ekActivityNoticeList(){
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into EKActivityNoticeAction.ekActivityNoticeList");
        log.info("page=" + page);
      try{
            // 分页显示活动注意事项
            retmap = ekActivityNoticeService.ekActivityNoticeList(page);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        }catch(Exception e){
            retmap.put("status", "N");
            log.error("GameCategoryAction.gameCategoryList failed, e : " + e);
        }
        return NONE;
    }

    // 跳转到添加页面
    public String toAdd(){
        log.info("into EKActivityNoticevAction.toAdd");
        try{
            // 获取主键
            String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_ACTIVITY_NOTICE_BASE");
            getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
            getRequest().setAttribute("cid", newId);
        }catch(Exception e){
            log.error("EKActivityNoticeAction.toAdd failed,",e);
        }
        return "toAdd";
    }

    // 新增分类
    public String addTag(){
        String id = getParameter("cid");// 获取cid
        String name = getParameter("cname");// 分类名称
        String logo = getParameter("logo");// logo
        log.info("into EKActivityNoticeAction.addTag");
        log.info("id=" + id + ", name=" + name + ", logo=" + logo);
        try{
            // 获取当前登录人
           /* String modifier = super.getSessionLoginUser().getUserName();*/
            // 新增
            ekActivityNoticeService.addTag(id, name, logo);
        }catch(Exception e){
            log.error("EKActivityNoticeAction.addTag failed,",e);
        }
        return "reload";
    }

    // 删除分类
    public String toDel(){
        // 获取cid
        String id = getParameter("cid");
        log.info("into EKActivityNoticeAction.toDel");
        log.info("id=" + id);
        try{
            // 删除
            ekActivityNoticeService.toDel(id);
        }catch(Exception e){
            log.error("EKActivityNoticection.toDel failed,",e);
        }
        return NONE;
    }

    // 跳转到分类修改页面
    public String toUpdate(){
        // 获取请求参数
        String id = getParameter("editId");
        String name = getParameter("editName");
        String logo = getParameter("editLogo");
        log.info("into EKActivityNoticeAction.toUpdate");
        log.info("id=" + id + ", name=" + name  + ", logo=" + logo);
        try{
            getRequest().setAttribute("cid", id);
            getRequest().setAttribute("cname", name);
            getRequest().setAttribute("logo", logo);
        }catch(Exception e){
            log.error("EKActivityNoticeAction.toUpdate failed,",e);
        }
        return "toUpdate";
    }

    // 更新分类
    public String updateTag(){
        // 获取请求参数
        String id = getParameter("cid");
        String name = getParameter("cname");
        String logo = getParameter("logo");
        log.info("into EKActivityNoticeAction.updateTag");
        log.info("id=" + id + ", name=" + name + ", logo=" + logo);
        try{
            // 更新
            ekActivityNoticeService.updateTag(id, name, logo);
        }catch(Exception e){
            log.error("EKActivityNoticeAction.updateTag failed,",e);
        }
        return "reload";
    }
}

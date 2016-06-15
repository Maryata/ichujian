package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKHeadLineService;
import com.org.mokey.basedata.sysinfo.util.ZipUtils;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ： 活动头条
 */
public class EKHeadLineAction extends AbstractAction {


    public EKHeadLineService getEkHeadLineService() {
        return ekHeadLineService;
    }

    public void setEkHeadLineService(EKHeadLineService ekHeadLineService) {
        this.ekHeadLineService = ekHeadLineService;
    }

    private EKHeadLineService ekHeadLineService;
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


    // 跳转到活动头条列表首页
    public String toHeadLineList(){
        return "ekHeadLineList";
    }

    /**
     * 获取 e键 ： 活动头条   ： 列表
     * @return
     */

    public String  ekHeadLineList(){
        Map<String, Object> retmap = new HashMap<String, Object>();
       /* String name = getParameter("name");
        String type = getParameter("type");*/
        String sPage = getParameter("page");// 获取page
        log.info("into EKHeadLineAction.ekHeadLineList");
        log.info("sPage=" + sPage);
      try{
          int page = 1;// 默认第一页
          if(null != sPage && sPage.matches( "\\d+" )) {
              page = Integer.parseInt( sPage );
          } else {
              log.info( sPage );
          }
            // 分页显示活动头条
            retmap = ekHeadLineService.ekHeadLineList(page);
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
        log.info("into EKHeadLinevAction.toAdd");
        try{
            // 获取主键
            String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_ACTIVITY_HEADLINE");
            getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
            getRequest().setAttribute("cid", newId);
        }catch(Exception e){
            log.error("EKHeadLineAction.toAdd failed,",e);
        }
        return "toAdd";
    }

    // 新增分类
    public String addHeadLine(){
        String id = getParameter("cid");//
        String name =getParameter("name");//
        String order =getParameter("order");//
        String type =getParameter("type");//
        String logo =getParameter("logo");//
        log.info("into EKHeadLineAction.addHeadLine");
        log.info("id=" + id + ", logo=" + logo);
        try{
            ekHeadLineService.addHeadLine(id,name,order,type,logo);
        }catch(Exception e){
            log.error("EKHeadLineAction.addHeadLine failed,",e);
        }
        return "reload";
    }

    // 删除分类
    public String toDel(){
        // 获取cid
        String id = getParameter("cid");
        log.info("into EKHeadLineAction.toDel");
        log.info("id=" + id);
        try{
            // 删除
            ekHeadLineService.toDel(id);
        }catch(Exception e){
            log.error("EKHeadLinection.toDel failed,",e);
        }
        return NONE;
    }

    // 跳转到分类修改页面
    public String toUpdate(){
        // 获取请求参数
        String id = getParameter("editId");
        String name =getParameter("editName");
        String order =getParameter("editOrder");
        String type =getParameter("editType");
        String logo =getParameter("editLogo");
        log.info("into EKHeadLineAction.toUpdate");
        log.info("id=" + id );
        try{
            //获取  活动分类  list
            getRequest().setAttribute("cid", id);
            getRequest().setAttribute("name", name);
            getRequest().setAttribute("logo", logo);
            getRequest().setAttribute("order", order);
            getRequest().setAttribute("type", type);

        }catch(Exception e){
            log.error("EKHeadLineAction.toUpdate failed,",e);
        }
        return "toUpdate";
    }

    // 更新分类
    public String updateHeadLine(){
        // 获取请求参数
        String id = getParameter("cid");
        String name =getParameter("name");
        String order =getParameter("order");
        String type =getParameter("type");
        String logo =getParameter("logo");
        log.info("into EKHeadLineAction.updateHeadLine");
        log.info("id=" + id);
        try{
            // 更新
            String detail = ZipUtils.compress(getParameter("detail"));// 活动头条
            // 获取当前登录人
            String modifier = super.getSessionLoginUser().getUserName();
            ekHeadLineService.updateHeadLine(id,name,order,type,logo);
        }catch(Exception e){
            log.error("EKHeadLineAction.updateHeadLine failed,",e);
        }
        return "reload";
    }


}

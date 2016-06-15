package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKIndexAdvertService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 首页  ： 广告位
 */
@Controller("eKIndexAdvertAction")
public class EKIndexAdvertAction  extends AbstractAction {
    @Autowired
    private EKIndexAdvertService ekIndexAdvertService;

    private String out;

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 跳转到首页：广告位列表首页
    public String toEKIndexAdvertList(){
        return "ekIndexAdvertList";
    }

    /**
     * 获取 e键 ： 首页：广告位   ： 列表
     * @return
     */
    public String  ekIndexAdvertList(){
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into EKIndexAdvertAction.ekIndexAdvertList");
        log.info("page=" + page);
        try{
            // 分页显示首页：广告位
            retmap = ekIndexAdvertService.ekIndexAdvertList(page);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        }catch(Exception e){
            retmap.put("status", "N");
            log.error("EKIndexAdvertAction.ekIndexAdvertList failed, e : " + e);
        }
        return NONE;
    }

    // 跳转到添加页面
    public String toAdd(){
        log.info("into EKIndexAdvertvAction.toAdd");
        try{
            // 获取主键
            String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_INDEX_ADVERT");
            //获取 app 应用
            String name="";
            List<Map<String,Object>> list=ekIndexAdvertService.getList(name);
            getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
            getRequest().setAttribute("cid", newId);
            getRequest().setAttribute("list", list);
        }catch(Exception e){
            log.error("EKIndexAdvertAction.toAdd failed,",e);
        }
        return "toAdd";
    }

    // 新增分类
    public String addAdvert(){
        String id = getParameter("cid");// 获取cid
        String name = getParameter("name");
        String logo = getParameter("logo");
        String aid = getParameter("aid");
        String order = getParameter("order");
        String type = getParameter("type");

        log.info("into EKIndexAdvertAction.addAdvert");
        log.info("id=" + id + ", name=" + name + ", logo=" + logo);
        try{
            // 获取当前登录人
           /* String modifier = super.getSessionLoginUser().getUserName();*/
            // 新增
            ekIndexAdvertService.addAdvert(id, name, logo,aid,order,type);
        }catch(Exception e){
            log.error("EKIndexAdvertAction.addAdvert failed,",e);
        }
        return "reload";
    }

    // 删除分类
    public String toDel(){
        // 获取cid
        String id = getParameter("cid");
        log.info("into EKIndexAdvertAction.toDel");
        log.info("id=" + id);
        try{
            // 删除
            ekIndexAdvertService.toDel(id);
        }catch(Exception e){
            log.error("EKIndexAdvertction.toDel failed,",e);
        }
        return NONE;
    }

    // 跳转到分类修改页面
    public String toUpdate(){
        // 获取请求参数
        String id = getParameter("editId");
        String name = getParameter("editName");
        String logo = getParameter("editLogo");
        String aid = getParameter("editAid");
        String order = getParameter("editOrder");
        String type = getParameter("editType");
        //获取 app 应用
        String tname="";
        List<Map<String,Object>> list=ekIndexAdvertService.getList(tname);
        log.info("into EKIndexAdvertAction.toUpdate");
        log.info("id=" + id + ", name=" + name  + ", logo=" + logo);
        try{
            getRequest().setAttribute("cid", id);
            getRequest().setAttribute("name", name);
            getRequest().setAttribute("logo", logo);
            getRequest().setAttribute("aid", aid);
            getRequest().setAttribute("order", order);
            getRequest().setAttribute("type", type);
            getRequest().setAttribute("list", list);
        }catch(Exception e){
            log.error("EKIndexAdvertAction.toUpdate failed,",e);
        }
        return "toUpdate";
    }

    // 更新分类
    public String updateAdvert(){
        // 获取请求参数
        String id = getParameter("cid");
        String name = getParameter("name");
        String logo = getParameter("logo");
        String aid = getParameter("aid");
        String order = getParameter("order");
        String type = getParameter("type");
        log.info("into EKIndexAdvertAction.updateAdvert");
        log.info("id=" + id + ", name=" + name + ", logo=" + logo);
        try{
            // 更新
            ekIndexAdvertService.updateAdvert(id, name, logo,aid,order,type);
        }catch(Exception e){
            log.error("EKIndexAdvertAction.updateAdvert failed,",e);
        }
        return "reload";
    }

    public String toSelect(){
        String tname= getParameter("name");
        List<Map<String,Object>> list=ekIndexAdvertService.getList(tname);
        try {
            this.writeJSONToResponse(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }


}

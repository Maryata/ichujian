package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKIndexKeyAppService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 首页  ： 键位app管理
 */
@Controller("eKIndexKeyAppAction")
public class EKIndexKeyAppAction extends AbstractAction {
    @Autowired
    private EKIndexKeyAppService ekIndexKeyAppService;

    private String out;

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 跳转到首页：键位app管理列表首页
    public String toKeyAppList(){

        return "toEKIndexKeyAppList";
    }

    /**
     * 获取 e键 ： 首页：键位app管理   ： 列表
     * @return
     */
    public String  ekIndexKeyAppList(){
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into EKIndexKeyAppAction.ekIndexKeyAppList");
        log.info("page=" + page);
        try{
            // 分页显示首页：键位app管理
            retmap = ekIndexKeyAppService.ekIndexKeyAppList(page);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        }catch(Exception e){
            retmap.put("status", "N");
            log.error("EKIndexKeyAppAction.ekIndexKeyAppList failed, e : " + e);
        }
        return NONE;
    }

    // 跳转到添加页面
    public String toAdd(){
        log.info("into EKIndexKeyAppvAction.toAdd");
        try{
            // 获取主键
            String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_INDEX_KEY_APP");
            //获取 供应商列表
            List<Map<String,Object>> list=ekIndexKeyAppService.getList();
            getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
            getRequest().setAttribute("cid", newId);
            getRequest().setAttribute("list", list);
        }catch(Exception e){
            log.error("EKIndexKeyAppAction.toAdd failed,",e);
        }
        return "toAdd";
    }

    // 新增
    public String addKeyApp(){
        String id = getParameter("cid");// 获取ID
        String key = getParameter("key");//键位
        String supcode = getParameter("supcode");//供应商code
        String name = getParameter("name");
        String title = getParameter("title");
        String logo = getParameter("logo");
        String img = getParameter("img");
        String app = getParameter("app");
        String jar = getParameter("jar");
        String size = getParameter("size");
        String menu = getParameter("menu");
        String menuTel = getParameter("menuTel");
        String abStract = getParameter("abstract");
        String version = getParameter("version");
        String download = getParameter("download");
        String grade = getParameter("grade");
        String h5url = getParameter("h5url");
        String source = getParameter("source");
        String supurl = getParameter("supurl");
        String order = getParameter("order");
        String islive=getParameter("islive");

        log.info("into EKIndexKeyAppAction.addKeyApp");
        log.info("id=" + id + ", name=" + name + ", logo=" + logo);
        try{
            /*创建人*/
          String modifier = super.getSessionLoginUser().getUserName();
          Date cdate = new Date();
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(id);
            argsList.add(key);
            argsList.add(supcode);
            argsList.add(name);
            argsList.add(title);
            argsList.add(logo);
            argsList.add(img);
            argsList.add(app);
            argsList.add(jar);
            argsList.add(size);
            argsList.add(menu);
            argsList.add(menuTel);
            argsList.add(abStract);
            argsList.add(version);
            argsList.add(download);
            argsList.add(grade);
            argsList.add(h5url);
            argsList.add(source);
            argsList.add(supurl);
            argsList.add(order);
            argsList.add(modifier);
            argsList.add(islive);
            argsList.add(cdate);
            // 新增

            ekIndexKeyAppService.addKeyApp(argsList);
        }catch(Exception e){
            log.error("EKIndexKeyAppAction.addKeyApp failed,",e);
        }
        return "reload";
    }

    // 删除
    public String toDel(){
        // 获取cid
        String id = getParameter("cid");
        log.info("into EKIndexKeyAppAction.toDel");
        log.info("id=" + id);
        try{
            // 删除
            ekIndexKeyAppService.toDel(id);
        }catch(Exception e){
            log.error("EKIndexKeyAppction.toDel failed,",e);
        }
        return NONE;
    }

    // 跳转到修改页面
    public String toUpdate(){
        // 获取请求参数
        String cid = getParameter("editId");
        //查询当前id的数据
        List<Map<String,Object>> selectOne = ekIndexKeyAppService.selectOne(cid);
        //获取 app 应用
        List<Map<String,Object>> list=ekIndexKeyAppService.getList();
        log.info("into EKIndexKeyAppAction.toUpdate");
        log.info("id=" + cid );
        try{
            if(selectOne.size()==1){
                for(Map item : selectOne){
                    getRequest().setAttribute("act", item);
                    getRequest().setAttribute("cid", cid);
                }
            }
            getRequest().setAttribute("list", list);
        }catch(Exception e){
            log.error("EKIndexKeyAppAction.toUpdate failed,",e);
        }
        return "toUpdate";
    }

    // 更新分类
    public String updateKeyApp(){
        // 获取请求参数
        String id = getParameter("cid");// 获取ID
        String _key = getParameter("_key");//键位
        String  key="";
        if("1".equals(_key)){
             key="1";
        }else{
             key = getParameter("key");//键位
        }
        String supcode = getParameter("supcode");//供应商code
        String name = getParameter("name");
        String title = getParameter("title");
        String logo = getParameter("logo");
        String img = getParameter("img");
        String app = getParameter("app");
        String jar = getParameter("jar");
        String size = getParameter("size");
        String menu = getParameter("menu");
        String menuTel = getParameter("menuTel");
        String abStract = getParameter("abstract");
        String version = getParameter("version");
        String download = getParameter("download");
        String grade = getParameter("grade");
        String h5url = getParameter("h5url");
        String source = getParameter("source");
        String supurl = getParameter("supurl");
        String order = getParameter("order");
        String islive=getParameter("islive");
        log.info("into EKIndexKeyAppAction.updateKeyApp");
        log.info("id=" + id + ", name=" + name + ", logo=" + logo);
        try{
            // 更新
            String modifier = super.getSessionLoginUser().getUserName();
            Date edate = new Date();
            List<Object> argsList = new ArrayList<Object>();
            argsList.add(key);
            argsList.add(supcode);
            argsList.add(name);
            argsList.add(title);
            argsList.add(logo);
            argsList.add(img);
            argsList.add(app);
            argsList.add(jar);
            argsList.add(size);
            argsList.add(menu);
            argsList.add(menuTel);
            argsList.add(abStract);
            argsList.add(version);
            argsList.add(download);
            argsList.add(grade);
            argsList.add(h5url);
            argsList.add(source);
            argsList.add(supurl);
            argsList.add(order);
            argsList.add(modifier);
            argsList.add(islive);
            argsList.add(edate);
            argsList.add(id);
            ekIndexKeyAppService.updateKeyApp(argsList);
        }catch(Exception e){
            log.error("EKIndexKeyAppAction.updateKeyApp failed,",e);
        }
        return "reload";
    }



}

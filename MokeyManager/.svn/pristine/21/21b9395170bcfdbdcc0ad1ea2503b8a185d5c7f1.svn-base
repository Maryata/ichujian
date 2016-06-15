package com.org.mokey.basedata.tag;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.org.mokey.common.SysConstant;
import com.org.mokey.formdesign.service.FormCtrlService;

/**
 *    1.hasAll 用来控制是否添加   全部  选项
      2.isShowall 用来控制是否显示所有的数据，同时 hasall生效
      3.省份，公司，风场 要同时出现，且每一个都有一个id为0的记录，当用户持有0 id时，则该用户拥有该项所有及子项所有
        如果该用户拥有某项非0id时，则用户可以拥有级联子项的0或非0 id
      4.当typeselect时，获取用户的 省份，公司，风场，角色id
        当ishowall时，所有这些id失效
        当isshowall为n时，只显示用户对应的数据，如果用户持有的id为0，则hasall生效，如果持有的id为非0，则hasall失效
        同时所有的查询都要带入上一级持有的id
        如添加用户类,只需最原始事件源设置为isshowall即可
 * @function 系统设计到的基础类型的标签
 * id值为 0, 所有权限
 *      -2 没有权限
 *      具体id 该id的权限
 * @create 2007-9-10
 * @version 1.0
 * @logs 2007-9-10创建::
 */
public class TypeSelect implements Tag {

    //页面对象
    private PageContext pageContext;
    private Tag parentTag;
    private HttpServletRequest req;  
    private String onchang;
    
    private ServletContext sc;

    private String isRelation = "N";
    private Long keyId = -2l;
    private boolean isKeyId = false;
    private boolean isAllDisplay = false;
    private String multiple = "n";
    /*
     */
    private String type;

    /*
     * 当前所选
     */
    private String selected;

    /*
     * 表单名称
     */
    private String name;

    public String hasAll = "y";

    /**
     */
    public String plantId = "-1";

    /**
     */
    public String isShowAll = "n"; 

    public String getIsShowAll() {
        return isShowAll;
    }
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }
    public void setParent(Tag parentTag) {
        this.parentTag = parentTag;
    }
    public Tag getParent() {
        return this.parentTag;
    }

    public void setType(String type){
        this.type=type;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int doStartTag() throws JspException {
        req = (HttpServletRequest) pageContext.getRequest();
        
        sc = pageContext.getServletContext();
        //HttpSession session = req.getSession();
        WebApplicationContext wacUtil =  WebApplicationContextUtils.getWebApplicationContext(sc);
        FormCtrlService formctrlService = (FormCtrlService) wacUtil.getBean("formctrlService");
        
        List<?> list = null;
        // 此处根据传进来的类型,来取不同的对象
        try {
            //供应商
            if(type.equalsIgnoreCase(SysConstant.Signal.supplier.name())) {
            	list = formctrlService.findSeletData(SysConstant.Signal.supplier.getSql());
            } else if(type.equalsIgnoreCase(SysConstant.Signal.brand.name())) {//品牌
            	list = formctrlService.findSeletData(SysConstant.Signal.brand.getSql());
            }
            writeTag(name, list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JspException("Error in RequiredTag.doStartTag()");
        }
        return SKIP_BODY;
    }

    private void writeTag(String name, List<?> list) throws IOException {
        JspWriter out = pageContext.getOut();
        // 默认值
        if (name == null) {
            name = "type";
        }
        out.print("<select name=\"" + name + "\" ");
        out.print(" id=\"" + name + "\" ");
        if(multiple.equalsIgnoreCase("true") || multiple.equalsIgnoreCase("y")) {
            out.print(" multiple size=1 ");
        }
        if(this.getOnchang() != null) {
            out.print("onchange=\"" + this.getOnchang() + "\" ");
        }
        out.println(">");
        // 如果是被联动标签，通过本标签初始化时直接返回-2,数据油联动标签初始化
        if(isRelation.equalsIgnoreCase("true") || isRelation.equalsIgnoreCase("y")) {
            out.println("<option value=\"-2\">--------</option>");
            out.println("</select>");
            return;
        }
        // 如果查询出来的结果为空,则返回-2,证明没有权限
        /*if(list == null || list.size() == 0) {
            out.println("<option value=\"-2\">");
            out.println("-------");
            out.println("</option>");
            out.println("</select>");
            return;
        }*/
        // 如果显示全部按钮,则必须 hasall为y 而且该用户持有的该项id为0 或isshowall为y true
        if(hasAll != null && ((hasAll.equalsIgnoreCase("Y") || hasAll.equalsIgnoreCase("true")))  ) {
            if((!isKeyId || (keyId != null && keyId == 0))) {
                out.println("<option value=\"0\">全部</option>");
            }
        }
        
        String select =  getSelectObj(name);
        // 展示列表
        for (int i = 0; i < list.size(); i++) {
            Map obj = (Map) list.get(i);
            out.print("<Option value=\"");
            out.print(obj.get("ID"));
            out.print("\" ");
            if (select.equals(obj.get("ID").toString())) {
                out.print("selected");
            }
            out.println(">" + obj.get("NAME") + "</Option>");
        }
        out.println("</select>");
    }

    /**
     * 查找默认选项
     * @create 2007-9-10
     * @author
     * @param name
     * @return
     * @version 1.0
     * @logs 2007-9-10创建::
     */
    private String getSelectObj(String name) {
        if(selected!=null){
            return selected;
        }else if(req.getAttribute(name)!=null){
            return req.getAttribute(name).toString();
        }else if(req.getParameter(name)!=null){
            return req.getParameter(name);
        }else {
            return "0";
        }
    }
    public int doEndTag() throws JspException{
        return EVAL_PAGE;
    }

    public void release(){
    }
    public String getSelected() {
        return selected;
    }
    public void setSelected(String selected) {
        this.selected = selected;
    }
    public String getHasAll() {
        return hasAll;
    }
    public void setHasAll(String hasAll) {
        this.hasAll = hasAll;
    }
    /**
     * @ 获得变量onchang的方法
     * @return the onchang
     */
    public String getOnchang() {
        return onchang;
    }
    /**
     * @ 设置变量onchang 为字段 onchang的值 的方法
     */
    public void setOnchang(String onchang) {
        this.onchang = onchang;
    }
    public void setIsShowAll(String isShowAll) {
        this.isShowAll = isShowAll;
    }
    public String getPlantId() {
        return plantId;
    }
    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }
    
    /**
     * @return the isRelation
     */
    public String getIsRelation() {
        return isRelation;
    }
    
    /**
     * @param isRelation the isRelation to set
     */
    public void setIsRelation(String isRelation) {
        this.isRelation = isRelation;
    }
    
    /**
     * @return the multiple
     */
    public String getMultiple() {
        return multiple;
    }
    
    /**
     * @param multiple the multiple to set
     */
    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }
}
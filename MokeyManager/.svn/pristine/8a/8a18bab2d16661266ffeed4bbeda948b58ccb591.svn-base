package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKMallProductService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.common.util.str.UnicodeReader;
import com.org.mokey.util.StrUtils;
import net.sf.json.JSONObject;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品详情维护
 * Created by vpc on 2016/4/25.
 */
@Controller("eKMallProductAction")
public class EKMallProductAction extends AbstractAction {
    @Autowired
    private EKMallProductService eKMallProductService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 跳转到商品列表
     *
     * @return
     */
    public String toList() {
        return "toList";
    }

    /**
     * 查询商品列表信息
     *
     * @return
     */
    public String list() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        String title = getParameter("title");
        String type = getParameter("type");
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into eKMallProductAction.list");
        log.info("page=" + page);
        try {
            retmap = eKMallProductService.list(page, title, type);
            retmap.put("status", "Y");
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("eKMallProductAction.list failed, e : " + e);
        }
        return NONE;
    }

    /**
     * 跳转到添加页面
     *
     * @return
     */
    public String toAdd() {
        log.info("into eKMallProductAction.toAdd");
        try {
            // 获取主键
            String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_MALL_PRODUCT");
            getRequest().setAttribute("addFlag", "1");// “新增”操作的标记
            getRequest().setAttribute("cid", newId);
        } catch (Exception e) {
            log.error("eKMallProductAction.toAdd failed,", e);
        }
        return "toUpdate";
    }

    /**
     * 添加商品信息
     *
     * @return
     */
    public String addProduct() {
        String id = getParameter("cid");// 获取cid
        String title = getParameter("title");
        String subTitle = getParameter("subTitle");
        String function = getParameter("function");
        String _abstract = getParameter("_abstract");
        String cost = getParameter("cost");
        String total = getParameter("total");
        String type = getParameter("type");
        String carriage = getParameter("carriage");
        String logo = getParameter("logo");
        String amount = getParameter("amount");
        String islive = getParameter("islive");
        String _detail = getParameter("detail");
        String _illustr = getParameter("illustr");

        log.info("into eKMallProductAction.addProduct");
        log.info("id=" + id + ", title=" + title + ", logo=" + logo);
        try { // 获取当前登录人
            String modifier = super.getSessionLoginUser().getUserName();//发布人

            eKMallProductService.addProduct(id, title, subTitle, function, _abstract, cost, total, type, carriage, logo, amount, islive, _detail, _illustr, modifier);
        } catch (Exception e) {
            log.error("eKMallProductAction.addProduct failed,", e);
        }
        return "reload";
    }

    /**
     * 删除商品信息
     *
     * @return
     */
    public String toDel() {
        // 获取cid
        String id = getParameter("cid");
        log.info("into eKMallProductAction.toDel");
        log.info("id=" + id);
        try {
            eKMallProductService.toDel(id);
        } catch (Exception e) {
            log.error("eKMallProductAction.toDel failed,", e);
        }
        return NONE;
    }

    /**
     * 跳转修改页面
     *
     * @return
     */
    public String toUpdate() {
        // 获取请求参数
        String id = getParameter("editId");
        log.info("into eKMallProductAction.toUpdate");
        log.info("id=" + id);
        try {
            //查询一个商品
            List<Map<String, Object>> productMap = eKMallProductService.selectOne(id);
            Map<String, Object> objMap = productMap.get(0);
            String title = StrUtils.emptyOrString(objMap.get("C_TITLE"));
            String subTitle = StrUtils.emptyOrString(objMap.get("C_SUBTITLE"));
            String function = StrUtils.emptyOrString(objMap.get("C_FUNCTION"));
            String _abstract = StrUtils.emptyOrString(objMap.get("C_FUNCTION"));
            String cost = StrUtils.emptyOrString(objMap.get("C_COST"));
            String total = StrUtils.emptyOrString(objMap.get("C_TOTAL"));
            String type = StrUtils.emptyOrString(objMap.get("C_TYPE"));
            String carriage = StrUtils.emptyOrString(objMap.get("C_CARRIAGE"));
            String logo = StrUtils.emptyOrString(objMap.get("C_IMG"));
            String amount = StrUtils.emptyOrString(objMap.get("C_AMOUNT"));
            String islive = StrUtils.emptyOrString(objMap.get("C_ISLIVE"));
            String _detail = StrUtils.emptyOrString(objMap.get("C_DETAIL"));
            String _illustr = StrUtils.emptyOrString(objMap.get("C_ILLUSTR"));
            getRequest().setAttribute("cid", id);
            getRequest().setAttribute("title", title);
            getRequest().setAttribute("subTitle", subTitle);
            getRequest().setAttribute("function", function);
            getRequest().setAttribute("_abstract", _abstract);
            getRequest().setAttribute("cost", cost);
            getRequest().setAttribute("total", total);
            getRequest().setAttribute("type", type);
            getRequest().setAttribute("carriage", carriage);
            getRequest().setAttribute("logo", logo);
            getRequest().setAttribute("cid", id);
            getRequest().setAttribute("amount", amount);
            getRequest().setAttribute("logo", logo);
            getRequest().setAttribute("islive", islive);
            getRequest().setAttribute("detail", _detail);
            getRequest().setAttribute("illustr", _illustr);

        } catch (Exception e) {
            log.error("eKMallProductAction.toUpdate failed,", e);
        }
        return "toUpdate";
    }

    /**
     * 更新 商品
     *
     * @return
     */
    public String updateProduct() {
        String id = getParameter("cid");// 获取cid
        String title = getParameter("title");
        String subTitle = getParameter("subTitle");
        String function = getParameter("function");
        String _abstract = getParameter("_abstract");
        String cost = getParameter("cost");
        String total = getParameter("total");
        String type = getParameter("type");
        String carriage = getParameter("carriage");
        String logo = getParameter("logo");
        String amount = getParameter("amount");
        String islive = getParameter("islive");
        String _detail = getParameter("detail");
        String _illustr = getParameter("illustr");

        log.info("into eKMallProductAction.updateProduct");
        log.info("id=" + id + ", name=" + title + ", logo=" + logo);
        try {
            String modifier = super.getSessionLoginUser().getUserName();//发布人
            eKMallProductService.updateProduct(id, title, subTitle, function, _abstract, cost, total, type, carriage, logo, amount, islive, _detail, _illustr, modifier);
        } catch (Exception e) {
            log.error("EKMallAdvertAction.updateProduct failed,", e);
        }
        return "reload";
    }

    /**
     * 跳转到商品吗列表
     *
     * @return
     */
    public String toCodesList() {
        String pid = getParameter("editId");
        log.info("into eKMallProductAction.toCodesList");
        log.info("pid = " + pid);
        try {
            getRequest().setAttribute("pid", pid);
        } catch (Exception e) {
            log.error("eKMallProductAction.toCodesList failed,", e);
        }
        return "toCodesList";
    }

    /**
     * 查询商品吗列表
     *
     * @return
     */
    public String getCodeList() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        String type = getParameter("type");
        String pid = getParameter("pid");
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into eKMallProductAction.getCodeList");
        log.info("page=" + page);
        try {
            retmap = eKMallProductService.getCodeList(page, pid, type);
            retmap.put("status", "Y");
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("eKMallProductAction.getCodeList failed, e : " + e);
        }
        return NONE;
    }

    /**
     * 上传商品码
     *
     * @return
     */
    public String upload() {
        String id = getParameter("id");// 礼包id
        Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) getRequest();
            File[] files = multiWrapper.getFiles("codeScanField");
            List<String> list = new ArrayList<String>();
            try {
                FileInputStream in = new FileInputStream(files[0]);
                BufferedReader br = new BufferedReader(new UnicodeReader(in, Charset.defaultCharset().name()));
                String line = null;
                try {
                    while ((line = br.readLine()) != null & line.length()!=0) {
                        list.add(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    retMap.put("status", "I/O流读写出错！");
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        log.error("eKMallProductAction.upload failed, e : " + e);
                        retMap.put("status", "关闭I/O流出错！");
                    }
                }
            } catch (Exception e) {
                log.error("eKMallProductAction.upload failed, e : " + e);
                retMap.put("status", "I/O流读写出错！");
            }
            eKMallProductService.upload(list, id);
        } catch (Exception e) {
            log.error("eKMallProductAction.upload failed, e : " + e);
            retMap.put("status", "上传失败！");
        } finally {
            try {
                writeToResponse(JSONObject.fromObject(retMap).toString());
            } catch (IOException e) {
                log.error("eKMallProductAction.upload failed, e : " + e);
            }
        }
        return NONE;
    }


}

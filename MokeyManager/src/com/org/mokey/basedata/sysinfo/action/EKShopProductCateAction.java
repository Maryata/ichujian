package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.EKShopProductCateService;
import com.org.mokey.common.AbstractAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vpc on 2016/3/14.
 * e键 ： 商品分类管理
 */
@Controller("eKShopProductCateAction")
public class EKShopProductCateAction extends AbstractAction {

   @Autowired
    private EKShopProductCateService eKShopProductCateService;

    private String out;

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    // 跳转到商品分类首页
    public String toList() {
        return "toList";
    }

    // 首页显示的列表信息
    public String productCateList() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        log.info("into EKShopProductCateAction.productCateList");
        log.info("page=" + page);
        try {
            // 分页显示商品分类
            retmap = eKShopProductCateService.productCateList(page);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKShopProductCateAction.productCateList failed, e : " + e);
        }
        return NONE;
    }

    // 跳转到分类修改页面
    public String toHandle() {
        // 获取请求参数
        String id = getParameter("editId");
        String name = getParameter("editName");
        log.info("into EKShopProductCateAction.AddShopProductCate");
        log.info("id=" + id + ", name=" + name);
        try {
            getRequest().setAttribute("cid", id);// 分类id
            getRequest().setAttribute("cname", name);
        } catch (Exception e) {
            log.error("EKShopProductCateAction.toUpdate failed,", e);
        }
        return "toHandle";
    }

    // 查询非当前商品分类的其他所有游戏
    public String getAllProduct() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        String cid = getParameter("cid");// 当前分类id
        String name = getParameter("name");// 游戏名称
        log.info("into EKShopProductCateAction.getAllProduct");
        log.info("page=" + page + ", cid=" + cid + ", name=" + name);
        try {
            // 分页查询
            retmap = eKShopProductCateService.getAllProduct(page, cid, name);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKShopProductCateAction.getAllProduct failed, e : " + e);
        }
        return NONE;
    }

    // 查询当前商品分类的游戏
    public String getCurrCateProduct() {
        Map<String, Object> retmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 获取page
        String cid = getParameter("cid");// 当前分类id
        String name = getParameter("name");// 游戏名称
        log.info("into EKShopProductCateAction.getCurrCateProduct");
        log.info("page=" + page + ", cid=" + cid + ", name=" + name);
        try {
            // 分页查询
            retmap = eKShopProductCateService.getCurrCateProduct(page, cid, name);
            retmap.put("status", "Y");
            // 回写查询结果
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            retmap.put("status", "N");
            log.error("EKShopProductCateAction.getCurrCateProduct failed, e : " + e);
        }
        return NONE;
    }

    // 分类维护
    public String handleCate() {
        // 获取请求参数
        String cid = getParameter("cid");// 获取分类id
        String pid = getRequest().getParameter("pid");// 获取应用id
        String removePid = getRequest().getParameter("removePid");// 获取要移除出当前分类的应用id
        String order = getRequest().getParameter("order");// 获取顺序
        log.info("into EKShopProductCateAction.handleStrategyCate");
        log.info("cid = " + cid + ", pid = " + pid + ", removePid = " + removePid + ", order = " + order);
        try {
            // 维护
            eKShopProductCateService.handleCate(cid, pid, removePid, order);
        } catch (Exception e) {
            log.error("EKShopProductCateAction.handleCate failed,", e);
        }
        return NONE;
    }

}

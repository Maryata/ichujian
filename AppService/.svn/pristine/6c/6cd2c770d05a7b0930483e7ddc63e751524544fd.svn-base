package com.sys.ekey.tbk.action;

import com.sys.commons.AbstractAction;
import com.sys.ekey.tbk.service.TbkService;
import com.sys.util.ApDateTime;
import com.sys.util.StrUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/8/15.
 */
@Component("tbkAction")
public class TbkAction extends AbstractAction {
    @Autowired
    private TbkService tbkService;

    /**
     * 输出内容
     */
    private String out;

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    /**
     * 广告位
     *
     * @return
     */
    public String advert() {
        log.info("into TbkAction advert ...");
        Map<String, Object> retMap = new HashMap<String, Object>();
        String type = "1";//顶部广告位
        log.info("type -->" + type);
        List<Map<String, Object>> advert = tbkService.advert(type);
        retMap.put("advert", advert);
        retMap.put("status", "Y");
        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception ex) {
            retMap.put("status", "N");
            retMap.put("info", "1003," + ex.getMessage());
            log.error("广告位数据写出错误！", ex);
        }
        return NONE;
    }

    /**
     * 分类列表
     *
     * @return
     */
    public String cateList() {
        log.info("into TbkAction cateList ...");
        Map<String, Object> retMap = new HashMap<String, Object>();
        List<Map<String, Object>> cateList = tbkService.cateList();
        retMap.put("cateList", cateList);
        retMap.put("status", "Y");
        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception ex) {
            retMap.put("status", "N");
            retMap.put("info", "1003," + ex.getMessage());
            log.error("分类列表数据写出错误！", ex);
        }
        return NONE;
    }

    /**
     * 主题商品列表
     *
     * @return
     */
    public String theme() {
        log.info("into TbkAction theme ...");
        Map<String, Object> retMap = new HashMap<String, Object>();
        String sPageNumber = getParameter("pageNumber");
        int pageNumber = 0; // 默认第一页
        if (!StringUtils.isEmpty(sPageNumber)) {
            pageNumber = Integer.parseInt(sPageNumber);
        }
        String day = ApDateTime.dateAdd("d", -pageNumber, ApDateTime.getNowDateTime(""), ApDateTime.DATE_TIME_YMD);
        List<Map<String, Object>> themes = tbkService.theme(day);
        List<Map<String, Object>> products = tbkService.product(day);
        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar c = Calendar.getInstance();
        c.get(Calendar.DAY_OF_WEEK);
        int week_index = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        retMap.put("week", weeks[week_index]);
        retMap.put("date", day);
        retMap.put("themeList", themes);
        retMap.put("productList", products);
        retMap.put("status", "Y");
        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception ex) {
            retMap.put("status", "N");
            retMap.put("info", "1003," + ex.getMessage());
            log.error("主题商品列表数据写出错误！", ex);
        }
        return NONE;
    }

    /**
     * 主题详情
     *
     * @return
     */
    public String themeDetail() {
        log.info("into TbkAction themeDetail ...");
        String aid = getParameter("aid");
        String uid = getParameter("uid");
        Map<String, Object> retMap = new HashMap<String, Object>();
        log.info("aid -->" + aid);
        List<Map<String, Object>> themeDetail = tbkService.themeDetail(aid);
        List<Map<String, Object>> themeProductDetail = tbkService.themeProductDetail(aid);
        List<Map<String, Object>> collectList = tbkService.collectList(aid, uid);
        String collect="1";
        if(StrUtils.isEmpty(collectList)){
            collect="0";
        }
        retMap.put("collect", collect);
        retMap.put("themeDetail", themeDetail);
        retMap.put("themeProductDetail", themeProductDetail);
        retMap.put("status", "Y");
        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception ex) {
            retMap.put("status", "N");
            retMap.put("info", "1003," + ex.getMessage());
            log.error("主题详情数据写出错误！", ex);
        }
        return NONE;

    }

    /**
     * 商品列表
     *
     * @return
     */
    public String products() {
        log.info("into TbkAction productList ...");
        String aid = getParameter("aid");
        Map<String, Object> retMap = new HashMap<String, Object>();
        log.info("aid -->" + aid);
        List<Map<String, Object>> products = tbkService.productList(aid);
        retMap.put("products", products);
        retMap.put("status", "Y");
        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception ex) {
            retMap.put("status", "N");
            retMap.put("info", "1003," + ex.getMessage());
            log.error("商品列表数据写出错误！", ex);
        }
        return NONE;
    }

    /**
     * 添加收藏信息
     *
     * @return
     */
    public String collect() {
        log.info("into TbkAction collect ...");
        String uid = getParameter("uid");
        String aid = getParameter("aid");
        Map<String, Object> retMap = new HashMap<String, Object>();
        log.info("aid -->" + aid + "uid-->" + uid);
        List<Map<String, Object>> collectList = tbkService.collectList(aid, uid);
        if (StrUtils.isEmpty(collectList)) {
            tbkService.collect(aid, uid);
        }
        retMap.put("status", "Y");
        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception ex) {
            retMap.put("status", "N");
            retMap.put("info", "1003," + ex.getMessage());
            log.error("添加收 藏信息数据写出错误！", ex);
        }
        return NONE;
    }

    /**
     * 收藏列表
     *
     * @return
     */
    public String collectList() {
        log.info("into TbkAction collectList ...");
        String uid = getParameter("uid");
        Map<String, Object> retMap = new HashMap<String, Object>();
        log.info("uid -->" + uid );
        List<Map<String, Object>> collects = tbkService.collects(uid);
        retMap.put("collects", collects);
        retMap.put("status", "Y");
        try {
            writeToResponse(JSONObject.fromObject(retMap).toString());
        } catch (Exception ex) {
            retMap.put("status", "N");
            retMap.put("info", "1003," + ex.getMessage());
            log.error("收藏列表信息数据写出错误！", ex);
        }
        return NONE;
    }

}
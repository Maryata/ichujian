package com.org.mokey.basedata.sysinfo.action;

import com.org.mokey.basedata.sysinfo.service.PhoneModelService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 手机型号管理
 * <p/>
 * Created by Maryn on 2016/4/26.
 */
@Controller
public class PhoneModelAction extends AbstractAction {

    @Autowired
    private PhoneModelService phoneModelService;

    // 跳转到列表页
    public String toList() {
        return "toBrandList";
    }

    // 分页查询手机品牌
    public String phoneList() {
        Map<String, Object> reqMap = new HashMap<>();
        int page = getParameter2Int("page", 1);// 获取page
        int rows = getParameter2Int("rows", 10);// 每页数量
        String brandName = getParameter("brandName");// 获取品牌名称
        String islive = getParameter("islive");// 状态： 0.下架,1.上架
        log.info("into EKGameGiftAction.phoneList...");
        log.info("page = " + page + ", rows = " + rows + ", brandName = " + brandName + ", islive = " + islive);
        try {
            reqMap = phoneModelService.phoneList(page, rows, brandName, islive);
        } catch (Exception e) {
            log.error("PhoneModelAction.phoneList failed, e : " + e);
        }
        try {
            super.writeJSONToResponse(reqMap);
        } catch (IOException e) {
            log.error("PhoneModelAction.phoneList >>> writeJSONToResponse failed, e : " + e);
        }
        return NONE;
    }

    // 跳转到新增手机品牌页面
    public String toBrandAdd() {
        return "toBrandAdd";
    }

    // 新增手机品牌
    public String addBrand() {
        String name = getParameter("name");// 获取品牌名称
        String userName = getSessionLoginUser().getUserName();// 操作人
        log.info("into EKGameGiftAction.addBrand...");
        log.info("name = " + name + ", userName = " + userName);
        try {
            phoneModelService.addBrand(name, userName);
        } catch (Exception e) {
            log.error("PhoneModelAction.addBrand failed, e : " + e);
        }
        return "toBrandList";
    }

    // 跳转到编辑手机品牌页面
    public String toBrandEdit() {
        getRequest().setAttribute("id", getParameter("editId"));
        getRequest().setAttribute("name", getParameter("editName"));
        return "toBrandAdd";
    }

    // 编辑手机品牌
    public String editBrand() {
        String id = getParameter("id");// 获取品牌id
        String name = getParameter("name");// 获取品牌名称
        String userName = getSessionLoginUser().getUserName();// 操作人
        log.info("into EKGameGiftAction.editBrand...");
        log.info("id = " + id + ", name = " + name + ", userName = " + userName);
        try {
            phoneModelService.editBrand(id, name, userName);
        } catch (Exception e) {
            log.error("PhoneModelAction.editBrand failed, e : " + e);
        }
        return "toBrandList";
    }

    // 手机品牌上下架
    public String onShelf() {
        String id = getParameter("id");// 品牌id
        String act = getParameter("act");// 动作: 0.下架，1：上架
        String type = getParameter("type");// 类型: 1.品牌，2：型号
        String userName = getSessionLoginUser().getUserName();// 操作人
        log.info("into EKGameGiftAction.onShelf...");
        log.info("id = " + id + ", act = " + act + ", type = " + type + ", userName = " + userName);
        try {
            phoneModelService.onShelf(id, act, type, userName);
        } catch (Exception e) {
            log.error("PhoneModelAction.onShelf failed, e : " + e);
        }
        return "toBrandList";
    }

    // 跳转到型号列表页
    public String toModelList() {
        String id = getParameter("editId");// 品牌id
        String editName = getParameter("editName");// 品牌名称
        log.info("into EKGameGiftAction.onShelf...");
        log.info("id = " + id + ", editName = " + editName);
        try {
            getRequest().setAttribute("brandId", id);
            getRequest().setAttribute("brandName", editName);
        } catch (Exception e) {
            log.error("PhoneModelAction.toModelList failed, e : " + e);
        }
        return "toModelList";
    }

    // 分页查询品牌下的型号
    public String modelList() {
        Map<String, Object> reqMap = new HashMap<>();
        int page = getParameter2Int("page", 1);// 获取page
        int rows = getParameter2Int("rows", 10);// 每页数量
        String brandId = getParameter("brandId");// 获取品牌id
        String phoneName = getParameter("phoneName");// 获取型号名称
        String islive = getParameter("islive");// 上/下架状态
        log.info("into EKGameGiftAction.modelList...");
        log.info("page = " + page + ", rows = " + rows + ", brandId = " + brandId + ", phoneName = " + phoneName + ", islive = " + islive);
        try {
            reqMap = phoneModelService.modelList(page, rows, brandId, phoneName, islive);
        } catch (Exception e) {
            log.error("PhoneModelAction.modelList failed, e : " + e);
        }
        try {
            super.writeJSONToResponse(reqMap);
        } catch (IOException e) {
            log.error("PhoneModelAction.modelList >>> writeJSONToResponse failed, e : " + e);
        }
        return NONE;
    }


    // 跳转到新增手机品牌页面
    public String toModelAdd() {
        getRequest().setAttribute("brandId", getParameter("brandId"));
        return "toModelAdd";
    }


    // 跳转到编辑手机品牌页面
    public String toModelEdit() {
        getRequest().setAttribute("id", getParameter("editId"));
        getRequest().setAttribute("name", getParameter("editName"));
        getRequest().setAttribute("code", getParameter("editCode"));
        getRequest().setAttribute("brandId", getParameter("brandId"));
        return "toModelAdd";
    }

    // 新增手机品牌
    public String addModel() {
        String name = getParameter("name");// 获取型号名称
        String code = getParameter("code");// 获取型号代码
        String brandId = getParameter("brandId");// 获取品牌id
        String userName = getSessionLoginUser().getUserName();// 操作人
        log.info("into EKGameGiftAction.addModel...");
        log.info("name = " + name + ", code = " + code + ", brandId = " + brandId + ", userName = " + userName);
        try {
            phoneModelService.addModel(name, code, brandId, userName);
            getRequest().setAttribute("brandId", brandId);
        } catch (Exception e) {
            log.error("PhoneModelAction.addModel failed, e : " + e);
        }
        return "toModelList";
    }

    // 编辑手机品牌
    public String editModel() {
        String name = getParameter("name");// 获取型号名称
        String code = getParameter("code");// 获取型号代码
        String id = getParameter("id");// 获取型号id
        String brandId = getParameter("brandId");// 获取品牌id
        String userName = getSessionLoginUser().getUserName();// 操作人
        log.info("into EKGameGiftAction.editModel...");
        log.info("name = " + name + ", code = " + code + ", id = " + id + ", brandId = " + brandId + ", userName = " + userName);
        try {
            phoneModelService.editModel(name, code, brandId, userName, id);
            getRequest().setAttribute("brandId", brandId);
        } catch (Exception e) {
            log.error("PhoneModelAction.editModel failed, e : " + e);
        }
        return "toModelList";
    }
}

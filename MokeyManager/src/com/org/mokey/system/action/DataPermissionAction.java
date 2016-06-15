package com.org.mokey.system.action;


import com.org.mokey.common.AbstractAction;
import com.org.mokey.system.service.DataPermissionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/1/11.
 */
public class DataPermissionAction extends AbstractAction {

    private DataPermissionService dataPermissionService;

    private String out = "";

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public DataPermissionService getDataPermissionService() {
        return dataPermissionService;
    }

    public void setDataPermissionService(DataPermissionService dataPermissionService) {
        this.dataPermissionService = dataPermissionService;
    }

    // 跳转到供应商管理页面
    public String toDataPermission() {
        log.info("into DataPermissionAction.toDataPermission");
        try{
            // 查询所有后台供应商用户
            List<Map<String, Object>> suppliers = dataPermissionService.selectSuppliers();
            // 存入值栈
            getValueStack().set("suppliers", suppliers);
        }catch(Exception e){
            log.error("DataPermissionAction.toDataPermission failed,",e);
        }
        return "toDataPermission";
    }

    // 跳转到供应商维护页面
    public String toHandle() {
        String editId = getParameter("editId");
        String editCode = getParameter("editCode");
        String editName = getParameter("editName");
        log.info("into DataPermissionAction.toHandle");
        log.info("editId = " + editId + ", editCode = " + editCode + ", editName = " + editName);
        try{
            getRequest().setAttribute("editId", editId);
            getRequest().setAttribute("editCode", editCode);
            getRequest().setAttribute("editName", editName);
        }catch(Exception e){
            log.error("DataPermissionAction.toHandle failed,",e);
        }
        return "toHandle";
    }

    // 查询非当前用户可以看到的供应商
    public String getAllSuppliers(){
        Map<String, Object> reqmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 分页页码
        String uid = getParameter("uid");// 用户id
        String supCode = getParameter("supCode");// 供应商code
        String supName = getParameter("supName");// 供应商名称
        log.info("into DataPermissionAction.getAllSuppliers");
        log.info("page = " + page + ", supCode = " + supCode + ", supName = " + supName);
        try{
            // 查询
            reqmap = dataPermissionService.getAllSuppliers(uid, page, supCode, supName);
            reqmap.put("status", "Y");
            // 回写
            this.writeJSONToResponse(reqmap);
        }catch(Exception e){
            reqmap.put("status", "N");
            log.error("DataPermissionAction.getAllSuppliers failed, e : ", e);
        }
        return NONE;
    }

    // 查询当前用户可见的供应商
    public String getCurrSup(){
        Map<String, Object> reqmap = new HashMap<String, Object>();
        int page = getParameter2Int("page", 1);// 分页页码
        String uid = getParameter("uid");// 用户id
        String supCode = getParameter("supCode");// 供应商code
        String supName = getParameter("supName");// 供应商名称
        log.info("into DataPermissionAction.getCurrSup");
        log.info("page = " + page + ", supCode = " + supCode + ", supName = " + supName);
        try{
            // 查询
            reqmap = dataPermissionService.getCurrSup(uid, page, supCode, supName);
            reqmap.put("status", "Y");
            // 回写
            this.writeJSONToResponse(reqmap);
        }catch(Exception e){
            reqmap.put("status", "N");
            log.error("DataPermissionAction.getCurrSup failed, e : ", e);
        }
        return NONE;
    }

    // 维护当前用户可见供应商
    public String handle(){
        // 获取用户id
        String userId = getParameter("userId");
        // 获取供应商id
        String supId = getRequest().getParameter("supId");
        String[] supIds = supId.split(",");// 切割
        // 获取供应商code
        String supCode = getRequest().getParameter("supCode");
        String[] supCodes = supCode.split(",");// 切割
        // 获取要移除的供应商id
        String removeSupId = getRequest().getParameter("removeSupId");
        String[] removeSupIds = removeSupId.split(",");// 切割
        log.info("into DataPermissionAction.handle");
        log.info("userId = " + userId);
        try{
            // 维护
            dataPermissionService.handle(userId, supIds, supCodes, removeSupIds);
        }catch(Exception e){
            log.error("DataPermissionAction.handle failed,",e);
        }
        return NONE;
    }
}

package com.sys.action;

import com.sys.commons.AbstractAction;
import com.sys.service.AppService;
import com.sys.util.Base64;
import com.sys.util.StrUtils;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class AppAction extends AbstractAction {

    private AppService appService;

    /**
     * 输出内容
     */
    private String out;

    public String AppStart() {  //首次启动
        Map<String, Object> retMap = new HashMap<String, Object>();
        String system = getParameter("system");  //系统型号
        String imei = getParameter("imei");  //IMEI
        String model = getParameter("model");  //设备型号
        String brand = getParameter("brand");  //品牌
        String size = getParameter("size");  //屏幕尺寸
        String nettype = getParameter("nettype");  //网络类型
        String longitude = getParameter("longitude"); //经度
        String latitude = getParameter("latitude");   //纬度
        String source = getParameter("source");                    //新增来源--
        String version = getParameter("version");                  //新增来源--
        log.info("into AppStart");
        log.info("--system--" + system + "--imei--:" + imei + "--model--" + model + "--brand--" + brand + "--size--" + size + "--nettype--" + nettype + "--longitude--" + longitude + "--latitude--" + latitude + "--source--" + source + "--version--" + version);

        try {
            if (StrUtils.isEmpty(system) || StrUtils.isEmpty(imei) || StrUtils.isEmpty(model) || StrUtils.isEmpty(size)
                    || StrUtils.isEmpty(nettype) || StrUtils.isEmpty(longitude) || StrUtils.isEmpty(latitude) || StrUtils.isEmpty(source) || StrUtils.isEmpty(version)) {
                retMap.put("status", "N");
                retMap.put("info", "1001");
            } else {
                Date actiondate1 = new Date();
                appService.AppStart(imei, source, version, actiondate1);  //首次使用表       //更改        客户端首次启动只请求一次

                List listD = appService.FindDevice(imei);        //查询是否有设备记录
                if (listD.size() < 1) {                            //没有设备信息
                    appService.Device(imei, brand, model, size, system, nettype, longitude, latitude, actiondate1);   //设备 更改
                    appService.Device_Version(imei, version, actiondate1); //设备版本对照表
                }
                List list = appService.FindCodeByImei(imei);     //查询激活码  本地清除后 下次后台返回激活码   //更改
                if (list.size() > 0) {
                    Map m = (Map) list.get(0);
                    String activecode = m.get("C_ACTIVECODE").toString();
                    retMap.put("activecode", activecode);
                } else {
                    retMap.put("activecode", "");
                }

                retMap.put("status", "Y");
                retMap.put("info", "1002");
            }
        } catch (Exception e) {
            retMap.put("status", "N");
            retMap.put("info", "1003" + e.getMessage());
            log.error("AppAction.AppStart failed", e);
        }
        out = JSONObject.fromObject(retMap).toString();
        return "success";
    }


    public String AppActive() {  //激活
        Map<String, Object> retMap = new HashMap<String, Object>();
        String activecode = getParameter("activecode");    //激活码
        String jobnumber = getParameter("jobnumber");  //工号
        String imei = getParameter("imei");  //IMEI
        String code_imei = getParameter("code_imei");     //----新增认证码
        String uid = getParameter("uid");// 2016-8-17 增加参数uid，用于扫码后给该用户增加永久时长（每月215分钟，共送6个月，总计1290分钟）
        String fyAccId = getParameter("fyAccId");// 飞语账号id
        log.info("into AppActive");
        log.info("activecode = " + activecode + ", jobnumber = " + jobnumber + ", imei = "
                + imei + ", code_imei = " + code_imei + ", uid = " + uid + ", fyAccId = " + fyAccId);
        try {
            if (StrUtils.isEmpty(activecode) || StrUtils.isEmpty(imei)) {
                retMap.put("status", "N");
                retMap.put("info", "1001");
            } else {
                //查询激活码是否有效
                List lists = appService.FindProductSerial(activecode);   //更改
                if (lists.size() < 1) {
                    retMap.put("status", "N");
                    retMap.put("info", "1004");
                    out = JSONObject.fromObject(retMap).toString();
                    return "success";
                }
                if ("".equals(jobnumber) || null == jobnumber) {   //工号为空
                    List listcode = appService.FindCode(activecode);    //查询激活码是否激活过    //更改
                    if (listcode.size() > 0) {         //已经激活过
                        Map map = (Map) listcode.get(0);
                        if (imei.equals(map.get("C_IMEI").toString())) {     //同一个IMEI
                            appService.UpdateActiveCount(imei);  //激活数加1  增加active code   //更改
                            retMap.put("status", "Y");
                        } else {
                            retMap.put("status", "N");
                            retMap.put("info", "1005");
                        }
                    } else {
                        Date actiondate1 = new Date();
                        appService.AppActive(activecode, jobnumber, imei, actiondate1, code_imei); //激活
                        appService.UpdateActivecodeStatus(activecode);    //修改激活码状态
                        /** 2016-8-17  给扫码用户增加时长 begin*/
                        appService.addPerpetualTimeByFyAccId(fyAccId, uid);
                        /** 2016-8-17  给扫码用户增加时长 end*/
                        retMap.put("status", "Y");
                    }
                } else {   //工号不为空
                    List listjobnumber = appService.FindJobNumber(jobnumber);    //查询工号是否有效
                    if (listjobnumber.size() < 1) {
                        retMap.put("status", "N");
                        retMap.put("info", "1006");
                        out = JSONObject.fromObject(retMap).toString();
                        return "success";
                    }
                    //工号不为空并且有效
                    List listcode = appService.FindCode(activecode);    //查询激活码是否激活过    //更改
                    if (listcode.size() > 0) {         //已经激活过
                        Map map = (Map) listcode.get(0);
                        if (imei.equals(map.get("C_IMEI").toString())) {     //同一个IMEI
                            appService.UpdateActiveCount(imei);  //激活数加1  增加active code   //更改
                            retMap.put("status", "Y");
                        } else {
                            retMap.put("status", "N");
                            retMap.put("info", "1005");
                        }
                    } else {            //没有激活过
                        Date actiondate1 = new Date();
                        appService.AppActive(activecode, jobnumber, imei, actiondate1, code_imei);   //激活
                        appService.UpdateActivecodeStatus(activecode);    //修改激活码状态
                        /** 2016-8-17  给扫码用户增加时长 begin*/
                        appService.addPerpetualTimeByFyAccId(fyAccId, uid);
                        /** 2016-8-17  给扫码用户增加时长 end*/
                        Map mapactive = (Map) lists.get(0);
                        String activeID = mapactive.get("C_ID").toString();     //获取激活ID
                        Map mapjob = (Map) listjobnumber.get(0);
                        String jobnumberID = mapjob.get("C_ID").toString();//获取工号ID
                        appService.ActiveJob(activeID, jobnumberID);   //工号---激活码对照表
                        retMap.put("status", "Y");
                    }
                }
            }
        } catch (Exception e) {
            retMap.put("status", "N");
            retMap.put("info", "1003" + e.getMessage());
            log.error("AppAction.AppActive failed", e);
        }
        out = JSONObject.fromObject(retMap).toString();
        return "success";
    }

    public String Update() {    //更新
        Map<String, Object> retMap = new HashMap<String, Object>();
        String version = getParameter("version");
        String source = getParameter("source");

        boolean flag = false;
        StringBuffer _url = null;

        String activecode = getParameter("activecode");
        log.info("activecode : " + activecode);
        if (null != activecode && !activecode.isEmpty()) {
            String code = "";
            if (activecode.length() >= 12) {
                code = activecode.substring(5, 11);
            } else {
                code = activecode;
            }
            int islive = appService.islive(code);
            if (islive == 1) {
                _url = new StringBuffer("http://www.ichujian.com/app/");
                _url.append(code).append("/").append("ichujian_android.apk");
                log.info("url : " + _url);
                flag = true;
            }
        }


        log.info("into Update");
        log.info("--version--" + version);
        try {
            if (StrUtils.isEmpty(version)) {
                retMap.put("status", "N");
                retMap.put("info", "1001");
            } else {
                log.info("into select");
                List list = new ArrayList();
                if ("".equals(source) || null == source) {
                    list = appService.Update(version);
                } else {
                    list = appService.Update(version, source);
                }
                log.info("into select ok");
                if (list.size() > 0) {
                    Map m = (Map) list.get(0);
                    retMap.put("status", "Y");
                    retMap.put("yesorno", "Y");
                    retMap.put("version", m.get("C_VERSION").toString());
                    retMap.put("md5", m.get("C_MD5").toString());
                    retMap.put("description", m.get("C_DESCRIPTION").toString());
                    if (flag) {
                        retMap.put("url", _url.toString());
                    } else {
                        retMap.put("url", m.get("C_URL").toString());
                    }
                    retMap.put("size", m.get("C_SIZE").toString());
                    retMap.put("isforce", m.get("C_ISFORCE").toString());
                } else {
                    retMap.put("status", "Y");
                    retMap.put("yesorno", "N");
                }
            }
        } catch (Exception e) {
            retMap.put("status", "N");
            retMap.put("info", "1003" + e.getMessage());
            log.error("AppAction.Update failed,", e);
        }
        out = JSONObject.fromObject(retMap).toString();
        log.info("return data");
        return "success";
    }


    public String Ins() {
        try {
            String encoding = "gbk";
            File file = new File("D:\\d.txt");
            //Date actiondate1=new Date();
            Date ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-10-16 17:18:88");
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = "";
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    System.out.println(lineTxt);
                    appService.inscode(lineTxt, ss);
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return "success";
    }


    public String LogInfo() {
        Map<String, Object> retMap = new HashMap<String, Object>();
        String imei = getParameter("imei");
        String model = getParameter("model");
        String system = getParameter("system");
        String loginfo = getParameter("log");
        log.info("into logInfo");
        log.info("--imei--" + imei + "--model--" + model + "--system--" + system + "--loginfo--" + loginfo);
        try {
            if (StrUtils.isEmpty(imei) || StrUtils.isEmpty(model) || StrUtils.isEmpty(system) || StrUtils.isEmpty(loginfo)) {
                retMap.put("status", "N");
                retMap.put("info", "1001");
                out = JSONObject.fromObject(retMap).toString();
                return "success";
            }
            Date actiondate1 = new Date();
            String logs = "";
            if (loginfo.length() > 4000) {
                logs = loginfo.substring(0, 3999);
            } else {
                logs = loginfo;
            }
            appService.AppLog(imei, model, system, logs, actiondate1);
            retMap.put("status", "Y");
        } catch (Exception e) {
            retMap.put("status", "N");
            retMap.put("info", "系统错误" + e.getMessage());
            log.error("AppAction.LogInfo failed,", e);
        }
        out = JSONObject.fromObject(retMap).toString();
        return "success";
    }

    // 校验激活码是否存在、是否可用
    @SuppressWarnings("unchecked")
    public String validActCode() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        String code = getParameter("code");
        log.info("into GameAction.validActCode");
        log.info("code=" + code);
        try {
            if (StrUtils.isEmpty(code)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                reqMap.put("status", "Y");
                // 查询激活码是否存在
                List<Map<String, Object>> isExist = appService.FindProductSerial(code);
                if (StrUtils.isNotEmpty(isExist)) {
                    reqMap.put("exist", "1");
                    // 如果存在，判断是否可用
                    List<Map<String, Object>> isUseful = appService.FindCode(code);
                    if (StrUtils.isEmpty(isUseful)) {
                        reqMap.put("useful", "1");
                    } else {
                        reqMap.put("useful", "0");
                    }
                } else {
                    reqMap.put("exist", "0");
                }

            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003, " + e.getMessage());
            log.error("GameAction.validActCode failed,", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 获取所有手机品牌信息
    public String getAllBrandsOfPhone() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        log.info("into AppAction.getAllBrandsOfPhone");
        try {
            List<Map<String, Object>> list = appService.getAllBrandsOfPhone();
            reqMap.put("brands", list);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("AppAction.getAllBrandsOfPhone failed,e : ", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    /**
     * 获取所有的手机品牌名称
     *
     * @return
     */
    public String getAllBrandsOfPhone_H5() {
        String callback = getParameter("callback");
        Map<String, Object> reqMap = new HashMap<String, Object>();
        log.info("into AppAction.getAllBrandsOfPhone");
        try {
            List<Map<String, Object>> list = appService.getAllBrandsOfPhone();
            reqMap.put("brands", list);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("AppAction.getAllBrandsOfPhone failed,e : ", e);
        }
        out = Base64.encode(callback) + "(" + JSONObject.fromObject(reqMap).toString() + ")";
        return "success";
    }

    // 根据品牌id获取该品牌下的所有型号信息
    public String getSubBrandByBrandId() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        String id = getParameter("id");// 品牌id
        log.info("into AppAction.getSubbrandByBrandId");
        log.info("id = " + id);
        try {
            List<Map<String, Object>> list = appService.getSubBrandByBrandId(id);
            reqMap.put("subBrands", list);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("AppAction.getSubBrandByBrandId failed,e : ", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    /**
     * 根据品牌id获取该品牌下的所有型号信息
     *
     * @return
     */
    public String getSubBrandByBrandId_H5() {
        String callback = getParameter("callback");
        Map<String, Object> reqMap = new HashMap<String, Object>();
        String id = getParameter("id");// 品牌id
        log.info("into AppAction.getSubbrandByBrandId");
        log.info("id = " + id);
        try {
            List<Map<String, Object>> list = appService.getSubBrandByBrandId(id);
            reqMap.put("subBrands", list);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("AppAction.getSubBrandByBrandId failed,e : ", e);
        }
        out = Base64.encode(callback) + "(" + JSONObject.fromObject(reqMap).toString() + ")";
        return "success";
    }

    // 根据手机型号获取型号中文名
    public String getNameByModel() {
        Map<String, Object> reqMap = new HashMap<>();
        String model = getParameter("model");// 手机型号
        log.info("into AppAction.getNameByModel");
        log.info("model = " + model);
        try {
            List<Map<String, Object>> list = appService.getNameByModel(model);
            reqMap.put("list", list);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("AppAction.getNameByModel failed,e : ", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 查询所有物流信息
    public String expressInfo() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        log.info("into AppAction.expressInfo");
        try {
            List<Map<String, Object>> list = appService.expressInfo();
            reqMap.put("express", list);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("AppAction.expressInfo failed,e : ", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 记录免费换膜行为
    public String recExchange() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        String imei = getParameter("imei");
        String uid = getParameter("uid");
        log.info("into AppAction.recExchange");
        log.info("imei = " + imei + ", uid = " + uid);
        try {
            appService.recExchange(imei, uid);
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("AppAction.recExchange failed,e : ", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    // 查询是否有过“免费购膜”行为
    public String hasExchanged() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        String imei = getParameter("imei");
        log.info("into AppAction.hasExchanged");
        log.info("imei = " + imei);
        try {
            int i = appService.hasExchanged(imei);
            reqMap.put("has", i == 0 ? "0" : "1");
            reqMap.put("status", "Y");
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("AppAction.hasExchanged failed,e : ", e);
        }
        out = JSONObject.fromObject(reqMap).toString();
        return "success";
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public AppService getAppService() {
        return appService;
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

}

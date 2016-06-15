package com.org.mokey.common;

import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.util.StrUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class LogoUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(LogoUploadServlet.class);

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        //设置接收的编码格式
        request.setCharacterEncoding("UTF-8");
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        String flag = request.getParameter("flag");
        String ekey = request.getParameter("ekey");// 2016-03-10 增加“e键”标记的获取
        String apk = request.getParameter("apk");

        String httpPath = "";
        // 获得容器中上传文件夹所在的物理路径
        try {
            MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) request;

            String [] fileArr = new String[]{"logourlScanField" , "imgurlScanField" , "appurlScanField","longImgurlScanField","thinImgurlScanField","indexImgurlScanField","halfImgurlScanField","titleImgurlScanField"};
            for(String fileType : fileArr) {
                if (multiWrapper.getFileNames(fileType) != null) {
                    // 获取文件名
                    String[] fileNames = multiWrapper.getFileNames(fileType);
                    // 获取文件
                    File[] files = multiWrapper.getFiles(fileType);
                    for (int i = 0; i < files.length; i++) {//
                        String name = id + "_" + i + fileNames[i].substring(fileNames[i].lastIndexOf("."));
                        // 上传，返回文件路径--多文件用逗号隔开
                        if (null != flag) {// “微用帮”logo上传
                            if ("mcrCate".equals(flag)) {
                                httpPath = FileServices.saveFile(files[i], "mcrApp/" + type + "/" + name);
                                continue;
                            }
                            if ("gameCate".equals(flag)) {
                                httpPath = FileServices.saveFile(files[i], "gameCate/" + type + "/" + name);
                                continue;
                            }
                            /*if ("ek".equals(flag)) {
                                httpPath = FileServices.saveFile(files[i], "ekCate/" + type + "/" + name);
                                continue;
                            }*/
                        } else {
                            // “游戏帮”logo上传
                            if (StrUtils.isEmpty(ekey)) {
                                httpPath = FileServices.saveFile(files[i], "gameChildLogo/" + type + "/" + name);
                            } else {// “e键”
                                if (StrUtils.isNotEmpty(apk)) {
                                    if ("1".equals(ekey)) {
                                        name = fileNames[i];
                                        // 首页
                                        httpPath = FileServices.saveFile(files[i], "ek-index/" + type + "/" + name);
                                    }
                                } else {
                                    if ("1".equals(ekey)) {
                                        // 首页
                                        httpPath = FileServices.saveFile(files[i], "ek-index/" + type + "/" + name);
                                    } else if ("2".equals(ekey)) {
                                        // 活动
                                        if(i==0){
                                            httpPath = FileServices.saveFile(files[i], "ek-activity/" + type + "/" + name);
                                        }else{
                                            httpPath +=","+FileServices.saveFile(files[i], "ek-activity/" + type + "/" +name);
                                        }
                                    } else if ("3".equals(ekey)) {
                                        // 游戏
                                        httpPath = FileServices.saveFile(files[i], "ek-game/" + type + "/" + name);
                                    }else if("4".equals(ekey)){
                                        httpPath = FileServices.saveFile(files[i], "ek-shop/" + type + "/" + name);
                                        //商城
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error(" update failed,", ex);
            this.msg(response, false, null, "没有上传文件");
            return;
        }
        this.msg(response, true, httpPath, null);
    }

    private void msg(HttpServletResponse response, boolean status, String filePath, String msg) {
        String json = "";
        if (status) {
            json = "{\"status\":1,\"filePath\":\"" + filePath + "\"}";
        } else {
            json = "{\"status\":0,\"msg\":\"" + msg + "\"}";
        }
        response.setContentType("text/html;charset=utf-8");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

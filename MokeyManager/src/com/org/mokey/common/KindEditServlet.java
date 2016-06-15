package com.org.mokey.common;

import com.alibaba.fastjson.JSONObject;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.util.Cn2Spell;
import com.org.mokey.util.StrUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class KindEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Logger logger = Logger.getLogger(KindEditServlet.class);
        logger.error("KindEditServlet>>>>>");

        HttpSession session = request.getSession();
        ServletOutputStream out = response.getOutputStream();
        // 获取请求参数
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        String ekey = request.getParameter("ekey");// 2016-03-10 增加“e键”标记的获取

        // 定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

        session.setAttribute("ajax_upload", 1);
        response.setContentType("text/html; charset=UTF-8");
        if (!ServletFileUpload.isMultipartContent(request)) {
            out.println(getError("请选择文件。"));
            return;
        }
        String dirName = request.getParameter("dir");
        request.getParameterMap();
        if (dirName == null) {
            dirName = "image";
        }
        if (!extMap.containsKey(dirName)) {
            out.println(getError("目录名不正确。"));
            return;
        }
        String httpPath = "";
        try {
            MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) request;
            if (multiWrapper.getFileNames("imgFile") != null) {
                // 获取文件名
                String[] fileNames = multiWrapper.getFileNames("imgFile");
                // 获取文件
                File[] files = multiWrapper.getFiles("imgFile");
                for (int i = 0; i < files.length; i++) {//
                    String name = id + "_" + i + fileNames[i].substring(fileNames[i].lastIndexOf("."));
                    /*if (StrUtils.isNotEmpty(ekey)) {// 2016-03-10 增加“e键”文件上传的判断
						type = "ek-" + type;
					}*/
                    // 上传，返回文件路径--多文件用逗号隔开
                    if (StringUtils.isEmpty(id)) {
                        httpPath = FileServices.saveFile(files[i], "webView/" + type + "/" + Cn2Spell.converterToSpellTrim(fileNames[i]) + "_" + System.currentTimeMillis());
                    } else {
                        if (StrUtils.isEmpty(ekey)) {
                            httpPath = FileServices.saveFile(files[i], "/" + type + "/" + id + "/" + Cn2Spell.converterToSpellTrim(fileNames[i]));
                        } else {// “e键”
                            if ("1".equals(ekey)) {
                                // 首页
                                httpPath = FileServices.saveFile(files[i], "ek-index/" + type + "/" + id + "_" + i + System.currentTimeMillis() + fileNames[i].substring(fileNames[i].lastIndexOf(".")));
                            } else if ("2".equals(ekey)) {
                                // 活动
                                httpPath = FileServices.saveFile(files[i], "ek-activity/" + type + "/" + id + "_" + i + System.currentTimeMillis() + fileNames[i].substring(fileNames[i].lastIndexOf(".")));
                            } else if ("3".equals(ekey)) {
                                // 游戏
                                httpPath = FileServices.saveFile(files[i], "ek-game/" + type + "/" + id + "_" + i + System.currentTimeMillis() + fileNames[i].substring(fileNames[i].lastIndexOf(".")));
                            }else if ("4".equals(ekey)) {
                                // 商城
                                httpPath = FileServices.saveFile(files[i], "ek-shop/" + type + "/" + id + "_" + i + System.currentTimeMillis() + fileNames[i].substring(fileNames[i].lastIndexOf(".")));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传文件异常：" + e.getMessage());
            out.println(getError("上传文件失败。"));
            return;
        }
        JSONObject obj = new JSONObject();
        obj.put("error", 0);
        obj.put("url", httpPath);
        out.println(obj.toJSONString());
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    private String getError(String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("message", message);
        return obj.toJSONString();
    }
}
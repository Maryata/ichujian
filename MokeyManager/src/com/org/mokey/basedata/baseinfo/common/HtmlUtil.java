package com.org.mokey.basedata.baseinfo.common;

import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.Base64;
import com.org.mokey.common.util.file.FileServices;
import com.org.mokey.common.util.number.NumberUtil;
import com.org.mokey.util.ParameterEncryptor;
import com.org.mokey.util.StrUtils;
import com.org.mokey.util.StreamUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlUtil {
    private Logger log = (Logger.getLogger(getClass()));

    private String readFile(String inputPath) throws IOException {
        BufferedReader inputStream = null;
        try {
            if (inputPath == null || inputPath.length() == 0) {
                return null;
            }
            inputPath = inputPath.replaceAll("%20", " ");// 处理空格
            StringBuffer returnSb = new StringBuffer();
            inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath), "UTF-8")); // 读取文档
            String temStr = null;
            String crlf = System.getProperty("line.separator");
            do {
                temStr = inputStream.readLine();
                if (temStr != null) {
                    returnSb.append(temStr).append(crlf);
                }
            } while (temStr != null);
            return returnSb.toString();
        } finally {
            if (inputStream != null) inputStream.close();
        }

    }

    @SuppressWarnings("unused")
    private void writeHtml(String filePath, String content) throws IOException {
        BufferedWriter bw = null;
        try {
            filePath = filePath.replaceAll("%20", " ");// 处理空格
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
        } finally {
            if (bw != null) bw.close();
        }
    }

    private void writeHtml2(String filePath, String content) throws IOException {
        log.debug("save.filePath :" + filePath);
        FileOutputStream out = null;
        try {
            filePath = filePath.replaceAll("%20", " ");// 处理空格
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file);
            out.write(content.getBytes("UTF-8"));
        } finally {
            if (out != null) out.close();
        }
    }

    public Map<String, String> createHtml(Map<String, ?> contentMap, Map<String, ?> actBrandMap) {
        Map<String, String> ret = new HashMap<String, String>();
        if (contentMap == null || StrUtils.isEmpty(contentMap.get("C_ID"))) {
            return ret;
        }
        if (StrUtils.isEmpty(contentMap.get("C_SDATE")) || StrUtils.isEmpty(contentMap.get("C_EDATE"))) {
            log.error("this act date is null\r\n: " + contentMap);
            return ret;
        }
        String httpRoot = FileServices.getHttpRoot();
        String savePath = FileServices.getSaveRoot();
        // test
        // httpRoot = savePath = "e:\\temp\\";

        String sdate = StrUtils.emptyOrString(contentMap.get("C_SDATE")).substring(0, 10).replace("-", "");

        String sharePath = "webView/actShare/" + sdate + "/" + contentMap.get("C_ID") + ".html";
        String detailPath = "webView/actDetail/" + sdate + "/" + contentMap.get("C_ID") + "_detail.html";
        // 获取之前的链接

        this.createHtml(savePath + sharePath, savePath + detailPath, contentMap, actBrandMap);
        ret.put("detailPath", httpRoot + detailPath);
        ret.put("sharePath", httpRoot + sharePath);
        log.debug("ret:" + ret);
        return ret;
    }

    public boolean createHtml(String sharePath, String detailPath, Map<String, ?> contentMap, Map<String, ?> actBrandMap) {
        try {
            long beginDate = (new Date()).getTime();
            String rootPath = StreamUtil.getRootPath();
            String filePath = rootPath + "WEB-INF/classes/conf/html/actShare.html";
            String htmlContent = readFile(filePath);
            if (null == htmlContent || "".equals(htmlContent)) {
                return false;
            }
            // 替换内容 @时间
            String date = StrUtils.emptyOrString(contentMap.get("C_SDATE")).substring(0, 10).replace("-", ".") + "-" + StrUtils.emptyOrString(contentMap.get("C_EDATE")).substring(0, 10).replace("-", ".");
            String content = StrUtils.emptyOrString(contentMap.get("C_CONTENT"));
            String img = StrUtils.emptyOrString(contentMap.get("C_IMAGEURL"));
            StringBuffer imgs = new StringBuffer();
            if (StrUtils.isNotEmpty(img)) {
                String[] imgArr = img.split(",");
                for (int i = 0; i < imgArr.length; i++) {
                    // <li> <img
                    // src="http://www.ichujian.com/static/images/1.jpg" > </li>
                    if (imgs.length() > 0) {
                        imgs.append("\r\n\t");
                    }
                    imgs.append("<li> <img src=\"").append(imgArr[i]).append("\"> </li>");
                }
            }

            String id = StrUtils.emptyOrString(contentMap.get("C_ID"));
            try {
                id = Base64.encode(id).trim();
            } catch (Exception e) {
                e.printStackTrace();
            }

            htmlContent = htmlContent.replace("###C_ID###", id);

            htmlContent = htmlContent.replace("###C_TITLE###", StrUtils.emptyOrString(contentMap.get("C_TITLE")));
            htmlContent = htmlContent.replace("###C_IMAGEURL###", imgs);
            htmlContent = htmlContent.replace("###C_GRADE###", StrUtils.emptyOrString(contentMap.get("C_GRADE")));
            htmlContent = htmlContent.replace("###C_DATE###", date);
            htmlContent = htmlContent.replace("###C_JOINURL###", StrUtils.emptyOrString(contentMap.get("C_JOINURL")));
            htmlContent = htmlContent.replace("###C_PUBLISHER###", StrUtils.emptyOrString(contentMap.get("C_PUBLISHER")));

            // 品牌信息
            htmlContent = htmlContent.replace("###C_BRANDNAME###", StrUtils.emptyOrString(actBrandMap.get("C_BRANDNAME")));
            htmlContent = htmlContent.replace("###C_BRANDLOGO###", StrUtils.emptyOrString(actBrandMap.get("C_BRANDLOGO")));
            htmlContent = htmlContent.replace("###C_INDUSTRYNAME###", StrUtils.emptyOrString(actBrandMap.get("C_INDUSTRYNAME")));

            // 活动内容
            htmlContent = htmlContent.replace("###C_CONTENT###", content);

            // 生成新文件
            writeHtml2(sharePath, htmlContent);
            htmlContent = null;

            // 根据内容生成html
            filePath = rootPath + "WEB-INF/classes/conf/html/actDetail.html";
            htmlContent = readFile(filePath);
            if (null == htmlContent || "".equals(htmlContent)) {
                return false;
            }
            htmlContent = htmlContent.replace("###C_CONTENT###", content);
            // 生成新文件
            writeHtml2(detailPath, htmlContent);
            htmlContent = null;
            log.debug("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
        } catch (IOException e) {
            log.error("createHtml failed,", e);
            return false;
        }
        return true;
    }

    public String createGameHtml(Map<String, ?> contentMap) {
        String sharePath = null;
        try {
            long beginDate = (new Date()).getTime();
            String rootPath = StreamUtil.getRootPath();
            String filePath = rootPath + "WEB-INF/classes/conf/html/gameShare.html";
            String htmlContent = readFile(filePath);
            if (null == htmlContent || "".equals(htmlContent)) {
                return null;
            }

            // 获取模版页面所需字段
            String c_id = StrUtils.emptyOrString(contentMap.get("c_id"));
            String c_name = StrUtils.emptyOrString(contentMap.get("c_name"));
            String c_logurl = StrUtils.emptyOrString(contentMap.get("C_LOGOURL"));
            String c_version = StrUtils.emptyOrString(contentMap.get("c_version"));
            String c_size = StrUtils.emptyOrString(contentMap.get("c_size"));
            String c_abstract = StrUtils.emptyOrString(contentMap.get("c_abstract"));
            String c_appurl = StrUtils.emptyOrString(contentMap.get("C_APPURL"));
            String c_picurl = StrUtils.emptyOrString(contentMap.get("C_PICURL"));

            // 图片集数组
            JSONArray jsonArray = new JSONArray();

            if (StrUtils.isNotEmpty(c_picurl)) {
                String[] imgArr = c_picurl.split(",");
                for (int i = 0; i < imgArr.length; ++i) {
                    JSONObject json = new JSONObject();
                    json.put("href", imgArr[i]);
                    json.put("pic", imgArr[i]);
                    json.put("title", "");

                    jsonArray.add(json);
                }
            }
            Map<String, String> params = new HashMap<String, String>();
            try {
                params.put("gid", com.alibaba.druid.util.Base64.byteArrayToBase64(c_id.getBytes("UTF-8")));
            } catch (Exception e1) {
                log.error(e1);
            }

            htmlContent = htmlContent.replace("###C_ID###", c_id);
            try {
                htmlContent = htmlContent.replace("###sign###", ParameterEncryptor.encrypt(params));
            } catch (Exception e) {
                e.printStackTrace();
            }

            String strSize = "0M";
            if (!c_size.isEmpty()) {
                strSize = NumberUtil.divToStr(Integer.parseInt(c_size), 1024 * 1024, 2) + "M";
            }

            String absString1 = "", absString2 = "";
            if (c_abstract.length() >= 91) {
                absString1 = c_abstract.substring(0, 90);
                absString2 = c_abstract.substring(90, c_abstract.length());
            } else {
                absString1 = c_abstract;
            }

            htmlContent = htmlContent.replace("###C_NAME###", c_name);
            htmlContent = htmlContent.replace("###C_LOGOURL###", c_logurl);
            htmlContent = htmlContent.replace("###C_VERSION###", c_version);
            htmlContent = htmlContent.replace("###C_SIZE###", strSize);
            htmlContent = htmlContent.replace("###C_ABSTRACT1###", absString1);
            htmlContent = htmlContent.replace("###C_ABSTRACT2###", absString2);
            htmlContent = htmlContent.replace("###C_DATE###", ApDateTime.getNowDateTime("yyyy-MM-dd"));
            htmlContent = htmlContent.replace("###C_APPURL###", c_appurl);
            htmlContent = htmlContent.replace("###IMGARRAY###", jsonArray.toString());

            sharePath = "webView/gameShare/" + c_id + ".html";
            // 生成新文件
            writeHtml2(FileServices.getSaveRoot() + sharePath, htmlContent);
            htmlContent = null;
            sharePath = FileServices.getHttpRoot() + sharePath;

            log.debug("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
        } catch (IOException e) {
            log.error("createHtml failed,", e);
            sharePath = null;
        }

        return sharePath;
    }

    /**
     * 生成游戏资讯、攻略、活动相关html
     *
     * @param item       资讯/攻略/活动
     * @param contentMap 参数
     * @return html静态文件保存地址
     */
    public String createGameHtmlV2(String item, Map<String, ?> contentMap) {
        String sharePath = null;
        try {
            long beginDate = (new Date()).getTime();
            String rootPath = StreamUtil.getRootPath();
            String filePath;
            if (item.startsWith("ek-")) {
                filePath = rootPath + "WEB-INF/classes/conf/html/" + item.substring(3) + ".html";
            } else {
                filePath = rootPath + "WEB-INF/classes/conf/html/" + item + ".html";
            }
            String htmlContent = readFile(filePath);
            if (null == htmlContent || "".equals(htmlContent)) {
                return null;
            }

            // 获取模版页面所需字段
            String c_id = StrUtils.emptyOrString(contentMap.get("c_id"));
            String c_name = StrUtils.emptyOrString(contentMap.get("c_name"));

            String c_date = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Object dateObj = contentMap.get("c_date");
            if (StrUtils.isNotEmpty(dateObj)) {
                Date date = (Date) contentMap.get("c_date");
                c_date = sdf.format(date);
            }
            // String c_num = StrUtils.emptyOrString(contentMap.get("c_num"));
            String c_depict = StrUtils.emptyOrString(contentMap.get("c_depict"));

            Map<String, String> params = new HashMap<String, String>();
            try {
                params.put("id", com.alibaba.druid.util.Base64.byteArrayToBase64(c_id.getBytes("UTF-8")));
            } catch (Exception e1) {
                log.error(e1);
            }

            htmlContent = htmlContent.replace("###C_ID###", c_id);
            try {
                htmlContent = htmlContent.replace("###sign###", ParameterEncryptor.encrypt(params));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!"ek-gameActivityShare".equals(item)) {
                htmlContent = htmlContent.replace("###C_NAME###", c_name);
            }
            htmlContent = htmlContent.replace("###C_DATE###", c_date);
            // htmlContent = htmlContent.replace("###C_NUM###", c_num);
            htmlContent = htmlContent.replace("###C_DEPICT###", c_depict);

            sharePath = "webView/" + item + "/html/" + c_id + ".html";
            // 生成新文件
            writeHtml2(FileServices.getSaveRoot() + sharePath, htmlContent);
            htmlContent = null;
            sharePath = FileServices.getHttpRoot() + sharePath;

            log.debug("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
        } catch (IOException e) {
            log.error("createGameHtmlV2 failed,", e);
            sharePath = null;
        }

        return sharePath;
    }

    /**
     * @param json 包含游戏列表和广告的集合
     * @Description: TODO 创建H5游戏静态页面
     */
    public void createH5GameHtml(JSONObject json) {

        _fillDiv(json);
        _fillDiv2(json);
        // _fillH5Advert( json.getJSONObject( "advert" ) ); // 广告信息
        // _fillH5Collection( json.getJSONObject( "collection-0" ), 1 ); //
        // 热门游戏集合
        // _fillH5Collection( json.getJSONObject( "collection-1" ), 2 ); //
        // 最新游戏集合
        // _fillH5Collection( json.getJSONObject( "collection-2" ), 3 ); //
        // 排行游戏集合

        // 第二套生成规则
        // _fillH5Advert2( json.getJSONObject( "advert" ) ); // 广告信息
        // _fillH5Collection2( json.getJSONObject( "collection-0" ), 1 ); //
        // 热门游戏集合
        // _fillH5Collection2( json.getJSONObject( "collection-1" ), 2 ); //
        // 最新游戏集合
        // _fillH5Collection2( json.getJSONObject( "collection-2" ), 3 ); //
        // 排行游戏集合

    }

    /**
     * @param json
     * @Description: TODO
     */
    private void _fillDiv(JSONObject json) {
        String path = null;
        try {
            long beginDate = (new Date()).getTime();
            String rootPath = StreamUtil.getRootPath();
            String filePath = rootPath + "WEB-INF/classes/conf/html/index_div.html";
            String htmlContent = readFile(filePath);
            if (null == htmlContent || "".equals(htmlContent)) {
                return;
            }
            JSONObject jsonObject = json.getJSONObject("advert");
            String status = jsonObject.getString("status");
            StringBuffer sb_a = new StringBuffer("");
            StringBuffer sb_li = new StringBuffer();
            if ("Y".equals(status)) {
                JSONArray jsonArray = jsonObject.getJSONArray("advert");
                for (int i = 0; i < jsonArray.size(); ++i) {
                    JSONObject _json = jsonArray.getJSONObject(i);
                    String name = _json.getString("NAME");
                    if (null == name || "null".equalsIgnoreCase(name)) {
                        name = "";
                    }

                    sb_a.append("<a href='#'></a>");
                    sb_li.append("<li><a href='javascript:void(0);' onclick='startH5Game(\"").append(_json.getString("APPURL")).append("\",").append(_json.getLong("APPID")).append(",\"").append(_json.getString("PICURL")).append("\",\"").append(name).append("\"").append(");'><img width='100%' src='").append(_json.getString("PICURL")).append("'></a></li>");
                }

                htmlContent = htmlContent.replace("###a###", sb_a.toString()).replace("###li###", sb_li.toString());
            }

            JSONObject[] array = new JSONObject[]{json.getJSONObject("collection-0"), json.getJSONObject("collection-1"), json.getJSONObject("collection-2")};
            for (int i = 1; i <= array.length; ++i) {
                jsonObject = array[i - 1];
                status = jsonObject.getString("status");

                StringBuffer sb_dl = new StringBuffer("");
                if ("Y".equals(status)) {
                    JSONArray jsonArray = jsonObject.getJSONArray("games");
                    int len = jsonArray.size();
                    for (int j = 0; j < len; ++j) {
                        JSONObject _json = jsonArray.getJSONObject(j);
                        sb_dl.append("<dl>");
                        if (j == 0 && i == 3) {
                            sb_dl.append("<span class='first'></span>");
                        } else if (j == 1 && i == 3) {
                            sb_dl.append("<span class='second'></span>");
                        } else if (j == 2 && i == 3) {
                            sb_dl.append("<span class='third'></span>");
                        } else if ((j != len - 1) && i == 3) {
                            sb_dl.append("<span>").append(j + 1).append("</span>");
                        } else if (j == len - 1 && i == 3) {
                            sb_dl.append("<span id='last'>").append(j + 1).append("</span>");
                        }
                        String title = _json.getString("C_TITLE");
                        if (null == title || "null".equalsIgnoreCase(title)) {
                            title = "";
                        }

                        sb_dl.append("<a href='javascript:void(0);' onclick='startH5Game(\"").append(_json.getString("C_APPURL")).append("\",").append(_json.getLong("C_ID")).append(",\"").append(_json.getString("C_LOGOURL")).append("\",\"").append(_json.getString("C_NAME")).append("\"").append(");' class='play'>开始玩</a><dt><img width='100%' src='").append(_json.getString("C_LOGOURL")).append("'></dt><dd><h1>").append(_json.getString("C_NAME")).append("</h1><span class='online'>").append(_json.getString("startCnts")).append("人已玩过</span><span class='intr'>").append(title).append("</span></dd></dl>");
                    }
                    String dl = "###dl" + i + "###";

                    htmlContent = htmlContent.replace(dl, sb_dl.toString());
                }
            }

            path = "webView/h5Game/h5List.html";
            // 生成新文件
            writeHtml2(FileServices.getSaveRoot() + path, htmlContent);
            htmlContent = null;
            path = FileServices.getHttpRoot() + path;

            log.debug("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
        } catch (IOException e) {
            log.error("createHtml failed,", e);
            path = null;
        }
    }

    private void _fillDiv2(JSONObject json) {
        String path = null;
        try {
            long beginDate = (new Date()).getTime();
            String rootPath = StreamUtil.getRootPath();
            String filePath = rootPath + "WEB-INF/classes/conf/html/index_div2.html";
            String htmlContent = readFile(filePath);
            if (null == htmlContent || "".equals(htmlContent)) {
                return;
            }
            JSONObject jsonObject = json.getJSONObject("advert");
            String status = jsonObject.getString("status");
            StringBuffer sb_a = new StringBuffer("");
            StringBuffer sb_li = new StringBuffer();
            if ("Y".equals(status)) {
                JSONArray jsonArray = jsonObject.getJSONArray("advert");
                for (int i = 0; i < jsonArray.size(); ++i) {
                    JSONObject _json = jsonArray.getJSONObject(i);
                    String name = _json.getString("NAME");
                    if (null == name || "null".equalsIgnoreCase(name)) {
                        name = "";
                    }

                    sb_a.append("<a href='#'></a>");
                    sb_li.append("<li><a href='").append(_json.getString("APPURL")).append("'><img width='100%' src='").append(_json.getString("PICURL")).append("'></a></li>");
                }

                htmlContent = htmlContent.replace("###a###", sb_a.toString()).replace("###li###", sb_li.toString());
            }

            JSONObject[] array = new JSONObject[]{json.getJSONObject("collection-0"), json.getJSONObject("collection-1"), json.getJSONObject("collection-2")};
            for (int i = 1; i <= array.length; ++i) {
                jsonObject = array[i - 1];
                status = jsonObject.getString("status");

                StringBuffer sb_dl = new StringBuffer("");
                if ("Y".equals(status)) {
                    JSONArray jsonArray = jsonObject.getJSONArray("games");
                    int len = jsonArray.size();
                    for (int j = 0; j < len; ++j) {
                        JSONObject _json = jsonArray.getJSONObject(j);
                        sb_dl.append("<dl>");
                        if (j == 0 && i == 3) {
                            sb_dl.append("<span class='first'></span>");
                        } else if (j == 1 && i == 3) {
                            sb_dl.append("<span class='second'></span>");
                        } else if (j == 2 && i == 3) {
                            sb_dl.append("<span class='third'></span>");
                        } else if ((j != len - 1) && i == 3) {
                            sb_dl.append("<span>").append(j + 1).append("</span>");
                        } else if (j == len - 1 && i == 3) {
                            sb_dl.append("<span id='last'>").append(j + 1).append("</span>");
                        }
                        String title = _json.getString("C_TITLE");
                        if (null == title || "null".equalsIgnoreCase(title)) {
                            title = "";
                        }

                        sb_dl.append("<a href='").append(_json.getString("C_APPURL")).append("' target='_parent' class='play'>开始玩</a><dt><img width='100%' src='").append(_json.getString("C_LOGOURL")).append("'></dt><dd><h1>").append(_json.getString("C_NAME")).append("</h1><span class='online'>").append(_json.getString("startCnts")).append("人已玩过</span><span class='intr'>").append(title).append("</span></dd></dl>");
                    }
                    String dl = "###dl" + i + "###";

                    htmlContent = htmlContent.replace(dl, sb_dl.toString());
                }
            }

            path = "webView/h5Game/h5List-WebBrowser.html";
            // 生成新文件
            writeHtml2(FileServices.getSaveRoot() + path, htmlContent);
            htmlContent = null;
            path = FileServices.getHttpRoot() + path;

            log.debug("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
        } catch (IOException e) {
            log.error("createHtml failed,", e);
            path = null;
        }
    }

    /**
     * @param jsonObject
     * @param i
     * @Description: TODO
     */
    public void _fillH5Collection(JSONObject jsonObject, int i) {
        long beginDate = (new Date()).getTime();
        String rootPath = StreamUtil.getRootPath();
        String filePath = null, path = null;
        if (i == 1) { // 热门
            filePath = rootPath + "WEB-INF/classes/conf/html/h5_hot.html";
            path = "webView/h5Game/hot.html";
        } else if (i == 2) { // 最新
            filePath = rootPath + "WEB-INF/classes/conf/html/h5_new.html";
            path = "webView/h5Game/new.html";
        } else if (i == 3) { // 排行
            filePath = rootPath + "WEB-INF/classes/conf/html/h5_rank.html";
            path = "webView/h5Game/rank.html";
        }

        try {
            String htmlContent = readFile(filePath);
            if (null == htmlContent || "".equals(htmlContent)) {
                return;
            }
            String status = jsonObject.getString("status");
            // String[] keys = new String[] { "C_JARNAME", "C_NAME",
            // "C_LOGOURL",
            // "C_ID", "C_APPURL", "C_TITLE","startCnts" };
            // <dl>
            // <span class="first"></span>
            // <a href="#" class="play">开始玩</a>
            // <dt><img width="100%" src="img/img2.png"></dt>
            // <dd>
            // <h1>少年三国志</h1>
            // <span class="online">1234人正在玩</span> <span
            // class="intr">雄心壮志一统三国</span> </dd>
            // </dl>
            StringBuffer sb_dl = new StringBuffer("");
            if ("Y".equals(status)) {
                JSONArray jsonArray = jsonObject.getJSONArray("games");
                int len = jsonArray.size();
                for (int j = 0; j < len; ++j) {
                    JSONObject json = jsonArray.getJSONObject(j);
                    sb_dl.append("<dl>");
                    if (j == 0 && i == 3) {
                        sb_dl.append("<span class='first'></span>");
                    } else if (j == 1 && i == 3) {
                        sb_dl.append("<span class='second'></span>");
                    } else if (j == 2 && i == 3) {
                        sb_dl.append("<span class='third'></span>");
                    } else if ((j != len - 1) && i == 3) {
                        sb_dl.append("<span>").append(j + 1).append("</span>");
                    } else if (j == len - 1 && i == 3) {
                        sb_dl.append("<span id='last'>").append(j + 1).append("</span>");
                    }
                    String title = json.getString("C_TITLE");
                    if (null == title || "null".equalsIgnoreCase(title)) {
                        title = "";
                    }

                    sb_dl.append("<a href='javascript:void(0);' onclick='startH5Game(\"").append(json.getString("C_APPURL")).append("\",").append(json.getLong("C_ID")).append(",\"").append(json.getString("C_LOGOURL")).append("\",\"").append(json.getString("C_NAME")).append("\"").append(");' class='play'>开始玩</a><dt><img width='100%' src='").append(json.getString("C_LOGOURL")).append("'></dt><dd><h1>").append(json.getString("C_NAME")).append("</h1><span class='online'>").append(json.getString("startCnts")).append("人已玩过</span><span class='intr'>").append(title).append("</span></dd></dl>");
                }

                htmlContent = htmlContent.replace("###dl###", sb_dl.toString());
            }
            // 生成新文件
            writeHtml2(FileServices.getSaveRoot() + path, htmlContent);
            htmlContent = null;
            path = FileServices.getHttpRoot() + path;

            log.debug("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
        } catch (IOException e) {
            log.error("createHtml failed,", e);
            path = null;
        }

    }

    public void _fillH5Collection2(JSONObject jsonObject, int i) {
        long beginDate = (new Date()).getTime();
        String rootPath = StreamUtil.getRootPath();
        String filePath = null, path = null;
        if (i == 1) { // 热门
            filePath = rootPath + "WEB-INF/classes/conf/html/h5_hot2.html";
            path = "webView/h5Game/hot2.html";
        } else if (i == 2) { // 最新
            filePath = rootPath + "WEB-INF/classes/conf/html/h5_new2.html";
            path = "webView/h5Game/new2.html";
        } else if (i == 3) { // 排行
            filePath = rootPath + "WEB-INF/classes/conf/html/h5_rank2.html";
            path = "webView/h5Game/rank2.html";
        }

        try {
            String htmlContent = readFile(filePath);
            if (null == htmlContent || "".equals(htmlContent)) {
                return;
            }
            String status = jsonObject.getString("status");
            StringBuffer sb_dl = new StringBuffer("");
            if ("Y".equals(status)) {
                JSONArray jsonArray = jsonObject.getJSONArray("games");
                int len = jsonArray.size();
                for (int j = 0; j < len; ++j) {
                    JSONObject json = jsonArray.getJSONObject(j);
                    sb_dl.append("<dl>");
                    if (j == 0 && i == 3) {
                        sb_dl.append("<span class='first'></span>");
                    } else if (j == 1 && i == 3) {
                        sb_dl.append("<span class='second'></span>");
                    } else if (j == 2 && i == 3) {
                        sb_dl.append("<span class='third'></span>");
                    } else if ((j != len - 1) && i == 3) {
                        sb_dl.append("<span>").append(j + 1).append("</span>");
                    } else if (j == len - 1 && i == 3) {
                        sb_dl.append("<span id='last'>").append(j + 1).append("</span>");
                    }
                    String title = json.getString("C_TITLE");
                    if (null == title || "null".equalsIgnoreCase(title)) {
                        title = "";
                    }

                    sb_dl.append("<a href='").append(json.getString("C_APPURL")).append("' target='_parent' class='play'>开始玩</a><dt><img width='100%' src='").append(json.getString("C_LOGOURL")).append("'></dt><dd><h1>").append(json.getString("C_NAME")).append("</h1><span class='online'>").append(json.getString("startCnts")).append("人已玩过</span><span class='intr'>").append(title).append("</span></dd></dl>");
                }

                htmlContent = htmlContent.replace("###dl###", sb_dl.toString());
            }
            // 生成新文件
            writeHtml2(FileServices.getSaveRoot() + path, htmlContent);
            htmlContent = null;
            path = FileServices.getHttpRoot() + path;

            log.debug("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
        } catch (IOException e) {
            log.error("createHtml failed,", e);
            path = null;
        }

    }

    /**
     * @param jsonObject 包含H5广告位信息的集合
     * @Description: TODO 填充H5广告位信息
     */
    public void _fillH5Advert(JSONObject jsonObject) {
        // <a href="#"></a>
        // <li> <a href="#"><img width="100%" src="img/1.jpg"></a> </li>
        String path = null;
        try {
            long beginDate = (new Date()).getTime();
            String rootPath = StreamUtil.getRootPath();
            String filePath = rootPath + "WEB-INF/classes/conf/html/h5_index.html";
            String htmlContent = readFile(filePath);
            if (null == htmlContent || "".equals(htmlContent)) {
                return;
            }
            String status = jsonObject.getString("status");
            // String[] keys = new String[]
            // {"APPID","APPURL","ID","PICURL","NAME"};
            StringBuffer sb_a = new StringBuffer("");
            StringBuffer sb_li = new StringBuffer();
            if ("Y".equals(status)) {
                JSONArray jsonArray = jsonObject.getJSONArray("advert");
                for (int i = 0; i < jsonArray.size(); ++i) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    String name = json.getString("NAME");
                    if (null == name || "null".equalsIgnoreCase(name)) {
                        name = "";
                    }

                    sb_a.append("<a href='#'></a>");
                    sb_li.append("<li><a href='javascript:void(0);' onclick='startH5Game(\"").append(json.getString("APPURL")).append("\",").append(json.getLong("APPID")).append(",\"").append(json.getString("PICURL")).append("\",\"").append(name).append("\"").append(");'><img width='100%' src='").append(json.getString("PICURL")).append("'></a></li>");
                }

                htmlContent = htmlContent.replace("###a###", sb_a.toString()).replace("###li###", sb_li.toString());
            }

            path = "webView/h5Game/h5List.html";
            // 生成新文件
            writeHtml2(FileServices.getSaveRoot() + path, htmlContent);
            htmlContent = null;
            path = FileServices.getHttpRoot() + path;

            log.debug("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
        } catch (IOException e) {
            log.error("createHtml failed,", e);
            path = null;
        }
    }

    public void _fillH5Advert2(JSONObject jsonObject) {
        String path = null;
        try {
            long beginDate = (new Date()).getTime();
            String rootPath = StreamUtil.getRootPath();
            String filePath = rootPath + "WEB-INF/classes/conf/html/h5_index2.html";
            String htmlContent = readFile(filePath);
            if (null == htmlContent || "".equals(htmlContent)) {
                return;
            }
            String status = jsonObject.getString("status");
            // String[] keys = new String[]
            // {"APPID","APPURL","ID","PICURL","NAME"};
            StringBuffer sb_a = new StringBuffer("");
            StringBuffer sb_li = new StringBuffer();
            if ("Y".equals(status)) {
                JSONArray jsonArray = jsonObject.getJSONArray("advert");
                for (int i = 0; i < jsonArray.size(); ++i) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    String name = json.getString("NAME");
                    if (null == name || "null".equalsIgnoreCase(name)) {
                        name = "";
                    }

                    sb_a.append("<a href='#'></a>");
                    sb_li.append("<li><a href='").append(json.getString("APPURL")).append("'><img width='100%' src='").append(json.getString("PICURL")).append("'></a></li>");
                }

                htmlContent = htmlContent.replace("###a###", sb_a.toString()).replace("###li###", sb_li.toString());
            }

            path = "webView/h5Game/h5List-WebBrowser.html";
            // 生成新文件
            writeHtml2(FileServices.getSaveRoot() + path, htmlContent);
            htmlContent = null;
            path = FileServices.getHttpRoot() + path;

            log.debug("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
        } catch (IOException e) {
            log.error("createHtml failed,", e);
            path = null;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        HtmlUtil hu = new HtmlUtil();
        Map<String, Object> contentMap = new HashMap<String, Object>();
        contentMap.put("C_ID", "12");
        contentMap.put("C_IMAGEURL", "http://f2.lashouimg.com/cms/M00/87/55/CqgBJlRXILeASpZXAABVSc42Qys128-440x280.jpg,http://www.ichujian.com/static/images/2.jpg,http://f2.lashouimg.com/cms/M00/87/61/CqgBJlRXKZ-AekyEAACXTxOYScQ664-440x280.jpg");
        contentMap.put("C_TITLE", "【上海】椰园东南亚风情餐厅cccc");
        contentMap.put("C_SDATE", "2014-01-02");
        contentMap.put("C_EDATE", "2015-12-09");
        contentMap.put("C_GRADE", 4);
        contentMap.put("C_JOINURL", "http://sh.meituan.com/deal/28627444.html");
        contentMap.put("C_CONTENT", "霸王餐：椰园东南亚风情餐厅，免费享！价值200元的代金券1张免费赠送。邀请1个新用户参加或绑定新浪微博多1个抽奖号码，中奖概率翻倍");

        Map brandMap = new HashMap();
        brandMap.put("C_BRANDNAME", "亚马逊");
        brandMap.put("C_BRANDLOGO", "http://resources.51camel.com/Resources/uploadFile/B_Brand/BrandLogo/0/634751830140222446.jpg");
        brandMap.put("C_INDUSTRYNAME", "爱车&养车");

        hu.createHtml(contentMap, brandMap);
        /*
		 * for(int i=0;i<2;i++){
		 * hu.createHtml("e:\\temp\\act_"+i+".html","e:\\temp\\act_"
		 * +i+"_detail.html", contentMap); }
		 */

    }

    /**
     * 生成活动详情html
     *
     * @param /ek-activityShare 活动
     * @param contentMap        参数
     * @return html静态文件保存地址
     */
    public String createEkHtml(String item, Map<String, ?> contentMap) {

        String sharePath = null;
        try {
            long beginDate = (new Date()).getTime();
            String rootPath = StreamUtil.getRootPath();
            String filePath = rootPath + "WEB-INF/classes/conf/html/" + item + ".html";
            String htmlContent = readFile(filePath);
            if (null == htmlContent || "".equals(htmlContent)) {
                return null;
            }

            // 获取模版页面所需字段
            String c_id = StrUtils.emptyOrString(contentMap.get("C_ID"));
            String detail = StrUtils.emptyOrString(contentMap.get("C_FULL_DETAIL"));

            Map<String, String> params = new HashMap<String, String>();
            try {
                params.put("id", com.alibaba.druid.util.Base64.byteArrayToBase64(c_id.getBytes("UTF-8")));
            } catch (Exception e1) {
                log.error(e1);
            }

            htmlContent = htmlContent.replace("###C_FULL_DETAIL###", detail);


            sharePath = "webView/" + item + "/html/" + c_id + ".html";
            // 生成新文件
            writeHtml2(FileServices.getSaveRoot() + sharePath, htmlContent);
            htmlContent = null;
            sharePath = FileServices.getHttpRoot() + sharePath;

            log.debug("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
        } catch (IOException e) {
            log.error("createEkHtml failed,", e);
            sharePath = null;
        }

        return sharePath;
    }

    /**
     * 生成活动完整页面
     *
     * @param item
     * @param contentMap
     * @return
     */
    public String createActivityHtml(String item, Map<String, Object> contentMap,Map<String,List> mapList) {
        String sharePath = null;
        try {
            long beginDate = (new Date()).getTime();
            String rootPath = StreamUtil.getRootPath();
            String filePath = rootPath + "WEB-INF/classes/conf/html/" + item + ".html";
            String htmlContent = readFile(filePath);
            if (null == htmlContent || "".equals(htmlContent)) {
                return null;
            }
            // 获取模版页面所需字段
            String c_id = StrUtils.emptyOrString(contentMap.get("C_ID"));
            String title = StrUtils.emptyOrString(contentMap.get("C_TITLE"));
            String sdate = StrUtils.emptyOrString(contentMap.get("C_SDATE"));
            String edate = StrUtils.emptyOrString(contentMap.get("C_EDATE"));
            String img = StrUtils.emptyOrString(contentMap.get("C_IMG"));
            String view = StrUtils.emptyOrString(contentMap.get("C_VIEW"));
            String vote = StrUtils.emptyOrString(contentMap.get("C_VOTE"));
            String favorite = StrUtils.emptyOrString(contentMap.get("C_FAVORITE"));
            String detail = StrUtils.emptyOrString(contentMap.get("C_DETAIL"));
            String tip = StrUtils.emptyOrString(contentMap.get("C_TIP"));
            String reason = StrUtils.emptyOrString(contentMap.get("C_REASON"));
            String publisher = StrUtils.emptyOrString(contentMap.get("C_PUBLISHER"));
            String urlShare = StrUtils.emptyOrString(contentMap.get("C_URL_SHARE"));
            String fullDetail = StrUtils.emptyOrString(contentMap.get("C_FULL_DETAIL"));
            String url = StrUtils.emptyOrString(contentMap.get("C_URL"));
            List nameList=mapList.get("nameList");
            List LogoList=mapList.get("logoList");
            Map<String, String> params = new HashMap<String, String>();
            try {
                params.put("id", com.alibaba.druid.util.Base64.byteArrayToBase64(c_id.getBytes("UTF-8")));
            } catch (Exception e1) {
                log.error(e1);
            }
            //轮播图
            String img_html="";
            String[] imgs=img.split(",");
            for(String logo : imgs){
                if(!"".equals(logo)){
                    img_html+="<li style=\"background: url("+logo+")  no-repeat;background-size:100% 300px;\">" + "<a></a></li>";
                }else{
                    img_html+="<li style=\"background: url()  no-repeat;background-size:100% 300px;\">" + "<a></a></li>";
                }
            }

            //注意事项
            String notice_html="";
            if(StrUtils.isNotEmpty(nameList) ||StrUtils.isNotEmpty(LogoList)){
                notice_html+="<div class=\"w_all div_h30 l_h30 c_40 f_16 p_0_10 m_t10\">\n" +
                        "<img class=\" f_l yl_ico\" src=\"http://ichujian.com/webView/ek-activityShare/basestatic/images/ico_ts.png\" />\n" +
                        "<span class=\"f_l \">注意事项</span>\n" +
                        "</div>";
                notice_html+="<div class=\"w_all div_h30 l_h30 c_40 f_16 p_0_10\">";
                for(int i=0;i<nameList.size();i++){
                    notice_html+="<img class=\" f_l yl_ico\" style=\"margin-left:40px;\" src=\""+LogoList.get(i)+"\" />\n" + "  <span class=\"f_l c_999 f_16\">"+nameList.get(i)+"</span>";
                }
                notice_html+="</div>";
                htmlContent = htmlContent.replace("###C_NOTICE###", notice_html);
            }else{
                htmlContent = htmlContent.replace("###C_NOTICE###", "");
            }
            //标题
            htmlContent = htmlContent.replace("###C_TITLE###", title);
            htmlContent = htmlContent.replace("###C_SDATE###", sdate);
            htmlContent = htmlContent.replace("###C_EDATE###", edate);
            htmlContent = htmlContent.replace("###C_IMG###", img_html);
            htmlContent = htmlContent.replace("###C_VIEW###", view);
            htmlContent = htmlContent.replace("###C_VOTE###", vote);
            htmlContent = htmlContent.replace("###C_FAVORITE###", favorite);
            //活动详情
            String  detail_html="";
            if (StrUtils.isNotEmpty(detail)){
                detail_html+="<div class=\"w_all div_h30 l_h30 c_40 f_16 p_0_10 m_t10\">\n" +
                        "<img class=\" f_l yl_ico\" src=\"http://ichujian.com/webView/ek-activityShare/basestatic/images/ico_rj.png\" />\n" +
                        "<span class=\"f_l \">活动详情</span>\n" +
                        "</div>";
                detail_html+="<div class=\"w_85b c_999 f_16 p_0_10 m_l30 m_b10\">"+detail+"</div>";
                htmlContent = htmlContent.replace("###C_DETAIL###", detail_html);
            }else{
                htmlContent = htmlContent.replace("###C_DETAIL###", "");
            }

            //小编提示
            String tip_html="";
            if(StrUtils.isNotEmpty(tip)){
                tip_html+="<div class=\"w_all div_h30 l_h30 c_40 f_16 p_0_10 m_t10\">\n" +
                        " <img class=\" f_l yl_ico\" src=\"http://ichujian.com/webView/ek-activityShare/basestatic/images/ico_ts-07.png\" />\n" +
                        " <span class=\"f_l \">小编提示</span>\n" +
                        " </div>";
                tip_html+="<div class=\"w_85b c_999 f_16 p_0_10 m_l30 m_b10\">"+tip+"</div>";
                htmlContent = htmlContent.replace("###C_TIP###", tip_html);
            }else{
                htmlContent = htmlContent.replace("###C_TIP###", "");
            }

            //推荐理由
            String reason_html="";
            if(StrUtils.isNotEmpty(reason)){
                reason_html+=" <div class=\"w_all div_h30 l_h30 c_40 f_16 p_0_10 m_t10\">\n" +
                        "<img class=\" f_l yl_ico\" src=\"http://ichujian.com/webView/ek-activityShare/basestatic/images/ico_bang.png\" />\n" +
                        " <span class=\"f_l \">推荐理由</span>\n" +
                        "</div>";
                reason_html+="<div class=\"w_85b c_999 f_16 p_0_10 m_l30 m_b10\">"+reason+"</div>";
                htmlContent = htmlContent.replace("###C_REASON###", reason_html);
            }else{
                htmlContent = htmlContent.replace("###C_REASON###", "");
            }


            htmlContent = htmlContent.replace("###C_PUBLISHER###", publisher);


            //查看完整活动详情
            String urlShare_html="";
            if(StrUtils.isNotEmpty(fullDetail.trim()) ){
                urlShare_html+="<div class=\"w_all div_h50 l_h50 t_c c_cheng f_16 p_0_10\"><a href=\""+urlShare+"\">查看完整活动详情>></a></div>";
                htmlContent = htmlContent.replace("###C_URL_SHARE###", urlShare_html);
            }else{
                htmlContent = htmlContent.replace("###C_URL_SHARE###", "");
            }
            htmlContent = htmlContent.replace("###C_URL###", url);


            sharePath = "webView/" + item + "/html/" + c_id + ".html";
            // 生成新文件
            writeHtml2(FileServices.getSaveRoot() + sharePath, htmlContent);
            htmlContent = null;
            sharePath = FileServices.getHttpRoot() + sharePath;

            log.debug("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
        } catch (IOException e) {
            log.error("createEkHtml failed,", e);
            sharePath = null;
        }

        return sharePath;

    }
}

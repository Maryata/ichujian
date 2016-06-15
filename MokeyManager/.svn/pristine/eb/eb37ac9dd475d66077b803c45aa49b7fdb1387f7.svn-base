<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="org.slf4j.Logger"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="com.org.mokey.common.util.file.FileServices" %>
<%@ page import="com.org.mokey.util.Cn2Spell" %>
<%-- <%@ page import="org.json.simple.*" %> --%>
<%

/**
 * KindEditor JSP
 * 
 * 本JSP程序是演示程序，建议不要直接在实际项目中使用。
 * 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
 * 
 */
Logger logger = LoggerFactory.getLogger(OSSObjectSample.class);
logger.error("upload_json.jsp>>>>>");

//定义允许上传的文件扩展名
HashMap<String, String> extMap = new HashMap<String, String>();
extMap.put("image", "gif,jpg,jpeg,png,bmp");
extMap.put("flash", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

//最大文件大小
long maxSize = 1000000;

session.setAttribute("ajax_upload", 1);
response.setContentType("text/html; charset=UTF-8");
if(!ServletFileUpload.isMultipartContent(request)){
	System.out.println("111111122");
	out.println(getError("请选择文件。"));
	return;
}

String dirName = request.getParameter("dir");
if (dirName == null) {
	dirName = "image";
}
if(!extMap.containsKey(dirName)){
	out.println(getError("目录名不正确。"));
	return;
}
SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
String ymd = sdf.format(new Date());

FileItemFactory factory = new DiskFileItemFactory();
ServletFileUpload upload = new ServletFileUpload(factory);
upload.setHeaderEncoding("UTF-8");
List items = upload.parseRequest(request);
Iterator itr = items.iterator();
while (itr.hasNext()) {
	FileItem item = (FileItem) itr.next();
	String fileName = item.getName();
	long fileSize = item.getSize();
	if (!item.isFormField()) {
		//检查文件大小
		if(item.getSize() > maxSize){
			out.println(getError("上传文件大小超过限制。"));
			return;
		}
		//检查扩展名
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
			out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
			return;
		}
		
		String httpPath = "";
		try{
			String timestamp = "_" + System.currentTimeMillis();
			String name = timeSuffix(timestamp, fileName);
			httpPath = FileServlices.saveFile(item,"webView/"+appType+"logourl/"+Cn2Spell.converterToSpellTrim(name));
		}catch(Exception e){
			e.printStackTrace();
			logger.error("上传文件异常："+e.getMessage());
			out.println(getError("上传文件失败。"));
			return;
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
// 		obj.put("url", saveUrl + newFileName3);
		obj.put("url", httpPath);
		out.println(obj.toJSONString());
	}
}
%>
<%!
// 文件名后添加时间戳后缀
private String timeSuffix(String timestamp, String name){
	String s1 = name.substring(0,name.lastIndexOf("."));
	String s2 = name.substring(s1.length());
	name = s1 + timestamp + s2;
	return name;
}
%>
<%!
private String getError(String message) {
	JSONObject obj = new JSONObject();
	obj.put("error", 1);
	obj.put("message", message);
	return obj.toJSONString();
}
%>
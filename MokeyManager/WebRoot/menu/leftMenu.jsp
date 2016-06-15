<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@page import="com.org.mokey.common.*"%>
<%
Map<String,List> modelMenu = (Map<String,List>)AbstractAction.getSessionKey(AbstractAction.AP_SYS_SESSION_MENU_MODEL);
String pid = request.getParameter("pid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css">
body {
	margin: 0px;
	padding: 0px;
	text-align:center;	
	font-size: 12px;
	line-height: 100%;
}
.a1 {
	text-decoration:none; color:#FFFFFF; padding:0 5px;
}
.a2 {
	text-decoration:none; color:#000; padding:0 5px;
}

ul {
	margin: 0px;
	padding: 0px;
	list-style-type: none;
}

li {
	display: block;
	background:#f3f3f3;
	width: 153px;
	line-height: 32px;
	font-size: 11px;
	vertical-align:middle;
	padding-left: 0px;
	border:1px dotted #ccc;
}

.menu {
	width: 153px;
	line-height: 27px;
	font-size: 13px;
	color:#FFFFFF;
	vertical-align:middle;
	padding-left: 0px;
	padding-top: 0px;
	cursor:pointer;
	background-image:url(../image/1_02.png);
}

</style>
<script type="text/javascript">
	function menuSwitch(divId) {
		var ob = document.getElementById(divId + "_ul");
		if(!ob){
			return;
		}
		if (!ob.style.display || ob.style.display == ""
				|| ob.style.display == "block") {
			ob.style.display = "none";
		} else {
			ob.style.display = "block";
		}
	}
</script>
</head>

<body >
<div class="left_div" >
<%
if(pid!=null && !"".equals(pid)){
List menuList = modelMenu.get(pid);  //二级菜单
if(menuList !=null && menuList.size()>0){

for(int i=0;i<menuList.size();i++){
	Map menuMap = (Map)menuList.get(i);
%>
<% if("#".equals(menuMap.get("C_URL").toString())){ %> 
<div class="menu" id="model_<%=menuMap.get("C_ID")%>" onclick="menuSwitch('<%=menuMap.get("C_ID")%>')"><%=menuMap.get("C_NAME") %></div>
<% }else{%>
<div class="menu" id="model_<%=menuMap.get("C_ID")%>" onclick="menuSwitch('<%=menuMap.get("C_ID")%>')"><a class="a1" href="<%=request.getContextPath()+menuMap.get("C_URL")%>" target="mainFrame"><%=menuMap.get("C_NAME") %></a></div>
<% }%>
<ul id="<%=menuMap.get("C_ID")%>_ul" style="display:none">
      <% 
      List ismenulist=modelMenu.get(menuMap.get("C_ID"));  //三级菜单
      if(ismenulist!=null&&ismenulist.size()>0){
         for(int j=0;j<ismenulist.size();j++){
         Map ismenuMap=(Map)ismenulist.get(j);   //总数据中获取3级菜单
      %>       
          <li><a class="a2" href="<%=request.getContextPath()+ismenuMap.get("C_URL") %>" target="mainFrame"><%=ismenuMap.get("C_NAME") %></a></li> 
<%}}%>
</ul>
<%}}} %> 	
</div>
	<div>
     <a href="<%=request.getContextPath()%>/anaylse/brandUserGrowth!getUserGrowthList.action" target="mainFrame">按品牌统计日用户增长</>
	</div>
</body>
</html>
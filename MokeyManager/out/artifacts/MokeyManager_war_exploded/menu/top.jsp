<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@page import="com.org.mokey.common.*"%>
<%@page import="com.org.mokey.system.entiy.TSysUser"%>
<%
TSysUser user = AbstractAction.getSessionLoginUser();
if(user==null){
	user = new TSysUser();
}
List rootMenu = (List)AbstractAction.getSessionKey(AbstractAction.AP_SYS_SESSION_MENU_ROOT);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>mokey后台管理系統</title>
<style>
html,body {
	font: normal 12px verdana;
	margin: 0;
	padding: 0px;
	border: 0 none;
	overflow: hidden;
	line-height: 150%;
	background-color: #F6F6F6;
	scrollbar-face-color:#EEF2FD;
	scrollbar-highlight-color: #EEF2FD;
	scrollbar-shadow-color: #EEF2FD;
	scrollbar-3dlight-color:#2F6068;
	scrollbar-arrow-color:#2F6068;
	scrollbar-track-color:#EEF2FD;
	scrollbar-darkshadow-color:#2F6068;
	scrollbar-base-color:#EEF2FD; 
}
.navright a:hover{
	color:#00FF00;
}
table {
	font-size: 12px;
	line-height: 150%;
	star:expression(this.align='center');
	padding:0px;
}
.image {
	vertical-align:absmiddle;
	text-align:absmiddle;
	cursor:hand;
}
.white {
	color: #FFFFFF;
}
.green {
	font-weight: bold;
	color: #00FF00;
}
</style>
<script type="text/javascript">
//点击一个功能菜单，则将这个功能菜单文字设为红色，其它设为黑色
function changeColor(obj){
  for(i=0;i<document.all.Item_bg.length;i++){
    document.all.Item_bg[i].style.foreground ='#FFFFFF';
  }
  obj.style.foreground ='#000000';
}
//点击菜单进入相应的子菜单
function changeBg(bg)
{   
    oBj=document.all.Item_bg;
    for(i=0;i<oBj.length;i++){
       oBj[i].style.backgroundImage="url(<bt:root/>/images/bq02.jpg)";
    oBj[i].style.color="#000000";
    }
    bg.style.backgroundImage="url(<bt:root/>/images/bq01.jpg)";
    bg.style.color="#FFFFFF";
    //parent.window.frames["mainFrame"].location.href="<bt:root/>/menu/blank.jsp";
}
function changeMain()
{   
    //parent.window.frames["mainFrame"].location.href="<bt:root/>/menu/blank.jsp";
}

function getToday()
{
	var sdate=new Date();
	tdDate.innerHTML="今天是"+sdate.getFullYear()+"年"+(parseInt(sdate.getMonth())+1)+"月"+sdate.getDate()+"日";
}
function out(){
	if(window.confirm("是否退出？")){
		window.parent.location.href='<%=request.getContextPath()%>/login!logOut.action';
	}
}
</script>
</head>

<body onLoad="getToday();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="400"><img src="../image/top_left.jpg" width="400" height="69" /></td>
    <td ><table width="100%" border="0" cellpadding="0" cellspacing="0" background="../image/top_bg.jpg" class="white">
      <tr>
        <td width="39%"><img src="../image/top_lefttop.jpg" width="142" height="42" /></td>
        <td width="61%" align="right"><span id="tdDate"></span>&nbsp;&nbsp;&nbsp;登录用户：<span class="green"><%=user.getUserName() %></span>&nbsp;&nbsp;&nbsp;&nbsp;</td>
      </tr>
    </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0" background="../image/top_bombg.jpg" class="navright">
        <tr>
         <td height="27" align="right" valign="middle" >
         <%
         Map menu = null;
         for(int i=0;i<rootMenu.size();i++){
        	 menu = (Map)rootMenu.get(i);
         %>
         <a id="menu_<%=i %>" href="../menu/leftMenu.jsp?pid=<%=menu.get("C_ID") %>" target="leftFrame" style="text-decoration:none; color:#000; padding:0 5px;"><%=menu.get("C_NAME") %></a>
         <%} %>
         <script type="text/javascript">
         	//加载的第一个菜单
	         var menu0 = document.getElementById("menu_0")
	         if(menu0){
	        	 menu0.click();
	         }
         </script>
         <!-- 
        <a href="../menu/basedata.jsp" target="leftFrame" style="text-decoration:none; color:#000; padding:0 5px;">基础信息</a>
        <a href="../menu/sysInfo.jsp" target="leftFrame" style="text-decoration:none; color:#000; padding:0 5px;">系统信息</a>
        <a href="../menu/analyse.jsp" target="leftFrame" style="text-decoration:none; color:#000; padding:0 5px;">运营分析</a>
        <a href="../menu/report.jsp" target="leftFrame" style="text-decoration:none; color:#000; padding:0 5px;">报表管理</a>
        <a href="../menu/systemManager.jsp" target="leftFrame" style="text-decoration:none; color:#000; padding:0 5px;">系统管理xxx</a>
        -->
        <a href="javascript:out()" style="text-decoration:none; color:#000; padding:0 5px;">退出</a>
         </td>
         <td width='5%'></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
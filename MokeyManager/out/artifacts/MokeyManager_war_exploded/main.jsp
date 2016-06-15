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
<!DOCTYPE HTML>
<html>
 <head>
  <title>管理系统</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="icon" href="image/favicon.ico" type="image/x-icon"/>
	<%--<link rel="shortcut icon" href="image/favicon.ico" type="image/x-icon"/>
   --%>
   <link rel="stylesheet" href="css/iconfont.css">
   <link href="assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
   <link href="assets/css/bui-min.css" rel="stylesheet" type="text/css" />
   <link href="assets/css/main.css" rel="stylesheet" type="text/css" />
   
   <style>
.navright a:hover{
	color:#00FF00;
}

.white {
	color: #FFFFFF;
}
.green {
	font-weight: bold;
	color: #00FF00;
}
table td{word-break: keep-all;white-space:nowrap;}
</style>
<script type="text/javascript">
function getToday()
{
	var sdate=new Date();
	tdDate.innerHTML=sdate.getFullYear()+"年"+(parseInt(sdate.getMonth())+1)+"月"+sdate.getDate()+"日";
}
function out(){
	if(window.confirm("是否退出？")){
		window.parent.location.href='<%=request.getContextPath()%>/login!logOut.action';
	}
}
var GLOB_USER = {
	userName:'<%=user.getUserName() %>'
};
</script>
 </head>
<body onLoad="getToday();">
 


<!--<table id="main_top_table" width="100%" height="42" border="0" cellspacing="0" cellpadding="0" background="image/top_bg.jpg">
  <tr>
    <td width="553"><img src="image/main_top.png" width="553" height="42" /></td>
    <td width="100%" align="right">
   		<div class="white"><i class="icon iconfont icon-tuichu f_20"></i><span id="tdDate"></span> &nbsp;&nbsp;&nbsp;&nbsp;欢迎您，<span  class="green"> </span>
        <a href="javascript:out()" title="退出系统" class="dl-log-quit">[退出]</a> &nbsp;&nbsp;&nbsp;&nbsp;
    </div>
    	
     </td>
  </tr>
</table>-->
<div class="header2 c_fff">
  <img class="m_l30 f_l m_t-10" src="images/logo_cj.png"/>
  <a class=" f_r m_r30 c_fff relative"  href="javascript:out()"><span class="signout_btn  iblock c_fff absolute" ><i class="icon iconfont icon-tuichu f_18 m_l-5"></i></span><span class=" m_l40 f_14">退出</span></a>
  <span class=" f_r m_r30 c_fff relative"><span class="time_btn  iblock c_fff absolute" ><i class="icon iconfont icon-shijian f_18 m_l-5"></i></span><span class=" m_l40 f_14"  id="tdDate"></span></span>
  <a class=" f_r m_r30 c_fff relative"><span class="admin_btn  iblock c_fff absolute"><i class="icon iconfont icon-account f_18 m_l-5"></i></span><span class=" m_l40 f_14"><%=user.getUserName() %></span></a>  
</div>

   <div class="content">
    <div class="dl-main-nav">
      <ul id="J_Nav"  class="nav-list ks-clear">
      	 <%
      	 String [] cssInner = new String []{"nav-home","nav-storage","nav-order","nav-inventory","nav-supplier","nav-marketing"};
		 String [] icoInner = new String []{"<i class='icon iconfont icon-fenxibaobiao f_18 z_ico_m'></i>","<i class='icon iconfont icon-jichuxinxi f_18 z_ico_m'></i>","<i class='icon iconfont icon-xiaoshoubaobiao f_18 z_ico_m'></i>","<i class='icon iconfont icon-biaoge f_18 z_ico_m'></i>","<i class='icon iconfont icon-xitongshezhi f_20 z_ico_m'></i>","<i class='icon iconfont icon-yonghuming f_20 z_ico_m'></i>"};
         Map menu = null;
         if(rootMenu!=null && rootMenu.size()>0){
         for(int i=0;i<rootMenu.size();i++){
        	 menu = (Map)rootMenu.get(i);
        	 String cssInn = cssInner[i];
			 String icoInn = icoInner[i];
         %>
         <li class="nav-item"><div class="nav-item-inner <%=cssInn%>"><%=icoInn%><%=menu.get("C_NAME") %></div></li>
         <%}} %>
         <%--
        <li class="nav-item dl-selected"><div class="nav-item-inner nav-storage">运营分析</div></li>
        <li class="nav-item"><div class="nav-item-inner nav-inventory">基础信息</div></li>
         --%>
      </ul>
    </div>
    
    <ul id="J_NavContent" class="dl-tab-conten">
	 <%--
	 <div align="center">
	  <p><img src="image/welcome1.jpg" width="842" height="478" /></p>
	</div>
    --%>
    </ul>
      
   </div>
  <script type="text/javascript" src="assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="assets/js/bui-min.js"></script>
  <script type="text/javascript" src="assets/js/config-min.js"></script>
  <script>
     BUI.use('common/main',function(){
     	$.getJSON('<%=request.getContextPath()%>/login!getRoleMenu.action',function(config){

		    new PageUtil.MainPage({
		        modulesConfig : config.menuConfig
		    });
	 
	  });
    });
 
  </script>
 </body>
</html>
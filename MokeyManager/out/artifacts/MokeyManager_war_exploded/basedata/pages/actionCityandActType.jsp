<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib  prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>mokey后台管理系統-->系统信息-专题维护</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	
	<s:include value="../../pages/ExtCommon.jsp"></s:include>
	<script type="text/javascript" src="../js/actionCityandActType.js"></script> 
	<script type="text/javascript"> 
	var activityFollowScheme=null;
	Ext.onReady(function(){
		activityFollowScheme =new Ext.activityFollowScheme();
		activityFollowScheme.load();
	});
	</script>
	
  </head>
  
  <body> 
  	<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  		<tr>
    		<td><img src="../../image/dian.jpg" width="9" height="9" /> 系统信息 -&gt; 专题维护 </td>
 		</tr>
	</table>
	<div id="mainDivId" >
		
	</div>
	
  </body>
</html>

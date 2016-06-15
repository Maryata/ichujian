<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    <title>mokey后台管理系統-->系统信息-行业类别管理</title>
	<meta content="text/html" http-equiv="Content-type" charset="utf-8">
	<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
	<s:include value="../../pages/ExtCommon.jsp"></s:include><!-- 包含页 -->
	<script type="text/javascript" src="../js/actionIndustryType.js"></script>
	
	<script type="text/javascript">
		Ext.onReady(function(){
			var industryType = new Ext.industryType();
			industryType.load();
		})
	</script>
	
	<!-- 对页面进行渲染 -->
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../../image/dian.jpg" width="9" height="9" /> 系统信息 -&gt; 行业类别管理  </td>
  </tr>
</table>
<div id="mainDivId" ></div>
  </body>
</html>

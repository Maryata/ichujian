<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.org.mokey.common.*"%>
<%@page import="com.org.mokey.system.entiy.TSysUser"%>
<%
TSysUser user = AbstractAction.getSessionLoginUser();
if(user==null){
	user = new TSysUser();
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>mokey后台管理系統-->基础信息-错误日志管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<s:include value="../../pages/ExtCommon.jsp"></s:include>

<script type="text/javascript" src="../js/logInfo.js"></script>

<script type="text/javascript">
Ext.onReady(function(){
	var logInfo = new Ext.logInfo();
	logInfo.load();
	logInfo.user='<%=user.getUserName()%>';
});
</script>
 
</head>

<body >
<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../../image/dian.jpg" width="9" height="9" /> 基础信息 -&gt; 日志信息  </td>
  </tr>
</table>
<div id="mainDivId" ></div>

</body>
</html>

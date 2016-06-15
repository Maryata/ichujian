<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>系统管理-用户管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<s:include value="../../pages/ExtCommon.jsp"></s:include>

<script type="text/javascript" src="../js/userInfoManager.js"></script>

<script type="text/javascript">
Ext.onReady(function(){
	var userInfoManager = new Ext.userInfoManager();
	userInfoManager.load();
});
</script>
 
</head>

<body >
<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../../image/dian.jpg" width="9" height="9" /> 用户中心 -&gt; 用户管理  </td>
  </tr>
</table>
<div id="mainDivId" ></div>

</body>
</html>

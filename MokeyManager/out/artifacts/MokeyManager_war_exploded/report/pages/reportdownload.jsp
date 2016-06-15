<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>mokey后台管理系統-->广告位管理</title>
<!-- 
<link rel="stylesheet" type="text/css" href="../../css/css.css" />
<link rel="stylesheet" type="text/css" href="../../js/ext-3.4.0/resources/css/ext-all.css" />

<script type="text/javascript" src="../../js/ext-3.4.0/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="../../js/ext-3.4.0/ext-all.js"></script>
<script type="text/javascript" src="../../js/ext-3.4.0/ext-lang-zh_CN.js"></script>

<script type="text/javascript" src="../../js/ext-3.4.0/formValidation.js"></script>
<script type="text/javascript" src="../../js/ext-3.4.0/hotFixForExtjs.js"></script>
<script type="text/javascript" src="../../js/common_ext.js"></script>
 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<s:include value="../../pages/ExtCommon.jsp"></s:include>

<script type="text/javascript" src="../js/reportdownload.js"></script>

<!-- ENDLIBS -->
<script type="text/javascript">
Ext.onReady(function(){
	var reportdownload = new Ext.reportdownload();
	reportdownload.load();
});
</script>
 
</head>

<body >
<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../../image/dian.jpg" width="9" height="9" /> 报表管理 -&gt; 报表管理  </td>
  </tr>
</table>
<div id="mainDivId" ></div>

</body>
</html>

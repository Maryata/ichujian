<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>mokey后台管理系統-->activeCode</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<s:include value="../../pages/ExtCommon.jsp"></s:include>
<script type="text/javascript" src="../../js/qrcode.js"></script>

<script type="text/javascript" src="../js/activeCode.js"></script>
<script type="text/javascript" src="../js/actionActive.js"></script>

<style>
    #qrcode{
        /*text-align: center;*/
        /*display: table-cell;*/
        /*width: 96px;*/
        /*height: 96px;*/
        /*vertical-align:middle;*/
        /*position: relative;*/
    }
</style>


<script type="text/javascript">
Ext.onReady(function(){
	var activeCode = new Ext.activeCode();
	activeCode.load();
});
</script>
 
</head>

<body >
<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../../image/dian.jpg" width="9" height="9" /> 基础信息 -&gt; 激活码管理  </td>
  </tr>
</table>
<div id="mainDivId" >
</div>

</body>
</html>

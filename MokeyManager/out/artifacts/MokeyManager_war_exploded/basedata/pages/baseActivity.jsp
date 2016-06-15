<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>mokey后台管理系統-->系统信息 -活动信息</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<s:include value="../../pages/ExtCommon.jsp"></s:include>

<script type="text/javascript" src="../js/baseActivity.js"></script>

<script type="text/javascript">
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

Ext.onReady(function(){
	var role = getQueryString('role');
	var activityInfo = new Ext.ActivityInfo();
	activityInfo.audit = 'audit'==role ? true : false;
	// 传递session中的当前登录人
	var userName = '${sessionScope.AP_SYS_SESSION_LOGON_USER.userName}';
	activityInfo.userName = userName;
	activityInfo.load();
});
</script>
   
</head>

<body>
<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../../image/dian.jpg" width="9" height="9" /> 系统信息 -&gt; 活动信息  </td>
  </tr>
</table>
<div id="mainDivId" ></div>

</body>
</html>

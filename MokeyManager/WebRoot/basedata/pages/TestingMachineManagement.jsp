<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
	<head>
		<c:set var="basePath"><%=basePath%></c:set>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="${basePath}">
		<title>mokey后台管理系統--&gt测试机管理</title>

		<link rel="stylesheet" type="text/css"
			href="${basePath}/css/reCss.css" />
	</head>
	<body>
		<form name="form1" method="post" action="">
			<table width="98%" height="35" border="0" align="center"
				cellpadding="0" cellspacing="0">
				<tr>
					<td class="tz_crumbs">
						<img src="${basePath}/images/crumbs.jpg" width="9" height="9" />
						系统管理-&gt;测试机管理 -&gt;
					</td>
				</tr>
			</table>


			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="tbg">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="1" cellpadding="2">
							<tr>
								<td width="8%" height="23" class="biaodan-top" align="left">手机名称</td>
								<td width="8%" class="biaodan-q" align="left"><input name="phonename" onkeypress="return TestingMachineManagement.runScript(event);" id="phonename"/></td>
								<td width="8%" class="biaodan-top" align="left">用户名</td>
								<td width="20%" class="biaodan-q" align="left"><input name="username" onkeypress="return TestingMachineManagement.runScript(event);" id="username"/></td>
								<td width="10%" rowspan="2" align="center" class="biaodan-q">
									<input type="button" class="butt" onclick="TestingMachineManagement.query({phonename:document.getElementById('phonename').value,username : document.getElementById('username').value});" value="查询" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

			<table width="98%" height="35" border="0" align="center" cellpadding="1">
				<tr>
					<td align="right" class="jg_fc"></td>
				</tr>
			</table>

			<table id="dt" width="98%" border="0" align="center"
				cellpadding="0" cellspacing="1" class="tbg sortable">
				<tr class="biaodan-top">
					<td width="4%">手机</td>
					<td width="5%">用户名</td>
					<td width="6%">时间</td>
					<td width="8%">联系电话</td>
				</tr>
			</table>
			<div id="page"></div>
			<input type="hidden" value="${basePath}" id="basePath"/>
	</form>
	</body>
	<script type="text/javascript" charset="utf-8" src="${basePath }/js/jquery/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${basePath}/js/laypage/laypage.js"></script>
	<script type="text/javascript" charset="utf-8" src="${basePath }/basedata/js/TestingMachineManagement.js"></script>
	<script type="text/javascript">
		$(function() {
			TestingMachineManagement.init($("#basePath").val(),laypage||{});
		});
	</script>
</html>

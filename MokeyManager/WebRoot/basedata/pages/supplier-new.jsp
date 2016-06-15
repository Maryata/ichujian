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
		<title>mokey后台管理系統--&gt代理商管理</title>

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
						系统管理-&gt;代理商管理 -&gt;
					</td>
				</tr>
			</table>


			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="tbg">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="1" cellpadding="2">
							<tr>
								<td width="8%" height="23" class="biaodan-top" align="left">
									代码
								</td>
								<td width="8%" class="biaodan-q" align="left">
									<input name="code" id="code"/>
								</td>
								<td width="8%" class="biaodan-top" align="left">
									名称
								</td>
								<td width="20%" class="biaodan-q" align="left">
									<input name="name" id="name"/>
								</td>
								<td width="10%" rowspan="2" align="center" class="biaodan-q">
									<input type="button" class="butt" onclick="Supplier.query({c_code:document.getElementById('code').value,c_name : document.getElementById('name').value});" value="查询" />
									<input type="button" class="butt" value="添加" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

			<table width="98%" height="35" border="0" align="center"
				cellpadding="1">
				<tr>
					<td align="right" class="jg_fc">
						
					</td>
				</tr>
			</table>

			<table id="dt" width="98%" border="0" align="center"
				cellpadding="0" cellspacing="1" class="tbg sortable">
				<tr class="biaodan-top">
					<td width="4%">代码</td>
					<td width="5%">名称</td>
					<td width="6%">公司名称</td>
					<td width="8%">电话</td>
					<td width="7%">联系人</td>
					<td width="10%">email</td>
					<td width="10%">地址</td>
					<td width="2%">级别</td>
					<td width="20%">url</td>
					<td>操作</td>
				</tr>
			</table>
			<div id="page"></div>
			<input type="hidden" value="${basePath}" id="basePath"/>
</form>
	</body>
	<script type="text/javascript" charset="utf-8" src="${basePath }/js/jquery/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${basePath}/js/laypage/laypage.js"></script>
	<script type="text/javascript" charset="utf-8" src="${basePath }/basedata/js/Supplier.js"></script>
	<script type="text/javascript">
		$(function() {
			Supplier.init($("#basePath").val(),laypage||{});
		});
	</script>
</html>

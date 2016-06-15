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
		<title>mokey后台管理系統--&gt微用帮管理--&gt应用管理</title>

		<link rel="stylesheet" type="text/css"
			href="${basePath}/css/reCss.css" />
			<style>
			.tanchu_bg { display:none; background:#666; opacity:0.5; filter:alpha(opacity=50); width:100%; height:100%; position:fixed!important; position:absolute; left:0; top:0;  _top:expression(eval(documentElement.scrollTop+(document.documentElement.clientHeight-this.offsetHeight)/2)); z-index:88;}
			.tanchu{width:800px;height:370px;margin-left:-400px!important;margin-top:-180px!important;display:none;background:#fff; overflow:hidden;border:1px solid #aaa;left:50%;top:50%;margin-top:0;position:fixed!important;position:absolute;_top:expression(eval(documentElement.scrollTop+(document.documentElement.clientHeight-this.offsetHeight)/2)); z-index:99; position:20px;}
			.tanchu .tanchu-box{padding:15px;color:#666;}
			.tanchu-title{padding:10px 15px; background:#f3f3f3; color:#666; font-weight:bold;cursor: default;}
			.tanchu_close{  color:#999; font-weight:100; display:inline-block; float:right; cursor:pointer;}
			</style>
	</head>
	<body>
		<form name="form1" method="post" action="">
			<table width="98%" height="35" border="0" align="center"
				cellpadding="0" cellspacing="0">
				<tr>
					<td class="tz_crumbs">
						<img src="${basePath}/images/crumbs.jpg" width="9" height="9" />
						系统管理-&gt;微用帮管理 -&gt应用管理;
					</td>
				</tr>
			</table>

			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="tbg">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="1" cellpadding="2">
							<tr>
								<td width="8%" height="23" class="biaodan-top" align="left">应用名称</td>
								<td width="8%" class="biaodan-q" align="left"><input name="name" onkeypress="return McrApp.runScript(event);" id="name"/></td>
								<td width="10%" rowspan="2" align="center" class="biaodan-q">
									<input type="button" class="butt" onclick="McrApp.query({c_name:document.getElementById('name').value});" value="查询" />
									<input type="button" class="butt" onclick="McrApp.openEdit();" value="添加" />
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
					<td width="4%">ID</td>
					<td width="20%">logo</td>
					<td width="10%">应用名称</td>
					<td width="6%">操作</td>
				</tr>
			</table>
			<div id="page"></div>
			<input type="hidden" value="${basePath}" id="basePath"/>
	</form>

	  <div class="tanchu" style="display:none;">
	    <div class="tanchu-title">应用编辑<span title="关闭" class="tanchu_close">关闭</span></div>
	    <div class="tanchu-box" style="height:270px;overflow: auto;">
	    	<form id="editForm" enctype="multipart/form-data">
	    	<input type="hidden" name="C_ID"/>
	    	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
			  <tr>
			    <td>  
			    <table width="100%"  border="0" cellspacing="1" cellpadding="0">
			      <tr>
			        <td class="biaodan-top">应用名称：</td>
			        <td class="biaodan-q"><input name="C_NAME"/></td>
			      </tr>
			      <tr>
			        <td width="17%" height="23" class="biaodan-top">logo：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_LOGOURL" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_logourl"></span></td>
			      </tr>
			      <tr>
			        <td width="17%" height="23" class="biaodan-top">app地址：</td>
			        <td colspan="3" width="33%" class="biaodan-q"><input type='url' name="C_APPURL" /></td>
			      </tr>
			      <tr>
			        <td width="17%" height="23" class="biaodan-top">应用简介：</td>
			        <td colspan="3" width="33%" class="biaodan-q"><input name="C_ABSTRACT" /></td>
			      </tr>
			      <tr>
			        <td width="17%" height="23" class="biaodan-top">默认收藏数：</td>
			        <td colspan="3" width="33%" class="biaodan-q"><input type='number' name="C_NUMBER_OF_FAVORITES" /></td>
			      </tr>
			      <tr>
			        <td width="17%" height="23" class="biaodan-top">应用分类：</td>
			        <td colspan="3" width="33%" class="biaodan-q"><select id='s_categories' name='C_CATEGORY'><option value=''></option></select></td>
			      </tr>
			    </table>
			    </td>
			  </tr>
			</table>
			<table width="100%" height="50" border="0" cellpadding="0" cellspacing="0">
			  <tr>
			    <td align="center">
			       <input type="button" class="butt" value="保存" onclick="McrApp.save(this);" />
			       <input type="button" class="butt" value="取消" onclick="McrApp.closeEdit();" />
			    </td>
			  </tr>
			</table>
			</form>
	    </div>
	  </div>
	  <div class="tanchu_bg" ></div>
		<div id="div_ajax"></div>
	</body>
	<script type="text/javascript" charset="utf-8" src="${basePath }/js/jquery/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${basePath }/js/jquery/jquery.validate.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${basePath }/basedata/js/additional-methods.js"></script>
	<script type="text/javascript" src="${basePath}/js/laypage/laypage.js"></script>
	<script type="text/javascript" charset="utf-8" src="${basePath }/basedata/js/spin.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${basePath }/basedata/js/McrApp.js"></script>
	<script type="text/javascript" charset="UTF-8">
		$(function() {
			McrApp.init($('#basePath').val(),laypage||{},Spinner||{});
		});
	</script>
</html>

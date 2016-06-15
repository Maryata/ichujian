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
								<td width="8%" height="23" class="biaodan-top" align="left">品牌代码</td>
								<td width="8%" class="biaodan-q" align="left"><input name="code" onkeypress="return Supplier.runScript(event);" id="code"/></td>
								<td width="8%" class="biaodan-top" align="left">品牌名称</td>
								<td width="20%" class="biaodan-q" align="left"><input name="name" onkeypress="return Supplier.runScript(event);" id="name"/></td>
								<td width="10%" rowspan="2" align="center" class="biaodan-q">
									<input type="button" class="butt" onclick="Supplier.query({c_code:document.getElementById('code').value,c_name : document.getElementById('name').value});" value="查询" />
									<input type="button" class="butt" onclick="Supplier.openEdit();" value="添加" />
									<input type="button" class="butt" onclick="Supplier.processApk(this);" value="apk批处理" />
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
					<td width="4%">品牌代码</td>
					<td width="5%">品牌名称</td>
					<td width="6%">公司名称</td>
					<td width="8%">电话</td>
					<td width="7%">联系人</td>
					<td width="10%">email</td>
					<td width="10%">地址</td>
					<%--<td width="2%">级别</td>
					--%><td width="20%">一键购膜url</td>
					<td>操作</td>
				</tr>
			</table>
			<div id="page"></div>
			<input type="hidden" value="${basePath}" id="basePath"/>
	</form>

	  <div class="tanchu" style="display:none;">
	    <div class="tanchu-title">代理商编辑<span title="关闭" class="tanchu_close">关闭</span></div>
	    <div class="tanchu-box" style="height:270px;overflow: auto;">
	    	<form id="editForm" enctype="multipart/form-data">
	    	<input type="hidden" name="C_ID"/>
	    	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
			  <tr>
			    <td>  
			    <table width="100%"  border="0" cellspacing="1" cellpadding="0">
			      <tr>
			        <td class="biaodan-top">品牌代码：</td>
			        <td class="biaodan-q"><input name="C_SUPPLIER_CODE"/></td>
			        <td width="17%" height="23" class="biaodan-top">是否定制：</td>
			        <td width="33%" class="biaodan-q"><select id="s_c_islive" name="C_ISLIVE"><option value="1">是</option><option value="0">否</option></select></td>
			      </tr>
			      <tr>
					  <td class="biaodan-top">品牌名称：</td>
					  <td class="biaodan-q"><input name="C_SUPPLIER_NAME" /></td>
			        <td width="17%" height="23" class="biaodan-top">是否免费换膜</td>
			        <td colspan="3" width="33%" class="biaodan-q"><select id="s_c_isExchange" name="C_ISEXCHANGE"><option value="1">是</option><option value="0">否</option></select></td>
			      </tr>
			      <tr>
			        <td class="biaodan-top">公司名称：</td>
			        <td class="biaodan-q"><input name="C_COMPANY" /></td>
			         <td width="17%" height="23" class="biaodan-top">是否潜在客户：</td>
			        <td width="33%" class="biaodan-q"><select id="s_c_is_potential_demand" name="C_IS_POTENTIAL_DEMAND"><option value="1">是</option><option value="0">否</option></select></td>
			      </tr>
			      <tr>
			        <td width="17%" height="23" class="biaodan-top">电话：</td>
			        <td colspan="3" width="33%" class="biaodan-q"><input name="C_PHONE" /></td>
			      </tr>
			      <tr>
			        <td width="17%" height="23" class="biaodan-top">联系人：</td>
			        <td colspan="3" width="33%" class="biaodan-q"><input name="C_CONTACTS" /></td>
			      </tr>
			      <tr>
			        <td width="17%" height="23" class="biaodan-top">email：</td>
			        <td colspan="3" width="33%" class="biaodan-q"><input type="email" name="C_EMAIL" /></td>
			      </tr>
			      <tr>
			        <td width="17%" height="23" class="biaodan-top">地址：</td>
			        <td colspan="3" width="33%" class="biaodan-q"><input name="C_LOCATION" /></td>
			      </tr>
			      <tr>
			        <td width="17%" height="23" class="biaodan-top">一键购膜url：</td>
			        <td width="33%" class="biaodan-q"><input type="text" name="C_URL" /></td>
			         <td width="17%" height="23" class="biaodan-top">类型：</td>
			        <td width="33%" class="biaodan-q"><select id='s_c_type' name="C_TYPE"><option value="1">浏览器</option><option value="0">webview</option></select></td>
			      </tr>
			      <tr>
			      	<td width="17%" height="23" class="biaodan-top">启动图标：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_LOGO_URI" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_logo_uri"></span></td>
			      </tr>
			      <tr>
			      	<td width="17%" height="23" class="biaodan-top">导航栏颜色：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_COLOR" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_color"></span></td>
			      </tr>
			      <tr>
			      	<td width="17%" height="23" class="biaodan-top">欢迎页面LOGO：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_ABOUT_LOGO_URI" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_about_logo_uri"></span></td>
			      </tr>
			      <tr>
			      	<td width="17%" height="23" class="biaodan-top">欢迎页面slogan：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_MAIN_COMMON_SLOGAN" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_main_common_slogan"></span></td>
			      </tr>
			      <tr>
			      	<td width="17%" height="23" class="biaodan-top">导航栏LOGO：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_MAIN_MAIN_LOGO_URI" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_main_main_logo_uri"></span></td>
			      </tr>
			      <tr>
			      	<td width="17%" height="23" class="biaodan-top">关于信息：</td>
			        <td colspan="3" width="33%" class="biaodan-q"><input name="C_ABOUT_INFO" /></td>
			      </tr>
			      <tr>
			      	<td width="17%" height="23" class="biaodan-top">一键购膜名称：</td>
			        <td colspan="3" width="33%" class="biaodan-q"><input name="C_MAIN_MAIN_BUY" /></td>
			      </tr>
			      <tr>
			      	<td width="17%" height="23" class="biaodan-top">一键购膜图片：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_SHOPPING_URI" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_shopping_uri"></span></td>
			      </tr>
			      <%--
			      <tr>
			      	<td width="17%" height="23" class="biaodan-top">版权信息：</td>
			        <td colspan="3" width="33%" class="biaodan-q"><input name="C_COMMON_COPYRIGHT" /></td>
			      </tr>
			      --%>
			      <tr>
			      	<td width="17%" height="23" class="biaodan-top">帮助和反馈：</td>
			        <td colspan="3" width="33%" class="biaodan-q"><input name="C_HELPANDFEEDBACK" /></td>
			      </tr>
			      <tr>
			      	<td width="17%" height="23" class="biaodan-top">网站地址：</td>
			        <td width="33%" class="biaodan-q"><input type="url" name="C_WEBSITE" /></td>
			        <td width="17%" height="23" class="biaodan-top">是否显示：</td>
			        <td width="33%" class="biaodan-q"><select id="s_c_host_website_wether_show" name="C_HOST_WEBSITE_WETHER_SHOW"><option value="1">是</option><option value="0">否</option></select></td>
			      </tr>
			       <tr>
			      	<td width="17%" height="23" class="biaodan-top">启动页背景：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_BACKGROUND_LANCH" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_background_lanch"></span></td>
			      </tr>
			       <tr>
			      	<td width="17%" height="23" class="biaodan-top">首页背景：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_BACKGROUND_HOME" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_background_home"></span></td>
			      </tr>
			       <tr>
			      	<td width="17%" height="23" class="biaodan-top">小米V4\V5系统悬浮窗设置：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_FLOATWINDOW_MIUIV4V5" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_floatwindow_miuiv4v5"></span></td>
			      </tr>
			       <tr>
			      	<td width="17%" height="23" class="biaodan-top">小米V6系统悬浮窗设置：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_FLOATWINDOW_MIUIV6" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_floatwindow_miuiv6"></span></td>
			      </tr>
			       <tr>
			      	<td width="17%" height="23" class="biaodan-top">华为悬浮窗设置：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_FLOATWINDOW_EMUI3" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_floatwindow_emui3"></span></td>
			      </tr>
			      <tr>
			      	<td width="17%" height="23" class="biaodan-top">华为悬浮窗设置：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_FLOATWINDOW_EMUI3" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_floatwindow_emui3"></span></td>
			      </tr>
			      <tr>
			      	<td width="17%" height="23" class="biaodan-top">华为悬浮窗设置引导：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_HUAWEI_EMUI" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_huawei_emui"></span></td>
			      </tr>
			      <tr>
			      	<td width="17%" height="23" class="biaodan-top">辅助功能引导图片：</td>
			        <td width="33%" class="biaodan-q"><input type="file" name="C_AID_ONE" /></td>
			        <td class="biaodan-q" colspan="2"><span id="span_aid_one"></span></td>
			      </tr>
			    </table>
			    </td>
			  </tr>
			</table>
			<table width="100%" height="50" border="0" cellpadding="0" cellspacing="0">
			  <tr>
			    <td align="center">
			       <input type="button" class="butt" value="保存" onclick="Supplier.save(this);" />
			       <input type="button" class="butt" value="取消" onclick="Supplier.closeEdit();" />
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
	<script type="text/javascript" charset="utf-8" src="${basePath }/basedata/js/Supplier.js"></script>
	<script type="text/javascript">
		$(function() {
			Supplier.init($("#basePath").val(),laypage||{},Spinner||{});
		});
	</script>
</html>

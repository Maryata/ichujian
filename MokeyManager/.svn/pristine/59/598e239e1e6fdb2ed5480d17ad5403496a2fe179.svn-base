<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,user-scalable=no">
	<meta name="screen-orientation" content="portrait">
	<meta name="x5-orientation" content="portrait">
	<meta http-equiv="Expires" content="0"><meta http-equiv="Cache-Control" content="no-cache"><meta http-equiv="Cache-Control" content="no-store">
	<title>特殊任务修改</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ekpages/js/jquery.searchableSelect.css" />
	<script type="text/javascript">rootPath ="${pageContext.request.contextPath}";</script>
	<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js" type="text/javascript"></script>
	<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.selectseach.min.js"></script>--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ekpages/js/jquery.searchableSelect.js"></script>
	<style type="text/css">
		.file-btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
		.file-file{ position:absolute; top:-4px; right:74px; height:24px; filter:alpha(opacity:0);opacity: 0;width:70px; }
		.file-box {position: relative;width: 340px}
	</style>
	<script type="text/javascript">
		function formValid(){

			$("#form1").attr("action","${pageContext.request.contextPath }/basedata/ekey/eKShopTaskInfoAction!updateInfo.action");
		}

		//app应用改变事件
		$(document).ready(function(){
			//$("select").selectseach();
			$('select').searchableSelect();
		});
	</script>
</head>

<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 
    	系统信息 -&gt; e键:商城 -&gt;特殊任务管理-&gt; 编辑特殊任务
   	</td>
  </tr>
</table>
<form method="post" id="form1">
<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	<input type="hidden" id="cid" name="cid" value="<s:property value="#request.cid"/>" >
	<input type="hidden" id="tid" name="tid" value="<s:property value="#request.tid"/>" >
	<br/>
	<tr>
		<td width="5%"  class="biaodan-top" align="left">特殊任务名称：</td>
		<td width="15%" class="biaodan-q" align="left">
			<input type="text" id="name" name="name" value="<s:property value="#request.name"/>"/>
		</td>
		<s:if test="#request.tid == '18'">
			<td width="5%" class="biaodan-top" align="left">e键首页任务</td>
			<td width="15%" class="biaodan-q" align="left">
				<s:select list="#request.appList" name="gid" id="gid" listKey="C_ID" value="#request.gid"
						  listValue="C_NAME" cssStyle="width:100%;border:none;hight:100%;" m="search"
				/>
			</td>
		</s:if>
		<s:elseif test="#request.tid == '19'">
			<td width="5%"  class="biaodan-top" align="left">活动任务</td>
			<td width="15%" class="biaodan-q" align="left">
				<s:select list="#request.actList" name="gid" id="gid" listKey="C_ID" value="#request.gid"
						  listValue="C_NAME" cssStyle="width:100%;border:none;hight:100%;"  m="search"
				/>
			</td>
		</s:elseif>

		<s:elseif test="#request.tid == '24'">
			<td width="5%"  class="biaodan-top" align="left">指定按键设置</td>
			<td width="15%" class="biaodan-q" align="left">
				<s:select list="#request.settingList" name="gid" id="gid" listKey="C_ID" value="#request.gid"
						  listValue="C_NAME" cssStyle="width:100%;border:none;hight:100%;" m="search"
				/>
			</td>
		</s:elseif>

		<s:else>
			<td width="5%"  class="biaodan-top" align="left">游戏任务</td>
			<td width="15%" class="biaodan-q" align="left">
				<s:select list="#request.gameList" name="gid" id="gid" listKey="C_ID" value="#request.gid"
						  listValue="C_NAME" cssStyle="width:100%;border:none;hight:100%;" m="search"
				/>
			</td>
		</s:else>
	</tr>

	<tr>
		<td width="5%"  class="biaodan-top" align="left">开始时间：</td>
		<td width="15%" class="biaodan-q" align="left">
			<input type="text" style="border:none; width:100%; height:100%" id="sdate" name="sdate" class="Wdate"
				   onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'edate\')}'})" value="<s:property value='#request.sdate'/>"/>
		</td>
		<td width="5%"  class="biaodan-top" align="left">结束时间：</td>
		<td width="18%" class="biaodan-q" align="left">
			<input type="text" style="border:none; width:100%; height:100%" id="edate" name="edate" class="Wdate"
				   onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'sdate\')}'})" value="<s:property value='#request.edate'/>"/>
		</td>
	</tr>
	<tr>
	    <td class="biaodan-q" align="center" colspan="5">
		<button class="butt" onclick="return formValid();">保 存</button>
 	</td>
  	</tr>
</table>
</form>
</body>
</html>


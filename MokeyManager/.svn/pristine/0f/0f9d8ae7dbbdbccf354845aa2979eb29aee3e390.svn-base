<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title>任务维护</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
	<script type="text/javascript">rootPath ="${pageContext.request.contextPath}";</script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.selectseach.min.js"></script>
<style type="text/css">
.file-btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
.file-file{ position:absolute; top:-4px; right:74px; height:24px; filter:alpha(opacity:0);opacity: 0;width:70px; }
.file-box {position: relative;width: 340px}
</style>
<script type="text/javascript">
function formValid(){
	var _flag = true;
	if(_flag){
		if($("#addFlag").val()!="1"){
			$("#form1").attr("action","${pageContext.request.contextPath }/basedata/ekey/eKRewardAction!updateReward.action");
		}
	}
	return _flag;
}

</script>
</head>

<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 
    	系统信息 -&gt; e键:商城 -&gt;任务管理-&gt; 编辑分类
   	</td>
  </tr>
</table>
<form method="post" id="form1">
<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	<input type="hidden" id="cid" name="cid" value="<s:property value="#request.cid"/>" >
	<input type="hidden" id="addFlag" name="addFlag" value="<s:property value="#request.addFlag"/>" >
	<br/>
	<tr>
		<td width="5%"  class="biaodan-top" align="left">连续天数：</td>
		<td width="15%" align="center" class="biaodan-q">
			<input type="text" style="border:none; width:100%; height:100%" id="day" name="day" value="${day}"/>
		</td>
		<td width="5%"  class="biaodan-top" align="left">对应奖励：</td>
		<td align="center" class="biaodan-q">
			<input type="text" style="border:none; width:100%; height:100%" id="score" name="score" value="${score}"/>
		</td>
	</tr>
	<tr>
		<td width="5%"  class="biaodan-top" align="left">类型：</td>
		<td width="15%" align="center" class="biaodan-q" colspan="4">
			<select name="type" id="type" style="border:none; color:#636775; width:100%; height:30px;">
				<option value="1"  <c:if test="${type eq '1'}">selected</c:if>>连续天数</option>
				<option value="2"  <c:if test="${type eq '2'}">selected</c:if>>累计天数</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="biaodan-q" align="center" colspan="4">
			<button class="butt" onclick="return formValid();">保 存</button>
		</td>
	</tr>
</table>
</form>
</body>
</html>


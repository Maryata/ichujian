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
<title>定制APP管理</title>
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
	if($("#aid").val()==""){
		alert("“app应用”不能为空！");
		_flag = false;
		return _flag;
	};
	if(_flag){
			$("#form1").attr("action","${pageContext.request.contextPath }/basedata/ekey/eKIndexSuppIndexAppAction!update.action");
	}
	return _flag;
}

//app应用改变事件
$(document).ready(function(){
	$("select").selectseach();
});
</script>
</head>

<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 
    	系统信息 -&gt; e键:首页 -&gt;定制APP管理-&gt; 编辑
   	</td>
  </tr>
</table>
<form method="post" id="form1">
<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	<input type="hidden" id="cid" name="cid" value="${act.C_ID}" >
	<br/>
  	<tr>
		<td width="8%"  height="23" class="biaodan-top" align="left">APP应用：</td>
		<td width="30%" align="center" class="biaodan-q">
			<select  name="aid" id="aid"  style="width:100%;border:none;hight:100%;" m="search">
				<option></option>
				<c:forEach var="item" items="${appList}" varStatus="status">
					<option data-val="${item.C_IMG}" value="${item.C_ID}" <c:if test="${item.C_ID==act.C_INDEX_AID}">selected</c:if>>${item.C_NAME}</option>
				</c:forEach>
			</select>
		</td>
		<td width="8%"  height="23" class="biaodan-top" align="left">排序：</td>
		<td align="center" class="biaodan-q" colspan="2">
			<input type="text" style="border:none; width:100%; height:100%" id="order" name="order"
				   value="${act.C_ORDER}"/>
		</td>
  	</tr>
	<tr>
		<td width="8%"  height="23" class="biaodan-top" align="left">供应商名称：</td>
		<td width="30%" align="center" class="biaodan-q" >
			<input type="text" style="border:none; width:100%; height:100%" id="suppname" name="suppname" disabled="disabled"
				   value="${act.C_SUP_NAME}"/>
		</td>
		<td width="8%"  height="23" class="biaodan-top" align="left">供应商code：</td>
		<td align="center" class="biaodan-q" colspan="2">
			<input type="text" style="border:none; width:100%; height:100%" id="code" name="code" disabled="disabled"
				   value="${act.C_CODE}"/>
		</td>
	</tr>
	<tr>
		<td width="8%"  height="23" class="biaodan-top" align="left">是否有效：</td>
		<td width="30%" align="center" class="biaodan-q" COLSPAN="4">
			<select name="islive" id="islive" style="width:100%;border:none;hight:100%;margin-left: 0px;">
				<option value="1" <c:if test="${act.C_ISLIVE=='1'}">selected</c:if>>有效</option>
				<option value="0" <c:if test="${act.C_ISLIVE=='0'}">selected</c:if>>无效</option>
			</select>
		</td>
	</tr>
  	<tr>
	    <td class="biaodan-q" align="center" colspan="5">
		<button class="butt" onclick="return formValid();">保 存</button>
 	</td>
  	</tr>
</table>
</form>
<br>
</body>
</html>


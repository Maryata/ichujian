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
<title>新增/编辑手机型号</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript">
function formValid(){
	var _flag = true;
	if($("#name").val()==""){
		alert("名称不能为空！");
		_flag = false;
		return _flag;
	};
	if(_flag){
		var $id = $("#id").val();
		if ($id) {
			$("#form1").attr("action","${pageContext.request.contextPath }/basedata/phoneModelAction!editModel.action");
		} else {
			$("#form1").attr("action","${pageContext.request.contextPath }/basedata/phoneModelAction!addModel.action");
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
    	系统信息 -&gt; 免费换膜 -&gt; 新增/编辑手机型号
   	</td>
  </tr>
</table>
<form method="post" id="form1">
<table width="40%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	<br/>
  	<tr>
		<input type="hidden" name="brandId" id="brandId" value="${brandId}" />
		<input type="hidden" name="id" id="id" value="${id}" />
	   	<td width="30%"  height="23" class="biaodan-top" align="left">型号名称</td>
	    <td align="center" class="biaodan-q">
	    	<input type="text" style="border:none; width:100%; height:100%" id="name" name="name"
				value="${name}"/>
	    </td>
  	</tr>
  	<tr>
	   	<td width="30%"  height="23" class="biaodan-top" align="left">型号编码</td>
	    <td align="center" class="biaodan-q">
	    	<input type="text" style="border:none; width:100%; height:100%" id="code" name="code"
				value="${code}"/>
	    </td>
  	</tr>
  	<tr>
	    <td class="biaodan-q" align="center" colspan="2">
		<button class="butt" onclick="return formValid();">保 存</button>
 	</td>
  	</tr>
</table>
</form>
<br>
</body>
</html>

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
<title>数据权限维护</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<!--2015-4-29新样式，reCss.css样式一定要最后调用-->
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
</head>
<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <!--2015-4-29新样式，面包屑-->
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/image/dian.jpg" width="9" height="9" /> 用户中心 -&gt; 数据权限管理 </td>
  </tr>
</table>

<form id="form1" method="post">
	<input type="hidden" id="editId" name="editId" />
	<input type="hidden" id="editCode" name="editCode" />
	<input type="hidden" id="editName" name="editName" />
<table id="mytable" width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
  <tr class="biaodan-top">
    <td width="35%">CODE</td>
    <td width="35%">名称</td>
    <td width="30%">操作</td>
  </tr>
  	<s:iterator var="sup" value="suppliers">
	<tr class="biaodan-q">
		<td align="center"><s:property value="C_USERCODE"/></td>
		<td align="center"><s:property value="C_USERNAME"/></td>
		<td align="center">
	    <input type="button" value="维 护" class="butt"
			   onclick="handle('<s:property value="C_USERID"/>', '<s:property value="C_USERCODE"/>', '<s:property value="C_USERNAME"/>')">
		</td>
	</tr>
	</s:iterator>
</table>
</form>
<script type="text/javascript">
	// 供应商维护
	function handle(editId,editCode,editName){
		$("#editId").val(editId);
		$("#editCode").val(editCode);
		$("#editName").val(editName);
		$("#form1").attr("action","${pageContext.request.contextPath}/basedata/dataPermissionAction!toHandle.action");
		$("#form1").submit();
	}
</script>
</body>
</html>


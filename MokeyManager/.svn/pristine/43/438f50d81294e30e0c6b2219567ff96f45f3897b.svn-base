<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,user-scalable=no">
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<title>应用合集</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<!--2015-4-29新样式，reCss.css样式一定要最后调用-->
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript">
// 删除
function deleteCol(cid){
    var flag = confirm("您确定要删除？");
    if(flag){
		$.post("${pageContext.request.contextPath}/basedata/mcrAppColAction!mcrAppColDel.action",
				{cid:cid},function(data){
			window.location.reload();
		});
	}
}
// 修改
function modifyCol(cid,cname){
	$("#form2").attr("action","${pageContext.request.contextPath}/basedata/mcrAppColAction!toUpdate.action"); 
	$("#editId").val(cid);
	$("#editName").val(cname);
	$("#form2").submit();
} 
// 合集分类
function handle(cid,cname){
	$("#form2").attr("action","${pageContext.request.contextPath}/basedata/mcrAppColAction!toHandle.action"); 
	$("#editId").val(cid);
	$("#editName").val(cname);
	$("#form2").submit();
}
// 校验新增合集名
function checkAdd(){
	var addName = $("#addName").val();
	addName = $.trim(addName);
	if(addName=="" || addName==null){
		alert("名称不能为空！");
		return;
	}
	$.post("${pageContext.request.contextPath}/basedata/mcrAppColAction!mcrAppColAdd.action",
			{addName:addName},function(data){
		window.location.reload();
	});
}
</script>
</head>
<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; 微用帮管理 -&gt; 应用合集维护 </td>
  </tr>
</table>

<form id="form1" method="post">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
  <tr>
    <td>
    	<table width="100%"  border="0" cellspacing="1" cellpadding="2">
	        <tr> 
	         <td width="11.55%"  height="23" class="biaodan-top" align="left">合集名称：</td>
	         <td width="50%" class="biaodan-q" align="center">
	         	<input type="text" name="name" style="border:none; width:100%; height:100%" id="addName" />
			 </td>
	         <td rowspan="2" align="center" class="biaodan-q">
	           <input name="Button" type="button" class="butt" value="添 加" onclick="checkAdd()" />
	         </td>
	        </tr>
		</table>
	</td>
  </tr>
</table>
</form>

<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
  <tr>
  </tr>
</table>

<form id="form2" method="post">
<table id="mytable" width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
  <tr class="biaodan-top">
    <td width="11.55%">合集id</td>
    <td width="25%">合集名称</td>
    <td width="25%">操作人</td>
    <td >操作</td>
  </tr>
  	<input type="hidden" id="editId" name="editId" />
  	<input type="hidden" id="editName" name="editName" />
  	<s:iterator var="col" value="mcrAppCol">
	<tr class="biaodan-q">
		<td align="center"><s:property value="C_ID"/></td>
		<td align="center"><s:property value="C_NAME"/></td>
		<td align="center"><s:property value="C_MODIFIER"/></td>
		<td align="center">
		<input type="button" value="修 改" class="butt"  onClick="modifyCol(<s:property value='C_ID'/>,'<s:property value="C_NAME"/>')">
	    <input type="button" value="删 除" class="butt"  onclick="deleteCol(<s:property value="C_ID"/>)">
	    <input type="button" value="维 护" class="butt"  onclick="handle(<s:property value="C_ID"/>,'<s:property value="C_NAME"/>')"></td>
	</tr>
	</s:iterator>
</table>
</form>
</body>
</html>


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
<title>游戏分类</title>
<!-- <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
<link rel="stylesheet" type="text/css" href="css/hbnw.css" />
<link rel="stylesheet" type="text/css" href="css/leftCss.css" /> -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<!--2015-4-29新样式，reCss.css样式一定要最后调用-->
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript">
// $(function(){
// 	游戏分类下拉框
// 	$.post("${pageContext.request.contextPath}/basedata/gameColAction!getColById.action",function(data){
// 		json = eval("("+data+")");
// 		for(i=0;i<json.length;i++){
// 			$("#colSelect").append("<option value="+json[i].C_ID+">"+json[i].C_NAME+"</option>");
// 		}
// 	});
// });
// 删除
function deleteCol(cid){
    var flag = confirm("您确定要删除？");
    if(flag){
		$.post("${pageContext.request.contextPath}/basedata/gameColAction!deleteCol.action",
				{cid:cid},function(data){
			window.location.reload();
		});
	}
}
// 修改
function modifyCol(cid,cname){ 
	var answer=window.open("${pageContext.request.contextPath}/basedata/pages/gameCol-modify.jsp?cid="+cid+"&cname="+cname,
		"修改分类","height=150, width=350, top=300, left=450, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no"); 
} 
// 有效性
function isvalid(cid,islive){
	$.post("${pageContext.request.contextPath}/basedata/gameColAction!isvalid.action",
			{cid:cid,islive:islive},function(data){
		window.location.reload();
	});
}
// 分类分类
function handle(cid,cname,type){
	$("#addCid").val(cid);
	$("#addCname").val(cname);
	$("#type").val(type);
	$("#form1").attr("action","${pageContext.request.contextPath}/basedata/gameColAction!toHandle.action");
	$("#form1").submit();
	/*
	var answer=window.open("${pageContext.request.contextPath}/basedata/pages/gameCol-handle.jsp?cid="+cid+"&cname="+cname+"&type="+type,
			"游戏分类分类","height=600, width=650, top=50, left=450, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no"); 
	*/
}
// 校验新增分类名
function checkAdd(){
	var addName = $("#addName").val();
	addName = $.trim(addName);
	if(addName=="" || addName==null){
		alert("名称不能为空！");
		$("#addName").focus();
		return;
	}
	var addType = $("#addType").val();
	if(addType=="" || addType==null){
		alert("请选择类型！");
		$("#addType").focus();
		return;
	}
	$.post("${pageContext.request.contextPath}/basedata/gameColAction!addCol.action",
			{addName:addName,addType:addType},function(data){
		window.location.reload();
	});
}
</script>
</head>

<body >

<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <!--2015-4-29新样式，面包屑-->
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; 3号键管理 -&gt; 首页分类维护 </td>
  </tr>
</table>

<!-- <form name="form1" method="post" action="${pageContext.request.contextPath}/basedata/gameColAction!gameCol.action"> -->
<!-- <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg"> -->
<!--   <tr> -->
<!--     <td> -->
<!--       <table width="100%"  border="0" cellspacing="1" cellpadding="2"> -->
<!--         <tr>  -->
        
<!--          <td width="11.55%"  height="23" class="biaodan-top" align="left">分类类型：</td> -->
<!--          <td width="19.6%" class="biaodan-q" align="center"> -->
<!--          	<select style="border:none; color:#636775; width:40%; height:30px;" id="colSelect" name="cid"> -->
<!-- 				<option value="">- 请 选 择  -</option> -->
<!-- 			</select> -->
<!-- 		 </td> -->
<!--          <td width="70%"  rowspan="2" align="center" class="biaodan-q"> -->
<!--            <input name="Button" type="submit" class="butt" value="查 询"/> -->
<!--          </td> -->
<!--         </tr> -->
<!--           </table></td> -->
<!--   </tr> -->
<!-- </table> -->
<!-- </form> -->

<form id="form2" method="post">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
        <%--<tr> --%>
        <%----%>
         <%--<td width="10%"  height="23" class="biaodan-top" align="left">分类名称：</td>--%>
         <%--<td width="30%" class="biaodan-q" align="center">--%>
         	<%--<input type="text" name="name" style="border:none; width:100%; height:100%" id="addName" />--%>
		 <%--</td>--%>
         <%--<td width="10%"  height="23" class="biaodan-top" align="left">分类类型：</td>--%>
         <%--<td width="30%" class="biaodan-q" align="center">--%>
         	<%--<select name="addType" id="addType" style="border:none; width:100%; height:100%">--%>
         		<%--<option value="0">商城</option>--%>
         		<%--<option value="1">虚拟兑换</option>--%>
         		<%--<option value="2">商城礼包</option>--%>
         		<%--<option value="3">礼包</option>--%>
         		<%--<option value="4">活动</option>--%>
         		<%--<option value="5">游戏</option>--%>
         		<%--<option value="6">H5游戏</option>--%>
         		<%--<option value="7">资讯</option>--%>
         		<%--<option value="8">攻略</option>--%>
         	<%--</select>--%>
		 <%--</td>--%>
         <%--<td width=""  rowspan="2" align="center" class="biaodan-q">--%>
           <%--<input name="Button" type="button" class="butt" value="添 加" onclick="checkAdd()" />--%>
         <%--</td>--%>
        <%--</tr>--%>
          </table></td>
  </tr>
</table>
</form>




<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
  <tr>
  </tr>
</table>

<form id="form1" method="post">
	<input type="hidden" id="addCid" name="addCid" />
	<input type="hidden" id="addCname" name="addCname" />
	<input type="hidden" id="type" name="type" />
<table id="mytable" width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
  <tr class="biaodan-top">
    <td width="10%">分类id</td>
    <td width="30%">分类类型</td>
    <td width="40%">分类名称</td>
    <td width="20%">操作</td>
  </tr>
  	<s:iterator var="col" value="gameCol">
	<tr class="biaodan-q">
		<td align="center"><s:property value="C_ID"/></td>
		<td align="center">
			<s:if test="C_TYPE==0">
				商城
			</s:if>
			<s:if test="C_TYPE==1">
				虚拟兑换
			</s:if>
			<s:if test="C_TYPE==2">
				商城礼包
			</s:if>
			<s:if test="C_TYPE==3">
				礼包
			</s:if>
			<s:if test="C_TYPE==4">
				活动
			</s:if>
			<s:if test="C_TYPE==5">
				游戏
			</s:if>
			<s:if test="C_TYPE==6">
				H5游戏
			</s:if>
			<s:if test="C_TYPE==7">
				资讯
			</s:if>
			<s:if test="C_TYPE==8">
				攻略
			</s:if>
		</td>
		<td align="center"><s:property value="C_NAME"/></td>
		<td align="center">
<%--		<input type="button" value="修 改" class="butt"  onClick="modifyCol(<s:property value='C_ID'/>,'<s:property value="C_NAME"/>')">--%>
		<s:if test="C_ISLIVE==1">
<%--			<input type="button" value="无 效" class="butt"  onClick="isvalid(<s:property value="C_ID"/>,0)">--%>
		</s:if>
		<s:else>
<%--			<input type="button" value="生 效" class="butt-none"  onClick="isvalid(<s:property value="C_ID"/>,1)">--%>
		</s:else>
<%--	    <input type="button" value="删 除" class="butt"  onclick="deleteCol(<s:property value="C_ID"/>)">--%>
	    <input type="button" value="维 护" class="butt"  onclick="handle(<s:property value="C_ID"/>,'<s:property value="C_NAME"/>','<s:property value="C_TYPE"/>')"></td>
	</tr>
	</s:iterator>
</table>
</form>
</body>
</html>


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
<title>游戏提示语</title>
<!-- <link rel="stylesheet" type="text/css" href="css/displaytag.css" />
<link rel="stylesheet" type="text/css" href="css/hbnw.css" />
<link rel="stylesheet" type="text/css" href="css/leftCss.css" /> -->
<link rel="stylesheet" type="text/css" href="../../css/reCss.css" />
<!--2015-4-29新样式，reCss.css样式一定要最后调用-->
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript">
// 删除
function deleteCol(cid){
    var flag = confirm("您确定要删除？");
    if(flag){
		$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameCueAction!deleteCue.action",
				{cid:cid},function(data){
			window.location.reload();
		});
	}
}
// 修改
function modifyCol(cid,ctitle){
	var answer=window.open("${pageContext.request.contextPath}/ekpages/pages/game/gameCue-modify.jsp?cid="+cid+"&ctitle="+ctitle,
		"修改提示语","height=150, width=450, top=300, left=450, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
}
// 有效性
function isvalid(cid,islive){
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameCueAction!isvalid.action",
			{cid:cid,islive:islive},function(data){
		window.location.reload();
	});
}
// 校验新增合集名
function checkAdd(){
	var addTitle = $("#addTitle").val();
	addTitle = $.trim(addTitle);
	if(addTitle=="" || addTitle==null){
		return;
	}
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameCueAction!addCue.action",
			{addTitle:addTitle},function(data){
		window.location.reload();
	});
}
</script>
</head>

<body >

<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <!--2015-4-29新样式，面包屑-->
    <td width="62.7%" class="tz_crumbs"><img src="../../images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; 3号键管理 -&gt; 游戏提示语  </td>
  </tr>
</table>

<form id="form2" method="post" action="${pageContext.request.contextPath}/basedata/gameColAction!addCol.action">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
        <tr> 
        
         <td width="10%"  height="23" class="biaodan-top" align="left">提示内容：</td>
         <td width="70%" class="biaodan-q" align="center">
         	<input type="text" name="title" style="border:none; width:100%; height:100%" id="addTitle" />
		 </td>
         <td width="20%"  rowspan="2" align="center" class="biaodan-q">
           <input name="Button" type="button" class="butt" value="添 加" onclick="checkAdd()" />
         </td>
        </tr>
          </table></td>
  </tr>
</table>
</form>




<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
  <tr>
  <!--2015-4-29新样式，绿色文字-->
<!--     <td align="right" class="jg_fc">单位：&nbsp;电压：KV ，容量：MW&nbsp;&nbsp;&nbsp;</td> -->
  </tr>
</table>


<table id="mytable" width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
  <tr class="biaodan-top">
    <td width="4%">提示id</td>
    <td width="25%">提示内容</td>
    <td width="10%">提示时间</td>
    <td width="15%">操作</td>
  </tr>
  	<s:iterator var="cue" value="gameCue">
	<tr class="biaodan-q">
		<td align="center"><s:property value="C_ID"/></td>
		<td align="center"><s:property value="C_TITLE"/></td>
		<td align="center"><s:date name="C_DATE" format="yyyy/MM/dd hh:mm:ss" /></td>
		<td align="center">
		<input type="button" value="修 改" class="butt"  onClick="modifyCol(<s:property value='C_ID'/>,'<s:property value="C_TITLE"/>')">
		<s:if test="C_ISLIVE==1">
			<input type="button" value="无 效" class="butt"  onClick="isvalid(<s:property value="C_ID"/>,0)">
		</s:if>
		<s:else>
			<input type="button" value="生 效" class="butt-none"  onClick="isvalid(<s:property value="C_ID"/>,1)">
		</s:else>
	    <input type="button" value="删 除" class="butt"  onclick="deleteCol(<s:property value="C_ID"/>)">
	</tr>
	</s:iterator>
</table>
</body>
</html>


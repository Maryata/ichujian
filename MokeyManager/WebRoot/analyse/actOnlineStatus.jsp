<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>mokey后台管理系統</title>
<!--2015-4-29新样式，reCss.css样式一定要最后调用-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<%@ include file="../../pages/chart.Inc.jsp"%>
<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"> 
function submits(){
	var sDate = document.getElementById("sDate").value;
	var eDate = document.getElementById("eDate").value;
	if(!sDate){
		alert("必须选择起始日期条件");
		return;
	}
	if(!eDate){
		alert("必须选择截止日期条件");
		return;
	}
    document.forms[0].submit();
}
</script>
<style>
.sbtable td{border-top:1px #c3c3d6 solid;border-left:1px #ddd solid;margin-top:-1px;}
.sbtable{margin: -1px 0 0 -1px;}  
</style>
</head>

<body>

<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../image/dian.jpg" width="9" height="9" /> 运营分析 -&gt; 活动上线下线情况  -&gt; 活动上线下线数量统计 </td>
  </tr>
</table>
<form name="form1" method="post" action="<%=request.getContextPath()%>/anaylse/actOnlineStatusAction!actOnlineStatus.action">
	<table width="98%"  border="0" align="center" cellpadding="1" cellspacing="0" class="tbg">
	  <tr>
	    <td>
	      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
	        <tr> 
	          <td width="10%" height="50" class="biaodan-top" align="left">起始日期：</td>
	          <td class="biaodan-q"  align="left">
	         	<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="sDate" name="sDate" 
	         		value='<s:property value="#request.sDate"/>' style="border:none; width:90%; height:100%; text-align:center;" /> 
	          </td>
	          <td width="10%" height="50" class="biaodan-top" align="left">截止日期：</td>
	          <td class="biaodan-q"  align="left">
	         	<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="eDate" name="eDate" 
	         		value='<s:property value="#request.eDate"/>' style="border:none; width:90%; height:100%; text-align:center;" /> 
	          </td>
	          <td width="30%" align="center" class="biaodan-q">
	          	<input name="Button" type="button" class="butt" onClick="submits()" value="查 询"/>
	          </td>
	          </tr>
	        </table>
	        </td>
	    </tr>
	</table>
</form>
<table width="96%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr align="right">
    <td>单 位：个 </td>
  </tr>
</table>
<table id="mytable" width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable ">
	<tr class="biaodan-top">
	  <td width="20%">类 型</td>
	  <td width="30%">人 员</td>
	  <td width="30%">数 量</td>
	  <td width="20%">总 计</td>
	</tr>
	<tr class="biaodan-q">
		<td align="center" rowspan="request.online.size()">上 线</td>
		<td align="center" colspan="2">
		<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg sortable sbtable">
		<s:set var="sumOn" value="0"/>
		<s:if test="#request.online!=null && #request.online.size()>0">
			<s:iterator value="#request.online">
			<tr class="biaodan-q ">
				<td align="center" width="50%"><s:property value="C_EDITPERSON"/></td>
				<td align="center" width="50%"><s:property value="CNT"/></td>
			</tr>
			<s:set var="sumOn" value="#sumOn+CNT"/>
			</s:iterator>
	  	</s:if>
	  	</table>
	  	</td>
	  	<td align="center"><s:property value="#sumOn"/></td>
	</tr>
	<tr class="biaodan-q">
		<td align="center" rowspan="request.offline.size()">下 线</td>
		<td align="center" colspan="2">
		<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg sortable sbtable">
		<s:set var="sumOff" value="0"/>
		<s:if test="#request.offline!=null && #request.offline.size()>0">
			<s:iterator value="#request.offline">
			<tr class="biaodan-q ">
				<td align="center" width="50%"><s:property value="C_EDITPERSON"/></td>
				<td align="center" width="50%"><s:property value="CNT"/></td>
			</tr>
			<s:set var="sumOff" value="#sumOff+CNT"/>
			</s:iterator>
	  	</s:if>
	  	</table>
	  	</td>
	  	<td align="center"><s:property value="#sumOff"/></td>
	</tr>
</table>
</body>
</html>

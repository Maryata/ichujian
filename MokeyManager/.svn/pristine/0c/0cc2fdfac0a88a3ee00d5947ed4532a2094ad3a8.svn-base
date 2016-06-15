<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.org.mokey.analyse.entiy.AppUseBean"%>
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
$(function () {
	<%--var data = $.parseJSON();--%>
	$('#container').highcharts(<%=request.getAttribute("chartData") %>);
});
</script>
</head>

<body>

<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../image/dian.jpg" width="9" height="9" /> 运营分析 -&gt; 行业品牌情况分析 -&gt; 行业品牌数量及占比 </td>
  </tr>
</table>
<table id="mytable" width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
  <tr class="biaodan-top">
    <td width="20%">行业名</td>
    <td width="20%">品牌数/个</td>
    <td width="20%">比例</td>
  </tr>
  <s:if test="#request.industryBrandList!=null && #request.industryBrandList.size()>0">
  	<s:set var="total" value="0"/>
  	<s:iterator value="#request.industryBrandList">
		<tr class="biaodan-q">
			<td align="center"><s:property value="C_NAME"/></td>
			<td align="center"><s:property value="CNT"/></td>
			<td align="center"><s:property value="RATE"/></td>
		</tr>
		<s:set var="total" value="#total+CNT"/>
	</s:iterator>
  </s:if>
  <tr class="biaodan-q">
		<td align="center">总计</td>
		<td align="center"><s:property value="#total"/></td>
		<td align="center">100%</td>
  </tr>
</table>
<div>
<br/>
<div id="container"></div>
</div>

</body>
</html>

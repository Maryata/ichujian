<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.org.mokey.analyse.entiy.UserGrowthBean"%>
<%@page import="com.org.mokey.common.util.number.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>运营分析 -&gt; 用户使用情况 -&gt; 月使用情况</title>
<script type="text/javascript">var rootPath="<%=request.getContextPath() %>";</script>
<link rel="stylesheet" type="text/css" href="../css/css.css" />

<%@ include file="../../pages/chart.Inc.jsp"%>
<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
<!-- <script type="text/javascript" src="../js/flash_scripts/zingchart-1.1.min.js"></script> -->

<script type="text/javascript"> 
$(function () {
	<%--var data = $.parseJSON();--%>
	var chart1 =document.getElementById("chart1");
	chart1.onclick = function(){
		$("#container2").hide();
		$("#container1").show();
		$('#container1').highcharts(<%=request.getAttribute("chartData1") %>);
		
	};
	
	document.getElementById("chart2").onclick = function(){
		$("#container1").hide();
		$("#container2").show();
		$('#container2').highcharts(<%=request.getAttribute("chartData2") %>);
	};
	
	chart1.onclick();
	
	
	
  }
);
<%--
function winload(){

$('#container1').highcharts(<%=request.getAttribute("chartData1") %>);
	$('#container2').highcharts(<%=request.getAttribute("chartData2") %>);
	zingchart.render({
		data 			: '<%=request.getAttribute("chartData1") %>',
		container 		: "zingchart1",
		width			: "100%",
		height			: 450,
		liburl			: rootPath+"/js/flash_scripts/zingchart.swf",
		flashvars		: {allowlocal : 0},
		wmode           : 'opaque'
	});
	
	zingchart.render({
		data 			: '<%=request.getAttribute("chartData2") %>',
		container 		: "zingchart2",
		width			: "100%",
		height			: 450,
		liburl			: rootPath+"/js/flash_scripts/zingchart.swf",
		flashvars		: {allowlocal : 0},
		wmode           : 'opaque'
	});
}--%>
function submits(){
	var startdate = document.getElementById("startdate").value;
	var enddate = document.getElementById("enddate").value;
	if(!startdate || !enddate){
		alert("必须选择日期条件");
		return;
	}
	var start  = new Date(startdate.replace(/-/g,"/")).getTime();
	var end = new Date(enddate.replace(/-/g,"/")).getTime();
	
	if(start > end){
		alert("结束月份必须大于开始月份");
		return;
	}
	if((end - start) > 366*24*60*60*1000  ){
		alert("时间跨度最多1年");
		return;
	}
    document.forms[0].submit();
}
</script>
 
</head>

<body >
<form name="form1" method="post" action="<%=request.getContextPath()%>/analyse/userUseInfo!getUserUseByMonth.action">
<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../image/dian.jpg" width="9" height="9" /> 运营分析 -&gt; 用户使用情况 -&gt; 月使用情况</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="1" cellspacing="0" class="tbg">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
        <tr> 
         <td height="23" class="biaodan-top" align="left">开始月份：</td>
         <td class="biaodan-q">
         	<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM'})" id="startdate" name="startdate" value="<%=request.getAttribute("startdate")%>" > 
         </td>
          <td height="23" class="biaodan-top" align="left">结束月份：</td>
         <td class="biaodan-q">
         	<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM'})" id="enddate" name="enddate" value="<%=request.getAttribute("enddate")%>" >
         </td>
           <td width="10%"  rowspan="2" align="center" class="biaodan-q">
           	<input name="Button" type="button" class="butt" onClick="submits()" value="查 询"/>
           </td>
          </tr>
        </table>
        </td>
    </tr>
</table>
<table width="100%" height="35"  border="0" align="center" cellpadding="1" cellspacing="tbg">
  <tr>
    <td align="right">单位：&nbsp;   次 &nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>

<%
Map<String,Object> userMap = (Map<String,Object>)request.getAttribute("userMap");

List<String> nowDays = (List<String>)userMap.get("nowDays");

Map<String,UserGrowthBean> nowList = (Map<String,UserGrowthBean>)userMap.get("nowList");
%>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg">
  <tr class="biaodan-top">
  <td width="8%" align="center" >
  	日期
  </td>
  <%
  for(String nowDay:nowDays){
  %>
  <td align="center" >
  	<%=nowDay %>
  </td>
  <%}%>
  </tr>
  
  <tr class="biaodan-q">
  <td nowrap="nowrap" align="center" >使用次数</td>
  <%
  for(String nowDay:nowDays){
  %>
   <td align="center"><%=nowList.get(nowDay).getStartCn() %> </td>
  <%}%>
  </tr>
  
    <tr class="biaodan-q">
  <td nowrap="nowrap" align="center" >平均数</td>
  <%
  for(String nowDay:nowDays){
  %>
   <td align="center"><%=nowList.get(nowDay).getActiveRate() %> </td>
  <%}%>
  </tr>
  </tr>
  
  <tr class="biaodan-q">
  <td nowrap="nowrap" align="center" >人数</td>
  <%
  for(String nowDay:nowDays){
  %>
   <td align="center"><%=nowList.get(nowDay).getActiveCn() %> </td>
  <%}%>
  </tr>
</table>
<%-- 
<table width="100%"  border="0" align="center">
	<tr nowrap="nowrap">
		<td align="center">
		<div id="zingchart1" ></div>
		</td>
		<td align="center">
		<div id="zingchart2" ></div>
		</td>
	</tr>
</table>
--%>
<div>
<br/>
<a href="#" id="chart1">使用次数</a>|<a href="#" id="chart2">平均次数</a>
<div id="container1" ></div>
<div id="container2" ></div>
</div>
</form>
</body>
</html>

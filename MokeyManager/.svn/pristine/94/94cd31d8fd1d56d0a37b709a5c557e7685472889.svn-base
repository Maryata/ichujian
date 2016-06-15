<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.org.mokey.analyse.entiy.AKeyControlBean"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>运营分析-一键操控方式统计</title>
<script type="text/javascript">var rootPath="<%=request.getContextPath() %>";</script>
<link rel="stylesheet" type="text/css" href="../css/css.css" />

<%@ include file="../../pages/chart.Inc.jsp"%>
<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../js/flash_scripts/zingchart-1.1.min.js"></script>

<script type="text/javascript"> 
$(function () {
	<%--var data = $.parseJSON();--%>
	$('#container').highcharts(<%=request.getAttribute("chartData") %>);
  }
)
<%--
function winload(){
	zingchart.render({
		data 			: '<%=request.getAttribute("chartData") %>',
		container 		: "zingchart",
		width			: "100%",
		height			: 450,
		liburl			: rootPath+"/js/flash_scripts/zingchart.swf",
		flashvars		: {allowlocal : 0},
		wmode           : 'opaque'
	});
}
--%>
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
<form name="form1" method="post" action="<%=request.getContextPath()%>/anaylse/aKeyControl!getUseKeyList.action">
<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../image/dian.jpg" width="9" height="9" /> 运营分析 -&gt;设置分析情况-&gt; 1号键操控方式统计 </td>
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
    <td align="right">单位：&nbsp;   次 ,%&nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>

<%
Map<String,String> keyMaps = (Map<String,String>)request.getAttribute("keyMaps");
List<String> nowDays = (List<String>)request.getAttribute("nowDays");
List<String> typeList = (List<String>)request.getAttribute("typeList");
Map<String,Map<String,AKeyControlBean>> keyDatas = (Map<String,Map<String,AKeyControlBean>>)request.getAttribute("keyDatas");
Map<String,AKeyControlBean> totalMaps = (Map<String,AKeyControlBean>)request.getAttribute("totalMaps");
%>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg">
  <tr class="biaodan-top">
  <td width="8%" rowspan='2'>
  	日期
  </td>
  <%
  for(String nowDay:nowDays){
  %>
  <td colspan='2'>
  	<%=nowDay %>
  </td>
  <%}%>
  <td colspan='2'>合计 </td>
  </tr>
  
  <tr class="biaodan-top">
  <%
  for(String nowDay:nowDays){
  %>
   <td >启动次数</td>
   <td >比例 </td>
  <%}%>
  <td >启动次数</td>
   <td >比例 </td>
  </tr>
  	<%
  	for(String type:typeList){
  	%>
    <tr class="biaodan-q">
  	 <td align="center"><%= keyMaps.get(type) %></td>
  <%
  Map<String,AKeyControlBean> keyTemp = null;
  AKeyControlBean item = null;
  for(String nowDay:nowDays){
	  item = keyDatas.get(nowDay).get(type);
  %>
   <td align="center"><%=item.getStartCn() %></td>
   <td align="center"><%=item.getStartRate() %></td>
  <%}
  item = totalMaps.get(type);
  %>
   <td align="center"><%=item.getStartCn() %></td>
   <td align="center"><%=item.getStartRate() %></td>
   </tr>
   <%} 
   %>
  
</table>
<div>
<br/>
<div id="container"></div>
</div>


</form>
</body>
</html>

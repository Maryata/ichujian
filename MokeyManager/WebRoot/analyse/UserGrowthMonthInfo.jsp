<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.org.mokey.analyse.entiy.UserGrowthBean"%>
<%@ taglib uri="http://mokey.com/tag" prefix="tss"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>运营分析-月用户增长情况</title>
<script type="text/javascript">var rootPath="<%=request.getContextPath() %>";</script>
<link rel="stylesheet" type="text/css" href="../css/css.css" />
<%@ include file="../../pages/chart.Inc.jsp"%>
<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
<!-- <script type="text/javascript" src="../js/flash_scripts/zingchart-1.1.min.js"></script> -->

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
<form name="form1" method="post" action="<%=request.getContextPath()%>/anaylse/userGrowth!getUserMonthGrowthList.action">
<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../image/dian.jpg" width="9" height="9" /> 运营分析 -&gt; 用户增长情况 -&gt; 月增长情况 </td>
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
         <td height="23" class="biaodan-top" align="left">代理商：</td>
         <td class="biaodan-q">
         	<tss:typeSelect name="supplierCode" type="supplier" isShowAll="y"/>
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
    <td align="right">单位：&nbsp;   个,% &nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg">
  <tr class="biaodan-top">
  <td width="8%" rowspan='2'>
  	日期
  </td>
   <td width="18%" colspan='2'>
  	安装数
  </td>
   <td width="18%" colspan='2'>
  	激活数
  </td>
   <td width="18%" colspan='2'>
  	库存数
  </td>
   <td width="18%" colspan='2'>
  	安装激活比
  </td>
  </tr>
  
  <tr class="biaodan-top">
   <td width="9%" > 当月</td>
   <td width="9%" > 去年同期</td>
   
    <td width="9%" > 当月</td>
   <td width="9%" > 去年同期</td>
   
    <td width="9%" > 当月</td>
   <td width="9%" > 去年同期</td>
   
    <td width="9%" > 当月</td>
   <td width="9%" > 去年同期</td>
  </tr>
  <%
  List<String> nowDays = (List<String>)request.getAttribute("nowDays");
  //List<String> lastDays =  (List<String>)request.getAttribute("lastDays");
  Map<String,UserGrowthBean> nowList = (Map<String,UserGrowthBean>)request.getAttribute("nowList");
  Map<String,UserGrowthBean> lastList = (Map<String,UserGrowthBean>)request.getAttribute("lastList");
  
  UserGrowthBean nowBean=null;
  UserGrowthBean lastBean=null;
  for(String nowDay:nowDays){
	  nowBean = nowList.get(nowDay);
	  lastBean = lastList.get(nowBean.getLasyDay());
  %>
  <tr class="biaodan-q">
  	 <td align="center"><%=nowDay %></td>
  	 
  	 <td align="center"><%=nowBean.getStartCn() %></td>
  	 <td align="center"><%=lastBean.getStartCn() %></td>
  	 <td align="center"><%=nowBean.getActiveCn() %></td>
  	 <td align="center"><%=lastBean.getActiveCn() %></td>
  	 
  	 <td align="center"><%=nowBean.getInventoryCn() %></td>
  	 <td align="center"><%=lastBean.getInventoryCn() %></td>
  	 <td align="center"><%=nowBean.getActiveRate() %></td>
  	 <td align="center"><%=lastBean.getActiveRate() %></td>
  </tr>
  
  <%
  }
  %>
  
</table>
<div>
<br/>
<div id="container"></div>
</div>


</form>
</body>
</html>

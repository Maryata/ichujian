<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.org.mokey.analyse.entiy.NewsAppInfoBean"%>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>mokey后台管理系統</title>
<link rel="stylesheet" type="text/css" href="../css/css.css" />
<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
<%@ include file="../../pages/chart.Inc.jsp"%>
<%-- 
<script type="text/javascript" src="../js/flash_scripts/zingchart-1.1.min.js"></script>
 --%>
 <script type="text/javascript"> 
$(function () {
	<%--var data = $.parseJSON();--%>
	//$('#container').highcharts();
	var chart = new Highcharts.Chart(<%=request.getAttribute("chartData") %>);
	
	 // Activate the sliders
    $('#R0').on('change', function(){
        chart.options.chart.options3d.alpha = this.value;
        showValues();
        chart.redraw(false);
    });
    $('#R1').on('change', function(){
        chart.options.chart.options3d.beta = this.value;
        showValues();
        chart.redraw(false);
    });

    function showValues() {
        $('#R0-value').html(chart.options.chart.options3d.alpha);
        $('#R1-value').html(chart.options.chart.options3d.beta);
    }
    chart.options.chart.options3d.alpha = 1;
    chart.options.chart.options3d.beta = 6;
    chart.redraw(false);
});
<%-- 
function winload(){
   	
	zingchart.render({
		data 			: '<%=request.getAttribute("chartData") %>',
		container 		: "zingchart",
		width			: "100%",
		height			: 450,
		liburl			: "<%=request.getContextPath()%>/js/flash_scripts/zingchart.swf",
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
<form name="form1" method="post" action="<%=request.getContextPath()%>/anaylse/newsAppUseInfoAction.action">
<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../image/dian.jpg" width="9" height="9" /> 运营分析 -&gt; app使用情况分析 -&gt; 2号键单击使用情况 </td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="1" cellspacing="0" class="tbg">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
        <tr> 
              <td height="23" class="biaodan-top" align="left">开始月份：</td>
         <td class="biaodan-q">
           <input class="Wdate" type="text" id="startdate" name="startdate" value="<%=request.getAttribute("startdate")%>" onClick="WdatePicker({dateFmt:'yyyy-MM'})" readonly="readonly">         </td>
          <td height="23" class="biaodan-top" align="left">结束月份：</td>
         <td class="biaodan-q">
           <input class="Wdate" type="text" id="enddate" name="enddate" value="<%=request.getAttribute("enddate")%>" onClick="WdatePicker({dateFmt:'yyyy-MM'})"  readonly="readonly">         </td>
           <td width="10%"  rowspan="2" align="center" class="biaodan-q">
		   <select name="top" id="top">
             <option value="5" <%=request.getAttribute("top").toString().equals("5")?"selected":""%>>top5</option>
             <option value="10" <%=request.getAttribute("top").toString().equals("10")?"selected":""%>>top10</option>
             <option value="15" <%=request.getAttribute("top").toString().equals("15")?"selected":""%>>top15</option>
             <option value="20" <%=request.getAttribute("top").toString().equals("20")?"selected":""%>>top20</option>
           </select>
           </td>
           <td width="10%"  rowspan="2" align="center" class="biaodan-q">
           	<input name="button" type="button" class="butt" onClick="submits()" value="查 询"/>           </td>
          </tr>
        </table></td>
    </tr>
  </table>
<table width="100%" height="35"  border="0" align="center" cellpadding="1" cellspacing="tbg">
  <tr>
    <td align="right">单位：&nbsp;   次 &nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg">
  <tr class="biaodan-top">
    <td width="6%" rowspan="2">app名称</td>
   <td width="6%">合计</td>
   <% 
      	  List result = (List)request.getAttribute("result");
      	  List<String> nowDays=(List)request.getAttribute("nowDays");
              for(String day:nowDays){
   %>
      <td width="5%"><%=day%></td>
   <%
              }
   %>
  </tr>
   	  	 <tr class="biaodan-top">
   	  	   <td align="center"><strong>启动数</strong></td>
   	  	   <%for(String day:nowDays){ %>
   	  	   <td align="center"><strong>启动数</strong></td>
   	  	   <%} %>
    </tr>
       <%
        DecimalFormat df = new DecimalFormat("#.##");   

   	    for(int i = 0 ; i < result.size(); i ++){
   	  	NewsAppInfoBean bean = (NewsAppInfoBean)result.get(i);
   	  	String appname = bean.getAppname()==null?" ":bean.getAppname();
   	  	String count = bean.getCount()==null?" ":bean.getCount();
   	  	%>
   	  	 <tr class="biaodan-q">
   	  	 <td align="center"><%=appname %></td>
   	  	 <td align="center" ><%=count %></td>
   	  	 <% 
   	  	List indexList = bean.getListBean();
   	  	for(int j = 0 ; j < indexList.size(); j++){
   	  		NewsAppInfoBean indexBean = (NewsAppInfoBean)indexList.get(j);
   	  		String indexAppname= indexBean.getAppname()==null?" ":indexBean.getAppname();
   	  		String indexCount = indexBean.getCount()==null?"0":indexBean.getCount();
   	    %>
   	  			 <td align="center"><%=indexCount %></td>
  			
   	  	<% 
   	  	}
   	  	%>
   	  	</tr>
   	  	<%
   	    }
        %>
</table>
<div>
<br/>
<div id="container" style="min-width:700px;height:400px"></div>
﻿	<div id="sliders" style="min-width:310px;max-width: 800px;margin: 0 auto;">
		<table>
			<tr><td>Alpha Angle</td><td><input id="R0" type="range" min="0" max="45" value="15"/> <span id="R0-value" class="value"></span></td></tr>
			<tr><td>Beta Angle</td><td><input id="R1" type="range" min="0" max="45" value="15"/> <span id="R1-value" class="value"></span></td></tr>
		</table>
	</div>
</div>
</form>
</body>
</html>

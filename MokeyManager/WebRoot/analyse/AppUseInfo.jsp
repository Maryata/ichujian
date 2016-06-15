<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.org.mokey.analyse.entiy.AppUseBean"%>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>mokey后台管理系統</title>
<link rel="stylesheet" type="text/css" href="../css/css.css" />
<%@ include file="../../pages/chart.Inc.jsp"%>
<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
<!-- <script type="text/javascript" src="../js/flash_scripts/zingchart-1.1.min.js"></script> -->
 <script type="text/javascript"> 
 <%--
    function winload(){
		zingchart.render({
			data 		: '<%=request.getAttribute("chartData")%>',
			container 		: "zingchart",
			width			: "100%",
			height			: 450,
			liburl			: "<%=request.getContextPath()%>/js/flash_scripts/zingchart.swf",
			flashvars		: {allowlocal : 0},
			wmode           : 'opaque'
		});
	}
	--%>
	$(function () {
	<%--var data = $.parseJSON();--%>
	$('#container').highcharts(<%=request.getAttribute("chartData") %>);
  }
)
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

<body>
<form name="form1" method="post" action="<%=request.getContextPath()%>/anaylse/appUseInfo.action">
<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../image/dian.jpg" width="9" height="9" /> 运营分析 -&gt; app使用情况分析 -&gt; 1号键单击使用情况 </td>
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
   	  	AppUseBean bean = (AppUseBean)result.get(i);
   	  	String appname = bean.getAppname()==null?" ":bean.getAppname();
   	  	String count = bean.getCount()==null?" ":bean.getCount();
   	  	
   	  	%>
    
   	  	 <tr class="biaodan-q">
   	  	 <td align="center"><%=appname %></td>
   	  	 <td align="center" ><%=count %></td>
   	  	 <% 
   	  	List indexList = bean.getListBean();
   	  	for(int j = 0 ; j < indexList.size(); j++){
   	  		AppUseBean indexBean = (AppUseBean)indexList.get(j);
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
<div id="container"></div></form>
</div>

</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.org.mokey.analyse.entiy.KeyUsageStatBean"%>
<%@page import="com.org.mokey.common.util.number.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>运营分析-按键使用率次统计</title>
<script type="text/javascript">var rootPath="<%=request.getContextPath() %>";</script>
<link rel="stylesheet" type="text/css" href="../css/css.css" />
<%@ include file="../../pages/chart.Inc.jsp"%>
<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
<!-- 
<script type="text/javascript" src="../js/flash_scripts/zingchart-1.1.min.js"></script>
 -->
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

<body>
<form name="form1" method="post" action="<%=request.getContextPath()%>/anaylse/aKeyControl!getKeyUsageStatList.action">
<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../image/dian.jpg" width="9" height="9" /> 运营分析 -&gt; 按键使用情况分析 -&gt; 按键使用率统计</td>
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
Map<String,Object> userMap = (Map<String,Object>)request.getAttribute("userMap");

List<String> nowDays = (List<String>)userMap.get("nowDays");
Map<String,Long> usageDatas = (Map<String,Long>)userMap.get("usageDatas");
Map<String,Long> sumDatas = (Map<String,Long>)userMap.get("sumDatas");
Map<String,Long> totalDatas = (Map<String,Long>)userMap.get("totalDatas");
Long tatalCn = (Long)userMap.get("tatalCn");
Long DeviceCount = (Long)userMap.get("DeviceCount");

String[] xtitle = new String[]{"一键","二键","三键","四键"} ;
//按键集合
String [] keys = new String []{"1","2","3","4"};
%>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg">
  <tr class="biaodan-top">
  <td width="8%" rowspan='2'>
  	日期
  </td>
  <td width="8%" rowspan='2'>
  	方式
  </td>
  <%
  for(String nowDay:nowDays){
  %>
  <td colspan='1'>
  	<%=nowDay %>
  </td>
  <%}%>
  <td rowspan='2'>总数 </td>
  <td rowspan='2'>比例 </td>
  <td rowspan='2'>用户总数 </td>
  </tr>
  
  <tr class="biaodan-top">
  <%
  for(String nowDay:nowDays){
  %>
   <td >启动次数</td>
  <%}%>
  </tr>
  <%
  long data = 0;
  String key = "";
  
  long data3 = 0;
  
  for(int i=0;i<xtitle.length;i++){
	  data3 = 0;
	  if(totalDatas.containsKey(keys[i])){
		  data3 = totalDatas.get(keys[i]);
	  }
  %>
  <tr class="biaodan-q">
  	<td align="center" rowspan='2'><%=xtitle[i] %> </td>
  	<td align="center" >单击</td>
  	  <%
	  for(String nowDay:nowDays){
		  data = 0; 
		  
		  key = nowDay+keys[i]+"0";
		  if(usageDatas.containsKey(key)){
			  data = usageDatas.get(key);
		  }
		  
		 // data = usageDatas.get();
	  %>
	  <td align="center" > <%=data %> </td>
	  <%}
	  %>
	  <td align="center"  rowspan='2' > <%=data3 %> </td>
	  <td align="center"  rowspan='2' > <%=NumberUtil.divRateToStr(data3, tatalCn) %> </td>
	  <%if(i==0){ %>
	  <td align="center"  rowspan='10' ><%=DeviceCount %> </td>
	  <%} %>
  </tr>
   <tr class="biaodan-q">
  	<td align="center" >长按</td>
  	 <%
	  for(String nowDay:nowDays){
		  data = 0;
		  key = nowDay+keys[i]+"1";
		  if(usageDatas.containsKey(key)){
			  data = usageDatas.get(key);
		  }
		 // data = usageDatas.get();
	  %>
	  <td align="center" > <%=data %> </td>
	  <%}%>
	  
  </tr>
  <%} %>
  
  
  <tr class="biaodan-q">
  	<td align="center" rowspan='2'>合计 </td>
  	<td align="center" >单击</td>
  	<%
	  for(String nowDay:nowDays){
		  data = 0;
		  key = nowDay+"0";
		  if(sumDatas.containsKey(key)){
			  data = sumDatas.get(key);
		  }
		 // data = usageDatas.get();
	  %>
	  <td align="center" > <%=data %> </td>
	  <%}%>
	  <td align="center"  rowspan='2' > <%=tatalCn %> </td>
	  <td align="center"  rowspan='2' > <%=NumberUtil.divRateToStr(tatalCn, tatalCn) %> </td>
  </tr>
   <tr class="biaodan-q">
  	<td align="center" >长按</td>
  	<%
	  for(String nowDay:nowDays){
		  data = 0;
		  key = nowDay+"1";
		  if(sumDatas.containsKey(key)){
			  data = sumDatas.get(key);
		  }
		 // data = usageDatas.get();
	  %>
	  <td align="center" > <%=data %> </td>
	  <%}%>
  </tr>
</table>
<div>
<br/>
<div id="container"></div>
</div>
</form>
</body>
</html>

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
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../../image/dian.jpg" width="9" height="9" /> 报表管理 -&gt; 报表管理 -&gt; 数据统计指标分析 </td>
  </tr>
</table>
<table width="99%"  border="0" align="left" cellpadding="1" cellspacing="0" class="tbg">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
        <tr> 
          <td width="10%" height="50" class="biaodan-top" align="left">起始日期：</td>
          <td width="28.5%" class="biaodan-q"  align="left">
         	<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="sDate" name="sDate" 
         		style="border:none; width:90%; height:100%; text-align:center;" /> 
          </td>
          <td width="10%" height="50" class="biaodan-top" align="left">截止日期：</td>
          <td width="32.95%" class="biaodan-q"  align="left">
         	<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="eDate" name="eDate" 
         		style="border:none; width:90%; height:100%; text-align:center;" /> 
          </td>
          <td align="center" class="biaodan-q">
          	<input name="Button" type="button" class="butt" onClick="searchAll()" value="查 询"/>
          	<form method="post" action="${pageContext.request.contextPath}/report/statisticsAction!exp.action">
				<input type="hidden" name="firstRow" id="firstRow" value="" />
				<input type="hidden" name="secondRow" id="secondRow" value="" />
				<input type="hidden" name="thirdRow" id="thirdRow" value="" />
				<input type="hidden" name="fourthRow" id="fourthRow" value="" />
				<input type="hidden" name="firstKeySetSVG" id="firstKeySetSVG" value="" />
				<input type="hidden" name="secondKeySetSVG" id="secondKeySetSVG" value="" />
				<input type="hidden" name="thirdKeySetSVG" id="thirdKeySetSVG" value="" />
				<input type="hidden" name="fourthKeySetSVG" id="fourthKeySetSVG" value="" />
				
				<input type="hidden" name="dt0" id="i_dt0" >
				<input type="hidden" name="dt1" id="i_dt1" >
				<input type="hidden" name="svg0" id="i_svg0">
				<input type="hidden" name="svg1" id="i_svg1">
				
          		<input type="submit" class="butt" value="导 出"/>
			</form>
          </td>
          </tr>
        </table>
        </td>
    </tr>
</table>
<div class="tagpage">
  <ul>
    <li id="tagpage1" onclick="setTab('tagpage',1,2)"  class="hover">按键设置情况分析</li>
    <li id="tagpage2" onclick="setTab('tagpage',2,2)">安装激活情况分析</li>
  </ul>  
</div>
<div id="con_tagpage_1" class="tagpage-info hover">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg">
  <tr class="biaodan-top">
    <td width="5%" rowspan="2">键位</td>
    <td width="9.5%" colspan="2">1</td>
    <td width="9.5%" colspan="2">2</td>
    <td width="9.5%" colspan="2">3</td>
    <td width="9.5%" colspan="2">4</td>
    <td width="9.5%" colspan="2">5</td>
    <td width="9.5%" colspan="2">6</td>
    <td width="9.5%" colspan="2">7</td>
    <td width="9.5%" colspan="2">8</td>
    <td width="9.5%" colspan="2">9</td>
    <td width="9.5%" colspan="2">10</td>
  </tr>
  <tr class="biaodan-top">
  	<td width="5.5%">应用名称</td><td width="4%">数量</td>
  	<td width="5.5%">应用名称</td><td width="4%">数量</td>
  	<td width="5.5%">应用名称</td><td width="4%">数量</td>
  	<td width="5.5%">应用名称</td><td width="4%">数量</td>
  	<td width="5.5%">应用名称</td><td width="4%">数量</td>
  	<td width="5.5%">应用名称</td><td width="4%">数量</td>
  	<td width="5.5%">应用名称</td><td width="4%">数量</td>
  	<td width="5.5%">应用名称</td><td width="4%">数量</td>
  	<td width="5.5%">应用名称</td><td width="4%">数量</td>
  	<td width="5.5%">应用名称</td><td width="4%">数量</td>
  </tr>
  <tbody id="keyset"></tbody>
</table>
<br/>
<table>
<tbody>
<tr><td><div id="firstKeySet"></div></td><td><div id="secondKeySet"></div></td></tr>
<tr><td><div id="thirdKeySet"></div></td><td><div id="fourthKeySet"></div></td></tr>
</tbody>
</table>
</div>

<div id="con_tagpage_2" class="tagpage-info" style="display:none">
   <table id='dt1' width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg">
  		
  	</table>
  	<br><br>
  	<table id='dt2' width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg">
  	</table>
  	<input type="hidden" id='contextPath' value='${pageContext.request.contextPath}'>
  	<div id='container0' style='width:95%;text-align: center'></div>
  	<div id='container1' style='width:95%;text-align: center'></div>
</div>
</body>
<script>
function setTab(name,cursel,n){// 'tagpage',1,2
 for(var i=1;i<=n;i++){
  var menu=document.getElementById(name+i);
  var con=document.getElementById("con_"+name+"_"+i);
  menu.className=i==cursel?"hover":"";
  con.style.display=i==cursel?"block":"none";
 }
}
</script>
<script src="${pageContext.request.contextPath}/report/js/Statistics.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8">
	$(function() {
		var contextPath = $('#contextPath').val();
		
		Statistics.init(contextPath);
	});
</script>

<script type="text/javascript"> 
$(function () {
	// 获取按键设置数据
	getKeySettings();
});
function getKeySettings(){
	var sDate = $("#sDate").val();
	var eDate = $("#eDate").val();
	$.post("${pageContext.request.contextPath}/report/statisticsAction!keySet.action",
		{sDate:sDate,eDate:eDate},function(data){
		// 清空列表
		$("#keyset").empty();
		// 查询时间
		$("#sDate").val(data[0].sDate);
		$("#eDate").val(data[0].eDate);
		// 表格数据
		var first = data[0].first;
		var second = data[0].second;
		var third = data[0].third;
		var fourth = data[0].fourth;
		// 表格数据
		$("#keyset").append(appendRow(first));
		$("#keyset").append(appendRow(second));
		$("#keyset").append(appendRow(third));
		$("#keyset").append(appendRow(fourth));
		// 表格数据存入隐藏域
		setHiddenRow(first,"firstRow");
		setHiddenRow(second,"secondRow");
		setHiddenRow(third,"thirdRow");
		setHiddenRow(fourth,"fourthRow");
		// 饼状图
		setGraph(data[0].graph_1,"firstKeySet");
		setGraph(data[0].graph_2,"secondKeySet");
		setGraph(data[0].graph_3,"thirdKeySet");
		setGraph(data[0].graph_4,"fourthKeySet");
		// 获取SVG存入隐藏域
		setSVG();
	},"json");
}

function appendRow(data){
	var row = "";
	if(data.length > 0){
		row = "<tr class='biaodan-q'>";
		row += "<td align='center'>" + data[0].C_KEY + "</td>";
		for(i=0;i<10;i++){
			var list = data[i];
			if(list){
				row += "<td align='center' width='5.5%'>"+(data[i].C_APP_NAME)+"</td><td align='center' width='4%'>"+(data[i].CNT)+"</td>";
			}else{
				row += "<td></td><td></td><td></td>";
			}
		}
		row += "</tr>";
	}
	return row;
}
function setGraph(v, div){
	if(v){
		var vObj = $.parseJSON(v);// json字符串解析成对象
		$("#"+div).highcharts({
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 45,
	                beta: 0
	            }
	        },
	        title: {
	            text: vObj.title.text
	        },
	        tooltip: {
	            pointFormat: '{point.percentage:.1f}%'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                depth: 35,
	                dataLabels: {
	                    enabled: true,
	                    format: '{point.name} | {point.percentage:.1f}%'
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	//          	name: '',
	            data: vObj.series[0].data
	        }]
	    });
    }
}
// 查询所有table页的数据
function searchAll(){
	getKeySettings();// 按键设置
	
	var sDate = $('#sDate').val();
	var eDate = $('#eDate').val();
	Statistics.query({sDate:sDate,eDate:eDate});
}
// 每行数据存入隐藏域
function setHiddenRow(obj,row){
	var arr = new Array();
	for(i=0;i<obj.length;i++){
		if(obj[i]){
			arr[i] = obj[i].C_APP_NAME+":"+obj[i].CNT;
		}
	}
	$("#"+row).val(arr);
}
// 饼状图获取的SVG数据存入隐藏域
function setSVG(){
	// 按键设置
	var first = $("#firstKeySet").highcharts();
	var second = $("#secondKeySet").highcharts();
	var third = $("#thirdKeySet").highcharts();
	var fourth = $("#fourthKeySet").highcharts();
	if(first){
		$("#firstKeySetSVG").val(first.getSVG());
	}
	if(second){
		$("#secondKeySetSVG").val(second.getSVG());
	}
	if(third){
		$("#thirdKeySetSVG").val(third.getSVG());
	}
	if(fourth){
		$("#fourthKeySetSVG").val(fourth.getSVG());
	}
}
// 导出
function exportExcel(){
	var firstRow = $("#firstRow").val();
	var secondRow = $("#secondRow").val();
	var thirdRow = $("#thirdRow").val();
	var fourthRow = $("#fourthRow").val();
	var firstGraph = $("#firstKeySetSVG").val();
	var secondGraph = $("#secondKeySetSVG").val();
	var thirdGraph = $("#thirdKeySetSVG").val();
	var fourthGraph = $("#fourthKeySetSVG").val();
	$.post("${pageContext.request.contextPath}/report/statisticsAction!exp.action",
		{firstRow:firstRow,secondRow:secondRow,thirdRow:thirdRow,fourthRow:fourthRow,
		 firstGraph:firstGraph,secondGraph:secondGraph,thirdGraph:thirdGraph,fourthGraph:fourthGraph},
		function(data){
	});
}
</script>
</html>

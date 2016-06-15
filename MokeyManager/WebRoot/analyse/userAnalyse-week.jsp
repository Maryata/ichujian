<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>周用户分析</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<%@ include file="../pages/chart.Inc.jsp"%>
</head>

<body >
<div class="box_w"> 
  <!--面包屑-->
  <table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td class="c_gray">数据分析 <img class="m_0_10" src="${pageContext.request.contextPath}/image/dian2.jpg" width="9" height="9" />周用户分析 </td>
    </tr>
  </table>
  <!--表格1-->
  	<div class="w_100b bc_gray2  p_10 f_14" > 供应商
    <select id="sup" name="sup" class="input_bor2 m_l_10"></select>
    <span class="m_l_20"></span>
    <span class="m_l_20">时间</span>
    <select id="s_year" name="year" class="input_bor2 m_l_10">
        <option value="">请选择年份</option>
    </select>
    <select id="s_sDate" name="sDate" class="input_bor2 m_l_10">

    </select>
    <select id="s_eDate" name="eDate" class="input_bor2 m_l_10">

    </select>
    <button type="button" class="btn_orange f_r m_r_20" onclick="expForm();">导出报表</button>
  </div>
  <!--表格2-->
  <div class="w_100b f_14 m_t_20" >
	  <a data-type="1" name="dataTypeBut" class="btn_gray w_100" onclick="formData(1, this);">下载情况</a>
	  <a data-type="2" name="dataTypeBut" class="btn_gray w_100" onclick="formData(2, this);">激活情况</a>
	  <a data-type="3" name="dataTypeBut" class="btn_gray w_100" onclick="formData(3, this);">注册情况</a>
	  <a data-type="4" name="dataTypeBut" class="btn_gray w_100" onclick="formData(4, this);">活跃情况</a>
	  <a data-type="5" name="dataTypeBut" class="btn_gray w_100" onclick="formData(5, this);">留存情况</a>
  </div>
  <!--标题-->
  <div class="w_100b t_c f_18 f_b m_t_20" id="dataTitle"></div>
  <!--表格3-->
  <table class="w_100b border_b t_c p_td_10 m_t_20" cellspacing="0" cellpadding="0">
    <tbody id="dataTable"></tbody>
  </table>
</div>
<div id="highChartsGraph" ></div>
<form id="expForm" action="${pageContext.request.contextPath}/analyse/userAnalyseAction!exp.action" method="post">
	<input type="hidden" id="title" name="title">
	<input type="hidden" id="dataStr" name="dataStr">
	<input type="hidden" id="exp_dataType" name="exp_dataType" value="2">
	<input type="hidden" id="exp_svg" name="svg">
</form>
<script type="text/javascript">
$(function(){
	initYearSelect();
	initMonthSelect();
	initWeekSelect();
	getSuppliers();
	formData(1);
});
function expForm() {
	var flag = true;
	$("a[name='dataTypeBut']").each(function(){
		var _class = $(this).attr("class");
		if (_class.indexOf('btn_orange2')>=0) {
			flag = false;
			var dataStr = "";
			if ("1" == $(this).attr("data-type")) {
				dataStr = "用户下载情况数据分析（周）";
			} else if ("2" == $(this).attr("data-type")) {
				dataStr = "用户激活情况数据分析（周）";
			} else if ("3" == $(this).attr("data-type")) {
				dataStr = "用户注册情况数据分析（周）";
			} else if ("4" == $(this).attr("data-type")) {
				dataStr = "用户活跃情况数据分析（周）";
			} else {
				dataStr = "用户留存情况数据分析（周）";
			}
			var s_year = $("#s_year").val();// 年
			var s_sDate = $("#s_sDate").val();// 起始周
			var s_eDate = $("#s_eDate").val();// 结束周
			var title = s_year + "年第" + s_sDate + "周至第" + s_eDate + "周" + dataStr;
			$("#title").val(title);
			$("#dataStr").val(dataStr);
			$("#exp_dataType").val(2);
			$("#expForm").submit();
		}
	});
	if (flag) {
		alert("查询后即可导出报表");
		return;
	}
}
// 设置点击的颜色为橙色，其他的为灰色
function setDataTypeStyle(obj){
	$("a[name='dataTypeBut']").each(function(){
		$(this).attr("class","btn_gray w_100");
	});
	if (obj != null) {
		$(obj).attr("class","btn_orange2 w_100");
	} else {
		$("a[name='dataTypeBut']").each(function(){
			if ("1" == $(this).attr("data-type")) {
				$(this).attr("class","btn_orange2 w_100");
				return false;// return false相当于break, true相当于continue.
			}
		});
	}
}
function getSuppliers(){
	$.post("${pageContext.request.contextPath}/analyse/userAnalyseAction!getSuppliers.action", 
		function(data){
		var suppliers = data[0].suppliers;
		var supStr = "";
		if (suppliers.length > 1) {
			supStr += "<option value='0'>全部</option>"
		}
		for (i = 0; i < suppliers.length; i++) {
			supStr += "<option value='" + suppliers[i].C_SUPPLIER_CODE + "'>" + suppliers[i].C_SUPPLIER_NAME + "</option>";
		}
		$("#sup").append(supStr);	
	},"json");
}
function initYearSelect() {
    var d = new Date();
    var year = d.getFullYear();
    for (var i = year - 10; year > i; --year) {
        $('#s_year').append('<option value="' + year + '">第' + year + '年</option>');
    }
}
function initMonthSelect() {
    clear();
    for (var m = 1; m <= 12; ++m) {
        $('#s_sDate').append('<option value="' + m + '">第' + m + '月</option>');
        $('#s_eDate').append('<option value="' + m + '">第' + m + '月</option>');
    }
}

function initWeekSelect() {
    clear();
    for (var w = 1; w <= 52; ++w) {
        $('#s_sDate').append('<option value="' + w + '">第' + w + '周</option>');
        $('#s_eDate').append('<option value="' + w + '">第' + w + '周</option>');
    }
}
function clear() {
    $('#s_sDate').html('');
    $('#s_eDate').html('');
}
function formData(dataType, obj){
	var url = "${pageContext.request.contextPath}/analyse/userAnalyseAction!analyse.action";

	var sup = "";// 供应商
	var sDate = "";// 起始时间
	var eDate = "";// 结束时间
	var year = "";// 年
	if (obj != null) {
		sup = $("#sup").val();// 供应商
		sDate = $("#s_sDate").val();// 起始时间
		eDate = $("#s_eDate").val();// 结束时间
		year = $("#s_year").val();

		if (year==null || year=="" || sDate=="year") {
			alert("请选择年份");
			$("#s_year").focus();
			return;
		}
		if (parseInt(sDate) > parseInt(eDate)) {
			alert("起始周不能大于结束周");
			$("#s_sDate").focus();
			return;
		}
	} else {
		sup = '0';

		var date = new Date();
		year = date.getFullYear();

		var _today = new Date();// 今天
		var _1stJan = new Date();// 1月1日
		_1stJan.setMonth(0);
		_1stJan.setDate(1);
		var todaySeq = Math.ceil((_today - _1stJan) / (24*60*60*1000));// 今年的第几天

		eDate = Math.ceil((todaySeq + _1stJan.getDay() + 1) / 7);// 今年的第几周（当前周）
		if (eDate >= 3) {
			sDate = eDate - 2;// 当前周的2周之前（的周）
		} else {
			sDate = 1;
		}
	}

	$("#sup").val(sup);// 供应商
	$("#s_sDate").val(sDate);// 起始时间
	$("#s_eDate").val(eDate);// 结束时间
	$("#s_year").val(year);

	setDataTypeStyle(obj);
	
	var timeType = "2";// 时间类型 1:日、2:周、3:月
	$.post(url, {sup:sup,year:year,sDate:sDate,eDate:eDate,dataType:dataType,timeType:timeType}, function(data){
		
		var times = data[0].timeList;// 时间
		var dataMap = data[0].dataMap;// 数据
		var sup_cnt = dataMap.length;// 供应商的数量
		var sup = times.shift();// 去除第一个元素"供应商"后剩余的元素
		var tb_cnt = 0;// 表的数量
		if (times.length <=3) {
			tb_cnt = 1;
		} else {
			tb_cnt = (times.length % 3)==0 ? (times.length / 3) : parseInt(times.length / 3) + 1;// 共有几个表（时间/3）
			
		}
		var headLine = "";

		for (i=0; i<tb_cnt; i++) {
			headLine += "<table class='w_100b border_b t_c p_td_10 m_t_20 f_14' cellspacing='0' cellpadding='0'>";
			headLine = oneTable(headLine, sup, times, i, sup_cnt, dataMap, tb_cnt);
			headLine += "</table>";
		}

		if (obj != null) {
			$("#dataTitle").text($(obj).text());
		} else {
			$("#dataTitle").text("下载情况");
		}
		$("#dataTable").empty();
		$("#dataTable").append(headLine);
		$("#highChartsGraph").highcharts($.parseJSON(data[0].chartData));
		var _chartData = $("#highChartsGraph").highcharts().getSVG();
		$("#exp_svg").val(_chartData);
	},
	"json");
}
function oneTable(headLine, sup, times, i, sup_cnt, dataMap, tb_cnt){
	var tableCol = 0;// 最后一个表的列数（数据的列数，不含表头、供应商名称）

	var oddData = times.length % 3;

	if (times.length == 1) {// 一个table， 一列数据
		tableCol = 1;
	} else if (times.length == 2) {// 一个table， 两列数据
		tableCol = 2;
	} else {// 多个table
		if (oddData == 0) {// 多个table，最后一个表三列数据
			tableCol = 3;
		} else if (oddData == 1) {// 多个table，最后一个表一列数据

			if (i < tb_cnt-oddData) {// 前面的表
				tableCol = 3;
			} else {// 最后一个表
				tableCol = 1;
			}
		} else if (oddData == 2) {// 多个table，最后一个表两列数据

			if (i <= tb_cnt-oddData) {// 前面的表
				tableCol = 3;
			} else {// 最后一个表
				tableCol = 2;
			}
		}
	}

	for (j=0; j<tableCol; j++) {
		if (j == 0) {
			headLine += "<tr class='bc_gray2 f_b'><td rowspan='2'>" + sup + "</td>";
			headLine += "<td class='bc_gray1' colspan='2'>" + times[j + 3 * i] + "</td>";
		} else {
			headLine += "<td class='bc_gray1' colspan='2'>" + times[j + 3 * i] + "</td>";
		}
	}

	headLine += "</tr>";

	headLine += "<tr class='bc_gray2 f_b'>";
	for (j=0; j<tableCol; j++) {
		headLine += "<td>本周统计</td>";
		headLine += "<td>上期同比</td>";
	}
	headLine += "</tr>";

	var count_0 = 0;
	var count_1 = 0;
	var count_2 = 0;
	var count_3 = 0;
	var count_4 = 0;
	var count_5 = 0;
	for (j=0; j<sup_cnt; j++) {
		var tr_j = dataMap[j];
		var sup_data = tr_j.data;// 数据
		var sup_name = tr_j.sup;// 供应商
		headLine += "<tr class='bc_gray2 f_b'><td>" + sup_name + "</td>";
		for (m=0; m<tableCol*2; m++) {
			var _currCnt = parseInt(sup_data[m + 6 * i]);
			if (tableCol==1) {
				if (m==0) { count_0 += _currCnt; }
				if (m==1) { count_1 += _currCnt; }
			} else if (tableCol==2) {
				if (m==0) { count_0 += _currCnt; }
				if (m==1) { count_1 += _currCnt; }
				if (m==2) { count_2 += _currCnt; }
				if (m==3) { count_3 += _currCnt; }
			} else if (tableCol==3) {
				if (m==0) { count_0 += _currCnt; }
				if (m==1) { count_1 += _currCnt; }
				if (m==2) { count_2 += _currCnt; }
				if (m==3) { count_3 += _currCnt; }
				if (m==4) { count_4 += _currCnt; }
				if (m==5) { count_5 += _currCnt; }
			}
			headLine += "<td>" + _currCnt + "</td>";
		}
		headLine += "</tr>";
	}
	headLine += "<tr class='bc_gray2 f_b'><td>合计</td>";
	headLine += "<td>" + count_0 + "</td><td>"  + count_1 + "</td>";
	if (tableCol==2) {
		headLine += "<td>" + count_2 + "</td><td>"  + count_3 + "</td>";
	} else if (tableCol==3) {
		headLine += "<td>" + count_2 + "</td><td>"  + count_3 + "</td>";
		headLine += "<td>" + count_4 + "</td><td>"  + count_5 + "</td>";
	}
	headLine += "</tr>";
	return headLine;
}
</script>
</body>
</html>

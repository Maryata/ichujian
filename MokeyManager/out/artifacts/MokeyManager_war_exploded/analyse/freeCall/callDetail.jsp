<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>通话详情</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<script type="text/javascript" charset="utf-8"
			src="${pageContext.request.contextPath}/js/jquery/jquery-1.11.1.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${pageContext.request.contextPath}/ekpages/layer/layer.js"></script>
</head>

<body>
<div class="box_w">
	<!--面包屑-->
	<table width="100%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td class="c_gray">数据分析 <img class="m_0_10" src="${pageContext.request.contextPath}/image/dian2.jpg"
										 width="9" height="9"/></td>
		</tr>
	</table>
	<!--表格1-->
	<form id="form0" method="post">
		<div class="w_100b bc_gray2  p_10 f_14">手机号
			<input id="phone" name="phone" class="input_bor2 m_l_10"/>
			<span class="m_l_20">飞语账号</span>
			<input id="fyid" name="fyid" class="input_bor2 m_l_10"/>
			<span class="m_l_20">开始时间</span>
			<input class="input_bor2 m_l_10" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'eDate\')}'})" id="sDate"
				   name="sDate" value="${sDate}"/>
			<span class="m_l_20">结束时间</span>
			<input class="input_bor2 m_l_10" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'sDate\')}',maxDate:'%y-%M-%d'})" id="eDate"
				   name="eDate" value="${eDate}"/>
		</div>
		<button type="button" class="btn_orange f_r m_r_20" onclick="clickEvent(2);">导出报表</button>
		<button type="button" class="btn_green f_r m_r_10" onclick="clickEvent(1);">查　询</button>
	</form>
	<div class="w_100b t_c f_18 f_b m_t_20" id="dataTitle" style="overflow-x: auto;">
		<!--表格3-->
		<table class="w_100b border_b t_c p_td_10 m_t_20" cellspacing="0" cellpadding="0" id="query">
			<thead>
			<tr class='bc_gray2 f_14 m_t_20'>
				<td>日期</td>
				<td>飞语账号</td>
				<td>手机号</td>
				<td>开始时间</td>
				<td>结束时间</td>
				<td>时长（分）</td>
			</tr>
			</thead>
			<tbody id="data_body" class="f_14 m_t_20" cellspacing="0" cellpadding="0">
			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript">
   function clickEvent(type){
	   var phone = $("#phone").val();
	   var sDate = $("#sDate").val();
	   var eDate = $("#eDate").val();
	   var fyid=$("#fyid").val();
	   if(type==1){//查询
		   layer.load();
		     $.post("${pageContext.request.contextPath}/freeCallAction!selectCallDetailList.action",{phone:phone,sDate:sDate,eDate:eDate,fyid:fyid},
			   function(data){
				   var json = $.parseJSON(data);
				   var list = json[0].list;
				   $("#data_body").empty();
				   layer.closeAll();
				   $(list).each(function(index,value){
					   var head ="";
					   head+="<tr>";
					   head+="<td>"+(this.DAY)+"</td>" +
							   "<td >"+(this.C_FYACCID)+"</td>" +
							   "<td >"+(this.C_GLOBALMOBILEPHONE)+"</td>" +
							   "<td >"+(this.EKSTIME)+"</td>" +
							   "<td >"+(this.EKETIME)+"</td>" +
							   "<td >"+(this.EKTIME)+"</td>" +
							   "</tr>"
					   $("#data_body").append(head);
				   });
			   });
	   }else{
		   if($("#data_body tr").length ==0){
			   layer.msg("请查询你要导出的数据！");
			   return false;
		   }else{
			   $('#form0').attr("action", "${pageContext.request.contextPath}freeCallAction!exportCallDetailList.action");
			   $('#form0').submit();
		   }
	   }
   }
</script>
</body>
</html>

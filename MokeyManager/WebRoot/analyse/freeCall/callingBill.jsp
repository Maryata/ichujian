<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>通话账单</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <script type="text/javascript" charset="utf-8"
            src="${pageContext.request.contextPath}/js/jquery/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <%@ include file="../../pages/chart.Inc.jsp" %>
</head>

<body>
<div class="box_w">
    <!--面包屑-->
    <table width="100%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="c_gray">数据分析
                <img class="m_0_10" src="${pageContext.request.contextPath}/image/dian2.jpg" width="9" height="9"/>免费通话
                <img class="m_0_10" src="${pageContext.request.contextPath}/image/dian2.jpg" width="9" height="9"/>通话账单
            </td>
        </tr>
    </table>
    <!--表格1-->
    <form id="form1" action="" type="post">
        <div class="w_100b bc_gray2  p_10 f_14">
            <span class="m_l_20">开始时间</span>
            <input class="input_bor2 m_l_10" type="text" value="${sDate}" id="sDate" name="sDate"
                   onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            <span class="m_l_20">结束时间</span>
            <input class="input_bor2 m_l_10" type="text" value="${eDate}" id="eDate" name="eDate"
                   onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            <input type="hidden" name="timeType" id="timeType" value="${timeType}"/>
            <input type="hidden" name="userType" id="userType" value="${userType}"/>
            <button type="button" class="btn_orange f_r m_r_20" onclick="expForm();">导出报表</button>
            <button type="button" class="btn_green f_r m_r_10" onclick="_query();">查　询</button>
        </div>
    </form>
</div>
<div class="w_100b t_c f_18 f_b m_t_20" id="dataTitle"></div>
<!--表格3-->
<table class="w_100b border_b t_c p_td_10 m_t_20" cellspacing="0" cellpadding="0">
    <thead>
    <tr class='bc_gray2 f_14 m_t_20'>
        <td width="16%">日 期</td>
        <td width="28%">e 键</td>
        <td width="28%">飞 语</td>
        <td width="28%">差 值</td>
    </tr>
    </thead>
    <tbody id="dataTable">
    <s:iterator value="#session.bills" var="bill">
        <tr>
            <td><s:property value="date"/></td>
            <td><s:property value="ekCallingTime"/></td>
            <td><s:property value="fyCallingTime"/></td>
            <td class="dif"><span style="color: <s:property value="color"/>"><s:property value="difference"/></span>
            </td>
        </tr>
    </s:iterator>
    </tbody>
</table>
</div>
<!-- 图 -->
<br>
<div id="highChartsGraph"></div>
<form id="expForm" action="${pageContext.request.contextPath}/analyse/freeCallAction!exp.action" method="post">
    <input type="hidden" id="title" name="title">
    <input type="hidden" id="exp_svg" name="svg">
</form>
<script type="text/javascript">
    $(function () {
        $('#highChartsGraph').highcharts(<%=request.getAttribute("chartData") %>);
    });
    function _query() {
        if (checkDate()) {
            $("#form1").attr("action", "${pageContext.request.contextPath}/freeCallAction!getCallingBill.action");
            $("#form1").submit();
        }
    }
    function expForm() {
        var sDate = $("#sDate").val();// 起始时间
        var eDate = $("#eDate").val();// 结束时间
        if (checkDate()) {
            var title = sDate + "至" + eDate + "免费通话账单比对";
            var _chartData = $("#highChartsGraph").highcharts().getSVG();
            $("#exp_svg").val(_chartData);
            $("#title").val(title);
            $("#expForm").submit();
        }
    }
    function checkDate() {
        var $nullSDate = $("#sDate").val().length;
        var $nullEDate = $("#sDate").val().length;
        if ($nullSDate <= 0 || $nullEDate <= 0) {
            alert("请选择时间");
            return false;
        } else {
            return true;
        }
    }
</script>
</body>
</html>

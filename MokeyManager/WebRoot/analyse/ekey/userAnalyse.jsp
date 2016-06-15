<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <c:set var="basePath"><%=basePath%>
    </c:set>
    <meta charset="UTF-8"/>
    <title>mokey后台管理系統-->e 键 数据分析-综合统计</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link rel="stylesheet" href="${basePath}/css/table.css">
    <script type="text/javascript" charset="utf-8" src="${basePath }/js/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="box_w">
    <input type="hidden" id="h_0" value="${timeType}">
    <input type="hidden" id="h_1" value="${year}">
    <input type="hidden" id="h_2" value="${sDate}">
    <input type="hidden" id="h_3" value="${eDate}">
    <!--面包屑-->
    <table width="100%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="c_gray"> 数据分析 <img class="m_0_10" src="${basePath}/image/dian2.jpg" width="9" height="9"/> 综合统计
            </td>
        </tr>
    </table>
    <!--表格1-->
    <form id="form0" action="${basePath}/analyse/userAnalyseAction!complexStatistics.action" method="post">
        <div class="w_100b bc_gray2  p_10 f_14">供应商
            <select name="sup" class="input_bor2 m_l_10">
                <c:if test="${fn:length(suppliers) ge 2}">
                    <option value="0">全部</option>
                </c:if>
                <c:forEach items="${suppliers}" var="sup">
                    <option value="${sup['C_SUPPLIER_CODE']}">${sup['C_SUPPLIER_NAME']}</option>
                </c:forEach>
            </select>
            <span class="m_l_20">时间</span>
            <c:choose>
                <c:when test="${ timeType eq 1 }">
                    <span class="m_l_20">起始时间</span>
                    <input class="input_bor2 m_l_10" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                           id="sDate" name="sDate"/>
                    <span class="m_l_20">结束时间</span>
                    <input class="input_bor2 m_l_10" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                           id="eDate" name="eDate"/>
                </c:when>
                <c:when test="${ timeType eq 3 }">
                    <span class="m_l_20">起始时间</span>
                    <input class="input_bor2 m_l_10" type="text" value="${sDate}" onClick="WdatePicker({dateFmt:'yyyy-MM'})" id="sDate"
                           name="sDate"/>
                    <span class="m_l_20">结束时间</span>
                    <input class="input_bor2 m_l_10" type="text" value="${eDate}" onClick="WdatePicker({dateFmt:'yyyy-MM'})" id="eDate"
                           name="eDate"/>
                </c:when>
                <c:otherwise>
                    <select id="s_year" name="year" class="input_bor2 m_l_10">
                        <option value="">请选择年份</option>
                    </select>
                    <select id="s_sDate" name="sDate" class="input_bor2 m_l_10">

                    </select>
                    至
                    <select id="s_eDate" name="eDate" class="input_bor2 m_l_10">

                    </select>
                </c:otherwise>
            </c:choose>

            <button type="button" class="btn_orange f_r m_r_20" onclick="_exp();">导出报表</button>
            <button type="button" class="btn_green f_r m_r_10" onclick="_query();">查　询</button>
        </div>
    </form>
    <!--标题-->
    <div class="w_100b t_c f_18 f_b m_t_20">综合统计</div>
    <!--表格3-->
    <table class="w_100b border_b t_c p_td_10 m_t_20 f_14" cellspacing="0" cellpadding="0">
        <tbody>
        <tr class=" bc_gray2 f_b">
            <td>日期</td>
            <td>下载数</td>
            <td>激活数</td>
            <td>注册数</td>
            <td>活跃数</td>
            <td>留存数</td>
        </tr>
        <c:set var="downloadSum">0</c:set>
        <c:set var="activationSum">0</c:set>
        <c:set var="registerSum">0</c:set>
        <c:set var="activeSum">0</c:set>
        <c:set var="stayingSum">0</c:set>
        <c:forEach items="${ eKeyData }" var="v">
            <tr>
                <td>${v.time}</td>
                <td>${v.count_download}<c:set var="downloadSum">${downloadSum + v.count_download}</c:set></td>
                <td>${v.count_activation}<c:set var="activationSum">${activationSum + v.count_activation}</c:set></td>
                <td>${v.count_register}<c:set var="registerSum">${registerSum + v.count_register}</c:set></td>
                <td>${v.count_active}<c:set var="activeSum">${activeSum + v.count_active}</c:set></td>
                <td>${v.count_staying}<c:set var="stayingSum">${stayingSum + v.count_staying}</c:set></td>
            </tr>
        </c:forEach>
        <tr>
            <td>合计</td>
            <td>${downloadSum}</td>
            <td>${activationSum}</td>
            <td>${registerSum}</td>
            <td>${activeSum}</td>
            <td>${stayingSum}</td>
        </tr>
        </tbody>
    </table>
</div>
<form method="post" id="expForm" action="${basePath}/analyse/userAnalyseAction!exportComplexStatistics.action">
    <input name="year" value="${year}" type="hidden" id="exp0">
    <input name="sDate" value="${sDate}" type="hidden" id="exp1">
    <input name="eDate" value="${eDate}" type="hidden" id="exp2">
    <input name="timeType" value="${timeType}" type="hidden" id="exp3">
</form>
<div id="container" class="w_100b border_b t_c p_td_10 m_t_20"></div>
<script src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
<script src="http://cdn.hcharts.cn/highcharts/modules/exporting.js"></script>
<script>
    var chArr = [];

    $(function () {
        var timeType = $('#h_0').val() || '';

        if (timeType == '2') {
            initYearSelect();
            initWeekSelect();
        }
    });
    function initYearSelect() {
        var d = new Date();
        var year = d.getFullYear();
        var defaultYear = $('#h_1').val();
        if (!defaultYear) {
            defaultYear = year;
            $('#exp0').val(year);
        }

        for (var i = year - 10; year > i; --year) {
            if (defaultYear == year) {
                $('#s_year').append('<option selected="selected" value="' + year + '">第' + year + '年</option>');
            } else {
                $('#s_year').append('<option value="' + year + '">第' + year + '年</option>');
            }
        }
    }

    function initWeekSelect() {
        clear();
        var defaultSDate = $('#h_2').val();
        var defaultEDate = $('#h_3').val();
        for (var w = 1; w <= 52; ++w) {
            if (defaultSDate && defaultSDate == w) {
                $('#s_sDate').append('<option selected="selected" value="' + w + '">第' + w + '周</option>');
            } else {
                $('#s_sDate').append('<option value="' + w + '">第' + w + '周</option>');
            }

            if (defaultEDate && defaultEDate == w) {
                $('#s_eDate').append('<option selected="selected" value="' + w + '">第' + w + '周</option>');
            } else {
                $('#s_eDate').append('<option value="' + w + '">第' + w + '周</option>');
            }
        }
    }

    function initChart() {
        var count = $('#cCount').val();
        var id = 'container';
        var $expForm = $('#expForm');

        for (var i = 0; i < count; ++i) {
            var v = $('#c' + i).val();
            v = v.replace(/'/g, '"');
            v = JSON.parse(v);
            $('#container').append('<div id="container' + i + '" style="min-width:400px;height:400px"></div>');
            id = 'container' + i;
            $('#' + id).highcharts(v);
            var svg = $('#' + id).highcharts().getSVG();
            var $svgArrInput = $('<input type="hidden" name="svgArr" >');
            $svgArrInput.val(svg);
            $expForm.append($svgArrInput);
//            console.log(v);
        }
    }

    function clear() {
        $('#s_sDate').html('');
        $('#s_eDate').html('');
    }

    function _query() {
        var timeType = $('#h_0').val() || '';

        if (timeType == 2) {
            var year = $('#s_year').val();
            if (!year) {
                alert('请选择年份');
                return false;
            }
            var sDate = $('#s_sDate').val();
            var eDate = $('#s_eDate').val();

            if (parseInt(eDate) < parseInt(sDate)) {
                alert('开始时间需要大于等于结束时间');
                return false;
            }
        } else {
            sDate = $('#sDate').val();
            eDate = $('#eDate').val();

            if (sDate == null || sDate == '' || sDate == 'undefined') {
                alert('起始时间不能为空');
                $('#sDate').focus();
                return false;
            }
            if (eDate == null || eDate == '' || eDate == 'undefined') {
                alert('结束时间不能为空');
                $('#eDate').focus();
                return false;
            }

            if (Date.parse(sDate) > Date.parse(eDate)) {
                alert('起始时间不能大于结束时间');
                $('#sDate').focus();
                return false;
            }
        }

        $('#form0').submit();
    }

    function _exp() {
        // 获取svg数据数组
        $('#expForm').submit();
    }
</script>
</body>
</html>

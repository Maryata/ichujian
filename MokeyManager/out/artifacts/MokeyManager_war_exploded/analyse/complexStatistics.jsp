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
    <title>mokey后台管理系統-->数据分析-综合统计</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link rel="stylesheet" href="${basePath}/css/table.css">
    <script type="text/javascript" charset="utf-8" src="${basePath }/js/jquery/jquery-1.11.1.min.js"></script>
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
            <td class="c_gray"> 数据分析 <img class="m_0_10" src="${basePath}/image/dian2.jpg" width="9" height="9"/> 综合统计</td>
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
            <select id="s_0" name="timeType" class="input_bor2 m_l_10">
                <option value="3">按月纬度</option>
                <option value="2">按周维度</option>
            </select>
            <%--select   <input class="input_bor1 w_100 m_l_10" type="text">--%>
            <span class="m_l_20">时间</span>
            <select id="s_year" name="year" class="input_bor2 m_l_10">
                <option value="">请选择年份</option>
            </select>
            <select id="s_sDate" name="sDate" class="input_bor2 m_l_10">

            </select>
            至
            <select id="s_eDate" name="eDate" class="input_bor2 m_l_10">

            </select>

            <button type="button" class="btn_orange f_r m_r_20" onclick="_exp();">导出报表</button>
            <button type="button" class="btn_green f_r m_r_10" onclick="_query();">查　询</button>
        </div>
    </form>
    <!--标题-->
    <div class="w_100b t_c f_18 f_b m_t_20">综合统计</div>
    <!--表格3-->
    <c:forEach items="${timeStringList}" begin="0" step="2" varStatus="globalStatus">
        <table class="w_100b border_b t_c p_td_10 m_t_20 f_14" cellspacing="0" cellpadding="0">
            <tbody>
            <tr class=" bc_gray2 f_b">
                <td class="f_14" rowspan="2">供应商</td>
                <c:forEach begin="${globalStatus.index}" items="${timeStringList}" end="${globalStatus.index + 1}"
                           var="timeString">
                    <td class=" bc_gray1" colspan="5">${timeString}</td>
                </c:forEach>
            </tr>
            <tr class=" bc_gray2 f_b">
                <c:forEach begin="${globalStatus.index}" items="${timeStringList}" end="${globalStatus.index + 1}"
                           var="timeString">
                    <td>下载数</td>
                    <td>激活数</td>
                    <td>注册数</td>
                    <td>活跃数</td>
                    <td>留存数</td>
                </c:forEach>
            </tr>
            <c:set var="downloadSum1">0</c:set>
            <c:set var="activationSum1">0</c:set>
            <c:set var="registerSum1">0</c:set>
            <c:set var="activeSum1">0</c:set>
            <c:set var="stayingSum1">0</c:set>
            <c:set var="downloadSum2">0</c:set>
            <c:set var="activationSum2">0</c:set>
            <c:set var="registerSum2">0</c:set>
            <c:set var="activeSum2">0</c:set>
            <c:set var="stayingSum2">0</c:set>
            <c:set var="flag" value="${false}"></c:set>
            <c:forEach items="${supVoList}" var="supVo">
                <tr>
                    <td class=" bc_gray2 f_b">${supVo.supName}</td>
                    <c:forEach begin="${globalStatus.index}" items="${supVo.l_timeVo}" varStatus="timeVoStatus"
                               end="${globalStatus.index + 1}" var="timeVo">
                        <c:choose>
                            <c:when test="${timeVoStatus.first}">
                                <c:set var="downloadSum1">${downloadSum1 + timeVo.downloadNum }</c:set>
                                <c:set var="activationSum1">${activationSum1 + timeVo.activationNum}</c:set>
                                <c:set var="registerSum1">${registerSum1 + timeVo.registerNum}</c:set>
                                <c:set var="activeSum1">${activeSum1 + timeVo.activeNum}</c:set>
                                <c:set var="stayingSum1">${stayingSum1 + timeVo.stayingNum}</c:set>
                            </c:when>
                            <%--只有两个才能这么写--%>
                            <c:otherwise>
                                <c:set var="flag">${true}</c:set>
                                <c:set var="downloadSum2">${downloadSum2 + timeVo.downloadNum }</c:set>
                                <c:set var="activationSum2">${activationSum2 + timeVo.activationNum}</c:set>
                                <c:set var="registerSum2">${registerSum2 + timeVo.registerNum}</c:set>
                                <c:set var="activeSum2">${activeSum2 + timeVo.activeNum}</c:set>
                                <c:set var="stayingSum2">${stayingSum2 + timeVo.stayingNum}</c:set>
                            </c:otherwise>
                        </c:choose>
                        <td>${timeVo.downloadNum}</td>
                        <td>${timeVo.activationNum}</td>
                        <td>${timeVo.registerNum}</td>
                        <td>${timeVo.activeNum}</td>
                        <td>${timeVo.stayingNum}</td>
                    </c:forEach>
                </tr>
            </c:forEach>
            <tr>
                <td class=" bc_gray2 f_b">合计</td>
                <td>${downloadSum1}</td>
                <td>${activationSum1}</td>
                <td>${registerSum1}</td>
                <td>${activeSum1}</td>
                <td>${stayingSum1}</td>
                <c:if test="${flag}">
                    <td>${downloadSum2}</td>
                    <td>${activationSum2}</td>
                    <td>${registerSum2}</td>
                    <td>${activeSum2}</td>
                    <td>${stayingSum2}</td>
                </c:if>
            </tr>
            </tbody>
        </table>
    </c:forEach>

</div>
<input type="hidden" id="cCount" value="${fn:length(chartArr)}">
<c:forEach items="${chartArr}" varStatus="cStatus" var="c">
    <input type="hidden" id="c${cStatus.index}" value="${c}">
</c:forEach>
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

        initYearSelect();

        if(timeType == '2') {
            $('#s_0').val(timeType);
            initWeekSelect();
        } else {
            $('#exp3').val('3');
            initMonthSelect();
        }

        $('#s_0').on('change', function () {
            var _self = $(this);
            if (_self.val() == '3') {
                initMonthSelect();
            } else {
                initWeekSelect();
            }
        });

        initChart();
    });
    function initYearSelect() {
        var d = new Date();
        var year = d.getFullYear();
        var defaultYear = $('#h_1').val();
        if(!defaultYear) {
            defaultYear = year;
            $('#exp0').val(year);
        }

        for (var i = year - 10; year > i; --year) {
            if(defaultYear == year) {
                $('#s_year').append('<option selected="selected" value="' + year + '">第' + year + '年</option>');
            } else {
                $('#s_year').append('<option value="' + year + '">第' + year + '年</option>');
            }
        }
    }
    function initMonthSelect() {
        clear();
        var d = new Date();
        var month = d.getMonth() + 1;
        var defaultSDate = $('#h_2').val();
        var defaultEDate = $('#h_3').val();
        if(!defaultSDate) {
            defaultSDate = month;
            $('#exp1').val(month);
        }
        if(!defaultEDate) {
            defaultEDate = month;
            $('#exp2').val(month);
        }

        for (var m = 1; m <= 12; ++m) {
            if(defaultSDate == m) {
                $('#s_sDate').append('<option selected="selected" value="' + m + '">第' + m + '月</option>');
            } else {
                $('#s_sDate').append('<option value="' + m + '">第' + m + '月</option>');
            }

            if(defaultEDate == m) {
                $('#s_eDate').append('<option selected="selected" value="' + m + '">第' + m + '月</option>');
            } else {
                $('#s_eDate').append('<option value="' + m + '">第' + m + '月</option>');
            }
        }
    }

    function initWeekSelect() {
        clear();
        var defaultSDate = $('#h_2').val();
        var defaultEDate = $('#h_3').val();
        for (var w = 1; w <= 52; ++w) {
            if(defaultSDate && defaultSDate == w) {
                $('#s_sDate').append('<option selected="selected" value="' + w + '">第' + w + '周</option>');
            } else {
                $('#s_sDate').append('<option value="' + w + '">第' + w + '周</option>');
            }

            if(defaultEDate && defaultEDate == w) {
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
        var year = $('#s_year').val();
        if(!year) {
            alert('请选择年份');
            return false;
        }
        var sDate = $('#s_sDate').val();
        var eDate = $('#s_eDate').val();

        if(parseInt(eDate) < parseInt(sDate)) {
            alert('开始时间需要大于等于结束时间');
            return false;
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

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,user-scalable=no">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <title>定制APP添加</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css"/>
    <!--2015-4-29新样式，reCss.css样式一定要最后调用-->
    <script type="text/javascript">rootPath ="${pageContext.request.contextPath}";</script>
    <script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.selectseach.min.js"></script>
</head>
<body>
<table width="98%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9"
                                                 height="9"/> 系统信息 -&gt; e键：首页 -&gt; 定制APP添加
        </td>
    </tr>
</table>

<form id="form1" method="post"
      action="${pageContext.request.contextPath}/basedata/ekey/eKIndexSuppIndexAppAction!toAdd.action">
    <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
        <tr>
            <td>
                <table width="100%" border="0" cellspacing="1" cellpadding="2">
                    <tr>
                        <td width="8%" height="23" class="biaodan-top" align="left">供应商</td>
                        <td width="15%" class="biaodan-q" align="left">
                            <s:select list="#request.suppList" id="code" listKey="C_SUPPLIER_CODE"
                                      headerKey="" headerValue="" listValue="C_SUPPLIER_NAME"
                                      cssStyle="width:100%;border:none;hight:100%;" m="search"
                            />
                        </td>
                        <td width="15%" class="biaodan-q" align="left" style="display: none;">
                            <s:select list="#request.appList" id="aid" listKey="C_ID"
                                      headerKey="" headerValue="" listValue="C_NAME"
                                      cssStyle="width:100%;border:none;hight:100%;" m="search"
                            />
                        </td>
                        <td rowspan="2" align="right" class="biaodan-q">
                            <input type="button" class="butt" value="查 询" onclick="infoList()"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>

<table width="98%" height="35" border="0" align="center" cellpadding="1">
    <tr>
    </tr>
</table>

<form id="form2" method="post">
    <table width="98%" border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
        <thead>
        <tr class="biaodan-top">
            <td width="20%">供应商名称</td>
            <td width="10%">供应商CODE</td>
            <td width="20%">APP应用</td>
            <td width="10%">排序</td>
        </tr>
        <input type="hidden" name="editId" id="editId"/>
        </thead>
        <tbody id="mytable"></tbody>
    </table>
    <table width="98%" height="35" border="0" align="center" cellpadding="1">
        <tr>
            <td align="center">
                <input type="button" value="提 交" class="butt" onclick="submitt()"/>
            </td>
        </tr>
    </table>
    <input type="hidden" id="cateTotal"/>
    <div id="page11"></div>
</form>
<script type="text/javascript">

    $(function () {
        infoList();
        $("select").selectseach();
    });
    function infoList() {
        // 查询应用总数
        var code = $("#code").val();
        $.post("${pageContext.request.contextPath}/basedata/ekey/eKIndexSuppIndexAppAction!addList.action", {code: code},
                function (data) {
                    var json = $.parseJSON(data);
                    var count = json[0].count;// 数据总数
                    $("#page11").empty();
                    if (count != 0) {
                        setTotal(count, "cateTotal");
                        // 分页显示键位管理
                        getCatePage("${pageContext.request.contextPath}/basedata/ekey/eKIndexSuppIndexAppAction!addList.action", code);
                    }
                });
        // 查询键位管理(第一页)
        $.post("${pageContext.request.contextPath}/basedata/ekey/eKIndexSuppIndexAppAction!addList.action", {code: code},
                function (data) {
                    var json = $.parseJSON(data);
                    var list = json[0].list;
                    $("#mytable").empty();
                    $(list).each(function () {
                        var _s = "<tr class='biaodan-q'><input type='hidden' name='CODE' value=" + this.C_SUPPLIER_CODE + " />";
                        _s += "<td align='center'>" + (this.C_SUP_NAME || '') + "</td>";
                        _s += "<td align='center'>" + (this.C_SUPPLIER_CODE || '') + "</td>";
                        _s += "<td align='center'>";
                        _s += "<select name='AID' id='AID'>";
                        _s += "<option value=''>请选择APP应用</option>";
                        _s += "<c:forEach var='item' items='${appList}' varStatus='status'>";
                        _s += "<option  value='${item.C_ID}'>${item.C_NAME}</option>";
                        _s += "</c:forEach></select></td>";
                        _s += "<td align='center'><input type='text' name='ORDER'/></td>";
                        _s += "<tr>";
                        $("#mytable").append(_s);
                    });
                });
    }
    ;
    // 设置总页数
    function setTotal(count, pageTotal) {
        var totalPage = 0;
        var rows = 5;// 每页显示条数
        totalPage = 1;
        // 计算总页数
        totalPage = parseInt((count - 1) / rows) + 1;
        $("#" + pageTotal).val(totalPage);
    }
    // 分页显示键位管理
    function getCatePage(url, code) {
        laypage({
            cont: 'page11',
            pages: document.getElementById("cateTotal").value,
            curr: function () { //通过url获取当前页，也可以同上（pages）方式获取
                var page = location.search.match(/page=(\d+)/);
                return page ? page[1] : 1;
            }(),
            jump: function (e, first) { //触发分页后的回调
                if (!first) { //一定要加此判断，否则初始时会无限刷新
                    $.post(url, {page: e.curr, code},
                            function (data) {
                                var json = $.parseJSON(data);
                                var list = json[0].list;
                                $("#mytable").empty();
                                $(list).each(function () {
                                    var _s = "<tr class='biaodan-q'><input type='hidden' name='CODE' value=" + this.C_SUPPLIER_CODE + " />";
                                    _s += "<td align='center'>" + (this.C_SUP_NAME || '') + "</td>";
                                    _s += "<td align='center'>" + (this.C_SUPPLIER_CODE || '') + "</td>";
                                    _s += "<td align='center'>";
                                    _s += "<select name='AID' id='AID'>";
                                    _s += "<option value=''>请选择APP应用</option>";
                                    _s += "<c:forEach var='item' items='${appList}' varStatus='status'>";
                                    _s += "<option  value='${item.C_ID}'>${item.C_NAME}</option>";
                                    _s += "</c:forEach></select></td>";
                                    _s += "<td align='center'><input type='text' name='ORDER'/></td>";
                                    _s += "<tr>";
                                    $("#mytable").append(_s);
                                });
                            });
                }
            }
        });
    }


    //提交
    function submitt() {
        var code = new Array();//供应商
        var aid = new Array();//app
        var order = new Array();//排序

        $("#form2 [name='CODE']").each(function () {//code
            var _code = $(this).val();
            code.push(_code);
        });
        $("#form2 [name='ORDER']").each(function () {
            var _order = $(this).val();
            if (_order == "" || _order == null) {
                _order = "n";
            }
            order.push(_order);
        });
        $("#form2 select[name=AID]").each(function () {
            var _aid = $(this).val();
            if (_aid == "" || _aid == null) {
                _aid = "n";
            }
            aid.push(_aid);
        });
        // 转成字符串
        aid = aid.toString();
        order = order.toString();
        code = code.toString();
        $.post("${pageContext.request.contextPath}/basedata/ekey/eKIndexSuppIndexAppAction!add.action",
                {aid: aid, order: order, code: code}, function (data) {
                    //window.location.reload();
                    history.back();
                });
    }
</script>
</body>
</html>


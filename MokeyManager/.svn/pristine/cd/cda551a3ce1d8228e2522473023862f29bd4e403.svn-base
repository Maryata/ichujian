<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,user-scalable=no">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <title>签到奖励</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
    <!--2015-4-29新样式，reCss.css样式一定要最后调用-->
    <script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
    <script src="${pageContext.request.contextPath}/ekpages/js/jquery.json.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/ekpages/layer/layer.js"></script>
</head>
<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; e键管理 -&gt; 签到奖励 </td>
    </tr>
</table>
<form id="form1" method="post">
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
        <tr>
            <td>
                <table width="100%"  border="0" cellspacing="1" cellpadding="2">
                    <tr>
                        <td width="8%"  height="23" class="biaodan-top" align="left">类型</td>
                        <td width="15%" class="biaodan-q" align="left">
                            <select style="width:100%;border:none;hight:100%;" id="type">
                                <option></option>
                                <option value="1">连续天数</option>
                                <option value="2">累计天数</option>
                            </select>
                        </td>
                        <td rowspan="2" align="right" class="biaodan-q">
                            <input type="button" class="butt" value="查 询" onclick="infoList()" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
    <tr>
    </tr>
</table>

<form id="form2" method="post">
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
        <thead>
        <tr class="biaodan-top">
            <td width="5%">ID</td>
            <td width="10%">天数</td>
            <td width="10%">奖励类型</td>
            <td width="10%">类型</td>
            <td width="10%">操作</td>
        </tr>
        </thead>
        <tbody id="mytable"></tbody>
        <input type="hidden" id="editId" name="editId">
        <input type="hidden" id="editDay" name="editDay">
        <input type="hidden" id="editScore" name="editScore">
        <input type="hidden" id="editType" name="editType">
    </table>
    <input type="hidden" id="cateTotal" />
    <div id="page11"></div>
</form>
<script type="text/javascript">
    $(function(){
        infoList();
    });
    function infoList(){
        var type = $("#type").val();
        // 查询应用总数
        $.post("${pageContext.request.contextPath}/basedata/ekey/eKRewardAction!list.action",{type:type},
                function(data){
                    var json = $.parseJSON(data);
                    var count = json[0].count;// 数据总数
                    $("#page11").empty();
                    if(count != 0){
                        setTotal(count,"cateTotal");
                        // 分页显示活动详情
                        getCatePage("${pageContext.request.contextPath}/basedata/ekey/eKRewardAction!list.action",type);
                    }
                });
        // 查询活动详情(第一页)
        $.post("${pageContext.request.contextPath}/basedata/ekey/eKRewardAction!list.action",{type:type},
                function(data){
                    var json = $.parseJSON(data);
                    var list = json[0].list;
                    $("#mytable").empty();
                    $(list).each(function(){
                        var _s = "<tr class='biaodan-q'><td align='center'>"+this.C_ID+"</td>";
                        _s += "<td align='center'>"+(this.C_DAYS||'')+"</td>";
                        _s += "<td align='center'>"+(this.C_SCORE||'')+"</td>";
                        _s += "<td align='center'>"+(this.TYPE||'')+"</td><td align='center'>";
                        _s += "<input type='button' value='修 改' class='butt' onClick='modifyCate("+this.C_ID+",\""+this.C_DAYS+"\",\""+this.C_SCORE+"\",\""+this.C_TYPE+"\")'/>";
                        _s += "</td></tr>";
                        $("#mytable").append(_s);
                    });
                });
    }
    // 设置总页数
    function setTotal(count,pageTotal){
        var totalPage = 0;
        var rows = 5;// 每页显示条数
        totalPage = 1;
        // 计算总页数
        totalPage = parseInt((count - 1) / rows) + 1;
        $("#"+pageTotal).val(totalPage);
    }
    // 分页显示活动详情
    function getCatePage(url,type) {
        laypage({
            cont: 'page11',
            pages: document.getElementById("cateTotal").value,
            curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
                var page = location.search.match(/page=(\d+)/);
                return page ? page[1] : 1;
            }(),
            jump: function(e, first){ //触发分页后的回调
                if(!first){ //一定要加此判断，否则初始时会无限刷新
                    $.post(url,{page:e.curr,type:type},
                            function(data){
                                var json = $.parseJSON(data);
                                var list = json[0].list;
                                $("#mytable").empty();
                                $(list).each(function(){
                                    var _s = "<tr class='biaodan-q'><td align='center'>"+this.C_ID+"</td>";
                                    _s += "<td align='center'>"+(this.C_DAYS||'')+"</td>";
                                    _s += "<td align='center'>"+(this.C_SCORE||'')+"</td>";
                                    _s += "<td align='center'>"+(this.TYPE||'')+"</td><td align='center'>";
                                    _s += "<input type='button' value='修 改' class='butt' onClick='modifyCate("+this.C_ID+",\""+this.C_DAYS+"\",\""+this.C_SCORE+"\",\""+this.C_TYPE+"\")'/>";
                                    _s += "</td></tr>";
                                    $("#mytable").append(_s);
                                });
                            });
                }
            }
        });
    }

    //编辑
    function modifyCate(id,day,score,type){
        $("#form2").attr("action","${pageContext.request.contextPath}/basedata/ekey/eKRewardAction!toUpdate.action");
        $("#editId").val(id);
        $("#editDay").val(day);
        $("#editScore").val(score);
        $("#editType").val(type);
        $("#form2").submit();
    }
</script>
</body>
</html>
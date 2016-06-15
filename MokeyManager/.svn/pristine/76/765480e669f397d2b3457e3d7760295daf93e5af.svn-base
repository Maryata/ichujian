<%--
  Created by IntelliJ IDEA.
  User: vpc
  Date: 2015/12/30
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!doctype html>
<html>
<head>
    <c:set var="basePath"><%=basePath%>
    </c:set>
    <meta charset="utf-8">
    <title>用户留言</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/messageStyle.css"/>
</head>
<body>
<div class="content">
    <h1 class="title">用户留言</h1>
    <input type="hidden" value="${uid}" id="i_uid">
    <input type="hidden" value="${message.count}" id="total">

    <div class="chat_box">
        <c:forEach items="${message.list}" var="m">
            <c:choose>
                <c:when test="${m['C_TYPE'] eq 1 }">
                    <div class="chat_ta">
                        <span class="time">${m['C_DATE']}</span>

                        <div class="chat_info">
                            <div class="name" style="width: 200px;">${m['C_NICKNAME']}</div>
                            <p>${m['C_MESS']}<c:if test="${not empty m['C_IMG']}"><img src="${m['C_IMG']}"></c:if></p>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="chat_wo">
                        <span class="time">${m['C_DATE']}</span>

                        <div class="chat_info">
                            <div class="name">客服</div>
                            <p>${m['C_MESS']}<c:if test="${not empty m['C_IMG']}"><img src="${m['C_IMG']}"></c:if></p>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
    <div class="history">聊天记录：<a class="page" href="javascript:void(0);" onclick="next();">上一页</a><a class="page" href="javascript:void(0);" onclick="prev();">下一页</a><a class="page" href="javascript:void(0);" onclick="last();">首页</a></div>
    <div class="text_box"><textarea id="mess" placeholder="输入信息..."></textarea></div>
    <a class="send" onclick="_send(this);" href="javascript:void(0);">发送信息</a>
    <input type="hidden" value="${basePath}" id="basePath"/>
</div>
<script type="text/javascript" charset="utf-8" src="${basePath }/js/jquery/jquery-1.11.1.min.js"></script>
<script>
    /**
     * Created by vpc on 2015/12/31.
     */
    ;
    (function (global) {
        "use strict";

        var Page = {};
        var pageSize = 10, totalPage = 0, total = 0, currentPage = 1;

        Page.init = function (v1, v2) {
            pageSize = v1 || pageSize;
            total = v2 || total;

            totalPage = global.Math.ceil(total / pageSize);
        };

        Page.getTotalPage = function () {
            return totalPage;
        };

        Page.hasNext = function () {
            return currentPage < totalPage;
        };

        Page.hasPrev = function () {
            return currentPage >= 2;
        };

        Page.setCurrentPage = function (i) {
            currentPage = i;
        };

        Page.getCurrentPage = function () {
            return currentPage;
        };

        global.Page = Page;
    })(window);
</script>
<script>
    var currentPage, total, pageSize = 5;
    var uid = 0;
    var basePath;
    $(function () {
        currentPage = 1;
        total = $('#total').val() || 1;
        uid = $('#i_uid').val() || 0;
        basePath = $("#basePath").val()||'';

        // 每页显示5个数据
        Page.init(pageSize, total);
        Page.setCurrentPage(currentPage);
    });

    function _send(obj) {
        var $mess = $('#mess');
        var mess = $mess.val();
        $('.chat_box').append('<div class="chat_wo"><span class="time">' + new Date().toLocaleString() + '</span><div class="chat_info"><div class="name">客服</div><p>' + mess + '</p></div></div>');
        $mess.val('');

        obj.disabled = 'disabled';

        var formAction = basePath + '/basedata/messageAction!ajaxSave.action';
        $.ajax({
            url         : formAction,
            data        : {'C_UID' : uid, 'C_MESS':mess},
            method        : 'POST',
            dataType	: 'JSON',
            success     : function(data, textStatus, jqXHR){
                if(data && data.status === 'Y') {

                } else {

                }

                obj.disabled = '';
            }
        });
    }

    // 下页, 和正常的上页一样，这边数据是反的
    function next() {
        console.debug('next1');
        if (!Page.hasNext()) {
            return false;
        }
        console.debug('next2');

        Page.setCurrentPage(Page.getCurrentPage() + 1);

        list({'start': (Page.getCurrentPage() - 1) * pageSize + 1, 'limit': pageSize, 'uid': uid});
    }

    // 上页,和正常的下页一样，这边数据是反的
    function prev() {
        console.debug('prev1');
        if (!Page.hasPrev()) {
            return false;
        }

        console.debug('prev2');

        Page.setCurrentPage(Page.getCurrentPage() - 1);

        list({'start': (Page.getCurrentPage() - 1) * pageSize + 1, 'limit': pageSize, 'uid': uid});
    }

    // 最后一页, 和正常的第一页一样，这边数据是反的
    function last() {
        if(Page.getCurrentPage() == Page.getTotalPage()) {
            return false;
        }

        Page.setCurrentPage(Page.getTotalPage());

        list({'start': (Page.getCurrentPage() - 1) * pageSize + 1, 'limit': pageSize, 'uid': uid});
    }

    var list = function (queryParam) {
        queryParam = queryParam || {};
        queryParam.start = queryParam.start || 0; // 默认0条开始
        queryParam.limit = queryParam.limit || 20; // 默认一页20数据
        var $chatBox = $('.chat_box');
        $chatBox.html('');
        $.ajax({
            type: 'GET',
            url: basePath + '/basedata/messageAction!ajaxListByUid.action',
            data: queryParam,
            async : false,
            dataType: 'json',
            success: function (data) {
                if (data && data.status === 'Y') {
                    var l = data.list || []; //  默认列表空
                    var count = data.count || 0; // 默认记录0
                    Page.init(pageSize, count);

                    for (var i = 0; i < l.length; ++i) {
                        var obj = l[i];

                        if (obj['C_TYPE'] == 1) {
                            $('<div class="chat_ta"><span class="time">' + obj['C_DATE'] + '</span><div class="chat_info"><div class="name" style="width: 200px;">' + obj['C_NICKNAME'] + '</div><p>' + obj['C_MESS'] + '<img src="' + (obj['C_IMG']||'') + '"></p></div></div>')
                                    .appendTo($chatBox);
                        } else {
                            $('<div class="chat_wo"><span class="time">' + obj['C_DATE'] + '</span><div class="chat_info"><div class="name">客服</div><p>' + obj['C_MESS'] + '</p></div></div>')
                                    .appendTo($chatBox);
                        }
                    }
                }
            }
        });
    };
</script>

</body>
</html>

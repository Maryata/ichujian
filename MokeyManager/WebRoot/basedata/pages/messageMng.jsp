<%--
  Created by IntelliJ IDEA.
  User: vpc
  Date: 2015/12/29
  Time: 14:28
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

<!DOCTYPE HTML>
<html>
<head>
  <c:set var="basePath"><%=basePath%></c:set>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <base href="${basePath}">
  <title>mokey后台管理系統--&gt留言管理</title>

  <link rel="stylesheet" type="text/css"
        href="${basePath}/css/reCss.css"/>
  <style>
    .tanchu_bg {
      display: none;
      background: #666;
      opacity: 0.5;
      filter: alpha(opacity=50);
      width: 100%;
      height: 100%;
      position: fixed !important;
      position: absolute;
      left: 0;
      top: 0;
      _top: expression(eval(documentElement.scrollTop+(document.documentElement.clientHeight-this.offsetHeight)/2));
      z-index: 88;
    }

    .tanchu {
      width: 870px;
      height: 420px;
      margin-left: -400px !important;
      margin-top: -180px !important;
      display: none;
      background: #fff;
      overflow: hidden;
      border: 1px solid #aaa;
      left: 50%;
      top: 50%;
      margin-top: 0;
      position: fixed !important;
      position: absolute;
      _top: expression(eval(documentElement.scrollTop+(document.documentElement.clientHeight-this.offsetHeight)/2));
      z-index: 99;
      position: 20;
    }

    .tanchu .tanchu-box {
      padding: 15px;
      color: #666;
    }

    .tanchu-title {
      padding: 10px 15px;
      background: #f3f3f3;
      color: #666;
      font-weight: bold;
      cursor: default;
    }

    .tanchu_close {
      color: #999;
      font-weight: 100;
      display: inline-block;
      float: right;
      cursor: pointer;
    }
  </style>
</head>
<body>
<form name="form1" method="post" action="">
  <table width="98%" height="35" border="0" align="center"
         cellpadding="0" cellspacing="0">
    <tr>
      <td class="tz_crumbs">
        <img src="${basePath}/images/crumbs.jpg" width="9" height="9"/>
        系统管理-&gt;留言管理 -&gt;
      </td>
    </tr>
  </table>

  <table width="98%" border="0" align="center" cellpadding="0"
         cellspacing="0" class="tbg">
    <tr>
      <td>
        <table width="100%" border="0" cellspacing="1" cellpadding="2">
          <tr>
            <td width="8%" class="biaodan-top" align="left">留言内容</td>
            <td width="20%" class="biaodan-q" align="left"><input name="name"
                                                                  onkeypress="return Message.runScript(event);"
                                                                  id="name"/></td>
            <td width="10%" rowspan="2" align="center" class="biaodan-q">
              <input type="button" class="butt"
                     onclick="Message.query({c_name : document.getElementById('name').value});"
                     value="查询"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>

  <table width="98%" height="35" border="0" align="center" cellpadding="1">
    <tr>
      <td align="right" class="jg_fc"></td>
    </tr>
  </table>

  <table id="dt" width="98%" border="0" align="center"
         cellpadding="0" cellspacing="1" class="tbg sortable">
    <tr class="biaodan-top">
      <td width='2%'>ID</td>
      <td width="10%">用户</td>
      <td width="20%">留言文字</td>
      <td width="10%">留言图片</td>
      <td width="10%">是否已回复</td>
      <td width="10%">操作</td>
    </tr>
  </table>
  <div id="page"></div>
  <input type="hidden" value="${basePath}" id="basePath"/>
</form>

</body>
<script type="text/javascript" charset="utf-8" src="${basePath }/js/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${basePath}/js/laypage/laypage.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath }/basedata/js/message.js"></script>
<script type="text/javascript">
  $(function () {
    Message.init($('#basePath').val(), laypage || {});
  });
</script>
</html>
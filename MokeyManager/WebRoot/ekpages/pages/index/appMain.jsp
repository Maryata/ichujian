
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
  <title>mokey后台管理系統--&gt应用管理</title>

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
        系统管理-&gt;应用管理 -&gt;
      </td>
    </tr>
  </table>

  <table width="98%" border="0" align="center" cellpadding="0"
         cellspacing="0" class="tbg">
    <tr>
      <td>
        <table width="100%" border="0" cellspacing="1" cellpadding="2">
          <tr>
            <td width="8%" class="biaodan-top" align="left">应用名称</td>
            <td width="20%" class="biaodan-q" align="left"><input name="name"
                                                                  onkeypress="return Index.runScript(event);"
                                                                  id="name"/></td>
            <td class="biaodan-top" align="left">是否有效</td>
            <td class="biaodan-q" align="left"><select name="isLive" id="isLive"><option value="1">是</option><option value="0">否</option></select></td>
            <td width="10%" rowspan="2" align="center" class="biaodan-q">
              <input type="button" class="butt"
                     onclick="Index.query({c_name : document.getElementById('name').value,c_isLive:document.getElementById('isLive').value});"
                     value="查询"/>
              <input type="button" class="butt" onclick="Index.openEdit();" value="添加" />
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
      <td width="10%">名称</td>
      <td width="20%">logo</td>
      <td width="5%">是否有效</td>
      <td width="10%">操作</td>
    </tr>
  </table>
  <div id="page"></div>
  <input type="hidden" value="${basePath}" id="basePath"/>
</form>
<div class="tanchu" style="display:none;">
  <div class="tanchu-title">首页应用编辑<span title="关闭" class="tanchu_close">关闭</span></div>
  <div class="tanchu-box" style="height:270px;overflow: auto;">
    <form id="editForm" enctype="multipart/form-data">
      <input type="hidden" name="C_ID"/>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
        <tr>
          <td>
            <table width="100%"  border="0" cellspacing="1" cellpadding="0">
              <tr>
                <td class="biaodan-top">名称：</td>
                <td class="biaodan-q"><input name="C_NAME"/></td>
                <td width="17%" height="23" class="biaodan-top">是否有效：</td>
                <td width="33%" class="biaodan-q"><select id="s_c_islive" name="C_ISLIVE"><option value="1">是</option><option value="0">否</option></select></td>
              </tr>
              <tr>
                <td width="17%" height="23" class="biaodan-top">logo地址：</td>
                <td width="33%" class="biaodan-q"><input type="file" name="C_IMG" /></td>
                <td class="biaodan-q" colspan="2"><span id="span_img"></span></td>
              </tr>
              <tr>
                <td class="biaodan-top">链接地址：</td>
                <td class="biaodan-q"><input name="C_URL"/></td>
                <td width="17%" height="23" class="biaodan-top">类型：</td>
                <td width="33%" colspan="3" class="biaodan-q">
                  <select id="s_c_type" name="C_TYPE">
                    <option value="0">第三方应用</option>
                    <option value="1">微用帮</option>
                    <option value="2">周边数码</option>
                    <%--<option value="3">一键购膜</option>--%>
                    <%--<option value="4">触宝电话</option>--%>
                    <option value="5">免费换膜</option>
                    <option value="6">定制app</option>
                  </select>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table width="100%" height="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center">
            <input type="button" class="butt" value="保存" onclick="Index.save(this);" />
            <input type="button" class="butt" value="取消" onclick="Index.closeEdit();" />
          </td>
        </tr>
      </table>
    </form>
  </div>
</div>
<div class="tanchu_bg" ></div>
<div id="div_ajax" style="margin-left: 300px"></div>
</body>
<script type="text/javascript" charset="utf-8" src="${basePath }/js/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${basePath}/js/laypage/laypage.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath }/ekpages/js/Index.js"></script>
<script type="text/javascript">
  $(function () {
    Index.init($('#basePath').val(), laypage || {});
  });
</script>
</html>
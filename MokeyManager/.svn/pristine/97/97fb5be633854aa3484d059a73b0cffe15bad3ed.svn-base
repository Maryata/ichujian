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
<title>在线状态列表</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<!--2015-4-29新样式，reCss.css样式一定要最后调用-->
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
<script src="${pageContext.request.contextPath}/ekpages/js/jquery.json.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/ekpages/layer/layer.js"></script>
</head>
<body >
<form id="form2" method="post">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
  <thead>
  <input type="hidden" id="fyAccountIds" name="fyAccountIds"/>
	  <tr class="biaodan-top">
		  <td width="5%">客户端注册的IP地址</td>
		  <td width="20%">飞语云账户ID</td>
		  <td width="10%">上线时间</td>
	  </tr>
  </thead>
  <tbody id="mytable">

  </tbody>
</table>
<input type="hidden" id="cateTotal" />
<div id="page11"></div>
</form>
<script type="text/javascript">
	$(function(){
		var fyAccountIds = $("#fyAccountIds", parent.document).val();
		$("#fyAccountIds").val(fyAccountIds);

		infoList();
	});
	function infoList(){
		var fyAccountIds=$("#fyAccountIds").val();
		$.post("${pageContext.request.contextPath}/basedata/ekey/eKFreeCallAction!onlineStatusList.action",{fyAccountIds:fyAccountIds},
				function(data){
					var json = $.parseJSON(data);
					var resultCode = json[0].resultCode;
					var resultMsg = json[0].resultMsg;
					var result = json[0].result;
					if(resultCode=="0"){
						$("#mytable").empty();
						if(result==""){
							var _tr="<tr class='biaodan-q'><td colspan='3' align='center'>未在线</td></tr>";
							$("#mytable").append(_tr);
						}
						$(result).each(function(){
							var _s = "<tr class='biaodan-q'>";
							if(result.fyAccountId ==""){
								_s += "<td colspan='3' align='center'>未在线</td>";
							}else{
								_s += "<td align='center'>"+(result.clientIp||'')+"</td>";
								_s += "<td align='center'>"+(result.fyAccountId||'')+"</td>";
								_s += "<td align='center'>"+(result.onlineTime||'')+"</td>";
							}
							_s += '</tr>';
							$("#mytable").append(_s);
						});

					}else{
						layer.msg(resultMsg, function(){
							layer.closeAll();
						});
					}
				});
	}


</script>
</body>
</html>


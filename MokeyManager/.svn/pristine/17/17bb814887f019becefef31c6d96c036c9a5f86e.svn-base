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
<title>未注册列表</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<!--2015-4-29新样式，reCss.css样式一定要最后调用-->
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
<script src="${pageContext.request.contextPath}/ekpages/js/jquery.json.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/ekpages/layer/layer.js"></script>
</head>
<body >
<form id="form1" method="post" action="">
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
		<tr>
			<td>
				<table width="100%"  border="0" cellspacing="1" cellpadding="2">
					<tr>
						<td rowspan="2" align="right" class="biaodan-q" colspan="2">
							<input type="button" class="butt" value="全部注册" onclick="register()" />
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
		  <td width="20%">APPID</td>
		  <td width="10%">手机号</td>
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
		infoList();
	});
	function infoList(){
		var fyAccountIds=$("#fyAccountIds").val();
		$.post("${pageContext.request.contextPath}/basedata/ekey/eKFreeCallAction!registerList.action",
				function(data){
					var json = $.parseJSON(data);
					var list = json[0].list;
					$("#mytable").empty();
					$(list).each(function(){
						var _s = "<tr class='biaodan-q'>";
						_s += "<td align='center'>"+(this.C_ID||'')+"</td>";
						_s += "<td align='center'>"+(this.C_REGID||'')+"</td>";
						_s += "<td align='center'>"+(this.C_PHONENUM||'')+"</td>";
						_s += '</tr>';
						$("#mytable").append(_s);
					});
				});
	}

	function register(){
		$.post("${pageContext.request.contextPath}/basedata/ekey/eKFreeCallAction!addAccount.action",{type:"2"}
				,function(data){
					window.location.reload();
				});
	}
</script>
</body>
</html>


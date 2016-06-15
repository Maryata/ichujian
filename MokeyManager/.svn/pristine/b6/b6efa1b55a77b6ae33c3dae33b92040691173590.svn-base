<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript">
function submitt(){
	var score = $("#score").val();
	if(null==score || ""==score || !$.isNumeric(score)){
		alert("奖励不能为空且必须为数字！")
		$("#score").focus();
		return;
	}
	$("#form1").attr("action","${pageContext.request.contextPath}/basedata/gameTaskAction!handleReward.action");
	$("#form1").submit();
}
</script>
</head>
<body>
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <!--2015-4-29新样式，面包屑-->
    <td width="62.7%" class="tz_crumbs"><img src="../../images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; 3号键管理 -&gt; 签到奖励 -&gt; 奖励修改  </td>
  </tr>
</table>
<br/>
<form id="form1" method="post">
	<table width="60%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	  <tr class="biaodan-q">
	  	<input type="hidden" id="id" name="id" value="${id }"/>
	  	<input type="hidden" id="days" name="days" value="${days }"/>
	    <td width="40%"  height="23" class="biaodan-top" align="left">连续天数</td>
	    <td width="60%" class="biaodan-q" align="center">
	    	<font size="4">${requestScope.days }</font>
	    </td>
	  </tr>
	  <tr class="biaodan-q">
	  	<td width="40%"  height="23" class="biaodan-top" align="left">奖励</td>
	  	<td width="60%" class="biaodan-q" align="center">
	  		<input type="text" id="score" name="score" value="${score }"
	  		 style="border:none; color:#636775; width:100%; height:30px; text-align:center; vertical-align:middle;" />
	  	</td>
	  </tr>
	  <tr class="biaodan-q" >
	    <td colspan="2" align="center"><input type="button" class="butt" value="保 存" onclick="submitt()"/></td>
	  </tr>
	</table>
</form>
</body>
</html>
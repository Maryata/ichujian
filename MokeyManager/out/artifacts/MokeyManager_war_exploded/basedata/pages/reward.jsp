<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,user-scalable=no">
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<title>游戏提示语</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script type="text/javascript">rootPath="${pageContext.request.contextPath}";</script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
</head>

<body >

<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <!--2015-4-29新样式，面包屑-->
    <td width="62.7%" class="tz_crumbs"><img src="../../images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; 3号键管理 -&gt; 签到奖励  </td>
  </tr>
</table>
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	  <tr> 
	   	<td width="10%"  height="23" class="biaodan-top" align="left">连续天数</td>
	    <td width="25%" align="center" class="biaodan-q">
	    	<input type="text" style="border:none; width:100%; height:100%" id="days" name="days"/>
	    </td>
	    <td width="10%"  height="23" class="biaodan-top" align="left">奖励</td>
	   	<td width="25%" class="biaodan-q" align="left">
			<input type="text" style="border:none; width:100%; height:100%" id="score" name="score" />
	 	</td>
	 	<td width="30%" class="biaodan-q" align="center">
			<input type="submit" class="butt" value="添 加" onclick="add()"/>
	 	</td>
	  </tr>
	</table>
<br>
<form id="form2" method="post">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
  <thead>
	  <tr class="biaodan-top">
	    <td width="35%">连续天数</td>
	    <td width="35%">奖励</td>
	    <td width="30%">操作</td>
	  </tr>
  </thead>
  <input type="hidden" id="editId" name="editId" />
  <input type="hidden" id="editDays" name="editDays" />
	<input type="hidden" id="editScore" name="editScore" />
  <input type="hidden" id="rewards" name="rewards" value="<s:property value="#request.rewards"/>" />
  <tbody id="mytable">
  	<s:if test="#request.rewards!=null && #request.rewards.size()>0">
	<s:iterator value="#request.rewards">
	<tr class="biaodan-q ">
		<td align="center"><s:property value="C_DAYS"/></td>
		<td align="center"><s:property value="C_SCORE"/></td>
		<td align="center">
		<input type="button" value="修 改" class="butt" onclick="handle(<s:property value="C_ID"/>,<s:property value="C_DAYS"/>,<s:property value="C_SCORE"/>)">
		<input type="button" value="删 除" class="butt" onclick="del(<s:property value="C_ID"/>)">
		</td>
	</tr>
	</s:iterator>
	</s:if>
  </tbody>
</table>
</form>
<script type="text/javascript">
function add(){
	var days = $("#days").val();
	var score = $("#score").val();
	if(null==days || ""==days || !$.isNumeric(days)){
		alert("连续天数不能为空且必须为数字！");
		$("#days").focus();
		return;
	}
	if(null==score || ""==score || !$.isNumeric(score)){
		alert("奖励不能为空且必须为数字！")
		$("#score").focus();
		return;
	}
	var existDays = $("#rewards").val();
	var flag = true;
	if(existDays != null && existDays.length > 0){
		existDays = existDays.replace(/\=/gi,"\:");
		existDays = eval("(" + existDays + ")");
		for (i=0; i<existDays.length; i++){
			var this_day = existDays[i].C_DAYS;
			if(this_day == days){
				alert("天数已经存在！");
				flag = false;
				return;
			}
		}
	}
	if(flag){
		$.post("${pageContext.request.contextPath}/basedata/gameTaskAction!addReward.action",
				{days:days,score:score},function(data){
			window.location.reload();
		});
	}
}
function del(id){
	if(confirm("确定删除吗？")){
		$.post("${pageContext.request.contextPath}/basedata/gameTaskAction!delReward.action",
				{id:id},function(data){
			window.location.reload();
		});
	}
}
function handle(id, days, score){
	$("#editId").val(id);
	$("#editDays").val(days);
	$("#editScore").val(score);
	$("#form2").attr("action","${pageContext.request.contextPath}/basedata/gameTaskAction!toHandle.action");
	$("#form2").submit();
}
</script>
</body>
</html>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../../css/reCss.css" />
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript">
function submitt(){
	var cid = $("#cid").val();
	var cname = $("#cname").val();
	var islive = $("#islive").val();
	$.post("${pageContext.request.contextPath}/basedata/h5GameColAction!h5ModifyCol.action",{cid:cid,cname:cname,islive:islive},function(data){
		window.opener.location.reload();
		window.close();  
	});  
}
</script>
</head>
<body>
<form id="updateForm" method="post">
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	  <tr class="biaodan-q">
	  	<input type="hidden" id="cid" name="cid" value="${param.cid }"/>
	    <td width="11.55%"  height="23" class="biaodan-top" align="left">合集名称：</td>
	    <td width="19.6%" class="biaodan-q" align="center">
	    	<input type="text" id="cname" name="cname" value="${param.cname }" style="border:none; color:#636775; width:100%; height:30px;" />
	    </td>
	  </tr>
	  <tr class="biaodan-q">
	  	<td width="11.55%"  height="23" class="biaodan-top" align="left">是否有效：</td>
	  	<td width="19.6%" class="biaodan-q" align="center">
	  		<select name="islive" id="islive" style="border:none; color:#636775; width:100%; height:30px;">
	  			<option value="1">有 效</option>
	  			<option value="0">无 效</option>
	  		</select>
	  	</td>
	  </tr>
	  <tr class="biaodan-q" >
	    <td colspan="2" align="center"><input type="button" class="butt" value="保 存" onclick="submitt()"/></td>
	  </tr>
	</table>
</form>
</body>
</html>
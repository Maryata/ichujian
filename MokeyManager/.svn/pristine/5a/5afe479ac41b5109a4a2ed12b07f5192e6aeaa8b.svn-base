<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css">
body {
	margin: 0px;
	padding: 0px;
	text-align:center;
	background-color: #F9F9F9;
	font-size: 12px;
	line-height: 140%;
}
a {
	text-decoration:none; color:#000; padding:0 5px;
}

ul {
	margin: 0px;
	padding: 0px;
	list-style-type: none;
}

li {
	display: block;
	background:#eee;
	width: 153px;
	height: 27px;
	font-size: 12px;
	vertical-align:middle;
	padding-left: 0px;
}

.menu {
	width: 153px;
	height: 27px;
	font-size: 12px;
	vertical-align:middle;
	padding-left: 0px;
	padding-top: 3px;
	cursor:pointer;
	background-image:url(../image/top_bombg.jpg);
}

</style>
<script type="text/javascript">
	function menuSwitch(divId) {
		var ob = document.getElementById(divId + "_ul");
		if (!ob.style.display || ob.style.display == ""
				|| ob.style.display == "block") {
			ob.style.display = "none";
		} else {
			ob.style.display = "block";
		}
	}
</script>
</head>
<body >
	<div class="left_div" >
		<div class="menu" >
			<a href="<%=request.getContextPath()%>/system/UpPassword.jsp" target="mainFrame" >修改密码</a>
		</div>
		<div class="menu" >
			<a href="<%=request.getContextPath()%>/system/pages/userInfoManager.jsp" target="mainFrame" >用户管理</a>
		</div>
		<div class="menu" >
			<a href="<%=request.getContextPath()%>/system/pages/roleManager.jsp" target="mainFrame" >角色管理</a>
		</div>
	</div>
	<div class="left_div" >
		<div class="menu" >
			<a href="<%=request.getContextPath()%>/system/pages/function.jsp" target="mainFrame" >功能管理</a>
		</div>
	</div>
</body>
</html>
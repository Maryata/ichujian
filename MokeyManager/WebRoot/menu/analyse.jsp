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
	border:1px dotted #ccc;
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
		<div class="menu" id="userGrowth" onclick="menuSwitch('userGrowth')">用户增长情况</div>
		<ul id="userGrowth_ul" style="display:none">
			<li><a href="<%=request.getContextPath()%>/analyse/userGrowth!getUserGrowthList.action" target="mainFrame" >日增长情况</a></li>
			<li><a href="#" >月增长情况</a></li>
		</ul>
		
		<div class="menu" id="userUse" onclick="menuSwitch('userUse')">用户使用情况</div>
		<ul id="userUse_ul" style="display:none">
			<li><a href="<%=request.getContextPath()%>/analyse/userUseInfo!getUserUseByDay.action" target="mainFrame" >日使用情况</a></li>
			<li><a href="<%=request.getContextPath()%>/analyse/userUseInfo!getUserUseByMonth.action" target="mainFrame" >月使用情况</a></li>
		</ul>
		
		<div class="menu" id="setAnn" onclick="menuSwitch('setAnn')">设置分析情况</div>
		<ul id="setAnn_ul" style="display:none">
			<li> <a href="<%=request.getContextPath()%>/analyse/setAppInfo.action" target="mainFrame">一键APP设置排名</a> </li>
			<li> <a href="<%=request.getContextPath()%>/analyse/holdTypeInfo.action" target="mainFrame">一键长按类型统计</a> </li>
			<li> <a href="<%=request.getContextPath()%>/anaylse/aKeyControl!getUseKeyList.action" target="mainFrame">一键操控方式统计</a> </li>
		</ul>
		
		<div class="menu" id="keyUse" onclick="menuSwitch('keyUse')">按键使用情况分析</div>
		<ul id="keyUse_ul" style="display:none">
			<li> <a href="<%=request.getContextPath()%>/anaylse/aKeyControl!getKeyUsageStatList.action" target="mainFrame">按键使用率统计</a> </li>
		</ul>
		
		<div class="menu" id="appUse" onclick="menuSwitch('appUse')">app使用情况分析</div>
		<ul id="appUse_ul" style="display:none">
			<li> <a href="<%=request.getContextPath()%>/analyse/appUseInfo.action" target="mainFrame">一键APP使用排名</a> </li>
			<li> <a href="<%=request.getContextPath()%>/analyse/newsAppUseInfoAction.action" target="mainFrame">一键新闻使用排名</a> </li>
		</ul>
	</div>

</body>
</html>
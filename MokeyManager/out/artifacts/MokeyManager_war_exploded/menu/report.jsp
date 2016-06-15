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
	width: 153px;
	height: 27px;
	font-size: 12px;
	vertical-align:middle;
	padding-left: 10px;
}

.menu {
	width: 153px;
	height: 27px;
	font-size: 12px;
	vertical-align:middle;
}

.left_div {
	height: 100%;
	width: 153px;
	padding-left: 5px;
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
	<div align="center" >
	   <table width="100%" border="0" cellpadding="0" cellspacing="0" background="../image/top_bombg.jpg">
         <tr>
         <td height="27" align="center" valign="middle">      
        <a href="../report/pages/reportdownload.jsp" target="mainFrame"  style="text-decoration:none; color:#000; padding:0 5px;">报表下载</a>
         </td>
        </tr>
      </table>
	</div>

</body>
</html>
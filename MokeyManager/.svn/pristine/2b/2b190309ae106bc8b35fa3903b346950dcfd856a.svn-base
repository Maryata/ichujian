<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<script type="text/javascript">rootPath ="${pageContext.request.contextPath}";</script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
	<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.selectseach.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$("select").selectseach();
		});
	</script>
</head>
<body>
<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
	<tr>
		<td align="left" class="jg_fc">首页分类管理&nbsp;-&gt;&nbsp;</td>
	</tr>
</table>
<form name="form0" method="post">
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
		<tr>
			<td>
				<table width="100%"  border="0" cellspacing="1" cellpadding="2">
					<tr>
						<td width="12.1%" height="23" class="biaodan-top" align="left">活动名称：</td>
						<td width="23.8%" class="biaodan-q" align="left">
							<input type="text" name="title" style="border:none; width:100%; height:100%" id="title1" />
						</td>
						<td align="center" class="biaodan-q">
							<input name="Button" type="button" class="butt" value="查 询" onclick="infoList()" />
						</td>
					</tr>
				</table></td>
		</tr>
	</table>
</form>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	<thead>
	<tr class="biaodan-top">
		<td width="12.1%">选择</td>
		<td >名称</td>
	</tr>
	</thead>
	<tbody id="table1"></tbody>
</table>
<input type="hidden" id="giftTotal" />
<div id="page11"></div>
<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
	<tr biaodan-q>
		<td width="40%" align="right">
			<a onclick="down()"><img src="${pageContext.request.contextPath}/image/A2.jpg"></img></a>
			<a onclick="up()"><img src="${pageContext.request.contextPath}/image/A2-2.jpg"></img></a>
		</td>
		<td width="10%"></td>
		<td width="40%" align="left">
			<a onclick="allDown()"><img src="${pageContext.request.contextPath}/image/A3.jpg"></img></a>
			<a onclick="allUp()"><img src="${pageContext.request.contextPath}/image/A3-2.jpg"></img></a>
		</td>
	</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
	<tr>
		<td>
			<table width="100%"  border="0" cellspacing="1" cellpadding="2">
				<tr>
					<td width="12.1%"  height="23" class="biaodan-top" align="left">名称：</td>
					<td width="61%" class="biaodan-q" align="left">
						<input type="text" name="indexName" style="border:none; width:100%; height:100%" id="title2" />
					</td>
					<td align="center" class="biaodan-q">
						<input name="Button" type="button" class="butt" value="查 询" onclick="currList()" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<form id="form1" method="post">
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
		<thead>
		</tr>
		<tr class="biaodan-top">
			<td width="12.1%">选择</td>
			<td width="31%">名称</td>
			<td >排序</td>
		</tr>
		</thead>
		<tbody id="table2"></tbody>
	</table>
	<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
		<tr>
			<td align="center">
				<input type="button" value="提 交" class="butt" onclick="submitt()" />
			</td>
		</tr>
	</table>
	<input type="hidden" id="cateTotal" />
	<div id="page22"></div>
</form>
<script src="${pageContext.request.contextPath}/ekpages/js/ekActivityIndexCategory-handle.js" type="text/javascript"></script>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <c:set var="basePath"><%=basePath%></c:set>
    <title>My JSP 'main.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  	<form action="" id="form0">
  		用户名 ：<input name="name" id="name" type="text"/>
  		用户代码	：<input name="code" id="code" type="text"/>
  		<input type="button" value="查询"/>
  	</form>
  	
  	<table>
  		<tr>
  			<th>用户代码</th>
  			<th>用户名</th>
  		</tr>
  		<c:forEach items="${pg.rows}" var="obj">
  		<tr>
  			<td>${ obj.C_USERCODE }</td>
  			<td>${ obj.C_USERNAME }</td>
  		</tr>
  		</c:forEach>
  	</table>
  	<input type="hidden" id="total" value="${ pg.totalPages }" />
  	<div id="page11"></div>
  	<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/laypage/laypage.js"></script>
    <script type="text/javascript">
    	$(function() {
			laypage({
			    cont: 'page11',
			    pages: document.getElementById("total").value,
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	var url = location.href;
			    		var index = url.indexOf("?");
			    		if(index !== -1)
			    			url = url.substring(0,index);
			    		
			            location.href = url + '?page='+e.curr + "&" + $("#form0").serialize();
			        }
			    }
			});
    	});
    	
    </script>
  </body>
</html>

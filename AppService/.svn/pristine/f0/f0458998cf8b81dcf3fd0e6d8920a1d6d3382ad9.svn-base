<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/md5.js"></script>
	<script type="text/javascript" src="js/Base64.js"></script>
	
	<script type="text/javascript">
	function submitForm(){
		var b = new Base64(); 
		var secretKey = "ichujian";
		form.version.value = b.encode(form.version.value);
		form.sourec.value = b.encode(form.sourec.value);
		var sign = 'cjcc'
			+'sourec'+form.sourec.value
			+'version'+form.version.value
			+'qjkjxxjsyxgs';
		form.sign.value = hex_md5(sign).toUpperCase();;
		form.submit();
	}
	
	</script>
  </head>
  
  <body> 
    upload app<br>
    <form method="post" action="app/update.action" name="form">
	    version:<input type="text" name="version" value="2">
	    sourec:<input type="text" name="sourec" value="ichuhjian">
	    <input type="text" name="sign" value="">
	    &nbsp;
	    <input type="button" value="submit" name="button" onclick="submitForm()">
    </form>
 </body>
</html>

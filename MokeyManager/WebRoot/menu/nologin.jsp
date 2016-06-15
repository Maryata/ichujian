<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="css/css.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="15" rowspan="3"></td>
    <td></td>
    <td width="15" rowspan="3"></td>
  </tr>
  <tr>
    <td height="100%" ><div align="center"></div>
        <table border="0" align="center" cellpadding="0" cellspacing="0" class="red">
          <tr>
            <td><div align="center"><img src="images/error_top.jpg" ></div></td>
          </tr>
          <tr>
            <td height="203" background="images/error_mid.jpg"><div align="center">对不起! 您还没有登陆或者没有权限访问!<c:out value="${errmessage}"/></div></td>
          </tr>
          <tr>
            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          
            <a href="index.action" target="_top"><img src="images/error_back.jpg" width="75" height="23" border="0"></a>
         
            </td>
          </tr>
    </table></td>
  </tr>
  <tr>
    <td></td>
  </tr>
</table>
</body>
</html>

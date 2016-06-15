<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String result = (String)request.getAttribute("result");
%>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>mokey后台管理系統</title>
<link rel="stylesheet" type="text/css" href="../css/css.css" />
<script type="text/JavaScript">
function keydown(){
  //如果是回车键
  if(window.event.keyCode==13){		
    submitForm();
  }
}
 function submitForm() {
   if (updateForm.oldPassword.value == ""){
    	alert("请输入原密码!");
    	updateForm.oldPassword.focus();
    	return false;
    }
    if (updateForm.newPassword.value == ""){
    	alert("请输入新密码!");
    	updateForm.newPassword.focus();
    	return false;
    }
    if (updateForm.newPassword2.value == ""){
    	alert("请输入确认新密码!");
    	updateForm.newPassword2.focus();
    	return false;
    }
    if (updateForm.newPassword.value != updateForm.newPassword2.value){
    	alert("确认密码不一致!");
    	updateForm.newPassword2.focus();
    	return false;
    }
    updateForm.submit();
  }
</script>
</head>


<body>
<form name="updateForm" method="post" action="<%=request.getContextPath()%>/system/sysUser!UpdatePass.action">
<table width="100%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../image/dian.jpg" width="9" height="9" /> 系统管理 -&gt; 修改密码</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="1" cellspacing="0" class="tbg">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
            <tr>
              <td height="23" class="biaodan-top" align="left">原密码</td>
              <td class="biaodan-q"><div align="center">
                <input type="text" name="oldPassword" id="oldPassword">
              </div></td>
            </tr>
            <tr>
              <td height="23" class="biaodan-top" align="left">新密码</td>
              <td class="biaodan-q"><div align="center">
                <div align="center">
                  <input type="text" name="newPassword" id="newPassword">
                </div>
              </div></td>
            </tr>
              <tr>
              <td height="23" class="biaodan-top" align="left">确认新密码</td>
              <td class="biaodan-q"><div align="center">
                <div align="center">
                  <input type="text" name="newPassword2" id="newPassword2">
                </div>
              </div></td>
            </tr>
          <tr> 
              <td height="23" class="biaodan-top" align="left">&nbsp;</td>
         <td class="biaodan-q"><div align="center">
           <input name="button" type="submit" class="butt" onClick="return submitForm()" value="修 改"/>
           <FONT color="blue"><c:out value="${result}" /></FONT>
         </div></td>
          </tr>
        </table></td>
    </tr>
  </table>
</form>
</body>
</html>

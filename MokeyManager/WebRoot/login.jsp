<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String result = (String)request.getAttribute("result");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 <style>
.loginBgGround{
background-repeat:no-repeat;
background-position:center;
background-image:url(image/back.jpg);
background-attachment:fixed;
width:100%;
height:100%;
}
.divImg{
width:100%;
height:100%;
position:absolute;
z-index:-1;
margin: 0;
}

.divTB{
width:100%;
height:100%;
position:absolute;
z-index:1;
margin: 0;
}

</style>
    <title>登录</title>
<link rel="icon" href="image/favicon.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="image/favicon.ico" type="image/x-icon"/> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<link href="css/style.css" type=text/css rel=stylesheet>
	<script type="text/JavaScript">
<!--

function MM_preloadImages() { //v3.0
	  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
	    document.getElementById('userName').focus();
	}
	function MM_swapImgRestore() { //v3.0
	  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
	}
	function MM_findObj(n, d) { //v4.01
	  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
	    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
	  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
	  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
	  if(!x && d.getElementById) x=d.getElementById(n); return x;
	}

	function MM_swapImage() { //v3.0
	  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
	   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
	}
function go(){
    if (loginForm.userName.value == ""){
    alert("请输入用户号!");
    loginForm.userName.focus();
    return false;
    }
  if (loginForm.password.value == ""){
    alert("请输入密码!");
    loginForm.password.focus();
    return false;
    }
 }
 document.onkeydown=keydown;
		
function keydown(){
  //如果是回车键
  if(window.event.keyCode==13){		
    submitForm();
  }
}
 function winOpenEdit(url){
  var tempAttr="height=200,width=420,scrollbars=no,status=yes,toolbar=no,directories=no,menubar=no,location=no,resizable=no,left=0,top=";
  tempAttr+=(window.screen.Height-500)/2;
  tempAttr+=",left=";
  tempAttr+=(window.screen.Width-850)/2;
  window.open(url,"",tempAttr,"");
}
 function submitForm() {
   if (loginForm.username.value == ""){
    alert("请输入账号!");
    loginForm.userName.focus();
    return false;
    }
  if (loginForm.password.value == ""){
    alert("请输入密码!");
    loginForm.password.focus();
    return false;
    }
  	//alert(loginForm.action);
    loginForm.submit();
  }
 function resetForm(){
	loginForm.reset();
 }
//-->
</script>
  </head>
  
<body>

<div style=" width:100%;position: relative; height:100%;">
<img src="image/back.jpg" style="width:100%; height:100%;" />
 <form name="loginForm" action="<%=request.getContextPath()%>/login!login.action"
 method="post" style="text-align: left;position: absolute; left: 14%;top: 60%; width:35%;">   

        	<!-- <table bordercolor="black" align="center" width="100%" height="100%"  class="divTB">
            	<tr height="47%">
                	<td colspan="7" width="100%">
                    </td>                
                </tr>
             	<tr height="1.875%">
                	<td colspan="4" width="65.1%" >	
                    </td>
                    <td width="6.5%">
                    </td> 
                    <td colspan="2" width="27%">
                    </td>                                               
                </tr>
            	<tr height="4.375%">
                	<td colspan="7" width="100%">
                    </td>                
                </tr>  
                	<tr height="1.875%">
                	<td colspan="4" width="65.1%" >	
                    </td>
                    <td width="6.5%">
                    </td> 
                    <td colspan="2" width="27%">
                    </td>                                               
                </tr>
            	<tr height="4.375%">
                	<td colspan="7" width="100%">
                    </td>                
                </tr>
                	<tr height="1.875%">
                	<td colspan="4" width="65.1%" >	
                    </td>
                    <td width="6.5%">
                    </td> 
                    <td colspan="2" width="27%">
                    </td>                                               
                </tr>
            	<tr height="4.375%">
                	<td colspan="7" width="100%">
                    </td>                
                </tr> 
                 <tr>
        <td width="15%" height="35">&nbsp;</td>
        <td width="28%" height="35">&nbsp;</td>
        <td width="27%">&nbsp;</td>
        <td width="30%">&nbsp;</td>
      </tr>
      <tr>
        <td height="35"></td>
        <td style=" color:#ffffff">用户名：
		</td>
		   <td height="50" align="center">
        &nbsp;&nbsp;&nbsp;
		</td>
		</tr> 
		<tr>
		 <td height="35"></td>
        <td style=" color:#ffffff">
        </td>
        <td height="50" style=" color:#ffffff" align="center">
        &nbsp;&nbsp;&nbsp;&nbsp;
		</td>
      </tr>                            
                          
                
            </table>-->
            <div>
            	<div style="clear:both; overflow:hidden;">
            		<span style="color:#fff; width:15%; text-align:right; display:block; float:left; line-height:30px; margin-right:5%;">账号</span>
	            	<input style="width:70%; max-width:200px; height:25px; font-size:14px; line-height:25px; float:left;" type="text" id="username" class="inputTextStyle" name="username" value="">
		           
            	</div>
            	<div style="margin:20px 0 0;clear:both; overflow:hidden;">
            		<span style="color:#fff; width:15%; text-align:right; display:block; float:left; line-height:30px;margin-right:5%;">密码</span>
		            <input style="width:70%; max-width:200px; height:25px; font-size:14px; line-height:25px; display:block; float:left;"  type="password" name="password" class="inputTextStyle"  id="password" value="">
		         
            	</div>
            	<div style="margin-top: 3%"> 
            		<a style="margin:1% 0 0 20%; width:10%;" href="#" onclick="return submitForm()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image2','','image/login02.png',1)">
		            	<img height="21" width="65" border="0" align="absmiddle" src="image/login01.png" alt="登录" name="Image2" id="Image2">
		            </a>
		            <a style="margin:1% 0 0 10%; width:10%;" href="#" onclick="return resetForm()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image3','','image/reset02.png',1)">
		            	<img height="21" width="65" border="0" align="absmiddle" src="image/reset01.png" alt="重置" name="Image3" id="Image3">
		           </a>  <FONT color="blue"><c:out value="${result}" /></FONT>
	            </div>
            </div>
</form>
</div>

</body>
</html>
</html>

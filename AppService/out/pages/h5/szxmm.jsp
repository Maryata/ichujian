<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<c:set var="basePath"><%=basePath%></c:set>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content ="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<title>设置新密码</title>
<link href="${basePath }/resource/h5/css/reset.css" rel="stylesheet">
<link href="${basePath }/resource/h5/css/style.css" rel="stylesheet">

</head>

<body>
<div class="title_box"><a class="btn_arr" href="${ basePath }/weixin/sendCode.action"></a>设置新密码</div>
<div class="content">
  <form action="">
  	<input type="hidden" value="${phoneNumber}" id="phonenum">
	  <p class="line">
	    <input class="" type="password" placeholder="请输入密码" id="pass" >    
	    <input type="button" class="close-btn" id="passBtn"  value=" ">    
	  </p>
	  <p><span class="hint">密码由8~32位数字、字母组成。</span></p>
	  <p>
	    <input type="button" class="enter-btn" id="enter" value="确定 " disabled="disabled">
	  </p>
	  <!-- 
	  <p style="margin-top:50px;"><a id="test2">新密码设置成功-提示！</a></p>
	  <p style="margin-top:50px;"><a id="test1">手机号已注册-提示！</a></p> -->
  </form>
</div>

<script src="${basePath }/resource/h5/js/jquery-2.1.4.js"></script>
<script src="${basePath }/resource/h5/layer.m/layer.m.js"></script>
<script>
<!--input输入字符后弹出叉-->
$(document).ready(function(){
  $("#pass").keyup(function(){
	if(document.getElementById('pass').value.length > 0){
	document.getElementById('passBtn').style.display = 'block';
	}else{
	document.getElementById('passBtn').style.display = 'none';
	}
  });
    
  $("#passBtn").click(function(){
     document.getElementById('pass').value = '';
     document.getElementById('passBtn').style.display = 'none';
   });  
});
</script>

<!--该手机号码已注册-提示！-->
<script>
$('#test1').on('click', function(){
	layer.open({
    content: '<span class="ico-cue"></span>  <span class="f-s18">该手机号码已注册</span>',
    btn: ['确定'],
	shadeClose:false,//是否点击遮罩时关闭层		
});
});
</script>
<!--账号注册成功-提示！-->
<script>
$('#test2').on('click', function(){
	layer.open({
    content: '<span class="ico-pass"></span>  <span class="f-s18">新密码设置成功！</span>',
    btn: ['完成'],
	shadeClose:false,//是否点击遮罩时关闭层		
});
});
</script>
<!--input输入字符等于11位且密码大于7位时激活下一步按钮-->
<script>
$(document).ready(function(){
  $("#pass").keyup(function(){
	_0();
  });
  
  _0();
  
  function _0() {
	  if($("#pass").val().length>7){
		 document.getElementById("enter").disabled = false;
		}else{
		 document.getElementById("enter").disabled = true;
		}
  }
  
  $('#enter').on('click',function() {
	  var phonenum = $('#phonenum').val();
	  var pwd = $('#pass').val();
	  
	 $.ajax({
		 async : false,
		 url : '/AppService/weixin/setpwdoffgt.action',
		 type : 'post',
		 data : { phonenum : phonenum, pwd : pwd},
		 dataType : 'json',
		 success : function(data) {
			 if(data && data.status === 'Y') {
					//setCookie('username',phonenum,365);
					window.location.href = '/AppService/weixin/index.action';
			 } else {
				showMsg('设置密码错误!');
			 }
		 }
	 }) ;
  });
}); 

function showMsg(msg) {
	layer.open({
	    content: '<span class="ico-cue"></span>  <span class="f-s18">'+msg+'</span>',
	    btn: ['确定'],
		shadeClose:false,		
	});
}

function setCookie(c_name,value,expiredays) {
	var exdate=new Date();
	exdate.setDate(exdate.getDate()+expiredays);
	document.cookie=c_name+ "=" +escape(value)+
	((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}
</script>
</body>
</html>

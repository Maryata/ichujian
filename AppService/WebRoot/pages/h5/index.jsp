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
<title>登录</title>
<link href="${basePath }/resource/h5/css/reset.css" rel="stylesheet">
<link href="${basePath }/resource/h5/css/style.css" rel="stylesheet">

</head>

<body>
<div class="title_box"><a class="btn_close" href="#"></a>登录趣游戏<a class="btn_reg" href="${ basePath }/weixin/register.action">注册</a></div>
<div class="content">
	<form action="${ basePath }/weixin/login.action" method="post">
	  <p class="line">
	    <input class="phone-ico" name="phonenum" type="text" placeholder="手机号码" id="phone" autofocus="autofocus" >
	    <input type="button" class="close-btn" id="phoneBtn"  value=" ">
	  </p>
	  <p class="line">
	    <input class="pass-ico" name="pwd" type="password" placeholder="密码" id="pass" >    
	    <input type="button" class="close-btn" id="passBtn"  value=" ">
	  </p>
	  <p>
	    <input type="button" class="enter-btn" onclick="login();" id="enter" value="登录 ">
	  </p>
	  <p>
	    <a class="forget" href="${ basePath }/weixin/pwdoffgt.action">忘记密码？</a>
	    <!-- <p style="margin-top:50px;"><a id="test1">手机号码不存在-提示！</a></p> -->
	  </p>
	</form>
</div>

<script src="${basePath }/resource/h5/js/jquery-2.1.4.js"></script>
<script src="${basePath }/resource/h5/layer.m/layer.m.js"></script>
<!--input输入字符后弹出叉-->
<script>
$(document).ready(function(){
  $("#phone").keyup(function(){
	if(document.getElementById('phone').value.length > 0){
	document.getElementById('phoneBtn').style.display = 'block';
	}else{
	document.getElementById('phoneBtn').style.display = 'none';
	}
  });
    
  $("#phoneBtn").click(function(){
     document.getElementById('phone').value = '';
     document.getElementById('phoneBtn').style.display = 'none';
   });  
});
</script>
<script>
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
<!--手机号码不存在-提示！-->
<script>
$('#test1').on('click', function(){
	layer.open({
    content: '<span class="ico-cue"></span>  <span  class="f-s18">手机号码不存在</span>',
    btn: ['确定'],
	shadeClose:false,//是否点击遮罩时关闭层		
});
});
</script>
<!--input输入字符等于11位且密码大于7位时激活下一步按钮-->
<script>
$(document).ready(function(){
  $("#phone,#pass").keyup(function(){
	  _0();
  });
  
  //_0();
  
  function _0() {
	  if($('#phone').val().length==11 && $("#pass").val().length>7){
		 document.getElementById("enter").disabled = false;
		}else{
		 document.getElementById("enter").disabled = true;
		}
  }
});
function showMsg(msg) {
	layer.open({
	    content: '<span class="ico-cue"></span>  <span class="f-s18">'+msg+'</span>',
	    btn: ['确定'],
		shadeClose:false,		
	});
}
function login() {
	var phone = $('#phone').val();
	var pass = $('#pass').val();
	
	if(!checkMobile(phone)) {
		showMsg('手机号码格式不正确!');
		
		return false;
	}
	
	if(!pass || pass.length <=7 || !checkPwd(pass)) {
		showMsg('密码由8~32位数字、字母组成!');
		
		return false;
	}
	
	if(checkPhoneNumberIsExist(phone)) {
		showMsg('该手机号码没注册!');
		
		return false;
	}
	
	$.ajax({
		url : '/AppService/weixin/login.action',
		type : 'post',
		async : false,
		data : {phonenum:phone,pwd:pass},
		dataType : 'json',
		success : function(data) {
			if(data && data.status === 'Y') {
				setCookie('username',data.userinfo[0].C_ID,365);
				window.location.href = '/AppService/weixin/list.action';
			} else {
				showMsg('帐号或密码错误!');
			}
		},
		error : function(data) {
			
		}
	});
}

function setCookie(c_name,value,expiredays) {
	var exdate=new Date();
	exdate.setDate(exdate.getDate()+expiredays);
	document.cookie=c_name+ "=" +escape(value)+
	((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}

function checkPhoneNumberIsExist(phone) {
	var result = false;
	$.ajax({
		async : false,
		url : '/AppService/weixin/validatePhone.action',
		data : {phoneNum : phone},
		dataType : 'json',
		success : function( data ) {
			if(data && data.status === 'Y') {
				result = true;	
			}
		},
		error : function( data ) {
			
		}
	});
	
	return result;
}

function checkPwd(val) {
	//var p = /^[A-Za-z0-9]+$/;
	var p = /[\s\.\u4e00-\u9fa5]/;
	
	return !p.test(val);
}

/**
 * 校验手机号码格式
 */
function checkMobile(val) {
	//中国移动号段 134、135、136、137、138、139、150、151、152、157、158、159、147#、182、183、184*、187*、188
	//中国联通号段 130、131、132、145#、155、156、185、186
	//中国电信号段 133 、153 、180 、181 、189
	//var pattern = /^(?:13\d|15[89])-?\d{5}(\d{3}|\*{3})$/;
	//var pattern = /^1[3|4|5|8][0-9]\d{4,8}$/;
	var pattern = /^((13[0-9])|(14[5,7])|(15[^4,\D])|(18[0,1,2,3,4-9])|(17[7]))\d{8}$/;
	
	return pattern.test(val);
}
</script>
</body>
</html>
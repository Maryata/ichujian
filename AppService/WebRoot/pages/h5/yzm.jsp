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
<title>输入验证码</title>
<link href="${basePath }/resource/h5/css/reset.css" rel="stylesheet">
<link href="${ basePath }/resource/h5/css/style.css" rel="stylesheet">
</head>
<body class="verify-bg">
<div class="title_box"><a class="btn_arr" href="${ basePath }/weixin/register.action"></a>输入验证码</div>
<form method="post" action=""  ng-app="myApp" ng-init="myBtn=true">
	<input type="hidden" value="${ type }" id="i_type">
  <div class="content" >
    <p>
      <span class="hint">我们已经发送验证码到：</span>
      <span class="hint bor-bot f-w" id='phoneNumber'>${ phoneNumber }</span>     
    </p>
    <p class="line">
      <input class="" type="text" name="code" placeholder="请输入验证码" id="code"  autofocus="autofocus" >
      <a class="verify-time" id='verify-time0'>120秒后重新发送</a>
      <a class="verify-time" id='verify-time1' style='display: none'>发送</a>
    </p>
    <p>
      <input type="button" class="enter-btn"  id="enter" onclick="next();" value="下一步 " disabled="disabled" ><!--disabled="disabled"-->
    </p>
    <%-- <p style="margin-top:50px;"><a id="test2">账号注册成功-提示！</a></p>
    <p style="margin-top:50px;"><a id="test1">验证码错误或失效-提示！</a></p>
    <p style="margin-top:50px;"><a href="${ basePath }/weixin/pwdoffgt.action">上一步（返回忘记密码）</a></p>
    <p style="margin-top:50px;"><a href="${ modifypwd }/weixin/modifypwd.action">设置新密码</a></p> --%>
  </div>
</form>
<!--<script src="http://apps.bdimg.com/libs/angular.js/1.3.9/angular.min.js"></script>-->
<script src="${ basePath }/resource/h5/js/jquery-2.1.4.js"></script>
<script src="${ basePath }/resource/h5/layer.m/layer.m.js"></script>
<!--验证码错误或失效-提示！-->
<script>
$('#test1').on('click', function(){
	layer.open({
    content: '<span class="ico-cue"></span>  <span class="f-s18">验证码错误或失效</span>',
    btn: ['确定'],
	shadeClose:false,//是否点击遮罩时关闭层		
});
});
</script>
<!--账号注册成功-提示！-->
<script>
$('#test2').on('click', function(){
	layer.open({
    content: '<span class="ico-pass"></span>  <span class="f-s18">恭喜，您的账号注册成功！</span>',
    btn: ['进入游戏帮'],
	shadeClose:false,//是否点击遮罩时关闭层		
});
});
</script>
<!--input输入字符时激活下一步按钮-->
<script>
$(document).ready(function(){
  $("#code").keyup(function(){
	_c0();
  });
  
  _c0();
  
  function _c0() {
	  if(document.getElementById('code').value.length > 0){
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

function layerConfirm(phone) {
	layer.open({
		title: [
        '确认手机号码',
        'font-weight: bold;'
    	],
	    content: '<span class=" width100 f-s18 color777 dis-b">我们会把短信验证码发到：</span>  <span class="f-s18  f-w">'+phone+'</span>',
	    btn: ['确定', '取消'],
		shadeClose:false,		
		yes : function(index) {
		    layer.close(index);
		    
		    // 发送验证码
		    location.href = '/AppService/weixin/sendCode.action?type=1&phone=' + phone;//yzm.html';
		},
		no : function(index) {
		    layer.close(index);
		}
	});
}

function next() {
	var phone = $('#phoneNumber').text();
	var code = $('#code').val();
	var  type = $('#i_type').val();
	
	if(type == 1) {
		// 注册
		$.ajax({
			async : false,
			url : '/AppService/weixin/reg.action',
			data : {phonenum : phone,valicode : code},
			dataType : 'json',
			success : function( data ) {
				if(data && data.status === 'Y') {
					layer.open({
					    content: '<span class="ico-pass"></span>  <span class="f-s18">恭喜，您的账号注册成功！</span>',
					    btn: ['进入游戏帮'],
						shadeClose:false,//是否点击遮罩时关闭层
						yes : function(index) {
							layer.close(index);
							
							setCookie('username',data.userinfo[0].C_ID,365);
							location.href='/AppService/weixin/list.action';
						}
					});
				} else {
					showMsg('验证码错误或失效');
				}
			},
			error : function( data ) {
				console.log('error : ' + data);
			}
		});
	} else {
		// 忘记密码
		$.ajax({
			async : false,
			url : '/AppService/weixin/isvalidcode.action',
			data : {phonenum : phone,valicode : code, type : type},
			dataType : 'json',
			success : function( data ) {
				if(data && data.status === 'Y') {
					location.href = '/AppService/weixin/modifypwd.action?phoneNumber=' + phone + '&code=' + code;
				} else {
					showMsg('验证码错误或失效');
				}
			},
			error : function( data ) {
			}
		});
	}
}

$(function() {
    _0();
    
    $('#verify-time1').on('click',function() {
    	layerConfirm($('#phoneNumber').text());
    	
    	$(this).css('display','none');
    	
    	_0();
    });
});

function _0() {
	var wait = 120;  //定义倒计时- 秒
    var self = $('#verify-time0');
    self.css('display','');
	
    timer(self);
    function timer(obj){
    	if(wait == 0) {
    		$('#verify-time1').css('display','');
    		obj.css('display','none');
    	} else {
    		obj.text(wait + '秒后重新发送');
            wait--;
            setTimeout(function () {
                timer(obj);
            },1000);	
    	}
    };
}

function setCookie(c_name,value,expiredays) {
	var exdate=new Date();
	exdate.setDate(exdate.getDate()+expiredays);
	document.cookie=c_name+ "=" +escape(value)+
	((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}

function getCookie(c_name) {
	if (document.cookie.length>0) {
	  c_start=document.cookie.indexOf(c_name + "=");
	  if (c_start!=-1) { 
	    c_start=c_start + c_name.length+1;
	    c_end=document.cookie.indexOf(";",c_start);
	    if (c_end==-1) c_end=document.cookie.length;
	    return unescape(document.cookie.substring(c_start,c_end));
	  } 
	}
	return "";
}

function checkCookie() {
	username=getCookie('username');
	if (username!=null && username!="") {
		
	} else {
  	}
}

</script>

</body>
</html>

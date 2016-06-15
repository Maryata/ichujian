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
<title>忘记密码</title>
<link href="${basePath }/resource/h5/css/reset.css" rel="stylesheet">
<link href="${basePath }/resource/h5/css/style.css" rel="stylesheet">
</head>
<body>
<div class="title_box"><a class="btn_arr" href="${ basePath }/weixin/index.action"></a>忘记密码</div>
<form method="post" id="form0" action="${ basePath }/weixin/sendCode.action"  ng-app="myApp" ng-init="myBtn=true">
	<input type="hidden" name="type" value="2">
  <div class="content" >
    <p class="line">
      <input class="" type="text" name="phonenum" placeholder="请输入手机号码" id="phone"  autofocus="autofocus"  >
      <input class="close-btn" type="button" name="phoneText" id="phoneBtn" value=" ">
    </p>
    <p>
      <input type="button" onclick="next();" class="enter-btn" id="enter" value="下一步 " disabled="disabled" ><!--disabled="disabled"-->
    </p>
    <!-- <p style="margin-top:50px;"><a id="test2">确认手机号码-提示！</a></p>
    <p style="margin-top:50px;"><a id="test1">手机号码不存在-提示！</a></p>
    <p style="margin-top:50px;"><a href="yzm.html">下一步（输入验证码）</a></p> -->
  </div>
</form>
<!--<script src="http://apps.bdimg.com/libs/angular.js/1.3.9/angular.min.js"></script>-->
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
<!--手机号码不存在-提示！-->
<script>
$('#test1').on('click', function(){
	layer.open({
    content: '<span class="ico-cue"></span>  <span class="f-s18">手机号码不存在</span>',
    btn: ['确定'],
	shadeClose:false,//是否点击遮罩时关闭层		
});
});
</script>
<!--确认手机号码-提示！-->
<script>
$('#test2').on('click', function(){
	layer.open({
		title: [
        '确认手机号码',
        'font-weight: bold;'
    ],
    content: '<span class=" width100 f-s18 color777 dis-b">我们会把短信验证码发到：</span>  <span class="f-s18  f-w">1311111111</span>',
    btn: ['确认', '取消'],
	shadeClose:false,//是否点击遮罩时关闭层		
});
});
</script>
<!--input输入字符时激活下一步按钮-->
<script>
$(document).ready(function(){
  $("#phone").keyup(function(){
	_0();
  });
  
  _0();
  
  function _0() {
	  if(document.getElementById('phone').value.length == 11){
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
		    
		    $('#form0').submit();
		    // 发送验证码
		    //location.href = '/AppService/weixin/sendCode.action?type=1&phone=' + phone;//yzm.html';
		},
		no : function(index) {
		    layer.close(index);
		}
	});
}

function next() {
	var phone = $('#phone').val();
	
	if(!checkMobile(phone)) {
		showMsg('手机号码格式不正确!');
		
		return false;
	}
	
	if(checkPhoneNumberIsExist(phone)) {
		showMsg('该手机号码未注册!');
		
		return false;
	}
	
	layerConfirm(phone);
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

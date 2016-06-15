<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html>
<html>
	<head>
	<c:set var="basePath"><%=basePath%></c:set>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<meta name="viewport"
			content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<link rel="icon" href="${basePath}/img/favicon.ico"
			type="image/x-icon">
		<title>${supplier.name}</title>
		<link rel="stylesheet" href="${basePath}/css/main.css">
		<style>
.head {
	text-align: center;
}
</style>
		<style>
			body{ font-family:"微软雅黑","宋体";font-size:12px;background:url(images/bg.jpg) top center no-repeat; background-color:#343e71; background-size:100% auto; position:relative;color:#fff;}
			.logo{ width:100px; margin:20px auto 0; display:block;}
			.name{ width:100%; text-align:center; font-size:18px; margin-top: 8px;}
			.info{ width:100%; text-align:center; font-size:18px; font-weight:bold;line-height:10px; margin-top:15%;}
			.btn{ width:75%; margin:12% auto; display:block}
			.text{  position:fixed; bottom:20px; width:100%; line-height:10px; text-align:center; }
		</style>
	</head>

	<body>
		<div id="center">
			<div id="div1" style="display: none; position: fixed; top: 0; z-index: 999;">
				<img src="${basePath}/img/ma.gif">
			</div>

			<img class="logo" src="${supplier.logouri}"
				 style="width: 72px; height: 72px;">
			<div class="name">${supplier.name}</div>
			<div class="info">
				<p>智能按键开启步骤</p>
				<p>第一步:下载、安装　　　　</p>
				<p>第二步:打开APP、设置按键</p>
			</div>
			<a id="downlink" href="javascript:down()"><img class="btn" src="${basePath}/images/btn.png"></a>
			<div id="infoId" class="text">
				<p>版本：v2.0.0 | 大小：17.6M</p>
				<p>支持版本：Android 4.4</p>
				<p>更新日期：2016.04.01</p>
			</div>
		</div>
		<script type="text/javascript" src="${basePath}/js/jquery.min.js">
</script>

		<script type="text/javascript">
var docEle = function() {
	return document.getElementById(arguments[0]) || false;
};
window.showMask = function() {
	var _id = "maskDivId";
	var m = "mask";
	if (docEle(_id))
		document.body.removeChild(docEle(_id));
	if (docEle(m))
		document.body.removeChild(docEle(m));

	//mask遮罩层
	var newMask = document.createElement("div");
	newMask.id = m;
	newMask.style.position = "absolute";
	newMask.style.zIndex = "1";
	_scrollWidth = document.body.scrollWidth;//Math.max(document.body.scrollWidth, document.documentElement.scrollWidth);
	_scrollHeight = Math.max(document.body.scrollHeight,
			document.documentElement.scrollHeight);
	newMask.style.width = _scrollWidth + "px";
	newMask.style.height = _scrollHeight + "px";
	newMask.style.top = "0px";
	newMask.style.left = "0px";
	newMask.style.background = "#33393C";
	newMask.style.filter = "alpha(opacity=10)";
	newMask.style.opacity = "0.20";
	document.body.appendChild(newMask);

	//新弹出层
	var newDiv = document.createElement("div");
	newDiv.id = _id;
	newDiv.style.position = "absolute";
	newDiv.style.zIndex = "9999";
	newDivWidth = 250;
	newDivHeight = 150;
	newDiv.style.width = newDivWidth + "px";
	newDiv.style.height = newDivHeight + "px";
	newDiv.style.top = (document.body.clientHeight / 2) + "px"; //(document.body.scrollTop + document.body.clientHeight / 2)
	newDiv.style.left = (document.body.clientWidth / 2 - newDivWidth / 2 - 10)
			+ "px"; //(document.body.scrollLeft + document.body.clientWidth / 2 - newDivWidth / 2-5)
	newDiv.style.background = "#f5f5f5";
	newDiv.style.border = "0px solid #860001";
	newDiv.style.padding = "15px";
	newDiv.innerHTML = "<img src='img/loading.gif' alt='loading..' /> <br><br> 正在下载中... ";
	document.body.appendChild(newDiv);

	//弹出层滚动居中
	function newDivCenter() {
		newDiv.style.top = (document.body.scrollTop
				+ document.body.clientHeight / 2 - newDivHeight / 2)
				+ "px";
		newDiv.style.left = (document.body.scrollLeft
				+ document.body.clientWidth / 2 - newDivWidth / 2)
				+ "px";
	}
	if (document.all) {
		window.attachEvent("onscroll", newDivCenter);
	} else {
		window.addEventListener('scroll', newDivCenter, false);
	}

	//关闭新图层和mask遮罩层
	window.hideMask = function() {
		if (document.all) {
			window.detachEvent("onscroll", newDivCenter);
		} else {
			window.removeEventListener('scroll', newDivCenter, false);
		}
		document.body.removeChild(docEle(_id));
		document.body.removeChild(docEle(m));
		return false;
	}
}
</script>

		<script>
//var ajaxbg = $("#progressBar"); 
//ajaxbg.hide(); 
$(document).ajaxStart(function() {
	//ajaxbg.show(); 
		showMask();
	}).ajaxStop(function() {
	//ajaxbg.hide(); 
		hideMask();
	});
</script>

		<script>
var weixin = false;
var ua = window.navigator.userAgent.toLowerCase();
if (ua.match(/MicroMessenger/i) == 'micromessenger') {
	weixin = true;
}
var lc = getUrlParam('lc');
//root
var down_root = "http://www.ichujian.com/app/";
//var down_root = "http://www.ichujian.com/apk_base/";
//var down_root = "http://192.168.8.221/app/";

$(function() {
	if (weixin) {
		$("#div1").css("display", "block").addClass("tanceng");
	}
	//原始版本大小
	if (!lc || lc.length == 0) {

	$("#infoId")
			.html(
			'<p>版本：v2.0丨大小：17.6MB</p><p>支持版本：Android 4.4</p><p>更新日期：2016.04.01</p>');
} else {//打包后下载大小
	$("#infoId")
			.html(
			'<p>版本：v2.0丨大小：17.6MB</p><p>支持版本：Android 4.4</p><p>更新日期：2016.04.01</p>');
}
$("#infoId").css("display", "block");
});
var isDown = false;
function down() {
	if (isDown) {
		return;
	}
	//$("#downImg").attr("src", "img/btnafter.png");
	$("#downImg").animate( {
		opacity : 'toggle'
	}, "slow", null, function() {
		$("#downImg").attr("src", "img/btnafter.png");
		$("#downImg").animate( {
			opacity : 'toggle'
		}, "slow");
	});

	if (!lc || lc.length == 0) {
		isDown = false;
		//not lc ,download default apk
		window.location.href = down_root + "ichujian_android.apk";
		return;
	}
	//
//	if('001002' == lc.substr(5,6)) {
//		isDown = false;
//		window.location.href = down_root + "ichujian_android_cb.apk";
//		return ;
//	}

	isDown = true;
	$("#downlink").attr('disabled', true);
	$.ajax( {
		type : "POST",
		url : "servlet/download",
		data : "lc=" + lc,//+Math.ceil(Math.random()*1000),
		dataType : "html",
		success : function(data) {
			//alert(data);
			window.location.href = down_root + data;
			$("#downlink").attr('disabled', false);
			isDown = false;
		},
		error : function(xhr) {
		}
	});
}

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return '';
}

if (!weixin) {
	//down();
}
</script>
	</body>
</html>

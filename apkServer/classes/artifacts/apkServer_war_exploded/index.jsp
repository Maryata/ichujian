<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
    <link rel="icon" href="${basePath}/img/favicon.ico"
          type="image/x-icon">
	<title>${supplier.name}APP下载</title>
	<style>
        /*.btn_none{float:right; width:15%; background:#ccc; border-radius:5px; padding:10px 10px; color:#fff; text-decoration:none; text-align:center;position: absolute;right: 5%; top: 25%;line-height:20px; font-size:16px;}*/
        body{ font-family:"微软雅黑","宋体";font-size:12px;background:url(${basePath}images/bg.jpg) top center no-repeat; background-color:#343e71; background-size:100% auto; position:relative;color:#fff; line-height:8px;}
		/*.logo{ float:left; width:15%;margin-right: 10px;}
		.btn{float:right; width:15%; background:#238fad; border-radius:5px; padding:10px 10px; color:#fff; text-decoration:none; text-align:center;position: absolute;right: 5%; top: 25%;line-height:20px; font-size:16px;}
		.dow{ position:fixed; left:0; bottom:0; padding:5%; width:90%; background:#fff; color:#888;z-index:9999;}*/
		.logo{ float:left; width:15%;margin-left: 15%;margin-right: 5%;}
		.btn{border: none;float:right; width:85%; background:#238fad; border-radius:5px; padding:10px 10px; color:#fff; text-decoration:none; text-align:center;position: absolute;right: 5%; top: 55%;line-height:20px; font-size:16px;}
		.btn_none{border: none;float:right; width:85%; background:#ccc; border-radius:5px; padding:10px 10px; color:#fff; text-decoration:none; text-align:center;position: absolute;right: 5%; top: 55%;line-height:20px; font-size:16px;}
		.dow{ position:fixed; left:0; bottom:0; padding:2% 5% 15% 5%; width:90%; background:#fff; color:#888;z-index:9999;}
		.margin_top0{ margin-top:0px; }
			.rslides{position:relative;list-style:none;overflow:hidden;width:100%;padding:0;margin:0;}
		.rslides li{-webkit-backface-visibility:hidden;position:absolute;display:none;width:100%;left:0;top:0;}
		.rslides li:first-child{position:relative;display:block;float:left;}
		.rslides img{display:block;height:auto;float:left;width:100%;border:0;}
		.slide_container{position:relative;float:left;width:100%;}
		.slide{position:relative;list-style:none;overflow:hidden;width:100%;padding:0;margin:0;}
		.slideli{position:absolute;width:100%;left:0;top:0;}
		.slide img{display:block;position:relative;z-index:1;height:auto;width:100%;border:0;}
		.slide.caption{display:block;position:absolute;z-index:2;font-size:20px;text-shadow:none;color:#fff;background:#000;background:rgba(0,0,0,.8);left:0;right:0;bottom:0;padding:10px20px;margin:0;max-width:none;}
		.slide_nav{position:absolute;-webkit-tap-highlight-color:rgba(0,0,0,0);top:52%;left:0;opacity:0.5;z-index:3;text-indent:-9999px;overflow:hidden;text-decoration:none;height:100px;width:50px;background:transparent url("${basePath}images/themes.png")no-repeat left top;margin-top:-45px;}
		.slide_nav:active{opacity:1.0;}
		.slide_nav.next{left:auto;background-position:right top;right:0;}
	</style>
	<script src="${basePath}js/jquery.min.js"></script>
	<script src="${basePath}js/base-loading.js"></script>
</head>
<body >
<div name="loadingDiv" style="position:absolute;left:0;width:100%;z-index:10000; display:none;">
	<div style="position: absolute;  height: 57px; line-height: 57px; padding-left: 50px; padding-right: 5px; background: #fff url(images/loading.gif) no-repeat scroll 5px 10px; border: 2px solid #95B8E7; color: #696969; font-family:\'Microsoft YaHei\';">资源获取中，请稍等...
	</div>
</div>
<div name="div_bg" style=" display:none;"></div>
<!--轮播图-->
<div class="slide_container">
	<ul class="rslides" id="slider">
			<li>
				<img src="${basePath}images/img1.png" alt="">
			</li>
			<li name="li_2">
				<img src="${basePath}images/img2.png" alt="">
			</li>
			<li name="li_2">
				<img src="${basePath}images/img3.png" alt="">
			</li>
	</ul>
</div>
<div id="div1" style="display: none; left: -50px; position: fixed; top: 0; z-index: 99999;">
	<img style="width:100%" src="${basePath}/img/ma.gif">
</div>
<!--下载栏-->
<div class="dow">
	<img class="logo" src="${supplier.logouri}">
	<p>版本：v2.1.1丨大小：30.4MB</p>
	<p>支持版本：Android 4.4</p>
	<button href="javascript:void(0);" onclick="down();" id="downlink" class="btn">立即下载</button>
</div>
<script src="${basePath}js/responsiveslides.min.js"></script>
<script src="${basePath}js/slide.js"></script>
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
//	newDiv.style.width = newDivWidth + "px";
//	newDiv.style.height = newDivHeight + "px";
	newDiv.style.width = "100%";
//	newDiv.style.height = '100%';
//	newDiv.style.top = (document.body.clientHeight / 2) + "px"; //(document.body.scrollTop + document.body.clientHeight / 2)
//	newDiv.style.left = (document.body.clientWidth / 2 - newDivWidth / 2 - 10)
//			+ "px"; //(document.body.scrollLeft + document.body.clientWidth / 2 - newDivWidth / 2-5)
//	newDiv.style.background = "#000000";
//	newDiv.style.border = "0px solid #860001";
//	newDiv.style.opacity= "0.8";
//	newDiv.style.padding = "15px";
	newDiv.innerHTML = "<img src='img/loading.gif' style='position: fixed;left: 43%; top: 45%;' alt='loading..' /> <br><br>";
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
console.log("ua >>> " + ua);
if (ua.match(/MicroMessenger/i) == 'micromessenger') {
	console.log("ua >>> weixin");
	weixin = true;
}
var lc = getUrlParam('lc');
//root
var down_root = "http://www.ichujian.com/app/";
//var down_root = "http://www.ichujian.com/apk_base/";
//var down_root = "http://192.168.8.221/app/";
// 记录页面打开行为
$(function(){
	$.ajax({
		type : "POST",
		url : "servlet/openDownloadPage",
		data : "lc="+lc,//+Math.ceil(Math.random()*1000),
		dataType:"html",
		success : function(data) {
		},
		error: function(xhr){
		}
	});
});
$(function() {
	if (weixin) {
		$("#div1").css("display", "block").addClass("tanceng");
	}
	//原始版本大小
	if (!lc || lc.length == 0) {

	$("#infoId")
			.html(
			'<p>版本：v2.1.1丨大小：30.4MB</p><p>支持版本：Android 4.4</p><p>更新日期：2016.05.16</p>');
} else {//打包后下载大小
	$("#infoId")
			.html(
			'<p>版本：v2.1.1丨大小：30.4MB</p><p>支持版本：Android 4.4</p><p>更新日期：2016.05.16</p>');
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
//		down_root = "http://www.ichujian.com/app/888001/";
		window.location.href = down_root + "ichujian_android.apk";
		return;
	}
	//
//	if('001002' == lc.substr(5,6)) {
//		isDown = false;
//		window.location.href = down_root + "ichujian_android_cb.apk";
//		return ;
//	}

	$("div[name='loadingDiv']").attr("style","display:block;top:200px;left:25%;position:absolute;z-index:10001;width:100%;");
	$("div[name='div_bg']").attr("style","display:block;position:fixed;left:0;width:100%;height:2000px;top:0;background:#f3f8ff;opacity:1;filter:alpha(opacity=100);z-index:10000;");
	isDown = true;
	$("#downlink").attr('disabled', "true");
    $('#downlink').removeClass("btn").addClass("btn_none");
	$.ajax( {
		type : "POST",
		url : "servlet/download",
		data : "lc=" + lc,//+Math.ceil(Math.random()*1000),
		dataType : "html",
		success : function(data) {
			$("div[name='loadingDiv']").attr("style","display:none;");
			$("div[name='div_bg']").attr("style","display:none;");
			//alert(data);
			window.location.href = down_root + data;
			$("#downlink").attr('disabled', false);
            $('#downlink').removeClass("btn_none").addClass("btn");
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
<script type="text/javascript">
	$(function () {
	    $("li[name='li_2']").attr("style","display: none");
	});

</script>
	</body>
</html>

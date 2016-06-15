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
<meta content="telephone=no" name="format-detection" />
<title>礼包</title>
<link href="${basePath }/resource/h5/css/reset.css" rel="stylesheet">
<link href="${basePath }/resource/h5/css/style.css" rel="stylesheet">
</head>
<body>
<div class="title_box"><span class="m-l20">礼包</span></div>
<div class="content" >
	<%-- <c:forEach items="${ list }" var="gift" varStatus="status">
			<c:choose>
				<c:when test="${ gift.ACTION eq 1 }">
					<dl>
					<a href="javascript:void(0);" onclick="get(${gift.ID},${ status.index })" class="play" >领取</a>
				</c:when>
				<c:when test="${ gift.ACTION eq 3 }">
					<dl>
					<a href="javascript:void(0);" onclick="view(${gift.ID},${ status.index })" class="play" >查看</a>
				</c:when>
			</c:choose>
			<c:if test="${ gift.ACTION eq 1 or gift.ACTION eq 3 }">
				<dt><img width="100%" src="${ gift.C_LOGOURL }"></dt>
			    <dd>
			      <h1>${ gift.NAME }</h1>
			      <span class="online">${gift.GOT}人领取，剩余${ gift.REMAIN }个</span> <span class="intr">${ gift.DEPICT }</span> 
			    </dd>
		     	<span class="code-tip" id="s${ status.index }" style="display:none;">
		        <p>恭喜您，领号成功，长按复制：</p>
		        <p class="nub">激活码：<span id="ss${ status.index }"></span></p>
		      	</span>
		      	</dl>
			</c:if>
	</c:forEach> --%>
  <div class="after_div"></div>
  <div class="ajax_loading"><span style="display:none;" class="loading" ><img src="${basePath }/resource/h5/images/loading.gif"><span>正在加载...</span></span></div>
</div>
</body>
<script src="${basePath }/resource/h5/js/jquery-2.1.4.js"></script>
<script src="${basePath }/resource/h5/layer.m/layer.m.js"></script>
<%-- <script src="${basePath }/resource/h5/js/ZeroClipboard.min.js"></script> --%>
<!--已领取-提示！-->
<script>
function get(o,id,index) {
	var username = getCookie('username');
	
	if(username) {
		$.ajax({
			type : 'GET',
			async : false,
			url : '/AppService/weixin/getGift.action',
			data : { username : username, id : id},
			dataType : 'json',
			success : function(data) {
				if(data && data.status == 'Y') {
					$('#s' + index).css('display','block');
					$('#ss' + index).text(data.code).css('display','block');
					$(o).removeClass('play').addClass('play-hover').text('已领取');
				}
			},
			error : function(data) {
				
			}
		});
	} else {
		window.location.href = '/AppService/weixin/index.action';
	}
	
}

function view(id, index) {
	var username = getCookie('username');
	
	if(username) {
		$.ajax({
			type : 'GET',
			async : false,
			url : '/AppService/weixin/giftDetail.action',
			data : { username : username, id : id},
			dataType : 'json',
			success : function(data) {
				if(data && data.status == 'Y') {
					$('#s' + index).css('display','block');
					$('#ss' + index).text(data.gift[0].CODE).css('display','block');
				}
			},
			error : function(data) {
				
			}
		});
	}
}
/* var client = new ZeroClipboard( $('.play') );

client.on( 'ready', function(event) {
  // console.log( 'movie is loaded' );

  client.on( 'copy', function(event) {
    event.clipboardData.setData('text/plain', event.target.innerHTML);
  } );

  client.on( 'aftercopy', function(event) {
    //console.log('Copied text to clipboard: ' + event.data['text/plain']);
    alert('Copied text to clipboard: ' + event.data['text/plain']);
  } );
} ); */

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

</script>
<script type="text/javascript" charset="UTF-8">
$(document).ready(function() {	
	var $_div = $('.after_div');
	
	var username = getCookie('username') || '-1';
	
	$.ajax({
		type : 'GET',
		async : false,
		url : '/AppService/weixin/ajaxList.action',
		data : { uid : username},
		dataType : 'json',
		success : function(data) {
			
			if(data && data.status == 'Y') {
				var l = data.apps;
				for(var i = 0; i < l.length; ++i) {
					var o = l[i];
					
					var action = o.ACTION;
					
					$dl = $('<dl></dl>');
					if(action == '1') {
						$a = $('<a href="javascript:void(0);" onclick="get(this,'+o.ID+','+i+')" class="play" >领取</a>');	
					} else if(action == '3') {
						$a = $('<a href="javascript:void(0);" onclick="view('+o.ID+','+i+')" class="play" >查看</a>');
					} else {
						continue;
					}
					
					$dt = $('<dt></dt>');
					$logo = $('<img width="100%" src="'+o.LOGOURL+'">');
					$dd = $('<dd></dd>');
					$h1 = $('<h1>'+o.NAME+'</h1>');
					$span0 = $('<span class="online">'+o.GOT+'人领取，剩余'+o.REMAIN +' 个</span>');
					$span1 = $('<span class="intr">'+o.DEPICT+'</span>');
					$span2 = $('<div class="code-tip" id="s'+i+'" style="display:none;"></div>');
					$p0 = $('<p>恭喜您，领号成功，长按复制激活码：</p>');
					//$p1 = $('<p class="nub">激活码：</p>');
					$span3 = $('<div class="jhm" style="display:none" id="ss'+i+'"></div>');
					
					$div = ('<div class="jhm-k"><div>');
					
					$dt.append($logo);
					$dd.append($h1).append($span0).append($span1);
					
					//$p1.append($span3);
					$span2.append($p0);//.append($p1);//.append($span3);
					
					$dl.append($a).append($dt).append($dd).append($span2).append($span3).append($div);   
					
					$_div.prepend($dl);
				}
			}
		},
		error : function(data) {
			
		}
	});
});
</script>

</html>
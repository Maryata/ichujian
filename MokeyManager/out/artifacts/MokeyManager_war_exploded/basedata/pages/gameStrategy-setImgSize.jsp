<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,user-scalable=no">
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<title>攻略管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script type="text/javascript">rootPath ="${pageContext.request.contextPath}";</script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor/lang/zh_CN.js"></script>
<script>

setTimeout(function(){
window.location.reload();
},1000*60*1); //指定1秒刷新一次

var DEPICTS;
var DEPT_INDEX=0;
$(function(){
	_getDecipt();
});
function _getDecipt(){
// 查询所有id和对应的内容
	$.post("${pageContext.request.contextPath }/basedata/gameStrategyAction!selectDepict.action",
		function(data){
			$("#ref0").append("--| "+data.length+" |-- ");
			$("#ref1").append("   --|--   ");
			$("#ref2").append("   --|--   ");
			DEPICTS = data;
			_setFun(DEPICTS[0]);
			DEPT_INDEX=0;
	},"json");
}
function _setFun(obj){
	$("#ref1").append("; "+obj.ID);
	_setImgSize(obj);
	_update();
}

function _setImgSize(obj){
	$("#id").val(obj.ID);
	KindEditor.html("#depict", obj.DECIPT);
}
function _update(){
	$("#ref2").append("  "+$("#id").val()+":");
	var _width=-1;
	var _height;
	setTimeout(function(){
		// 处理大尺寸图片
		$(".ke-edit-iframe").contents().find("img").each(function(){
			_width = this.width;
			//alert(_width);
			_height = this.height;
			if(_width==0){
				$(this).removeAttr("width");
				$(this).removeAttr("height");
				$("#ref2").append(" del["+_width+","+_height+"]");
			}
		});
		var flag=1;
		$(".ke-edit-iframe").contents().find("img").each(function(){
			_width = this.width;
			//alert(_width);
			_height = this.height;
			// 如果图片宽度大于300，按照等比例缩小为宽度300（同时高度等比例缩小）
			if(_width>300){
				_height = parseInt((300 * _height) / _width);
				_width = 300;
			}
			if(_width==0){
				$(this).removeAttr("width");
				$(this).removeAttr("height");
				//$(this).attr("width", _width);
				//$(this).attr("height", _height);
				$("#ref2").append(" del--["+_width+","+_height+"]");
				flag = 5;
			}else{
				// 为img标签添加宽高属性
				$(this).attr("width", _width);
				$(this).attr("height", _height);
				//$("#ref2").append("["+_width+","+_height+"]");
			}
		});
		if(flag==5){
			$("#ref3").append(","+$("#id").val());
		}
		
		var $id = $("#id").val();
		var $depict =editor_content.html();
		var result = 0;
		$.post("${pageContext.request.contextPath }/basedata/gameStrategyAction!setImgSize.action",
			{id:$id,depict:$depict,flag:flag},function(data){
				if(DEPT_INDEX<DEPICTS.length-1){
					DEPT_INDEX +=1;
					_setFun(DEPICTS[DEPT_INDEX]);
				}
				if(DEPT_INDEX==DEPICTS.length-1){
					$("#ref0").append("-load new-;");
					_getDecipt();
				}
		});
	},6000);
}


KindEditor.ready(function(K) {
	window.editor_content = K.create('#depict', {
		urlType : 'domain',
		fullscreenMode:false,
        resizeType : 1,  //文本框拖动  1/yes，0/no
        uploadJson : "${pageContext.request.contextPath}/KindEditServlet.do?type=gameStrategyShare/upload&id="+$("#newId").val(),
        allowImageUpload : true,
        afterCreate : function() {
          	this.loadPlugin('autoheight');
        },
		afterBlur : function(){ 
			this.sync(); 
		}  //Kindeditor下获取文本框信息
	});
});
function formValid(){
	// 处理大尺寸图片
	$(".ke-edit-iframe").contents().find("img").each(function(){
		var _width = this.width;
		var _height = this.height;
		// 如果图片宽度大于300，按照等比例缩小为宽度300（同时高度等比例缩小）
		if(_width>300){
			_height = parseInt((300 * _height) / _width);
			_width = 300;
		}
		// 为img标签添加宽高属性
		$(this).attr("width", _width);
		$(this).attr("height", _height);
	});
	$("#form1").attr("action","${pageContext.request.contextPath }/basedata/gameStrategyAction!saveGameStrategy.action");
}
</script>
</head>

<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 
   	</td>
  </tr>
</table>
<form method="post" id="form1">
<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
  <tr> 
	<td width="20%"  height="23" class="biaodan-top" align="left">ID</td>
   	<td class="biaodan-q" align="left">
		<input type="text"  id="id" name="id" value=""/>
 	</td>
  </tr>
  <tr>
  	<td colspan="2">
  		<textarea id="depict" name="depict" style="width:99.8%;height:400px;visibility:hidden;">
  			
  		</textarea>
  		
	</td>
  </tr>
  <tr>
  <td colspan="2">
  <p id="ref0">[数据量]:</p>
  ---------------------------------------------
  <p id="ref1">[所有ID]:</p>
  ---------------------------------------------
  <p id="ref2">[宽高]:</p>
  
  -----------error id----------------------------------
  <p id="ref3"></p>
  </td>
  </tr>
  
</table>
</form>
<br>
</body>
</html>


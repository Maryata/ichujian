<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>actImg</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<style type="text/css">
body {
	font: normal 10px verdana;
	margin: 0;
	padding: 0px;
	border: 0 none;
	overflow: hidden;
	height: 100%;
}
.divX
{
    z-index:100;
    border-style:solid;
    border-width:1px;
    border-color:#FF0000;
    -moz-border-radius:20px;
    -webkit-border-radius:20px;
    background-color:#ffffff;
    line-height:10px;
    text-align:center;
    font-weight:bold;
    cursor:pointer;
    font-size:10px;
    color:red;
    display: none;
}
img{
width:92px;height:71px;padding:2px;
}

</style>
<script type="text/javascript" src="../../../js/jquery/jquery-1.8.x.min.js"></script>
<script type="text/javascript" src="../../../js/jquery/ajaxfileupload.js"></script>
<script type="text/javascript">
var imgFlag = false; //鼠标放在图片上标记
var divFlag = false; //鼠标放在删除层上标记

function showDeleteDiv(resourceCode){
    imgFlag = true;
    $("#"+resourceCode+"DIV").css("display","block");
};

function hideDeleteDiv(resourceCode){
    if(!imgFlag && !divFlag){
        $("#"+resourceCode+"DIV").css("display","none");  
    }
};

function imgOnmouseout(){
    imgFlag=false;
};

function divOnmouseover(resourceCode){
    divFlag = true;
    showDeleteDiv(resourceCode);
};

function divOnmouseout(resourceCode){
    divFlag = false;
    if(imgFlag){
        showDeleteDiv(resourceCode);
    }else{
        hideDeleteDiv(resourceCode);
    }
};

function removeImg(resourceCode) {
    if(window.confirm('是否确认删除？')){
    	var img = $("#img_"+resourceCode+"");
        var oldImg = $("#imgs").val().split(",");
        oldImg.removeByValue(img.attr("src"));
        img.parent().detach();
        $("#imgs").val(oldImg.toString());
    }
}
</script>

<script type="text/javascript">
//hash表去重
Array.prototype.unique = function()
{
	var n = {},r=[]; //n为hash表，r为临时数组
	for(var i = 0; i < this.length; i++) //遍历当前数组
	{
		if (!n[this[i]]) //如果hash表中没有当前项
		{
			n[this[i]] = true; //存入hash表
			r.push(this[i]); //把当前数组的当前项push到临时数组里面
		}
	}
	return r;
}
//根据值删除数组元素
Array.prototype.removeByValue = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val) {
			this.splice(i, 1);
			break;
		}
	}
}

 function loadImgFun (imgs){
	 var imgArr = [];
	 var newImg = imgs.split(",");
	 var oldImg = $("#imgs").val().split(",");
	 
	 var imgArr = oldImg.concat(newImg);
	 imgArr = imgArr.unique();
	 
	 var imgs="";
	 var html = "";
	 for(var i=0;i<imgArr.length;i++){
		if(imgArr[i] && imgArr[i]!=''){
			html +="<span style='position: relative;'>";
			html +="<img id='img_"+i+"' alt='"+i+"' src='"+imgArr[i]+"' onmouseover=\"showDeleteDiv('"+i+"');\" onmouseout=\"imgOnmouseout(),hideDeleteDiv('"+i+"');\" />";
			html +="</span>";
			if(imgs!=""){
				imgs+=",";
			}
			imgs +=imgArr[i];
		}
	 }
	 $("#imgs").val(imgs);
	 $("#imgshow").html(html);
	 
	 $("img").each(function(i){
		 //if($(this).attr('id').indexOf("img_")>-1){
			 var divObj=$("<div onclick=removeImg('"+$(this).attr('alt')+"'); onmouseover=divOnmouseover('"+$(this).attr('alt')+"'); onmouseout=divOnmouseout('"+$(this).attr('alt')+"');>×</div>");
			 divObj.addClass("divX");
			 divObj.attr("id",$(this).attr("alt")+"DIV");
			 divObj.attr("title","删除图片"+$(this).attr('alt'));
			 divObj.css({position:"absolute", left: $(this).position().left+82, top:$(this).position().top});
			 $(this).parent().append(divObj);
		 //}
	});
 }
function getImgs(){
	var oldImg = $("#imgs").val();
	return oldImg;
}
</script>
<script type="text/javascript">
function ajaxFileUpload() {
	$("#loading").ajaxStart(function() {
		$(this).show();
	})//开始上传文件时显示一个图片
	.ajaxComplete(function() {
		$(this).hide();
	});//文件上传完成将图片隐藏起来

	$.ajaxFileUpload( {
		url : "<%=request.getContextPath()%>/actionBaseActivityInfoService!saveUploadImg",//用于文件上传的服务器端请求地址
		secureuri : false,//一般设置为false
		fileElementId : 'img',//文件上传控件的id属性
		dataType : 'json',//返回值类型 一般设置为json
		success : function(data, status){ //服务器成功响应处理函数
			if(!data.imgs){
				alert("上传失败!");
				return;
			}
			//
			loadImgFun(data.imgs);
		},
		error : function(data, status, e){//服务器响应失败处理函数
			alert(e);
		}
	});
	return false;
}
</script>
</head>
<body>
<form action="" method="post" focus="">
    <input type="file" id="img" name="img" multiple />
    <input type="hidden" id="imgs" name="imgs" />
    <input type="button" value="上传" onclick="return ajaxFileUpload();">
    <img id="loading" src="../../../image/imgload.gif" style="width:100px;height:15px;display: none;"/>
</form>
<div id="imgshow" style="width:570px;height:80px; overflow-y:scroll; border:0px solid;">
</div>
  </body>
</html>

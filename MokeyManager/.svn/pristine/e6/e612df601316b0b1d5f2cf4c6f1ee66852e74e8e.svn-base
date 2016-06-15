<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<meta http-equiv="Expires" content="0"><meta http-equiv="Cache-Control" content="no-cache"><meta http-equiv="Cache-Control" content="no-store">
<title>广告位维护</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
	<script type="text/javascript">rootPath ="${pageContext.request.contextPath}";</script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.selectseach.min.js"></script>
<style type="text/css">
.file-btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
.file-file{ position:absolute; top:-4px; right:74px; height:24px; filter:alpha(opacity:0);opacity: 0;width:70px; }
.file-box {position: relative;width: 340px}
</style>
<script type="text/javascript">
function formValid(){
	var _flag = true;
	if($("#name").val()==""){
		alert("“名称”不能为空！");
		_flag = false;
		return _flag;
	};
	if($("#pid").val()==""){
		alert("“商品”不能为空！");
		_flag = false;
		return _flag;
	};
	if(_flag){
		if($("#addFlag").val()=="1"){
			$("#form1").attr("action","${pageContext.request.contextPath }/basedata/ekey/eKMallAdvertAction!addAdvert.action");
		}else{
			$("#form1").attr("action","${pageContext.request.contextPath }/basedata/ekey/eKMallAdvertAction!updateAdvert.action");
		}
	}
	return _flag;
}

//app应用改变事件
$(document).ready(function(){
	$("select").selectseach();
});
function checkFile(file,seeId){
	var fName = $(file).val();
	if(fName==''){
		alert("请选择要上传的资料");
		return false;
	}
	var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if(fExt!='.jpg' && fExt!='.gif' && fExt!='.jpeg' && fExt!='.bpm' && fExt!='.png'){
		alert("请选择正确的图片格式:.jpg,.gif,.jpeg,.bpm,png");
		return false;
	}
	$("#"+seeId).val("已选择文件");
	return true;
}
function uploadFile(btn,fileId,baseId) {
	var fName = $("#"+fileId).val();
	if(fName==''){
		alert("请选择要上传的资料");
		return false;
	}
	var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if(fExt!='.jpg' && fExt!='.gif' && fExt!='.jpeg' && fExt!='.bpm' && fExt!='.png'){
		alert("请选择正确的图片格式:.jpg,.gif,.jpeg,.bpm,png");
		return false;
	}
	$(btn).html("uploading...");
	$.ajaxFileUpload( {// ekey : 1.首页，2.活动，3.游戏
		url : "${pageContext.request.contextPath }/LogoUploadServlet.do?ekey=4&type=mallAdvert/logo&id="+$("#cid").val(),//用于文件上传的服务器端请求地址
		secureuri : false,//一般设置为false
		fileElementId : fileId,//文件上传控件的id属性
		dataType : 'json',//返回值类型 一般设置为json
		success : function(data, status){ //服务器成功响应处理函数
			if(data.filePath){
				$('#'+baseId).val(data.filePath);
				$('#'+baseId+"Img").attr("src",data.filePath);
				$('#'+baseId+"ImgA").attr("href",data.filePath).show();
				$('#'+baseId+"See").val("浏览...");
				$(btn).val("上传完成");
			}else{
				alert('上传失败:'+data.msg);
				$(btn).val("上传失败");
			}
		},
		error : function(data, status, e){//服务器响应失败处理函数
			$(btn).val("上传失败");
		}
	});
	return false;
}
</script>
</head>

<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 
    	系统信息 -&gt; e键:商城 -&gt;广告位维护管理-&gt; 编辑分类
   	</td>
  </tr>
</table>
<form method="post" id="form1">
<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	<input type="hidden" id="addFlag" name="addFlag" value="<s:property value="#request.addFlag"/>" >
	<input type="hidden" id="cid" name="cid" value="<s:property value="#request.cid"/>" >
	<br/>
  	<tr>
		<td width="8%"  height="23" class="biaodan-top" align="left">商城商品：</td>
		<td width="30%" align="center" class="biaodan-q">
			<select  name="pid" id="pid"  style="width:100%;border:none;hight:100%;" m="search">
				<option></option>
				<c:forEach var="item" items="${list}" varStatus="status">
					<option value="${item.C_ID}" <c:if test="${item.C_ID==pid}">selected</c:if>>${item.C_TITLE}</option>
				</c:forEach>
			</select>
		</td>
		<td width="8%"  height="23" class="biaodan-top" align="left">图片：</td>
		<td align="center" class="biaodan-q" colspan="2">
	    	<span class="file-box moreImg">
			<s:if test="#request.logo != null">
				<a href="<s:property value="#request.logo"/>" target="_blank" id="logourlScanImgA"><img id="logourlScanImg" style="width: 30px;height: 30px;" src="<s:property value="#request.logo"/>" ></a>
			</s:if>
          	<s:else>
				<a target="_blank" id="logourlScanImgA" style="display: none"><img id="logourlScanImg" style="width: 30px;height: 30px;" ></a>
			</s:else>
			<input type="hidden" name="logo" id="logourlScan" value="<s:property value="#request.logo"/>"/>
       	 	<input type="button" style="width:70px;" class="file-btn" id="logourlScanSee" value="浏览..." />
       	 	<input type="file" style="height:24px;" class="file-file" id="logourlScanField" size="28" multiple=true  onchange="return checkFile(this,'logourlScanSee')" />
          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'logourlScanField','logourlScan');" />
       		</span>
		</td>
  	</tr>
	<tr>
		<td width="8%"  height="23" class="biaodan-top" align="left">广告名称：</td>
		<td width="30%" align="center" class="biaodan-q" >
			<input type="text" style="border:none; width:100%; height:100%" id="name" name="name" maxlength="32"
				   value="<s:property value='#request.name'/>"/>
		</td>
		<td width="8%"  height="23" class="biaodan-top" align="left">排序：</td>
		<td align="center" class="biaodan-q" colspan="2">
			<input type="text" style="border:none; width:100%; height:100%" id="order" name="order"
				   value="<s:property value='#request.order'/>"/>
		</td>
	</tr>
  	<tr>
	    <td class="biaodan-q" align="center" colspan="5">
		<button class="butt" onclick="return formValid();">保 存</button>
 	</td>
  	</tr>
</table>
</form>
<br>
</body>
</html>


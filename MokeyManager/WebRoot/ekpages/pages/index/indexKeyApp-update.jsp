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
<title>键位添加</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/js/validate/jquery.validate.min.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.file-btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
.file-file{ position:absolute; top:-4px; right:74px; height:24px; filter:alpha(opacity:0);opacity: 0;width:70px; }
.file-box {position: relative;width: 340px}

</style>
<script type="text/javascript">
var editor;
var editor1;
$(document).ready(function() {
	$(".form-horizontal").validate({
		rules:{
            view:{digits:true},
			vote:{digits:true},
			favorite:{digits:true}
		},
		messages:{

		},
		submitHandler: function(form){
			loading();
			form.submit();
		},
		errorContainer: "#messageBox",
		errorPlacement: function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});

});
KindEditor.ready(function(K){
	editor = K.create('textarea[name="detail"]', {
		allowFileManager : true
	});
	editor1 = K.create('textarea[name="fullDetail"]', {
		allowFileManager : true
	});
});
function formValid(){
	var _flag = true;
	if($("#cname").val()==""){
		alert("“分类名称”不能为空！");
		_flag = false;
		return _flag;
	};
	if(_flag){
		if($("#addFlag").val()=="1"){
			$("#form1").attr("action","${pageContext.request.contextPath }/basedata/ekey/eKIndexKeyAppAction!addKeyApp.action");
		}else{
			$("#form1").attr("action","${pageContext.request.contextPath }/basedata/ekey/eKIndexKeyAppAction!updateKeyApp.action");
		}
	}
	return _flag;
}
function checkFile(file,seeId,did){
	var fName = $(file).val();
	if(fName==''){
		alert("请选择要上传的资料");
		return false;
	}
	var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if(did=="3"){
		if(fExt!='.apk' ){
			alert("请选择正确的jar包格式:.apk");
			return false;
		}
	}else{
		if(fExt!='.jpg' && fExt!='.gif' && fExt!='.jpeg' && fExt!='.bpm' && fExt!='.png'){
			alert("请选择正确的图片格式:.jpg,.gif,.jpeg,.bpm,png");
			return false;
		}
	}	
	$("#"+seeId).val("已选择文件");
	return true;
}
function uploadFile(btn,fileId,baseId,vid) {
	var fName = $("#"+fileId).val();
	if(fName==''){
		alert("请选择要上传的资料");
		return false;
	}
	var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if(vid=="3"){
		if(fExt!='.apk' ){
			alert("请选择正确的jar包格式:.apk");
			return false;
		}
	}else{
		if(fExt!='.jpg' && fExt!='.gif' && fExt!='.jpeg' && fExt!='.bpm' && fExt!='.png'){
			alert("请选择正确的图片格式:.jpg,.gif,.jpeg,.bpm,png");
			return false;
		}
	}
	var _file="";
	if(vid=="1"){
		_file="logo";
	}else if(vid=="2"){
		_file="image";
	}else{
		_file="apk";
	}
	$(btn).html("uploading...");
	$.ajaxFileUpload( {// ekey : 1.首页，2.活动，3.游戏
		url : "${pageContext.request.contextPath }/LogoUploadServlet.do?apk=1&ekey=1&type=indexKeyApp/"+_file+"&id="+$("#cid").val(),//用于文件上传的服务器端请求地址
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
    	系统信息 -&gt; e键-首页 -&gt;键位管理-&gt; 编辑分类
   	</td>
  </tr>
</table>
<form method="post" id="form1" class="form-horizontal">
<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	<input type="hidden" id="addFlag" name="addFlag" value="<s:property value="#request.addFlag"/>" >
	<input type="hidden" id="cid" name="cid" value="<s:property value="#request.cid"/>" >
	<br/>
	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">键位：</td>
		<td align="center" class="biaodan-q required" width="15%">
			<input type="hidden" name="_key" id="_key" value="${act.C_KEY}">
			<select name="key" id='key' style="border:none; width:100%; height:100%">
				<option value="4">4号键</option>
			</select>
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">供应商：</td>
		<td align="center" class="biaodan-q" width="18%">
			<select name="supcode" id='supcode' style="border:none; width:100%; height:100%">
				<option value="-1">非定制</option>
				<c:forEach var="item" items="${list}" varStatus="status">
					<option value="${item.C_SUPPLIER_CODE}" <c:if test="${item.C_SUPPLIER_CODE==act.C_SUPCODE}">selected</c:if>>${item.C_SUP_NAME}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td width="5%"  class="biaodan-top" align="left">名称：</td>
		<td width="15%" class="biaodan-q" align="left">
			<input type="text" style="border:none; width:100%; height:100%" id="name" name="name"  value="${act.C_NAME}"/>
		</td>
		<td width="5%"  class="biaodan-top" align="left">标题：</td>
		<td width="18%" class="biaodan-q" align="left">
			<input type="text" style="border:none; width:100%; height:100%" id="title" name="title" value="${act.C_TITLE}"/>
		</td>
	</tr>

 	<tr>
	   	<td width="5%"  height="23" class="biaodan-top" align="left">图片上传：</td>
	    <td align="center" class="biaodan-q">
	    	<span class="file-box">
			<c:if test="${not empty  act.C_LOGO}">
           		<a href="${act.C_LOGO}" target="_blank" id="logourlScanImgA"><img id="logourlScanImg" style="width: 30px;height: 30px;" src="${act.C_LOGO}" ></a>
          	</c:if>
          	<c:if test="${empty  act.C_LOGO}">
            	<a target="_blank" id="logourlScanImgA" style="display: none"><img id="logourlScanImg" style="width: 30px;height: 30px;" ></a>
          	</c:if>
			<input type="hidden" name="logo" id="logourlScan" value="${act.C_LOGO}"/>
       	 	<input type="button" style="width:70px;" class="file-btn" id="logourlScanSee" value="浏览..." />
       	 	<input type="file" style="height:24px;" class="file-file" id="logourlScanField" size="28" onchange="return checkFile(this,'logourlScanSee','1')" />
          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'logourlScanField','logourlScan','1');" />
       		</span>
	    </td>
		<td width="5%"  height="23" class="biaodan-top" align="left">截图上传：</td>
		<td align="center" class="biaodan-q">
	    	<span class="file-box">
			<%--<s:if test="${act.C_IMG !=''}">
				<a href="${act.C_IMG}" target="_blank" id="imgurlScanImgA"><img id="imgurlScanImg" style="width: 30px;height: 30px;" src="${act.C_IMG}" ></a>
			</s:if>
          	<s:else>
				<a target="_blank" id="imgurlScanImgA" style="display: none"><img id="imgurlScanImg" style="width: 30px;height: 30px;" ></a>
			</s:else>--%>
            <c:choose>
				<c:when test="${not empty act.C_IMG}">
					<a href="${act.C_IMG}" target="_blank" id="imgurlScanImgA"><img id="imgurlScanImg" style="width: 30px;height: 30px;" src="${act.C_IMG}" ></a>
				</c:when>
				<c:when test="${empty act.C_IMG}">
					<a target="_blank" id="imgurlScanImgA" style="display: none"><img id="imgurlScanImg" style="width: 30px;height: 30px;" ></a>
				</c:when>

			</c:choose>
			<input type="hidden" name="img" id="imgurlScan" value="${act.C_IMG}"/>
       	 	<input type="button" style="width:70px;" class="file-btn" id="imgurlScanSee" value="浏览..." />
       	 	<input type="file" multiple="multiple" style="height:24px;" class="file-file" id="imgurlScanField" size="28" onchange="return checkFile(this,'imgurlScanSee','2')" />
          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'imgurlScanField','imgurlScan','2');" />
       		</span>
		</td>
  	</tr>


      <tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">安装包上传：</td>
		<td align="center" class="biaodan-q">
	    	<span class="file-box">
			<%--<s:if test="${act.C_APPURL != null}">
				<a href="<s:property value="#request.app"/>" target="_blank" id="appurlScanImgA"><img id="appurlScanImg" style="width: 30px;height: 30px;" src="${act.C_APPURL}" ></a>
			</s:if>
          	<s:else>
				<a target="_blank" id="appurlScanImgA" style="display: none"><img id="appurlScanImg" style="width: 30px;height: 30px;" ></a>
			</s:else>--%>
			<input type="hidden" name="app" id="appurlScan" value="${act.C_APPURL}"/>
       	 	<input type="button" style="width:70px;" class="file-btn" id="appurlScanSee" value="浏览..." />
       	 	<input type="file" style="height:24px;" class="file-file" id="appurlScanField" size="28" onchange="return checkFile(this,'appurlScanSee','3')" />
          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'appurlScanField','appurlScan','3');" />
       		</span>
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">jar包名称：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="jar" name="jar" value="${act.C_JAR}" maxlength="64" >
		</td>
	</tr>


	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">安装包大小（B）：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="size" name="size" value="${act.C_SIZE}">
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">厂商：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="menu" name="menu" value="${act.C_MENU}" maxlength="64" >
		</td>
	</tr>


	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">厂商电话：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="menuTel" name="menuTel" value="${act.C_MENUTEL}" maxlength="32" >
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">简介：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="abstract" name="abstract" value="${act.C_ABSTRACT}" maxlength="128" >
		</td>
	</tr>


	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">版本：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="version" name="version" value="${act.C_VERSION}" maxlength="32" >
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">初始下载量：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="download" name="download" value="${act.C_DOWNLOAD}" >
		</td>
	</tr>


	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">初始评分：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="grade" name="grade" value="${act.C_GRADE}" maxlength="32" >
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">详情H5页面：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="h5url" name="h5url" value="${act.C_H5URL}" maxlength="128" >
		</td>
	</tr>


	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">原下载地址：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="source" name="source" value="${act.C_SOURCE}" maxlength="256" >
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">是否有效：</td>
		<td align="center" class="biaodan-q" width="18%">
			<select name="islive" id='islive' style="border:none; width:100%; height:100%">
				<option value="1" <c:if test="${act.C_ISLIVE ==1}">selected</c:if> >有效</option>
				<option value="0" <c:if test="${act.C_ISLIVE ==0}">selected</c:if>>无效</option>
			</select>
		</td>
	</tr>


	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">供应商H5页面链接：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="supurl" name="supurl" value="${act.C_SUPURL}" maxlength="256" >
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">排序：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="order" name="order" value="${act.C_ORDER}">
		</td>
	</tr>

  	<tr>
	    <td class="biaodan-q" align="center" colspan="4">
		<button class="butt" onclick="return formValid();">保 存</button>
 	</td>
  	</tr>
</table>
</form>
<br>
</body>
</html>


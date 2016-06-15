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
<title>商品详情</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
	<script type="text/javascript">rootPath ="${pageContext.request.contextPath}";</script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.selectseach.min.js"></script>
<%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/ueditor1_4_3_2-utf8-jsp/utf8-jsp/themes/default/css/ueditor.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ueditor1_4_3_2-utf8-jsp/utf8-jsp/ueditor.config.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ueditor1_4_3_2-utf8-jsp/utf8-jsp/ueditor.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ueditor1_4_3_2-utf8-jsp/utf8-jsp/lang/zh-cn/zh-cn.js"></script>--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor/lang/zh_CN.js"></script>
<script>
	/*var ue0 = UE.getEditor('detail');
	var ue1 = UE.getEditor('illustr');
	//对编辑器的操作最好在编辑器ready之后再做
	ue0.ready(function() {
		//设置编辑器的内容
		ue0.setContent($("#_detail").val());
		//获取html内容，返回: <p>hello</p>
		//var html = ue0.getContent();
		//$("#_detail").val(html);
		//获取纯文本内容，返回: hello
		//var txt = ue0.getContentTxt();
	});
	ue1.ready(function() {
		//设置编辑器的内容
		ue1.setContent($("#_illustr").val());
		//获取html内容，返回: <p>hello</p>
		//var html = ue1.getContent();
		//$("#_illustr").val(html);
		//获取纯文本内容，返回: hello
		//var txt = ue1.getContentTxt();
	});*/
	var editor2;
	var editor1;
	KindEditor.ready(function(K){
		editor1 = K.create('textarea[name="detail"]', {
			allowFileManager : true,
			resizeMode : 1,
			shadowMode : false,
			allowPreviewEmoticons : false,
			allowUpload : true,
			//syncType : 'auto',
			urlType : 'domain',
			//cssPath : 'css/ke.css',
			uploadJson:'${pageContext.request.contextPath }/KindEditServlet.do?ekey=4&type=mallProduct/detail&id='+$("#cid").val()
		});

		editor2 = K.create('textarea[name="illustr"]', {
			allowFileManager : true,
			resizeMode : 1,
			shadowMode : false,
			allowPreviewEmoticons : false,
			allowUpload : true,
			//syncType : 'auto',
			urlType : 'domain',
			//cssPath : 'css/ke.css',
			uploadJson:'${pageContext.request.contextPath }/KindEditServlet.do?ekey=4&type=mallProduct/illustr&id='+$("#cid").val()
		});
	});

	$(function(){
		$("#type").change(function(){
			if($("#type").val()=='1'){
				$("#total").attr("disabled","disabled");
				$("#shopCode").removeAttr("disabled");
			}else{
				$("#total").removeAttr("disabled");
				$("#shopCode").attr("disabled","disabled");
			}
		});
		$("#type").change();
	});

	$("#type").change(

	);

</script>
	<style type="text/css">
.file-btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
.file-file{ position:absolute; top:-4px; right:74px; height:24px; filter:alpha(opacity:0);opacity: 0;width:70px; }
.file-box {position: relative;width: 340px}
</style>
<script type="text/javascript">
function formValid(){
	var _flag = true;
	if($("#title").val()==""){
		alert("“名称”不能为空！");
		_flag = false;
		return _flag;
	};
	/*var html = ue0.getContent();
	$("#_detail").val(html);
	var html = ue1.getContent();
	$("#_illustr").val(html);*/
	if(_flag){
		if($("#addFlag").val()=="1"){
			$("#form1").attr("action","${pageContext.request.contextPath }/basedata/ekey/eKMallProductAction!addProduct.action");
		}else{
			$("#form1").attr("action","${pageContext.request.contextPath }/basedata/ekey/eKMallProductAction!updateProduct.action");
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
		url : "${pageContext.request.contextPath }/LogoUploadServlet.do?ekey=4&type=mallProduct/logo&id="+$("#cid").val(),//用于文件上传的服务器端请求地址
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


function checkFile1(file,seeId){
	var fName = $(file).val();
	if(fName==''){
		alert("请选择要上传的资料");
		return false;
	}
	var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if(fExt!='.txt'){
		alert("请选择正确的文件格式:.txt");
		return false;
	}
	$("#"+seeId).val("已选择文件");
	return true;
}
function uploadFile1(btn,fileId) {
	if($("#gid option:selected").val()==""){
		alert("上传文件之前，请先选择游戏！");
		return false;
	};
	var fName = $("#"+fileId).val();
	if(fName==''){
		alert("请选择要上传的资料");
		return false;
	}
	var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if(fExt!='.txt'){
		alert("请选择正确的文件格式:.txt");
		return false;
	}

	$(btn).html("uploading...");
	$.ajaxFileUpload( {
		url : "${pageContext.request.contextPath }/basedata/ekey/eKMallProductAction!upload.action?id="+$("#cid").val(),//用于文件上传的服务器端请求地址
		secureuri : false,//一般设置为false
		fileElementId : fileId,//文件上传控件的id属性
		dataType : 'json',//返回值类型 一般设置为json
		success : function(data, status){ //服务器成功响应处理函数
			$(btn).val("上传完成");
		},
		error : function(data, status, e){//服务器响应失败处理函数
			$(btn).val($.parseJSON(data).status);
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
    	系统信息 -&gt; e键:商城 -&gt;商品详情管理-&gt; 编辑商品
   	</td>
  </tr>
</table>
<form method="post" id="form1">
<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	<input type="hidden" id="addFlag" name="addFlag" value="<s:property value="#request.addFlag"/>" >
	<input type="hidden" id="cid" name="cid" value="<s:property value="#request.cid"/>" >
	<br/>

	<tr>
		<td width="5%"  height="40" class="biaodan-top" align="left">标题：</td>
		<td align="center" class="biaodan-q" width="15%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="title" name="title" value="<s:property value="#request.title"/>" maxlength="64" >
		</td>
		<td width="5%"  height="40" class="biaodan-top" align="left">子标题：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="subTitle" name="subTitle" value="<s:property value="#request.subTitle"/>" maxlength="64" >
		</td>
	</tr>

	<tr>
		<td width="5%"  height="40" class="biaodan-top" align="left">用途说明：</td>
		<td align="center" class="biaodan-q" width="15%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="function" name="function" value="<s:property value="#request.function"/>" maxlength="64" >
		</td>
		<td width="5%"  height="40" class="biaodan-top" align="left">简介：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="abstract" name="_abstract" value="<s:property value="#request._abstract"/>" maxlength="128" >
		</td>
	</tr>

	<tr>
		<td width="5%"  height="40" class="biaodan-top" align="left">积分价值：</td>
		<td align="center" class="biaodan-q" width="15%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="cost" name="cost" value="<s:property value="#request.cost"/>" maxlength="64" >
		</td>
		<td width="5%"  height="40" class="biaodan-top" align="left">数量：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="total" name="total" value="<s:property value="#request.total"/>" maxlength="64" >
		</td>
	</tr>

	<tr>
		<td width="5%"  height="40" class="biaodan-top" align="left">类型：</td>
		<td align="center" class="biaodan-q" width="15%">
			<select  name="type" id="type"  style="width:100%;border:none;hight:100%;">
				<option value="1" <c:if test="${type==1}">selected</c:if>>虚拟物品</option>
				<option value="2" <c:if test="${type==2}">selected</c:if>>实物物品</option>
			</select>
		</td>
		<td width="5%"  height="40" class="biaodan-top" align="left">物流费：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="carriage" name="carriage" value="<s:property value="#request.carriage"/>"  >
		</td>
	</tr>

	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">图片：</td>
		<td align="center" class="biaodan-q">
	    	<span class="file-box moreImg">
				<span id="imgArea">
			<s:if test="#request.logo != null">
				<a href="<s:property value="#request.logo"/>" target="_blank" id="logourlScanImgA"><img id="logourlScanImg" style="width: 30px;height: 30px;" src="<s:property value="#request.logo"/>" ></a>
			</s:if>
          	<s:else>
				<a target="_blank" id="logourlScanImgA" style="display: none"><img id="logourlScanImg" style="width: 30px;height: 30px;" ></a>
			</s:else>
				</span>
			<input type="hidden" name="logo" id="logourlScan" value="<s:property value="#request.logo"/>"/>
       	 	<input type="button" style="width:70px;" class="file-btn" id="logourlScanSee" value="浏览..." />
       	 	<input type="file" style="height:24px;" class="file-file" id="logourlScanField" size="28" multiple=true  onchange="return checkFile(this,'logourlScanSee')" />
          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'logourlScanField','logourlScan');" />
       		</span>
		</td>
		<td width="5%"  height="40" class="biaodan-top" align="left">单次可购买的数量：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="amount" name="amount" value="<s:property value="#request.amount"/>" maxlength="64" >
		</td>
	</tr>

	<tr>
		<td width="5%"  height="40" class="biaodan-top" align="left">是否可用：</td>
		<td align="center" class="biaodan-q" width="15%">
			<select  name="islive" id="islive"  style="width:100%;border:none;hight:100%;">
				<option value="1" <c:if test="${islive==1}">selected</c:if>>是</option>
				<option value="0" <c:if test="${islive==0}">selected</c:if>>否</option>
			</select>
		</td>
		<td width="5%"  class="biaodan-top" align="left">商品码：</td>
		<td width="18%" class="biaodan-q" align="left">
			<span class="file-box">
         	 	<input type="button" style="width:70px;" class="file-btn" id="codeScanSee" value="浏览..." />
         	 	<input type="file" style="height:24px;" class="file-file" id="codeScanField" size="28" onchange="return checkFile1(this,'codeScanSee')" />
	          	<input type="button" style="width:70px;" id="shopCode"  class="file-btn" value="上传" onclick="return uploadFile1(this,'codeScanField');" />
          	</span>
		</td>
	</tr>
	<tr>
		<td width="5%"  class="biaodan-top" align="left">商品详情：</td>
		<td align="center" class="biaodan-q" width="18%" colspan="3">
			<textarea name="detail" id="detail" style="border:none; width:100%; height:500px;visibility:hidden;" ><s:property value="#request.detail"/></textarea>
		</td>
	</tr>

	<tr>
		<td width="5%"  class="biaodan-top" align="left">使用说明：</td>
		<td align="center" class="biaodan-q" width="18%" colspan="3">
			<textarea name="illustr" id="illustr" style="border:none; width:100%; height:500px;visibility:hidden;" ><s:property value="#request.illustr"/></textarea>
		</td>
	</tr>

	<%--<tr>
		<td width="5%"  height="40" class="biaodan-top" align="left">商品详情：</td>
		<td align="center" class="biaodan-q" width="15%" colspan="4">
			<textarea id="_detail" name="detail" style="display:none"><s:property value="#request.detail"/></textarea>
				<script id="detail" type="text/javascript" style="width:800px;height:500px;">
				</script>
		</td>
	</tr>
	<tr>
		<td width="5%"  height="40" class="biaodan-top" align="left">使用说明：</td>
		<td align="center" class="biaodan-q" width="15%" colspan="4">
			    <textarea id="_illustr" name="illustr" style="display:none"><s:property value="#request.illustr"/></textarea>
				<script id="illustr" type="text/javascript" style="width:800px;height:500px;">
				</script>
		</td>
	</tr>--%>



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


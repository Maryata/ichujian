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
<title>礼包管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script type="text/javascript">rootPath ="${pageContext.request.contextPath}";</script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.selectseach.min.js"></script>
<style type="text/css">
.file-btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
.file-file{ position:absolute; top:-4px; right:74px; height:24px; filter:alpha(opacity:0);opacity: 0;width:70px; }
.file-box {position: relative;width: 340px}
</style>
<script type="text/javascript">
$(function(){
	$("select").selectseach(); 
});
function checkFile(file,seeId){
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
function uploadFile(btn,fileId) {
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
		url : "${pageContext.request.contextPath }/basedata/gameGiftAction!upload.action?id="+$("#newId").val(),//用于文件上传的服务器端请求地址
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
function formValid(){
	var _flag = true;
	if($("#gid option:selected").val()==""){
		alert("请选择游戏！");
		_flag = false;
		return _flag;
	};
	if(_flag){
		$("#form1").attr("action","${pageContext.request.contextPath }/basedata/gameGiftAction!saveGameGift.action");
	}
	return _flag;
}
</script>
</head>

<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 
    	<s:if test="#request.addFlag==1">系统信息 -&gt; 3号键管理 -&gt; 礼包管理-&gt; 添加礼包</s:if>
		<s:else>系统信息 -&gt; 3号键管理 -&gt; 礼包管理-&gt; 编辑礼包</s:else>
   	</td>
  </tr>
</table>
<br>
<br>
<form method="post" id="form1">
<style>.biaodan-top{height:40px;}</style>
<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	<input type="hidden" name="id" id="newId" value="<s:property value='#request.newId'/>" />
	<tr>
	  	<td width="5%"  class="biaodan-top" align="left">开始时间：</td>
	 	<td width="15%" class="biaodan-q" align="left">
			<input type="text" style="border:none; width:100%; height:100%" id="sdate" name="sdate" class="Wdate"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<s:property value='#request.editSDate'/>"/>
		</td>
	  	<td width="5%"  class="biaodan-top" align="left">结束时间：</td>
	 	<td width="18%" class="biaodan-q" align="left">
			<input type="text" style="border:none; width:100%; height:100%" id="edate" name="edate" class="Wdate"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<s:property value='#request.editEDate'/>"/>
		</td>
	</tr>
	<tr> 
	 	<td width="5%"  class="biaodan-top" align="left">游戏名称：</td>
	  	<td width="15%" align="center" class="biaodan-q">
	     	<s:select list="#request.games" name="gid" id="gid" listKey="C_ID" value="#request.editGid"
	     	 listValue="C_NAME" cssStyle="width:100%;border:none;hight:100%;" m="search" 
	     	 headerKey="" headerValue=""/>
	  	</td>
	 	<td width="5%"  class="biaodan-top" align="left">礼包分类：</td>
	  	<td align="center" class="biaodan-q">
	     	<s:select list="#request.cate" name="cate" id="cate" listKey="C_ID" value="#request.editCate"
	     	 listValue="C_NAME" cssStyle="width:100%;border:none;hight:100%;" m="search" 
	     	 headerKey="" headerValue=""/>
	  	</td>
	</tr>
	<tr>
	  	<td width="5%"  class="biaodan-top" align="left">礼包名称：</td>
	 	<td width="15%" class="biaodan-q" align="left">
			<input type="text" style="border:none; width:100%; height:100%" id="name" name="name"
				value="<s:property value='#request.editName'/>"/>
		</td>
		<td width="5%"  class="biaodan-top" align="left">排&nbsp;&nbsp;&nbsp;&nbsp;序：</td>
	  	<td width="18%" align="center" class="biaodan-q">
	     	<input type="text" style="border:none; width:100%; height:100%" id="order" name="order" 
	     		value="<s:property value='#request.editOrder'/>"/>
	  	</td>
	</tr>
	<tr>
		<td width="5%"  class="biaodan-top" align="left">礼包内容：</td>
		<td width="15%" class="biaodan-q" align="left" colspan="3">
			<input type="text" style="border:none; width:100%; height:100%" id="depict" name="depict"
				value="<s:property value='#request.editDepict'/>"/>
		</td>
	</tr>
	<tr>
		<td width="5%"  class="biaodan-top" align="left">礼包方法：</td>
	 	<td width="15%" class="biaodan-q" align="left" colspan="3">
			<input type="text" style="border:none; width:100%; height:100%" id="method" name="method"
				value="<s:property value='#request.editMehtod'/>"/>
		</td>
	</tr>
	<tr>
		<td width="5%"  class="biaodan-top" align="left">礼包码数量：</td>
	 	<td width="15%" class="biaodan-q" align="left">
			<input type="text" style="border:none; width:100%; height:100%" id="count" name="count"
				value="<s:property value='#request.editCount'/>"/>
		</td>
		<td width="5%"  class="biaodan-top" align="left">礼 包 码：</td>
		<td width="18%" class="biaodan-q" align="left">
			<span class="file-box">
         	 	<input type="button" style="width:70px;" class="file-btn" id="codeScanSee" value="浏览..." />
         	 	<input type="file" style="height:24px;" class="file-file" id="codeScanField" size="28" onchange="return checkFile(this,'codeScanSee')" />
	          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'codeScanField');" />
          	</span>
		</td>
	</tr>
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


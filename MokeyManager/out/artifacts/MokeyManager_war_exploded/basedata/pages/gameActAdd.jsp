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
<title>活动管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script type="text/javascript">rootPath ="${pageContext.request.contextPath}";</script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.selectseach.min.js"></script>
<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.file-btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
.file-file{ position:absolute; top:-4px; right:74px; height:24px; filter:alpha(opacity:0);opacity: 0;width:70px; }
.file-box {position: relative;width: 340px}
</style>
<script type="text/javascript">
$(function(){
	$("select").selectseach(); 
});
	KindEditor.ready(function(K) {
		window.editor_content = K.create('#depict', {
			urlType : 'domain',
			fullscreenMode:false,
            resizeType : 1,  //文本框拖动  1/yes，0/no
            uploadJson : "${pageContext.request.contextPath}/KindEditServlet.do?type=gameActivityShare/upload&id="+$("#newId").val(),
//             fileManagerJson : '${pageContext.request.contextPath}/js/kindeditor/plugins/image/jsp/file_manager_json.jsp',
//             allowFileManager : true,
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
	var _flag = true;
	if($("#gid option:selected").val()==""){
		alert("请选择游戏！");
		_flag = false;
		return _flag;
	};
	if($("#actName").val()==""){
		alert("“活动名称”不能为空！");
		_flag = false;
		return _flag;
	};
	if($("#sDate").val()==""){
		alert("“开始时间”不能为空！");
		_flag = false;
		return _flag;
	};
	if($("#eDate").val()==""){
		alert("“结束时间”不能为空！");
		_flag = false;
		return _flag;
	};
	if(_flag){
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
		$("#form1").attr("action","${pageContext.request.contextPath }/basedata/gameActAction!saveGameAct.action");
	}
	return _flag;
}
function checkFile(file,seeId){
	var fName = $(file).val();
	if(fName==''){
		alert("请选择要上传的资料");
		return false;
	}
	var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if(fExt!='.jpg' && fExt!='.gif' && fExt!='.jpeg' && fExt!='.bpm'){
		alert("请选择正确的图片格式:.jpg,.gif,.jpeg,.bpm");
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
	if(fExt!='.jpg' && fExt!='.gif' && fExt!='.jpeg' && fExt!='.bpm'){
		alert("请选择正确的图片格式:.jpg,.gif,.jpeg,.bpm");
		return false;
	}	

	$(btn).html("uploading...");
	$.ajaxFileUpload( {
		url : "${pageContext.request.contextPath }/LogoUploadServlet.do?type=activity&id="+$("#newId").val(),//用于文件上传的服务器端请求地址
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
    	<s:if test="#request.flag==1">系统信息 -&gt; 3号键管理 -&gt; 活动管理-&gt; 添加活动</s:if>
		<s:else>系统信息 -&gt; 3号键管理 -&gt; 活动管理-&gt; 编辑活动</s:else>
   	</td>
  </tr>
</table>
<form method="post" id="form1">
<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	<input type="hidden" name="id" id="newId" value="<s:property value='#request.newId'/>" />
  <tr> 
   	<td width="8%"  height="23" class="biaodan-top" align="left">游戏名称</td>
    <td width="10%" align="center" class="biaodan-q">
       	<s:select list="#request.games" name="gid" id="gid" listKey="C_ID" value="#request.editGid"
       	 listValue="C_NAME" cssStyle="width:100%;border:none;hight:100%;" m="search" 
       	 headerKey="" headerValue=""/>
    </td>
   	<td width="8%"  height="23" class="biaodan-top" align="left">排序</td>
    <td width="10%" align="center" class="biaodan-q">
       	<input type="text" style="border:none; width:100%; height:100%" id="order" name="order" 
       		value="<s:property value='#request.editOrder'/>"/>
    </td>
    <td width="5%" class="biaodan-q" align="center">
		<button class="butt" onclick="return formValid();">保 存</button>
 	</td>
  </tr>
  <tr>
  	<td width="8%"  height="23" class="biaodan-top" align="left">活动名称</td>
   	<td width="10%" class="biaodan-q" align="left">
		<input type="text" style="border:none; width:100%; height:100%" id="infoName" name="name"
			value="<s:property value='#request.editName'/>"/>
 	</td>
 	<td width="8%"  height="23" class="biaodan-top" align="left">活动LOGO</td>
	<td width="10%" class="biaodan-q" align="left" colspan="2">
		<span class="file-box">
			<s:if test="#request.logourl != null"> 
           		<a href="<s:property value="#request.logourl"/>" target="_blank" id="logourlScanImgA"><img id="logourlScanImg" style="width: 30px;height: 30px;" src="<s:property value="#request.logourl"/>" ></a>
          	</s:if>
          	<s:else>
            	<a target="_blank" id="logourlScanImgA" style="display: none"><img id="logourlScanImg" style="width: 30px;height: 30px;" ></a>
          	</s:else>
			<input type="hidden" name="logourlScan" id="logourlScan" value="<s:property value="#request.logourl"/>"/>
       	 	<input type="button" style="width:70px;" class="file-btn" id="logourlScanSee" value="浏览..." />
       	 	<input type="file" style="height:24px;" class="file-file" id="logourlScanField" size="28" onchange="return checkFile(this,'logourlScanSee')" />
          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'logourlScanField','logourlScan');" />
       	</span>
	</td>
  </tr>
  <tr>
  	 <td width="10%" height="50" class="biaodan-top" align="left">开始时间</td>
		<td class="biaodan-q"  align="left">
		<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="sDate" name="sDate" 
		value='<s:property value="#request.sDate"/>' style="border:none; width:90%; height:100%; text-align:center;" /> 
		</td>
		<td width="10%" height="50" class="biaodan-top" align="left">结束时间</td>
		<td class="biaodan-q"  align="left" colspan="2">
		<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="eDate" name="eDate" 
			value='<s:property value="#request.eDate"/>' style="border:none; width:90%; height:100%; text-align:center;" /> 
     </td>
  </tr>
  <tr>
  	<td width="8%"  height="23" class="biaodan-top" align="left">奖励</td>
    <td width="10%" align="center" class="biaodan-q" colspan="5">
       	<input type="text" style="border:none; width:100%; height:100%" id="reward" name="reward" 
       		value="<s:property value='#request.reward'/>"/>
    </td>
  </tr>
  <tr>
  	<td colspan="5">
  		<textarea id="depict" name="depict" style="width:99.8%;height:400px;visibility:hidden;">
  			<s:property value='#request.editDepict'/>
  		</textarea>
	</td>
  </tr>
</table>
</form>
<br>
</body>
</html>


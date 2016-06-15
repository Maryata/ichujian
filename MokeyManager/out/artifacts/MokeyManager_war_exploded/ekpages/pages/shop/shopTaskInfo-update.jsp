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
	<title>任务维护</title>
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
			/*if($("#name").val()==""){
			 alert("“名称”不能为空！");
			 _flag = false;
			 return _flag;
			 };*/
			if(_flag){
				if($("#addFlag").val()=="1"){
					$("#form1").attr("action","${pageContext.request.contextPath }/basedata/ekey/eKShopTaskInfoAction!addTask.action");
				}else{
					$("#form1").attr("action","${pageContext.request.contextPath }/basedata/ekey/eKShopTaskInfoAction!updateTask.action");
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
				url : "${pageContext.request.contextPath }/LogoUploadServlet.do?ekey=1&type=indexAdvert/logo&id="+$("#cid").val(),//用于文件上传的服务器端请求地址
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
			系统信息 -&gt; e键:商城 -&gt;任务管理-&gt; 编辑分类
		</td>
	</tr>
</table>
<form method="post" id="form1">
	<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
		<input type="hidden" id="cid" name="cid" value="<s:property value="#request.cid"/>" >
		<input type="hidden" id="addFlag" name="addFlag" value="<s:property value="#request.addFlag"/>" >
		<br/>
		<tr>
			<td width="17%" height="23" class="biaodan-top">任务类型：</td>
			<td class="biaodan-q" colspan="4">
				<select name="type" id="type">
					<option value="1" <c:if test="${type eq '1'}">selected</c:if>>新手任务</option>
					<option value="2" <c:if test="${type eq '2'}">selected</c:if>>每日任务</option>
					<option value="3" <c:if test="${type eq '3'}">selected</c:if>>特殊任务</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" height="23" class="biaodan-top">子任务类型：</td>
			<td width="33%" class="biaodan-q">
				<select name="subtype" id="subtype">
				</select>
			</td>
			<td width="17%" height="23" class="biaodan-top">是否有效：</td>
			<td  width="33%" colspan="2" class="biaodan-q">
				<select id="islive" name="islive">
					<option value="1" <c:if test="${islive==1}">selected</c:if>>是</option>
					<option value="0" <c:if test="${islive==0}">selected</c:if>>否</option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="17%" height="23" class="biaodan-top">任务名称：</td>
			<td width="33%" colspan="2" class="biaodan-q">
				<input type="text" id="name" name="name" value="<s:property value="#request.name"/>"/>
			</td>
			<td width="17%" height="23" class="biaodan-top">子任务名称：</td>
			<td width="33%" colspan="2" class="biaodan-q">
				<input type="text" id="subName" name="subName" value="<s:property value="#request.subName"/>"/>
			</td>
		</tr>
		<tr>
			<td width="17%" height="23" class="biaodan-top">任务logo图标：</td>
			<td width="33%" class="biaodan-q" colspan="4">
			<span class="file-box moreImg" >
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
		</tr>
		<tr>
			<td width="17%" height="23" class="biaodan-top">任务分值：</td>
			<td width="33%" colspan="4" class="biaodan-q">
				<input id="score" name="score" value="<s:property value="#request.score"/>"/></td>
		</tr>
		<tr>
			<td class="biaodan-q" align="center" colspan="5">
				<button class="butt" onclick="return formValid();">保 存</button>
			</td>
		</tr>
	</table>
</form>
<script>
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
		var tid = $("#cid").val();
		$(btn).html("uploading...");
		$.ajaxFileUpload( {// ekey : 1.首页，2.活动，3.游戏
			url : "${pageContext.request.contextPath }/LogoUploadServlet.do?ekey=4&type=shop/logo&id="+$("#cid").val(),//用于文件上传的服务器端请求地址
			secureuri : false,//一般设置为false
			fileElementId : fileId,//文件上传控件的id属性
			dataType : 'json',//返回值类型 一般设置为json
			success : function(data, status){ //服务器成功响应处理函数
				if(data.filePath){
					var path = data.filePath;
					var arr= path.split(",");
					if(arr.length==1){
						$('#'+baseId).val(path);
						$('#'+baseId+"Img").attr("src",path);
						$('#'+baseId+"ImgA").attr("href",path).show();
						$('#'+baseId+"See").val("浏览...");
						$(btn).val("上传完成");
					}else{
						$('#'+baseId).val(path);
						$('#'+baseId+"See").val("浏览...");
						$("#imgArea").empty();
						for(var i=0;i<arr.length;i++){
							var _tr="<a target='_blank' href='"+arr[i]+"'><img style='width: 30px;height: 30px;' src='"+arr[i]+"'></a>";
							$(".moreImg").prepend(_tr);
						}
					}

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
	$(function(){
		$("#subName").attr("disabled",true);
		$("#type").change(function () {
			var type = $("#type").val();
			if(type=='1'){
				$("#subName").attr("disabled",true);
				$("#name").attr("disabled",false);
				$("#subtype").empty();
				$("#name").val("");
			}else{
				$("#subName").attr("disabled",false);
				$("#name").attr("disabled",true);
				$.post("${pageContext.request.contextPath}/basedata/ekey/eKShopTaskInfoAction!loadInfos.action",{type:type},
						function(data){
							var json = $.parseJSON(data);
							var list = json[0].list;
							$("#subtype").empty();
							$(list).each(function(){
								var _s = "<option value='"+this.C_SUBTYPE+"'>"+this.C_NAME+"</option>";
								$("#subtype").append(_s);
							});
							$("#name").val($("#subtype").find("option:selected").text());

						});
			}
		});

		$("#subtype").change(function(){
			$("#name").val($("#subtype").find("option:selected").text());
		});


		$('#type').change();
	});



</script>
</body>
</html>

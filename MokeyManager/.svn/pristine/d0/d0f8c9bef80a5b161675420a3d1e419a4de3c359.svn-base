<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="/struts-tags" %>
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
<title>活动详情</title>
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
</head>

<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 
    	系统信息 -&gt; e键管理 -&gt;活动详情管理-&gt; 编辑分类
   	</td>
  </tr>
</table>
<form method="post" id="form1" class="form-horizontal">
<table width="80%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	<input type="hidden" id="addFlag" name="addFlag" value="<s:property value="#request.addFlag"/>" >
	<input type="hidden" id="cid" name="cid" value="<s:property value="#request.cid"/>" >
	<br/>
	<tr>
		<td width="5%"  class="biaodan-top" align="left">开始时间：</td>
		<td width="15%" class="biaodan-q" align="left">
			<input type="text" style="border:none; width:100%; height:100%" id="sdate" name="sdate" class="Wdate"
				   onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'edate\')}'})" value="<s:property value='#request.sdate'/>"/>
		</td>
		<td width="5%"  class="biaodan-top" align="left">结束时间：</td>
		<td width="18%" class="biaodan-q" align="left">
			<input type="text" style="border:none; width:100%; height:100%" id="edate" name="edate" class="Wdate"
				   onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'sdate\')}'})" value="<s:property value='#request.edate'/>"/>
		</td>
	</tr>
  	<tr> 
	   	<td width="5%"  height="40" class="biaodan-top" align="left">标题：</td>
		<td align="center" class="biaodan-q" width="15%">
			<input type="text" style="border:none; width:100%; height:100%" class="required"
				   id="title" name="title" value="<s:property value="#request.title"/>" maxlength="64" >
		</td>
		<td width="5%"  height="40" class="biaodan-top" align="left">子标题：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="subTitle" name="subTitle" value="<s:property value="#request.subTitle"/>" maxlength="64" >
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
          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'logourlScanField','logourlScan','1');" />
       		</span>
	    </td>
		<td width="5%"  height="23" class="biaodan-top" align="left">首页图片：</td>
		<td align="center" class="biaodan-q">
	    	<span class="file-box">
			<s:if test="#request.indexImg != null">
				<a href="<s:property value="#request.indexImg"/>" target="_blank" id="indexImgurlScanImgA"><img id="indexImgurlScanImg" style="width: 30px;height: 30px;" src="<s:property value="#request.indexImg"/>" ></a>
			</s:if>
          	<s:else>
				<a target="_blank" id="indexImgurlScanImgA" style="display: none"><img id="indexImgurlScanImg" style="width: 30px;height: 30px;" ></a>
			</s:else>
			<input type="hidden" name="indexImg" id="indexImgurlScan" value="<s:property value="#request.indexImg"/>"/>
       	 	<input type="button" style="width:70px;" class="file-btn" id="indexImgurlScanSee" value="浏览..." />
       	 	<input type="file" style="height:24px;" class="file-file" id="indexImgurlScanField" size="28"   onchange="return checkFile(this,'indexImgurlScanSee')" />
          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'indexImgurlScanField','indexImgurlScan','4');" />
       		</span>
		</td>
  	</tr>
	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">长图：</td>
		<td align="center" class="biaodan-q">
	    	<span class="file-box">
			<s:if test="#request.longImg != null">
				<a href="<s:property value="#request.longImg"/>" target="_blank" id="longImgurlScanImgA"><img id="longImgurlScanImg" style="width: 30px;height: 30px;" src="<s:property value="#request.longImg"/>" ></a>
			</s:if>
          	<s:else>
				<a target="_blank" id="longImgurlScanImgA" style="display: none"><img id="longImgurlScanImg" style="width: 30px;height: 30px;" ></a>
			</s:else>
			<input type="hidden" name="longImg" id="longImgurlScan" value="<s:property value="#request.longImg"/>"/>
       	 	<input type="button" style="width:70px;" class="file-btn" id="longImgurlScanSee" value="浏览..." />
       	 	<input type="file" style="height:24px;" class="file-file" id="longImgurlScanField" size="28" multiple=true  onchange="return checkFile(this,'longImgurlScanSee')" />
          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'longImgurlScanField','longImgurlScan','2');" />
       		</span>
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">短图：</td>
		<td align="center" class="biaodan-q">
	    	<span class="file-box">
			<s:if test="#request.thinImg != null">
				<a href="<s:property value="#request.thinImg"/>" target="_blank" id="thinImgurlScanImgA"><img id="thinImgurlScanImg" style="width: 30px;height: 30px;" src="<s:property value="#request.thinImg"/>" ></a>
			</s:if>
          	<s:else>
				<a target="_blank" id="thinImgurlScanImgA" style="display: none"><img id="thinImgurlScanImg" style="width: 30px;height: 30px;" ></a>
			</s:else>
			<input type="hidden" name="thinImg" id="thinImgurlScan" value="<s:property value="#request.thinImg"/>"/>
       	 	<input type="button" style="width:70px;" class="file-btn" id="thinImgurlScanSee" value="浏览..." />
       	 	<input type="file" style="height:24px;" class="file-file" id="thinImgurlScanField" size="28" multiple=true  onchange="return checkFile(this,'thinImgurlScanSee')" />
          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'thinImgurlScanField','thinImgurlScan','3');" />
       		</span>
		</td>
	</tr>
	<%--<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">非首页分类的半窄图：</td>
		<td align="center" class="biaodan-q">
	    	<span class="file-box">
			<s:if test="#request.halfImg != null">
				<a href="<s:property value="#request.longImg"/>" target="_blank" id="halfImgurlScanImgA"><img id="halfImgurlScanImg" style="width: 30px;height: 30px;" src="<s:property value="#request.halfImg"/>" ></a>
			</s:if>
          	<s:else>
				<a target="_blank" id="halfImgurlScanImgA" style="display: none"><img id="halfImgurlScanImg" style="width: 30px;height: 30px;" ></a>
			</s:else>
			<input type="hidden" name="halfImg" id="halfImgurlScan" value="<s:property value="#request.halfImg"/>"/>
       	 	<input type="button" style="width:70px;" class="file-btn" id="halfImgurlScanSee" value="浏览..." />
       	 	<input type="file" style="height:24px;" class="file-file" id="halfImgurlScanField" size="28" multiple=true  onchange="return checkFile(this,'halfImgurlScanSee')" />
          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'halfImgurlScanField','halfImgurlScan','5');" />
       		</span>
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">非首页分类的标题配图：</td>
		<td align="center" class="biaodan-q">
	    	<span class="file-box">
			<s:if test="#request.titleImg != null">
				<a href="<s:property value="#request.titleImg"/>" target="_blank" id="titleImgurlScanImgA"><img id="titleImgurlScanImg" style="width: 30px;height: 30px;" src="<s:property value="#request.titleImg"/>" ></a>
			</s:if>
          	<s:else>
				<a target="_blank" id="titleImgurlScanImgA" style="display: none"><img id="titleImgurlScanImg" style="width: 30px;height: 30px;" ></a>
			</s:else>
			<input type="hidden" name="thinImg" id="titleImgurlScan" value="<s:property value="#request.titleImg"/>"/>
       	 	<input type="button" style="width:70px;" class="file-btn" id="titleImgurlScanSee" value="浏览..." />
       	 	<input type="file" style="height:24px;" class="file-file" id="titleImgurlScanField" size="28" multiple=true  onchange="return checkFile(this,'titleImgurlScanSee')" />
          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'titleImgurlScanField','titleImgurlScan','6');" />
       		</span>
		</td>
	</tr>--%>
	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">分享URL：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="urlShare" name="urlShare" value="<s:property value="#request.urlShare"/>" disabled="disabled" maxlength="128" >
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">活动内容webview路径：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%" disabled="disabled"
				   id="webViewUrl" name="webViewUrl" value="<s:property value="#request.webViewUrl"/>" maxlength="128" >
		</td>
	</tr>
	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">默认浏览数：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="view" name="view" value="<s:property value="#request.view"/>" maxlength="128" >
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">默认点赞数：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="vote" name="vote" value="<s:property value="#request.vote"/>" maxlength="128" >
		</td>
	</tr>
	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">注意事项：</td>
		<input type="hidden" id="noticeBef" value="<s:property value="#request.noticeBef" />"/>
		<input type="hidden" id="orderBef" value="<s:property value="#request.orderBef" />"/>
		<input type="hidden" id="orderArr" name="orderArr"/>
		<input type="hidden" id="noticeArr" name="noticeArr"/>
		<td align="left" class="biaodan-q" colspan="3">
			<s:iterator value="#request.noticeList">
				<span class="noticeItem">
				<input type="number" name="order" min="1" style="width:50px;">
				<input type="checkbox" name="notice" value="<s:property value="C_ID" />" ><s:property value="C_NAME" />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
			</s:iterator>
		</td>
	</tr>
	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">默认收藏数：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="favorite" name="favorite" value="<s:property value="#request.favorite"/>" maxlength="128" >
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">推荐理由：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="reason" name="reason" value="<s:property value="#request.reason"/>" maxlength="128" >
		</td>
	</tr>
	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">信息来源：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="publisher" name="publisher" value="<s:property value="#request.publisher"/>" maxlength="32" >
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">活动链接：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="url" name="url" value="<s:property value="#request.url"/>" maxlength="1024" >
		</td>
	</tr>
	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">小编提示：</td>
		<td align="center" class="biaodan-q" width="18%">
			<input type="text" style="border:none; width:100%; height:100%"
				   id="tip" name="tip" value="<s:property value="#request.tip"/>" maxlength="128" >
		</td>
		<td width="5%"  height="23" class="biaodan-top" align="left">活动初始分类ID：</td>
		<td align="center" class="biaodan-q" width="18%">
				<s:select list="#request.activityCategoryList" name="ccid" id="ccid" listKey="C_ID" value="#request.ccid"
						  listValue="C_NAME" cssStyle="width:100%;border:none;hight:100%;" m="search"
						 />
		</td>
	</tr>
	<tr>
		<td width="5%"  height="23" class="biaodan-top" align="left">活动详情：</td>
		<td align="center" class="biaodan-q" width="18%" colspan="3">
				<textarea name="detail" id="detail" style="border:none; width:100%; height:100%;" maxlength="128"><s:property value="#request.detail"/></textarea>
		</td>
	</tr>
	<tr>
		<td width="5%"  class="biaodan-top" align="left">完整活动详情：</td>
		<td align="center" class="biaodan-q" width="18%" colspan="3">
			<textarea name="fullDetail" id="fullDetail" style="border:none; width:100%; height:500px;visibility:hidden;" ><s:property value="#request.fullDetail"/></textarea>
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
		var href = $("#logourlScan").val();
		var arr= href.split(",");
		if(arr.length>1){
			$("#logourlScanImgA").hide();
			for(var i=0;i<arr.length;i++){
				var _tr="<a target='_blank' href='"+arr[i]+"'><img style='width: 30px;height: 30px;' src='"+arr[i]+"'></a>";
				$(".moreImg").prepend(_tr);
			}
		}


	});
	$(function(){
		var $noticeId = $("#noticeBef").val();
		var $orderBef = $("#orderBef").val();
		if ($noticeId.length > 0 && $orderBef.length > 0) {
			var $noticeIdArr = $noticeId.split(",");
			var $orderBef = $orderBef.split(",");
			$("input[name='notice']").each(function(){
				var $notice = this.value;
				for (var i = 0; i < $noticeIdArr.length; i++) {
					if ($notice == $noticeIdArr[i]) {
						$(this).attr("checked", "checked");
						$(this).prev("input[name='order']").val($orderBef[i]);
						break;
					}
				}
			});
		}
	});
	KindEditor.ready(function(K){
		editor1 = K.create('textarea[name="fullDetail"]', {
			allowFileManager : true,
			resizeMode : 1,
			shadowMode : false,
			allowPreviewEmoticons : false,
			allowUpload : true,
			//syncType : 'auto',
			urlType : 'domain',
			//cssPath : 'css/ke.css',
			uploadJson:'${pageContext.request.contextPath }/KindEditServlet.do?ekey=2&type=activityActivity/activityEdit&id='+$("#cid").val()
		});
	});
	function formValid(){
		var _flag = true;
		if($("#cname").val()==""){
			alert("“分类名称”不能为空！");
			_flag = false;
			return _flag;
		};
		if (_flag) {
			// 处理大尺寸图片
//			$(".ke-edit-iframe").contents().find("img").each(function(){
//				var _width = this.width;
//				var _height = this.height;
//				// 如果图片宽度大于300，按照等比例缩小为宽度300（同时高度等比例缩小）
//				if(_width>300){
//					_height = parseInt((300 * _height) / _width);
//					_width = 300;
//				}
//				// 为img标签添加宽高属性
//				$(this).attr("width", _width);
//				$(this).attr("height", _height);
//			});
			var noticeArr = new Array();
			var orderArr = new Array();
			$(".noticeItem").each(function(){
				$currNotice= $(this).find("input[name='notice']");
				$currOrder= $(this).find("input[name='order']");
				// 填排序没有勾选
				if ($currOrder.val()!=null && $currOrder.val()!="" && !$($currNotice).is(":checked")) {
					alert("“注意事项”未选择或排序未填写");
					_flag = false;
					return false;
				}
				// 勾选了没填排序
				if (($currOrder.val()==null || $currOrder.val()=="") && $($currNotice).is(":checked")) {
					alert("“注意事项”未选择或排序未填写");
					_flag = false;
					return false;
				}
				if ($($currNotice).attr("checked")=="checked" && $currOrder.val()!=null && $currOrder.val()!="") {
					noticeArr.push($($currNotice).val())
					orderArr.push($($currOrder).val())
				}
			});
			$("#noticeArr").val(noticeArr.toString());
			$("#orderArr").val(orderArr.toString());
		}



		if(_flag){
			if($("#addFlag").val()=="1"){
				$("#form1").attr("action","${pageContext.request.contextPath }/basedata/ekey/EKActivityAction!addActivity.action");
			}else{
				$("#form1").attr("action","${pageContext.request.contextPath }/basedata/ekey/EKActivityAction!updateActivity.action");
			}
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
		if(fExt!='.jpg' && fExt!='.gif' && fExt!='.jpeg' && fExt!='.bpm' && fExt!='.png'){
			alert("请选择正确的图片格式:.jpg,.gif,.jpeg,.bpm,png");
			return false;
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
		if(fExt!='.jpg' && fExt!='.gif' && fExt!='.jpeg' && fExt!='.bpm' && fExt!='.png'){
			alert("请选择正确的图片格式:.jpg,.gif,.jpeg,.bpm,png");
			return false;
		}
		var tid = $("#cid").val();
		var _file="";
		if(vid=="1"){
			_file="logo";
		}else if(vid=="2"){
			_file="longImg";
		}else if(vid=="3"){
			_file="thinImg";
		}else if(vid=="4"){
			_file="indexImg";
		}else if(vid=="5"){
			_file="halfImg";
		}else{
			_file="titleImg";
		}
		$(btn).html("uploading...");
		$.ajaxFileUpload( {// ekey : 1.首页，2.活动，3.游戏
			url : "${pageContext.request.contextPath }/LogoUploadServlet.do?ekey=2&type=activityActivity/"+_file+"&id="+tid,//用于文件上传的服务器端请求地址
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
</script>
</body>
</html>


<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="资料中心" currentListUrl="/manage/dataCenter/selectList?init=y">
<style>
.check-more{ width:959px;  line-height: 1.5em;margin-top: 7px;  margin-bottom: 10px; margin-left: -1px;}
.check-more label{ float:left;margin-right:10px;margin-left:-1px; color:#666; cursor:pointer; font-size:12px; width:125px;}
.file-txt{ height:22px; border:1px solid #cdcdcd; width:180px; padding:0 5px;}
.file-btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
.file-file{ position:absolute; top:-4px; right:74px; height:24px; filter:alpha(opacity:0);opacity: 0;width:70px; }
.file-box {position: relative;width: 340px}
</style>
<form action="${basepath}/manage/dataCenter" method="post" theme="simple" id="form" >
<table class="table table-bordered">
	<table class="table table-bordered">
		<tr>
			<td colspan="2" style="background-color: #dff0d8;text-align: center;">
				<strong>添加资料</strong>
			</td>
		</tr>
		<tr style="display: none;">
			<th>id</th>
			<td><input type="hidden" value="${e.id!""}" name="id" id="id"/></td>
		</tr>
		<tr>
			<th style="text-align: right;">标题</th> 
			<td style="text-align: left;">
				<input type="text" value="${e.title!""}" data-rule="标题:required;title;length[1~15]" name="title" id="title"/>
			</td>
		</tr>
		<tr>
			<th style="text-align: right;">内容</th>
			<td style="text-align: left;">
				<input type="text" value="${e.content!""}" data-rule="内容:content;length[0~100]" name="content" id="content" />
			</td>
		</tr>
		<tr>
		<th style="text-align: right;">图片</th>
			<td style="text-align: left;">
				<span class="file-box">
					<#if e.picture?? && e.picture!='' > 
			            <a href="${e.picture!""}" target="_blank" id="pictureImgA"><img id="pictureImg" style="width: 80px;height: 80px;" title="图片" alt="图片" src="${e.picture}" ></a>
		          	<#else>
		            	<a target="_blank" id="pictureImgA" style="display: none"><img id="pictureImg" style="width: 80px;height: 80px;" title="图片" alt="图片" ></a>
		          	</#if>
		          	<input type="hidden" name="picture" id="picture" value="${e.picture!""}"/>
		          	<input type="button" style="width:70px;" class="file-btn" id="pictureSee" value="浏览..." />
	         	 	<input type="file" style="height:24px;" class="file-file" id="pictureField" size="28" onchange="return checkimg(this,'pictureSee')"/>
		          	<input type="button" style="width:70px;" class="file-btn" value="上传" onclick="return uploadimg(this,'pictureField','picture');" />
	          	</span>
			</td>
			</tr>
		<tr>
		    <#assign dicIdMap = sysDic("dataCenter_dicId") >
			<th style="text-align: right;">栏目</th>
			<td style="text-align: left;" >
               <select name="dicId" id="dicId">
             		<option value="">请选择</option>
             		<#list dicIdMap?keys as key>
             			<option value="${key}" <#if e.dicId?? && e.dicId==key>selected</#if> >
             				${dicIdMap[key]}
         				</option>
             		</#list>	
             	</select><span style="color:red;text-align: left;">(视频资料请放在"视频资料"栏目！)</span>
			</td>
		</tr>
		<tr>
			<th style="text-align: right;">状态</th>
			<td style="text-align: left;" >
                <select class="input-small" id="status" name="status" >
                    <option value="1" <#if e.status?? && e.status == "1">selected="selected" </#if>>启用</option>
                    <option value="0"<#if e.status?? && e.status == "0">selected="selected" </#if>>禁用</option>
                </select>
			</td>
		</tr>
	</table>
	<table class="table table-bordered">
		<tr>
			<td style="text-align:left;width:50%;">
				<span style="color:red;text-align:left;">视频资源请选择格式为.flv,.mp3,.mp4</span>
			</td>
			<td style="text-align:right;width:50%;">
                <input type="button" class="btn btn-success" onclick="addDetail()" value="新增资源详情">
                </input>
			</td>
		</tr>
	</table>
	<table class="table table-bordered" id="tb_detail" style="display: none;">
		<span id="nullVal"></span>
		<tr>
			<th style='text-align: left;width:31%;'>资源名称</th>
			<th style='text-align: left;width:31%;'>上传图片</th>
			<th style='text-align: left;width:31%;'>上传资源<span style="color:red;">(注意：视频文件只能选择栏目"视频资料")</span></th>
			<th style='text-align: center;width:7%;'>删除</th>
		</tr>
		<#if list??>
			<#list list as item>
			<tr>	
				<input type="hidden" name='cId' value='${item.cId}' />
				<input type="hidden" name='ids' value='${item.id}' />
			
				<td style='text-align:left;'>
					<input style='width:300px;' type='text' name='fnames' value='${item.fname}'/>
				</td>
				<td style='text-align:left;'>
					<span class="file-box">
					<#if item.furl?? && item.furl!='' > 
			            <a href="${item.furl!""}" target="_blank" id="furlsImgA"><img id="furlsImg" style="width: 80px;height: 80px;" title="图片" alt="图片" src="${item.furl}" ></a>
		          	<#else>
		            	<a target="_blank" id="furlsImgA" style="display: none"><img id="furlsImg" style="width: 80px;height: 80px;" title="图片" alt="图片" ></a>
		          	</#if>
		          	<input type="hidden" name="furls" id="furls" value="${item.furl!""}"/>
		          	<input type="button" style="width:70px;" class="file-btn" id="furlsSee" value="浏览..." />
	         	 	<input type="file" style="height:24px;" class="file-file" id="furlsField" size="28" onchange="return checkimg(this,'furlsSee')"/>
		          	<input type="button" style="width:70px;" class="file-btn" value="上传" onclick="return uploadimg(this,'furlsField','furls');" />
	          	</span>
				</td>
				<td style='text-align:left;'>
					<span class="file-box">
					<#if item.fbigurls?? && item.fbigurls!='' > 
			            <a href="${item.fbigurl!""}" target="_blank" id="fbigurlsImgA">文件</a>
		          	<#else>
		            	<a target="_blank" id="fbigurlsImgA" href="${item.fbigurl!""}">文件</a>
		          	</#if>
		          	<input style='width:100px;' type='hidden' name='playurls' id="playurl" value='${item.playurl!""}'/>
		          	<input type="hidden" name="fbigurls" id="fbigurls" value="${item.fbigurl!""}"/>
		          	<input type="button" style="width:70px;" class="file-btn" id="fbigurlsSee" value="浏览..." />
	         	 	<input type="file" style="height:24px;" class="file-file" id="fbigurlsField" size="28" onchange="return checkFile(this,'fbigurlsSee')"/>
		          	<input type="button" style="width:70px;" class="file-btn" value="上传" onclick="return uploadFile(this,'fbigurlsField','fbigurls');" />
	          	</span>
				</td>
				<td style='text-align:center;'>
					<button class='btn btn-danger' onclick="delCurrTr(this,${item.id})">
						<i class='icon-remove-sign icon-white'></i>删除
					</button>
				</td>
			</tr>
			</#list>
		</#if>
	</table>
	<table class="table table-bordered">
		<tr>
			<td style="text-align: center;">
				<#if !e.id??>
                <input type="submit" method="addDataCenter" class="btn btn-success" onclick="return validForm()" value="新&nbsp;&nbsp;&nbsp;增">
                </input>
                <#else>
                <input type="submit" method="updateDataCenter" class="btn btn-success" onclick="return validForm()" value="保&nbsp;&nbsp;&nbsp;存">
                </input>
                </#if>
			</td>
		</tr>
	</table>
</table>
</form>

<script type="text/javascript" src="${basepath}/resource/js/ajaxfileupload.js"></script>
<script type="text/javascript">
$(function() {
	if(!jQuery.isEmptyObject($("#id").val())){
		$("#tb_detail").show();
	}
	$("#name").focus();
});
var fIndex = 0;
function addDetail(){
	fIndex++;
    //添加一行
	$("#tb_detail").show();
	var _s = "<tr><input type='hidden' name='ids' value='-1'/>";
	_s += "<td style='text-align:left;'><input style='width:300px;' type='text' name='fnames'/></td>";
	_s += "<td style='text-align:left;'><span class='file-box'><a target='_blank' id='furls"+fIndex+"ImgA' style='display: none'><img id='furls"+fIndex+"Img' style='width: 80px;height: 80px;' title='图片' alt='图片' ></a><input type='hidden' name='furls' id='furls"+fIndex+"' value=''/><input type='button' style='width:70px;' class='file-btn' id='furls"+fIndex+"See' value='浏览...' /><input type='file' style='height:24px;' class='file-file' id='furls"+fIndex+"Field' size='28' onchange=\"return checkimg(this,'furls"+fIndex+"See')\"/><input type='button' style='width:70px;' class='file-btn' value='上传' onclick=\"return uploadimg(this,'furls"+fIndex+"Field','furls"+fIndex+"');\" /></span></td>";
	_s += "<td style='text-align:left;'><span class='file-box'><a target='_blank' id='fbigurls"+fIndex+"ImgA' style='display: none'>文件</a><input style='width:100px;' type='hidden' name='playurls' id='playurl' value=''/><input type='hidden' name='fbigurls' id='fbigurls"+fIndex+"' value=''/><input type='button' style='width:70px;' class='file-btn' id='fbigurls"+fIndex+"See' value='浏览...' /><input type='file' style='height:24px;' class='file-file' id='fbigurls"+fIndex+"Field' size='28' onchange=\"return checkFile(this,'fbigurls"+fIndex+"See')\"/><input type='button' style='width:70px;' class='file-btn' value='上传' onclick=\"return uploadFile(this,'fbigurls"+fIndex+"Field','fbigurls"+fIndex+"');\"/></span></td>";
	_s += "<td style='text-align:center;'><button class='btn btn-danger' onclick=\"delCurrTr(this)\"><i class='icon-remove-sign icon-white'></i>删除</button></td></tr>";
	$("#tb_detail").append(_s);
}
   //删除一行
function delCurrTr(_tr,id){
	$(_tr).parent('td').parent('tr').remove();
	if($("#tb_detail tr").length==1){
		$("#tb_detail").hide();
	}
	if(id){
		$.post("${basepath}/manage/dataCenter/delDetailById",{id:id},function(data){
			if($.parseJSON(data)==1){
				alert("删除成功！");
			}else{
				alert("删除失败！");
			}
		});
	}
}
//判定
function validForm(){
	var _valid = true;
	var _firstTr = true;
	var keys = [],valus=[];
	//标题
	var title=$("#title").val();
	//内容
	var content=$("#content").val();
	//图片
	var picture=$("#picture").val();
	//栏目
	var dicId=$("#dicId").val();
	//状态
	var status=$("#status").val();
	if(title==null || title==""){
			alert("资料名称不能为空！")
			$(this).find("[name='title']").focus();
			_valid = false;
		}
		else if(content==null || content==""){
			alert("资料内容不能为空！")
			$(this).find("[name='content']").focus();
			_valid = false;
		}
		else if(picture==null || picture==""){
			alert("资料图片不能为空！")
			$(this).find("[name='picture']").focus();
			_valid = false;
		}
		else if(dicId==null || dicId==""||dicId=="已选择"){
			alert("栏目不能为空！")
			$(this).find("[name='dicId']").focus();
			_valid = false;
		}
		else if(status==null || status==""){
			alert("状态不能为空！")
			$(this).find("[name='status']").focus();
			_valid = false;
		}else if($("#tb_detail tr").length<2){
		   alert("资源详情不能为空！");
		   _valid = false;
		}


		
	$("#tb_detail tr").each(function(){
		// 不处理表头
		if(_firstTr){
			_firstTr = false;
			return true;
		}
		var fname = $(this).find("[name='fnames']").val();
		var furl = $(this).find("[name='furls']").val();
		var fbigurl = $(this).find("[name='fbigurls']").val();
		if(fname==null || fname==""){
			alert("资料详情名称不能为空！")
			$(this).find("[name='fname']").focus();
			_valid = false;
		}
		else if(furl==null || furl==""){
			alert("资料详情图片不能为空！")
			$(this).find("[name='furl']").focus();
			_valid = false;
		}
		else if(fbigurl==null || fbigurl==""){
			alert("资料详情文件不能为空！")
			$(this).find("[name='fbigurl']").focus();
			_valid = false;
		}
			keys.push(fname); valus.push(furl);
	});
	
	return _valid;
}

//核对图片
function checkimg(file,seeId){
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
//核对文件
function checkFile(file,seeId){
	var fName = $(file).val();
	var dicId=$("#dicId").val();
	if(fName==''){
		alert("请选择要上传的资料");
		return false;
	}
   
    if(dicId==''){
         alert("请先选择栏目！");
         return false;
    }
    
    if(dicId=="video"){
	    var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
		if(fExt!='.flv' && fExt!='.mp3' && fExt!='.mp4'){
		alert("请选择正确的视频格式:.flv,.mp3,.mp4");
		return false;
    }
    
    
    }
	$("#"+seeId).val("已选择文件");
	return true;
}
//上传图片
function uploadimg(btn,fileId,baseId) {
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

	$(btn).val("uploading...");
	$.ajaxFileUpload( {
		url : basepath+'/UploadServlet.do?id='+$("#id").val()+'&folder=dataCenter&saveModel=server&nameModel=&name='+baseId,//用于文件上传的服务器端请求地址
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
//上传视频文件
function uploadFile(btn,fileId,baseId) {
    var dicId=$("#dicId").val();
	var fName = $("#"+fileId).val();
	if(fName==''){
		alert("请选择要上传的资料");
		return false;
	}
	if(dicId=="video"){
		var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
		if(fExt!='.flv' && fExt!='.mp3' && fExt!='.mp4'){
		alert("请选择正确的视频格式:.flv,.mp3,.mp4");
		return false;
	    }
	  } 
    
	$(btn).val("uploading...");
	$.ajaxFileUpload( {
		url : basepath+'/UploadServlet.do?id='+$("#id").val()+'&folder=datafile&saveModel=server&nameModel=&name='+baseId,//用于文件上传的服务器端请求地址
		secureuri : false,//一般设置为false
		fileElementId : fileId,//文件上传控件的id属性
		dataType : 'json',//返回值类型 一般设置为json
		success : function(data, status){ //服务器成功响应处理函数
			if(data.filePath){
				$('#'+baseId).val(data.filePath);
				$('#playurl').val(data.filePath);
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
</@page.pageBase>
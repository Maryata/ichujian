<#import "/static/common_front.ftl" as html/>
<@html.htmlBase title="注册" footer="footer-enrol" >
<!--标签切换js-->
<script>
function setTab(name,cursel,n){
 for(i=1;i<=n;i++){
  var menu=document.getElementById(name+i);
  var con=document.getElementById("con_"+name+"_"+i);
  menu.className=i==cursel?"hover":"";
  con.style.display=i==cursel?"block":"none";
 }
}
</script>
<!--标签切换js END-->
<div class="enrolbox"> <a class="enrol-logo" href="${basepath}/index.html">指电直销平台欢迎您</a>
  <div class=" enrol-form ">
    <div class="enrol-title"><a id="enrol1" onclick="setTab('enrol',1,2)" class="hover">用户注册</a><a id="enrol2" onclick="setTab('enrol',2,2)">销售协助注册</a><span><a href="login.html">直接登录</a></span></div>
    <!--注册类别1-->
    <div id="con_enrol_1" class="enrol-info f-l hover">
      <form class="registerform" method="post" action="${basepath}/account/doRegister.html">
        <input type="hidden" name="registerType" id="registerType" value="0"/>
       <li class="height60">
          <b>账号类型<span style="color:red;">*</span></b>
          <input autocomplete="off" type="radio"  id="R1_person" name="accType" class="inputbox regType1accType" value="0" checked="true">
           <label for="R1_person">个人账号</label>
           <input autocomplete="off" type="radio"  id="R1_company" name="accType" class="inputbox regType1accType" value="-1">
           <label for="R1_company">企业账号</label>
          </li>
        <li class="height60">
          <b>手机号<span style="color:red;">*</span></b>
          <input autocomplete="off" type="text" value="<#if e??>${e.phone!''}</#if>" id="R1_phone" name="phone" class="inputbox " datatype="m" maxlength="15" ajaxurl="unique2.html" errormsg="手机号错误" nullmsg="请输入手机号" placeholder="手机号将作为您的帐号">
          <span class="Validform_checktip" id="R1_phone_msg"></span>
          </li>
         <li class="height60">
         <b>验证码<span style="color:red;">*</span></b>
         <input autocomplete="off" class="inputbox wi-130" value="" type="text" id="R1_vcode" name="vcode" datatype="*4-4" maxlength="6" ajaxurl="${basepath}/account/unique2.html" nullmsg="请输入验证码" errormsg="请输入4位验证码" placeholder="请输入验证码">
          <img class="cursor-p R1_vcode_img" src="${basepath}/ValidateImage.do" onclick="javaScript:reloadImg2(this,'R1_vcode');" title="点击刷新" style="vertical-align:bottom;margin-left:3px;">
          <span class="Validform_checktip"  id="R1_vcode_msg"></span>
        </li>
        <li class="height60">
            <b>短信码<span style="color:red;">*</span></b>
            <input autocomplete="off" class="inputbox" type="text" value="" id="R1_msgVcode" name="msgVcode" datatype="*6-6" maxlength="6" ajaxurl="unique2.html?type=p&phone=" nullmsg="请输入短信验证码" errormsg="验证码格式为6位数字" placeholder="请输入短信验证码">
            <a class="test-btn m-l10 R1_getVcode">获取短信验证码</a>
            <span id="R1_msgVcode_msg" class="Validform_checktip"></span>
        </li>
        <li class="height60 none regType1SaleCode">
              <b>销售代码&nbsp;&nbsp;</b>
              <input autocomplete="off" class="inputbox" type="text" value="" id="R1_saleCode" name="saleCode" datatype1="s3-18" maxlength="15" ajaxurl="${basepath}/account/unique2.html?type=saleCode" nullmsg="请输入销售3-10位代码" errormsg="请输入3-10位销售人员工号" placeholder="请输入名片的销售代码">          
              <span id="R1_saleCode_msg" class="Validform_checktip"></span>        
        </li>
        <li>
          <div></div><label for="remember" ><input id="remember" type="checkbox" checked="checked" name="remember" >我已阅读并同意<a target="_blank" href="${basepath}/help/protocol.html">注册协议</a> </label>
        </li>
        <li>
          <input id="submitBtn" class="enrol-btn" style="margin:20px 0 0 18px;" type="submit" value="确认注册"></input>  <span id="msg_holder" style="display:;">${errorMsg!''}</span>
        </li>
      </form>
    </div>
    <!--注册类别2-->
    <div id="con_enrol_2" class="enrol-info f-l" style="display:none;">
      
       <form class="registerform" method="post" action="${basepath}/account/doRegister.html">
        <input type="hidden" name="registerType" id="registerType" value="1"/>
        <li class="height60">
          <b>账号类型<span style="color:red;">*</span></b>
          <input autocomplete="off" type="radio"  id="R2_person" name="accType" class="inputbox" value="0" datatype="radio" errormsg="请选择账号类型" nullmsg="请选择账号类型" checked="true">
           <label for="R2_person">个人账号</label>
           <input autocomplete="off" type="radio"  id="R2_company" name="accType" class="inputbox" value="-1">
           <label for="R2_company">企业账号</label>
          </li>
        <li class="height60">
          <b>手机号<span style="color:red;">*</span></b>
         <input autocomplete="off" type="text" value="<#if e??>${e.phone!''}</#if>" id="R2_phone" name="phone" class="inputbox " datatype="m" maxlength="15" ajaxurl="unique2.html" errormsg="手机号格式错误" nullmsg="请输入手机号" placeholder="手机号将作为您的帐号">
          <span class="Validform_checktip" id="R2_phone_msg"></span>
          </li>
         <li class="height60">
         <b>验证码<span style="color:red;">*</span></b>
         <input autocomplete="off" class="inputbox wi-130" value="" type="text" id="R2_vcode" name="vcode" datatype="*4-4" maxlength="6" ajaxurl="${basepath}/account/unique2.html" nullmsg="请输入验证码" errormsg="请输入4位验证码" placeholder="请输入验证码">
          <img class="cursor-p R2_vcode_img" src="${basepath}/ValidateImage.do" onclick="javaScript:reloadImg2(this,'R2_vcode');" title="点击刷新" style="vertical-align:bottom;margin-left:3px;">
          <span class="Validform_checktip"  id="R2_vcode_msg"></span>
        </li>
        <li class="height60">
            <b>短信码<span style="color:red;">*</span></b>
            <input autocomplete="off" class="inputbox" type="text" value="" id="R2_msgVcode" name="vcode" datatype="*6-6" maxlength="6" ajaxurl="unique2.html?type=p&phone=" nullmsg="请输入短信验证码" errormsg="验证码格式为6位数字" placeholder="请输入短信验证码">
            <a class="test-btn m-l10 R2_getVcode">获取短信验证码</a>
            <span id="R2_msgVcode_msg" class="Validform_checktip"></span>
        </li>
        <li class="height60">
              <b>销售代码<span style="color:red;">*</span></b>
              <input autocomplete="off" class="inputbox" type="text" value="" id="R2_saleCode" name="saleCode" datatype="s3-18" maxlength="15" ajaxurl="${basepath}/account/unique2.html?type=saleCode" nullmsg="销售人员的工号不能为空" errormsg="请输入3-10位销售人员工号" placeholder="请输入名片的销售代码">          
              <span id="saleCode_msg" class="Validform_checktip"></span>
        </li>
        <li class="height60">
            <b>微信号<span style="color:red;">*</span></b>
            <input  class="inputbox" type="text" autocomplete="off"  name="weixin" id="weixin" datatype="*"  nullmsg="请输入微信号" errormsg="微信号错误" placeholder="请输入微信号" >
            <span class="Validform_checktip"></span>          
        </li>
        <li>
          <div class="upload-box1">
            <div class="img-box">
		         <a target="_blank" id="mingpianImageImgA" style="display: none"><img id="mingpianImageImg"  title="名片图" alt="名片图" ></a>
		         <#--<a target="_blank" id="busLicenseScanImgA" style="display: none"><img id="busLicenseScanImg"  title="公司营业执照扫描件" alt="公司营业执照扫描件"/></a>-->
            </div>
            
            <div class="btn-box">
              <input type="hidden" name="mingpianImage" id="mingpianImage" nullmsg="请上传名片图"/>
              <input type="button"  class="file-btn" id="mingpianImageSee" value="浏览..." />
              <input type="file" class="file-file" id="mingpianImageField" size="28" onchange="return checkFile(this,'mingpianImageSee')" />
              <input type="button"  class="file-btn" value="名片上传" onclick="return uploadFile(this,'mingpianImageField','mingpianImage');" />
            </div>
          </div>
          <div class="upload-box2">
           <div class="img-box">
		        <#-- <a target="_blank" id="busLicenseScanImgA" style="display: none"><img id="busLicenseScanImg"  title="公司营业执照扫描件" alt="公司营业执照扫描件"/></a>-->
                <a target="_blank" id="mendianImageImgA" style="display: none"><img id="mendianImageImg"  title="门店图" alt="门店图"></a>
            </div>
            <div class="btn-box">
              <input type="hidden" name="mendianImage" id="mendianImage" nullmsg="请上传门店图"/>
              <input type="button"  class="file-btn" id="mendianImageSee" value="浏览..." />
              <input type="file" class="file-file" id="mendianImageField" size="28" onchange="return checkFile(this,'mendianImageSee')" />
              <input type="button"  class="file-btn" value="门店上传" onclick="return uploadFile(this,'mendianImageField','mendianImage');" />
            </div>
          </div>
        </li>
        <li>
          <div></div><label for="remember" ><input id="remembers" type="checkbox" checked="checked" name="remembers" >我已阅读并同意<a target="_blank" href="${basepath}/help/protocol.html">注册协议</a> </label>
        </li>
        <li>
          <input id="submitBtns" class="enrol-btn" style="margin:20px 0 0 18px;" type="submit" value="确认注册"></input>  <span id="msg_holder" style="display:;">${errorMsg!''}</span>
        </li>
      </form>
    </div>
  </div>
  <img class="cloud-img" src="${basepath}/static/images/cloud_img.png" > </div>
<script type="text/javascript" src="${basepath}/static/js/Validform_v5.3.2_min.ext.js"></script>
<script type="text/javascript">
//验证图片是否为空
$(function(){
	$(".registerform").Validform({
		tiptype:function(msg,o,cssctl){
			//msg：提示信息;
			//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
			//cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
			if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
				var objtip=o.obj.siblings(".Validform_checktip");
				cssctl(objtip,o.type);
				objtip.text(msg);
			}else{
				var objtip=o.obj.find("#msg_holder");
				cssctl(objtip,o.type);
				objtip.text(msg);
			}
		},
		ajaxPost:true,
		callback:function(data){
			if(data.status=="y"){
				login = true;
				 var _data="";
				 _data+="<div style='text-align:center;'>恭喜，注册成功！</div><br><div style='text-align:center;'>初始密码为您的手机后六位。为了您的账户安全，请及时在个人中心中修改密码!</div>";
				 _data+="<br/><button class='sure m-300'  style='width:150px' onclick='tourl(\""+basepath+"/account/account\")'>立即补全资料</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				 _data+="<button class='sure m-300'  style='width:150px' onclick='tourl(\""+basepath+"/product/productList?material=ghAndroid\")'>立即购买</button>";
				layer.open({
				    icon: 6,
				    title:'信息',
				    type: 1,
				    skin: 'layui-layer-rim', //加上边框
				    area: ['340px', '150px'], //宽高
				    content: _data
				});	
				return;
			}
		}
	});
	
	var count = parseInt($.cookie("count"))||0;
	var maxSubmitCount = 50;
	var vFlag = false;
	//客户注册获取验证码
	$(".R1_getVcode").click(function(){
	    
		var _this = $(this);
		if(vFlag) return false;
		if(count > maxSubmitCount || $("#R1_phone").val()==''|| $("#R1_phone_msg").hasClass("Validform_wrong") || $("#R1_vcode").val()=="" || $("#R1_vcode_msg").hasClass("Validform_wrong") ){
			return false;
		}
		$.cookie("count",++count,{expires:1});
		$("#R1_msgVcode_msg").removeClass().addClass("Validform_checktip Validform_right").html("<img src='${basepath}/static/images/testico.gif'>正在获取验证码");
		_this.html("验证码发送中...");
		vFlag = true;
		$.ajax({
			url:basepath+"/account/getVaSysCode",
		    data:{client:"code",phone:$.base64.encode($("#R1_phone").val()),vcode : $("#R1_vcode").val(),sign:$.base64.encode('sys:'+Math.random())},
		    type:"POST",
		    dataType:'json',
		    success:function(result){
		    	if(result && result.status=='y'){
		    		$.cookie("smsVcodeTime", 60);
					_this.html("验证码已发送");
					$("#R1_msgVcode_msg").removeClass().addClass("Validform_checktip Validform_right").html("验证码已发送,30分钟内有效");
					_this.removeClass("test-btn").addClass("test2-btn").attr({"disabled":"disabled"});
					var ctime = 60;
					var timerId = setInterval(function(){
						$.cookie("smsVcodeTime", ctime);
						if(ctime==0){clearInterval(timerId); vFlag=false; _this.removeClass("test2-btn").addClass("test-btn").html("重新获取验证码").removeAttr("disabled"); return;}
					 	_this.html("重新获取("+(ctime--)+"s)");//(ctime--)+秒后，
					},1000);
				}else{
					_this.html("获取短信验证码");
					$("#R1_msgVcode_msg").removeClass().addClass("Validform_checktip Validform_wrong").html((result && result.status) ? result.info : "验证码发送失败");
					vFlag = false;
				}
			}
			
		});
	});
	//销售注册获取验证码
	$(".R2_getVcode").click(function(){
		var _this = $(this);
		if(vFlag) return false;
		if(count > maxSubmitCount || $("#R2_phone").val()==''|| $("#R2_phone_msg").hasClass("Validform_wrong") || $("#R2_vcode").val()=="" || $("#R2_vcode_msg").hasClass("Validform_wrong") ){
			return false;
		}
		$.cookie("count",++count,{expires:1});
		$("#R2_msgVcode_msg").removeClass().addClass("Validform_checktip Validform_right").html("<img src='${basepath}/static/images/testico.gif'>正在获取验证码");
		_this.html("验证码发送中...");
		vFlag = true;
		$.ajax({
			url:basepath+"/account/getVaSysCode",
		    data:{client:"code",phone:$.base64.encode($("#R2_phone").val()),vcode : $("#R2_vcode").val(),sign:$.base64.encode('sys:'+Math.random())},
		    type:"POST",
		    dataType:'json',
		    success:function(result){
		    	if(result && result.status=='y'){
		    		$.cookie("smsVcodeTime", 60);
					_this.html("验证码已发送");
					$("#R2_msgVcode_msg").removeClass().addClass("Validform_checktip Validform_right").html("验证码已发送,30分钟内有效");
					_this.removeClass("test-btn").addClass("test2-btn").attr({"disabled":"disabled"});
					var ctime = 60;
					var timerId = setInterval(function(){
						$.cookie("smsVcodeTime", ctime);
						if(ctime==0){clearInterval(timerId); vFlag=false; _this.removeClass("test2-btn").addClass("test-btn").html("重新获取验证码").removeAttr("disabled"); return;}
					 	_this.html("重新获取("+(ctime--)+"s)");//(ctime--)+秒后，
					},1000);
				}else{
					_this.html("获取短信验证码");
					$("#R2_msgVcode_msg").removeClass().addClass("Validform_checktip Validform_wrong").html((result && result.status) ? result.info : "验证码发送失败");
					vFlag = false;
				}
			}
			
		});
	});
	
	
	$("#remember").click(function(){
		if($(this).is(":checked")){
			$("#submitBtn").css('background','').removeAttr("disabled");
		}else{
			$("#submitBtn").css('background','#aaa').attr({"disabled":"disabled"});
		}
	});
	$("#remembers").click(function(){
		if($(this).is(":checked")){
			$("#submitBtns").css('background','').removeAttr("disabled");
		}else{
			$("#submitBtns").css('background','#aaa').attr({"disabled":"disabled"});
		}
	});
	
	$(".regType1accType").click(function(){
		var _v = $(this).val();
		if(_v=="0"){
			$(".regType1SaleCode").hide();
			$("#R1_saleCode").removeAttr("dataType").val("");
		}else{
			$(".regType1SaleCode").show();
			$("#R1_saleCode").attr("dataType",$("#R1_saleCode").attr("dataType1"));
		}
	});
	
	
	$("#R1_phone").change(function(){
		$("#R1_vcode").val("");
		$(".R1_vcode_img").click();
		$("#R1_msgVcode").val("").attr("ajaxurl","unique2.html?type=p&phone="+$(this).val());
	});
	$("#R2_phone").change(function(){
		$("#R2_vcode").val("");
		$(".R2_vcode_img").click();
		$("#R2_msgVcode").val("").attr("ajaxurl","unique2.html?type=p&phone="+$(this).val());
	});
	
	
	var ctime =  parseInt($.cookie("smsVcodeTime"));
	if(ctime && ctime!="0"){
		var timerId = setInterval(function(){
			$.cookie("smsVcodeTime", ctime);
			var _this = $(".R1_getVcode");
			if(ctime==0){clearInterval(timerId); vFlag=false; _this.removeClass("test2-btn").addClass("test-btn").html("重新获取验证码").removeAttr("disabled"); return;}
		 	_this.html("重新获取("+(ctime--)+"s)");//(ctime--)+秒后，
		},1000);			
	}
	
});
function reloadImg2(img,tagId) {
	$(img).attr('src',"${basepath}/ValidateImage.do?radom="+ Math.random()).focus();
	$("#"+tagId).val("").blur();
}
</script>
<script type="text/javascript" src="${basepath}/resource/js/ajaxfileupload.js"></script>
<script type="text/javascript">
function checkFile(file,seeId){
	var fName = $(file).val();
	if(fName==''){
		layer.alert("请选择要上传的资料");
		return false;
	}
	var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if(fExt!='.jpg' && fExt!='.gif' && fExt!='.jpeg' && fExt!='.bpm'){
		layer.alert("请选择正确的图片格式:.jpg,.gif,.jpeg,.bpm");
		return false;
	}
	$("#"+seeId).val("已选择文件");
	return true;
}
function uploadFile(btn,fileId,baseId) {
	var fName = $("#"+fileId).val();
	var phone=$("#R2_phone").val();
	var reg_vcodes=$("#R2_msgVcode").val();
	if(fName==''){
		layer.alert("请选择要上传的资料");
		return false;
	}
	if(phone==""){
	    layer.alert("请填写手机号");
		return false;
	}
	if(reg_vcodes==""){
	    layer.alert("请填写验证码");
		return false;
	
	}
	var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if(fExt!='.jpg' && fExt!='.gif' && fExt!='.jpeg' && fExt!='.bpm'){
		layer.alert("请选择正确的图片格式:.jpg,.gif,.jpeg,.bpm");
		return false;
	}	

	$(btn).html("uploading...");
	$.ajaxFileUpload( {
		url : basepath+'/UploadServlet.do?id='+$("#phones").val()+'&folder=account&saveModel=server&nameModel=new&name='+baseId,//用于文件上传的服务器端请求地址
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
				layer.alert('上传失败:'+data.msg);
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
<script>
//图片验证
function tijiao(){
   var mingpianImage =document.getElementById('mingpianImage').value;
   var mendianImage =document.getElementById('mendianImage').value; 
   
   if(mingpianImage==""){
     layer.alert("请上传名片图");
     return false;
   }
   if(mendianImage==""){
     layer.alert("请上传门店图");
     return false;
   }
   return true;
}
 
document.onkeydown=function(event){ 
    event = window.event || event;
    //var srcElement = event.srcElement || event.target;
    if (event.keyCode == 13 ) {//&& srcElement.type != 'button' && srcElement.type != 'submit' && srcElement.type != 'reset' && srcElement.type != 'textarea' &&srcElement.type != ''
        var o = document.getElementById('submitBtn');
        var saleCode=document.getElementById('saleCode').value;
        var reg_phone = document.getElementById('reg_phone').value;
         var phones = document.getElementById('phones').value;
        var reg_vcode = document.getElementById('reg_vcode').value;
         var reg_vcodes = document.getElementById('reg_vcodes').value;
        var weixin=document.getElementById('weixin').value;
        var mingpianImage =document.getElementById('mingpianImage').value;
        var mendianImage =document.getElementById('mendianImage').value;
        if(reg_phone!='' && reg_vcode!=''&& saleCode!='' && weixin!='' && mingpianImage!='' && mendianImage!=''){
        	setTimeout(function(){
	        	if (document.all) {
	                o.click();
	            }
	            else {
	                var e = document.createEvent('MouseEvent');
	                e.initEvent('click', false, false);
	                o.dispatchEvent(e);
	            }
	        },50);
        }
    }
} 
</script> 
<script type="text/javascript" src="${basepath}/static/js/lib/jquery.base64.js?v=${jversion}"></script>
<script type="text/javascript" src="${basepath}/static/js/lib/jquery.cookie.js?v=${jversion}"></script>


</@html.htmlBase>
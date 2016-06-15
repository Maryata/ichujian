<#import "/static/common_front.ftl" as html/>
<@html.htmlBase title="登录" footer="footer-enrol" jqueryValidator=false>

<div class="enrolbox"> <a class="enrol-logo" href="${basepath}/index.html">${systemSetting().title}欢迎您</a>
  <div class=" enrol-form ">
    <div class="enrol-title">登录<span><a href="${basepath}/account/register.html">马上注册</a></span></div>
    <div class="enrol-info f-l">
      <form class="registerform" method="post" action="${basepath}/account/doLoginAjax.html">
        <li class="height60"><b>帐　号</b><input autocomplete="off" type="text" id="account" name="loginAccount" value="<#if e??>${e.account!''}</#if>" class="inputbox wi-346" datatype="*5-16"  maxlength="15" errormsg="手机号格式错误" nullmsg="请输入手机号" placeholder="请输入您注册时所用的手机号码">
          <span class="Validform_checktip"></span>
        </li>
        <li class="height60"><b>密　码</b><input autocomplete="off" type="password" value="" id="password" name="loginPass" class="inputbox wi-346" datatype="*6-16"  maxlength="20" nullmsg="请输入密码" errormsg="请输入6-16位密码" placeholder="请输入密码">
          <span class="Validform_checktip"></span>
        </li>
        <li class="height60"><b>验证码</b><input autocomplete="off" class="inputbox wi-130" value="" type="text" id="vcode" name="vcode" datatype="*4-4" maxlength="6" ajaxurl="${basepath}/account/unique2.html" nullmsg="请输入验证码" errormsg="请输入4位验证码" placeholder="请输入验证码">
          <img class="cursor-p" src="${basepath}/ValidateImage.do" onclick="javaScript:reloadImg2(this);" title="点击刷新" style="vertical-align:bottom;margin-left:3px;"><a class="btn-pwd" href="${basepath}/find/findpwd" style="margin-left:60px;">忘记密码?</a><#--${basepath}/find/findpwd-->
          <span class="Validform_checktip"></span>
        </li>
        <li>
        <span id="msg_holder" style="margin-left: 20px;">${errorMsg!''}</span>
        </li>
        <li style="margin-top:-20px;">
          <input id="submitBtn" class="enrol-btn" style="margin:20px 0 0 18px;" type="submit" value="确认登录">
        </li>
      </form>
    </div>
  </div>
  <img class="cloud-img" src="${basepath}/static/images/cloud_img.png" > 
</div>

<script type="text/javascript" src="${basepath}/static/js/Validform_v5.3.2_min.ext.js"></script>

<script type="text/javascript">
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
				if(G_CALLBACK_LOGIN_URL && G_CALLBACK_LOGIN_URL!=''){
					window.location = G_CALLBACK_LOGIN_URL;
					G_CALLBACK_LOGIN_URL = null;
				}else{
					//layer.msg('已经登录,可以继续操作');
					window.location="${basepath}/index.html";
				}
				return;
			}
		}
	});
	
    $("#account").focus();
});

function reloadImg2(img) {
	$(img).attr('src',"${basepath}/ValidateImage.do?radom="+ Math.random()).focus();
}

</script>

<script> 
document.onkeydown=function(event){ 
    event = window.event || event;
    //var srcElement = event.srcElement || event.target;
    if (event.keyCode == 13 ) {//&& srcElement.type != 'button' && srcElement.type != 'submit' && srcElement.type != 'reset' && srcElement.type != 'textarea' &&srcElement.type != ''
        var o = document.getElementById('submitBtn');
        
        var account = document.getElementById('account').value;
        var password = document.getElementById('password').value;
        var vcode = document.getElementById('vcode').value;
        if(account!='' && password!='' && vcode!=''){
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

</@html.htmlBase>
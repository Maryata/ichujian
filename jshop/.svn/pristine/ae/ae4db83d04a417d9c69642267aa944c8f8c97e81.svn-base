<#import "/static/common_front.ftl" as html/>
<@html.htmlBase title="欢迎登录${systemSetting().title}后台" footer="footer-enrol" jqueryValidator=false>

<div class="enrolbox"> <a class="enrol-logo" title="访问站点" href="${basepath}/index.html" target="_blank">${systemSetting().title}后台</a>
  <div class=" enrol-form ">
    <div class="enrol-info f-l">
      <form class="registerform" method="post" action="${basepath}/manage/user/login">
        <li class="height60"><b>帐　号</b><input type="text" autocomplete="off" id="username" name="username" value="<#if e??>${e.username!''}</#if>" class="inputbox wi-346" datatype="*2-16" maxlength="15" errormsg="账号有误" nullmsg="请输入账号" autofocus>
          <span class="Validform_checktip">请输入账号</span>
        </li>
        <li class="height60"><b>密　码</b><input type="password" autocomplete="off" value="" id="password" name="password" class="inputbox wi-346" datatype="*6-16"  maxlength="20" nullmsg="请输入密码" errormsg="密码错误">
          <span class="Validform_checktip">请输入密码</span>
        </li>
        <li class="height60"><b>验证码</b><input autocomplete="off" class="inputbox wi-130" value="" type="text" id="vcode" name="vcode" datatype="*4-4" maxlength="6" ajaxurl="${basepath}/account/unique2.html" nullmsg="请输入验证码" errormsg="验证码错误" >
          <img class="cursor-p" src="${basepath}/ValidateImage.do" onclick="javaScript:reloadImg2(this);" title="点击刷新" style="vertical-align:bottom;margin-left:3px;">
          <span class="Validform_checktip">请输入验证码</span>
        </li>
        <li>
        <span id="msg_holder" style="margin-left: 20px;color:red">${errorMsg!''}</span>
        </li>
        <li style="margin-top:-20px;">
          <input id="submitBtn" class="enrol-btn" style="margin:20px 0 0 18px;" type="submit" value="确认登录">
        </li>
        <input type="hidden" id="returl" name="returl" />
      </form>
    </div>
  </div>
  <img class="cloud-img" src="${basepath}/static/images/cloud_img.png" > 
</div>

<script type="text/javascript" src="${basepath}/static/js/Validform_v5.3.2_min.ext.js"></script>

<script type="text/javascript">
$(function(){
	$(".registerform").Validform({
		//btnSubmit:'#submitBtn',
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
		ajaxPost:false
	});
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
        
        var username = document.getElementById('username').value;
        var password = document.getElementById('password').value;
        var vcode = document.getElementById('vcode').value;
        if(username!='' && password!='' && vcode!=''){
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
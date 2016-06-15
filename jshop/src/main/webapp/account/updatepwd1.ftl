<#import "/static/common_front.ftl" as html/>
<#import "/indexMenu.ftl" as menu/>
<@html.htmlBase title="修改密码" badyClass="login-bg">
<@menu.menu selectMenu=""/>
<!--注册内容-->
<div class="content">
  <div class="protocol-box">
    <h1><!--订单状态-->
      <div class="flowstep">
        <ol class="flowstep-5">
          <li class="step-first" style="width:33%;">
            <div class="step-cur"><!--class为step-done表示完成</div>-->
              <div class="step-name">身份验证</div>
              <div class="step-no">1</div>
              <div class="step-time"> 
                <!--<div class="step-time-wraper">2015-03-17 11:04:28</div>--> 
              </div>
            </div>
          </li>
          <li style="width:33%;">
            <div class="step-name">重置登录密码</div>
            <div class="step-no">2</div>
            <div class="step-time"> 
              <!--<div class="step-time-wraper">2015-03-17 13:22:34</div>--> 
            </div>
          </li>
          <li class="step-last"  style="width:33%;">
            <div class="step-name">完成</div>
            <div class="step-no">3</div>
            <div class="step-time"> 
              <!--<div class="step-time-wraper">2015-03-27 13:22:46</div>--> 
              
            </div>
          </li>
        </ol>
      </div>
    </h1>
    <div class="ubox-info m-l240 m-t30">
     <form method="post" class="registerform"  action="${basepath}/find/doCheckAjax">
        <li><span class="inputtitle">手机号</span>
          <input autocomplete="off" type="text"  id="reg_phone" style="width:200px;" name="phone" class="inputbox " datatype="m" maxlength="15" ajaxurl="${basepath}/account/unique2.html?type=findpwd"  errormsg="手机号错误" nullmsg="请输入手机号" placeholder="请输入注册时所填的手机号">
          <span class="Validform_checktip" id="R_phone_msg"></span>
        </li>
        <li style="display:block;"><span class="inputtitle">验证码</span>
          <input autocomplete="off" class="inputbox" type="text"  style="width:200px;" id="reg_vcode" name="vcode" datatype="*6-6" maxlength="6" ajaxurl="${basepath}/account/unique2.html?type=findpwd&phone=" nullmsg="请输入短信验证码" errormsg="验证码格式为6位数字" placeholder="请输入短信验证码">
          <a class="test-btn m-l10 getVcode">获取短信验证码</a>
          <span id="vcode_msg" class="Validform_checktip"></span>
          </li>
        <li>
           <input id="submitBtn" class="enrol-btn" style="margin:20px 0 0 18px;" type="submit" value="提交"></input> <span id="msg_holder" style="display:;">${errorMsg!''}</span>
        </li>
      </form>
    </div>
  </div>
</div>
<script type="text/javascript" src="${basepath}/static/js/Validform_v5.3.2_min.ext.js"></script>
<script>
//显示提示信息
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
		}
	});
	
	var vFlag = false;
	//客户注册获取验证码
	$(".getVcode").click(function(){
		var _this = $(this);
		if(vFlag) return false;
		if($("input[name=phone]").val()=='' || $("#R_phone_msg").hasClass("Validform_wrong")){
			return false;
		}
		
		$("#vcode_msg").removeClass().addClass("Validform_checktip Validform_right").html("<img src='${basepath}/static/images/testico.gif'>正在获取验证码");
		_this.html("验证码发送中...");
		vFlag = true;
		$.ajax({
			url:basepath+"/account/getVaSysCode",
		    data:{client:"findpwd",phone:$("input[name=phone]").val()},
		    type:"POST",
		    dataType:'json',
		    success:function(result){
		    	if(result && result.status=='y'){
				_this.html("验证码已发送");
				$("#vcode_msg").removeClass().addClass("Validform_checktip Validform_right").html("验证码已发送,30分钟内有效");
				_this.removeClass("test-btn").addClass("test2-btn").attr({"disabled":"disabled"});
				var ctime = 60;
				var timerId = setInterval(function(){
					if(ctime==0){clearInterval(timerId); vFlag=false; _this.removeClass("test2-btn").addClass("test-btn").html("重新获取验证码").removeAttr("disabled"); return;}
				 	_this.html("重新获取验证码("+(ctime--)+"s)");//(ctime--)+秒后，
				},1000);
			}else{
				_this.html("获取短信验证码");
				$("#vcode_msg").removeClass().addClass("Validform_checktip Validform_wrong").html((result && result.status) ? result.info : "验证码发送失败");
				vFlag = false;
			}
			}
		});
	});
	$("input[name=phone]").change(function(){
		$("input[name=vcode]").attr("ajaxurl","${basepath}/account/unique2.html?type=findpwd&phone="+$(this).val()) 
	});
	$("input[name=phone]").focus().select();
	$("input[name=vcode]").attr("ajaxurl","${basepath}/account/unique2.html?type=findpwd&phone="+$("input[name=phone]").val()) 
});
</script>

<script> 
document.onkeydown=function(event){ 
    event = window.event || event;
    //var srcElement = event.srcElement || event.target;
    if (event.keyCode == 13 ) {//&& srcElement.type != 'button' && srcElement.type != 'submit' && srcElement.type != 'reset' && srcElement.type != 'textarea' &&srcElement.type != ''
        var o = document.getElementById('submitBtn');
        
        var reg_phone = document.getElementById('reg_phone').value;
        var reg_vcode = document.getElementById('reg_vcode').value;
        if(reg_phone!='' && reg_vcode!=''){
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
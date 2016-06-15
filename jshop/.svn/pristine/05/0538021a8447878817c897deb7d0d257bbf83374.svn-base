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
            <div class="step-done"><!--class为step-done表示完成</div>-->
              <div class="step-name">身份验证</div>
              <div class="step-no"></div>
              <div class="step-time"> 
                <!--<div class="step-time-wraper">2015-03-17 11:04:28</div>--> 
              </div>
            </div>
          </li>
          <li style="width:33%;">
            <div class="step-cur"><!--class为step-cur表示当前状态</div>-->
              <div class="step-name">重置登录密码</div>
              <div class="step-no">2</div>
              <div class="step-time"> 
                <!--<div class="step-time-wraper">2015-03-17 13:22:34</div>--> 
              </div>
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
      <form method="post" class="registerform" action="${basepath}/find/doCheckPwd">
        <table  style="line-height:50px;">
          <input type="hidden" name="id"  value="${e.id}"/>
          <tr>
            <td width="110"><span class="inputtitle">新密码</span></td>
            <td width="200"><input type="password" id="password1" value="" name="newPassword" class="inputbox" datatype="*6-16" nullmsg="请设置密码！" errormsg="密码范围在6~16位之间！" /></td>
            <td width="200"><div class="Validform_checktip color999">密码范围在6~16位之间</div></td>
          </tr>
          <tr class="m-t30">
            <td><span class="inputtitle">确认新密码</span></td>
            <td><input type="password" value="" id="password2" name="newPassword2" class="inputbox" datatype="*" recheck="newPassword" nullmsg="请再输入一次密码！" errormsg="您两次输入的账号密码不一致！" data-rule="确认新密码: required;match(newPassword);"/></td>
            <td><div class="Validform_checktip color999">两次输入密码需一致</div></td>
          </tr>
          <tr>
            <td></td>
            <td colspan="2" style="padding:10px 0 18px 0;"><input class="sure" id="submitBtn" type="submit" value="提 交" /></td>
          </tr>
        </table>
      </form>
    </div>
  </div>
</div>
<script>
//显示提示信息
$(function(){
	//$(".registerform").Validform();  //就这一行代码！;
		
	$(".registerform").Validform({
		tiptype:2
	});
}) 
</script>
<script>
document.onkeydown=function(event){ 
    event = window.event || event;
    //var srcElement = event.srcElement || event.target;
    if (event.keyCode == 13 ) {//&& srcElement.type != 'button' && srcElement.type != 'submit' && srcElement.type != 'reset' && srcElement.type != 'textarea' &&srcElement.type != ''
        var o = document.getElementById('submitBtn');
        
        var password1 = document.getElementById('password1').value;
        var password2 = document.getElementById('password2').value;
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
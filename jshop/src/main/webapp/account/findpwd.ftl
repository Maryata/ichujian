<#import "/static/common_front.ftl" as html/>
<#import "/indexMenu.ftl" as menu/>
<@html.htmlBase title="修改密码" badyClass="login-bg">

<@menu.menu selectMenu=""/>
<!--注册内容-->
<div class="content">
  <div class="protocol-box">
    <h1>
      找回密码
    </h1>
    <div class="ubox-info m-l90 m-t30">
      <form method="post" class="registerform" action="${basepath}/find/doFindpwdAjax">
        <table  class="pwdbox">
          <tr>
            <td width="110"><span class="inputtitle">账&nbsp;&nbsp;&nbsp;号</span></td>
            <td width="200">
            <input style="width:200px;" autocomplete="off" type="text"  id="reg_phone" name="phone" class="inputbox" datatype="s6-18" maxlength="15" ajaxurl="${basepath}/account/unique2.html?type=findpwd" errormsg="手机号格式错误" nullmsg="请输入手机号" placeholder="手机号将作为您的帐号">
            <#--<input type="text" value="" name="name" class="inputbox" datatype="s6-18"  errormsg="账号为6~18个字符！" />-->
            </td>
            <td width="400"><div class="Validform_checktip color999"></div></td>
          </tr>
          <tr>
            <td><span class="inputtitle f-l">验证码</span></td>
            <td width="100">
                <input type="text" style="width:100px; value="" autocomplete="off" name="vcode" id="reg_vcode" class="inputbox wi-55 f-l  m-t-ie" datatype="*" id="vcode"  errormsg="验证码错误！" maxlength="6" ajaxurl="${basepath}/account/unique2.html" nullmsg="请输入验证码" placeholder="请输入验证码" /><a href="#">
                <img class=" f-l m-t-ie" src="${basepath}/ValidateImage.do"  onclick="javaScript:reloadImg2(this);" title="点击刷新" style="vertical-align:bottom;margin-left:10px;margin-top:5px;"></a>	
            </td>
            <td><div class="Validform_checktip color999 f-l"></div></td>
          </tr>
           
          <tr>
            <td><span id="msg_holder" style="margin-left: 20px;">${errorMsg!''}</span></td>
            <td colspan="2" style="padding:10px 0 18px 0;"><input id="submitBtn" class="sure m-t20" type="submit" value="提 交" /></td>
          </tr>
        </table>
       
      </form>
    </div>
  </div>
</div>
<script type="text/javascript" src="${basepath}/static/js/Validform_v5.3.2_min.ext.js"></script>
<script>
//显示提示信息
$(function(){
	//$(".registerform").Validform();  //就这一行代码！;
		
	$(".registerform").Validform({
		tiptype:2
	});
}) ;
//自动刷新验证码
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
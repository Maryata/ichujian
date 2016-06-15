<#import "/account/accountHtml.ftl" as accountHtml/>
<@accountHtml.html currentMenu="topwd" title="修改密码" jqueryValidator=true>

<form role="form" id="form" method="post" class="form-horizontal jqtransform" action="${basepath}/account/changePwd" autocomplete="off" theme="simple" >
<div class="center-box m-t20">    
    <table class="cpx addborder m-t20" width="100%" border="0" cellspacing="0" cellpadding="0">
        <thead class="title1">
          <tr>
            <td colspan="2">修改密码</td>
          </tr>
        </thead>
        <thead class="left">   
          <tr>
            <td class="title2" width="150">原密码</td>
            <td>
				 <input type="password" style="display:none">
        		<input class="inputbox" autocomplete="off" type="password" name="password" data-rule="旧密码:required;password;remote[unique]" placeholder="请输入原密码" data-rule="密码:length[6~16];" maxlength="密码:length[6~16];" />
			</td>      
          </tr>
          <tr>
            <td class="title2">新密码</td>
            <td>
            <input class="inputbox" autocomplete="off" type="password" name="newPassword" id="newPassword" data-rule="新密码: required;password;" placeholder="请输入新密码">
            </td>      
          </tr>
          <tr>
            <td class="title2">确认新密码</td>
            <td>
            <input class="inputbox" autocomplete="off" type="password" name="newPassword2" id="newPassword2"  data-rule="确认新密码: required;match(newPassword);" placeholder="请输入确认密码" >
            </td>      
          </tr>
        </thead>
      </table>
      <div class="function-btns m-t20"> 
      <button type="submit" class="btn-orange-max tanchu2_btn f-r">保 存</button>
      </div>
   </div>
</form>    


</@accountHtml.html>
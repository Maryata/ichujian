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
            <div class="step-done"><!--class为step-cur表示当前状态</div>-->
              <div class="step-name">重置登录密码</div>
              <div class="step-no"></div>
              <div class="step-time"> 
                <!--<div class="step-time-wraper">2015-03-17 13:22:34</div>--> 
              </div>
            </div>
          </li>          
          <li class="step-last"  style="width:33%;">
           <div class="step-cur">
            <div class="step-name">完成</div>
            <div class="step-no">3</div>
            <div class="step-time"> 
              <!--<div class="step-time-wraper">2015-03-27 13:22:46</div>--> 
              
            </div>
           </div>
          </li>
        </ol>
      </div></h1>
    <span id="time" style="text-align:center;">3秒钟之后自动跳转，如果不跳转，请点击下面链接</span>
    <div class="pwdok">新密码已设置成功!<a href="${basepath}/index.html">点此返回首页</a></div>
  </div>
</div>
<script type="text/javascript">
  setTimeout(function(){
    //document.getElementById("time").innerHTML('在3秒钟之后自动跳转，如果不跳转，请点击下面链接');
    window.location="${basepath}/index.html";
  },3000);
</script>
</@html.htmlBase>
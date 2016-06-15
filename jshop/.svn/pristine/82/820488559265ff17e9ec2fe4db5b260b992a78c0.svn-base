
<!--弹出框-->
<div class="tanchu" style="display:none; width:300px;height:270px;margin-top:-200px!important; margin-left:-150px!important;  border-radius: 10px;">
<div class="tanchu-title">立即登录<span title="关闭" class="tanchu_close"></span></div>
<form class="loginWinform" action="${basepath}/account/doLoginAjax" method="post" theme="simple">
  <div class="login-form login-tanchu" style="top:60px;">	  
  <p>
    <input type="text" placeholder="请输入账号" name="loginAccount" datatype="*5-16"  nullmsg="请输入账号" errormsg="请输入6-16位账号" style="color: #999;"/>
    <span class="Validform_checktip"></span>
  </p>
  <p>
    <input autocomplete="off" style="color: #999;" type="text" onfocus="changeTipLogin(this);" id="loginPassText" name="passText" value="请输入密码"/>
    <input autocomplete="off" style="display:none;" type="password" onblur="changeTipLogin(this);" id="loginPass" value="" name="loginPass" datatype="*6-16" nullmsg="请输入密码" errormsg="请输入6-16位密码" />
    <span class="Validform_checktip"></span>			
  </p>
  <p class="he-40">
	<a class="btn-pwd" target="_blabk" href="${basepath}/account/register.html">立即注册</a>
  </p>
  <p>
	<input type="submit" class="enrol-btn kjdl" value="登 录" style=""/>
  </p>
  <p class="warn hei-40" style="margin-top:-20px;">
	<span id="login_msg_holder"></span>
  </p>
  </div>
</form>
</div>
<div class="tanchu_bg" ></div>
<script type="text/javascript" src="${basepath}/static/js/Validform_v5.3.2_min.ext.js"></script>
<script type="text/javascript" src="${basepath}/static/js/login.js?v=${jversion}"></script>
<!--弹出框end--> 

<!--底部 start-->
<div class="${footer!'footer'}">
  <div class="foot">
      <div class="copyright" style="text-align: center;">${systemSetting().icp} &nbsp;&nbsp; <a class="copyright" href='http://www.ichujian.com' target='_blak'>官网 </a> </div>
  </div>
  <div style="display: none;">
	<!-- cnzz站点统计 -->
	${systemSetting().statisticsCode!""}
 </div>
</div>
<!--底部 end-->

<!-- 浮动返回按钮 -->
<div id="gotopbox">	
	<a title="返回顶部" id="gotop" href="javascript:void(0)"></a>
</div>
<script type="text/javascript" src="${basepath}/static/js/gotop.js?v=${jversion}"></script>
<!-- 浮动返回按钮END -->

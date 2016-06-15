<#macro menu selectMenu="0">

<a name="toTop" id="toTop"></a>


<!--头部-->
<div id="mynav" style="width:100%;">
<!--导航 start-->
<div class="header">
  <div class="head">
    <div class="logo"><a href="${basepath}/index.html"><img src="${basepath}/static/images/logo.png"  title="${systemSetting().title}"></a></div>
    <!--登录注册购物车-->

    <div class="function-btn">
    <ul>
        <li><a class="cart-btn" href="${basepath}/cart/cart.html">购物车<#if shoppingCart()?? && shoppingCart().quantity?? && shoppingCart().quantity!=0 > <span class="cue-red" id="cartQuantityLogo">${shoppingCart().quantity}</span> <#else><span class="cue-red" style="display:none" id="cartQuantityLogo">0</span> </#if></a></li>
         <#if currentAccount()??>
         <#else>
        <li><a class="reg-btn" href="${basepath}/account/register">注册</a></li>        
        <li><a class="dl_tanchu_btn login-btn" href="${basepath}/account/login">登录</a></li>
        </#if>
      </ul>
        <#-- <ul>
        <li><a href="${basepath}/cart/cart.html" class="shopping-cart"><img src="${basepath}/static/images/ico-car.png"  title="购物车"><#if shoppingCart()?? && shoppingCart().quantity?? && shoppingCart().quantity!=0 > <span class="cue-red" id="cartQuantityLogo">${shoppingCart().quantity}</span> <#else><span class="cue-red" style="display:none" id="cartQuantityLogo">0</span> </#if></a></li>
        <#if currentAccount()??>
        <#else>
        <li><a href="${basepath}/account/register"><img src="${basepath}/static/images/ico-enrol.png"  title="注册"></a></li>        
        <li><a href="${basepath}/account/login"><img src="${basepath}/static/images/ico-login.png"  title="登录"></a></li>
        </#if>
      </ul>-->
    </div>
    
  	<#if currentAccount()??>
    <#assign currentAcc=currentAccount()>
    <div class="login-box f-r m-t20" style="display:;">
      <ul class="menu">
        <li><a class="user">(<#if currentAcc.phone?? && currentAcc.phone!="">${currentAcc.phone!""}</#if>)</a><#--<#if currentAcc.companyName?? && currentAcc.companyName!="">${currentAcc.companyName!""}<#else>${currentAcc.nickname!""}</#if>-->
          <ul>
            <li><a href="${basepath}/account/accountCenter" class="user-ico">个人资料</a></li>
            <li><a href="${basepath}/account/topwd" class="topwd-ico">修改密码</a></li>
            <li><a href="${basepath}/account/orders" class="orders-ico">我的订单</a></li>            
            <li><a href="${basepath}/account/address" class="address-ico">配送地址</a></li>
            <li><a href="${basepath}/dataCenter/list" class="data-core-ico">资料中心</a></li>
            <li><a href="${basepath}/account/exit" class="signout-ico">退出登录</a></li>
          </ul>
        </li>
      </ul>
      <span style="display: none;">
		${currentAcc.nickname!""}
		(${currentAcc.loginType!""})
	  </span>
    </div>
	</#if>
	
  </div>
</div>


<!--导航-->
<div class="naver">
  <div class="nav" >
    <div class="cover-page-wrapper cover-page-wrapper2 clearfix">
      <div class="nav-menus j-nav-menus  fl" style="position: relative;">
        <a class="handle" href="javascript:void();"><i></i>订购中心</a>
        <!--弹出菜单-->
        <div class="categorys j-categorys" style=" opacity: 0; display:none; overflow: hidden;">
          <i class="category-trangle-bg"></i>
          <div class="cpxnav">
            <ul>
              <!--<li class="nocss">产品目录</li>-->
            <#--  
        	  <li class="nav-item"><a href="${basepath}/product/productList?material=ghAndroid">安卓高透智能钢化膜</a></li>
              <li class="nav-item"><a href="${basepath}/product/productList">高透智能钢化膜</a></li>
          	-->
              <#if currentAccount()??>
				<#if currentAccount().activeCode?? && currentAccount().activeCode=="1" >
			  		<li class="nav-item"><a href="${basepath}/product/productList?material=gh">配件</a></li>
			  		<#else>
			  		<li class="nav-item"><a href="${basepath}/product/productList?material=ghAndroid">高透智能钢化膜</a></li>
			  	</#if>  
			  <#else>
			  	<li class="nav-item"><a href="${basepath}/product/productList?material=ghAndroid">高透智能钢化膜</a></li>
			  </#if> 
            </ul>
            <!--移动的滑动-->
            <div class="move-bg"></div>
            <!--移动的滑动 end-->
          </div>
          <script>
          $(function(){
              $(".cpxnav").movebg({height:40/*滑块的大小*/,extra:0/*额外反弹的距离*/,speed:300/*滑块移动的速度*/,rebound_speed:300/*滑块反弹的速度*/});
          })
          </script>
          
        </div>
      </div>
      <a class="channel  ${(selectMenu=="0")?string("channel-now","")}" href="${basepath}/index.html">首　页<span></span></a>
      <a class="channel ${(selectMenu=="introduce")?string("channel-now","")}" href="${basepath}/introduce.html">产品介绍<span></span></a>
      <a class="channel ${(selectMenu=="models")?string("channel-now","")}" href="${basepath}/models.html">适配清单<span></span></a>
      <#if currentAccount()?? > <a class="channel ${(selectMenu=="orders")?string("channel-now","")}" href="${basepath}/account/orders">我的订单</a> </#if>
      <a class="channel" target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=1013355396&site=qq&menu=yes">样品申请<span></span></a>
      <a class="channel ${(selectMenu=="contact")?string("channel-now","")}" href="${basepath}/contact">联系我们<span></span></a>
      
      
      <form role="form" name="searchForm" id="searchForm" method="get" action="${basepath}/search.html">
	    <div class="search-bag">
	      <input type="text" id="key" name="key" placeholder="请输入机型" value="${key!""}" maxlength="20"/>
	      <a class="search-btn" method="${basepath}/search.html" onclick="searchFun(this);"><img src="${basepath}/static/images/search-btn.png"  title="搜索"></a>
	    </div>
	</form>
    </div>
  </div>
</div>
</div>
</div>
<script type="text/javascript">
$(document).ready(function(e) {
 //$('#mynav').navfix(0,999);    
});
//第一个值： 你期望导航在距离顶部多少的位置浮动
//第二个值： 导航zindex
</script>

<script>
//搜索商品
function searchFun(obj){
	var _key = $.trim($("#key").val());
	if(_key==''){
		return false;
	}
	var _form = $("#searchForm");
	//_form.attr("action",$(obj).attr("method"));
	console.log("submit..."+_form.attr("action"));
	_form.submit();
}
</script>
<!--导航 end-->
</#macro>
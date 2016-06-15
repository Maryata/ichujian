<#import "/static/common_front.ftl" as html/>
<#import "/indexMenu.ftl" as menu/>

<@html.htmlBase badyClass="login-bg">

	<@menu.menu/>
	
<!-- banner2代码 开始 -->
<div class="bannerbox">
    <div id="focus">
        <ul>
            <li><a href="${basepath}/introduce.html"> <img src="${basepath}/static/images/bn1.jpg" alt="" /></a></li>
            <li><a href="${basepath}/introduce.html?p=2" > <img src="${basepath}/static/images/bn2.jpg" alt="" /></a></li>
            <li><a href="${basepath}/introduce.html"> <img src="${basepath}/static/images/bn3.jpg" alt="" /></a></li>
        </ul>
    </div>
</div>
<!-- banner2代码 结束 -->

<#assign activeCode="0">
<#assign isLogin ="0" >
<#assign accType ="0" >

<!--内容-->
<div class="content">
   <div class="show-title">产品分类</div>
  <div class="product-box">
  <#if currentAccount()??> <#assign isLogin ="1" >  <#assign accType =currentAccount().accType!"0" >
	<#if currentAccount().activeCode?? && currentAccount().activeCode=="1">
		<#assign activeCode="1">
  		<a title="配件" href="${basepath}/product/productList?material=gh"><img src="${basepath}/static/images/pj-Screen.png"></a>
	<#else>
  		<a title="高透智能钢化膜" href="${basepath}/product/productList?material=ghAndroid"><img src="${basepath}/static/images/hd-Screen1.png"></a>
  	</#if>  
  <#else>
	<a title="高透智能钢化膜" href="${basepath}/product/productList?material=ghAndroid"><img src="${basepath}/static/images/hd-Screen1.png"></a>
  </#if>  
    <#--<a title="苹果高透智能钢化膜" href="${basepath}/product/productList?material=ghIOS"><img src="${basepath}/static/images/hd-Screen2.png"></a>-->
  </div>
  <div class="show-title">新品上市</div>
  <div class="show-new">
  	<div class="wares-box">
<#list systemManager().newProducts as item>
	<#assign isInternational="gn"> <#if item.isInternational?? && item.isInternational=="1"> <#assign isInternational="gj">  </#if>
	
		    <a title="${item.name!""}">
		     <img class="ico-jiaobiao" src="${basepath}/static/images/ico-${isInternational}.png">
		     <img class="p-img" src="${systemSetting().imageRootPath!""}${item.picture!""}">
		     <span class="info">
		      <p class="name">${item.plineName!""}</p>
		      <p class="name">${item.name!""}</p>
		       <p class="money2 jiage">¥${(accType=="1")?string( item.nowPrice,item.price )}</p>
		      <input type="text" class="nub none" value="1" minNum="1" maxlength="8" name="buyNum">
		      <p><button class="bluebtn addcar_index" data-itemid="${item.id!""}">加入购物车</button></p>
		     </span>
		    </a>
</#list>  
 </div>  
  </div>
  
</div>
<!--品牌展开收起js END-->
<script src="${basepath}/static/js/product.js?v=${jversion}" charset="utf-8"></script>
<a title="查看购物车" id="cartico" href="${basepath}/cart/cart.html"><span class="cue-red" id="cartQuantity" title="购物车商品:">0</span></a>
<a id="qq-kf1" target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=934819443&site=qq&menu=yes"></a>
<a id="qq-kf2" target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=1013355396&site=qq&menu=yes"></a>
<script>
(function($, w, d){
    var quantity = "${quantity!'0'}";
	if(quantity &&quantity!=0){
		$("#cartQuantity").html(quantity);
		$("#cartQuantity").show();
	}else{
		$("#cartQuantity").hide();
	}
}(jQuery, window, document));
</script>
</@html.htmlBase>
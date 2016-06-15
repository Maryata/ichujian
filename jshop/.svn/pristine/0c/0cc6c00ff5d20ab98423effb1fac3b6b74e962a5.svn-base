<#--左侧导航栏 start-->
<div class="left290-box">
	<#assign plineMap = sysDic("account_purchaseType_curruser") >
	
	<#assign activeCode="0">
	<#assign isLogin ="0" >
	<#assign accType ="0" >
	
	<#if currentAccount()??> <#assign isLogin ="1" ><#assign accType =currentAccount().accType!"0" >
	<#if currentAccount().activeCode?? && currentAccount().activeCode=="1">
		<#assign activeCode="1">
	</#if></#if>
<#list systemManager().newProducts as item>
      <#assign isInternational="gn"> <#if item.isInternational?? && item.isInternational=="1"> <#assign isInternational="gj">  </#if>
       <div class="new-box"> 
		     <img class="ico-jiaobiao" src="${basepath}/static/images/ico-${isInternational}.png">
		     <div class="img-box"><img class="p-img" src="${systemSetting().imageRootPath!""}${item.picture!""}"></div>
		     <div class="new-info">
		      <p class="title">${item.plineName!""}</p>
		      <p class="title">${item.name!""}</p>
		      <p class="jiage2">¥${(accType=="1")?string( item.nowPrice,item.price )}</p> 
		      <input type="text" class="nub none" value="1" minNum="1" maxlength="8" name="buyNum">
		      <p><button class="bluebtn addcar_left" data-itemid="${item.id!""}">加入购物车</button></p>
		     </div>
	</div>
</#list>

</div>    
<#--左侧导航栏 end-->


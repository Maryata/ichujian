<#import "/account/accountHtml.ftl" as accountHtml/>
<@accountHtml.html currentMenu="orders" title="订单详情" jqueryValidator=false>
<#assign sMap =sysDic("order_status")  >
<#if msg??>
<div style="margin-left:200px;margin-top:10px;">
<span style="color:red;">${msg!""}!</span>
</div>
<script>
$(function(){  
 location.href = "${basepath}/account/orders";
 });  
</script>
<#else>

<form id="submitOrderForm" action="${basepath}/order/submitOrder" method="post" theme="simple">
<div class="content">
	<div class="center-box">
	   <!--订单状态-e.status-->
	   	<#--
	    <div class="wi-all align-c"  id="con_items_1" > 
	    -->
	    <div class="wi-all align-c" > 
	      	<#assign currStatus =e.status >
	      	<#assign confirmStatus =e.confirmStatus!'n' >
	      	<#assign confirmFlag =false >
	      	<#assign waitmsg = "当前为\"<b>${sMap[currStatus]}</b>\"状态" >
			<div class="flowstep">
	       		<ol class="flowstep-5">
	     		<#if currStatus=='init'>
		          	<li class="step-first"><div class="step-done"><div class="step-name">提交订单</div><div class="step-no">1</div><div class="step-time"></div></div></li>
		          	<li ><div class="step-cur"><div class="step-name">处理中</div><div class="step-no">2</div><div class="step-time"></div></div></li>
		          	<li ><div class="step-name">待发货</div><div class="step-no">3</div><div class="step-time"></div></li>
		          	<li ><div class="step-name">已发货</div><div class="step-no">4</div><div class="step-time"></div></li>
		          	<li class="step-last"><div class="step-name">订单完成</div><div class="step-no">5</div><div class="step-time"></div></li>
	        	<#elseif currStatus=='stock'>
		          <li class="step-first">
		          <div class="step-done"><div class="step-name">提交订单</div><div class="step-no">1</div><div class="step-time"></div></div></li>
		          <li ><div class="step-done"><div class="step-name">处理中</div><div class="step-no">2</div><div class="step-time"></div></div></li>
		          <li ><div class="step-name">待发货</div><div class="step-no">3</div><div class="step-time"></div></li>
		          <li ><div class="step-name">已发货</div><div class="step-no">4</div><div class="step-time"></div></li>
		          <li class="step-last"><div class="step-name">订单完成</div><div class="step-no">5</div><div class="step-time"></div></li>
		        <#elseif currStatus=='pass'>
		          <li class="step-first"><div class="step-done"><div class="step-name">提交订单</div><div class="step-no">1</div><div class="step-time"></div></div></li>
		          <li ><div class="step-done"><div class="step-name">处理中</div><div class="step-no">2</div><div class="step-time"></div></div></li>
		          <li ><div class="step-cur"><div class="step-name">待发货</div><div class="step-no">3</div><div class="step-time"></div></div></li>
		          <li ><div class="step-name">已发货</div><div class="step-no">4</div><div class="step-time"></div></li>
		          <li class="step-last"><div class="step-name">订单完成</div><div class="step-no">5</div><div class="step-time"></div></li>
          		<#assign waitmsg = "您的订单已确认并安排生产发货，感谢您的耐心等候。" >        
	        	
				<#elseif currStatus=='send'>
	          		<li class="step-first"><div class="step-done"><div class="step-name">提交订单</div><div class="step-no">1</div><div class="step-time"></div></div></li>
	          		<li ><div class="step-done"><div class="step-name">处理中</div><div class="step-no">2</div><div class="step-time"></div></div></li>
	          		<li ><div class="step-done"><div class="step-name">待发货</div><div class="step-no">3</div><div class="step-time"></div></div></li>
	          		<li ><div class="step-cur"><div class="step-name">已发货</div><div class="step-no">4</div><div class="step-time"></div></div></li>
	     			<li class="step-last"><div class="step-name">订单完成</div><div class="step-no">5</div><div class="step-time"></div></li>
	          	<#assign waitmsg = "您采购的商品已从仓库发货，请留意物流通知并及时签收，谢谢。" >
	        	<#elseif currStatus=='file'>
		          	<li class="step-first"><div class="step-done"><div class="step-name">提交订单</div><div class="step-no">1</div><div class="step-time"></div></div></li>
		          	<li ><div class="step-done"><div class="step-name">处理中</div><div class="step-no">2</div><div class="step-time"></div></div></li>
		          	<li ><div class="step-done"><div class="step-name">待发货</div><div class="step-no">3</div><div class="step-time"></div></div></li>
		          	<li ><div class="step-done"><div class="step-name">已发货</div><div class="step-no">4</div><div class="step-time"></div></div></li>
		          	<li class="step-last"><div class="step-cur"><div class="step-name">订单完成</div><div class="step-no">5</div><div class="step-time"></div></div></li>
	          	<#assign waitmsg = "您的订单完成。" >
	        	</#if>
	        	</ol>
	        	<span class="f_24 color999 m-t20 block">当前订单状态：<span class="color-green">
	        		<#if currStatus=='init' && e.paystatus=='y'>
			      	已支付
			      	<#else>
			      	${sMap[currStatus]}	
			      	</#if>		       
	        	</span></span>
			</div>
		</div>
	  	<!-- 订单详情 -->
		<table class="cpx addborder m-t50" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead class="title1">
			<tr>
			  <td colspan="2">订单详情</td>
			</tr>
			</thead>
			<thead class="left">
			<tr>
			  <td class="title2" width="100">订单编号</td>
			  <td align="left">${e.serialId!""}</td>      
			</tr>
			<tr>
			  <td class="title2">创建日期</td>
			  <td>${e.createdate?substring(0,16)}</td>      
			</tr>
			<tr>
			  <td class="title2">预计发货日期</td>
			  <td>${e.expectSignDate!""}</td>      
			</tr>
			<tr>
			  <td class="title2">物流费用</td>
			  <td>
			     <#if e.expressType=="0">
		           到付(以物流公司收费标准为准)
		          </#if>
		          <#if e.expressType=="1">
		            包邮
		          </#if>
			  </td>      
			</tr>
			<#if e.isServiceFee?? && e.isServiceFee=="1">
	        <tr>
	          <td class="title2">服务费</td>
	          <td>￥${e.fee!""}</td> 
	          <!-- 服务费=折后金额*0.1 -->     
	        </tr>
            </#if>
			<tr>
			  <td class="title2">数量(个)</td>
			  <td>${e.number!""}</td>      
			</tr>
			<tr>
			  <td class="title2">实付金额</td>
			  <td>￥${e.amount!""}</td>      
			</tr>
			<tr>
			  <td class="title2">收 货 人</td>
			  <td><#if e.ordership??>${e.ordership.shipname!""}</#if></td>      
			</tr>
			<tr>
			  <td class="title2">联系地址</td>
			  <td><#if e.ordership??>${e.ordership.shiparea!""}  ${e.ordership.shipaddress!""}</#if></td>      
			</tr>
			<tr>
			  <td class="title2">联系电话</td>
			  <td><#if e.ordership??>${e.ordership.phone!""}</#if></td>      
			</tr>
			<tr>
			  <td class="title2">备注信息</td>
			  <td>${e.otherRequirement!"无"}</td>      
			</tr>
			</thead>
	        
		</table>  
		<!-- 商品详情 -->
		<div class="cpx-box m-t20">
		<#if e?? && e.orders?? && e.ordermainList?? >
		<#list e.ordermainList as m>
			<table class="cpx" border="0" cellspacing="0" cellpadding="0">
				<thead class="title1">
				<tr>
					<td colspan="7">${m.mainCatalogName!""}</td>
				</tr>
				</thead>
				<thead class="title2">
			  		<tr>
						<td>商品名称</td>
						<td>单价(元)</td>
						<td>数量(个)</td>
						<td>小计(元)</td>
				  	</tr>
				</thead>
				<#assign pindex=1 > <#assign lastMainName="" >
				<tbody>
				<#list e.orders as item>
					<#if item.pline?? && m.mainCatalogName?? && item.pline==m.mainCatalogName>
					<!--用于不同品牌之间的样式拆分-->
					<#assign trCss="">
						<#if pindex==1><#assign trCss="first" > 
						<#else>
						<#if lastMainName!=item.mainCatalogName>
						  <#assign trCss="line">
						</#if>
					</#if>
					<#assign pindex=pindex+1 >
				  	<tr class="${trCss!""}">
						<td><#if item.picture?? && item.picture!=""><img src="${systemSetting().imageRootPath}${item.picture!""}" height="40" title="${item.productName!""}"></#if>
						<span class="title3" title="${item.productName!""}">${item.productName!""}</span></td>
						<td class="bc-f9">${item.price!"0"}</td>
						<td>${item.productNumber!"0"}</td>
						<td class="bc-f9">${item.total0!"0"}</td>
				  	</tr>
				   	<#assign lastMainName="${item.mainCatalogName!''}" >
					</#if>
				</#list>
				</tbody>
				<tfoot>
				   <tr>
				<td colspan="7"><span title="实付：￥${m.subTotal!"0.00"}" class="money">合计金额：￥${m.subTotal!"0.00"}</span> <span title="总数：${m.number!"0"}" class="total">合计数量：${m.number!"0"}</span></td>
				  </tr>
				</tfoot>
			</table>
		</#list>
		</#if>
		</div>
		<!--功能操作-->
		<div class="function-box">     
			<span class="mtinfo">
			   <span  class="money" title="实付金额：${e.amount!""}" >实付金额：￥${e.amount!""}</span>
			   <#--<#if e.rebate?? && e.rebate!=0 ><span class="agio">折扣率：${e.rebate!""}%</span></#if>-->   
			   <span class="money">总计金额：￥${e.ptotal!""}</span>        
			   <span title="总数：${e.number!""}" class="total">总计数量：${e.number!""}</span>
			</span>     
		</div>
		<div class="function-btns">        
			<a class="btn-orange-max f-r" href="${basepath}/account/orders">返回</a>
	     	<#--<a class="btn-blue-max f-r m-r20" href="${basepath}/order/orderExport?id=${e.id!""}">导出订单</a>-->      
	    </div>
	</div>
</div>
</form>
<script type="text/javascript" src="${staticpath}/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript">
  function confirmOrder(){
	console.log("提交订单...");
	var msg = "<br>期望到货日期："+$("#expectSignDateSpan").attr("data-time");
	layer.confirm('是否确认? '+msg, {icon: 3, title:''}, function(index){
    	$("#submitOrderForm").submit();//确认提交
	    layer.close(index);
	});
}
	function setTab(name,cursel,n){
	 for(i=1;i<=n;i++){
	  var menu=document.getElementById(name+i);
	  var con=document.getElementById("con_"+name+"_"+i);
	  menu.className=i==cursel?"hover":"";
	  con.style.display=i==cursel?"block":"none";
	 }
	}
</script>
</#if>
</@accountHtml.html>
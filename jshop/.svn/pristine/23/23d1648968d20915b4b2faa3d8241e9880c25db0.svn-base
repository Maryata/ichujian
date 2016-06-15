<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="订单管理" currentListUrl="/manage/order/selectList?init=y">
<style>
.simpleOrderReport{
	font-weight: 700;font-size: 16px;color: #f50;
}

.title1{
	/* background-color: #f0f0f0; */
    /* background: -webkit-linear-gradient(top,#fafafa 0,#f0f0f0 100%); */
	background: -moz-linear-gradient(top, #fafafa 0, #f0f0f0 100%);
	background: -o-linear-gradient(top, #fafafa 0, #f0f0f0 100%);
	background: linear-gradient(to bottom, #fafafa 0, #f0f0f0 100%);
	background-repeat: repeat-x;
 	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#fafafa, endColorstr=#f0f0f0, GradientType=0);
	font-weight: bold;
	color: #888;
	text-align: left;
	padding-left: 10px;
	text-shadow: 1px 1px 1px #fff;
	height: 25px;
	font-size: 13px;
}

.title2{
	/* background-color: #f3f3f3; */
    /* background: -webkit-linear-gradient(top,#f7f7f7 0,#f3f3f3 100%); */
	background: -moz-linear-gradient(top, #f7f7f7 0, #f3f3f3 100%);
	background: -o-linear-gradient(top, #f7f7f7 0, #f3f3f3 100%);
	background: linear-gradient(to bottom, #f7f7f7 0, #f3f3f3 100%);
	background-repeat: repeat-x;
 	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#f7f7f7, endColorstr=#f3f3f3, GradientType=0);
	color: #999;
	text-shadow: 1px 1px 1px #fff;
}
tr.first {
	border-top: none;
}
tr.line {border-top: 2px #cedae6 dashed;}

</style>
<form action="${basepath}/manage/order" method="post" theme="simple" id="form" name="form">
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">订单处理</a></li>
			<li><a href="#tabs-2">订单详情<#if e.lowStocks??&&e.lowStocks=="y"><font color="red">【缺货】</font></#if></a></li>
			<li><a href="#tabs-3">订单日志</a></li>
		</ul>
	<div id="tabs-1">
		<input type="hidden" value="${e.id!""}" id="orderId"  name="id"/>
<#--<%-- 	<s:hidden name="type"/> --%>-->
<#--<%-- 	<s:if test="e.type.equals('update')"> --%>-->
		<div id="buttons" style="text-align: center;border-bottom: 1px solid #ccc;padding: 5px;">
		<div id="updateMsg"><font color='red'>${e.updateMsg!""}</font></div>
	 <#assign sMap =sysDic("order_status")  >
	  <!--订单状态-->
      <#assign currStatus =e.status >
      <#assign confirmStatus =e.confirmStatus!'n' >
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
          
        <#elseif currStatus=='send'>
          <li class="step-first"><div class="step-done"><div class="step-name">提交订单</div><div class="step-no">1</div><div class="step-time"></div></div></li>
          <li ><div class="step-done"><div class="step-name">处理中</div><div class="step-no">2</div><div class="step-time"></div></div></li>
          <li ><div class="step-done"><div class="step-name">待发货</div><div class="step-no">3</div><div class="step-time"></div></div></li>
          <li ><div class="step-cur"><div class="step-name">已发货</div><div class="step-no">4</div><div class="step-time"></div></div></li>
          <li class="step-last"><div class="step-name">订单完成</div><div class="step-no">5</div><div class="step-time"></div></li>
          
        <#elseif currStatus=='file'>
          <li class="step-first"><div class="step-done"><div class="step-name">提交订单</div><div class="step-no">1</div><div class="step-time"></div></div></li>
          <li ><div class="step-done"><div class="step-name">处理中</div><div class="step-no">2</div><div class="step-time"></div></div></li>
          <li ><div class="step-done"><div class="step-name">待发货</div><div class="step-no">3</div><div class="step-time"></div></div></li>
          <li ><div class="step-done"><div class="step-name">已发货</div><div class="step-no">4</div><div class="step-time"></div></div></li>
          <li class="step-last"><div class="step-cur"><div class="step-name">订单完成</div><div class="step-no">5</div><div class="step-time"></div></div></li>
          
        </#if>
        </ol>
      </div>
			
			<#if e.status??>
			
			<#if e.status=='init' >
			
			<#if e.paystatus=='y'>
				<span style='color:#009349;font-weight:700;'>
					已 支 付&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
			<#else>
				<span style='color:red;font-weight:700;'>
					未 支 付&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
			</#if>
			
			  <#-- 审核权限  -->
			  <#if checkPrivilege("/manage/order/check") >
			     <input type="button" method="updateOrderStatus?status=stock" onclick="return onSubmit(this);" value="财务确认已收到货款" class="btn btn-warning"/>
			  </#if>
			 (请销售助理尽快确认订单)
			  <#-- 审核权限  -->
			  <#if checkPrivilege("/manage/order/check") >
			    <input type="button" method="updateOrderStatus?status=cancel" onclick="return onSubmit(this);" value="取消订单" class="btn btn-warning"/>
			  </#if>
			  <input type="hidden" value="${e.confirmStatus!"n"}" name="confirmStatus"/>
			  <input type="hidden" value="n" name="isConfirmStatus"/>
			</#if>
			
			<#if e.status=='stock'>
			  <input type="button" method1="${basepath}/manage/order/updateOrderStatus?status=pass" onclick="$.load_order_stock(this,${e.id!""});" value="库存核对" class="btn btn-warning"/>
			</#if>
			<#if e.status=='pass'>
			  <#-- 确认发货权限  -->
			  <#if checkPrivilege("/manage/order/send") >
				<input type="button" method="updateOrderStatus?status=send" onclick="return sendOrderFunc(this);" value="确认发货" class="btn btn-warning"/>
			  </#if>
			</#if>
			
			<#if e.status=='send'>
			  <#if checkPrivilege("/manage/order/payy") >
			  <input type="button" method="updateOrderStatus?status=file" onclick="return onSubmit(this);" value="确认完成订单" class="btn btn-warning"/>
			  </#if>
			</#if>
			</#if>
		</div>

	<div id="addPayDiv" style="display: none;">
		<table class="table">
			<tr>
				<td colspan="2">
					<h4>添加支付记录</h4>
				</td>
			</tr>
			<tr>
				<td>支付方式</td>
				<td>
					<select name="orderpay.paymethod">
						<option value="zfb">支付宝</option>
						<option value="xx">线下支付</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>支付金额</td>
				<td>
					<input name="orderpay.payamount" value="0">
				</td>
			</tr>
			<tr>
				<td>备注</td>
				<td>
					<div class="controls"><input name="orderpay.remark" value="后台添加"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" method="insertOrderpay" onclick="return onSubmit(this);" value="确认" class="btn btn-primary"/>
					<input id="cancelPayBtn" type="button" value="取消" class="btn"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="updatePayMoneryDiv" style="display: none;">
		<table class="table">
			<tr>
				<td colspan="2">
					<h4>修改订单总金额</h4>
				</td>
			</tr>
			<tr>
				<td>支付金额</td>
				<td>
					<input name="amount">
				</td>
			</tr>
			<tr>
				<td>备注</td>
				<td>
					<div class="controls"><input name="updatePayMoneryRemark" placeholder="修改订单金额备注"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" method="updatePayMonery" onclick="javascript:return confirm('确认本次操作?');" value="确认" class="btn btn-primary"/>
					<input id="cancelUpdatePayMoneryBtn" type="button" value="取消" class="btn"/>
				</td>
			</tr>
		</table>
	</div>
	<table class="table table-bordered">
	        <#assign isS = false> 
			<#if e.ordership?? && e.ordership.shipname??>
			<#assign isS = true>
			</#if>
			<tr >
				<td class="title2" style="text-align: left;width:15%;">创建日期</td>   
				<td style="text-align: left;width:85%;">${e.createdate!""}</td>
			</tr>
			<#if e.status=='pass' >
			    <tr>
					<td class="title2" style="text-align: left;width:15%;">发货日期<span style="color:red;">&nbsp;&nbsp;*</span></td>   
					<td style="text-align: left;width:85%;">
	                     <input class="search-query input-small" maxlength="10" style="width:160px" type="text" name="sendDate" value="${nowDateTime!""}"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowWeek:true,maxDate:'2999-12-12'})"/>
					
	                </td>
                </tr>
			 </#if>
			 <#if e.status=='send' || e.status=='file'>
			     <tr>
					<td class="title2" style="text-align: left;width:15%;">发货日期<span style="color:red;">&nbsp;&nbsp;*</span></td>   
					<td style="text-align: left;width:85%;">
					${e.sendDate!""}
	                </td>
                </tr>
			 </#if>
			 <#if e.status=='pass' >
			    <tr>
					<td class="title2" style="text-align: left;width:15%;">物流单号<span style="color:red;">&nbsp;&nbsp;*</span></td>   
					<td style="text-align: left;width:85%;">
	                    <input class="inputbox input-medium" type="text" value="${e.expressNo!''}" name="expressNo" id="expressNo" placeholder="请输入物流单号" maxlength="20" />
	                </td>
                </tr>
			 </#if>
			 <#if e.status=='send' || e.status=='file'>
			     <tr>
					<td class="title2" style="text-align: left;width:15%;">物流单号<span style="color:red;">&nbsp;&nbsp;*</span></td>   
					<td style="text-align: left;width:85%;">
					${e.expressNo!''}
	                </td>
                </tr>
			 </#if>
			<#--   aaaaaa-->
			<input type="hidden" name="shipname" id="shipname" value="${e.ordership.shipname!""}"/>
			<input type="hidden" name="expressCompanyName" id="expressCompanyName" value="${e.expressCompanyName!''}"/>
			<input type="hidden" name="number" id="number" value="${e.number!""}"/>
			<input type="hidden" name="serialId" id="serialId" value="${e.serialId!""}"/>
			<input type="hidden" name="account" id="account" value="${e.account!""}"/>
			<tr >
				<td class="title2" style="text-align: left;width:15%;">预计发货日期<span style="color:red;">&nbsp;&nbsp;*</span></td>   
				<td style="text-align: left;width:85%;">
	            ${e.expectSignDate!""}
	            </td>
			</tr>
			<tr >
				<td class="title2" style="text-align: left;width:15%;">物流费用</td>   
				<td style="text-align: left;width:85%;">
				 <#if e.expressType=="0">
                   	到付(以物流公司收费标准为准)
                 </#if>
                 <#if e.expressType=="1">
                   	包邮
                 </#if>
                </td>
			</tr>
			<tr >
				<td class="title2" style="text-align: left;width:15%;">物流公司</td>   
				<td style="text-align: left;width:85%;">
                  	${e.expressCompanyName!''}
                </td>
			</tr>
			
			
			<tr >
				<td class="title2" style="text-align: left;width:15%;">客户账号</td>   
				<td style="text-align: left;width:85%;">
                  ${e.account!''}
                </td>
			</tr>
			 <#if e.incoiceTitle?? && e.incoiceTitle!="">
			<tr >
				<td class="title2" style="text-align: left;width:15%;">发票抬头</td>   
				<td style="text-align: left;width:85%;">
                  ${e.incoiceTitle!''}
                </td>
			</tr>
			</#if>
			<tr >
				<td class="title2" style="text-align: left;width:15%;">备注</td>   
				<td style="text-align: left;width:85%;">
				${e.otherRequirement!''}
                </td>
			</tr>
			<tr>
				<td class="title2" style="text-align: left;width:15%;">是否代客下单</td>   
				<td style="text-align: left;width:85%;">
				<#if e.isSysOrder?? && e.isSysOrder=="0">
					否
				<#else>
					是
				</#if>
                </td>
			</tr>
			<tr >
				<td class="title2" style="text-align: left;width:15%;">结算方式</td>   
				<td style="text-align: left;width:85%;">
			    <#if e.payType?? && e.payType=="1">现结<#else>月结</#if>
                </td>
			</tr>
			
		</table>
		<div id="isHidden" style="display:none;">
		 <table class="table table-bordered">
		   <thead class="title2">
		    <tr>
		      <td>支付方式</td>
		      <td>支付时间</td>
		      <td>支付状态</td>
		    </tr>
		   </thead>
		   <tbody id="payType"></tbody>
		 </table>
		</div>
	
   </div>		
		<#assign materialMap = sysDic("product_material") >
		<div id="tabs-2">
		<#assign isS = false> 
		<#if e.ordership?? && e.ordership.shipname??>
		<#assign isS = true>
		</#if>

		<table class="table table-bordered"  border="0" cellspacing="0" cellpadding="0">
        <thead  class="title1"><tr><td colspan="6">订单信息</td></tr></thead>
        <tbody>
          <tr>
            <td class="title2" width="120px">订单编号</td>
            <td width="150px"><#--${e.id!""}-->${e.serialId!""}</td>
            <td class="title2" width="120px">订单总额</td>
            <td width="140px">${e.amount!""}元</td>
            <td class="title2" width="120px">数量(个)</td>
            <td width="160px">${e.number!""}</td>
          </tr>
          <tr>
            <td class="title2">下单日期</td>
            <td>${e.createdate?substring(0,16)}</td>
            <td class="title2">物流费用</td>
            <td >
        	 <#if e.expressType=="0">
           	    到付(以物流公司收费标准为准)
             </#if>
             <#if e.expressType=="1">
               	包邮
             </#if>
             </td>
            <td class="title2">联系电话</td>
            <td><#if e.ordership??>${e.ordership.phone!""}</#if></td>
          </tr>
          <input type="hidden" id="phone" name="phone" value="${e.ordership.phone!""}"/>
          <tr>
            <td class="title2">收 货 人</td>
            <td><#if e.ordership??>${e.ordership.shipname!""}</#if></td>
            <td class="title2">联系地址</td>
            <td colspan="3"><#if e.ordership??>${e.ordership.shiparea!""}  ${e.ordership.shipaddress!""}</#if></td>
          </tr>
        </tbody>
      </table>
            <#if e?? && e.orderdetail?? && e.ordermain?? >
            <#list e.ordermain as m>
			<table class="table table-bordered">
			<thead class="title2"><tr><td colspan="12">${m.mainCatalogName!""}</td></tr></thead>
			    <thead class="title2">
				<tr class="title2" style="background-color: #dff0d8" >
				    <th width="14%" style="text-align: center" >商品编号</th>
				    <th width="45%" style="text-align: center">商品名称</th>
				   <#-- <th width="10%" style="text-align: center">品牌</th>
					<th width="6%">序号</th>
					<th width="5%" style="text-align: center"></th>
					<th width="15%" style="text-align: center">适配型号</th>
					<th width="12%" style="text-align: center">材质</th>-->
					<!--<th>购买的商品规格</th>-->
<!-- 					<th>赠送积分</th> -->
					<th width="14%" style="text-align: center">数量</th>
					<th width="15%" style="text-align: center">单价</th>
<!-- 					<th>配送费</th> -->
					<th width="13%" style="text-align: center" colspan="12">小计</th>
				</tr>
				</thead>
				<#assign pindex=1 > <#assign lastMainName="" >
				    <tbody> 
				    <#list e.orderdetail as item>
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
					     <td style="text-align: center" nowrap="nowrap">${item.pcode!""}</td>
					     <td><#if item.picture?? && item.picture!=""><img src="${systemSetting().imageRootPath}${item.picture!""}" height="40" title="${item.productName!""}"></#if>
                         <span class="title33" title="${item.productName!""}">${item.productName!""}</span></td>
					     <#--<td style="text-align: center">&nbsp;${item.mainCatalogName!""}</td>
						 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						    <#if item.picture?? && item.picture!=""><img src="${systemSetting().imageRootPath}${item.picture!""}" width="24px" height="40px"></#if>
							${item.productName!""}
						</td>
						<td style="text-align: center">&nbsp;${item.childrenCatalogName!""}</td>
						<td style="text-align: center">&nbsp; ${item.material}</td>-->
						<!--<td>&nbsp;${item.specInfo!""}</td>-->
<#--<%-- 						<td>&nbsp;${item.score!""}</td> --%>-->
						<td style="text-align: center">&nbsp;${item.number!""}</td>
						<td style="text-align: center">&nbsp;￥${item.price!""}</td>
<#--<%-- 						<td>&nbsp;￥${item.fee!""}</td> --%>-->
						<td style="text-align: center" colspan="12">&nbsp;￥${item.total0!""}</td>
						</td>
					</tr>
					<#assign lastMainName="${item.mainCatalogName!''}" >
				</#if>
              </#list>
				</tbody>
			</table>
	       </#list>
	       </#if>
			<div style="text-align:center;">
			     <a class="btn btn-primary"  href="toPrint?id=${e.id}">
					<i class="icon-search icon-white"></i> 打印
			     </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			     <a class="btn  btn-success"  href="${basepath}/manage/order/selectList?init=y">
				     <i class="icon-search icon-white"></i> 返回
			     </a>
			</div>
		</div>
		
		<div id="tabs-3">
			<table class="table table-bordered">
				<tr class="title2"  style="background-color: #dff0d8">
					<th width="50px">序号</th>
					<th>操作人</th>
					<th>操作人类型</th>
					<th>时间</th>
					<th>日志</th>
				</tr>
				<#list e.orderlogs as item>
					<tr>
						<td>&nbsp;${item_index+1}</td>
						<td nowrap="nowrap">&nbsp;
							<#if item.accountType??&&item.accountType=="w">
								<a target="_blank" href="${basepath}/manage/account/show?account=${item.account!""}">${item.account!""}</a>
							<#elseif item.accountType??&&item.accountType=="m">
								<a target="_blank" href="${basepath}/manage/user/show?account=${item.account!""}">${item.account!""}</a>
							<#elseif item.accountType??&&item.accountType=="p">
								第三方支付系统
							<#else>
								未知
							</#if>
						</td>
						<td>&nbsp;
							<#if item.accountType??&&item.accountType=="w">
								客户
							<#elseif item.accountType??&&item.accountType=="m">
								${systemSetting().systemCode}(系统)
							<#elseif item.accountType??&&item.accountType=="p">
								支付宝
							<#else>
								未知
							</#if>
						</td>
						<td>&nbsp;${item.createdate!""}</td>
						<td>&nbsp;${item.content!""}</td>
					</tr>
				</#list>
			</table>
		</div>
	</div>
</form>

<script>
$(function() {
	$( "#tabs" ).tabs({
		//event: "mouseover"
	});
	$("#cancelPayBtn").click(function(){
		$("#addPayDiv").slideUp();
		$("#addPayBtn").show();
		//$("#buttons").find("input[type=button]").each(function(){
			//$(this).attr("disabled","");
		//});
		return false;
	});
	$("#cancelUpdatePayMoneryBtn").click(function(){
		$("#updatePayMoneryDiv").slideUp();
		$("#updatePayMoneryBtn").show();
		return false;
	});
});
function addPayFunc(){
	$("#addPayDiv").slideDown();
	$("#addPayBtn").hide();
	//$("#buttons").find("input[type=button]").each(function(){
		//$(this).attr("disabled","disabled");
	//});
	return false;
}
function updatePayMoneryFunc(){
	$("#updatePayMoneryDiv").slideDown();
	$("#updatePayMoneryBtn").hide();
	return false;
}
function onSubmit(obj){//20150813
	var thisBtn = $(obj);
	thisBtn.attr('s',false);
	if($(obj).attr("disabled")=='disabled'){//alert("disabled不可点击"+$(obj).attr("disabled"));
		return false;
	}
	if(!confirm("确认本次操作?")){
		return;
	}
	thisBtn.attr('s',true);
}
function onSubmits(obj){//20150813
    var esd = $("input[name=expectSignDate]").val();
    var phone=$("input[name=phone]").val();
	var thisBtn = $(obj);
	thisBtn.attr('s',false);
	if($(obj).attr("disabled")=='disabled'){//alert("disabled不可点击"+$(obj).attr("disabled"));
		return false;
	}
	if(!confirm("确认本次操作?")){
		return;
	}
	alert(esd);
	thisBtn.attr('s',true);
	//location.href="${basepath}/manage/order/updateOrderStatus?status=pass&id="+${e.id!""}+"&expectSignDate="+esd+"&phone="+phone;
}
function confirmSubmit(obj,v){
	var thisBtn = $(obj);
	thisBtn.attr('s',false);
	if($(obj).attr("disabled")=='disabled'){//alert("disabled不可点击"+$(obj).attr("disabled"));
		return;
	}
	var esd = $("input[name=expectSignDate]");
	var fee = $("input[name=fee]");
	var sendDate=$("input[name=sendDate]");
	if(sendDate.val()==''){
	alert(请输入发货日期);
	sendDate.focus();
	return;
	}
	if(esd.val()=='' ){//|| fee.val()=='' || fee.val()=='0.00' 
		alert("请确认物流费和日期");
		fee.focus();
		return;
	}
	var msg = "\n期望到货日期："+esd.val();
	msg += "\n物流费用："+fee.val();
	if(!confirm('是否确认? '+msg)){
		return;
	}
	$("input[name=isConfirmStatus]").val("y");
	$("input[name=confirmStatus]").val(v);
	thisBtn.attr('s',true);
}


function sendOrderFunc(obj){
	var thisBtn = $(obj);
	thisBtn.attr('s',false);
	if($("#expressNo").val()=='' || $("#expressNo").val().length<3 ){
		alert("请输入物流单号");
		return;
	}
	if(!confirm("确认本次操作?")){
		return;
	}
	thisBtn.attr('s',true);
}


</script>

<script type="text/javascript" src="${basepath}/resource/layer-v1.9.2/layer/layer.js"></script>	
<script type="text/javascript" src="${basepath}/manage/orderManager.js"></script>	

<div id="detailsPage" style="display:none;height: 410px;overflow: auto;">
<table class="table table-hover" >
   <thead>
	<tr style="background-color: #dff0d8">
		<th nowrap="nowrap">序号</th>
		<th nowrap="nowrap">产品代码</th>
		<th nowrap="nowrap">名称</th>
		<th nowrap="nowrap">购买数量</th>
		<th nowrap="nowrap">可销售库存</th>
	</tr>
  </thead>
  <tbody id="detailsList">
  </tbody>	
</table>
</div>

</@page.pageBase>
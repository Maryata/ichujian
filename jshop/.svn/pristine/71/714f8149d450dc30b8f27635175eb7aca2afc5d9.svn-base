<#import "/manage/tpl/htmlBase.ftl" as html>
<@html.htmlBase>
<form action="${basepath}/manage/account/updateAccount" theme="simple" id="form" method="post">

<div id="tabs">
	<ul>
		<li><a href="#tabs-1">基本信息</a></li>
	</ul>
	<div id="tabs-1">
		<div class="alert alert-info" style="margin-bottom: 2px;text-align: left;">
			<strong>会员信息：</strong>
		</div>
		<table class="table table-bordered">
					<tr style="display: none;">
						<td>id</td>
						<td><input type="hidden" value="${e.id!""}" name="id" label="id" id="id"/></td>
					</tr>
					
					<tr>
					 <td style="text-align: left;" colspan="7"><span style="color:red;">基础信息</span></td>   
					</tr>
					<tr>
						<td style="text-align: right;"><span style="color:red;">*</span>账号:</td>   
						<td style="text-align: left;">${e.account!""}</td>
						<td style="text-align: right;"><span style="color:red;">*</span>账号类型:</td>   
						<td style="text-align: left;" colspan="4">
						<#if e.accType=="0" || e.accType=="-1">
		             	   个人账号<#if e.registerType?? && e.registerType=="1">
		             	   （销售注册）<#else>（客户注册）</#if>
		             	</#if>
						<#if e.accType=="1">
						   企业账号<#if e.registerType?? && e.registerType=="1">
		             	   （销售注册）<#else>（客户注册）</#if>
		             	</#if>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;"><span style="color:red;">*</span>会员代码:</td>
						<td style="text-align: left;" >${e.accountCode!""}</td>
						<td style="text-align: right;"><span style="color:red;">*</span>是否激活码用户:</td>
						<td style="text-align: left;" colspan="4">
						<#if e.activeCode?? && e.activeCode=="0">否</#if>
						<#if e.activeCode?? && e.activeCode=="1">是</#if>
						
						</td>
					</tr>
					<tr>
						<td style="text-align: right;"><span style="color:red;">*</span>联系人姓名:</td>
						<td style="text-align: left;">${e.trueName!""}</td>
						<td style="text-align: right;" ><span style="color:red;">*</span>联系人电话:</td>
						<td style="text-align: left;" colspan="4">${e.tel!""}</td>
					</tr>
					<#if e.accType=="-1" || e.accType=="1">
					<tr>
						<td style="text-align: right;"><span style="color:red;">*</span>联系人邮箱:</td>
						<td style="text-align: left;">${e.email!""}
							<#if e.emailIsActive??&&e.emailIsActive=="y"><span class="badge badge-success">已激活</span>
							<#else><span class="badge badge-success">未激活</span></#if>
						</td>
						<td style="text-align: right;"><span style="color:red;">*</span>联系人职位:</td>
						<td style="text-align: left;" colspan="4">${e.jobTitle!""}</td>
					</tr>
					<#else>
					<tr>
						<td style="text-align: right;"><span style="color:red;">*</span>联系人邮箱:</td>
						<td style="text-align: left;" colspan="7">${e.email!""}
							<#if e.emailIsActive??&&e.emailIsActive=="y"><span class="badge badge-success">已激活</span>
							<#else><span class="badge badge-success">未激活</span></#if>
						</td>
					</tr>
					</#if>
					<#if e.accType=="-1" || e.accType=="1">
					<tr>
						<td style="text-align: right;"><span style="color:red;">*</span>公司法人:</td>
						<td style="text-align: left;">${e.corporate!""}</td>
						<td style="text-align: right;"><span style="color:red;">*</span>商户/企业名称:</td>
						<td style="text-align: left;" colspan="4">${e.companyName!""}</td>
					</tr>
					<tr>
						<td style="text-align: right;"><span style="color:red;">*</span>总经理姓名:</td>
						<td style="text-align: left;">${e.bossName!""}</td>
						<td style="text-align: right;">总经理电话:</td>
						<td style="text-align: left;" colspan="4">${e.bossPhone!""}</td>
					</tr>
					<tr>
					    <td style="text-align: right;"><span style="color:red;">*</span>公司地址:</td>
						<td style="text-align: left;" colspan="6">${e.address!""}</td>
					</tr>
				    <tr>
					 <td style="text-align: left;"  colspan="7"><span style="color:red;">公司资质</span></td>   
					</tr>
					<tr>
						<td style="text-align: right;">订制品牌名:</td>
						<td style="text-align: left;">${e.accountBrand!""}</td>
					</tr>
					<tr>
						<td style="text-align: right;">开户帐号:</td>
						<td style="text-align: left;">${e.bankNo!""}</td>
						<td style="text-align: right;">税务登记号:</td>
						<td style="text-align: left;" colspan="4">${e.taxNo!""}</td>
					</tr>
					<#assign natureMap = sysDic("account_nature") >
			        <#assign annualSalesMap = sysDic("account_annualSales") >
			        <#assign ltdMap = sysDic("account_ltd") >
			        <#assign productsMap = sysDic("account_products") >
					<#assign purchaseTypeMap = sysDic("account_purchaseType") >
					<#assign paymentTypeMap=sysDic("account_paymentType")>
					<#assign expressTypeMap = sysDic("express_type") >
					<#assign express =sysDic("order_express")>
					<#assign serviceFee =sysDic("isService_Fee")>
					<#assign isOtherFee =sysDic("isOther_Fee")>
					<#assign plineMap = sysDic("account_purchaseType") >
					<tr>
					 <td style="text-align: left;" colspan="7"><span style="color:red;">经营信息</span></td>   
					</tr>
					<tr>
						<td style="text-align: right;"><span style="color:red;">*</span>运输类型:</td>
						<td style="text-align: left;">
						   <#if e.expressType??&& e.expressType!="">${expressTypeMap[e.expressType]}</#if>
						</td>
						<td style="text-align: right;" ><span style="color:red;">*</span>会员折扣率:</td>
						<td style="text-align: left;" colspan="4">${e.discount!""}%</td>
					</tr>
					<#if e.expressType=="1" || e.expressType=="-1">
					<tr>
						<td style="text-align: right;"><span style="color:red;">*</span>物流公司:</td>
						<td style="text-align: left;">
						   <#if e.expressCode??&& e.expressCode!="">${express[e.expressCode]}</#if>
						</td>
						<td style="text-align: right;"><span style="color:red;">*</span>其他物流:</td>
						<td style="text-align: left;" colspan="4">${e.expressOther!""}</td>
					</tr>
					</#if>
					<tr>
						<td style="text-align: right;"><span style="color:red;">*</span>服务费:</td>
						<td style="text-align: left;" >
						   <#if e.isServiceFee??&& e.isServiceFee!="">${serviceFee[e.isServiceFee]}</#if>
						</td>
						<td style="text-align: right;"><span style="color:red;">*</span>其他费用:</td>
						<td style="text-align: left;" colspan="4">
						  <#if e.isOtherFee??&& e.isOtherFee!="">${isOtherFee[e.isOtherFee]}</#if>
						</td>
					</tr>
					<#if e.pline?? && e.pline!='' >
				   	 		<#assign plineChecks = e.pline?split(",")>
				        <#else>
				        	<#assign plineChecks =[]>
				        </#if>
					<tr>
						<td style="text-align: right;"><span style="color:red;">*</span>产品线:</td>
						<td style="text-align: left;" colspan="6">
						<#list plineChecks as c >${plineMap[c]},</#list>
						</td>
					</tr>
					<#if e.accType=="-1" || e.accType=="1">
					<tr>
						<td style="text-align: right;"><span style="color:red;">*</span>公司性质:</td>
						<td style="text-align: left;">
						   <#if e.nature??&& e.nature!="">${natureMap[e.nature]}</#if>
						</td>
						<td style="text-align: right;">年销售额:</td>
						<td style="text-align: left;" colspan="4">
						<#if e.annualSales??&& e.annualSales!="">${annualSalesMap[e.annualSales]}</#if>
						</td>
					</tr>
						<#if e.ltd?? && e.ltd!='' >
				   	 		<#assign ltdChecks = e.ltd?split(",")>
				        <#else>
				        	<#assign ltdChecks =[]>
				        </#if>
					<tr>
						<td style="text-align: right;"><span style="color:red;">*</span>经营性质:</td>
						<td style="text-align: left;"colspan="6">
						<#list ltdChecks as c >${ltdMap[c]},</#list>
						</td>
					</tr>
					<#if e.products?? && e.products!='' >
				        <#assign productsChecks = e.products?split(",")>
			        <#else>
				        <#assign productsChecks =[]>
			        </#if>
					<tr>
						<td style="text-align: right;"><span style="color:red;">*</span>经销产品:</td>
						<td style="text-align: left;" colspan="6">
						  <#list productsChecks as c >${productsMap[c]},</#list>
						</td>
					</tr>
					</#if>
					<tr>
						<td style="text-align: right;">销售人员:</td>
						<td style="text-align: left;">
						${e.username!""}
						</td>
						<td style="text-align: right;">微信:</td>
						<td style="text-align: left;"  colspan="4">${e.weixin!""}</td>
					</tr>
					<#--添加注册类型     -->
					<tr>
					 <td style="text-align: left;"  colspan="7"><span style="color:red;">注册信息</span></td>   
					</tr>
					<tr>
						<td style="text-align: right;width: 200px;">名片图片:</td>
						<td style="text-align: left;">
						  <#if e.mingpianImage?? && e.mingpianImage!='' > 
					            <a href="${e.mingpianImage!""}" target="_blank" id="mingpianImage"><img id="mingpianImage" style="width: 80px;height: 80px;" title="名片图片" alt="名片图片" src="${e.mingpianImage}" ></a>
						  </#if>
						</td>
						<td style="text-align: right;">门店图片:</td>
						<td style="text-align: left;" colspan="4">
						  <#if e.mendianImage?? && e.mendianImage!='' > 
					            <a href="${e.mendianImage!""}" target="_blank" id="mendianImage"><img id="mendianImage" style="width: 80px;height: 80px;" title="门店图片" alt="门店图片" src="${e.mendianImage}" ></a>
						  </#if>
						</td>
					</tr>
					</#if>
				</table>
	</div>
</div>
</form>

<script type="text/javascript">
$(function() {
	$( "#tabs" ).tabs({
		//event: "mouseover"
	});
	
});

</script>

</@html.htmlBase>
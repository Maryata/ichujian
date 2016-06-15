
<#--	/* *
 功能：支付宝页面跳转同步通知页面
 版本：3.2
 日期：2011-03-17
 说明：
 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 //***********页面功能说明***********
 该页面可在本机电脑测试
 可放入HTML等美化页面的代码、商户业务逻辑程序代码
 TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
 TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
 //********************************
 * */-->
<#import "/static/common_front.ftl" as html/>
<#import "/indexMenu.ftl" as menu/>
<@html.htmlBase title="支付宝页面跳转同步通知页面" jqueryValidator=false>
<@menu.menu selectMenu=""/>

		<input type="hidden" id="pay_result" name="pay_result" value="${pay_result!""}">
		<input type="hidden" id="flag" name="flag" value="${flag!""}">
		<div class="content">
			    <div class="ubox-info m-t100">
		<#if flag?? && flag=="1">
			    <p class="align-c"><img width="80" src="${basepath}/static/images/ico-pass.png"></p>
			    <p class="align-c f_18">恭喜，您的订单已支付成功！</p>
			    <p class="align-c m-t100">
			    	<a class="color-blue2 f_16" onclick="tourl('${basepath}/order/${Session.payInfo_orderId!""}.html')">返回我的订单</a>
			    </p>
		</#if>
		<#if flag?? && flag=="0">
			    <p class="align-c"><img width="80" src="${basepath}/static/images/ico-fail.png"></p>
			    <p class="align-c f_18">对不起，您的订单支付失败！</p>
			    <p class="align-c m-t100"><a class="color-blue2 f_16" onclick="tourl('${basepath}/order/topay?id=${Session.payInfo_orderId!""}')">重新支付</a></p>
		</#if>
		<#if flag?? && flag=="3">
			    <p class="align-c"></p>
			    <p class="align-c f_18">请不要重复操作！</p>
			    <p class="align-c m-t100">
			    	<a class="color-blue2 f_16" onclick="tourl('${basepath}/order/${Session.payInfo_orderId!""}.html')">返回我的订单</a>
			    </p>
		</#if>
			    </div>
			</div>

<script>
function tourl(url){
	window.location.href=url;
}
</script >
</@html.htmlBase>
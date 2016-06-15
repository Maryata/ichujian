<#import "/account/accountHtml.ftl" as accountHtml/>
<@accountHtml.html currentMenu="orders" title="我的订单" jqueryValidator=false>
  	<div class="center-box m-t20">    
    	<table class="cpx" border="0" cellspacing="0" cellpadding="0">  
            <thead class="title1"><tr><td colspan="7">查询订单</td></tr></thead>
            <thead class="title2">
            	<tr>                
                <td width="150">
                <form action="${basepath}/account/orders" method="get" onsubmit="return queryOrders()" >
                <input class="inputbox wi-110" name="serialId" type="text" 
                	<#if serialId?? && serialId!="">
                 		value="${serialId!""}" 
                 	<#else> 
                 		value="订单编号"
             		</#if> 
             		defval="订单编号" onfocus="if(!this.haswriting){this.style.color='#333'; this.value='';}" 
             	    onblur="if(!this.value){this.style.color='#999'; this.value=$(this).attr('defval'); this.haswriting=false;}else{this.haswriting=true};"
             	    style="color: #999;"/>
                </td>
                <td width="120">
			      <#assign tMap = {'w1':'最近一周','m1':'最近一个月','m3':'最近三个月','3mb':'三个月之前'} >
			      <select class="inputbox wi-110" name="orderDate">
			        <option value="">成交时间</option>
			        <#list tMap?keys as key>
			        	<option value="${key}" <#if orderDate?? && orderDate==key > selected</#if> >
			        	${tMap[key]}
			        	</option>
			        </#list>
			      </select>
		     	</td>
                <td></td>
                <td></td>
                <td></td>
                <td width="120">
			      	<#assign sMap =sysDic("order_status")>
					<select class="inputbox wi-110" name="status">
					<option value="">订单状态</option>
					<#list sMap?keys as key>
					   <#if key !="cancel">
						<option value="${key}"<#if status?? && status==key > selected </#if> >
						${sMap[key]}
						</option>
						</#if>
					</#list>
					</select>
			    </td>
                <td width="120"><button class="btn-blue-min" >查询</button></td>
                </form>
              	</tr>
              	<tr>                
                <td width="150">订单编号</td>
                <td width="120">成交时间</td>
                <td>总数量</td>
                <td>服务费</td>
                <td>总金额</td>
                <td width="120">订单状态</td>
                <td width="120">操作</td>
              	</tr>
            </thead>
            <tbody>
            <#assign oindex=0 >
		    <!--订单列表1-->
		    <#if pager.list?? && pager.pagerSize gt 0>
			    <#assign materialMap = sysDic("product_material") >
			  	<#assign oindex=1 >
			    <#list pager.list as item>
				    <#assign oindex=oindex+1 ><!--集合中元素个数--> 
				    <#if item.status?? && item.status=='temp'><!--过滤确认下单--> 
				    <#else>
		              <tr class="first">
		                <td>${item.serialId!""}</td>                
		                <td>${item.createdate?substring(0,16)}</td>
		                <td>${item.number!""}</td>
		                <td>￥${item.fee!""}</td>
		                <td class=" color-red">￥${item.amount!""}</td>
		                <td class=" color-green">
							<#if item.status??>
							   <#if item.status=='init' && item.paystatus=='y'>
						      	已支付
						      	<#else>
						      	${sMap[item.status]}
						      	</#if>
				        	</#if> 
						</td>
		                <td>
		                	<#if item.status=='init' && item.paystatus=='n'>
						      	<a href="${basepath}/order/${item.id!""}.html" class="btn-orange-min">订单详情</a>
						      	<a onclick="chosePayWay(${item.id!""})" class="btn-orange-min m-t10">立即支付</a>
					      	<#else>
					      		<a href="${basepath}/order/${item.id!""}.html" class="btn-orange-min">订单详情</a>
					      	</#if>
		                </td>
		              </tr>
          			</#if>
				</#list>
				</#if>
				</tbody>
	          	<tfoot><tr><td colspan="7" style="height:20px;"></td></tr></tfoot>
          </table>   
	</div>
	<#include "/pager.ftl"/>

<div class="addInfoId "  style="display:none;">
  <div class="zffs_box">
    <a onclick="javascript:setPayWay(3)"><img src="${basepath}/static/images/zffs_zxzf.jpg"><span>银联在线支付</span></a>
    <a class="m-t20" href="#" onclick="javascript:setPayWay(2)"><img src="${basepath}/static/images/zffs_kjzf.jpg"><span>银联快捷支付</span></a>
    <a class="m-t20" href="#" onclick="javascript:setPayWay(1)"><img src="${basepath}/static/images/zffs_zfb.jpg"><span>支付宝</span></a>
  </div>
</div>
<form id="toPayForm" action="${basepath}/order/toPay" method="post" >
	<!-- 订单id -->
	<input type="hidden" id="id" name="id" />
	<!-- 1:支付宝，2:快捷支付，3:银联在线支付 -->
	<input type="hidden" id="payWay" name="payWay" />
</form>
<script>

// 选择付款方式
function chosePayWay(id){
	$("#id").val(id);
	layer.open({
        type: 1,
        title:['请选择支付方式','color:#fff;background-color:#69a6c3;text-align:center;height:40px;font-size:14px;line-height: 40px;'],  
        area: ['600px', '400px'],
		shade: 0.5,//背景色		
        shadeClose: false, //点击遮罩关闭
		content:$(".addInfoId").html() ,
		scrollbar: false  
    });
}
// 设置付款方式，提交
function setPayWay(payWay){
	$("#payWay").val(payWay);
	$("#toPayForm").submit();
}

$(document).ready(function(){
  var itemLen = $("div[name=orderbox-item]").length;
  // 循环“集合的元素个数”次，给每个元素加上样式
  for(var i = 1; i <= ${oindex}; ++i) {
	  $("#orderBtn" + i).click(function() {
	  	  
		  var index = $(this).attr("index");
		  $("#orderBox" + index ).toggleClass( "order-box-off ", 100 );
		  $("#orderBox-bottom" + index ).fadeToggle(100);
	  });
  }
});

function queryOrders(){
// var orderId = $("input[name=orderId]");
var orderId = $("input[name=serialId]");
if(orderId.val()==orderId.attr('defval')){
	orderId.val("");
}
return true;
};
</script>
<script type="text/javascript">
  function confirmOrder(orderid,est){
  	$("#orderId").val(orderid);
	console.log("提交订单...");
	var msg = "<br>期望到货日期："+est;
	layer.confirm('是否确认? '+msg, {icon: 3, title:''}, function(index){
    	$("#submitOrderForm").submit();//确认提交
	    layer.close(index);
	});
}
</script>

</@accountHtml.html>
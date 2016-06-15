<#import "/static/common_front.ftl" as html/>
<#import "/indexMenu.ftl" as menu/>
<@html.htmlBase title="确认订单"  jqueryValidator=true>
<@menu.menu selectMenu=""/>
<script src="${basepath}/static/js/discount.js?v=${jversion}"></script>
<!--订单详情-->
<form id="submitOrderForm" action="${basepath}/order/submitOrder" method="post" theme="simple">

<!--内容-->
<div class="content">
  <!--左内容-->
  
  <!--右内容-->
  <div class="center-box">
    
    <!--地址信息-->
    <table class="cpx addborder" width="100%" border="0" cellspacing="0" cellpadding="0">
	    <input type="hidden" value="" id="payFlag" name="payFlag" />
	    <input type="hidden"  id="orderType" name="orderType" />
	    
      <thead class="title1">
        <tr>
          <td colspan="2">收货地址</td>
        </tr>
      </thead>
      <thead class="left">
        <tr>
          <td class="title2" width="100">收货人姓名</td>
          <td align="left">${address.name!""}</td>      
        </tr>
        <tr>
          <td class="title2">联系电话</td>
          <td>${address.mobile!""}</td>      
        </tr>
        <tr>
          <td class="title2">收货地址</td>
          <td>${address.pcadetail!""} ${address.address!""}</td>      
        </tr>
      </thead>
    </table>
    <!--收货日期-->
    <table class="cpx addborder m-t30" width="100%" border="0" cellspacing="0" cellpadding="0">
      <thead class="title1">
        <tr>
          <td colspan="2">收货信息</td>
        </tr>
      </thead>
      <thead class="left">
        <tr>
          <td class="title2" width="100">物流公司</td>
          <td align="left">${e.expressCompanyName!""}</td>      
        </tr>
        
        <tr>
          <td class="title2">预计收货日期</td>
          <td>${e.expectSignDate!""}</td>      
        </tr>
        <tr>
          <td class="title2">物流费用</td>
           <#if e.isOtherFee=="1">
	        <input type="hidden" name="isOtherFee" value="${e.isOtherFee!""}"/>
	       </#if>
          <td>
              <#if e.expressType=="0">
	           到付(以物流公司收费标准为准)
	          </#if>
	          <#if e.expressType=="1">
	           <input type="hidden" value="${e.expressFee!""}" id="expressFee" name="expressFee" />
               <input type="hidden" name="expressType" value="${e.expressType!""}"/>
	            包邮
	          </#if>
          </td>      
        </tr>
      </thead>
    </table>
    <!--收费信息-->
    <table class="cpx addborder m-t30" width="100%" border="0" cellspacing="0" cellpadding="0">
      <thead class="title1">
        <tr>
          <td colspan="2">收费信息</td>
        </tr>
      </thead>
      <thead class="left">
        <tr>
          <td class="title2" width="100">总计数量</td>
          <td align="left">${e.number!""}</td>      
        </tr>
        
        <tr>
          <td class="title2">总计金额</td>
          <td>￥${e.ptotal!""}</td>      
        </tr>
        <#-- <#if e.rebate?? && e.rebate!=0 >
        <tr>
          <td class="title2">折扣率</td>
          <td>${e.rebate!""}%</td>      
        </tr>
        <tr>
          <td class="title2">折后金额</td>
          <td>￥${e.rebateAmount!""}</td>  
        </tr>
        </#if>
        -->
        <#if e.isServiceFee?? && e.isServiceFee=="1">
        <tr>
          <td class="title2">服务费</td>
          <td>￥${e.fee!""}</td> 
          <!-- 服务费=折后金额*0.1 -->     
        </tr>
        </#if>
        <tr>
          <td class="title2">实付总额</td>
          <td><span class="money order_amount color-red">￥${e.amount!""}</span>
				<#--<#if e.discountFee!="0.00">
				<span class="color-orange  m-t10">(本次折扣优惠为您省去${e.discountFee!""}元)</span>
				</#if>-->
          </td>      
        </tr>
      </thead>
    </table>
    <!--功能操作-->
    
    <div class="function-btns">     
      <a class="btn-red-max tanchu2_btn f-r m-t10 payNowBtn" id="tc_zffs" >立即支付</a>
    </div>
     <#if e.accType=="1">
    <div class="function-btns">
      <a class="btn-orange-max tanchu2_btn f-r m-t20 payNoBtn" onclick="javascript:setPayFlag(0)">线下支付</a>
    </div>
    </#if>
  </div>
</div>
<div class="addInfoId "  style="display:none;">
  <div class="zffs_box">
  	<a href="javascript:void();" onclick="javascript:setPayFlag(1)"><img src="${basepath}/static/images/zffs_zfb.jpg"><span>支付宝</span></a>
    <a class="m-t20" onclick="javascript:setPayFlag(3)"><img src="${basepath}/static/images/zffs_zxzf.jpg"><span>银联在线支付</span></a>
    <a class="m-t20" href="javascript:void();" onclick="javascript:setPayFlag(2)"><img src="${basepath}/static/images/zffs_kjzf.jpg"><span>银联快捷支付</span></a>
  </div>
</div>
</form>
<script>
$('.payNowBtn').on('click', function(){
	layer.open({
        type: 1,
        title:['请选择支付方式','color:#fff;background-color:#69a6c3;text-align:center;height:40px;font-size:14px;line-height: 40px;'],  
        area: ['600px', '400px'],
		shade: 0.5,//背景色		
        shadeClose: false, //点击遮罩关闭
		content:$(".addInfoId").html() ,
		scrollbar: false  
    });
});
function setPayFlag(payFlag){
	//标记线下、线上支付;
	$("#orderType").val(payFlag==0 ? 0 : 1);
	$("#payFlag").val(payFlag);// 标记改为1-支付（0表示不支付只生成订单）
	$("#submitOrderForm").submit();//确认提交
}
</script>
</@html.htmlBase>
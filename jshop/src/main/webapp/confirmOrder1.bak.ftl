<#import "/static/common_front.ftl" as html/>
<#import "/indexMenu.ftl" as menu/>
<@html.htmlBase title="确认订单"  jqueryValidator=true>

	<@menu.menu selectMenu=""/>
	<!--内容-->
	<div class="content">
	
	  <!-- left menu start -->
	  <#include "/index_left_menu.ftl">
	  <!-- left menu end -->
	  
	  <!-- right center start -->
	  <!--右内容-->
<form id="submitOrderForm" action="${basepath}/order/submitOrder" method="post" theme="simple">
<div class="right-box">
    <!--面包屑-->
    <div class="crumbs"><a >1.查看购物车</a><a class="active">2.确认订单</a></div>
    
    <!--地址信息-->
    <div class="add-box">
      <span class="title">收货地址</span>
      <a class="edit tanchu_btn checkAddressBtn">更改收货地址</a>
      <span class="add">
      	<#assign addressId ='' >
      	<#assign addressType ='address' >
        <div id="order_address">
        <#if address?? && address.name?? && address.phone??>   
        <p><b>收货人姓名：</b>${address.name!""}</p>
        <p><b>联系电话：</b>${address.phone!""}</p>
        <p><b>收货地址：</b>${address.pcadetail!""} ${address.address!""}</p>
        <#assign addressId =address.id!"" >
        <#else>
      	暂时还没有收获地址, 请点击右侧的 修改收货地址 编辑.
        </#if>        
        </div>
        <input type="hidden" value="${addressId!""}" name="selectAddressID" />
      </span>
    </div>
    <!--收货日期-->
    <div class="add-box">
      <span class="title">期望到货日期</span>
      <span class="add">      
      <input class="Wdate inputbox" style="width:100px" type="text" value="${e.expectSignDate!""}" name="expectSignDate" onfocus="WdatePicker({isShowWeek:true,minDate:'${minDate}',maxDate:'2999-12-12'})"/> &nbsp;&nbsp;(含3天预计物流日期)
      </span>
    </div>
    <!--表格信息-->
    <div class="order-title"><span>商品信息</span></div>
    <#if e?? && e.orders?? && e.ordermainList?? >
    <#assign materialMap = sysDic("product_material") >
    <#list e.ordermainList as m>
    <table class="list-info" border="0" cellspacing="0" cellpadding="0">
      <thead>
      <tr>
        <td colspan="2" class="title-order">适配品牌：${m.mainCatalogName!""}</td>
        <td width="110">适配型号</td>
        <td width="80">材质</td>
        <td width="100">单价(元)</td>
        <td width="100">数量(个)</td>
        <td width="160">小计(元)</td>
      </tr>
      </thead>
      <tbody>
      <#list e.orders as item>
      <#if item.mainCatalogCode==m.mainCatalogCode>
      <tr>
        <td class="img-box"><#if item.picture?? && item.picture!=""><img src="${systemSetting().imageRootPath}${item.picture!""}" height="40" title="${item.productName!""}"></#if></td>
        <td><span class="img-title-order" title="${item.productName!""}">${item.productName!""}</span></td>
        <td><span class="model" title="${item.childrenCatalogName!""}" >${item.childrenCatalogName!""}</span></td>
        <td>${item.material!""}</td>
        <td class="price">￥${item.price!"0"}</td>
        <td class="color39f">${item.productNumber!"0"}</td>
        <td class="price">￥${item.total0!"0"}</td>
      </tr>
      </#if>
      </#list>
      </tbody>
      <tfoot>
        <tr>
          <td colspan="7">
            <span class="money-order" title="合计：${m.subTotal!"0.00"}">合计：${m.subTotal!"0.00"}</span>
            <span class="total-order" title="总数：${m.number!"0"}">总数：${m.number!"0"}</span>
          </td>
        </tr>
      </tfoot>
    </table>
    </#list>
    </#if>
    
    <!--订单备注-->
    <div class="remarks">
    	<input type="text" value="${e.otherRequirement!"此处可以备注您的需求"}" defval="此处可以备注您的需求" name="otherRequirement" maxlength="100" onfocus="if(!this.haswriting){this.style.color='#333'; this.value='';}" onblur="if(!this.value){this.style.color='#999'; this.value=$(this).attr('defval'); this.haswriting=false;}else{this.haswriting=true};" style="color: #999;"/>    	
    	<span class="order-tips">不含运费，运费信息请联系您的销售人员！</span>
    </div>
    <hr>
    
    <!--功能操作-->
    <div class="function-box">
     <a class="enter-btn" onclick="javascript:submitOrder()" onselectstart="return false;">确认下单</a>
     <a class="back-btn" onclick="javascript:returnCart()">返回修改</a>
     <a class="print-btn" href="${basepath}/order/orderExport?id=${e.id!""}" target="_blank">导出订单</a>
     <span class="type-info"><b>结算方式</b><br>${paymentType!""}</span>
     <span class="mtinfo">
       
       <#if e.rebate??  >
       <span class="money" > 
         <p title="实付：￥${e.amount!""}" >实付：￥${e.amount!""}</p>
         <p title="折扣商品不包含激活码">折扣百分比：${e.rebate!""} </p>
       </span>
       <#else>
       <span class="money" title="实付：￥${e.amount!""}">实付：￥${e.amount!""} </span>
       </#if>
       
       <span class="total" title="总数：${e.number!""}">总数：${e.number!""}</span>
     </span>
          
     <input type="hidden" value="${e.id!""}" id="orderId" name="orderId" />
    </div>
</div>
</form>

<form action="${basepath}/cart/cart.html" method="POST" id="returnCart">
	<input type="hidden" name="cartType" value="order">
	<input type="hidden" name="orderId" value="${e.id!""}">
</form>

</div>

<!--弹出框-->
  <div id="edit-address" style="display:none;">
    <!--<div class="tanchu-title">修改收货地址<span title="关闭" class="tanchu_close"></span></div>-->
    <div class="ubox-info">
    <form role="addressform" id="addressform" class="add-line-box">
      <li><span class="inputtitle">收货人姓名</span>
         <input class="inputbox" type="text" name="name"  id="name" data-rule="收货人姓名:required;length[2~20];name;" placeholder="请输入收货人姓名" maxlength="20" size="20">
      </li>
      <li><span class="inputtitle">联系电话</span>
        <input class="inputbox" type="text" name="mobile"  type="text" id="mobile" data-rule="联系电话:required;length[7~15];" placeholder="请输入收货人联系电话" maxlength="15"/>
      </li>
      <li><span class="inputtitle">邮政编码</span>
        <input class="inputbox" type="text"  name="zip"  type="text" id="zip" data-rule="邮编:required;digits:true;length[6];zip;" placeholder="请输入收货人邮编" maxlength="6"/>
      </li>
      <li>		
        <span class="inputtitle">所在地区</span>
        <div id="areaItem"></div>
        <span class="inputtips n-msg" id="areaItem-msg"></span>
      </li>
      <li><span class="inputtitle">详细地址</span>
      	<input type="text" name="address"  type="text" class="inputbox widthlong" id="address" data-rule="地址:required;length[0~70];address;" placeholder="请输入收货人地址" maxlength="70" size="70"/>
      </li>
      <li>
      	<div style="display:none;">
      	<input type="text" id="id" name="id" />
      	<input type="text" id="phone" name="phone" />
      	<input type="text" id="province" name="province" />
		<input type="text" id="city" name="city" />
		<input type="text" id="area" name="area" />
		<input type="text" id="town" name="town" />
		<input type="text" id="pcadetail" name="pcadetail" />
		</div>
		<!-- m-l500-->
        <button class="sure m-l90 " id="submit_saveButton">保 存</button>
      </li>
      </form>
      
      <li><hr></li>
      <li>
        <!--表格信息-->
        <div id="pageview"></div>
    </div>
  </div>

<script type="text/javascript" src="${basepath}/resource/laytpl-v1.1/laytpl.js"></script>
<script id="tpl_addresss" type="text/html">
{{# 
var list = d.addressList; 
if(list){
}}
<table class="add-info" border="0" cellspacing="0" cellpadding="0">
      <thead>
      <tr>
      	<td width="85">选择地址</td>
        <td width="75">收货人</td>
        <td width="300">收货地址</td>
        <td width="75">邮编</td>
        <td width="100">手机/座机</td>
        <td width="125">操作</td>
      </tr>
      </thead>
      <tbody>
{{# for(var i = 0, len = list.length; i < len; i++){ }}
    <tr id="address-{{ list[i].id }}" data-id="{{ list[i].id }}" data-name="{{ list[i].name }}" data-zip="{{ list[i].zip }}" data-pcadetail="{{ list[i].pcadetail }}" data-address="{{ list[i].address }}" data-phone="{{ list[i].phone }}"  data-mobile="{{ list[i].mobile }}"
    	 data-province="{{ list[i].province }}"  data-city="{{ list[i].city }}"  data-area="{{ list[i].area }}"  data-town="{{ list[i].town }}" >        
        <td><input name="setDefaultRadio" title="选择当前地址" type="radio" onclick="setDefaultRadio({{ list[i].id }})" value="{{ list[i].id }}" {{# if(list[i].isdefault && list[i].isdefault=="y") { }} checked {{# } }} ></td>
        <td> <span class="name-more" title="{{ list[i].name }}"> {{ list[i].name }}</span></td>
        <td> <span class="add-more" title="{{ list[i].pcadetail }}&nbsp;{{ list[i].address }}">{{ list[i].pcadetail }}&nbsp;{{ list[i].address }}</span> </td>
        <td >{{ list[i].zip }}</td>
        <td>{{ list[i].mobile }}</td>
        <td>
        <a class="address.edit" onclick="addressEdit({{ list[i].id }})">修改</a>|
		<a class="address.delete" onclick="addressDelete({{ list[i].id }})">删除</a>
        </td>
      </tr>
{{# } }}
{{# } }}
</tbody>
    </table>
</script>
<script type="text/javascript" src="${staticpath}/My97DatePicker/WdatePicker.js"></script> 
<script src="${basepath}/static/js/area.js?v=${jversion}"></script>
<script src="${basepath}/static/js/order.address.js?v=${jversion}"></script>

<!--弹出框end--> 

<script type="text/javascript">

function checkAccount(){
	var flag = true;
	$.ajax({
		url : "${basepath}/account/checkAccount",
		async : false,
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data=="0"){
			   flag = false;
			   layer.msg('个人资料不全,请补全资料! <a href="${basepath}/account/account" target="_blank">补全资料</a>', {icon: 6});
			}else if(data=="-1"){
			   flag = false;
			   layer.msg('请登录! <a href="${basepath}/account/login" target="_blank">登录</a>', {icon: 6});
			}
		}
	});
	return flag;
}

function submitOrder(obj){
	console.log("提交订单...");
	if(!checkAccount()){
		return false;
	}
	var estime = $("input[name=expectSignDate]");
	if( estime.hasClass("WdateFmtErr")){
		layer.msg('请选择正确的望到货日期', {icon: 6});
		return false;
	}
	if( estime.val()==''){
		layer.msg('请选择期望到货日期', {icon: 6});
		return false;
	}
	if($("input[name=selectAddressID]").val()==''){
		layer.msg('请选择收货地址', {icon: 6});
		return false;
	}
	
	loadAddress(function(){
    	setDefaultRadio($("input[name=selectAddressID]").val());
	});
	addressModel = "o";
	$("#submit_saveButton").addClass("m-l500");
	$("#submit_saveButton").html("确 认");
	$("#submit_msg").show();
    layer.open({
    	title: '确认收货地址',
	    type: 1,
	    skin: 'layui-layer-rim', //加上边框
	    area: ['800px',"540px"],
	    content: $('#edit-address')
	});
	layer.msg("请再次确认收货地址.",{time:1500});
	return false;
}

checkAccount();

function returnCart(){
$("#returnCart").submit();
}

</script>
</@html.htmlBase>
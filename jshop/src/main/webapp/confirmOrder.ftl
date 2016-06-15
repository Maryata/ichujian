<#import "/static/common_front.ftl" as html/>
<#import "/indexMenu.ftl" as menu/>
<@html.htmlBase title="确认订单"  jqueryValidator=true>
	<@menu.menu selectMenu=""/>
<script src="${basepath}/static/js/discount.js?v=${jversion}"></script>
	<!--内容-->
	<div class="content">
	
	  <!-- left menu start -->
	  <!-- left menu end -->
	  
	  <!-- right center start -->
	  <!--右内容-->
<form id="submitOrderForm" action="${basepath}/order/confirmOrderInfo" method="post" onsubmit="return checkSubmit()" theme="simple">
<div class="center-box">
    <!--面包屑-->
    <#--
    <div class="crumbs"><a >1.查看购物车</a><a class="active">2.确认订单</a></div>
    -->
    <!--地址信息-->
 <table class="cpx addborder" width="100%" border="0" cellspacing="0" cellpadding="0">
      <thead class="title1">
        <tr>
          <td colspan="2">收货地址<a id="checkAddressBtn" class="edit m-t10 tanchu_btn checkAddressBtn">地址管理</a></td>
        </tr>
      </thead>
      <#assign addressId ='' >
      <#assign addressType ='address' >
      <thead class="left" id="order_address">
       <#if address??>  
        <tr>
          <td class="title2" width="100">收货人姓名</td>
          <td align="left" id="address_name">${address.name!""}</td>      
        </tr>
        <tr>
          <td class="title2">联系电话</td>
          <td id="address_phone">${address.mobile!""}</td>      
        </tr>
        <tr>
          <td class="title2">收货地址</td>
          <td id="address_address">${address.pcadetail!""} ${address.address!""}</td>      
        </tr>
        <#assign addressId =address.id!"" >
        <#else>
        <tr>
           <td colspan="2">请点击右侧 修改收货地址.</td>
        </tr>
       </#if>
      </thead>
      <input type="hidden" value="${addressId!""}" name="selectAddressID" />
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
          <td align="left"> 
          <#if e.expressType=="0" || ( e.accType!="1"  ) || !(e.expressCode??)>
          <#assign express =sysDic("order_express")>
	      <select class="dd-ml" id="address_express" name="expressCode" onchange="getExpressCompany()">
	        <option value="">请选择物流公司</option>
	        <#list express?keys as key>
	        <option value="${key}" <#if e.expressCode?? && e.expressCode==key>selected</#if> > ${express[key]}</option>
	        </#list>
	      </select>
	      <input type="text" value="${e.expressCompanyName!""}" style="display:none;border-bottom: 1px #999 solid;" id="expressCompanyName" name="expressCompanyName" title="请输入物流公司名称" placeholder="请输入物流公司名称"/>
	      <#else>
	       ${e.expressCompanyName!""}
	      </#if>
       </td>      
        </tr>
        <tr>
          <td class="title2">预计发货日期</td>
          <td>${e.expectSignDate!""}</td>      
        </tr>
        <input type="hidden" name="isOtherFee" value="${e.isOtherFee!""}"/>
        <tr>
          <td class="title2" width="100">物流费用</td>
          <td align="left">
	          <span id="sendTime">
	          <#if e.expressType?? && e.expressType=="1">包邮
	          <#else> 到付(以物流公司收费标准为准) </#if>	
	          </span>
          </td>      
        </tr>
        <#if e.isServiceFee?? && e.isServiceFee=="1">
        <tr>
          <td class="title2" width="100">服务费</td>
          <td align="left">
	        ￥${e.fee!""}
          </td>      
        </tr>
        </#if>
      </thead>
        
    </table>
         <div class="remarks">
    	   <input type="text" value="${e.otherRequirement!"此处可以备注您的需求"}"
    	    defval="此处可以备注您的需求" name="otherRequirement" maxlength="100"
    	     onfocus="if(!this.haswriting){this.style.color='#333'; this.value='';}"
    	      onblur="if(!this.value){this.style.color='#999'; this.value=$(this).attr('defval'); this.haswriting=false;}else{this.haswriting=true};"
    	       style="color: #999;" />    	
        </div>
        
        <div class="none"><input type="text" value=""/></div>
        
        <div class="cpx-box">
	    <#if e?? && e.orderdetail?? && e.ordermainList?? >
	    <#list e.ordermainList as m>
        <table class="cpx" border="0" cellspacing="0" cellpadding="0">
            <thead class="title1">
              <tr>
                <td colspan="7">${m.mainCatalogName!""}</td>
              </tr>
            </thead>
            <thead class="title2">
              <tr>
                <td>名称</td>
                <td>单价(元)</td>
                <td>数量(个)</td>
                <td>小计(元)</td>
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
              	<td><#if item.picture?? && item.picture!=""><img src="${systemSetting().imageRootPath}${item.picture!""}" height="40" title="${item.productName!""}"></#if>
                <span class="title33" title="${item.productName!""}">${item.productName!""}</span></td>
                <td>${item.price!"0"}</td>
                <td>${item.number!"0"}</td>
                <td>${item.total0!"0"}</td>
              </tr>
           
            <#assign lastMainName="${item.mainCatalogName!''}" >
            </#if>
            </#list>
            </tbody>
            <tfoot>
              <tr>
                <td colspan="7"><span title="实付：${m.subTotal!"0.00"}" class="money">合计金额：￥${m.subTotal!"0.00"}</span> <span title="总数：${m.number!"0"}" class="total">合计数量：${m.number!"0"}</span></td>
              </tr>
            </tfoot>
          </table>
         </#list>
        </#if>
       </div>
    
    <!--订单备注-->
    <input type="hidden" value="${e.amount!""}" id="base_amount"/>
   <#-- <input type="hidden" value="0" id="orderType" name="orderType" />-->
   <#-- <input type="hidden" value="${e.phone!""}" id="phone" name="phone" />-->
    <!--功能操作-->
    <div class="function-box">
    	<span class="type-info"><a onclick="javascript:returnCart()">返回修改</a></span>
    	<#--     
     	<span class="type-info" >结算方式：${paymentType!""}</span>
     	-->
	    <span class="mtinfo">
	       <#--<#if e.rebate?? && e.rebate!=0 >
	       <span id="zhehou" class="money m-r20" title="折后总额：${e.rebateAmount!""}" >折后金额：￥${e.rebateAmount!""}</span>
	       <span class="agio" style="width:120px;">折扣率：${e.rebate!""}%</span>   
	       </#if>-->
	       <span class="money">总计金额：￥${e.ptotal!""}</span>     
	       <span title="总数：${e.number!""}" class="total">总计数量：${e.number!""}</span>
	    </span>     
    </div>
    <div class="function-btns">
      	<a class="btn-orange-max tanchu2_btn f-r" onclick="javascript:submitOrder()" onselectstart="return false;">确认订单</a>
      	<br>
		<#--<#if e.discountFee!="0.00">
		<span class="color-orange f-r m-t10">本次折扣优惠为您省去${e.discountFee!""}元</span>
		</#if>-->
    </div>
    <input type='hidden' id='payFlag' name='payFlag' value='0' />   
    <input type='hidden' id='account' name='account' value='${e.account!""}' />    
    <input type='hidden' id='trueName' name='trueName' value='${e.trueName!""}' />             
    
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
      <li id="tables" style="display: none1;">
        <!--表格信息-->
        <div id="pageview"></div>
      </li>
      <li id="adds" style="display: none1;">
        <button type="button"  class="sure m-l300"  onClick="addEven()">添加新地址</button>
      </li> 
      <li id="hr" style="display: none1;"><hr></li>
    <form role="addressform" id="addressform" class="add-line-box" style="display: none1;">
      <li><span class="inputtitle">收货人姓名<span style="color:red;" title="必填项">*</span></span>
         <input class="inputbox" type="text" name="name"  id="name" data-rule="收货人姓名:required;length[2~20];name;" placeholder="请输入收货人姓名" maxlength="20" size="20">
      </li>
      <li><span class="inputtitle">联系电话<span style="color:red;" title="必填项">*</span></span>
        <input class="inputbox" type="text" name="mobile"  type="text" id="mobile" data-rule="手机号:required;mobile;length[11];" data-rule-mobile="[/^1([3]\d|4[57]|5[^3]|8\d)\d{8}$/, '请检查手机号格式']"  placeholder="方便你接收订单信息" maxlength="15"/>
      </li>
      <li><span class="inputtitle">邮箱&nbsp;&nbsp;</span>
        <#--<input class="inputbox" type="text"  name="zip"  type="text" id="zip" data-rule="邮箱:email;" placeholder="方便你接收订单信息" maxlength="50"/>-->
        <input class="inputbox" type="text"   name="zip"  type="text" id="zip" data-rule="收货人邮箱:email;" data-rule-email="[/^(([^<>()[\]\\.,;:\s@]+(\.[^<>()[\]\\.,;:\s@]+)*)|(.+))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,'请输入正确的邮箱']"; placeholder="方便您接收订单信息" maxlength="80"/>
      </li>
      <li>		
        <span class="inputtitle">所在地区<span style="color:red;" title="必填项">*</span></span>
        <div id="areaItem" style="margin-left:105px;"></div>
        <span class="inputtips n-msg" id="areaItem-msg" ></span>
      </li>
      <li><span class="inputtitle">详细地址<span style="color:red;" title="必填项">*</span></span>
      	<input type="text" name="address"  type="text" class="inputbox widthlong" id="address" data-rule="地址:required;length[3~70];address;" placeholder="请输入收货人地址" maxlength="70" size="70"/>
      </li>
      <li>
      	<div style="display:none;">
      	<input type="text" id="id" name="id" />
      	<input type="text" id="phone" name="phone" />
      	<input type="text" id="province" name="province" />
		<input type="text" id="city" name="city" />
		<input type="text" id="zip" name="zip" />
		<input type="text" id="area" name="area" />
		<input type="text" id="town" name="town" />
		<input type="text" id="pcadetail" name="pcadetail" />
		<input type="text" name="provinceAddress" id="provinceAddress" />
        <input type="text" name="cityAddress" id="cityAddress" />
        <input type="text" name="areaAddress" id="areaAddress" />
        <input type="text" name="townAddress" id="townAddress" />
		</div>
		<!-- m-l500 id="submit_saveButton"-->
        <button type="submit" id="submit_saveButton" class="sure m-l300" >完 成</button>
      </li>
      </form>
      
    </div>
  </div>

<script type="text/javascript" src="${basepath}/resource/laytpl-v1.1/laytpl.js"></script>
<script id="tpl_addresss" type="text/html">
{{# 
var list = d.addressList; 
if(list){
}}
<table class="add-info" id="add-info" border="0" cellspacing="0" cellpadding="0">
      <thead>
      <tr>
      	<td width="30">选择</td>
        <td width="60">收货人</td>
        <td width="350">收货地址</td>
        <td width="120">邮箱</td>
        <td width="95">手机号</td>
        <td width="75">操作</td>
      </tr>
      </thead>
      <tbody>
{{# for(var i = 0, len = list.length; i < len; i++){ }}
    <tr id="address-{{ list[i].id }}" data-id="{{ list[i].id }}" data-name="{{ list[i].name }}" data-zip="{{ list[i].zip }}" data-pcadetail="{{ list[i].pcadetail }}" data-address="{{ list[i].address }}" data-phone="{{ list[i].phone }}"  data-mobile="{{ list[i].mobile }}"
    	 data-province="{{ list[i].province }}"  data-city="{{ list[i].city }}"  data-area="{{ list[i].area }}"  data-town="{{ list[i].town }}" data-provinceAddress="{{list[i].provinceAddress}}" data-cityAddress="{{list[i].cityAddress}}" data-areaAddress="{{list[i].areaAddress}}" data-townAddress="{{list[i].townAddress}}">        
        <td><input name="setDefaultRadio" title="选择当前地址" type="radio" onclick="setDefaultRadio({{ list[i].id }})" value="{{ list[i].id }}" {{# if(list[i].isdefault && list[i].isdefault=="y") { }} checked {{# } }} ></td>
        <td> <span class="name-more" title="{{ list[i].name }}"> {{ list[i].name }}</span></td>
        <td> <span class="add-more" title="{{ list[i].pcadetail }}&nbsp;{{ list[i].address }}">{{ list[i].pcadetail }}&nbsp;{{ list[i].address }}</span> </td>
        <td> <span class="mail-more" title="{{ list[i].zip }}">{{ list[i].zip }}</span></td>
        <td> <span class="tel-more" title="{{ list[i].mobile }}">{{ list[i].mobile }}</span></td>
        <td><span class="does-more">
        <a class="address.edit" onclick="addressEdit({{ list[i].id }})">修改</a>
		<a class="address.delete" onclick="addressDelete({{ list[i].id }})">删除</a>
        </span></td>
      </tr>
{{# } }}
{{# } }}
</tbody>
    </table>
</script>
<script type="text/javascript" src="${staticpath}/My97DatePicker/WdatePicker.js"></script> 
<script src="${basepath}/static/js/area.js?v=${jversion}"></script>
<script src="${basepath}/static/js/order.address.js?v=${jversion}"></script>
<script src="${basepath}/static/js/comfirmOrder.js"></script>

<!--弹出框end--> 
<script type="text/javascript">
 $(function(){
    if($("#address_express").val()=="OTHER"){
       $("#expressCompanyName").show();
    }
  });
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

function checkSubmit(){
	var expressCode =$("select[name=expressCode]");
	if(expressCode.val()==""){
	    layer.msg('请选择物流公司');
		return false;
	}
	if($("input[name=selectAddressID]").val()==''){
		layer.confirm('没有收获地址,是否添加收获地址？', {
		    btn: ['添加','暂不添加']
		}, function(){
		    $(".checkAddressBtn").click();
		}, function(){		    
		});		
		return false;
	}
	if($("#expressCompanyName").val()==""){
	    layer.msg("请输入物流公司名称");
	    $('#expressCompanyName').focus();
	    return false;
	   }
	var otherRequirement = $("input[name=otherRequirement]");
	if(otherRequirement.val()==otherRequirement.attr('defval')){
		otherRequirement.val("无");
	}
	return true;
}
 

function submitOrder(title){
	if(!checkAccount()){
		return false;
	}
	if(!checkSubmit()){
		return false;
	}
	$("#submitOrderForm").submit();//确认提交
	console.log("提交订单..=========.");
	return true;
}

checkAccount();

function returnCart(){
$("#returnCart").submit();
}


//赋值
function addEven(){
$("#addressform").show();
$("#hr").show();
$("#name").val($("#trueName").val());
$("#mobile").val($("#account").val());
}
</script>
</@html.htmlBase>
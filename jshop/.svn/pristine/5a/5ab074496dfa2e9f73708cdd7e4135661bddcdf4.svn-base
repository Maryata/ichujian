<#import "/static/common_front.ftl" as html/>
<#import "/indexMenu.ftl" as menu/>
<@html.htmlBase title="购物车" >
	<@menu.menu selectMenu=""/>
	<!--内容-->
	<div class="content">
	
	  <!-- left menu start -->
	  <!-- left menu end -->
	  
	  <!-- right center start -->
	  <!--右内容-->
	  <form action="#" method="post">
	  <div class="center-box">
	    <!--面包屑-->
	    <#--
	    <div class="crumbs"><a class="active">1.查看购物车</a><a >2.确认订单</a></div>
	    -->
	    <#assign accType ="0" > 
	    <#if currentAccount()??>  <#assign accType =currentAccount().accType!"0" > </#if>
	    
	    
	    <div class="cpx-box">
	    <#assign materialMap = sysDic("product_material") >
	    <#if productList?? && productList?size gt 0>
	    <!--表格信息-->
	    <#assign lastItem={"pline":""} >
	    <#assign plineMap = sysDic("account_purchaseType") >
	    
	    <#list productList as itemRoot>
	    <#if lastItem.pline!= itemRoot.pline >	    
	    <table class="cpx" width="100%" border="0" cellspacing="0" id="tb_${itemRoot_index+1}" cellpadding="0">
	      <thead class="title1">
	        <tr>
	          <td colspan="9"><input type="checkbox" name="chkTable" onclick="checkAll($(this))"><span class="m-l10">${plineMap[itemRoot.pline]}</span></td>
	        </tr>
	      </thead>
	      <thead class="title2">
	      <tr>
	        <td width="20"></td>
	        <td>名称</td>
            <td>单价(元)</td>
            <td width="200">数量(个)</td>
            <td>小计(元)</td>
            <td width="100">操作</td>
	      </tr>
	      </thead>
	      
	      <tbody>
	      <#assign pindex=1 > <#assign lastMainName="" >
	      <#list productList as item>
	      <#if item.pline == itemRoot.pline >
	      
	      <#assign trCss="">
	      <#if pindex==1><#assign trCss="first" > 
	      <#else>
	        <#if lastMainName!=item.mainCatalogName>
	          <#assign trCss="line">
	        </#if>
	      </#if>
	      
	      <#assign pindex=pindex+1 >
	      <tr data-id="${item.id!""}" class="${trCss!""}">
	        <td><input type="checkbox" name="chk" onclick="rowCheck($(this))"></td>
	        <td>
	        	<#if item.picture?? && item.picture!="">
	        		<img src="${systemSetting().imageRootPath}${item.picture!""}" title="${item.productName!""}">
        		</#if>
                <span class="title3" title="${item.name!""}" >${item.title!""}</span>
            </td>
	        <#--<td><span class="model" title="${item.mainCatalogName!""}" >${item.mainCatalogName!""}</span></td>-->
	        <#--<td><#if item.picture??><img src="${systemSetting().imageRootPath}${item.picture!""}" height="40" title="${item.name!""}" ></#if><span class="title3" title="${item.name!""}">${item.name!""}</span></td>
	        <td><span class="model" title="${item.childrenCatalogName!""}" >${item.childrenCatalogName!""}</span></td>
	        <td>${item.material!""}</td>-->
	        <td class="bc-f9">￥<span name="price">${(accType=="1")?string( item.nowPrice,item.price )}</span></td>
	        <td>
	        	<span class="tycss">
	        	<button type="button" class="fuhao" onclick="return changebuyNum_cart($(this),false)">-</button><input type="text" data-id="${item.id!""}" class="nub" value="${item.buyCount!"0"}" minNum="${item.minNum!"100"}" maxlength="10" name="buyNum_cart"/><button type="button" class="fuhao" onclick="return changebuyNum_cart($(this),true)">+</button>
	        	</span>
        	</td>
	        <td class="bc-f9">￥<span name="rowPrice">${item.total0!""}</span></td>
	        <td><a onclick="javascript:deleteFromCart('${item.id!""}')" class="del-btn">删除</a></td>
	      </tr> 
	      <#assign lastMainName="${item.mainCatalogName!''}" >     
	      </#if>
	      </#list>
	      </tbody>
	      <tfoot>
	        <tr>
	          <td colspan="9">
	            <span class="money" >合计金额：￥<span name="totalPrice">0.00</span></span>
	            <span class="total" >合计数量：<span name="totalNum">0</span></span>
	          </td>
	        </tr>
	      </tfoot>
	    </table>
	    </#if>
	    <#assign currentAcc=currentAccount()>
	    <#assign lastItem=itemRoot >
	    </#list>
	    </div>
	    <!--功能操作-->
	   	<div class="function-box">
  	   		<span class="mtinfo">
  	   		  <#-- <#if currentAcc.accType=="1" && currentAcc.discount?? && currentAcc.discount!='0' >
		    	<span id="zhehou" class="money m-r20" >折后总额：￥<span id='cart_zhehou'>${ cartInfo.amount!"" }</span></span>
		       	<span class="agio">折扣率：<span id='cart_rate'>${currentAcc.discount}</span>%</span>   
		       	</#if>-->
		       	<span class="money">总计金额：￥<span id="totalCost">${cartInfo.amount!"0"}</span></span>     
		       	<span class="total" >总计数量：<span id="allNum">0</span></span>
     		</span>
   		</div>
   		<div class="function-btns">
	      	<#--
			<a class="btn-red-max tanchu2_btn  f-r" data-toggle="show" 
				data-placement="top" id="confirmOrderBtn" onclick="javascript:readyFormOrder()" 
				onselectstart="return false;">去下单</a>
			-->
			<#if currentAccount().isSysAccount=="0">
				<button class="btn-red-max tanchu2_btn  f-r choseAcc" data-toggle="show"
					data-placement="top" type="button" id="confirmOrderBtn" onselectstart="return false;"
					onclick="javascript:readyFormOrder(0)">去下单</button>
			<#else>
				<button class="btn-red-max tanchu2_btn  f-r choseAcc" data-toggle="show"
					data-placement="top" type="button" id="confirmOrderBtn" onselectstart="return false;"
					onclick="javascript:readyFormOrder(1)">选择客户</button>
			</#if>
			<#--<span class="color-blue f-r m-t30 m-l20 m-r20 cursor-p" id="tips123" onClick="dis()">折扣方案</span>
			<span class="color-orange f-r m-t30" id="txt_save">购买越多，折扣越大！</span>-->
	    </div>
  </div>
    <#else>
		<div class="crumbs bs-callout bs-callout-danger author" style="text-align: left;font-size: 22px;margin: 50px 0px 400px;">
			
			<#if msg?? && msg!="" >
			<span class="glyphicon glyphicon-info-sign"></span> &nbsp; ${msg??}
			<#else>
			<span class="glyphicon glyphicon-info-sign"></span>&nbsp;您的购物车是空的，赶紧去看看有什么好宝贝吧...
			</#if>
			
		</div>
	</#if>
  </div>
  </div>
  <!-- right center end -->  
  </form>
</div>


<form action="${basepath}/cart/delete" method="POST" id="formDelete">
	<input type="hidden" name="id">
</form>

<form action="${basepath}/order/readyOrder" method="POST" id="formReadyOrder">
	<input type="hidden" name="productIDs">
	<input type="hidden" name="buyCounts">
	<input type="hidden" name="guestId">
</form>



<script src="${basepath}/static/js/cart.js?v=${jversion}"></script>
<script src="${basepath}/static/js/discount.js?v=${jversion}"></script>

<script id="tpl_accInfoBox" type="text/html">
<div class="addInfoId ">
<div class="xz_box" style="width:450px;">
	<p class="xz_inputbox"><input id="queryCond" type="text" placeholder="请输入查询条件"><a class="xz_btn" onclick="loadAccData_new()">查询</a></p>
	<table class="list_user m_t20" border="0" cellspacing="0" cellpadding="0">
	  <thead>
	      <tr>
	        <td width="20%">选择</td>
	        <td width="30%">姓名</td>
	        <td width="50%">电话</td>
	      </tr> 
	  </thead> 
	</table>
	<div class="list_ubox">
	<table class="list_user" id="accList" border="0" cellspacing="0" cellpadding="0">
	  <tbody id="accTable"></tbody>
	</table>
	<div id="page11" style="margin-top: 5px;"></div>
	</div>
	<input type="hidden" id="accTotal" />
</div>
</div>
</script>

<script id="tpl_accTable" type="text/html">
{{# 
for(var i=0,dLength=d.length;i<dLength;i++){
var item = d[i];
}}
<tr><input type="hidden" name="id" value="{{item.id}}">
<td width="20%"><input type="radio" name="radio"></td>
<td width="30%">{{item.trueName}}</td><td width="50%">{{item.phone}}</td></tr>
{{#}}}
</script>

<script src="${basepath}/static/laypage/laypage.js"></script>
<script src="${basepath}/resource/laytpl-v1.1/laytpl.js"></script>

<script type="text/javascript">



var refresh_win = true;
//购物车删除
function deleteFromCart(productId){
  layer.confirm('确定要删除么？', {
    btn: ['确认','取消'] //按钮
	}, function(){
	    if(productId){
		$("#formDelete :hidden[name=id]").val(productId);
		$("#formDelete").submit();
	}
	});
}

/*function deleteFromCartAll(){
	var _ids = "";
	var _checked = $("input[name='chk']:checked");
	_checked.each(function(){
		_ids += $(this).parents("tr").attr("data-id")+",";
	});
	if(_ids==""){
		layer.alert('请选择要删除的商品', {icon: 6});
		return false;
	}else{
		layer.confirm('确定要删除么？', {icon: 3}, function(index){
		    layer.close(index);
		    _ids = _ids.substr(0,_ids.length-1);
		    deleteFromCart(_ids);
		});
	}
}*/

</script>

</@html.htmlBase>

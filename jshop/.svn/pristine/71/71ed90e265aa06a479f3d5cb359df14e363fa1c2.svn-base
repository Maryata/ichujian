 <!--product list -->
<div class="mainclass">
	
	<#assign plineMap = sysDic("account_purchaseType_curruser") >
	<#assign pline ="" >
	<#assign material ="" >
	<#assign mainCatalogCode ="" >
	<#assign childrenCatalogCode ="" >
	<#if e??>
	<#assign pline=e.pline!"">
	<#assign material =e.material!"" >
	<#assign mainCatalogCode =e.mainCatalogCode!"" >
	<#assign childrenCatalogCode =e.childrenCatalogCode!"" >
	</#if>
    <div class="classify">
    	<table>
        <tr>
          <td class="name">产品分类：</td>
          <td>
			<#if isLogin=="0">
	    		<a class="active" href="${basepath}/product/productList?material=ghAndroid">
				  		高透智能钢化膜
			  	</a>
		  	</#if>
			<#if isLogin=="1" && activeCode?? && activeCode=="0">
	    		<a class="active" href="${basepath}/product/productList?material=ghAndroid">
			  		高透智能钢化膜
			  	</a>
		  	</#if>
		  	<#if isLogin=="1" && activeCode??&& activeCode=="1">
			  	<a class="active" href="${basepath}/product/productList?material=gh">
						配件
				</a>
		  	</#if>
    		<#--
    		<a class="${(material=='ghAndroid')?string("active","")}" href="${basepath}/product/productList?material=ghAndroid">安卓高透智能钢化膜</a>
    		<a class="${(material=='ghIOS')?string("active","")}" href="${basepath}/product/productList?material=ghIOS">苹果高透智能钢化膜</a>
			-->     	  
    	  </td>
    	</tr>
      </table> 
    </div>
    
    <div class="brand-tab">
       <table> 
     	<tr>
          <td class="name">指电品牌：</td>
          <td>
        <#list plineMap?keys as key>
        	<#-- 未登录，只显示手机膜 -->
        	<li v="${key}" class='<#if pline==key>hover</#if> search_item_pline' href1="${basepath}/product/productList?material=${material}&pline=${key}"> ${plineMap[key]}</li>
        </#list>
        
		</td>
		</tr>
		</table>    
    </div>
    
  <div id="con_tagpage_1" class="brand-box hover">
	<div class="brand-nav2">
        <table>
          <tr>
          	<#if isLogin=="0">
        		<td class="name">手机品牌：</td>
          	<#else>
	          	<#if activeCode?? && activeCode=="0">
	          		<td class="name">手机品牌：</td>
	          		<#else>
	          		<td class="name"></td>
	      		</#if>
	      	</#if>
            <td>
				<#list systemManager().catalogs as item>
			        <#assign index=0>
			        <#if isLogin=="1">
				        <#if activeCode?? && activeCode=="0">
							<a  href1="${basepath}/product/productList?material=ghAndroid&pline=${pline}&mainCatalogCode=${item.code}"  class='search_item_brand ${(mainCatalogCode==item.code)?string("hover","")}' > ${item.name}</a>
						</#if>
						<#else>
							<a   href1="${basepath}/product/productList?material=ghAndroid&pline=${pline}&mainCatalogCode=${item.code}"  class='search_item_brand ${(mainCatalogCode==item.code)?string("hover","")}' > ${item.name}</a>
					    </#if>
				  	<#assign index=index+1>
				</#list>
		 	</td>
		 </tr>
        </table>
    </div>
	
	<#if modelType?? >
	<div class="brand-nav2" id="childrenCatalogCodeDiv">
		<table>
		  <tr>
			<td class="name">型号：</td>
			<td >
				<a href1="${basepath}/product/productList?material=${material}&pline=${pline}&mainCatalogCode=${mainCatalogCode}"  class='search_item_xinhao ${(childrenCatalogCode!="")?string("","hover")}' > 全部</a>
				<#list modelType as item>   <#--<#if item_index!=0 >|</#if> -->
				<a href1="${basepath}/product/productList?material=${material}&pline=${pline}&mainCatalogCode=${mainCatalogCode}&childrenCatalogCode=${item.code}" class='search_item_xinhao ${(childrenCatalogCode==item.code)?string("hover","")}' >${item.name}</a>
				</#list>
			</td>  
		  </tr>
		</table>
	</div>
	<#else>
	<div class="brand-nav2" id="childrenCatalogCodeDiv">
	</div>
	</#if>
   </div>
	
</div>


	<#assign materialMap = sysDic("product_material") >
	<#assign actCodesId = [1697,1698,1699,1700,1701,1702,1703] >
<div class="content-gb">

<!-- left menu start -->
  <#include "/index_left_menu.ftl">
  <!-- left menu end -->

  <div class="right910-box">	
	<div class="wares-box" id="productListDiv">
	<#if productList??>
	<#list productList as item>
		  <a title="${item.name!""}" data-new="${item.isnew!""}">
			<img class="p-img" src="${systemSetting().imageRootPath}${item.picture!""}" alt="${item.name!""}" >
			<#if item.isInternational?? && item.isInternational=="0">
			<img class="ico-jiaobiao" src="${basepath}/static/images/ico-gn.png">
			</#if>
			<#if item.isInternational?? && item.isInternational=="1">
			<img class="ico-jiaobiao" src="${basepath}/static/images/ico-gj.png">
			</#if>
			<span class="info" data-nowPrice="${item.nowPrice!"0"}" data-mainCatalogCode="${item.mainCatalogCode!""}" data-mainCatalogName="${item.mainCatalogName!""}" data-childrenCatalogCode="${item.childrenCatalogCode!""}" data-childrenCatalogName="${item.childrenCatalogName!""}">
			  <p class="name">${item.plineName!""}</p>
		      <p class="name">${item.name!""}</p>
			  <p class="money2"> ¥${(accType=="1")?string( item.nowPrice,item.price )}</p>
			  <p><button type="button" class="bluebtn addcar_list" data-toggle="show" data-placement="top" data-itemid="${item.id!""}">加入购物车</button></p>
			  <input type="text" class="nub none" value="1" minNum="${item.minNum!"100"}" maxlength="8" name="buyNum">
			</span>
		  </a>
	 </#list>
	 </#if>
	 </div>
	
  </div>
</div>

<div class="page-box2" id="pagerDiv" >
	<#include "/pager_index.ftl">
</div>
  
<!--品牌展开收起js-->

<!--品牌展开收起js END-->
<script src="${basepath}/static/js/product.js?v=${jversion}" charset="utf-8"></script>
<!--飞入购物车2-->
<a title="查看购物车" id="cartico" href="${basepath}/cart/cart.html"><span class="cue-red" id="cartQuantity" title="购物车商品:">0</span></a>
<a id="qq-kf1" target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=934819443&site=qq&menu=yes"></a>
<a id="qq-kf2" target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=1013355396&site=qq&menu=yes"></a>

<script type="text/javascript" src="${basepath}/resource/laytpl-v1.1/laytpl.js"></script>
<script id="tpl_search_childrenCatalogCode" type="text/html">
{{#
   var modelType =d.modelType;
   var e = d.e;
   if(modelType){
   var cls = "xx";
   if(!e.childrenCatalogCode || e.childrenCatalogCode=='')cls="hover";
}}   
   <table>
	  <tr>
		<td class="name">型号：</td>
		<td >
			<a class="{{cls}} search_item_xinhao brandclass" href1="${basepath}/product/productList?material={{ e.material }}&pline={{ e.pline }}&mainCatalogCode={{ e.mainCatalogCode }}" > 全部</a>
			 {{#
		          for(var i=0;i<modelType.length;i++){
		          if(modelType[i].code==e.childrenCatalogCode)cls="hover";
		          else cls="";
		      }} 
			<a  class='{{cls}} search_item_xinhao  brandclass' href1="${basepath}/product/productList?material={{ e.material }}&pline={{ e.pline }}&mainCatalogCode={{ e.mainCatalogCode }}&childrenCatalogCode={{ modelType[i].code }}" >{{ modelType[i].name }}</a>
			{{# } }}
		</td>  
	  </tr>
	</table>
{{# } }}
</script>
<script id="tpl_products" type="text/html">
{{#
  var productList=d.productList;
  var isLogin=d.isLogin;
  var accType = d.accType; 
  var activeCode=d.activeCode;
  var pmsg=d.pmsg;
  if(productList){
   for(var i=0;i<productList.length;i++){
  }}
		 <a title="{{ productList[i].name }}" data-new="{{ productList[i].isnew }}">
			<img src="${systemSetting().imageRootPath}{{productList[i].picture}}" alt="{{productList[i].name}}" >
			{{#
			    if(productList[i].isInternational && productList[i].isInternational=="0"){
			}}    
			<img class="ico-jiaobiao" src="${basepath}/static/images/ico-gn.png">
			{{# } }}
			
			{{#
			    if(productList[i].isInternational && productList[i].isInternational=="1"){
			}}  
			<img class="ico-jiaobiao" src="${basepath}/static/images/ico-gj.png">
			{{# } }}
			<span class="info" data-nowPrice="{{ productList[i].nowPrice }}" data-mainCatalogCode="{{ productList[i].mainCatalogCode }}" data-mainCatalogName="{{ productList[i].mainCatalogName }}" data-childrenCatalogCode="{{ productList[i].childrenCatalogCode }}" data-childrenCatalogName="{{ productList[i].childrenCatalogName }}">
			  <p class="name"><span>{{ productList[i].plineName }}</span></p>
			  <p class="name">{{ productList[i].name }}</p>
			   <p class="money2">¥  {{ accType=="1" ? productList[i].nowPrice : productList[i].price }} </p>
			 <input type="text" class="nub none" value="{{ productList[i].minNum }}" minNum="{{ productList[i].minNum }}" maxlength="8" name="buyNum">
			  <p> <button type="button" class="bluebtn addcar_list" data-toggle="show" data-placement="top" data-itemid="{{ productList[i].id }}">加入购物车</button></p>
			</span>
		  </a>
 {{# } }}		  
{{# } }}
</script>

<script id="tpl_pager" type="text/html">
{{#
   var purl="";
   var pager=d.pager;
   var url= d.pagerUrl;
   var t="";
   var pageSize = pager.pageSize;
   var currentPageNumber=pager.offset * pager.pageSize + 1;
   var maxpager = pager.pagerSize;
   if(maxpager>8)maxpager=8;
   if(pager.list && pager.pagerSize >1){
 }}
   <ul>
      {{#
        if(currentPageNumber!=1){
      }}
       <li class="on-btn"><a href="{{purl}}" class="pageLink">上一页</a></li>
       {{# } }}
           {{#
            for(var i=0;i<maxpager;i++){
            var url= d.pagerUrl;
            t=i+1;
            var purls=url+"&pager.offset="+pageSize*i;
            if(currentPageNumber==t){
            purl=url+"&pager.offset="+pageSize*(i+1);
           }}
           <li><a class="active">{{t}}</a></li>
           {{# }else{ }}
           <li><a href="{{ purls }}" class="pageLink">{{t}}</a></li>
           {{# } }}
            {{# } }}
        {{#
        if(currentPageNumber!=t){
      }}    
       <li class="next-btn"><a href="{{purl}}" class="pageLink">下一页</a></li>
       {{# } }}
</ul>
<div class="page-nub">共{{pager.pagerSize}}页 </div>
<div class="form">
	<span class="text">到第</span>
	<input class="page-inp" id="page_to_Num" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" type="number" value="" min="1" max="${pager.pagerSize}" aria-label="页码输入框">
	<span class="text">页</span>
	<a id="page_to_btn" class="ok-btn" >确定</a>
</div>
{{# } }}
</script>
<script>
(function($, w, d){
  $("#switch").click(function(){
	 $( "#switch" ).toggleClass( "brand-nav-off", 500 );
		$(".brand-nav-open").fadeToggle(300);
  });
	var quantity = "${quantity!'0'}";
	if(quantity &&quantity!=0){
		$("#cartQuantity").html(quantity).show();
	}else{
		$("#cartQuantity").hide();
	}
}(jQuery, window, document));
</script>
<script>

var pline = "${pline}";
var material = "${material}";
var mainCatalogCode = "${mainCatalogCode}";
var childrenCatalogCode = "${childrenCatalogCode}";

	
if(!currentU_check){
setTimeout(function(){$.get("${basepath}/account/loadLoginUser", function(result){console.log(result);});},10000);//2min
}

function buidClick(_this,flag){
  var url = _this.attr("href1").replace("/productList?","/productListAjax?");
 
  //品牌；
  if(_this.hasClass("search_item_brand")){
  	url = $.UrlParamDel(url,"pline");
	url = $.UrlParams(url,"pline", pline);
  }
  //xinghao
  if(_this.hasClass("search_item_xinhao")){
  	url = $.UrlParamDel(url,"mainCatalogCode");
	url = $.UrlParams(url,"mainCatalogCode", mainCatalogCode);
  }
  
  pline = getQueryString("pline",url);
  mainCatalogCode = getQueryString("mainCatalogCode",url);
  childrenCatalogCode = getQueryString("childrenCatalogCode",url);
  
  $.ajax({
        type:'post',
        url:  url,
        dataType : 'json',
        success: function(data){
       // alert(0)
	        var gettpl = document.getElementById('tpl_search_childrenCatalogCode').innerHTML;
			laytpl(gettpl).render(data, function(html){
			    document.getElementById('childrenCatalogCodeDiv').innerHTML = html;
			});
			
			data.accType = ${accType};
			
			laytpl(document.getElementById('tpl_products').innerHTML).render(data, function(html){
			    document.getElementById('productListDiv').innerHTML = html;
			});
			
			data.pagerUrl = url.replace("/productListAjax?","/productList?");
			laytpl(document.getElementById('tpl_pager').innerHTML).render(data, function(html){
			    document.getElementById('pagerDiv').innerHTML = html;
			});
			loadPageFun();
			load_addcar_list();
		    //$(_this).addClass("hover");
		   
		    //if(flag){
			    $(".search_item_xinhao").unbind('click');
				$(".search_item_xinhao").click(function(){
				  var _this1 = $(this);
				  buidClick(_this1,false);
				  return false;
			  	});
		  	//}
		  	 
		    if(_this.hasClass("search_item_xinhao")){
		       //$(".brandclass").removeClass("hover");
			   //$(_this).addClass("hover");
			   //return false;
		    }
		  	return false;
        },
        error : function(data) {
       // alert(data+"2222")  
       } 
  });
}


(function($, w, d){
  $(".search_item_pline").click(function(){
	  var _this = $(this);
	  buidClick(_this,true);
	  $(".search_item_pline").removeClass("hover");
	  //$(_this).parent().addClass("hover");
	  $(".search_item_brand").removeClass("hover");
      $(_this).addClass("hover");
	  return false;
  });
  $(".search_item_brand").click(function(){
	  var _this = $(this);
	  buidClick(_this,true);
	  $(".search_item_brand").removeClass("hover");
      $(_this).addClass("hover");
	  return false;
  });
  
  $(".search_item_xinhao").click(function(){
	  var _this = $(this);
	  buidClick(_this,true);
	  $(".search_item_xinhao").removeClass("hover");
      $(_this).addClass("hover");
	  return false;
  });
  
}(jQuery, window, document));
</script>

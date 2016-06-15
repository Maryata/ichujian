<#import "/static/common_front.ftl" as html/>
<#import "/indexMenu.ftl" as menu/>
<@html.htmlBase title="适配清单">

	<@menu.menu selectMenu="models"/>
	<!--内容-->
	<div class="content">
	  
	  <!-- right center start -->
	   <!--product list -->
	<div class="brand-nav wi-all m-t50" id="catalogs">
	<#list systemManager().catalogs as item>
		<a href="#${item.code}"><img src="${basepath}/static/models/model_${item.code}.jpg" ></a> 
	</#list>
	</div>

	
	<#assign materialMap = sysDic("product_material") >
	<#if modelList??>
	<#list systemManager().catalogs as item>
	 <div class="model-box">
       <#if item_index==0 && currentAccount()??>
       <div class="nobrand"><a class="nobrand_open"> 没有您需要的品牌型号？</a> </div>
       </#if>
       <h1 id="${item.code}"><a href="${basepath}/product/productList?material=ghAndroid&mainCatalogCode=${item.code}" >${item.name}</a></h1>
       <div class="model-info ${item.code}"  data-code="${item.code}" data-name="${item.name}">
       <#list modelList as p>
	       <#if item.code?? && p.mainCatalogCode?? && item.code==p.mainCatalogCode>
	         <span class="childdata" data-name="${p.childrenCatalogName!""}" data-code="${p.childrenCatalogCode!""}" > <a href="${basepath}/product/productList?material=ghAndroid&mainCatalogCode=${item.code}&childrenCatalogCode=${p.childrenCatalogCode!""}" > ${p.childrenCatalogName!""}</a> 
	           <b>
	           </b>
	         </span>
	       </#if>
       </#list>
       </div>
     </div>
	</#list>
</div>

<!--增加机型弹出框-->
<div class="model_window" style="display:none; width:500px;height:300px;margin-top:-200px!important; margin-left:-250px!important;  border-radius: 10px;">
  <div class="tanchu-title">请在此处填写您需要的品牌机型，我们的销售人员会尽快与您联系<span title="关闭" class="model_window_close"></span></div>
  <form id="form" class="registerform" action="" method="post" theme="simple">
    <div class="nobrand-form login-tanchu">
      <div class="infobox">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <thead>
            <tr class="title">
              <td>具体品牌</td>
              <td>具体机型</td>
              <td>预计采购量</td>
              <td>操作</td>
            </tr>
            
          </thead>
          <tbody class="tb_detail"> 
            <tr class="input">
              <td><input type="text"  name="brand" datatype="*6-16"  nullmsg="品牌" errormsg="品牌输入错误"></td>
              <td><input type="text"  name="models" datatype="*6-16"  nullmsg="机型" errormsg="机型输入错误"></td>
              <td><input type="text"  name="buyNumber" datatype="*6-16"  nullmsg="预计采购量" errormsg="预计采购量输入错误" ></td>
              <td><a onclick="javascript:add();">添加</a></td>
            </tr>
          </tbody>
        </table>
      </div>
      <p>
        <input type="button" class="enrol-btn" value="确认提交" onclick="insert()"/>
      </p>
    </div>
  </form>
</div>
</#if>

<div class="tanchu_bg" ></div>
<script type="text/javascript">
    $(".nobrand").click(function(){
        $(".tanchu_bg").show();
        $(".model_window").show();
    });
    $(".model_window_close").click(function(){
        $(".tanchu_bg").hide();
        $(".model_window").hide();
    })
  </script> 
<!--增加机型弹出框end--> 
<script src="${basepath}/static/js/product.js?v=${jversion}" charset="utf-8"></script>
<script src="${basepath}/static/js/promodels.js?v=${jversion}" charset="utf-8"></script>
<script>
(function($, w, d){
   	$("#switch").click(function(){
 	 	$( "#switch" ).toggleClass( "brand-nav-off", 500 );
 		$(".brand-nav-open").fadeToggle(300);
  	});
  
    var quantity = "${quantity!'0'}";
	if(quantity &&quantity!=0){
		$("#cartQuantity").html(quantity);
		$("#cartQuantity").show();
	}else{
		$("#cartQuantity").hide();
	}
}(jQuery, window, document));
</script>
<script>
    function dosomething(obj){//加载样式background-color: #dbeff8;
        var data = obj.getAttribute("data");
        $("div").removeClass("light-blue");
        $(''+data).addClass("light-blue");
    }
</script>
</@html.htmlBase>
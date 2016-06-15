<#import "/static/common_front.ftl" as html/>
<#import "/indexMenu.ftl" as menu/>
<@html.htmlBase title="资料详情" badyClass="login-bg">

<@menu.menu selectMenu=""/>
<div class="content">
  <div class="protocol-box">
    <h1><#if e?? && e.title??>${e.title!""}<#else></#if></h1>
    <div class="protocol-info">
    <#if files?? && files?size gt 0>
    <#list files as f>
      <div class="core-info">
      <p class="stitle">${f.fname!""}</p>
      <img src="${f.furl!""}">
      <br>
      <a class="applayDown" data-id="${f.id}" data-url="${f.fbigurl}" data-cId="${f.cId}">素材打包下载</a>
      </div>
    </#list>
    <#else>
           没有详细资料
    </#if>
    </div>
    
  </div>
</div>
<iframe id="downloadframe" name="downloadframe" style="display:none"></iframe>

<script type="text/javascript">
$(".applayDown").click(function(){
  var _this = $(this);
  $.ajax({
	  type: 'POST',
	  url: basepath+"/dataCenter/applyData",
	  data: {fId:_this.attr("data-id"),cId:_this.attr("data-cId")},
	  dataType : 'json',
	  success: function(data){
	  	if(data.status=="-1"){
	  		openLogin();
	  	}else{
	  		window.frames["downloadframe"].location.href=_this.attr("data-url");
	  		//layer.msg('申请成功,请等待客服人员联系');
	  		 _this.addClass("nohover").unbind('click');
	  	}
	  }
  });
});
</script>

</@html.htmlBase>
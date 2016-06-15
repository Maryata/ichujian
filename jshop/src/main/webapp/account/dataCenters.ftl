<#import "/static/common_front.ftl" as html/>
<#import "/indexMenu.ftl" as menu/>
<@html.htmlBase title="资料中心">

<@menu.menu selectMenu=""/>
<div class="content">
  <div class="left-box">
    <div class="user-nav "> 
    <#if dicMap??>
      <#list dicMap?keys as key>
	    <a href="${basepath}/dataCenter/list?dicId=${key}" class="list-group-item ${(e.dicId==key)?string('active', '')}"><span class="ico-<#if key=='video'>video<#elseif key=='spread'>spread<#else>course</#if>"></span>${dicMap[key]}</a> 
	  </#list>
    </#if> 
    </div>
  </div>
  
  
  <div class="right-box user-box">
    <div class="ubox-title">${dicMap[e.dicId]}</div>
    <div class="ubox-info">
    <#if pager.list?? && pager.pagerSize gt 0>
    
    <#if e.dicId=='video' >
    <#if files?? >
     <#list pager.list as item>
     
     <#list files as f>
      <#if f.cId==item.id>
      <div class="video-box">
        <h1>${f.fname!""}</h1>
        
        <a class="video_play" id="playercont${item.id}" data-id="${item.id}" data-title="${item.title}" data-fname="${f.fname}" data-picture="${f.furl}" data-furl="${f.fbigurl!""}" >
        <span><img src="${basepath}/static/images/play.png" ></span><img src="${f.furl}" > 
        </a>
        <div class="profile" title="${item.content!""}">${item.content!""} <!--<a class="dowbtn applayDown" data-id="${f.id}" data-url="${f.fbigurl}" data-cId="${f.cId}">视频下载</a>--> </div>
      </div>
      
      <div class="son-title"></div>
     </#if>
     </#list>
     </#list>
     
    </#if>
    <#else>
    <div class="spread-box">
    <#list pager.list as item>
      <a class="spread" href="${basepath}/dataCenter/${item.id}" target="_blank"> <img src="${item.picture!""}" >
        <h3 title="${item.title!""}">${item.title!""}</h3>
      </a>
    </#list>
    </div>      
    </#if>
    <#include "/pager.ftl"/>  
    </#if>
      
    </div>
  </div>
</div>

<!--弹出框-->
<div id="_box" class="interim" style="display:none; width:600px;height:400px;margin-top:-250px!important; margin-left:-300px!important;  border-radius: 10px;">
<div class="tanchu-title"><a id="titlebox">视频播放</a><span title="关闭" class="interim_close"></span></div>
<div class="interim_info" >
<div id="infobox"></div>
</div>
</div>
<div id="bgbox" class="tanchu_bg" ></div>

<iframe id="downloadframe" name="downloadframe" style="display:none"></iframe>
<script type="text/javascript" src="${basepath}/static/js/player/jwplayer.js"></script>
<script type='text/javascript'>
$(".video_play").click(function(){
  var _this = $(this);
  var id,file,image;
  id=_this.attr('id'),file=_this.attr("data-furl"),image=_this.attr("data-picture");
  $('#_box,#bgbox').show();
  $('#titlebox').html(_this.attr("data-title"));
  jwplayer("infobox").setup({ 
    file: file, 
    image: image,
    height: 348,
    autostart : true,
    flashplayer: "${basepath}/static/js/player/player.swf",
    width: 585 
  });
  
});

$(".interim_close").click(function(){
    $("#_box,#bgbox").hide();
});

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
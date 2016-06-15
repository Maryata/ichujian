<#import "/account/accountHtml.ftl" as accountHtml/>
<@accountHtml.html currentMenu="topwd" title="修改密码" jqueryValidator=true>

<div class="content">
    <div class="ubox-info m-t100">
    	<p class="align-c"><img width="80" src="${basepath}/static/images/ico-pass.png"></p>
	    <p class="align-c f_18">密码修改成功！</p>
	</div>
</div>
<script>
$(function(){
  layer.msg('1秒钟后跳转页面。。。。');
  setTimeout(function(){location.href="${basepath}/account/accountCenter";},1000 );
});

</script>
</@accountHtml.html>

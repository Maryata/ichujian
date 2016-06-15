<#macro html currentMenu title="用户中心" jqueryValidator=false>
<#import "/static/common_front.ftl" as html/>
<#import "/indexMenu.ftl" as menu/>

<#import "/account/accountMenu.ftl" as accountMenu>

<@html.htmlBase title=title jqueryValidator=jqueryValidator>

<@menu.menu selectMenu="${currentMenu}"/>
	
<div class="content">
<#nested/>
</div>

</@html.htmlBase>
</#macro>
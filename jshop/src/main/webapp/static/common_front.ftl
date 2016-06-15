<#macro htmlBase title="" footer="footer" jsFiles=[] cssFiels=[] jqueryValidator=false nobody=false badyClass="" >
<!DOCTYPE html>
<html<#--><#if footer !="footer" > class="html-enrol" </#if> </#-->>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script>var basepath = "${basepath}";</script>
    <script type="text/javascript" src="${basepath}/static/js/lib/clientExt.js?v=${jversion}"></script>
    
    <#assign non_responsive2>y</#assign>
    <#assign responsive>${Session["responsive"]!""}</#assign>
    <#if responsive == "y">
        <#assign non_responsive2>n</#assign>
    <#elseif systemSetting().openResponsive == "n">
        <#assign non_responsive2>y</#assign>
    <#else >
        <#assign non_responsive2>n</#assign>
    </#if>
    <script>
    	var currentU_check = false;
        var staticpath = "${staticpath}";
        var non_responsive2 = "${non_responsive2}";
        
        <#if currentAccount()??>
        <#assign currentU=currentAccount()>
        var login = true;
        var currentUser = "${currentU.nickname!""}";
        	<#if currentU.checkAduitUser()==true>
        	currentU_check = true;
        	<#assign currentU_check=true>
        	</#if>
        <#else>
        var login = false;
        var currentUser = "";
        </#if>
    </script>
    <#if non_responsive2 != "y">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </#if>
    <meta name="description" content="${systemSetting().description!""}"/>
    <meta name="keywords" content="${systemSetting().keywords!""}"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title><#if title!=''>${title!""}&nbsp;&nbsp;&nbsp;</#if>${systemSetting().title}</title>
    
    <link rel="shortcut icon" type="image/x-icon" href="${basepath}/static/images/favicon.ico">
    
	<link rel="stylesheet" type="text/css" href="${basepath}/static/css/ichujian.css?v=${cversion}">
	<link rel="stylesheet" type="text/css" href="${basepath}/static/css/menu.css?v=${cversion}">
	<link rel="stylesheet" type="text/css" href="${basepath}/static/css/nav.css?v=${cversion}">
	<link rel="stylesheet" type="text/css" href="${basepath}/static/css/banner.css?v=${cversion}">

	<link rel="stylesheet" type="text/css" href="${basepath}/static/css/gotop.css?v=${cversion}" />
	<!--
	<script type="text/javascript" src="${basepath}/resource/js/jquery-1.9.1.min.js"></script>
	-->
	
	<script type="text/javascript" src="${basepath}/static/js/jquery-1.8.3.js"></script>
	
	<script type="text/javascript" src="${basepath}/static/js/jquery.movebg.js"></script>
	<script type="text/javascript" src="${basepath}/static/js/banner.js"></script>
	<!--导航效果js-->
	<script type="text/javascript" src="${basepath}/static/js/nav.js"></script>
	
	<script type="text/javascript" src="${basepath}/static/js/navfix.js"></script>


	<script type="text/javascript" src="${basepath}/static/js/lib/jquery.common.js?v=${jversion}"></script>	
	<!-- jquery validator -->
	<#if jqueryValidator == true>
	<link rel="stylesheet" href="${basepath}/resource/validator-0.7.3/jquery.validator.css" />
	</#if>
	
	<!--飞入购物车-->
	<script src="${basepath}/static/js/requestAnimationFrame.js" charset="utf-8"></script>
	<script src="${basepath}/static/js/jquery.fly.min.js" charset="utf-8"></script>
	<!--[if lte IE 9]>
	
	<![endif]-->
	<!--[if lt IE 9]><script src="http://www.iteblog.com/wp-content/themes/yusi2.0/js/html5.js"></script><![endif]-->
	
</head>
    <#if nobody>
        <#if systemSetting().isopen=="false">
        ${systemSetting().closeMsg!"系统关闭，请联系管理员"}
        <#return />
        </#if>
        <#nested />
    <#else >
    <body <#if footer=='footer-enrol'>class="enrol-bg"</#if> <#if badyClass!=''>class="${badyClass}"</#if> >
        <#if systemSetting().isopen=="false">
        ${systemSetting().closeMsg!"系统关闭，请联系管理员"}
        <#return />
        </#if>
        <#nested />

  
<#if jqueryValidator == true>
<script type="text/javascript" src="${basepath}/resource/validator-0.7.3/jquery.validator.js"></script>
<script type="text/javascript" src="${basepath}/resource/validator-0.7.3/local/zh_CN.js"></script>
</#if> 
<script type="text/javascript" src="${basepath}/resource/layer-v1.9.2/layer/layer.js"></script>

        <#include "/foot.ftl">
    </body>
    </#if>
</html>
</#macro>

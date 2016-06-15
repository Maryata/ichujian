//IE提示console未定义问题解决 
if (!window.console){// || !console.firebug
    var names = ["log", "debug", "info", "warn", "error", "assert", "dir", "dirxml", "group", "groupEnd", "time", "timeEnd", "count", "trace", "profile", "profileEnd"];
    window.console = {};
    for (var i = 0; i < names.length; ++i)
        window.console[names[i]] = function() {};
}
//浏览器有效性判断;
function userAgentInit(){
	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
	(s = ua.match(/rv:([\d.]+)/)) ? Sys.ie = s[1] : //Ie11 Edge 模式
	(s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
	(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
	(s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
	(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
	
	var pf = navigator.platform.toLowerCase();
	(pf.indexOf('win')>-1) ? Sys.win = true : 
	(pf.indexOf('mac')>-1) ? Sys.mac = true : false;
	//alert(Sys.ie +" -- "+navigator.platform+"  userAgent:"+s +" ,appName:"+navigator.appName +" ,appVersion:"+navigator.appVersion);
	var v = true;
	window.CLIENT_userAgent = Sys;
	if(Sys.ie ){
		if( Sys.ie=='6.0' || Sys.ie=='7.0' || Sys.ie=='8.0'){
		v= false;
		//window.location.href = basepath+'/browser.html';
		}
	}else if(Sys.firefox || Sys.chrome || Sys.opera || Sys.safari){
		v = true;
	}else{
		v = true;
	}
	
	if(!v ){//|| (!Sys.win && !Sys.mac )
		//alert('v:'+v+' Sys.win:'+Sys.win+' Sys.mac:'+Sys.mac);
		//window.location.href = basepath+'/browser.html';
	}
}
userAgentInit();

//处理键盘事件  
function doKey(e){  
    var ev = e || window.event;//获取event对象  
    var obj = ev.target || ev.srcElement;//获取事件源  
    var t = obj.type || obj.getAttribute('type');//获取事件源类型  
    if(ev.keyCode == 13 && t != "password" && t != "text" && t != "textarea"){  
        return false;  
    }  
}  
 //禁止后退键 作用于Firefox、Opera  
document.onkeypress=doKey;  
 //禁止后退键  作用于IE、Chrome  
document.onkeydown=doKey; 


// 获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == ""){
	    url = window.location.search;
    }else{	
    	url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return "";
}

function tourl(url){
	window.location.href=url;
}
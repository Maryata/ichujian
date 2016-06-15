<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
  <head>
    <base >
    <title>e键下载</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  <script type="text/javascript">
   //function isWeiXin(){ }
   var ua = window.navigator.userAgent.toLowerCase(); 
   if(ua.match(/MicroMessenger/i) == 'micromessenger'){ 
   window.location.replace("http://fusion.qq.com/app_download?appid=1103571626&platform=qzone&via=QZ.MOBILEDETAIL.QRCODE&u=269914066");
   }else{ 
   window.location.replace("http://115.236.16.79/ichujian_android.apk");
   } 
 
  </script>
  <body>
  </body>
</html>

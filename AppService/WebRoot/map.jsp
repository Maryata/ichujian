<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base >
    <title>MoKey</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript">
var opts = {
	width : 330,     // 信息窗口宽度
	height: 100,     // 信息窗口高度
	title : ""  // 信息窗口标题
}
function initialize() {
	var map = new BMap.Map('contentmap');
	var myGeo = new BMap.Geocoder();  
	var marker=null;
	map.addControl(new BMap.NavigationControl()); 
	map.addControl(new BMap.MapTypeControl()); 
	//将地址解析结果显示在地图上，并调整地图视野  
	var address='上海市长宁区凯旋路613号 创邑源 G栋楼';
	 
	myGeo.getPoint(address, function(point){  
	if (point) {  
		marker=new BMap.Marker(point);
		map.addOverlay(marker);  
		
		var infoWindow = new BMap.InfoWindow("<span class='orange' style='font-size:14px;font-weight:bold'>上海扬邑企业管理咨询有限公司</span><br><span style='font-size:12px;'>凯旋路613号</span><br><a href='http://ditu.google.cn/maps?hl=zh-CN&ie=UTF8&dirflg=r&f=d&daddr="+address+"' target='_blank'>驾车/公交路线</a>", opts);  // 创建信息窗口对象
		marker.openInfoWindow(infoWindow);  

		map.centerAndZoom(point, 15);
	}  
	}, "");
}
	 
function loadScript() {
	var script = document.createElement("script");
	script.src = "http://api.map.baidu.com/api?v=1.2&callback=initialize";
	document.body.appendChild(script);
}
window.onload = loadScript;
</script>
	
  </head>
  <body>
   map test
   <!-- -->
   <div id="contentmap" style="width:566px;height:366px;border:4px solid silver;margin-left: 32px;margin-top: 22px;"><div>
    
  </body>
</html>

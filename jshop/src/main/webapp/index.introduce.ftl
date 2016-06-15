<script>
</script>
<#import "/static/common_front.ftl" as html/>
<#import "/indexMenu.ftl" as menu/>
<@html.htmlBase title="产品介绍">

	<@menu.menu selectMenu="introduce"/>
	<!--内容-->
    <!--产品介绍-->
<div class="content" style="padding-bottom:60px;">
  <div class="cpjs-hover">
    <li id="cpjs1" onclick="setTab('cpjs',1,2)"  class="hover"><img src="${basepath}/static/images/android-hover.jpg"></li>
    <li id="cpjs2" onclick="setTab('cpjs',2,2)"><img src="${basepath}/static/images/iPhone-hover.jpg"></li>
  </div>
  <!--<div class="android-video"></div>-->
  <div id="con_cpjs_1" class="hover">
    <div class="android-video">
	 	<iframe style="margin:65px 0 0 272px;" height="375" width="670"
	 		src="http://player.youku.com/embed/XMTQxOTg2Mjk0NA=="
	 		frameborder=0 allowfullscreen>
	 	</iframe>
    </div>
	<div id="bgbox" class="tanchu_bg" ></div>
    <img class="cpjs" src="${basepath}/static/images/android_02.jpg">
    <img class="cpjs" src="${basepath}/static/images/android_03.jpg">
    <img class="cpjs" src="${basepath}/static/images/android_04.jpg">
    <img class="cpjs" src="${basepath}/static/images/android_05.jpg">
    <img class="cpjs" src="${basepath}/static/images/android_06.jpg">
    <img class="cpjs" src="${basepath}/static/images/android_07.jpg">
    <img class="cpjs" src="${basepath}/static/images/android_08.jpg">
    <img class="cpjs" src="${basepath}/static/images/android_09.jpg">
    <img class="cpjs" src="${basepath}/static/images/android_10.jpg">
    <img class="cpjs" src="${basepath}/static/images/android_11.jpg">
    <img class="cpjs" src="${basepath}/static/images/android_12.jpg">
  </div>
  <div id="con_cpjs_2" class=" " style="display:none;">
    <div class="iPhone-video">
    	<iframe style="margin:87px 0 0 272px;" height="386" width="626" 
    		src="http://player.youku.com/embed/XMTQyNzcxNjY0OA=="
    		frameborder="0" allowfullscreen>
    	</iframe>
    </div>  
    <img class="cpjs" src="${basepath}/static/images/iPhone_02.jpg">
    <img class="cpjs" src="${basepath}/static/images/iPhone_03.jpg">
    <img class="cpjs" src="${basepath}/static/images/iPhone_04.jpg">
    <img class="cpjs" src="${basepath}/static/images/iPhone_05.jpg">
    <img class="cpjs" src="${basepath}/static/images/iPhone_06.jpg">
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
<!--产品介绍 END-->
<script type="text/javascript" src="${basepath}/static/js/player/jwplayer.js"></script>
<script>
function setTab(name,cursel,n){
 for(i=1;i<=n;i++){
  var menu=document.getElementById(name+i);
  var con=document.getElementById("con_"+name+"_"+i);
  menu.className=i==cursel?"hover":"";
  con.style.display=i==cursel?"block":"none";
 }
}
$(function(){
   var p = getpara();//执行函数
   if(p==1){ 
      setTab('cpjs',1,2);
   }else if (p==2){ setTab('cpjs',2,2);}
});
function getpara()//获取参数的函数
{
  var url=document.URL;
  var para="";
   if(url.lastIndexOf("?")>0)
   {
        para=url.substring(url.lastIndexOf("?")+1,url.length);
		var arr=para.split("&");
		para="";
		for(var i=0;i<arr.length;i++)
		{
		   //alert(arr[i].split("=")[1]);
		   return arr[i].split("=")[1];
		   //para+="第"+(i+1)+"个参数>>名:"+arr[i].split("=")[0];
		   //para+=" 值:"+arr[i].split("=")[1]+"<br>";
		}
   }
}


</script>
</@html.htmlBase>
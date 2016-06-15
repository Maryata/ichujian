//*写一些各页面可能会用到的公用函数。
var curI=0;//当前默认点击的对象。
//在右下角显示窗口
var iws=0;//显示的窗口个数。
var iallheight=0;//目前为止窗口的总高度，后面的窗口将累加向上显示。
var iwidth=200;//窗口的宽度都是200
var iheight=147;
var const_pm=999;//默认为对象只有一个，不是数组
var wclose=10000;
var planWin;//保持日计划窗口，再次点击，自定聚焦
var mainWin;//保持其他窗口的焦点。
//当前点击的菜单，如果重复点击将不再进行反应
//保存上一个点击的菜单的tag
var premenu="-1",presubmenu="";

function closeOpenWindow()
{window.close();}
//通过tag找到点击的是那个连接模块。
function getLinkP(tag)
{
	if(tag!="")
		if(document.all["linkP"]!=undefined)
		if(document.all["linkP"].length!=undefined)
		for(var i=0;i<document.all["linkP"].length;i++)
		{
			if(document.all["linkP"][i].tag==tag)
			{
			return(i);
			}
		}
		else//只有一个菜单
			if(document.all["linkP"].tag==tag)
			{
				//999，界面上只有一个对象，不是数组。
			return(const_pm);
			}
	return(-1);
}
//左边菜单进入的时候初始化点击的菜单
//如果没有传入模块信息，那么默认点击的是第一个菜单
function initPage()
{
	var module=getParameter("subModuleId");
	if(module!="")
	{

		var i=getLinkP(module);
		if(i>=0)
			if(i==const_pm)
			{
				linkP.click();
				return;}
			else
			{
				linkP[i].click();
				return;
			}
	}
//初始化右边页面
//这里以后需要修改现在mainframe是写死的，应该可以传。
//parent.mainFrame.location.href="../menu/blank.jsp";
//parent.mainFrame.location.href="../report/DcForeReportAction.do?method=ay_fact_fc_&type=power&moduleId=00002101";
/*
	if(document.all["linkP"]!=undefined)
		if(linkP.length==undefined)
		{
			if(linkP.tag=="");
				//eval(getFirstSubM().click());
			else
		 		//linkP.click();
		}
		else
			if(linkP[0].tag=="");
				//eval(getFirstSubM().click());
			else
			//默认第一个菜单
			//linkP[0].click();*/
}
function getFirstSubM()
{
	if(document.all["linkPC"]!=undefined)
		if(document.all["linkPC"].length!=undefined)
			return(document.all["linkPC"][0]);
		else
			return(document.all["linkPC"]);
	return(null);
}
//取消菜单的样式
function cancelStyle(tag)
{
	//只有在不是当前功能的时候才进行变化
	var lis=document.all["lImage"];	
	if(lis!=undefined)
	{
		var i=getLinkP(tag);
		if(i>=0)
		{
			if(i==999)
			{
				//恢复上一个点击的菜单的状态。
				if(lis.oSrc!=undefined)
				{ 
					if(lis.src!=lis[i].oSrc)
					{
						document.all["tdLink"].className="leftc";
						lis.src=lis.oSrc;
					}
				}
			}
			else
			{
				//恢复上一个点击的菜单的状态。
				if(lis[i].oSrc!=undefined)
				{ 
					if(lis[i].src!=lis[i].oSrc)
					{
						document.all["tdLink"][i].className="leftc";
						lis[i].src=lis[i].oSrc;
					}
				}
			}
		}
	}
}

//取消子菜单的样式
function cancelSubStyle(tag)
{
	//只有在不是当前功能的时候才进行变化
	var lis=document.all["linkPC"];
	if(lis!=undefined)
		//恢复上一个点击的菜单的状态。
		for(var j=0;j<lis.length;j++)
		{
			if(lis[j].tag==tag)
			{
				lis[j].className="image";
			}
		}
}
//点击每一个左边的菜单时，触发此事件。
//tag，菜单的tag，pagename对应的页面，target打开页面的容器名称。
function clickBg(tag,pagename,target)
{
if(tag==premenu)
{
	if(tag=="00000601")
		try{
			//重新聚焦
			planWin.focus();
			return;
		}
		catch(e){}
	else
		return;
}
cancelSubStyle(premenu);
cancelStyle(premenu);
//只有在不是当前功能的时候才进行变化
var lis=document.all["lImage"];
var element=event.srcElement;
var i=getLinkP(tag);
var obj,obj1;
	if(i==const_pm)
	{
		obj=lis;
		obj1=document.all["tdLink"];
	}
	else
		if(i>=0){
			obj=lis[i];
			obj1=document.all["tdLink"][i];
		}
if(i>=0)
{		
	premenu=tag;
	//*************************
	//修改当前点击的菜单的样式
	obj1.className="leftclick";
	obj.oSrc=obj.src; 
	obj.src="../images/left_button02.jpg";
	//查找new图片，并删除
	var newobj=document.getElementById("new"+tag);
	//这块代码报错，还没有进行处理。
	if(newobj!=undefined)
	{
		//document.body.removeChild(newobj);
	}
	//在脚本中写的target与直接用<a href="" target"">还不一样，此处还没有判断_parent,_blank等这样的特殊的。
	if(tag=="00000601")//一个特殊的模块，需要弹出新窗口
		planWin=window.open(pagename,"planmake","left=0,top=0,width="+screen.width+",height="+screen.height);
	else
		eval("parent."+target+".location.href=\""+pagename+"\"");
	//=================================
}
//else//--只有一个菜单图片的时候
//	obj.src="../images/left_dian01.jpg";
}
//这个函数专门为子菜单定制的，用于点击子菜单的时候，修改子菜单的样式。
//所有的子菜单连接都默认样式为h1，修改后的样式为topclick
function markMenu(obj,pagename,target)
{
	//在前面添加收缩子菜单的代码。
	if((premenu!=obj.tag)){
	cancelSubStyle(premenu);
	cancelStyle(premenu);
	//判断一个子菜单或者多个子菜单的情况
	if(document.all["linkPC"].length!=undefined)
	{
		for(var i=0;i<document.all["linkPC"].length;i++)
			document.all["linkPC"].className="image";
	}
	else
		document.all["linkPC"].className="toplink";
	eval("parent."+target+".location.href=\""+pagename+"\"");
	premenu=obj.tag;
	//清除所有父级菜单的选中样式.
	obj.className="topclick";
	}
}


//在大图片窗口中显示小图片
function showPic(id)
{
	dcclImg.src="../images/dccl0"+id+".png";
}
var __sto = setTimeout;
window.setTimeout = function(callback,timeout,param)
{
    var args = Array.prototype.slice.call(arguments,2);
    var _cb = function()
    {
        callback.apply(null,args);
    }
    
    __sto(_cb,timeout);
}
//动态生成弹出的信息对象。
//wpage弹出的窗口内嵌的页面，itype弹出内容的类型2为调度信息，0为检修，1为调度日报上报提醒，3为日负荷上报提醒。
function showWindowRB(wpage,itype)
{
	var stitle="";//展示的窗口标题
	var strHTML="";
	var left=(parent.document.body.clientWidth-iwidth);
	var top=(parent.document.body.clientHeight-50);
	switch(itype)
	{
		case 2:
			return(window.open(wpage,"diaodu","resizable=false,status=no,top="+top+",left="+left+",toolbar=no,directories=no,menubar=no,width="+iwidth+",height=150",true));
			stitle="新调度指令<img src=\"images/new.gif\" align=\"absmiddle\">";
			strHTML="<table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"biaodan-bg\"><tr height=24 class=\"biaodan-tt\"><td class=\"zhuxiao\" >"
				+stitle+"</td></tr><tr>"
				+"<td><iframe src=\""+wpage+"\" width=100% height=100 frameborder=\"NO\" border=\"0\" framespacing=\"0\"></iframe>"
				+"</td></tr><tr><td bgcolor=\"#FFFFFF\" align=\"right\"><a href=\"javascript:closeWindows("+iws+");\">关闭窗口</a></td></tr>"
				+"</table>"
			break;
		case 1:
			stitle="调度日报上报提醒";
			strHTML="<table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"biaodan-bg\"><tr height=24 class=\"biaodan-tt\"><td class=\"zhuxiao\" >"
				+stitle+"</td></tr><tr>"
				+"<td height=100 bgcolor=\"#FFFFFF\" class=\"zhuxiao\" >请在17:30分前上报本日的调度日报。"
				+"</td></tr><tr><td bgcolor=\"#FFFFFF\" align=\"right\"><a href=\"javascript:closeWindows("+iws+");\">关闭窗口</a></td></tr>"
				+"</table>"
			break;
		case 3:
			stitle="日负荷上报提醒";
			strHTML="<table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"biaodan-bg\"><tr height=24 class=\"biaodan-tt\"><td class=\"zhuxiao\" >"
				+stitle+"</td></tr><tr>"
				+"<td height=100 bgcolor=\"#FFFFFF\" class=\"zhuxiao\" >请在17:30分前上报本日的日负荷。"
				+"</td></tr><tr><td bgcolor=\"#FFFFFF\" align=\"right\"><a href=\"javascript:closeWindows("+iws+");\">关闭窗口</a></td></tr>"
				+"</table>"
			break;
		default:
			stitle="新检修计划<img src=\"images/new.gif\" align=\"absmiddle\">";
			strHTML="<table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"biaodan-bg\"><tr height=24 class=\"biaodan-tt\"><td class=\"zhuxiao\" >"
				+stitle+"</td></tr><tr>"
				+"<td><iframe src=\""+wpage+"\" width=100% height=100 frameborder=\"NO\" border=\"0\" framespacing=\"0\"></iframe>"
				+"</td></tr><tr><td bgcolor=\"#FFFFFF\" align=\"right\"><a href=\"javascript:closeWindows("+iws+");\">关闭窗口</a></td></tr>"
				+"</table>"
			break;
	}
	//当前的div对象
	var objw;
	//动态生成div<td><img class=\"image\" src=\"images/close.gif\" onclick="javascript:closeWindow("+objw+");"></td>
	//只有一个parent.divMessage
		if((iws==0)||(parent.divMessage.length==undefined)||(parent.divMessage.length<=iws))
		{
			objw=parent.document.createElement("div"); 
			objw.id="divMessage";
			objw.style.zindex=(iws+1)*10;
			objw.style.postion="absolute";
			objw.className="biaodan-div";
			objw.style.width=200; 
			objw.innerHTML="";
			objw.innerHTML+=strHTML; 
			parent.document.body.appendChild(objw); 
//			alert(objw.innerHTML);
/*			var objw1=parent.document.createElement("table"); 
			objw.appendChild(objw1);
			objw1.style.width="100%";
			objw1.className="biaodan-bg";
			var row1=parent.document.createElement("row"); 
			objw1.appendChild(row1); 
			row1.className="biaodan-tt";
			var cell1=parent.document.createElement("cell"); 
			row1.appendChild(row1); 
			cell1.className="zhuxiao";
			cell1.innerHTML="hi";*/
		}
		else
		{
			objw=parent.divMessage[iws];
			objw.innerHTML="";
			objw.innerHTML+=strHTML; 
		}
	iws++; 
	iallheight=iws*iheight;
	left=(parent.document.body.clientWidth-iwidth);
	top=(parent.document.body.clientHeight-iallheight);
	objw.style.left=100;
	objw.style.top=300;
	objw.style.visibility="";
	window.setTimeout(closeWindow,wclose,objw);
}

function closeWindows(iwsp)
{
	var objw;
	if(parent.divMessage.length==undefined)	
		objw=parent.divMessage;
	else
		objw=parent.divMessage[iwsp];	
	closeWindow(objw);
}

function closeWindow(objw)
{
	//document.body.removeChild(objw);
	objw.style.visibility="hidden";
//	window.status+=iws;
	iws--;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//返回上一窗口
function historyBack()
{
	window.history.back();
}


//关闭层，带有过程的关闭
var divh=224;
function showDiv(div1,div2)
{
	if(parseInt(div1.style.height)>1)
		closeDiv(div1,div2);
	else
		openDiv(div1,div2)
}
function closeDiv(div1,div2)
{
	div2.style.top=parseInt(div1.style.height)+parseInt(div2.style.top)-23; 
	div1.style.height=1; 
	var obj=event.srcElement;
	obj.src="../images/c1.gif";
}
function openDiv(div1,div2)
{ 
	div1.style.height=divh; 
	div2.style.top=parseInt(div2.style.top)-divh+23; 
	div2.style.height=divh;
	var obj=event.srcElement;
	obj.src="../images/c2.gif";
}
var trs;
function showH(trname)
{ 
	var trs=document.all[trname];
	for(var i=1;i<trs.length;i++)
	{
		if(trs[i].style.display=="none")
		{
			trs[i].style.display="";
		}
		else
			trs[i].style.display="none";
	}
}

//控制电厂出力div
//clmethod保存出力方式,clvalue保存出力值,cltype出力方式，这里主要指的是负荷上限或者下限0为上限，1为下限
var clpm=0,clpv=0,clpt=0;//分别保存出力方式，出力值和上下限
var cldcid,cldcmc;//保存电厂id,电厂名称
function showClfsDiv(clmethod,clvalue,cltype,dcid,dcmc)
{
//	divClfs.style.left=(document.body.clientWidth-parseInt(divClfs.style.width))/2; 
	clpv=clvalue;
	clpt=cltype;
	cldcid=dcid;
	cldcmc=dcmc;
	makeDivCenter('divClfs');
//	divClfs.style.visibility="";
	var rm=document.all["style"]; 
	for(var i=0;i<rm.length;i++)
	{
		if(parseInt(rm[i].value)==clmethod)
		{
			rm[i].checked=true;
			changeContent(clmethod);
		}
	}
	tdMethodTitle.innerHTML="设置 "+dcmc+" 出力方式";
}
function closeClfsDiv(divname)
{var obj=document.getElementById(divname);
	obj.style.visibility="hidden";
}

function makeDivCenter(divname)
{
	var obj=document.getElementById(divname);
	obj.style.left=(document.body.clientWidth-parseInt(divClfs.style.width))/2; 
	obj.style.visibility="";
}
//根据出力方式不同，显示不同的内容
function changeContent(clmethod)
{
	clpm=clmethod;
	switch(clmethod)
	{
		case 1://固定出力和范围出力都显示文字提示。
			tdMethod.innerHTML="点击确定按钮后，进入<b>固定出力</b>设置界面。";
			formClfs.action="fastnessedit.html";
			break;
		case 3://日电量
			tdMethod.innerHTML="<input value=\""+clpv+"\"> MW";
			formClfs.action="styleList.html";
			break;
		case 4://负荷率
			if(clpt==0)//上限
			{
				tdMethod.innerHTML="<input type=checkbox value=0 checked>上限<input value=\""+clpv+"\" size=4> ";
				tdMethod.innerHTML+="<input type=checkbox value=1>下限<input value=\"\" size=4> 数值在1-100之间。";
			}
			else
			if(clpt==1)//下限
			{
				tdMethod.innerHTML="<input type=checkbox value=0>上限<input value=\""+clpv+"\" size=4> ";
				tdMethod.innerHTML+="<input type=checkbox value=1 checked>下限<input value=\"\" size=4> 数值在1-100之间。";
			}
			else//上下限都有
			{
				tdMethod.innerHTML="<input type=checkbox value=0 checked>上限<input value=\""+clpv+"\" size=4> ";
				tdMethod.innerHTML+="<input type=checkbox value=1 checked>下限<input value=\"\" size=4> 数值在1-100之间。";
			}
			formClfs.action="styleList.html";
			break;
		default://默认出力范围
			tdMethod.innerHTML="点击确定按钮后，进入<b>出力范围</b>设置界面。";
			formClfs.action="fastnetbound.html";
			break;
	}
}
//根据选择的出力方式提交页面。
function checkForm()
{
	formClfs.submit();
}
//通过脚本接收静态页面传过来的参数，只局限于pagename?parm=&parm1=这种形式。
function getParameter(param)
{
var query = window.location.search;
if(query!="#")
{
var iLen = param.length;
var iStart = query.indexOf(param);
if (iStart == -1)
   return "";
iStart += iLen + 1;
var iEnd = query.indexOf("&", iStart);
if (iEnd == -1)
   return query.substring(iStart);

return query.substring(iStart, iEnd);
}
else
	return("");
}

function clickMenuS(tag,subm)
{
	var hrLinks=document.all["hrLink"]; 
	for(var i=0;i<hrLinks.length;i++)
	{
		if(hrLinks[i].tag==tag)
		{
			//本来要在此修改点击主菜单的时候，可以初始化子菜单选中的模块，没有成功===lsz
			hrLinks[i].click();
			break;
		}
	}
}
//当前点击的菜单
var prei=-1;
function clickMenu(pagename,target,par,tag)
{ 
	if(tag!="")
	{
		if(par!="")
			if(pagename.indexOf("?")>=0)
				pagename+="&"+par;
			else
				pagename+="?"+par;	
		//alert(pagename);
		//var module=getParameter("moduleId");
		//if(module!=""){
		//	if(par!="")
		//		par+="&";
		//	//par=par+"module="+module;
		//}
	}
	var hrLinks=document.all["hrLink"]; 
	if(hrLinks.length!=undefined)
		for(var i=0;i<hrLinks.length;i++)
		{
			if(hrLinks[i].tag==tag)
			{
				if(prei!=i)
				{
				prei=i;
				hrLinks[i].className="topclick";
				/*imgLeft[i].oSrc=imgLeft[i].src;
				imgLeft[i].src="images/sec_topn01a.jpg";
				tdMiddle[i].background="images/sec_topn02a.jpg";
				imgRight[i].oSrc=imgRight[i].src;
				imgRight[i].src="images/sec_topn03a.jpg";*/
				switch(target)
				{
					case '_parent':
						eval("parent.location.href=\""+pagename+"\"");
						break;
					default:
						eval(target+".location.href=\""+pagename+"\"");
					}
				}
				else
					break;
			}
			else
			{
				if(hrLinks[i].className!="image")
					hrLinks[i].className="image";
			/*	if(imgLeft[i].oSrc!=undefined)
				{
				imgLeft[i].src=imgLeft[i].oSrc;
				tdMiddle[i].background="images/sec_topn02.jpg"; 
				imgRight[i].src=imgRight[i].oSrc;
				//tdMiddle[i].innerHTML=tdMiddle[i].tag; 
				}*/
			}
		}
	else
	{
			hrLinks.className="topclick";
			switch(target)
			{
				case '_parent':
					eval("parent.location.href=\""+pagename+"\"");
					break;
				default:
					eval(target+".location.href=\""+pagename+"\"");
				}
	}
		
}
function logOut(pagename)
{
	if (confirm("您确定要退出系统吗？")){
		eval("parent.location.href=\""+pagename+"\"");
	}else{
		 return false;
	}
}
//控制图形的是否可见
function controlPic(picname)
{
	var obj=document.getElementById(picname);
	if(event.srcElement.checked)
		obj.style.display="";
	else
		obj.style.display="none";
}


//一下函数是电方电厂页面中用到的公用函数
//执行删除操作的路径，删除对象的id，提交的form，删除对象类型（1电厂，2机组，3、省际输电线路，4、节点信息，5、线路信息，6、断面信息，7、变压器管理）
function onDelSub(url,id,form,type) {
	var sname="电厂";
switch(type)
{	
	case 7:
		sname="变压器管理";
		break;
	case 6:
		sname="断面信息";
		break;
	case 5:
		sname="线路信息";
		break;
	case 4:
		sname="节点信息";
		break;
	case 3:
		sname="省际输电线路";
		break;
	case 2:
		sname="机组";
		break;
	case 11:
		sname="用户";
		break;
	case 12:
		sname="角色";
		break;
	case 15:
		sname="断面";
		break;
	case 16:
		sname="变压器";
		break;
	case 17:
		sname="缺陷";
		break;
	case 18:
		sname="设备台帐";
		break;
	case 20:
		sname="发电集团";
		break;
  case 21:
		sname="信息";
		break;		
	case 23:
		sname="送出线路";
		break;
	case 24:
		sname="栏目";
		break;
	case 26:
		sname="缺陷性质";
		break;
	default:
		sname="电厂";
		break;
}
if(confirm("确认要删除选中的 "+sname+" 吗？？\n\n注:此操作不能恢复。")==1){
   var urll =url;
   urll +="&id=" + id;
   form.action = urll;
   form.submit();   }
else
	return;
}
//**根据选择的年份、月份对后面的日列表进行更改
  var lunarCalendar = 0;
  var imgDir = "../js/calendar/";
  function changeDay(name,nowForm){
    var date = name.split("-");
    var year="year";
    var month="month";
    var day="day";
    if(date.length==1 && date[0] !=""){
      year=name+"Year";
      month=name+"Month";
      day=name+"Day";
    }else if(date.length == 3){
      year = date[0];
      month = date[1];
      day = date[2];
    }
    if(nowForm.elements[month] && nowForm.elements[day]){
      var year = nowForm.elements[year].options[nowForm.elements[year].selectedIndex].value;
      var month = nowForm.elements[month].options[nowForm.elements[month].selectedIndex].value;
      var optionLength = nowForm.elements[day].options.length;
      var dayLength;
      var daySelectedIndex = nowForm.elements[day].selectedIndex;
      if(year % 4 == 0 && month == 2){
        dayLength = 29;
      }else if(year % 4 != 0 && month == 2){
        dayLength = 28;
      }else if(month == 4 || month == 6 || month == 9 || month == 11){
        dayLength = 30;
      }else{
        dayLength = 31;
      }
      if(optionLength > dayLength){
        for(var i = 0; i < optionLength - dayLength; i++){
          nowForm.elements[day].options[nowForm.elements[day].options.length - 1] = null;
        }
      }else if(optionLength < dayLength){
        for(var i = 0; i < dayLength - optionLength; i++){
          nowForm.elements[day].options[nowForm.elements[day].options.length] = new Option(nowForm.elements[day].options.length + 1, nowForm.elements[day].options.length + 1);
        }
      }
      if(daySelectedIndex >= nowForm.elements[day].options.length){
        nowForm.elements[day].selectedIndex = nowForm.elements[day].options.length - 1;
      }
    }
  }
   //判断起始日期是否大于终止日期，如果起始日期>终止日期，将不能提交
   //这个方法有缺陷就是用的form[0]进行判断。
  function compareDate()
  {
  	
   //判断起始日期是否大于终止日期，如果起始日期>终止日期，将不能提交
   var startDay=document.forms[0].startYear.value+document.forms[0].startMonth.value+document.forms[0].startDay.value;
   var endDay=document.forms[0].endYear.value+document.forms[0].endMonth.value+document.forms[0].endDay.value;
	if(startDay>endDay)
	{alert("终止日期不能小于起始日期，请重新选择！");
	return false;
	}
	else
		return true;
	}

//在notice中显示保存的信息
function showMessage(title)
{
	notice.innerHTML=title;
	notice.style.visibility="visible";
	setTimeout("hideNotice()",2000);
}
function hideNotice()
{
	notice.style.visibility="hidden";
}

function checkChild()
{
	var obj=event.srcElement;
	var tagobj=obj.value;
	var arr1=tagobj.split('-');
	for(var i=0;i<document.all("moduleIds").length;i++)
	{
		var arr2=document.all("moduleIds")[i].value.split('-');
		//找到模块下的子模块，并将其一起选中或者不选中。
		if(arr2[1].substring(0,arr1[1].length)==arr1[1])
			document.all("moduleIds")[i].checked=obj.checked;
		}
	}
	//点击图片，同时将左边菜单和上面菜单隐藏
	function hideShowAll(){
    if((parent.document.all.frameset1.cols =="0,5,*")&&(parent.document.all.mainset.rows =="0,5,*")){
        parent.topCloseFrame.document.getElementById("close").style.display="";
        parent.topCloseFrame.document.getElementById("open").style.display="none";
        parent.closeLeft.document.getElementById("close").style.display="";
        parent.closeLeft.document.getElementById("open").style.display="none";
    parent.document.all.frameset1.cols = "158,5,*";
    parent.document.all.mainset.rows = "75,5,*";
  }
  else
  	{
        parent.topCloseFrame.document.getElementById("close").style.display="none";
        parent.topCloseFrame.document.getElementById("open").style.display="";
        parent.closeLeft.document.getElementById("close").style.display="none";
        parent.closeLeft.document.getElementById("open").style.display="";
  	parent.document.all.frameset1.cols ="0,5,*";
  	parent.document.all.mainset.rows ="0,5,*";
  		}
	}

	function hideshowLeft(){
    if(parent.document.all.frameset1.cols =="0,5,*"){
        document.getElementById("close").style.display="";
        document.getElementById("open").style.display="none";
    }else{
        document.getElementById("close").style.display="none";
        document.getElementById("open").style.display="";
    }
    parent.document.all.frameset1.cols = parent.document.all.frameset1.cols =="0,5,*" ? "158,5,*" : "0,5,*";
	}

	function hideshowTop(){
    if(parent.document.all.mainset.rows =="0,5,*,0"){
        document.getElementById("close").style.display="";
        document.getElementById("open").style.display="none";
    }else{
        document.getElementById("close").style.display="none";
        document.getElementById("open").style.display="";
    }
    parent.document.all.mainset.rows = parent.document.all.mainset.rows =="0,5,*,0" ? "75,5,*,23" : "0,5,*,0";
	}
//显示窗口，page页面，iwidth宽度，iheight高度，itype类型0表示窗口居中
function showWindow(page,iwidth,iheight,itype)
{
	var left=(screen.width-iwidth)/2;
	var top=(screen.Height-iheight)/2-20;
	switch(itype)
	{
		case 0:		
		window.open(page,"","resizable=false,status=no,top="+top+",left="+left+",toolbar=no,directories=no,menubar=no,width="+iwidth+",height="+iheight,true);
		break;
		default:
		break;
		}
	}
	function showChild(tag)
	{
	//var tag1=tag.substring(0,tag.length-2);
	//showChild1(tag1);
	//收缩同级的菜单;
	for(var i=0;i<tblLink.length;i++)
	{if((tblLink[i].tag.length>=tag.length)&&(tblLink[i].tag!=tag))
		hideChild1(tblLink[i].tag);}
	showChild1(tag);
	}
	//缩菜单
	function hideChild1(tag)
	{
	//先收缩所有其他展开的。
	var j=-1;
		for(var i=0;i<tblLink.length;i++)
		{
			if((tblLink[i].tag.indexOf(tag)==0)&&(tblLink[i].tag!=tag))
			{
				if(j==-1)
				{			
					j=i;
					//if(tblLink[i].style.display=="")
				    		tblLink[i].style.display="none";
				  	//else
				  	//	tblLink[i].style.display="";
				}
				else
					tblLink[i].style.display=tblLink[j].style.display;
			}		
		}
	}
	//点击左边菜单，收缩或者展开子菜单。
	function showChild1(tag)
	{
	//先收缩所有其他展开的。
	var j=-1;
		for(var i=0;i<tblLink.length;i++)
		{
			if((tblLink[i].tag.indexOf(tag)==0)&&(tblLink[i].tag!=tag)&&(tblLink[i].tag.length==tag.length+2))
			{
				if(j==-1)
				{			
					j=i;
					if(tblLink[i].style.display=="")
				    tblLink[i].style.display="none";
				  else
				  	tblLink[i].style.display="";
				}
				else
					tblLink[i].style.display=tblLink[j].style.display;
			}		
		}
	}
//打开窗口脚本，在index.jsp特殊用到的

function winOpen(url){
	if(mainWin==undefined)
		mainWin=window.open(url,'','toolbar=no,menubar=no,location=no,status=no,width=' + screen.width + ',height=' + screen.height + ',left=0,top=0,resizable=1'); 
	else
	try{
		if(mainWin.location.href.indexOf(url)<0)
		mainWin.location.href=url;
		mainWin.focus();
	}
	catch(e){
		mainWin=window.open(url,'','toolbar=no,menubar=no,location=no,status=no,width=' + screen.width + ',height=' + screen.height + ',left=0,top=0,resizable=1'); 
	}
}
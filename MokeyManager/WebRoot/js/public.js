//*дһЩ��ҳ����ܻ��õ��Ĺ��ú�����
var curI=0;//��ǰĬ�ϵ���Ķ���
//�����½���ʾ����
var iws=0;//��ʾ�Ĵ��ڸ�����
var iallheight=0;//ĿǰΪֹ���ڵ��ܸ߶ȣ�����Ĵ��ڽ��ۼ�������ʾ��
var iwidth=200;//���ڵĿ�ȶ���200
var iheight=147;
var const_pm=999;//Ĭ��Ϊ����ֻ��һ������������
var wclose=10000;
var planWin;//�����ռƻ����ڣ��ٴε�����Զ��۽�
var mainWin;//�����������ڵĽ��㡣
//��ǰ����Ĳ˵�������ظ���������ٽ��з�Ӧ
//������һ������Ĳ˵���tag
var premenu="-1",presubmenu="";

function closeOpenWindow()
{window.close();}
//ͨ��tag�ҵ���������Ǹ�����ģ�顣
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
		else//ֻ��һ���˵�
			if(document.all["linkP"].tag==tag)
			{
				//999��������ֻ��һ�����󣬲������顣
			return(const_pm);
			}
	return(-1);
}
//��߲˵������ʱ���ʼ������Ĳ˵�
//���û�д���ģ����Ϣ����ôĬ�ϵ�����ǵ�һ���˵�
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
//��ʼ���ұ�ҳ��
//�����Ժ���Ҫ�޸�����mainframe��д���ģ�Ӧ�ÿ��Դ���
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
			//Ĭ�ϵ�һ���˵�
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
//ȡ���˵�����ʽ
function cancelStyle(tag)
{
	//ֻ���ڲ��ǵ�ǰ���ܵ�ʱ��Ž��б仯
	var lis=document.all["lImage"];	
	if(lis!=undefined)
	{
		var i=getLinkP(tag);
		if(i>=0)
		{
			if(i==999)
			{
				//�ָ���һ������Ĳ˵���״̬��
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
				//�ָ���һ������Ĳ˵���״̬��
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

//ȡ���Ӳ˵�����ʽ
function cancelSubStyle(tag)
{
	//ֻ���ڲ��ǵ�ǰ���ܵ�ʱ��Ž��б仯
	var lis=document.all["linkPC"];
	if(lis!=undefined)
		//�ָ���һ������Ĳ˵���״̬��
		for(var j=0;j<lis.length;j++)
		{
			if(lis[j].tag==tag)
			{
				lis[j].className="image";
			}
		}
}
//���ÿһ����ߵĲ˵�ʱ���������¼���
//tag���˵���tag��pagename��Ӧ��ҳ�棬target��ҳ����������ơ�
function clickBg(tag,pagename,target)
{
if(tag==premenu)
{
	if(tag=="00000601")
		try{
			//���¾۽�
			planWin.focus();
			return;
		}
		catch(e){}
	else
		return;
}
cancelSubStyle(premenu);
cancelStyle(premenu);
//ֻ���ڲ��ǵ�ǰ���ܵ�ʱ��Ž��б仯
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
	//�޸ĵ�ǰ����Ĳ˵�����ʽ
	obj1.className="leftclick";
	obj.oSrc=obj.src; 
	obj.src="../images/left_button02.jpg";
	//����newͼƬ����ɾ��
	var newobj=document.getElementById("new"+tag);
	//�����뱨����û�н��д���
	if(newobj!=undefined)
	{
		//document.body.removeChild(newobj);
	}
	//�ڽű���д��target��ֱ����<a href="" target"">����һ�����˴���û���ж�_parent,_blank������������ġ�
	if(tag=="00000601")//һ�������ģ�飬��Ҫ�����´���
		planWin=window.open(pagename,"planmake","left=0,top=0,width="+screen.width+",height="+screen.height);
	else
		eval("parent."+target+".location.href=\""+pagename+"\"");
	//=================================
}
//else//--ֻ��һ���˵�ͼƬ��ʱ��
//	obj.src="../images/left_dian01.jpg";
}
//�������ר��Ϊ�Ӳ˵����Ƶģ����ڵ���Ӳ˵���ʱ���޸��Ӳ˵�����ʽ��
//���е��Ӳ˵����Ӷ�Ĭ����ʽΪh1���޸ĺ����ʽΪtopclick
function markMenu(obj,pagename,target)
{
	//��ǰ����������Ӳ˵��Ĵ��롣
	if((premenu!=obj.tag)){
	cancelSubStyle(premenu);
	cancelStyle(premenu);
	//�ж�һ���Ӳ˵����߶���Ӳ˵������
	if(document.all["linkPC"].length!=undefined)
	{
		for(var i=0;i<document.all["linkPC"].length;i++)
			document.all["linkPC"].className="image";
	}
	else
		document.all["linkPC"].className="toplink";
	eval("parent."+target+".location.href=\""+pagename+"\"");
	premenu=obj.tag;
	//������и����˵���ѡ����ʽ.
	obj.className="topclick";
	}
}


//�ڴ�ͼƬ��������ʾСͼƬ
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
//��̬���ɵ�������Ϣ����
//wpage�����Ĵ�����Ƕ��ҳ�棬itype�������ݵ�����2Ϊ������Ϣ��0Ϊ���ޣ�1Ϊ�����ձ��ϱ����ѣ�3Ϊ�ո����ϱ����ѡ�
function showWindowRB(wpage,itype)
{
	var stitle="";//չʾ�Ĵ��ڱ���
	var strHTML="";
	var left=(parent.document.body.clientWidth-iwidth);
	var top=(parent.document.body.clientHeight-50);
	switch(itype)
	{
		case 2:
			return(window.open(wpage,"diaodu","resizable=false,status=no,top="+top+",left="+left+",toolbar=no,directories=no,menubar=no,width="+iwidth+",height=150",true));
			stitle="�µ���ָ��<img src=\"images/new.gif\" align=\"absmiddle\">";
			strHTML="<table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"biaodan-bg\"><tr height=24 class=\"biaodan-tt\"><td class=\"zhuxiao\" >"
				+stitle+"</td></tr><tr>"
				+"<td><iframe src=\""+wpage+"\" width=100% height=100 frameborder=\"NO\" border=\"0\" framespacing=\"0\"></iframe>"
				+"</td></tr><tr><td bgcolor=\"#FFFFFF\" align=\"right\"><a href=\"javascript:closeWindows("+iws+");\">�رմ���</a></td></tr>"
				+"</table>"
			break;
		case 1:
			stitle="�����ձ��ϱ�����";
			strHTML="<table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"biaodan-bg\"><tr height=24 class=\"biaodan-tt\"><td class=\"zhuxiao\" >"
				+stitle+"</td></tr><tr>"
				+"<td height=100 bgcolor=\"#FFFFFF\" class=\"zhuxiao\" >����17:30��ǰ�ϱ����յĵ����ձ���"
				+"</td></tr><tr><td bgcolor=\"#FFFFFF\" align=\"right\"><a href=\"javascript:closeWindows("+iws+");\">�رմ���</a></td></tr>"
				+"</table>"
			break;
		case 3:
			stitle="�ո����ϱ�����";
			strHTML="<table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"biaodan-bg\"><tr height=24 class=\"biaodan-tt\"><td class=\"zhuxiao\" >"
				+stitle+"</td></tr><tr>"
				+"<td height=100 bgcolor=\"#FFFFFF\" class=\"zhuxiao\" >����17:30��ǰ�ϱ����յ��ո��ɡ�"
				+"</td></tr><tr><td bgcolor=\"#FFFFFF\" align=\"right\"><a href=\"javascript:closeWindows("+iws+");\">�رմ���</a></td></tr>"
				+"</table>"
			break;
		default:
			stitle="�¼��޼ƻ�<img src=\"images/new.gif\" align=\"absmiddle\">";
			strHTML="<table width=\"100%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"biaodan-bg\"><tr height=24 class=\"biaodan-tt\"><td class=\"zhuxiao\" >"
				+stitle+"</td></tr><tr>"
				+"<td><iframe src=\""+wpage+"\" width=100% height=100 frameborder=\"NO\" border=\"0\" framespacing=\"0\"></iframe>"
				+"</td></tr><tr><td bgcolor=\"#FFFFFF\" align=\"right\"><a href=\"javascript:closeWindows("+iws+");\">�رմ���</a></td></tr>"
				+"</table>"
			break;
	}
	//��ǰ��div����
	var objw;
	//��̬����div<td><img class=\"image\" src=\"images/close.gif\" onclick="javascript:closeWindow("+objw+");"></td>
	//ֻ��һ��parent.divMessage
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
//������һ����
function historyBack()
{
	window.history.back();
}


//�رղ㣬���й��̵Ĺر�
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

//���Ƶ糧����div
//clmethod���������ʽ,clvalue�������ֵ,cltype������ʽ��������Ҫָ���Ǹ������޻�������0Ϊ���ޣ�1Ϊ����
var clpm=0,clpv=0,clpt=0;//�ֱ𱣴������ʽ������ֵ��������
var cldcid,cldcmc;//����糧id,�糧����
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
	tdMethodTitle.innerHTML="���� "+dcmc+" ������ʽ";
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
//���ݳ�����ʽ��ͬ����ʾ��ͬ������
function changeContent(clmethod)
{
	clpm=clmethod;
	switch(clmethod)
	{
		case 1://�̶������ͷ�Χ��������ʾ������ʾ��
			tdMethod.innerHTML="���ȷ����ť�󣬽���<b>�̶�����</b>���ý��档";
			formClfs.action="fastnessedit.html";
			break;
		case 3://�յ���
			tdMethod.innerHTML="<input value=\""+clpv+"\"> MW";
			formClfs.action="styleList.html";
			break;
		case 4://������
			if(clpt==0)//����
			{
				tdMethod.innerHTML="<input type=checkbox value=0 checked>����<input value=\""+clpv+"\" size=4> ";
				tdMethod.innerHTML+="<input type=checkbox value=1>����<input value=\"\" size=4> ��ֵ��1-100֮�䡣";
			}
			else
			if(clpt==1)//����
			{
				tdMethod.innerHTML="<input type=checkbox value=0>����<input value=\""+clpv+"\" size=4> ";
				tdMethod.innerHTML+="<input type=checkbox value=1 checked>����<input value=\"\" size=4> ��ֵ��1-100֮�䡣";
			}
			else//�����޶���
			{
				tdMethod.innerHTML="<input type=checkbox value=0 checked>����<input value=\""+clpv+"\" size=4> ";
				tdMethod.innerHTML+="<input type=checkbox value=1 checked>����<input value=\"\" size=4> ��ֵ��1-100֮�䡣";
			}
			formClfs.action="styleList.html";
			break;
		default://Ĭ�ϳ�����Χ
			tdMethod.innerHTML="���ȷ����ť�󣬽���<b>������Χ</b>���ý��档";
			formClfs.action="fastnetbound.html";
			break;
	}
}
//����ѡ��ĳ�����ʽ�ύҳ�档
function checkForm()
{
	formClfs.submit();
}
//ͨ���ű����վ�̬ҳ�洫�����Ĳ�����ֻ������pagename?parm=&parm1=������ʽ��
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
			//����Ҫ�ڴ��޸ĵ�����˵���ʱ�򣬿��Գ�ʼ���Ӳ˵�ѡ�е�ģ�飬û�гɹ�===lsz
			hrLinks[i].click();
			break;
		}
	}
}
//��ǰ����Ĳ˵�
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
	if (confirm("��ȷ��Ҫ�˳�ϵͳ��")){
		eval("parent.location.href=\""+pagename+"\"");
	}else{
		 return false;
	}
}
//����ͼ�ε��Ƿ�ɼ�
function controlPic(picname)
{
	var obj=document.getElementById(picname);
	if(event.srcElement.checked)
		obj.style.display="";
	else
		obj.style.display="none";
}


//һ�º����ǵ緽�糧ҳ�����õ��Ĺ��ú���
//ִ��ɾ��������·����ɾ�������id���ύ��form��ɾ���������ͣ�1�糧��2���飬3��ʡ�������·��4���ڵ���Ϣ��5����·��Ϣ��6��������Ϣ��7����ѹ������
function onDelSub(url,id,form,type) {
	var sname="�糧";
switch(type)
{	
	case 7:
		sname="��ѹ������";
		break;
	case 6:
		sname="������Ϣ";
		break;
	case 5:
		sname="��·��Ϣ";
		break;
	case 4:
		sname="�ڵ���Ϣ";
		break;
	case 3:
		sname="ʡ�������·";
		break;
	case 2:
		sname="����";
		break;
	case 11:
		sname="�û�";
		break;
	case 12:
		sname="��ɫ";
		break;
	case 15:
		sname="����";
		break;
	case 16:
		sname="��ѹ��";
		break;
	case 17:
		sname="ȱ��";
		break;
	case 18:
		sname="�豸̨��";
		break;
	case 20:
		sname="���缯��";
		break;
  case 21:
		sname="��Ϣ";
		break;		
	case 23:
		sname="�ͳ���·";
		break;
	case 24:
		sname="��Ŀ";
		break;
	case 26:
		sname="ȱ������";
		break;
	default:
		sname="�糧";
		break;
}
if(confirm("ȷ��Ҫɾ��ѡ�е� "+sname+" �𣿣�\n\nע:�˲������ָܻ���")==1){
   var urll =url;
   urll +="&id=" + id;
   form.action = urll;
   form.submit();   }
else
	return;
}
//**����ѡ�����ݡ��·ݶԺ�������б���и���
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
   //�ж���ʼ�����Ƿ������ֹ���ڣ������ʼ����>��ֹ���ڣ��������ύ
   //���������ȱ�ݾ����õ�form[0]�����жϡ�
  function compareDate()
  {
  	
   //�ж���ʼ�����Ƿ������ֹ���ڣ������ʼ����>��ֹ���ڣ��������ύ
   var startDay=document.forms[0].startYear.value+document.forms[0].startMonth.value+document.forms[0].startDay.value;
   var endDay=document.forms[0].endYear.value+document.forms[0].endMonth.value+document.forms[0].endDay.value;
	if(startDay>endDay)
	{alert("��ֹ���ڲ���С����ʼ���ڣ�������ѡ��");
	return false;
	}
	else
		return true;
	}

//��notice����ʾ�������Ϣ
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
		//�ҵ�ģ���µ���ģ�飬������һ��ѡ�л��߲�ѡ�С�
		if(arr2[1].substring(0,arr1[1].length)==arr1[1])
			document.all("moduleIds")[i].checked=obj.checked;
		}
	}
	//���ͼƬ��ͬʱ����߲˵�������˵�����
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
//��ʾ���ڣ�pageҳ�棬iwidth��ȣ�iheight�߶ȣ�itype����0��ʾ���ھ���
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
	//����ͬ���Ĳ˵�;
	for(var i=0;i<tblLink.length;i++)
	{if((tblLink[i].tag.length>=tag.length)&&(tblLink[i].tag!=tag))
		hideChild1(tblLink[i].tag);}
	showChild1(tag);
	}
	//���˵�
	function hideChild1(tag)
	{
	//��������������չ���ġ�
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
	//�����߲˵�����������չ���Ӳ˵���
	function showChild1(tag)
	{
	//��������������չ���ġ�
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
//�򿪴��ڽű�����index.jsp�����õ���

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
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://bluton.com/tag" prefix="bt" %> 
<html>
<head>
<title>Untitled Document</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #0F7FAD;
}
-->
</style>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
function hideshow(){
    if(parent.document.all.frameset1.cols =="0,5,*"){
        document.getElementById("close").style.display="";
        document.getElementById("open").style.display="none";
    }else{
        document.getElementById("close").style.display="none";
        document.getElementById("open").style.display="";
    }
    parent.document.all.frameset1.cols = parent.document.all.frameset1.cols =="0,5,*" ? "153,5,*" : "0,5,*";
}

</script>
</head>

<body onLoad="MM_preloadImages('../image/close02.jpg')">
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    <div id="close">
      <p><a href="#" onclick ="hideshow()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1','','../image/close02.jpg',1)"><img src="../image/close01.jpg" name="Image1" width="5" height="51" border="0"></a></p>    
    </div>
    <div id="open" style="display:none">
      <p><a href="#" onclick ="hideshow()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1','','../image/open02.jpg',1)"><img src="../image/open01.jpg" name="Image1" width="5" height="51" border="0"></a></p>
    </div>
    </td>
  </tr>
</table>
</body>
</html>

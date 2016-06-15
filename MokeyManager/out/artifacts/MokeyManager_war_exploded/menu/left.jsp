<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://bluton.com/tag" prefix="bt" %>
<html>
 <link rel="stylesheet" href="../css/leftCss.css" type="text/css">
 <style>
 <!--
 body {
	font-size: 12px;
	color: #000000;
	background-color: #FEFEFE;
	background-image: url(../image/middle_topbg.jpg);
	background-repeat: repeat-x;
	background-position: top;
	margin: 0px;
}
 //-->
 </style>
  <!-- 
   <script language='javascript' src="../js/table.js"></script>
   -->
 
  <script language='javascript' src="../js/public.js"></script>
  <script type="text/javascript">
  function showTrade(objId) { //v3.0

  var trade = document.getElementById(objId)
  if(trade.style.display == "none"){
     trade.style.display = "block"
  }else{
     trade.style.display = "none"
  }
} 
function changeColor(obj){
  for(i=0;i<document.all.ItemName.length;i++){
    document.all.ItemName[i].style.color ='#000000';
  }
  obj.style.color ='#000000';
  obj.style.bold="bold";
}
function getToday()
{
	var sdate=new Date();
	tdDate.innerHTML="今天是"+sdate.getYear()+"年"+(parseInt(sdate.getMonth())+1)+"月"+sdate.getDate()+"日";
}

  </script>
<head>
</head>
<body onload="javascript:initPage();">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td colspan="2" height="20"></td>
      </tr>
      <tr><td heigth="4"></td></tr>
    </table>
      
    </td>
  </tr>
</table>

</body>
</html>
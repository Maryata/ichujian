<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>mokey后台管理系統</title>
<link href="../css/ajaxtags.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="../css/displaytag.css" />

<script type="text/javascript" src="../js/popcalendar.js"></script>
<script type="text/javascript" src="../js/ajaxtagjs/prototype.js"></script>
<script type="text/javascript" src="../js/ajaxtagjs/scriptaculous/scriptaculous.js"></script>
<script type="text/javascript" src="../js/ajaxtagjs/ajax/ajaxtags.js"></script>
<script type="text/javascript" src="../js/ajaxtagjs/ajax/ajaxtags_controls.js"></script>
<script type="text/javascript" src="../js/ajaxtagjs/ajax/ajaxtags_parser.js"></script>

<script type="text/javascript" src="../js/flash_scripts/zingchart-1.1.min.js"></script>
 <script type="text/javascript"> 
    var dataurl="<%=request.getContextPath()+request.getAttribute("dataurl")%>";
    function winload(){
		zingchart.render({
			dataurl 		: "<%=request.getContextPath()+request.getAttribute("dataurl")%>",
			container 		: "zingchart",
			width			: "100%",
			height			: 450,
			liburl			: "<%=request.getContextPath()%>/js/flash_scripts/zingchart.swf",
			flashvars		: {allowlocal : 0},
			wmode           : 'opaque'
		});
	}
</script>
 
</head>

<body onload="winload();">
<form name="form1" method="post" action="<%=request.getContextPath()%>/anaylse/newsAppUseInfoAction.action">
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="../image/dian.jpg" width="9" height="9" /> 基礎信息 -&gt; ZingChart示範 </td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="1" cellspacing="0" class="tbg">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
        <tr> 
        
         <td height="23" class="biaodan-top" align="left" width="8%">時間：</td>
         <td width="10%" class="biaodan-q" align="left">&nbsp;
           </td>
         <td width="8%" class="biaodan-top" align="left" >公司：</td>
         <td width="25%" class="biaodan-q" align="left">&nbsp;
               
          </td>
         <td width="8%" class="biaodan-top" align="left" >风场：</td>
         <td width="25%" class="biaodan-q" align="left">&nbsp;<span id="fcIda" class="top">
            </span>
          </td>
         <td width="18%"  rowspan="2" align="center" class="biaodan-q">
           <input name="Button" type="submit" class="butt" value="查 询"/>
           </td>
        </tr>
        <tr> 
         <td  class="biaodan-top" align="left" >测风塔：</td>
         <td  class="biaodan-q" align="left">&nbsp;<span id="cftIda"  class="top">
            </span>
          </td>
         <td height="23" class="biaodan-top" align="left">日期：</td>
                  <td width="30%" class="biaodan-q">
         <td height="23" class="biaodan-top" align="left" >--：</td>
         <td class="biaodan-q" >&nbsp;&nbsp;--
         </td>
          </tr>
    </table></td>
  </tr>
</table>
<table width="98%" height="35"  border="0" align="center" cellpadding="1" cellspacing="tbg">
  <tr>
    <td align="right">单位：&nbsp;风向：-- &nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>
<div id="zingchart"></div></form>
</body>
</html>

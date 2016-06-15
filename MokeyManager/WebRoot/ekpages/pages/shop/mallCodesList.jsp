<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,user-scalable=no">
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<title>商品码列表</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<!--2015-4-29新样式，reCss.css样式一定要最后调用-->
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
</head>
<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; e键：商城 -&gt; 商品码详情 </td>
  </tr>
</table>

<form id="form1" method="post" >
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
	<input type="hidden" id="pid" name="pid" value="<s:property value="#request.pid"/>" />
  <tr>
    <td>
    	<table width="100%"  border="0" cellspacing="1" cellpadding="2">
	        <tr>
				<td width="8%"  height="23" class="biaodan-top" align="left">领取状态</td>
				<td width="15%" class="biaodan-q" align="left">
					<select style="width:100%;border:none;hight:100%;" id="type">
						<option></option>
						<option value="0">未兑换</option>
						<option value="1">已兑换</option>
					</select>
				</td>
	         <td rowspan="2" align="right" class="biaodan-q">
				 <input type="button" class="butt" value="查 询" onclick="infoList()" />
	         </td>
	        </tr>
		</table>
	</td>
  </tr>
</table>
</form>

<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
  <tr>
  </tr>
</table>

<form id="form2" method="post">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
  <thead>
	  <tr class="biaodan-top">
	    <td width="5%">ID</td>
	    <td width="10%">兑换码</td>
		<td width="15%">是否兑换</td>
		<td width="15%">是否有效</td>
	  </tr>
  </thead>
  <tbody id="mytable"></tbody>
</table>
<input type="hidden" id="cateTotal" />
<div id="page11"></div>
</form>
<script type="text/javascript">
	$(function(){
		infoList();
	});

	function infoList(){
		var type = $("#type").val();
		var pid =$("#pid").val();
		// 查询应用总数
		$.post("${pageContext.request.contextPath}/basedata/ekey/eKMallProductAction!getCodeList.action",{pid:pid,type:type},
				function(data){
					var json = $.parseJSON(data);
					var count = json[0].count;// 数据总数
					$("#page11").empty();
					if(count != 0){
						setTotal(count,"cateTotal");
						// 分页显示广告位
						getCatePage("${pageContext.request.contextPath}/basedata/ekey/eKMallProductAction!getCodeList.action",pid,type);
					}
				});
		// 查询广告位(第一页)
		$.post("${pageContext.request.contextPath}/basedata/ekey/eKMallProductAction!getCodeList.action",{pid:pid,type:type},
				function(data){
					var json = $.parseJSON(data);
					var list = json[0].list;
					$("#mytable").empty();
					$(list).each(function(){
						var _s = "<tr class='biaodan-q'><td align='center'>"+this.C_ID+"</td>";
						_s += "<td align='center'>"+(this.C_CODE||'')+"</td>";
						if(this.C_STATE=='0'){
							_s += "<td align='center' style='color: greenyellow'>未兑换</td>";
						}else{
							_s += "<td align='center' style='color: red'>已兑换</td>";
						}
						if(this.ISLIVE=='0'){
							_s += "<td align='center'>无效</td>";
						}else{
							_s += "<td align='center'>有效</td>";
						}
						_s  += "</tr>";
						$("#mytable").append(_s);
					});
				});
	}
// 设置总页数
function setTotal(count,pageTotal){
	var totalPage = 0;
	var rows = 20;// 每页显示条数
	totalPage = 1;
	// 计算总页数
	totalPage = parseInt((count - 1) / rows) + 1;
	$("#"+pageTotal).val(totalPage);
}
// 分页显示广告位
function getCatePage(url,pid,type) {
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("cateTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(),
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,pid:pid,type:type},
					function(data){
						var json = $.parseJSON(data);
						var list = json[0].list;
						$("#mytable").empty();
						$(list).each(function(){
							var _s = "<tr class='biaodan-q'><td align='center'>"+this.C_ID+"</td>";
							_s += "<td align='center'>"+(this.C_CODE||'')+"</td>";
							if(this.C_STATE=='0'){
								_s += "<td align='center' style='color: greenyellow'>未兑换</td>";
							}else{
								_s += "<td align='center' style='color: red'>已兑换</td>";
							}
							if(this.ISLIVE=='0'){
								_s += "<td align='center'>无效</td>";
							}else{
								_s += "<td align='center'>有效</td>";
							}
							_s  += "</tr>";
							$("#mytable").append(_s);
						});
				});
	        }
	    }
	});
}

</script>
</body>
</html>


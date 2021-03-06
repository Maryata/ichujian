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
<title>商品分类</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<!--2015-4-29新样式，reCss.css样式一定要最后调用-->
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
</head>
<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; 商城 -&gt; 商品分类维护 </td>
  </tr>
</table>

<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
  <tr>
  </tr>
</table>

<form id="form2" method="post">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
  <thead>
	  <tr class="biaodan-top">
	    <td width="10%">分类ID</td>
	    <td width="50%">分类名称</td>
	    <td >操作</td>
	  </tr>
  </thead>
  	<input type="hidden" id="editId" name="editId" />
  	<input type="hidden" id="editName" name="editName" />
  <tbody id="mytable"></tbody>
</table>
<input type="hidden" id="cateTotal" />
<div id="page11"></div>
</form>
<script type="text/javascript">

$(function(){
	// 查询应用总数
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKShopProductCateAction!productCateList.action",
		function(data){
			var json = $.parseJSON(data);
			var count = json[0].count;// 数据总数
			$("#page11").empty();
			if(count != 0){
				setTotal(count,"cateTotal");
				// 分页显示商品分类
				getCatePage("${pageContext.request.contextPath}/basedata/ekey/eKShopProductCateAction!productCateList.action");
			}
	});
	// 查询分类(第一页)
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKShopProductCateAction!productCateList.action",
		function(data){
			formData(data, "mytable");
	});
});
// 设置总页数
function setTotal(count,pageTotal){
	var rows = 5;// 每页显示条数
	// 计算总页数
	var totalPage = parseInt((count - 1) / rows) + 1;
	$("#"+pageTotal).val(totalPage);
}
// 分页显示商品分类
function getCatePage(url) {
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("cateTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr},
					function(data){
						formData(data, "mytable");
				});
	        }
	    }
	});
}

// 构建列表数据
function formData(data, table) {
	var json = $.parseJSON(data);
	var list = json[0].list;
	$("#" + table).empty();
	$(list).each(function(){
		var _s = "<tr class='biaodan-q'><td align='center'>"+this.C_ID+"</td>";
		_s += "<td align='center'>"+(this.C_NAME||'')+"</td>";
		_s += "<td align='center'><input type='button' value='维 护' class='butt' onClick='handleCate("+this.C_ID+",\""+this.C_NAME+"\")'/></td></tr>";
		$("#" + table).append(_s);
	});
}
// 分类分类
function handleCate(cid,cname){
	$("#form2").attr("action","${pageContext.request.contextPath}/basedata/ekey/eKShopProductCateAction!toHandle.action"); 
	$("#editId").val(cid);
	$("#editName").val(cname);
	$("#form2").submit();
}
</script>
</body>
</html>


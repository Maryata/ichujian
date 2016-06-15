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
<title>键位管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<!--2015-4-29新样式，reCss.css样式一定要最后调用-->
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
</head>
<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; e键：首页 -&gt; 键位管理维护 </td>
  </tr>
</table>

<form id="form1" method="post" action="${pageContext.request.contextPath}/basedata/ekey/eKIndexKeyAppAction!toAdd.action">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
  <tr>
    <td>
    	<table width="100%"  border="0" cellspacing="1" cellpadding="2">
	        <tr> 
	         <td rowspan="2" align="right" class="biaodan-q">
	           <input type="submit" class="butt" value="添 加" />
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
	    <td width="10%">ID</td>
	    <td width="20%">名称</td>
	    <td width="10%">标题</td>
		<td width="5%">键位</td>
		<td width="10%">供应商</td>
		<td width="10%">创建人</td>
		<td width="10%">创建日期</td>
		<td width="5%">序号</td>
	    <td >操作</td>
	  </tr>
  <input type="hidden" name="editId" id="editId"/>
  </thead>
  <tbody id="mytable"></tbody>
</table>
<input type="hidden" id="cateTotal" />
<div id="page11"></div>
</form>
<script type="text/javascript">

$(function(){
	// 查询应用总数
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKIndexKeyAppAction!ekIndexKeyAppList.action",
		function(data){
			var json = $.parseJSON(data);
			var count = json[0].count;// 数据总数
			$("#page11").empty();
			if(count != 0){
				setTotal(count,"cateTotal");
				// 分页显示键位管理
				getCatePage("${pageContext.request.contextPath}/basedata/ekey/eKIndexKeyAppAction!ekIndexKeyAppList.action");
			}
	});
	// 查询键位管理(第一页)
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKIndexKeyAppAction!ekIndexKeyAppList.action",
		function(data){
			var json = $.parseJSON(data);
			var list = json[0].list;
			$("#mytable").empty();
			$(list).each(function(){
				var _s = "<tr class='biaodan-q'><td align='center'>"+this.C_ID+"</td>";
				_s += "<td align='center'>"+(this.C_NAME||'')+"</td>";
				_s += "<td align='center'>"+(this.C_TITLE||'')+"</td>";
				_s += "<td align='center'>"+(this.C_KEY||'')+"</td>";
				_s += "<td align='center'>"+(this.C_SUPPLIER_NAME||'')+"</td>";
				_s += "<td align='center'>"+(this.C_CREATER||'')+"</td>";
				_s += "<td align='center'>"+(this.C_CDATE||'')+"</td>";
				_s += "<td align='center'>"+(this.C_ORDER||'')+"</td>";
				_s += "<td align='center'><input type='button' value='修 改' class='butt' onClick='modifyCate("+this.C_ID+")'/>";
				_s += "<input type='button' value='删 除' class='butt' onClick='deleteCate("+this.C_ID+")'/>";
				$("#mytable").append(_s);
			});
	});
});
// 设置总页数
function setTotal(count,pageTotal){
	var totalPage = 0;
	var rows = 5;// 每页显示条数
	totalPage = 1;
	// 计算总页数
	totalPage = parseInt((count - 1) / rows) + 1;
	$("#"+pageTotal).val(totalPage);
}
// 分页显示键位管理
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
						var json = $.parseJSON(data);
						var list = json[0].list;
						$("#mytable").empty();
						$(list).each(function(){
							var _s = "<tr class='biaodan-q'><td align='center'>"+this.C_ID+"</td>";
							_s += "<td align='center'>"+(this.C_NAME||'')+"</td>";
							_s += "<td align='center'>"+(this.C_TITLE||'')+"</td>";
							_s += "<td align='center'>"+(this.C_KEY||'')+"</td>";
							_s += "<td align='center'>"+(this.C_SUPPLIER_NAME||'')+"</td>";
							_s += "<td align='center'>"+(this.C_CREATER||'')+"</td>";
							_s += "<td align='center'>"+(this.C_CDATE||'')+"</td>";
							_s += "<td align='center'>"+(this.C_ORDER||'')+"</td>";
							_s += "<td align='center'><input type='button' value='修 改' class='butt' onClick='modifyCate("+this.C_ID+")'/>";
							_s += "<input type='button' value='删 除' class='butt' onClick='deleteCate("+this.C_ID+")'/>";
							$("#mytable").append(_s);
						});
				});
	        }
	    }
	});
}
// 删除
function deleteCate(cid){
    var flag = confirm("您确定要删除？");
    if(flag){
		$.post("${pageContext.request.contextPath}/basedata/ekey/eKIndexKeyAppAction!toDel.action",
				{cid:cid},function(data){
			window.location.reload();
		});
	}
}
// 修改
function modifyCate(cid){
	$("#form2").attr("action","${pageContext.request.contextPath}/basedata/ekey/eKIndexKeyAppAction!toUpdate.action");
	$("#editId").val(cid);
	$("#form2").submit();
}
</script>
</body>
</html>


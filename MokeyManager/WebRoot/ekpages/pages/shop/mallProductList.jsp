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
<title>商品详情</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<!--2015-4-29新样式，reCss.css样式一定要最后调用-->
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
</head>
<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; e键：商城 -&gt; 商品详情 </td>
  </tr>
</table>

<form id="form1" method="post" action="${pageContext.request.contextPath}/basedata/ekey/eKMallProductAction!toAdd.action">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
  <tr>
    <td>
    	<table width="100%"  border="0" cellspacing="1" cellpadding="2">
	        <tr>
				<td width="8%"  height="23" class="biaodan-top" align="left">标题</td>
				<td width="15%" align="center" class="biaodan-q">
					<input type="text" style="border:none; width:100%; height:100%" id="title" />
				</td>
				<td width="8%"  height="23" class="biaodan-top" align="left">类型</td>
				<td width="15%" class="biaodan-q" align="left">
					<select style="width:100%;border:none;hight:100%;" id="type">
						<option></option>
						<option value="2">实物</option>
						<option value="1">虚拟</option>
					</select>
				</td>
	         <td rowspan="2" align="right" class="biaodan-q">
				 <input type="button" class="butt" value="查 询" onclick="infoList()" />
	           <input type="submit" class="butt" value="添 加" />
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
	    <td width="15%">商品名称</td>
	    <td width="10%">积分价格</td>
		<td width="5%">数量</td>
		<td width="10%">图片</td>
		<td width="10%">类型</td>
		<td width="10%">最大数量</td>
		<td width="10%">创建人</td>
		<td width="10%">创建时间</td>
	    <td >操作</td>
	  </tr>
  </thead>
  	<input type="hidden" id="editId" name="editId" />
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
		var title = $("#title").val();
		var type = $("#type").val();
		// 查询应用总数
		$.post("${pageContext.request.contextPath}/basedata/ekey/eKMallProductAction!list.action",{title:title,type:type},
				function(data){
					var json = $.parseJSON(data);
					var count = json[0].count;// 数据总数
					$("#page11").empty();
					if(count != 0){
						setTotal(count,"cateTotal");
						// 分页显示广告位
						getCatePage("${pageContext.request.contextPath}/basedata/ekey/eKMallProductAction!list.action",title,type);
					}
				});
		// 查询广告位(第一页)
		$.post("${pageContext.request.contextPath}/basedata/ekey/eKMallProductAction!list.action",{title:title,type:type},
				function(data){
					var json = $.parseJSON(data);
					var list = json[0].list;
					$("#mytable").empty();
					$(list).each(function(){
						var _s = "<tr class='biaodan-q'><td align='center'>"+this.C_ID+"</td>";
						_s += "<td align='center'>"+(this.C_TITLE||'')+"</td>";
						_s += "<td align='center'>"+(this.C_COST||'')+"</td>";
						_s += "<td align='center'>"+(this.C_TOTAL||0)+"</td>";
						_s += "<td align='center'><img src='"+(this.C_IMG||'')+"' style='height:25px;margin-top:8px;' /></td>";
						_s += "<td align='center'>"+(this.TYPE||'')+"</td>";
						_s += "<td align='center'>"+(this.C_AMOUNT||'')+"</td>";
						_s += "<td align='center'>"+(this.C_CREATER||'')+"</td>";
						_s += "<td align='center'>"+(this.C_CTIME||'')+"</td>";
						_s += "<td align='center'><input type='button' value='修 改' class='butt' onClick='modifyCate("+this.C_ID+")'/>";
						_s += "<input type='button' value='删 除' class='butt' onClick='deleteCate("+this.C_ID+")'/>";
						if(this.C_TYPE=='1'){
							_s  += "<input type='button' value='商品码' class='butt'  onclick='giftCodes("+this.C_ID+")'>";
						}
						_s +="</td></tr>";
						$("#mytable").append(_s);
					});
				});
	}
// 设置总页数
function setTotal(count,pageTotal){
	var totalPage = 0;
	var rows = 5;// 每页显示条数
	totalPage = 1;
	// 计算总页数
	totalPage = parseInt((count - 1) / rows) + 1;
	$("#"+pageTotal).val(totalPage);
}
// 分页显示广告位
function getCatePage(url,title,type) {
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("cateTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(),
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,title:title,type:type},
					function(data){
						var json = $.parseJSON(data);
						var list = json[0].list;
						$("#mytable").empty();
						$(list).each(function(){
							var _s = "<tr class='biaodan-q'><td align='center'>"+this.C_ID+"</td>";
							_s += "<td align='center'>"+(this.C_TITLE||'')+"</td>";
							_s += "<td align='center'>"+(this.C_COST||'')+"</td>";
							_s += "<td align='center'>"+(this.C_TOTAL||0)+"</td>";
							_s += "<td align='center'><img src='"+(this.C_IMG||'')+"' style='height:25px;margin-top:8px;' /></td>";
							_s += "<td align='center'>"+(this.TYPE||'')+"</td>";
							_s += "<td align='center'>"+(this.C_AMOUNT||'')+"</td>";
							_s += "<td align='center'>"+(this.C_CREATER||'')+"</td>";
							_s += "<td align='center'>"+(this.C_CTIME||'')+"</td>";
							_s += "<td align='center'><input type='button' value='修 改' class='butt' onClick='modifyCate("+this.C_ID+")'/>";
							_s += "<input type='button' value='删 除' class='butt' onClick='deleteCate("+this.C_ID+")'/>";
							if(this.C_TYPE=='1'){
								_s  += "<input type='button' value='商品码' class='butt'  onclick='giftCodes("+this.C_ID+")'>";
							}
							_s +="</td></tr>";
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
		$.post("${pageContext.request.contextPath}/basedata/ekey/eKMallProductAction!toDel.action",
				{cid:cid},function(data){
			window.location.reload();
		});
	}
}
// 修改
function modifyCate(cid){
	$("#form2").attr("action","${pageContext.request.contextPath}/basedata/ekey/eKMallProductAction!toUpdate.action");
	$("#editId").val(cid);
	$("#form2").submit();
}

	// 查询虚拟商品码
	function giftCodes(id){
		$("#form2").attr("action","${pageContext.request.contextPath}/basedata/ekey/eKMallProductAction!toCodesList.action");
		$("#editId").val(id);
		$("#form2").submit();
	}
</script>
</body>
</html>

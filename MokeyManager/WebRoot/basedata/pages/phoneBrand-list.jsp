<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,user-scalable=no">
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<title>手机品牌</title>
<script type="text/javascript">rootPath ="${pageContext.request.contextPath}";</script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
<script type="text/javascript">
$(function(){
	brandList();
});
</script>
</head>

<body >

<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <!--2015-4-29新样式，面包屑-->
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; 免费换膜 -&gt; 手机品牌管理  </td>
  </tr>
</table>
<form id="form1" method="post">
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
		<input type="hidden" id="editId" name="editId" />
		<input type="hidden" id="editName" name="editName" />
	  <tr>
	   	<td width="10%"  height="23" class="biaodan-top" align="left">品牌名称</td>
	    <td width="40%" align="center" class="biaodan-q">
			<input type="text" style="border:none; width:100%; height:100%" id="brandName" name="brandName" />
	    </td>
	   	<td width="10%"  height="23" class="biaodan-top" align="left">状态</td>
	    <td width="10%" align="center" class="biaodan-q">
			<select name="islive" id="islive" style="border:none; width:100%; height:100%">
				<option value="1">上架</option>
				<option value="0">下架</option>
			</select>
	    </td>
	 	<td class="biaodan-q" align="center">
			<input type="button" class="butt" value="查 询" onclick="brandList()" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" class="butt" value="添 加" onclick="addBrand()" />
	 	</td>
	  </tr>
	</table>
</form>
<br>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
  <thead>
	  <tr class="biaodan-top">
	    <td width="10%">品牌ID</td>
	    <td width="20%">品牌名称</td>
	    <td width="20%">修改人</td>
	    <td width="20%">修改时间</td>
	    <td >操作</td>
	  </tr>
  </thead>
  <tbody id="mytable"></tbody>
</table>
<input type="hidden" id="total" />
<div id="page11"></div>
<script type="text/javascript">
	function brandList(page){
		var page = page || 1;
		var rows = rows || 10;
		var brandName = $("#brandName").val();// 获取品牌名称
		var islive = $("#islive").val();// 上/下架状态
		var url = "${pageContext.request.contextPath}/basedata/phoneModelAction!phoneList.action";
		// 查询总数
		$.post(url,{brandName:brandName, page:page, rows:rows, islive:islive},
			function(data){
				var json = $.parseJSON(data);
				var count = json[0].count;// 数据总数
				var list = json[0].list;// 数据
				setTotal(count,"total");
				formData(list);
				$("#page11").empty();
				if(count != 0){
					laypage({
						cont: 'page11',
						pages: document.getElementById("total").value,
						curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
							return page;
						}(),
						jump: function(e, first){ //触发分页后的回调
							if(!first){ //一定要加此判断，否则初始时会无限刷新
								brandList(e.curr);
							}
						}
					});
				}
			});
	}
// 设置总页数
	function setTotal(count,pageTotal){
		var rows = 10;// 每页显示条数
		// 计算总页数
		var totalPage = parseInt((count - 1) / rows) + 1;
		$("#"+pageTotal).val(totalPage);
	}
	function formData(list) {
		$("#mytable").empty();
		$(list).each(function(){
			var _str = "<tr class='biaodan-q'>";
			_str += "<td align='center' name='id'>"+(this.C_ID || '')+"</td>";
			_str += "<td align='center' name='name'>"+(this.C_NAME || '')+"</td>";
			_str += "<td align='center' name='modifier'>"+(this.C_MODIFIER || '')+"</td>";
			_str += "<td align='center' name='etime'>"+(this.C_ETIME || '')+"</td>";
			_str += "<td align='center' name='handle'>";
			if (this.C_ISLIVE == 1) {
				_str += "<input type='button' value='下 架' class='butt'  onclick='onShelf("+this.C_ID+", 0)'>&nbsp;";
			} else {
				_str += "<input type='button' value='上 架' class='butt-none'  onclick='onShelf("+this.C_ID+", 1)'>&nbsp;";
			}
			_str += "<input type='button' value='编 辑' class='butt'  onclick='addBrand("+this.C_ID+",\""+this.C_NAME+"\")'>&nbsp;";
			_str += "<input type='button' value='型 号' class='butt'  onclick='toModelList("+this.C_ID+",\""+this.C_NAME+"\")'></td></tr>";
			$("#mytable").append(_str);
		});
	}

	// 上/下架
	function onShelf(id, act){
		$.post("${pageContext.request.contextPath}/basedata/phoneModelAction!onShelf.action", {id:id, act:act, type:1},
				function(data){
					window.location.reload();
				});
	}
	// 新增/编辑品牌
	function addBrand(id, name){
		if(id==null || id==""){// 没有id，添加
			$("#form1").attr("action","${pageContext.request.contextPath}/basedata/phoneModelAction!toBrandAdd.action");
			$("#form1").submit();
		}else{// 有id，编辑
			$("#form1").attr("action","${pageContext.request.contextPath}/basedata/phoneModelAction!toBrandEdit.action");
			$("#editId").val(id);
			$("#editName").val(name);
			$("#form1").submit();
		}
	}
	// 查询品牌下的
	function toModelList(id, name){
		$("#form1").attr("action","${pageContext.request.contextPath}/basedata/phoneModelAction!toModelList.action");
		$("#editId").val(id);
		$("#editName").val(name);
		$("#form1").submit();
	}
</script>
</body>
</html>

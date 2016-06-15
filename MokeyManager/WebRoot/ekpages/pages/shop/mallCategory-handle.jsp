<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">rootPath ="${pageContext.request.contextPath}";</script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
</head>
<body>
<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
  <tr>
    <td align="left" class="jg_fc">当前商品分类&nbsp;-&gt;&nbsp;<s:property value="#request.cname"/>&nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>
<form name="form0" method="post">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
        <tr> 
         <td width="12.1%" height="23" class="biaodan-top" align="left">商品名称：</td>
         <td width="61%" class="biaodan-q" align="left">
			<input type="text" name="gameName" style="border:none; width:100%; height:100%" id="gameName" />
		 </td>
         <td align="center" class="biaodan-q">
           <input name="Button" type="button" class="butt" value="查 询" onclick="productList()" />
           </td>
        </tr>
          </table></td>
  </tr>
</table>
</form>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
    <thead>
		<tr class="biaodan-top">
		    <td width="12.1%">选择</td>
		    <td >商品名称</td>
	  	</tr>
  	</thead>
  	<tbody id="table1"></tbody>
</table>
<input type="hidden" id="gameTotal" />
<div id="page11"></div>
<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
  <tr biaodan-q>
     <td width="40%" align="right">
		<a onclick="down()"><img src="${pageContext.request.contextPath}/image/A2.jpg" /></a>
		<a onclick="up()"><img src="${pageContext.request.contextPath}/image/A2-2.jpg" /></a>
     </td>
     <td width="10%"></td>
     <td width="40%" align="left">
		<a onclick="allDown()"><img src="${pageContext.request.contextPath}/image/A3.jpg" /></a>
		<a onclick="allUp()"><img src="${pageContext.request.contextPath}/image/A3-2.jpg" /></a>
	 </td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
        <tr> 
         <td width="12.1%"  height="23" class="biaodan-top" align="left">商品名称：</td>
         <td width="61%" class="biaodan-q" align="left">
			<input type="text" name="cateGameName" style="border:none; width:100%; height:100%" id="cateGameName" />
		 </td>
         <td align="center" class="biaodan-q">
           <input name="Button" type="button" class="butt" value="查 询" onclick="cateProductList()" />
           </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<form id="form1" method="post">
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	    <thead>
		    </tr>
				<tr class="biaodan-top">
			    <td width="12.1%">选择</td>
			    <td width="61%">商品名称</td>
			    <td >排序</td>
		  	</tr>
	  	</thead>
	  	<tbody id="table2"></tbody>
	</table>
	<input type="hidden" id="cid" name="cid" value="<s:property value="#request.cid"/>" />
	<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
		<tr>
	    	<td align="center">
				<input type="button" value="提 交" class="butt" onclick="submitt()" />
		 	</td>
	  	</tr>
	</table>
<input type="hidden" id="cateTotal" />
<div id="page22"></div>
</form>

<script>
// 全局数组，用来存放移除出当前分类的商品id
var removePid = new Array();
// 定义Map，存放移除分类的商品的排序
var orderMap = {};

$(function(){
	productList();
	cateProductList();
});
// 非当前分类的其他所有商品
function productList(){
	var cid = $("#cid").val();
	var name = $("#gameName").val();
	// 查询非当前分类的其他所有商品
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKShopProductCateAction!getAllProduct.action",{cid:cid,name:name},
		function(data){
			var json = eval("("+data+")");
			var count = json[0].count;// 数据总数
			$("#page11").empty();
			if(json != 0){
				setTotal(count,"gameTotal");
				// 所有商品
				getGamePage("${pageContext.request.contextPath}/basedata/ekey/eKShopProductCateAction!getAllProduct.action",cid,name);
			}
	});
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKShopProductCateAction!getAllProduct.action",{cid:cid,name:name},
		function(data){
			formData(data, "table1");
	});
}
// 当前分类的所有商品
function cateProductList(){
	var cid = $("#cid").val();
	var name = $("#cateGameName").val();
	// 查询当前分类的商品总数
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKShopProductCateAction!getCurrCateProduct.action",{cid:cid,name:name},
		function(data){
			var json = eval("("+data+")");
			var count = json[0].count;// 数据总数
			$("#page22").empty();
			if(json != 0){
				setTotal(count,"cateTotal");
				// 当前分类中的商品
				currCateGamePage("${pageContext.request.contextPath}/basedata/ekey/eKShopProductCateAction!getCurrCateProduct.action",cid,name);
			}
	});
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKShopProductCateAction!getCurrCateProduct.action",{cid:cid,name:name},
		function(data){
			formDataCurrCate(data, "table2");
	});
}
// 设置总页数
function setTotal(count,pageTotal){
	var rows = 5;// 每页显示条数
	// 计算总页数
	var totalPage = parseInt((count - 1) / rows) + 1;
	$("#"+pageTotal).val(totalPage);
}
// 分页查询所有商品
function getGamePage(url,cid,name) {
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("gameTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,cid:cid,name:name},
					function(data){
						formData(data, "table1");
				});
	        }
	    }
	});
}
// 分页查询当前分类中的商品
function currCateGamePage(url,cid,name) {
	laypage({
	    cont: 'page22',
	    pages: document.getElementById("cateTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,cid:cid,name:name},
					function(data){
						formDataCurrCate(data, "table2");
				});
	        }
	    }
	});
}
// 构建“商品”表
function formData(data, table) {
	var json = eval("("+data+")");
	var list = json[0].list;
	$("#" + table).empty();
	$(list).each(function(){
		var _str = "<tr class='biaodan-q'><input type='hidden' name='PID' value="+this.C_ID+" />";
		_str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
		_str += "<td align='center' name='PNAME'>"+this.C_TITLE+"</td></tr>";
		$("#" + table).append(_str);
	});
}
// 构建“当前分类中的商品”表
function formDataCurrCate(data, table) {
	var json = eval("("+data+")");
	var list = json[0].list;
	$("#" + table).empty();
	$(list).each(function(){
		var _str = "<tr class='biaodan-q'><input type='hidden' name='PID' value="+this.C_PID+" />";
		_str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
		_str += "<td align='center' name='PNAME'>"+this.C_TITLE+"</td>";
		_str += "<td align='center'><input type='text' name='CORDER' value='"+(this.C_ORDER||'')+"'/></td></tr>";
		$("#" + table).append(_str);
	});
}



// 上  ==》 下
function down(){
// 获取上面table中选中，并遍历
	$("#table1 input:checked").each(function(){
	  	// 获取当前checkbox所在的行
	  	var tablerow = $(this).parent("td").parent("tr");
	  	// 获取id、name、size
	  	var id = tablerow.find("input[name='PID']").val();
		var name = tablerow.find("[name='PNAME']").html();
		// 填入下面的table
		var str_ = "<tr class='biaodan-q'><input type='hidden' name='PID' value="+id+" />";
		str_ += "<td align='center'><input type='checkbox' name='ckbox2'></td>";
		str_ += "<td align='center' name='PNAME'>"+name+"</td>";
		var order = orderMap[id];
		if(order){
			str_ += "<td align='center'><input name='CORDER' value="+order+" /></td></tr>";
		}else{
			str_ += "<td align='center'><input name='CORDER' /></td></tr>";
	  	}
		$("#table2").append(str_);
	  	// 移除
	  	tablerow.remove();
	}) ;
}
// 下  ==》 上
function up(){
	$("#table2 input:checked").each(function(){
	  	var tablerow = $(this).parent("td").parent("tr");
	  	var id = tablerow.find("input[name='PID']").val();
	  	// 将要移除出当前分类的商品id存入全局数组
	  	removePid.push(id);
	  	// 将要移除出当前分类的商品的order存入全局Map
	  	orderMap[id] = tablerow.find("[name='CORDER']").val();
	  	var name = tablerow.find("[name='PNAME']").html();
		var str_ = "<tr class='biaodan-q'><input type='hidden' name='PID' value="+id+" />";
		str_ += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
		str_ += "<td align='center' name='PNAME'>"+name+"</td></tr>";
	  	$("#table1").append(str_);
	  	tablerow.remove();
	}) ;
}
function allDown(){
	$("#table1 input").prop("checked",true);
	down();
}
function allUp(){
	$("#table2 input").prop("checked",true);
	up();
}
// 保存分类
function submitt(){
	// 获取分类id
	var cid = $("#cid").val();
	var pid = new Array();
	var order = new Array();
	// 获取下面表的所有商品id
	$("#form1 [name='PID']").each(function(){
		// 存入数组
		pid.push($(this).val());
	});
	$("#form1 [name='CORDER']").each(function(){
		var _order = $(this).val();
		// 如果排序值为空，设置其值为""
		if(_order=="" || _order==null){
			_order = "n";
		}
		// 存入数组
		order.push(_order);
	});
	// 转成字符串
	pid = pid.toString();
	order = order.toString();
	// 移除的商品id转成字符串
	removePid = removePid.toString();
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKShopProductCateAction!handleCate.action",{cid:cid,pid:pid,removePid:removePid,order:order},function(data){
		window.location.reload();
	});  
}
</script>

</body>
</html>
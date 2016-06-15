<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>

</head>
<body>
<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
  <tr>
    <td align="left" class="jg_fc">当前用户&nbsp;-&gt;&nbsp;${editName } | ${editCode }&nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>
<form name="form0" method="post">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
        <tr>
			<input type="hidden" id="supId" name="uid" value="${editId }" />
         	<td width="10%"  height="23" class="biaodan-top" align="left">供应商代码</td>
         	<td width="30%" class="biaodan-q" align="left">
				<input type="text" id="supCode" name="supCode" style="border:none; width:100%; height:100%" />
		 	</td>
         	<td width="10%"  height="23" class="biaodan-top" align="left">供应商名称</td>
         	<td width="30%" class="biaodan-q" align="left">
				<input type="text" id="supName" name="supName" style="border:none; width:100%; height:100%" />
		 	</td>
         	<td width="20%" align="center" class="biaodan-q">
           		<input name="Button" type="button" class="butt" value="查 询" onclick="supList()" />
           	</td>
        </tr>
          </table></td>
  </tr>
</table>
</form>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
    <thead>
		<tr class="biaodan-top">
		    <td width="10%">选择</td>
		    <td width="30%">供应商代码</td>
		    <td width="60%">供应商名称</td>
	  	</tr>
  	</thead>
  	<tbody id="table1"></tbody>
</table>
<input type="hidden" id="supTotal" />
<div id="page11"></div>
<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
  <tr biaodan-q>
     <td width="40%" align="right">
		<a onclick="down()"><img src="${pageContext.request.contextPath}/image/A2.jpg"></img></a>
		<a onclick="up()"><img src="${pageContext.request.contextPath}/image/A2-2.jpg"></img></a>
     </td>
     <td width="10%"></td>
     <td width="40%" align="left">
		<a onclick="allDown()"><img src="${pageContext.request.contextPath}/image/A3.jpg"></img></a>
		<a onclick="allUp()"><img src="${pageContext.request.contextPath}/image/A3-2.jpg"></img></a>
	 </td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
        <tr>
			<input type="hidden" id="currUid" name="uid" value="${editId }" />
			<td width="10%"  height="23" class="biaodan-top" align="left">供应商代码</td>
			<td width="30%" class="biaodan-q" align="left">
				<input type="text" id="currSupCode" name="currSupCode" style="border:none; width:100%; height:100%" />
			</td>
			<td width="10%"  height="23" class="biaodan-top" align="left">供应商名称</td>
			<td width="30%" class="biaodan-q" align="left">
				<input type="text" id="currSupName" name="currSupName" style="border:none; width:100%; height:100%" />
			</td>
         	<td width="20%" align="center" class="biaodan-q">
           		<input name="Button" type="button" class="butt" value="查 询" onclick="getColGameCondition()" />
           	</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<form id="form1" method="post">
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	    <thead>
			<tr class="biaodan-top">
				<td width="10%">选择</td>
				<td width="30%">供应商代码</td>
				<td width="60%">供应商名称</td>
		  	</tr>
	  	</thead>
	  	<tbody id="table2"></tbody>
	</table>
	<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
		<input type="hidden" id="userId" name="userId" value="${editId }" />
		<tr biaodan-q>
	    	<td align="center">
				<input type="button" value="提 交" class="butt" onclick="submitt()" />
		 	</td>
	  	</tr>
	</table>
<input type="hidden" id="currTotal" />
<div id="page22"></div>
</form>
<script type="text/javascript">
var temp;
	// 全局数组，用来存放修改为“当前用户不可见的”的供应商id
	var removeSupId = new Array();
$(function(){
	supList();
	currSupList();
});
// 当前用户不可见的供应商
function supList(){
	var uid = $("#supId").val();
	var supCode = $("#supCode").val();
	var supName = $("#supName").val();
	// 查询非当前用户不可见的供应商
	$.post("${pageContext.request.contextPath}/basedata/dataPermissionAction!getAllSuppliers.action",
			{uid:uid,supCode:supCode,supName:supName},
			function(data){
				var json = eval("("+data+")");
				var count = json[0].count;// 数据总数
				$("#page11").empty();
				if(json != 0){
					setTotal(count,"supTotal");
					// 分页
					getSupPage("${pageContext.request.contextPath}/basedata/dataPermissionAction!getAllSuppliers.action",uid,supCode,supName);
				}
			});
	$.post("${pageContext.request.contextPath}/basedata/dataPermissionAction!getAllSuppliers.action",
			{uid:uid,supCode:supCode,supName:supName},
			function(data){
				json = eval("("+data+")");
				var list = json[0].list;
				$("#table1").empty();
				$(list).each(function(){
					var _str = "<tr class='biaodan-q'><input type='hidden' name='ID' value="+this.C_ID+" />";
					_str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
					_str += "<td align='center' name='CODE'>"+this.C_SUPPLIER_CODE+"</td>";
					_str += "<td align='center' name='NAME'>"+this.C_SUPPLIER_NAME+"</td></tr>";
					$("#table1").append(_str);
				});
			});
}
// 当前用户可见的供应商
function currSupList(){
	var uid = $("#currUid").val();// 
	var supCode = $("#currSupCode").val();
	var supName = $("#currSupName").val();
	// 查询用户可见的供应商
	$.post("${pageContext.request.contextPath}/basedata/dataPermissionAction!getCurrSup.action",
			{uid:uid,supCode:supCode,supName:supName},
			function(data){
				var json = eval("("+data+")");
				var count = json[0].count;// 数据总数
				$("#page22").empty();
				if(json != 0){
					setTotal(count,"currTotal");
					// 分页
					currSupPage("${pageContext.request.contextPath}/basedata/dataPermissionAction!getCurrSup.action",uid,supCode,supName);
				}
			});
	$.post("${pageContext.request.contextPath}/basedata/dataPermissionAction!getCurrSup.action",
			{uid:uid,supCode:supCode,supName:supName},
			function(data){
				json = eval("("+data+")");
				var list = json[0].list;
				$("#table2").empty();
				$(list).each(function(){
					var _str = "<tr class='biaodan-q'><input type='hidden' name='ID' value="+this.C_ID+" />";
					_str += "<td align='center'><input type='checkbox' name='ckbox2'></td>";
					_str += "<td align='center' name='CODE'>"+this.C_SUPPLIER_CODE+"</td>";
					_str += "<td align='center' name='NAME'>"+this.C_SUPPLIER_NAME+"</td></tr>";
					$("#table2").append(_str);
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
// 分页查询当前用户不可见的供应商
function getSupPage(url,uid,supCode,supName) {
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("supTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(),
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,uid:uid,supCode:supCode,supName:supName},
					function(data){
						json = eval("("+data+")");
						var list = json[0].list;
						$("#table1").empty();
						$(list).each(function(){
							var _str = "<tr class='biaodan-q'><input type='hidden' name='ID' value="+this.C_ID+" />";
							_str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
							_str += "<td align='center' name='CODE'>"+this.C_SUPPLIER_CODE+"</td>";
							_str += "<td align='center' name='NAME'>"+this.C_SUPPLIER_NAME+"</td></tr>";
							$("#table1").append(_str);
						});
				});
	        }
	    }
	});
}
// 分页查询当前用户可见的供应商
function currSupPage(url,uid,supCode,supName) {
	laypage({
	    cont: 'page22',
	    pages: document.getElementById("currTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(),
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,uid:uid,supCode:supCode,supName:supName},
					function(data){
						json = eval("("+data+")");
						var list = json[0].list;
						$("#table2").empty();
						$(list).each(function(){
							var _str = "<tr class='biaodan-q'><input type='hidden' name='ID' value="+this.C_ID+" />";
							_str += "<td align='center'><input type='checkbox' name='ckbox2'></td>";
							_str += "<td align='center' name='CODE'>"+this.C_SUPPLIER_CODE+"</td>";
							_str += "<td align='center' name='NAME'>"+this.C_SUPPLIER_NAME+"</td></tr>";
							$("#table2").append(_str);
						});
				});
	        }
	    }
	});
}
// 上  ==》 下
function down(){
// 获取上面table中选中，并遍历
	$("#table1 input:checked").each(function(){
		// 获取当前checkbox所在的行
		var tablerow = $(this).parent("td").parent("tr");
		// 获取id、code、name
		var id = tablerow.find("input[name='ID']").val();
		var code = tablerow.find("[name='CODE']").html();
		var name = tablerow.find("[name='NAME']").html();
		// 填入下面的table
		var _str = "<tr class='biaodan-q'><input type='hidden' name='ID' value=" + id + " />";
		_str += "<td align='center'><input type='checkbox' name='ckbox2'></td>";
		_str += "<td align='center' name='CODE'>" + code + "</td>";
		_str += "<td align='center' name='NAME'>" + name + "</td></tr>";
		$("#table2").append(_str);
		// 移除
		tablerow.remove();
	}) ;
}
// 下  ==》 上
function up(){
	$("#table2 input:checked").each(function(){
		var tablerow = $(this).parent("td").parent("tr");
		var id = tablerow.find("input[name='ID']").val();
		// 将要移除出当前合集的游戏id存入全局数组
		removeSupId.push(id);
		var code = tablerow.find("[name='CODE']").html();
		var name = tablerow.find("[name='NAME']").html();
		// 填入上面的table
		var _str = "<tr class='biaodan-q'><input type='hidden' name='ID' value=" + id + " />";
		_str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
		_str += "<td align='center' name='CODE'>" + code + "</td>";
		_str += "<td align='center' name='NAME'>" + name + "</td></tr>";
		$("#table1").append(_str);
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

// 保存合集
function submitt(){
	// 获取用户id
	var userId = $("#userId").val();
	var supId = new Array();
	var supCode = new Array();
	// 获取下表的所有供应商id
	$("#form1 [name='ID']").each(function(){
		// 存入数组
		supId.push($(this).val());
	});
	$("#form1 [name='CODE']").each(function(){
		// 存入数组
		supCode.push($(this).html());
	});
	// 转成字符串
	supId = supId.toString();
	supCode = supCode.toString();
	// 移除的供应商id转成字符串
	removeSupId = removeSupId.toString();
	$.post("${pageContext.request.contextPath}/basedata/dataPermissionAction!handle.action",{userId:userId,supId:supId,removeSupId:removeSupId,supCode:supCode},function(data){
		window.location.reload();
	});  
}
</script>
</body>
</html>
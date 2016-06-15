<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微用帮首页排版</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>

</head>
<body>
<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; 微用帮管理 -&gt; 首页排版维护 </td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
  		<tr>
  			<td width="12.1%" class="biaodan-top" align="left">合集名称：</td>
         	<td width="49%" class="biaodan-q" align="center">
         	<s:iterator var="col" value="#request.cols">
         		<s:if test="C_ID == #request.colId">
					<input type="radio" name="col" id="col" value='<s:property value="C_ID"/>' checked="checked"/>
					<s:property value="C_NAME"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         		</s:if>
         		<s:else>
         			<input type="radio" name="col" id="col" value='<s:property value="C_ID"/>' />
					<s:property value="C_NAME"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         		</s:else>
         	</s:iterator>
		 	</td>
		 	<td width="12%"  height="23" class="biaodan-top" align="left">合集应用数量：</td>
         	<td class="biaodan-q" align="left">
         		<input type="text" name="colCnt" style="border:none; width:100%; height:100%"
         			value='<s:property value="#request.colCnt"/>' id="colCnt" />
		 	</td>
  		</tr>
</table>
<form name="form0" method="post">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg sortable">
  <tr>
    <td>
      <table width="100%" border="0" cellspacing="1" cellpadding="2">
        <tr> 
         <td width="12.1%" class="biaodan-top" align="left">分类名称：</td>
         <td width="61%" class="biaodan-q" align="left">
			<input type="text" name="appName" style="border:none; width:100%; height:100%" id="appName" />
		 </td>
         <td align="center" class="biaodan-q">
           <input name="Button" type="button" class="butt" value="查 询" onclick="cateList()" />
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
		    <td >分类名称</td>
	  	</tr>
  	</thead>
  	<tbody id="table1"></tbody>
</table>

<input type="hidden" id="appTotal" />
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
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg sortable">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
        <tr> 
         <td width="12.1%"  height="23" class="biaodan-top" align="left">名称：</td>
         <td width="61%" class="biaodan-q" align="left">
			<input type="text" name="cateName" style="border:none; width:100%; height:100%" id="cateName" />
		 </td>
         <td align="center" class="biaodan-q">
           <input name="Button" type="button" class="butt" value="查 询" onclick="indexColList()" />
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
			    <td width="34%">应用名称</td>
			    <td width="27%">分类应用数量</td>
			    <td >排序</td>
		  	</tr>
	  	</thead>
	  	<tbody id="table2"></tbody>
	</table>
	<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
		<tr>
	    	<td align="center">
				<button class="butt" onclick="return submitt();" />提 交
		 	</td>
	  	</tr>
	</table>
<input type="hidden" id="cateTotal" />
<div id="page22"></div>
</form>

<script>
// 全局数组，用来存放移除出首页的分类的id
var removeId = new Array();
// 定义Map，存放移除出首页的分类的排序
var orderMap = {};
// 定义Map，存放移除出首页的分类的应用的数量
var numberMap = {};

$(function(){
	var appName = $("#appName").val();// 应用名称
	var cateName = $("#cateName").val();// 分类中应用的名称
	cateList(appName);// 查询应用
	indexColList(cateName);// 查询首页显示的分类
});
// 查询所有分类
function cateList(appName){
	if(!appName){
		appName = $("#appName").val();
	}
	$.post("${pageContext.request.contextPath}/basedata/mcrAppIndexAction!cateList.action",{name:appName},
		function(data){
			var json = $.parseJSON(data);
			var count = json[0].count;// 数据总数
			$("#page11").empty();
			if(count != 0){
				setTotal(count,"appTotal");
				// 分页查询所有分类
				getAppPage("${pageContext.request.contextPath}/basedata/mcrAppIndexAction!cateList.action",appName);
			}
	});
	// 查询分类(第一页)
	$.post("${pageContext.request.contextPath}/basedata/mcrAppIndexAction!cateList.action",{name:appName},
		function(data){
			var json = $.parseJSON(data);
			var list = json[0].list;
			$("#table1").empty();
			$(list).each(function(){
				var _s = "<tr class='biaodan-q'><input type='hidden' name='ID' value="+this.C_ID+" ></input>";
				_s += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
				_s += "<td align='center' name='NAME'>"+this.C_NAME+"</td></tr>";
				$("#table1").append(_s);
			});
	});
}
// 查询首页显示的分类
function indexColList(cateName){
	if(!cateName){
		cateName = $("#cateName").val();
	}
	$.post("${pageContext.request.contextPath}/basedata/mcrAppIndexAction!indexColList.action",{name:cateName},
		function(data){
			var json = $.parseJSON(data);
			var count = json[0].count;// 数据总数
			$("#page22").empty();
			if(count != 0){
				setTotal(count,"cateTotal");
				// 当前首页显示的分类
				getIndexCateList("${pageContext.request.contextPath}/basedata/mcrAppIndexAction!indexColList.action",cateName);
			}
	});
	// 查询首页显示的分类(第一页)
	$.post("${pageContext.request.contextPath}/basedata/mcrAppIndexAction!indexColList.action",{name:cateName},
		function(data){
			var json = $.parseJSON(data);
			var list = json[0].list;
			$("#table2").empty();
			$(list).each(function(){
				var _s = "<tr class='biaodan-q'><input type='hidden' name='ID' value="+this.C_ID+" ></input>";
				_s += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
				_s += "<td align='center' name='NAME'>"+this.C_NAME+"</td>";
				_s += "<td align='center'><input type='text' name='NUMBER' value="+(this.C_NUMBER||'')+" ></input></td>";
				_s += "<td align='center'><input type='text' name='ORDER' value="+(this.C_ORDER||'')+" ></input></td></tr>";
				$("#table2").append(_s);
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
// 分页查询所有应用
function getAppPage(url,name) {
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("appTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,name:name},
					function(data){
						var json = $.parseJSON(data);
						var list = json[0].list;
						$("#table1").empty();
						$(list).each(function(){
							var _s = "<tr class='biaodan-q'><input type='hidden' name='ID' value="+this.C_ID+" ></input>";
							_s += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
							_s += "<td align='center' name='NAME'>"+this.C_NAME+"</td></tr>";
							$("#table1").append(_s);
						});
				});
	        }
	    }
	});
}
// 分页查询首页显示的分类
function getIndexCateList(url) {
	laypage({
	    cont: 'page22',
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
						$("#table2").empty();
						$(list).each(function(){
							var _s = "<tr class='biaodan-q'><input type='hidden' name='ID' value="+this.C_ID+" ></input>";
							_s += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
							_s += "<td align='center' name='NAME'>"+this.C_NAME+"</td>";
							_s += "<td align='center'><input name='NUMBER' value="+this.C_NUMBER+" ></input></td>";
							_s += "<td align='center'><input name='ORDER' value="+this.C_ORDER+" ></input></td></tr>";
							$("#table2").append(_s);
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
	  // 获取id、name、size
	  var id = tablerow.find("input[name='ID']").val();
	  var name = tablerow.find("[name='NAME']").html();
	  var number = numberMap[id];
	  var order = orderMap[id];
	  // 填入下面的table
	  var _str = "<tr class='biaodan-q'><input type='hidden' name='ID' value="+id+" ></input>";
	  _str += "<td align='center'><input type='checkbox' name='ckbox2'></td>";
	  _str += "<td align='center' name='NAME'>"+name+"</td><td align='center'>"; 
	  if(number){
	  	_str += "<input name='NUMBER' value="+number+" ></input></td>";
	  }else{
	  	_str += "<input name='NUMBER' ></input></td>";
	  }
	  _str += "<td align='center'>"; 
	  if(order){
 	  	_str += "<input name='ORDER' value="+order+" ></input></td>";
	  }else{
	  	_str += "<input name='ORDER' ></input></td>";
	  }
	  _str += "</tr>";
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
	  // 将要移除出首页的分类id存入全局数组
	  removeId.push(id);
	  // 将要移除出首页的分类的order存入全局排序Map
	  orderMap[id] = tablerow.find("[name='ORDER']").val();
	  // 将要移除出首页的分类的number存入全局数量Map
	  numberMap[id] = tablerow.find("[name='NUMBER']").val();
	  var name = tablerow.find("[name='NAME']").html();
	  $("#table1").append("<tr class='biaodan-q'><input type='hidden' name='ID' value="+id+" ></input><td align='center'><input type='checkbox' name='ckbox1' ></input></td><td align='center' name='NAME'>"+name+"</td></tr>");
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
	var flag = true;
	var col = $("#col").val();// 合集id
	var colCnt = $("#colCnt").val();// 合集id
	if(!colCnt){
		flag = false;
		alert("请输入合集应用数量！");
		$("#colCnt").focus();
	}
	var id = new Array();// 分类id
	var order = new Array();// 分类顺序
	var number = new Array();// 每个分类的应用的数量
	// 获取下面表的所有应用id
	$("#form1 [name='ID']").each(function(){
		// 存入数组
		id.push($(this).val());
	});
	$("#form1 [name='ORDER']").each(function(){
		var _order = $(this).val();
		// 如果排序值为空，设置其值为""
		if(_order=="" || _order==null){
			_order = "n";
		}
		// 存入数组
		order.push(_order);
	});
	$("#form1 [name='NUMBER']").each(function(){
		var _number = $(this).val();
		// 如果排序值为空，设置其值为""
		if(_number=="" || _number==null){
			_number = "n";
		}
		// 存入数组
		number.push(_number);
	});
	// 转成字符串
	id = id.toString();
	order = order.toString();
	number = number.toString();
	// 移除的分类id转成字符串
	removeId = removeId.toString();
	if(flag){
		$.post("${pageContext.request.contextPath}/basedata/mcrAppIndexAction!handle.action",{col:col,colCnt:colCnt,id:id,removeId:removeId,order:order,number:number},function(data){
			window.location.reload();
		});  
	}
	return flag;
}
</script>

</body>
</html>
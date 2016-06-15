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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.selectseach.min.js"></script>
<script type="text/javascript">
$(function(){
	$("select").selectseach(); 
});
</script>
</head>
<body>
<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
  <tr>
    <td align="left" class="jg_fc">当前游戏分类&nbsp;-&gt;&nbsp;<s:property value="#request.cname"/>&nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>
<form name="form0" method="post">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
        <tr> 
         <td width="12.1%" height="23" class="biaodan-top" align="left">游戏名称：</td>
         <td width="23.8%" class="biaodan-q" align="left">
			<input type="text" name="gameName" style="border:none; width:100%; height:100%" id="gameName" />
		 </td>
		 <td width="12.1%" class="biaodan-top" align="left">初始分类：</td>
         <td width="25%" class="biaodan-q" align="left">
			<s:select list="#request.gameCate" name="gameCate" id="gameCate" listKey="C_ID" m="search"
	       	 listValue="C_NAME" cssStyle="width:100%;border:none;hight:100%;" 
	       	 headerKey="" headerValue=""/>
		 </td>
         <td align="center" class="biaodan-q">
           <input name="Button" type="button" class="butt" value="查 询" onclick="gameList()" />
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
		    <td >游戏名称</td>
	  	</tr>
  	</thead>
  	<tbody id="table1"></tbody>
</table>
<input type="hidden" id="gameTotal" />
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
         <td width="12.1%"  height="23" class="biaodan-top" align="left">游戏名称：</td>
         <td width="61%" class="biaodan-q" align="left">
			<input type="text" name="cateGameName" style="border:none; width:100%; height:100%" id="cateGameName" />
		 </td>
         <td align="center" class="biaodan-q">
           <input name="Button" type="button" class="butt" value="查 询" onclick="cateGameList()" />
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
			    <td width="61%">游戏名称</td>
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
// 全局数组，用来存放移除出当前分类的游戏id
var removeGid = new Array();
// 定义Map，存放移除分类的游戏的排序
var orderMap = {};

$(function(){
	gameList();
	cateGameList();
});
// 非当前分类的其他所有游戏
function gameList(){
	var cid = $("#cid").val();
	var name = $("#gameName").val();
	var gameCate = $("#gameCate").val();
	// 查询非当前分类的其他所有游戏
	$.post("${pageContext.request.contextPath}/basedata/gameCategoryAction!getAllGame.action",{cid:cid,name:name,gameCate:gameCate},
		function(data){
			var json = eval("("+data+")");
			var count = json[0].count;// 数据总数
			$("#page11").empty();
			if(json != 0){
				setTotal(count,"gameTotal");
				// 所有游戏
				getGamePage("${pageContext.request.contextPath}/basedata/gameCategoryAction!getAllGame.action",cid,name,gameCate);
			}
	});
	$.post("${pageContext.request.contextPath}/basedata/gameCategoryAction!getAllGame.action",{cid:cid,name:name,gameCate:gameCate},
		function(data){
			json = eval("("+data+")");
			var list = json[0].list;
			$("#table1").empty();
			$(list).each(function(){
				var _str = "<tr class='biaodan-q'><input type='hidden' name='GID' value="+this.C_ID+" />";
				_str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
				_str += "<td align='center' name='GNAME'>"+this.C_NAME+"</td></tr>";
				$("#table1").append(_str);
			});
	});
}
// 当前分类的所有游戏
function cateGameList(){
	var cid = $("#cid").val();
	var name = $("#cateGameName").val();
	// 查询当前分类的游戏总数
	$.post("${pageContext.request.contextPath}/basedata/gameCategoryAction!getCurrCateGame.action",{cid:cid,name:name},
		function(data){
			var json = eval("("+data+")");
			var count = json[0].count;// 数据总数
			$("#page22").empty();
			if(json != 0){
				setTotal(count,"cateTotal");
				// 当前分类中的游戏
				currCateGamePage("${pageContext.request.contextPath}/basedata/gameCategoryAction!getCurrCateGame.action",cid,name);
			}
	});
	$.post("${pageContext.request.contextPath}/basedata/gameCategoryAction!getCurrCateGame.action",{cid:cid,name:name},
		function(data){
			json = eval("("+data+")");
			var list = json[0].list;
			$("#table2").empty();
			$(list).each(function(){
				var _str = "<tr class='biaodan-q'><input type='hidden' name='GID' value="+this.C_ID+" />";
				_str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
				_str += "<td align='center' name='GNAME'>"+this.C_NAME+"</td>";
				_str += "<td align='center'><input type='text' name='CORDER' value='"+(this.C_ORDER||'')+"'/></td></tr>";
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
// 分页查询所有游戏
function getGamePage(url,cid,name,gameCate) {
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("gameTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,cid:cid,name:name,gameCate:gameCate},
					function(data){
						json = eval("("+data+")");
						var list = json[0].list;
						$("#table1").empty();
						$(list).each(function(){
							var _str = "<tr class='biaodan-q'><input type='hidden' name='GID' value="+this.C_ID+" />";
							_str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
							_str += "<td align='center' name='GNAME'>"+this.C_NAME+"</td></tr>";
							$("#table1").append(_str);
						});
				});
	        }
	    }
	});
}
// 分页查询当前分类中的游戏
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
						json = eval("("+data+")");
						$("#table2").empty();
						$(json[0].list).each(function(){
							var _str = "<tr class='biaodan-q'><input type='hidden' name='GID' value="+this.C_ID+" />";
							_str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
							_str += "<td align='center' name='GNAME'>"+this.C_NAME+"</td>";
							_str += "<td align='center'><input type='text' name='CORDER' value='"+(this.C_ORDER||'')+"'/></td></tr>";
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
	  // 获取id、name、size
	  var id = tablerow.find("input[name='GID']").val();
	  var name = tablerow.find("[name='GNAME']").html();
	  var order = orderMap[id];
	  // 填入下面的table
	  if(order){
	  	$("#table2").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+id+" /><td align='center'><input type='checkbox' name='ckbox2'></td><td align='center' name='GNAME'>"+name+"</td><td align='center'><input name='CORDER' value="+order+" ></input></td></tr>");
	  }else{
	  	$("#table2").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+id+" /><td align='center'><input type='checkbox' name='ckbox2'></td><td align='center' name='GNAME'>"+name+"</td><td align='center'><input name='CORDER' ></input></td></tr>");
	  }
	  // 移除
	  tablerow.remove();
	}) ;
}
// 下  ==》 上
function up(){
	$("#table2 input:checked").each(function(){
	  var tablerow = $(this).parent("td").parent("tr");
	  var id = tablerow.find("input[name='GID']").val();
	  // 将要移除出当前分类的游戏id存入全局数组
	  removeGid.push(id);
	  // 将要移除出当前分类的游戏的order存入全局Map
	  orderMap[id] = tablerow.find("[name='CORDER']").val();
	  var name = tablerow.find("[name='GNAME']").html();
	  $("#table1").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+id+" /><td align='center'><input type='checkbox' name='ckbox1'></td><td align='center' name='GNAME'>"+name+"</td></tr>");
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
	var gid = new Array();
	var order = new Array();
	// 获取下面表的所有游戏id
	$("#form1 [name='GID']").each(function(){
		// 存入数组
		gid.push($(this).val());
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
	gid = gid.toString();
	order = order.toString();
	// 移除的游戏id转成字符串
	removeGid = removeGid.toString();
	$.post("${pageContext.request.contextPath}/basedata/gameCategoryAction!handleCate.action",{cid:cid,gid:gid,removeGid:removeGid,order:order},function(data){
		window.location.reload();
	});  
}
</script>

</body>
</html>
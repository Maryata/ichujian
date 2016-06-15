<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../../css/reCss.css" />
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="../../js/laypage/laypage.js"></script>
<script type="text/javascript">
	// 全局数组，用来存放移除出当前合集的游戏id
	var removeGid = new Array();
	// 定义Map，存放移除合集的游戏的排序
	var orderMap = {};

$(function(){
	var cid = ${param.cid};
	$("#cid").val(cid);
	// 查询不属于当前合集的游戏总页数
	$.post("${pageContext.request.contextPath}/basedata/h5GameColAction!getH5Total.action",{cid:cid},
		function(data){
			var json = $.parseJSON(data);
			if(json != 0){
				$("#gameTotal").val(json);
				// 查询不属于当前合集的游戏
				getGamePage("${pageContext.request.contextPath}/basedata/h5GameColAction!getH5GameList.action",cid);
			}
	});
	// 查询当前合集的游戏总数
	$.post("${pageContext.request.contextPath}/basedata/h5GameColAction!getH5TotalCol.action",{cid:cid},
		function(data){
			var json = $.parseJSON(data);
			if(json != 0){
				$("#colTotal").val(json);
				// 当前合集中的游戏
				getGamePageByColId("${pageContext.request.contextPath}/basedata/h5GameColAction!getH5GamePageByColId.action",cid);
			}
	});
	$.post("${pageContext.request.contextPath}/basedata/h5GameColAction!getH5GameList.action",{cid:cid},
		function(data){
			json = $.parseJSON(data);
			$(json).each(function(){
				$("#table1").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+this.C_ID+" /><td align='center'><input type='checkbox' name='ckbox1' width='130px'></td><td align='center' name='GNAME'>"+this.C_NAME+"</td></tr>");
			});
	});
	$.post("${pageContext.request.contextPath}/basedata/h5GameColAction!getH5GamePageByColId.action",{cid:cid},
		function(data){
			json = $.parseJSON(data);
			$(json).each(function(){
				$("#table2").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+this.C_ID+" /><td align='center'><input type='checkbox' name='ckbox1' width='130px'></td><td align='center' name='GNAME'>"+this.C_NAME+"</td><td align='center'><input name='CORDER' value="+this.C_ORDER+" ></input></td></tr>");
			});
	});
});
// 分页查询所有游戏
function getGamePage(url,cid) {
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("gameTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,cid:cid},
					function(data){
						json = $.parseJSON(data);
						$("#table1").empty();
						$(json).each(function(){
							$("#table1").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+this.C_ID+" /><td align='center' width='130px'><input type='checkbox' name='ckbox1'></td><td align='center' name='GNAME'>"+this.C_NAME+"</td></tr>");
						});
				});
	        }
	    }
	});
}
// 分页查询当前合集中的游戏
function getGamePageByColId(url,cid) {
	laypage({
	    cont: 'page22',
	    pages: document.getElementById("colTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,cid:cid},
					function(data){
						json = $.parseJSON(data);
						$("#table2").empty();
						$(json).each(function(){
							$("#table2").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+this.C_ID+" /><td align='center' width='130px'><input type='checkbox' name='ckbox1'></td><td align='center' name='GNAME'>"+this.C_NAME+"</td><td align='center'><input name='CORDER' value="+this.C_ORDER+" ></input></td></tr>");
						});
				});
	        }
	    }
	});
}
// 条件查询游戏
function getGameCondition(){
	var cid = $("#cid").val();
	var name = $("#appName").val();
	// 条件查询游戏总数
	$.post("${pageContext.request.contextPath}/basedata/h5GameColAction!getH5TotalCondition.action",{cid:cid,name:name},
		function(data){
			var json = $.parseJSON(data);
			$("#page11").empty();
			if(json != 0){
				$("#gameTotal").val(json);
				// 分页查询
				conditionPage("${pageContext.request.contextPath}/basedata/h5GameColAction!queryH5GameCondition.action",cid,name);
			}
	});
	$.post("${pageContext.request.contextPath}/basedata/h5GameColAction!queryH5GameCondition.action",{cid:cid,name:name},
		function(data){
			json = $.parseJSON(data);
			$("#table1").empty();
			$(json).each(function(){
				$("#table1").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+this.C_ID+" /><td align='center' width='130px'><input type='checkbox' name='ckbox1'></td><td align='center' name='GNAME'>"+this.C_NAME+"</td></tr>");
		});
	});
}
function conditionPage(url,cid,name,minSize,maxSize){
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("gameTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,name:name},
					function(data){
						json = $.parseJSON(data);
						$("#table1").empty();
						$(json).each(function(){
							$("#table1").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+this.C_ID+" /><td align='center' width='130px'><input type='checkbox' name='ckbox1'></td><td align='center' name='GNAME'>"+this.C_NAME+"</td></tr>");
						});
				});
	        }
	    }
	});
}
// 条件查询合集中的游戏
function getColGameCondition(){
	var cid = $("#cid").val();
	var name = $("#colAppName").val();
	// 条件查询合集游戏总数
	$.post("${pageContext.request.contextPath}/basedata/h5GameColAction!getH5ColTotalCondition.action",{cid:cid,name:name},
		function(data){
			var json = eval("("+data+")");
			$("#page22").empty();
			if(json != 0){
				$("#colTotal").val(json);
				// 分页查询
				colConditionPage("${pageContext.request.contextPath}/basedata/h5GameColAction!queryH5ColGameCondition.action",cid,name);
			}
	});
	$.post("${pageContext.request.contextPath}/basedata/h5GameColAction!queryH5ColGameCondition.action",{cid:cid,name:name},
		function(data){
			json = eval("("+data+")");
			$("#table2").empty();
			$(json).each(function(){
				$("#table2").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+this.C_ID+" /><td align='center'><input type='checkbox' name='ckbox1'></td><td align='center' name='GNAME'>"+this.C_NAME+"</td><td align='center'><input name='CORDER' value="+(this.C_ORDER==null?'':this.C_ORDER)+" ></input></td></tr>");
		});
	});
}
function colConditionPage(url,cid,name){
	laypage({
	    cont: 'page22',
	    pages: document.getElementById("colTotal").value,
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
						$(json).each(function(){
							$("#table2").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+this.C_ID+" /><td align='center'><input type='checkbox' name='ckbox1'></td><td align='center' name='GNAME'>"+this.C_NAME+"</td><td align='center'><input name='CORDER' value="+(this.C_ORDER==null?'':this.C_ORDER)+" ></input></td></tr>");
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
	  	$("#table2").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+id+" /><td align='center' width='130px'><input type='checkbox' name='ckbox2'></td><td align='center' name='GNAME'>"+name+"</td><td align='center'><input name='CORDER' value="+order+" ></input></td></tr>");
	  }else{
	  	$("#table2").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+id+" /><td align='center' width='130px'><input type='checkbox' name='ckbox2'></td><td align='center' name='GNAME'>"+name+"</td><td align='center'><input name='CORDER' ></input></td></tr>");
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
	  // 将要移除出当前合集的游戏id存入全局数组
	  removeGid.push(id);
	  // 将要移除出当前合集的游戏的合集order存入全局Map
	  orderMap[id] = tablerow.find("[name='CORDER']").val();
	  var name = tablerow.find("[name='GNAME']").html();
	  var size = tablerow.find("[name='GSIZE']").html();
	  $("#table1").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+id+" /><td align='center' width='130px'><input type='checkbox' name='ckbox1'></td><td align='center' name='GNAME'>"+name+"</td></tr>");
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
	// 获取集合id
	var cid = ${param.cid};
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
	$.post("${pageContext.request.contextPath}/basedata/h5GameColAction!h5HandleCol.action",{cid:cid,gid:gid,removeGid:removeGid,order:order},function(data){
		window.location.reload();
	});  
}
</script>
</head>
<body>
<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
  <tr>
    <td align="left" class="jg_fc">当前游戏合集&nbsp;-&gt;&nbsp;${param.cname }&nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>
<form name="form0" method="post">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
        <tr> 
         <td width="20%"  height="23" class="biaodan-top" align="left">名称：</td>
         <td width="55%" class="biaodan-q" align="left">
			<input type="text" name="name" style="border:none; width:100%; height:100%" id="appName" />
		 </td>
         <td align="center" class="biaodan-q">
           <input name="Button" type="button" class="butt" value="查 询" onclick="getGameCondition()" />
           </td>
        </tr>
          </table></td>
  </tr>
</table>
</form>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	<thead>
		<tr class="biaodan-top">
	    <td width="20%">选择</td>
	    <td width="80%">游戏名称</td>
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
         <td width="20%"  height="23" class="biaodan-top" align="left">名称：</td>
         <td width="55%" class="biaodan-q" align="left">
			<input type="text" name="colAppName" style="border:none; width:100%; height:100%" id="colAppName" />
		 </td>
         <td align="center" class="biaodan-q">
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
		    </tr>
			<tr class="biaodan-top">
		    <td width="20%">选择</td>
		    <td width="55%">游戏名称</td>
		    <td width="25%">排序</td>
		  	</tr>
		</thead>
	  	<tbody id="table2"></tbody>
	</table>
	<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
		<input type="hidden" id="cid" name="cid" value="${param.cid }" />
		<tr biaodan-q>
	    	<td align="center">
				<input type="button" value="提 交" class="butt" onclick="submitt()" />
		 	</td>
	  	</tr>
	</table>
<input type="hidden" id="colTotal" />
<div id="page22"></div>
</form>
</body>
</html>
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
    <td align="left" class="jg_fc">当前游戏合集&nbsp;-&gt;&nbsp;${cname }&nbsp;&nbsp;&nbsp;</td>
  </tr>
</table>
<form name="form0" method="post">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
  <tr>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
        <tr> 
         <td width="20%"  height="23" class="biaodan-top" align="left">名称</td>
         <td width="60%" class="biaodan-q" align="left">
			<input type="text" name="name" style="border:none; width:100%; height:100%" id="appName" />
		 </td>
<%--		 <td width="8%" class="biaodan-top" align="left" >大小/MB：</td>--%>
<%--         <td width="20%" class="biaodan-q" align="left">--%>
<!-- 			<input type="text" placeholder="请填写数字" name="minSize" style="width:40%; height:100%" id="minSize" /> -->
<%--			<input type="text" value="请输入数字" onclick="if(this.value=='请输入数字'){this.value='';this.style.color='#999';}" onblur="if(this.value==''){this.value='请输入数字';this.style.color='#999';}" name="minSize" style="width:40%; height:100%; color:#999" id="minSize" />--%>
<%--			<b> ~ </b>--%>
<%--			<input type="text" value="请输入数字" onclick="if(this.value=='请输入数字'){this.value='';this.style.color='#999';}" onblur="if(this.value==''){this.value='请输入数字';this.style.color='#999';}" name="maxSize" style="width:40%; height:100%; color:#999" id="maxSize" />--%>
<%--         </td>--%>
         <td width="20%" align="center" class="biaodan-q">
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
<%--		    <td >游戏大小/MB</td>--%>
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
         <td width="20%"  height="23" class="biaodan-top" align="left">名称</td>
         <td width="60%" class="biaodan-q" align="left">
			<input type="text" name="colAppName" style="border:none; width:100%; height:100%" id="colAppName" />
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
		    </tr>
				<tr class="biaodan-top">
			    <td width="20%">选择</td>
			    <td width="40%">游戏名称</td>
<%--			    <td width="30.5%">游戏大小/MB</td>--%>
			    <td width="40%">排序</td>
		  	</tr>
	  	</thead>
	  	<tbody id="table2"></tbody>
	</table>
	<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
		<input type="hidden" id="cid" name="cid" value="${cid }" />
		<input type="hidden" id="cname" name="cname" value="${cname }" />
		<input type="hidden" id="type" name="type" value="${type }" />
		<tr biaodan-q>
	    	<td align="center">
				<input type="button" value="提 交" class="butt" onclick="submitt()" />
		 	</td>
	  	</tr>
	</table>
<input type="hidden" id="colTotal" />
<div id="page22"></div>
</form>
<script type="text/javascript">
var temp;
	// 全局数组，用来存放移除出当前合集的游戏id
	var removeGid = new Array();
	// 定义Map，存放移除合集的游戏的排序
	var orderMap = {};

$(function(){
	var cid = $("#cid").val();
	var type = $("#type").val();
	// 查询游戏总数
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameColAction!getTotal.action",{cid:cid,type:type},
		function(data){
			var json = eval("("+data+")");
			if(json != 0){
				$("#gameTotal").val(json);
				// 所有游戏
				getGamePage("${pageContext.request.contextPath}/basedata/ekey/eKGameColAction!getGameList.action",cid,type);
			}
	});
	// 查询当前合集的游戏总数
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameColAction!getTotalCol.action",{cid:cid,type:type},
		function(data){
			var json = eval("("+data+")");
			if(json != 0){
				$("#colTotal").val(json);
				// 当前合集中的游戏
				getGamePageByColId("${pageContext.request.contextPath}/basedata/ekey/eKGameColAction!getGamePageByColId.action",cid,type);
			}
	});
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameColAction!getGameList.action",{cid:cid,type:type},
		function(data){
			formData(data, "table1");
	});
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameColAction!getGamePageByColId.action",{cid:cid,type:type},
		function(data){
			formDataCurrCate(data, "table2");
	});
});
// 分页查询所有游戏
function getGamePage(url,cid,type) {
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("gameTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,cid:cid,type:type},
					function(data){
						formData(data, "table1");
				});
	        }
	    }
	});
}
// 分页查询当前合集中的游戏
function getGamePageByColId(url,cid,type) {
	laypage({
	    cont: 'page22',
	    pages: document.getElementById("colTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,cid:cid,type:type},
					function(data){
						formDataCurrCate(data, "table2");
				});
	        }
	    }
	});
}
// 构建“游戏”表
function formData(data, table) {
	var json = eval("("+data+")");
	$("#" + table).empty();
	$(json).each(function(){
		$("#" + table).append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+this.C_ID+" /><td align='center'><input type='checkbox' name='ckbox1'></td><td align='center' name='GNAME'>"+this.C_NAME+"</td></tr>");
	});
}
// 构建“当前分类中的游戏”表
function formDataCurrCate(data, table) {
	var json = eval("("+data+")");
	$("#" + table).empty();
	$(json).each(function(){
		$("#" + table).append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+this.C_ID+" /><td align='center'><input type='checkbox' name='ckbox1'></td><td align='center' name='GNAME'>"+this.C_NAME+"</td><td align='center'><input name='CORDER' value="+this.C_ORDER+" ></input></td></tr>");
	});
}
// 条件查询游戏
function getGameCondition(){
	var cid = $("#cid").val();
	var name = $("#appName").val();
	var type = $("#type").val();
	var minSize = $("#minSize").val();
	if(minSize=="请输入数字"){
		minSize = "";
	}
	var maxSize = $("#maxSize").val();
	if(maxSize=="请输入数字"){
		maxSize = "";
	}
	// 条件查询游戏总数
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameColAction!getTotalCondition.action",{cid:cid,name:name,minSize:minSize,maxSize:maxSize,type:type},
		function(data){
			var json = eval("("+data+")");
			$("#page11").empty();
			if(json != 0){
				$("#gameTotal").val(json);
				// 分页查询
				conditionPage("${pageContext.request.contextPath}/basedata/ekey/eKGameColAction!queryGameCondition.action",cid,name,minSize,maxSize,type);
			}
	});
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameColAction!queryGameCondition.action",{cid:cid,name:name,minSize:minSize,maxSize:maxSize,type:type},
		function(data){
			formData(data, "table1");
	});
}
function conditionPage(url,cid,name,minSize,maxSize,type){
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("gameTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,cid:cid,name:name,minSize:minSize,maxSize:maxSize,type:type},
					function(data){
						formData(data, "table1");
				});
	        }
	    }
	});
}
// 条件查询合集中的游戏
function getColGameCondition(){
	var cid = $("#cid").val();
	var name = $("#colAppName").val();
	var type = $("#type").val();
	// 条件查询合集游戏总数
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameColAction!getColTotalCondition.action",{cid:cid,name:name,type:type},
		function(data){
			var json = eval("("+data+")");
			$("#page22").empty();
			if(json != 0){
				$("#colTotal").val(json);
				// 分页查询
				colConditionPage("${pageContext.request.contextPath}/basedata/ekey/eKGameColAction!queryColGameCondition.action",cid,name,type);
			}
	});
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameColAction!queryColGameCondition.action",{cid:cid,name:name,type:type},
		function(data){
			formDataCurrCate(data, "table2");
	});
}
function colConditionPage(url,cid,name,type){
	laypage({
	    cont: 'page22',
	    pages: document.getElementById("colTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,cid:cid,name:name,type:type},
					function(data){
						formDataCurrCate(data, "table2");
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
	  //var size = tablerow.find("[name='GSIZE']").html();
	  var order = orderMap[id];
	  // 填入下面的table
	  if(order){
	  	//$("#table2").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+id+" /><td align='center'><input type='checkbox' name='ckbox2'></td><td align='center' name='GNAME'>"+name+"</td><td align='center' name='GSIZE'>"+size+"</td><td align='center'><input name='CORDER' value="+order+" ></input></td></tr>");
	  	$("#table2").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+id+" /><td align='center'><input type='checkbox' name='ckbox2'></td><td align='center' name='GNAME'>"+name+"</td><td align='center'><input name='CORDER' value="+order+" ></input></td></tr>");
	  }else{
	  	//$("#table2").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+id+" /><td align='center'><input type='checkbox' name='ckbox2'></td><td align='center' name='GNAME'>"+name+"</td><td align='center' name='GSIZE'>"+size+"</td><td align='center'><input name='CORDER' ></input></td></tr>");
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
	  // 将要移除出当前合集的游戏id存入全局数组
	  removeGid.push(id);
	  // 将要移除出当前合集的游戏的合集order存入全局Map
	  orderMap[id] = tablerow.find("[name='CORDER']").val();
	  var name = tablerow.find("[name='GNAME']").html();
	  //var size = tablerow.find("[name='GSIZE']").html();
	 //$("#table1").append("<tr class='biaodan-q'><input type='hidden' name='GID' value="+id+" /><td align='center'><input type='checkbox' name='ckbox1'></td><td align='center' name='GNAME'>"+name+"</td><td align='center' name='GSIZE'>"+size+"</td></tr>");
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

// 保存合集
function submitt(){
	// 获取集合id
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
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameColAction!handleCol.action",{cid:cid,gid:gid,removeGid:removeGid,order:order},function(data){
		window.location.reload();
	});  
}
</script>
</body>
</html>
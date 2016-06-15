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
<title>游戏提示语</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script type="text/javascript">rootPath="${pageContext.request.contextPath}";</script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.selectseach.min.js"></script>
<script type="text/javascript">
$(function(){
	infoList();
	$("select").selectseach(); 
});
function infoList(){
	var gName = $("#selectinputnamegid").val();// 游戏名
	var gid = $("#gid").val();// 获取游戏id
	var name = $("#infoName").val();// 获取活动标题
	// 查询总数
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameActAction!getTotal.action",{gid:gid,name:name,gName:gName},
		function(data){
			var json = $.parseJSON(data);
			$("#total").val(json);
			// 分页查询
			getGameActPage("${pageContext.request.contextPath}/basedata/ekey/eKGameActAction!GameActList.action",gid,name,gName);
	});
	// 查询所有
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameActAction!GameActList.action",{gid:gid,name:name,gName:gName},
		function(data){
			formData(data, "mytable");
	});
}
// 分页查询
function getGameActPage(url,gid,name,gName) {
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("total").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,gid:gid,name:name,gName:gName},
					function(data){
						formData(data, "mytable");
				});
	        }
	    }
	});
}

function formData(data, table) {
	var json = $.parseJSON(data);
	$("#" + table).empty();
	$(json).each(function(){
		var _str = "<tr class='biaodan-q'><td align='center' name='id' style='width:118px;'>"+(this.ID==null?'':this.ID)+"</td>";
		_str += "<td align='center' name='gname' style='width:354px;'>"+(this.GNAME==null?'':this.GNAME)+"</td>";
		_str += "<td align='center' name='cname' style='width:416px;'>"+(this.CNAME==null?'':this.CNAME)+"</td>";
		_str += "<td align='center' name='handle'>";
		_str += "<input type='button' value='删 除' class='butt'  onclick='deleteInfo("+this.ID+")'><br>";
		_str += '<input value="参与用户" class="butt" onclick="_users('+this.ID+');"><br>';
		_str += "<input type='button' value='修 改' class='butt'  onclick='addInfo("+this.ID+")'></td></tr>";
		$("#" + table).append(_str);
	});
}

// 参与的用户列表
function _users(id) {
	if(id) {
		window.location.href = '${pageContext.request.contextPath}/basedata/ekey/eKGameActAction!users.action?aid=' + id;
	}
	// 没有ID是错误的的
}

// 删除
function deleteInfo(id){
    var flag = confirm("您确定要删除？");
    if(flag){
		$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameActAction!deleteGameAct.action",
				{id:id},function(data){
			window.location.reload();
		});
	}
}
// 校验新增合集名
function addInfo(id){
	if(id==null || id==""){// 没有id，添加
		$("#form1").attr("action","${pageContext.request.contextPath}/basedata/ekey/eKGameActAction!toGameActAdd.action");
		$("#form1").submit();
	}else{// 有id，编辑
		$("#form1").attr("action","${pageContext.request.contextPath}/basedata/ekey/eKGameActAction!toGameActEdit.action");
		$("#editId").val(id);
		$("#form1").submit();
	}
}
</script>
</head>

<body >

<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <!--2015-4-29新样式，面包屑-->
    <td width="62.7%" class="tz_crumbs"><img src="../../images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; 3号键管理 -&gt; 活动管理  </td>
  </tr>
</table>
<form id="form1" method="post">
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
		<input type="hidden" id="editId" name="editId" />
	  <tr> 
	   	<td width="8%"  height="23" class="biaodan-top" align="left">游戏名称：</td>
	    <td width="20%" align="center" class="biaodan-q">
	       	<s:select list="#request.games" name="gid" id="gid" listKey="C_ID" m="search"
	       	 listValue="C_NAME" cssStyle="width:100%;border:none;hight:100%;"
	       	 headerKey="" headerValue=""/>
	    </td>
	    <td width="8%"  height="23" class="biaodan-top" align="left">活动标题：</td>
	   	<td width="20%" class="biaodan-q" align="left">
			<input type="text" style="border:none; width:100%; height:100%" id="infoName" />
	 	</td>
	 	<td width="20%" class="biaodan-q" align="center">
			<input type="button" class="butt" value="查 询" onclick="infoList()" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" class="butt" value="添 加" onclick="addInfo()"/>
	 	</td>
	  </tr>
	</table>
</form>
<br>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
  <thead>
	  <tr class="biaodan-top">
	    <td >活动id</td>
	    <td >游戏名称</td>
	    <td >活动标题</td>
	    <td >操作</td>
	  </tr>
  </thead>
  <tbody id="mytable"></tbody>
</table>
<input type="hidden" id="total" />
<div id="page11"></div>
</body>
</html>


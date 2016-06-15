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
<title>活动详情</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<!--2015-4-29新样式，reCss.css样式一定要最后调用-->
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
</head>
<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; e键管理 -&gt; 活动详情维护 </td>
  </tr>
</table>

<form id="form1" method="post" action="${pageContext.request.contextPath}/basedata/ekey/EKActivityAction!toAdd.action">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
  <tr>
    <td>
    	<table width="100%"  border="0" cellspacing="1" cellpadding="2">
			<tr>
			<td width="8%"  height="23" class="biaodan-top" align="left">活动标题</td>
			<td width="15%" align="center" class="biaodan-q">
				<input onkey type="text" style="border:none; width:100%; height:100%" id="title" />
			</td>
			<td width="8%"  height="23" class="biaodan-top" align="left">活动分类</td>
			<td width="15%" class="biaodan-q" align="left">
				<s:select list="#request.activityCategoryList"  id="ccid" listKey="C_ID"
						  headerKey="" headerValue="" listValue="C_NAME" cssStyle="width:100%;border:none;hight:100%;" m="search"
				/>
			</td>
			<td width="8%"  height="23" class="biaodan-top" align="left">活动状态</td>
			<td width="15%" class="biaodan-q" align="left">
				<select style="width:100%;border:none;hight:100%;" id="status">
					<option></option>
					<option value="1">已审核</option>
					<option value="0">未审核</option>
				</select>
			</td>
	         <td rowspan="2" align="right" class="biaodan-q">
				 <input type="button" class="butt" value="查 询" onclick="infoList()" />
				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
	      <td width="20%">标题</td>
		  <td width="10%">活动分类</td>
		  <td width="10%">添加人员</td>
	      <td width="10%">添加日期</td>
		  <td width="10%">审核状态</td>
		  <td width="10%">审核时间</td>
		  <td width="10%">审核人</td>
	    <td >操作</td><td >预览</td>
	  </tr>
  </thead>
  	<input type="hidden" id="editId" name="editId" />
	<input type="hidden" id="_status" name="status" />
  <tbody id="mytable"></tbody>
</table>
<input type="hidden" id="cateTotal" />
<div id="page11"></div>
</form>
<script type="text/javascript">
	$(function(){
		infoList();
		//$("select").selectseach();
	});
	function infoList(){
    var title = $("#title").val();
	var ccid = $("#ccid").val();
	var status = $("#status").val();
	// 查询应用总数
	$.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityAction!ekActivityList.action",{title:title,ccid:ccid,status:status},
		function(data){
			var json = $.parseJSON(data);
			var count = json[0].count;// 数据总数
			$("#page11").empty();
			if(count != 0){
				setTotal(count,"cateTotal");
				// 分页显示活动详情
				getCatePage("${pageContext.request.contextPath}/basedata/ekey/EKActivityAction!ekActivityList.action",title,ccid,status);
			}
	});
	// 查询活动详情(第一页)
	$.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityAction!ekActivityList.action",{title:title,ccid:ccid,status:status},
		function(data){
			var json = $.parseJSON(data);
			var list = json[0].list;
			var authButt = json[0].authButt;
			$("#mytable").empty();
			$(list).each(function(){
				var _s = "<tr class='biaodan-q'><td align='center'>"+this.C_ID+"</td>";
				_s += "<td align='center'>"+(this.C_TITLE||'')+"</td>";
				_s += "<td align='center'>"+(this.NAME||'')+"</td>";
				_s += "<td align='center'>"+(this.C_CREATOR||'')+"</td>";
				_s += "<td align='center'>"+(this.C_CTIME||'')+"</td>";
				_s += "<td align='center'>"+(this.STATUS||'')+"</td>";
				_s += "<td align='center'>"+(this.C_AUDIT_TIME||'')+"</td>";

				_s += "<td align='center'>"+(this.C_AUDITOR||'')+"</td><td align='center'>";

				if(this.C_AUDIT_STATUS == 0){
					_s += "<input type='button' value='修 改' class='butt' onClick='modifyCate("+this.C_ID+")'/>";
					_s += "<input type='button' value='删 除' class='butt' onClick='deleteCate("+this.C_ID+")'/>";
				}
				if(this.C_AUDIT_STATUS == 0 && authButt == "1"){
					_s += "<input type='button' value='审 核' class='butt' onClick='auditCate("+this.C_ID+",1)'/>";
				} else if(this.C_AUDIT_STATUS == 1 && authButt == '1') {
					_s += "<input type='button' value='修 改' class='butt' onClick='modifyCate("+this.C_ID+")'/>";
					_s += "<input type='button' value='驳 回' class='butt' onClick='auditCate("+this.C_ID+",0)'/>";
				}
				_s += "</td><td><a target='_blank' href='"+this.C_HREF+"' class='butt'>预览</a></td>";
				_s += '</tr>';

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
// 分页显示活动详情
function getCatePage(url,title,ccid,status) {
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("cateTotal").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(),
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,title:title,ccid:ccid,status:status},
					function(data){
						var json = $.parseJSON(data);
						var list = json[0].list;
						var authButt = json[0].authButt;
						$("#mytable").empty();
						$(list).each(function(){
							var _s = "<tr class='biaodan-q'><td align='center'>"+this.C_ID+"</td>";
							_s += "<td align='center'>"+(this.C_TITLE||'')+"</td>";
							_s += "<td align='center'>"+(this.NAME||'')+"</td>";
							_s += "<td align='center'>"+(this.C_CREATOR||'')+"</td>";
							_s += "<td align='center'>"+(this.C_CTIME||'')+"</td>";
							_s += "<td align='center'>"+(this.STATUS||'')+"</td>";
							_s += "<td align='center'>"+(this.C_AUDIT_TIME||'')+"</td>";
							_s += "<td align='center'>"+(this.C_AUDITOR||'')+"</td><td align='center'>";

							if(this.C_AUDIT_STATUS == 0){
								_s += "<input type='button' value='修 改' class='butt' onClick='modifyCate("+this.C_ID+")'/>";
								_s += "<input type='button' value='删 除' class='butt' onClick='deleteCate("+this.C_ID+")'/>";
							}
							if(this.C_AUDIT_STATUS == 0 && authButt == "1"){
								_s += "<input type='button' value='审 核' class='butt' onClick='auditCate("+this.C_ID+",1)'/>";
							} else if(this.C_AUDIT_STATUS == 1 && authButt == '1') {
								_s += "<input type='button' value='修 改' class='butt' onClick='modifyCate("+this.C_ID+")'/>";
								_s += "<input type='button' value='驳 回' class='butt' onClick='auditCate("+this.C_ID+",0)'/>";
							}
							_s += "</td><td><a target='_blank' href='"+this.C_HREF+"' class='butt'>预览</a></td>";
							_s += '</tr>';

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
		$.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityAction!toDel.action",
				{cid:cid},function(data){
			window.location.reload();
		});
	}
}
// 修改
function modifyCate(cid){
	$("#form2").attr("action","${pageContext.request.contextPath}/basedata/ekey/EKActivityAction!toUpdate.action");
	$("#editId").val(cid);
	$("#form2").submit();

}
	//审核/驳回
	function auditCate(cid,v){
        if(confirm("确定执行操作？")) {
            v = v ;
            $("#form2").attr("action","${pageContext.request.contextPath}/basedata/ekey/EKActivityAction!auditActivity.action");
            $("#editId").val(cid);
            $('#_status').val(v);
            $("#form2").submit();
        }
	}
</script>
</body>
</html>


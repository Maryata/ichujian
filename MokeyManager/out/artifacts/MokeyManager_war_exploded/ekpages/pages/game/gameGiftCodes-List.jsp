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
<title>礼包码</title>
<script type="text/javascript">rootPath ="${pageContext.request.contextPath}";</script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.selectseach.min.js"></script>
<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <!--2015-4-29新样式，面包屑-->
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; e键-游戏周边 -&gt; 礼包管理  </td>
  </tr>
</table>
<form id="form1" method="post">
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
		<input type="hidden" id="gid" name="gid" value="<s:property value="#request.gid"/>" />
	  <tr> 
	    <td width="11%" height="23" class="biaodan-top" align="left">领取状态</td>
	   	<td width="15%" class="biaodan-q" align="left">
			<select id="queryState" style="width:100%;height:100%;border:none;" name="queryState">
				<option></option>
				<option value="0">未领取</option>
				<option value="1">已领取</option>
			</select>
	 	</td>
	 	<td width="10%" height="50" class="biaodan-top" align="left">开始日期：</td>
        <td width="16%" class="biaodan-q"  align="left">
       	<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="sDate" name="sDate" 
       		value='<s:property value="#request.sDate"/>' style="border:none; width:100%; height:100%; text-align:center;" /> 
        </td>
        <td width="10%" height="50" class="biaodan-top" align="left">结束日期：</td>
        <td width="16%" class="biaodan-q"  align="left">
       	<input class="Wdate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="eDate" name="eDate" 
       		value='<s:property value="#request.eDate"/>' style="border:none; width:100%; height:100%; text-align:center;" /> 
        </td>
	 	<td class="biaodan-q" align="center">
			<input type="button" class="butt" value="查 询" onclick="codesList()" />
			&nbsp;
			<input type="button" class="butt" value="导 出" onclick="exportExcel()" />
			&nbsp;
			<input type="button" class="butt" value="批量删除" onclick="batchDel()" />
	 	</td>
	  </tr>
	</table>
</form>
<br>
<form id="form2" method="post">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
  <thead>
	  <tr class="biaodan-top">
	    <td width="3%"><input type="checkbox" id="chkAll" onclick="checkAll(this);"></td>
	    <td width="8%">礼包ID</td>
	    <td width="15%">礼包码</td>
	    <td width="13%">开始时间</td>
	    <td width="13%">结束时间</td>
	    <td width="10%">领取状态</td>
	    <td width="16.2%">领取人</td>
	    <td width="10%">领取人ID</td>
	    <td >操作</td>
	  </tr>
  </thead>
  <tbody id="mytable">
  </tbody>
</table>
</form>
<input type="hidden" id="total" />
<div id="page11"></div>
</body>
<script type="text/javascript">
$(function(){
	codesList();
	$("select").selectseach(); 
});
function codesList(){
	var gid = $("#gid").val();// 获取礼包id
	var state = $("#queryState").val();// 领取状态
	var sDate = $("#sDate").val();// 礼包码开始时间
	var eDate = $("#eDate").val();// 结束时间
	// 查询总数
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameGiftAction!getCodesListByGid.action",{gid:gid,state:state,sDate:sDate,eDate:eDate},
		function(data){
			var json = $.parseJSON(data);
			var count = json[0].count;// 数据总数
			$("#page11").empty();
			setTotal(count,"total");
			if(count != 0){
				// 分页查询
				getGameGiftCodesPage("${pageContext.request.contextPath}/basedata/ekey/eKGameGiftAction!getCodesListByGid.action",gid,state,sDate,eDate);
			}
	});
	// 查询所有
	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameGiftAction!getCodesListByGid.action",{gid:gid,state:state,sDate:sDate,eDate:eDate},
		function(data){
			formData(data, "mytable");
	});
}
// 分页查询
function getGameGiftCodesPage(url,gid,state,sDate,eDate) {
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("total").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,gid:gid,state:state,sDate:sDate,eDate:eDate},
					function(data){
						formData(data, "mytable");
				});
	        }
	    }
	});
}

// 构建“数据”列表
function formData(data, table) {
	var json = $.parseJSON(data);
	var list = json[0].list;
	$("#" + table).empty();
	$(list).each(function(){
		var _str = "<tr class='biaodan-q'><td align='center'><input type='checkbox' name='chkOne' value="+(this.C_ID||'')+"></td>";
		_str += "<td align='center' name='id'>"+(this.C_ID||'')+"</td>";
		_str += "<td align='center' name='code'>"+(this.C_CODE||'')+"</td>";
		_str += "<td align='center' name='sdate'>"+(this.C_SDATE||'')+"</td>";
		_str += "<td align='center' name='edate'>"+(this.C_EDATE||'')+"</td>";
		if(this.C_STATE==0){
			_str += "<td align='center' name='state'><font color='green'>未领取</font></td>";
		}else if(this.C_STATE==1){
			_str += "<td align='center' name='state'><font color='red'>已领取</font></td>";
		}else{
			_str += "<td align='center' name='state'></td>";
		}
		_str += "<td align='center' name='uName'>"+(this.UNAME||'')+"</td>";
		_str += "<td align='center' name='uId'>"+(this.UID||'')+"</td>";
		_str += "<td align='center' name='handle'>";
		_str += "<input type='button' value='删 除' class='butt'  onclick='delCode("+this.C_ID+")'></td></tr>";
		$("#" + table).append(_str);
	});
}
// 设置总页数
function setTotal(count,pageTotal){
	var totalPage = 0;
	var rows = 10;// 每页显示条数
	// 计算总页数
	totalPage = parseInt((count - 1) / rows) + 1;
	$("#"+pageTotal).val(totalPage);
}
// 删除
function delCode(id){
    var flag = confirm("您确定要删除？");
    var gid = $("#gid").val();// 获取礼包id
    if(flag){
		$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameGiftAction!delCode.action",
				{id:id,gid:gid},function(data){
			window.location.reload();
		});
	}
}
// 校验新增合集名
function addInfo(id){
	if(id==null || id==""){// 没有id，添加
		$("#form1").attr("action","${pageContext.request.contextPath}/basedata/ekey/eKGameGiftAction!toGameGiftAdd.action");
		$("#form1").submit();
	}else{// 有id，编辑
		$("#form1").attr("action","${pageContext.request.contextPath}/basedata/ekey/eKGameGiftAction!toGameGiftEdit.action");
		$("#editId").val(id);
		$("#form1").submit();
	}
}
// 查询礼包码
function giftCodes(id){
	$("#form1").attr("action","${pageContext.request.contextPath}/basedata/ekey/eKGameGiftAction!toGiftCodesList.action");
	$("#editId").val(id);
	$("#form1").submit();
}
// 批量删除
function batchDel(){
	var flag = confirm("您确定要删除？");
	if(flag){
	   	var gid = $("#gid").val();// 获取礼包id
	   	var ids = new Array();
	  	$("#mytable input:checked").each(function(){
	 	 	var id = $(this).val();
		  	ids.push(id);
	   	});
	   	var id = ids.toString();
	   	$.post("${pageContext.request.contextPath}/basedata/ekey/eKGameGiftAction!batchDel.action",
	   		{id:id,gid:gid},function(data){
				window.location.reload();
	    }); 
    } 
}
// 全选/全不选
function checkAll(obj){
	var _rowIds = $("#mytable input[type='checkbox']");
	$(_rowIds).each(function(){
		if($(obj).is(":checked")){
			$(this).prop("checked", true);
		}else{
			$(this).prop("checked", false);
		}
	});
}
// 导出
function exportExcel(){
	// 获取领取状态
	var state = $("#queryState").val();
	if(state==null || state==""){
		alert("请选择“领取状态”");
		$("#queryState").focus();
		return;
	}
	$("#form1").attr("action","${pageContext.request.contextPath}/basedata/ekey/eKGameGiftAction!exportExcel.action");
	$("#form1").submit();
}
</script>
</html>


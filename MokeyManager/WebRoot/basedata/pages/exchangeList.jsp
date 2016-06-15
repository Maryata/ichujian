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
<title>商城产品兑换</title>
<script type="text/javascript">rootPath ="${pageContext.request.contextPath}";</script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
</head>
<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <!--2015-4-29新样式，面包屑-->
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; 3号键管理 -&gt; 礼包管理  </td>
  </tr>
</table>
<form id="form1" method="post">
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	  	<tr>
			<td width="9%" height="50" class="biaodan-top" align="left">商品名称</td>
			<td width="35.8%" class="biaodan-q"  align="left">
				<input type="text" name="pname" id="pname" style="border:none; width:100%; height:100%" />
			</td>
			<td width="8.9%" height="23" class="biaodan-top" align="left">状态</td>
			<td width="18%" class="biaodan-q" align="left">
			  	<select id="state" style="border:none; width:100%; height:100%" name="state">
				  	<option></option>
				  	<option value="0">未审核</option>
				  	<option value="1">已审核</option>
			  	</select>
			</td>
			<td class="biaodan-q" align="center">
				<input type="button" class="butt" value="查 询" onclick="exchangeList()" />
			</td>
	  </tr>
	</table>
</form>
<br>
<form id="form2" method="post">
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	<thead>
		<input type="hidden" name="c_id" id="c_id" />
		<input type="hidden" name="c_uid" id="c_uid" />
		<input type="hidden" name="c_pid" id="c_pid" />
		<input type="hidden" name="c_cost" id="c_cost" />
		<tr class="biaodan-top">
			<td width="9%">用户ID</td>
			<td width="9%">用户积分</td>
			<td width="9%">产品价值</td>
			<td width="9%">产品总数</td>
			<td width="9%">产品名称</td>
			<td width="9%">联系方式</td>
			<td width="9%">联系类型</td>
			<td width="9%">审核状态</td>
			<td width="9%">申请时间</td>
			<td width="9%">审核时间</td>
			<td >操作</td>
		</tr>
	</thead>
	<tbody id="mytable"></tbody>
</table>
</form>
<input type="hidden" id="total" />
<div id="page11"></div>
</body>
<script type="text/javascript">
$(function(){
	exchangeList(0);// 默认查询未审核的申请记录
});
function exchangeList(state){
	var pname = $("#pname").val();// 商品名
	if(state!=0){
		state = $("#state").val();// 审核状态
	}
	// 查询总数
	$.post("${pageContext.request.contextPath}/basedata/exchangeAction!uncompletedExchange.action",
		{pname:pname,state:state},
		function(data){
			var json = $.parseJSON(data);
			var count = json[0].count;// 数据总数
			$("#page11").empty();
			setTotal(count,"total");
			if(count != 0){
				// 分页查询
				exchangePage("${pageContext.request.contextPath}/basedata/exchangeAction!uncompletedExchange.action",pname,state);
			}
	});
	// 查询所有
	$.post("${pageContext.request.contextPath}/basedata/exchangeAction!uncompletedExchange.action",
		{pname:pname,state:state},
		function(data){
			json = $.parseJSON(data);
			var list = json[0].list;
			$("#mytable").empty();
			$(list).each(function(){
				var cdate = this.C_CDATE==null?"":this.C_CDATE.substr(0, 10);
				var edate = this.C_EDATE==null?"":this.C_CDATE.substr(0, 10);
				var _score = parseInt(this.SCORE);
				var _cost = parseInt(this.COST);
				var _total = parseInt(this.TOTAL);

				var _str = "<tr class='biaodan-q'><input type='hidden' name='id' value="+(this.C_ID||'')+">";
				_str += "<td align='center' name='uid' style='line-height:35px;'>"+(this.C_UID||'')+"</td>";
				_str += "<td align='center' name='score' style='line-height:35px;'>"+(this.SCORE||'')+"</td>";
				_str += "<td align='center' name='cost' style='line-height:35px;'>"+(this.COST||'')+"</td>";
				_str += "<td align='center' name='total' style='line-height:35px;'>"+(this.TOTAL||'')+"</td>";
				_str += "<td align='center' name='pname'>"+(this.C_NAME||'')+"</td>";
				_str += "<td align='center' name='contact'>"+(this.C_CONTACT||'')+"</td>";
				if(this.C_TYPE=='1'){
					_str += "<td align='center' name='type'>手机</td>";
				}else if(this.C_TYPE=='2'){
					_str += "<td align='center' name='type'>QQ</td>";
				}
				if(this.C_STATE=='1'){
					_str += "<td align='center' name='state'><font color='green'>已审核</font></td>";
				}else if(this.C_STATE=='0'){
					_str += "<td align='center' name='state'><font color='red'>未审核</font></td>";
				}
				_str += "<td align='center' name='cdate'>"+cdate+"</td>";
				_str += "<td align='center' name='edate'>"+edate+"</td>";
				_str += "<td align='center' name='handle'>";
				if(this.C_STATE=='0'){
					if (_score < _cost) {
						_str += "<input type='button' disabled='disabled' value='积分不足' ></td></tr>"
					} else if (_total < 1) {
						_str += "<input type='button' disabled='disabled' value='商品不足' ></td></tr>"
					} else {
						_str += "<input type='button' value='审核通过' class='butt'  onclick='complete("+this.C_ID+","+this.C_UID+","+this.C_PID+","+this.COST+")'></td></tr>"
					}
				}
				$("#mytable").append(_str);
			});
	});
}
// 分页查询
function exchangePage(url,pname,state) {
	laypage({
	    cont: 'page11',
	    pages: document.getElementById("total").value,
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	           $.post(url,{page:e.curr,pname:pname,state:state},
					function(data){
						json = $.parseJSON(data);
						$("#mytable").empty();
						$(json[0].list).each(function(){
							var cdate = this.C_CDATE==null?"":this.C_CDATE.substr(0, 10);
							var edate = this.C_EDATE==null?"":this.C_CDATE.substr(0, 10);
							var _score = parseInt(this.SCORE);
							var _cost = parseInt(this.COST);
							var _total = parseInt(this.TOTAL);

							var _str = "<tr class='biaodan-q'><input type='hidden' name='id' value="+(this.C_ID||'')+">";
							_str += "<td align='center' name='uid' style='line-height:35px;'>"+(this.C_UID||'')+"</td>";
							_str += "<td align='center' name='score' style='line-height:35px;'>"+(this.SCORE||'')+"</td>";
							_str += "<td align='center' name='cost' style='line-height:35px;'>"+(this.COST||'')+"</td>";
							_str += "<td align='center' name='total' style='line-height:35px;'>"+(this.TOTAL||'')+"</td>";
							_str += "<td align='center' name='pname'>"+(this.C_NAME||'')+"</td>";
							_str += "<td align='center' name='contact'>"+(this.C_CONTACT||'')+"</td>";
							if(this.C_TYPE=='1'){
								_str += "<td align='center' name='type'>手机</td>";
							}else if(this.C_TYPE=='2'){
								_str += "<td align='center' name='type'>QQ</td>";
							}
							if(this.C_STATE=='1'){
								_str += "<td align='center' name='state'><font color='green'>已审核</font></td>";
							}else if(this.C_STATE=='0'){
								_str += "<td align='center' name='state'><font color='red'>未审核</font></td>";
							}
							_str += "<td align='center' name='cdate'>"+cdate+"</td>";
							_str += "<td align='center' name='edate'>"+edate+"</td>";
							_str += "<td align='center' name='handle'>";
							if(this.C_STATE=='0'){
								if (_score < _cost) {
									_str += "<input type='button' disabled='disabled' value='积分不足' ></td></tr>"
								} else if (_total < 1) {
									_str += "<input type='button' disabled='disabled' value='商品不足' ></td></tr>"
								} else {
									_str += "<input type='button' value='审核通过' class='butt'  onclick='complete("+this.C_ID+","+this.C_UID+","+this.C_PID+","+this.COST+")'></td></tr>"
								}
							}
							$("#mytable").append(_str);
						});
				});
	        }
	    }
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
// 审核通过
function complete(id, uid, pid, cost){
	var flag = confirm("确定通过？");
	var id = $("#c_id").val(id);
	var uid = $("#c_uid").val(uid);
	var pid = $("#c_pid").val(pid);
	var cost = $("#c_cost").val(cost);
	if(flag){
		$("#form2").attr("action", "${pageContext.request.contextPath}/basedata/exchangeAction!complete.action");
		$("#form2").submit();
	}
}
</script>
</html>


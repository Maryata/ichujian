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
<title>飞语话单</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reCss.css" />
<!--2015-4-29新样式，reCss.css样式一定要最后调用-->
<script src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laypage/laypage.js"></script>
<script src="${pageContext.request.contextPath}/ekpages/js/jquery.json.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/ekpages/layer/layer.js"></script>
</head>
<body >
<table width="98%" height="35"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="62.7%" class="tz_crumbs"><img src="${pageContext.request.contextPath}/images/crumbs.jpg" width="9" height="9" /> 系统信息 -&gt; e键管理 -&gt; 飞语话单 </td>
  </tr>
</table>
<form id="form1" method="post" action="">
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
		<tr>
			<td>
				<table width="100%"  border="0" cellspacing="1" cellpadding="2">
					<tr>
						<td width="8%"  height="23" class="biaodan-top" align="left">呼叫ID：</td>
						<td width="20%" class="biaodan-q" align="left">
							<input type="text" style="border:none; width:100%; height:100%" id="hjId" />
						</td>
						<td width="8%"  height="23" class="biaodan-top" align="left">中断原因</td>
						<td width="15%" class="biaodan-q" align="left">
							<select style="width:100%;border:none;hight:100%;" id="zdReason">
								<option></option>
								<option value="1">主叫挂断</option>
								<option value="2">被叫挂断</option>
								<option value="3">呼叫不可及</option>
								<option value="5">超时未接</option>
								<option value="6">拒接或超时</option>
								<option value="7">网络问题</option>
								<option value="9">API请求挂断</option>
								<option value="10">余额不足</option>
								<option value="11">呼叫失败，系统错误</option>
								<option value="12">被叫拒接</option>
								<option value="13">被叫无人接听</option>
								<option value="14">被叫正忙</option>
								<option value="15">被叫不在线</option>
								<option value="16">呼叫超过最大呼叫时间</option>
							</select>
						</td>
						<td rowspan="2" align="right" class="biaodan-q">
							<input type="button" class="butt" value="查 询" onclick="infoList()" />
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
  <input type="hidden" id="editId" name="editId"/>
  <input type="hidden" id="fyAccountIds" name="fyAccountIds"/>
	  <tr class="biaodan-top">
		  <td width="5%">ID</td>
	      <td width="15%">呼叫ID</td>
		  <td width="10%">呼叫的唯一标识</td>
		  <td width="10%">开始时间</td>
	      <td width="10%">结束时间</td>
		  <td width="10%">中断原因</td>
		  <td width="10%">显号类型</td>
		  <td width="10%">是否录音</td>
	  </tr>
  </thead>
  <tbody id="mytable"></tbody>
</table>
<input type="hidden" id="cateTotal" />
<div id="page11"></div>
</form>
<script type="text/javascript">
	$(function(){
		infoList();
	});
	function infoList(){
		var hjId = $("#hjId").val();
		var zdReason = $("#zdReason").val();
		// 查询应用总数
		$.post("${pageContext.request.contextPath}/basedata/ekey/eKFreeCallAction!callHisList.action",{hjId:hjId,zdReason:zdReason},
				function(data){
					var json = $.parseJSON(data);
					var count = json[0].count;// 数据总数
					$("#page11").empty();
					if(count != 0){
						setTotal(count,"cateTotal");
						// 分页显示活动详情
						getCatePage("${pageContext.request.contextPath}/basedata/ekey/eKFreeCallAction!callHisList.action",hjId,zdReason);
					}
				});
		// 查询活动详情(第一页)
		$.post("${pageContext.request.contextPath}/basedata/ekey/eKFreeCallAction!callHisList.action",{hjId:hjId,zdReason:zdReason},
				function(data){
					var json = $.parseJSON(data);
					var list = json[0].list;
					$("#mytable").empty();
					$(list).each(function(){
						var _s = "<tr class='biaodan-q'><td align='center'>"+this.C_ID+"</td>";
						_s += "<td align='center'>"+(this.C_FYCALLID||'')+"</td>";
						_s += "<td align='center'>"+(this.C_APPCALLID||'')+"</td>";
						_s += "<td align='center'>"+(this.C_CALLSTARTTIME||'')+"</td>";
						_s += "<td align='center'>"+(this.C_CALLENDTIME||'')+"</td>";
						_s += "<td align='center'>"+(this.STOPREASON||'')+"</td>";
						_s += "<td align='center'>"+(this.TRUESHOWNUMBERTYPE||'')+"</td>";
						_s += "<td align='center'>"+(this.TRUEIFRECORD||'')+"</td>";
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
	function getCatePage(url,hjId,zdReason) {
		laypage({
			cont: 'page11',
			pages: document.getElementById("cateTotal").value,
			curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
				return page ? page[1] : 1;
			}(),
			jump: function(e, first){ //触发分页后的回调
				if(!first){ //一定要加此判断，否则初始时会无限刷新
					$.post(url,{page:e.curr,hjId,zdReason},
							function(data){
								var json = $.parseJSON(data);
								var list = json[0].list;
								$("#mytable").empty();
								$(list).each(function(){
									var _s = "<tr class='biaodan-q'><td align='center'>"+this.C_ID+"</td>";
									_s += "<td align='center'>"+(this.C_FYCALLID||'')+"</td>";
									_s += "<td align='center'>"+(this.C_APPCALLID||'')+"</td>";
									_s += "<td align='center'>"+(this.C_CALLSTARTTIME||'')+"</td>";
									_s += "<td align='center'>"+(this.C_CALLENDTIME||'')+"</td>";
									_s += "<td align='center'>"+(this.STOPREASON||'')+"</td>";
									_s += "<td align='center'>"+(this.TRUESHOWNUMBERTYPE||'')+"</td>";
									_s += "<td align='center'>"+(this.TRUEIFRECORD||'')+"</td>";
									_s += '</tr>';
									$("#mytable").append(_s);
								});
							});
				}
			}
		});
	}
</script>
</body>
</html>


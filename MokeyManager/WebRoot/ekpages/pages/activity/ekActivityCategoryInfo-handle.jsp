<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<script src="${pageContext.request.contextPath}/ekpages/js/jquery.json.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function(){
			$("select").selectseach();
		});
	</script>
	<style>input[type="text"]{ border:none; width:100%; height:100%; }</style>
</head>
<body>
<table width="98%" height="35"  border="0" align="center" cellpadding="1" >
	<tr>
		<td align="left" class="jg_fc">当前活动分类&nbsp;-&gt;&nbsp;<s:property value="#request.cname"/>&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table>
<form name="form0" method="post">
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tbg">
		<tr>
			<td>
				<table width="100%"  border="0" cellspacing="1" cellpadding="2">
					<tr>
						<td width="7%" height="23" class="biaodan-top" align="left">活动名称：</td>
						<td width="20%" class="biaodan-q" align="left">
							<input type="text" name="title" style="border:none; width:100%; height:100%" id="editTitle" />
						</td>
						<td width="8%"  height="23" class="biaodan-top" align="left">活动分类</td>
						<td width="15%" class="biaodan-q" align="left">
							<s:select list="#request.activityCategoryList"  id="editCcid" listKey="C_ID"
									  headerKey="" headerValue="" listValue="C_NAME" cssStyle="width:100%;border:none;hight:100%;" m="search"
							/>
						</td>
						<td width="8%"  height="23" class="biaodan-top" align="left">图片类型</td>
						<td width="14.7%" class="biaodan-q" align="left">
							<select name="imgType" id="imgType" value="" style="width:100%;border:none;hight:100%;">
								<option value="0"></option>
								<option value="1">宽图</option>
								<option value="2">窄图</option>
								<option value="3">首页宽图</option>
							</select>
						</td>
						<td align="center" class="biaodan-q">
							<input name="Button" type="button" class="butt" value="查 询" onclick="giftList()" />
						</td>
					</tr>
				</table></td>
		</tr>
	</table>
</form>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
	<thead>
	<tr class="biaodan-top">
		<td width="7%">选择</td>
		<td width="51.1%">活动名称</td>
		<td width="14.7%">是否过期</td>
		<td width="9%">宽图</td>
		<td width="9%">窄图</td>
		<td >首页宽图</td>
	</tr>
	</thead>
	<tbody id="table1"></tbody>
</table>
<input type="hidden" id="giftTotal" />
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
					<td width="7%"  height="23" class="biaodan-top" align="left">活动名称：</td>
					<td width="65.65%" class="biaodan-q" align="left">
						<input type="text" name="indexName" style="border:none; width:100%; height:100%" id="indexEditName" />
					</td>
					<td align="center" class="biaodan-q">
						<input name="Button" type="button" class="butt" value="查 询" onclick="cateGiftList()" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<input type="hidden" name="tagAll" id="tagAll" value="${List}">
<form id="form1" method="post">
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" class="tbg sortable">
		<thead>
		</tr>
		<tr class="biaodan-top">
			<td width="7%">选择</td>
			<td width="25.8%">活动名称</td>
			<td width="5%">是否过期</td>
			<td width="5%">宽图</td>
			<td width="5%">窄图</td>
			<td width="5%">首页宽图</td>
			<td width="18%">标题</td>
			<td width="18%">子标题</td>
			<s:if test='#request.type=="0"'><td width="5%">角标选择</td></s:if>
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
	// 全局数组，用来存放移除出当前的分类id
	var removeGid = new Array();
	// 定义Map，存放移除的分类的排序
	var orderMap = {};
    var tagIdMap={};
	var tagAllStr;
	$(function(){
		tagAllStr = $("#tagAll").val();
		giftList();
		cateGiftList();
	});
	// 非当前的其他所有分类
	function giftList(){
		var cid = $("#cid").val();
		var title = $("#editTitle").val();
		var ccid =$("#editCcid").val();
		var imgType =$("#imgType").val();
		// 查询非当前的其他所有分类
		$.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityCategoryInfoAction!getAllActivityCategoryInfo.action",{cid:cid,title:title,ccid:ccid,imgType:imgType},
				function(data){
					var json = eval("("+data+")");
					var count = json[0].count;// 数据总数
					$("#page11").empty();
					if(json != 0){
						setTotal(count,"giftTotal");
						// 所有分类
						getGiftPage("${pageContext.request.contextPath}/basedata/ekey/EKActivityCategoryInfoAction!getAllActivityCategoryInfo.action",cid,title,ccid,imgType);
					}
				});
		$.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityCategoryInfoAction!getAllActivityCategoryInfo.action",{cid:cid,title:title,ccid:ccid,imgType:imgType},
				function(data){
					json = eval("("+data+")");
					var list = json[0].list;
					$("#table1").empty();
					$(list).each(function(){
						var longImg = this.C_IMG_LONG != null ? "<div style='color : green'>有</div>" : "<div style='color : grey'>无</div>";
						var thinImg = this.C_IMG_THIN != null ? "<div style='color : green'>有</div>" : "<div style='color : grey'>无</div>";
						var idxLongImg = this.C_IMG_INDEX_LONG != null ? "<div style='color : green'>有</div>" : "<div style='color : grey'>无</div>";
						var _str = "<tr class='biaodan-q'><input type='hidden' name='AID' value="+this.C_ID+" />";
						_str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
						_str += "<td align='center' name='ATITLE'>"+this.C_TITLE+"</td>";
						_str += "<td align='center' name='isOver'>"+this.isOver+"</td>";
						_str += "<td align='center' name='C_IMG_LONG'>"+longImg+"</td>";
						_str += "<td align='center' name='C_IMG_THIN'>"+thinImg+"</td>";
						_str += "<td align='center' name='C_IMG_INDEX_LONG'>"+idxLongImg+"</td></tr>";
						$("#table1").append(_str);
					});
				});
	}
	// 当前的所有分类
	function cateGiftList(){
		var cid = $("#cid").val();
		var title = $("#indexEditName").val();
		// 查询当前的分类总数
		$.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityCategoryInfoAction!getCurrActivityCaInfo.action",{cid:cid,title:title},
				function(data){
					var json = eval("("+data+")");
					var count = json[0].count;// 数据总数
					$("#page22").empty();
					if(json != 0){
						setTotal(count,"cateTotal");
						// 当前中的分类
						currCateGiftPage("${pageContext.request.contextPath}/basedata/ekey/EKActivityCategoryInfoAction!getCurrActivityCaInfo.action",cid,title);
					}
				});
		$.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityCategoryInfoAction!getCurrActivityCaInfo.action",{cid:cid,title:title},
				function(data){
					json = eval("("+data+")");
					var list = json[0].list;
					$("#table2").empty();
					$(list).each(function(){
						var longImg = this.C_IMG_LONG != null ? "<div style='color : green'>有</div>" : "<div style='color : grey'>无</div>";
						var thinImg = this.C_IMG_THIN != null ? "<div style='color : green'>有</div>" : "<div style='color : grey'>无</div>";
						var idxLongImg = this.C_IMG_INDEX_LONG != null ? "<div style='color : green'>有</div>" : "<div style='color : grey'>无</div>";
						var _str = "<tr class='biaodan-q'><input type='hidden' name='AID' value="+this.C_ID+" />";
						var d=this;
						var tagid=d.C_TAGID;
						var _str = "<tr class='biaodan-q'><input type='hidden' name='AID' value="+this.C_AID+" />";
						_str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
						_str += "<td align='center' name='ATITLE'>"+this.C_TITLE+"</td>";
						_str += "<td align='center' name='isOver'>"+this.isOver+"</td>";
						_str += "<td align='center' name='C_IMG_LONG'>"+longImg+"</td>";
						_str += "<td align='center' name='C_IMG_THIN'>"+thinImg+"</td>";
						_str += "<td align='center' name='C_IMG_INDEX_LONG'>"+idxLongImg+"</td>";
						_str += "<td align='center'><input type='text' name='CTITLE' value='"+(this.CTITLE||'')+"'/></td>";
						_str += "<td align='center'><input type='text' name='CSUBTITLE' value='"+(this.CSUBTITLE||'')+"'/></td>";

						if(this.C_CID=="1" || this.C_CID=="2"){
							_str += "<td align='center'><select  name='ATAGID' id='ATAGID' style=''>";
							_str +="<option value='' data-val=''>请选择标签</option>";
							_str +="<c:forEach var='item' items='${List}' varStatus='status'>";

							var itemId = ${item.C_ID};
							if(itemId == tagid) {
								_str +="<option selected value='${item.C_ID}'>${item.C_NAME}</option>";
							}else{
								_str +="<option  value='${item.C_ID}'>${item.C_NAME}</option>";
							}
							_str +="</c:forEach></select></td>";

						}
						_str += "<td align='center'><input type='text' name='AORDER' value='"+(this.C_ORDER||'')+"'/></td></tr>";
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
	// 分页查询所有分类
	function getGiftPage(url,cid,title) {
		laypage({
			cont: 'page11',
			pages: document.getElementById("giftTotal").value,
			curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
				return page ? page[1] : 1;
			}(),
			jump: function(e, first){ //触发分页后的回调
				if(!first){ //一定要加此判断，否则初始时会无限刷新
					$.post(url,{page:e.curr,cid:cid,title:title},
							function(data){
								json = eval("("+data+")");
								var list = json[0].list;
								$("#table1").empty();
								$(list).each(function(){
									var longImg = this.C_IMG_LONG != null ? "<div style='color : green'>有</div>" : "<div style='color : grey'>无</div>";
									var thinImg = this.C_IMG_THIN != null ? "<div style='color : green'>有</div>" : "<div style='color : grey'>无</div>";
									var idxLongImg = this.C_IMG_INDEX_LONG != null ? "<div style='color : green'>有</div>" : "<div style='color : grey'>无</div>";
									var _str = "<tr class='biaodan-q'><input type='hidden' name='AID' value="+this.C_ID+" />";
									_str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
									_str += "<td align='center' name='ATITLE'>"+this.C_TITLE+"</td>";
									_str += "<td align='center' name='isOver'>"+this.isOver+"</td>";
									_str += "<td align='center' name='C_IMG_LONG'>"+longImg+"</td>";
									_str += "<td align='center' name='C_IMG_THIN'>"+thinImg+"</td>";
									_str += "<td align='center' name='C_IMG_INDEX_LONG'>"+idxLongImg+"</td></tr>";
									$("#table1").append(_str);
								});
							});
				}
			}
		});
	}
	// 分页查询当前中的分类
	function currCateGiftPage(url,cid,title) {
		laypage({
			cont: 'page22',
			pages: document.getElementById("cateTotal").value,
			curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
				return page ? page[1] : 1;
			}(),
			jump: function(e, first){ //触发分页后的回调
				if(!first){ //一定要加此判断，否则初始时会无限刷新
					$.post(url,{page:e.curr,cid:cid,title:title},
							function(data){
								json = eval("("+data+")");
								$("#table2").empty();
								$(json[0].list).each(function(){
									var longImg = this.C_IMG_LONG != null ? "<div style='color : green'>有</div>" : "<div style='color : grey'>无</div>";
									var thinImg = this.C_IMG_THIN != null ? "<div style='color : green'>有</div>" : "<div style='color : grey'>无</div>";
									var idxLongImg = this.C_IMG_INDEX_LONG != null ? "<div style='color : green'>有</div>" : "<div style='color : grey'>无</div>";
									var _str = "<tr class='biaodan-q'><input type='hidden' name='AID' value="+this.C_ID+" />";
									var d=this;
									var tagid=d.C_TAGID;
									var _str = "<tr class='biaodan-q'><input type='hidden' name='AID' value="+this.C_AID+" />";
									_str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
									_str += "<td align='center' name='ATITLE'>"+this.C_TITLE+"</td>";
									_str += "<td align='center' name='isOver'>"+this.isOver+"</td>";
									_str += "<td align='center' name='C_IMG_LONG'>"+longImg+"</td>";
									_str += "<td align='center' name='C_IMG_THIN'>"+thinImg+"</td>";
									_str += "<td align='center' name='C_IMG_INDEX_LONG'>"+idxLongImg+"</td>";
									_str += "<td align='center'><input type='text' name='CTITLE' value='"+(this.CTITLE||'')+"'/></td>";
									_str += "<td align='center'><input type='text' name='CSUBTITLE' value='"+(this.CSUBTITLE||'')+"'/></td>";
									if(d.C_CID=="1" || d.C_CID=="2"){
										_str += "<td align='center'><select  name='ATAGID' id='ATAGID' style=''>";
										_str +="<option value='' data-val=''>请选择标签</option>";
										_str +="<c:forEach var='item' items='${List}' varStatus='status'>";
										var itemId = ${item.C_ID};
										if(itemId == tagid) {
											_str +="<option selected value='${item.C_ID}'>${item.C_NAME}</option>";
										}else{
											_str +="<option  value='${item.C_ID}'>${item.C_NAME}</option>";
										}
										_str +="</c:forEach></select></td>";

									}
									_str += "<td align='center'><input type='text' name='AORDER' value='"+(this.C_ORDER||'')+"'/></td></tr>";
									$("#table2").append(_str);
								});
							});
				}
			}
		});
	}
	var i=0;
	// 上  ==》 下
	function down(){
// 获取上面table中选中，并遍历
		$("#table1 input:checked").each(function(){
			// 获取当前checkbox所在的行
			var tablerow = $(this).parent("td").parent("tr");
			// 获取id、name、size
			var id = tablerow.find("input[name='AID']").val();
			var title = tablerow.find("[name='ATITLE']").html();
			var isOver = tablerow.find("[name='isOver']").html();
			var longImg = tablerow.find("[name='C_IMG_LONG']").html();
			var thinImg = tablerow.find("[name='C_IMG_THIN']").html();
			var idxLongImg = tablerow.find("[name='C_IMG_INDEX_LONG']").html();
			var order = orderMap[id];
			var tagId = tagIdMap[id];
			// 填入下面的table
			if ($("#cid").val()=="1" || $("#cid").val()=="2"){
//				var i=0;
				if(order){
					var str__ = "<tr class='biaodan-q'><input type='hidden' name='AID' value="+id+" />" +
							"<td align='center'><input type='checkbox' name='ckbox2'></td>" +
							"<td align='center' name='ATITLE'>"+title+"</td>" +
					        "<td align='center' name='isOver'>"+isOver+"</td>" +
							"<td align='center' name='C_IMG_LONG'>"+longImg+"</td>" +
							"<td align='center' name='C_IMG_THIN'>"+thinImg+"</td>" +
							"<td align='center' name='C_IMG_INDEX_LONG'>"+idxLongImg+"</td>" +
							"<td align='center'><input type='text' name='CTITLE'/></td>" +
							"<td align='center'><input type='text' name='CSUBTITLE'/></td>" +
							"<td align='center'><select  name='ATAGID' class='ATAGID"+i+"' id='ATAGID' style='' >" + //data-value='"+tagId+"'
							"<option value=''>请选择标签</option></select></td>"+
							"<td align='center'><input name='AORDER' value="+order+" ></input></td>" +
							"</tr>"
					$("#table2").append(str__);
				}else{
					$("#table2").append("<tr class='biaodan-q'><input type='hidden' name='AID' value="+id+" />" +
							"<td align='center'><input type='checkbox' name='ckbox2'></td>" +
							"<td align='center' name='ATITLE'>"+title+"</td>" +
							"<td align='center' name='isOver'>"+isOver+"</td>" +
							"<td align='center' name='C_IMG_LONG'>"+longImg+"</td>" +
							"<td align='center' name='C_IMG_THIN'>"+thinImg+"</td>" +
							"<td align='center' name='C_IMG_INDEX_LONG'>"+idxLongImg+"</td>" +
							"<td align='center'><input type='text' name='CTITLE'/></td>" +
							"<td align='center'><input type='text' name='CSUBTITLE'/></td>" +
							"<td align='center'><select  name='ATAGID' class='ATAGID"+i+"' id='ATAGID' style=''>" +
							"<option value=''>请选择标签</option></select></td>"+
							"<td align='center'><input name='AORDER' ></input></td>" +
							"</tr>");
				}

				/*var $tagAllStr = $.toJSON(tagAllStr);
				$tagAllStr = $tagAllStr.substr(1,$tagAllStr.length-1);
				$($tagAllStr).each(function(){
					var _tr="<option value='"+this.C_ID+"'>"+this.C_NAME+"</option>";
					$(".ATAGID"+i).append(_tr);
				});
				if(tagId){
					$(".ATAGID"+i).val(tagId);
				}
				i++;*/

				$.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityCategoryInfoAction!activityCategoryList.action",
					function(data){
						json = eval("("+data+")");
						$(json).each(function(){
						var _tr="<option value='"+this.C_ID+"'>"+this.C_NAME+"</option>";
						   $(".ATAGID"+i).append(_tr);
						});
						if(tagId){
							$(".ATAGID"+i).val(tagId);
						}
						i++;
				});

			}else{
				if(order){
					$("#table2").append("<tr class='biaodan-q'><input type='hidden' name='AID' value="+id+" />" +
							"<td align='center'><input type='checkbox' name='ckbox2'></td>" +
							"<td align='center' name='ATITLE'>"+title+"</td>" +
							"<td align='center' name='isOver'>"+isOver+"</td>" +
							"<td align='center' name='LONGIMG'>"+longImg+"</td>" +
							"<td align='center' name='THINIMG'>"+thinImg+"</td>" +
							"<td align='center' name='C_IMG_INDEX_LONG'>"+idxLongImg+"</td>" +
							"<td align='center'><input type='text' name='CTITLE'/></td>" +
							"<td align='center'><input type='text' name='CSUBTITLE'/></td>" +
							"<td align='center'><input name='AORDER' value="+order+" ></input></td>" +
							"</tr>");
				}else{
					$("#table2").append("<tr class='biaodan-q'><input type='hidden' name='AID' value="+id+" />" +
							"<td align='center'><input type='checkbox' name='ckbox2'></td>" +
							"<td align='center' name='ATITLE'>"+title+"</td>" +
							"<td align='center' name='isOver'>"+isOver+"</td>" +
							"<td align='center' name='LONGIMG'>"+longImg+"</td>" +
							"<td align='center' name='THINIMG'>"+thinImg+"</td>" +
							"<td align='center' name='C_IMG_INDEX_LONG'>"+idxLongImg+"</td>" +
							"<td align='center'><input type='text' name='CTITLE'/></td>" +
							"<td align='center'><input type='text' name='CSUBTITLE'/></td>" +
							"<td align='center'><input name='AORDER' ></input></td>" +
							"</tr>");
				}

			}
			// 移除
			tablerow.remove();
		}) ;
	}
	// 下  ==》 上
	function up(){
		$("#table2 input:checked").each(function(){
			var tablerow = $(this).parent("td").parent("tr");
			var id = tablerow.find("input[name='AID']").val();
			// 将要移除出当前的分类id存入全局数组
			removeGid.push(id);
			// 将要移除出当前的分类的order存入全局Map
			orderMap[id] = tablerow.find("[name='AORDER']").val();
			tagIdMap[id] = tablerow.find("[name='ATAGID']").val();
			var title = tablerow.find("[name='ATITLE']").html();
			var isOver = tablerow.find("[name='isOver']").html();
			var longImg = tablerow.find("[name='C_IMG_LONG']").html();
			var thinImg = tablerow.find("[name='C_IMG_THIN']").html();
			var idxLongImg = tablerow.find("[name='C_IMG_INDEX_LONG']").html();
			$("#table1").append("<tr class='biaodan-q'><input type='hidden' name='AID' value="+id+" />" +
					"<td align='center'><input type='checkbox' name='ckbox1'></td>" +
					"<td align='center' name='ATITLE'>"+title+"</td>" +
					"<td align='center' name='isOver'>"+isOver+"</td>" +
					"<td align='center' name='C_IMG_LONG'>"+longImg+"</td>" +
					"<td align='center' name='C_IMG_THIN'>"+thinImg+"</td>" +
					"<td align='center' name='C_IMG_INDEX_LONG'>"+idxLongImg+"</td>" +
					"</tr>");
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
	// 保存
	function submitt(){
		// 获取id
		var cid = $("#cid").val();
		var aid = new Array();
		var order = new Array();
		var tagid = new Array();
		var title = new Array();
		var subtitle = new Array();
		// 获取下面表的所有分类id
		$("#form1 [name='AID']").each(function(){
			// 存入数组
			aid.push($(this).val());
		});
		$("#form1 [name='AORDER']").each(function(){
			var _order = $(this).val();
			// 如果排序值为空，设置其值为""
			if(_order=="" || _order==null){
				_order = "n";
			}
			// 存入数组
			order.push(_order);
		});
		if ($("#cid").val()=="1" || $("#cid").val()=="2") {
			$("select[name=ATAGID]").each(function () {
				// 存入数组
				var _tagid = $(this).val();
				if(_tagid=="" ||  _tagid==null){
					_tagid="n";
				}
				tagid.push(_tagid);
			});
		}
		$("#form1 [name='CTITLE']").each(function(){
			var _order = $(this).val();
			// 如果排序值为空，设置其值为""
			if(_order=="" || _order==null){
				_order = "n";
			}
			// 存入数组
			title.push(_order);
		});
		$("#form1 [name='CSUBTITLE']").each(function(){
			var _order = $(this).val();
			// 如果排序值为空，设置其值为""
			if(_order=="" || _order==null){
				_order = "n";
			}
			// 存入数组
			subtitle.push(_order);
		});
		// 转成字符串
		aid = aid.toString();
		order = order.toString();
		tagid = tagid.toString();
		title = title.toString();
		subtitle = subtitle.toString();
		// 移除的分类id转成字符串
		removeGid = removeGid.toString();
		$.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityCategoryInfoAction!handleActivity.action",
				{cid:cid,aid:aid,removeGid:removeGid,order:order,tagid:tagid,title:title,subtitle:subtitle},function(data){
			window.location.reload();
		});
	}
</script>

</body>
</html>
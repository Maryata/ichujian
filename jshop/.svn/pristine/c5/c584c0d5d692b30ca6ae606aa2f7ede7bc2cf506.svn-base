<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="折扣方案管理">
	<form action="${basepath}/manage/discntSolutn" method="post" theme="simple">
	<input type="hidden" name="pager.offset" id="pager_offset" value="0">
		<table class="table table-bordered">
			<tr>
				<td colspan="8">
<#--<%-- 					<s:submit method="selectList" value="查询" cssClass="btn btn-primary"/> --%>-->
					<a href="selectList" class="btn btn-primary btn-select">
						<i class="icon-search icon-white"></i> 查询
					</a>
					<a href="toAdd" class="btn btn-success">
						<i class="icon-plus-sign icon-white"></i> 添加
					</a>
<#--<%-- 					<s:submit method="toAdd" value="添加" cssClass="btn btn-success" /> --%>-->
<#--<%-- 					<s:submit method="deletes" onclick="return deleteSelect();" value="删除" cssClass="btn btn-danger" /> --%>-->
					
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<#include "/manage/system/pager.ftl"/>
					</div>
				</td>
			</tr>
		</table>
		
		<table class="table table-bordered table-hover">
			<tr style="background-color: #dff0d8;">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th nowrap="nowrap" style="width: 105px;">方案名称</th>
				<th nowrap="nowrap" style="width: 75px;">更新时间</th>
				<th nowrap="nowrap" style="width: 75px;">操作人</th>
				<th nowrap="nowrap" style="width: 215px;">备注</th>
				<th nowrap="nowrap" style="width: 75px;">状态</th>
				<th style="width: 155px;">操作</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td nowrap="nowrap">&nbsp;${item.name!""}</td>
					<td nowrap="nowrap">&nbsp;${item.updateTime!""}</td>
					<td nowrap="nowrap">&nbsp;${item.updatePerson!""}</td>
					<td nowrap="nowrap">&nbsp;${item.comment!""}</td>
					<td nowrap="nowrap">&nbsp;${item.status!""}</td>
					<td nowrap="nowrap">
						<a href="toDiscntEdit?id=${item.id}" class="btn btn-primary">
							<i class="icon-search icon-white"></i> 编辑
						</a>
					</td>
					
				</tr>
			</#list>
			<tr>
				<td colspan="16" style="text-align: center;">
					<#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>
	</form>
<script type="text/javascript">
	$(function() {
		function c1(f) {
			$(":checkbox").each(function() {
				$(this).attr("checked", f);
			});
		}
		$("#firstCheckbox").click(function() {
			if ($(this).attr("checked")) {
				c1(true);
			} else {
				c1(false);
			}
		});

	});
	function deleteSelect() {
		if ($("input:checked").size() == 0) {
			return false;
		}
		return confirm("确定删除选择的记录?");
	}
</script>
</@page.pageBase>
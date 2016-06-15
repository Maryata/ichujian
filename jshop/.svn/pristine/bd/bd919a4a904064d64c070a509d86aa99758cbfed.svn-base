<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="资料下载记录">
	<form action="${basepath}/manage/dataApply" method="post" theme="simple">
	<input type="hidden" name="pager.offset" id="pager_offset" value="0">
	    <div class="table-responsive">
		<table class="table table-bordered table-condensed table-hover">
			<tr>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">资料名称</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<input type="text" value="${e.title!""}" name="title" style="width:80%;"
				 		class="search-query input-small" id="account" />
					</div>
				</td>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">文件名称</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<input type="text" value="${e.fname!""}" name="fname" style="width:40%;" 
						class="search-query input-small" id="trueName" />
					</div>
				</td>		
			</tr>
			<tr>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">公司名称</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<input type="text" value="${e.companyName!""}" name="companyName" style="width:80%;"
				 		class="search-query input-small" id="account" />
					</div>
				</td>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:8px;">申请时间</div></td>
				<td style="text-align: left;" colspan="3" nowrap="nowrap">
					<div style="text-align: left;margin-top:1px;">
					<input id="d4311" class="Wdate search-query input-small" type="text" name="startDate" style="width:30%;"
					value="${e.startDate!""}"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
					~ 
					<input id="d4312" class="Wdate search-query input-small" type="text" name="endDate" style="width:30%;"
					value="${e.endDate!""}"
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="28">
					<button method="selectList" class="btn btn-primary btn-select" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</button>
					
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<#include "/manage/system/pager.ftl"/>
					</div>
				</td>
				
			</tr>
		</table>
        </div>
		<div class="table-responsive">
		<table class="table table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th nowrap="nowrap">id</th>
				<th nowrap="nowrap">资料名称</th>
				<th nowrap="nowrap">文件名称</th>
				<th nowrap="nowrap">公司名称</th>
				<th nowrap="nowrap">申请时间</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids" value="${item.id!""}"</td>
					<td nowrap="nowrap">&nbsp;${item.id!""}</td>
					<td nowrap="nowrap">&nbsp;${item.title!""}</td>
					<td nowrap="nowrap">&nbsp;${item.fname!""}</td>
					<td nowrap="nowrap">&nbsp;${item.companyName!""}</td>
					<td nowrap="nowrap">&nbsp;${item.applyDate!""}</td>
				</tr>
			</#list>
			<tr>
				<td colspan="16" style="text-align: center;">
					<#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>
        </div>
	</form>
</@page.pageBase>
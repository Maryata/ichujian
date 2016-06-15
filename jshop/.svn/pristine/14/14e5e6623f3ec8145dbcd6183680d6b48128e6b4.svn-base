<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="适配需求清单">
	<form action="${basepath}/manage/accountAdapter" method="post" theme="simple">
	<input type="hidden" name="pager.offset" id="pager_offset" value="0">
	    <div class="table-responsive">
		<table class="table table-bordered table-condensed table-hover">
			<tr>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">公司名称</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<input type="text" value="${e.companyName!""}" name="companyName" style="width:80%;"
				 		class="search-query input-small" id="account" />
					</div>
				</td>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">品牌</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<input type="text" value="${e.brand!""}" name="brand" style="width:80%;" 
						class="search-query input-small" id="trueName" />
					</div>
				</td>		
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">型号</div></td>
			    		<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<input type="text" value="${e.models!""}" name="models" style="width:80%;" 
						class="search-query input-small" id="trueName" />
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
				<th nowrap="nowrap">公司名称</th>
				<th nowrap="nowrap">具体品牌</th>
				<th nowrap="nowrap">具体型号</th>
				<th nowrap="nowrap">预计采购量</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids" value="${item.id!""}"</td>
					<td nowrap="nowrap">&nbsp;${item.id!""}</td>
					<td nowrap="nowrap">&nbsp;${item.companyName!""}</td>
					<td nowrap="nowrap">&nbsp;${item.brand!""}</td>
					<td nowrap="nowrap">&nbsp;${item.models!""}</td>
					<td nowrap="nowrap">&nbsp;${item.buyNumber!""}</td>
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
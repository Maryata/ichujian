<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="会员管理">
	<form action="${basepath}/manage/account" method="post" theme="simple">
	    <input type="hidden" name="pager.offset" id="pager_offset" value="0">
		<div class="table-responsive">
		<table class="table table-bordered table-condensed table-hover">
			<tr>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">账号</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<input type="text" value="${e.account!""}" name="account" style="width:80%;"
				 		class="search-query input-small" id="account" />
					</div>
				</td>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">姓名</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<input type="text" value="${e.trueName!""}" name="trueName" style="width:80%;" 
						class="search-query input-small" id="trueName" /></td>
					</div>	
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">冻结状态</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<#assign freezeMap = {'':'','y':'已冻结','n':'未冻结'}>
                    <select id="freeze" name="freeze" class="input-medium">
						<#list freezeMap?keys as key>
                            <option value="${key}" <#if e.freeze?? && e.freeze==key>selected="selected" </#if>>${freezeMap[key]}</option>
						</#list>
                    </select>
                    </div>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;  padding: 5px;" nowrap="nowrap"><div style="text-align: center;margin-top:8px;">公司</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:1px;">
					<input type="text" value="${e.companyName!""}" name="companyName"  style="width:80%;" 
						class="search-query input-small" id="account" />
					</div>	
				</td>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:8px;">电话</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:1px;">
					<input type="text" value="${e.tel!""}" name="tel"  style="width:80%;" 
						class="search-query input-small" id="account" />
					</div>
				</td>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:8px;">注册日期</div></td>
				<td style="text-align: left;" colspan="3" nowrap="nowrap">
					<div style="text-align: left;margin-top:1px;">
					<input id="d4311" class="Wdate search-query input-small" type="text" name="startDate" style="width:40%;"
					value="${e.startDate!""}"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
					~ 
					<input id="d4312" class="Wdate search-query input-small" type="text" name="endDate" style="width:40%;"
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
				<!--<th nowrap="nowrap">登陆方式</th>-->
				<th nowrap="nowrap">公司名称</th>
				<th nowrap="nowrap">帐号</th>
				<th nowrap="nowrap">销售人员</th>
				<th nowrap="nowrap">姓名</th>
				<th nowrap="nowrap">邮箱</th>
				<th nowrap="nowrap">注册日期</th>
				
				<th nowrap="nowrap">联系人电话</th>
<!--				<th nowrap="nowrap">最后登录时间</th>
				<th nowrap="nowrap">最后登录IP</th> -->
				<th nowrap="nowrap">是否冻结</th>
<!-- 				<th width="150px">冻结时间</th> -->
				<th nowrap="nowrap">操作</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids" value="${item.id!""}"</td>
					<td nowrap="nowrap">&nbsp;${item.companyName!""}</td>
					<td nowrap="nowrap">&nbsp;<a href="toAccontEdit?id=${item.id}">${item.account!""}</a></td>
					<td nowrap="nowrap">&nbsp;${item.username!""}</td>
					<td nowrap="nowrap">&nbsp;${item.trueName!""}</td>
					<td nowrap="nowrap">&nbsp;${item.email!""}</td>
					<td nowrap="nowrap">&nbsp;<#if item.regeistDate??>${item.regeistDate[0..10]!""}</#if></td>
					<td nowrap="nowrap">&nbsp;${item.tel!""}</td>
					<td nowrap="nowrap">&nbsp;
						<#if item.freeze?? && item.freeze=="y">
							<img alt="" src="${basepath}/resource/images/login.gif">
						<#elseif item.freeze?? && item.freeze=="n">
						<#else>
							异常
						</#if>
					</td>
					<td nowrap="nowrap">
						<a target="_blank" href="show?account=${item.account!""}">查看</a>
						<a href="toFreeze?id=${item.id!""}">冻结</a>
					</td>
				</tr>
			</#list>
			<tr>
				<td colspan="16" style="text-align: center;">
					<#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>
        </div>
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
</script>

</@page.pageBase>
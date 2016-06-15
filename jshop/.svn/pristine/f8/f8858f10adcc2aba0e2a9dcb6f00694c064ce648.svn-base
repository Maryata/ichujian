<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="佣金管理">
<style type="text/css">
.titleCss {
	background-color: #e6e6e6;
	border: solid 1px #e6e6e6;
	position: relative;
	margin: -1px 0 0 0;
	line-height: 32px;
	text-align: left;
}

.aCss {
	overflow: hidden;
	word-break: keep-all;
	white-space: nowrap;
	text-overflow: ellipsis;
	text-align: left;
	font-size: 12px;
}

.liCss {
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	height: 30px;
	text-align: left;
	margin-left: 10px;
	margin-right: 10px;
}
</style>
	<form action="${basepath}/manage/order" method="post" theme="simple">
	<input type="hidden" name="pager.offset" id="pager_offset" value="0">
		<table class="table table-bordered">
			<tr>
				<td>订单号</td>
				<td>
					<input type="text" value="${e.serialId!""}" name="serialId" class="search-query"/>
				</td>
				<td>时间范围</td>
				<td><input id="d4311" class="Wdate search-query" type="text" name="startDate"
					value="${e.startDate!""}"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
					~ 
					<input id="d4312" class="Wdate search-query" type="text" name="endDate"
					value="${e.endDate!""}"
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>
				</td>
			</tr>
			<tr>
			<td>审核状态</td>
				<td>
					<#assign sMap ={"0":"未申请","1":"未审核","2":"已审核"}>
                    <select id="commisionStatus" name="commisionStatus" class="search-query">
                        <option value="">请选择</option>
						<#list sMap?keys as key>
                            <option value="${key}" <#if e.commisionStatus?? && e.commisionStatus==key>selected="selected" </#if>>${sMap[key]}</option>
						</#list>
                    </select></td>
			<td>会员账号</td>
		    <td><input type="text"  value="${e.account!""}" name="account" class="search-query"/></td>
			</tr>
			<tr>
			<td>销售代码</td>
		    <td><input type="text"  value="${e.commisionUser!""}" name="commisionUser" class="search-query"/></td>
			<td>审核人员</td>
		    <td><input type="text"  value="${e.commisionCheckUser!""}" name="commisionCheckUser" class="search-query"/></td>
			</tr>
			<tr>
				<td colspan="8">
						<button method="selectList" class="btn btn-primary btn-select" onclick="selectList(this)">
							<i class="icon-search icon-white"></i> 查询
						</button>
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<#include "/manage/system/pager.ftl"/>
					</div>
				</td>
			</tr>
		</table>
				
		<table class="table table-bordered table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th>订单号</th>
				<th>总金额</th>
				<th>会员账号</th>
                <th>销售</th>
				<th width="85px;">创建时间</th>
				<th>申请人</th>
				<th width="85px;">申请时间</th>
				<th>佣金</th>
				<th>审核人员</th>
				<th width="85px;">审核时间</th>
				<th>审核状态</th>
				<th width="60px">操作</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids" value="${item.id!""}" /></td>
					<td>${item.serialId!""}</td>
					<td id="ptotal">${item.ptotal!""}</td>
					<td>${item.account!""}</td>
					<td>${item.username!""}</td>
					<td>${item.createdate!""}</td>
					<td class="item_Sale">${item.commisionSale!""}</td>
					<td class="item_Time">${item.commisionTime!""}</td>
					<td class="item_commision">${item.commision!""}</td>
					<td class="item_checkUser">${item.commisionCheckUser!""}</td>
                    <td class="item_checkTime">${item.commisionCheckTime!""}</td>
                    <td class="item_status">
                       <#if item.commisionStatus=="0">未申请</#if>
                       <#if item.commisionStatus=="1">未审核</#if>
                       <#if item.commisionStatus=="2">已审核</#if>
                    </td>
					<td class="item_cz">
					<#if checkPrivilege("/manage/commision/commisionStatus0") > <#if item.commisionStatus=="0"><a href="javascript:;" class="btn btn-primary btn-select" onclick="comm_update(this,${item.id!""},1)">申请</a></#if> </#if>
					<#if checkPrivilege("/manage/commision/commisionStatus1") > <#if item.commisionStatus=="1"><a href="javascript:;" class="btn btn-primary btn-select" onclick="comm_update(this,${item.id!""},2)">审核</a></#if> </#if>
					</td>
				</tr>
			</#list>
			<tr>
				<td colspan="55" style="text-align: center;">
					<#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>
	</form>
<script>
 function comm_update(_btn,id,status){
   _btn = $(_btn);
   var ptotal= $("#ptotal").text();
   $.ajax({
		url: basepath+"/manage/commision/toUpdate",
		type:"post",
		data:{id:id ,commisionStatus:status,ptotal:ptotal},
		dataType:"json",
		success:function(data){
			if(data.status==1){
				var e = data.e;
				var _trObj = _btn.parent().parent();
				if(e.commisionStatus=="1"){
					_trObj.find(".item_Sale").html(e.commisionSale);
					_trObj.find(".item_Time").html(e.commisionTime);
					_trObj.find(".item_status").html("已申请");
					_trObj.find(".item_cz").html('<a href="javascript:;" class="btn btn-primary btn-select" onclick="comm_update(this,'+id+',2)">审核</a>');
					alert("申请完成");
				}
				if(e.commisionStatus=="2"){
					_trObj.find(".item_checkUser").html(e.commisionCheckUser);
					_trObj.find(".item_checkTime").html(e.commisionCheckTime);
					_trObj.find(".item_commision").html(e.commision);
					_trObj.find(".item_status").html("已审核");
					_trObj.find(".item_cz").html("");
					alert("审核完成");
				}
			}
		},
		error:function(){
			//alert("error");
		}
	});
 }
</script>
</@page.pageBase>
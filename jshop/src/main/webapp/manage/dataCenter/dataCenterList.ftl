<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="资料中心">
	<form action="${basepath}/manage/dataCenter" method="post" theme="simple">
	<input type="hidden" name="pager.offset" id="pager_offset" value="0">
		<div class="table-responsive">
		<table class="table table-bordered table-condensed table-hover">
		 <tr>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">标题</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<input type="text" value="${e.title!""}" name="title" style="width:80%;"
				 		class="search-query input-small" id="account" />
					</div>
				</td>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">栏目</div></td>
				<td style="text-align: left;">
				<div style="text-align: left;margin-top:8px;">
				<#assign dicIdMap = sysDic("dataCenter_dicId") >
             	<select name="dicId" id="dicId">
             		<option value="">请选择</option>
             		<#list dicIdMap?keys as key>
             			<option value="${key}" <#if e.dicId?? && e.dicId==key>selected</#if> >
             				${dicIdMap[key]}
         				</option>
             		</#list>	
             	</select>
             	</div>
			    </td>
			</tr>
			<tr>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:8px;">创建日期</div></td>
				<td style="text-align: left;" colspan="3" nowrap="nowrap">
					<div style="text-align: left;margin-top:1px;">
					<input id="d4311" class="Wdate search-query input-small" type="text" name="startDate" style="width:20%;"
					value="${e.startDate!""}"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
					~ 
					<input id="d4312" class="Wdate search-query input-small" type="text" name="endDate" style="width:20%;"
					value="${e.endDate!""}"
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="28">
					<button method="selectList" class="btn btn-primary btn-select " onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</button>
					<button method="toAdd" class="btn btn-success" onclick="selectList(this)">
						<i class="icon-search icon-success"></i> 添加
					</button>
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<#include "/manage/system/pager.ftl"/>
					</div>
				</td>
			</tr>
		</table>
		</div>
		<div class="table-responsive">
		<table class="table table-bordered table-hover">
			<tr style="background-color: #dff0d8;">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th nowrap="nowrap" style="width: 105px;">标题</th>
				<th nowrap="nowrap" style="width: 75px;">内容</th>
				<th nowrap="nowrap" style="width: 75px;">栏目</th>
				<th nowrap="nowrap" style="width: 215px;">创建时间</th>
				<th nowrap="nowrap" style="width: 75px;">状态</th>
				<th style="width: 155px;">操作</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td nowrap="nowrap">&nbsp;${item.title!""}</td>
					<td nowrap="nowrap">&nbsp;${item.content!""}</td>
					<td nowrap="nowrap">&nbsp;${item.dicId!""}</td>
					<td nowrap="nowrap">&nbsp;${item.createDate!""}</td>
					<td nowrap="nowrap">&nbsp;${item.status!""}</td>
					<td nowrap="nowrap">
						<a href="toDataCenterEdit?id=${item.id}" class="btn btn-primary">
							<i class="icon-search icon-white"></i> 编辑
						</a>
						<button method="deletes" class="btn btn-danger" onclick="return deleteSelect('${item.id}');">
                        <i class="icon-remove-sign icon-white"></i> 删除
                    </button>
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
	function deleteSelect(id) {
			try{
			if(confirm("确定删除当前记录?")){
				$.blockUI({ message: "系统处理中，请等待...",css: { 
			        border: 'none', 
			        padding: '15px', 
			        backgroundColor: '#000', 
			        '-webkit-border-radius': '10px', 
			        '-moz-border-radius': '10px', 
			        opacity: .5, 
			        color: '#fff' 
			    }});
				var _url = "delete?id="+id;
				$.ajax({
				  type: 'POST',
				  url: _url,
				  data: {},
				  async:false,
				  success: function(data){
					  if(data){
					  	$('.btn-select').click();
						//var _form = $("#form");
						//_form.attr('action','${basepath}/manage/dataCenter/selectList').submit();
					  }
					  
					  jQuery.unblockUI();
				  },
				  dataType: "text",
				  error:function(){
					  	jQuery.unblockUI();
						alert("加载失败，请联系管理员。");
				  }
				});
			}
		}catch(e){
			console.log("eee="+e);
		}
		return false;
	}
</script>
</@page.pageBase>
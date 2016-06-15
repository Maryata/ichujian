<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="用户管理">
<script>
	$(function(){
        $('#dataTables-example').dataTable({
            responsive: true,
            scrollY: 400,
            searching: false,
            "serverSide": true,
            "processing": true,
            "ajax": {
				url:"loadData",
				dataSrc:"list"
            },
            pagingType : "bootstrap",
            serverParams:function(data){
			$.extend(data, {"text":"text value"});
			},
			columns:[
                {name:"ID", "orderable": false, title:'<input type="checkbox" name="firstCheckbox"/>', data:"id"},
				{name:"username", title:"User Name", data:"username"},
				{name:"nickname", title:"Nick Name", data:"nickname"},
                {name:"createtime", title:"create Time", data:"createtime"},
                {name:"role_name", title:"ROLE NAME", data:"role_name"},
			],
			columnDefs:[
				{name:"ID",data:"id",targets:[0], render:function ( data, type, row, meta ) {
            // 'sort', 'type' and undefined all just use the integer
            return '<input type="checkbox" name="ids" value="'+data+'"/>';
        }}
			]
        });
	});
</script>
<form action="${basepath}/manage/user" method="post">
<input type="hidden" name="pager.offset" id="pager_offset" value="0">
	<table class="table table-bordered table-condensed">
		<tr>
			<td ><div style="text-align: center;margin-top:14px;">账号</div></td>
			<td style="text-align: left;" >
				<div style="text-align: left;margin-top:8px;">
                <input type="text" name="username" id="username" value="${e.username!""}"/>
                </div>
			</td>
			<td ><div style="text-align: center;margin-top:14px;">工号</div></td>
			<td style="text-align: left;" >
				<div style="text-align: left;margin-top:8px;">
                <input type="text" name="usercode" id="usercode" value="${e.usercode!""}"/>
                </div>
			</td>
			<td ><div style="text-align: center;margin-top:14px;">姓名</div></td>
			<td style="text-align: left;" >
				<div style="text-align: left;margin-top:8px;">
                <input type="text" name="nickname" id="nickname" value="${e.nickname!""}"/>
                </div>
			</td>
			<td ><div style="text-align: center;margin-top:14px;">状态</div></td>
			<td style="text-align: left;" >
				<div style="text-align: left;margin-top:8px;">
				<select name="status" id="status" class="input-small">
                    <option value="">全部</option>
                    <option value="y">启用</option>
                    <option value="n">禁用</option>
                </select>
                </div>
			</td>
		</tr>
		<tr>
			<td colspan="11">
            <#if checkPrivilege("/manage/user/insert") >
					<button method="selectList" class="btn btn-primary btn-select" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</button>
             </#if>
				<#if checkPrivilege("/manage/user/insert") >
                <a href="${basepath}/manage//user/toAdd" class="btn btn-success"><i class="icon-plus-sign icon-white"></i> 添加</a>
				</#if>

				<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
                    <#include "/manage/system/pager.ftl"/>
				</div>

			</td>
		</tr>
	</table>

    <table class="table table-striped table-bordered table-hover" id="dataTables-example">
    </table>
	<table class="table table-bordered table-hover">
		<thead>
		<tr style="background-color: #dff0d8">
			<th width="20"><input type="checkbox" id="firstCheckbox"/></th>
			<th style="display: none;">id</th>
			<th>帐号</th>
			<th>工号</th>
			<th>手机号</th>
			<th>销售品牌</th>
			<th>姓名</th>
			<th>创建时间</th>
			<th>角色</th>
			<th>部门</th>
			<th>状态</th>
			<th nowrap="nowrap">操作</th>
		</tr></thead>
        <#list pager.list as item>
			<tr >
				<td><#if item.id!=1><input type="checkbox" name="ids" value="${item.id}"/></#if></td>
				<td  style="display: none;">&nbsp;${item.id!""}</td>
				<td>&nbsp;${item.username!""}</td>
				<td>&nbsp;${item.usercode!""}</td>
				<td>&nbsp;${item.phone!""}</td>
				<#assign brandTypeMap = sysDic("user_brand") >
				<td>
					<#list brandTypeMap?keys as key>
						 <#if item.brandType?? && item.brandType==key>
		                 ${brandTypeMap[key]}
		                 </#if>
	               </#list>
				  <#--&nbsp;${item.brandType!""}-->
				</td>
				<td>&nbsp;${item.nickname!""}</td>
				<td>&nbsp;${item.createtime!""}</td>
				<td>&nbsp;${item.role_name}</td>
				<td>&nbsp; <#if item.dept??> ${item.dept.name!""}</#if></td> 
				<td>
                    <#if item.status == "y">
                        <img src="${basepath}/resource/images/action_check.gif">
                    <#else >
                        <img src="${basepath}/resource/images/action_delete.gif">
                    </#if>
				</td>
				<td>
                    <#if checkPrivilege("/manage//user/edit")>
                        <a href="${basepath}/manage//user/toEdit?id=${item.id}">编辑</a>
                    </#if>
				</td>
			</tr>
        </#list>
		<tr><td colspan="16" style="text-align:center;">
        <#include "/manage/system/pager.ftl"/>
		</tr>
	</table>
</form>
</@page.pageBase>
<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="数据字典管理">
<form action="${basepath}/manage/systemDict" method="post">
	<table class="table table-bordered table-condensed">
		<tr>
			<td ><div style="text-align: center;margin-top:14px;">类别代码</div></td>
			<td style="text-align: left;" >
				<div style="text-align: left;margin-top:8px;">
                <input type="text" name="ddlKey" id="ddlKey" value="${e.ddlKey!""}"/>
                </div>
			</td>
			<td ><div style="text-align: center;margin-top:14px;">类别名称</div></td>
			<td style="text-align: left;" >
				<div style="text-align: left;margin-top:8px;">
                <input type="text" name="ddlVal" id="ddlVal" value="${e.ddlVal!""}"/>
                </div>
			</td>
			<td style="text-align: center;" >
				<button method="systemDictList" class="btn btn-primary" onclick="selectList(this)">
					<i class="icon-search icon-white"></i> 查询
				</button>
			 	<a href="toAdd" class="btn btn-success">
                	<i class="icon-plus-sign icon-white"></i> 添加
            	</a>
			</td>
		</tr>
	</table>

    <table class="table table-striped table-bordered table-hover" id="dataTables-example">
    </table>
	<table class="table table-bordered table-hover">
		<thead>
		<tr style="background-color: #dff0d8">
			<th style="width:15.5%">id</th>
			<th style="width:40%">类别代码</th>
			<th style="width:30%">名称</th>
			<th nowrap="nowrap" style="width:30%;text-align: center;">操作</th>
		</tr></thead>
        <#list list as item>
			<tr>
				<td>&nbsp;${item.id!""}</td>
				<td>&nbsp;${item.ddlKey!""}</td>
				<td>&nbsp;${item.ddlVal!""}</td>
				<td style="text-align: center;">
					<a href="toSystemDictEdit?id=${item.id!""}" class="btn btn-primary">
						<i class="icon-search icon-white"></i> 编辑
					</a>
				</td>
			</tr>
        </#list>
	</table>
</form>
</@page.pageBase>
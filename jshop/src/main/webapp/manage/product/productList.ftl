<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="商品管理">
<style type="text/css">
.product-name {
	display: inline-block;
	width: 250px;
	overflow: hidden; /*注意不要写在最后了*/
	white-space: nowrap;
	-o-text-overflow: ellipsis;
	text-overflow: ellipsis;
}
</style>

	<form action="${basepath}/manage/product" namespace="/manage" method="post" theme="simple">
	    <input type="hidden" name="pager.offset" id="pager_offset" value="0">
		<input type="hidden" value="${e.catalogID!""}" id="catalogID"/>
		<table class="table table-bordered table-condensed">
			<tr>
				<td style="text-align: right;">商品编号</td>
				<td style="text-align: left;"><input type="text"  value="${e.pcode!""}" name="pcode"  class="search-query input-small"
						id="pcode" /></td>
				<td style="text-align: right;">状态</td>
				<td style="text-align: left;">
                    <#assign map = {"0":'',"1":'新增',"2":'已上架',"3":'已下架'}>
                    <select id="status" name="status" class="input-medium">
                        <#list map?keys as key>
                            <option value="${key}" <#if e.status?? && e.status==key?eval>selected="selected" </#if> >${map[key]}</option>
                        </#list>
				</td>
				<td style="text-align: right;">
					商品分类
				</td>
				<td>
<!-- 							> -->
					<select name="catalogID" id="catalogSelect" class="input-medium">
						<option></option>
                        <#list catalogs as item>
							<option pid="0" value="${item.id!""}" ><font color='red'>${item.name!""}</font></option>
                            <#if item.children??>
                                <#list item.children as item>
								    <option value="${item.id!""}">&nbsp;&nbsp;&nbsp;&nbsp;${item.name!""}</option>
                                </#list>
                            </#if>
                        </#list>
					</select>
				</td>
				<td style="text-align: right;">产品线</td>
				<td style="text-align: left;" >
                    <#assign plineMap = sysDic("account_purchaseType") >
                    <select id="pline" name="pline" class="input-medium">
                        <option value=""></option>
                        <#list plineMap?keys as key>
                    	 <#if key[0..0] !="A" && key[0..0] !="Z">                                     
                         <option value="${key}" <#if e.pline?? && e.pline==key>selected="selected" </#if>>${plineMap[key]}</option>
                         </#if>
                    </#list>
                    <option value=''>========配件在下面========</option>
                    <#list plineMap?keys as key> 
                        	 <#if key[0..0]=="A" || key[0..0]=="Z">                                     
                              <option value="${key}" <#if e.pline?? && e.pline==key>selected="selected" </#if>>${plineMap[key]}</option>
                             </#if>
                        </#list>
				</td>
				<td style="text-align: right;">新品</td>
				<td style="text-align: left;">
                    <#assign map = {'':'','y':'是','n':'否'}>
                    <select id="isnew" name="isnew" class="input-medium">
                        <#list map?keys as key>
                            <option value="${key}" <#if e.isnew?? && e.isnew==key>selected="selected" </#if>>${map[key]}</option>
                        </#list>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">商品名称</td>
				<td style="text-align: left;" ><input type="text"  value="${e.name!""}" name="name"  class="input-small"
						id="name" /></td>
				<td style="text-align: right;">录入日期</td>
				<td style="text-align: left;" colspan="9">
					<input id="d4311" class="Wdate search-query input-small" type="text" name="startDate"
					value="${e.startDate!""}"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
					~ 
					<input id="d4312" class="Wdate search-query input-small" type="text" name="endDate"
					value="${e.endDate!""}"
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>
				</td>
<!-- 						<td style="text-align: right;">录入人</td> -->
			</tr>
			<tr>
				<td colspan="20">
                    <#if checkPrivilege("product/selectList")>
						<button method="selectList" class="btn btn-primary btn-select" onclick="selectList(this)">
							<i class="icon-search icon-white"></i> 查询
						</button>
                    </#if>

                     <#if checkPrivilege("product/toAdd")>
						<a href="toAdd" class="btn btn-success">
							<i class="icon-plus-sign icon-white"></i> 添加
						</a>
                     </#if>

                    <#if checkPrivilege("product/deletes")>
						<button method="deletes" class="btn btn-danger" onclick="return submitIDs(this,'确定删除选择的记录?');">
							<i class="icon-remove-sign icon-white"></i> 删除
						</button>
                    </#if>

                    <#if checkPrivilege("product/updateUp")>
<!-- 							<i class="icon-arrow-up icon-white"></i> 上架 -->
						<button method="updateUp" class="btn btn-warning" onclick="return submitIDs(this,'确定上架选择的记录?');">
							<i class="icon-arrow-up icon-white"></i> 上架
						</button>
                    </#if>

                    <#if checkPrivilege("product/updateDown")>
						<button method="updateDown" class="btn btn-warning" onclick="return submitIDs(this,'确定下架选择的记录?');">
							<i class="icon-arrow-down icon-white"></i> 下架
						</button>
                    </#if>

					<!--
					<a target="_blank" href="${systemSetting().www}/product/selectMemoryStock.html" class="btn btn-info">
					<i class="icon-eye-open icon-white"></i> 内存库存查询</a>
					-->
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<#include "/manage/system/pager.ftl"/>
					</div>
				</td>
			</tr>
		</table>

		<table class="table table-bordered table-condensed table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th nowrap="nowrap">编号</th>
				<th nowrap="nowrap">商品编号</th>
				<th>名称</th>
				<th>定价</th>
				<th>企业用户价格</th>
				<th>录入日期</th>
				<th>新品</th>
				<#--<th>特价</th>
				<th>浏览次数</th>
				<th>库存</th>
				<th>销量</th>-->
				<th>状态</th>
				<th width="60">操作</th>
			</tr>
            <#list pager.list as item>
				<tr ondblclick="dbclick(${item.id!""})">
					<td><input type="checkbox" name="ids"
						value="${item.id!""}" /></td>
					<td nowrap="nowrap">&nbsp;${item.id!""}</td>
					<td nowrap="nowrap">&nbsp;${item.pcode!""}</td>
					<td >
						<#if item.giftID?? && item.giftID!="" >
							【赠品】
						</#if>
						<a class="product-name" title="${item.title}" href="toEdit?id=${item.id}">${item.title!""}</a>
					</td>
					<td>&nbsp;${item.price!""}</td>
					<td>&nbsp;${item.nowPrice!""}</td>
					<td>&nbsp;${item.createtime!""}</td>
					<td>&nbsp;
						<#if item.isnew??&&item.isnew=="n">
							<font color='red'></font>
						<#elseif item.isnew??&&item.isnew=="y">
<!-- 							<font color='blue'>新品</font> -->
							<img alt="新品" src="${basepath}/resource/images/action_check.gif">
						</#if>
					 </td>
							
					<td>&nbsp;
						<#if item.status??&&item.status==1>
							<img alt="新增" src="${basepath}/resource/images/action_add.gif">
						<#elseif item.status??&&item.status==2>
							<img alt="已上架" src="${basepath}/resource/images/action_check.gif">
						<#elseif item.status??&&item.status==3>
							<img alt="已下架" src="${basepath}/resource/images/action_delete.gif">
						</#if>
					</td>
					<td ><a href="toEdit?id=${item.id}">编辑</a>
					</td>
				</tr>
            </#list>

			<tr>
				<td colspan="70" style="text-align: center;">
                    <#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>
		
		<div class="alert alert-info" style="text-align: left;font-size: 14px;margin: 2px 0px;">
			图标含义：<BR>
			<img alt="新增" src="${basepath}/resource/images/action_add.gif">：新增商品
			<img alt="已上架" src="${basepath}/resource/images/action_check.gif">：商品已上架
			<img alt="已下架" src="${basepath}/resource/images/action_delete.gif">：商品已下架
		</div>

	</form>
	
<script type="text/javascript">
	$(function() {
		selectDefaultCatalog();
	});
	function selectDefaultCatalog(){
		var _catalogID = $("#catalogID").val();
		if(_catalogID!='' && _catalogID>0){
			$("#catalogSelect").attr("value",_catalogID);
		}
	}
	//双击事件
	function dbclick(val){
	   window.location.href="${basepath}/manage/product/toEdit?id="+val;
	}
</script>
</@page.pageBase>
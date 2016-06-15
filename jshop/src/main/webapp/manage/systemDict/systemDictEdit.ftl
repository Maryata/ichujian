<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="数据字典管理" currentListUrl="/manage/systemDict/systemDictList?init=y">
<form action="${basepath}/manage/systemDict" method="post" theme="simple" id="form" >
<table class="table table-bordered">
	<table class="table table-bordered">
		<tr>
			<td colspan="2" style="background-color: #dff0d8;text-align: center;">
				<strong>字典类别</strong>
			</td>
		</tr>
		<tr style="display: none;">
			<th>id</th>
			<td><input type="hidden" value="${e.id!""}" name="id" id="id"/></td>
		</tr>
		<tr>
			<th style="text-align: right;" width="200px">类别代码  <span style="color: #777;">(请勿随意修改)</font> </th> 
			<td style="text-align: left;">
				<input type="text" style="width:300px" value="${e.ddlKey!""}" data-rule="类别代码:required;ddlKey;length[1~50]" name="ddlKey" id="ddlKey"/>
			</td>
		</tr>
		<tr>
			<th style="text-align: right;">类别名称</th>
			<td style="text-align: left;">
				<input type="text" style="width:300px" value="${e.ddlVal!""}" data-rule="类别名称:required;ddlVal;length[1~50]" name="ddlVal" id="ddlVal" />
			</td>
		</tr>
	</table>
	<table class="table table-bordered">
		<tr>
			<td style="text-align:right;">
                <input type="button" class="btn btn-success" onclick="addDetail()" value="添加详情">
                </input>
			</td>
		</tr>
	</table>
	<table class="table table-bordered" id="tb_detail" style="display: none;">
		<span id="nullVal"></span>
		<tr>
			<th style='text-align: left;width:25%;'>字典代码</th>
			<th style='text-align: left;width:40%;'>字典名称</th>
			<th style='text-align: left;width:15%;'>扩展字段</th>
			<th style='text-align: left;width:15%;'>字典排序</th>
			<th style='text-align: center;width:10%;'>删除</th>
		</tr>
		<#if datas??>
			<#list datas as item>
			<tr>	
				<input type="hidden" name='ids' value='${item.id}' />
				<td style='text-align:left;'>
					<input style='width:100px;' type='text' name='subDdlKey' value='${item.ddlKey}'/>
				</td>
				<td style='text-align:left;'>
					<input  type='text' name='subDdlVal' value='${item.ddlVal}'/>
				</td>
				<td style='text-align:left;'>
					<input style='width:150px;' type='text' name='subExtVal' value='${item.extVal!""}'/>
				</td>
				<td style='text-align:left;'>
					<input style='width:100px;' type='text' name='sorts' value='${item.sort!"1"}'/>
				</td>
				<td style='text-align:center;'>
					<input type="button" class='btn btn-danger' onclick="delCurrTr(this,${item.id})" value="删除">
						<i class='icon-remove-sign icon-white'></i>
					</input>
				</td>
			</tr>
			</#list>
		</#if>
	</table>
	<table class="table table-bordered">
		<tr>
			<td style="text-align: center;">
				<#if !e.id??>
                <input type="submit" method="addSystemDict" class="btn btn-success" onclick="return validForm();" value="新&nbsp;&nbsp;&nbsp;增">
                </input>
                <#else>
                <input type="submit" method="updateSystemDict" class="btn btn-success" onclick="return validForm();" value="保&nbsp;&nbsp;&nbsp;存">
                </input>
                </#if>
			</td>
		</tr>
	</table>
</table>
</form>
<script type="text/javascript">
$(function() {
	if(!jQuery.isEmptyObject($("#id").val())){
		$("#tb_detail").show();
	}
	$("#ddlKey").focus();
});
function addDetail(){
	$("#tb_detail").show();
	var _s = "<tr><input type='hidden' name='ids' value='-1'/>";
	_s += "<td style='text-align:left;'><input style='width:100px;' type='text' name='subDdlKey' /></td>";   
	_s += "<td style='text-align:left;'><input type='text' name='subDdlVal' /></td>";
	_s += "<td style='text-align:left;'><input style='width:80px;' type='text' name='subExtVal' /></td>";
	_s += "<td style='text-align:left;'><input style='width:100px;' type='text' name='sorts' /></td>";
	_s += "<td style='text-align:center;'><button class='btn btn-danger' onclick=\"delCurrTr(this)\"><i class='icon-remove-sign icon-white'></i>删除</button></td></tr>";
	$("#tb_detail").append(_s);
}
function delCurrTr(_tr,id){
	$(_tr).parent('td').parent('tr').remove();
	if($("#tb_detail tr").length==1){
		$("#tb_detail").hide();
	}
	if(id){
		$.post("${basepath}/manage/systemDict/delSystemDictById",{id:id},function(data){
			if($.parseJSON(data)==1){
				alert("删除成功！");
			}else{
				alert("删除失败！");
			}
		});
	}
}
function validForm(){
	var _valid = true;
	var _firstTr = true;
	
	var keys = [],valus=[];
	$("#tb_detail tr").each(function(){
		// 不处理表头
		if(_firstTr){
			_firstTr = false;
			return true;
		}
		var _subDdlKey = $(this).find("[name='subDdlKey']").val();
		var _subDdlVal = $(this).find("[name='subDdlVal']").val();
		if(_subDdlKey==null || _subDdlKey==""){
			alert("字典代码不能为空！")
			$(this).find("[name='subDdlKey']").focus();
			_valid = false;
		}
		else if($.inArray(_subDdlKey, keys)>-1){
			alert("字典代码："+_subDdlKey+" 重复");
			$(this).find("[name='subDdlKey']").focus();
			_valid = false;
			return false;
		}
		else if(_subDdlVal==null || _subDdlVal==""){
			alert("字典名称不能为空！")
			$(this).find("[name='subDdlVal']").focus();
			_valid = false;
		}
		else if($.inArray(_subDdlVal, valus)>-1){
			alert("字典名称："+_subDdlKey+" 重复");
			$(this).find("[name='subDdlVal']").focus();
			_valid = false;
			return false;
		}
		var _sorts = $(this).find("[name='sorts']").val();
		if(_sorts==null || _sorts==""){
			$(this).find("[name='sorts']").val("0");
		}
		
		keys.push(_subDdlKey); valus.push(_subDdlVal);
		
	});
	
	if(keys.length==0){
		alert("请添加详情!");
		return false;
	}
	
	return _valid;
}
</script>
</@page.pageBase>
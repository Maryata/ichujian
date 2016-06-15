<#import "/manage/tpl/pageBase.ftl" as page/>
<@page.pageBase currentMenu="部门管理" currentListUrl="/manage/dept/deptList?init=y">
<style>
#insertOrUpdateMsg{
border: 0px solid #aaa;margin: 0px;position: fixed;top: 0;width: 100%;
background-color: #d1d1d1;display: none;height: 30px;z-index: 9999;font-size: 18px;color: red;
}
</style>
	<form action="${basepath}/manage/dept" theme="simple" id="form" name="form">
		<div><!--class="navbar navbar-inverse" -->
			<div id="insertOrUpdateMsg">
				${insertOrUpdateMsg!""}
			</div>
		</div>
		<input id="deptPid" value="${e.pid!""}" style="display: none;"/>
		<input id="catalogID_currentID" value="${e.id!""}" style="display: none;"/>		
		
		<table class="table table-bordered" style="width: 95%;margin: auto;">
			<tr style="background-color: #dff0d8">
				<td colspan="2" style="background-color: #dff0d8;text-align: center;">
					<span class="badge badge-important">添加部门</span>&nbsp;<strong>
				</td>
			</tr>
			<tr style="display: none;">
				<td>id</td>
				<td><input type="hidden" value="${e.id!""}" name="id" label="id" /></td>
			</tr>
				<tr>
				<td style="text-align: right;">上级部门</td>
				<td style="text-align: left;">
					<select name="pid" id="deptSelect">
						<option></option>
						<#if list??>
	                        <#list list as item>
								<option pid="0" value="${item.id!""}"><font color='red'>${item.name!""}</font></option>
	                            <#if item.children??>
	                                <#list item.children as item>
	                                    <option value="${item.id!""}">&nbsp;&nbsp;&nbsp;&nbsp;${item.name!""}</option>
	                                </#list>
	                            </#if>
	                        </#list>	
                        </#if>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">名称</td>
				<td style="text-align: left;"><input type="text"  value="${e.name!""}" name="name"  id="name" data-rule="名称;required;name;remote[unique, id];" size="40" maxlength="35"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">编号</td>
				<td style="text-align: left;"><input type="text"  value="${e.code!""}" name="code"  id="code" data-rule="编号;required;code;remote[unique, id];" size="40" maxlength="35"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">排序</td>
				<td style="text-align: left;"><input type="text"  value="${e.orderNum!""}" name="orderNum"  data-rule="顺序;required;integer;orderNum;" size="20" maxlength="20" id="orderNum" /></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
                    <#if e.id??>
                        <input type="submit" method="update" class="btn btn-primary" value="保存"/>
					<#else>
                        <button method="insert" class="btn btn-success">
                            <i class="icon-ok icon-white"></i> 新增
                        </button>
					</#if>
				</td>
			</tr>
		</table>
	</form>
<script type="text/javascript">
	$(function() {
		$("#title").focus();
		if($("#insertOrUpdateMsg").html()!='' && $("#insertOrUpdateMsg").html().trim().length>0){
			$("#insertOrUpdateMsg").slideDown(1000).delay(1500).slideUp(1000);
		}
	});

	function onSubmit() {
		if ($.trim($("#name").val()) == "") {
			alert("名称不能为空!");
			$("#title").focus();
			return false;
		}
	}
</script>	
<script type="text/javascript">
$(function(){
	selectDefaultDept();
	
	$("#name").blur(function(){
		getCode();
	});
});
function selectDefaultDept(){
	var _deptPid = $("#deptPid").val();
	console.log("selectDefaultDept._deptPid="+_deptPid);
	$("#deptSelect").val(_deptPid);
}

function catalogChange(obj){
	var _pid = $(obj).find("option:selected").attr("pid");
}

function getCode(){
	var _name = $("#name").val();
	var _url = "autoCode";
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {"name":_name},
	  dataType:"text",
	  //async:false,
	  success: function(data){
		  if(!data){return null;}
		  console.log("data="+data);
		  $("#code").val(data);
	  },
	  error:function(){
		  console.log("加载数据失败，请联系管理员。");
	  }
	});
}
	
</script>
</@page.pageBase>
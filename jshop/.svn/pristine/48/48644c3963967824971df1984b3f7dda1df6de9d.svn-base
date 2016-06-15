<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="通知模板管理">
<link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css" rel="stylesheet" />
<script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
<script>
//-----
var $state;
var $acceptId ;
$(document).ready(function() {
  $acceptId = $("#acceptId");
  $acceptId.select2();
});
</script>
	<form action="${basepath}/manage/notifyTemplate" method="post" theme="simple">
		<#if e.templateCheckError??>
			<div class="alert alert-danger">
				${e.templateCheckError!""}
			</div>
		</#if>
		<table class="table table-bordered">
			<tr style="background-color: #dff0d8">
				<td colspan="2" style="background-color: #dff0d8;text-align: center;">
					<strong>邮件、短信 通知模板管理</strong>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;width: 80px;" nowrap="nowrap" >选择模板</td>
				<td style="text-align: left;">

                    <select id="code" name="code" style="width:350px" class="input-medium"  data-rule="模板:required;code;"onchange="changeTemplate()"  >
                        <option value=""></option>
                        <#list notifyTemplateList as item>
                            <option value="${item.code}" <#if e.code?? && e.code==item.code>selected="selected" </#if>>${item.name}</option>
                        </#list>
                    </select>
			</tr>
			<tr>
				<td style="text-align: right;" nowrap="nowrap">主题(邮件发送主题)</td>
				<td style="text-align: left;">
					<input type="text" name="title"  style="width:350px" data-rule="主题:required;title;"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;" nowrap="nowrap">参数解释</td>
				<td style="text-align: left;">
					<div id="remarkDiv">${e.remark!""}</div>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;" nowrap="nowrap">模板内容</td>
				<td style="text-align: left;">
					<textarea name="template" id="template" style="width:95%;height:250px;visibility:hidden;" data-rule="模板内容:required;template;">${e.template!""}</textarea>
			</tr>
			<tr>
				<td style="text-align: right;" nowrap="nowrap">接收人</td>
				<td style="text-align: left;">
				    <select id="acceptId" name="acceptId" multiple="true" style="width:350px">
                        <option value=""></option>
                        <#list userList as item>
                            <option value="${item.id!""}" <#if e.acceptId?? && e.acceptId==item.id>selected="selected" </#if>>${item.nickname!""} ${item.phone!""}</option>
                        </#list>
                    </select>
				</td>
			</tr>
			<tr>
				<#assign stateMap = sysDic("kg_state") >
				<td style="text-align: right;" nowrap="nowrap">状态</td>
				<td style="text-align: left;">
				<select name="state" id="state" onchange= "change(this.value)">
				    <option value="">请选择</option>
				    <#list stateMap ? keys as key>
             			<option value="${key}" <#if e.state ?? && e.state==key>selected</#if> >
             				${stateMap[key]}
         				</option>
             		</#list>
             	</select>
				</td>
			</tr>
			<tr>
				<td colspan="28" style="text-align: center;">
					<button method="update" class="btn btn-success">
						<i class="icon-ok icon-white"></i> 保存
					</button>
				</td>
			</tr>
		</table>
	</form>

<script>
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="template"]', {
			allowFileManager : true
		});
		K('input[name=getHtml]').click(function(e) {
			alert(editor.html());
		});
		K('input[name=isEmpty]').click(function(e) {
			alert(editor.isEmpty());
		});
		K('input[name=getText]').click(function(e) {
			alert(editor.text());
		});
		K('input[name=selectedHtml]').click(function(e) {
			alert(editor.selectedHtml());
		});
		K('input[name=setHtml]').click(function(e) {
			editor.html('<h3>Hello KindEditor</h3>');
		});
		K('input[name=setText]').click(function(e) {
			editor.text('<h3>Hello KindEditor</h3>');
		});
		K('input[name=insertHtml]').click(function(e) {
			editor.insertHtml('<strong>插入HTML</strong>');
		});
		K('input[name=appendHtml]').click(function(e) {
			editor.appendHtml('<strong>添加HTML</strong>');
		});
		K('input[name=clear]').click(function(e) {
			editor.html('');
		});
	});
	
	//加载选择的模板
	function changeTemplate(){
		var _code = $("#code").val();
		
		$("#template").html('');
		$("#remarkDiv").html('');
		$("input[name=title]").val('');
		$("input[name=acceptName]").val('');
		if(_code==''){
			return;
		}
		var _url = "${basepath}/manage/notifyTemplate/selectTemplateByCode?code="+_code;
		console.log("_url="+_url);
		$.ajax({
		  type: 'POST',
		  url: _url,
		  data: {},
		  success: function(data){
			  console.log("changeTemplate.data="+data.template);
			  $("input[name=title]").val(data.title);
			  editor.html(data.template);
			  $("#remarkDiv").html(data.remark);
			  $("input[name=acceptName]").val(data.acceptName);
			  
			  var acceptId = data.acceptId || '';
			  var acceptIdArr = acceptId.split(",");
			  $acceptId.val(acceptIdArr).trigger("change");
			  
			   $("select[name=state]").val(data.state);
			  
		  },
		  dataType: "json",
		  error:function(er){
			  console.log("changeTemplate.er="+er);
		  }
		});
	}
	
	//加载选择的模板
	function updateTemplate(){
		var _code = $("#code").val();
		alert($("#acceptId").val());
		if(editor.isEmpty() || _code==''){
			return;
		}
		var formData=$("form").serialize();
		var _url = "${basepath}/manage/notifyTemplate/updateTemplate";
		console.log("_url="+_url);
		$.ajax({
		  type: 'POST',
		  url: _url,
		  data: formData,
		  success: function(data){
		  alert();
			  console.log("updateTemplate.data="+data);
			  if(data=="0"){
				  alert("保存成功！");
			  }else{
				  alert("保存失败！");  
			  }
		  },
		  dataType: "text",
		  error:function(er){
			  console.log("updateTemplate.er="+er);
		  }
		});
	}
</script>
</@page.pageBase>
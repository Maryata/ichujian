<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="用户管理" currentListUrl="/manage/user/selectList?init=y">
<style>
	.td_right{text-align: right;}
</style>
<script type="text/javascript">
$(function() {
	selectDefaultDept();
	$("#username").focus();
});

function selectDefaultDept(){
	var _deptId = $("#deptId").val();
	console.log("selectDefaultDept._deptId="+_deptId);
	$("#deptSelect").val(_deptId);
}

function  CheckUserCode(){

}
</script>
</head>

<body>
<#if e.id??>
    <#assign formAction="111">
<#assign insertAction=false />
<#else >
<#assign formAction="insert">
    <#assign insertAction=true />
</#if>

<#--<%-- formAction=<s:property value="#formAction"/><br> --%>-->
<#--<%-- formAction2=<s:property value="#request.formAction"/><br> --%>-->
<form action="${basepath}/manage/user" id="form" method="post" onsubmit="return checkMess()">
	<#--<s:form action="user!" namespace="/" theme="simple" id="form">-->
		<table class="table table-bordered">
			<tr>
				<td colspan="2" style="background-color: #dff0d8;text-align: center;">
					<strong>帐号编辑<#if e.id??><a class="btn btn-primary btn-select" onclick="updatePass(${e.id!""})">修改密码</a></#if>
					</strong>
				</td>
			</tr>
			<tr style="display:none;">
				<th>id</th>
				<td>
				<input type="hidden" name="id" id="_id" value="${e.id!""}">
				<#if e.id??>
				<#else>
				<input autocomplete="off" type="password" name="password" data-rule="密码:password;length[6~20];" id="password" value="123456"/>
				<input autocomplete="off" type="password" name="newpassword2" data-rule="确认密码:match(password)" id="newpassword2" value="123456"/>
				</#if>
				</td>
			</tr>
			<tr>
				<th class="td_right">帐&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</th>
				<td style="text-align: left;">
                        <input type="text" autocomplete="off" value="${e.username!""}" name="username" id="username"  data-rule="帐号:required;username;length[4~20];remote[unique?id=${e.id!""}]">
				</td>
			</tr>
			<tr>
				<th class="td_right">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</th>
				<td style="text-align: left;">
				<input type="text" name="nickname" autocomplete="off" value="${e.nickname!""}" id="nickname"  data-rule="姓名:required;nickname;length[2~20]"/>
					</td>
			</tr>
			<tr>
				<th class="td_right">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</th>
				<td style="text-align: left;">
				<input type="text" name="usercode" autocomplete="off" value="${e.usercode!""}" id="usercode"  data-rule="工号:required;usercode;length[2~20];remote[unique?id=${e.id!""}]"/>
				</td>
			</tr>
			<tr>
				<th class="td_right">阿智折扣率</th>
				<td style="text-align: left;">
				<input type="text" name="discount" autocomplete="off" value="${e.discount!""}" data-rule="阿智折扣率:required; integer; range[0~100]" id="discount"/>%
				</td>
			</tr>
			<tr>
				<th class="td_right">非阿智折扣率</th>
				<td style="text-align: left;">
				<input type="text" name="otherDiscount" autocomplete="off" value="${e.otherDiscount!""}" data-rule="非阿智折扣率:required; integer; range[0~100]" id="otherDiscount"/>%
				</td>
			</tr>
			<tr>
				<th class="td_right">选择角色</th>
				<td style="text-align: left;">
                    <select name="rid">
                        <#list roleList as item>
                            <#--${item.id}-->
                            <option value="${item.id}" <#if e.rid?? && item.id?string == e.rid?string>selected="selected"</#if>> ${item.role_name}</option>
                        </#list>
                    </select>
				</td>
			</tr>
			<tr>
				<th class="td_right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱</th>
				<td style="text-align: left;">
				 <input type="text" name="email" autocomplete="off" value="${e.email!""}" id="email"  data-rule="邮箱:required;email;length[2~80];"/>
				</td>
			</tr>
			<tr>
				<th class="td_right">手&nbsp;&nbsp;机&nbsp;&nbsp;号</th>
				<td style="text-align: left;">
				 <input type="text" name="phone" autocomplete="off" value="${e.phone!""}" id="phone"  data-rule="手机号:required;phone;length[2~20];"/>
				</td>
			</tr>
			<tr>
				<input id="deptId" value="${deptId!""}" style="display: none;"/>
				<th class="td_right">选择部门</th>
				<td style="text-align: left;">
					<select name="did" id="deptSelect" data-rule="部门:required;did;">
						<option></option>
						<#if deptList??>
	                        <#list deptList as item>
								<option pid="0" value="${item.id!""}"><font color='red'>${item.name!""}</font></option>
	                            <#if item.children??>
	                                <#list item.children as item>
	                                    <option value="${item.id!""}" >&nbsp;&nbsp;&nbsp;&nbsp;${item.name!""}</option>
	                                </#list>
	                            </#if>
	                        </#list>	
                        </#if>
					</select>
				</td>
			</tr>
			<tr style="display:none;">
				<input id="brandType" value="${brandType!""}" style="display: none;"/>
				<th class="td_right">销售品牌</th>
				<#assign brandTypeMap = sysDic("user_brand") >
				<td style="text-align: left;">
					<select name="brandType" id="brandType" data-rule="销售品牌:required;brandType;">
						<#list brandTypeMap?keys as key>
                            <option value="${key}" <#if e.brandType?? && e.brandType==key>selected="selected" </#if>>${brandTypeMap[key]}</option>
                        </#list>
					</select>
				</td>
			</tr>
            <#if !e.username?exists || e.username?exists && e.username != "admin">
				<tr>
					<th class="td_right">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态</th>
					<td style="text-align: left;" >
                        <select class="input-small" id="status" name="status" >
                            <option value="y" <#if e.status?? && e.status == "y">selected="selected" </#if>>启用</option>
                            <option value="n"<#if e.status?? && e.status == "n">selected="selected" </#if>>禁用</option>
                        </select>
					</td>
				</tr>
            </#if>
			<tr >
				<td colspan="2" style="text-align: center;">
					<#if insertAction>
						<button method="insert" class="btn btn-success">
							<i class="icon-ok icon-white"></i> 新增
						</button>
                        <#else >
						<button method="update" class="btn btn-success">
							<i class="icon-ok icon-white"></i> 保存
						</button>
                    </#if>
				</td>
			</tr>
		</table>
</form>
<div id="updatepass" style="display:none;">
<form id="form_update" method="post">
  <table class="table table-bordered">
    <input type="hidden" name="id" id="id_id" />
    <tr>
      <th class="td_right">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码</th>
      <td style="text-align: left;"><input autocomplete="off" type="password" name="password" data-rule="密码:password;length[6~20];" id="password" />
            <#if !insertAction> <br>(不输入表示不修改密码)</#if>
       </td>
     </tr>
     <tr>
	    <th class="td_right">确认密码</th>
	    <td style="text-align: left;"><input autocomplete="off" type="password" name="newpassword2" data-rule="确认密码:match(password)" id="newpassword2" />
         </td>
     </tr>
     <tr >
		<td colspan="2" style="text-align: center;">
				<a class="btn btn-success" onclick="checkUpdate()"> 确认修改</a>
		</td>
	</tr>
  </table>
 </form> 
</div>
<script type="text/javascript" src="${basepath}/resource/layer-v1.9.2/layer/layer.js"></script>
<script>
function checkMess(){
 if($("#_id").val()==""){
  alert("默认密码：123456，请尽快修改");
  }
}
function updatePass(id){
  $("#id_id").val(id);
  layer.open({
    	title: '修改密码',
	    type: 1,
	    skin: 'layui-layer-rim', //加上边框
	    area: ['550px',"340px"],
	    content: $('#updatepass')
	});
}
function checkUpdate(){
  $.ajax({
 	    url:'${basepath}/manage/user/updatePass',
		data: $('#form_update').serialize(),
		type:"POST",
		dataType:'json',
		success:function(data){
		if(data.status=="1"){
		 layer.closeAll();
		 $("#newpassword2").val("");
		 $("#password").val("");
		}
		layer.msg(data.msg);
		    
		}
 	});
}
</script>
</@page.pageBase>
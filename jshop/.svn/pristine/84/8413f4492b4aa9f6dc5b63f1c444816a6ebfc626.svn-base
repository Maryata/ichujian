<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="会员管理" currentListUrl="/manage/account/selectList?init=y">
<style>
.check-more{ width:959px;  line-height: 1.5em;margin-top: 7px;  margin-bottom: 10px; margin-left: -1px;}
.check-more label{ float:left;margin-right:10px;margin-left:-1px; color:#666; cursor:pointer; font-size:12px; width:165px;}
.file-txt{ height:22px; border:1px solid #cdcdcd; width:180px; padding:0 5px;}
.file-btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
.file-file{ position:absolute; top:-4px; right:74px; height:24px; filter:alpha(opacity:0);opacity: 0;width:70px; }
.file-box {position: relative;width: 340px}
</style>
<script type="text/javascript">
$(function(){
	var setting = {
		check: {
			enable: true,
			dblClickExpand: false,
			chkStyle :  "radio",
			radioType : 'all' // all：所有选项作为一个分组，level：每一级作为一个分组
		},view: {
			fontCss: getFontCss
		},callback: {
			onClick: onClick
		}
	};
	
	function checkBox(obj) {
	alert("222");
}
	
	function onClick(e,treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
		var nodes = zTree.getCheckedNodes();
		zTree.expandNode(treeNode);
	}
	
	function getFontCss(treeId, treeNode) {
		return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
	}
	
	loadMenusTree($("#id").val());
	
	//加载菜单树
	function loadMenusTree(id){
		$.ajax({
			url:"${basepath}/manage/account/loadDeptTree",
			type:"post",
			data:{id:id},
			dataType:"text",
			success:function(data, textStatus){
				var zNodes = eval('('+data+')');
				$.fn.zTree.init($("#treeDemo2"), setting, zNodes);
				$("#account").focus();
			},
			error:function(){
				alert("error");
			}
		});
	}
	
	//全部展开	
	$("#expandOrCollapseAllBtn").bind("click", {type:"expandOrCollapse"}, expandNode);
	$("#checkAllTrueOrFalse").bind("click", {type:"checkAllTrueOrFalse"}, expandNode);
});

function checkSave(btn,accType){
	var _submitFlag = true;
	if(accType=="0"){
	  $("#accType").val("1");
	}
	
	//$("#test").is(":hidden");
	//判断其他物流是否隐藏
	var display =$('#express_other_input').is(":hidden");
	if(display){
	   
	}else{
	   if($("#expressOther").val()==""){
	    alert("请输入其他物流公司");
	    return false;
	   }
	  
	}
	//判断物流公司是否隐藏
	var express =$('#express_express_input').is(":hidden");
	if(express){
	   
	}else{
	   if($("#expressCode").val()=="0"){
	    alert("请输入物流公司");
	    return false;
	   }
	  
	}
	
	//产品线
    var isD = 0, isNotD = 0;
	 $("input[name=pline]:checked").each(function(){
        if($(this).val().substring(0,1)=="D"){
        	isD++;
        }else{
        	isNotD++;
        }
    });
   var isSys=$("#isSysAccount").val();
   if(isSys=="0"){
	   if(isD>0 && isNotD >0){
	      alert("勾选阿智产品线时非阿智产品线不能勾选");
	      return false;
	   }
	 }
   /*var isDis = parseInt($("#discount").attr("data-user-discount"));//阿智
   var isNotDis = parseInt($("#discount").attr("data-discount"));//非阿智
   var Dis = parseInt($("#discount").val());//手动输入
   if($("#discount").val()==""){//折扣率为空
     if(isD>0){//设置阿智
       $("#discount").val(isDis);
     }else if(isNotD >0){//设置非阿智
       $("#discount").val(isNotDis);
     }
   }else{
     if(isD>0){//设置阿智
    	if(isDis < Dis){
    	  	alert("设置阿智最大折扣率权限为："+isDis+"% ,请修改折扣率");
    	  	return false;
    	}
     }else if(isNotD >0){//设置非阿智
    	 if(isNotDis < Dis){
    		 alert("设置非阿智最大折扣率权限为："+isNotDis+"%,请修改折扣率");
    		return false; 
     	}
     }
   }*/
	// 销售人员
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo2");
	var nodes = treeObj.getCheckedNodes(true);
	if(nodes && nodes.length>0 && nodes[0].id ){
		$("#saleId").val(nodes[0].id);
		$("#saleDeptId").val(nodes[0].pid);
		$("#saleCode").val(nodes[0].saleCode);
	}else{
		alert("请选择销售人员！");
		return false;
	}
	
	if($("#isServiceFee").val()=="1"){//根据服务费 填写发票抬头
	    if($("#bankAccountName").val()==""){
	       alert("请填写发票抬头");
	       $("#bankAccountName").focus();
	       return false;
	    }
	}
	$("#accPrice").val("");
	var _false = setAccPrices();
	if(!_false){
	  return false;
	}
	
	if(_submitFlag){
		//$("#form1").submit();
		return true
		//btn.attr('s',"true");
	}
	
	return false;
}

var expandAllFlg = true;
var checkAllTrueOrFalseFlg = true;
function expandNode(e) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo2"),
	type = e.data.type,
	nodes = zTree.getSelectedNodes();

	if (type == "expandAll") {
		zTree.expandAll(true);
	} else if (type == "collapseAll") {
		zTree.expandAll(false);
	} else if (type == "expandOrCollapse") {
		zTree.expandAll(expandAllFlg);
		expandAllFlg = !expandAllFlg;
	} else if (type == "checkAllTrueOrFalse") {
		zTree.checkAllNodes(checkAllTrueOrFalseFlg);
		checkAllTrueOrFalseFlg = !checkAllTrueOrFalseFlg;
	} else {
		if (type.indexOf("All")<0 && nodes.length == 0) {
			alert("请先选择一个父节点");
		}
		var callbackFlag = $("#callbackTrigger").attr("checked");
		for (var i=0, l=nodes.length; i<l; i++) {
			zTree.setting.view.fontCss = {};
			if (type == "expand") {
				zTree.expandNode(nodes[i], true, null, null, callbackFlag);
			} else if (type == "collapse") {
				zTree.expandNode(nodes[i], false, null, null, callbackFlag);
			} else if (type == "toggle") {
				zTree.expandNode(nodes[i], null, null, null, callbackFlag);
			} else if (type == "expandSon") {
				zTree.expandNode(nodes[i], true, true, null, callbackFlag);
			} else if (type == "collapseSon") {
				zTree.expandNode(nodes[i], false, true, null, callbackFlag);
			}
		}
	}
}
$(function(){
	$("#activeCode").val($("#ac").val());
	$("#discntSolutnId").val($("#ds").val());
});
function showDeptNSaler(){
	$("#deptNSaler").slideToggle();
}
</script>
<form action="${basepath}/manage/account" method="post" name="form1" id="form1">
	<table class="table table-bordered" >
		<tr>
			<td colspan="4" style="background-color: #dff0d8;text-align: center;">
				<strong>会员编辑</strong>
			</td>
		</tr>
		<tr>
			<div id="buttons" style="text-align: center;border-bottom: 1px solid #ccc;padding: 5px;">
			
	  		<!--审核状态-->
	      	<#assign currStatus =e.status >
	      	<div class="flowstep" style="width:520px;">
	      	<#if e.accType=="-1">
	      	<input type="submit" method="updateAccount" class="btn btn-primary" 
		        value="确认审核" onclick="return checkSave(this,0);" />
	      	<#else>
	      	<input type="submit" method="updateAccount" class="btn btn-primary" 
		        value="保存资料" onclick="return checkSave(this,1);" />
	      	</#if>
		        <a class="btn btn-success" onclick="syncAccount2Sys(${e.id!""});">一键同步客户资料<#if e.syncState=='1'><span style="color:red;" title="同步成功！">√</span></#if><#if e.syncState=='0'><span style="color:red;" title="请同步！">×</span></#if></a>
			<#if checkPrivilege("/manage/account/valetOrders")>
			    <a class="btn btn-info" onclick="isValetOrder(${e.id!""})" id="valetOrder"><#if e.isSysAccount?? && e.isSysAccount=="0">转为代客下单<#else>转为非代客下单</#if></a>
			    <input type="hidden" name="isSysAccount" id="isSysAccount" value="${e.isSysAccount!""}"/>
			</#if>
			</div>
			
		</tr>
		<tr style="display: none;">
			<th>id</th>
			<td>
			    <input type="text" name="accType" id="accType" value="${e.accType!""}" />
			    <input type="hidden" name="auditStatus" id="auditStatus" value="${e.auditStatus!""}" />
				<input type="hidden" name="id" id="id" value="${e.id!""}" />
				<input type="hidden" name="saleId" id="saleId" value="${e.saleId!""}" />
				<input type="hidden" name="saleCode" id="saleCode" value="${e.saleCode!""}" />
				<input type="hidden" name="saleDeptId" id="saleDeptId" value="${e.saleDeptId!""}" />
				<input type="hidden" name="status" id="status" value="${e.status!""}" />
			</td>
		</tr>
		<tr>
		<td colspan="4" style="color:#999;"><span style="color:red;font-weight:bold;">基础信息(*为必填项)</span></td>
		</tr>
			<th style="text-align: center;width:150px;height:55px;">会员名称</th>
			<td style="text-align: left;" >
             	${e.account!""}
			</td>
			<th style="text-align: center;width:150px;height:55px;">账号类型</th>
			<td style="text-align: left;" id="valetacc">
			<#if e.isSysAccount?? && e.isSysAccount=="1">
			 代客下单
			<#else>
			    <#if e.accType=="0" || e.accType=="-1">
             	   个人账号<#if e.registerType?? && e.registerType=="1">
             	   （销售注册）<#else>（客户注册）</#if>
             	</#if>
				<#if e.accType=="1">
				   企业账号<#if e.registerType?? && e.registerType=="1">
             	   （销售注册）<#else>（客户注册）</#if>
             	</#if>
			</#if>
			</td>
		<tr>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:55px;">会员代码</th>
			<td style="text-align: left;">
             	${e.accountCode!""}
			</td>
			<th style="text-align: center;width:150px;height:10px;">是否配件用户</th>
			<td style="text-align: left;height:10px;">
				<input type="hidden" name="activeCode_old" id="ac" value="${e.activeCode!"0"}">
				<select name="activeCode" id="activeCode" value="${e.activeCode!"0"}" onchange="selectPline_()">
					<option value="0">否</option>
					<option value="1">是</option>
         		</select>	
			</td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">联系人姓名<span style="color:red;">*</span></th>
			<td>
             	<input type="text" value="${e.trueName!""}" style="width:60%;"
             	data-rule="联系人姓名:required;length[2~15];" maxlength="15" name="trueName" id="trueName" />
			</td>
			<th style="text-align: center;width:150px;height:10px;">联系人电话<span style="color:red;">*</span></th>
			<td>
             	<input type="text" value="${e.tel!""}" style="width:60%;" 
             	data-rule="联系人电话:required;length[8~15];" maxlength="15" name="tel" id="tel" />
			</td>
		</tr>
		<#if e.accType=="-1" || e.accType=="1">
		<tr>
		  <th style="text-align: center;width:150px;height:10px;">联系人邮箱<span style="color:red;">*</span></th>
			<td>
             	<input type="text" value="${e.email!""}" style="width:60%;" 
             	data-rule="联系人邮箱:required;email;length[1~100];remote[${basepath}/account/unique.html?id=${e.id!""}]"
             	maxlength="100" name="email" id="email" />
			</td>
			<th style="text-align: center;width:150px;height:10px;">联系人职位<span style="color:red;">*</span></th>
			<td>
             	<input type="text" name="jobTitle" value="${e.jobTitle!""}" style="width:60%;" data-rule="联系人职位:required;length[2~15];"
			</td>
		</tr>
		<#else>
		<tr>
		  <th style="text-align: center;width:150px;height:10px;">联系人邮箱<span style="color:red;">*</span></th>
			<td colspan="3">
             	<input type="text" value="${e.email!""}" style="width:60%;" 
             	data-rule="联系人邮箱:required;email;length[1~100];remote[${basepath}/account/unique.html?id=${e.id!""}]"
             	maxlength="100" name="email" id="email" />
			</td>
		</tr>
		</#if>
		<#if e.accType=="-1" || e.accType=="1">
		<tr>
		    <th style="text-align: center;width:150px;height:10px;">公司法人</th>
			<td>
             	<input type="text" value="${e.corporate!""}" style="width:60%;" 
             	data-rule="公司法人:length[2~45];" maxlength="100"
             	name="corporate" id="corporate" />
			</td>
		  <th style="text-align: center;width:150px;height:10px;">商户/企业名称<span style="color:red;">*</span></th>
		  <td >
         	<input type="text" value="${e.companyName!""}" style="width:60%;" maxlength="200" data-rule="商户/企业名称:required;length[1~200];"	name="companyName" id="companyName" />
		  </td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">总经理姓名</th>
			<td>
             	<input type="text" value="${e.bossName!""}" style="width:60%;" 
             	data-rule="总经理姓名:length[2~45];" maxlength="100"
             	name="bossName" id="bossName" />
			</td>
			<th style="text-align: center;width:150px;height:10px;">总经理电话</th>
			<td>
             	<input type="text" value="${e.bossPhone!""}" style="width:60%;" 
             	data-rule="总经理电话:length[6~15];" maxlength="100"
             	name="bossPhone" id="bossPhone" />
			</td>
		</tr>
		<tr>
		<th style="text-align: center;width:150px;height:10px;">公司地址<span style="color:red;">*</span></th>
			<td colspan="3">
             	<input type="text" value="${e.address!""}" style="width:60%;" 
             	data-rule="公司地址:required;length[3~45];" maxlength="100" name="address" id="address" />
			</td>
		</tr>
		<td colspan="4" style="color:#999;"><span style="color:red;font-weight:bold;">销售维护(*为必填项)</span></td>
		<#assign natureMap = sysDic("account_nature") >
        <#assign annualSalesMap = sysDic("account_annualSales") >
        <#assign ltdMap = sysDic("account_ltd") >
        <#assign productsMap = sysDic("account_products") >
		<#assign purchaseTypeMap = sysDic("account_purchaseType") >
		<#assign paymentTypeMap=sysDic("account_paymentType")>
		<#assign expressTypeMap = sysDic("express_type") >
		<#assign express =sysDic("order_express")>
		<#assign serviceFee =sysDic("isService_Fee")>
		<#assign isOtherFee =sysDic("isOther_Fee")>
		<#assign plineMap = sysDic("account_purchaseType") >
		<#assign plineExtMap = sysDic("account_purchaseType_ext") >
		<#if e.pline?? && e.pline!='' >
   	 		<#assign plineChecks = e.pline?split(",")>
        <#else>
        	<#assign plineChecks =[]>
        </#if>
		<tr >
		 <th rowspan="2" style="text-align: center;width:150px;height:10px;">产品线<span style="color:red;">*</span></th>
			<td style="text-align: left;height:10px;" colspan="3">
			   <span class="check-more">
			   <span style="color:red" id="_span_is">阿智：</span><br/>
				<#list plineMap?keys as key>
				   <#if key[0..0]=="D">
					<label id="plines" class="plineLible pline_<#if key[0..0]=="A" || key[0..0]=="Z">1<#else>0</#if>">
						<input type="checkbox" name="pline" 
						<#list plineChecks as c > <#if c=key> checked </#if></#list>
							value="${key}" vName="${plineMap[key]}" id="pline_${key}"  data-price="${plineExtMap[key]}" <#if key[0..0]=="A" || key[0..0]=="Z" ||  e.isSysAccount=="1" >data-p="${plineMap[key]}"<#else>onclick="getPrice(this)"</#if>/>${plineMap[key]}
					</label>
					</#if>
				</#list>
				<span id="_hr"><br/><br/><hr></span>
				<span style="color:red" id="_span_not">非阿智：</span><br/>
				<#list plineMap?keys as key>
				   <#if key[0..0]!="D">
					<label id="plines" class="plineLible pline_<#if key[0..0]=="A" || key[0..0]=="Z">1<#else>0</#if>">
						<input type="checkbox" name="pline" 
						<#list plineChecks as c > <#if c=key> checked </#if></#list>
							value="${key}" vName="${plineMap[key]}" id="pline_${key}"  data-price="${plineExtMap[key]}" <#if key[0..0]=="A" || key[0..0]=="Z" || e.isSysAccount=="1">data-p="${plineMap[key]}"<#else>onclick="getPrice(this)"</#if>/>${plineMap[key]}
					</label>
					</#if>
				</#list>
				<span class="msg-box n-left" style="margin-top:80px;" for="pline"></span>
				<span style="width:959px;float: left;">
					<span style="color:red">(勾选阿智产品线时非阿智产品线不能勾选)</span>
				</span>
			   </span>
			</td>
		</tr>
		<tr <#--style="display:none;-->">
			<td colspan="3">
				<input type="hidden" value='${e.accPrice!""}'  name="accPrice" id="accPrice" />
			    <table class="table table-bordered table-hover" width="80%">
				    <thead>
					    <tr style="background-color: #dff0d8">
					      <td>产品线 </td>
					      <td>android会员价</td>
					      <td>android原价</td>
					      <td>iphone6会员价</td>
					      <td>iphone6原价</td>
					      <td>iphone6plus会员价</td>
					      <td>iphone6plus原价</td>
					    </tr>
				    </thead>
				     <tbody class="tb_detail"> 
			            
		             </tbody>
			    </table>
			</td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">运输类型<span style="color:red;">*</span></th>
			<td style="text-align: left;height:10px;" colspan="1">
				<select name="expressType" id="expressType" onchange="getType()" data-rule="运输类型:required;">
             		<#list expressTypeMap?keys as key>
             			<option value="${key}" <#if e.expressType?? && e.expressType==key>selected</#if> >
             				${expressTypeMap[key]}
         				</option>
             		</#list>	
             	</select>
			</td>
			<th style="text-align: center;width:150px;height:10px;">结算方式<span style="color:red;">*</span></th>
			<td style="text-align: left;height:10px;">
				<select name="paymentType" id="paymentType">
             		<#list paymentTypeMap?keys as key>
             			<option value="${key}" <#if e.paymentType?? && e.paymentType==key>selected</#if> >
             				${paymentTypeMap[key]}
         				</option>
             		</#list>
         		</select>	
			</td>
		</tr>
		<tr id="_express" style="display:none">
			<th style="text-align: center;width:150px;height:10px;" id="express_express_m" style="display:none">物流公司<span style="color:red;">*</span></th>
			<td style="text-align: left;height:10px;" id="express_express_input" style="display:none" colspan="2">
				<select name="expressCode" id="expressCode" width=""   onchange="getOtherExpress()">
				<option value="0">请选择物流公司</option>
             		<#list express?keys as key>
             			<option value="${key}" <#if e.expressCode?? && e.expressCode==key>selected</#if> >
             				${express[key]}
         				</option>
             		</#list>	
             	</select>
			</td>
		    <th style="text-align: center;width:150px;height:10px;"  id="express_other_m" style="display:none">其他物流<span style="color:red;">*</span></th>
			<td  id="express_other_input" style="display:none" colspan="1">
             	<input type="text" value="${e.expressOther!""}"
             	data-rule="其他物流:length[2~15];" maxlength="15" name="expressOther" id="expressOther" />
			</td>
		</tr>
		
		<tr>
			<th style="text-align: center;width:150px;height:10px;">服务费<span style="color:red;">*</span></th>
			<td style="text-align: left;height:10px;">
				<select name="isServiceFee" id="isServiceFee" onchange="changeFPTT()" data-rule="服务费:required;">
				<option value="">请选择服务费</option>
             		<#list serviceFee?keys as key>
             			<option value="${key}" <#if e.isServiceFee?? && e.isServiceFee==key>selected</#if> >
             				${serviceFee[key]}
         				</option>
             		</#list>	
             	</select>
			</td>
			<th style="text-align: center;width:150px;height:10px;">其他费用<span style="color:red;">*</span></th>
			<td style="text-align: left;height:10px;">
				<select name="isOtherFee" id="isOtherFee" data-rule="其他费用:required;">
				<option value="">请选择其他费用</option>
             		<#list isOtherFee?keys as key>
             			<option value="${key}" <#if e.isOtherFee?? && e.isOtherFee==key>selected</#if> >
             				${isOtherFee[key]}
         				</option>
             		</#list>	
             	</select>
			</td>
		</tr>
		<tr>
		  <th style="text-align: center;width:150px;height:10px;display:none;">会员折扣率<span style="color:red;">*</span></th>
			<td style="text-align: left;height:10px;display:none;">
				<input type="text" data-discount="${currentUser().otherDiscount!""}" data-user-discount="${currentUser().discount!""}"  value="${e.discount!""}" style="width:220px;" data-rule="会员折扣: integer; range[0~100]"  maxlength="15"  name="discount" id="discount" />%
			</td>  
		</tr>
		<tr>
			<th style="text-align: center;">
				<input type="button" class="btn btn-success"  id="showSaler"
        			onclick="showDeptNSaler()" value="销售人员" /> 
        		</input>
			</th>
			
			<td style="text-align: left;" colspan="3"><span>${e.name!""}</span>
				<div id="deptNSaler" style="display:none"  colspan="2">
					<div id="optionDiv">
						[<a id="expandOrCollapseAllBtn" href="#" title="展开/折叠全部资源" onclick="return false;">展开/折叠</a>]
					</div>
					<ul id="treeDemo2" class="ztree"></ul>
				</div>
			</td>
		</tr>
		<tr>
		<td colspan="4" style="color:#999;"><span style="color:red;font-weight:bold;">经营信息(*为必填项)</span></td>
		</tr>
		<tr>
		<th style="text-align: center;width:150px;height:10px;">公司性质<span style="color:red;">*</span></th>
			<td style="text-align: left;width:300px;height:10px;" colspan="3">
             	<select name="nature" id="nature"  data-rule="公司性质:required;">
             		<option value="">请选择</option>
             		<#list natureMap?keys as key>
             			<option value="${key}" <#if e.nature?? && e.nature==key>selected</#if> >
             				${natureMap[key]}
         				</option>
             		</#list>	
             	</select>
			</td>
		</tr>
		<#if e.ltd?? && e.ltd!='' >
   	 		<#assign ltdChecks = e.ltd?split(",")>
        <#else>
        	<#assign ltdChecks =[]>
        </#if>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">经营性质<span style="color:red;">*</span></th>
			<td style="text-align: left;height:10px;" colspan="3">
				<span class="check-more">
				<#list ltdMap?keys as key>
					<label id="ltds">
						<input type="checkbox" name="ltd" 
							<#list ltdChecks as c > <#if c=key> checked </#if></#list>
							value="${key}" vName="${ltdMap[key]}" id="ltd_${key}" />${ltdMap[key]}
					</label>
				</#list>
				<span class="msg-box n-left" style="margin-top:30px;" for="ltd"></span>
				<span style="width:959px;float: left;">
					<span>其他性质</span>
					<input type="text" name="ltdName" id="ltdName" maxLength="50" class="file-txt"/>
				</span>
				</span>
			</td>
		</tr>
		<#if e.products?? && e.products!='' >
	        <#assign productsChecks = e.products?split(",")>
        <#else>
	        <#assign productsChecks =[]>
        </#if>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">经销产品<span style="color:red;">*</span></th>
			<td style="text-align: left;height:10px;" colspan="3">
				<span class="check-more">
				<#list productsMap?keys as key>
					<label id="productss">
						<input type="checkbox" name="products" value="${key}" 
							<#list productsChecks as c > <#if c=key> checked </#if></#list>
							vName="${productsMap[key]}" id="products_${key}" />${productsMap[key]}
					</label>
				</#list>
				<span class="msg-box n-left" style="margin-top:50px;" for="products"></span>
				</span>
			</td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">订制品牌名</th>
			<td>
             	<input type="text" value="${e.accountBrand!""}" style="width:60%;" 
             	 maxlength="100" name="accountBrand" id="accountBrand" />
			</td>
			<th style="text-align: center;width:150px;height:10px;">年销售额</th>
			<td style="text-align: left;width:300px;height:10px;">
             	<select name="annualSales" id="annualSales" >
             		<option value="">请选择</option>
             		<#list annualSalesMap?keys as key>
             			<option value="${key}" <#if e.annualSales?? && e.annualSales==key>selected</#if> >
             				${annualSalesMap[key]}
         				</option>
             		</#list>	
             	</select>
			</td>
		</tr>
		<tr>
		<td colspan="4" style="color:#999;"><span style="color:red;font-weight:bold;">公司资质</span></td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">公司营业执照扫描件</th>
			<td style="text-align: left;width:300px;height:10px;">
				<span class="file-box">
					<#if e.busLicenseScan?? && e.busLicenseScan!='' > 
	            		<a href="${e.busLicenseScan!""}" target="_blank" id="busLicenseScanImgA"><img id="busLicenseScanImg" style="width: 80px;height: 80px;" title="公司营业执照扫描件" alt="公司营业执照扫描件" src="${e.busLicenseScan}" ></a>
		          	<#else>
		            	<a target="_blank" id="busLicenseScanImgA" style="display: none"><img id="busLicenseScanImg" style="width: 80px;height: 80px;" title="公司营业执照扫描件" alt="公司营业执照扫描件" ></a>
		          	</#if>
		          	<input type="hidden" name="busLicenseScan" id="busLicenseScan" value="${e.busLicenseScan!""}"/>
	         	 	<input type="button" style="width:70px;" class="file-btn" id="busLicenseScanSee" value="浏览..." />
	         	 	<input type="file" style="height:24px;" class="file-file" id="busLicenseScanField" size="28" onchange="return checkFile(this,'busLicenseScanSee')" />
		          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'busLicenseScanField','busLicenseScan');" />
	          	</span>
			</td>
			<th style="text-align: center;width:150px;height:10px;">公司税务登记证扫描件</th>
			<td style="text-align: left;width:300px;height:10px;">
				<span class="file-box">
					<#if e.taxCertificateScan?? && e.taxCertificateScan!='' > 
			            <a href="${e.taxCertificateScan!""}" target="_blank" id="taxCertificateScanImgA"><img id="taxCertificateScanImg" style="width: 80px;height: 80px;" title="公司税务登记证扫描件" alt="公司税务登记证扫描件" src="${e.taxCertificateScan}" ></a>
	          		<#else>
			            <a target="_blank" id="taxCertificateScanImgA" style="display: none"><img id="taxCertificateScanImg" style="width: 80px;height: 80px;" title="公司营业执照扫描件" alt="公司营业执照扫描件" ></a>
		          	</#if>
		          	<input type="hidden" name="taxCertificateScan" id="taxCertificateScan" value="${e.taxCertificateScan!""}"/>
		          	<input type="button" style="width:70px;" class="file-btn" id="taxCertificateScanSee" value="浏览..." />
	         	 	<input type="file" style="height:24px;" class="file-file" id="taxCertificateScanField" size="28" onchange="return checkFile(this,'taxCertificateScanSee')"/>
		          	<input type="button" style="width:70px;" class="file-btn" value="上传" onclick="return uploadFile(this,'taxCertificateScanField','taxCertificateScan');" />
	          	</span>
			</td>
		</tr>
		<tr>
		   <#if e.isServiceFee?? && e.isServiceFee=="1">
		     <th style="text-align: center;width:150px;height:10px;">开户发票抬头<span id="_tt" style="color:red;">*</span></th>
			<td>
             	<input type="text" value="${e.bankAccountName!""}" style="width:60%;" name="bankAccountName" id="bankAccountName" data-rule="开户发票抬头:length[2~30];" maxlength="30"/>
			</td>
		   <#else>
		    <th style="text-align: center;width:150px;height:10px;">开户发票抬头<span id="_tt" style="color:red;"></span></th>
			<td>
             	<input type="text" value="${e.bankAccountName!""}" style="width:60%;" name="bankAccountName" id="bankAccountName" />
			</td>
		   </#if>
		    
			<th style="text-align: center;width:150px;height:10px;">开户行名称</th>
			<td>
             	<input type="text" value="${e.companyBank!""}" style="width:60%;" name="companyBank" id="companyBank" />
			</td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">开户帐号</th>
			<td>
             	<input type="text" value="${e.bankNo!""}" style="width:60%;" name="bankNo" id="bankNo" />
			</td>
			<th style="text-align: center;width:150px;height:10px;">税务登记号</th>
			<td>
             	<input type="text" value="${e.taxNo!""}" style="width:60%;" name="taxNo" id="taxNo" />
			</td>
		</tr>
		<tr>
			<td colspan="4" style="color:#999;"><span style="color:red;font-weight:bold;">注册信息</span></td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">微信<span style="color:red;">*</span></th>
			<td>
            ${e.weixin!""}	
			</td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">名片图片<span style="color:red;">*</span></th>
			<td>
	            <#if e.mingpianImage?? && e.mingpianImage!='' > 
		            <a href="${e.mingpianImage!""}" target="_blank" id="mingpianImage"><img id="mingpianImage" style="width: 80px;height: 80px;" title="名片图片" alt="名片图片" src="${e.mingpianImage}" ></a>
			    </#if>
			</td>
			<th style="text-align: center;width:150px;height:10px;">门店图片<span style="color:red;">*</span></th>
			<td>
                <#if e.mendianImage?? && e.mendianImage!='' > 
		            <a href="${e.mendianImage!""}" target="_blank" id="mendianImage"><img id="mendianImage" style="width: 80px;height: 80px;" title="门店图片" alt="门店图片" src="${e.mendianImage}" ></a>
			    </#if>
			</td>
		</tr>
		</#if>
	</table>
	<div style="text-align:center">
	
	</div>
</form>
<script type="text/javascript" src="${basepath}/resource/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${basepath}/manage/account/account.js"></script>
<script type="text/javascript" src="${basepath}/static/js/lib/jquery.json.min.js"></script>
<script type="text/javascript">
function checkFile(file,seeId){
	var fName = $(file).val();
	if(fName==''){
		alert("请选择要上传的资料");
		return false;
	}
	var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if(fExt!='.jpg' && fExt!='.gif' && fExt!='.jpeg' && fExt!='.bpm'){
		alert("请选择正确的图片格式:.jpg,.gif,.jpeg,.bpm");
		return false;
	}
	$("#"+seeId).val("已选择文件");
	return true;
}
function uploadFile(btn,fileId,baseId) {
	var fName = $("#"+fileId).val();
	if(fName==''){
		alert("请选择要上传的资料");
		return false;
	}
	var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if(fExt!='.jpg' && fExt!='.gif' && fExt!='.jpeg' && fExt!='.bpm'){
		alert("请选择正确的图片格式:.jpg,.gif,.jpeg,.bpm");
		return false;
	}	

	$(btn).html("uploading...");
	$.ajaxFileUpload( {
		url : basepath+'/UploadServlet.do?id='+$("#id").val()+'&folder=account&saveModel=server&nameModel=new&name='+baseId,//用于文件上传的服务器端请求地址
		secureuri : false,//一般设置为false
		fileElementId : fileId,//文件上传控件的id属性
		dataType : 'json',//返回值类型 一般设置为json
		success : function(data, status){ //服务器成功响应处理函数
			if(data.filePath){
				$('#'+baseId).val(data.filePath);
				$('#'+baseId+"Img").attr("src",data.filePath);
				$('#'+baseId+"ImgA").attr("href",data.filePath).show();
				$('#'+baseId+"See").val("浏览...");
				$(btn).val("上传完成");
			}else{
				alert('上传失败:'+data.msg);
				$(btn).val("上传失败");
			}
		},
		error : function(data, status, e){//服务器响应失败处理函数
			$(btn).val("上传失败");
		}
	});
	return false;
}
function syncAccount2Sys(id){
     $.ajax({
        url:basepath+'/manage/account/syncAccount2Sys?id='+id,
        type:"POST",
		dataType:'json',
		success:function(data){
			if(data.success==true){
			   alert(data.msg);
			}
			if(data.success==false){
			   alert(data.msg);
			}
            location.reload(); 
		}
     });
 
}
//选择包邮时 选择物流公司
function getType(){
 var expressCode=$("#expressType").val();
	if(expressCode=="1"){
	   $("#_express").show();
	   $("#express_express_m").show();
	   $("#express_other_m").hide();
	   $("#express_express_input").show();
	   $("#express_other_input").hide();
	   $("#express_express_input").attr("colspan","4");
	}else{
	   $("#_express").hide();
	   $("#express_express_input").attr("colspan","1");
	   $("#expressCode").val("");
	   $("#expressOther").val("");
	   
	}
}
function getOtherExpress(){
  var expressOther=$("#expressCode").val();
  if(expressOther=="OTHER"){
    $("#expressOther").val("")
    $("#express_other_m").show();
    $("#express_other_input").show();
    $("#express_express_input").attr("colspan","1");
  }else{
    $("#express_express_input").attr("colspan","4");
    $("#express_other_m").hide();
    $("#express_other_input").hide();
    var express_e=$('#expressCode option:selected').text();
    $("#expressOther").val($.trim(express_e));
  }
}
$(function(){
	var expressType=$("#expressType").val();
	var expressCode=$("#expressCode").val();
	var expressOther=$("#expressOther").val();
	if(expressType=="1"){
	  $("#_express").show();
	}else{
	  var expressCode=$("#expressCode").val("");
	  var expressOther=$("#expressOther").val("");
	}
	  $("#express_other_m").hide();
	  $("#express_other_input").hide();
	if(expressCode=="OTHER"){
	  $("#express_other_m").show();
	  $("#express_other_input").show();
	  $("#express_express_input").attr("colspan","1");
	}else{
	  $("#express_express_input").attr("colspan","3");
	}
});
</script>
</@page.pageBase>
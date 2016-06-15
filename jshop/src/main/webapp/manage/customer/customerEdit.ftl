<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="客户资料管理" currentListUrl="/manage/customer/selectList?init=y">
<style>
.check-more{ width:959px;  line-height: 1.5em;margin-top: 7px;  margin-bottom: 10px; margin-left: -1px;}
.check-more label{ float:left;margin-right:10px;margin-left:-1px; color:#666; cursor:pointer; font-size:12px; width:125px;}
.file-txt{ height:22px; border:1px solid #cdcdcd; width:180px; padding:0 5px;}
.file-btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
.file-file{ position:absolute; top:-4px; right:74px; height:24px; filter:alpha(opacity:0);opacity: 0;width:70px; }
.file-box {position: relative;width: 340px}
</style>
<script type="text/javascript">
function checkSave(btn,status){
	var _submitFlag = true;
	// 销售人员
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo2");
	var nodes = treeObj.getCheckedNodes(true);
	var id = nodes[0].id;
	var pid = nodes[0].pid;
	if(id==null || id==""){
		alert("请选择销售人员！");
		return false;
		$("#showSaler").focus();
	}
	$("#saleId").val(id);
	$("#saleDeptId").val(pid);
	
	// 审核状态
	$("#status").val(status);
	
	if(_submitFlag){
		//$("#form1").submit();
		return true;
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
<form action="${basepath}/manage/customer" method="post" name="form1" id="form1">
	<table class="table table-bordered" >
		<tr>
			<td colspan="4" style="background-color: #dff0d8;text-align: center;">
				<strong>客户资料编辑</strong>
			</td>
		</tr>
		<tr>
			<div id="buttons" style="text-align: center;border-bottom: 1px solid #ccc;padding: 5px;">
			
	  		<!--审核状态-->
	      	<div class="flowstep" style="width:520px;">
	      	<#if e.id?exists>
		        <input type="submit" method="toCusEdit" class="btn btn-primary" 
		        value="保存资料" onclick="return checkSave(this,'salesm');" />
		    <#else>
		        <input type="submit" method="toCustomerAdd" class="btn btn-primary" 
		        value="新增资料" onclick="return checkSave(this,'salesm');" />
		    </#if>
			</div>
			
		</tr>
		<tr style="display: none;">
			<th>id</th>
			<td>
				<input type="hidden" name="id" id="id" value="<#if e.id?exists>${e.id}</#if>" />
			</td>
		</tr>
		<tr>
		<td colspan="4" style="color:#999;"><span style="color:red;font-weight:bold;">基础信息(*为必填项)</span></td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:55px;">会员名称</th>
			<td style="text-align: left;">
             	<input type="text" value="<#if e.account?exists>${e.account}</#if>" name="account" id="account" style="width:60%;" data-rule="会员名称:required;remote[unique?id=<#if e.id?exists>${e.id}</#if>]" maxlength="50" placeholder="请输入会员名称(会员手机号或姓名)" />
			</td>
			<th style="text-align: center;width:150px;height:10px;">是否激活码用户</th>
			<td style="text-align: left;height:10px;">
				<input type="hidden" name="ac" id="ac" value="<#if e.activeCode?exists>${e.activeCode!"0"}</#if>">
				<select name="activeCode" id="activeCode" value="<#if e.activeCode?exists>${e.activeCode!"0"}</#if>">
					<option value="0">否</option>
					<option value="1">是</option>
         		</select>	
			</td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">公司名称<span style="color:red;">*</span></th>
			<td>
             	<input type="text" value="<#if e.companyName?exists>${e.companyName}</#if>" style="width:60%;" 
             	    maxlength="25"	name="companyName" id="companyName" data-rule="公司名称:required;length[2~100];"/>
			</td>
			<th style="text-align: center;width:150px;height:10px;">联系人姓名<span style="color:red;">*</span></th>
			<td>
             	<input type="text" value="<#if e.trueName?exists>${e.trueName}</#if>" style="width:60%;"
             	data-rule="联系人姓名:required;length[2~15];" maxlength="15" name="trueName" id="trueName" />
			</td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">联系人电话<span style="color:red;">*</span></th>
			<td>
             	<input type="text" value="<#if e.tel?exists>${e.tel}</#if>" style="width:60%;" 
             	data-rule="联系人电话:required;length[6~20];" maxlength="20" name="tel" id="tel" />
			</td>
			<th style="text-align: center;width:150px;height:10px;">联系人邮箱</th>
			<td>
             	<input type="text" value="<#if e.email?exists>${e.email}</#if>" style="width:60%;" 
             	data-rule="联系人邮箱:email;length[1~100];remote[unique?id=<#if e.id?exists>${e.id}</#if>];"
             	maxlength="100" name="email" id="email" />
			</td>
			
		</tr>
		<tr>
		    <th style="text-align: center;width:150px;height:10px;">公司法人</th>
			<td>
             	<input type="text" value="<#if e.corporate?exists>${e.corporate}</#if>" style="width:60%;" 
             	data-rule="公司法人:length[2~45];" maxlength="100"
             	name="corporate" id="corporate" />
			</td>
			<th style="text-align: center;width:150px;height:10px;">联系人职位<#--<span style="color:red;">*</span>--></th>
			<td>
             	<input type="text" value="<#if e.jobTitle?exists>${e.jobTitle}</#if>" style="width:60%;"
             	length[2~15];" maxlength="15"  name="jobTitle" id="jobTitle" />
			</td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">总经理姓名</th>
			<td>
             	<input type="text" value="<#if e.bossName?exists>${e.bossName}</#if>" style="width:60%;" 
             	data-rule="总经理姓名:length[2~45];" maxlength="100"
             	name="bossName" id="bossName" />
			</td>
			<th style="text-align: center;width:150px;height:10px;">总经理电话</th>
			<td>
             	<input type="text" value="<#if e.bossPhone?exists>${e.bossPhone}</#if>" style="width:60%;" 
             	data-rule="总经理电话:bossPhone;length[6~15];" data-rule-bossPhone="[/^1([3]\d|4[57]|5[^3]|8\d)\d{8}$/, '请检查手机号格式']"  maxlength="100"
             	name="bossPhone" id="bossPhone" />
			</td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">订制品牌名</th>
			<td colspan="3">
             	<input type="text" value="<#if e.accountBrand?exists>${e.accountBrand}</#if>" style="width:60%;" 
             	 maxlength="100" name="accountBrand" id="accountBrand" />
			</td>
		</tr>
		<tr>
		<th style="text-align: center;width:150px;height:10px;">公司地址<span style="color:red;">*</span></th>
			<td colspan="3">
             	<input type="text" value="<#if e.address?exists>${e.address}</#if>" style="width:60%;" 
             	data-rule="公司地址:required;length[3~500];" maxlength="500" name="address" id="address" />
			</td>
		</tr>
		<td colspan="4" style="color:#999;"><span style="color:red;font-weight:bold;">经营信息(*为必填项)</span></td>
		
		<#assign natureMap = sysDic("account_nature") >
        <#assign annualSalesMap = sysDic("account_annualSales") >
        <#assign ltdMap = sysDic("account_ltd") >
        <#assign productsMap = sysDic("account_products") >
      
		<#assign purchaseTypeMap = sysDic("account_purchaseType") >
		<#assign paymentTypeMap=sysDic("account_paymentType")>
		
		<tr>
			<th style="text-align: center;width:150px;height:10px;">公司性质</th>
			<td style="text-align: left;width:300px;height:10px;">
             	<select name="nature" id="nature">
             		<option value="">请选择</option>
             		<#list natureMap?keys as key>
             			<option value="${key}" <#if e.nature?exists && e.nature==key>selected</#if> >
             				${natureMap[key]}
         				</option>
             		</#list>	
             	</select>
			</td>
			<th style="text-align: center;width:150px;height:10px;">年销售额</th>
			<td style="text-align: left;width:300px;height:10px;">
             	<select name="annualSales" id="annualSales">
             		<option value="">请选择</option>
             		<#list annualSalesMap?keys as key>
             			<option value="${key}" <#if e.annualSales?exists && e.annualSales==key>selected</#if> >
             				${annualSalesMap[key]}
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
				<span style="width:959px;float: left;">
					<span>其他性质</span>
					<input type="text" name="ltdName" id="ltdName" maxLength="50" class="file-txt"/>
				</span>
				</span>
				<span class="msg-box n-left" style="margin-top:20px;" for="ltd"></span>
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
				</span>
				<span class="msg-box n-left" style="margin-top:20px;" for="products"></span>
			</td>
		</tr>
		<#if e.purchaseType?? && e.purchaseType!='' >
	        <#assign purchaseTypeChecks = e.purchaseType?split(",")>
        <#else>
	        <#assign purchaseTypeChecks =[]>
        </#if>
		<tr>
			<th style="text-align: center;">
				<input type="button" class="btn btn-success"  id="showSaler" value="录入人员" /> 
        		</input>
			</th>
			
			<td style="text-align: left;" colspan="3">
				${e.productsName!""}
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
		          	<#if e.busLicenseScan?? && e.busLicenseScan!='' >
		          	 <input type="hidden" name="busLicenseScan" id="busLicenseScan" value="${e.busLicenseScan!""}"/>
	         	 	<input type="button" style="width:90px;" class="file-btn" id="busLicenseScanSee" value="浏览..." />
	         	 	<input type="file" style="height:24px;" class="file-file" id="busLicenseScanField" size="28" onchange="return checkFile(this,'busLicenseScanSee')" />
		          	<input type="button" style="width:70px;"  class="file-btn" value="已上传" onclick="return uploadFile(this,'busLicenseScanField','busLicenseScan');" />
		          	<#else>
		          	<input type="hidden" name="busLicenseScan" id="busLicenseScan" value="${e.busLicenseScan!""}"/>
	         	 	<input type="button" style="width:90px;" class="file-btn" id="busLicenseScanSee" value="浏览..." />
	         	 	<input type="file" style="height:24px;" class="file-file" id="busLicenseScanField" size="28" onchange="return checkFile(this,'busLicenseScanSee')" />
		          	<input type="button" style="width:70px;"  class="file-btn" value="上传" onclick="return uploadFile(this,'busLicenseScanField','busLicenseScan');" />
	          	    </#if>
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
		          	<#if e.taxCertificateScan?? && e.taxCertificateScan!='' > 
		          	<input type="hidden" name="taxCertificateScan" id="taxCertificateScan" value="${e.taxCertificateScan!""}"/>
		          	<input type="button" style="width:90px;" class="file-btn" id="taxCertificateScanSee" value="浏览..." />
	         	 	<input type="file" style="height:24px;" class="file-file" id="taxCertificateScanField" size="28" onchange="return checkFile(this,'taxCertificateScanSee')"/>
		          	<input type="button" style="width:70px;" class="file-btn" value="已上传" onclick="return uploadFile(this,'taxCertificateScanField','taxCertificateScan');" />
		          	<#else>
		          	<input type="hidden" name="taxCertificateScan" id="taxCertificateScan" value="${e.taxCertificateScan!""}"/>
		          	<input type="button" style="width:90px;" class="file-btn" id="taxCertificateScanSee" value="浏览..." />
	         	 	<input type="file" style="height:24px;" class="file-file" id="taxCertificateScanField" size="28" onchange="return checkFile(this,'taxCertificateScanSee')"/>
		          	<input type="button" style="width:70px;" class="file-btn" value="上传" onclick="return uploadFile(this,'taxCertificateScanField','taxCertificateScan');" />
	          	    </#if>
	          	</span>
			</td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">开户行名称</th>
			<td>
             	<input type="text" value="<#if e.companyBank?exists>${e.companyBank}</#if>" style="width:60%;" name="companyBank" id="companyBank" />
			</td>
			<th style="text-align: center;width:150px;height:10px;">开户人名称</th>
			<td>
             	<input type="text" value="<#if e.bankAccountName?exists>${e.bankAccountName}</#if>" style="width:60%;" name="bankAccountName" id="bankAccountName" />
			</td>
		</tr>
		<tr>
			<th style="text-align: center;width:150px;height:10px;">开户帐号</th>
			<td>
             	<input type="text" value="<#if e.bankNo?exists>${e.bankNo}</#if>" style="width:60%;" name="bankNo" id="bankNo" />
			</td>
			<th style="text-align: center;width:150px;height:10px;">税务登记号</th>
			<td>
             	<input type="text" value="<#if e.taxNo?exists>${e.taxNo}</#if>" style="width:60%;" name="taxNo" id="taxNo" />
			</td>
		</tr>
	</table>
	<div style="text-align:center">
	<#if e.status??>
		<#if e.status=='init' >
        	<input type="submit" method="toCusEdit" class="btn btn-primary" 
        		value="保存" onclick="return checkSave(this,'sales');" />
     	<#elseif e.status=='sales'>
     		<#if checkPrivilege("/manage/account/audit/salesm") >
            	<input type="submit" method="toCusEdit" class="btn btn-primary" 
            		value="保存" onclick="return checkSave(this,'salesm');" />
     		</#if>
     	<#elseif e.status=='salesm' || e.status=='finance'>
     		<#if checkPrivilege("/manage/account/audit/finance") >
            	<input type="submit" method="toCusEdit" class="btn btn-primary" 
            		value="保存" onclick="return checkSave(this,'finance');" />
     		</#if>
		</#if>
	</#if>
	</div>
</form>
<script>
$(document).ready(function(){
  //$("#ltds:first-child").children().attr("data-rule","经营性质:checked;");
  //$("#productss:first-child").children().attr("data-rule","经销产品:checked;");
});
</script>
<script type="text/javascript" src="${basepath}/resource/js/ajaxfileupload.js"></script>
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
		url : basepath+'/UploadServlet.do?id='+$("#id").val()+'&folder=customer&saveModel=server&nameModel=new&name='+baseId,//用于文件上传的服务器端请求地址
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
</script>
</@page.pageBase>
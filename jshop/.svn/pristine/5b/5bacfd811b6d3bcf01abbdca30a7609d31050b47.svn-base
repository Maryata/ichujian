<#import "/account/accountHtml.ftl" as accountHtml/>
<@accountHtml.html currentMenu="account" title="个人资料" jqueryValidator=true>
<div class="center-box m-t20">
<form method="post" role="form" id="form" class="form-horizontal" action="" theme="simple">
    <table class="cpx addborder" width="100%" border="0" cellspacing="0" cellpadding="0">
        <thead class="title1">
          <tr>
            <td colspan="2">个人信息</td>
          </tr>
        </thead>
        <thead class="left">
          <tr>
            <td class="title2" width="150">登录帐号</td>
            <td align="left">${e.account!""	}
             <input type="hidden" id="account" name="account" value="${e.account!""	}" />
            <#if e.accType?? && e.accType=="0" || e.accType=="-1">
                （个人账号）
            </#if>    
              <#if e.accType?? && e.accType=="1">
                （企业账号）
              </#if></td>      
          </tr>
          <tr>
            <td class="title2">联系人姓名<span class="color-red">*</span></td>
            <td>
            <input class="inputbox " type="text" placeholder="请输入联系人姓名" value="${e.trueName!''}" name="trueName" id="trueName" data-rule="联系人姓名:required;length[2~15];" maxlength="15" >
            <input type="hidden" name="accType" id="accType" value="${e.accType!""}"/>
            </td>      
          </tr>
          <tr>
            <td class="title2">联系人电话</td>
            <td><input class="inputbox " type="text" placeholder="请输入联系人电话" value="${e.tel!''}" name="tel" id="tel" data-rule="手机号:mobile;length[11];"  <#--data-rule-mobile="[/^1([3]\d|4[57]|5[^3]|8\d)\d{8}$/, '请检查手机号格式']"-->   maxlength="11" ></td>      
          </tr>
          <tr>
            <td class="title2">联系人邮箱</td>
            <td><input class="inputbox " type="text" placeholder="请输入联系人邮箱" value="${e.email!''}" name="email" id="email" data-rule="联系人邮箱:email;length[1~100];remote[unique.html?id=${e.id!""}]" data-rule-email="[/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.(?:com|cn)$/,'请输入正确的邮箱']"; maxlength="100" ></td>      
          </tr>
          <#if e.accType?? && e.accType=="1" || e.accType=="-1">
          <tr>
            <td class="title2">联系人职位</td>
            <td><input class="inputbox " type="text" placeholder="请输入联系人职位" value="${e.jobTitle!''}" name="jobTitle" id="jobTitle" data-rule="联系人职位:length[0~15];" maxlength="15"></td>      
          </tr>
          <tr>
            <td class="title2">公司名称<span class="color-red">*</span></td>
            <td><input class="inputbox widthlong " type="text" placeholder="请输入公司名称" value="${e.companyName!''}" name="companyName" id="companyName" data-rule="公司名称:required;length[0~100];" maxlength="100"></td>      
          </tr>
          <tr>
            <td class="title2">公司地址<span class="color-red">*</span></td>
            <td><input class="inputbox widthlong " type="text" placeholder="请输入公司地址" value="${e.address!''}" name="address" id="address" data-rule="公司地址:required;length[0~100];" maxlength="100"></td>      
          </tr>
		  
		  <tr>
            <td class="title2">订制品牌名</td>
            <td><input class="inputbox widthlong " type="text" placeholder="填写订制品牌名" value="${e.accountBrand!''}" name="accountBrand" id="accountBrand" data-rule="订制品牌名:length[0~100];" maxlength="100"></td>      
          </tr>
          </#if>
        </thead>
    </table>
	<#if e.accType?? && e.accType=="1" || e.accType=="-1">
	<#assign natureMap = sysDic("account_nature") >
    <#assign annualSalesMap = sysDic("account_annualSales") >
    <#assign ltdMap = sysDic("account_ltd") >
    <#assign productsMap = sysDic("account_products") >
	
	<table class="cpx addborder m-t20" width="100%" border="0" cellspacing="0" cellpadding="0">
        <thead class="title1">
          <tr>
            <td colspan="2">经营信息</td>
          </tr>
        </thead>
        <thead class="left">
          <tr>
            <td class="title2" width="150">公司性质<span class="color-red">*</span></td>
            <td align="left">
			<select class="inputbox" name="nature" id="nature" data-rule="公司性质:required;">
	        <option value="">请选择</option>
	        <#list natureMap?keys as key>
	        <option value="${key}" <#if e.nature?? && e.nature==key > selected </#if> > ${natureMap[key]}</option>
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
            <td class="title2">经营性质<span class="color-red ">*</span></td>
            <td><span class="check-more">
			<#list ltdMap?keys as key>
            <label id="ltds"> <input type="checkbox" <#list ltdChecks as c > <#if c=key> checked </#if></#list> name="ltd" value="${key}" vName="${ltdMap[key]}" id="ltd_${key}">${ltdMap[key]}
            </label>
			</#list>
			<span class="msg-box n-right" for="ltd"></span>
			<span class="other"><span>其他性质</span>
		  <input type="text" name="ltdName" id="ltdName" value="${e.ltdName!""}" maxlength="50" class="file-txt">
          </span> </span> 
		  </td>      
          </tr>
        
		<#if e.products?? && e.products!='' >
        <#assign productsChecks = e.products?split(",")>
        <#else>
        <#assign productsChecks =[]>
        </#if>
		
          <tr>
            <td class="title2">经销产品<span class="color-red">*</span></td>
            <td><span class="check-more">
          <#list productsMap?keys as key>
            <label id="productss"> <input type="checkbox" <#list productsChecks as c > <#if c=key> checked </#if></#list> name="products" value="${key}" vName="${productsMap[key]}" id="products_${key}">${productsMap[key]}</label>
          </#list>
           <span class="other"><span>其它配件</span><input type="text" name="productsName" id="productsName" value="${e.productsName!""}" maxLength="50" class="file-txt"/></span>
		  </span>
		  </td>      
          </tr>
		  
          <tr>
            <td class="title2">年销售额</td>
            <td> <select class="inputbox" name="annualSales" id="annualSales">
	        <option value="">请选择</option>
	        <#list annualSalesMap?keys as key>
	        <option value="${key}" <#if e.annualSales?? && e.annualSales==key > selected </#if> > ${annualSalesMap[key]}</option>
	        </#list>
	      </select></td>      
          </tr>          
        </thead>
    </table>
	
	<table class="cpx addborder m-t20" width="100%" border="0" cellspacing="0" cellpadding="0">
        <thead class="title1">
          <tr>
            <td colspan="2">公司资质</td>
          </tr>
        </thead>
        <thead class="left">
          <tr>
            <td class="title2" width="150">公司营业执照<br>扫描件</td>
            <td align="left"><span class="file-box">
          <#if e.busLicenseScan?? && e.busLicenseScan!='' > 
            <a href="${e.busLicenseScan!""}" target="_blank" id="busLicenseScanImgA"><img id="busLicenseScanImg" style="width: 80px;height: 80px;margin-right:5px;" title="公司营业执照扫描件" alt="公司营业执照扫描件" src="${e.busLicenseScan}" ></a>
          <#else>
            <a target="_blank" id="busLicenseScanImgA" style="display: none"><img id="busLicenseScanImg" style="width: 80px;height: 80px;margin-right:5px;" title="公司营业执照扫描件" alt="公司营业执照扫描件" ></a>
          </#if>
          <input type="hidden" name="busLicenseScan" id="busLicenseScan" value="${e.busLicenseScan!""}"/>
          
          <input type="button" class="file-btn" id="busLicenseScanSee" value="浏览..." />
          <input type="file" class="file-file" id="busLicenseScanField" size="28" onchange="return checkFile(this,'busLicenseScanSee')" />
          <input type="button" class="file-btn" value="上传" onclick="return uploadFile(this,'busLicenseScanField','busLicenseScan');" />
          </span></td>      
          </tr>
          <tr>
            <td class="title2">公司税务登记证<br>扫描件</td>
            <td><span class="file-box">          
           <#if e.taxCertificateScan?? && e.taxCertificateScan!='' > 
            <a href="${e.taxCertificateScan!""}" target="_blank" id="taxCertificateScanImgA"><img id="taxCertificateScanImg" style="width: 80px;height: 80px;margin-right:5px;" title="公司税务登记证扫描件" alt="公司税务登记证扫描件" src="${e.taxCertificateScan}" ></a>
          <#else>
            <a target="_blank" id="taxCertificateScanImgA" style="display: none"><img id="taxCertificateScanImg" style="width: 80px;height: 80px;margin-right:5px;" title="公司营业执照扫描件" alt="公司营业执照扫描件" ></a>
          </#if>
          <input type="hidden" name="taxCertificateScan" id="taxCertificateScan" value="${e.taxCertificateScan!""}"/>
          
          <input type="button" class="file-btn" id="taxCertificateScanSee" value="浏览..." />
          <input type="file" class="file-file" id="taxCertificateScanField" size="28" onchange="return checkFile(this,'taxCertificateScanSee')"/>
          <input type="button" class="file-btn" value="上传" onclick="return uploadFile(this,'taxCertificateScanField','taxCertificateScan');" />
          </span></td>      
          </tr>
          <#if e.isServiceFee?? && e.isServiceFee=="1">
            <tr>
            <td class="title2">开户发票抬头<span class="color-red">*</span></td>
            <td><input class="inputbox" type="text" value="${e.bankAccountName!''}" name="bankAccountName" id="bankAccountName" placeholder="开户发票抬头" data-rule="开户发票抬头:required;length[2~30];" maxlength="30" /></td>      
          </tr>
          <#else>
	        <tr>
	        <td class="title2">开户发票抬头</td>
	        <td><input class="inputbox" type="text" value="${e.bankAccountName!''}" name="bankAccountName" id="bankAccountName" placeholder="开户发票抬头" data-rule="开户发票抬头:length[2~30];" maxlength="30" /></td>      
	        </tr>
          </#if>
          
          <tr>
            <td class="title2">开户行名称</td>
            <td><input class="inputbox" type="text" value="${e.companyBank!''}" name="companyBank" id="companyBank" placeholder="请输入开户行名称" data-rule="开户行名称:length[2~20];" maxlength="20" /></td>      
          </tr>
          <tr>
            <td class="title2">开户帐号</td>
            <td><input class="inputbox" type="text" value="${e.bankNo!''}" name="bankNo" id="bankNo" placeholder="请输入开户帐号"  data-rule="开户帐号:bankNo;length[10~30];"   maxlength="30" /></td>      
          </tr>
          <tr>
            <td class="title2">税务登记号</td>
            <td><input class="inputbox" type="text" value="${e.taxNo!''}" name="taxNo" id="taxNo" placeholder="请输入税务登记号" data-rule="税务登记号:taxNo;length[10~30];"  maxlength="30" /></td>      
          </tr>
        </thead>
      </table>
     </#if>
     <#if e.accType?? && e.accType=="0">
     <div class="function-btns m-t20">
      <button type="button" class="btn-red-max tanchu2_btn f-r" style="padding:15px 75px;" onclick="changeType()"/>申请转为商家/企业账号</button>
     </div>
     </#if>	
	<div class="function-btns m-t20">
	  <input type="hidden" value="${e.id!""}" id="accountId"/>
      <input type="submit" class="btn-orange-max tanchu2_btn f-r" onClick="CheckMess(this)" value="提交">
      </div>	 
     
      </div>
      
    </div>
</form>    
</div>
<script>
$(document).ready(function(){
  $("#ltds:first-child").children().attr("data-rule","经营性质:checked;");
  $("#productss:first-child").children().attr("data-rule","经销产品:checked;");
});
</script>
<script type="text/javascript" src="${basepath}/resource/js/ajaxfileupload.js"></script>
<script type="text/javascript">
function changeType(){
    if(confirm("是否申请转为企业账号？")){
      $("#accType").val(-1);
      $.ajax({
 	    url:'${basepath}/account/saveSetting',
		data: $('#form').serialize(),
		type:"POST",
		dataType:'json',
		success:function(data){
		    layer.msg("请补全资料！");
		    setTimeout(function(){location.reload();}, 1000 );
		}
 	});
   }
}
function CheckMess(form){
 $('#form').isValid(function(v){
  if(!v){return false;}
    var index = layer.load(3,{shade: false});
 	$.ajax({
 	    url:'${basepath}/account/saveSetting',
		data:$('#form').serialize(),
		type:"POST",
		dataType:'json',
		success:function(data){
		setTimeout(function(){layer.close(index);},2000 );
			if(data.msg){
			setTimeout(function(){location.href="${basepath}/account/accountCenter";},1000 );
			}
		}
 	});
   });
};
function checkFile(file,seeId){
	var fName = $(file).val();
	if(fName==''){
		layer.alert("请选择要上传的资料");
		return false;
	}
	var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if(fExt!='.jpg' && fExt!='.gif' && fExt!='.jpeg' && fExt!='.bpm'){
		layer.alert("请选择正确的图片格式:.jpg,.gif,.jpeg,.bpm");
		return false;
	}
	$("#"+seeId).val("已选择文件");
	return true;
}
function uploadFile(btn,fileId,baseId) {
	var fName = $("#"+fileId).val();
	if(fName==''){
		layer.alert("请选择要上传的资料");
		return false;
	}
	var fExt = fName.substr(fName.lastIndexOf(".")).toLowerCase();//获得文件后缀名
	if(fExt!='.jpg' && fExt!='.gif' && fExt!='.jpeg' && fExt!='.bpm'){
		layer.alert("请选择正确的图片格式:.jpg,.gif,.jpeg,.bpm");
		return false;
	}	

	$(btn).val("uploading...");
	$.ajaxFileUpload( {
		url : basepath+'/UploadServlet.do?id='+$("#accountId").val()+'&folder=account&saveModel=server&nameModel=new&name='+baseId,//用于文件上传的服务器端请求地址
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
				layer.alert('上传失败:'+data.msg);
				$(btn).val("上传失败");
			}
		},
		error : function(data, status, e){//服务器响应失败处理函数
			$(btn).val("上传失败");
		}
	});
	return false;
}
$(function(){//如果手机号为空   自动带出
    if($("#tel").val()==""){
       $("#tel").val($("#account").val());
    }
});
</script>
</@accountHtml.html>
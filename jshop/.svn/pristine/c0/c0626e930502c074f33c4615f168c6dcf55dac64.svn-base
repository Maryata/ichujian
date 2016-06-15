<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="样品申请管理" currentListUrl="/manage/sampleApply/selectList?init=y">
<#assign plineMap = sysDic("account_purchaseType") >
<form method="post" theme="simple" id="form" action="#">
<style>.col-lg-12 .layui-layer{ left:25%;}</style>
<table class="table table-bordered">
	<table class="table table-bordered">
		<tr>
			<td colspan="4" style="background-color: #dff0d8;text-align: center;">
				<strong>样品申请添加<span id="titles" style="display:none;color:red;">(当前用户已申请样品总数量为<span id="sampleNumber" style="color:black;">0</span>张)</span></strong>
			</td>
		</tr>
		<tr>
			<th style="text-align: right;width:150px;">选择用户</th> 
			<td style="text-align: left;" colspan="4">
			<button type="button" class="btn btn-success selectCustomers" onClick="selectCustomer()">选择用户</button>
			<span type="color:red">*</span>
			用户申请的样品数量最多为6张，同款最多2张，不同款最多3种！
			</td>
		</tr>
		
		<tr id="contacts" style="display:none;">
			<th style="text-align: right;">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</th> 
			<td style="text-align: left;" colspan="4">
			   <input type="text" name="contact" id="contact"/>
			   <input type="hidden" name="customerId" id="customerId" readonly="readonly"/>
			   <input type="hidden" name="sId" id="sId" readonly="readonly"/>
			</td>
		</tr>
		<tr id="phones" style="display:none;">
			<th style="text-align: right;">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话</th> 
			<td style="text-align: left;" colspan="4">
			<input type="text" name="phone" id="phone" readonly="readonly"/>
			</td>
		</tr>
		<tr id="companys" style="display:none;">
			<th style="text-align: right;">公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司</th> 
			<td style="text-align: left;" colspan="4">
			<input type="text" name="company" id="company" />
			</td>
		</tr>
		<tr id="expresss" style="">
			<th style="text-align: right;">物流公司</th> 
			<td style="text-align: left;" colspan="4">
			<#assign express =sysDic("order_express_yp")>
		      <select class="dd-ml" id="address_express"  onchange="getExpress()" name="expressCode">
		        <option value="">请选择物流公司</option>
		        <#list express?keys as key>
		        <option value="${key}"> ${express[key]}</option>
		        </#list>
		      </select>
			</td>
		</tr>
		<tr id="addresss" style="display:none;">
			<th style="text-align: right;">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址</th> 
			<td style="text-align: left;" colspan="4">
			<input type="text" name="address" id="address" style="width:700px;"/>
			</td>
		</tr>
		<table class="table table-bordered">
		<tr>
			<td style="text-align:center;width:100%;">
                <input type="button" class="btn btn-success" onclick="addDetail()"  value="新增样品">
                <button type="button" class="btn btn-info"　id="addSampleApply"　 onClick="return addSampleApply()">确认添加</button>
                </input>
			</td>
		</tr>
	</table>
		<table class="table table-bordered" id="tb_detail" style="display: none;">
			<tr>
				<th nowrap='nowrap' width="10%">样品</th>
				<th nowrap='nowrap' width="80%">选择</th>
				<th nowrap='nowrap' width="10%">删除</th>
			</tr>
		</table>
	</table>
</table>

</form>
<div style="display:none;" id="product">
  <table class="table table-hover" >
   <thead>
    <tr>
    <td style="background-color: #dff0d8;text-align: center;color:red;" colspan="3"><h4>样品添加历史记录</h4></td>
    </tr>
	<tr style="background-color: #dff0d8">
		<th width="250">产品名称</th>
		<th nowrap="nowrap">产品数量</th>
		<th nowrap="nowrap">添加时间</th>
	</tr>
  </thead>
  <tbody id="productList">
  </tbody>	
</table>
</div>

<div id="customerPage" style="display:none;">
<table class="table table-hover" id="table">
  <tr style="background-color: #dff0d8">
    <td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:15px;">此处可精确查找用户信息</div></td>
	<td style="text-align: left;">
		<div style="text-align: left;margin-top:8px;">
		<input type="text"  name="detail" style="width:75%;" class="search-query input-small" id="detail" placeholder="请输入姓名或手机号" />
		</div>
	</td>
	<td style="text-align: left;">
	  <div style="text-align: left;margin-top:8px;">
	   <button id="selectDetail" type="button" onClick="loadCuntData()" class="btn btn-info">确认查找</button>
	  </div> 
	</td>
  </tr>
</table>
<table class="table table-hover" >
   <thead>
	<tr style="background-color: #dff0d8">
		<th width="50">选择</th>
		<th nowrap="nowrap">姓名</th>
		<th nowrap="nowrap">电话</th>
		<th nowrap="nowrap">公司</th>
		<th nowrap="nowrap">地址</th>
	</tr>
  </thead>
  <tbody id="customerList">
  </tbody>	
</table>
<div id="custPager"></div>
</div>

<script type="text/javascript" src="${basepath}/resource/layer-v1.9.2/layer/layer.js"></script>	
<script type="text/javascript" src="${basepath}/static/laypage/laypage.js"></script>
<script type="text/javascript" src="${basepath}/manage/sampleApply.js"></script>	
<script type="text/javascript">
  /*选择用户*/
function selectCustomer(){
      $("#detail").val("");
      $("#titles").hide();
      $("#product").hide();
      $("#productList").empty();
     $(".selectCustomers").attr({"disabled":"disabled"});//置灰
	 layer.open({
    	title: "选择用户",
	    type: 1,
	    skin: 'layui-layer-rim',
	    area: ['780px',"450px"],
	    content: $('#customerPage'),
	    success: function(){
	    	loadCuntData();
	    	$(".selectCustomers").removeAttr("disabled");
	    }
   });
  }
function loadCuntData(d){
  var d = d||{};
  d.detail = $("#detail").val();
  d.page = d.page || 1;
  
  $.ajax({
 	    url:'${basepath}/manage/customer/selectCustomer',
		type:"POST",
		dataType:'json',
		data:d,
		success:function(data){
			//load data..
			$("#customerList").empty();
			for(var m = 0;m<data.customerList.length;m++){
                var cust = data.customerList[m];
			    var _ts="<tr id='customer-"+cust.id+"'  data-id='"+cust.id+"' data-trueName='"+cust.trueName+"' data-tel='"+cust.tel+"' data-companyName='"+cust.companyName+"' data-address='"+cust.address+"'>";
		        _ts +="<td><input type='radio' name='id' onClick=\"insertCustomer("+cust.id+")\" value='"+cust.id+"'/></td>";
		        _ts +="<td nowrap='nowrap'>"+cust.trueName+"</td>";
		        _ts +="<td nowrap='nowrap'>"+cust.tel+"</td>";
		        _ts +="<td nowrap='nowrap'>"+cust.companyName+"</td>";
		        _ts +="<td nowrap='nowrap'>"+cust.address+"</td>";
		        _ts +="</tr>";
		        $("#customerList").append(_ts);
		    }
		    
		    laypage({
			    cont: "custPager",
			    pages: data.pagerSize,
			    groups: 3,
			    curr : d.page,
			    jump: function(obj, first){ //触发分页后的回调
	                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
	                    loadCuntData({page:obj.curr});
	                }
	            }
			});
		}
	});
};
  
  /*赋值 ------待修改*/
  function insertCustomer(id){
    var _currRow = $("#customer-"+id);
    var index=_currRow.attr("data-index");
    $("#customerId").val(_currRow.attr("data-id"));
    //$("#sId").val(_currRow.attr("data-id"));
    $("#contact").val(_currRow.attr("data-trueName")).show();
    $("#phone").val(_currRow.attr("data-tel")).show();
    $("#address").val(_currRow.attr("data-address")).show();
    $("#company").val(_currRow.attr("data-companyName")).show();
    $("#contacts").show();
    $("#phones").show();
    $("#addresss").show();
    $("#companys").show();
     $.ajax({//判断已推送的数量
 	    url:'${basepath}/manage/sampleApply/judgeSampleNumber',
		type:"POST",
		dataType:'json',
		data: {accountId :$("#customerId").val()},
		success:function(data){
			//callback.call(this,data);
			layer.msg("当前用户已申请样品总数量为"+data+"张");
			$("#sampleNumber").text(data);
			$("#titles").show();
		 }
	});
	 $.ajax({
 	    url:'${basepath}/manage/sampleApply/checkMessage',
		type:"POST",
		dataType:'json',
		data: {customerId :$("#customerId").val()},
		success:function(data){
		   var customer = data.customer;
		   var _info="";
		   if(customer.length==0){
		      _info+="当前用户暂无申请过样品";
		      layer.msg(_info);
		   }else{
		      for(var m = 0;m<customer.length;m++){
                var type = customer[m];
                _info+="<tr>";
	            _info+="<td>"+type.productName+"</td><td>"+type.productNum+"</td><td>"+type.creatTime+"</td>";
	            _info+="</tr>";
               }
            _info+="</table>";
           $("#productList").append(_info);
		   }
		  }
      });
    layer.closeAll();
    $("#product").show();    
  }

 /*添加样品*/ 
var fIndex = 0;
function addDetail(){
   var customerId=$("#customerId").val();
     if(customerId==""){
      layer.alert('请选择用户！');
      return false;
    }
	fIndex++;
	$("#tb_detail").show();
	var _s="<tr><input type='hidden' name='ids' value='-1'/>";
	  _s +="<th style='text-align: ;'>样品"+fIndex+"</th>";
	  _s +="<td style='text-align: ;' colspan='8'>";
	  _s +="<input type='hidden' name='plineCodes' class='plineCode"+fIndex+"'/><input type='hidden' name='code' class='code"+fIndex+"'/><input type='hidden' name='productNames' class='productName"+fIndex+"'/> <input type='hidden' name='productIds' class='productId"+fIndex+"' /><input type='hidden' name='plineNames' class='plineName"+fIndex+"' /> <input type='hidden' name='brandNames' class='brandName"+fIndex+"' /> <input type='hidden' name='modelNames' class='modelName"+fIndex+"' />";
	  _s +="<select id='plineName"+fIndex+"' name='plineName' ><option value='"+fIndex+"'>请选择指电品牌</option><#list plineMap?keys as key><#if key=="A300A1" || key=="A300A2"><#else><option value='${key}'>${plineMap[key]}</option></#if></#list></select>";
	  _s +="<select id='brandName"+fIndex+"' name='brandName' onchange=\"getMobel("+fIndex+")\"><option>请选择手机品牌</option><#list systemManager().catalogs as item><option value='${item.code}'>${item.name}</option></#list></select>"
	  _s +="<select id='modelName"+fIndex+"'  name='modelName' onchange=\"getDetail("+fIndex+")\"><option>请选择手机型号</option></select>";
	  _s +="<select id='productNum"+fIndex+"' name='productNums' class='productNum"+fIndex+"' style='width:55px;'><option value='2'>2张</option><option value='1'>1张</option></select>";
	  _s +="</td> <td style='text-align:;'><button class='btn btn-danger' onclick=\"delCurrTr(this)\"><i class='icon-remove-sign icon-white'></i>删除</button></td>";
	  _s +="</tr>";
	  $("#tb_detail").append(_s);
}
//删除一行
function delCurrTr(_tr,id){
	$(_tr).parent('td').parent('tr').remove();
	if($("#tb_detail tr").length==1){
		$("#tb_detail").hide();
	} 
}	
//手机品牌改变事件 	    
function getMobel(index){
    if($("#plineName"+index).val()==index){
      layer.alert('请选择指电品牌！');
      $("#brandName"+index).val("");
      return false;
    }
     var name=$("#brandName"+index).find("option:checked").text();
     $("#modelName"+index).empty();
     $("#modelName"+index).prepend("<option>请选择手机型号</option>"); 
     $.ajax({
 	    url:'${basepath}/manage/sampleApply/findModel',
		type:"POST",
		dataType:'json',
		data: {code :$("#brandName"+index).val()},
		success:function(data){
		var modelType = data.mainItem;
		 for(var m = 0;m<modelType.length;m++){
         var type = modelType[m];
         $("#modelName"+index).append("<option id='"+type.id+"'  value='"+type.code+"'>" + type.name + "</option>");
		 }
		 }
	  });
}			   
//添加机型信息
function getDetail(index){
    var modelName =$("#modelName"+index).find("option:selected").text();
    var brandName=$("#brandName"+index).find("option:selected").text();
    var plineName=$("#plineName"+index).find("option:selected").text();
    if($("#plineName"+index).val()==index){
      layer.alert('请选择指电品牌！');
      $("#modelName"+index).val("");
      return false;
    }
     if($("#brandName"+index).val()==index){
      layer.alert('请选择手机品牌！');
      $("#modelName"+index).val("");
      return false;
    }
    $(".productName"+index).val(plineName+"-"+brandName+"-"+modelName);//产品名字
    $(".productId"+index).val($("#modelName"+index+" option:selected").attr("id"));
	$(".code"+index).val($("#modelName"+index).val());//value
	$(".plineCode"+index).val($("#plineName"+index).val());
	$(".plineName"+index).val(plineName);
	$(".brandName"+index).val(brandName);
	$(".modelName"+index).val(modelName);

}  
  
function addSampleApply(){
    var customerId=$("#customerId").val();
     if(customerId==""){
      layer.alert('请选择用户！');
      return false;
    }
    var expectSignDate =$("select[name=expressCode]");
	if(expectSignDate.val()==""){
	    layer.alert('请选择物流公司');
		return false;
	}
    if($("#tb_detail tr").length<2){
      layer.alert('请新增样品！');
      return false;
    }
    checkApplyNum();
    return;
}


 function checkSampleNumber(callback){//判断样品申请的数量
   $.ajax({
 	    url:'${basepath}/manage/sampleApply/checkModel',
		type:"POST",
		dataType:'json',
		data: {code :$("#modelNames").val(),accountId :$("#customerId").val()},
		success:function(data){
		callback.call(this,data);
		 }
	  });
  }
</script>
</@page.pageBase>
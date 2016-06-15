<#import "/account/accountHtml.ftl" as accountHtml/>
<@accountHtml.html currentMenu="address" title="配送地址" jqueryValidator=true>

<div class="center-box m-t20">

<#assign addressSize = addressList?size >


  <div id="tables" class="${(addressSize==0)?string('none', '')}">
	<table class="cpx" border="0" cellspacing="0" cellpadding="0">
		<thead class="title1">
		  <tr>
			<td colspan="6">配送地址</td>
		  </tr>
		</thead>
		<thead class="title2">
		  <tr>                
			<td width="100">设为默认</td>
			<td width="100">收货人</td>
			<td>收货地址</td>
			<td width="200">邮箱</td>
			<td width="120">联系电话</td>
			<td width="130">操作</td>
		  </tr>
		</thead>
		<tbody id="tableDataList">		 
		  <#list addressList as item>
		  <tr class="first1" data-id="${item.id!""}" data-address="${item.address!""}" data-phone="${item.phone!""}"  data-mobile="${item.mobile!""}">
			<td><input name="setDefaultRadio" type="radio" value="${item.id!""}" <#if item.isdefault?? && item.isdefault=="y"> checked </#if> ></td>        
			<td><span title="${item.name!""}">${item.name!""} </span></td>
			<td><span title="${item.pcadetail!""}&nbsp;&nbsp;${item.address!""}">${item.pcadetail!""}&nbsp;&nbsp;${item.address!""}</span></td>
			<td>${item.zip!""}</td>
			<td>${item.mobile!""}</td>
			<td>
			<a class="btn-orange-min" onclick="javascript:update(${item.id!""});">修改</a>
			<a class="btn-orange-min m-t10" href="${basepath}/account/deleteAddress?id=${item.id!""}" onclick="return deletes()" >删除</a>
			</td>
		  </tr>
		  </#list>
		  
		</tbody>            
		<tfoot>
		  <tr>
			<td colspan="6" style="height:20px;"></td>
		  </tr>
		</tfoot>
	  </table>  
  	<div class="function-btns m-t20"> 
      <a class="btn-orange-max tanchu2_btn f-r" href="javascript:newAddress()">新增地址</a>
      </div>
  </div>
  
  <div id="addsForm" class="${(addressSize==0)?string('', 'none')}">
  <form role="form" id="form" class="form-horizontal" method="post" action="" theme="simple">
	<table class="cpx addborder" width="100%" border="0" cellspacing="0" cellpadding="0">
        <thead class="title1">
          <tr>
            <td colspan="2">收货地址</td>
          </tr>
        </thead>
        <thead class="left">
          <tr>
            <td class="title2" width="150">收货人姓名<span class="color-red">*</span></td>
            <td align="left"><input class="inputbox " value="${address.name!""}" name="name" type="text"  id="name" data-rule="收货人姓名:required;length[2~20];name;" placeholder="请输入收货人姓名" maxlength="20" size="20"></td>      
          </tr>
            <td class="title2">联系电话<span class="color-red">*</span></td>
            <td><input   value="${address.mobile!""}" name="mobile"  type="text" class="inputbox" data-rule="手机号:required;mobile;length[11];" id="mobile" data-rule-mobile="[/^1([3]\d|4[57]|5[^3]|8\d)\d{8}$/, '请检查手机号格式']" placeholder="请输入联系人电话" maxlength="11"/></td>      
          </tr>
          <tr>
            <td class="title2">邮　　箱</td>
            <td><input class="inputbox" type="text"  value="${address.zip!""}" name="zip"   id="zip" data-rule="收货人邮箱:zip;"  data-rule-zip="[/^(([^<>()[\]\\.,;:\s@]+(\.[^<>()[\]\\.,;:\s@]+)*)|(.+))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,'请输入正确的邮箱']"; placeholder="方便您接收订单信息" maxlength="80"/></td>      
          </tr>
          
          
          <tr>
            <td class="title2">所在地址<span class="color-red">*</span></td>
            <td><div id="areaItem" style="margin: 0;"></div>
        <span class="inputtips n-msg" id="areaItem-msg"></span></td>      
          </tr>
          <tr>
            <td class="title2">详细地址<span class="color-red">*</span></td>
            <td><input  value="${address.address!""}" name="address"  type="text" class="inputbox widthlong" id="address" data-rule="地址:required;length[3~70];address;" placeholder="请输入收货人地址" maxlength="70" size="70"/></td>      
          </tr>
        </thead>
    </table>
	<input type="hidden" id="id" name="id" value="${address.id!""}"/>
	<input type="hidden" id="phone" name="phone" value="${address.phone!""}"/>
	<input type="hidden" id="province" name="province" value="${address.province!""}"/>
	<input type="hidden" id="city" name="city" value="${address.city!""}"/>
	<input type="hidden" id="area" name="area" value="${address.area!""}"/>
	<input type="hidden" id="town" name="town" value="${address.town!""}"/>
	<input type="hidden" name="provinceAddress" id="provinceAddress" value="${address.provinceAddress!""}"/>
	<input type="hidden" name="cityAddress" id="cityAddress" value="${address.cityAddress!""}"/>
	<input type="hidden" name="areaAddress" id="areaAddress" value="${address.areaAddress!""}"/>
	<input type="hidden" name="townAddress" id="townAddress" value="${address.townAddress!""}"/>
	
	<input type="hidden" id="pcadetail" name="pcadetail" value="${address.pcadetail!""}"/>
	
    <div class="function-btns m-t20"> <a href="javascript:returnInfo()" class="tanchu2_btn btn-red-max">返回</a> <button type="button" class="btn-orange-max tanchu2_btn f-r" id="submit_saveButton" style="margin-left:300px;">保 存</button> </div>  
  </form>
  </div>
  
 </div>
</div>

<script src="${basepath}/static/js/area.js"></script>

<script type="text/javascript">
$(function(){

    add_select(0);

    $('body').on('change', '#areaItem select', function() {
        var $me = $(this);
        var $next = $me.next();
        /**
         * 如果下一级已经是当前所选地区的子地区，则不进行处理
         */
        if ($me.val() == $next.data('pid')) {
            return;
        }
        setPcaDetail();
        $me.nextAll().remove();
        add_select($me.val());
        
        $("#areaItem-msg").html("");
        
    });

    function add_select(pid) {
        var area_names = areaItem['name'+pid];
        if (!area_names) {
            return false;
        }
        
        var areaIndex = $('#areaItem select').length+1;
        if(areaIndex==4){
	    	return;
	    }
        
        var area_codes = areaItem['code'+pid];
        
        var $select = $('<select>');
        $select.attr('id', 'area_item_'+areaIndex);
        $select.data('pid', pid);
        //$select.attachEvent('onchange',mychange);//添加事件
        
        if (area_codes[0] != -1) {
            area_names.unshift('请选择');
            area_codes.unshift(-1);
        }
        for (var idx=0;idx< area_codes.length;idx++) {
            var $option = $('<option>');
            $option.attr('value', area_codes[idx]);
            $option.text(area_names[idx]);
            $select.append($option);
        }
        $('#areaItem').append($select);
        
    };
    
    function setPcaDetail(){
    	var pca ='';
    	$('#province').val("");
    	$('#city').val("");
    	$('#area').val("");
    	$('#town').val("");
    	$('#provinceAddress').val("");
    	$('#cityAddress').val("");
    	$('#areaAddress').val("");
    	$('#townAddress').val("");
    	$('#areaItem select').each(function(){
    		var _me = $(this);
    		if(_me.val()!="-1" ){
    			pca +=_me.find("option:selected").text()+" ";
    			if(_me.attr("id")=="area_item_1"){
    				$('#province').val(_me.val());
    				$("#provinceAddress").val(_me.find("option:selected").text());
    			}else if(_me.attr("id")=="area_item_2"){
    				$('#city').val(_me.val());
    				$("#cityAddress").val(_me.find("option:selected").text());
    			}else if(_me.attr("id")=="area_item_3"){
    				$('#area').val(_me.val());
    				$("#areaAddress").val(_me.find("option:selected").text());
    			}else if(_me.attr("id")=="area_item_4"){
    				$('#town').val(_me.val());
    				$("#townAddress").val(_me.find("option:selected").text());
    			}
    		}
    	});
    	$('#pcadetail').val(pca);
    }
    
    $("#submit_saveButton").click(function(){
     $('#form').isValid(function(v){
	     if(!v){return false;}
	     var _thisFlag=true;
	      $('#areaItem select').each(function(){
    		var _me = $(this);
    		if(_me.val()=="-1" && _me.attr('id')!="area_item_3"){
				layer.msg("所在地区不能为空！");
				_thisFlag = false;
         		return;
    		}
    	});
    	if(!_thisFlag){
    	return false;
    	}
    	$('#phone').val($('#mobile').val());
    	 $.ajax({
	 	    url:'${basepath}/account/saveAddress',
			data:$('#form').serialize(),
			type:"POST",
			success:function(data){
				layer.msg("保存成功，请设置默认收货地址。");				 
			    setTimeout(function(){location.reload()}, 200 );
			}
	 	});
     });
	})
    
    initPCA();
	
});

function initPCA(){
    	var p = $('#province').val(); var c = $('#city').val(); var a = $('#area').val(); //var t = $('#town').val();
    	if(p!=""){
    		var op = $('#area_item_1').find("option[value='"+p+"']");
    		if(op){
    			op.attr("selected",true);
    			$('#area_item_1').change();
    			
    			op = $('#area_item_2').find("option[value='"+c+"']");
    			if(op){
    				op.attr("selected",true);
    				$('#area_item_2').change();
    				
    				op = $('#area_item_3').find("option[value='"+a+"']");
	    			if(op){
	    				op.attr("selected",true);
	    				$('#area_item_3').change();	    				
	    			}
    			}
    		}
    		
    	}
    }

$(function() {
	$("input[name=setDefaultRadio]").click(function(){
		$.ajax({
		  type: 'POST',
		  url: "setAddressDefault",
		  data: {id :$(this).val() },
		  success: function(data){
			  layer.msg("修改默认地址成功！");
		  },
		  dataType: "json",
		  error:function(){
			layer.msg("操作失败，请联系管理员或更换浏览器再试!");				  
		  }
		});
	});
});
function deletes(){
	return confirm("确定删除选择的记录?");	 
}
function update(id){
	//先刷新页面在弹出添加框
	$.ajax({
		type: "post",
	   	data:{ id: id },
       	url :"${basepath}/account/updateAddress",
       	dataType:'json',
        success : function (data){
 			$("#addsForm").show(); $("#tables").hide();
 			$("input[name=name]").val(data.name);
	        $("input[name=id]").val(data.id);
	        $("input[name=mobile]").val(data.mobile);
	        $("input[name=zip]").val(data.zip);
            $("input[name=phone]").val(data.phone);
            $("input[name=province]").val(data.province);
            $("input[name=city]").val(data.city);
            $("input[name=town]").val(data.town);
            $("input[name=area]").val(data.area);
            $("input[name=provinceAddress]").val(data.provinceAddress);
            $("input[name=cityAddress]").val(data.cityAddress);
            $("input[name=areaAddress]").val(data.areaAddress);
            $("input[name=townAddress]").val(data.townAddress);
            $("input[name=address]").val(data.address);
            initPCA();
            $('#name').focus();
    	}
	});
};

function newAddress(){
$('#form')[0].reset();
$("#addsForm").show(); $("#tables").hide();
};

function returnInfo(){
$("#addsForm").hide(); $("#tables").show();
};

</script>
</@accountHtml.html>
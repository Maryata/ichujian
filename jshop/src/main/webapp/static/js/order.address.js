//订单确认页地址管理
var addressModel="a";//"o"
/*
$('body').on('change', '#areaItem select', function() {
    var $me = $(this);
    console.log("change----:"+$me.attr("id"));
    
    var $next = $me.next();
    if ($me.val() == $next.data('pid')) {
        return;
    }
    setPcaDetail();
    $me.nextAll().remove();
    add_select($me.val());
    
    $("#areaItem-msg").html("");
});
*/

function areaChange($me){
	var $me = $($me);
    console.log("change----:"+$me.attr("id") +" v:"+$me.val());
    var $next = $me.next();
    if ($me.val() == $next.data('pid')) {
        return;
    }
    setPcaDetail();
    $me.nextAll().remove();
    add_select($me.val());
    
    $("#areaItem-msg").html("");
}

function add_select(pid) {
	//console.log("add_select----:"+pid);
    var area_names = areaItem['name'+pid];
    if (!area_names) {
        return false;
    }
    //console.log("---:"+pid);
    var areaIndex = $('#areaItem select').length+1;
    
    if(areaIndex==4){
    	return;
    }
    
    var area_codes = areaItem['code'+pid];
    var $select = $('<select>');
    $select.attr('id', 'area_item_'+areaIndex);
    $select.data('pid', pid);
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
    //console.log("-new--:"+pid);
    $('#areaItem').append($select);
    $('#area_item_'+areaIndex).change(function(){ areaChange($(this));});
    //console.log("-add end--:"+pid);
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

function initPCA(){
	var p = $('#province').val(); var c = $('#city').val(); var a = $('#area').val(); 
	//var t = $('#town').val();
	//console.log("p:"+p +" ,c:"+c+" ,a:"+a+" ,t:"+t);
	if(p){
		var op;
		op = $('#area_item_1').find('option[selected="selected"]');if(op)op.removeAttr("selected");
		op = $('#area_item_1').find("option[value='"+p+"']");
		$('#area_item_1').val(p);
		if(op){
			op.attr("selected",true);
			$('#area_item_1').change();
			
			op = $('#area_item_2').find("option[value='"+c+"']");
			$('#area_item_2').val(c);
			if(op){
				op.attr("selected",true);
				$('#area_item_2').change();
				
				op = $('#area_item_3').find("option[value='"+a+"']");
				$('#area_item_3').val(a);
    			if(op){
    				op.attr("selected",true);
    				$('#area_item_3').change();
    				
    				/*op = $('#area_item_4').find("option[value='"+t+"']");
    				$('#area_item_4').val(t);
	    			if(op){
	    				op.attr("selected",true);
	    				$('#area_item_4').change();
	    			}*/
    			}
			}
		}
		
	}
}
	function loadAddress2Temp(data,callback){
		var gettpl = document.getElementById('tpl_addresss').innerHTML;
		laytpl(gettpl).render(data, function(html){
		    document.getElementById('pageview').innerHTML = html;
		    if(callback){
		    	callback();	
		    }
		});
		
	}
function loadAddress(callback){
	$.getJSON(basepath+'/account/addressAjax', {}, function(data){
		var gettpl = document.getElementById('tpl_addresss').innerHTML;
		laytpl(gettpl).render(data, function(html){
		    document.getElementById('pageview').innerHTML = html;
		    if(callback){
		    	callback();	
		    }
		});
		addressData();
    });
}

$(".checkAddressBtn").click(function(){
	if($('#checkAddressBtn').hasClass('disabled')) return;
  	$('#checkAddressBtn').addClass('disabled');
  	setTimeout("$('#checkAddressBtn').removeClass('disabled');",1000);
	loadAddress();
});

function addressData(){
	$('#addressform')[0].reset();
    addressModel = "a";
	if($("#pageview tr").length==1){//如果table中有数据
    	 $("#tables").hide();
    	 $("#adds").hide();
    	 $("#hr").hide();
    }
	if($("#pageview tr").length>1){//如果table中有数据
	    $("#addressform").hide();
	    $("#hr").hide();
    }
    $("#submit_saveButton").html("完成");
	$("#submit_msg").hide();
    layer.open({
    	title: '更改收货地址',
	    type: 1,
	    //scrollbar :false,
	    skin: 'layui-layer-rim', //加上边框
	    area: ['850px',"540px"],
	    content: $('#edit-address')
	});
}

function addressEdit(id){
	 $("#addressform").show();
	 $("#hr").show();
	 $("#adds").hide();
	 
	//layer.msg("edit");
	//$('#addressform')[0].reset();
	//$('#addressform').validator( "cleanUp" );
	var _currRow = $("#address-"+id);// $(obj).parent("td").parent("tr");// 当前行
	$('#id').val(_currRow.attr("data-id"));    	
	$('#province').setVal(_currRow.attr("data-province"));
	$('#city').setVal(_currRow.attr("data-city"));
	$('#area').setVal(_currRow.attr("data-area"));
	//$('#town').setVal(_currRow.attr("data-town"));
	$('#pcadetail').val(_currRow.attr("data-pcadetail"));
	$('#address').val(_currRow.attr("data-address"));
	$('#name').val(_currRow.attr("data-name"));
	$('#phone').val(_currRow.attr("data-phone"));
	$('#mobile').val(_currRow.attr("data-mobile"));
	$('#zip').val(_currRow.attr("data-zip"));
	$("#provinceAddress").val(_currRow.attr("data-provinceAddress"));
	$("#cityAddress").val(_currRow.attr("data-cityAddress"));
	$("#areaAddress").val(_currRow.attr("data-areaAddress"));
	$("#townAddress").val(_currRow.attr("data-townAddress"));
	initPCA();
	$('#name').focus();
}
function addressDelete(id){
	if(confirm("确定删除选择的记录?")){
		 if($("#add-info tr").length==2){//如果table中有数据
	    	 $("#tables").hide();
	    	 $("#adds").hide();
	    	 $("#hr").hide();
	    	 $("#addressform").show();
	    }
		 if($("#add-info tr").length==1){//如果table中有数据
			 $("#addressform").show();
	    }
		$.ajax({    
	        type:'post',        
	        url:basepath+'/account/deleteAddressAjax',    
	        data:{id:id},    
	        dataType:'json',    
	        success:function(data){
	           $('#order_address').html("<tr><td colspan='2'>请选择地址</td></tr>");
			   $('input[name=selectAddressID]').val("");
			   $('#addressform')[0].reset();
	           loadAddress2Temp(data);
	           layer.msg("删除成功！");
	        }    
	    });
		
	}  
}

function setDefaultRadio(id){
	var _currRow = $("#address-"+id);//var _currRow = $(obj).parent("td").parent("tr");// 当前行
	var address ='<tr><td class="title2"  width="100">收货人姓名</td><td align="left" id="address_name">'+_currRow.attr("data-name")+'</td></tr>';
	address +='<tr><td class="title2"  width="100">联系电话</td><td align="left" id="address_phone">'+_currRow.attr("data-mobile")+'</td></tr>';
	address +='<tr><td class="title2"  width="100">收货地址</td><td align="left" id="address_address">'+_currRow.attr("data-pcadetail")+' '+_currRow.attr("data-address")+'</td></tr>';
	$('#order_address').html(address);
	$('input[name=selectAddressID]').val(_currRow.attr("data-id"));
	//$('input[name=addressType]').val("address");
	if(addressModel=="o"){
		addressEdit(id);
	}else{
		$.ajax({
			  type: 'POST',
			  url: basepath+"/account/setAddressDefault",
			  data: {id :id},
			  success: function(data){
				  layer.msg("成功选择收货地址。",{time:1000});
				  layer.closeAll('page'); //关闭所有页面层
			  },
			  dataType: "json",
			  error:function(){
				layer.msg("操作失败，请联系管理员或更换浏览器再试!");				  
			  }
			});
	}
}
//
(function($, w, d){


//接收表单验证通过的事件
$('#addressform').bind('valid.form', function(){
	var _thisform = $(this);
    var isV = true;
    $('#areaItem select').each(function(){
		var _me = $(this);
		if(_me.val()=="-1" && _me.attr('id')!="area_item_3"){
			$("#areaItem-msg").html("请选择所在地区");
			isV = false;
     		return false;
		}
	});
	if(!isV){
		return false;
	}
	
	$('#phone').val($('#mobile').val());
	//var address =$('#address').val();
	//var mobil =$('#mobile').val();
	 //var patrn=/^1([3]\d|4[57]|5[^3]|8\d)\d{8}$/; 
    // if(!patrn.test(mobil)){
	   // layer.alert("手机号不正确!");
	   // return false;
	   // }
      //if(mobile==""){
		//    layer.alert("请输入收货人电话!");
	//	    return false;
	 //} 
     /*if(address.trim()==""){
    	 layer.alert("详细地址不能为空!");
		 return false;    	  
     }*/
     
	var layIndex = layer.load(0, {content: '保存中',time: 30*1000});
    $.ajax({
        type:'post',        
        url:basepath+'/account/saveAddressAjax',    
        data:_thisform.serialize(),
        dataType:'json',
        success:function(data){
        	loadAddress2Temp(data);
        	
        	$("#tables").show();
           	$("#adds").show();
           	$("#addressform").hide();
           	$("#hr").hide();
           	
           if(addressModel=="o"){
             //layer.closeAll('page'); //关闭所有页面层
           	 if(data.addressId){
           	 	setDefaultRadio(data.addressId);
           	 	
           	 	var otherRequirement = $("input[name=otherRequirement]");
           	 	if(otherRequirement.val()==otherRequirement.attr('defval')){
           	 		otherRequirement.val("");
           	 	}
	           	
           	 	$("#submitOrderForm").submit();//确认提交
           	 	layer.close(layIndex);
           	 }
           }else{
	           layer.close(layIndex);
	           // 
	           //layer.msg(0, {content: '保存成功，请设选择收货地址。',time: 30*1000});
	           layer.close();
	           _thisform[0].reset();
	           $('#area_item_1').val(0);
	           $('#area_item_1').change();
           }
           layer.msg("保存成功，请选择收货地址。",{time:1000});	 
        }
    });
});	

add_select(0);	
}(jQuery, window, document));

/*$(function() {
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
});*/
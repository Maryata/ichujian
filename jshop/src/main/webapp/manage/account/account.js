var accPriceTempId = "price_item_";
var MODEL_LIST = [];
$("#discount").blur(function(){
	var disObj = $(this);
	var thisDis =parseInt(disObj.val());
	var isSys=$("#isSysAccount").val();
	var isDis = parseInt(disObj.attr("data-user-discount"));//阿智
	var isNotDis = parseInt(disObj.attr("data-discount"));//非阿智
	console.log(thisDis + ", isDis:"+isDis+" , isNotDis:"+isNotDis);
	//产品线
    var isD = 0, isNotD = 0;
	 $("input[name=pline]:checked").each(function(){
        if($(this).val().substring(0,1)=="D"){
        	isD++;
        }else{
        	isNotD++;
        }
    });
   if(isD==0 && isNotD ==0){
	      alert("请勾选产品线。");
	      return false;
	  }
   if(isSys=="0"){
	   if(isD>0 && isNotD >0){
		      alert("勾选阿智产品线时非阿智产品线不能勾选");
		      return false;
		}  
   }
   
    /*if(isD>0){//设置阿智
    	if(isDis < thisDis){
    	  	alert("设置阿智最大折扣率权限为："+isDis+"% ,请修改折扣率");
    	  	return false;
    	}
     }else if(isNotD >0){//设置非阿智
    	 if(isNotDis < thisDis){
    		 alert("设置非阿智最大折扣率权限为："+isNotDis+"%,请修改折扣率");
    		return false; 
     	}
     }*/
});
function selectPline(){//选择  显示产品线内容
	if($("#activeCode").val()==0){//不是配件用户
	    $(".pline_0").show();
	    $(".pline_1").hide();
    	$(".pline_1 input[name=pline]").removeAttr("checked");
    	$("#_hr").show();
    	$("#_span_is").show();
    	$("#_span_not").html("非阿智：");
	}else{
		$(".pline_0").hide();
	    $(".pline_1").show();
	    $(".pline_0 input[name=pline]").removeAttr("checked");
	    $(".pline_1 input[name=pline]").prop("checked",'true');//全选
	    $("#_hr").hide();
	    $("#_span_is").hide();
	    $("#_span_not").html("配件：");
	}
}
function selectPline_(){//选择  显示产品线内容
	  if($("#activeCode").val()==0){
		  alert("请设置会员价和产品线");
	  }else{
		  if(confirm("切换将会重置会员价，确认切换吗？")){
			   MODEL_LIST=[];
			   $(".tb_detail").html("");
			   $("#accPrice").val("");
		      }
	  }
		if($("#activeCode").val()==0){//不是配件用户
		    $(".pline_0").show();
		    $(".pline_1").hide();
	    	$(".pline_1 input[name=pline]").removeAttr("checked");
	    	$("#_hr").show();
	    	$("#_span_is").show();
	    	$("#_span_not").html("非阿智：");
		}else{
			$(".pline_0").hide();
		    $(".pline_1").show();
		    $(".pline_0 input[name=pline]").removeAttr("checked");
		    $(".pline_1 input[name=pline]").prop("checked",'true');//全选
		    $("#_hr").hide();
		    $("#_span_is").hide();
		    $("#_span_not").html("配件：");
		}
	  
	}
function isValetOrder(id){//代客下单  非代客下单 转换
	var isSysAccount=$("#isSysAccount").val();
	$.ajax({
 	    url:basepath+"/manage/account/valetOrder",
		data: {isSysAccount:isSysAccount==0?1:0,id:id},
		type:"POST",
		dataType:'json',
		success:function(data){
		   if(data.status=="1"){//成功
			   alert(data.msg);
			   $("#isSysAccount").val(data.isSys);
			   $(".pline_0 input[name=pline]").removeAttr("checked");
			    $(".pline_1 input[name=pline]").removeAttr("checked");
			    $(".tb_detail").html("");
			   if(data.isSys=="1"){
				   $("#valetOrder").html("转为非代客下单");valetacc
				   $("#valetacc").html("代客下单");
				   $(".plineLible").removeAttr("onclick");
			   }else{
				   $("#valetOrder").html("转为代客下单");
				   $("#valetacc").html("非代客下单");
				   $(".plineLible").attr("onclick","getPrice(this)");
			   }
			   $("#valetOrder").html();
			   location.reload();
			   
		   }else{
			   alert(data.msg);
		   } 
		}
 	});
}

function changeFPTT(){//选择服务费  改变发票抬头的必填
	var isServiceFee=$("#isServiceFee").val();//服务费  值
	//alert(isServiceFee);
	if(isServiceFee!="" && isServiceFee=="1"){//有服务费
		$("#_tt").html("*");
		$("#bankAccountName").attr("data-rule","开户发票抬头:length[2~30];");
	}else{//无服务费
		$("#_tt").html("");
	}
	
}

function getPrice(_this){//设置产品线价格
  var _this = $(_this);
  var id =accPriceTempId+_this.val();
  var price=_this.attr("data-price");
  if(_this.is(":checked")){
	  var model ={
			pline: _this.val(),
			oldprice:price,
			price_a:'',
			price_p:'',
			price_pl:'',
			vname:_this.attr("vname")
		};
		MODEL_LIST.push(model);
		var arr = price.split(',');
	  var _s="<tr id=\""+id+"\"><td >"+_this.attr("vname")+"</td>" +
	  		"<td >￥<input type='text' style='width:60px' class='price_a' id="+_this.val()+"/></td><td>￥"+arr[0]+"</td>" +
	  		"<td >￥<input type='text' style='width:60px' class='price_p' id="+_this.val()+"/></td><td>￥"+arr[1]+"</td>" +
	  		"<td >￥<input type='text' style='width:60px' class='price_pl' id="+_this.val()+"/></td><td>￥"+arr[2]+"</td>" +
			"</tr>";
	  $(".tb_detail").append(_s);
  }else{
	  $("#" + id).remove();
	  for(var i =0;i <MODEL_LIST.length;i++){  
	        var temp = MODEL_LIST[i];
	        if(_this.val()==temp.pline){
	    		MODEL_LIST.splice(i, 1);
	    		break;
	    	}	
		}
  }
}

function setAccPrices(){
	var data = [];
	for(var i =0;i <MODEL_LIST.length;i++){
		var temp = MODEL_LIST[i];
		var tid = "#"+accPriceTempId+temp.pline;
		var price_a= $(tid+" .price_a").val();
		var price_p= $(tid+" .price_p").val();
		var price_pl= $(tid+" .price_pl").val();
		var re=/^\+?(:?(:?\d+\.\d+)|(:?\d+))$/;
		if(parseFloat(price_a)=="0"){
			alert("会员价不能为0");
			$(tid+" .price_a").val("");
			$(tid+" .price_a").focus();
			return false;
		}
		if(parseFloat(price_p)=="0"){
			alert("会员价不能为0");	
			$(tid+" .price_p").val("");
			$(tid+" .price_p").focus();
			return false;
				}
		if(parseFloat(price_pl)=="0"){
			alert("会员价不能为0");
			$(tid+" .price_pl").val("");
			$(tid+" .price_pl").focus();
			return false;
		}
		if(price_a!=""){
			if(!price_a.match(re)){
				alert("只能输入整数或小数");
				return false;
				
			}
		}
		if(price_p !=""){
			if(!price_p.match(re)){
				alert("只能输入整数或小数");
				return false;
				
			}		
				}
		if(price_pl !=""){
			if(!price_pl.match(re)){
				alert("只能输入整数或小数");
				return false;
				
			}
		}
		
		var _false = checkPlines(price_a,price_p,price_pl,temp.vname,temp.pline);
		if(!_false){
			return false;
		}
		data.push({
			price_a : price_a,
			price_p : price_p, 
			price_pl : price_pl, 
			pline: temp.pline,
			vname: temp.vname
		     });
	}
	var priceStr = $.toJSON(data);
	$("#accPrice").val(priceStr);
	return true;
	
} 

function checkPlines(price_a,price_p,price_pl,vname,pline){//判断会员价值是否小于折后值
	var _false = true;
	var isDis = parseInt($("#discount").attr("data-user-discount"));//阿智
	var isNotDis = parseInt($("#discount").attr("data-discount"));//非阿智
	 var isD = 0, isNotD = 0;
	 $("input[name=pline]:checked").each(function(){
       if($(this).val().substring(0,1)=="D"){
       	isD++;
       }else{
       	isNotD++;
       }
   });
	 var old_id="pline_"+pline;
	 var arr=$("#"+old_id).attr("data-price").split(',');
	 //var arr = oldprice.split(',');
	 if(isD>0){//设置阿智
		if((arr[0]-isDis*arr[0]*0.01)>price_a && price_a != 0){
			alert(vname+"android最小会员价为"+(arr[0]-isDis*arr[0]*0.01)+"请重新设置!");
			_false=false;
		}
		if((arr[1]-isDis*arr[1]*0.01)>price_p && price_p  != 0){
			alert(vname+"iphone6s最小会员价为"+(arr[1]-isDis*arr[1]*0.01)+"请重新设置!");
			_false=false;
		}
		if((arr[2]-isDis*arr[2]*0.01)>price_pl && price_pl != 0){
			alert(vname+"iphone6 plus最小会员价为"+(arr[2]-isDis*arr[2]*0.01)+"请重新设置!");
			_false=false;
		}
		
	 }else if(isNotD >0){//设置非阿智
		 if((arr[0]-isNotDis*arr[0]*0.01)>price_a && price_a != 0){
				alert(vname+"android最小会员价为"+(arr[0]-isNotDis*arr[0]*0.01)+"请重新设置!");
				_false=false;
			}
			if((arr[1]-isNotDis*arr[1]*0.01)>price_p && price_p != 0){
				alert(vname+"iphone6s最小会员价为"+(arr[1]-isNotDis*arr[1]*0.01)+"请重新设置!");
				_false=false;
			}
			if((arr[2]-isNotDis*arr[2]*0.01)>price_pl && price_pl != 0){
				alert(vname+"iphone6 plus最小会员价为"+(arr[2]-isNotDis*arr[2]*0.01)+"请重新设置!");
				_false=false;
			} 
	 }
	 if(_false){
			return true;
		}else{
			return false;
		}
	
}

$(function(){//加载价格
	$("#ltds:first-child").children().attr("data-rule","经营性质:checked;");
	$("#productss:first-child").children().attr("data-rule","经销产品:checked;");
	$("#plines").children().attr("data-rule","产品线:checked;");
	selectPline();
	var accPrice=$("#accPrice").val();
	if(accPrice!=''){
		MODEL_LIST = $.parseJSON(accPrice);
		 for(var i=0;i<MODEL_LIST.length;i++){
			 var temp = MODEL_LIST[i];
			 var id =accPriceTempId+temp.pline;
			 var old_id="pline_"+temp.pline;
			 var arr=$("#"+old_id).attr("data-price").split(',');
			 //alert($("#"+old_id).attr("data-price"));
			 //var arr = temp.oldprice.split(',');
			 var _s="<tr id=\""+id+"\"><td >"+temp.vname+"</td>" +
		  		"<td >￥<input type='text' style='width:60px' class='price_a' id="+temp.pline+" value='"+temp.price_a+"'/></td><td>￥"+arr[0]+"</td>" +
		  		"<td >￥<input type='text' style='width:60px' class='price_p' id="+temp.pline+" value='"+temp.price_p+"'/></td><td>￥"+arr[1]+"</td>" +
		  		"<td >￥<input type='text' style='width:60px' class='price_pl' id="+temp.pline+" value='"+temp.price_pl+"'/></td><td>￥"+arr[2]+"</td>" +
				"</tr>";
		  $(".tb_detail").append(_s);
		};
	}
	if($("#activeCode").val()=='0' && $("#isSysAccount").val()=="0"){
		//--------
		$("input[name=pline]:checked").each(function(){
			var _this = $(this);
			var isHas = false;
			for(var i =0;i <MODEL_LIST.length;i++){  
		        var temp = MODEL_LIST[i];
		        if(_this.val()==temp.pline){
		        	isHas = true;
		        	break;
		    	}	
			}
			if(!isHas){
				getPrice(_this);
			}
		});
	}
	
});

/*$("#price_a").blur(function(){
	var _this = $(_this);
	alert(_this.val());
	
});*/




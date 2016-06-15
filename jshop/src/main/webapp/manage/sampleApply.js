

function checkApplyNum(){
	
	var selectData = {};
	var selectIndex = {};
	var selectCount = 0;
	var selectModelNum = 0;
	var isNoCheck = false;
	$("#tb_detail tr").each(function(i){
		// 不处理表头
		if(i==0){
			return true;
		}
		var _this = $(this);
		
		var obj = _this.find("input[name=plineNames]");
		if(obj.val()==''){
			layer.alert("指电品牌不能为空！");
			obj.focus();
			isNoCheck = true;
			return;
		}
		obj = _this.find("input[name=brandNames]");
		if(obj.val()==''){
			layer.alert("手机品牌不能为空！");
			obj.focus();
			isNoCheck = true;
			return;
		}
		obj = _this.find("input[name=modelNames]");
		if(obj.val()==''){
			layer.alert("手机型号不能为空！");
			obj.focus();
			isNoCheck = true;
			return;
		}		
		
		var code = _this.find("input[name=plineCodes]").val()+_this.find("input[name=code]").val();//产品线+机型
		var num = parseInt(_this.find("select[name=productNums]").val());				
		
		selectIndex[code] = i;
		if(!selectData[code]){
			selectData[code] = num;
		}else{
			selectData[code] += num;
		}
		selectCount += num;
		selectModelNum++;
		if(selectData[code]>2){
			layer.alert("每款机型最多2张样品？样品"+i+"重新选择或取消");
			isNoCheck = true;
			return;
		}
	});
	if(isNoCheck){
		return;
	}
	
	if(selectCount>6){
		layer.alert("一个用户最多申请6片");
		return;
	};
	
	$.ajax({
 	    url:basepath+'/manage/sampleApply/checkApplyNum',
		data:$('#form').serialize(),
		type:"POST",
		dataType:'json',
		success:function(data){
			var list = data.list;
			var count = 0;
			//var applayMap={};
			$.each(list,function(i,item){
				var num = parseInt(item.productNum);
				var code = item.productCode;
				count += num;
				//applayMap[code] = num;
				
				if(!selectData[code]){					
					selectModelNum++;
					
				}else{//比对每款机型数量;
					if(selectData[code] > (2-num) ){
						layer.alert("样品"+selectIndex[code]+"超出最大数量,最多还能申请"+(2-num)+"张");
						isNoCheck = true;
						return;
					}
				}				
			});
			if(isNoCheck){
				return;
			}
			
			if(count>=6){
				layer.alert("当前用户申请样品已达上限");
				return;
			}
			if(selectModelNum>3){
				layer.alert("当前用户还能申请"+( 3-list.length )+"种新机型样品,请删除列表多余的样品");
				return;
			}
			if((6-count)<selectCount){
				layer.alert("当前用户最多还能申请"+(6-count));
				return;
			}
			addSampleApplyFun();
			
		}
 	});
};


function addSampleApplyFun(){
	layer.load(0, {shade: [0.1,'#fff']});
	$.ajax({
	    url:basepath+'/manage/sampleApply/addSampleApply',
		data:$('#form').serialize(),
		type:"POST",
		dataType:'json',
		success:function(data){
			layer.closeAll();
			if(data.msg){
				layer.msg(data.msg);
				$("#productList").empty();
				
				$("#tb_detail tr").each(function(i){//清除
					// 不处理表头
					if(i==0){
						return true;
					}
					var _this = $(this);
					_this.remove();
				 });
				judgeSampleNumber(function(data){
					layer.alert("当前用户已申请样品总数量为"+data+"张");
					$("#sampleNumber").text(data);
					$("#titles").show();
					checkMessageInfo(function(data){
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
					});
				});
				
			}
			
		}
	});
};
//显示已添加的数量
function judgeSampleNumber(callback){
	$.ajax({//判断已推送的数量
	    url:basepath+'/manage/sampleApply/judgeSampleNumber',
	type:"POST",
	dataType:'json',
	data: {accountId :$("#customerId").val()},
	success:function(data){
		callback.call(this,data);
	 }
});
}
function checkMessageInfo(callback){
	$.ajax({
		url:basepath+'/manage/sampleApply/checkMessage',
		type:"POST",
		dataType:'json',
		data: {customerId :$("#customerId").val()},
		success:function(data){
		   callback.call(this,data);
			  }
		  });
}



(function($){

    $.load_sampleapply_stock = function(thisBtn,id) {
    	//alert($(document).height());
    	var qtyCount = 0;
    	var data={id:id};
		var index = layer.open({
	    	title: "核对库存",
		    type: 1,
		    //skin: 'layui-layer-rim',
		    area: [$(document).height()+'px',$(document).width()+"px"],
		    content: $('#detailsPage'),
		    btn:["审核通过","取消"],
		    success: function(){
		    	var load_index = layer.load();
		        $.ajax({
		        	url:basepath+'/manage/sampleApply/get_sampleapply_stock',
		            type: "post",
		            dataType: "json",
		            data:data,
		            success: function(d) {
		            	$("#detailsList").empty();
				    	$.each(d.list,function(i,item){
				    		var qtyRed = "";
				    		//item.number = 1000;
				    		if(item.number > item.salable_qty ){
				    			qtyRed = "style='color: #F00;' title='超出预售库存'";
				    			qtyCount ++;
				    		}
				    		var tr = "<tr "+qtyRed+" >";
				    		tr +="<td nowrap='nowrap'>"+(i+1)+"</td>";
				    		tr +="<td nowrap='nowrap'>"+item.productCode+"</td>";
				    		tr +="<td nowrap='nowrap'>"+item.productName+" "+item.productCode+"</td>";
				    		tr +="<td nowrap='nowrap'>"+item.productNum+"</td>";
				    		tr +="<td nowrap='nowrap' >"+item.salable_qty+"</td>";
				    		tr +="</tr>";
		        			$("#detailsList").append(tr);
				    	});	
				    	layer.close(load_index);
		            }
		        });
		    				    	
		    },yes:function(){
		    	if(qtyCount>0){
		    		alert("当前订单有"+qtyCount+"种产品可销售库存缺货");
		    		return false;
		    	}
		    	$.ajax({
		        	url:basepath+"/manage/sampleApply/toEdit",
		        	type: "post",
		            dataType: "json",
		            data:data,
		            success: function(d){
		            	if(d.status=="1"){
		            		location.reload();
		            	}
		            	//
		            }
		    	});
		    }
	   });
	   $('#detailsPage').height($(document).height()-400+"px");
	   layer.full(index);
    };    
    
}(jQuery));





(function($){

    //load_order_stock
    $.load_order_stock = function(thisBtn,orderId) {
    	var qtyCount = 0;
		var index = layer.open({
	    	title: "核对库存",
		    type: 1,
		    //skin: 'layui-layer-rim',
		    area: [$(document).height()+'px',$(document).width()+"px"],
		    content: $('#detailsPage'),
		    btn:["审核通过","取消"],
		    success: function(){
		    	var load_index = layer.load();
		    	var data={orderId:orderId};
		        $.ajax({
		            url: basepath+"/manage/order/get_order_stock",
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
				    		tr +="<td nowrap='nowrap'>"+item.pcode+"</td>";
				    		tr +="<td nowrap='nowrap'>"+item.productName+" "+item.pline+"</td>";
				    		tr +="<td nowrap='nowrap'>"+item.number+"</td>";
				    		tr +="<td nowrap='nowrap' >"+item.salable_qty+"</td>";
				    		tr +="</tr>";
		        			$("#detailsList").append(tr);
				    	});	
				    	layer.close(load_index);
		            }
		        });
		    				    	
		    },yes:function(){
		    	/*//@20160229 修改：超出预售库存也可以推送;
		    	 * if(qtyCount>0){
		    		alert("当前订单有"+qtyCount+"种产品可销售库存缺货");
		    		return false;
		    	}*/
		    	createMark();
		    	$("#form").attr("action",$(thisBtn).attr("method1")).submit();
		    }
	   });
	   $('#detailsPage').height($(document).height()-400+"px");
	   layer.full(index);
    };
    $.loadPayInfo = function(){
    	var orderId=$("#orderId").val();
    	var data={orderId:orderId};
        $.ajax({
            url: basepath+"/manage/order/get_order_payInfo",
            type: "post",
            dataType: "json",
            data:data,
            success: function(d) {
            	var payList = d.list;
            	var _data;
            	if(payList.length>0){
            		$("#isHidden").show();
            		$.each(payList,function(i,a){
            			if(a.paystatus=="y"){
            				_data+="<tr><td>"+a.payType+"</td><td>"+a.createtime+"</td><td>已支付</td></tr>";
            			}else{
            				_data+="<tr><td>"+a.payType+"</td><td>"+a.createtime+"</td><td>未支付</td></tr>";
            			}
            			
            		});
            		$("#payType").html(_data);
            	}
            	
            }
        });
    };
    
    $.loadPayInfo();
    
}(jQuery));


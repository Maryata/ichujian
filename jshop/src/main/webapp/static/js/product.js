/**
 * 商品javascript，对购买商品的一些控制和提示
 * 
 */
var msgcolor = '#red';//'#78BA32'
 
function changeBuyNum(obj,flag){
	var currNum = $(obj).parent().find("input[name=buyNum]");
	var _currNum = currNum.val();
	if(flag){
		_currNum++;
		currNum.val(_currNum);
	}else{
		if(_currNum<=1){
			return false;
		}
		_currNum--;
		currNum.val(_currNum);
	}
	checkMinNumFun(currNum);
};

//检查最小下单量
function checkMinNumFun(_obj){
	var minNum = parseInt(_obj.attr("minNum"));
	var maxNum = 10000000;
	var _v = _obj.val().replace(/\D/g,'');
	if(isNaN(_v) || isNaN( parseInt(_v)) || parseInt(_v)<minNum ){
		layer.tips('当前商品最小起订量：'+minNum, _obj,{tips: [4, msgcolor]});
		_obj.val(minNum);
	}else if(parseInt(_v)>maxNum ){
		layer.tips('当前商品最大起订量：'+maxNum, _obj,{tips: [4, msgcolor]});
		_obj.val(maxNum);
	}
}

function load_addcar_list(){
$(".addcar_list").click(function(event){
	if(!isLogin0()){ return false;};
	var _this = $(this);
	var _numObj = _this.parent("p").parent("span").find("input[name=buyNum]");
	var _data = {
		productID:_this.attr("data-itemid"),
		buyCount:_numObj.val()
	};

	$.ajax({
	  type: 'POST',
	  url: basepath+"/cart/addToCart",
	  data: _data,
	  timeout:60000,
	  success: function(data){
		//console.log("data="+data);
		if(data=="1"){
			openLogin();
		 	//console.log("提示用户需要登录！");
		 	//layer.msg('需要先登录，才能加入购物车！');
		}else if(data.code && data.code=="result"){
			G_CALLBACK_LOGIN = null;
			$("#cartQuantity").html(data.quantity).show();
			$("#cartQuantityLogo").html(data.quantity).show();
			_this.attr("disabled",true).unbind('click');
        	//layer.tips('<span style="font-size: 20px;text-align: center;">添加购物车成功</span>', $("#cartQuantity"),{shift:4,area: ['160px', '35px'],  time: 1000,tips: [4, '#red']});
        	//待处理好后使用;
        	var offset = $("#cartico").offset();
	        var img = _this.parent("p").parent("span").parent("a").find('img').attr('src');
	        var flyer = $('<img class="u-flyer" src="' + img + '">');
	        flyer.fly({
	            start: {
	                left: event.pageX,
                    top: event.pageY - document.body.scrollTop
	            },
	            end: {
	                left: offset.left + 50,
                    top: window.screen.availHeight - 260,
	                width: 0,
	                height: 0
	            },
	            onEnd: function () {
	                this.destory();
	            }
	        });
        
		}else{
			console.log("data.tips="+data.tips);
			if(data.id=="account"){
				data.tips += " <a href='"+basepath+"/account/account'>立即补全资料</a>";
			}
			layer.alert(data.tips);
		}
	  },
	  dataType: "json",
	  error:function(e){
		  console.log("加入购物车失败！请联系站点管理员。异常:"+e);
		  //layer.alert("加入购物车失败！请联系客服寻求解决办法。");
	  }
	});
});
}


(function($, w, d){
//添加到购物车
load_addcar_list();
//左侧新品列表加入购物车
$(".addcar_left").click(function(event){
	if(!isLogin0()){ return false;};
	var _this = $(this);
	var _numObj = _this.parent("p").parent("div").find("input[name=buyNum]");
	var _data = {
		productID:_this.attr("data-itemid"),
		buyCount:_numObj.val()
	};
	if(!_data.buyCount){
		_data.buyCount = 1;
	}

	$.ajax({
	  type: 'POST',
	  url: basepath+"/cart/addToCart",
	  data: _data,
	  timeout:60000,
	  success: function(data){
		//console.log("data="+data);
		if(data=="1"){
			openLogin();
		 	//console.log("提示用户需要登录！");
		 	//layer.msg('需要先登录，才能加入购物车！');
			/*layer.open({
			    content: '需要先登录，才能加入购物车!',
			    btn: ['立即登录', '取消'],
			    shadeClose: false,
			    yes: function(){
			    	tourl(basepath+'/account/login');
			    }, no: function(){
			    }
			 });*/
		}else if(data.code && data.code=="result"){
			G_CALLBACK_LOGIN = null;
			$("#cartQuantity").html(data.quantity).show();
			$("#cartQuantityLogo").html(data.quantity).show();
			_this.attr("disabled",true).unbind('click');
        	//layer.tips('<span style="font-size: 20px;text-align: center;">添加购物车成功</span>', $("#cartQuantity"),{shift:4,area: ['160px', '35px'],  time: 1000,tips: [4, '#red']});
        	
			if(!$("#cartico")){
				return;
			}
			if(typeof(refresh_win) == "undefined"){	        	
				//待处理好后使用;
	        	var offset = $("#cartico").offset();
		        var img = _this.parent("p").parent("div").parent("div").find('.p-img').attr('src');
		        var flyer = $('<img class="u-flyer" src="' + img + '">');
		        flyer.fly({
		            start: {
		                left: event.pageX,
	                    top: event.pageY - document.body.scrollTop-500
		            },
		            end: {
		                left: offset.left + 50,
	                    top: window.screen.availHeight - 300,
		                width: 0,
		                height: 0
		            },
		            onEnd: function () {
		                this.destory();
		            }
		        });
	        
	        }else{
	        	location.reload();
	        }
        
		}else{
			console.log("data.tips="+data.tips);
			if(data.id=="account"){
				data.tips += " <a href='"+basepath+"/account/account'>立即补全资料</a>";
			}
			layer.alert(data.tips);
		}
	  },
	  dataType: "json",
	  error:function(e){
		  console.log("加入购物车失败！请联系站点管理员。异常:"+e);
		  //layer.alert("加入购物车失败！请联系客服寻求解决办法。");
	  }
	});
	
});

//首页新品加入购物车
$(".addcar_index").click(function(event){
	if(!isLogin0()){ return false;};
	var _this = $(this);
	var _numObj = _this.parent("p").parent("span").find("input[name=buyNum]");
	var _data = {
		productID:_this.attr("data-itemid"),
		buyCount:_numObj.val()		
	};
	if(!_data.buyCount){
		_data.buyCount = 1;
	}
	
	$.ajax({
	  type: 'POST',
	  url: basepath+"/cart/addToCart",
	  data: _data,
	  timeout:60000,
	  success: function(data){
		//console.log("data="+data);
		if(data=="1"){
			openLogin();
		 	//console.log("提示用户需要登录！");
		 	//layer.msg('需要先登录，才能加入购物车！');
		}else if(data.code && data.code=="result"){
			G_CALLBACK_LOGIN = null;
			$("#cartQuantity").html(data.quantity).show();
			$("#cartQuantityLogo").html(data.quantity).show();
			_this.attr("disabled",true).unbind('click');
        	//layer.tips('<span style="font-size: 20px;text-align: center;">添加购物车成功</span>', $("#cartQuantity"),{shift:4,area: ['160px', '35px'],  time: 1000,tips: [4, '#red']});
        	
			if(!$("#cartico")){
				return;
			}
			
			//待处理好后使用;
        	var offset = $("#cartico").offset();
	        var img = _this.parent("p").parent("span").parent("a").find('.p-img').attr('src');
	        var flyer = $('<img class="u-flyer" src="' + img + '">');
	        flyer.fly({
	            start: {
	                left: event.pageX,
                    top: event.pageY - document.body.scrollTop
	            },
	            end: {
	                left: offset.left + 50,
                    top: window.screen.availHeight - 320,
	                width: 0,
	                height: 0
	            },
	            onEnd: function () {
	                this.destory();
	            }
	        });
        
		}else{
			console.log("data.tips="+data.tips);
			if(data.id=="account"){
				data.tips += " <a href='"+basepath+"/account/account'>立即补全资料</a>";
			}
			layer.alert(data.tips);
		}
	  },
	  dataType: "json",
	  error:function(e){
		  console.log("加入购物车失败！请联系站点管理员。异常:"+e);
		  //layer.alert("加入购物车失败！请联系客服寻求解决办法。");
	  }
	});
	
});


//键盘按下的时候对字符进行检查，只能是数字
$("input[name=buyNum]").keydown(function(event) {
	return isNumKey(event,$(this));
});
//键盘抬起来的时候对库存进行检查
$("input[name=buyNum]").keyup(function(event) {
	return isNumKey(event,$(this));
});
$("input[name=buyNum]").blur(function(){ checkMinNumFun($(this));});
$("input[name=buyNum]").change(function(){ checkMinNumFun($(this));});


$(window).scroll(function(){
    //alert($(window).scrollTop);
    if($(window).scrollTop()>0){
        $(" .footer").addClass("fix");
    }else{
        $(" .footer").removeClass("fix");
    }
});

}(jQuery, window, document));

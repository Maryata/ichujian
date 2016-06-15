/**
 * 商品javascript，对购买商品的一些控制和提示 适配机型
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
		if(_currNum==1){
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

(function($, w, d){
//添加到购物车
$(".addcar").click(function(event){
	if(!isLogin0()){ return false};
	
	var _this = $(this);
	
	var _productId = _this.parents("a[class=itemdata]").attr("data-id");
	var _numObj = _this.parent().find("input[name=buyNum]");
	var _model = _this.parents("span[class=childdata]");
	var _main = _this.parents("div");
	
	var _data = {
		productID: _productId,
		buyCount:_numObj.val(),
		buySpecID:'',
		mainCatalogCode:_main.attr("data-code"),
		mainCatalogName:_main.attr("data-name"),
		childrenCatalogCode:_model.attr("data-code"),
		childrenCatalogName:_model.attr("data-name")
		
	};

	$.ajax({
	  type: 'POST',
	  url: basepath+"/cart/addToCart",
	  data: _data,
	  success: function(data){
		//console.log("data="+data);
		if(data=="1"){
		 	openLogin();
		 	//console.log("提示用户需要登录！");
		 	//layer.msg('需要先登录，才能加入购物车！');
		}else if(data.code && data.code=="result"){
			$("#cartQuantity").html(data.quantity).show();
			$("#cartQuantityLogo").html(data.quantity).show();
			
			_this.addClass("btn-no").unbind('click').val("已加入购物车");
		}else{
			console.log("出现错误。data.tips="+data.tips);
			layer.alert(data.tips);
		}
	  },
	  dataType: "json",
	  error:function(e){
		  console.log("加入购物车失败！请联系站点管理员。异常:"+e);
		  layer.alert("加入购物车失败！请联系客服寻求解决办法。");
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

}(jQuery, window, document));

var msgcolor = '#red';//'#78BA32'
var DIS_INFO=[];//	
var DIS_INFO_IS_LOAD = false;
var DIS_INFO_HTML="";

$(function(){
	// 页面加载时，选中所有checkbox
	$("[name=chkTable]").each(function(){
		$(this).prop("checked",true);
		checkAll($(this),false);
	});
});

// 每个table上的全选 all:load使用
function checkAll(obj,all){
	obj.attr("disabled","disabled");
	var _totalNum = 0;
	var _totalPrice = 0;
	// 获取obj所在的table
	var _tb = obj.parents("table");
	// table的id
	var _tbId = _tb.prop("id");
	// table下的所有checkbox
	var _ck = $("#"+_tbId+" input[type='checkbox']");
	if(obj.is(":checked")){
		// 选中table中的所有checkbox
		if(!all)_ck.prop("checked",true);
		// 计算总金额
		_tb.find("[name='rowPrice']").each(function(){
			_totalPrice += parseFloat($(this).text());
		});
		$("#"+_tbId+" [name='totalPrice']").text(roundDigit(_totalPrice));
		// 计算总数量
		
		_tb.find("[name='buyNum_cart']").each(function(){
			_totalNum += parseInt($(this).val());// 当前table购买总数
		});
		
		$("#"+_tbId+" [name='totalNum']").text(_totalNum);
	}else{
		_ck.prop("checked",false);
		_totalNum = 0;
	    _totalPrice = 0;
	    // 当前table总数、总金额置为0
	    _tb.find("[name='totalNum']").text(_totalNum);
	    _tb.find("[name='totalPrice']").text(_totalPrice);
	}
	setTotal();
	obj.removeAttr("disabled");
}

// 单行checkbox
function setRowNum(obj){
	var _currRow = $(obj).parent("span").parent("td").parent("tr");// 当前行
	var currNum = _currRow.find("[name='buyNum_cart']");// "当前行数量"节点
	var _currNum = parseInt(currNum.val());// 当前行数量
	
	var price = roundDigit(_currRow.find("[name='price']").text());// 当前行单价
	_currRow.find("[name='rowPrice']").text(roundDigit(price * _currNum));// 当前行金额小计
	
	// 修改当前table总金额
	var _table = _currRow.parents("table");// 当前table
	var _tbChecked = _table.find("input[name='chk']:checked").parents("tr");// 当前table选中的行
	var _totalPrice = 0;
	_tbChecked.find("[name='rowPrice']").each(function(){
		_totalPrice += parseFloat($(this).text());// 当前table总金额
	});
	_table.find("[name='totalPrice']").text(roundDigit(_totalPrice));
	
	// 修改当前table总数量
	var _tableNum = 0;
	_tbChecked.find("[name='buyNum_cart']").each(function(){
		_tableNum += parseInt($(this).val());// 当前table总数量
	});
	_table.find("[name='totalNum']").text(_tableNum);
	
	setTotal();
	return false;
}

// 点击，数量增加/减少
function changebuyNum_cart(obj,flag){
	// 修改当前行
	var _currRow = $(obj).parent("span").parent("td").parent("tr");// 当前行
	var currNum = _currRow.find("[name='buyNum_cart']");// "当前行数量"节点
	var _currNum = currNum.val();// 当前行数量
	if(flag){
		_currNum++;
		currNum.val(_currNum);
	}else{
		if(_currNum<=1){
			return false;
		}
		_currNum--;
		var minNum = parseInt(currNum.attr("minNum"));
		if(minNum>0 && parseInt(_currNum)<minNum ){
			layer.tips('当前商品最小起订量：'+minNum, currNum,{tips: [4, msgcolor]});
			//currNum.val(minNum);
			return false;
		}else{
			currNum.val(_currNum);
		}
	}
	checkMinNumFun_cart(currNum);
	return false;
}


// 单行checkbox
function rowCheck(obj){
	var obj = $(obj);
	obj.attr("disabled","disabled");
	
	var _checkedRow = obj.parent("td").parent("tr");
	var thisTable = _checkedRow.parent("tbody").parent("table");

	var _totalNum = 0,_totalPrice=0;
	thisTable.find("tbody tr").each(function(){
		var _tr = $(this);
		if(_tr.find("[name='chk']").is(":checked")){
			_totalPrice += parseFloat( _tr.find("[name='rowPrice']").text());
			_totalNum += parseInt( _tr.find("[name='buyNum_cart']").val());
		}
	});

	thisTable.find("[name='totalNum']").text(_totalNum);
	thisTable.find("[name='totalPrice']").text(roundDigit(_totalPrice));

	setTotal();
	
	var chCount = thisTable.find("input[name='chk']").length-thisTable.find("input[name='chk']:checked").length;
	if(chCount>0){
		thisTable.find("input[name='chkTable']").prop("checked",false);
	}else if(chCount==0){
		thisTable.find("input[name='chkTable']").prop("checked",true);
	}
	
	obj.removeAttr("disabled");
}
// 设置"已选数量"、"实付"
function setTotal(){
	// 修改"实付"金额
	var _cost = 0;
	$("[name='totalPrice']").each(function(){
		_cost += parseFloat($(this).text());
	});
	$("#totalCost").text(roundDigit(_cost));
	//--
	//var _rate= parseFloat($("#cart_rate").text());//折扣
	//if(_rate && _rate>0){
		_cost = _cost * 1;
	//}
	$("#cart_zhehou").text(roundDigit(_cost));
	
	
	// 修改"已选数量"
	var _num = 0;
	$("[name='totalNum']").each(function(){
		_num += parseInt($(this).text());
	});
	$("#allNum").text(_num);
	
}


//键盘按下的时候对字符进行检查，只能是数字
var keyNumTimmer;
$("input[name=buyNum_cart]").keydown(function(event) {
	clearTimeout(keyNumTimmer);
	var _this = $(this);
	var downFlag = isNumKey(event,_this);
	keyNumTimmer = setTimeout(function(){checkMinNumFun_cart(_this);},800);
	return downFlag;
	
});

//键盘抬起来的时候对库存进行检查
$("input[name=buyNum_cart]").keyup(function(event) {
	return isNumKey(event,$(this));
});

$("input[name=buyNum_cart]").blur(function(){checkMinNumFun_cart($(this));});
$("input[name=buyNum_cart]").change(function(){ checkMinNumFun_cart($(this));});

//检查最小下单量
function checkMinNumFun_cart(obj){
	var _obj = $(obj);
	var minNum = parseInt(_obj.attr("minNum"));
	var maxNum = 10000000;
	console.log(">>>checkMinNumFun_cart._obj.val()="+_obj.val());
	var _v = _obj.val().replace(/\D/g,'');
	if(isNaN(_v) || isNaN( parseInt(_v)) || parseInt(_v)<minNum ){
		layer.tips('当前商品最小起订量：'+minNum, _obj,{tips: [4, msgcolor]});
		_obj.val(minNum);
	}else if(parseInt(_v)>maxNum ){
		layer.tips('当前商品最大起订量：'+maxNum, _obj,{tips: [4, msgcolor]});
		_obj.val(maxNum);
	}
	setRowNum(_obj);
	//----
	var _data = {
		productID:_obj.attr("data-id"),
		buyCount:_obj.val(),
		isAdd : 'N'
		//buySpecID:'',
		//mainCatalogCode:_obj.attr("data-mainCatalogCode"),
		//childrenCatalogCode:_obj.attr("data-childrenCatalogCode"),
		//mainCatalogName:_obj.attr("data-mainCatalogName"),
		//childrenCatalogName:_obj.attr("data-childrenCatalogName")
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
		}else{
			console.log("data.tips="+data.tips);
			layer.alert(data.tips);
		}
	  },
	  dataType: "json",
	  timeout:60000,
	  error:function(e){
		  console.log("加入购物车失败！请联系站点管理员。异常:"+e);
	  }
	});
}

//购物车 提交
function readyFormOrder(flag){
    var _obj="";
	var _ids = "";
	var _nums = "";
	var success = true;
	var maxNum = 10000000;
	$("input[name='chk']:checked").each(function(){
		_ids += $(this).parents("tr").attr("data-id")+",";
		var _buyNum = $(this).parents("tr").find("[name='buyNum_cart']");
		var minNum = parseInt(_buyNum.attr("minNum"));
		var _v = _buyNum.val().replace(/\D/g,'');
		if(isNaN(_v) || isNaN( parseInt(_v)) || parseInt(_v)<minNum ){
			layer.tips('当前商品最小起订量：'+minNum, _obj,{tips: [4, msgcolor]});
			success = false;
			return false;
		}else if(parseInt(_v)>maxNum ){
			layer.tips('当前商品最大起订量：'+maxNum, _obj,{tips: [4, msgcolor]});
			success = false;
			return false;
		}
		_nums += _v+",";
	});
	if(!success){
		return false;
	}
	console.log(_nums);
	if(_ids==''){
		layer.alert("请选择要下单的商品!");
		return false;
	}
	_ids = _ids.substr(0,_ids.length-1);
	_nums = _nums.substr(0,_nums.length-1);
    $("#formReadyOrder :hidden[name=productIDs]").val(_ids);
	$("#formReadyOrder :hidden[name=buyCounts]").val(_nums);
	$(".choseAcc").attr("disabled",true);// 禁用“去下单”按钮
	if(flag=="1"){
		openAccWin();
		return false;
	}else{
		submitCart();
	}
}

function submitCart(sysflag){
	if(sysflag==1){
		var checkedAcc = $("#accTable").find("input:radio:checked");
		if(checkedAcc.length==0){
			layer.msg("请选择代下单的用户");
			return false;
		}
		var accId = $(checkedAcc).parents("tr").find("input[name='id']").val();
		$("#formReadyOrder :hidden[name=guestId]").val(accId);
	}
	$.ajax({
		 url:basepath+'/cart/checkAccount',
		 type:"POST",
		 dataType:'json',
		 success:function(data){
			 var _data="";
			 if(data.status==-1){
				 layer.msg('立即登录', {
					    time: 0 //不自动关闭
					    ,btn: ['立即登录', '取消']
					    ,yes: function(index){
					        layer.close(index);
					        tourl(basepath+"/account/login");
					    }
					});
			 }else if(data.status==0){
				 layer.msg('请补全您的资料', {
					    time: 0 //不自动关闭
					    ,btn: ['立即补全资料', '取消']
					    ,yes: function(index){
					        layer.close(index);
					        tourl(basepath+"/account/account");
					    },cancel:function(){
					    	 $(".choseAcc").attr("disabled",false);
					    }
					});
			 }else if(data.status==1){
				 $("#formReadyOrder").submit();
			 }
			 
		 }
	  });
}
function openAccWin(){
	layer.open({
		type: 1,
		title : [
			'请选择要下单的客户',
			'color:#fff;background-color:#69a6c3;text-align:center;height:40px;font-size:14px;line-height: 40px;'
		],
		area : ['500px', '490px'],
		content : $("#tpl_accInfoBox").html(),
		btn: ['确认选择'],
		shade: 0.5,//背景色		
        shadeClose: false, //点击遮罩关闭
		scrollbar: false,  
		success : function(){
			loadAccData_new();
			$(".choseAcc").removeAttr("disabled");// 启用“去下单”按钮
		},
		yes: function(index){
			submitCart(1);
	    }
	});
}

function loadAccData_new(d){
	var d = d||{};
	d.page = d.page||1;
	d.cond = $("#queryCond").val();
	$.post(basepath+"/account/getAllGuests",d,function(data){
		//---
		laytpl(document.getElementById("tpl_accTable").innerHTML).render(data.list, function(html){
			$("#accTable").html(html);	    
			setPageTotal(data.count,"accTotal");
	    	
	    	laypage({
			    cont: 'page11',
			    pages: $("#accTotal").val(),
			    groups: 3,
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			    	return d.page;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	loadAccData_new({page:e.curr});			        	
			        }
			    }
	    	});
		});
		
	},"json");
}

// 设置总页数
function setPageTotal(count,pageTotal){
	var totalPage = 0;
	var rows = 5;// 每页显示条数
	totalPage = 1;
	// 计算总页数
	totalPage = parseInt((count - 1) / rows) + 1;
	$("#"+pageTotal).val(totalPage);
}
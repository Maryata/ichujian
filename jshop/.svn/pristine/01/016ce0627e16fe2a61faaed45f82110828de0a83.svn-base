//common
String.prototype.endsWith = function (pattern) {
    var d = this.length - pattern.length;
    return d >= 0 && this.lastIndexOf(pattern) === d;
}
/**
 * 后台脚本JS
 */

$(function(){
	//后台查询页面 全选/全不选 功能	jQuery v1.9
	$("#firstCheckbox").on("click",function(){
		console.log("check="+$(this).prop("checked"));
		if($(this).prop("checked")){
			$("input[type=checkbox]").prop("checked",true);
		}else{
			$("input[type=checkbox]").prop("checked", false);
		}
	});
	
	//为了使用bootstrap2的图标功能，只有牺牲使用struts2的s:submit方式提交表单。
	//这里对s:form表单的action进行重新组装，加上了你点击的按钮的method="update"方法，最后验证通过则提交表单。
	$("form").on('click', 'button[method]', function(e){
		console.log('this.button.mothod=='+$(this).attr('method'));
	    this.form.buttonMethod = $(this).attr('method');
	});
	$("form").on('click', 'input[type=submit][method]', function(e){
		console.log('this.button.mothod=='+$(this).attr('method'));
	    this.form.buttonMethod = $(this).attr('method');
	});
	
	//扩展验证状态;查看状态s为true;才提交
	$("form").on('click', 'input[type=button][method]', function(e){
		var thisBtn = $(this);
		//alert(thisBtn.attr('s'))
		if(thisBtn.attr('s')=='true'){
			console.log('this.button.mothod=='+thisBtn.attr('method'));
		    var buttonMethod = thisBtn.attr('method');
		    var form = thisBtn.parents("form");
			var _formAction = form.attr("action");
			//var aa = _formAction.substring(0,_formAction.lastIndexOf("/")+1);
	        var aa = _formAction.endsWith("/")?_formAction : _formAction + "/";
			console.log(aa);
			
			var lastFormAction = aa+buttonMethod;
			console.log("lastFormAction="+lastFormAction);
			form.attr("action",lastFormAction);
	        //form.attr("method", "POST");
			console.log(form.attr("action"));
			createMark();
			form.submit();
		}
	});
	
	/*$(document).on('click', 'button[method]', function(e){
		alert("cc");
		console.log('this.button.mothod=='+$(this).attr('method'));
	    this.form.buttonMethod = $(this).attr('method');
	});*/
	
	//扩展超链接
	$(document).on('click', 'a[method]', function(e){
		console.log('this.a.mothod=='+$(this).attr('method'));
		window.location.href=$(this).attr('method');
	});
	//--
	
	//onclick="doSubmitFuncWhenButton(this)"
	
	//通用按钮的提交表单事件
	$("form").on("valid.form", function(e, form){
		console.log(this.isValid);
        console.log("submit..."+form.buttonMethod);
        var buttonMethod = form.buttonMethod;
		console.log(buttonMethod);
		var _formAction = $(form).attr("action");
		//var aa = _formAction.substring(0,_formAction.lastIndexOf("/")+1);
        var aa = _formAction.endsWith("/")?_formAction : _formAction + "/";
		console.log(aa);
		
		var lastFormAction = aa+buttonMethod;
		console.log("lastFormAction="+lastFormAction);
		$(form).attr("action",lastFormAction);
        $(form).attr("method", "POST");
        //alert(lastFormAction)
		console.log($(form).attr("action"));
		createMark();
		form.submit();
	});
	/*$("form").on("invalid.form", function(e, form){
		alert("验证失败");
	});*/
	setTimeout(function(){
		$('#alert-success').alert("close");
		$('#alert-warning').alert("close");
		$('#alert-danger').alert("close");
	}, 3000);
		
});

//创建遮罩效果
function createMark(){
	$.blockUI({ message: "系统处理中，请等待...",css: { 
        border: 'none', 
        padding: '15px', 
        backgroundColor: '#000', 
        '-webkit-border-radius': '10px', 
        '-moz-border-radius': '10px', 
        opacity: .5, 
        color: '#fff',
        baseZ : 19999999
    }});
}

//查询
function selectList(obj){
	console.log("selectList...");
	var _form = $("form");
	_form.attr("action",$(obj).attr("method"));
	_form.submit();
}

//批量删除选择的记录
function submitIDs(obj,tip){
	console.log("submitIDs...");
	if ($("input:checked").size() == 0) {
		alert("请先选择要操作的内容！");
		return false;
	}

	if(confirm(tip)){
		createMark();
		var _form = $("form");
		_form.attr("action",$(obj).attr("method"));
		_form.submit();
	}
	return false;
}

//不需要任何验证的提交    
function submitNotValid2222(obj){
	createMark();
	console.log("submitNotValid2222...");
	var _form = $("form");
	_form.attr("action",$(obj).attr("method"));
	_form.submit();
}

//为了使用bootstrap2的图标功能，只有牺牲使用struts2的s:submit方式提交表单。
//这里对s:form表单的action进行重新组装，加上了你点击的按钮的method="update"方法，最后验证通过则提交表单。
function doSubmitFuncWhenButton(obj){
	/*
	$("#form").validator({
		
		valid: function(form){
			var me = this;
	        // ajax提交表单之前，先禁用submit
	        me.holdSubmit();
	        $(form).find('button').css('color', '#999').text('正在提交..');
	        
			this.isAjaxSubmit = false;
			var method = $(obj).attr("method");
			console.log(method);
			var _formAction = $(form).attr("action");
			var aa = _formAction.substring(0,_formAction.lastIndexOf("/")+1);
			console.log(aa);
			
			var lastFormAction = aa+method;//aa +"!" +method+".action";
			console.log("lastFormAction="+lastFormAction);
			$(form).attr("action",lastFormAction);
			
			console.log($(form).attr("action"));
			
			form.submit();
			
			me.holdSubmit(false);
		}
	});
	*/
	
	$("#form").on("valid.form", function(e, form){
		console.log(this.isValid);
		//if(this.isValid && this.isValid==true){
	        console.log("submit...");
		//}
	});
	
	$("#form").on("valid.form", function(e, form){
		console.log(this.isValid);
		if(this.isValid && this.isValid==true){
			
			//var me = this;
	        // ajax提交表单之前，先禁用submit
	        //me.holdSubmit();
	        //$(form).find('button').css('color', '#999').text('正在提交..');
	        console.log("submit...");
			/*
			this.isAjaxSubmit = false;
			var method = $(obj).attr("method");
			console.log(method);
			var _formAction = $(form).attr("action");
			var aa = _formAction.substring(0,_formAction.lastIndexOf("/")+1);
			console.log(aa);
			
			var lastFormAction = aa+method;
			console.log("lastFormAction="+lastFormAction);
			$(form).attr("action",lastFormAction);
			
			console.log($(form).attr("action"));
			
			form.submit();
			*/
			
			//me.holdSubmit(false);
		}
	});
	
}

// 获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == ""){
	    url = window.location.search;
    }else{	
    	url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
function page(href){
	//alert(href);
	var offset = getQueryString("pager.offset",href);
	//alert(offset);
	$("#pager_offset").val(offset);
	$(".btn-select").click();
}

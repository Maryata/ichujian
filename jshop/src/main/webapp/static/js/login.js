//验证是否登录;

G_CALLBACK_LOGIN_FUN = null;
G_CALLBACK_LOGIN_URL = null;

(function($, w, d){
//close win
$(".tanchu_close").click(function(){
    $(".tanchu_bg").hide();
    $(".tanchu").hide();
});

$(".loginWinform").Validform({
	tiptype:function(msg,o,cssctl){
		/*var objtip=$("#msgdemo2");
		cssctl(objtip,o.type);
		objtip.text(msg);*/
		if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
			var objtip=o.obj.siblings(".Validform_checktip");
			cssctl(objtip,o.type);
			objtip.text(msg);
		}else{
			var objtip=o.obj.find("#login_msg_holder");
			cssctl(objtip,o.type);
			objtip.text(msg);
		}
	},
	ajaxPost:true,
	callback:function(data){
		if(data.status=="y"){
			login = true;
			if(G_CALLBACK_LOGIN_URL && G_CALLBACK_LOGIN_URL!=''){
				window.location = G_CALLBACK_LOGIN_URL;
				G_CALLBACK_LOGIN_URL = null;
			}else{
				layer.msg('已经登录,可以继续操作');
				window.location.reload();
			}
			return;
			/*
			currentUser =data.currentUser;
			
			$(".tanchu_close").click();
			
			var loginBox = '<ul class="menu">' +
				'<li><a class="user" href="#">('+currentUser+')</a>' +
				'<ul>' +
				'<li><a href="'+basepath+'/account/account" class="user-ico">个人资料</a></li>' +
				'<li><a href="'+basepath+'/account/topwd" class="topwd-ico">修改密码</a></li>' +
				'<li><a href="'+basepath+'/account/orders" class="orders-ico">我的订单</a></li>' +
				'<li><a href="'+basepath+'/account/address" class="address-ico">配送地址</a></li>' +
				'<li><a href="'+basepath+'/account/exit" class="signout-ico">退出系统</a></li>' +
				'</ul>' +
				'</li>' +
				'</ul>' +
				'<span style="display: none;">' +currentUser+
				'</span>';
			$(".login-box").html(loginBox);
			
			//left product
			$(".jiage").show();
			$(".leftProductXianjia").hide();
			//center
			$(".danjia").show();
			$(".centerProductXianjia").hide();
			
						
			if($(".index_orders").length==0){
				$(".index_models").after('<a href="'+basepath+'/account/orders" class="index_orders">我的订单</a>');
			}
			
			$("#cartQuantityLogo").html(data.quantity).show();
			$("#cartQuantity").html(data.quantity).show();
			
			if(G_CALLBACK_LOGIN)G_CALLBACK_LOGIN();
			console.log('set login-box');
			*/
		}
	}
});
}(jQuery, window, document));

function changeTipLogin(th){
	var passText =document.getElementById('loginPassText');
    var pass =document.getElementById('loginPass');
    if(th.id =='loginPass'){
        if(th.value ==''||th.value.length ==0){
            passText.style.display='';
            pass.style.display='none';
        }
    }else{
        passText.style.display='none';
        pass.style.display='';
        pass.focus();
    }
}
function isLogin0(){
	console.log('isLogin0:'+login);
	if(!login){
		openLogin();
	}
	return login;
}

function isLogin(){
	var isL = false; 
	$.ajax({
	    url :basepath+'/account/unique2',
	    data:{name:'isLogin',param:'isLogin'},
	    cache : false, 
	    async : false,
	    type : "POST",
	    dataType : 'json',
	    success : function (data){
	    	if(data && data.status=='y'){
	    		isL = true;
	    	}
	    }
	});

	if(!isL){//打开登录;
		openLogin();
	}
	console.log('isLog:'+isL);
	return isL;
};

function openLogin(){
	$(".tanchu_bg").show();
	$(".tanchu").show();
	console.log('openLogin win');
}


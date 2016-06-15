var MODEL_LIST = [];
var MODEL_INDEX = 0;


//添加span标签
function add(){	
	var brand=$("input[name=brand]");
	var models=$("input[name=models]");
	var buyNumber=$("input[name=buyNumber]");
	var patrn=/^[0-9]*[1-9][0-9]*$/;//验证正整数的正则表达式  
	if(brand.val()==""){//brand.val()=="null"品牌可以为null且‘永不为空’
		layer.msg("请输入品牌！");
		brand.focus();
		return false;
	}else if(models.val()==""){//null机型的手机待定
		layer.msg("请输入机型！");
		models.focus();
		return false;
	}else if(buyNumber.val()==""||buyNumber.val()=="null"||!patrn.test(buyNumber.val())){
		layer.msg("输入正整数！");
		buyNumber.focus();
		return false;
	}else{
		//添加的数据放入表格中
		var id ="model_item_"+(MODEL_INDEX++);
		var _s="<tr id=\""+id+"\"><td>"+brand.val()+"</td><td>"+models.val()+"</td><td>"+buyNumber.val()+"</td><td><a href='javascript:remove(\""+id+"\")'>删除</a></td></tr>";
		$("table>tbody").prepend(_s);
		/*<--------------------------------------------------------->
		//var spanstr =brand.val()+"-"+models.val()+"-"+buyNumber.val();//向span注入值
		//var modelHtml = "<span id='"+id+"'>"+spanstr+"<a href='javascript:remove(\""+id+"\")'>xX</a></span>";
		//$("#infobox").append(modelHtml);
		<------------------------------------------------------------>*/
		//放入一个对象中
		var model ={
			brand: brand.val(),
			models:models.val(),
			buyNumber:buyNumber.val(),
			tempId : id
		};
		//把对象放入josn
		MODEL_LIST.push(model);
		//------------
		$("input[name=brand]").val("");
		$("input[name=models]").val("");
		$("input[name=buyNumber]").val("");
	}
	return true;

}
//删除创建的
function remove(value){
	$('#' + value).remove();
	for(var i =0;i <MODEL_LIST.length;i++){  
        var temp = MODEL_LIST[i];
        if(value==temp.tempId){
    		MODEL_LIST.splice(i, 1);
    		//console.log("--");
    		break;
    	}	
	}
}

//把适配清单<span>中的值通过ajax传入后台
function insert(){
	if(!MODEL_LIST || MODEL_LIST.length==0){
		return false;
	}
	$("#infobox").empty();
	$.ajax({
	    url :basepath+'/accountAdapter/toAdd',
	    data: { accountAdapters: JSON.stringify(MODEL_LIST) },
	    type : "post",
	    dataType : 'json',
	    success : function (data){
	    	MODEL_LIST = [];MODEL_INDEX = 0;
	    	if(data.status=="k"){
	    		layer.msg(data.info);//未登录
	    	}else if(data.status=="j"){
	    		layer.msg(data.info);//传递的josn为空
	    	}else if(data.status=="m"){
	    		layer.msg(data.info);//添加失败
	    	}else{
	    		layer.msg(data.info);//添加成功
	    	}
	    	window.location.reload(); 
	    	$(".model_window_close").click();
	    }
	});
}

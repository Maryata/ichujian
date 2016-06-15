function load(){
$.ajax({
	type: "get",
	url : "http://www.ichujian.com/AppService/webInterface_actInfo",
	data : {id:$("#data_actId").val()},
	dataType:"json",
	success : function(data) {
		if(data && data.status=='Y'){
			$("#data_actLook").html(data.actLook);
			$("#data_brandFocus").html(data.brandFocus)
		}
	},
	error: function(xhr){
	}
});
}
load();
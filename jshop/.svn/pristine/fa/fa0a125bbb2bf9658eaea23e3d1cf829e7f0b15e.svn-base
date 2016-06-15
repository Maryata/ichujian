// 购物车页面获取折扣方案
function getDis(callback){
	if(callback && DIS_INFO_IS_LOAD){
		callback();
		return;
	}
	$.ajax({
	  	type: "post",
	   	url :basepath+"/dis/selectDis",
	   	success : function (data){
	       	var arr=eval(data); 
	       	var _info="<div class='tip_zkbox'>";
	        for(var m = 0;m<arr.length;m++){
	        	DIS_INFO.push({minVal:arr[m].dis.minVal,maxVal:arr[m].dis.maxVal,rate:arr[m].dis.rate});
        		_info += arr[m].dis.minVal + "-" + arr[m].dis.maxVal +"(张)"+ "<span>" + arr[m].dis.rate + "</span>折"+"<br>";
	        }
	        _info += "</div>";
	        DIS_INFO_HTML = _info;
	        DIS_INFO_IS_LOAD = true;
	        if(callback){
	    		callback();
	    	}
  		}
   	});
}

function dis(){
  	layer.tips(
		DIS_INFO_HTML, 
		'#tips123', 
		{
		   	tips: [3, '#eee'],
		   	time: 4000
	   	}
   	);
}
// 其他页面获取折扣方案--d:“折扣方案”所在标签的id
function discnt(d){
	$.ajax({
	  	type: "post",
	   	url :basepath+"/dis/selectDis",
	   	success : function (data){
	       	var arr=eval(data); 
	       	var _info="<div class='tip_zkbox'>";
	        for(var m = 0;m<arr.length;m++){
        		_info += arr[m].dis.minVal + "-" + arr[m].dis.maxVal +"(张)"+ "<span>" + arr[m].dis.rate + "</span>折"+"<br>";
	        }
	        _info += "</div>";
	        layer.tips(
	        	_info, "#"+d, 
        		{
        		   	tips: [3, '#eee'],
        		   	time: 4000
        	   	}
           	);;
  		}
   	});
}
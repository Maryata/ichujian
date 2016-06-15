$(function(){
    if (top.location != self.location) {
        top.location = self.location;
    }
    $("#username").focus();
    $("#returl").val(getUrlParam('returl'));
    $("#btnLogin").click(function(){
        $("#formLogin").submit();
    });
    
   	function getUrlParam(name){
   		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); 
   		var r = window.location.search.substr(1).match(reg); 
   		if (r != null)
   		return unescape(r[2]); 
   		return ''; 
 	}
});
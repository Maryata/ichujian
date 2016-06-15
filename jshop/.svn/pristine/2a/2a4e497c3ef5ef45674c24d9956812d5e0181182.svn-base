function getExpressCompany(){//选取其他填写物流公司
	var expressCode=$("#address_express").val();
	if(expressCode!="" && expressCode=="OTHER"){
		$("#expressCompanyName").val("");
		$("#expressCompanyName").show();
	}else{
		var express=$("#address_express").find("option:selected").text();
		$("#expressCompanyName").val(express);
		$("#expressCompanyName").hide();
	}
}
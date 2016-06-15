(function(global, $) {
	var Supplier = {}; // 供应商对象
	var basePath = ""; // 根路径
	var _laypage = function() {}; // 分页插件对象
	var array = []; // 存放当前页数据对象
	var _Spinner= function(){}; // ajax 加载效果类
	var spinner = {}; //ajax 加载效果对象

	var currentStart = 0;

	var list = function(queryParam) {
		queryParam = queryParam || {},
		queryParam.start = queryParam.start || 0; // 默认0条开始
		queryParam.limit = queryParam.limit || 5; // 默认一页数据量

		currentStart = queryParam.start;

		$('#page').html('');
		$.ajax({
			type : "GET",
			url : basePath + "/basedata/supplierInfo!ajaxGetSupplierList.action",
			data : queryParam,
			dataType : "json",
			success : function( data ) {
				if( data && data.status === "Y") {
					//$(".biaodan-q").remove();
					array = [];
					$("tr[name='datatr']").remove(); // 每次加载完成后，清空之前数据
					var l = data.list || []; //  默认列表空
					var count = data.count || 0; // 默认记录0
					for(var i = 0; i < l.length; ++i) {
						var o = l[i];
						var cols = ["C_SUPPLIER_CODE","C_SUPPLIER_NAME","C_COMPANY","C_PHONE","C_CONTACTS","C_EMAIL","C_LOCATION","C_URL"]; // "C_LEVE",
						var tr = "<tr name='datatr' class='biaodan-q' style='text-align:center'>";
						for(var j = 0; j < cols.length; ++j) {
							tr += "<td nowrap='nowrap'>"+o[cols[j]]+"</td>";
						}
						tr += "<td nowrap='nowrap'><input type='button' class='butt' onclick='Supplier.openEdit(\""+o["C_ID"]+"\");' value='编辑'>&nbsp;<input type='button' class='butt' onclick='Supplier.del("+o["C_ID"]+")' value='删除'><input type='button' class='butt' onclick='Supplier.processApk(this,\""+o["C_SUPPLIER_CODE"]+"\")' value='apk处理'></td></tr>";

						$("#dt").append(tr); // 数据表格追加行
						array.push({"C_ID":o["C_ID"],"Supplier" : o});
					}
					
					var rows = queryParam.limit; // 每页行数
					var page = (queryParam.start) / rows + 1; // 当前页
					var totalPages = function() { // 总页数
						if(count % rows ==0) return count / rows;
						return count / rows + 1;
					}();
					
					if(totalPages !== 0) {
						// 分页组件初始化
						_laypage({
						    cont: 'page',
						    pages: totalPages,
						    curr: function(){
						    	return page;
						    }(), 
						    jump: function(e, first){ //触发分页后的回调
						        if(!first){ //一定要加此判断，否则初始时会无限刷新
						        	var c_code = $("#code").val();
						        	var c_name = $("#name").val();
						        	list({start:(e.curr - 1) * queryParam.limit,c_code : c_code,c_name : c_name});
						        }
						    }
						});
					}
				}
			}
		});	
	};
	Supplier.init = function(root, laypage,Spinner) {
		basePath = root || ""; // 初始化项目根路径
		_laypage = laypage; // 初始化分页对象
		_Spinner = Spinner;
		
		list(); // 初始化列表
		validate(); // 初始化校验
		_ajaxLoading();
	};
	
	// 列表查询
	Supplier.query = function(queryParam) {
		list(queryParam);
	};
	// 编辑供应商信息
	Supplier.openEdit = function(id) {
		if( id ) {
			$("#editForm :input[name='C_ID']").val(id);
			// 这个也可以通过ID异步到后台取，不过，如果列表包含编辑需要的所有数据，就直接页面取了。
			// 最初的想法是直接通过方法参数传入的（在列表表格追加的时候）不过，参数传递有问题，所以将列表数据存入一个临时数组，这边通过ID去获取供应商对象数据
			var Supplier = findById(array,id); 
			if(Supplier) {
				//...
				$("#editForm :input[name='C_SUPPLIER_CODE']").val(Supplier.C_SUPPLIER_CODE||"");
				$("#editForm :input[name='C_SUPPLIER_NAME']").val(Supplier.C_SUPPLIER_NAME||"");
				$("#editForm :input[name='C_COMPANY']").val(Supplier.C_COMPANY||"");
				$("#editForm :input[name='C_PHONE']").val(Supplier.C_PHONE||"");
				$("#editForm :input[name='C_CONTACTS']").val(Supplier.C_CONTACTS||"");
				$("#editForm :input[name='C_EMAIL']").val(Supplier.C_EMAIL||"");
				$("#editForm :input[name='C_LOCATION']").val(Supplier.C_LOCATION||"");
			//	$("#editForm :input[name='C_LEVE']").val(Supplier.C_LEVE||"");
				$("#editForm :input[name='C_URL']").val(Supplier.C_URL||"");
				$("#editForm :input[name='C_WEBSITE']").val(Supplier.C_WEBSITE||"");
				$("#s_c_type").val(Supplier.C_TYPE||1);
				
				$("#s_c_islive").val(Supplier.C_ISLIVE || 1);

				$('#s_c_isExchange').val(Supplier.C_ISEXCHANGE || 1);
				
				$('#s_c_is_potential_demand').val(Supplier.C_IS_POTENTIAL_DEMAND || 1);
				
				//$("#editForm :input[name='C_COLOR']").val(Supplier.C_COLOR||"");
				//$("#editForm :input[name='C_ABOUT_LOGO_URI']").val(Supplier.C_ABOUT_LOGO_URI||"");
				//$("#editForm :input[name='C_MAIN_COMMON_SLOGAN']").val(Supplier.C_MAIN_COMMON_SLOGAN||"");
				//$("#editForm :input[name='C_MAIN_MAIN_LOGO_URI']").val(Supplier.C_MAIN_MAIN_LOGO_URI||"");
				$("#editForm :input[name='C_ABOUT_INFO']").val(Supplier.C_ABOUT_INFO||"");
				$("#editForm :input[name='C_MAIN_MAIN_BUY']").val(Supplier.C_MAIN_MAIN_BUY||"");
				//$("#editForm :input[name='C_SHOPPING_URI']").val(Supplier.C_SHOPPING_URI||"");
				//$("#editForm :input[name='C_COMMON_COPYRIGHT']").val(Supplier.C_COMMON_COPYRIGHT||"");
				$("#editForm :input[name='C_HELPANDFEEDBACK']").val(Supplier.C_HELPANDFEEDBACK||"");
				
				//$('#editForm :input[name="C_HUAWEI_EMUI"]').val(Supplier.C_HUAWEI_EMUI||'');
				//$('#editForm :input[name="C_AID_ONE"]').val(Supplier.C_AID_ONE||'');
				
				$("#s_c_host_website_wether_show").val(Supplier.C_HOST_WEBSITE_WETHER_SHOW||1);
				
				$("#span_logo_uri").text(Supplier.C_LOGO_URI||"");
				$("#span_color").text(Supplier.C_COLOR||"");
				$("#span_about_logo_uri").text(Supplier.C_ABOUT_LOGO_URI||"");
				$("#span_main_common_slogan").text(Supplier.C_MAIN_COMMON_SLOGAN||"");
				$("#span_main_main_logo_uri").text(Supplier.C_MAIN_MAIN_LOGO_URI||"");
				$("#span_shopping_uri").text(Supplier.C_SHOPPING_URI||"");
				
				$("#span_background_lanch").text(Supplier.C_BACKGROUND_LANCH||"");
				$("#span_background_home").text(Supplier.C_BACKGROUND_HOME||"");
				$("#span_floatwindow_miuiv4v5").text(Supplier.C_FLOATWINDOW_MIUIV4V5||"");
				$("#span_floatwindow_miuiv6").text(Supplier.C_FLOATWINDOW_MIUIV6||"");
				$("#span_floatwindow_emui3").text(Supplier.C_FLOATWINDOW_EMUI3||"");
				
				$('#span_huawei_emui').text(Supplier.C_HUAWEI_EMUI||'');
				$('#span_aid_one').text(Supplier.C_AID_ONE||'');
			}
		} else {
			$("#editForm :input[name='C_ID']").val("");
			$("#editForm")[0].reset();
			$("#span_logo_uri").text("");
			$("#span_color").text("");
			$("#span_about_logo_uri").text("");
			$("#span_main_common_slogan").text("");
			$("#span_main_main_logo_uri").text("");
			$("#span_shopping_uri").text("");
			
			$("#span_background_lanch").text("");
			$("#span_background_home").text("");
			$("#span_floatwindow_miuiv4v5").text("");
			$("#span_floatwindow_miuiv6").text("");
			$("#span_floatwindow_emui3").text("");
			
			$('#span_huawei_emui').text('');
			$('#span_aid_one').text('');
		}
		
		$(".tanchu").show();
		$(".tanchu_bg").show();
		
		$(".tanchu_close").on("click",function(){
	        $(".tanchu_bg").hide();
	        $(".tanchu").hide();
    	});
	};
	// 持久化供应商信息
	Supplier.save = function(obj) {
		if($("#editForm").valid()) {
			obj.disabled = "disabled"; 
		
		    var form = $("#editForm");
		    var formdata = false;
		    if (global.FormData){
		        formdata = new FormData(form[0]);
	    	}
	
		    var formAction = basePath + "/basedata/supplierInfo!ajaxSaveSupplier.action";
		    $.ajax({
		        url         : formAction,
		        data        : formdata ? formdata : form.serialize(),
		        mimeType    : "multipart/form-data",
		        cache       : false,
		        contentType : false,
		        processData : false,
		        type        : "POST",
		        dataType	: "JSON",
		        success     : function(data, textStatus, jqXHR){
		        	if(data && data.status === "Y") {
		        		alert("保存成功");
		        		Supplier.closeEdit();
		        		list({'start':currentStart});
		        	} else {
		        		alert("保存失败");
		        	}
		        	
		        	obj.disabled = "";
		        }
		    });
		}
	};
	// 删除供应商信息
	Supplier.del = function(id) {
		if(id) {
			if(confirm("确定删除本条数据吗？")) {
				$.ajax({
					type : "POST",
					url : basePath + "/basedata/supplierInfo!ajaxDeleteSupplier.action",
					data : {"c_id" : id},
					dataType : "json",
					success : function( data ) {
						if( data && data.status === "Y") {
							alert("删除成功！");
							list({'start':currentStart});
						} else {
							alert("删除失败！");
						}
					}
				});	
			} 
		}
	};
	// 取消编辑操作
	Supplier.closeEdit = function() {
		$(".tanchu").hide();
		$(".tanchu_bg").hide();
	};
	Supplier.processApk = function(obj,code) {
		obj.disabled = "disabled";
		code = code || "";
		$.ajax({
			type : "POST",
			url : basePath + "/basedata/supplierInfo!ajaxProcessApk.action",
			data : {"code" : code},
			dataType : "text",
			beforeSend : function() {
				ajaxStart();
			},
			complete : function () {
				ajaxStop();
			},
			success : function( data ) {
				ajaxStop();
				obj.disabled = "";
				if(data === "1") {
					alert("处理完成!");
				} else {
					alert("处理失败!");
				}
			}
		});	
	};
	Supplier.runScript = function(event) {
		if(!event) return true;
		
	    if (event.which == 13 || event.keyCode == 13) {
			var c_code = $("#code").val();
			var c_name = $("#name").val();
			list({start:0,c_code : c_code,c_name : c_name});
			return false;
	    }
	    return true;
	};
	
	var validate = function() {
		//$("#editForm").validate({
		//	//rules : {
		//		//validateSpecialChars
		//	//	C_SUPPLIER_CODE : {
		//	//		maxlength : 50,
		//	//		required : true,
		//	//		"remote" : {
		//	//			url : basePath + "/basedata/supplierInfo!ajaxCheckCode.action",
		//	//			type : "POST",
		//	//			data :  {
		//	//				C_SUPPLIER_CODE : function(){
		//	//					return $("#editForm :input[name='C_SUPPLIER_CODE']").val();
		//	//				},
		//	//				C_ID : function() {
		//	//					return $("#editForm :input[name='C_ID']").val()||"";
		//	//				}
		//	//			}
		//	//		},
		//	//		"validateSpecialChars" : $("#editForm :input[name='C_SUPPLIER_CODE']").val()
		//	//	},
		//	//	C_SUPPLIER_NAME : {
		//	//	 //	required : true,
		//	//		"remote" : {
		//	//			url : basePath + "/basedata/supplierInfo!ajaxCheckName.action",
		//	//			type : "POST",
		//	//			data :  {
		//	//				C_SUPPLIER_NAME : function(){
		//	//					return $("#editForm :input[name='C_SUPPLIER_NAME']").val();
		//	//				},
		//	//				C_ID : function() {
		//	//					return $("#editForm :input[name='C_ID']").val()||"";
		//	//				}
		//	//			}
		//	//		},
		//	//		maxlength : 50,
		//	//		"validateSpecialChars" : $("#editForm :input[name='C_SUPPLIER_NAME']").val()
		//	//	},
		//	//	C_COMPANY : {
		//	//		maxlength : 200,
		//	//		"validateSpecialChars" : $("#editForm :input[name='C_COMPANY']").val()
		//	//	},
		//	//	C_PHONE : {
		//	//		maxlength : 20,
		//	//		"validateSpecialChars" : $("#editForm :input[name='C_PHONE']").val()
		//	//	},
		//	//	C_CONTACTS : {
		//	//		maxlength : 20,
		//	//		"validateSpecialChars" : $("#editForm :input[name='C_CONTACTS']").val()
		//	//	},
		//	//	C_EMAIL : {
		//	//		maxlength : 50
		//	//		//"validateSpecialChars" : $("#editForm :input[name='C_EMAIL']").val()
		//	//	},
		//	//	C_LOCATION : {
		//	//		maxlength : 600,
		//	//		"validateSpecialChars" : $("#editForm :input[name='C_LOCATION']").val()
		//	//	},
		//	//	C_LEVE : {
		//	//		maxlength : 10,
		//	//		"validateSpecialChars" : $("#editForm :input[name='C_LEVE']").val()
		//	//	},
		//	//	C_URL : {
		//	//		maxlength : 1000//,
		//	//		//"validateSpecialChars" : $("#editForm :input[name='C_URL']").val()
		//	//	},
		//	//	C_ABOUT_INFO : {
		//	//		maxlength : 64,
		//	//		"validateSpecialChars" : $("#editForm :input[name='C_ABOUT_INFO']").val()
		//	//	},
		//	//	C_MAIN_MAIN_BUY : {
		//	//		maxlength : 128,
		//	//		"validateSpecialChars" : $("#editForm :input[name='C_MAIN_MAIN_BUY']").val()
		//	//	},
		//	//	C_HELPANDFEEDBACK : {
		//	//		"validateSpecialChars" : $("#editForm :input[name='C_HELPANDFEEDBACK']").val()
		//	//	},
		//	//	C_WEBSITE : {
		//	//		"validateSpecialChars" : $("#editForm :input[name='C_HELPANDFEEDBACK']").val()
		//	//	}
		//	//},
		//	//messages : {
		//	//	C_SUPPLIER_CODE : {
		//	//		remote: $.validator.format(" {0} is already taken.")
		//	//	},
		//	//	C_SUPPLIER_NAME : {
		//	//		remote: $.validator.format(" {0} is already taken.")
		//	//	}
		//	//},
		//	submitHandler: function(form) {
         //      // form.submit();
         //   }
		//});
	};
	
	var findById = function(arr, id) {
		for(var i = 0; i < arr.length; ++i) {
			if(arr[i]["C_ID"] === id) {
				return arr[i]["Supplier"];
			}
		}
		
		return null;
	};
	var ajaxStart = function() {
		var target = document.getElementById('div_ajax');
		spinner.spin(target);
	};
	var ajaxStop = function() {
		spinner.stop();
	};
	var _ajaxLoading = function() {
		var opts = {
			  lines: 13 // The number of lines to draw
			, length: 28 // The length of each line
			, width: 14 // The line thickness
			, radius: 42 // The radius of the inner circle
			, scale: 1 // Scales overall size of the spinner
			, corners: 1 // Corner roundness (0..1)
			, color: '#000' // #rgb or #rrggbb or array of colors
			, opacity: 0.25 // Opacity of the lines
			, rotate: 0 // The rotation offset
			, direction: 1 // 1: clockwise, -1: counterclockwise
			, speed: 1 // Rounds per second
			, trail: 60 // Afterglow percentage
			, fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
			, zIndex: 2e9 // The z-index (defaults to 2000000000)
			, className: 'spinner' // The CSS class to assign to the spinner
			, top: '50%' // Top position relative to parent
			, left: '50%' // Left position relative to parent
			, shadow: false // Whether to render a shadow
			, hwaccel: false // Whether to use hardware acceleration
			, position: 'absolute' // Element positioning
		};
		spinner = new _Spinner(opts);
	};
	
	global.Supplier = Supplier;
})(window,jQuery);
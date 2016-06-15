(function(global, $) {
	var McrApp = {}; // 微用帮应用对象
	var basePath = ""; // 根路径
	var _laypage = {}; // 分页插件对象
	var array = []; // 存放当前页数据对象
	var categories = [];
	
	var list = function(queryParam) {
		queryParam = queryParam || {},
		queryParam.start = queryParam.start || 0; // 默认0条开始
		queryParam.limit = queryParam.limit || 10; // 默认一页数据数量
		$('#page').html('');
		$.ajax({
			type : "GET",
			url : basePath + "/basedata/mcrAppInfo!ajaxList.action",
			data : queryParam,
			dataType : "json",
			success : function( data ) {
				if( data && data.status === "Y") {
					array = [];
					$("tr[name='datatr']").remove(); // 每次加载完成后，清空之前数据
					var l = data.list || []; //  默认列表空
					var count = data.count || 0; // 默认记录0
					for(var i = 0; i < l.length; ++i) {
						var o = l[i];
						var cols = ['C_NAME'];
						var tr = "<tr name='datatr' class='biaodan-q' style='text-align:center'>";
						
						tr += '<td nowrap="nowrap">'+o['C_ID']+'</td>';
						tr += '<td><img src="' + o['C_LOGOURL'] + '"></td>';
						for(var j = 0; j < cols.length; ++j) {
							tr += "<td nowrap='nowrap'>"+o[cols[j]]+"</td>";
						}
						tr += "<td nowrap='nowrap'><input type='button' class='butt' onclick='McrApp.openEdit(\""+o["C_ID"]+"\");' value='编辑'>&nbsp;<input type='button' class='butt' onclick='McrApp.del("+o["C_ID"]+")' value='删除'></td></tr>";

						$("#dt").append(tr); // 数据表格追加行
						array.push({"C_ID":o["C_ID"],"McrApp" : o});
					}
					categories = data.categories || [];
					var $categories = $('#s_categories');
					$categories.empty();
					$categories.append('<option value=""></option>');
					for(var i = 0; i < categories.length; ++i) {
						var o = categories[i];
						$categories.append('<option value="' + o['C_ID'] + '">' + o['C_NAME'] + '</option>');
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
						        	var c_name = $("#name").val();
						        	list({start:(e.curr - 1) * queryParam.limit,c_name : c_name});
						        }
						    }
						});
					}
				}
			},
			error : function(jqXHR,textStatus,errorThrown) {
				if(typeof console !== 'undefiend') {
					console.log(textStatus);
				}
			}
		});	
	};
	McrApp.init = function(root, laypage,Spinner) {
		basePath = root || ""; // 初始化项目根路径
		_laypage = laypage; // 初始化分页对象
		
		list(); // 初始化列表
		validate(); // 初始化校验
	};
	
	// 列表查询
	McrApp.query = function(queryParam) {
		list(queryParam);
	};
	// 编辑微用帮应用信息
	McrApp.openEdit = function(id) {
		if( id ) {
			$("#editForm :input[name='C_ID']").val(id);
			
			var _McrApp = findById(array,id); 
			if(_McrApp) {
				$("#editForm :input[name='C_NAME']").val(_McrApp.C_NAME||"");
				$('#editForm :input[name="C_APPURL"]').val(_McrApp.C_APPURL||'');
				$('#editForm :input[name="C_ABSTRACT"]').val(_McrApp.C_ABSTRACT||'');
				$('#editForm :input[name="C_NUMBER_OF_FAVORITES"]').val(_McrApp.C_NUMBER_OF_FAVORITES||'');
				$('#s_categories').val(_McrApp.C_CATEGORY||'');
				
				$("#span_logourl").text(_McrApp.C_LOGOURL||'');
			}
		} else {
			$("#editForm :input[name='C_ID']").val("");
			$("#editForm")[0].reset();
			
			$("#span_logourl").text('');
		}
		
		$(".tanchu").show();
		$(".tanchu_bg").show();
		
		$(".tanchu_close").on("click",function(){
	        $(".tanchu_bg").hide();
	        $(".tanchu").hide();
    	});
	};
	// 持久化微用帮应用信息
	McrApp.save = function(obj) {
		if($("#editForm").valid()) {
			obj.disabled = "disabled"; 
		
		    var form = $("#editForm");
		    var formdata = false;
		    if (global.FormData){
		        formdata = new FormData(form[0]);
	    	}
	
		    var formAction = basePath + "/basedata/mcrAppInfo!ajaxSave.action";
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
		        		McrApp.closeEdit();
		        		list();
		        	} else {
		        		alert("保存失败");
		        	}
		        	
		        	obj.disabled = "";
		        },
		        error : function(jqXHR,textStatus,errorThrown) {
					if(typeof console !== 'undefiend') {
						console.log(textStatus);
					}
				}
		    });
		}
	};
	// 删除微用帮应用信息
	McrApp.del = function(id) {
		if(id) {
			if(confirm("确定删除本条数据吗？")) {
				$.ajax({
					type : "POST",
					url : basePath + "/basedata/mcrAppInfo!ajaxDelete.action",
					data : {"c_id" : id},
					dataType : "json",
					success : function( data ) {
						if( data && data.status === "Y") {
							alert("删除成功！");
							list();
						} else {
							alert("删除失败！");
						}
					},
					error : function(jqXHR,textStatus,errorThrown) {
						if(typeof console !== 'undefiend') {
							console.log(textStatus);
						}
						alert("删除失败！");
					}
				});	
			} 
		}
	};
	// 取消编辑操作
	McrApp.closeEdit = function() {
		$(".tanchu").hide();
		$(".tanchu_bg").hide();
	};
	
	McrApp.runScript = function(event) {
		if(!event) return true;
		
	    if (event.which == 13 || event.keyCode == 13) {
			var c_name = $("#name").val();
			list({start:0,c_name : c_name});
			return false;
	    }
	    return true;
	};
	
	var validate = function() {
		$("#editForm").validate({
			rules : {
				//validateSpecialChars
				C_NAME : {
					maxlength : 50,
					required : true,
					"validateSpecialChars" : $("#editForm :input[name='C_NAME']").val()
				},
				C_APPURL : {
					maxlength : 1000,
					"validateSpecialChars" : $("#editForm :input[name='C_APPURL']").val()
				}
			},
			messages : {
			},
			submitHandler: function(form) {
            }
		});
	};
	
	var findById = function(arr, id) {
		for(var i = 0; i < arr.length; ++i) {
			if(arr[i]["C_ID"] === id) {
				return arr[i]["McrApp"];
			}
		}
		
		return null;
	};
	
	global.McrApp = McrApp;
})(window,jQuery);
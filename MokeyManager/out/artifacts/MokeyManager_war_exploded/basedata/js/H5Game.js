(function(global, $) {
	var H5Game = {}; // 供应商对象
	var basePath = ""; // 根路径
	var _laypage = {}; // 分页插件对象
	var array = []; // 存放当前页数据对象
	var _Spinner= {}; // ajax 加载效果类
	var spinner = {}; //ajax 加载效果对象
	
	var list = function(queryParam) {
		queryParam = queryParam || {},
		queryParam.start = queryParam.start || 0; // 默认0条开始
		queryParam.limit = queryParam.limit || 5; // 默认一页五条数据
		$('#page').html('');
		$.ajax({
			type : "GET",
			url : basePath + "/basedata/h5GameAction!ajaxList.action",
			data : queryParam,
			dataType : "json",
			success : function( data ) {
				if( data && data.status === "Y") {
					array = [];
					$("tr[name='datatr']").remove(); // 每次加载完成后，清空之前数据
					var l = data.list || []; //  默认列表空
					var count = data.count || 0; // 默认记录0
					var cols = ["C_NAME","C_PUBLISH_DATE","C_ISLIVE"];
					for(var i = 0; i < l.length; ++i) {
						var o = l[i];
						var tr = "<tr name='datatr' class='biaodan-q' style='text-align:center'>";
						
						tr += '<td nowrap="nowrap">'+o['C_ID']+'</td>';
						tr += '<td><img src="' + o['C_LOGOURL'] + '"></td>';  
						for(var j = 0; j < cols.length; ++j) {
							tr += "<td nowrap='nowrap'>"+o[cols[j]]+"</td>";
						}
						tr += "<td nowrap='nowrap'><input type='button' class='butt' onclick='H5Game.openEdit(\""+o["C_ID"]+"\");' value='编辑'>&nbsp;<input type='button' class='butt' onclick='H5Game.del("+o["C_ID"]+")' value='删除'></td></tr>";

						$("#dt").append(tr); // 数据表格追加行
						array.push({"C_ID":o["C_ID"],"H5Game" : o});
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
			}
		});	
	};
	H5Game.init = function(root, laypage,Spinner) {
		basePath = root || ""; // 初始化项目根路径
		_laypage = laypage; // 初始化分页对象
		_Spinner = Spinner;
		
		list(); // 初始化列表
		//validate(); // 初始化校验
		_ajaxLoading();
	};
	
	// 列表查询
	H5Game.query = function(queryParam) {
		list(queryParam);
	};
	// 编辑供应商信息
	H5Game.openEdit = function(id) {
		if( id ) {
			$("#editForm :input[name='C_ID']").val(id);
			var _H5Game = findById(array,id); 
			if(_H5Game) {
				$("#editForm :input[name='C_NAME']").val(_H5Game.C_NAME||"");
				$("#editForm :input[name='C_VERSION']").val(_H5Game.C_VERSION||"");
				$("#editForm :input[name='C_JARNAME']").val(_H5Game.C_JARNAME||"");
				$("#s_c_islive").val(_H5Game.C_ISLIVE || 1);
				$('#editForm :input[name="C_TITLE"]').val(_H5Game.C_TITLE||'');
				$('#editForm :input[name="C_APPURL"]').val(_H5Game.C_APPURL||'');
				
				$("#span_logourl").text(_H5Game.C_LOGOURL||"");
				//$("#span_appurl").text(_H5Game.C_APPURL||"");
			}
		} else {
			$("#editForm")[0].reset();
			$("#editForm :input[name='C_ID']").val('');
			$("#span_logourl").text("");
			//$("#span_appurl").text("");
		}
		
		$(".tanchu").show();
		$(".tanchu_bg").show();
		
		$(".tanchu_close").on("click",function(){
	        $(".tanchu_bg").hide();
	        $(".tanchu").hide();
    	});
	};
	// 持久化供应商信息
	H5Game.save = function(obj) {
		if($("#editForm").valid()) {
			//ajaxStart();
			obj.disabled = "disabled"; 
		
		    var form = $("#editForm");
		    var formdata = false;
		    if (global.FormData){
		        formdata = new FormData(form[0]);
	    	}
	
		    var formAction = basePath + "/basedata/h5GameAction!ajaxSave.action";
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
		        		H5Game.closeEdit();
		        		list();
		        	} else {
		        		alert("保存失败");
		        	}
		        	
		        	obj.disabled = "";
		        	//ajaxStop();
		        }
		    });
		}
	};
	// 删除供应商信息
	H5Game.del = function(id) {
		if(id) {
			if(confirm("确定删除本条数据吗？")) {
				$.ajax({
					type : "POST",
					url : basePath + "/basedata/h5GameAction!ajaxDelete.action",
					data : {"c_id" : id},
					dataType : "json",
					success : function( data ) {
						if( data && data.status === "Y") {
							alert("删除成功！");
							list();
						} else {
							alert("删除失败！");
						}
					}
				});	
			} 
		}
	};
	// 取消编辑操作
	H5Game.closeEdit = function() {
		$(".tanchu").hide();
		$(".tanchu_bg").hide();
	};
	
	H5Game.runScript = function(event) {
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
				C_JARNAME : {
					maxlength : 200,
					required : true,
					"remote" : {
						url : basePath + "/basedata/h5GameAction!ajaxCheck.action",
						type : "POST",
						data :  {
							C_JARNAME : function(){
								return $("#editForm :input[name='C_JARNAME']").val(); 
							},
							C_ID : function() {
								return $("#editForm :input[name='C_ID']").val()||"";
							}
						}
					},
					"validateSpecialChars" : $("#editForm :input[name='C_JARNAME']").val()
				},
				C_TITLE : {
					maxlength : 100
				}
			},
			messages : {
				C_JARNAME : {
					remote: $.validator.format(" {0} is already taken.")
				}
			},
			submitHandler: function(form) {
               // form.submit();
            }
		});
	};
	
	var findById = function(arr, id) {
		for(var i = 0; i < arr.length; ++i) {
			if(arr[i]["C_ID"] === id) {
				return arr[i]["H5Game"];
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
	
	global.H5Game = H5Game;
})(window,jQuery);
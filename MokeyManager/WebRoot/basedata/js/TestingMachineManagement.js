(function(global, $) {
	var TestingMachineManagement = {}; // 供应商对象
	var basePath = ""; // 根路径
	var _laypage = {}; // 分页插件对象
	
	var list = function(queryParam) {
		queryParam = queryParam || {},
		queryParam.start = queryParam.start || 0; // 默认0条开始
		queryParam.limit = queryParam.limit || 20; // 默认一页一条数据
		$('#page').html('');
		$.ajax({
			type : "GET",
			url : basePath + "/basedata/testingMachineManagementAction!ajaxTestingMachineList.action",
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
						var cols = ["C_PHONENAME","C_UNAME","C_DATE", "C_PHONENUM"];
						var tr = "<tr name='datatr' class='biaodan-q' style='text-align:center'>";
						for(var j = 0; j < cols.length; ++j) {
							tr += "<td nowrap='nowrap'>"+o[cols[j]]+"</td>";
						}

						$("#dt").append(tr); // 数据表格追加行
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
						        	var phonename = $("#phonename").val();
						        	var username = $("#username").val();
						        	list({start:(e.curr - 1) * queryParam.limit,phonename : phonename,username : username});
						        }
						    }
						});
					}
				}
			}
		});	
	};
	TestingMachineManagement.init = function(root, laypage) {
		basePath = root || ""; // 初始化项目根路径
		_laypage = laypage; // 初始化分页对象
		
		list(); // 初始化列表
	};
	// 列表查询
	TestingMachineManagement.query = function(queryParam) {
		list(queryParam);
	};
	
	TestingMachineManagement.runScript = function(event) {
		if(!event) return true;
		
	    if (event.which == 13 || event.keyCode == 13) {
	        var phonename = $("#phonename").val();
        	var username = $("#username").val();
        	list({start:0,phonename : phonename,username : username});
        	
	        return false;
	    }
	    return true;
	};
	
	
	global.TestingMachineManagement = TestingMachineManagement;
})(window,jQuery);
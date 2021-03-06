(function(global, $) {
	var Message = {}; // 留言管理对象
	var basePath = ''; // 根路径
	var _layPage = function(){}; // 分页插件对象
	var array = []; // 存放当前页数据对象
	
	var list = function(queryParam) {
		queryParam = queryParam || {},
		queryParam.start = queryParam.start || 0; // 默认0条开始
		queryParam.limit = queryParam.limit || 5; // 默认一页五条数据
		$('#page').html('');
		$.ajax({
			type : 'GET',
			url : basePath + '/basedata/ekey/eKMessageAction!ajaxList.action',
			data : queryParam,
			dataType : 'json',
			success : function( data ) {
				if( data && data.status === 'Y') {
					array = [];
					$('tr[name="datatr"]').remove(); // 每次加载完成后，清空之前数据
					var l = data.list || []; //  默认列表空
					var count = data.count || 0; // 默认记录0
					var cols = ['C_ID','C_NICKNAME','C_MESS'];
					for(var i = 0; i < l.length; ++i) {
						var o = l[i];
						var tr = '<tr name="datatr" class="biaodan-q" style="text-align:center">';

						for(var j = 0; j < cols.length; ++j) {
							tr += '<td nowrap="nowrap">'+(o[cols[j]]||'')+'</td>';
						}

						tr += '<td><img style="width: 40px;height: 30px;" src="' + (o['C_IMG']||'') + '"></td>';
						tr += '<td nowrap="nowrap">'+replied(o['C_REPLIED'])+'</td>'

						tr += '<td nowrap="nowrap"><input type="button" class="butt" onclick="Message.toEdit(\''+o['C_UID']+'\');" value="编辑">&nbsp;</td></tr>';

						$('#dt').append(tr); // 数据表格追加行
						array.push({'C_ID':o['C_ID'],'Message' : o});
					}
					
					var rows = queryParam.limit; // 每页行数
					var page = (queryParam.start) / rows + 1; // 当前页
					var totalPages = function() { // 总页数
						if(count % rows ==0) return count / rows;
						return parseInt(count / rows) + 1;
					}();
					
					if(totalPages !== 0) {
						// 分页组件初始化
						_layPage({
						    cont: 'page',
						    pages: totalPages,
						    curr: function(){
						    	return page;
						    }(), 
						    jump: function(e, first){ //触发分页后的回调
						        if(!first){ //一定要加此判断，否则初始时会无限刷新
						        	var c_name = $('#name').val();
						        	list({start:(e.curr - 1) * queryParam.limit,c_name : c_name});
						        }
						    }
						});
					}
				}
			}
		});	
	};
	Message.init = function(root, laypage) {
		basePath = root || ''; // 初始化项目根路径
		_layPage = laypage; // 初始化分页对象

		list(); // 初始化列表
	};

	// 列表查询
	Message.query = function(queryParam) {
		list(queryParam);
	};
	// 编辑留言信息
	Message.toEdit = function(id) {
		global.location.href = basePath + '/basedata/ekey/eKMessageAction!toEdit.action?uid=' + id;
	};

	// 删除留言信息
	Message.del = function(id) {
		if(id) {
			if(confirm('确定删除本条数据吗？')) {
				$.ajax({
					type : 'POST',
					url : basePath + '/basedata/ekey/eKMessageAction!ajaxDelete.action',
					data : {'c_id' : id},
					dataType : 'json',
					success : function( data ) {
						if( data && data.status === 'Y') {
							alert('删除成功！');
							list();
						} else {
							alert('删除失败！');
						}
					}
				});	
			}
		}
	};
	
	Message.runScript = function(event) {
		if(!event) return true;
		
	    if (event.which == 13 || event.keyCode == 13) {
			var c_name = $('#name').val();
			list({start:0,c_name : c_name});
			return false;
	    }
	    return true;
	};

	var replied = function(v) {
		return v == '1' ? '是' : '否';
	};
	
	global.Message = Message;
})(window,jQuery);
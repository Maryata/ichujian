(function(global, $) {
	var GameTask = {}; // 供应商对象
	var basePath = ''; // 根路径
	var _layPage = {}; // 分页插件对象
	var array = []; // 存放当前页数据对象
	var taskType = [
		{value : 1, text : '上传头像'},
		{value : 2, text : '绑定第三方帐号'},
		{value : 3, text : '签到'},
		{value : 4, text : '分享类'},
		{value : 5, text : '游戏类'},
		{value : 6, text : '趣游戏平台类'}
	];
	
	var list = function(queryParam) {
		queryParam = queryParam || {},
		queryParam.start = queryParam.start || 0; // 默认0条开始
		queryParam.limit = queryParam.limit || 5; // 默认一页五条数据
		$('#page').html('');
		$.ajax({
			type : 'GET',
			url : basePath + '/basedata/ekey/eKGameTaskAction!ajaxList.action',
			data : queryParam,
			dataType : 'json',
			success : function( data ) {
				if( data && data.status === 'Y') {
					array = [];
					$('tr[name="datatr"]').remove(); // 每次加载完成后，清空之前数据
					var l = data.list || []; //  默认列表空
					var count = data.count || 0; // 默认记录0
					var cols = ['C_ID','C_NAME','C_SUBNAME','C_TITLE','C_SCORE'];
					for(var i = 0; i < l.length; ++i) {
						var o = l[i];
						var tr = '<tr name="datatr" class="biaodan-q" style="text-align:center">';

						for(var j = 0; j < cols.length; ++j) {
							tr += '<td nowrap="nowrap">'+(o[cols[j]]||'')+'</td>';
						}

						tr += '<td nowrap="nowrap"><input type="button" class="butt" onclick="GameTask.openEdit(\''+o['C_ID']+'\');" value="编辑">&nbsp;<input type="button" class="butt" onclick="GameTask.del('+o['C_ID']+')" value="删除"></td></tr>';

						$('#dt').append(tr); // 数据表格追加行
						array.push({'C_ID':o['C_ID'],'GameTask' : o});
					}
					
					var rows = queryParam.limit; // 每页行数
					var page = (queryParam.start) / rows + 1; // 当前页
					var totalPages = function() { // 总页数
						if(count % rows ==0) return count / rows;
						return count / rows + 1;
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
	GameTask.init = function(root, laypage) {
		basePath = root || ''; // 初始化项目根路径
		_layPage = laypage; // 初始化分页对象

		initTaskType();

		list(); // 初始化列表
	};

	// 列表查询
	GameTask.query = function(queryParam) {
		list(queryParam);
	};
	// 编辑任务信息
	GameTask.openEdit = function(id) {
		if( id ) {
			$('#editForm :input[name="C_ID"]').val(id);
			var _GameTask = findById(array,id); 
			if(_GameTask) {
				$('#s_type').val(_GameTask.C_TYPE||1);
				$('#c_subName').val(_GameTask.C_SUBNAME|| '');
				$('#c_link').val(_GameTask.C_LINK||'');
				$('#span_logourl').text(_GameTask.C_LOGO);
				$('#c_score').val(_GameTask.C_SCORE||5);
				$('#s_c_isLive').val(_GameTask.C_ISLIVE||1);
				$('#c_title').val(_GameTask.C_TITLE);
			}
		} else {
			$('#editForm')[0].reset();
			$('#editForm :input[name="C_ID"]').val('');
		}
		
		$('.tanchu').show();
		$('.tanchu_bg').show();
		
		$('.tanchu_close').on('click',function(){
	        $('.tanchu_bg').hide();
	        $('.tanchu').hide();
    	});
	};
	// 持久化供应商信息
	GameTask.save = function(obj) {
		obj.disabled = 'disabled';

		var form = $('#editForm');
		var formData = false;
		if (global.FormData){
			formData = new FormData(form[0]);

			formData.append('C_NAME',getTaskTypeName($('#s_type').val()));
		}

		var formAction = basePath + '/basedata/ekey/eKGameTaskAction!ajaxSave.action';
		$.ajax({
			url         : formAction,
			data        : formData ? formData : form.serialize(),
			mimeType    : 'multipart/form-data',
			cache       : false,
			contentType : false,
			processData : false,
			method        : 'POST',
			dataType	: 'JSON',
			success     : function(data, textStatus, jqXHR){
				if(data && data.status === 'Y') {
					alert('保存成功');
					GameTask.closeEdit();
					list();
				} else {
					alert('保存失败');
				}

				obj.disabled = '';
			}
		});
	};
	// 删除任务信息
	GameTask.del = function(id) {
		if(id) {
			if(confirm('确定删除本条数据吗？')) {
				$.ajax({
					type : 'POST',
					url : basePath + '/basedata/ekey/eKGameTaskAction!ajaxDelete.action',
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
	// 取消编辑操作
	GameTask.closeEdit = function() {
		$('.tanchu').hide();
		$('.tanchu_bg').hide();
	};
	
	GameTask.runScript = function(event) {
		if(!event) return true;
		
	    if (event.which == 13 || event.keyCode == 13) {
			var c_name = $('#name').val();
			list({start:0,c_name : c_name});
			return false;
	    }
	    return true;
	};

	var initTaskType = function() {
		$.each(taskType,function(i, item) {
			$('#s_type').append($('<option>',{
				value : item.value,
				text : item.text
			}));
		});
	};

	var findById = function(arr, id) {
		for(var i = 0; i < arr.length; ++i) {
			if(arr[i]['C_ID'] == id) {
				return arr[i]['GameTask'];
			}
		}
		
		return null;
	};

	var getTaskTypeName = function(id) {
		for(var i = 0; i < taskType.length; ++i) {
			if(taskType[i].value == id) {
				return taskType[i].text;
			}
		}
		return '';
	};
	
	global.GameTask = GameTask;
})(window,jQuery);
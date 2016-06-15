(function(global, $) {
	var MallProduct = {}; // 商品管理对象
	var basePath = ''; // 根路径
	var _layPage = {}; // 分页插件对象
	var array = []; // 存放当前页数据对象
	var type = [
		{value : 1, text : 'Q币'},
		{value : 2, text : '话费'},
		{value : 3, text : '礼包'}
	];
	
	var list = function(queryParam) {
		queryParam = queryParam || {},
		queryParam.start = queryParam.start || 0; // 默认0条开始
		queryParam.limit = queryParam.limit || 5; // 默认一页五条数据
		$('#page').html('');
		$.ajax({
			type : 'GET',
			url : basePath + '/basedata/mallProductAction!ajaxList.action',
			data : queryParam,
			dataType : 'json',
			success : function( data ) {
				if( data && data.status === 'Y') {
					array = [];
					$('tr[name="datatr"]').remove(); // 每次加载完成后，清空之前数据
					var l = data.list || []; //  默认列表空
					var count = data.count || 0; // 默认记录0
					var cols = ['C_ID','C_TYPE','C_NAME','C_TOTAL'];
					for(var i = 0; i < l.length; ++i) {
						var o = l[i];
						var tr = '<tr name="datatr" class="biaodan-q" style="text-align:center">';

						for(var j = 0; j < cols.length; ++j) {
							if(j == 1) {
								tr += '<td nowrap="nowrap">'+(getTypeName(o[cols[j]]))+'</td>';
							} else {
								tr += '<td nowrap="nowrap">'+(o[cols[j]]||'')+'</td>';
							}
						}

						tr += '<td nowrap="nowrap"><input type="button" class="butt" onclick="MallProduct.openEdit(\''+o['C_ID']+'\');" value="编辑">&nbsp;<input type="button" class="butt" onclick="MallProduct.del('+o['C_ID']+')" value="删除"></td></tr>';

						$('#dt').append(tr); // 数据表格追加行
						array.push({'C_ID':o['C_ID'],'MallProduct' : o});
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
	MallProduct.init = function(root, laypage) {
		basePath = root || ''; // 初始化项目根路径
		_layPage = laypage; // 初始化分页对象

		// 初始化商品类型
		initType();

		// 初始化可选游戏
		initGame();

		list(); // 初始化列表
	};

	// 列表查询
	MallProduct.query = function(queryParam) {
		list(queryParam);
	};
	// 编辑商品信息
	MallProduct.openEdit = function(id) {
		if( id ) {
			$('#editForm :input[name="C_ID"]').val(id);
			var _MallProduct = findById(array,id); 
			if(_MallProduct) {
				$('#s_type').val(_MallProduct.C_TYPE||1);
				$('#s_c_isLive').val(_MallProduct.C_ISLIVE||1);
				$('#c_name').val(_MallProduct.C_NAME||'');
				$('#s_c_gid').val(_MallProduct.C_GID || -1);
				$('#c_total').val(_MallProduct.C_TOTAL || 0);
				$('#span_imgurl').val(_MallProduct.C_IMG || '');
				$('#c_worth').val(_MallProduct.C_WORTH || 0);
				$('#c_cost').val(_MallProduct.C_COST||0);
				$('#c_illustr').val(_MallProduct.C_ILLUSTR || '');
				$('#editor_id').val(_MallProduct.C_METHOD||'');
				//global.editor_content.html(_MallProduct.C_METHOD||'');
			}
		} else {
			$('#editForm')[0].reset();
			$('#editor_id').val('');
			//global.editor_content.html('');
			$('#editForm :input[name="C_ID"]').val('');
		}
		
		$('.tanchu').show();
		$('.tanchu_bg').show();
		
		$('.tanchu_close').on('click',function(){
	        $('.tanchu_bg').hide();
	        $('.tanchu').hide();
    	});
	};
	// 持久化商品信息
	MallProduct.save = function(obj) {
		obj.disabled = 'disabled';

		var form = $('#editForm');
		var formData = false;
		if (global.FormData){
			formData = new FormData(form[0]);
		}

		var formAction = basePath + '/basedata/mallProductAction!ajaxSave.action';
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
					MallProduct.closeEdit();
					list();
				} else {
					alert('保存失败');
				}

				obj.disabled = '';
			}
		});
	};
	// 删除商品信息
	MallProduct.del = function(id) {
		if(id) {
			if(confirm('确定删除本条数据吗？')) {
				$.ajax({
					type : 'POST',
					url : basePath + '/basedata/mallProductAction!ajaxDelete.action',
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
	MallProduct.closeEdit = function() {
		$('.tanchu').hide();
		$('.tanchu_bg').hide();
	};
	
	MallProduct.runScript = function(event) {
		if(!event) return true;
		
	    if (event.which == 13 || event.keyCode == 13) {
			var c_name = $('#name').val();
			list({start:0,c_name : c_name});
			return false;
	    }
	    return true;
	};

	var initType = function() {
		$.each(type,function(i, item) {
			$('#s_type').append($('<option>',{
				value : item.value,
				text : item.text
			}));
		});
	};

	var findById = function(arr, id) {
		for(var i = 0; i < arr.length; ++i) {
			if(arr[i]['C_ID'] == id) {
				return arr[i]['MallProduct'];
			}
		}
		
		return null;
	};

	var initGame = function() {
		var queryParam = {};
		queryParam.start = queryParam.start || 0; // 默认0条开始
		queryParam.limit = queryParam.limit || 1000; // 默认一页1000条数据
		$.ajax({
			type : 'GET',
			url : basePath + '/basedata/mallProductAction!ajaxListGame.action',
			data : queryParam,
			dataType : 'json',
			success : function( data ) {
				if( data && data.status === 'Y') {
					//console.log(data.list);
					$.each(data.list,function(i,item){
						$('#s_c_gid').append($('<option>',{
							value : item.VALUE,
							text : item.TEXT
						}));
					});
				}
			}
		});
	};

	var getTypeName = function(id) {
		for(var i = 0; i < type.length; ++i) {
			if(type[i].value == id) {
				return type[i].text;
			}
		}
		return '';
	};
	
	global.MallProduct = MallProduct;
})(window,jQuery);
(function(global, $) {
	var GameAct = {}; // 供应商对象
	var basePath = ''; // 根路径
	var _layPage = {}; // 分页插件对象
    var _aid = -1;
	var array = []; // 存放当前页数据对象
	
	var list = function(queryParam) {
		queryParam = queryParam || {},
		queryParam.start = queryParam.start || 0; // 默认0条开始
		queryParam.limit = queryParam.limit || 5; // 默认一页五条数据
        queryParam.aid = _aid; // 活动ID
		queryParam.flag = queryParam.flag || 0;
		$('#page').html('');
		$.ajax({
			type : 'GET',
			url : basePath + '/basedata/ekey/eKGameActAction!ajaxList.action',
			data : queryParam,
			dataType : 'json',
			success : function( data ) {
				if( data && data.status === 'Y') {
					array = [];
					$('tr[name="datatr"]').remove(); // 每次加载完成后，清空之前数据
					var l = data.list || []; //  默认列表空
					var count = data.count || 0; // 默认记录0
					var cols = ['C_ID','C_NICKNAME','C_NAME','C_COMMENT'];
					for(var i = 0; i < l.length; ++i) {
						var o = l[i];
						var tr = '<tr name="datatr" class="biaodan-q" style="text-align:center">';

						for(var j = 0; j < cols.length; ++j) {
							tr += '<td>'+(o[cols[j]]||'')+'</td>';
						}
						var imgStr = o['C_IMG']||'';
						var imgArr = imgStr.split(';');
						tr += '<td>';
						for(var j = 0; j < imgArr.length; ++j) {
							tr += '<img style="max-width: 100px;" src=' + imgArr[j] + '>&#09;&#09;';
                            if(j % 2 == 1) {
                                tr += '<br>';
                            }
						}
						tr += '</td>';

						tr += '<td nowrap="nowrap"><input type="button" class="butt" onclick="GameAct.openEdit(\''+o['C_ID']+'\',\''+o['C_UID']+'\');" value="发放积分"></td></tr>';

						$('#dt').append(tr); // 数据表格追加行
						array.push({'C_ID':o['C_ID'],'GameAct' : o});
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
						        	list({start:(e.curr - 1) * queryParam.limit});
						        }
						    }
						});
					}
				}
			}
		});	
	};
	GameAct.init = function(root,aid, laypage) {
		basePath = root || ''; // 初始化项目根路径
		_layPage = laypage; // 初始化分页对象
        _aid = aid;

		list(); // 初始化列表
	};

	// 列表查询
	GameAct.query = function(queryParam) {
		list(queryParam);
	};
	// 编辑发放积分信息
	GameAct.openEdit = function(id, uid) {
        $('#editForm :input[name="C_ID"]').val(id);
		$('#editForm :input[name="C_SCORE"]').val('');
        var _GA = findById(array,id);
        if(_GA) {
            $('#editForm :input[name="C_UID"]').val(_GA.C_UID);
        }
		$('.tanchu').show();
		$('.tanchu_bg').show();
		
		$('.tanchu_close').on('click',function(){
	        $('.tanchu_bg').hide();
	        $('.tanchu').hide();
    	});
	};
	// 持久发放积分信息
	GameAct.save = function(obj) {
		obj.disabled = 'disabled';

		var form = $('#editForm');
		var formData = false;
		if (global.FormData){
			formData = new FormData(form[0]);
		}

		var formAction = basePath + '/basedata/ekey/eKGameActAction!ajaxSave.action';
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
					alert('积分发放成功');
					GameAct.closeEdit();
					list();
				} else {
					alert('积分发放失败');
				}

				obj.disabled = '';
			}
		});
	};

	// 取消编辑操作
	GameAct.closeEdit = function() {
		$('.tanchu').hide();
		$('.tanchu_bg').hide();
	};
	
	GameAct.runScript = function(event) {
		if(!event) return true;
		
	    if (event.which == 13 || event.keyCode == 13) {
			var c_name = $('#name').val();
			list({start:0,c_name : c_name});
			return false;
	    }
	    return true;
	};

	var findById = function(arr, id) {
		for(var i = 0; i < arr.length; ++i) {
			if(arr[i]['C_ID'] == id) {
				return arr[i]['GameAct'];
			}
		}
		
		return null;
	};
	
	global.GameAct = GameAct;
})(window,jQuery);
(function(global, $) {
	var EkActivity = {}; // 供应商对象
	var basePath = ''; // 根路径
	var _layPage = function() {}; // 分页插件对象
	var array = []; // 存放当前页数据对象
	var _categories = [];
	var _activity = [];
	
	var list = function(queryParam) {
		queryParam = queryParam || {},
		queryParam.start = queryParam.start || 0; // 默认0条开始
		queryParam.limit = queryParam.limit || 10; // 默认一页五条数据

		$('#page').html('');
		$.ajax({
			type : 'GET',
			url : basePath + '/basedata/ekey/eKAdvertInfo!ajaxList.action',
			data : queryParam,
			dataType : 'json',
			success : function( data ) {
				if( data && data.status === 'Y') {
					array = [];
					$('tr[name="datatr"]').remove(); // 每次加载完成后，清空之前数据
					var l = data.list || []; //  默认列表空
					var count = data.count || 0; // 默认记录0
					var cols = ['C_ID','C_NAME','C_ORDER'];
					for(var i = 0; i < l.length; ++i) {
						var o = l[i];
						var tr = '<tr name="datatr" class="biaodan-q" style="text-align:center">';

						for(var j = 0; j < cols.length; ++j) {
							tr += '<td>'+(o[cols[j]]||'')+'</td>';
						}
						tr += '<td><img style="width: 75px;height: 75px;" src="' + o['C_IMG'] + '"></td>';

						tr += '<td nowrap="nowrap"><input type="button" class="butt" onclick="EkActivity.openEdit(\''+o['C_ID']+'\');" value="编辑">&nbsp;<input type="button" class="butt" onclick="EkActivity.del('+o['C_ID']+')" value="删除"></td></tr>';

						$('#dt').append(tr); // 数据表格追加行
						array.push({'C_ID':o['C_ID'],'EkActivity' : o});
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
						        	list({start:(e.curr - 1) * queryParam.limit,c_name : queryParam.c_name});
						        }
						    }
						});
					}
				}
			}
		});	
	};
	EkActivity.init = function(root,laypage) {
		basePath = root || ''; // 初始化项目根路径
		_layPage = laypage; // 初始化分页对象

		list(); // 初始化列表

		_initCategory();
		_initActivity();

        var type = $('#s_c_type').val();

        if( type == 0) {
            _setSValue(_categories);
        } else {
            _setSValue(_activity);
        }

		$('#s_c_type').on('change',function() {
			var v = $(this).val();
			if( v == 0) {
                _setSValue(_categories);
			} else {
                _setSValue(_activity);
			}
		});
	};

    function _setSValue(arr) {
        $('#s_c_e').find('option').remove().end();

        $('#s_c_e').append('<otpion></otpion>');
        $.each(arr,function(i, item) {
            $('#s_c_e').append($('<option>',{
                value : item.C_ID,
                text : (item.C_NAME || item.C_TITLE).substr(0, 23)
            }));
        });
    }

	function _initCategory() {
		$.ajax({
			type : "GET",
            async : false,
			url : basePath + "/basedata/ekey/eKAdvertInfo!ajaxCategoryList.action",
			dataType : "json",
			success : function( data ) {
				if( data && data.status === "Y") {
					_categories = data.categories
				} else {

				}
			}
		});
	}

	function _initActivity() {
		$.ajax({
			type : "GET",
            async : false,
			url : basePath + "/basedata/ekey/eKAdvertInfo!ajaxActivityList.action",
			dataType : "json",
			success : function( data ) {
				if( data && data.status === "Y") {
					_activity = data.activity;
				} else {

				}
			}
		});
	}

	// 列表查询
	EkActivity.query = function(queryParam) {
		list(queryParam);
	};
    // 删除app信息
    EkActivity.del = function(id) {
        if(id) {
            if(confirm("确定删除本条数据吗？")) {
                $.ajax({
                    type : "POST",
                    url : basePath + "/basedata/ekey/eKAdvertInfo!ajaxDelete.action",
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
	// 编辑app信息
	EkActivity.openEdit = function(id) {
        if( id ) {
            $('#editForm :input[name="C_ID"]').val(id);
            var _AdvertInfo = findById(array,id);
            if(_AdvertInfo) {
                $('#editForm :input[name="C_NAME"]').val(_AdvertInfo.C_NAME||'');

				$('#editForm :input[name="C_ORDER"]').val(_AdvertInfo.C_ORDER || 0);

                $('#s_c_islive').val(_AdvertInfo.C_ISLIVE || 1);
                $('#s_c_type').val(_AdvertInfo.C_TYPE || 0);

				if( $('#s_c_type').val() == 0) {
					_setSValue(_categories);
				} else {
					_setSValue(_activity);
				}

				$('#s_c_e').val(_AdvertInfo.C_EID || 1);

                $('#span_img').text(_AdvertInfo.C_IMG||'');
            }
        } else {
            $('#editForm')[0].reset();
            $('#editForm :input[name="C_ID"]').val('');
            $('#span_img').text('');
        }

        $('.tanchu').show();
        $('.tanchu_bg').show();

        $('.tanchu_close').on('click',function(){
            $('.tanchu_bg').hide();
            $('.tanchu').hide();
        });
	};
	// 持久化app信息
	EkActivity.save = function(obj) {
		obj.disabled = 'disabled';

		var form = $('#editForm');
		var formData = false;
		if (global.FormData){
			formData = new FormData(form[0]);
		}

		var formAction = basePath + '/basedata/ekey/eKAdvertInfo!ajaxSave.action';
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
					EkActivity.closeEdit();
					list();
				} else {
					alert('保存失败');
				}

				obj.disabled = '';
			}
		});
	};

	// 取消编辑操作
	EkActivity.closeEdit = function() {
		$('.tanchu').hide();
		$('.tanchu_bg').hide();
	};
	
	EkActivity.runScript = function(event) {
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
				return arr[i]['EkActivity'];
			}
		}
		
		return null;
	};
	
	global.EkActivity = EkActivity;
})(window,jQuery);
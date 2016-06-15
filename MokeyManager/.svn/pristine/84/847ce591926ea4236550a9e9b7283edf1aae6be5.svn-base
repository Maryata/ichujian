;
(function(global, $) {
	var Statistics = {};
	var separator = '-';
	var defaultDay = '01';
	var basePath = '';
	
	var dt1 = []; // 数据表格1 存放品牌库存、安装、激活 统计信息
	var dt2 = []; // 数据表格2 存放时间维度内，安装、激活 统计信息

	Statistics.init = function(_basePath) {
		basePath = _basePath;
		
		_0();
		
		_1();
		
		_2();
	};
	
	Statistics.query = function(condition) {
		_0();
		
		_1(condition);
		
		_2(condition);
	};
	
	Statistics.exp = function() {
		$('#i_dt1').val(JSON.stringify(dt1));
		$('#i_img1').val('');
		
		$('#i_dt2').val(JSON.stringify(dt2));
		$('#i_img2').val('');
		
		$('#myfrom').submit();
	};
	
	/**
	 * 各供应商下载、激活、库存信息
	 */
	var _0 = function() {
		$.ajax({
			type : 'post',
			url : basePath + '/report/statisticsAction!brandUser.action',
			dataType : 'json',
			success : function( data ) {
				if( data && data.status === "Y") {
					var l = data.list || []; //  默认列表空
					var $dt = $('#dt1');
					$dt.html('');
					
					$dt.append('<tr class="biaodan-top"><td>供应商名称</td><td>库存数</td><td>下载数</td><td>激活数</td></tr>');
					
					
					dt1.push({r : ['供应商名称','库存数','下载数','激活数'] });
					for(var i = 0; i < l.length; ++i) {
						var o = l[i];
						// 供应商名称、库存数、下载数、激活数
						var cols = ['C_SUPPLIER_NAME','C1','C2','C3'];
						var tr = '<tr name="datatr" class="biaodan-q" style="text-align:center">';
						
						var row = [];
						for(var j = 0; j < cols.length; ++j) {
							var _td = o[cols[j]]; 
							tr += '<td nowrap="nowrap">'+_td+'</td>';
							row.push(_td);
						}
						dt1.push({r : row});
						
						tr += '</tr>';

						$dt.append(tr); // 数据表格追加行
					}
					
					$('#i_dt0').val(JSON.stringify(dt1)); // 导出时用
				}
			},
			error : function (jqXHR, textStatus, errorThrown) {
				if(typeof console !== 'undefiend') {
					console.debug(jqXHR);
					console.debug(textStatus);
					console.debug(errorThrown);
				}
			}
		});
	};

	/**
	 * 一段时间内，各供应商激活、下载信息(用户增长情况)
	 * 
	 * @param 日期条件，格式：{sDate:'2015-08-01',eDate:'2015-08-21'}
	 */
	var _1 = function(dateCondition) {
		dateCondition = dateCondition || _getDateCondition();
		
		$.ajax({
			type : 'post',
			url : basePath + '/report/statisticsAction!userGrowth.action',
			data : dateCondition,
			dataType : 'json',
			success : function( data ) {
				if( data && data.status === "Y") {
					var l = data.list || []; //  默认列表空
					
					var $dt = $('#dt2');
					$dt.html('');
					var categories = []; // 横轴数据，日期
					var series = []; // 纵轴数据，下载、激活
					var cells = [];
					cells[0] = [];
					cells[1] = [];
					cells[2] =[];
					cells[0].push('');
					cells[1].push('下载');
					cells[2].push('激活');
					
					var d0 = {name:'下载',data:[]};
					var d1 = {name:'激活',data:[]};
					for(var i = 0; i < l.length; ++i) {
						var o = l[i];
						
						// 日期
						cells[0].push(o['D']);
						categories.push(o['D']);
						// 下载数
						cells[1].push(o['C1']||0);
						d0.data.push(o['C1']||0);
						// 激活数
						cells[2].push(o['C2']||0);
						d1.data.push(o['C2']||0);
					}
					
					series.push(d0);
					series.push(d1);
					
					//dt2 = cells;
					dt2.push({r : cells[0]});
					dt2.push({r : cells[1] });
					dt2.push({r : cells[2]});
					
					$('#i_dt1').val(JSON.stringify(dt2));
					
					var len = cells[0].length;
					var r = 3;
					for(var j = 0; j < r; ++j) {
						var tr = '<tr name="datatr" class="biaodan-q" style="text-align:center">';
						for(var i = 0; i < len; ++i) {
							tr += '<td nowrap="nowrap">'+cells[j][i]+'</td>';
						}
						$dt.append(tr); // 数据表格追加行
					}
					
					$('#container0').highcharts({
				        title: {
				            text: '下载激活情况'
				        },
				        xAxis: {
				            categories: categories
				        },
				        yAxis: {
				            title: {
				                text: '激活数'
				            }
				        },
				        tooltip: {
				            valueSuffix: '个'
				        },
				        credits : {
				        	enabled : false
				        },
				        series: series
				    });
					
					var svg = $('#container0').highcharts().getSVG();
					$('#i_svg0').val(svg);
				}
			},
			error : function (jqXHR, textStatus, errorThrown) {
				if(typeof console !== 'undefiend') {
					console.debug(jqXHR);
					console.debug(textStatus);
					console.debug(errorThrown);
				}
			}
		});
	};
	
	var _2 = function(dateCondition) {
		dateCondition = dateCondition || _getDateCondition();
		
		$.ajax({
			type : 'post',
			url : basePath + '/report/statisticsAction!brandUserGrowth.action',
			data : dateCondition,
			dataType : 'json',
			success : function( data ) {
				if( data && data.status === "Y") {
					var l = data.list || []; //  默认列表空
					var categories = data.categories||[]; // 横轴数据，日期
					var series = []; // 纵轴数据，各品牌每日数据
					var objArr = []; // 品牌数组
					var obj = {}; // 纵轴对象
					
					// 初始化品牌数组,按highcharts纵轴格式要求构建
					// 品牌维度
					for(var j = 0; j < l.length; ++j) {
						var o = l[j];
						var sname = o['C_SUPPLIER_NAME']||'MAGICLICK';
						if(typeof obj.name == 'undefined') {
							obj.name = sname;
							obj.data = [];
							
							objArr.push({sname:sname,obj : obj});
						} else if(obj.name != sname) {
							obj = {};
							obj.name = sname;
							obj.data = [];
							
							objArr.push({sname:sname,obj : obj});
						}
					}
					
					// 各品牌每日的数据
					for(var i = 0; i < categories.length; ++i) {
						var x = categories[i];
						
						var _tempSname = ''; // 临时存放供应商名称，用以对比是否读取下一个供应商
						var _flag = false; // 标识,各供应商在每个日期是否有添加数据
						
						var p = 0;
						for(var j = 0; j < l.length; ++j) {
							var o = l[j];
							var sname = o['C_SUPPLIER_NAME']||'MAGICLICK';
							var day = o['D'];
							var count = o['C'];
							
							if(!_tempSname) {
								_tempSname = sname;
							}
							
							if(_tempSname != sname) { // 说明是另外一个供应商 --这边假定供应商名称唯一
								p = 0; // 一个供应商一天应该只有一笔统计数据
								if(!_flag) { // 如果没有添加数据
									obj.data.push(0);
									_flag = false;
									_tempSname = sname;
								} else {
									_flag = false; // 重置标识
									_tempSname = sname;
								}
							}
							
							obj = (function(sname) {
								for(var k = 0; k < objArr.length; ++k) {
									var _obj = objArr[k];
									
									if(_obj.sname == sname) {
										return _obj.obj;
									}
								}
							}(sname));
							
							
							
							if(x == day) {
								_flag = true; // 有添加数据
								
								if(p != 0) {
									var _c = obj.data.pop();
									obj.data.push(_c + count);
								} else {
									obj.data.push(count);
								}
								
								p++;// 同一个供应商，在一天内有多笔数据（这种情况在供应商定制过程中，没有品牌名称，选择用‘触键’，或者是数据不规范会出现
							}
							
							if(j == l.length - 1) { // 如果是最后一个供应商
								if(!_flag) { // 如果没有添加数据
									obj.data.push(0);
								}
							}
						}
					}
					
					for(var i = 0; i < objArr.length; ++i) {
						series.push(objArr[i].obj);
					}
					
					$('#container1').highcharts({
				        title: {
				            text: '各品牌日激活情况'
				        },
				        xAxis: {
				            categories: categories
				        },
				        yAxis: {
				            title: {
				                text: '激活数'
				            }
				        },
				        tooltip: {
				            valueSuffix: '个'
				        },
				        credits : {
				        	enabled : false
				        },
				        series: series
				    });
					
					var svg = $('#container1').highcharts().getSVG();
					$('#i_svg1').val(svg);
				}
			},
			error : function (jqXHR, textStatus, errorThrown) {
				if(typeof console !== 'undefiend') {
					console.debug(jqXHR);
					console.debug(textStatus);
					console.debug(errorThrown);
				}
			}
		});
	};

	/**
	 * 返回{start:当前月第一天,end:当前日期}
	 */
	var _getDateCondition = function() {
		var d = new Date();
		var year = d.getFullYear();
		var month = d.getMonth() + 1;
		var day = d.getDate();

		return {
			sDate : year + separator + month + separator + defaultDay,
			eDate : year + separator + month + separator + day
		};
	};
	
	

	global.Statistics = Statistics;
})(window, jQuery);
//Ext.namespace("Ext.ux");
SYS_CONSTANT_PAGESIZE = 20;
Ext.sendDemoQuery = function() {

	return {

		formId : 'sendDemoQuery_formId',
		gridId : 'sendDemoQuery_gridId',
		addWinId : 'sendDemoQuery_addWinId',
		textwidth : 120,
		init : function() {

		},
		load : function() {
			var _this = this;

			var time_s = _this.createDateField( {
				fieldLabel : "开始时间",
				name : 'time_s',
				value : new Date().add(Date.DAY, -7)
			});
			var time_e = _this.createDateField( {
				fieldLabel : "结束时间",
				name : 'time_e',
				value : new Date()
			});
			var sendDemoCombostore = new Ext.data.ArrayStore( {
				fields : [ 'id', 'name' ],
				data : [ [ 2, '是' ], [ 1, '否' ],[3,'全部'] ]
			});
			// 获得代理商的 下拉框开始
		var suppStore = new Ext.data.Store( {
			// autoLoad: true,
			proxy : new Ext.data.HttpProxy( {
				url : rootPath + "basedata/supplierInfo!getSuppliers.action",
				timeout : 60000
			}),
			reader : new Ext.data.JsonReader( {
				root : 'list'
			}, Ext.data.Record.create( [ {
				name : 'C_SUPPLIER_CODE'
			}, {
				name : 'C_SUPPLIER_NAME'
			} ]))
		});

		suppStore.load( {
			callback : function(obj) {
				suppStore.insert(0, new suppStore.recordType( {
					C_SUPPLIER_CODE : '',
					C_SUPPLIER_NAME : '全部'
				}));
			}
		});

		var supp_Comb = new Ext.form.ComboBox( {
			typeAhead : true,
			loadStatus : false,
			triggerAction : 'all',
			store : suppStore,
			fieldLabel : '代理商',
			id : _this.formId + 'supp',
			name : 'supp',
			allowBlank : false,
			editable : false,
			mode : 'local',
			displayField : 'C_SUPPLIER_NAME',
			valueField : 'C_SUPPLIER_CODE',
			resizable : true,
			editable : false,
			width : _this.textwidth,
			selectOnFocus : true
		});
		// 获得 代理商下拉框结束

		var isSendDemo = new Ext.form.ComboBox( {
			fieldLabel : '是否送样',
			store : sendDemoCombostore,
			id : _this.formId + 'isSendDemo',
			displayField : 'name',
			valueField : 'id',
			name : 'isSendDemo',
			triggerAction : 'all',
			emptyText : '',
			allowBlank : false,
			blankText : '',
			editable : false,
			mode : 'local'
		});

		var pnlMain = new Ext.form.FormPanel( {
			id : _this.formId + 'formPanel',
			autoWidth : true,
			autoHeight : true,
			bodyBorder : false,
			border : false,
			// frame false,
			labelWidth : 60,
			align : 'center',
			items : [ {
				layout : 'column',
				border : false,
				items : [ {
					columnWidth : .20,
					layout : 'form',
					items : [ time_s ]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ time_e ]
				}, {
					columnWidth : .20,
					layout : 'form',
					items : [ supp_Comb ]
				}, {
					columnWidth : .10,
					layout : 'form',
					items : [ isSendDemo ]
				}, {
					columnWidth : .20,
					border : false,
					layout : 'column',
					items : [ {
						columnWidth : .4,
						border : false,
						layout : 'form',
						items : [ {
							xtype : 'button',
							text : '查询',
							width : 50,
							handler : function() {
								_this.priQueryFun();
							}
						} ]
					} ]
				} ]
			} ]
		});

		var grid = _this.createGrid();

		Manager.sys.Utils.antoScrollerInit(grid, pnlMain);
		grid.add(pnlMain);

	

		var pnl = new Ext.Panel( {
			id : _this.formId + 'panel',
			bodyStyle : 'border:0px solid #99bbe8',// padding:5 5 5 5;
			renderTo : 'mainDivId',// applyTo
			items : [ grid ]
		})
//		_this.priQueryFun(); // 初始化
	},

	createDateField : function(option) {
		var _this = this;
		var dateField = {
			xtype : 'datefield',
			name : option.name,
			fieldLabel : option.fieldLabel,
			width : _this.textwidth,
			format : 'Y-m-d',
			id : _this.formId + option.name
		};
		if (option.value) {
			dateField.value = option.value;
		}
		return dateField;
	},
	
	
	//------------------------------------------------创建创建点击情况FormPanel   11t
	createhitFormPanel : function(params){
		var _this = this;
		
		//创建点击详细情况 
		function createHitGrid() {
		 //定义列
			var hitResultsRecord = Ext.data.Record.create( [ {
				name : 'keyIndex'
			}, {
				name : 'oneOrLongClick'
			}, {
				name : 'hitTimes'
			}, {
				name : 'oneKeyCount'
			}, {
				name : 'sum'
			}]);
			// 列表用到的数据源
			var hitGrid_store = new Ext.data.Store( {
				proxy : new Ext.data.HttpProxy( {
					url : rootPath+"basedata/sendDemoQuery!getHitTimes.action",
					timeout : 60000
				}),
				reader : new Ext.data.JsonReader( {
					root : 'list'
				}, hitResultsRecord),
				baseParams : {}
			});
			
//			hitGrid_store.baseParams.id =params.c_imei ;
			
			
			//-----点击IMEI弹出的表格	 
			var hitTempArray = [ {    
				header : "键位",
				dataIndex : 'keyIndex',
				width : 35
			}, {
				header : "单击/长按",
				sortable : true,  
				dataIndex : 'oneOrLongClick',
				width : 35
			},{
				header : "平均数",
				sortable : true,  
				dataIndex : 'hitTimes',
				width : 35
			},{
				header : "总次数",
				sortable : true,  
				dataIndex : 'oneKeyCount',
				width : 35
			},{
				header : "合计",
				sortable : true,  
				dataIndex : 'sum',
				width : 35
			}];
			var hitResultColumnModel = new Ext.grid.ColumnModel(hitTempArray); 
		
			var hitGrid = new Ext.grid.EditorGridPanel( {
				id: 'hitGrid',
				store : hitGrid_store,
				scrollable : true,
				bodyStyle : 'width:100%;border:1px solid #99bbe8;',
				cm : hitResultColumnModel,
				viewConfig : {
					forceFit : true,
					autoWrapRow : true
				}, 
				autoWidth :true,
				height : 150, 
				loadMask : {
					msg : LOADMASK_MSG
				}
			});
			return hitGrid;
			}
		
		 
		
		//创建隐藏的 点击记录  表22
		function createHistoryHitGrid() {
		var hitHistoryResultsRecord = Ext.data.Record.create( [ {
			name : 'keyIndex'
		}, {
			name : 'oneOrLongClick'
		}, {
			name : 'times'
		}, {
			name : 'setContent'
		}]);  
		// 列表用到的数据源
		var hitHistoryGrid_store = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : rootPath+"basedata/sendDemoQuery!getHitHistory.action",
				timeout : 60000
			}),
			reader : new Ext.data.JsonReader( {
				totalProperty : 'count',
				root : 'list'
			}, hitHistoryResultsRecord),
			baseParams : {}
		});
		
//		hitGrid_store.baseParams.id =params.c_imei ;
		
		
		//-----点击IMEI弹出的表格	 
		var hitHistoryTempArray = [ {    
			header : "键位",
			dataIndex : 'keyIndex',
			width : 35
		}, {
			header : "单击/长按",
			sortable : true,  
			dataIndex : 'oneOrLongClick',
			width : 35
		},{
			header : "时间",
			sortable : true,  
			dataIndex : 'times',
			width : 35
		},{
			header : "启动内容",
			sortable : true,  
			dataIndex : 'setContent',
			width : 35
		}];
		var hitHistoryResultColumnModel = new Ext.grid.ColumnModel(hitHistoryTempArray); 
	
		var hitHistoryGrid = new Ext.grid.EditorGridPanel( {
			id: 'hitHistoryGrid',
			title:'用户使用详情', 
			store : hitHistoryGrid_store,
			hidden:false,
			scrollable : true,
			bodyStyle : 'width:100%;border:1px solid #99bbe8;',
			cm : hitHistoryResultColumnModel,
			viewConfig : {
				forceFit : true,
				autoWrapRow : true
			}, 
			autoWidth :true,
			height : 150, 
			loadMask : {
				msg : LOADMASK_MSG
			},
			bbar : new Ext.PagingToolbar({//分页工具条
    			pageSize :10,
    			store : hitHistoryGrid_store
    		})
		});
		return hitHistoryGrid;
		}  
		
		
		
		
		
		
		var time_start = _this.createDateField({fieldLabel:"激活时间",
			name:'hitStart',value:new Date().add(Date.DAY, -7)});
		var time_end= _this.createDateField({fieldLabel:"到",name:'hitEnd',value:new Date()});
		
		
		var hitFormPanel = new Ext.form.FormPanel( {//abc
			id : 'hitFormPanel',
			autoWidth : true,
			autoHeight : true,
			border :false,
			frame : false,
			labelWidth : 2,
			items : [{
				layout : 'column',
				border :false,
				items : [{
					columnWidth : .12,
					layout : 'form',
					items : [  
					    new Ext.form.Radio({ 
					    id:'option',
		                name:'option',
		                value:'true',
		                height:22,
		                value:1,
		                checked: true,//名字相同的单选框做为一组  
		                boxLabel:'截至今天'  
		            })  ]
				},{
					columnWidth : .10,
					layout : 'form',
					items : [  
					    new Ext.form.Radio({  
		                name:'option',//名字相同的单选框做为一组  
		                value:2,
		                height:22,
		                //fieldLabel : '&nbsp;&nbsp;',
		                boxLabel:'&nbsp;'   
		            })  ]
				},
				{
					columnWidth : .25,
					labelWidth : 60,
					layout : 'form',
					items : [time_start]
				},
				{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 60,
					items : [time_end]
				},
				{
					columnWidth : .10,
					layout : 'form',
					border :false,
					items : [{
						xtype : 'button',
						text : '查询',
						width : 50,
						handler : function() {
							_this.getHitTimesByDate();
							_this.getHitTimesHistoryByDate();
						}}]
				}
				]
			},{
				//columnWidth : .33,
				layout : 'form',
				items : [createHitGrid(params)]
			},{
				//columnWidth : .33,
				layout : 'form',
				items : [createHistoryHitGrid()]
			}]

			
			
			
		});
		return hitFormPanel;
	}
	,
	createSetFormPanel : function(params){
		var _this = this;
		
		var pnlMain = new Ext.form.FormPanel( {
			id : 'setFormPanel',
			autoWidth : true,
			autoHeight : true,
			border :false,
			//frame false,
			labelWidth : 80,
			items : [{
					columnWidth : .20,
					border :false,
					layout : 'column',
					items : [{
						columnWidth : .4,
						border :false,
						layout : 'form',
						items : [
						]
					}]
				},{
					//columnWidth : .33,         //1t
					layout : 'form',
					items : [_this.cretaDetailGrid()]   
				},{
					//columnWidth : .33,
					layout : 'form',
					items : [_this.createSetDetailGrid(params)]
				}]

				
				
				
			});
			return pnlMain;
		}
		,
		createSetDetailGrid : function(params) {

			var setDetailResultsRecord = Ext.data.Record.create( [ {
				name : 'C_KEY'
			}, {
				name : 'TYPE'
			}, {
				name : 'C_ACTIONDATE'
			}, {
				name : 'C_APP_NAME'
			}]);  
			// 列表用到的数据源
			
//			var Store1 = Ext.getCmp('hitGrid').store;
			
			var setDetailGrid_store = new Ext.data.Store( {
				proxy : new Ext.data.HttpProxy( {
					url : rootPath+"basedata/sendDemoQuery!getSetHistory.action",
					timeout : 60000
				}),
				reader : new Ext.data.JsonReader( {
					totalProperty : 'count',
					root : 'list'
				}, setDetailResultsRecord),
				baseParams : {params : { id : params.c_imei }}
			});
			//-----点击IMEI弹出的表格	 
			var setDetailTempArray = [ {    
				header : "键位",
				dataIndex : 'C_KEY',
				width : 100
			}, {
				header : "单击/长按",
				sortable : true,  
				dataIndex : 'TYPE',
				width : 100
			},{
				header : "时间",
				sortable : true,  
				dataIndex : 'C_ACTIONDATE',
				width : 100
			},{
				header : "设置内容",
				sortable : true,  
				dataIndex : 'C_APP_NAME',
				width : 100
			}];
			var setDetailResultColumnModel = new Ext.grid.ColumnModel(setDetailTempArray); 
		
			var setDetailGrid = new Ext.grid.EditorGridPanel( {
				id: 'setDetailGrid',
				title:'用户设置详情',
				store : setDetailGrid_store,
				hidden:false,
				scrollable : true,
				bodyStyle : 'width:100%;border:1px solid #99bbe8;',
				cm : setDetailResultColumnModel,
				viewConfig : {
					forceFit : true,
					autoWrapRow : true
				}, 
				autoWidth :true,
				height : 233, 
				loadMask : {
					msg : LOADMASK_MSG
				},
				bbar : new Ext.PagingToolbar({//分页工具条
	    			pageSize :SYS_CONSTANT_PAGESIZE,
	    			store : setDetailGrid_store
	    		})
			});
			return setDetailGrid;
			
	
		},
	priQueryFun : function() {
		var _this = this;

		var gridStore = Ext.getCmp(_this.gridId).store;
		// gridStore.removeAll();

		var paramJson = {};
		// 值得传递
		paramJson.time_s = Ext.getCmp(_this.formId + 'time_s').getRawValue();
		paramJson.time_e = Ext.getCmp(_this.formId + 'time_e').getRawValue();
		paramJson.supplier = Ext.getCmp(_this.formId + 'supp').getValue();
		paramJson.isSendDemo = Ext.getCmp(_this.formId + 'isSendDemo')
				.getValue();

		gridStore.baseParams = paramJson;
		gridStore.load( {
			params : {
				start : 0,
				limit : SYS_CONSTANT_PAGESIZE
			},
			callback : function(obj) {
				if (obj.length == 0) {
				}
			}
		});

	},
	
	getHitTimesByDate : function(){
		var _this = this;

		var Store = Ext.getCmp('hitGrid').store;
		// gridStore.removeAll();
		var paramJson = {};
		// 值得传递
		paramJson.hitStart = Ext.getCmp(_this.formId + 'hitStart').getRawValue();
		paramJson.hitEnd = Ext.getCmp(_this.formId + 'hitEnd').getRawValue();
		paramJson.option=Ext.getCmp('option').getValue();
		paramJson.id=Store.baseParams.id;

		Store.baseParams = paramJson;
		Store.load( {
			params : {
			
			},
			callback : function(obj) {
				if (obj.length == 0) {
				}
			}
		}); 

		
		
	},     //根据日期 查询出  点击 历史记录
	getHitTimesHistoryByDate : function(){
		var _this = this;
		
		var Store1 = Ext.getCmp('hitGrid').store;
		var Store = Ext.getCmp('hitHistoryGrid').store;
		// gridStore.removeAll();
		var paramJson = {};
		// 值得传递
		paramJson.hitStart = Ext.getCmp(_this.formId + 'hitStart').getRawValue();
		//----------
		paramJson.hitEnd = Ext.getCmp(_this.formId + 'hitEnd').getRawValue();
		paramJson.option=Ext.getCmp('option').getValue();
		paramJson.id=Store1.baseParams.id;
		
		Store.baseParams = paramJson;
		Store.load( {
			params : {
			start : 0,
			limit : 7
		},
		callback : function(obj) {
			if (obj.length == 0) {
			}
		}
		}); 
		
		
		
	},
	

	createGrid : function() {
		var _this = this;

		var resultsRecord = Ext.data.Record.create( [ {
			name : 'supplier'
		}, {
			name : 'brand'
		}, {
			name : 'code'
		}, {
			name : 'left'
		}, {
			name : 'sum'
		}, {
			name : 'active'
		}, {
			name : 'details'
		} ]);
		// 列表用到的数据源
		var resultsGrid_store = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : rootPath + "basedata/sendDemoQuery!getSendData.action",
				timeout : 60000
			}),
			reader : new Ext.data.JsonReader( {
				totalProperty : 'count',
				root : 'list'
			}, resultsRecord),
			baseParams : {}
		});
		resultsGrid_store.reload();
		// var resultsSm = new Ext.grid.CheckboxSelectionModel();

		var tempArray = [
				{
					header : "公司名称",
					sortable : true,
					dataIndex : 'supplier',
					width : 150
				},
				{
					header : "品牌名称",
					sortable : true,
					dataIndex : 'brand',
					width : 150
				},
				{
					header : "编号",
					sortable : true,
					dataIndex : 'code',
					width : 150
				},
				{
					header : "订单数",
					sortable : true,
					dataIndex : 'sum',
					width : 150
				},
				{
					header : "库存数",
					sortable : true,
					dataIndex : 'left',
					width : 150
				},
				{
					header : "激活数",
					sortable : true,
					dataIndex : 'active',
					width : 150
				}];

		// -------------------------------------------------------------------------------------------------------------------------------------------------------

		var expander = new Ext.grid.RowExpander( { // 新建一个 展开对象
					tpl : new Ext.XTemplate('<div class="detailData">', '','</div>')
				});

		expander.on("expand", function(expander, r, body, rowIndex) {
					// 查找 grid
			if (Ext.DomQuery.select("div.x-panel-bwrap", body).length == 0) {
			
				// var data=r.json[3]; //-- 这里是从json 数据的第四个数据加载进去
				// 在这里，我只需要把 从后台查出的数据 以JSON格式传入进来就可以了！
				
				
//				 alert(Ext.getCmp(_this.formId + 'time_s').getRawValue());
//				 alert(Ext.getCmp(_this.formId + 'time_e').getRawValue());
//				 alert(Ext.getCmp(_this.formId + 'supp').getValue());
//					 alert(Ext.getCmp(_this.formId + 'isSendDemo').getValue());    
//				
				
//					  Ext.getCmp('detailGrid').store.baseParams.start=Ext.getCmp(_this.formId + 'time_s').getRawValue();
				
				var innerResultsRecord = Ext.data.Record.create( [
						{
							name : 'C_ID'
						}, {
							name : 'NUM'
						}, {
							name : 'C_ACTIVECODE'
						}, {
							name : 'C_JOBNUMBER'
						}, {
							name : 'C_IMEI'
						}, {
							name : 'C_ACTIONDATE'
						}, {
							name : 'C_SYSDATE'
						}, {
							name : 'C_ACTIONCOUNT'
						}, {
							name : 'C_BRAND'
						}, {
							name : 'C_CODE_IMEI'
						}, {
							name : 'START_DATE'
						} ]);
				// 列表用到的数据源
				var innerStore = new Ext.data.Store(
					{
						proxy : new Ext.data.HttpProxy(
								{
									url : rootPath
											+ "basedata/sendDemoQuery!getActiveList.action",
									timeout : 60000
								}),
						reader : new Ext.data.JsonReader( {
							totalProperty : 'count',
							root : 'list'
						}, innerResultsRecord),
						baseParams : {}
					});
				// 将外面的一行的RECORD 传进来 获得ID
				innerStore.baseParams.id = r.get('code'); //这里是根据67 查询出激活表里面的激活信息,现在要根据时间和 是否是送样进行查询
				innerStore.baseParams.time_s = Ext.getCmp(_this.formId + 'time_s').getRawValue();
				innerStore.baseParams.time_e = Ext.getCmp(_this.formId + 'time_e').getRawValue();
				
				
				// 这里是用来 显示内部的 样式
				var innerTempArray = [
						{
							header : "C_ID",
							hidden : true,
							dataIndex : 'C_ID',
							width : 20
						},
						{
							header : "序号",
							sortable : true,
							dataIndex : 'NUM',
							width : 20
						},
						{
							header : "IMEI",
							sortable : true,
							dataIndex : 'C_IMEI',
							renderer : function(value, meta, record) {
								var formatStr = "<a style='cursor: hand;text-decoration:none;' href='javascript:void();' onclick='javscript:return false;' class='deviceinfo'>{0}</a>";
								var resultStr = String.format(
										formatStr, value);
								return "<div class='controlBtn'>"
										+ resultStr + "</div>";
							}.createDelegate(this)
						}, {
							header : "设备名称",
							sortable : true,
							dataIndex : 'C_BRAND'
						}, {
							header : "激活时间",
							sortable : true,
							dataIndex : 'C_SYSDATE'
						}, {
							header : "激活码",
							sortable : true,
							dataIndex : 'C_ACTIVECODE'
						}, {
							header : "启动时间",
							hidden : true,
							dataIndex : 'START_DATE'
						}, {
							header : "激活次数",
							sortable : true,
							dataIndex : 'C_ACTIONCOUNT',
							width : 50
						} ];

				var innerResultColumnModel = new Ext.grid.ColumnModel(innerTempArray);

				var innerGrid = new Ext.grid.GridPanel( {
					id:'innerGrid',
					store : innerStore,
					cm : innerResultColumnModel,
					renderTo : Ext.DomQuery.select("div.detailData", body)[0],
					autoWidth : true,
					height : 200,
					viewConfig : {
	    				forceFit : true
	    			},
					tbar : [ {
						xtype : 'label',
						text : '请输入IMEI：'
					}, {
						xtype : 'textfield',
						id : 'KeyWord'
					}, {
						text : '搜索',
						handler : function() {
							_this.innerPriQueryFun(innerGrid);
						}
					} ],

					bbar : new Ext.PagingToolbar( {// 分页工具条
								pageSize : 10,
								store : innerStore
							})
				// 将 里面的那些数据成为插件，插入到外边那行里面
						});
				_this.innerPriQueryFun(innerGrid);
				innerGrid.on('cellclick', function (grid, rowIndex, columnIndex, e) {
		  		  var btn = e.getTarget('.controlBtn');   
		  		  if (btn) {
		  			var t = e.getTarget();   
		  			var record = grid.getStore().getAt(rowIndex);
		  			var control = t.className;
		  			if(record&&control=='deviceinfo'){
							_this.loadImeiInfo({c_imei:record.get('C_IMEI')});
		  	            return true;
		  			}else{
		  			}
		  		  }   
		  		},   
		  		this); 
			}
		});

		// ------------------------------------------------

		var resultColumnModel = new Ext.grid.ColumnModel(tempArray);

		var grid = new Ext.grid.EditorGridPanel( {
			store : resultsGrid_store,
			scrollable : true,
			id : _this.gridId,
			clicksToEdit : 1,
			bodyStyle : 'width:100%;border:1px solid #99bbe8;',
			// sm : resultsSm,
			cm : resultColumnModel,
			viewConfig : {
				forceFit : true
			},
			width : mainWidth,
			height : mainHeigth,
			plugins : [ expander ], // 在这里加入嵌套组件------------------------------
			loadMask : {
				msg : LOADMASK_MSG
			},
			// tbar : funct_tbars,
			bbar : new Ext.PagingToolbar( {// 分页工具条
						pageSize : SYS_CONSTANT_PAGESIZE,
						store : resultsGrid_store
					})
		});
		return grid;
	},
	innerPriQueryFun : function(grid) {
		var _this = this;
		var gridStore = grid.store;
		var paramJson = {};
		paramJson = gridStore.baseParams;
		paramJson.c_imei = Ext.getCmp('KeyWord').getValue();
		gridStore.load( {
			params : {
				start : 0,
				limit : 10
			},
			callback : function(obj) {
				if (obj.length == 0) {
				}
			}
		});

	},

	createInnerFrom : function(fid) {
		var _this = this;
		var innerImeiField = new Ext.form.TextField( { 
					// id : _this.formId + 'c_imei',
					xtype : 'textfield',
					fieldLabel : 'IMEI',
					name : 'c_imei',
					width : _this.textwidth,
					readOnly : false,
					maxLength : 50,
					allowBlank : 'true',
					listeners : {
						focus : function() {
						}
					}
				});

		var innerPnlMain = new Ext.form.FormPanel( { // 内部的FormPanel组件
					id : _this.formId + 'innerFormPanel' + fid,
					autoWidth : true,
					autoHeight : true,
					bodyBorder : false,
					border : false,
					name:'innerFormPanel',
					// frame false,
					labelWidth : 60,
					align : 'center',
					items : [ {
						layout : 'column',
						border : false,
						items : [ {
							columnWidth : .20,
							layout : 'form',
							items : [ innerImeiField ]
						}, {
							columnWidth : .20,
							border : false,
							layout : 'column',
							items : [ {
								columnWidth : .4,
								border : false,
								layout : 'form',  
								items : [ {
									xtype : 'button',
									text : '查询',
									width : 50,
									handler : function() {
										_this.priQueryFun();
									}
								} ]
							} ]
						} ]
					} ]
				});

		return innerPnlMain;
//--------------------------------------------------------------------------------------
		
		},
		loadImeiInfo : function(params){
			var _this = this;
			var loadMask = new Ext.LoadMask(document.body, {msg : '正在查询,请稍等...', removeMask : true});
			loadMask.show();
			
			Ext.Ajax.request({
	            url : rootPath+"basedata/sendDemoQuery!GetDeviceInfoByImei.action",
	            timeout : 120000,
	            success: function(result, obj){
	            	loadMask.hide();
	            	
	                var jsonFromServer = Ext.util.JSON.decode(result.responseText);
	                var data=jsonFromServer.deviceinfo;
	                _this.priCreateWinFun(params);
	                
	               
	                var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
	                if(data!=null){                
		                dirfrom.findField('c_imei').setValue(data.C_IMEI); //'c_latitude',c_longitude
		                dirfrom.findField('c_brand').setValue(data.C_BRAND);
		                dirfrom.findField('c_model').setValue(data.C_MODEL);
		                dirfrom.findField('c_size').setValue(data.C_SIZE);
		                dirfrom.findField('c_system').setValue(data.C_SYSTEM);
		                dirfrom.findField('c_nettype').setValue(data.C_NETTYPE);
		                if(data.C_SOURCE=="0001"||data.C_SOURCE=="00000001"){
		                	dirfrom.findField('c_longitude').setValue("官网");
		                }else if(data.C_SOURCE=="0002"){
		                	dirfrom.findField('c_longitude').setValue("应用宝");
		                } 
		                dirfrom.findField('c_latitude').setValue(data.C_VERSION);
		             
		                //加载tabPanel里面的两个数据源
		                Ext.getCmp('detailGrid').store.baseParams.id = params.c_imei ;
		                Ext.getCmp('detailGrid').store.reload(); 
		                Ext.getCmp('hitGrid').store.baseParams.id = params.c_imei ;
		                Ext.getCmp('hitGrid').store.reload();//
		                Ext.getCmp('hitHistoryGrid').store.baseParams.id = params.c_imei ;
		                Ext.getCmp('hitHistoryGrid').store.reload();//
		                Ext.getCmp('setDetailGrid').store.baseParams.id =  params.c_imei ;
		                Ext.getCmp('setDetailGrid').store.reload();
		                
		                
		                
	                }else{
	                	dirfrom.reset();
	                }
              },
	            failure: function(result, obj){
	            	loadMask.hide();
	            	alert('查询失败!');
	            }, 
	            params: params
	        });
		},
		
		priCreateWinFun : function(params){
			var _this = this;
			
			var addWin = Ext.getCmp(_this.addWinId);
			if(addWin){
				addWin.show();
				return;
			}
			
			

				
			
//------------------------------------------------
		
			var c_form = new Ext.form.FormPanel({
				title:'基本情况',
				id : _this.addWinId + 'addForm',
				frame : true,
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
				labelWidth : 70,
				layout : 'form',
				items : [{
					layout : 'column',
					items : [{
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : 'IMEI',
							id : _this.addWinId + 'c_imei',
							name : 'c_imei',
							allowBlank : false,
							allowDecimals :false,
							anchor : '100%',
							readOnly:true
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '品牌',
							id : _this.addWinId + 'c_brand',
							name : 'c_brand',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '型号',
							id : _this.addWinId + 'c_model',
							name : 'c_model',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}]
					},{
					layout : 'column',
					items : [{
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '屏幕大小',
							id : _this.addWinId + 'c_size',
							name : 'c_size',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '系统型号',
							id : _this.addWinId + 'c_system',
							name : 'c_system',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '网络类型',
							id : _this.addWinId + 'c_nettype',
							name : 'c_nettype',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}]
					},{
					layout : 'column',
					items : [{
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '下载渠道',
							id : _this.addWinId + 'c_longitude',
							name : 'c_longitude',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '当前版本',
							id : _this.addWinId + 'c_latitude',
							name : 'c_latitude',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : []
					}]} 
			]
			}
			);
			
			
		
			

			
	
			
			_this.cretaDetailGrid = function () {
				//var _this = this;

			 //定义列
				
				var detailResultsRecord = Ext.data.Record.create( [ {
					name : 'keyIndex'
				}, {
					name : 'oneOrLongClick'
				}, {
					name : 'way'
				},{
					name : 'setContent'
						}
				]);
				// 列表用到的数据源
				var resultsGrid_store = new Ext.data.Store( {
					proxy : new Ext.data.HttpProxy( {
						url : rootPath+"basedata/sendDemoQuery!getDetail.action",
						timeout : 60000
					}),
					reader : new Ext.data.JsonReader( {
						root : 'list'
					}, detailResultsRecord),
					baseParams : { }
				});
				
//				resultsGrid_store.baseParams.id =params.c_imei ;
				
				
				//-----点击IMEI弹出的表格	 
				var myTempArray = [ {    
					header : "键位",
					dataIndex : 'keyIndex',
					width : 15
				}, {
					header : "单击/长按",
					sortable : true,  
					dataIndex : 'oneOrLongClick',
					width : 15
				}, {
					header : "启动方式",
					sortable : true,  
					dataIndex : 'way',
					width : 15 ,
					renderer: function (value, meta, record) {
					   if(Ext.isEmpty(value)){
         			   return value;
         		   }
         		   return value.replace(/\n/ig,"</br>");
         	   } 
				}, {
					header : "设置内容",
					sortable : true,
					dataIndex : 'setContent',
					width : 80,
					 renderer: function (value, meta, record) {
					   if(Ext.isEmpty(value)){
            			   return value;
            		   }
            		   return value.replace(/\n/ig,"</br>");
            	   } 
				}
//				, {
//					header : "启动方式",
//					sortable : true,  
//					dataIndex : 'way',
//					width : 10
//				} 
				];
				var detailResultColumnModel = new Ext.grid.ColumnModel(myTempArray); 
				
				var detailGrid = new Ext.grid.EditorGridPanel( {
					id: 'detailGrid',
					store : resultsGrid_store,
					scrollable : true,
					bodyStyle : 'width:100%;border:1px solid #99bbe8;',
					//sm : resultsSm,
					cm : detailResultColumnModel,
					viewConfig : {
						forceFit : true,
						autoWrapRow : true
					},  
					autoWidth : true,
					height : 150, 
					loadMask : {
						msg : LOADMASK_MSG
					}
				});
				return detailGrid;
				}
			
			

			function  createTabPanel(){
			    var tabpanel = new Ext.TabPanel({
			    	width:800, 
			    	autoScroll : true,
			    	height:540,
			        deferredRender:false,// 默认情况下tab不会被渲染,但是tab间有关联的情况下需要关闭延迟渲染
			        tabPosition:"top", //默认情况下是top
			       
			        activeTab: 0, 
			        items : [{
			            title : '用户设置情况',
			            items : _this.createSetFormPanel(params)  
			        }, {
			            title : '用户使用情况',
			            items :  _this.createhitFormPanel(params)
			        }]
			        
			    });
			    
			    return  tabpanel;
			}
			
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '详细信息',
				closable:true,
	            draggable : true,
	            resizable : true,
	            autoScroll : true, 
	            height:570,
	            width : 850, 
				layout : 'form',
				align:'center',
				items : [c_form,createTabPanel()]
			});
			
			addWin.show();
		}
		
		
		
		
	}
};
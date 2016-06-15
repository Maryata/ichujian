//Ext.namespace("Ext.ux");
SYS_CONSTANT_PAGESIZE = 20;
Ext.actionDeviceInfo = function() {
	return {
		formId : 'actionDeviceInfo_formId',
		gridId : 'actionDeviceInfo_gridId',
		textwidth : 120,
		init : function() {

		},
		load : function() {
			var _this = this;

			var time_s = _this.createDateField( {
				fieldLabel : "日期",
				name : 'time_s',
				value : new Date().add(Date.DAY, -7)
			});
			var time_e = _this.createDateField( {
				fieldLabel : "到",
				name : 'time_e',
				value : new Date()
			});

			var pnlMain = new Ext.form.FormPanel( {
				id : _this.formId + 'formPanel',
				autoWidth : true,
				autoHeight : true,
				bodyBorder : false,
				border : false,
				//frame false,
				labelWidth : 60,
				align : 'center',
				items : [ {
					layout : 'column',
					border : false,
					items : [ {
						columnWidth : .25,
						layout : 'form',
						items : [ time_s ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ time_e ]
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
			//
		Manager.sys.Utils.antoScrollerInit(grid, pnlMain);
		grid.add(pnlMain);

		var pnl = new Ext.Panel( {
			id : _this.formId + 'panel',
			bodyStyle : 'border:0px solid #99bbe8',//padding:5 5 5 5;
			renderTo : 'mainDivId',//applyTo
			items : [ grid ]
		})
		_this.priQueryFun(); //初始化
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

		priQueryFun : function() {
			var _this = this;

			var gridStore = Ext.getCmp(_this.gridId).store;
			//gridStore.removeAll();

		var paramJson = {};
		//值得传递
		paramJson.time_s = Ext.getCmp(_this.formId + 'time_s').getRawValue();
		paramJson.time_e = Ext.getCmp(_this.formId + 'time_e').getRawValue();
		
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

		createGrid : function() {
			var _this = this;
			var resultsRecord = Ext.data.Record.create( [ {
				name : 'C_ID'
			}, {
				name : 'C_IMEI'
			}, {
				name : 'C_BRAND'
			}, {
				name : 'C_MODEL'
			}, {
				name : 'C_SIZE'
			}, {
				name : 'C_SYSTEM'
			}, {
				name : 'C_NETTYPE'
			}, {
				name : 'C_LONGITUDE'
			}, {
				name : 'C_LATITUDE'
			} ]);
			// 列表用到的数据源
			var resultsGrid_store = new Ext.data.Store( {
				proxy : new Ext.data.HttpProxy( {
					url : rootPath
							+ "basedata/deviceInfo!getDeviceInfoList.action",
					timeout : 60000
				}),
				reader : new Ext.data.JsonReader( {
					totalProperty : 'count',
					root : 'list'
				}, resultsRecord),
				baseParams : {}
			});

			//var resultsSm = new Ext.grid.CheckboxSelectionModel();

			var tempArray = [ {
				header : "C_ID",
				hidden : true,
				dataIndex : 'C_ID',
				width : 10
			}, {
				header : "IMEI",
				sortable : true,
				dataIndex : 'C_IMEI',
				width : 40
			}, {
				header : "品牌",
				sortable : true,
				dataIndex : 'C_BRAND',
				width : 40
			}, {
				header : "型号",
				sortable : true,
				dataIndex : 'C_MODEL',
				width : 40
			}, {
				header : "屏幕大小",
				sortable : true,
				dataIndex : 'C_SIZE',
				width : 30
			}, {
				header : "系统型号",
				sortable : true,
				dataIndex : 'C_SYSTEM',
				width : 30
			}, {
				header : "网络类型",
				sortable : true,
				dataIndex : 'C_NETTYPE',
				width : 30,
				renderer : function(value, meta, record) {
					return value == 1 ? 'WIFI' : '移动流量';
				}
			}, {
				header : "经度",
				sortable : true,
				dataIndex : 'C_LONGITUDE',
				width : 30
			}, {
				header : "纬度",
				sortable : true,
				dataIndex : 'C_LATITUDE',
				width : 30
			} ];

			var resultColumnModel = new Ext.grid.ColumnModel(tempArray);

			var grid = new Ext.grid.EditorGridPanel( {
				store : resultsGrid_store,
				scrollable : true,
				id : _this.gridId,
				clicksToEdit : 1,
				bodyStyle : 'width:100%;border:1px solid #99bbe8;',
				//sm : resultsSm,
				cm : resultColumnModel,
				viewConfig : {
					forceFit : true,
					autoWrapRow : true
				},
				width : mainWidth,
				height : mainHeigth,
				loadMask : {
					msg : LOADMASK_MSG
				},
				//tbar : funct_tbars,
				bbar : new Ext.PagingToolbar( {//分页工具条
							pageSize : SYS_CONSTANT_PAGESIZE,
							store : resultsGrid_store
						})
			});
			return grid;
		}
	}
};
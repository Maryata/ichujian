//Ext.namespace("Ext.ux");

SYS_CONSTANT_PAGESIZE = 20;
LOADMASK_MSG = 'loading...';
 
Ext.actionFeedback = function() {
	return {
		formId : 'actionFeedback_formId',
		gridId : 'actionFeedback_gridId',
		textwidth : 120,
		init : function(){
			
		},
		load : function(){
			var _this = this;
			
			var time_s = _this.createDateField({fieldLabel:"反馈时间",name:'time_s',value:new Date().add(Date.DAY, -7)});
			var time_e = _this.createDateField({fieldLabel:"到",name:'time_e',value:new Date()});
			 
			var pnlMain = new Ext.form.FormPanel( {
				id : _this.formId + 'formPanel',
				autoWidth : true,
				autoHeight : true,
				bodyBorder: false,	
				border :false,
				//frame false,
				labelWidth : 60,
				align:'center',
				items : [{
					layout : 'column',
					border :false,
					items : [{
						columnWidth : .25,
						layout : 'form',
						items : [time_s ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [time_e]
					}, {
						columnWidth : .20,
						border :false,
						layout : 'column',
						items : [{
							columnWidth : .4,
							border :false,
							layout : 'form',
							items : [{
								xtype : 'button',
								text : '查询',
								width : 50,
								handler : function() {
									_this.priQueryFun();
								}
							}]
						}]
					}]
				}]
			});
			
			var grid = _this.createGrid();
			//
			Manager.sys.Utils.antoScrollerInit(grid,pnlMain);
			grid.add(pnlMain);
			
			var pnl = new Ext.Panel( {
				id : _this.formId + 'panel',
				bodyStyle : 'border:0px solid #99bbe8',//padding:5 5 5 5;
				renderTo : 'mainDivId',//applyTo
				items : [grid]
			})
			
			_this.priQueryFun();
		},
		
		createDateField : function(option) {
			var _this = this;
			var dateField ={
	            xtype: 'datefield',
	            name : option.name,
	            fieldLabel : option.fieldLabel,
	            width: _this.textwidth,
	            format: 'Y-m-d',
	            id: _this.formId+option.name
	        };
			if(option.value){
				dateField.value = option.value;
			}
			return dateField;
		},
		
		priQueryFun : function(){
			var _this = this;
			
			var gridStore = Ext.getCmp(_this.gridId).store;
			//gridStore.removeAll();
			
			var paramJson = {};
			
	    	paramJson.time_s = Ext.getCmp(_this.formId +'time_s').getRawValue();
	    	paramJson.time_e = Ext.getCmp(_this.formId +'time_e').getRawValue();
	    	
	    	gridStore.baseParams = paramJson;
	    	gridStore.load({
	    		params : {
					start : 0,
					limit : SYS_CONSTANT_PAGESIZE
				},
				callback: function(obj){
					if(obj.length == 0) {
					}
				}
			});
			
		},

		
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
                {name : 'C_ID'},
                {name : 'NUM'},
                {name : 'C_IMEI'},
                {name : 'FEED_DAY'},
                {name : 'FEED_TIME'},
                {name : 'C_CONTENT'},
                {name : 'C_BRAND'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/actionFeedback!getFeedbackList.action",
    				timeout : 60000
    			}),
    			reader : new Ext.data.JsonReader({
    				totalProperty : 'count',
    				root : 'list'
    			}, resultsRecord),
    			baseParams : {
    			}
    		});
    		
    		//
    		//var resultsSm = new Ext.grid.CheckboxSelectionModel();
    		// IMEI	设备名称	激活时间	激活码	激活次数
    		var tempArray = [
               { header: "C_ID", hidden: true, dataIndex: 'C_ID',width:10},
               { header: "序号", sortable: true, dataIndex: 'NUM',width:10},
               { header: "日期", sortable: true, dataIndex: 'FEED_DAY',width:18},
               { header: "IMEI", sortable: true, dataIndex: 'C_IMEI',width:22},
               { header: "手机品牌", sortable: true, dataIndex: 'C_BRAND',width:20},
               { header: "反馈内容", sortable: true, dataIndex: 'C_CONTENT'},
               { header: "反馈时间", sortable: true, dataIndex: 'FEED_TIME',width:15}
               ];
    		
    		var resultColumnModel = new Ext.grid.ColumnModel(tempArray);
    		
    		var grid = new Ext.grid.EditorGridPanel({
    			store : resultsGrid_store,
    			scrollable : true,
    			id : _this.gridId,
    			clicksToEdit : 1,
    			bodyStyle : 'width:100%;border:1px solid #99bbe8;',
    			//sm : resultsSm,
    			cm : resultColumnModel,
    			viewConfig : {
    				forceFit : true,
    				autoWrapRow:true
    			},
    			width : mainWidth,
    			height : mainHeigth,
    			loadMask : { msg : LOADMASK_MSG },
    			//tbar : funct_tbars,
    			bbar : new Ext.PagingToolbar({//分页工具条
        			pageSize : SYS_CONSTANT_PAGESIZE,
        			store : resultsGrid_store
        		})
    		});

    		/*grid.on('render',function(grid) {
				var store = grid.getStore();
				var view = grid.getView();
				var str = "";
				grid.tip = new Ext.ToolTip({
					target : view.mainBody,
					title : '显示详细信息',
					delegate : '.x-grid3-row',
					trackMouse : true,
					dismissDelay : 5000,
					renderTo : document.body,
					listeners : {
						"beforeshow" : function updateTipBody(tip) {
							var rowIndex = view.findRowIndex(tip.triggerElement);
							if (store.getAt(rowIndex).get('C_CONTENT').length == 0) {
								str = '<div style="padding:20px;border:1px solid #999; color:#555; background: #f9f9f9;">'
										+ "" + '</div>';
								tip.body.dom.innerHTML = "";
							} else {
								str = '<div style="padding:20px;border:1px solid #999; color:#555; background: #f9f9f9;">'
										+ store.getAt(rowIndex).get('C_CONTENT')
										+ '</div>';
								tip.body.dom.innerHTML = str;
							}
							rowIndex = null;
						}
					}
				});

			});*/
    		
    		return grid;
		}
	}
};
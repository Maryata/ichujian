//Ext.namespace("Ext.ux");
SYS_CONSTANT_PAGESIZE = 20;
Ext.logInfo = function() {
	return {
		user:{},
		formId : 'logInfo_formId',
		gridId : 'logInfo_gridId',
		textwidth : 120,
		init : function(){
			
		},
		load : function(){
			var _this = this;
			
			var time_s = _this.createDateField({fieldLabel:"日期",name:'time_s',value:new Date().add(Date.DAY, -7)});
			var time_e = _this.createDateField({fieldLabel:"到",name:'time_e',value:new Date()});
			
			var logField = new Ext.form.TextField({
				id: _this.formId+'c_log',
				xtype : 'textfield',
				fieldLabel : '异常关键字',
				name : 'c_log',
				width : _this.textwidth,
				readOnly : false,
				maxLength : 50,
				allowBlank : 'true',
				listeners : {
					focus : function() {
					}
				}
			});
			
			var imeiField = new Ext.form.TextField({
				id: _this.formId+'c_imei',
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
			
			var pnlMain = new Ext.form.FormPanel( {
				id : _this.formId + 'formPanel',
				autoWidth : true,
				autoHeight : true,
				bodyBorder: false,	
				border :false,
				//frame false,
				labelWidth : 70,
				align:'center',
				items : [{
					layout : 'column',
					border :false,
					items : [{
						columnWidth : .20,
						layout : 'form',
						items : [time_s ]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [time_e]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [logField]
					} ,{
						columnWidth : .20,
						layout : 'form',
						items : [imeiField]
					} ,
					{
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
			_this.priQueryFun();  //初始化
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
	    	paramJson.c_log = Ext.getCmp(_this.formId +'c_log').getRawValue();
	    	paramJson.c_imei = Ext.getCmp(_this.formId +'c_imei').getRawValue();
	    	
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
		
		
			priSaveFun : function(){
			var _this = this;
			var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
			
			if (!dirfrom.isValid()) {
				return;
			}
		
			var saveParam = {};
			
			if(dirfrom.Ext_cid ){
				saveParam.c_id_old = dirfrom.Ext_cid;
			}
			
			saveParam.c_remark = dirfrom.findField('C_REMARK').getValue();
						
			var params = {};
			params.saveParam = Ext.encode(saveParam);
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"basedata/logInfo!Updatedispose.action",
				waitMsg : 'save ing...',
				success : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					alert('保存成功');
					//dirfrom.Ext_cid = dirfrom.findField('c_id').getValue();
				},
				failure : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					alert('保存失败');
				},
				params : params
			});
			
		},

		priCreateWinFun : function(){
			var _this = this;
			
			var addWin = Ext.getCmp(_this.addWinId);
			if(addWin){
				addWin.show();
				return;
			}
			
			var winH = mainHeigth-30,
				winW = mainWidth-10;
			if(winH>500){
				winH = 500;
			}
			if(winW>800){
				winW = 800;
			}
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '异常详细信息',
				closable:false,
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : winW,
	            //height: 600,
				layout : 'fit',
				frame : true,
				items : [{
					layout : 'fit',
					xtype : 'textarea',
					id : _this.addWinId + 'C_LOG',
					name : 'C_LOG',
					anchor : '100%',
					height: winH
				}],
				buttonAlign: 'center',//居中
				buttons : [ {
					text : '返回',
		            handler : function() {
		            	addWin.close();
		            }
		        }]
			});
			
			addWin.show();
		},
		
		priDisposeWinFun : function(){
			var _this = this;
			
			var addWin = Ext.getCmp(_this.addWinId);
			if(addWin){
				addWin.show();
				return;
			}
			
			var c_form = new Ext.form.FormPanel({
				id : _this.addWinId + 'addForm',
				frame : true,
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
				labelWidth : 60,
				layout : 'form',
				fileUpload : true,
				items : [{
					xtype : 'textfield',
					fieldLabel : '处理人',
					id : _this.addWinId + 'c_admin',
					name : 'c_asmin',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 50,
					anchor : '98%',
					value : _this.user
				},{
					xtype : 'textarea',
					fieldLabel : '备注',
					id : _this.addWinId + 'C_REMARK',
					name : 'C_REMARK',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 200,
					anchor : '98%'
				}
				],
				buttonAlign: 'center',//居中
				buttons : [ {
					text : '返回',
		            handler : function() {
		            	addWin.close();
						var grid = Ext.getCmp(_this.gridId);
						grid.store.reload();
		            }
		        }, {
		        	text : '保存',
		            handler : function() {
		            	_this.priSaveFun();
		            }
		        } ]
			});
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '处理信息',
				closable:false,
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 300,
	            height: 200,
				layout : 'fit',
				frame : true,
				items : [c_form]
			});
			
			addWin.show();
		},
		
		
		
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
                {name : 'C_ID'},
                {name : 'C_IMEI'},
                {name : 'C_MODEL'},
                {name : 'C_SYSTEM'},
                {name : 'C_LOG'},
                {name : 'C_ACTIONDATE'},
                {name : 'C_REMARK'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/logInfo!GetLogInfoList.action",
    				timeout : 60000
    			}),
    			reader : new Ext.data.JsonReader({
    				totalProperty : 'count',
    				root : 'list'
    			}, resultsRecord),
    			baseParams : {
    			}
    		});
    		
    		
    		//var resultsSm = new Ext.grid.CheckboxSelectionModel();
  
    		var tempArray = [
               { header: "C_ID", hidden: true, dataIndex: 'C_ID',width:10},
               { header: "C_REMARK", hidden: true, dataIndex: 'C_REMARK',width:10},
               { header: "IMEI", sortable: true, dataIndex: 'C_IMEI',width:20},
               { header: "型号", sortable: true, dataIndex: 'C_MODEL',width:20},
               { header: "系统型号", sortable: true, dataIndex: 'C_SYSTEM',width:10},
               { header: "异常日志", sortable: true, dataIndex: 'C_LOG',
            	renderer: function (value, meta, record) {
            		   return value.toString().substr(0,100);
               }},
               { header: "时间", sortable: true, dataIndex: 'C_ACTIONDATE',width:20},
               { header: "操作", sortable: false, dataIndex: '',width:20,align:'center',
        	     renderer: function (value, meta, record) {
   				     var formatStr = "<a style='cursor: hand' href='javascript:void({0});' onclick='javscript:return false;' class='modify'>详情</a>";
   				     formatStr += "&nbsp;&nbsp;&nbsp;<a style='cursor: hand' href='javascript:void({0});' onclick='javscript:return false;' class='dispose'>处理</a>";
   				     var resultStr = String.format(formatStr, '001', '001', '001');   
   				     return "<div class='controlBtn'>" + resultStr + "</div>";
       			}.createDelegate(this),   
       			css: "text-align:center;"
   				}
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
    		
    		
    		  grid.on('cellclick', function (grid, rowIndex, columnIndex, e) {
    		  var btn = e.getTarget('.controlBtn');   
    		  if (btn) {
    			var t = e.getTarget();   
    			var record = grid.getStore().getAt(rowIndex);
    			var control = t.className;
    		  if(record&&control=='modify'){
    				_this.priCreateWinFun();
    				var dirfrom = Ext.getCmp(_this.addWinId + 'C_LOG').setValue(record.data.C_LOG);
    			}else if(record&&control=='dispose'){
    				_this.priDisposeWinFun();
    				var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
    				dirfrom.Ext_cid = record.data.C_ID;
    				dirfrom = Ext.getCmp(_this.addWinId + 'C_REMARK').setValue(record.data.C_REMARK);
    			}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		}
	}
};
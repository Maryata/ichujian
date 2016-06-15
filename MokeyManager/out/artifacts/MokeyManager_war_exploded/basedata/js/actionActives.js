SYS_CONSTANT_PAGESIZE = 20;
Ext.actionActive = function() {
	return {
		formId : 'actionActive_formId',
		gridId : 'actionActive_gridId',
		addWinId : 'actionActive_winId',
		textwidth : 120,
		init : function(){
			
		},
		load : function(){
			var _this = this;
			
			var time_s = _this.createDateField({fieldLabel:"激活时间",name:'time_s',value:new Date().add(Date.DAY, -7)});
			var time_e = _this.createDateField({fieldLabel:"到",name:'time_e',value:new Date()});
			 
			var pnlMain = new Ext.form.FormPanel( {
				id : _this.formId + 'formPanel',
				autoWidth : true,
				autoHeight : true,
				border :false,
				//frame false,
				labelWidth : 80,
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
                {name : 'C_ACTIVECODE'},
                {name : 'C_JOBNUMBER'},
                {name : 'C_IMEI'},
                {name : 'C_ACTIONDATE'},
                {name : 'C_SYSDATE'},
                {name : 'C_ACTIONCOUNT'},
                {name : 'C_BRAND'},
                {name : 'C_CODE_IMEI'},
                {name : 'START_DATE'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/actionActive!getActiveList.action",
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
               { header: "C_ID", hidden: true, dataIndex: 'C_ID',width:20},
               { header: "序号", sortable: true, dataIndex: 'NUM',width:20},
               { header: "IMEI", sortable: true, dataIndex: 'C_IMEI',
               		renderer: function (value, meta, record) {
               			var formatStr = "<a style='cursor: hand;text-decoration:none;' href='javascript:void();' onclick='javscript:return false;' class='deviceinfo'>{0}</a>";   
   				     	var resultStr = String.format(formatStr, value);   
   				     return "<div class='controlBtn'>" + resultStr + "</div>";
       				}.createDelegate(this)
               },
               { header: "设备名称", sortable: true, dataIndex: 'C_BRAND'},
               { header: "激活时间", sortable: true, dataIndex: 'C_SYSDATE'},
               { header: "激活码", sortable: true, dataIndex: 'C_ACTIVECODE'},
               { header: "启动时间", hidden: true, dataIndex: 'START_DATE'},
               { header: "激活次数", sortable: true, dataIndex: 'C_ACTIONCOUNT',width:50}
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
    		
    		//mouseover    cellclick
    		  grid.on('cellclick', function (grid, rowIndex, columnIndex, e) {
    		  var btn = e.getTarget('.controlBtn');   
    		  if (btn) {
    			var t = e.getTarget();   
    			var record = grid.getStore().getAt(rowIndex);
    			var control = t.className;
    			if(record&&control=='deviceinfo'){
    					var delparams = {};
						delparams.c_imei= record.get('C_IMEI');
						
						var loadMask = new Ext.LoadMask(document.body, {msg : '正在查询,请稍等...', removeMask : true});
						loadMask.show();
						Ext.Ajax.request({
				            url : rootPath+"basedata/actionActive!GetDeviceByImei.action",
				            timeout : 120000,
				            success: function(result, obj){
				            	loadMask.hide();
				                var jsonFromServer = Ext.util.JSON.decode(result.responseText);
				                var data=jsonFromServer.deviceinfo;
				                _this.priCreateWinFun();
				                if(data!=null){
				                var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
				                dirfrom.findField('c_imei').setValue(data.C_IMEI);
				                dirfrom.findField('c_brand').setValue(data.C_BRAND);
				                dirfrom.findField('c_model').setValue(data.C_MODEL);
				                dirfrom.findField('c_size').setValue(data.C_SIZE);
				                dirfrom.findField('c_system').setValue(data.C_SYSTEM);
				                dirfrom.findField('c_nettype').setValue(data.C_NETTYPE);
				                dirfrom.findField('c_longitude').setValue(data.C_LONGITUDE);
				                dirfrom.findField('c_latitude').setValue(data.C_LATITUDE);
				                
				                }
				                
			                },
				            failure: function(result, obj){
				            	loadMask.hide();
				            	alert('查询失败!');
				            }, 
				            params: delparams
				        });
    	                return true;
    			}else{
    			}
    		  }   
    		},   
    		this); 
    		return grid;
		},
		
		priCreateWinFun : function(){
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
					fieldLabel : 'IMEI',
					id : _this.addWinId + 'c_imei',
					name : 'c_imei',
					allowBlank : false,
					allowDecimals :false,
					width : 140,
					readOnly:true
				},{
					xtype : 'textfield',
					fieldLabel : '品牌',
					id : _this.addWinId + 'c_brand',
					name : 'c_brand',
					allowBlank : false,
					width : 140,
					readOnly:true
				},{
					xtype : 'textfield',
					fieldLabel : '型号',
					id : _this.addWinId + 'c_model',
					name : 'c_model',
					allowBlank : false,
					allowDecimals :false,
					width : 140,
					readOnly:true
				},{
					xtype : 'textfield',
					fieldLabel : '屏幕大小',
					id : _this.addWinId + 'c_size',
					name : 'c_size',
					allowBlank : false,
					allowDecimals :false,
					width : 140,
					readOnly:true
				},{
					xtype : 'textfield',
					fieldLabel : '系统型号',
					id : _this.addWinId + 'c_system',
					name : 'c_system',
					allowBlank : false,
					allowDecimals :false,
					width : 140,
					readOnly:true
				},{
					xtype : 'textfield',
					fieldLabel : '网络类型',
					id : _this.addWinId + 'c_nettype',
					name : 'c_nettype',
					allowBlank : false,
					allowDecimals :false,
					width : 140,
					readOnly:true
				},{
					xtype : 'textfield',
					fieldLabel : '经度',
					id : _this.addWinId + 'c_longitude',
					name : 'c_longitude',
					allowBlank : false,
					allowDecimals :false,
					width : 140,
					readOnly:true
				},{
					xtype : 'textfield',
					fieldLabel : '纬度',
					id : _this.addWinId + 'c_latitude',
					name : 'c_latitude',
					allowBlank : false,
					allowDecimals :false,
					width : 140,
					readOnly:true
				}
				],
			});
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '设备详细信息',
				closable:true,
				//plain:true,	 
	            //modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 300, 
	            height: 300,
				layout : 'fit',
				align:'center',
				//frame : true,
				items : [c_form]
			});
			
			addWin.show();
		},
	}
};
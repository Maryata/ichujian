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
						columnWidth : .25,
						layout : 'form',
						items : [imeiField]
					},{
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

//		actCategory
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
					_this.loadImeiInfo({c_imei:record.get('C_IMEI')});
    	            return true;
    			}else{
    			}
    		  }   
    		},   
    		this); 
    		return grid;
		},
		
		loadImeiInfo : function(params){
			var _this = this;
			var loadMask = new Ext.LoadMask(document.body, {msg : '正在查询,请稍等...', removeMask : true});
			loadMask.show();
			Ext.Ajax.request({
	            url : rootPath+"basedata/actionActive!GetDeviceInfoByImei.action",
	            timeout : 120000,
	            success: function(result, obj){
	            	loadMask.hide();
	                var jsonFromServer = Ext.util.JSON.decode(result.responseText);
	                var data=jsonFromServer.deviceinfo;
	                _this.priCreateWinFun();
	                var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
	                if(data!=null){               
		                dirfrom.findField('c_imei').setValue(data.C_IMEI);
		                dirfrom.findField('c_brand').setValue(data.C_BRAND);
		                dirfrom.findField('c_model').setValue(data.C_MODEL);
		                dirfrom.findField('c_size').setValue(data.C_SIZE);
		                dirfrom.findField('c_system').setValue(data.C_SYSTEM);
		                dirfrom.findField('c_nettype').setValue(data.C_NETTYPE);
		                dirfrom.findField('c_longitude').setValue(data.C_LONGITUDE);
		                dirfrom.findField('c_latitude').setValue(data.C_LATITUDE);
		                
		                dirfrom.findField('one_click').setValue(jsonFromServer.onekeyclick);
		                dirfrom.findField('one_hold').setValue(jsonFromServer.onekeyhold);
		                dirfrom.findField('two_click').setValue(jsonFromServer.twokeyclick);
		                dirfrom.findField('two_hold').setValue(jsonFromServer.twokeyhold);
		                dirfrom.findField('three_click').setValue(jsonFromServer.threekeyclick);
		                dirfrom.findField('three_hold').setValue("");
		                dirfrom.findField('four_click').setValue(jsonFromServer.fouekeyclick);
		                dirfrom.findField('four_hold').setValue("");
		                
		                dirfrom.findField('c_totle').setValue(jsonFromServer.totle);
		                dirfrom.findField('c_remark').setValue(jsonFromServer.remark);
		                dirfrom.findField('c_onekeycount').setValue(jsonFromServer.c_onekeycount);
		                dirfrom.findField('c_twokeycount').setValue(jsonFromServer.c_twokeycount);
		                dirfrom.findField('c_threekeycount').setValue(jsonFromServer.c_threekeycount);
		                dirfrom.findField('c_fourkeycount').setValue(jsonFromServer.c_fourkeycount);
		                dirfrom.findField('c_onekeyhold').setValue(jsonFromServer.c_onekeyhold);
		                dirfrom.findField('c_twokeyhold').setValue(jsonFromServer.c_twokeyhold);
		                dirfrom.findField('c_threekeyhold').setValue(jsonFromServer.c_threekeyhold);
		                dirfrom.findField('c_fourkeyhold').setValue(jsonFromServer.c_fourkeyhold);
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
							fieldLabel : '经度',
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
							fieldLabel : '纬度',
							id : _this.addWinId + 'c_latitude',
							name : 'c_latitude',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
						}]
					}]
					}
					//-----------------------
					,{
						xtype : 'label',
						text: '设置情况',
						height:30
					}
					,{
					layout : 'column',
					items : [{
						columnWidth : .75,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '1号键单击',
							id : _this.addWinId + 'one_click',
							name : 'one_click',
							allowBlank : true, 
							anchor : '100%',
							labelWidth : 80,
							readOnly:true
						}]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '1号键长按',
							id : _this.addWinId + 'one_hold',
							name : 'one_hold',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}]}
					
					,{
					layout : 'column',
					items : [{
						columnWidth : .25,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '2号键单击',
							id : _this.addWinId + 'two_click',
							name : 'two_click',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}, {
						columnWidth : .75,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '2号键长按',
							id : _this.addWinId + 'two_hold',
							name : 'two_hold',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}]
					}
					
					,{
					layout : 'column',
					items : [{
						columnWidth : .75,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '3号键单击',
							id : _this.addWinId + 'three_click',
							name : 'three_click',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '3号键长按',
							id : _this.addWinId + 'three_hold',
							name : 'three_hold',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}]}, 
					
					{
					layout : 'column',
					items : [{
						columnWidth : .25,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '4号键单击',
							id : _this.addWinId + 'four_click',
							name : 'four_click',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '4号键长按',
							id : _this.addWinId + 'four_hold',
							name : 'four_hold',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}]
					}
					
					//-----------------------
					,{
						xtype : 'label',
						text: '使用情况',
						height:30
					},
					
					{
					layout : 'column',
					items : [{
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '按键使用情况合计',
							id : _this.addWinId + 'c_totle',
							name : 'c_totle',
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
							fieldLabel : '备注',
							id : _this.addWinId + 'c_remark',
							name : 'c_remark',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}]
					},{
					layout : 'column',
					items : [{
						columnWidth : .25,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '一号键单击',
							id : _this.addWinId + 'c_onekeycount',
							name : 'c_onekeycount',
							allowBlank : false,
							allowDecimals :false,
							anchor : '100%',
							readOnly:true
						}]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '一号键长按',
							id : _this.addWinId + 'c_onekeyhold',
							name : 'c_onekeyhold',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '二号键单击',
							id : _this.addWinId + 'c_twokeycount',
							name : 'c_twokeycount',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '二号键长按',
							id : _this.addWinId + 'c_twokeyhold',
							name : 'c_twokeyhold',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}]
					},{
					layout : 'column',
					items : [{
						columnWidth : .25,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '三号键单击',
							id : _this.addWinId + 'c_threekeycount',
							name : 'c_threekeycount',
							allowBlank : false,
							allowDecimals :false,
							anchor : '100%',
							readOnly:true
						}]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '三号键长按',
							id : _this.addWinId + 'c_threekeyhold',
							name : 'c_threekeyhold',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '四号键单击',
							id : _this.addWinId + 'c_fourkeycount',
							name : 'c_fourkeycount',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '四号键长按',
							id : _this.addWinId + 'c_fourkeyhold',
							name : 'c_fourkeyhold',
							allowBlank : true,
							anchor : '100%',
							readOnly:true
						}]
					}]
					}
					
				]
			});
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '详细信息',
				closable:true,
				//plain:true,	 
	            //modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 850, 
	           // height: 500,
				layout : 'form',
				align:'center',
				//frame : true,
				items : [c_form]
			});
			
			addWin.show();
		}
	}
};
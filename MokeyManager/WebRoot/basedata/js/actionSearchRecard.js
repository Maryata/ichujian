Ext.actionSearchRecard = function() {
	return {
		//定义下拉框
		priData : {
			categoryData : [{key:'1',value:'是'},{key:'0',value:'否'}]
//			typeData:[{key:'1',value:'热门'},{key:'0',value:'其他'}]	
		},
		formId : 'actionSearchRecard_formId',
		gridId : 'actionSearchRecard_gridId',
		addWinId : 'actionSearchRecard_addWinId',
		textwidth : 120,
		init : function(){
			
		},

		
		load : function(){
			//alert(11)
			var _this = this;
		
//			var time_s = _this.createDateField({fieldLabel:"搜索时间",name:'time_s',value:new Date().add(Date.DAY, -7)});
//			var time_e = _this.createDateField({fieldLabel:"到",name:'time_e',value:new Date()});
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
			
			var pnlMain = new Ext.form.FormPanel( {
				id : _this.formId + 'formPanel',
				//bodyStyle : 'padding:5 5 5 5;border:0px',
				autoWidth : true,
				autoHeight : true,
				bodyBorder: false,	
				border :false,
				//frame false,
				labelWidth : 80,
				align:'center',
				items : [{
					layout : 'column',
					border :false,
					items : [{
						columnWidth : .25,
						layout : 'form',
						items : [time_s]
					},{
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
		//***************************
		priQueryFun : function(){
			var _this = this;
			
			var gridStore = Ext.getCmp(_this.gridId).store;
			//gridStore.removeAll();
			
			var paramJson = {};
			
	    	//paramJson.c_imei = Ext.getCmp(_this.formId +'c_imei').getValue();
	    	paramJson.time_e = Ext.getCmp(_this.formId +'time_e').getRawValue();
	    	paramJson.time_s = Ext.getCmp(_this.formId +'time_s').getRawValue();
	    	
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
		
//		priSaveFun : function(){
//			var _this = this;
//			var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
//			//var fileObj = dirfrom.findField('file');
//			if (!dirfrom.isValid()) {
//				return;
//			}
//			
//			var saveParam = {};
//			if(dirfrom.Ext_cid ){
//				saveParam.c_id = dirfrom.Ext_cid;
//			}
//			saveParam.c_imei = dirfrom.findField('c_imei').getValue();
//			saveParam.c_date = dirfrom.findField('c_date').getRawValue();
//			saveParam.c_key = dirfrom.findField('c_key').getValue();
//		    
//			var params = {};
//			params.saveParam = Ext.encode(saveParam);
//			
//			dirfrom.submit({
//				method : 'post',
//				url : rootPath+"basedata/activitySearchRecard!saveActionSearchRecard.action",
//				waitMsg : 'save ing...',
//				success : function(form, action) {
//					var rsc = Ext.decode(action.response.responseText);
//					alert('保存成功');
//					dirfrom.Ext_cid = rsc.c_id;
//					
//				},
//				failure : function(form, action) {
//					var rsc = Ext.decode(action.response.responseText);
//					alert('保存失败');
//				},
//				params : params
//			});
//			
//		},
//		
		
		
//		priCreateWinFun : function(){
//			var _this = this;
//			
//			var addWin = Ext.getCmp(_this.addWinId);
//			if(addWin){
//				addWin.show();
//				return;
//			}
//								
//			var c_form = new Ext.form.FormPanel({
//				id : _this.addWinId + 'addForm',
//				frame : true,
//				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
//				labelWidth : 80,
//				layout : 'form',
//				fileUpload : true,
//				items : [{
//					xtype : 'textfield',
//					fieldLabel : '用户id',
//					id : _this.addWinId + 'c_imei',
//					name : 'c_imei',
//					allowBlank : false,
//					width : 140
//				},{
//					xtype : 'datefield',
//					fieldLabel : '时间',
//					id : _this.addWinId + 'c_date',
//					name : 'c_date',
//					format : 'Y-m-d',
//					allowBlank : false,
//					width : 140	
//				},{
//					xtype : 'textfield',
//					fieldLabel : '搜索关键字',
//					id : _this.addWinId + 'c_key',
//					name : 'c_key',
//					allowBlank : false,
//					allowDecimals :false,
//					width : 140
//				}
//				],
//				buttonAlign: 'center',//居中
//				buttons : [ {
//					text : '返回',
//		            handler : function() {
//		            	addWin.close();
//						var grid = Ext.getCmp(_this.gridId);
//						grid.store.reload();
//		            }
//		        }, {
//		        	text : '保存',
//		            handler : function() {
//		            	_this.priSaveFun();
//		            }
//		        } ]
//			});
//			
//			addWin = new Ext.Window({ 
//				id : _this.addWinId,
//				title: '搜索关键字管理',
//				closable:false,
//				plain:true,	 
//	            modal : true,           
//	            draggable : true,
//	            resizable : true,
//	            autoScroll : true,
//	            width : 300, 
//	            //height:500,	
//				layout : 'form',
//				frame : true,
//				items : [c_form]
//			});
//			
//			addWin.show();
//		},
		
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
               	{name : 'C_ID'},
                //{name : 'C_UID'},
                //{name : 'C_DATE'},
                {name : 'C_KEY'},
                {name:'C_COUNT'}
   
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/activitySearchRecard!getActionSearchRecardList.action",
    				timeout : 60000
    			}),
    			reader : new Ext.data.JsonReader({
    				//totalProperty : 'count',
    				root : 'list'
    			}, resultsRecord),
    			baseParams : {
    			}
    		});
    		
    		//
    		var tempArray = [
               { header: "ID", hidden: true, dataIndex: 'C_ID',width:15},
               { header: "关键字", sortable: true, dataIndex: 'C_KEY',width:40},
               { header: "搜索次数", sortable: true, dataIndex: 'C_COUNT',width:40}
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
    			loadMask : { msg : LOADMASK_MSG }
    			//tbar : funct_tbars,
    		});
    		

    		
//    		grid.on('cellclick', function (grid, rowIndex, columnIndex, e) {
//    		  var btn = e.getTarget('.controlBtn');   
//    		  if (btn) {
//    			var t = e.getTarget();   
//    			var record = grid.getStore().getAt(rowIndex);
//    			var control = t.className;
//    			if(record&&control=='delete'){
//    				if(window.confirm('是否确认删除？')){
//    					var delparams = {};
//						delparams.c_id= record.get('C_ID');
//						
//						var loadMask = new Ext.LoadMask(document.body, {msg : '正在删除,请稍等...', removeMask : true});
//						loadMask.show();
//						Ext.Ajax.request({
//				            url : rootPath+"basedata/activitySearchRecard!deleteActionSearchRecard.action",
//				            timeout : 120000,
//				            success: function(result, obj){
//				            	loadMask.hide();
//				                var jsonFromServer = Ext.util.JSON.decode(result.responseText);
//				                if (jsonFromServer.status=='Y') {
//				                   alert('删除成功!');
//				                   grid.getStore().reload();
//				                }else {
//				                	 alert('删除失败!');
//				                };
//			                },
//				            failure: function(result, obj){
//				            	loadMask.hide();
//				            	alert('删除失败!.');
//				            }, 
//				            params: delparams
//				        });
//    	                return true;
//    	             }
//    			}else if(record&&control=='modify'){
//    				_this.priCreateWinFun();
//    				
//    				var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
//    				
//    				var data = record.data;
//    				
//    				//dirfrom.Ext_DATA = data;
//    				dirfrom.Ext_cid = data.C_ID;
//    				dirfrom.findField('c_imei').setValue(data.C_UID);
//    				dirfrom.findField('c_date').setRawValue(data.C_DATE);
//    				dirfrom.findField('c_key').setValue(data.C_KEY);
//	
//    			}
//    		  }   
//    		},   
//    		this); 
    		
    		return grid;
		}
	}
};
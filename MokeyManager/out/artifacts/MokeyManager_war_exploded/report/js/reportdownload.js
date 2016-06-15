//Ext.namespace("Ext.ux");

SYS_CONSTANT_PAGESIZE = 20;
LOADMASK_MSG = 'loading...';
 
Ext.reportdownload = function() {
	return {
		formId : 'reportdownload_formId',
		gridId : 'reportdownload_gridId',
		textwidth : 120,
		init : function(){
			
		},
		load : function(){
			var _this = this;
			
			var time_s = _this.createDateField({fieldLabel:"开始日期",name:'time_s',value:new Date().add(Date.DAY, -7)});
			var time_e = _this.createDateField({fieldLabel:"结束日期",name:'time_e',value:new Date()});
			 
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
						},
						{
							columnWidth : .4,
							border :false,
							layout : 'form',
							items : [{
								xtype : 'button',
								text : '上传',
								width : 50,
								handler : function() {
									_this.priCreateWinFun();
								}
							}]
						}
						]
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
				applyTo : 'mainDivId',
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
			//var fileObj = dirfrom.findField('file');
			if (!dirfrom.isValid()) {
				return;
			}
			
			var saveParam = {};
			if(Ext.isEmpty(dirfrom.findField('c_reportFile').getValue())){
				alert(' 文件不能为空!');
				return;
			}
			saveParam.c_autor = dirfrom.findField('c_autor').getValue();
			
			var params = {};
			params.saveParam = Ext.encode(saveParam);
			
			dirfrom.submit({
				method : 'post',
				url : "report/reportDownload!updateReport.action",
				waitMsg : 'save ing...',
				success : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					alert('保存成功');
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
					
			var c_form = new Ext.form.FormPanel({
				id : _this.addWinId + 'addForm',
				frame : true,
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
				labelWidth : 60,
				layout : 'form',
				fileUpload : true,
				items : [{

					xtype : 'numberfield',
					fieldLabel : '上传人',
					id : _this.addWinId + 'c_autor',
					name : 'c_autor',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 5,
					width : 140
				},{
					xtype : 'field',
					inputType : 'file',
					fieldLabel : '文件',
					id : _this.addWinId + 'c_reportFile',
					name : 'c_reportFile',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 50,
					width : 200
				}],
				buttonAlign: 'center',//居中
				buttons : [ {
					text : '返回',
		            handler : function() {
		            	addWin.close();
						var grid = Ext.getCmp(_this.gridId);
						grid.store.reload();
		            }
		        }, {
		        	text : '导入',
		            handler : function() {
		            	_this.priSaveFun();
		            }
		        } ]
			});
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '上传文件',
				closable:false,
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 300, 
	            //height:500,	
				layout : 'form',
				frame : true,
				items : [c_form]
			});
			addWin.show();
		},
		
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
                {name : 'C_ID'},
                {name : 'C_ACTIONDATE'},
                {name : 'C_NAME'},
                {name : 'C_URL'},
                {name : 'C_AUTOR'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : "report/reportDownload!getReportDownload.action",
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
               { header: "文件名称", sortable: true, dataIndex: 'C_NAME',width:40},
               { header: "生成时间", sortable: true, dataIndex: 'C_ACTIONDATE',width:40},
               { header: "上传人", sortable: true, dataIndex: 'C_AUTOR',width:40},
                { header: "URL", hidden: true, dataIndex: 'C_URL',width:30},
               { header: "操作", sortable: false, dataIndex: '',align:'center',
        	     renderer: function (value, meta, record) {
   				     var formatStr = "<a style='cursor: hand' href='"+record.get('C_URL')+"' target='_blank'>下载</a>";   
   				     formatStr += "&nbsp;&nbsp;&nbsp;<a style='cursor: hand' href='javascript:void({0});' onclick='javscript:return false;' class='delete'>删除</a>";
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
    			if(record&&control=='delete'){
    				if(window.confirm('是否确认删除？')){
    					var delparams = {};
						delparams.c_id= record.get('C_ID');
						
						var loadMask = new Ext.LoadMask(document.body, {msg : '正在删除,请稍等...', removeMask : true});
						loadMask.show();
						Ext.Ajax.request({
				            url : "report/reportDownload!deleteReportDownload.action",
				            timeout : 120000,
				            success: function(result, obj){
				            	loadMask.hide();
				                var jsonFromServer = Ext.util.JSON.decode(result.responseText);
				                if (jsonFromServer.status=='Y') {
				                   alert('删除成功!');
				                   grid.getStore().reload();
				                }else {
				                	 alert('删除失败!');
				                };
			                },
				            failure: function(result, obj){
				            	loadMask.hide();
				            	alert('删除失败!.');
				            }, 
				            params: delparams
				        });
    	                return true;
    	             }
    			}
    		  }   
    		},   
    		this); 

    		return grid;
		}
	}
};
Ext.phoneBrandInfo = function() {
	return {
		priData : {
		},
		formId : 'phoneBrandInfo_formId',
		gridId : 'phoneBrandInfo_gridId',
		addWinId : 'phoneBrandInfo_addWinId',
		textwidth : 120,
		init : function(){
		},
		load : function(){
			//alert(11)
			var _this = this;
			//_this.createForm();
			
			var nameField = new Ext.form.TextField({
				id: _this.formId+'c_name',
				xtype : 'textfield',
				fieldLabel : '名称',
				name : 'c_name',
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
						items : [nameField]
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
						},{
							columnWidth : .4,
							border :false,
							layout : 'form',
								items : [{
								xtype : 'button',
								text : '添加',
								width : 50,
								handler : function() {
									_this.priCreateWinFun();
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
		
		
		/**
		 * query function
		 * @returns
		 */
		priQueryFun : function(){
			var _this = this;
			
			var gridStore = Ext.getCmp(_this.gridId).store;
			//gridStore.removeAll();
			var paramJson = {};
	    	paramJson.c_name = Ext.getCmp(_this.formId +'c_name').getValue();
	    	
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
		
		/**
		 * save function
		 * @returns
		 */
		priSaveFun : function(){
			var _this = this;
			var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
			//var fileObj = dirfrom.findField('file');
			if (!dirfrom.isValid()) {
				return;
			}
			var saveParam = {};
			if(dirfrom.Ext_cid ){
				saveParam.C_ID = dirfrom.Ext_cid;
				//dirfrom.old_model = dirfrom.old_model;
			}
			
			saveParam.C_NAME = dirfrom.findField('C_NAME').getValue();
			saveParam.C_CODE = dirfrom.findField('C_CODE').getValue();
			saveParam.C_ORDER = dirfrom.findField('C_ORDER').getValue();
			saveParam.C_SERIES = dirfrom.findField('C_SERIES').getValue();
			
			var c_model_code = dirfrom.findField('C_MODEL_CODE').getValue();
			var c_modelArr = c_model_code.split(',');
			c_modelArr = c_modelArr.unique();
			saveParam.C_MODEL_CODE = c_modelArr;
			
			var params = {};
			params.saveParam = Ext.encode(saveParam);
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"basedata/phoneBrand!saveBrandInfo.action",
				waitMsg : 'save ing...',
				success : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					if(rsc.cId){
						dirfrom.Ext_cid = rsc.cId;
						//dirfrom.old_model = saveParam.C_MODEL_CODE;
						alert('保存成功');
						var grid = Ext.getCmp(_this.gridId);
						grid.store.reload();
					}else{
						alert('保存失败');
					}
				},
				failure : function(form, action) {
					alert('保存失败');
				},
				params : params
			});
			
		},
		
		/**
		 * create new win
		 * @returns
		 */
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
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '品牌代码',
							id : _this.addWinId + 'C_CODE',
							name : 'C_CODE',
							allowBlank : false,
							//regex : /^[0-9a-zA-Z_]+$/,
							//regexText : '只能输入字母、数字-_',
							maxLength : 10,
							anchor : '90%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '品牌名称',
							id : _this.addWinId + 'C_NAME',
							name : 'C_NAME',
							allowBlank : false,
							maxLength : 10,
							anchor : '90%'
						}]
					}]
				},{
					
					layout : 'column',
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'numberfield',
							fieldLabel : '排序',
							id : _this.addWinId + 'C_ORDER',
							name : 'C_ORDER',
							allowBlank : false,
							allowDecimals :false,
							maxLength : 5,
							width : 100,
							anchor : '90%'
						}]
					}]
				},{
					layout : 'form',
					items : [{
						xtype : 'textfield',//						
						fieldLabel : '系列',
						id : _this.addWinId + 'C_SERIES',
						name : 'C_SERIES',
						disabled :false,
						allowBlank : false,
						maxLength :50,
						anchor : '96%'						
					}]
				},{
					layout : 'form',
					items : [{
						xtype : 'textarea',//						
						fieldLabel : '型号(,分隔)',
						id : _this.addWinId + 'C_MODEL_CODE',
						name : 'C_MODEL_CODE',
						disabled :false,
						allowBlank : false,
						maxLength :500,
						anchor : '96%',
						height:100
					}]
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
		        	text : '保存',
		            handler : function() {
		            	_this.priSaveFun();
		            }
		        } ]
			});
			
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '手机品牌管理',
				plain:true,	 
	            modal : true, 
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 600, 
				layout : 'form',
				frame : true,
				items : [c_form]
			});
			
			addWin.show();
		},
		
		/**
		 * create grid
		 * @returns Grid
		 */
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
                {name : 'C_ID'},
                {name : 'C_CODE'},
                {name : 'C_NAME'},
                {name : 'C_SERIES'},
                {name : 'C_ISLIVE'},
                {name : 'C_ORDER'},
                {name : 'C_MODEL_CODE'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/phoneBrand!getBrandInfoList.action",
    				timeout : 60000
    			}),
    			reader : new Ext.data.JsonReader({
    				totalProperty : 'count',
    				root : 'list'
    			}, resultsRecord)
    		});
    		
    		var tempArray = [
               { header: "C_ID", hidden: true, dataIndex: 'C_ID',width:10},
               { header: "品牌代码", sortable: true, dataIndex: 'C_CODE',width:30},
               { header: "品牌名称", sortable: true, dataIndex: 'C_NAME',width:50},
               { header: "系列", sortable: true, dataIndex: 'C_SERIES',width:50},
               { header: "型号", sortable: true, dataIndex: 'C_MODEL_CODE',width:180},
               { header: "排序", sortable: true, dataIndex: 'C_ORDER',width:30},
               { header: "操作", sortable: false, dataIndex: '',width:40,align:'center',
        	     renderer: function (value, meta, record) {
   				     var formatStr = "<a style='cursor: hand' href='javascript:void({0});' onclick='javscript:return false;' class='modify'>修改</a>";   
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
    			cm : resultColumnModel,
    			viewConfig : {
    				forceFit : true
    			},
    			width : mainWidth,
    			height : mainHeigth,
    			loadMask : { msg : LOADMASK_MSG },
    			bbar : new Ext.PagingToolbar({
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
				            url : rootPath+"basedata/phoneBrand!deleteBrandInfo.action",
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
    			}else if(record&&control=='modify'){
    				_this.priCreateWinFun();
    				
    				var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
    				var data = record.data;
    				dirfrom.Ext_cid = data.C_ID;
    				dirfrom.setValues(data);
    			}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		}
	}
};

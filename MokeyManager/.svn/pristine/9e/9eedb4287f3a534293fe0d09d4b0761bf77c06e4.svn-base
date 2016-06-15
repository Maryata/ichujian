Ext.activeCreateCode = function() {
	return {
		priData : {
			typeData : [{key:'1',value:'国内'},{key:'0',value:'国际'}],
			issampleData : [{key:'1',value:'是'},{key:'0',value:'否'}] 
		},
		formId : 'activeCreateCode_formId',
		gridId : 'activeCreateCode_gridId',
		addWinId : 'activeCreateCode_addWinId',
		textwidth : 120,
		init : function(){
		},
		load : function(){
			var _this = this;			
			
			var pnlMain = new Ext.form.FormPanel( {
				id : _this.formId + 'formPanel',
				autoWidth : true,
				autoHeight : true,
				border :false,
				labelWidth : 80,
				items : [{
					layout : 'column',
					border :false,
					items : [{
						columnWidth : .20,
						border :false,
						layout : 'column',
						items : [{
							xtype:'button',
							text:'创建',
							width:50,
							handler:function(){
								_this.priCreateWinFun();
							}
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
			//创建激活码信息对象
			//_this.actionActive = new Ext.actionActive();
		},
		
		
		/**
		 * query function
		 * @returns
		 */
		priQueryFun : function(){
			var _this = this;
			
			var gridStore = Ext.getCmp(_this.gridId).store;
			var paramJson = {};
	    	
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
		 * Create function
		 * @returns
		 */
		priCreateFun : function(){
			var _this = this;
			var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
			if (!dirfrom.isValid()) {
				return;
			}
			//参数的传递
			var saveParam={};

			saveParam.c_number=dirfrom.findField('c_number').getValue();
			saveParam.c_supplier = dirfrom.findField('c_supplier').getValue();
			saveParam.c_count = dirfrom.findField('c_count').getValue();
			saveParam.c_batch = dirfrom.findField('c_batch').getValue();
			saveParam.c_type = dirfrom.findField('c_type').getValue();
			saveParam.c_issample = dirfrom.findField('c_issample').getValue();
			saveParam.c_remark = dirfrom.findField('c_remark').getValue();
			//saveParam.C_ID =null; 

			var params = {};
			params.saveParam=Ext.encode(saveParam);
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"basedata/activeCreateCode!Create.action",
				waitMsg : 'upload ing...',
				timeout : 240000,
				success : function(form, action) {
					//var rsc = Ext.decode(action.response.responseText);
					alert('创建成功');
					Ext.getCmp(_this.gridId).store.reload();
				},
				failure : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					alert('创建失败');
					alert(rsc.status);
					Ext.getCmp(_this.gridId).store.reload();
				},
				params : params
			});
		},
		
		/**
		 * 创建ComboBox
		 * @param option
		 * @returns ComboBox
		 */
		createComVarField : function(option){
			var _this = this;
			var _data = option.data.slice(0);
			//--all
//			if(option.all){
//				_data.unshift({key:'',value:'全部 '});
//			}
			
			var _Store = new Ext.data.JsonStore({
				fields : ['key','value'],
				data : _data
			});
			
			var actCombo = new Ext.form.ComboBox({
				id: option.id ? option.id : _this.formId+option.name,
				store : _Store,
				displayField : 'value',
				valueField : 'key',
				typeAhead : true,
				resizable : true,
				editable : false,
				inputType : 'text',
				mode : 'local',
				triggerAction : 'all',
				name : option.name,
	            fieldLabel : option.fieldLabel,
				triggerAction : 'all',
				allowBlank : option.isNoBlank ? false : true, 
				//readOnly:true,
				width : 200
			});
				return actCombo;
		},
		
		priCreateWinFun : function(){
			var _this = this;
			
			var addWin = Ext.getCmp(_this.addWinId);
			if(addWin){
				addWin.destroy () 
			}
			
			var typeField = _this.createComVarField({fieldLabel:"类型",name:'c_type' , data:_this.priData.typeData , all: true});
			var issampleField = _this.createComVarField({fieldLabel:"是否样本",name:'c_issample' , data:_this.priData.issampleData , all: true});
					
			var c_form = new Ext.form.FormPanel({
				id : _this.addWinId + 'addForm',
				frame : true,
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
				labelWidth : 80,
				layout : 'form',
				fileUpload : true,
				items : [{
					xtype:'textfield',
					fieldLabel : '代理商编号',
					id : _this.addWinId + 'c_number',
					name:'c_number',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 70,
					width : 200
				},{
					xtype:'textfield',
					fieldLabel : '代理商',
					id : _this.addWinId + 'c_supplier',
					name:'c_supplier',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 50,
					width : 200
				},{
					xtype:'textfield',
					fieldLabel : '数量',
					id : _this.addWinId + 'c_count',
					name:'c_count',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 50,
					width : 200
				},typeField,{
					xtype:'textfield',
					fieldLabel : '批次',
					id : _this.addWinId + 'c_batch',
					name:'c_batch',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 50,
					width : 200
				},issampleField,{
					xtype:'textarea',
					fieldLabel : '备注',
					id : _this.addWinId + 'c_remark',
					name:'c_remark',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 200,
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
		        	text : '创建',
		            handler : function() {
		            	_this.priCreateFun();
		            }
		        } ]
			});
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '创建激活码',
				closable:false,
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 400, 
	            height:330,	
				layout : 'form',
				frame : true,
				items : [c_form]
			});
			addWin.show();
		},
		
		//*****************
		/**
		 * create grid
		 * @returns Grid
		 */
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
                {name : 'C_ID'},
                {name : 'C_NUMBER'},
                {name : 'C_SUPPLIER'},
                {name : 'C_COUNT'},
                {name : 'C_TYPE'},               
                {name : 'C_BATCH'},
                {name : 'C_ISSAMPLE'},
                {name : 'C_SYSDATE'},
                {name : 'C_REMARK'},
                {name : 'C_URL'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/activeCreateCode!getActiveList.action",
    				timeout : 60000
    			}),
    			reader : new Ext.data.JsonReader({
    				totalProperty : 'count',
    				root : 'list'
    			}, resultsRecord)
    		});
    		
    		var tempArray = [
               { header: "C_ID", hidden: true, dataIndex: 'C_ID',width:10},
               { header: "代理商编号", sortable: true, dataIndex: 'C_NUMBER',width:25},
               { header: "代理商", sortable: true, dataIndex: 'C_SUPPLIER',width:80},
               { header: "数量",  sortable: true, dataIndex: 'C_COUNT',width:30},
               { header: "类别", sortable: true, dataIndex: 'C_TYPE',width:30},
               { header: "批次", sortable: true, dataIndex: 'C_BATCH',width:60},
               { header: "是否样品", sortable: true, dataIndex: 'C_ISSAMPLE',width:20},
               { header: "创建时间", sortable: true, dataIndex: 'C_SYSDATE',width:50},
               { header: "备注", sortable: true, dataIndex: 'C_REMARK',width:100},
               { header: "下载地址", sortable: true, dataIndex: 'C_URL',width:100},
               { header: "操作", sortable: false, dataIndex: '',width:30,align:'center',
        	     renderer: function (value, meta, record) {
   				     var formatStr = "<a style='cursor: hand' href='javascript:void();' onclick='javscript:return false;' class='modify'>修改</a>";   
   				     return "<div class='controlBtn'>" + formatStr + "</div>";
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
    				forceFit : true
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
    				var data = record.data;
    				_this.priCreateRemarkWinFun();
    				
    				var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
    				dirfrom.Ext_cid = data.C_ID;
    				dirfrom.setValues(data);
    			}else{
    			}
    		  }   
    		},   
    		this); 
    		return grid;
		}
		
	}
};

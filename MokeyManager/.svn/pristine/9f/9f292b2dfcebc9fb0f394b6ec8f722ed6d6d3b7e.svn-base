Ext.activeCode = function() {
	return {
		priData : {
			typeData : [{key:'0',value:'已激活'},{key:'1',value:'未激活'}],
			sampleData : [{key:'0',value:'非样品'},{key:'1',value:'样品'}] //是否样品
		},
		formId : 'activeCode_formId',
		gridId : 'activeCode_gridId',
		addWinId : 'activeCode_addWinId',
		textwidth : 120,
		init : function(){
		},
		load : function(){
			var _this = this;			
			
			var codeField = new Ext.form.TextField({
				id: _this.formId+'code',
				xtype : 'textfield',
				fieldLabel : '激活码',
				name : 'code',
				width : _this.textwidth,
				readOnly : false,
				maxLength : 50,
				allowBlank : 'true',
				listeners : {
					focus : function() {
					}
				}
			});
			
			 var suppStore = new Ext.data.Store({
            	//autoLoad: true,
            	proxy : new Ext.data.HttpProxy({
            		url : rootPath+"basedata/supplierInfo!getSuppliers.action",
            	    timeout : 60000
            	}),
            	reader : new Ext.data.JsonReader({
            		root : 'list'
            	},
				Ext.data.Record.create([
					{ name : 'C_SUPPLIER_CODE'},
					{ name : 'C_SUPPLIER_NAME'}
				]))
            });
            
            suppStore.load({
            	callback: function(obj){
					suppStore.insert(0,new suppStore.recordType({C_SUPPLIER_CODE:'',C_SUPPLIER_NAME:'全部'}));
				}
            });
            
            var  supp_Comb = new Ext.form.ComboBox({
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : suppStore,
				fieldLabel : '代理商',
				id: _this.formId+'supp',
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
         
			 
			var typeField = _this.createComVarField({fieldLabel:"是否激活",name:'isActive' , data:_this.priData.typeData , all: true});
			var isRemarkField = _this.createComVarField({fieldLabel:"是否备注",name:'isRemark' , data:[{key:'1',value:'已备注'},{key:'0',value:'未备注'}] , all: true});
			var isSampleField = _this.createComVarField({fieldLabel:"是否样品",name:'isSample' , data: _this.priData.sampleData , all: true});
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
						items : [typeField ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [codeField]
					}]
				},{
					layout : 'column',
					border :false,
					items : [{
						columnWidth : .25,
						layout : 'form',
						items : [supp_Comb ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [isSampleField]//
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [isRemarkField]
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
						},{
							xtype:'button',
							text:'导入',
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
			_this.actionActive = new Ext.actionActive();
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
			if(option.all){
				_data.unshift({key:'',value:'全部 '});
			}
			
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
				width : option.width ? option.width : _this.textwidth
			});
			
			return actCombo;
			
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
			
	    	paramJson.isActive = Ext.getCmp(_this.formId +'isActive').getValue();
	    	paramJson.code = Ext.getCmp(_this.formId +'code').getValue();
	    	paramJson.supplier = Ext.getCmp(_this.formId +'supp').getValue();
	    	paramJson.isRemark = Ext.getCmp(_this.formId +'isRemark').getValue();
	    	paramJson.isSample = Ext.getCmp(_this.formId +'isSample').getValue();
	    	
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
			var params = {};
			var saveParam = {};
						
			saveParam.C_ID = dirfrom.Ext_cid;
			saveParam.C_REMARK = dirfrom.findField('C_REMARK').getValue();
			
			params.saveParam = Ext.encode(saveParam);
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"basedata/activeCode!saveActiveRemark.action",
				waitMsg : 'save ing...',
				timeout : 240000,
				success : function(form, action) {
					alert('保存成功');
					Ext.getCmp(_this.gridId).store.reload();
				},
				failure : function(form, action) {
					alert('保存失败');
				},
				params : params
			});
		},
		
		/**
		 * upload function
		 * @returns
		 */
		priUploadFun : function(){
			var _this = this;
			var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
			if (!dirfrom.isValid()) {
				return;
			}
			//参数的传递
			var saveParam={};
			if(Ext.isEmpty(dirfrom.findField('c_reportFile').getValue())){
				alert(' 文件不能为空!');
				return;
			}
			saveParam.C_ISSAMPLE=dirfrom.findField('isSample').getValue();
			saveParam.C_BATCH = dirfrom.findField('c_batch').getValue();
			saveParam.C_ID =null; 
			var params = {};
			//*
			params.saveParam=Ext.encode(saveParam);
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"basedata/activeCode!upload.action",
				waitMsg : 'upload ing...',
				timeout : 240000,
				success : function(form, action) {
					//var rsc = Ext.decode(action.response.responseText);
					alert('导入成功');
					Ext.getCmp(_this.gridId).store.reload();
				},
				failure : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					alert('导入失败');
					alert(rsc.status);
					Ext.getCmp(_this.gridId).store.reload();
				},
				params : params
			});
		},
		
		
		priCreateWinFun : function(){
			var _this = this;
			
			var addWin = Ext.getCmp(_this.addWinId);
			if(addWin){
				addWin.destroy () 
			}
					
			var c_form = new Ext.form.FormPanel({
				id : _this.addWinId + 'addForm',
				frame : true,
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
				labelWidth : 60,
				layout : 'form',
				fileUpload : true,
				items : [{
					xtype : 'field',
					inputType : 'file',
					fieldLabel : '文件',
					id : _this.addWinId + 'c_reportFile',
					name : 'c_reportFile',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 50,
					width : 200
				},{
					xtype:'textfield',
					fieldLabel : '批次',
					id : _this.addWinId + 'c_batch',
					name:'c_batch',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 50,
					width : 120
				},_this.createComVarField({fieldLabel:"是否样品",id:_this.addWinId+'isSample',name:'isSample' , data:_this.priData.sampleData})],
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
		            	_this.priUploadFun();
		            }
		        } ]
			});
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '导入文件',
				closable:false,
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 400, 
	            //height:500,	
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
                {name : 'C_CODE'},
                {name : 'C_SYSDATE'},
                {name : 'C_BATCH'},
                {name : 'C_IMEI'},               
                {name : 'C_REMARK'},
                {name : 'C_ISSAMPLE'},
                {name : 'ACTIONDATE'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/activeCode!getActiveList.action",
    				timeout : 60000
    			}),
    			reader : new Ext.data.JsonReader({
    				totalProperty : 'count',
    				root : 'list'
    			}, resultsRecord)
    		});
    		
    		var tempArray = [
               { header: "C_ID", hidden: true, dataIndex: 'C_ID',width:10},
               { header: "激活码", sortable: true, dataIndex: 'C_CODE',width:65,
               		renderer: function (value, meta, record) {
               			var formatStr = "<a style='cursor: hand;text-decoration:none;' href='javascript:void();' onclick='javscript:return false;' class='deviceinfo'>{0}</a>";   
               			formatStr += "&nbsp;&nbsp;&nbsp;&nbsp;<img style='cursor: hand;' src='../../image/barcode-2d.png' class='qrcode'/>";
   				     	var resultStr = String.format(formatStr, value);   
   				     return "<div class='controlBtn'>" + resultStr + "</div>";
       				}.createDelegate(this)
               },
               { header: "生成时间", sortable: true, dataIndex: 'C_SYSDATE',width:45},
               { header: "批次",  sortable: true, dataIndex: 'C_BATCH',width:50},
               { header: "备注", sortable: true, dataIndex: 'C_REMARK',width:60},
               { header: "IMEI", sortable: true, dataIndex: 'C_IMEI',width:60},
               { header: "激活时间", sortable: true, dataIndex: 'ACTIONDATE',width:50},
               { header: "是否样品", sortable: true, dataIndex: 'C_ISSAMPLE',width:20,
            	   renderer: function (value, meta, record) {
            		   if(Ext.isEmpty(value)){
            			   return value;
            		   }
            		   for(var i=0;i<_this.priData.sampleData.length;i++){
            			   if(_this.priData.sampleData[i].key==value){
            				   return _this.priData.sampleData[i].value;
            			   }
            		   }
            		   return value;
            	   }
               },
               { header: "操作", sortable: false, dataIndex: '',width:30,align:'center',
        	     renderer: function (value, meta, record) {
   				     var formatStr = "<a style='cursor: hand' href='javascript:void();' onclick='javscript:return false;' class='addRemark'>添加备注</a>";   
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
    			if(record&&control=='qrcode'){
    				var data = record.data;
    				
    				_this.priCreateQrWinFun();
    				var qrcode = new QRCode(document.getElementById("qrcode"), {
			            width : 200,//设置宽高 //96
			            height : 200
			        });
			        qrcode.makeCode(data.C_CODE);
    			
    			}else if(record&&control=='addRemark'){
    				var data = record.data;
    				_this.priCreateRemarkWinFun();
    				
    				var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
    				dirfrom.Ext_cid = data.C_ID;
    				dirfrom.setValues(data);
    			}else if(record&&control=='deviceinfo'){
    				_this.actionActive.loadImeiInfo({c_imei:record.get('C_IMEI')});
    			}
    		  }   
    		},   
    		this); 
    		
    		resultsGrid_store.on('beforeload', function(store) {
				if(Ext.getCmp(_this.addWinId)){
					Ext.getCmp(_this.addWinId).close();
				}
				if(Ext.getCmp(_this.addWinId+'QR')){
					Ext.getCmp(_this.addWinId+'QR').close();
				}
    		});
    		
    		return grid;
		},
		priCreateQrWinFun : function(){
			var _this = this;
			if(Ext.getCmp(_this.addWinId)){
				Ext.getCmp(_this.addWinId).close();
			}
			var addWin = Ext.getCmp(_this.addWinId+'QR');
			if(addWin){
				document.getElementById("qrcode").innerHTML='';
				addWin.show();
				return;
			}
			addWin = new Ext.Window({ 
				id : _this.addWinId+'QR',
				title: '二维码',
				closable:true,
				//plain:false,	 
	            //modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 222,
	            height: 238,
				layout : 'fit',
				align:'center',
				items : [{
					xtype : 'label',
					html : '<div id="qrcode">'
				}]
			});
			
			addWin.show();	

		},
		
		priCreateRemarkWinFun : function(){
			var _this = this;
			if(Ext.getCmp(_this.addWinId+'QR')){
				Ext.getCmp(_this.addWinId+'QR').close();
			}
			var addWin = Ext.getCmp(_this.addWinId);
			if(addWin){
				//addWin.show();
				//return;
				addWin.destroy () 
			}
			
			var c_form = new Ext.form.FormPanel({
				id : _this.addWinId + 'addForm',
				frame : true,
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
				labelWidth : 60,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					fieldLabel : '备注',
					id : _this.addWinId + 'C_REMARK',
					name : 'C_REMARK',
					allowBlank : true,
					anchor : '98%',
					readOnly:false
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
				title: '添加备注',
				plain:true,
	            //modal : true, //阴影
				shadow :false,      
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 400, 
				layout : 'form',
				//frame : true,
				items : [c_form]
			});
			
			addWin.show();	

		}
		
	}
};

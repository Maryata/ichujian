 UPDATE_TAGS="";
Ext.supplierInfo = function() {
	return {
		formId : 'supplierInfo_formId',
		gridId : 'supplierInfo_gridId',
		addWinId : 'supplierInfo_addWinId',
		textwidth : 120,
		init : function(){
			
		},
		load : function(){
			//alert(11)
			var _this = this;
			//_this.createForm();
			
			var codeField = new Ext.form.TextField({
				id: _this.formId+'c_code',
				xtype : 'textfield',
				fieldLabel : '代码',
				name : 'c_code',
				width : _this.textwidth,
				readOnly : false,
				maxLength : 50,
				allowBlank : 'true',
				listeners : {
					focus : function() {
					}
				}
			});
			
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
				//bodyStyle : 'padding:5 5 5 5;border:0px',
				autoWidth : true,
				autoHeight : true,
				bodyBorder: false,	
				border :false,
				//frame false,
				labelWidth : 40,
				align:'center',
				items : [{
					layout : 'column',
					border :false,
					items : [{
						columnWidth : .25,
						layout : 'form',
						items : [codeField ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [nameField ]
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
		
		priQueryFun : function(){
			var _this = this;
			
			var gridStore = Ext.getCmp(_this.gridId).store;
			//gridStore.removeAll();
			
			var paramJson = {};
			
			paramJson.c_code = Ext.getCmp(_this.formId +'c_code').getValue();
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
			
			//saveParam.c_id = dirfrom.findField('c_id').getValue();
			saveParam.c_supplier_code = dirfrom.findField('c_supplier_code').getValue();
		    saveParam.c_supplier_name = dirfrom.findField('c_supplier_name').getValue();
			saveParam.c_phone = dirfrom.findField('c_phone').getValue();
		    saveParam.c_contacts = dirfrom.findField('c_contacts').getValue();
		    saveParam.c_email = dirfrom.findField('c_email').getValue();
		    saveParam.c_location = dirfrom.findField('c_location').getValue();
		    saveParam.c_leve = dirfrom.findField('c_leve').getValue();
			saveParam.c_url = dirfrom.findField('c_url').getValue();
			saveParam.C_COMPANY = dirfrom.findField('C_COMPANY').getValue();
						
			
			var params = {};
			params.saveParam = Ext.encode(saveParam);
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"basedata/supplierInfo!saveSupplier.action",
				waitMsg : 'save ing...',
				success : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					alert('保存成功');
					dirfrom.Ext_cid = dirfrom.findField('c_id').getValue();
					//dirfrom.findField('c_id').setReadOnly(true);
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
			
			//var typeField = _this.createComVarField({fieldLabel:"是否有效",id: _this.addWinId + 'c_islive',name:'c_islive' , data:_this.priData.categoryData , width: 140,isNoBlank:true});
			
			// 检查是否重复公共方法
			function checkExitscodenameid(code,name,id) {
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", rootPath+"basedata/supplierInfo!checkCodeandNameBymodify.action", false);// false表示同步调用
				conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				conn.send("c_code="+code+"&c_name="+name+"&c_id="+id);
				var data = Ext.util.JSON.decode(conn.responseText);
				return data;
			}
			
						// 检查ID是否重复公共方法
			function checkExitscodename(code,name) {
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", rootPath+"basedata/supplierInfo!checkCodeandName.action", false);// false表示同步调用
				conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				conn.send("c_code="+code+"&c_name="+name);
				var data = Ext.util.JSON.decode(conn.responseText);
				return data;
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
					fieldLabel : '代码',
					id : _this.addWinId + 'c_supplier_code',
					name : 'c_supplier_code',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 20,
					anchor : '98%',
					validationDelay : 500,
					validator : function(value) {
					//alert('000');
					if(UPDATE_TAGS!='UPDATE'){
						var data = checkExitscodename(value,"");
						if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
							return "代码重复";
						}
				    }else{
				    	var data = checkExitscodenameid(value,"",Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);
						if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
							return "代码重复";
						}
				    }  	
						return true;
				    }
				},{
					xtype : 'textfield',
					fieldLabel : '名称',
					id : _this.addWinId + 'c_supplier_name',
					name : 'c_supplier_name',
					allowBlank : false,
					maxLength : 20,
					anchor : '98%',
					validationDelay : 500,
					validator : function(value) {
					if(UPDATE_TAGS!='UPDATE'){
					    var data = checkExitscodename("", value);
						if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
							return "名称重复";
						}
					}else{
					    var data = checkExitscodenameid("", value,Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);
						if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
							return "名称重复";
						}
					}
						return true;
				    }
				},{
					xtype : 'numberfield',
					fieldLabel : '电话',
					id : _this.addWinId + 'c_phone',
					name : 'c_phone',
					allowBlank : true,
					allowDecimals :false,
					maxLength : 20,
					anchor : '98%'
				},{
					xtype : 'textfield',
					fieldLabel : '联系人',
					id : _this.addWinId + 'c_contacts',
					name : 'c_contacts',
					allowBlank : true,
					allowDecimals :false,
					maxLength : 50,
					anchor : '98%'
				},{
					xtype : 'textfield',
					fieldLabel : 'Email',
					id : _this.addWinId + 'c_email',
					name : 'c_email',
					allowBlank : true,
					allowDecimals :false,
					maxLength : 50,
					anchor : '98%'
				},{
					xtype : 'textfield',
					fieldLabel : '地址',
					id : _this.addWinId + 'c_location',
					name : 'c_location',
					allowBlank : true,
					allowDecimals :false,
					maxLength : 200,
					anchor : '98%'
				},{
					xtype : 'textfield',
					fieldLabel : '级别',
					id : _this.addWinId + 'c_leve',
					name : 'c_leve',
					allowBlank : true,
					allowDecimals :false,
					maxLength : 200,
					anchor : '98%'
				},{
					xtype : 'textfield',
					fieldLabel : 'URL',
					id : _this.addWinId + 'c_url',
					name : 'c_url',
					allowBlank : true,
					allowDecimals :false,
					maxLength : 500,
					anchor : '98%'
				},{
					xtype : 'textarea',
					fieldLabel : '公司名称',
					id : _this.addWinId + 'C_COMPANY',
					name : 'C_COMPANY',
					allowBlank : true,
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
				title: '供应商管理',
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
                {name : 'C_SUPPLIER_CODE'},
                {name : 'C_SUPPLIER_NAME'},
                {name : 'C_PHONE'},
                {name : 'C_CONTACTS'},
                {name : 'C_EMAIL'},
                {name : 'C_LOCATION'},
                {name : 'C_LEVE'},
                {name : 'C_MODITY_TIME'},
                {name : 'C_COMPANY'},
                {name : 'C_URL'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/supplierInfo!getSupplierList.action",
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
    		//var resultsSm = new Ext.grid.CellSelectionModel(); //.CheckboxSelectionModel();
    		var tempArray = [
               { header: "ID", hidden: true, dataIndex: 'C_ID',width:15},
               { header: "代码", sortable: true, dataIndex: 'C_SUPPLIER_CODE',width:10},
               { header: "名称", sortable: true, dataIndex: 'C_SUPPLIER_NAME',width:30},
               { header: "公司名称", sortable: true, dataIndex: 'C_COMPANY',width:50},
               { header: "电话", sortable: true, dataIndex: 'C_PHONE',width:20},
               { header: "联系人", sortable: true, dataIndex: 'C_CONTACTS',width:20},
               { header: "Email", sortable: true, dataIndex: 'C_EMAIL',width:20},
               { header: "地址", sortable: true, dataIndex: 'C_LOCATION',width:20},
               { header: "级别", sortable: true, dataIndex: 'C_LEVE',width:10},
               { header: "URL", sortable: true, dataIndex: 'C_URL',width:30},
               { header: "操作", sortable: false, dataIndex: '',width:30,align:'center',
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
				            url : rootPath+"basedata/supplierInfo!deleteSupplier.action",
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
    				UPDATE_TAGS='UPDATE';
    				_this.priCreateWinFun();
    				
    				var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
    				
    				var data = record.data;
    				dirfrom.Ext_cid = data.C_ID;
    				dirfrom.findField('c_supplier_code').setValue(data.C_SUPPLIER_CODE);
    				dirfrom.findField('c_supplier_name').setValue(data.C_SUPPLIER_NAME);
    				dirfrom.findField('c_phone').setValue(data.C_PHONE);
    				dirfrom.findField('c_contacts').setValue(data.C_CONTACTS);
    				dirfrom.findField('c_email').setValue(data.C_EMAIL);
    				dirfrom.findField('c_location').setValue(data.C_LOCATION);
    				dirfrom.findField('c_leve').setValue(data.C_LEVE);
    				dirfrom.findField('c_url').setValue(data.C_URL);
    				dirfrom.findField('C_COMPANY').setValue(data.C_COMPANY);
    				
    			}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		}
	}
};
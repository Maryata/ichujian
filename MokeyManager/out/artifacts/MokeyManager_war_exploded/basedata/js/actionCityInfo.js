Ext.actionCityInfo = function() {
	return {
		//定义下拉框
		priData : {
			categoryData : [{key:'1',value:'是'},{key:'0',value:'否'}]
//			typeData:[{key:'1',value:'热门'},{key:'0',value:'其他'}]	
		},
		formId : 'actionCityInfo_formId',
		gridId : 'actionCityInfo_gridId',
		addWinId : 'actionCityInfo_addWinId',
		textwidth : 120,
		init : function(){
			
		},

		
		load : function(){
			//alert(11)
			var _this = this;
			//_this.createForm();
			
			var nameField = new Ext.form.TextField({
				id: _this.formId+'c_cname',
				xtype : 'textfield',
				fieldLabel : '城市名称',
				name : 'c_cname',
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
				labelWidth : 80,
				align:'center',
				items : [{
					layout : 'column',
					border :false,
					items : [{
						columnWidth : .25,
						layout : 'form',
						items : [nameField ]
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
			
			_this.priQueryFun(); //初始化
		},
		
		//？？
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
		
		
		//***************************
		priQueryFun : function(){
			var _this = this;
			
			var gridStore = Ext.getCmp(_this.gridId).store;
			//gridStore.removeAll();
			
			var paramJson = {};
			
	    	paramJson.c_cname = Ext.getCmp(_this.formId +'c_cname').getValue();
	    	
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
			if(dirfrom.Ext_cid ){
				saveParam.c_id = dirfrom.Ext_cid;
			}
			saveParam.c_type = dirfrom.findField('c_type').getValue();
			saveParam.c_name = dirfrom.findField('c_name').getValue();
			saveParam.c_number = dirfrom.findField('c_number').getValue();
		    saveParam.c_order = dirfrom.findField('c_order').getValue();
			saveParam.c_islive = dirfrom.findField('c_islive').getValue();
			
			var params = {};
			params.saveParam = Ext.encode(saveParam);
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"basedata/actionEareInfo!saveActionEareInfo.action",
				waitMsg : 'save ing...',
				success : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					alert('保存成功');
					dirfrom.Ext_cid = rsc.c_id;
					
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
			
			var typeField = _this.createComVarField({fieldLabel:"是否有效",id: _this.addWinId + 'c_islive',name:'c_islive' , data:_this.priData.categoryData , width: 140,isNoBlank:true});
			
			//查询所有的省的名字
			var functionNameStore = new Ext.data.Store({
            	//autoLoad: true,
            	proxy : new Ext.data.HttpProxy({
            		url : rootPath+"basedata/activityCityInfo!findAllName.action",
            	    timeout : 60000
            	}),
            	reader : new Ext.data.JsonReader({
            		root : 'list'
            	},
				Ext.data.Record.create([
					{ name : 'C_ID'},
					{ name : 'C_CNAME'}
				]))
            });
			
			 functionNameStore.load({
            	callback: function(obj){
				functionNameStore.insert(0,new functionNameStore.recordType());
				}
            });
			
			// 检查是否重复公共方法
			function checkExits(name,value,id) {
				if(!id){
					id = '';
				}
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", rootPath+"basedata/actionEareInfo!checkActionEareInfo.action", false);// false表示同步调用
				conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				conn.send("name="+name+"&value="+value+"&id="+id);
				var data = Ext.util.JSON.decode(conn.responseText);
				return data;
			}
			//创建省份的下拉框
			var provice_Comb = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store :functionNameStore,
				fieldLabel : '父级',
				id :_this.formId +'proviceid',
				name : 'proviceid',
				allowBlank : false,
				mode : 'local',
				displayField : 'C_CNAME',
				valueField : 'C_ID',
				resizable : true,
				width :140
			//	selectOnFocus : true 
			});
			//
			var c_form = new Ext.form.FormPanel({
				id : _this.addWinId + 'addForm',
				frame : true,
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
				labelWidth : 60,
				layout : 'form',
				fileUpload : true,
				items : [{
					xtype : 'textfield',
					fieldLabel : '城市名称',
					id : _this.addWinId + 'c_cname',
					name : 'c_cname',
					allowBlank : false,
					maxLength : 20,
					width : 140,
					validationDelay : 500,
					validator : function(value) {
				        var data = checkExits("c_name", value,Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);//dirfrom.Ext_cid = data.C_ID;
						if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
							return "城市名称重复";
						}
						return true;
				    }
				},{
					xtype : 'textfield',
					fieldLabel : '编号',
					id : _this.addWinId + 'c_code',
					name : 'c_code',
					allowBlank : false,
					maxLength : 5,
					width : 140,
					validationDelay : 500,
					validator : function(value) {
						//*************
						var data = checkExits("c_number", value,Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);
				        //var data = checkExitsNumber("c_number", value);//dirfrom.Ext_cid = data.C_ID;
						if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
							return "编号重复";
						}
						return true;
				    }
				},{
					
					layout : 'form',
					items : [provice_Comb]
	
				},{
					xtype : 'textfield',
					fieldLabel : '等级',
					id : _this.addWinId + 'c_level',
					name : 'c_level',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 5,
					width : 140
				},{
					xtype : 'textfield',
					fieldLabel : '英文名',
					id : _this.addWinId + 'c_ename',
					name : 'c_ename',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 5,
					width : 140
				},{
					xtype : 'textfield',
					fieldLabel : '短名',
					id : _this.addWinId + 'c_short_name',
					name : 'c_short_name',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 5,
					width : 140
				},{
					xtype : 'numberfield',
					fieldLabel : '排序',
					id : _this.addWinId + 'c_order',
					name : 'c_order',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 5,
					width : 140
				},typeField
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
				title: '城市管理',
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
               	{name:'P_NAME'},
                {name : 'C_CNAME'},
                {name : 'C_ENAME'},
                {name : 'C_SHORT_NAME'},
                {name:'C_CODE'},
                {name:'C_ISLIVE'},
                {name:'C_LEVEL'},
                {name : 'C_PARENT_ID'},
                {name : 'C_ORDER'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/activityCityInfo!getActionCityInfoList.action",
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
               { header: "城市名称", sortable: true, dataIndex: 'C_CNAME',width:60},
               { header: "编号", sortable: true, dataIndex: 'C_CODE',width:40},
               { header: "父级", sortable: true, dataIndex: 'C_PARENT_ID',width:40,
            		renderer:function (value, meta, record){
            	   		if(!Ext.isEmpty(record.get('P_NAME'))){
            	   			return record.get('P_NAME');
            	   		}
            	   		return value==0?'不详':value;
            	   }
               
               },
               { header: "等级", sortable: true, dataIndex: 'C_LEVEL',width:40},
               { header: "英文名", sortable: true, dataIndex: 'C_ENAME',width:40},
               { header: "短名", sortable: true, dataIndex: 'C_SHORT_NAME',width:40},
               { header: "排序", sortable: true, dataIndex: 'C_ORDER',width:40},
               { header: "是否有效", sortable: true, dataIndex: 'C_ISLIVE',width:20,
            	   renderer: function (value, meta, record) {
            		   return value==0?'否':'是';
            	   }},
               
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
				            url : rootPath+"basedata/activityCityInfo!deleteActionCityInfo.action",
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
    				
    				//dirfrom.Ext_DATA = data;
    				dirfrom.Ext_cid = data.C_ID;
    				//dirfrom.findField('c_type').setValue(data.C_TYPE);
    				dirfrom.findField('c_islive').setValue(data.C_ISLIVE);
    				dirfrom.findField('proviceid').setValue(data.C_PARENT_ID);
    				dirfrom.findField('proviceid').setRawValue(data.P_NAME);
    				dirfrom.findField('c_level').setValue(data.C_LEVEL);
    				dirfrom.findField('c_ename').setValue(data.C_ENAME);
    				dirfrom.findField('c_short_name').setValue(data.C_SHORT_NAME);
    				dirfrom.findField('c_cname').setValue(data.C_CNAME);
    				dirfrom.findField('c_order').setValue(data.C_ORDER);
    				dirfrom.findField('c_code').setValue(data.C_CODE);
    				//dirfrom.findField('c_id').setReadOnly(true);	
    			}
    		  }   
    		},   
    		this); 
    		 
    		return grid;
		}
	}
};
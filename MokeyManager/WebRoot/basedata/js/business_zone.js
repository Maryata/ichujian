UPDATE_TAGS="";
Ext.business_zone = function() {
	return {
		priData : {
			categoryData : [{key:'0',value:'否'},{key:'1',value:'是'}]
		},
		formId : 'business_zone_formId',
		gridId : 'business_zone_gridId',
		addWinId : 'business_zone_addWinId',
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
				fieldLabel : '商圈名称',
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
				labelWidth : 60,
				align:'center',
				items : [{
					layout : 'column',
					border :false,
					items : [{
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
			
			var config = {
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
				allowBlank : option.isNoBlank ? false : true
				//readOnly:true,
				//width : option.width ? option.width : _this.textwidth
			};
			if(option.anchor){
				config.anchor = option.anchor;
			}else{
				config.width = option.width ? option.width : _this.textwidth;
			}
			
			return new Ext.form.ComboBox(config);;
		},
		
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
			
		    saveParam.c_name = dirfrom.findField('c_name').getValue();
			saveParam.c_bus_areaid = dirfrom.findField('c_bus_areaid').getValue();
		    saveParam.c_islive = dirfrom.findField('c_islive').getValue();
		    saveParam.c_order = dirfrom.findField('c_order').getValue();
		    saveParam.c_longitude = dirfrom.findField('c_longitude').getValue();
		    saveParam.c_latitude = dirfrom.findField('c_latitude').getValue();
	
			var params = {};
			params.saveParam = Ext.encode(saveParam);
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"basedata/business_zone!Save.action",
				waitMsg : 'save ing...',
				success : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					alert('保存成功');
					//dirfrom.Ext_cid = dirfrom.findField('c_id').getValue();
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
			
            var typeField = _this.createComVarField({fieldLabel:"是否有效",id: _this.addWinId + 'c_islive',name:'c_islive' , data:_this.priData.categoryData , anchor : '98%',isNoBlank:true});
			
            var functionNameStore = new Ext.data.Store({
            	//autoLoad: true,
            	proxy : new Ext.data.HttpProxy({
            		url : rootPath+"basedata/business_zone!findName.action",
            	    timeout : 60000
            	}),
            	reader : new Ext.data.JsonReader({
            		root : 'list'
            	},
				Ext.data.Record.create([
					{ name : 'C_ID'},
					{ name : 'C_NAME'}
				]))
            });
            
            functionNameStore.load({
            	callback: function(obj){
				}
            });
            
            
						// 检查是否重复公共方法
			function checkExits(name,id) {
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", rootPath+"basedata/business_zone!checkName.action", false);// false表示同步调用
				conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				conn.send("c_name="+name+"&c_id="+id);
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
					fieldLabel : '商圈名称',
					id : _this.addWinId + 'c_name',
					name : 'c_name',
					allowBlank : false,
					maxLength : 20,
					anchor : '98%',
					validationDelay : 500,
					validator : function(value) {
				        var data = checkExits(value,Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid==null?"":Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);
						if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
							return "名称重复";
						}
						return true;
				    }
				},{
					xtype : 'combo',
					mode : 'local',
					store : functionNameStore,
					fieldLabel : '商区',
					id : _this.addWinId + 'c_bus_areaid',
					name : 'c_bus_areaid',
					
					displayField : 'C_NAME',
					valueField : 'C_ID',
					
					triggerAction: 'all',
					allowBlank : false,
					allowDecimals :false,
					//maxLength : 20,
					anchor : '98%'
				},typeField,
				{
					xtype : 'textfield',
					fieldLabel : '排序',
					id : _this.addWinId + 'c_order',
					name : 'c_order',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 200,
					anchor : '98%'
				},
				{
					xtype : 'textfield',
					fieldLabel : '经度',
					id : _this.addWinId + 'c_longitude',
					name : 'c_longitude',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 50,
					anchor : '98%'
				},{
					xtype : 'textfield',
					fieldLabel : '纬度',
					id : _this.addWinId + 'c_latitude',
					name : 'c_latitude',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 100,
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
				title: '功能管理',
				closable:false,
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 350, 
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
                {name : 'AREANAME'},
                {name : 'C_NAME'},
                {name : 'C_BUS_AREAID'},
                {name : 'C_ISLIVE'},
                {name : 'C_ORDER'},
                {name : 'C_LONGITUDE'},
                {name : 'C_LATITUDE'}
                
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/business_zone!GetBusinessZoneList.action",
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
               { header: "商圈名称", sortable: true, dataIndex: 'C_NAME',width:30},
               { header: "商区ID", hidden: true, dataIndex: 'C_BUS_AREAID',width:15},
               { header: "商区", sortable: true, dataIndex: 'AREANAME',width:30},
               { header: "是否有效", sortable: true, dataIndex: 'C_ISLIVE',width:30,
            	       renderer: function (value, meta, record) {
            		   return value==1?'是':'否';
            	   }},
               { header: "排序", sortable: true, dataIndex: 'C_ORDER',width:30},
               { header: "经度", sortable: true, dataIndex: 'C_LONGITUDE',width:20},
               { header: "纬度", sortable: true, dataIndex: 'C_LATITUDE',width:20},
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
				            url : rootPath+"basedata/business_zone!delete.action",
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
    				dirfrom.findField('c_name').setValue(data.C_NAME);
    				dirfrom.findField('c_bus_areaid').setValue(data.AREANAME);     
    				dirfrom.findField('c_islive').setValue(data.C_ISLIVE);
    				dirfrom.findField('c_order').setValue(data.C_ORDER);
    				dirfrom.findField('c_longitude').setValue(data.C_LONGITUDE);
    				dirfrom.findField('c_latitude').setValue(data.C_LATITUDE);
    			}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		}
	}
};
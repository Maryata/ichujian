UPDATE_TAGS="";
Ext.maininfo = function() {
	return {
		//定义下拉框
		priData : {
			categoryData : [{key:'0',value:'是'},{key:'1',value:'否'}]
		},
		formId : 'maininfo_formId',
		gridId : 'maininfo_gridId',
		gridId2 : 'maininfo_gridId2',
		addWinId : 'mainInfo_addWinId',
		textwidth : 120,
		init : function(){
			
		},
		load : function(){
			//alert(11)
			var _this = this;
			//_this.createForm();
			
			var codeField = new Ext.form.TextField({
				id: _this.formId+'c_city',
				xtype : 'textfield',
				fieldLabel : '城市',
				name : 'c_city',
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
									
									var grid = Ext.getCmp(_this.gridId2);	
    			       				var gridStore = grid.store;
			    					
			    					gridStore.load({
			   				 		params : {
									start : 0,
									limit : 300
									},
									callback: function(obj){
									}
									});
									
									
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
			var paramJson = {};
			
			paramJson.c_city = Ext.getCmp(_this.formId +'c_city').getValue();
	    	
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
		
			var rows=Ext.getCmp(_this.gridId2).getSelectionModel().getSelections();
			var data=[];
			if(rows.length<1){
			alert('请选择行业');
			return;
			}
			for(var i=0;i <rows.length;i++){
				if(rows[i].get('C_ORDER').toString().length==0||rows[i].get('C_TYPE').toString().length==0){
					alert('请填写排序和是否热门');
					return;
				}
				//alert(rows[i].get('C_TYPE').toString());
				var item ={id:rows[i].get('C_ID'),order:rows[i].get('C_ORDER'),type:rows[i].get('C_TYPE')};
				data.push(item);
			}
			
			var params = {};
			
			params.saveParam = Ext.encode(data);
			//params.cityid = dirfrom.cityid;
			params.cityid = dirfrom.findField('c_cityid').getValue();		
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"activity/maininfo!save.action",
				waitMsg : 'save ing...',
				success : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					alert('保存成功');
					//dirfrom.Ext_cid = dirfrom.findField('c_id').getValue();
//					var addWin = Ext.getCmp(_this.addWinId);
//					addWin.hidden();
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
			
			var typeField = _this.createComVarField({fieldLabel:"是否热门",id: _this.addWinId + 'C_TYPE',name:'C_TYPE' , data:_this.priData.categoryData , width: 120,isNoBlank:true});
			
			var resultsGrid_store;
			_this.createGrid1 = function(){
					//var _this = this;
					var resultsRecord = Ext.data.Record.create([
		               	{name : 'C_ID'},
		               	{name : 'C_NAME'},
		                {name : 'C_MATCH'},
		                {name : 'C_ORDER'},
		                {name : 'C_TYPE'}
		    		 ]);
		    		 // 列表用到的数据源
		    		resultsGrid_store = new Ext.data.Store({ 
		    			proxy : new Ext.data.HttpProxy({
		    				url : rootPath+"activity/maininfo!findIndustry.action",
		    				timeout : 60000
		    			}),
		    			reader : new Ext.data.JsonReader({
		    				totalProperty : 'count',
		    				root : 'list'
		    			}, resultsRecord),
		    			baseParams : {
		    			}
		    		});
		    		var resultsSm = new Ext.grid.CheckboxSelectionModel({ handleMouseDown:Ext.emtyFn}); //.CheckboxSelectionModel();
		    		var tempArray = [resultsSm,           	   
		    			/*renderer: function (value, meta, record) {
            		   if(Ext.isEmpty(value)){
            			   return value;
            		   }*/
		               { header: "ID", hidden: true, dataIndex: 'C_ID',width:15},
		               { header: "行业", sortable: true, dataIndex: 'C_NAME',width:30},
		               { header: "是否推荐", sortable: true, dataIndex: 'C_MATCH',width:20},
		               { header: "排序", sortable: true, dataIndex: 'C_ORDER',width:20,
		            	   editor: new Ext.form.NumberField({
                           allowBlank: false,
                		   allowNegative: false,
                		   maxValue: 100,
                			})
		               },
		               { header: "是否热门", sortable: true, dataIndex: 'C_TYPE',width:20,
		            	   editor: typeField,
		            	    renderer: function (value, meta, record) {
		            		   if(Ext.isEmpty(value)){
		            			   return value;
		            		   }
		            		   for(var i=0;i<_this.priData.categoryData.length;i++){
		            			   if(_this.priData.categoryData[i].key==value){
		            				   return _this.priData.categoryData[i].value;
		            			   }
		            		   }
		            		   return value;
		            	   }
		               }
		               ];
		    		
		    		var resultColumnModel = new Ext.grid.ColumnModel(tempArray);

		    		var grid = new Ext.grid.EditorGridPanel({
		    			store : resultsGrid_store,
		    			scrollable : true,
		    			id : _this.gridId2,
		    			clicksToEdit : 1,
		    			bodyStyle : 'width:100%;border:1px solid #99bbe8;',
		    			sm : resultsSm,
		    			cm : resultColumnModel,
		    			viewConfig : {
		    				forceFit : true,
		    				autoWrapRow:true
		    			},
		    			width : 550,
		    			height : 600,
		    			loadMask : { msg : LOADMASK_MSG }
		    			/*bbar : new Ext.PagingToolbar({
		        			pageSize : SYS_CONSTANT_PAGESIZE,
		        			store : resultsGrid_store
		        		})*/
		    		});
		    		return grid;	
				};
				
				
			
               var findcityStore = new Ext.data.Store({
            	//autoLoad: true,
            	proxy : new Ext.data.HttpProxy({
            		url : rootPath+"activity/maininfo!findcity.action",
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
               
             findcityStore.load({
            	callback: function(obj){
				}
            });
                           
			
			var c_form = new Ext.form.FormPanel({
				id : _this.addWinId + 'addForm',
				frame : true,
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
				labelWidth : 60,
				layout : 'form',
				fileUpload : true,
				
				items : [{
					xtype : 'combo',
					mode : 'local',
					store : findcityStore,
					fieldLabel : '城市',
					id : _this.addWinId + 'c_cityid',
					name : 'c_cityid',
					displayField : 'C_CNAME',
					valueField : 'C_ID',
					triggerAction: 'all',
					allowBlank : false,
					allowDecimals :false,
					//maxLength : 20,
					anchor : '98%'
				},{ 
								region: 'industry',
								width:550,
								height:600,
								items:[_this.createGrid1()]
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
				title: '首页推荐行业管理',
				closable:true,
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 600, 
	            height:580,	
				layout : 'form',
				frame : true,
				items : [c_form]
			});
			
			addWin.show();
		},
		
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
                {name : 'C_CITYID'},
                {name : 'C_CNAME'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"activity/maininfo!getList.action",
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
               { header: "ID", hidden: true, dataIndex: 'C_CITYID',width:15},
               { header: "城市", sortable: true, dataIndex: 'C_CNAME',width:30},
               { header: "操作", sortable: false, dataIndex: '',width:30,align:'center',
        	     renderer: function (value, meta, record) {
   				     var formatStr = "<a style='cursor: hand' href='javascript:void({0});' onclick='javscript:return false;' class='modify'>编辑</a>";   
   				     //formatStr += "&nbsp;&nbsp;&nbsp;<a style='cursor: hand' href='javascript:void({0});' onclick='javscript:return false;' class='delete'>删除</a>";
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
				            url : rootPath+"activity/maininfo!delete.action",
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
    				UPDATE_TAGS='MODIFY';
    				_this.priCreateWinFun();
    				
    				var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
    				var data = record.data;
    				
    				//Ext.getCmp('cityname').setText(data.C_CNAME,false);
    				//dirfrom.cityid = data.C_CITYID;
    				dirfrom.findField('c_cityid').setValue(data.C_CITYID);  
    				dirfrom.findField('c_cityid').setRawValue(data.C_CNAME); 
    				
    				var grid = Ext.getCmp(_this.gridId2);
    				var sm = grid.getSelectionModel();
    				
    				var gridStore = grid.store;
			    	gridStore.baseParams.cityid = record.data.C_CITYID;
			    	gridStore.load({
			    		params : {
							start : 0,
							limit : 300
						},
						callback: function(obj){
							var selectRes = [];
							gridStore.each(function(res){
								if(res.get("C_MATCH")==1){
									selectRes.push(res);
								}
							});
							sm.selectRecords(selectRes);
						}
					});
    			}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		}
	}
};
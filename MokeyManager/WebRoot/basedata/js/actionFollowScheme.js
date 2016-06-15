Ext.activityFollowScheme = function() {
	var update=false;

	return {
		//定义下拉框
		priData : {
			categoryData : [{key:'1',value:'是'},{key:'0',value:'否'}]
			//typeData:[{key:'1',value:'热门'},{key:'0',value:'其他'}]	
		},
		formId : 'activityFollowScheme_formId',
		gridId : 'activityFollowScheme_gridId',
		addWinId : 'activityFollowScheme_addWinId',
		textwidth : 120,
		init : function(){
			
		},

		
		load : function(){
			
			var _this = this;
			//_this.createForm();
			
			var nameField = new Ext.form.TextField({
				id: _this.formId+'c_name',
				xtype : 'textfield',
				fieldLabel : '方案名称',
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
		//-------------------	
				var suppStore = new Ext.data.Store( {
				// autoLoad: true,
				proxy : new Ext.data.HttpProxy( {
					url : rootPath + "basedata/fourKeyMainTainAd!getCities.action",
					timeout : 60000
				}),
				reader : new Ext.data.JsonReader( {
					root : 'list'
				}, Ext.data.Record.create( [ {
					name : 'C_ID'
				}, {
					name : 'C_CNAME'
				} ])) 
			});
 
			suppStore.load({
						callback : function(obj) {
						suppStore.insert(0, new suppStore.recordType( {
							C_ID : '', 
							C_CNAME : '全部'
						}));
					}
				}		
			);

			var supp_Comb = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : suppStore,
				fieldLabel : '城市',
				id :_this.formId +'c_cityid',
				name : 'c_cityid',
//				allowBlank : false,
				mode : 'local',
				displayField : 'C_CNAME',
				valueField : 'C_ID',
				resizable : true,
				width : _this.textwidth
//				selectOnFocus : true 
			});

			
			
			
			
			
			
//			 
//			var cityCombox= new Ext.form.ComboBox({
//					
//					xtype : 'combo',
//					fieldLabel : '城市',
//					store:_this.createCityField(),
//					id : _this.addWinId + 'cityid',
//					name : 'cityid',
//					displayField : 'C_CNAME',
//					valueField : 'C_ID',
//					triggerAction: 'all',
//					width:150
//			});
			
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
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [supp_Comb]
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
									Ext.getCmp('gridId2').store.reload();  			
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
		

		//查询所有的城市
//		createCityField:function(){
//			var _this =this;
//			var functionNameStore = new Ext.data.Store({
////            	autoLoad: true, 
//            	proxy : new Ext.data.HttpProxy({
//            		url : rootPath+"basedata/activityFollowScheme!findAllCity.action",
//            	    timeout : 60000
//            	}),
//            	reader : new Ext.data.JsonReader({
//            		root : 'list'
//            	},
//				Ext.data.Record.create([
//					{ name : 'C_ID'},
//					{ name : 'C_CNAME'},
//					{name:'C_NAME'},
//				]))
//            });
//			functionNameStore.load({
//            	callback: function(obj){
//					functionNameStore.insert(0,new functionNameStore.recordType({C_ID:'',C_NAME:'全部'}));
//				}
//            });
//			
//		},
		//
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
			
	    	paramJson.c_name = Ext.getCmp(_this.formId +'c_name').getValue();
	    	paramJson.c_cityid = Ext.getCmp(_this.formId +'c_cityid').getValue();
	    	
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
		
		priSaveFun : function(ProvinceID,ID){
			var _this = this;
			var dirfrom = Ext.getCmp(_this.addWinId+"addForm").getForm();
			if (!dirfrom.isValid()) {
				return;
			}
			var rows= Ext.getCmp('gridId3').store;
			
//			var rows=Ext.getCmp('bottom2Grid').getSelectionModel().getSelections();
			var data=[];
			for(var i=0;i <rows.getCount();i++){
//				alert(rows.getAt(i).get("C_ID"));
//				alert(rows.getAt(i).get("C_ORDER"));
				var item ={id:rows.getAt(i).get("C_ID"),number:rows.getAt(i).get("C_ORDER")};
				data.push(item);
			}
			//判断选择的推送方式			
			var schemetype =Ext.getCmp('radioid').getValue();
			if(schemetype==0){
				Ext.Ajax.request({
					url : rootPath+"basedata/activityFollowScheme!saveActivityFollowScheme.action", 
					timeout : 120000,
					success: function(result, obj){
					alert('指定成功!');
				},
				failure: function(result, obj){
					alert('指定失败');
				},
					params: {ProvinceID:ProvinceID,schemeName:Ext.getCmp(_this.addWinId + 'schemeName').getValue(), cityid:0 ,data:Ext.encode(data),MainId:ID}
				})	
			}else if(schemetype==1){
				Ext.Ajax.request({
					url : rootPath+"basedata/activityFollowScheme!saveActivityFollowScheme.action", 
					timeout : 120000,
					success: function(result, obj){
					alert('指定成功!');
				},
				failure: function(result, obj){
					alert('指定失败');
				},
					params: {ProvinceID:ProvinceID,schemeName:Ext.getCmp(_this.addWinId + 'schemeName').getValue(), cityid:Ext.getCmp(_this.addWinId + 'city').getValue() ,data:Ext.encode(data)}
				})	
			}
		},

		
		priCreateWinFun : function(Province,C_NAME,ProvinceID,checkID,ID){//如果是修改的话，方案的ID 和名称 都传入进来
			var _this = this;
			var addWin = Ext.getCmp(_this.addWinId);
			if(addWin){
				addWin.show();
				return;
			}
			//单选按钮
			 var radiogroup;
			 if(checkID!=0){
				 radiogroup= new Ext.form.RadioGroup({
		                // width: 140,
						 id:'radioid',
		                 columns: 2,
		                 items: [{
		                	 id:'check1',
		                     name: 'schemeType',
		                     inputValue: '0',
		                     boxLabel: '默认方案',
		                     width:120
		                     
		                 },{
		                	 id:'check2',
		                     name: 'schemeType',
		                     inputValue: '1',
		                     width:20,
		                     boxLabel: '省',
		                    	 checked: true
		                 }]
		             });
			 } else if(checkID==0){
				 radiogroup= new Ext.form.RadioGroup({
		                // width: 140,
						 id:'radioid',
		                 columns: 2,
		                 items: [{
		                	 id:'check1',
		                     name: 'schemeType',
		                     inputValue: '0',
		                     boxLabel: '默认方案',
		                     width:120,
		                     checked: true
		                     
		                 },{
		                	 id:'check2',
		                     name: 'schemeType',
		                     inputValue: '1',
		                     width:20,
		                     boxLabel: '省'
		                    	
		                 }]
		             });
			 }
			 
			 
//			 if(checkID!=0){
//				 Ext.getCmp('check2').setValue(true);
//			 }
			 
			//获取单选组的值
             radiogroup.on('change', function (rdgroup, checked) {
            	 schemetype =checked.getRawValue();
             });
            var appRecord = Ext.data.Record.create([{ name : 'C_ID'}
				, { name : 'C_CNAME'}
			]);
            
			var resultsGrid_store;
			var resultsGrid2_store;
			var record ;
			 //创建左边的grid
		 		_this.createGrid1 = function(){
					//var _this = this;
					var resultsRecord = Ext.data.Record.create([
		               	{name : 'C_ID'},
		               	{name :'C_CNAME'},
		                {name : 'C_INDUSTRUID'},
		                {name : 'P_NAME'}
		    		 ]);
		    		 // 列表用到的数据源
		    		resultsGrid_store = new Ext.data.Store({ 
		    			proxy : new Ext.data.HttpProxy({
		    				url : rootPath+"basedata/activityFollowScheme!findIndustryBrand.action",
		    				timeout : 60000
		    			}),
		    			reader : new Ext.data.JsonReader({
		    				totalProperty : 'count',
		    				root : 'list'
		    			}, resultsRecord),
		    			baseParams : {
		    			}
		    		});
		    		var resultsSm = new Ext.grid.CheckboxSelectionModel({singleSelect : false}); //.CheckboxSelectionModel();
		    		var tempArray = [resultsSm,
		               { header: "ID", hidden: true, dataIndex: 'C_ID',width:15},
		               { header: "行业名称", sortable: true, dataIndex: 'C_INDUSTRYID',width:45,
		            		renderer:function (value, meta, record){
		            	   		if(!Ext.isEmpty(record.get('P_NAME'))){
		            	   			return record.get('P_NAME');
		            	   		}
		            	        return value==0?'':value;
		            	        
		            	   }
		               },
		               { header: "品牌名称", sortable: true, dataIndex: 'C_CNAME',width:45}
		               ];
		    		
		    		var resultColumnModel = new Ext.grid.ColumnModel(tempArray);
		    		
		//    		var rows= resultColumnModel.getSelectionModel().getSelections();
		    		var grid = new Ext.grid.EditorGridPanel({
		    			store : resultsGrid_store,
		    			scrollable : true,
		    			id : 'gridId2',
		    			clicksToEdit : 1,
		    			bodyStyle : 'width:100%;border:1px solid #99bbe8;',
		    			sm : resultsSm,
		    			cm : resultColumnModel,
		    			viewConfig : {
		    				forceFit : true,
		    				autoWrapRow:true
		    			},
		    			width : 400,
		    			height : 490,
		    			loadMask : { msg : LOADMASK_MSG },
		    			bbar : new Ext.PagingToolbar({
		        			pageSize : SYS_CONSTANT_PAGESIZE,
		        			store : resultsGrid_store
		        		})
		    		});
		    		return grid;	
				};
				//查询方法
		    	_this.priQueryFun1 = function(){
		    		var gridStore = Ext.getCmp('gridId2').store;
						//gridStore.removeAll();
						
					var paramJson = {};
						
				    paramJson.c_industryid = Ext.getCmp(_this.addWinId + 'c_industryid').getValue();
				    paramJson.c_cname = Ext.getCmp(_this.addWinId + 'c_cname').getValue();	
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
		    	};
//			//创建右边的grid
            _this.createGrid2=function(){
        		var resultsRecord1 =  Ext.data.Record.create([
	               	{name : 'C_ID'},
	               	{name :'C_CNAME'},
	                {name : 'C_INDUSTRYID'},
	                {name : 'P_NAME'},
	                {name:'C_ORDER'}
	    		 ]);
           var 	 resultsGrid2_store;
           if(!update){
        	   resultsGrid2_store = new Ext.data.GroupingStore({});
           }else{ 
            	 resultsGrid2_store = new Ext.data.GroupingStore({ 
	    			proxy : new Ext.data.HttpProxy({
	    				url : rootPath+"basedata/activityFollowScheme!getSaveItemsForUpdate.action", 
	    				timeout : 60000
	    			}),
	    			reader : new Ext.data.JsonReader({
	    				root : 'list'
	    			}, resultsRecord1),
	    			//sortInfo: {field:"C_ID", direction:"ASC"}, //数据排序  
			  	 	groupField: "P_NAME" //分组字段  
	    		});
           }	 
	            

            	var tempArray = [
		               { header: "ID", hidden: true, dataIndex: 'C_ID',width:15},
		               { header: "行业名称", sortable: true, dataIndex: 'C_INDUSTRYID',width:45,
		            		renderer:function (value, meta, record){
		            	   		if(!Ext.isEmpty(record.get('P_NAME'))){
		            	   			return record.get('P_NAME');
		            	   		}
		            	        return value==0?'':value;
		            	   }
		               },
		               { header: "品牌名称", sortable: true, dataIndex: 'C_CNAME',width:45},
		               { header: "排序", sortable: true, dataIndex: 'C_ORDER',width:45,
		            	   editor: new Ext.form.NumberField({
                           allowBlank: false,
                		   allowNegative: false,
                		   maxValue: 100000
                			})
		               
		               	}
		               ];
            	var resultColumnModel = new Ext.grid.ColumnModel(tempArray);
        		var grid2 =new Ext.grid.EditorGridPanel({
	    			store : resultsGrid2_store,
	    			//scrollable : true,
	    			autoScroll:true,
	    			id : 'gridId3',
	    			clicksToEdit : 1,
	    			bodyStyle : 'width:100%;border:1px solid #99bbe8;',
	    			//sm : resultsSm,
	    			cm : resultColumnModel,
//	    			view:new Ext.grid.GroupingView({  
//		              	 forceFit:true,  
//		              	 groupTextTpl:'{text}({[values.rs.length]}  {[values.rs.length>1?"Items":"Item"]})'  
//		               
//		            }),
	    			viewConfig : {
	    				forceFit : true,
	    				autoWrapRow:true
	    			},
//	    			frame:true,
//	                collapsible:true,  
//	                animCollapse:false,              
//	                iconCls: 'icon-grid',  
	    			width : 340,
	    			height : 550,
	    			loadMask : { msg : LOADMASK_MSG }
          	  });
        		
	        	grid2.on('cellclick', function (grid, rowIndex, columnIndex, e) {
	    				 record = grid.getStore().getAt(rowIndex);			
				});
        		return grid2;
        	};
             //根据省份id查询对应的城市id
			var city_Store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : rootPath+"basedata/activityFollowScheme!findAllCityById.action",
					timeout : 60000
				}),
				reader : new Ext.data.JsonReader({
					root : 'list'
				}, appRecord),
				baseParams : {						
				}
			});
			//处理城市的下拉框		
			var functionNameStore = new Ext.data.Store({
            	autoLoad: true,            	proxy : new Ext.data.HttpProxy({
            		url : rootPath+"basedata/activityFollowScheme!findAllProvince.action",
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
			
			//处理所有行业的下拉框
			var functionAllIndustryStore = new Ext.data.Store({
				    autoLoad: true, 
				    proxy : new Ext.data.HttpProxy({
            		url : rootPath+"basedata/activityFollowScheme!findAllIndustryName.action",
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
			
			
			functionAllIndustryStore.load({
            	callback: function(obj){
				functionAllIndustryStore.insert(0,new functionAllIndustryStore.recordType(
						{
							C_ID:'',
							C_NAME:'全部'
						}
					));
				}
            });
//			functionAllIndustryStore.load({
//				callback:function(obj){
//					functionAllIndustryStore.insert(0,new functionAllIndustryStore.recordType({
//						C_ID:'',
//						C_NAME:'全部'
//					}))
//				}
//			})
//			
             //省份下拉框
             var provinceField;
             if(Province==null){
             provinceField= new Ext.form.ComboBox({
					columnWidth:.30,
					xtype : 'combo',
						//fieldLabel : '城市',
					store:functionNameStore,
					id : _this.addWinId + 'c_provinceid',
					name : 'c_provinceid',
					displayField : 'C_CNAME',
					valueField : 'C_ID',
					triggerAction: 'all',
					width:170,
					typeAhead : true,
					loadStatus : false,
					resizable : true
				
             });
             }else{
            	 provinceField= new Ext.form.ComboBox({
 					columnWidth:.30,
 					xtype : 'combo',
 						//fieldLabel : '城市',
 					store:functionNameStore,
 					id : _this.addWinId + 'c_provinceid',
 					name : 'c_provinceid',
 					displayField : 'C_CNAME',
 					valueField : 'C_ID',
 					triggerAction: 'query',
 					width:170,
 					typeAhead : true,
 					loadStatus : false,
 					resizable : true,
 					listeners:{  
 					   afterrender:function(functionNameStore){  
            		 functionNameStore.setValue(Province);  
 				   }  
 			}
              });
            	 
            	 
            	  
             }
             //动态创建城市items
            _this.PushCheckIiems = function (data,pid){
            	if(!data){
            		data = [];
            	}
            	 var itemsGroup = Ext.getCmp(_this.addWinId + 'city');
                 if(itemsGroup){
                 	itemsGroup.destroy();
                 }
            	
            	var items ;
            	var  item1;
            	var item2;
            	if(update){//然后根据传入进来的城市ID 进行判断.如果是 0的话 则为默认
            		if(checkID!='0'){
            		Ext.getCmp('gridId2').store.reload();
					Ext.Ajax.request({
				        url : rootPath+"basedata/activityFollowScheme!getSelectCity.action",
				        timeout : 120000,
				        success: function(result, obj){
				            var jsonFromServer = Ext.util.JSON.decode(result.responseText);
				            item1=Manager.sys.From.createGroupItem('city',jsonFromServer.list,{labelFiled:'C_CNAME',valueFiled:'C_ID',checked:true} ); //动态创建复选框
				       item1[0].inputValue;	
				        Ext.getCmp('gridId3').store.baseParams.id= item1[0].inputValue;
				        Ext.getCmp('gridId3').store.baseParams.cityid=checkID;  //将城市的ID  丢后台 查询 是否是 默认方案
				        Ext.getCmp('gridId3').store.reload();
				            
				            var  str=jsonFromServer.ids;//将已选中的城市  丢给下面去 ，可以查出没有 选中的城市的数组
				            Ext.Ajax.request({
						            url : rootPath+"basedata/activityFollowScheme!getNotSelectCity.action",
						            timeout : 120000,
						            success: function(result, obj){
						                var jsonFromServer = Ext.util.JSON.decode(result.responseText);
						                item2=Manager.sys.From.createGroupItem('city',jsonFromServer.list,{labelFiled:'C_CNAME',valueFiled:'C_ID',checked:false} ); //没有选择的数组
						                
						                
;						                items=item1.concat(item2);
						                
						                itemsGroup = new Ext.form.CheckboxGroup({
							    			xtype: 'checkboxgroup',
										    fieldLabel: '',
										    id : _this.addWinId + 'city',
											name : 'city',
										    itemCls: 'x-check-group-alt',
										    vertical: false,
										    columns: 5,
							        	    items:items
							        	    
							        	});  
						                
						                var city_items = Ext.getCmp(_this.addWinId + 'city_items');
							        	//Ext.getCmp(_this.addWinId + 'city_items_clem');
							        	city_items.setVisible(true);  
							        	city_items.add(itemsGroup);
							        	city_items.doLayout();  
						                
				            },
						            failure: function(result, obj){
						            }, 
						            params: {provinceid : pid,str:str}
						        });
				        },
				        failure: function(result, obj){
				        }, 
				        params: {provinceid : pid}
				    });
	
            	}else if(update&&checkID=='0')//如果修改 选择的是默认的方案
            		//只要修改  ID 为grid2的 表格里面的显示数据就可以了 ，保存都是没有问题的 
            	{
            		 Ext.getCmp('gridId3').store.baseParams.MainId=ID;
            		 Ext.getCmp('gridId3').store.reload();
            	}else{/*
            		items= Manager.sys.From.createGroupItem('city',data,{labelFiled:'C_CNAME',valueFiled:'C_ID',checked:true} ); 
            		  itemsGroup = new Ext.form.CheckboxGroup({
			    			xtype: 'checkboxgroup',
						    fieldLabel: '',
						    id : _this.addWinId + 'city',
							name : 'city',
						    itemCls: 'x-check-group-alt',
						    vertical: false,
						    columns: 5,
			        	    items:items
			        	    
			        	});  
		                
		                var city_items = Ext.getCmp(_this.addWinId + 'city_items');
			        	//Ext.getCmp(_this.addWinId + 'city_items_clem');
			        	city_items.setVisible(true);  
			        	city_items.add(itemsGroup);
			        	city_items.doLayout();   
            	*/}
//	        	var cityIds = Ext.getCmp(_this.addWinId + 'city').getValue();
//	        	alert(cityIds);
            	}else{
            		items= Manager.sys.From.createGroupItem('city',data,{labelFiled:'C_CNAME',valueFiled:'C_ID',checked:true} ); 
          		  itemsGroup = new Ext.form.CheckboxGroup({
			    			xtype: 'checkboxgroup',
						    fieldLabel: '',
						    id : _this.addWinId + 'city',
							name : 'city',
						    itemCls: 'x-check-group-alt',
						    vertical: false,
						    columns: 5,
			        	    items:items
			        	    
			        	});  
		                
		                var city_items = Ext.getCmp(_this.addWinId + 'city_items');
			        	//Ext.getCmp(_this.addWinId + 'city_items_clem');
			        	city_items.setVisible(true);  
			        	city_items.add(itemsGroup);
			        	city_items.doLayout();  
            		
            		
            	}
            };
            //加载城市数据
			_this.privateGetCity = function(pid){
				Ext.Ajax.request({
		            url : rootPath+"basedata/activityFollowScheme!findAllCityById.action",
		            timeout : 120000,
		            success: function(result, obj){
		                var jsonFromServer = Ext.util.JSON.decode(result.responseText);
		                 _this.PushCheckIiems(jsonFromServer.list,pid);
	                },
		            failure: function(result, obj){
		            }, 
		            params: {provinceid : pid}//根据省查询出城市来
		        });
			};
  			//省
             provinceField.on('change',function(combo,newValue ,oldValue ){
            	// alert(newValue)
				//重新加载city
            	_this.privateGetCity(newValue);
			});
             if(Province!=null){//根据传进来的省的长度,判断是修改还是添加--这里是修改，根据省的ID 查出市
            	 _this.privateGetCity(ProvinceID);	
             }
             
           
				var c_form = new Ext.form.FormPanel({
					id:_this.addWinId+"addForm",
					frame : true,
					bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
					width:800,
					height:700,
					labelWidth :60,
					items:[{
						layout:'column',
						items:[
							{
								columnWidth:.25,
								items:[radiogroup]
							},{
								columnWidth:.30,
								items:[provinceField]
							},{
								columnWidth:.30,
								layout:'form',
								items:[{
										xtype:'textfield',
										id: _this.addWinId + 'schemeName',
										fieldLabel:'方案名称',

										allowBlank : false,

										value:C_NAME,

										width:180
								}]
							}]
					},{
						
						hidden:true,
						items:[],
						height:120
						
					},{
						bodyStyle : 'padding:1px;border-bottom:1px solid #99bbe8;',
						//id : _this.addWinId + 'city_items_clem',
						items : [{
							id : _this.addWinId + 'city_items',
							hidden:true,
							items : []
						}]
					},{
						layout:'column',
						items:[{
								layout:'form',
								columnWidth:.40,
								items:[{
									xtype : 'combo',
									fieldLabel : '行业类型',
									store:functionAllIndustryStore,
									id : _this.addWinId + 'c_industryid',
									name : 'c_industryid',
									displayField : 'C_NAME',
									valueField : 'C_ID',
									triggerAction: 'all',
									width:160
								}]
						},{
								layout:'form',								
								columnWidth:.5,
								labelWidth :30,
								items:[{
									xtype : 'textfield',
									fieldLabel : '品牌',									
									id : _this.addWinId + 'c_cname',
									name : 'c_cname',
									autoWidth:true
								}]
						},{
							columnWidth:.10,
							items:[{
								xtype : 'button',
								
								text : '查询',
								width : 50,
								//buttonAlign: 'center',
								handler : function() {
									_this.priQueryFun1();
								}
							}]
						}]
					},{
						width:800,
						height:650,
						layout:'border',
						items:[{ 
								region: 'west',
								width:400,
								height:650,
								items:[_this.createGrid1()]
							},{
								region:'center',
								width:60,
								height:650,
								items:[{
//										title:'aa',
										height:230
										
									},{
										
										height:80,
										items:[{
												xtype : 'button',
												width : 60,
												height:10,
												text : '-->',
												handler : function() {
														var leftSelects =Ext.getCmp('gridId2').getSelectionModel().getSelections();
								                        resultsGrid_store.remove(leftSelects);
//								                        resultsGrid2_store.add(leftSelects);
								                        Ext.getCmp('gridId3').store.add(leftSelects);
					    	        				}
											},{
												width:60,
												height:38
											},{
												xtype : 'button',
												width : 60,
												height:10,
												text : '<--',
												handler : function() {
												//var record = grid.getStore().getAt(rowIndex);
												//Ext.getCmp('gridId3').getStore().getAt(rowIndex);
														var rightSelects =record;
														Ext.getCmp('gridId3').store.remove(rightSelects);
//								                        resultsGrid2_store.remove(rightSelects);
								                        resultsGrid_store.add(rightSelects);
								                       // resultsGrid_store.reload();
						            				}
											}]
									},{
//										title:'cc',
										height:300
									}]
							},{
								region:'east',
								width:340,
								height:650,
								items:[_this.createGrid2(C_NAME)]
							}]
					}],
					buttonAlign: 'center',
					buttons : [{
					text : '返回',
		            handler : function() {
			            	addWin.close();
							var grid = Ext.getCmp(_this.gridId);
							grid.store.reload();
			            }
		        	},{
			        	text : '保存',
			            handler : function() {
			            	_this.priSaveFun(ProvinceID,ID);
			            }
			        } ]	
				}),
				
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '方案管理',
				closable:false,
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
//	            autoWidth : true,
	            width:850,
	            height:550,	
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
                {name : 'C_NAME'},
                {name:'C_CITYID'},
                {name:'C_DATA'},
                {name : 'C_ORDER'},
                {name : 'C_ISLIVE'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/activityFollowScheme!getActivityFollowSchemeList.action",
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
               { header: "方案名称", sortable: true, dataIndex: 'C_NAME',width:60},
               { header: "城市", sortable: true, dataIndex: 'C_CITYID',width:40,
            	        renderer:function (value, meta, record){
            	   		if(!Ext.isEmpty(record.get('P_NAME'))){
            	   			return record.get('P_NAME');
            	   		}
            	        return value==0?'':value;
            	   }
               },
               { header: "时间", sortable: true, dataIndex: 'C_DATA',width:40},
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
				            url : rootPath+"basedata/activityFollowScheme!deleteActivityFollowScheme.action",
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
    			}else if(record&&control=='modify'){//&1
    				update=true;
    				//通过 这里根据城市查询 返回省，然后赋值给界面的下拉框
					if(record.get('C_CITYID')!=0){
    				Ext.Ajax.request({
			            url : rootPath+"basedata/activityFollowScheme!returnProvince.action",
			            timeout : 120000,
			            success: function(result, obj){
			                var jsonFromServer = Ext.util.JSON.decode(result.responseText);
			                _this.priCreateWinFun(jsonFromServer.Province,record.get('C_NAME'),jsonFromServer.ProvinceID,record.get('C_CITYID'));   //1.城市的名字,用于显示 不让用户修改 2.方案的名字 3.省的ID 4.城市的ID  用来判断 选择默认还是选择城市
		                },
			            failure: function(result, obj){
			            	alert('加载失败!.');
			            }, 
			            params: {C_ID:record.get('C_CITYID')}
			        });
					}else{
						_this.priCreateWinFun("",record.get('C_NAME'),"",record.get('C_CITYID'),record.get('C_ID'));
						Ext.getCmp('gridId2').store.reload();
					}
    				
    				
//    				var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
//    				var data = record.data;
    					
    			}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		}
	}
};
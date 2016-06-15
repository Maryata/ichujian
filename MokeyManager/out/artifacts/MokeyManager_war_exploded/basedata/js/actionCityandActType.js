Ext.activityFollowScheme = function() {
	var  tip=false;
	var ids="";
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
		savePicItem : {pic : '', id : ''},
		init : function(){
			
		},
  
		
		load : function(){
			var _this = this;
			//_this.createForm();
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
 
			suppStore.load( {
				callback : function(obj) {
				suppStore.insert(0, new suppStore.recordType( {
					C_ID : '0',
					C_CNAME : '默认城市' 
				}));
				suppStore.insert(0, new suppStore.recordType( {
					C_ID : '',
					C_CNAME : '全部' 
				}));
			} 
			});

			var supp_Comb = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : suppStore,
				fieldLabel : '城市',
				id :'city',
				name : 'supp',
				allowBlank : false,
				editable : false,
				mode : 'local',
				displayField : 'C_CNAME',
				valueField : 'C_ID',
				resizable : true,
				editable : false,
				width : _this.textwidth,
				selectOnFocus : true 
			});
			var menuStore = new Ext.data.Store( {
				// autoLoad: true,
				proxy : new Ext.data.HttpProxy( {
					url : rootPath + "basedata/cityandActTypeAction!getActType.action",
					timeout : 60000
				}),
				reader : new Ext.data.JsonReader( {
					root : 'list'
				}, Ext.data.Record.create( [ {
					name : 'C_ID'
				}, {
					name : 'C_NAME'
				} ]))
			});

			menuStore.load(
					{
						callback : function(obj) {
						menuStore.insert(0, new menuStore.recordType( {
							C_ID : '', 
							C_NAME : '全部'
						}));
					}
					});

			var menu_Comb = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : menuStore,
				fieldLabel : '活动专题',
				id :'act',
				allowBlank : false,
				editable : false,
				mode : 'local',
				displayField : 'C_NAME',
				valueField : 'C_ID',
				resizable : true,
				editable : false,
				width : _this.textwidth,
				selectOnFocus : true
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
					border : false,
					items : [ {
						columnWidth : .30,
						layout : 'form',
						items : [ supp_Comb ]
					}, {
						columnWidth : .30,
						layout : 'form',
						items : [ menu_Comb ]
					}, {
						columnWidth : .1,
						border : false,
						layout : 'column',
						items : [ {
							columnWidth : .4,
							border : false,
							layout : 'form',
							items : [ {
								xtype : 'button',
								text : '查询',
								width : 50,
								handler : function() {
									_this.MessageQuery();
								}
							} ]
						} ]
					}, {
						columnWidth : .1,
						border : false,
						layout : 'column',
						items : [ {
							columnWidth : .4,
							border : false,
							layout : 'form',
							items : [ {
								xtype : 'button',
								text : '添加',
								width : 50,
								handler : function() {
								tip=false;
								_this.priCreateWinFun();
								}
							} ]
						} ]
					} ]
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
			
			Ext.getCmp('grid').store.load({params:{start:0,limit:5}});
		},
			priCreateWinFun : function(a,b,id,CONTENTID){   //如果是 修改的话，默认显示城市和 内容类别，然后传一个ID 过来 查询出 这个ID 下面 保存的活动内容
			//id  是第一张表的 ID 
			var _this = this;
				
				var addWin = Ext.getCmp(_this.addWinId);
				if(addWin){
					addWin.show();
					return;
				}
		
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
				
				suppStore.load( {
					callback : function(obj) {
					suppStore.insert(0, new suppStore.recordType( {
						C_ID : '0',
						C_CNAME : '默认城市' 
					}));
				} 
				});
			

				var city_Comb ;
				if(tip){      
					city_Comb= new Ext.form.ComboBox( {
						typeAhead : true,
						loadStatus : false,
						triggerAction : 'query',
						store : suppStore,
						fieldLabel : '城市',
						id :'savecity',
						name : 'supp',
						allowBlank : false,
						editable : false,
						mode : 'local',
						displayField : 'C_CNAME',
						valueField : 'C_ID',
						resizable : true,
						editable : false,
						width : _this.textwidth,
						selectOnFocus : true ,
						listeners:{  
							   afterrender:function(city_Comb){  
						city_Comb.setValue(a);  
						   }  }
					});
				}else {
				
				city_Comb= new Ext.form.ComboBox( {
					typeAhead : true,
					loadStatus : false,
					triggerAction : 'all',
					store : suppStore,
					fieldLabel : '城市',
					id :'savecity',
					name : 'supp',
					allowBlank : false,
					editable : false,
					mode : 'local',
					displayField : 'C_CNAME',
					valueField : 'C_ID',
					resizable : true,
					editable : false,
					width : _this.textwidth,
					selectOnFocus : true 
					
				});
				}
				var activityStore = new Ext.data.Store( { 
					// autoLoad: true,
					proxy : new Ext.data.HttpProxy( {
						url : rootPath + "basedata/fourKeyMainTainAd!getActivityCat.action",
						timeout : 60000
					}),
					reader : new Ext.data.JsonReader( {
						root : 'list'
					}, Ext.data.Record.create( [ {
						name : 'C_ID'
					}, {
						name : 'C_NAME' 
					} ]))
				});
					activityStore.load( );
				var activity=new Ext.form.ComboBox( {
						typeAhead : true,
						loadStatus : false,
						triggerAction : 'all',
						store : activityStore,
						fieldLabel : '活动内容类别',
						id :'saveact',			
						allowBlank : false,
						editable : false,
						mode : 'local',
						displayField : 'C_NAME',
						valueField : 'C_ID',
						resizable : true,
						editable : false,
						width : 120,
						selectOnFocus : true
					})
					
					
			
				
				//------创建最底部的城市查询
				var bottomSuppStore = new Ext.data.Store( {
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

				bottomSuppStore.load();

				var bottomCity_Comb = new Ext.form.ComboBox( {
					typeAhead : true,
					loadStatus : false,
					triggerAction : 'all',
					store : bottomSuppStore,
					fieldLabel : '城市',
					id :'bottominnercity',
					name : 'supp',
					allowBlank : false,
					editable : false,
					mode : 'local',
					displayField : 'C_CNAME',
					valueField : 'C_ID',
					resizable : true,
					editable : false,
					width : _this.textwidth,
					selectOnFocus : true 
				});
				//------创建底部内容类别查询
				
				
			//*-------------------------
				
				
				
				var bottomMenuStore = new Ext.data.Store( {
					// autoLoad: true,
					proxy : new Ext.data.HttpProxy( {
						url : rootPath + "basedata/cityandActTypeAction!getActType.action",
						timeout : 60000
					}),
					reader : new Ext.data.JsonReader( {
						root : 'list'
					}, Ext.data.Record.create( [ {
						name : 'C_ID'
					}, {
						name : 'C_NAME'
					} ]))
				});

				bottomMenuStore.load();

				var bottomContent ;
				
//				if(tip){
//					bottomContent= new Ext.form.ComboBox( {
//						typeAhead : true,
//						loadStatus : false,
//						triggerAction : 'query',
//						store : bottomMenuStore,
//						fieldLabel : '活动专题',
//						id :'bottomcontent', 
//						name : 'menu',
//						allowBlank : false,
//						editable : false,
//						mode : 'local',
//						displayField : 'C_NAME',
//						valueField : 'C_ID',
//						resizable : true,
//						editable : false,
//						width : 120,
//						selectOnFocus : true,
//						listeners:{  
//						   afterrender:function(bottomContent){  
//						bottomContent.setValue(b);  
//					   }  }
//					});
//				}else{
//					bottomContent= new Ext.form.ComboBox( {
//						typeAhead : true,
//						loadStatus : false,
//						triggerAction : 'all',
//						store : bottomMenuStore,
//						fieldLabel : '活动专题',
//						id :'bottomcontent',
//						name : 'menu',
//						allowBlank : false,
//						editable : false,
//						mode : 'local',
//						displayField : 'C_NAME',
//						valueField : 'C_ID',
//						resizable : true,
//						editable : false,
//						width : 120,
//						selectOnFocus : true
//					});
//					}
				if(tip){
					bottomContent= new Ext.form.TextField({
				        width: 120,
			            allowBlank: false,
			            maxLength: 20,
			            id:'bottomcontent',
		                fieldLabel: '活动专题'});
					 Ext.getCmp('bottomcontent').setValue(b); 
				}else{
					bottomContent= new Ext.form.TextField({
				        width: 120,
			            allowBlank: false,
			            maxLength: 20,
			            id:'bottomcontent',
		                fieldLabel: '活动专题'});
					}
				
			
				var txtpassword = new Ext.form.TextField({
					width: 140,
					allowBlank: false,
					maxLength: 20,
					id:'bottomTitle',
					fieldLabel: '标题'
				});
				
				
				//创建内部表单
				var c_form = new Ext.form.FormPanel({
						border:false,
						id:_this.addWinId+"addForm",
						bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
						width:800,
						height:540,
						closable:true,
						labelWidth :60,
						items:[{
							layout:'column',
							items:[{
									layout:'form',
									columnWidth:.25,
									items:[city_Comb]
							},{ 
								labelWidth : 80,
									layout:'form',
									columnWidth:.30,
									items:[bottomContent] 
							}]
						},{
							layout:'column',
							items:[{
									layout:'form',
									columnWidth:.25,
									items:[bottomCity_Comb]
							},{
								labelWidth : 80,
									layout:'form',								
									columnWidth:.30,
									items:[activity ] 
							},{
								layout:'form',								
								columnWidth:.25,
								items:[txtpassword] 
							},{
								columnWidth:.20,
								items:[{
									xtype : 'button',
									text : '查询',
									width : 60,
									//buttonAlign: 'center',
									handler : function() {
										_this.queryForButtom(); 
									}
								}]
							}]
						},{ border:false,
							width:800,
							height:550,
							layout:'border',
							items:[{ 
									frame:false,
									region: 'north',
									width:800,
									height:170,
									items:[_this.createTopGrid()]
								},{
									border:false,
									region:'center',
									width:800,
									height:50,
									layout:'column',
									items:[{
											frame:false,
											columnWidth:.45,
											height:50
											
										},{
											columnWidth:.10,
											height:50,
											layout:'column',
											items:[{
													xtype : 'button',
													width : 15,
													height:50,
													text : '↓',
													handler : function() {
//				
//															var leftSelects =Ext.getCmp('gridId2').getSelectionModel().getSelections();
//									                        resultsGrid_store.remove(leftSelects);
//									                        resultsGrid2_store.add(leftSelects);
														var   topStore=Ext.getCmp('bottomGrid').store;
														var   bottomStore=Ext.getCmp('bottom2Grid').store;
														var  topSelects=Ext.getCmp('bottomGrid').getSelectionModel().getSelections();
								    	        		topStore.remove(topSelects);
								    	        		bottomStore.add(topSelects);
								    	        		Ext.getCmp('bottom2Grid').getSelectionModel().selectAll();
											}
												},{
													border:false,
													width:49,
													height:50
												},{ 
													xtype : 'button',
//													width : 35,
													height:50,
													text : '↑',
													handler : function() {
													var   topStore=Ext.getCmp('bottomGrid').store;
													var   bottomStore=Ext.getCmp('bottom2Grid').store;
													var  topSelects=Ext.getCmp('bottom2Grid').getSelectionModel().getSelections();
													bottomStore.remove(topSelects);
													if(tip){
													}else{
													topStore.add(topSelects);
													}
													Ext.getCmp('bottom2Grid').getSelectionModel().selectAll();
							            				}
												}]
										},{
											height:50
										}]
								},{
									border:false,
									frame:false,
									region:'south',
									width:750,
									height:340,
									items:[_this.createBottomGrid(CONTENTID)]
								}]
						}],
						buttonAlign: 'center',
						buttons : [ {
				        	text : '保存',
				            handler : function() {
							_this.saveall(id,CONTENTID, Ext.getCmp('bottomcontent').getValue());//将ID 传过去，可以用来修改不用于保存
							
				            }
				        } ]	
					}),
					
				addWin = new Ext.Window({
					border:false,
					id : _this.addWinId,
					title: '专题管理',
					closable:true,
					plain:true,	 
		            modal : true,           
		            draggable : true,
		            resizable : true,
		            autoScroll : true,
		            autoWidth : true,
		            autoHeight:true,
		            height:550,	
					layout : 'form',
					frame : true,
					items : [c_form]
				});
				
				addWin.show();
			},
//-----------------------------------------------------------------表单查询
		MessageQuery: function(){
			var _this = this;
			
			var gridStore = Ext.getCmp('grid').store;
			//gridStore.removeAll();
			
			var paramJson = {};
			
	    	paramJson.cityid = Ext.getCmp('city').getValue();
	    	paramJson.actid = Ext.getCmp('act').getValue();
	    	
	    	gridStore.baseParams = paramJson;
	    	gridStore.load({
	    		params : {
					start : 0,
					limit : 10
				},
				callback: function(obj){
					if(obj.length == 0) { 
					}
				}
			});
		},//活动查询 为了获得活动ID
		queryForButtom : function(){
			var gridStore = Ext.getCmp('bottomGrid').store;
			var paramJson = {};
	    	paramJson.cityid = Ext.getCmp('bottominnercity').getValue();//城市查询 
//	    	paramJson.industyid=Ext.getCmp('industy').getValue();
	    	paramJson.activityid = Ext.getCmp('saveact').getValue();//活动性质查询
	    	paramJson.title = Ext.getCmp('bottomTitle').getValue();//标题查询
	    	
	    	gridStore.baseParams = paramJson;
	    	gridStore.load({
	    		params : {
					start : 0,
					limit : 5
				},
				callback: function(obj){
					if(obj.length == 0) {
					}
				}
			});
	
			
			
			
			
		},//保存方法
		saveall:function(id,CONTENTID,CONTENTNAME){
			var  _this=this;
			//对选择城市 和 活动内别进行验证  存在的话 返回
			if(!tip){//如果是保存的的话 ,则进入到 这一步,如果是 修改的话，直接进入下一步
				if(Ext.getCmp('savecity').getValue().length!=0&&Ext.getCmp('bottomcontent').getValue().length!=0){
			Ext.Ajax.request({
				url : rootPath+"basedata/cityandActTypeAction!isExist.action", 
				timeout : 120000,
				success: function(result, obj){
				var jsonFromServer = Ext.util.JSON.decode(result.responseText);
                if (jsonFromServer.status=='N') {
                	alert('对不起,您输入的城市和活动专题已存在！');
                	return;
                }else{
                	_this.isExistSave(id,CONTENTID);
                }
				},
				failure: function(result, obj){
					alert('保存失败'); 
				},
				params: { savecityid:Ext.getCmp('savecity').getValue(), contentid:CONTENTNAME}
			});
				}else{alert('请选择城市和输入活动专题！')}
			}else{
				_this.isExistSave(id,CONTENTID);
			}
			
			Ext.getCmp("act").store.reload();
		},
		//创建第一个Grid
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
               
			    {name : 'C_ID'},
               	{name : 'MID'},
               	{name:'C_CITY'},
                {name : 'C_MENU'},
               	{name : 'C_CONTENTID'} 
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/cityandActTypeAction!getFirstGrid.action",
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
               { header: "CID", hidden: true, dataIndex: 'CID',width:15},
               { header: "MID", hidden: true, dataIndex: 'MID',width:15},
               { header: "城市", sortable: true, dataIndex: 'C_CITY',width:20},
               { header: "活动专题", sortable: true, dataIndex: 'C_MENU',width:20
               },
               
               { header: "操作", sortable: false, dataIndex: '',width:20,align:'center', 
        	     renderer: function (value, meta, record) {
   				     var formatStr = "<a style='cursor: hand' href='javascript:void({0});' onclick='javscript:return false;' class='modify'>编辑</a>";   
   				     formatStr += "&nbsp;&nbsp;&nbsp;<a style='cursor: hand' href='javascript:void({0});' onclick='javscript:return false;' class='delete'>删除</a>";
   				     var resultStr = String.format(formatStr, '001', '001', '001');   
   				     return "<div class='controlBtn'>" + resultStr + "</div>";
       			}.createDelegate(this),   
       			css: "text-align:center;"
   				}
               ];
    		
    		var resultColumnModel = new Ext.grid.ColumnModel(tempArray);
    		//中间的Gird
    		var grid = new Ext.grid.EditorGridPanel({
    			id:"grid",
    			store : resultsGrid_store,
    			scrollable : true,
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
						
						var loadMask = new Ext.LoadMask(document.body, {msg : '正在删除,请稍等...', removeMask : true});
						loadMask.show();
						Ext.Ajax.request({
							url : rootPath+"basedata/cityandActTypeAction!deleteById.action",
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
				            params: {id:record.get('C_ID')}
				        });
    	                return true;
    	             }
    			}else if(record&&control=='modify'){
    				tip=true;
    				
    				_this.priCreateWinFun(record.get("C_CITY"),record.get("C_MENU"),record.get('C_ID'),record.get("C_CONTENTID"));
    				
    				
    			}
    		  }   
    		},   
    		this); 
    		 
    		return grid;
		},
		
		createTopGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
			                                            {name : 'id'},
			                                            {name : 'address'},
			                                            {name:'activity'},
			                                            {name : 'title'},
//			                                            {name:'industy'},
			                                            {name : 'industy'},{
			                                            name:'date'	
			                                            }
			                                            ]);
			// 列表用到的数据源
			
		
			var innerResultsGrid_store = new Ext.data.Store({ 
				proxy : new Ext.data.HttpProxy({
					url : rootPath+"basedata/cityandActTypeAction!getInnerSummaryMessage.action", 
					timeout : 60000
				}),
				reader : new Ext.data.JsonReader({
					totalProperty : 'count',
					root : 'list'
				}, resultsRecord),
				baseParams : {
				start:0,limit:5 
			}
			}); 
			
			//
			//var resultsSm = new Ext.grid.CellSelectionModel(); //.CheckboxSelectionModel();    //city  activity title industy Url
			var resultsSm = new Ext.grid.CheckboxSelectionModel({singleSelect : false});//复选框 选择 
			var tempArray = [resultsSm,
			                 { header: "ID", hidden: true, dataIndex: 'id'},
			                 {header: "行业类别", sortable: true, dataIndex: 'industy',width:10},
			                 { header: "活动内容类别", sortable: true, dataIndex: 'activity',width:15},
			                 { header: "标题", sortable: true, dataIndex: 'title',width:45},
			                 { header: "活动地址", sortable: true, dataIndex: 'address',width:20},
			                 {header: "时间", sortable: true, dataIndex: 'date',width:20}
			                 ];
			var resultColumnModel = new Ext.grid.ColumnModel(tempArray);
			//中间的Gird
			innerResultsGrid_store.reload();
			var bottomGrid = new Ext.grid.EditorGridPanel({
				id:"bottomGrid",
				sm:resultsSm,
				store : innerResultsGrid_store,
				scrollable : true,
				clicksToEdit : 1,
				bodyStyle : 'border:1px solid #99bbe8;',
//				width:'400',
				//sm : resultsSm,
				cm : resultColumnModel,
				viewConfig : {
				forceFit : true,
				autoWrapRow:true
			},
			width : 815,
			height :170,
			loadMask : { msg : LOADMASK_MSG },
			bbar : new Ext.PagingToolbar({//分页工具条
				pageSize : 5,
				store : innerResultsGrid_store
			})
			}); 
			return bottomGrid;
		},
		createBottomGrid : function(id){//如果是修改状态的话  则 去加载数据，如果不是的话 则不加载数据
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
			                                            {name : 'id'},
			                                            {name : 'address'},
			                                            {name:'activity'},
			                                            {name : 'title'},
//			                                            {name:'industy'},
			                                            {name : 'industy'},{
			                                            	name:'date'	
			                                            }
			                                            ]);
			// 列表用到的数据源
			
			
			var innerResultsGrid_store; 
			if(tip){//如果是修改的话 
				innerResultsGrid_store= new Ext.data.Store({ 
					proxy : new Ext.data.HttpProxy({
						url : rootPath+"basedata/cityandActTypeAction!getUpdateInnerSummaryMessage.action", 
						timeout : 60000
					}),
					reader : new Ext.data.JsonReader({
						totalProperty : 'count',
						root : 'list'
					}, resultsRecord),
					baseParams : {
					start:0,limit:100 ,id:id
				}
				}); 
				
				
				
				
			}else{
			innerResultsGrid_store= new Ext.data.Store({ 
				proxy : new Ext.data.HttpProxy({
//					url : rootPath+"basedata/cityandActTypeAction!getInnerSummaryMessage.action", 
					timeout : 60000
				}),
				reader : new Ext.data.JsonReader({
					totalProperty : 'count',
					root : 'list'
				}, resultsRecord),
				baseParams : {
				start:0,limit:5 
			}
			}); 
			}
			//
			//var resultsSm = new Ext.grid.CellSelectionModel(); //.CheckboxSelectionModel();    //city  activity title industy Url
			var resultsSm = new Ext.grid.CheckboxSelectionModel({singleSelect : false});//复选框 选择 
			var tempArray = [resultsSm,
                 { header: "ID", hidden: true, dataIndex: 'id'},
                 { header: "序号", sortable: true, dataIndex: 'number',width:5,
                	 editor: new Ext.form.NumberField({
                           allowBlank: false,
                		   allowNegative: false,
                		   maxValue: 100000
                	})
                 },
                 {header: "行业类别", sortable: true, dataIndex: 'industy',width:10},
                 { header: "活动内容类别", sortable: true, dataIndex: 'activity',width:15},
                 { header: "标题", sortable: true, dataIndex: 'title',width:40},
                 { header: "活动地址", sortable: true, dataIndex: 'address',width:20},
                 {header: "时间", sortable: true, dataIndex: 'date',width:20}
                 ];
			var resultColumnModel = new Ext.grid.ColumnModel(tempArray);
			//中间的Gird
//			innerResultsGrid_store.reload();
			var bottomGrid = new Ext.grid.EditorGridPanel({
				id:"bottom2Grid",
				sm:resultsSm,
				store : innerResultsGrid_store,
				scrollable : true,
				clicksToEdit : 1,
				bodyStyle : 'border:1px solid #99bbe8;',
//				width:'400',
				//sm : resultsSm,
				cm : resultColumnModel,
				viewConfig : {
				forceFit : true,
				autoWrapRow:true
			},
			width : 800,
			height : 225,
			loadMask : { msg : LOADMASK_MSG },
			bbar : new Ext.PagingToolbar({//分页工具条
				pageSize : 5,
				store : innerResultsGrid_store
			})
			}); 
			if(tip){
				Ext.getCmp('bottom2Grid').store.reload();
			}
			
			return bottomGrid;
		},
		isExistSave : function(id,CONTENTID){
			var  _this=this;
			var rows=Ext.getCmp('bottom2Grid').getSelectionModel().getSelections();
			var data=[];
			for(var i=0;i <rows.length;i++){
				var item ={id:rows[i].get('id'),number:rows[i].get('number')};
				data.push(item);
			}
			//修改活动专题
			var saveParam = {};
			saveParam.c_id=CONTENTID;
			saveParam.c_name = Ext.getCmp('bottomcontent').getValue();
			saveParam.c_istitle = '1'; 
		    saveParam.c_order = '8';
			saveParam.c_islive ='1';
			
			var params = {};
			params.saveParam = Ext.encode(saveParam);
			
			
			if(tip){//如果是修改的话 则进入到这一步
				Ext.Ajax.request({
					url : rootPath+"basedata/cityandActTypeAction!updateSaveAll.action", 
					timeout : 120000,
					success: function(result, obj){
						tip=false;
						Ext.Ajax.request({
							url : rootPath+"basedata/actionActTheme!saveOrUpdate.action",
							success: function(result, obj){
								alert('修改成功');
								Ext.getCmp('grid').store.reload();
								Ext.getCmp(_this.addWinId).hide(); 
						
							},
							failure : function (result, obj){
								alert('保存失败');
								
							},
							params : params
						});
					},
					failure: function(result, obj){
						alert('修改失败');
						tip=false;
					},
					params: { id:CONTENTID,data: Ext.encode(data),ids:ids }//先根據DI 改变最终表含有这个ID 的状态 让它失效  然后再进行添加
				});	
				
				
				
				
			}else{  //--在这个地方返回一个专题字典表的ID  然后作为一个参数进行保存就OK了
				var  _this=this;
				Ext.Ajax.request({
					url : rootPath+"basedata/actionActTheme!saveOrUpdate.action",
					success: function(result, obj){
						var rsc = Ext.util.JSON.decode(result.responseText);
						
						//将返回的活动专题ID 进行最终保存
						Ext.Ajax.request({
						url : rootPath+"basedata/cityandActTypeAction!saveAll.action", 
						timeout : 120000,
						success: function(result, obj){
							var jsonFromServer = Ext.util.JSON.decode(result.responseText);
							var  idss=jsonFromServer.idss;
							ids=idss;
							alert('保存成功!');
							Ext.getCmp('grid').store.reload();
							Ext.getCmp(_this.addWinId).hide();
							
				
						},
						failure: function(result, obj){
							alert('保存失败');
						},
						params: { savecityid:Ext.getCmp('savecity').getValue(), contentid:rsc.c_id,data: Ext.encode(data) }
					});
						
						
						
					},
					failure : function (result, obj){
						alert('保存失败');
					},
					params : params
				});
			}
			
			
			
		}
	}
};
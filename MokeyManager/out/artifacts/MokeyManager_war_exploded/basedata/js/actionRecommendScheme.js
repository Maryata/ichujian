Ext.actionRecommendScheme = function() {
	var schid;
	return {
		
//		//定义下拉框
//		url : {
//			allBrand : rootPath+"basedata/activitySchemeDetail!findBrandAllName.action",
//			allScheme : rootPath+"basedata/activitySchemeDetail!findSchemeAllName.action"
//		},
		priData : {
//			categoryData : [{key:'1',value:'是'},{key:'0',value:'否'}],
			typeData:[{key:'1',value:'热门'},{key:'2',value:'推荐'}]	
		},
		formId : 'actionRecommendScheme_formId',
		gridId : 'actionRecommendScheme_gridId',
		addWinId : 'actionRecommendScheme_addWinId',
		textwidth : 120,
		init : function(){
			
		},


		load : function(){  
			//alert(11)
			var _this = this;
			
			//_this.createForm();
			//处理功能选择下拉框
			var type = _this.createComVarField({fieldLabel:"功能选择",id: _this.addWinId + 'c_type',name:'c_type' , data:_this.priData.typeData , width: 140});
			
			//方案名
			 var nameField = new Ext.form.TextField({
				id: _this.formId+'c_name',
				xtype : 'textfield',
				fieldLabel : '方案名称',
				name : 'c_name',
				width : _this.textwidth,
				readOnly : false,
				maxLength : 50,
				//allowBlank : 'true',
				listeners : {
					focus : function() {
					}
				}
			});
			 
			//查询所有的城市
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
 
			suppStore.load(

					{
						callback : function(obj) {
						suppStore.insert(0, new suppStore.recordType( {
							C_ID : '', 
							C_NAME : '全部'
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
							columnWidth : .2,
							layout : 'form',
							items:[nameField]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [supp_Comb]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [type]
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
									schid = "";
									_this.priCreateWinFun();
									Ext.getCmp(_this.addWinId + 'schemeName').cid = "";
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
		
		priQueryFun : function(){
			var _this = this;
			
			var gridStore = Ext.getCmp(_this.gridId).store;
			//gridStore.removeAll();
			
			var paramJson = {};
			paramJson.c_name = Ext.getCmp(_this.formId+'c_name').getValue();
			paramJson.c_cityid = Ext.getCmp(_this.formId +'c_cityid').getValue();
			paramJson.c_type = Ext.getCmp(_this.addWinId + 'c_type').getValue();
	    	//paramJson.c_schemeid = Ext.getCmp(_this.formId +'c_schemeid').getValue();
	    	
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
			var params = {meunid : 0};
			//判断是否有广告位
			if(Ext.getCmp("radioid").getValue()==1){
				params.meunid = Ext.getCmp(_this.addWinId + 'c_meunid').getValue();
			}
			params.schemeId = schid;
			params.schemename = Ext.getCmp(_this.addWinId + 'schemeName').getValue();
			params.cityid = Ext.getCmp(_this.addWinId + 'c_cityid').getValue();
			params.type = Ext.getCmp(_this.addWinId + 'c_type2').getValue();
			
			//复选框中的data
			var rows=Ext.getCmp('gridId3').getSelectionModel().getSelections();
			var data=[];
			for(var i=0;i <rows.length;i++){
				var item ={cid: rows[i].get('C_ID'),order: rows[i].get('C_ORDER')};//number:rows[i].get('C_NAME')
				//
//				if(!Ext.isEmpty(rows[i].get('C_ID'))){
//					item.cid = rows[i].get('C_ID');
//				}
				item.c_advertid = params.meunid;//
				data.push(item);
			}
			//城市id，功能选择id，方案id 
			params.checkMsg = Ext.encode(data);
			
			Ext.Ajax.request({
				url : rootPath+"basedata/activityRecommendScheme!saveActivityRecommendScheme.action", 
				timeout : 120000,
				success: function(result, obj){
					var res = Ext.decode(result.responseText);
					if(res.success){
						alert('指定成功!');
						schid = res.schemeId;
						_this.loadGridDetail(schid);
					}else{
						alert('指定失败:'+res.msg);
					}
				},
				failure: function(result, obj){
					alert('指定失败');
				},
				params: params
			})
		// 	alert(Ext.encode(data));
//			var saveParam = {};
//			if(dirfrom.Ext_cid ){
//				saveParam.c_id = dirfrom.Ext_cid;
//			}
//			saveParam.c_brandid = dirfrom.findField('c_brandid').getValue();
//			saveParam.c_schemeid = dirfrom.findField('c_schemeid').getValue();
//		    saveParam.c_order = dirfrom.findField('c_order').getValue();
//			var params = {};
//			params.saveParam = Ext.encode(saveParam);
			
//			dirfrom.submit({
//				method : 'post',
//				url : rootPath+"basedata/activitySchemeDetail!saveActivitySchemeDetail.action",
//				waitMsg : 'save ing...',
//				success : function(form, action) {
//					var rsc = Ext.decode(action.response.responseText);
//					alert('保存成功');
//					dirfrom.Ext_cid = rsc.c_id;
//					
//				},
//				failure : function(form, action) {
//					var rsc = Ext.decode(action.response.responseText);
//					alert('保存失败');
//				},
//				params : params
//			});
			
		},
		
		
		//城市下拉框
		createCityStore :function(){
			var _this=this;
			var suppStore2 = new Ext.data.Store( {
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
			suppStore2.load( {
				callback : function(obj) {
					suppStore2.insert(0, new suppStore2.recordType( {
						C_ID : '0',
						C_CNAME : '默认城市'
					}));
				}
			})
			return suppStore2;
		},
		//广告位下拉框
		createAdvertiseStore:function(){
			var _this=this;
			var suppStore = new Ext.data.Store( {
				// autoLoad: true,
				proxy : new Ext.data.HttpProxy( {
					url : rootPath + "basedata/activityRecommendScheme!findAllAdvertise.action",
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
			suppStore.load();
			return suppStore;
		},


		priCreateWinFun : function(id){
			var _this = this;
			
			var addWin = Ext.getCmp(_this.addWinId);
			if(addWin){
				addWin.show();
				return;
			}
			
			//城市下拉框的创建
	        var cityField= new Ext.form.ComboBox({			
					xtype : 'combo',
					fieldLabel : '城市',
					store:_this.createCityStore(),
					id : _this.addWinId + 'c_cityid',
					name : 'c_cityid',
					displayField : 'C_CNAME',
					valueField : 'C_ID',
					triggerAction: 'all',
					mode : 'local',//本地数据
					width:130,
					allowBlank : false,
					typeAhead : true,
					loadStatus : false,
					resizable : true
             });
			
			//查询推荐内容的集合    
			_this.privateGetcontentType = function(pid){
				Ext.Ajax.request({
		            url : rootPath + "basedata/activityRecommendScheme!findContentId.action",
		            timeout : 120000,
		            success: function(result, obj){
		                var jsonFromServer = Ext.util.JSON.decode(result.responseText);
		                //alert(jsonFromServer.list);
		             	_this.PushCheckIiems(jsonFromServer.list);   		    
	                },
		            failure: function(result, obj){
		            } ,
		           	params: {cityid : pid}
		        });
			};
			//动态创建复选框
			_this.PushCheckIiems = function (data){
	            	 var itemsGroup = Ext.getCmp(_this.addWinId + 'recommend');
	                 if(itemsGroup){
	                 		itemsGroup.destroy();
	                 }
	                 if(data.length!=0){
		            	var items = Manager.sys.From.createGroupItem('recommend',data,{labelFiled:'C_NAME',valueFiled:'C_ID',checked:true} );
		            	
						itemsGroup = new Ext.form.CheckboxGroup({
			    			xtype: 'checkboxgroup',
						    fieldLabel: '专题',
						    id : _this.addWinId + 'recommend',
							name : 'recommend',
						    itemCls: 'x-check-group-alt',
						    vertical: false,
						    columns: 5,
			        	    items:items
			        	});
						    
			        	var recommend_items = Ext.getCmp(_this.addWinId + 'recommend_items');
			        	recommend_items.setVisible(true);  
			        	recommend_items.add(itemsGroup);
			        	recommend_items.doLayout();
		        	}
	        	
            };
			
             cityField.on('change',function(combo,newValue ,oldValue ){
				//重新加载city
            	 _this.privateGetcontentType(newValue);
            	 //
            	 var menuObj = Ext.getCmp(_this.addWinId + 'c_meunid');
            	 menuObj.store.reload();
            	 //触发select	
				 var index = menuObj.store.find('C_ID',menuObj.getValue());
                 menuObj.fireEvent('select', menuObj, menuObj.store.getAt(index), index);
            	 //clear;
            	 Ext.getCmp('gridId3').store.removeAll();
			});
			
			//创建的单选按钮
			var radiogroup = new Ext.form.RadioGroup({
	        	 fieldLabel:'是否有广告',
				 id:'radioid',
	             columns: 2,
	             items: [{
	                 name: 'schemeType',
	                 id:'check1',
	                 inputValue: '0',
	                 boxLabel: '无',
	                 width:120,
	                 checked: true,
	                 handler:function(){
	            	 	Ext.getCmp("sb").setVisible(true);
	                 }
	             },{
	                 name: 'schemeType',
	                 id:'check2',
	                 inputValue: '1',
	                 width:20,
	                 boxLabel: '有',
	                 handler:function(){
	            	 	Ext.getCmp("sb").setVisible(false);
	                 }
	                	 
	             }]
	         });


			var record;
			//创建下面的grid
			_this.createGrid=function(){
        		var resultsRecord1 =  Ext.data.Record.create([
	               	{name : 'C_ID'},
	               	{name :'C_NAME'},//类别名称
	               	{name:'T_ID'}, //类别id
	               	{name:'C_ORDER'}
	               	
	    		 ]);
            	 resultsGrid2_store = new Ext.data.GroupingStore({
            		 reader : new Ext.data.JsonReader({
	    				root : 'list'
	    			}, resultsRecord1)
		    	});
            	 
//            	 var resultsGrid2_store1111 = new Ext.data.GroupingStore({ 
//	    			proxy : new Ext.data.HttpProxy({
//	    				url : rootPath+"basedata/activityFollowScheme!findIndustryBrand.action",
//	    				timeout : 60000
//	    			}),
//	    			reader : new Ext.data.JsonReader({
//	    				root : 'list'
//	    			}, resultsRecord1),
//	    			//sortInfo: {field:"C_ID", direction:"ASC"}, //数据排序  
//			  	 	groupField: "P_NAME" //分组字段  
//	    		});
				var resultsSm = new Ext.grid.CheckboxSelectionModel({singleSelect : false});
            	var tempArray = [resultsSm,
            		   { header: "主键id", hidden: true, dataIndex: 'C_ID',width:15},
		               { header: "类别id", hidden: true, dataIndex: 'T_ID',width:15},
		               { header: "名称", sortable: true, dataIndex: 'C_NAME',width:45
		               },
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
	    			sm : resultsSm,
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
	    			height : 200,
	    			loadMask : { msg : LOADMASK_MSG }
          	  });
        		
	        	grid2.on('cellclick', function (grid, rowIndex, columnIndex, e) {
	    			record = grid.getStore().getAt(rowIndex);			
				});
				
				resultsGrid2_store.addListener('load',function(){
					setTimeout(function(){
						//alert('xuan--')
				   var records=[];//存放选中记录
				   for(var i=0;i<resultsGrid2_store.getCount();i++){
				    var record = resultsGrid2_store.getAt(i);
				     records.push(record);
				   }
				   resultsSm.selectRecords(records);//执行选中记录
				   //alert('xuan--22')
					},100);
				 });
        		return grid2;
        	};
			var type2 = _this.createComVarField({fieldLabel:"功能选择",id: _this.addWinId + 'c_type2',name:'c_type2' , data:_this.priData.typeData , width: 100,allowBlank : true});
			var num=0;
			var c_form = new Ext.form.FormPanel({
				id : _this.addWinId + 'addForm',
//				frame : true,
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
				//labelWidth : 70,
				fileUpload : true,
				height:500,				
				items:[{
					frame : true,
					layout:'column',
					labelWidth : 70,
					items:[{
						height:20,
						columnWidth:.25,
						layout:'form',
						items:[radiogroup]
					},{
//						title:'c',
						layout:'form',
						columnWidth:.25,
						labelWidth : 30,
						items:[cityField]
							//{
//							xtype : 'combo',
//							fieldLabel : '城市',
//							store:_this.createCityStore(),
//							id : _this.addWinId + 'c_cityid',
//							name : 'c_cityid',
//							displayField : 'C_CNAME',
//							valueField : 'C_ID',
//							triggerAction: 'all',
//							allowBlank : false,
//							width:100,
//							listeners:{'select':function(combo,newValue ,oldValue ){
//									_this.privateGetCity(newValue);
//								}
//							}
//						}
					},{
						columnWidth:.25,
						labelWidth : 60,
						layout : 'form',
						items : [type2]
					},{
//						title:'d',
						labelWidth : 60,
						columnWidth:.25,
						layout:'form',
						items:[{
								xtype:'textfield',
								id: _this.addWinId + 'schemeName',
								fieldLabel:'方案名称',
								allowBlank : false,
								width:180
						}]
					}]
					
				},{
//					title:'ccc',
					frame : true,
					id:'sb',
					hidden:true,
					items:[{
							labelWidth :70,
							layout:'form',
							items:[{
								xtype : 'combo',
								fieldLabel : '菜单项选择',
								store:_this.createAdvertiseStore(),
								id : _this.addWinId + 'c_meunid',
								name : 'c_meunid',
								displayField : 'C_NAME',
								valueField : 'C_ID',
								triggerAction: 'all',
								mode : 'local',//本地数据
								width:160,
								listeners:{'select':function(){
									//alert('广告位选择--select');
									//异步加载对应的图片
							  	 	Ext.Ajax.request({
							            url : rootPath + "basedata/activityRecommendScheme!findAdvertiseimages.action",
							            timeout : 120000,
							            success: function(result, obj){
							                var jsonFromServer = Ext.util.JSON.decode(result.responseText);
							                var img1 = "",img2 = "";
											if(jsonFromServer.result && !Ext.isEmpty(jsonFromServer.list)){
												var imgs = jsonFromServer.list[0].C_PICURL;//.split(',')
												if(!Ext.isEmpty(imgs)){
													var imgArr = imgs.split(',');
													img1 = imgArr[0],img2 = imgArr[2];
												}
											}
											Ext.get('imageBrowse1').dom.src=img1;
							                Ext.get('imageBrowse2').dom.src=img2;
						                },
							            failure: function(result, obj){
							            }, 
						                params: { c_meunid:Ext.getCmp(_this.addWinId + 'c_meunid').getValue(), 
							            		  c_cityid:Ext.getCmp(_this.addWinId + 'c_cityid').getValue()								            		  
							            }
							           
							        });
									}
								}
							}]
					},{
							layout:'column',
							items:[{
	//							title:'fsdfsad',
								columnWidth:.5,
	 							items : [ new Ext.BoxComponent({
								frame:true,
						           id : 'browseImage1', 
						           autoEl : {  
						               width : 100,   
						               height :100,  
						               tag : 'img',  
						                // type : 'image',  
	//					               src : rootPath+'image/add.jpg',   //Ext.BLANK_IMAGE_URL
						               style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',     
						               complete : 'off',     
						               id : 'imageBrowse1'  
						           }   
							})]	
						},{
//							title:'46456465',
							columnWidth:.5,
							items : [ new Ext.BoxComponent({
								frame:true,
					           id : 'browseImage1', 
					           
					           autoEl : {  
					               width : 100,   
					               height :100,
//					               align:'center',
					               tag : 'img', 
					         
					                // type : 'image',  
//					               src : rootPath+'image/add.jpg',   //Ext.BLANK_IMAGE_URL
					               style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',     
					               complete : 'off',     
					               id : 'imageBrowse2'  
					           }   
						})]
						}]
						
					}]

				},
				//***************
				{
						layout:'form',
						hidden:true,
						items:[],
						height:120
						
					},{
//						layout:'form',
						bodyStyle : 'padding:1px;border-bottom:1px solid #99bbe8;',
						//id : _this.addWinId + 'city_items_clem',
						items : [{
							layout:'form',
							id : _this.addWinId + 'recommend_items',
							hidden:true,
							items : []
						}]
						
					}
				
				
				
			
//				{
//
//					layout:'form',
//					id :_this.addWinId + 'recommend_items',
//					items : []//itemsGroup
//				}
				,{
//					title:'图标',
					frame : true,
					layout:'column',
					items:[{
						columnWidth:.4,
						//title:'dsasdas',
						height:20
					},{
						//title:'dsfasdfsad',
						columnWidth:.2,
						layout:'column',
						items:[{
//							title:'aaa',
							xtype : 'button',
							text : '↓',
							columnWidth:.3,
							handler : function() {
//								_this.createRemoveDuplicate();
								var record = Ext.data.Record.create([
					               	{name : 'C_ID'},
					               	{name :'C_NAME'}
					    		 ]);
																
								var store = Ext.getCmp('gridId3').store;
								var recommendMsg= Ext.getCmp(_this.addWinId + 'recommend').getValueNames();
//								var recommendMsg= Ext.getCmp(_this.addWinId + 'recommend').getValueNames();
								for(var i=0;i<recommendMsg.length; i++){
									var dTemp = recommendMsg[i];
									var isHas = false;
									for(var j=0; j<store.getCount();j++){
										if(dTemp.value== store.getAt(j).get("C_ID")){
											isHas = true;
											break;
										}
									}
									if(!isHas){
										var rec = new record({
											C_ID : dTemp.value,
											C_NAME : dTemp.label
										});
										store.add(rec);
									}
								}
								 var records=[];//存放选中记录
								 for(var i=0;i<store.getCount();i++){
								    var record = store.getAt(i);
								    records.push(record);
								 }
								Ext.getCmp('gridId3').getSelectionModel().selectRecords(records);
				    	    }
						},{
							height:20,
							columnWidth:.4
						},{
//							title:'ccc',
							xtype : 'button',
							text : '↑',
							columnWidth:.3,
							handler:function(){
								var selects =Ext.getCmp('gridId3').getSelectionModel().getSelections();
								for(var i=0;i<selects.length;i++){
									Ext.getCmp('gridId3').store.remove(selects[i]);
								}
							}
						}]
					},{
						//title:'sdfasdfasd',
						height:20,
						columnWidth:.4	
					}]
				},{
					//title:'多选框',
					frame : true,
					layout:'column',
					height:200,
					items:[{
						//title:'1',
						columnWidth:.25,
						height:100
					},{
						columnWidth:.5,
						height:200,
						items:[_this.createGrid()]
					},{
						//title:'4',
						columnWidth:.25,
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
		        		//Ext.getCmp("sb").setVisible(false);
		        
		            	_this.priSaveFun();
		            	//alert(Ext.getCmp("checkid").getValue());
//		            	var leftSelects =Ext.getCmp('gridId3').getSelectionModel().getSelections();

						
		           
		            }
		        } ]
			});
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '推荐、热门方案',
				closable:false,
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 800, 
	            height:550,	
				layout : 'form',
				frame : true,
				items : [c_form]
			});
			
//			_this.privateGetcontentType();
			
			addWin.show();
		},
		
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
               	{name : 'C_ID'},
               	{name : 'P_NAME'},
                {name : 'C_NAME'},
                {name : 'C_CITYID'},
                {name:'C_TYPE'},
                {name:'C_DATE'},
                {name : 'C_ORDER'}
                
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/activityRecommendScheme!getActivityRecommendScheme.action",
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
            		renderer:function(value,meta,record){
            			if(!Ext.isEmpty(record.get('P_NAME'))){
            				return record.get('P_NAME');
            			}
            			return value=0?'':value;
            		}	   
               },
               { header: "类型", sortable: true, dataIndex: 'C_TYPE',width:40,
            	   renderer: function (value, meta, record) {
            	   		return value==1?'热门':'推荐';
            	   }
               },
               //{ header: "是否有效", sortable: true, dataIndex: 'C_ORDER',width:40},
               { header: "时间", sortable: true, dataIndex: 'C_DATE',width:40},
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
//				            url : rootPath+"basedata/activitySchemeDetail!deleteActivitySchemeDetail.action",
							url : rootPath+"basedata/activityRecommendScheme!deleteActivityRecommendScheme.action",
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
    				var data=record.get("C_NAME");
    				schid = record.get('C_ID');
    				_this.loadGridDetail(schid,record);
   				}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		},
		loadGridDetail : function(cid ,record){
			var _this = this;
			//异步请求获取是否有广告位
			Ext.Ajax.request({
				url : rootPath+"basedata/activityRecommendScheme!findById.action",
	            timeout : 120000,
	            success: function(result, obj){
	               var jsonFromServer = Ext.util.JSON.decode(result.responseText);
	              	
	                _this.priCreateWinFun(jsonFromServer.advertid);
	                //Ext.getCmp(_this.addWinId + 'schemeName').cid = cid;
	                if(record){
	                	Ext.getCmp(_this.addWinId + 'schemeName').setValue(record.get("C_NAME"));
	    				Ext.getCmp(_this.addWinId + 'c_type2').setValue(record.get("C_TYPE"));
	    				Ext.getCmp( _this.addWinId + 'c_cityid').setValue(record.get("C_CITYID"));
	    				Ext.getCmp( _this.addWinId + 'c_cityid').setRawValue(record.get("P_NAME"));
	    				
	    				_this.privateGetcontentType(record.get("C_CITYID"));//
	    				
	    				var rValue = 0;
		              	if(jsonFromServer.advertid!=0){
		              		rValue = 1;
		              	}	         	
		              	Ext.getCmp("radioid").setValue(rValue);
		              	Ext.getCmp("sb").setVisible(rValue==1); 
		              	
		              	//触发select	
		              	var menuObj = Ext.getCmp(_this.addWinId + 'c_meunid');
		              	menuObj.setValue(jsonFromServer.advertid);
		                menuObj.setRawValue(jsonFromServer.t_name);
	    				
	    				var index = menuObj.store.find('C_ID', jsonFromServer.advertid);
                        menuObj.fireEvent('select', menuObj, menuObj.store.getAt(index), index);
	    				
	                }
    				
	                var detailGrid = Ext.getCmp('gridId3');
	                detailGrid.store.loadData({list:jsonFromServer.list });

                },
	            failure: function(result, obj){
	            	alert('加载失败!.');
	            }, 
	            params: {C_ID:cid} //record.get('C_ID')
			});	
		}
	}
};
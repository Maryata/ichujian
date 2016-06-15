Ext.activityFollowScheme = function() {
	var   tip=false;//判断是否上传了图片
	var   tip2=false;//判断 是否选择了 活动单选框
	var   tip3=false;
	var   update=false;
	var   mNumber=1;
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
		//--------------------------------------城市的下拉框----------------------------------------	
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
		//---------------------------------菜单项的下拉框----------------------------	 
			var menuStore = new Ext.data.Store( {
				// autoLoad: true,
				proxy : new Ext.data.HttpProxy( {
					url : rootPath + "basedata/fourKeyMainTainAd!getMenus.action",
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

			menuStore.load( {
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
				fieldLabel : '菜单项',
				id :'menu',
				name : 'menu',
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
								update=false;
								_this.createInnerFormPanel();
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
			
			Ext.getCmp('grid').store.load({params:{start:0,limit:6}});
		},
		
//-----------------------------------------------------------------表单查询
		MessageQuery: function(){
			var _this = this;
			
			var gridStore = Ext.getCmp('grid').store;
			//gridStore.removeAll();
			
			var paramJson = {};
			
	    	paramJson.cityid = Ext.getCmp('city').getValue();
	    	paramJson.menuid = Ext.getCmp('menu').getValue();
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
		//-----------------------------创建内部的FormPanel-------------------------------
		createInnerFormPanel:function(id,cityid,menuid,city,menu,C_ADVERTID){
			var _this=this;
			
			var	suppStore ;
			if(!update){
			suppStore= new Ext.data.Store( {
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
			}
			
			);
			}else{
				suppStore= new Ext.data.Store( {
					// autoLoad: true,
					proxy : new Ext.data.HttpProxy( {
						url : rootPath + "basedata/fourKeyMainTainAd!getUpdateCities.action",
						timeout : 60000
					}),
					params:{id:id},
					reader : new Ext.data.JsonReader( {
						root : 'list'
					}, Ext.data.Record.create( [ {
						name : 'C_ID'
					}, {
						name : 'C_CNAME'
					} ]))
				});
				
				
				suppStore.load({params: { id: cityid}});
			}
			
		

			var supp_Comb = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				store : suppStore,
				fieldLabel : '城市',
				triggerAction : 'all',
				id :'innerCity',
				name : 'innerCity',
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
					   afterrender:function(supp_Comb){  
					   supp_Comb.setValue(city);  
				   }  
			}
			});
			
			var	menuStore
			if(!update){
			menuStore=new Ext.data.Store( {
					// autoLoad: true,
					proxy : new Ext.data.HttpProxy( {
						url : rootPath + "basedata/fourKeyMainTainAd!getMenus.action",
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
			menuStore.load();
			}else{
					menuStore=new Ext.data.Store( {
						// autoLoad: true,
						proxy : new Ext.data.HttpProxy( {
							url : rootPath + "basedata/fourKeyMainTainAd!getUpdateMenus.action",
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
				
					
					 
					
				}
			menuStore.load({params: { id: menuid}}); 
			var menu_Comb = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : menuStore,
				fieldLabel : '菜单项',
				id :'innerMenu',
				name : 'menu',
				allowBlank : false,
				editable : false,
				mode : 'local',
				displayField : 'C_NAME',
				valueField : 'C_ID',
				resizable : true,
				editable : false,
				width : _this.textwidth,
				selectOnFocus : true,
				listeners:{  
					   afterrender:function(menu_Comb){  
				      menu_Comb.setValue(menu);  
				   }  
			}  
			});
			
			var pnlMain = new Ext.form.FormPanel( {
				id : _this.formId + 'formPanel111',
				//bodyStyle : 'padding:5 5 5 5;border:0px',
				width : 825,
				height : 25,
				bodyBorder: false,	
				border :false,
				//frame false,
				labelWidth : 80,
				align:'center',
				items : [{
					layout : 'column',
					border : false,
					items : [ {
						columnWidth : .50,
						layout : 'form',
						items : [ supp_Comb ]   
					}, {  
						columnWidth : .30,
						layout : 'form',
						items : [ menu_Comb ]
					}, {
						columnWidth : .20,
						border : false,
						layout : 'column',
						items : [ {
							columnWidth : .4,
							border : false,
							layout : 'form',
							items : [ {
								xtype : 'button',
								text : '保存',
								width : 50,
								handler : function() {
									_this.saveAll(C_ADVERTID);
								
								}
							} ]
						} ]
					}, {
						columnWidth : .20,
						border : false,
						layout : 'form',
						items : [ {
							columnWidth : .4,
							border : false,
							layout : 'form',
							items : [] 
						} ]
					} ]
				}, {
					layout : 'form',
					items : []
				} ]
			});

			
			
			//***********************************************************
			//上传图片类型  
			 var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/;  
			  
			 var _panel1=new Ext.Panel({
				 id:'_panel1',
	             frame:true,
	             width:780,
	             height:80,
//	             layout:"column",
//	             autoScroll : true,
	             lableWidth:600,
	 			items : [ {
					layout : 'column',
					border : true,
					items : [  {
						layout:'form',
						columnWidth : .2, 
						items : [ new Ext.BoxComponent({
							frame:true,
					           id : 'browseImage1', 
					           autoEl : {  
					               width : 65,   
					               height :65,  
					               tag : 'img',  
					                // type : 'image',  
//					               src : rootPath+'image/add.jpg',   //Ext.BLANK_IMAGE_URL
					               style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',     
					               complete : 'off',     
					               id : 'imageBrowse1'  
					           }   
						})]
					}, {
						columnWidth : .8,
						items : [
							new Ext.form.Radio({
								width:600,
								id:'radio1',
//								  value:1,
								  name:'swim',  
//								  fieldLabel:'',  
								  boxLabel:'请指定活动...' ,
								  items : {pic : '', id : ''}
							})
			            ]
			            
					}]
				} ]
	             });
			 var _panel2=new Ext.Panel({
				 id:'_panel2',
				 hidden:false,
				 frame:true,
				 width:780,
				 height:80,
//				 layout:"column",
				 autoScroll : true,
//	             lableWidth:200,
				 items : [ {
					 layout : 'column',
					 border : true,
					 items : [  {
						 layout:'form',
						 columnWidth : 0.2, 
						 items : [ new Ext.BoxComponent({
							 frame:true,
							 id : 'browseImage2', 
							 autoEl : {  
							 width : 65,   
							 height :65,  
							 tag : 'img',  
							 // type : 'image',  
//					               src : rootPath+'image/add.jpg',   //Ext.BLANK_IMAGE_URL
							 style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',     
							 complete : 'off',     
							 id : 'imageBrowse2'  
						 }   
						 })]
					 }, {
						 columnWidth : .8,
						 items : [
						          new Ext.form.Radio({ 
						        	  width:600,
						        	  id:'radio2',
//						        	  value:'2',
						        	  name:'swim',  
						        	  fieldLabel:'',  
						        	  boxLabel:'请指定活动...' ,
						        	  items : {pic : '', id : ''}
						          })
						          
						          ]
						          
					 }]
				 } ]
			 });
			 var _panel3=new Ext.Panel({
				 id:'_panel3',
				 hidden:true,
				 frame:true,
				 width:780,
				 height:80,
//				 layout:"column",
				 autoScroll : true,
//	             lableWidth:200,
				 items : [ {
					 layout : 'column',
					 border : true,
					 items : [  {
						 layout:'form',
						 columnWidth : 0.2, 
						 items : [ new Ext.BoxComponent({
							 frame:true,
							 id : 'browseImage3', 
							 autoEl : {  
							 width : 65,   
							 height :65,  
							 tag : 'img',  
							 // type : 'image',  
//					               src : rootPath+'image/add.jpg',   //Ext.BLANK_IMAGE_URL
							 style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',     
							 complete : 'off',     
							 id : 'imageBrowse3'  
						 }   
						 })]
					 }, {
						 columnWidth : .8,
						 items : [
						          new Ext.form.Radio({ 
						        	  width: 600,
						        	  id:'radio3',
//						        	  value:'2',
						        	  name:'swim',  
						        	  fieldLabel:'',  
						        	  boxLabel:'请指定活动...' ,
						        	  items : {pic : '', id : ''}
						          })
						          
						          ]
						          
					 }]
				 } ]
			 });
			 var _panel5=new Ext.Panel({
				 id:'_panel5',
				 hidden:true,
				 frame:true,
				 width:780,
				 height:80,
//				 layout:"column",
				 autoScroll : true,
//	             lableWidth:200,
				 items : [ {
					 layout : 'column',
					 border : true,
					 items : [  {
						 layout:'form',
						 columnWidth : 0.2, 
						 items : [ new Ext.BoxComponent({
							 frame:true,
							 id : 'browseImage5', 
							 autoEl : {  
							 width : 65,   
							 height :65,  
							 tag : 'img',  
							 // type : 'image',  
//					               src : rootPath+'image/add.jpg',   //Ext.BLANK_IMAGE_URL
							 style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',     
							 complete : 'off',     
							 id : 'imageBrowse5'  
						 }   
						 })]
					 }, {
						 columnWidth : .8,
						 items : [
						          new Ext.form.Radio({ 
						        	  width: 600,
						        	  id:'radio5',
//						        	  value:'2',
						        	  name:'swim',  
						        	  fieldLabel:'',  
						        	  boxLabel:'请指定活动...' ,
						        	  items : {pic : '', id : ''}
						          })
						          ]
					 }]
				 } ]
			 });
			 var _panel4=new Ext.Panel({
				 id:'_panel4',
				 hidden:true,
				 frame:true,
				 width:780,
				 height:80,
//				 layout:"column",
				 autoScroll : true,
//	             lableWidth:200,
				 items : [ {
					 layout : 'column',
					 border : true,
					 items : [  {
						 layout:'form',
						 columnWidth : 0.2, 
						 items : [ new Ext.BoxComponent({
							 frame:true,
							 id : 'browseImage4', 
							 autoEl : {  
							 width : 65,   
							 height :65,  
							 tag : 'img',  
							 // type : 'image',  
//					               src : rootPath+'image/add.jpg',   //Ext.BLANK_IMAGE_URL
							 style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',     
							 complete : 'off',     
							 id : 'imageBrowse4'  
						 }   
						 })]
					 }, {
						 columnWidth : .8,
						 items : [
						          new Ext.form.Radio({ 
						        	  width:600,
						        	  id:'radio4',
//						        	  value:'2',
						        	  name:'swim',  
						        	  fieldLabel:'',  
						        	  boxLabel:'请指定活动...' ,
						        	  items : {pic : '', id : ''}
						          })
						          
						          ]
						          
					 }]
				 } ]
			 });
			 var _panel6=new Ext.Panel({
				 id:'_panel6',
				 hidden:true,
				 frame:true,
				 width:780,
				 height:80,
//				 layout:"column",
				 autoScroll : true,
//	             lableWidth:200,
				 items : [ {
					 layout : 'column',
					 border : true,
					 items : [  {
						 layout:'form',
						 columnWidth : 0.2, 
						 items : [ new Ext.BoxComponent({
							 frame:true,
							 id : 'browseImage6', 
							 autoEl : {  
							 width : 65,   
							 height :65,  
							 tag : 'img',  
							 // type : 'image',  
//					               src : rootPath+'image/add.jpg',   //Ext.BLANK_IMAGE_URL
							 style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',     
							 complete : 'off',     
							 id : 'imageBrowse6'  
						 }   
						 })]
					 }, {
						 columnWidth : .8,
						 items : [
						          new Ext.form.Radio({ 
						        	  width:600,
						        	  id:'radio6',
//						        	  value:'2',
						        	  name:'swim',  
						        	  fieldLabel:'',  
						        	  boxLabel:'请指定活动...' ,
						        	  items : {pic : '', id : ''}
						          })
						          
						          ]
						          
					 }]
				 } ]
			 });
			 var _panel7=new Ext.Panel({
				 id:'_panel7',
				 hidden:true,
				 frame:true,
				 width:780,
				 height:80,
//				 layout:"column",
				 autoScroll : true,
//	             lableWidth:200,
				 items : [ {
					 layout : 'column',
					 border : true,
					 items : [  {
						 layout:'form',
						 columnWidth : 0.2, 
						 items : [ new Ext.BoxComponent({
							 frame:true,
							 id : 'browseImage7', 
							 autoEl : {  
							 width : 65,   
							 height :65,  
							 tag : 'img',  
							 // type : 'image',  
//					               src : rootPath+'image/add.jpg',   //Ext.BLANK_IMAGE_URL
							 style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',     
							 complete : 'off',     
							 id : 'imageBrowse7'  
						 }   
						 })]
					 }, {
						 columnWidth : .8,
						 items : [
						          new Ext.form.Radio({ 
						        	  width: 600,
						        	  id:'radio7',
//						        	  value:'2',
						        	  name:'swim',  
						        	  fieldLabel:'',  
						        	  boxLabel:'请指定活动...' ,
						        	  items : {pic : '', id : ''}
						          })
						          
						          ]
						          
					 }]
				 } ]
			 });
			 var _panel8=new Ext.Panel({
				 id:'_panel8',
				 hidden:true,
				 frame:true,
				 width:780,
				 height:80,
//				 layout:"column",
				 autoScroll : true,
//	             lableWidth:200,
				 items : [ {
					 layout : 'column',
					 border : true,
					 items : [  {
						 layout:'form',
						 columnWidth : 0.2, 
						 items : [ new Ext.BoxComponent({
							 frame:true,
							 id : 'browseImage8', 
							 autoEl : {  
							 width : 65,   
							 height :65,  
							 tag : 'img',  
							 // type : 'image',  
//					               src : rootPath+'image/add.jpg',   //Ext.BLANK_IMAGE_URL
							 style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',     
							 complete : 'off',     
							 id : 'imageBrowse8'  
						 }   
						 })]
					 }, {
						 columnWidth : .8,
						 items : [
						          new Ext.form.Radio({ 
						        	  width:600,
						        	  id:'radio8',
//						        	  value:'2',
						        	  name:'swim',  
						        	  fieldLabel:'',  
						        	  boxLabel:'请指定活动...' ,
						        	  items : {pic : '', id : ''}
						          })
						          
						          ]
						          
					 }]
				 } ]
			 });
			 var _panel9=new Ext.Panel({
				 id:'_panel9',
				 hidden:true,
				 frame:true,
				 width:780,
				 height:80,
//				 layout:"column",
				 autoScroll : true,
//	             lableWidth:200,
				 items : [ {
					 layout : 'column',
					 border : true,
					 items : [  {
						 layout:'form',
						 columnWidth : 0.2, 
						 items : [ new Ext.BoxComponent({
							 frame:true,
							 id : 'browseImage9', 
							 autoEl : {  
							 width : 65,   
							 height :65,  
							 tag : 'img',  
							 // type : 'image',  
//					               src : rootPath+'image/add.jpg',   //Ext.BLANK_IMAGE_URL
							 style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',     
							 complete : 'off',     
							 id : 'imageBrowse9'  
						 }   
						 })]
					 }, {
						 columnWidth : .8,
						 items : [
						          new Ext.form.Radio({ 
						        	  width:600,
						        	  id:'radio9',
//						        	  value:'2',
						        	  name:'swim',  
						        	  fieldLabel:'',  
						        	  boxLabel:'请指定活动...' ,
						        	  items : {pic : '', id : ''}
						          })
						          
						          ]
						          
					 }]
				 } ]
			 });
			 var _panel10=new Ext.Panel({
				 id:'_panel10',
				 hidden:true,
				 frame:true,
				 width:780,
				 height:80,
//				 layout:"column",
				 autoScroll : true,
//	             lableWidth:200,
				 items : [ {
					 layout : 'column',
					 border : true,
					 items : [  {
						 layout:'form',
						 columnWidth : 0.2, 
						 items : [ new Ext.BoxComponent({
							 frame:true,
							 id : 'browseImage10', 
							 autoEl : {  
							 width : 65,   
							 height :65,  
							 tag : 'img',  
							 // type : 'image',  
//					               src : rootPath+'image/add.jpg',   //Ext.BLANK_IMAGE_URL
							 style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',     
							 complete : 'off',     
							 id : 'imageBrowse10'  
						 }   
						 })]
					 }, {
						 columnWidth : .8,
						 items : [
						          new Ext.form.Radio({ 
						        	  width:600,
						        	  id:'radio10',
//						        	  value:'2',
						        	  name:'swim',  
						        	  fieldLabel:'',  
						        	  boxLabel:'请指定活动...' ,
						        	  items : {pic : '', id : ''}
						          })
						          
						          ]
						          
					 }]
				 } ]
			 });

			var  number=1;
			var  max=0;
			var  N=1;
			var   add=3;
			
			var win_uploadImage = new Ext.Panel({ 
				id:'win_uploadImage',
				  autoScroll : true,
//			    layout:'fit',  
			    width: 825,  
//			    closeAction:'hide',  
			    height:234,  
//			    resizable:false,  
//			    shadow:false,  
//			    modal:true,  
//			    closable:true,  
//			    bodyStyle:'padding: 5 5 5 5',  
//			    animCollapse:true,  
//			    imageIndexName:'',  
			    items:[
			           {  
			        xtype:'form',  
			        id:'image-upload-form',  
			        frame:true,  
			        border:false,  
//			        isAdd:false,  
			        enctype: 'multipart/form-data',  
			        fileUpload : true,  
			        layout : 'form',  
			        items:[{  
			           id : 'file-idx',  
			           name : 'file',  
			           inputType : "file",  
			           fieldLabel : '上传图片',  
			           xtype : 'textfield',  
			           blankText:'上传图片不能为空'   
			        },{
			        	items : [_panel1,_panel2,_panel3,_panel4,_panel5,_panel6,_panel7,_panel8,_panel9,_panel10] 
			        }   
			        ],  
			        listeners : {     
			            'render' : function(f) {  
			            }     
			        },   
			        buttons:[
			               
			           {  
			                text:'上传',  
			                handler:function() {
//			        	   Ext.getCmp('_panel3').setVisible(true);
			        	   
					   //--------------------------------------------
					        	   for(act=1;act<=10;act++){//1xx
					        		   if(Ext.getCmp('radio'+act).getValue()==true){//查找到 哪个单选框被选中 ，则 上传文件到哪个里面
					        			   N=act;
					        			   
					        			   
					        			   var furl="";  
						                    furl = Ext.getCmp('image-upload-form').form.findField('file').getValue();  
						                    var type = furl.substring(furl.length - 3).toLowerCase();  
						                    if (furl == "" || furl == null) {  
						                        return;  
						                    }  
						                    if (type != 'jpg' && type != 'bmp' && type != 'gif' && type != 'png') {  
						                        alert('仅支持jpg、bmp、gif、png格式的图片');  
						                        return;  
						                    }  
						                   Ext.getCmp('image-upload-form').form.submit({  
						                        clienValidation:true,  
						                        waitMsg:'正在上传请稍候',
						                        waitTitle:'提示',  
						                        url:'basedata/fourKeyMainTainAd!upLoadImage.action',  
						                        method:'POST', 
						                        params:{cityID:Ext.getCmp('innerCity').getValue(),menuID:Ext.getCmp('innerMenu').getValue()},
						                        success:function(form,action){ 
						                        
						                            var picName = action.result.picName; 
//						                            str.replace(/Microsoft/, "W3School")
						                            _this.savePicItem.pic = picName;   //将这个地方 用城市ID +_+菜单项ID+日期
						                            Ext.getCmp('image-upload-form').form.el.dom.reset(); 
						                                Ext.get('imageBrowse'+N).dom.src=picName;
						                            	Ext.getCmp('radio'+N).items.pic=picName;
						                            	
						                            	//将图片的路径赋值给 选中的单选框 PIC 属性，点击下面活动的单选框，则将ID 赋值给这个单选框的ID
						                            	//最后FOR 循环的时候 ，将所有的值取出来， 一起保存就KO了
//						                                number++;
//						                                max=number;
//						                                mNumber=number;
//						                                Ext.getCmp('_panel').add(_this.create_panel(number));
//						                                Ext.getCmp('_panel').doLayout(); //2xx
//						                                tip=true;
//						                                tip3=true;
						                	    
						                        },
						                        failure:function(form,action){  
						                            Ext.MessageBox.show({title: '失败',msg: '上传失败!',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
						                        }
						                   })    
					        			   
					        			   
					        		   }
					        	   }
			                }  
			           },{
			        	   text:'继续上传',  
			                handler:function() {
			        	   Ext.getCmp('_panel'+add).setVisible(true); 
			        	   add++;
			           }
			        },{text:'移除',
			        	handler:function(){
			        	 for(act=1;act<=10;act++){
			        	 if(Ext.getCmp('radio'+act).getValue()==true){
			        		 Ext.getCmp('radio'+act).items.id='';
			        		 Ext.getCmp('radio'+act).items.pic='';
			        		 Ext.get('imageBrowse'+act).dom.src='';
			        		 Ext.select(".x-form-cb-label[for=radio"+act+"]").update('请指定活动...');
			        	 }
			        	 }
			        }}
			       ]  
			}]  
			});	
			addWin = new Ext.Window({ 
				id : 'innerWindow',
				title: '添加', 
//				closable:false,
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
//	            autoScroll : true,
	            width : 850,  
	            height:550,	 
				layout : 'column',
				frame : true,
				items : [ pnlMain,win_uploadImage,this.createBottomPanel()]
			});
			addWin.show();
			Ext.getCmp('bottomGrid').store.reload();
			
		},
		queryForButtom : function(){
			var gridStore = Ext.getCmp('bottomGrid').store;
			var paramJson = {};
	    	paramJson.cityid = Ext.getCmp('bottomcity').getValue(); 
	    	paramJson.industyid=Ext.getCmp('industy').getValue();
	    	paramJson.activityid = Ext.getCmp('activity').getValue();
	    	paramJson.title = Ext.getCmp('title').getValue();
	    	gridStore.baseParams = paramJson;
	    	gridStore.load({
	    		params : {
					start : 0,
					limit : 6
				},
				callback: function(obj){
					if(obj.length == 0) {
					}
				}
			});
	
			
			
			
			
		},
		
		//创建 底部的活动搜索面板
		createBottomPanel : function(){
			var _this = this;
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
					C_ID : '',
					C_CNAME : '全部'
				}));
			} 
			});
			var bottomcity = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : suppStore,
				fieldLabel : '城市',
				id :'bottomcity',
				name : 'supp',
				allowBlank : false,
				editable : false,
				mode : 'local',
				displayField : 'C_CNAME',
				valueField : 'C_ID',
				resizable : true,
				editable : false,
				width : 85,
				selectOnFocus : true 
			});
		//---------------------------------行业的下拉框---------------------------- 
			

			var industyStore = new Ext.data.Store( {
				// autoLoad: true,
				proxy : new Ext.data.HttpProxy( {
					url : rootPath + "basedata/fourKeyMainTainAd!getIndustryCat.action",
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
			
			industyStore.load( {
				callback : function(obj) {
				industyStore.insert(0, new industyStore.recordType( {
					C_ID : '',
					C_NAME : '全部'
				}));
			} 
			});
			var industy = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : industyStore,
				fieldLabel : '行业类别',
				id :'industy',						
				allowBlank : false,
				editable : false,
				mode : 'local',
				displayField : 'C_NAME',
				valueField : 'C_ID', 
				resizable : true,
				editable : false,
				width : 85,
				selectOnFocus : true
			});
			
		//---------------------获得活动内容类别----------------------------------------------	
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
			activityStore.load( {
				callback : function(obj) {
				activityStore.insert(0, new activityStore.recordType( {
					C_ID : '',
					C_NAME : '全部'
				}));
			}
			});
			var activity = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : activityStore,
				fieldLabel : '活动内容类别',
				id :'activity',			
				allowBlank : false,
				editable : false,
				mode : 'local',
				displayField : 'C_NAME',
				valueField : 'C_ID',
				resizable : true,
				editable : false,
				width : 80,
				selectOnFocus : true
			});
	   //--------------------------标题的模糊查询-------------------------------------------		
			
			var title = new Ext.form.TextField( {
				fieldLabel : '标题',
				id :'title',
				resizable : true,
				editable : false,
				width : 100, 
				selectOnFocus : true
			});
			
			
 
		//----------------------------声明查询指定活动的表单-----------------------------------------------------	
			var pnlMain = new Ext.form.FormPanel( {
				
				id :  'bottomformPanel',
				//bodyStyle : 'padding:5 5 5 5;border:0px',
				width :1100,
				autoHeight : true,
				bodyBorder: false,	
				border :false,
				//frame false,
				labelWidth : 60,
				align:'center',
				items : [{
					layout : 'column',
					border : false,
					items : [ {
						columnWidth : .15,
						layout : 'form',
						items : [  bottomcity]
					}, {
						columnWidth : .18,
						layout : 'form',
						items : [ industy ]
					}, {
					}, {
						columnWidth : .17,
						labelWidth : 80,
						layout : 'form',
						items : [ activity ]
					}, {
					}, {
						columnWidth : .15,
						layout : 'form',
						labelWidth : 45,
						items : [ title ]
					}, {
						columnWidth : .15,
						border : false,
						layout : 'column',
						items : [ {
							columnWidth : .37,
							border : false,
							layout : 'column',
							items : [ {
								xtype : 'button',
								text : '查询',
								width :40,
								handler : function() {
									_this.queryForButtom();
								}
							} ]
						},{} ]
					}
					]
				}]
			});
			var grid = _this.createBottomGrid();
			
			Manager.sys.Utils.antoScrollerInit(grid,pnlMain);
			
			grid.add(pnlMain);
			var pnl = new Ext.Panel( { 
				id : 'bottompanel', 
				bodyStyle : 'border:0px solid #99bbe8',//padding:5 5 5 5;
				renderTo : 'mainDivId',//applyTo
				items : [grid]
			})
			return  pnl;
		},
		create_panel:function(number){ 
			var _panel=new Ext.Panel({
				 id:'_panel'+number,
//	             frame:true, 
	             width:500,
	             height:80,
	             layout:"column",
	             autoScroll : true,
//	             lableWidth:200,
	 			items : [ {
					layout : 'column',
					border : true,
					items : [  {
						layout:'form',
						columnWidth : 1, 
						items : [ new Ext.BoxComponent({
							frame:true,
					           id : 'browseImage'+number, 
					           autoEl : {  
					               width : 65,   
					               height :65,  
					               tag : 'img',  
					                // type : 'image',  
//					               src : rootPath+'image/add.jpg',   //Ext.BLANK_IMAGE_URL
					               style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',     
					               complete : 'off',     
					               id : 'imageBrowse'+number  
					           }   
						})] 
					}, {
						columnWidth : .4,
						items : [] 
					} ]
				} ]
	             });	
			return _panel;
		},
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
               	{name : 'C_ID'},
               	{name : 'C_ADVERTID'},
               	{name : 'CID'},
               	{name : 'MID'},
               	{name:'C_CITY'},
                {name : 'C_MENU'} 
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/fourKeyMainTainAd!getSummaryMessage.action",
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
               { header: "C_ADVERTID", hidden: true, dataIndex: 'C_ADVERTID',width:15},
               { header: "CID", hidden: true, dataIndex: 'CID',width:15},
               { header: "MID", hidden: true, dataIndex: 'MID',width:15},
               { header: "城市", sortable: true, dataIndex: 'C_CITY',width:20},
               { header: "菜单项", sortable: true, dataIndex: 'C_MENU',width:20
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
    		//----------------------------------
  
    			grid.on('cellclick', function (grid, rowIndex, columnIndex, e) {
    		    var btn = e.getTarget('.controlBtn');   
    		    if (btn) {
    			var t = e.getTarget();   
    			var record = grid.getStore().getAt(rowIndex);
    			var control = t.className;
    	//将要修改的数据 传入到对象的属性中		
    			
    			
    			if(record&&control=='delete'){
    				if(window.confirm('是否确认删除？')){
    					var delparams = {};
						delparams.c_id= record.get('C_ID');
						delparams.MID= record.get('MID');
						
						
						var loadMask = new Ext.LoadMask(document.body, {msg : '正在删除,请稍等...', removeMask : true});
						loadMask.show();
						Ext.Ajax.request({
				            url : rootPath+"basedata/fourKeyMainTainAd!deleteByID.action",
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
	    			}else if(record&&control=='modify'){//这里为修改
	    				update=true; 
	    				
	    				var  id =record.get('C_ID');
	    				var  cityid=record.get('CID');
	    				var  menuid=record.get('MID');	
	    				var  city=record.get('C_CITY');
	    				var  menu=record.get('C_MENU');
	    				var  C_ADVERTID=record.get('C_ADVERTID')
	    				//点击修改 ，则从数据库里面取数据  找到 BOX 和radio 进行赋值就可以了
	    				
	    				Ext.Ajax.request({
				            url : rootPath+"basedata/fourKeyMainTainAd!getPicsAndIds.action",
				            timeout : 120000,
				            success: function(result, obj){
				            	
				                var jsonFromServer = Ext.util.JSON.decode(result.responseText);
				                var  pics=jsonFromServer.PICS
				                var  items=pics.split(",");
				                //赋值图片
				                for(i=0;i<items.length;i++){
				                	var  x=i+1;
				                	 Ext.get('imageBrowse'+x).dom.src=items[i];   //将图片 显示
		                            	Ext.getCmp('radio'+x).items.pic=items[i];//给 radio 赋值图片URL
				                }
				              //赋值 ID
				                var  titles=jsonFromServer.TITLES
				                var  items2=titles.split("|");
				                for(j=0;j<items2.length;j++){
				                	var  x=j+1;
				                	
				                	Ext.select(".x-form-cb-label[for=radio"+x+"]").update(items2[j]);
				                }
				                
				                var  ids=jsonFromServer.IDS
				                var  items3=ids.split(",");
				                for(k=0;k<items3.length;k++){
				                	var  x=k+1;
				                	Ext.getCmp('radio'+x).items.id=items3[k];
				                }
				                var  length=jsonFromServer.LENGTH
				                for(l=1;l<=length;l++){
				                	Ext.getCmp('_panel'+l).setVisible(true);
				                }
				                
				                
			                },
				            failure: function(result, obj){
				            	alert('加载数据失败!.');
				            }, 
				            params: {id:C_ADVERTID}
				        });
	    				
	    				
	    				
	    				
	    				_this.createInnerFormPanel(id,cityid,menuid,city,menu,C_ADVERTID);
	    			}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		},
		saveItem : function (id,title){
			alert('指定活动成功,请继续上传图片或保存！')//将单选框的ID 赋值给上面的对象
			tip2=true;//在这里 改变的是 活动的ID 现在想把标题也放到图片右边
			var _this = this;
			_this.savePicItem.id = id;
			var num=mNumber-1;    
			
			 for(act=1;act<=10;act++){//1xx
				 if(Ext.getCmp('radio'+act).getValue()==true){
					 Ext.getCmp('radio'+act).items.id=id;
					 Ext.select(".x-form-cb-label[for=radio"+act+"]").update(title);
				 }
				 
				 
			 }
		},
		saveAll:function(id){ 
			
//			if (!Ext.getCmp(_this.formId + 'formPanel111').isValid()) {
//				return;
//			}
			var data=[];
			var item;
			for(var i=1;i <=10;i++){//1xx
			//如果 那张图片没有上传的时候 没选活动，我就让他 不输入到后台
				item ={PICS:Ext.getCmp('radio'+i).items.pic,ACTIDS:Ext.getCmp('radio'+i).items.id};
				if((Ext.getCmp('radio'+i).items.pic.length==0&&Ext.getCmp('radio'+i).items.id.length==0)){
				}else{
					data.push(item); 
				}
			}
			if(update){ 
				var saveId=id;
				Ext.Ajax.request({
		            url : rootPath+"basedata/fourKeyMainTainAd!Update.action", 
		            timeout : 120000,
		            success: function(result, obj){
					alert('修改成功!');
					Ext.getCmp('grid').store.reload();
					Ext.getCmp('innerWindow').close();
	                }, 
		            failure: function(result, obj){
	                	alert('修改失败');
		            }, 
		             params: {saveID:saveId,data:Ext.encode(data)}
		        });	
			}else{//添加的时候,判断是否已经存在
				if(Ext.getCmp('innerCity').getValue()!=''&&Ext.getCmp('innerMenu').getValue()!=''){
					Ext.Ajax.request({
			            url : rootPath+"basedata/fourKeyMainTainAd!saveAll.action", 
			            timeout : 120000,
			            success: function(result, obj){
						var jsonFromServer = Ext.util.JSON.decode(result.responseText);
						if(jsonFromServer.status=='N'){
							alert('对不起,该城市的菜单项已经存在');
						}else{
							alert('保存成功!');
						}
						Ext.getCmp('grid').store.reload();
		                },
			            failure: function(result, obj){
		                	alert('保存失败');
			            }, 
			             params: {cityID:Ext.getCmp('innerCity').getValue(),menuID:Ext.getCmp('innerMenu').getValue(),data:Ext.encode(data)}
			        });		
				}else{
					alert('请选择城市和菜单项')
				}	
			}
			
			
		},
		
		renderActivity : function(){
			var _this = this;
			var gridStore = Ext.getCmp(_this.gridId).store;
			// gridStore.removeAll();

			var paramJson = {};
			// 值得传递
			paramJson.time_s = Ext.getCmp(_this.formId + 'time_s').getRawValue();
			paramJson.time_e = Ext.getCmp(_this.formId + 'time_e').getRawValue();
			paramJson.supplier = Ext.getCmp(_this.formId + 'supp').getValue();
			paramJson.isSendDemo = Ext.getCmp(_this.formId + 'isSendDemo')
					.getValue();

			gridStore.baseParams = paramJson;
			gridStore.load( {
				params : {
					start : 0,
					limit : 6
				},
				callback : function(obj) {
					if (obj.length == 0) {
					}
				}
			});

		},
		createBottomGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
			                                            {name : 'id'},
			                                            {name : 'city'},
			                                            {name:'activity'},
			                                            {name : 'title'},
			                                            {name:'industy'},
			                                            {name : 'industy'},{
			                                            name:'Url'	 
			                                            }
			                                            ]);
			// 列表用到的数据源
			var innerResultsGrid_store = new Ext.data.Store({ 
				proxy : new Ext.data.HttpProxy({
					url : rootPath+"basedata/fourKeyMainTainAd!getInnerSummaryMessage.action",
					timeout : 60000
				}),
				reader : new Ext.data.JsonReader({
					totalProperty : 'count',
					root : 'list'
				}, resultsRecord),
				baseParams : {
				start:0,limit:10
			}
			}); 
			
			var tempArray = [
			                 { header: "ID", hidden: true, dataIndex: 'id',width:15},
			                 { header: "指定",dataIndex:'flag',width:4,renderer:function(value, meta, record) { 
			                	 var cid = record.get("city");
			                	
			                	 return "<input type='radio' name='flag' onchange='activityFollowScheme.saveItem(\""+record.get("id")+"\",\""+record.get("title")+"\")'/>";
			                 }},
			                 { header: "城市", sortable: true, dataIndex: 'city',width:6},
			                 {header: "行业", sortable: true, dataIndex: 'industy',width:8},
			                 { header: "性质", sortable: true, dataIndex: 'activity',width:8},
//			                 { header: "标题", sortable: true, dataIndex: 'title',width:40,},
			                 { header: "标题", sortable: true, dataIndex: 'title',width:65,
			                		renderer: function (value, meta, record) {
			                			var formatStr = "<a style='cursor: hand;text-decoration:none;' href='javascript:void();' onclick='javscript:return false;' class='URL'>{0}</a>";   
			    				     	var resultStr = String.format(formatStr, value);   
			    				     return "<div class='controlBtn'>" + resultStr + "</div>";
			        				}.createDelegate(this)
			                }
//			                 {header: "参加URL", sortable: true, dataIndex: 'Url',width:20,},
			                 ];
			
			var resultColumnModel = new Ext.grid.ColumnModel(tempArray);
			//中间的Gird
			var bottomGrid = new Ext.grid.EditorGridPanel({
				id:"bottomGrid",
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
			width : 825,
			height :260,
			loadMask : { msg : LOADMASK_MSG },
			bbar : new Ext.PagingToolbar({//分页工具条
				pageSize : 6, 
				store : innerResultsGrid_store
			})
			}); 
			bottomGrid.on('cellclick', function (grid, rowIndex, columnIndex, e) {
	    		  var btn = e.getTarget('.controlBtn');   
	    		  if (btn) {
	    			var t = e.getTarget();   
	    			var record = grid.getStore().getAt(rowIndex);
	    			var control = t.className;
	    			if(record&&control=='URL'){
	    				window.open(record.get('Url'));
	    	            return true;
	    			}else{
	    			}
	    		  }   
	    		},   
	    		this);
			return bottomGrid;
		}
	}
};
Ext.actionSchemeDetail = function() {
	return {
		//定义下拉框
		url : {
			allBrand : rootPath+"basedata/activitySchemeDetail!findBrandAllName.action",
			allScheme : rootPath+"basedata/activitySchemeDetail!findSchemeAllName.action"
		},

		formId : 'actionSchemeDetail_formId',
		gridId : 'actionSchemeDetail_gridId',
		addWinId : 'actionSchemeDetail_addWinId',
		textwidth : 120,
		init : function(){
			
		},

		
		load : function(){
			//alert(11)
			var _this = this;
			//_this.createForm();
			
			var nameField = new Ext.form.ComboBox({
				id: _this.formId+'c_schemeid',
				fieldLabel : '方案名',
				store: _this.createStore(_this.url.allScheme),
				name : 'c_schemeid',
				displayField : 'C_NAME',
				valueField : 'C_ID',
				triggerAction: 'all',
				mode : 'local',
				width : 140
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
			
	    	paramJson.c_schemeid = Ext.getCmp(_this.formId +'c_schemeid').getValue();
	    	
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
			saveParam.c_brandid = dirfrom.findField('c_brandid').getValue();
			saveParam.c_schemeid = dirfrom.findField('c_schemeid').getValue();
		    saveParam.c_order = dirfrom.findField('c_order').getValue();
			var params = {};
			params.saveParam = Ext.encode(saveParam);
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"basedata/activitySchemeDetail!saveActivitySchemeDetail.action",
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
		
		createStore : function(url){
			var _this = this;
			//处理品牌的下拉框		
			var functionNameStore = new Ext.data.Store({
            	autoLoad: false, 
            	proxy : new Ext.data.HttpProxy({
            		url : url,
            	    timeout : 60000
            	}),
            	reader : new Ext.data.JsonReader({
            		root : 'list'
            	},
				Ext.data.Record.create([
					{ name : 'C_ID'},
					{ name : 'C_CNAME'},
					{name:'C_NAME'}
				]))
            });
			functionNameStore.load({
            	callback: function(obj){
					functionNameStore.insert(0,new functionNameStore.recordType({C_ID:'',C_NAME:'全部'}));
				}
            });
			return functionNameStore;
		},

		priCreateWinFun : function(){
			var _this = this;
			
			var addWin = Ext.getCmp(_this.addWinId);
			if(addWin){
				addWin.show();
				return;
			}
			//推荐内容的grid(下面的表格)
			
			
			//创建的单选按钮
			var radiogroup = new Ext.form.RadioGroup({
	        	 fieldLabel:'是否有广告',
				 id:'radioid',
				 
	             columns: 2,
	             items: [{
	                 name: 'schemeType',
	                 inputValue: '0',
	                 boxLabel: '无',
	                 width:120,
	                 checked: true
	             },{
	                 name: 'schemeType',
	                 inputValue: '1',
	                 width:20,
	                 boxLabel: '有'
	             }]
	         });
			//创建的推荐内容的单选按钮
			var radiogroup2 = new Ext.form.RadioGroup({
	        	 fieldLabel:'推荐内容',
				 id:'radioid2',
				 
	             columns: 4,
	             items: [{
	                 name: 'schemeMsg',
	                 inputValue: '0',
	                 boxLabel: '更新鲜',
	                 width:120,
	                 checked: true
	             },{
	                 name: 'schemeMsg',
	                 inputValue: '1',
	                 width:20,
	                 boxLabel: '热门'
	             },{
	                 name: 'schemeMsg',
	                 inputValue: '2',
	                 width:20,
	                 boxLabel: '打折'
	             },{
	                 name: 'schemeMsg',
	                 inputValue: '3',
	                 width:20,
	                 boxLabel: '聚会'
	             },{
	                 name: 'schemeMsg',
	                 inputValue: '4',
	                 width:20,
	                 boxLabel: '试用'
	             },{
	                 name: 'schemeMsg',
	                 inputValue: '5',
	                 width:20,
	                 boxLabel: '展会'
	             },{
	                 name: 'schemeMsg',
	                 inputValue: '6',
	                 width:20,
	                 boxLabel: '赛事'
	             },{
	                 name: 'schemeMsg',
	                 inputValue: '7',
	                 width:20,
	                 boxLabel: '上海'
	             }]
	         });
			var c_form = new Ext.form.FormPanel({
				id : _this.addWinId + 'addForm',
				frame : true,
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
				labelWidth : 80,
				fileUpload : true,
				height:700,				
				items:[{
					layout:'column',
					items:[{
						height:20,
						columnWidth:.35,
						layout:'form',
						items:[radiogroup]
					},{
//						title:'c',
						layout:'form',
						columnWidth:.35,
						items:[{
							xtype : 'combo',
							fieldLabel : '城市',
							
//							store:functionAllIndustryStore,
							id : _this.addWinId + 'c_industryid',
							name : 'c_industryid',
							displayField : 'C_NAME',
							valueField : 'C_ID',
							triggerAction: 'all',
							width:160
						}]
					},{
//						title:'d',
						columnWidth:.30,
						layout:'form',
						items:[{
								xtype:'textfield',
								id: _this.addWinId + 'schemeName',
								fieldLabel:'方案名称',
								width:180
						}]
					}]
					
				},{
					title:'ccc',
					items:[{
							
							layout:'form',
							items:[{
								xtype : 'combo',
								fieldLabel : '广告位选择',
								
		//							store:functionAllIndustryStore,
								id : _this.addWinId + 'c_industryid4',
								name : 'c_industryid4',
								displayField : 'C_NAME',
								valueField : 'C_ID',
								triggerAction: 'all',
								width:160
							}]
					},{
						
						layout:'column',
						items:[{
							title:'fsdfsad',
							
							columnWidth:.5
						},{
							title:'46456465',
							columnWidth:.5
						}]
						
					}]
				},{
					
					layout:'form',
					items:[radiogroup2]
				},{
//					title:'图标',
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
							text : '下',
							columnWidth:.3,
							handler : function() {
								alert("aaaaaaaaaaaaa")
					    	        				}
						},{
							
							height:20,
							
							columnWidth:.4
							
						},{
//							title:'ccc',
							xtype : 'button',
							text : '上',
							columnWidth:.3
						}]
					},{
						//title:'sdfasdfasd',
						height:20,
						columnWidth:.4	
					}]
				},{
					//title:'多选框',
					layout:'column',
					height:100,
					items:[{
						//title:'1',
						columnWidth:.25,
						height:100
					},{
					//	title:'2',
						height:100,
						layout:'column',
						columnWidth:.5,
						items:[{
							title:'ccccccccccc',
							columnWidth:.5,	
							height:100
						},{
							title:'fffffffffffff',
							columnWidth:.5,	
							height:100
						}]
						
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
		            	_this.priSaveFun();
		            }
		        } ]
			});
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '方案详细管理',
				closable:false,
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 800, 
	            height:700,	
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
                {name : 'P_NAME'},
                {name : 'P_NAME1'},
                {name:'C_SCHEMEID'},
                {name:'C_BRANDID'},
                {name : 'C_ORDER'}
                
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/activitySchemeDetail!getActivitySchemeDetailList.action",
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
               { header: "方案名称", sortable: true, dataIndex: 'C_SCHEMEID',width:60,
            		    renderer:function (value, meta, record){
            	   		if(!Ext.isEmpty(record.get('P_NAME1'))){
            	   			return record.get('P_NAME1');
            	   		}
            	        return value==0?'':value;
            	   }
               },
               { header: "品牌名称", sortable: true, dataIndex: 'C_BRANDID',width:40,
            		renderer:function(value,meta,record){
            			if(!Ext.isEmpty(record.get('P_NAME'))){
            				return record.get('P_NAME');
            			}
            			return value=0?'':value;
            		}	   
               },
               { header: "排序", sortable: true, dataIndex: 'C_ORDER',width:40},
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
				            url : rootPath+"basedata/activitySchemeDetail!deleteActivitySchemeDetail.action",
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
    				
    				dirfrom.findField('c_schemeid').setValue(data.C_SCHEMEID);
    				//dirfrom.findField('c_id').setValue(data.C_ID);
    				dirfrom.findField('c_schemeid').setValue(data.P_NAME1);
    				dirfrom.findField('c_brandid').setValue(data.P_NAME);
    				dirfrom.findField('c_brandid').setValue(data.C_BRANDID);
    				dirfrom.findField('c_order').setValue(data.C_ORDER);
    				
    				//dirfrom.findField('c_id').setReadOnly(true);	
    			}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		}
	}
};
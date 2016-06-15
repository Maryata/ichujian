Ext.actionBrandInfo = function() {
return {
		//定义下拉框
		priData : {
			categoryData : [{key:'1',value:'是'},{key:'0',value:'否'}]	
		},
		formId : 'actionBrandInfo_formId',
		gridId : 'actionBrandInfo_gridId',
		addWinId : 'actionBrandInfo_addWinId',
		textwidth : 120,
		init : function(){
			
		},
		load : function(){
			var _this = this;
			
			var nameField = new Ext.form.TextField({
				id: _this.formId+'c_cname',
				xtype : 'textfield',
				fieldLabel : '品牌名称',
				name:'c_cname',
				width : _this.textwidth,
				readOnly : false,
				maxLength : 50,
				allowBlank : 'true',
				listeners : {
					focus : function() {
					}
				}
			});
			var _Store = new Ext.data.Store({
	            	proxy : new Ext.data.HttpProxy({
	            		url : rootPath+"basedata/activityBrandInfo!findAllName.action",
	            	    timeout : 60000
	            	}),
	            	reader : new Ext.data.JsonReader({
	            		root : 'list'
	            	},
					Ext.data.Record.create([
						{ name:'C_ID'},
						{ name:'C_NAME'}
					]))
	            });
			_Store.load({
            	callback: function(obj){
				//_this.functionNameStore.insert(0,new _this.functionNameStore.recordType());
            		var allStore = new Ext.data.Record({
            			C_ID : '',
            			C_NAME : '全部'
            		});
            		_Store.insert(0,allStore);
				}
            });

			var pnlMain = new Ext.form.FormPanel( {
				id : _this.formId + 'formPanel',
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
					},{
						columnWidth : .30,
						layout : 'form',
						border : false,
						items : [{
							xtype : 'combo',//'lovcombo',//'combo',
							fieldLabel : '行业类别',
							store:_Store,
							id : _this.formId + 'c_industryid',
							name:'c_industryid',
							displayField : 'C_NAME',
							valueField : 'C_ID',
							triggerAction: 'all',
							mode : 'local',
							allowBlank : false,
							maxLength : 20,
							width: 140
						}]
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
			
			_this.priCreateWinFun(); // 打开一次编辑窗口
			Ext.getCmp(_this.addWinId).close(); // 关闭编辑窗口，由于编辑时第二次才可以设置combox数据正常、暂时没找到原因
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
				name:option.name,
	            fieldLabel : option.fieldLabel,
				triggerAction : 'all',
				allowBlank : option.isNoBlank ? false : true,
				value: option.value ? option.value : '',
				//readOnly:true,
				width : option.width ? option.width : _this.textwidth
			});
			
			return actCombo;
			
		},
		//***************************
		priQueryFun : function(){
			var _this = this;
			var gridStore = Ext.getCmp(_this.gridId).store;
			var paramJson = {};
			paramJson.c_cname = Ext.getCmp(_this.formId +'c_cname').getValue();
			paramJson.c_industryid = Ext.getCmp(_this.formId + 'c_industryid').getValue();
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
		
		priSaveFun : function(addWin){
			var _this = this;
			var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
			if (!dirfrom.isValid()) {
				return;
			}
			
			var saveParam = {};
			var searchParam = {};
			if(dirfrom.Ext_cid ){
				saveParam.c_id = dirfrom.Ext_cid;
				if(dirfrom.Ext_ckeyId){
					searchParam.c_id = dirfrom.Ext_ckeyId;
				}
			}else{
				/*if(Ext.isEmpty(dirfrom.findField('c_logourl').getValue())){
					alert('logoURL路径不能为空!');
					return;
				}
				if(Ext.isEmpty(dirfrom.findField('c_advertisurl').getValue())){
					alert('广告URL路径不能为空!');
					return;
				}*/
			};
			
			saveParam.c_industryid = dirfrom.findField('c_industryid').getValue();
			saveParam.c_cname = dirfrom.findField('c_cname').getValue();
			saveParam.c_foundtime = dirfrom.findField('c_foundtime').getRawValue();
			saveParam.c_brandidea = dirfrom.findField('c_brandidea').getValue();
			saveParam.c_brandurl = dirfrom.findField('c_brandurl').getValue();
		    saveParam.c_businessscope = dirfrom.findField('c_businessscope').getValue();
		    saveParam.c_abstract = dirfrom.findField('c_abstract').getValue();
		    saveParam.c_order = dirfrom.findField('c_order').getValue();
		    saveParam.c_islive = dirfrom.findField('c_islive').getValue();
			//saveParam.c_logourl = dirfrom.findField('c_logourl').getValue();
			//saveParam.c_advertisurl = dirfrom.findField('c_advertisurl').getValue();
		    saveParam.c_brandstore = dirfrom.findField('c_brandstore').getValue();
		    saveParam.c_weibo = dirfrom.findField('c_weibo').getValue();
		    saveParam.c_address = dirfrom.findField('c_address').getValue();
		    saveParam.c_slogan = dirfrom.findField('c_slogan').getValue();
		    
		    searchParam.c_pinyin = dirfrom.findField('c_pinyin').getValue();
		    searchParam.c_name = saveParam.c_cname;
		    
			var params = {};
			params.saveParam = Ext.encode(saveParam);
			params.searchParam = Ext.encode(searchParam);
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"basedata/activityBrandInfo!saveActivityBrandInfo.action",
				waitMsg : 'save ing...',
				success : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					//alert('保存成功');
					dirfrom.Ext_cid = rsc.c_id;
    				dirfrom.Ext_ckeyId = rsc.c_keyId;
    				addWin.close();
    				Ext.getCmp(_this.gridId).store.reload();
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
			
			var typeField = _this.createComVarField({fieldLabel:"是否有效",id: _this.addWinId + 'c_islive',name:'c_islive' , data:_this.priData.categoryData , width: 140,value:'1'});//,isNoBlank:true
			
			if(Ext.isEmpty(_this.functionNameStore)){
				//处理行业类型的下拉框		
				_this.functionNameStore = new Ext.data.Store({
	            	proxy : new Ext.data.HttpProxy({
	            		url : rootPath+"basedata/activityBrandInfo!findAllName.action",
	            	    timeout : 60000
	            	}),
	            	reader : new Ext.data.JsonReader({
	            		root : 'list'
	            	},
					Ext.data.Record.create([
						{ name:'C_ID'},
						{ name:'C_NAME'}
					]))
	            });
				_this.functionNameStore.load({
	            	callback: function(obj){
					//_this.functionNameStore.insert(0,new _this.functionNameStore.recordType());
					}
	            });
			}
			
			// 检查是否重复公共方法
			function checkExits(name,value,id) {
				if(!id){
					id = '';
				}
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", rootPath+"basedata/activityBrandInfo!checkActivityBrandInfo.action", false);// false表示同步调用
				conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				conn.send("name="+name+"&value="+value+"&id="+id);
				var data = Ext.util.JSON.decode(conn.responseText);
				return data;
			}
			
			var c_form = new Ext.form.FormPanel({
				id : _this.addWinId + 'addForm',
				frame : true,
				//;
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8',
				labelWidth : 60,
				layout : 'form',
				fileUpload : true,
				items:[{
					layout:'column',
					border :false,
					items:[{
						columnWidth:.5,		
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '品牌名称',
							id : _this.addWinId + 'c_cname',
							name:'c_cname',
							allowBlank : false,
							maxLength : 20,
							width : 140,
							validationDelay : 500,
							validator : function(value){
								var data =checkExits("c_cname",value,Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);
								if(!Ext.isEmpty(data.isExits) && data.isExits == '1'){
									return "品牌名称已存在！";
								}
								return true;
							}
						}]
					},{
						columnWidth:.5,		
						layout : 'form',
						items:[{
							xtype : 'lovcombo',//'combo',
							fieldLabel : '行业类别',
							store:_this.functionNameStore,
							id : _this.addWinId + 'c_industryid',
							name:'c_industryid',
							displayField : 'C_NAME',
							valueField : 'C_ID',
							triggerAction: 'all',
							mode : 'local',
							value : '',
							allowBlank : false,
							maxLength : 50,
							width : 140	
						}]
					}]
					
				},{
					layout:'column',
					border :false,
					items:[{
						columnWidth:.5,		
						layout : 'form',
						items : [{
							xtype:'datefield',
							fieldLabel : '成立时间',
							id : _this.addWinId + 'c_foundtime',
							name:'c_foundtime',
							format :'Y-m-d',
							width : 140
						}]
					},{
						columnWidth:.5,		
						layout : 'form',
						items:[{
							xtype : 'textfield',
							fieldLabel : '品牌理念',
							id : _this.addWinId + 'c_brandidea',
							name:'c_brandidea',
							maxLength : 150,
							width : 140
						}]
					}]
				},{
					layout:'column',
					border :false,
					items:[{
						columnWidth:.5,		
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '经营范围',
							id : _this.addWinId + 'c_businessscope',
							name:'c_businessscope',
							maxLength : 50,
							width : 140
						}]
					},{
						columnWidth:.5,		
						layout : 'form',
						items:[{
							xtype : 'textfield',
							fieldLabel : '网址',
							id : _this.addWinId + 'c_brandurl',
							name:'c_brandurl',
							maxLength : 100,
							width : 140
						}]
					}]
				},{
					layout:'column',
					border :false,
					items:[{
						columnWidth:.5,		
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '品牌商城',
							id : _this.addWinId + 'c_brandstore',
							name:'c_brandstore',
							maxLength : 100,
							width : 140
						}]
					},{
						columnWidth:.5,		
						layout : 'form',
						items:[{
							xtype : 'textfield',
							fieldLabel : '官方微博',
							id : _this.addWinId + 'c_weibo',
							name:'c_weibo',
							maxLength : 100,
							width : 140
						}]
					}]
				},{
					layout:'column',
					border :false,
					items:[{
						columnWidth:.5,		
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '总部地址',
							id : _this.addWinId + 'c_address',
							name:'c_address',
							maxLength : 70,
							width : 140
						}]
					},{
						columnWidth:.5,		
						layout : 'form',
						items:[{
							xtype : 'textfield',
							fieldLabel : '公司口号',
							id : _this.addWinId + 'c_slogan',
							name:'c_slogan',
							maxLength : 100,
							width : 140
						}]
					}]
				},{
					layout:'column',
					border :false,
					items:[{
						columnWidth:.5,		
						layout : 'form',
						items : [{
							xtype : 'textarea',
							fieldLabel : '简介',
							id : _this.addWinId + 'c_abstract',
							name:'c_abstract',
							minLength : 5,
							maxLength : 1000,
							allowBlank : false,
							width : 140
						}]
					},{
						columnWidth:.5,		
						layout : 'form',
						items:[{
							xtype : 'textfield',
							fieldLabel : '排序',
							id : _this.addWinId + 'c_order',
							value:'0',
							name:'c_order',
							allowDecimals :false,
							maxLength : 5,
							width : 140
						},typeField]
					}]
					
				},{
					layout:'column',
					border :false,
					items:[{
						columnWidth:.5,		
						layout : 'form',
						items : [{
							xtype : 'field',
							inputType : 'file',
							fieldLabel : 'logoURL',
							id : _this.addWinId + 'c_logourl',
							name:'c_logourl',
							width : 200		
						}]
					},{
						columnWidth:.5,		
						layout : 'form',
						items:[{
							xtype : 'field',
							inputType : 'file',
							fieldLabel : '广告URL',
							id : _this.addWinId + 'c_advertisurl',
							name:'c_advertisurl',
							width : 200	
						}]
					}]
					
				},{
					layout : "column",
					border : false,
					items:[{
						columnWidth:.5,		
						layout : 'form',
						items : [{
							xtype : 'displayfield',
							//hidden : true,
							//value : "test1",
							id : _this.addWinId + 'c_logourl_show',
							name:'c_logourl_show',
							width : 200		
						}]
					},{
						columnWidth:.5,		
						layout : 'form',
						items:[{
							xtype : 'displayfield',
							//hidden : true,
							//value : "test2",
							id : _this.addWinId + 'c_advertisurl_show',
							name:'c_advertisurl_show',
							width : 200	
						}]
					}]
				},{
					layout:'column',
					border :false,
					items:[{
						columnWidth:.5,	
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '品牌拼音',
							id : _this.addWinId + 'c_pinyin',
							name:'c_pinyin',
							maxLength : 100,
							regex : /^[a-zA-Z][a-zA-Z0-9_.]*$/,
							regexText : '只能输入以字母开头的字母、数字._',
							allowBlank : false,
							width : 140
						}]
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
		            	_this.priSaveFun(addWin);
		            }
		        } ]
			});
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '品牌管理',
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 500, 
				layout : 'form',
				frame : true,
				items : [c_form]
			});
			
			addWin.show();
			
		},
		
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
               	{name:'C_ID'},
                {name:'C_CNAME'},
                {name:'P_NAME'},
                {name:'C_INDUSTRYID'},
                {name:'C_FOUNDTIME'},
                {name:'C_BRANDIDEA'},
                {name:'C_BRANDURL'},
                {name:'C_BUSINESSSCOPE'},
                {name:'C_ABSTRACT'},
                {name:'C_ORDER'},
                {name:'C_ISLIVE'},
                {name:'C_LOGOURL'},
                {name:'C_ADVERTISURL'},
                {name:'C_BRANDSTORE'},
                {name:'C_WEIBO'},
                {name:'C_ADDRESS'},
                {name:'C_KEYID'},
                {name:'C_SLOGAN'}
    		 ]);

    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/activityBrandInfo!getActivityBrandInfoList.action",
    				timeout : 60000
    			}),
    			reader : new Ext.data.JsonReader({
    				totalProperty : 'count',
    				root : 'list'
    			}, resultsRecord)
    		});
    		
    		//
    		var tempArray = [
               { header: "ID", hidden: true, dataIndex: 'C_ID',width:15},
               { header: "C_KEYID", hidden: true, dataIndex: 'C_KEYID',width:15},
               { header: "品牌名称", sortable: true, dataIndex: 'C_CNAME',width:40},
               { header: "行业类别", sortable: true, dataIndex: 'C_INDUSTRYID',width:60,
                   	renderer:function (value, meta, record){
            	   		if(!Ext.isEmpty(record.get('P_NAME'))){
            	   			return record.get('P_NAME');
            	   		}
            	        return value==0?'':value;
            	   }
               },
               { header: "成立时间", hidden: true, dataIndex: 'C_FOUNDTIME',width:40},
               { header: "品牌理念",  hidden: true, dataIndex: 'C_BRANDIDEA',width:40},
               { header: "网址", sortable: true, dataIndex: 'C_BRANDURL',width:40},
               { header: "经营范围", hidden: true, dataIndex: 'C_BUSINESSSCOPE',width:40},
               { header: "简介",  hidden: true, dataIndex: 'C_ABSTRACT',width:40,
            	   renderer: function (value, meta, record) {
            	   	 if(value==null||value==""){
            	   		 return value;
            	   	 }else{
            	   		return value.toString().substr(0,20);	 
            	   	 }
            		 
               }
               },
               { header: "排序", hidden: true, dataIndex: 'C_ORDER',width:40},
               { header: "是否有效", sortable: true, dataIndex: 'C_ISLIVE',width:20,
            	   renderer: function (value, meta, record) {
            		   return value==0?'否':'是';
            	   }},
               { header: "logoURL", hidden: true, dataIndex: 'C_LOGOURL',width:40},
               { header: "广告url", hidden: true, dataIndex: 'C_ADVERTISURL',width:40},
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
    			cm : resultColumnModel,
    			viewConfig : {
    				forceFit : true,
    				autoWrapRow:true
    			},
    			width : mainWidth,
    			height : mainHeigth,
    			loadMask : { msg : LOADMASK_MSG },
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
				            url : rootPath+"basedata/activityBrandInfo!deleteActivityBrandInfo.action",
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
				            	alert('删除失败!');
				            }, 
				            params: delparams
				        });
    	                return true;
    	             }
    			}else if(record&&control=='modify'){
    				_this.priCreateWinFun();
    				//for(var t = Date.now();Date.now() - t <= 3000;); // 因为异步请求行业类别可能导致后面设置值失败，这边等几秒吧，不设置同步请求
    				
    				var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
    				
    				var data = record.data;
    				
    				//dirfrom.Ext_DATA = data;
    				dirfrom.Ext_cid = data.C_ID;
    				dirfrom.Ext_ckeyId = data.C_KEYID;
    				
    				dirfrom.findField('c_cname').setValue(data.C_CNAME);
    				dirfrom.findField('c_industryid').setValue(data.C_INDUSTRYID);
    			//	dirfrom.findField('c_industryid').setValue(data.C_INDUSTRYID);
    			//	dirfrom.findField('c_industryid').setRawValue(data.P_NAME);
    				dirfrom.findField('c_foundtime').setValue(data.C_FOUNDTIME);		
    				dirfrom.findField('c_brandidea').setValue(data.C_BRANDIDEA);
    				dirfrom.findField('c_brandurl').setValue(data.C_BRANDURL);
    				dirfrom.findField('c_businessscope').setValue(data.C_BUSINESSSCOPE);
    				dirfrom.findField('c_abstract').setValue(data.C_ABSTRACT);
    				dirfrom.findField('c_order').setValue(data.C_ORDER);
    				dirfrom.findField('c_islive').setValue(data.C_ISLIVE);
    				
    				dirfrom.findField('c_logourl_show').setValue(data.C_LOGOURL);
    				dirfrom.findField('c_advertisurl_show').setValue(data.C_ADVERTISURL);
    				//dirfrom.findField('c_logourl').setValue("test");
					//dirfrom.findField('c_advertisurl').getValue();
    				
    				dirfrom.findField('c_brandstore').setValue(data.C_BRANDSTORE);
    				dirfrom.findField('c_weibo').setValue(data.C_WEIBO);
    				dirfrom.findField('c_address').setValue(data.C_ADDRESS);
    				dirfrom.findField('c_slogan').setValue(data.C_SLOGAN);
    				if(Ext.isEmpty(dirfrom.Ext_ckeyId)){
    					//dirfrom.findField('c_pinyin').setValue("");
    					//return "";
    				}
    				Ext.Ajax.request({
			            url : rootPath+"basedata/activityBrandInfo!getBrandSearchDetail.action",
			            timeout : 120000,
			            success: function(result, obj){
			                var jsonFromServer = Ext.util.JSON.decode(result.responseText);
			                if (jsonFromServer.status=='Y') {
			                   var data = jsonFromServer.data;
			                   dirfrom.findField('c_pinyin').setValue(data.C_PINYIN);
			                }
			            },
			            failure: function(result, obj){
			            }, 
			            params: {id:dirfrom.Ext_ckeyId}
			        });
    					
    			}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		}
	}
};
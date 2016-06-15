Ext.advertInfo = function() {
	return {
		priData : {
			// 用户新增/修改窗口使用
			adType_edit : [// 0:'商城', 3:'礼包', 4:'活动',5:'游戏', 6:'H5游戏', 7:'资讯', 8:'攻略'
			          [0,'商城'], [3,'礼包'], [4,'活动'],
			          [5,'游戏'], [6,'H5游戏'], [7,'资讯'], [8,'攻略']
					 ],
			// 用于广告主页面使用
			adType : [// 脚标1、2位置的元素是为了让后面的元素的脚标和其type对应，以方便家页面加载时列表数据的渲染
			          [0,'商城'], [1,''], [2,''], [3,'礼包'], [4,'活动'],
			          [5,'游戏'], [6,'H5游戏'], [7,'资讯'], [8,'攻略']
					 ]
		},
		formId : 'advertInfo_formId',
		gridId : 'advertInfo_gridId',
		addWinId : 'advertInfo_addWinId',
		textwidth : 120,
		init : function(){
			
		},
		load : function(){
			//alert(11)
			var _this = this;
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
			
			// 选择广告类型之后联动查询类型对应的数据（游戏/商品/礼包等）
			_this.getItemStore = function(){
				//if(0!=type && !type){
				//	type = 0;
				//}
				var itemStores = new Ext.data.Store( {
					proxy : new Ext.data.HttpProxy( {
						url : rootPath + "basedata/advertInfo!getListByType.action",
						timeout : 60000 
					}),
					reader : new Ext.data.JsonReader( {
						root : 'list'
					}, Ext.data.Record.create( [ {
						name : 'C_NAME'
					}, {
						name : 'C_ID'
					} ]))
				});
				
				/*itemStores.load( {
					callback : function(obj) {
					} 
				});*/
				return itemStores;
			}

			var pnlMain = new Ext.form.FormPanel( {
				id : _this.formId + 'formPanel',
				autoWidth : true,
				autoHeight : true,
				border :false,
				//frame false,
				labelWidth : 80,
				items : [{
					layout : 'column',
					border :false,
					items : [
					{
						columnWidth : .25,
						layout : 'form',
						items : [nameField]
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
			
			_this.priQueryFun();
		},
		
		/**
		 * query function
		 * @returns
		 */
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
		/**
		 * save function
		 * @returns
		 */
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
			}else{
				if(Ext.isEmpty(dirfrom.findField('picurl').getValue())){
					alert('广告截图不能为空!');
					return;
				}
			}
			if(Ext.get(_this.addWinId + 'picurl').isExits){
				alert('广告截图重复!');
				return;
			}
			
			saveParam.c_name = dirfrom.findField('c_name').getValue();
			saveParam.c_type = dirfrom.findField('C_TYPE').getValue();
			saveParam.c_appid = dirfrom.findField('C_APPID').getValue();
			saveParam.c_order = dirfrom.findField('c_order').getValue();
			
			var params = {};
			params.saveParam = Ext.encode(saveParam);
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"basedata/advertInfo!saveAdvertIfo.action",
				waitMsg : 'save ing...',
				success : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					if(rsc.cId){
						dirfrom.Ext_cid = rsc.cId;
						alert('保存成功');
						var grid = Ext.getCmp(_this.gridId);
						grid.store.reload();
					}else{
						alert('保存失败');
					}
				},
				failure : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					alert('保存失败');
				},
				params : params
			});
			
		},
		/**
		 * create new win
		 * @returns
		 */
		priCreateWinFun : function(){
			var _this = this;
			
			var addWin = Ext.getCmp(_this.addWinId);
			if(addWin){
				addWin.show();
				return;
			}
			
			var app_Comb = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store :_this.getItemStore(),
				fieldLabel : '对应app',
				id : _this.addWinId + 'C_APPID',
				name : 'C_APPID',
				//value:'0',
				allowBlank : false,
				//editable : true,
				mode : 'local',
				displayField : 'C_NAME',
				valueField : 'C_ID',
				resizable : true,
				anchor : '90%',
				selectOnFocus : true
			});
			
			// 数据代理
			//var adTypeProxy = new Ext.data.MemoryProxy(_this.priData.adType);
			var adTypeProxy = new Ext.data.MemoryProxy(_this.priData.adType_edit);

			// 类型读取
			var adTypeReader = new Ext.data.ArrayReader(
				{},
				[
				 {name: 'typeId', type:'int', mapping:0},
                 {name: 'typeName', type:'string', mapping:1}
				]
			);
			
			// 读取数据
			var adTypeStore = new Ext.data.Store({
			    proxy : adTypeProxy,
			    reader : adTypeReader,
			    autoLoad : true //开启自动加载，一般情况下没有开启，属于延迟加载，也可以采用 adTypeStore.load()方法
			});
			
//			adTypeStore.load();
			
			// 定义下拉框
			var type_Comb = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : adTypeStore,
				fieldLabel : '广告类型',
				id : _this.addWinId + 'C_TYPE',
				name : 'C_TYPE',
				allowBlank : false,
				mode : 'local',
				displayField : 'typeName',
				valueField : 'typeId',
				resizable : true,
				anchor : '90%',
				selectOnFocus : true,
				listeners : {
					"select":function(combo, record, index){// 第一个参数是对象，第二个参数是所有数据
						Ext.getCmp(_this.addWinId + 'C_APPID').setValue('');
						Ext.getCmp(_this.addWinId + 'C_APPID').store.load({
							params :{type:combo.getValue()}
						});
					}
				}
			});
			
			
			// 检查是否重复公共方法
			function checkExits(appType,checkType, value,idVal) {
				if(Ext.isEmpty(idVal)){
					idVal = '';
				}
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", rootPath+"basedata/advertInfo!checkAdvertIfo.action", false);// false表示同步调用
				conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				conn.send("appType="+appType+"&checkType="+checkType+"&value="+value+"&idVal="+idVal);//
				var data = Ext.util.JSON.decode(conn.responseText);
				return data;
			}
			
			// 校验游戏id是否存在
			/*function checkGameIdExits(appType,checkType, value,idVal) {
				if(Ext.isEmpty(idVal)){
					idVal = '';
				}
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", rootPath+"basedata/appInfo!checkAppIfo.action", false);// false表示同步调用
				conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				conn.send("appType="+appType+"&checkType="+checkType+"&value="+value+"&idVal="+idVal);//
				var data = Ext.util.JSON.decode(conn.responseText);
				return data;
			}*/
			
			var c_form = new Ext.form.FormPanel({
				id : _this.addWinId + 'addForm',
				frame : true,
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
				//width : 720,
				//height : 400,
				labelWidth : 60,
				layout : 'form',
				fileUpload : true,
				items : [{
					layout : 'column',
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '广告名称',
							id : _this.addWinId + 'c_name',
							name : 'c_name',
							allowBlank : false,
							maxLength : 20,
							width : 140,
							validationDelay : 500,
							validator : function(value) {
						        //var length = value.replace(/[^\x00-\xff]/g, "xx").length;  
						        /*if(length>1){  
						            return '长度不能超过1000个字节，一个汉字字符为两个字节！';  
						        }*/  
						        var data = checkExits("","c_name", value, Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);
								if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
									return "广告名称重复";
								}
								return true;
						    }
						}]
					}
					,{
						columnWidth : .5,
						layout : 'form',
						items : [type_Comb]	
					}
//					,{
//						columnWidth : .25,
//						layout : 'form',
//						items : [{
//							xtype : 'numberfield',
//							fieldLabel : '游戏ID',
//							id : _this.addWinId + 'c_appid',
//							name : 'c_appid',
//							allowBlank : true,
//							maxLength : 10,
//							width : 100,
//							validationDelay : 500,
//							validator : function(value) {
//						        var data = checkGameIdExits("","c_id", value, "");
//								if (!Ext.isEmpty(data.isExits) && data.isExits == '0') {
//									return "游戏id不存在";
//								}
//								return true;
//						    }
//						}]	
//					}
					]},
					
				{
					layout : 'column',
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'numberfield',
							fieldLabel : '排序',
							id : _this.addWinId + 'c_order',
							name : 'c_order',
							allowBlank : false,
							allowDecimals :false,
							maxLength : 5,
							width : 140
						}]
					}
					,{
						columnWidth : .5,
						layout : 'form',
						items : [app_Comb]
					}
					]
				},{
					layout : 'column',
					items : [{
						//columnWidth : .9,
						//labelWidth : 80,
						layout : 'form',
						items : [{
							xtype : 'field',
							inputType : 'file',
							fieldLabel : '广告截图',
							id : _this.addWinId + 'picurl',
							name : 'picurl',
							allowBlank : false,
							maxLength : 20,
							width : 400
						}]
					}]
				},{
					layout : 'column',
					height : 40,
					scrollable : true,
					columnWidth : .9,
					items : [{
						xtype : 'label',
						id : _this.addWinId + 'oldPicLable',
						text : ''
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
				title: '广告位管理',
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 580, 
	            //height:500,	
				layout : 'form',
				frame : true,
				items : [c_form]
			});
			
			addWin.show();
		},
		/**
		 * create grid
		 * @returns Grid
		 */
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
                {name : 'C_ID'},
                {name : 'C_NAME'},
                {name : 'C_APPID'},
                {name : 'C_APPNAME'},
                {name : 'C_TYPE'},
                {name : 'C_ORDER'},
                {name : 'C_PICURL'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/advertInfo!getAdvertIfoList.action",
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
    		//var resultsSm = new Ext.grid.CheckboxSelectionModel();
    		var tempArray = [
               { header: "ID", hidden: false, dataIndex: 'C_ID',width:32},
               { header: "C_APPID", hidden: true, dataIndex: 'C_APPID'},
               { header: "广告类型", sortable: true, dataIndex: 'C_TYPE',width:32,
            	   renderer: function (value, meta, record) {
            		   if(Ext.isEmpty(value)){
            			   return value;
            		   }
            		   return _this.priData.adType[value][1];
            	   }
               },
               { header: "广告名称", sortable: true, dataIndex: 'C_NAME',width:32},
               { header: "对应app", sortable: true, dataIndex: 'C_APPNAME',width:30},
               { header: "图片路径", sortable: true, dataIndex: 'C_PICURL',width:60,
            	   renderer: function (value, meta, record) {
            		   if(Ext.isEmpty(value)){
            			   return value;
            		   }
            		   return value.substring(value.lastIndexOf('/')+1);
            	   }
               },
               { header: "排序", sortable: true, dataIndex: 'C_ORDER',width:15},
               { header: "操作", sortable: false, dataIndex: '',width:20,align:'center',
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
    			//frame true,
				//plain:true,
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
				            url : rootPath+"basedata/advertInfo!deleteAdvertIfo.action",
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
    				dirfrom.Ext_DATA = data;
    				dirfrom.Ext_cid = data.C_ID;
    				dirfrom.findField('c_name').setValue(data.C_NAME);
    				dirfrom.findField('c_order').setValue(data.C_ORDER);
    				dirfrom.findField('C_TYPE').setValue(data.C_TYPE);
    				dirfrom.findField('C_TYPE').setRawValue(_this.priData.adType[data.C_TYPE][1]);

					Ext.getCmp(_this.addWinId + 'C_APPID').store.load({
						params :{type : data.C_TYPE},
						callback: function(obj){
							dirfrom.findField('C_APPID').setValue(data.C_APPID);
							dirfrom.findField('C_APPID').setRawValue(data.C_APPNAME);
						}
					});

    				var picUrl = data.C_PICURL;
    				if(!Ext.isEmpty(picUrl)){
    					var picUrlArr = picUrl.split(',');
    					var picStr = "";
    					for(var i=0;i<picUrlArr.length;i++){
    						if(Ext.isEmpty(picUrlArr[i])){
    							continue;
    						}
    						picStr += picUrlArr[i].substring(picUrlArr[i].lastIndexOf('/')+1)+"</br>";
    					}
    					Ext.getCmp(_this.addWinId + 'oldPicLable').setText(picStr,false);
    				}
    				
    			}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		}
	}
};
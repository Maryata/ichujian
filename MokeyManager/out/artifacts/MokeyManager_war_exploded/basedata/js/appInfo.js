Ext.appInfo = function() {
	return {
		priData : {
			categoryData : [{key:'0',value:'新闻app'},{key:'1',value:'阅读app'},{key:'2',value:'游戏app'}],//app类型
			typeData : [{key:'2',value:'热门'},{key:'3',value:'精选'}], //推荐类型//,{key:'2',value:'精品+新品'}
			levelData: [{key:'1',value:'1'},
			            {key:'2',value:'2'},
			            {key:'3',value:'3'},
			            {key:'4',value:'4'},
			            {key:'5',value:'5'},]
		},
		formId : 'appInfo_formId',
		gridId : 'appInfo_gridId',
		addWinId : 'appInfo_addWinId',
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
			
			// 游戏分类的下拉的菜单
			_this.getGameCategory = function(){
				var gameCategory = new Ext.data.Store( {
					proxy : new Ext.data.HttpProxy( {
						url : rootPath + "basedata/ekey/eKGameCategoryAction!getGameCategoryList.action",
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
				
				gameCategory.load( {
					callback : function(obj) {
						// 在回调中，将查询到的游戏分类存入_this.priData.typeData这个数组中
						for(i=0;i<obj.length;i++){
							// 定义map
							var map = {};
							// 分类id作为key
							map['key']=obj[i].json.C_ID;
							// 分类名称作为value
							map['value']=obj[i].json.C_NAME;
							
							// 每一对key-value作为数组的一个元素
							_this.priData.typeData[i] = map;
						}
					} 
				});
				return gameCategory;
			}
			_this.gameCategory = _this.getGameCategory();
			
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
		 * 创建ComboBox
		 * @param option
		 * @returns ComboBox
		 */
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
		
		/**
		 * query function
		 * @returns
		 */
		priQueryFun : function(){
			var _this = this;
			
			var gridStore = Ext.getCmp(_this.gridId).store;
			//gridStore.removeAll();
			var paramJson = {};
			
//			paramJson.c_category = Ext.getCmp(_this.formId +'c_category').getValue();
//	    	paramJson.c_type = Ext.getCmp(_this.formId +'c_type').getValue();
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
				saveParam.C_LOGOURL = dirfrom.C_LOGOURL;
				saveParam.C_APPURL = dirfrom.C_APPURL;
			}else{
				if(Ext.isEmpty(dirfrom.findField('logourl').getValue())){
					alert('logo路径不能为空!');
					return;
				}
				if(Ext.isEmpty(dirfrom.findField('picurl').getValue())){
					alert('app应用截图不能为空!');
					return;
				}
				// 2015-9-8 修改：由于加入“原始下载地址”，所以上传文件的非空校验取消，可以不上传
				/*
				if(Ext.isEmpty(dirfrom.findField('appurl').getValue())){
					alert('apk路径不能为空!');
					return;
				}
				 */
			}
			if(Ext.get(_this.addWinId + 'logourl').isExits){
				alert('logo路径重复!');
				return;
			}
			if(Ext.get(_this.addWinId + 'appurl').isExits){
				alert('apk路径重复!');
				return;
			}
			if(Ext.get(_this.addWinId + 'picurl').isExits){
				alert('app应用截图重复!');
				return;
			}
			saveParam.c_name = dirfrom.findField('c_name').getValue();
			saveParam.c_manu = dirfrom.findField('c_manu').getValue();
			saveParam.c_size = dirfrom.findField('c_size').getValue();
			
			saveParam.c_type = dirfrom.findField('c_type').getValue();// v_1.4 新增“游戏分类”
			saveParam.c_source = dirfrom.findField('c_source').getValue();// v_1.4 新增“游戏原下载地址”
			
			saveParam.c_version = dirfrom.findField('c_version').getValue();
			//saveParam.c_order = dirfrom.findField('c_order').getValue();
			saveParam.c_jarname = dirfrom.findField('c_jarname').getValue();
			saveParam.c_abstract = dirfrom.findField('c_abstract').getValue();
			saveParam.c_title = dirfrom.findField('c_title').getValue();
//			saveParam.c_level = dirfrom.findField('c_level').getValue();// 新增：初始评分
//			saveParam.c_download = dirfrom.findField('c_download').getValue();// 新增：初始下载量
			
			saveParam.c_state = dirfrom.findField('c_state').getValue();// 新增：游戏状态
			saveParam.c_phone = dirfrom.findField('c_phone').getValue();// 新增：客服电话
			
			
			var params = {};
			params.saveParam = Ext.encode(saveParam);
			if(dirfrom.Ext_PICURL){
				if(!Ext.isEmpty(dirfrom.Ext_PICURL)){
					params.oldPicurl = dirfrom.Ext_PICURL;
				}
			}
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"basedata/appInfo!saveAppIfo.action",
				waitMsg : 'save ing...',
				success : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					if(rsc.cId){
						dirfrom.Ext_cid = rsc.cId;
						dirfrom.Ext_PICURL = rsc.picNames;
						loadImgDivForApp();
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
			
			var categoryField = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : _this.gameCategory,
				fieldLabel : '游戏分类',
				id : _this.addWinId + 'c_type',
				name : 'c_type',
				allowBlank : false,
				mode : 'local',
				displayField : 'C_NAME',
				valueField : 'C_ID',
				resizable : true,
				anchor : '90%',
				selectOnFocus : true
			});
			
			// 检查是否重复公共方法
			function checkExits(appType,checkType, value,idVal) {
				if(Ext.isEmpty(idVal)){
					idVal = '';
				}
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", rootPath+"basedata/appInfo!checkAppIfo.action", false);// false表示同步调用
				conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				conn.send("appType="+appType+"&checkType="+checkType+"&value="+value+"&idVal="+idVal);//
				var data = Ext.util.JSON.decode(conn.responseText);
				return data;
			}
			
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
						columnWidth : .45,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : 'app名称',
							id : _this.addWinId + 'c_name',
							name : 'c_name',
							allowBlank : false,
							maxLength : 30,
							width : 200,
							validationDelay : 500,
							validator : function(value) {
						        //var length = value.replace(/[^\x00-\xff]/g, "xx").length;  
						        /*if(length>1){  
						            return '长度不能超过1000个字节，一个汉字字符为两个字节！';  
						        }*/  
						        var data = checkExits("","c_name", value, Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);
								if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
									return "app名称重复";
								}
								return true;
						    }
						}]
					}, {
						columnWidth : .55,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '厂 商',
							id : _this.addWinId + 'c_manu',
							name : 'c_manu',
							allowBlank : true,
							maxLength : 30,
							width : 300
						}]
					}
					]
				},{
					layout : 'column',
					items : [{
						columnWidth : .45,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '版本',
							id : _this.addWinId + 'c_version',
							name : 'c_version',
							allowBlank : false,
							maxLength : 30,
							width : 200
						}]
					}, 
					{
						columnWidth : .55,
						layout : 'form',
						items : [{
							xtype : 'textfield',//htmleditor
							fieldLabel : 'jar包名',
							id : _this.addWinId + 'c_jarname',
							name : 'c_jarname',
							regex : /^[0-9a-zA-Z._]+$/,
							regexText : '只能输入字母和.',
							disabled :false,
							allowBlank : true,
							maxLength :100,
							width : 300
						}]
					}
				]},
				{
					layout : 'column',
					items : [{
						columnWidth : .45,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '客服电话',
							id : _this.addWinId + 'c_phone',
							name : 'c_phone',
							allowBlank : true,
							maxLength : 30,
							width : 200
						}]
					}, 
					{
						columnWidth : .55,
						layout : 'form',
						items : [{
							xtype : 'textfield',//htmleditor
							fieldLabel : '大小(b)',
							id : _this.addWinId + 'c_size',
							name : 'c_size',
							regex : /^[0-9]+$/,
							regexText : '只能输入数字',
							disabled :false,
							allowBlank : true,
							maxLength :100,
							width : 300
						}]
					}
				]},
				{
					layout : 'column',
					items : [{
						columnWidth : .45,
						layout : 'form',
						items : [categoryField]
					},
					{
						columnWidth : .55,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '原链接',
							id : _this.addWinId + 'c_source',
							name : 'c_source',
							allowBlank : true,
							width : 300,
							maxLength : 1024
	//						anchor : '99%'
						}]
					}]
				},{
					layout : 'column',
					items : [{
						columnWidth : .98,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '亮点',
							id : _this.addWinId + 'c_title',
							name : 'c_title',
							allowBlank : true,
							maxLength : 100,
							anchor : '99%'
						}]
					}]
				},
				{
					layout : 'column',
					items : [{
						columnWidth : .98,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '游戏状态',
							id : _this.addWinId + 'c_state',
							name : 'c_state',
							allowBlank : true,
							maxLength : 100,
							anchor : '99%'
						}]
					}]
				},
				{
					layout : 'column',
					items : [{
						layout : 'form',
						items : [{
							xtype : 'textarea',//htmleditor
							fieldLabel : 'app简介',
							id : _this.addWinId + 'c_abstract',
							name : 'c_abstract',
							autoScroll:true,
							disabled :false,
							allowBlank : false,
							maxLength :680,
							height:70,
							width:630,
							anchor : '99%'
						}]
					}]
				},
				{
					layout : 'column',
					items : [{
						columnWidth : .5,
						layout : 'form',
						labelWidth : 80,
						items : [{
							xtype : 'field',
							inputType : 'file',
							fieldLabel : 'logo路径',
							id : _this.addWinId + 'logourl',
							name : 'logourl',
							allowBlank : false,
							maxLength : 200,
							width : 250
							/*listeners : {
					            change : function(field, newValue, oldValue)
					            {
					                //var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/
					                //var url = 'file:///' + Ext.get(_this.addWinId + 'logourl').dom.value;
					                Ext.MessageBox.alert("kkk", Ext.get(_this.addWinId + 'logourl').dom);
					                if (img_reg.test(url))
					                {
					                    if (Ext.isIE7)
					                    {
					                        //var image = Ext.get('file_imageBrowse').dom;
					                       // image.src = Ext.BLANK_IMAGE_URL;// 覆盖原来的图片
					                       // image.filters/.item("DXImageTransform.Microsoft.AlphaImageLoader").src = url;
					                    }
					                    else
					                    {
					                        //Ext.get('file_imageBrowse').dom.src = getFullPath(Ext/.get('src_photoName').dom);
					                    }
					                }
					            }
					        }*/
						
					}, {
						columnWidth : .5,
						layout : 'form',
						labelWidth : 80,
						items : [{
							xtype : 'field',
							inputType : 'file',
							fieldLabel : 'apk路径',
							id : _this.addWinId + 'appurl',
							name : 'appurl',
							allowBlank : false,
							maxLength : 200,
							width : 250
						}]
					}]
				}]
				},{
					layout : 'column',
					items : [{
						columnWidth : .8,
						labelWidth : 80,
						layout : 'form',
						items : [{
							xtype : 'field',
							inputType : 'file',
							fieldLabel : 'app应用截图',
							id : _this.addWinId + 'picurl',
							name : 'picurl',
							allowBlank : false,
							maxLength : 500,
							width : 450,
							autoCreate : {  
					            tag : "input",  
					            type : "file",  
					            size : "400",  
					            multiple : "multiple",
					            autocomplete : "off"
					            //onChange : "browseImages(this.value);" 
					        }
						}]
					}]
				},{
					layout : 'column',
					height : 120,
					//scrollable : false,
					columnWidth : .9,
					items : [{
						xtype : 'label',
						id : _this.addWinId + 'oldPicLable',
						text : ''
					}]
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
		        }]
			});
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: 'app管理',
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 740, 
	            //height:500,	
				layout : 'form',
				frame : true,
				items : [c_form]
			});
			
			addWin.show();
			
			/*Ext.get(_this.addWinId + 'logourl').on('change',function(field, newValue, oldValue){
				var thisFile = this;
				var value = newValue.value;
				if(Ext.isEmpty(value)){
					thisFile.isExits = false;
					return;
				}
				value = value.substring(value.lastIndexOf('\\')+1);
				var data = checkExits(Ext.getCmp(_this.addWinId + 'c_category').getValue(),"c_logourl", value, Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);
				if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
					alert("logo["+value+"]重复!");
					thisFile.isExits = true;
				}else{
					thisFile.isExits = false;
				}
			});*/
			
			Ext.get(_this.addWinId + 'appurl').on('change',function(field, newValue, oldValue){
				var thisFile = this;
				var value = newValue.value;
				if(Ext.isEmpty(value)){
					thisFile.isExits = false;
					return;
				}
				value = value.substring(value.lastIndexOf('\\')+1);
				var data = checkExits(Ext.getCmp(_this.addWinId + 'c_category').getValue(),"c_appurl", value, Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);
				if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
					alert("apk["+value+"]重复!");
					thisFile.isExits = true;
				}else{
					thisFile.isExits = false;
				}
			});
			
			/*Ext.get(_this.addWinId + 'picurl').on('change',function(field, newValue, oldValue){
				var thisFile = this;
				var value = newValue.value;
				if(Ext.isEmpty(value)){
					thisFile.isExits = false;
					return;
				}
				value = value.substring(value.lastIndexOf('\\')+1);
				var data = checkExits(Ext.getCmp(_this.addWinId + 'c_category').getValue(),"c_picurl", value, Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);
				if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
					alert("app应用截图["+value+"]重复!");
					//Ext.get(_this.addWinId + 'picurl').dom.value='';
					thisFile.isExits = true;
				}else{
					thisFile.isExits = false;
				}
			});*/
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
                {name : 'C_MANU'},
                {name : 'C_SIZE'},
                {name : 'C_LOGOURL'},
                {name : 'C_APPURL'},
                {name : 'C_JARNAME'},
                {name : 'C_ABSTRACT'},
                {name : 'C_TYPE'},// v_1.4 新增“游戏分类”
                {name : 'C_SOURCE'},// v_1.4 新增“原下载地址”
                {name : 'C_VERSION'},
                {name : 'C_PUBLISH_DATE'},
                {name : 'C_ISLIVE'},
//                {name : 'C_LEVEL'},
//                {name : 'C_DOWNLOAD'},
                {name : 'C_TITLE'},
                {name : 'C_PICURL'},
                {name : 'C_STATE'},
                {name : 'C_PHONE'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/appInfo!getAppIfoList.action",
    				timeout : 60000
    			}),
    			reader : new Ext.data.JsonReader({
    				totalProperty : 'count',
    				root : 'list'
    			}, resultsRecord)
    		});
    		
    		//
    		//var resultsSm = new Ext.grid.CheckboxSelectionModel();
    		var tempArray = [
               { header: "C_ID", sortable: true, dataIndex: 'C_ID',width:10},
               { header: "C_TITLE", hidden: true, dataIndex: 'C_TITLE',width:10},
               { header: "C_SOURCE", hidden: true, dataIndex: 'C_SOURCE',width:10},// v_1.4 新增“原下载地址”
               { header: "C_STATE", hidden: true, dataIndex: 'C_STATE',width:10},
               { header: "C_PHONE", hidden: true, dataIndex: 'C_PHONE',width:10},
               { header: "app名称", sortable: true, dataIndex: 'C_NAME',width:10},
               { header: "厂商", hidden: true, dataIndex: 'C_MANU',width:10},
               { header: "大小", sortable: true, dataIndex: 'C_SIZE',width:10},
               { header: "logo名称", sortable: true, dataIndex: 'C_LOGOURL',width:10,
            	   renderer: function (value, meta, record) {
            		   if(Ext.isEmpty(value)){
            			   return value;
            		   }
            		   return value.substring(value.lastIndexOf('/')+1);
            	   }
               },
               { header: "apk名称", sortable: true, dataIndex: 'C_APPURL',width:30,
            	   renderer: function (value, meta, record) {
            		   if(Ext.isEmpty(value)){
            			   return value;
            		   }
            		   return value.substring(value.lastIndexOf('/')+1);
            	   }   
               },
               { header: "jar包名称", sortable: true, dataIndex: 'C_JARNAME',width:25},
               // v_1.4 新增“游戏类型”
               { header: "类型", sortable: true, dataIndex: 'C_TYPE',width:15
            	   ,renderer: function (value, meta, record) {
            		   if(Ext.isEmpty(value)){
            			   return value;
            		   }
	        		   for(var i=0;i<_this.priData.typeData.length;i++){
	        			   if(_this.priData.typeData[i].key==value){
	        				   return _this.priData.typeData[i].value;
	        			   }
	        		   }
	        		   return value;
            	   }
               },
               { header: "版本", sortable: true, dataIndex: 'C_VERSION',width:13},
//               { header: "评分", sortable: true, dataIndex: 'C_LEVEL',width:10},
//               { header: "下载量", sortable: true, dataIndex: 'C_DOWNLOAD',width:13},
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
    			bodyStyle : 'width:100%;border:1px solid #99bbe8;',
    			//sm : resultsSm,
    			cm : resultColumnModel,
    			viewConfig : {
    				forceFit : true
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
				            url : rootPath+"basedata/appInfo!deleteAppIfo.action",
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
    				dirfrom.Ext_PICURL = data.C_PICURL;
    				
    				dirfrom.C_LOGOURL = data.C_LOGOURL;
    				dirfrom.C_APPURL = data.C_APPURL;
    				
    				dirfrom.findField('c_name').setValue(data.C_NAME);
    				dirfrom.findField('c_manu').setValue(data.C_MANU);
    				dirfrom.findField('c_size').setValue(data.C_SIZE);
    				
    				dirfrom.findField('c_version').setValue(data.C_VERSION);
//    				dirfrom.findField('c_order').setValue(data.C_ORDER);
    				dirfrom.findField('c_jarname').setValue(data.C_JARNAME);
    				dirfrom.findField('c_abstract').setValue(data.C_ABSTRACT);
    				
//    				dirfrom.findField('c_level').setValue(data.C_LEVEL);// 新加app评分
//    				dirfrom.findField('c_download').setValue(data.C_DOWNLOAD);// 新加app初始下载量
    				dirfrom.findField('c_title').setValue(data.C_TITLE);// 新加app卖点
    				
    				dirfrom.findField('c_state').setValue(data.C_STATE);// 新加游戏状态
    				dirfrom.findField('c_phone').setValue(data.C_PHONE);// 新加客服电话
    				
    				dirfrom.findField('c_type').setValue(data.C_TYPE);// v_1.4 新增“游戏分类”
    				dirfrom.findField('c_source').setValue(data.C_SOURCE);// v_1.4 新增“原下载地址”
    				
    				//alert(11);
    				loadImgDivForApp(_this.addWinId)
    			}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		}
	}
};

//browse
function loadImgDivForApp(){
	var addWinId = 'appInfo_addWinId';
	var dirfrom = Ext.getCmp(addWinId + 'addForm').getForm();
	var picUrl = dirfrom.Ext_PICURL;
	
	var picStr = "";
	if(!Ext.isEmpty(picUrl)){
		var picUrlArr = picUrl.split(',');
		picStr +='<div style=" margin:0px auto 0 auto;width:680px;height:110px;overflow:auto;"> '
			+'<table style="font-size:12px;height:10px;"> ';
		var index = 1;
		for(var i=0;i<picUrlArr.length;i++){
			if(Ext.isEmpty(picUrlArr[i])){
				continue;
			}
			picStr +=' <tr > ';
			picStr +=' <td width="20px">'+(index++)+' </td>';
			picStr +=' <td >'+picUrlArr[i].substring(picUrlArr[i].lastIndexOf('/')+1);
			picStr +=' &nbsp;&nbsp;</td> ';
			picStr +=' <td> <a href="javascript:delImagesForApp('+i+')">X</a>';
			picStr +=' </td> ';
			picStr +=' </tr> ';
		}
		picStr +=' </table> </div>';
	}
	Ext.getCmp(addWinId + 'oldPicLable').setText(picStr,false);
}

function delImagesForApp(v){
	var addWinId = 'appInfo_addWinId';
	var dirfrom = Ext.getCmp(addWinId + 'addForm').getForm();
	var picUrlArr = dirfrom.Ext_PICURL.split(',');
	if(window.confirm('是否删除图片['+picUrlArr[v].substring(picUrlArr[v].lastIndexOf('/')+1)+']？')){
		picUrlArr.removeByIndex(v);
		dirfrom.Ext_PICURL = picUrlArr.toString();
		loadImgDivForApp();
	}
}
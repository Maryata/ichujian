Ext.userInfoManager = function() {
	return {
		priData : {
			sexData : [{key:'M',value:'男'},{key:'F',value:'女'}],
			statusData : [{key:'1',value:'有效'},{key:'0',value:'无效'}]
		},
		formId : 'userInfoManager_formId',
		gridId : 'userInfoManager_gridId',
		addWinId : 'userInfoManager_addWinId',
		textwidth : 120,
		init : function(){
		},
		load : function(){
			var _this = this;
			
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
					items : [{
						columnWidth : .25,
						layout : 'form',
						items : [new Ext.form.TextField({
							id: _this.formId+'C_USERCODE',
							xtype : 'textfield',
							fieldLabel : '账号',
							name : 'C_USERCODE',
							width : _this.textwidth,
							readOnly : false,
							maxLength : 50,
							allowBlank : 'true',
							listeners : {
								focus : function() {
								}
							}
						}) ]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [new Ext.form.TextField({
							id: _this.formId+'C_USERNAME',
							xtype : 'textfield',
							fieldLabel : '姓名',
							name : 'C_USERNAME',
							width : _this.textwidth,
							readOnly : false,
							maxLength : 50,
							allowBlank : 'true',
							listeners : {
								focus : function() {
								}
							}
						}) ]
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
			
			//load role
			_this.supplierStore = new Ext.data.Store({
				autoLoad: true,
				proxy : new Ext.data.HttpProxy({
					url : rootPath+"system/sysUser!getSuppliers.action",
					timeout : 60000
				}),
				reader : new Ext.data.JsonReader({
					root : 'list'
				},
				Ext.data.Record.create([
					{ name : 'C_ID'}, 
					{ name : 'C_SUPPLIER_NAME'}
				]))
			});
			//
			_this.roleStore = new Ext.data.Store({
				autoLoad: true,
				proxy : new Ext.data.HttpProxy({
					url : rootPath+"system/sysUser!getUserRoles.action",
					timeout : 60000
				}),
				reader : new Ext.data.JsonReader({
					root : 'list'
				},
				Ext.data.Record.create([
					{ name : 'C_ID'}, 
					{ name : 'C_ROLE_NAME'}
				]))
			});
			
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
				allowBlank : option.isNoBlank ? false : true, 
				anchor : '90%',
				//readOnly:true,
				width : option.width ? option.width : _this.textwidth
			};
			if(option.value){
				config.value = option.value;
			}
			return new Ext.form.ComboBox(config);
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
			
	    	paramJson.c_usercode = Ext.getCmp(_this.formId +'C_USERCODE').getValue();
	    	paramJson.c_username = Ext.getCmp(_this.formId +'C_USERNAME').getValue();
	    	
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
				saveParam.C_USERID = dirfrom.Ext_cid;
			}
			
			saveParam.C_USERCODE = dirfrom.findField('C_USERCODE').getValue();
			saveParam.C_USERNAME = dirfrom.findField('C_USERNAME').getValue();
			saveParam.C_SEX = dirfrom.findField('C_SEX').getValue();
			if(!saveParam.C_SEX){
				saveParam.C_SEX = '';
			}
			saveParam.C_PASSWORD = dirfrom.findField('C_PASSWORD').getValue();
			
			saveParam.C_USER_MOBILE = dirfrom.findField('C_USER_MOBILE').getValue();
			saveParam.C_EMAIL = dirfrom.findField('C_EMAIL').getValue();
			saveParam.C_BIRTHDAY = dirfrom.findField('C_BIRTHDAY').getRawValue();
			saveParam.C_POST = dirfrom.findField('C_POST').getValue();
			saveParam.C_VALIDITY = dirfrom.findField('C_VALIDITY').getRawValue();
			saveParam.C_USER_STATUS = dirfrom.findField('C_USER_STATUS').getValue();
			
			saveParam.C_HOME_TEL = dirfrom.findField('C_HOME_TEL').getValue();
			saveParam.C_HOME_ADDRESS = dirfrom.findField('C_HOME_ADDRESS').getValue();
			saveParam.C_SUPPLIER_ID = dirfrom.findField('C_SUPPLIER_ID').getValue();
			
			//用户角色
			saveParam.USER_ROLE = dirfrom.findField('USER_ROLE').getValue();
			
			var params = {};
			params.saveParam = Ext.encode(saveParam);
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"system/sysUser!saveUserIfo.action",
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
		priCreateWinFun : function(data){
			var _this = this;
			
			var addWin = Ext.getCmp(_this.addWinId);
			if(addWin){
				addWin.show();
				return;
			}
			
			var sexField = _this.createComVarField({fieldLabel:"性别",id: _this.addWinId + 'C_SEX', name:'C_SEX' , data:_this.priData.sexData , width: 100,isNoBlank:false });
			var statusField = _this.createComVarField({fieldLabel:"状态",id: _this.addWinId + 'C_USER_STATUS',name:'C_USER_STATUS' , data:_this.priData.statusData , width: 100,isNoBlank:true,value:'1'});
			
			

			/*if(data){
				supplierStore.load({
					callback: function(obj){
						c_form.getForm().findField('C_SUPPLIER_ID').setValue(data.C_SUPPLIER_ID);
					}
				});	
			}*/
			
			// 检查是否重复公共方法
			function checkExits(checkType, value,idVal) {
				if(Ext.isEmpty(idVal)){
					idVal = '';
				}
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", rootPath+"system/sysUser!checkUserInfo.action", false);// false表示同步调用
				conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				conn.send("checkType="+checkType+"&value="+value+"&id="+idVal);//
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
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '账号',
							id : _this.addWinId + 'C_USERCODE',
							name : 'C_USERCODE',
							allowBlank : false,
							//regex : /^[0-9a-zA-Z_]+$/,
							//regexText : '只能输入字母、数字',
							maxLength : 30,
							width : 100,
							anchor : '90%',
							validationDelay : 1000,
							validator : function(value) {
						        //var length = value.replace(/[^\x00-\xff]/g, "xx").length;  
						        /*if(length>1){  
						            return '长度不能超过1000个字节，一个汉字字符为两个字节！';  
						        }*/
								if(!/^[0-9a-zA-Z_]+$/.test(value)){
									return '只能输入字母、数字';
								}
						        var data = checkExits("C_USERCODE", value, Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);
								if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
									return "账号重复";
								}
								return true;
						    }
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '姓名',
							id : _this.addWinId + 'C_USERNAME',
							name : 'C_USERNAME',
							allowBlank : false,
							maxLength : 30,
							width : 270,
							anchor : '90%',
							validationDelay : 1000,
							validator : function(value) {
						        //var length = value.replace(/[^\x00-\xff]/g, "xx").length;  
						        /*if(length>1){  
						            return '长度不能超过1000个字节，一个汉字字符为两个字节！';  
						        }*/  
						        var data = checkExits("C_USERCODE", value, Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);
								if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
									return "姓名重复";
								}
								return true;
						    }
						}]
					}]
				},{
					layout : 'column',
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [sexField]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',//
							inputType : 'password',							
							fieldLabel : '密码',
							id : _this.addWinId + 'C_PASSWORD',
							name : 'C_PASSWORD',
							regex : REGEX_SYS_PASSWORD,
							regexText : '请输入长度为6到20位的数字、字母密码',
							disabled :false,
							allowBlank : false,
							maxLength :100,
							anchor : '90%'
						}]
					}]
				},{
					layout : 'column',
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',//						
							fieldLabel : '手机',
							id : _this.addWinId + 'C_USER_MOBILE',
							name : 'C_USER_MOBILE',
							//regex : /^[0-9a-zA-Z.]+$/,
							//regexText : '只能输入字母和.',
							disabled :false,
							allowBlank : true,
							maxLength :20,
							anchor : '90%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',//
							vtype : 'email',
							fieldLabel : '邮箱',
							id : _this.addWinId + 'C_EMAIL',
							name : 'C_EMAIL',
							//regex : /^[0-9a-zA-Z.]+$/,
							//regexText : '只能输入字母和.',
							disabled :false,
							allowBlank : true,
							maxLength :100,
							anchor : '90%'
						}]
					}]
				},{
					layout : 'column',
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'datefield',//
							fieldLabel : '生日',
							id : _this.addWinId + 'C_BIRTHDAY',
							name : 'C_BIRTHDAY',
							format : 'Y-m-d',
							disabled :false,
							allowBlank : true,
							maxValue :new Date(),
							anchor : '90%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',//							
							fieldLabel : '邮编',
							id : _this.addWinId + 'C_POST',
							name : 'C_POST',
							disabled :false,
							allowBlank : true,
							maxLength :6,
							anchor : '90%'
						}]
					}]
				},{
					layout : 'column',
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'datefield',//
							fieldLabel : '有效期',
							id : _this.addWinId + 'C_VALIDITY',
							name : 'C_VALIDITY',
							format : 'Y-m-d',
							disabled :false,
							allowBlank : true,
							anchor : '90%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [statusField]
					}]
				},{
					layout : 'column',
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',//
							fieldLabel : '家庭电话',
							id : _this.addWinId + 'C_HOME_TEL',
							name : 'C_HOME_TEL',
							disabled :false,
							allowBlank : true,
							maxLength :20,
							anchor : '90%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : []
					}]
				},{
					layout : 'form',
					items : [{
						xtype : 'textfield',//						
						fieldLabel : '家庭地址',
						id : _this.addWinId + 'C_HOME_ADDRESS',
						name : 'C_HOME_ADDRESS',
						disabled :false,
						allowBlank : true,
						maxLength :100,
						anchor : '96%'
					}]
				},{
					layout : 'form',
					items : [{
						xtype : 'lovcombo',
						store : _this.supplierStore,
						mode : 'local',
						fieldLabel : '供应商',
						id : _this.addWinId + 'C_SUPPLIER_ID',
						name : 'C_SUPPLIER_ID',
						displayField : 'C_SUPPLIER_NAME',
					    valueField : 'C_ID',
					    triggerAction: 'all',
						autoScroll:true,
						resizable :true,
						allowBlank : true,
						selectOnFocus:true,
						anchor : '96%'
					}]
				},{
					layout : 'form',
					items : [{
						xtype : 'lovcombo',
						store : _this.roleStore,
						mode : 'local',
						fieldLabel : '角色',
						id : _this.addWinId + 'USER_ROLE',
						name : 'USER_ROLE',
						displayField : 'C_ROLE_NAME',  
					    valueField : 'C_ID', 
						triggerAction: 'all',
						autoScroll:true,
						resizable :true,
						allowBlank : false,
						selectOnFocus:true,
						anchor : '96%'
					}]
				}/*,{
		            xtype: 'checkboxgroup',
		            fieldLabel : '角色',
		            //id : _this.addWinId + 'USER_ROLE_Group',
					name : 'USER_ROLE_Group',
		            itemCls: 'x-check-group-alt',
		            // Distribute controls across 3 even columns, filling each column
		            // from top to bottom before starting the next column
		            columns: 2,
		            vertical: true,
		            items: roleBoxs
		        }*/],
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
				title: '用户管理',
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
		
		/**
		 * create grid
		 * @returns Grid
		 */
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
                {name : 'C_USERID'},
                {name : 'C_USERCODE'},
                {name : 'C_USERNAME'},
                {name : 'C_PASSWORD'},
                {name : 'C_USER_MOBILE'},
                {name : 'C_EMAIL'},
                {name : 'C_SEX'},
                {name : 'C_BIRTHDAY'},
                {name : 'C_POST'},
                {name : 'C_HOME_TEL'},
                {name : 'C_HOME_ADDRESS'},
                {name : 'C_USER_STATUS'},
                {name : 'C_SUPPLIER_ID'},
                {name : 'C_VALIDITY'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"system/sysUser!getUserIfoList.action",
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
               { header: "userId", hidden: true, dataIndex: 'C_USERID',width:10},
               { header: "账号", sortable: true, dataIndex: 'C_USERCODE',width:40},
               { header: "姓名", sortable: true, dataIndex: 'C_USERNAME',width:40},
               { header: "性别", sortable: true, dataIndex: 'C_SEX',width:20,
            	   renderer: function (value, meta, record) {
            	   		if(!Ext.isEmpty(value)){
            	   			value = value=='M' ? '男' : '女'
            	   		}
            		   return value;
            	   }
               },
               { header: "手机", sortable: true, dataIndex: 'C_USER_MOBILE',width:40},
               { header: "邮箱", sortable: true, dataIndex: 'C_EMAIL',width:40},
               { header: "生日", sortable: true, dataIndex: 'C_BIRTHDAY',width:40},
               { header: "邮编", sortable: true, dataIndex: 'C_POST',width:40},
               { header: "有效期", sortable: true, dataIndex: 'C_VALIDITY',width:40},
               { header: "状态", sortable: true, dataIndex: 'C_USER_STATUS',width:25,
               		renderer: function (value, meta, record) {
            	   		if(!Ext.isEmpty(value)){
            	   			value = value=='1' ? '有效' : '无效'
            	   		}
            		   return value;
            	   }},
               { header: "操作", sortable: false, dataIndex: '',width:40,align:'center',
        	     renderer: function (value, meta, record) {
        	     	if(record.get('C_USERCODE')=='admin'||record.get('C_USERCODE')=='mokey'){
        	     		return "<div class='controlBtn'><a style='cursor: hand' href='javascript:void({0});' onclick='javscript:return false;' class='modify'>修改</a></div>";
        	     	}
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
						delparams.c_id= record.get('C_USERID');
						
						var loadMask = new Ext.LoadMask(document.body, {msg : '正在删除,请稍等...', removeMask : true});
						loadMask.show();
						Ext.Ajax.request({
				            url : rootPath+"system/sysUser!deleteUserIfo.action",
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
    				var data = record.data;
    				var loadMask = new Ext.LoadMask(Ext.getCmp(_this.gridId).body, {msg : 'loading...', removeMask : true});
					loadMask.show();
    				
					Ext.Ajax.request({
			            url : rootPath+"system/sysUser!getUserIfoById.action",
			            timeout : 120000,
			            success: function(result, obj){
			            	loadMask.hide();
			            	var jsonFromServer = Ext.util.JSON.decode(result.responseText);
			            	if(jsonFromServer.user){
			            		_this.priCreateWinFun(jsonFromServer.user);
    							var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
			    				//dirfrom.Ext_DATA = data;
			    				dirfrom.Ext_cid = data.C_USERID;
			    				//dirfrom.setValues(data);
			    				if(data.C_USERCODE =='admin' || data.C_USERCODE =='mokey'){
			    					dirfrom.findField('C_USERCODE').setReadOnly(true);
			    				}
			            		dirfrom.setValues(jsonFromServer.user);
			            	}
		                },
			            failure: function(result, obj){
			            	loadMask.hide();
			            }, 
			            params: {userId : data.C_USERID }
			        });
    				//dirfrom.findField('c_abstract').setValue(data.C_ABSTRACT);
    			}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		}
	}
};

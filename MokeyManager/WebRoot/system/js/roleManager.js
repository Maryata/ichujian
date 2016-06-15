Ext.roleManager = function() {
	return {
		userRoleArray : new Array,
		userPermArray : new Array,
		priData : {
			sexData : [{key:'M',value:'男'},{key:'F',value:'女'}],
			statusData : [{key:'1',value:'有效'},{key:'0',value:'无效'}]
		},
		formId : 'roleManager_formId',
		gridId : 'roleManager_gridId',
		addWinId : 'roleManager_addWinId',
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
							id: _this.formId+'C_ROLE_CODE',
							xtype : 'textfield',
							fieldLabel : '角色代码',
							name : 'C_ROLE_CODE',
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
							id: _this.formId+'C_ROLE_NAME',
							xtype : 'textfield',
							fieldLabel : '角色名称',
							name : 'C_ROLE_NAME',
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
			
	    	paramJson.c_role_code = Ext.getCmp(_this.formId +'C_ROLE_CODE').getValue();
	    	paramJson.c_role_name = Ext.getCmp(_this.formId +'C_ROLE_NAME').getValue();
	    	
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
				saveParam.C_ID = dirfrom.Ext_cid;
			}
			
			saveParam.C_ROLE_CODE = dirfrom.findField('C_ROLE_CODE').getValue();
			saveParam.C_ROLE_NAME = dirfrom.findField('C_ROLE_NAME').getValue();
			saveParam.C_REMARK = dirfrom.findField('C_REMARK').getValue();
			
			var temp_ur_Array = _this.userPermArray;
			var strParam = new Array;
			for (var i = 0; i < temp_ur_Array.length; i++) {
				strParam[i] = temp_ur_Array[i];
			}
			var params = {};
			params.saveParam = Ext.encode(saveParam);
			params.roleFuncts = strParam;
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"system/sysRole!saveRoleIfo.action",
				waitMsg : 'save ing...',
				timeout : 240000,
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
					_this.userPermArray = new Array;
				},
				failure : function(form, action) {
					alert('保存失败');
					_this.userPermArray = new Array;
				},
				params : params
			});
		},
		
		/**
		 * create new win
		 * @returns
		 */
		priCreateWinFun : function(roleId){
			var _this = this;
			
			if(!roleId){
				roleId = '';
			}
			
			var addWin = Ext.getCmp(_this.addWinId);
			if(addWin){
				addWin.show();
				return;
			}
			
			var sexField = _this.createComVarField({fieldLabel:"性别",id: _this.addWinId + 'C_SEX', name:'C_SEX' , data:_this.priData.sexData , width: 100,isNoBlank:false });
			var statusField = _this.createComVarField({fieldLabel:"状态",id: _this.addWinId + 'C_USER_STATUS',name:'C_USER_STATUS' , data:_this.priData.statusData , width: 100,isNoBlank:true,value:'1'});
			
			// 检查是否重复公共方法
			function checkExits(checkType, value,idVal) {
				if(Ext.isEmpty(idVal)){
					idVal = '';
				}
				var conn = Ext.lib.Ajax.getConnectionObject().conn;
				conn.open("post", rootPath+"system/sysRole!checkRoleInfo.action", false);// false表示同步调用
				conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
				conn.send("checkType="+checkType+"&value="+value+"&id="+idVal);//
				var data = Ext.util.JSON.decode(conn.responseText);
				return data;
			}
					
			
			var roleTreeRoot = new Ext.tree.AsyncTreeNode({
				text : '系统权限列表',
				draggable : false,
				id : '0'
			});

			var rolePepTree = new Ext.tree.TreePanel({
				autoScroll : true,
				animate : Ext.isIE6 ? false : true,
				enableDrag : true,
				enableDrop : false,
				height : 300,
				ddScroll : true,
				loadMask : {
					msg : LOADMASK_MSG
				},
				containerScroll : true,
				loader : new Ext.tree.TreeLoader({
					preloadChildren : true,
					timeout : 120000,
					dataUrl : rootPath+'system/sysRole!getPermTree.action',
					baseParams : {
						pid : '0',
						roleId : roleId// thisData[update_flag].get("roleId")
					},
					listeners : {
						beforeload : function(thisObj, node) {
							this.baseParams.pid = node.attributes.id;
						},
						load : function(thisObj, node, response) {
							//treeLoadMask.hide();
						},
						loadexception : function (thisObj,  node,  response ) {
							//treeLoadMask.hide();
							alert("加载异常");
						}
					}
				})

			});
			rolePepTree.setRootNode(roleTreeRoot);
			roleTreeRoot.expand(false, false);
			//rolePepTree.expandAll();

			rolePepTree.on('checkchange', function(node, isChecked) {
				_this.afterPermChecked(node, isChecked);
			});
			
			var c_form = new Ext.form.FormPanel({
				id : _this.addWinId + 'addForm',
				frame : true,
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
				//width : 720,
				//height : 400,
				//autoHeight:true,
				labelWidth : 60,
				layout : 'form',
				//fileUpload : true,
				items : [{
					layout : 'column',
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '角色代码',
							id : _this.addWinId + 'C_ROLE_CODE',
							name : 'C_ROLE_CODE',
							allowBlank : false,
							//regex : /^[0-9a-zA-Z_]+$/,
							//regexText : '只能输入字母、数字',
							maxLength : 10,
							width : 100,
							anchor : '90%',
							validationDelay : 1000,
							validator : function(value) {
								if(!/^[0-9a-zA-Z_]+$/.test(value)){
									return '只能输入字母、数字';
								}
						        var data = checkExits("C_ROLE_CODE", value, Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);
								if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
									return "角色代码重复";
								}
								return true;
						    }
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '角色名称',
							id : _this.addWinId + 'C_ROLE_NAME',
							name : 'C_ROLE_NAME',
							allowBlank : false,
							maxLength : 12,
							width : 270,
							anchor : '90%',
							validationDelay : 1000,
							validator : function(value) {
						        //var length = value.replace(/[^\x00-\xff]/g, "xx").length;  
						        /*if(length>1){  
						            return '长度不能超过1000个字节，一个汉字字符为两个字节！';  
						        }*/  
						        var data = checkExits("C_ROLE_NAME", value, Ext.getCmp(_this.addWinId + 'addForm').getForm().Ext_cid);
								if (!Ext.isEmpty(data.isExits) && data.isExits == '1') {
									return "角色名称重复";
								}
								return true;
						    }
						}]
					}]
				},{
					layout : 'form',
					items : [{
						xtype : 'textfield',//						
						fieldLabel : '备注',
						id : _this.addWinId + 'C_REMARK',
						name : 'C_REMARK',
						disabled :false,
						allowBlank : true,
						maxLength :30,
						anchor : '96%'
					}]
				},{
					xtype : 'panel',
					bodyStyle : 'border:1px solid #99bbe8;',
					//autoHeight : true,
					//checkboxToggle: false,
					//maskDisabled : false,
					//closable : true,
					//plain : true,
			        //collapsed: false, // fieldset initially collapsed
					items : [rolePepTree]
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
				title: '角色管理',
				plain:true,
	            modal : true, //阴影
				shadow :false,      
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 500, 
				layout : 'form',
				//frame : true,
				items : [c_form]
			});
			addWin.show();
			/*
			var treeLoadMask = new Ext.LoadMask(addWin.getEl().dom, {
				msg : LOADMASK_MSG,
				removeMask : true
			});
			treeLoadMask.show();*/
		},
		
		/**
		 * @函數功能 : 勾选节点事件触发
		 * 
		 */
		afterPermChecked : function(node, isChecked) {
			var _this = this;
			// 当节点为叶子节点
			if (node.isLeaf()) {
				_this.updateNodesDisInfo(node, isChecked);
			} else {
				node.disable();
				window.setTimeout(  function () { node.enable(); } , 500);
				node.expand(true);
				//非叶子节点也记录
				_this.updateNodesDisInfo(node, isChecked);
				_this.autoCheckSubNodes(node, isChecked);
			}

		},
		// -==-=-=-=========================================

		/*
		 * @函数功能 : 自动展开树 并勾选字节点 //-=-=-
		 */
		autoCheckSubNodes : function(node, isChecked) {
			var _this = this;
			node.expand();
			for (var i = 0; i < node.childNodes.length; i++) {
				var tempUi = node.childNodes[i].getUI();
				var tempNode = node.childNodes[i];
				// 更新权限节点操作信息
				if (isChecked) {
					if (!tempUi.isChecked()) {
						tempUi.toggleCheck();
					}
				} else {
					if (tempUi.isChecked()) {
						tempUi.toggleCheck(false);
					}
				}
				_this.autoCheckSubNodes(node.childNodes[i], isChecked);
			}

		},
		/**
		 * @函数功能 : 更新权限节点操作信息
		 */
		updateNodesDisInfo : function(node, isChecked) {
			var _this =this;
			var lastIndex = 0;
			var tempArray = _this.userPermArray;
			if (tempArray != null) {
				lastIndex = tempArray.length;
			}
			var tempUi = node.getUI();
			// 如 节点操作行为由上级节点的勾选行为自动触发时,不记得节点数据.
			/*
			 * if (tempUi.isChecked() != isChecked) { return false; }
			 */
			if (lastIndex == 0) {
				tempArray[lastIndex] = [node.id, isChecked];

			} else {

				for (var i = 0; i < lastIndex; i++) {
					if (node.id == tempArray[i][0]) {
						if (node.id == tempArray[i][0]) {
							tempArray.remove(tempArray[i]);
						}
						break;
					} else if (i == lastIndex - 1) {
						tempArray[lastIndex] = [node.id, isChecked];
					}
				}
			}

		}, 
		
		/**
		 * create grid
		 * @returns Grid
		 */
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
                {name : 'C_ID'},
                {name : 'C_ROLE_CODE'},
                {name : 'C_ROLE_NAME'},
                {name : 'C_MODIFY_TIME'},
                {name : 'C_REMARK'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"system/sysRole!getRoleList.action",
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
               { header: "roleId", hidden: true, dataIndex: 'C_ID',width:10},
               { header: "角色代码", sortable: true, dataIndex: 'C_ROLE_CODE',width:40},
               { header: "角色名称", sortable: true, dataIndex: 'C_ROLE_NAME',width:50},
               { header: "修改时间", sortable: true, dataIndex: 'C_MODIFY_TIME',width:35},
               { header: "备注", sortable: true, dataIndex: 'C_REMARK',width:100},
               { header: "操作", sortable: false, dataIndex: '',width:30,align:'center',
        	     renderer: function (value, meta, record) {
        	     	if(record.get('C_ROLE_CODE')=='admin'){
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
						delparams.c_id= record.get('C_ID');
						
						var loadMask = new Ext.LoadMask(document.body, {msg : '正在删除,请稍等...', removeMask : true});
						loadMask.show();
						Ext.Ajax.request({
				            url : rootPath+"system/sysRole!deleteRoleIfo.action",
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
    				_this.priCreateWinFun(data.C_ID);
    				var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
    				dirfrom.Ext_cid = data.C_ID;
    				dirfrom.setValues(data);
    				if(data.C_ROLE_CODE =='admin' ){
    					dirfrom.findField('C_ROLE_CODE').setReadOnly(true);
    					dirfrom.findField('C_ROLE_NAME').setReadOnly(true);
    				}
    			}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		}
	}
};

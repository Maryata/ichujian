//消息推送
Ext.push = function() {
	return {
		priData : {
			testData : [{key:'topic1',value:'类别1'},{key:'topic2',value:'类别2'},{key:'topic3',value:'类别3'},{key:'topic4',value:'类别4'},{key:'topic5',value:'类别5'}],
			//设置消息是否通过透传的方式送给app，1表示透传消息，0表示通知栏消息
			pushTypeData : [{key:1,value:'透传'}], // {key:0,value:'通知栏'},
			timeData : [{key:'0',value:'手动'},{key:'1',value:'定时'}],
			notifyEffectType : [{key:'1',value:'打开app对应的Launcher Activity'},{key:'2',value:'打开app内的任意一个Activity'},{key:'3',value:'打开网页'}],
			appActiveData : [{key:'com.mokey.activeMain',value:'活动帮主页'},{key:'com.mokey.uc',value:'com.mokey.uc'}]
		},
		formId : 'push_formId',
		gridId : 'push_gridId',
		addWinId : 'push_addWinId',
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
				allowBlank : option.isNoBlank ? false : true
				//anchor : '90%',
				//readOnly:true,
				//width : option.width ? option.width : _this.textwidth
			};
			
			if(option.width){
				config.width = option.width; 
			}else{
				config.anchor = '90%'; 
			}
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
		 * 获取保存数据
		 */
		priGetSaveData : function(){
			var _this = this;
			var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
			if (!dirfrom.isValid()) {
				return false;
			}
			var saveParam = {};
			if(dirfrom.Ext_cid ){
				saveParam.C_ID = dirfrom.Ext_cid;
			}
			
			saveParam.C_PUSH_TYPE = dirfrom.findField('C_PUSH_TYPE').getValue();
			saveParam.C_TIME = dirfrom.findField('C_TIME').getValue();
			saveParam.C_HINT_TYPE = dirfrom.findField('C_HINT_TYPE').getValue();
			saveParam.C_PUSH_SCOPE = dirfrom.findField('C_PUSH_SCOPE').getValue();
			saveParam.C_PUSH_TITLE = dirfrom.findField('C_PUSH_TITLE').getValue();
			saveParam.C_PUSH_CONTENT = dirfrom.findField('C_PUSH_CONTENT').getValue();
			saveParam.C_FAILD_COUNT = dirfrom.findField('C_FAILD_COUNT').getValue();
			
			if(saveParam.C_PUSH_SCOPE==0){
				saveParam.C_PUSH_TOPIC = '';
			}else{
				saveParam.C_PUSH_TOPIC = dirfrom.findField('C_PUSH_TOPIC').getValue();
			}
			saveParam.C_TURN_TYPE = dirfrom.findField('C_TURN_TYPE').getValue();
			if(!Ext.isEmpty(dirfrom.findField('C_APP_ACTIVE').getValue())){
				saveParam.C_APP_ACTIVE = dirfrom.findField('C_APP_ACTIVE').getValue();
			}else{
				saveParam.C_APP_ACTIVE = '';
			}
			
			if(saveParam.C_TURN_TYPE=='2' && Ext.isEmpty(saveParam.C_APP_ACTIVE) ){
				alert('请选择后面的activity');
				return false;
			}
			saveParam.C_TURN_URL = dirfrom.findField('C_TURN_URL').getValue();
			if(saveParam.C_TURN_TYPE=='3' && Ext.isEmpty(saveParam.C_TURN_URL) ){
				alert('请填写url');
				return false;
			}
			
			//saveParam.C_TURN_DATA = dirfrom.findField('C_TURN_DATA').getValue();
			var datas = [];
			var dataStore = Ext.getCmp(_this.addWinId+"DATD").getStore();
			for(var i=0; i<dataStore.getCount(); i++){
				var record = dataStore.getAt(i);
				if(!record.get('key') || !record.get('value')){
					continue;
				}
				datas.push({key:record.get('key') , value: record.get('value')})
			}
			saveParam.C_TURN_DATA = '';
			if(datas.length>0){
				saveParam.C_TURN_DATA = Ext.encode(datas);
				dataStore.loadData({list : datas});
			}
			
			var params = {};
			params.saveParam = Ext.encode(saveParam);
			return params;
		},
		/**
		 * save function
		 * @returns
		 */
		priSaveFun : function(){
			var _this = this;
			var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
			var params = _this.priGetSaveData();
			if (!params) {
				return;
			}
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"basedata/push!savePushInfo.action",
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
					alert('保存失败');
				},
				params : params
			});
			
		},
		
		/**
		 * 推送消息
		 */
		priSendFun : function(){
			var _this = this;
			var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
			var params = _this.priGetSaveData();
			if (!params) {
				return;
			}
			if(!dirfrom.Ext_cid ){
				alert('请先保存数据');
				return;
			}
			if(!window.confirm('是否推送？')){
				return;
			}
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"basedata/push!sendMessages.action",
				waitMsg : 'push ing...',
				success : function(form, action) {
					alert('推送成功');
					//Ext.getCmp(_this.gridId).store.reload();
				},
				failure : function(form, action) {
					alert('推送失败');
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
			
    		// 列表用到的数据源
    		var data_store = new Ext.data.Store({ 
    			reader : new Ext.data.JsonReader({
    				root : 'list'
    			},  Ext.data.Record.create([
                {name : 'key'},
                {name : 'value'}
    		 ]))
    		});
    		var data_cm = new Ext.grid.ColumnModel([ 
    			{ header: "key", dataIndex: 'key',width:50,
    				editor : new Ext.form.TextField({
						allowBlank : false,
						maxLength : 15
    				})
				},
               { header: "value", dataIndex: 'value',width:50,
    				editor : new Ext.form.TextField({
						allowBlank : false,
						maxLength : 50
    				})},
				{ header: "操作", sortable: false, dataIndex: '',width:30,align:'center',
        	     renderer: function (value, meta, record) {
   				     var formatStr = "<a style='cursor: hand' href='javascript:void();' onclick='javscript:return false;' class='add'>添加</a>";
   				     formatStr += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style='cursor: hand' href='javascript:void({0});' onclick='javscript:return false;' class='delete'>删除</a>";
   				     return "<div class='controlBtn'>" + formatStr + "</div>";
       			}.createDelegate(this),
       			css: "text-align:center;"
   				}
               ]);
    		
    		var data_grid = new Ext.grid.EditorGridPanel({
    			store : data_store,
    			scrollable : true,
    			id : _this.addWinId+"DATD",
    			clicksToEdit : 1,
    			bodyStyle : 'width:100%;border:1px solid #99bbe8;',
    			cm : data_cm,
    			viewConfig : {
    				forceFit : true
    			},
    			width : 550,
    			height : 120
    		});
    		
    		data_grid.on('cellclick', function (grid, rowIndex, columnIndex, e) {
    		  var btn = e.getTarget('.controlBtn');   
    		  if (btn) {
    			var t = e.getTarget();   
    			var record = grid.getStore().getAt(rowIndex);
    			var control = t.className;
    			if(record&&control=='add'){
    				var newRow = new Ext.data.Record({
						key : '',
						value : ''
					});
					grid.getStore().add(newRow);
    			}else if(record&&control=='delete'){
					grid.getStore().remove(record);
					//添加一个默认行
					if(grid.getStore().getCount()==0){
						var newRow = new Ext.data.Record({
							key : '',
							value : ''
						});
						grid.getStore().add(newRow);
					}
    			}
    		  }
    		});
    		
    		data_grid.store.loadData({list:[{key:'',value:''} ] });
			
			var c_form = new Ext.form.FormPanel({
				id : _this.addWinId + 'addForm',
				frame : true,
				labelWidth : 80,
				layout : 'form',
				items : [{
					layout : 'column',
					items : [{
						columnWidth : .3,
						layout : 'form',
						items : [
						_this.createComVarField({fieldLabel:"推送方式",id: _this.addWinId + 'C_PUSH_TYPE', name:'C_PUSH_TYPE' , data:_this.priData.pushTypeData,value:'1',  width:100,isNoBlank:true })
						]
					},{
						columnWidth : .3,
						layout : 'form',
						labelWidth : 1,
						items : [
						_this.createComVarField({fieldLabel:"",id: _this.addWinId + 'C_TIME', name:'C_TIME' , data:_this.priData.timeData, value:'0', width:100,isNoBlank:true })
						]
					}]
				},{
					
					layout : 'column',
					items : [{
						columnWidth : .6,
						layout : 'form',
						items : [{
							xtype: 'checkboxgroup',
						    fieldLabel: '提示方式',
						    id : _this.addWinId + 'C_HINT_TYPE',
							name : 'C_HINT_TYPE',
						    itemCls: 'x-check-group-alt',
						    // Put all controls in a single column with width 100%
						    columns: 3,
						    items: [
						        {boxLabel: '声音', name: 'C_HINT_TYPE-1',inputValue : 1 ,checked:true},
						        {boxLabel: '振动', name: 'C_HINT_TYPE-2',inputValue : 2},
						        {boxLabel: '呼吸灯(LED灯)', name: 'C_HINT_TYPE-4',inputValue : 4}
						    ]
						}]
					}]
				},{
					layout : 'column',
					bodyStyle : 'padding:1px;border-bottom:1px solid #99bbe8;',
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'radiogroup',
						    fieldLabel: '推送范围',
						    id : _this.addWinId + 'C_PUSH_SCOPE',
							name : 'C_PUSH_SCOPE',
						    // Put all controls in a single column with width 100%
						    columns: 4,
						    items: [
						        {boxLabel: '全部', name: 'C_PUSH_SCOPE-item',inputValue : 0,checked:true},
						        {boxLabel: '活动类型', name: 'C_PUSH_SCOPE-item',inputValue : 1}
						        //{boxLabel: '地域类型', name: 'C_PUSH_SCOPE-2',inputValue : 2}
						    ],
						    listeners: {  
			                    change: function(value) {
			                    	var scope = Ext.getCmp(_this.addWinId + 'C_PUSH_SCOPE').getValue();
			                    	var topicColumn = Ext.getCmp(_this.addWinId + 'TOPIC_COLUMN');
			                        if (scope==0) {  
			                            topicColumn.setVisible(false);  
			                        } else {
			                            
			                            var items = Manager.sys.From.createGroupItem('C_PUSH_TOPIC',_this.priData.testData,{labelFiled:'value',valueFiled:'key'} );
			                            var itemsGroup = Ext.getCmp(_this.addWinId + 'C_PUSH_TOPIC').destroy();
			                            topicColumn.setVisible(true);
			                            
										itemsGroup = new Ext.form.CheckboxGroup({
							    			xtype: 'checkboxgroup',
										    fieldLabel: '',
										    id : _this.addWinId + 'C_PUSH_TOPIC',
											name : 'C_PUSH_TOPIC',
										    itemCls: 'x-check-group-alt',
										    vertical: false,
										    columns: 3,
							        	    items:items
							        	});
							        	
							        	Ext.getCmp(_this.addWinId + 'TOPIC_ITEM').add(itemsGroup);
							        	Ext.getCmp(_this.addWinId + 'TOPIC_ITEM').doLayout();
							        	
			                        }
			                    }  
			                }
						}]
					}]
				},{
					layout : 'column',
					bodyStyle : 'padding:1px;border-bottom:1px solid #99bbe8;',
					id : _this.addWinId + 'TOPIC_COLUMN',
					hidden:true,
					items : [{
						columnWidth : .9,
						layout : 'form',
						id : _this.addWinId + 'TOPIC_ITEM',
						items : [{
							xtype: 'checkboxgroup',
						    fieldLabel: '',
						    id : _this.addWinId + 'C_PUSH_TOPIC',
							name : 'C_PUSH_TOPIC',
						    itemCls: 'x-check-group-alt',
						    vertical: false,
						    columns: 5,
						    items: [{boxLabel: '类别1', name: 'C_PUSH_TOPIC-1',inputValue : '1'}]
						}]
					}]
				}
				,{
					layout : 'column',
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '推送标题',
							id : _this.addWinId + 'C_PUSH_TITLE',
							name : 'C_PUSH_TITLE',
							allowBlank : false,
							maxLength : 50,
							width : 100,
							anchor : '90%'
						}]
					}]
				},{
					layout : 'form',
					items : [{
						xtype : 'textfield',//						
						fieldLabel : '推送内容',
						id : _this.addWinId + 'C_PUSH_CONTENT',
						name : 'C_PUSH_CONTENT',
						disabled :false,
						allowBlank : false,
						maxLength :50,
						anchor : '90%'
					}]
				},{
					layout : 'form',
					items : [{
						xtype : 'numberfield',					
						fieldLabel : '失败尝试次数',
						id : _this.addWinId + 'C_FAILD_COUNT',
						name : 'C_FAILD_COUNT',
						value:1,
						allowDecimals :false,
						allowBlank : true,
						maxLength :1,
						width:150
					}]
				},{
					layout : 'column',
					items : [{
						columnWidth : .4,
						layout : 'form',
						items : [
							_this.createComVarField({fieldLabel:"跳转类型",id: _this.addWinId + 'C_TURN_TYPE', name:'C_TURN_TYPE' ,value:'1', data:_this.priData.notifyEffectType, width:200,isNoBlank:true })
						]
					},{
						columnWidth : .6,
						layout : 'form',
						labelWidth : 1,
						items : [
							_this.createComVarField({fieldLabel:"",id: _this.addWinId + 'C_APP_ACTIVE', name:'C_APP_ACTIVE' , data:_this.priData.appActiveData, width:250,isNoBlank:false })
						]
					}]
				},{
					layout : 'form',
					items : [{
						xtype : 'textfield',//						
						fieldLabel : '跳转URL',
						id : _this.addWinId + 'C_TURN_URL',
						name : 'C_TURN_URL',
						hidden:true,
						disabled :false,
						allowBlank : true,
						maxLength :200,
						anchor : '80%'
					}]
				},{
					layout : 'column',
					id : _this.addWinId + 'data_column',
					hidden:false,
					items : [{
						columnWidth : .1,
						layout : 'column',
						items : [{
							xtype : 'label',//						
							text : '跳转数据:'//C_TURN_DATA
						}]
					},{
						columnWidth : .8,
						layout : 'form',
						items : [data_grid]
					}]
				}]
			});
			
			
			//绑定事件;
			//{key:'0',value:'有客户端自己定义'},{key:'1',value:'打开应用'},{key:'3',value:'打开网页'}]
			c_form.getForm().findField('C_TURN_TYPE').on('select',function(thiscomb){
				var turnType = thiscomb.value;
				if(Ext.isEmpty(turnType)){
					return;
				}
				c_form.getForm().findField('C_APP_ACTIVE').setValue('');
				c_form.getForm().findField('C_TURN_URL').setValue('');
				
				var turn_url = c_form.getForm().findField('C_TURN_URL');
				var app_active = c_form.getForm().findField('C_APP_ACTIVE');
				var data_column = Ext.getCmp(_this.addWinId + 'data_column');
				if(turnType=='1'){
					turn_url.setVisible(false);
					app_active.setVisible(false);
					data_column.setVisible(true);
				}else if(turnType=='2'){
					turn_url.setVisible(false);
					app_active.setVisible(true);
					data_column.setVisible(true)					
				}else if(turnType=='3'){
					turn_url.setVisible(true);
					app_active.setVisible(false);
					data_column.setVisible(false)
				}
			});
			
			c_form.getForm().findField("C_PUSH_TYPE").on("select",function(thiscomb) {
				var turnType = thiscomb.value;
				if(Ext.isEmpty(turnType)){
					return;
				}
				var turn_type = c_form.getForm().findField("C_TURN_TYPE");
				var app_active = c_form.getForm().findField("C_APP_ACTIVE");
				var turn_url = c_form.getForm().findField('C_TURN_URL');
				var data_column = Ext.getCmp(_this.addWinId + 'data_column');
				
				if(turnType === 1) { // 透传
					turn_type.setVisible(false);
					app_active.setVisible(false);
					turn_url.setVisible(false);
					
					Ext.getCmp(_this.addWinId + "C_TURN_TYPE").reset();
					
					data_column.setVisible(true);
				} else if(turnType === 0) { // 通知栏
					turn_type.setVisible(true);
					
					Ext.getCmp(_this.addWinId + "C_TURN_TYPE").reset();
				}
			}); 
			
			c_form.getForm().findField('C_APP_ACTIVE').setVisible(false);
			c_form.getForm().findField("C_TURN_TYPE").setVisible(false);
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '消息推送',
				plain:true,	 
	            modal : true, 
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 750, 
	            height : 450,
				layout : 'fit',
				frame : true,
				items : [c_form],
				buttonAlign: 'center',
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
		        }, {
		        	text : '推送',
		            handler : function() {
		            	_this.priSendFun();
		            }
		        } ]
			});
			
			addWin.show();
			//Ext.getCmp(_this.addWinId + 'data_column').setVisible(false);
		},
		
		/**
		 * create grid
		 * @returns Grid
		 */
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
                {name : 'C_ID'},
                {name : 'C_PUSH_TYPE'},
                {name : 'C_TIME'},
                {name : 'C_HINT_TYPE'},
                {name : 'C_PUSH_SCOPE'},
                {name : 'C_PUSH_TOPIC'},
                {name : 'C_PUSH_TITLE'},
                
                {name : 'C_PUSH_CONTENT'},
                {name : 'C_FAILD_COUNT'},
                {name : 'C_TURN_TYPE'},
                {name : 'C_TURN_URL'},
                {name : 'C_TURN_DATA'},
                {name : 'C_APP_ACTIVE'},
                {name : 'C_SYSDATE'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"basedata/push!getPushInfoList.action",
    				timeout : 60000
    			}),
    			reader : new Ext.data.JsonReader({
    				totalProperty : 'count',
    				root : 'list'
    			}, resultsRecord)
    		});
    		
    		var tempArray = [
               { header: "id", hidden: true, dataIndex: 'C_ID',width:10},
               { header: "scope", hidden: true, dataIndex: 'C_PUSH_SCOPE',width:10},
               { header: "topic", hidden: true, dataIndex: 'C_PUSH_TOPIC',width:10},
               { header: "appActive", hidden: true, dataIndex: 'C_APP_ACTIVE',width:10},
               { header: "c_turn_data", hidden: true, dataIndex: 'C_TURN_DATA',width:50},
               { header: "推送方式", sortable: true, dataIndex: 'C_PUSH_TYPE',width:25,
               		renderer: function (value, meta, record) {
            		   if(Ext.isEmpty(value)){
            			   return value;
            		   }
            		   for(var i=0;i<_this.priData.pushTypeData.length;i++){
            		   		var d = _this.priData.pushTypeData[i];  
            			   if(d.key==value){
            				   return d.value;
            			   }
            		   }
            		   return value;
            	   }
        	   },
               { header: "提示方式", sortable: true, dataIndex: 'C_HINT_TYPE',width:50,
               		renderer: function (value, meta, record) {
               			if(Ext.isEmpty(value)){
            			   return value;
            		    }
            		    var vArr = value.split(',');
            		    value = '';
            		    for(var i=0;i<vArr.length;i++){
            		    	if(i>0){
            		    		value += ',';
            		    	}
            		    	switch(vArr[i]){
            		    		case '1':
            		    			value += '声音';	
            		    			break;
        		    			case '2':
            		    			value += '振动';
            		    			break;
        		    			case '4':
            		    			value += '呼吸灯(LED灯)';
            		    			break;
            		    	}
            		    }
            		    return value;
               		}
               },
               { header: "推送标题", sortable: true, dataIndex: 'C_PUSH_TITLE',width:50},
               { header: "推送内容", sortable: true, dataIndex: 'C_PUSH_CONTENT',width:160},
               { header: "失败尝试次数", sortable: true, dataIndex: 'C_FAILD_COUNT',width:30},
               { header: "系统时间", sortable: true, dataIndex: 'C_SYSDATE',width:45},
               { header: "操作", sortable: false, dataIndex: '',width:30,align:'center',
        	     renderer: function (value, meta, record) {
   				     var formatStr = "<a style='cursor: hand' href='javascript:void();' onclick='javscript:return false;' class='modify'>修改</a>";   
   				     return "<div class='controlBtn'>" + formatStr + "</div>";
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
    				forceFit : true
    			},
    			width : mainWidth,
    			height : mainHeigth,
    			loadMask : { msg : LOADMASK_MSG },
    			bbar : new Ext.PagingToolbar({
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
				            url : rootPath+"basedata/phoneBrand!deleteBrandInfo.action",
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
    				dirfrom.Ext_cid = data.C_ID;
    				dirfrom.setValues(data);
    				
    				var turnType = data.C_TURN_TYPE;
	    			var turn_url = dirfrom.findField('C_TURN_URL');
					var app_active = dirfrom.findField('C_APP_ACTIVE');
					var data_column = Ext.getCmp(_this.addWinId + 'data_column');
					if(turnType=='1'){
						turn_url.setVisible(false);
						app_active.setVisible(false);
						data_column.setVisible(true);
					}else if(turnType=='2'){
						turn_url.setVisible(false);
						app_active.setVisible(true);
						data_column.setVisible(true)
					}else if(turnType=='3'){
						turn_url.setVisible(true);
						app_active.setVisible(false);
						data_column.setVisible(false)
					}
    				setTimeout(function(){
    					dirfrom.findField('C_PUSH_TOPIC').setValue(data.C_PUSH_TOPIC);
    				},200);
    				
    				if(!Ext.isEmpty(data.C_TURN_DATA)){//Ext.decode
    					var datas = eval(data.C_TURN_DATA);
    					Ext.getCmp(_this.addWinId+"DATD").getStore().loadData({list : datas});
    				}else{
    					Ext.getCmp(_this.addWinId+"DATD").getStore().loadData({list:[{key:'',value:''} ] });
    				}
    			}
    		  }   
    		},   
    		this); 
    		
    		return grid;
		}
	}
};

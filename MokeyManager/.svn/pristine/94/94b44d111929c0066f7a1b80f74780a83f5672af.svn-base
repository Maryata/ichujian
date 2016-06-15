function open_Dialog(url)
{
	if(!url || url=='null'){
		alert("预览地址为空");
		return false;
	}
	url +="?rd="+Math.random();
	var iWidth=420; //窗口宽度
    var iHeight=580;//窗口高度
    var iTop=(window.screen.height-iHeight)/2-30;
    var iLeft=(window.screen.width-iWidth)/2;
    /*var return_Value;
    if (document.all&&window.print)
    {
    return_Value = window.showModalDialog(url,window,"dialogHeight: "+iHeight+"px; dialogWidth: "+iWidth+"px;dialogTop: "+iTop+"; dialogLeft: "+iLeft+"; resizable: no; status: no;scroll:no");
    }
    else */
    window.open(url,"","width=" + iWidth + "px,height=" + iHeight + "px,top="+iTop+",left="+iLeft+",resizable=0,scrollbars=0"); 
}

Ext.ActivityInfo = function() {
	return {
		priData : {
			isliveData : [{key:'0',value:'未发布'},{key:'1',value:'已发布'},{key:'2',value:'审核未通过'}],
			gradeData : [{key:'3',value:'3'},{key:'4',value:'4'},{key:'5',value:'5'}]//,{key:'4.5',value:'4.5'}
		},
		formId : 'ActivityInfo_formId',
		gridId : 'ActivityInfo_gridId',
		addWinId : 'ActivityInfo_addWinId',
		textwidth : 120,
		init : function(){
		},
		load : function(){
			var _this = this;			
			var codeField3 = new Ext.form.TextField({
				id: 'title',
				xtype : 'textfield',
				fieldLabel : '标题',
				name : 'code', 
				width : _this.textwidth,
				readOnly : false,
				maxLength : 50,
				allowBlank : 'true',
				listeners : {
				focus : function() {
			}
			}
			});
          //--------------------------------------城市的下拉框----------------------------------------	
			var cityStore = new Ext.data.Store( {
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
			_this.cityStore = {};
			Ext.apply(_this.cityStore,cityStore);
			cityStore.load( {
				callback : function(obj) {
				cityStore.insert(0, new cityStore.recordType( {
					C_ID : '0',
					C_CNAME : '全部'
				}));
			} 
			});

			var city_Comb = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : cityStore,
				fieldLabel : '城市',
				id :'city',
				name : 'supp',
				//allowBlank : false,
				editable : false,
				mode : 'local',
				displayField : 'C_CNAME',
				valueField : 'C_ID',
				resizable : true,
				editable : false,
				width : _this.textwidth,
				selectOnFocus : true 
			});
		
			//---------------------------------行业的下拉框----------------------------	 
			/*var IndustyStore = new Ext.data.Store( {
				// autoLoad: true,
				proxy : new Ext.data.HttpProxy( {
					url : rootPath + "actionBaseActivityInfoService!getIndusty",
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
			//行业
			_this.IndustyStore = {};
			Ext.apply(_this.IndustyStore,IndustyStore);

			IndustyStore.load( {
				callback : function(obj) {
				IndustyStore.insert(0, new IndustyStore.recordType( {
					C_ID : '',
					C_NAME : '全部'
				}));
			} 
			});

			var Industy_Comb = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : IndustyStore,
				fieldLabel : '行业',
				id :'industy',
				//allowBlank : false,
				editable : false,
				mode : 'local',
				displayField : 'C_NAME',
				valueField : 'C_ID',
				resizable : true,
				editable : false,
				width : _this.textwidth,
				selectOnFocus : true
			});*/
			
			//------------------------------- 品牌的下拉框----------------------------	
			_this.getBrandStore = function(isAll){
				var BrandStore = new Ext.data.Store( {
					proxy : new Ext.data.HttpProxy( {
						url : rootPath + "actionBaseActivityInfoService!getBrands",
						timeout : 60000 
					}),
					reader : new Ext.data.JsonReader( {
						root : 'list'
					}, Ext.data.Record.create( [ {
						name : 'C_CNAME'
					}, {
						name : 'C_ID'
					} ]))
				});
				
				BrandStore.load( {
					callback : function(obj) {
						if(isAll){
							BrandStore.insert(0, new BrandStore.recordType( {
								C_ID : '0',
								C_CNAME : '全部'
							}));
						}
					} 
				});
				return BrandStore;
			}			

			var Brand_Comb = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : _this.getBrandStore(true),
				fieldLabel : '品牌',
				id :'brand',
				//allowBlank : false,
				editable : false,
				mode : 'local',
				displayField : 'C_CNAME',
				valueField : 'C_ID',
				resizable : true,
				editable : false,
				width : _this.textwidth,
				selectOnFocus : true
			});

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
			_this.activityStore = {};
			Ext.apply(_this.activityStore,activityStore);
			var activityProperty=new Ext.form.ComboBox( {
					typeAhead : true,
					loadStatus : false,
					triggerAction : 'all',
					store : activityStore,
					fieldLabel : '性质',
					id :'property',			
					//allowBlank : false,
					editable : false,
					mode : 'local',
					displayField : 'C_NAME',
					valueField : 'C_ID',
					resizable : true,
					editable : false,
					width : 120,
					selectOnFocus : true
				})
			var S_time_s = _this.createDateField({fieldLabel:"开始时间",name:'S_time_s'});
			var S_time_e = _this.createDateField({fieldLabel:"到",name:'S_time_e'});
			var E_time_s = _this.createDateField({fieldLabel:"结束时间",name:'E_time_s'});//,value:new Date().add(Date.DAY, -200)
			var E_time_e = _this.createDateField({fieldLabel:"到",name:'E_time_e'});
			var liveField = _this.createComVarField({fieldLabel:"审核状态",name:'C_ISLIVE' ,id:_this.formId+'C_ISLIVE', data:_this.priData.isliveData , all: true});
			var pnlMain = new Ext.form.FormPanel( {
				id : _this.formId + 'formPanel',
				autoWidth : true,
				autoHeight : true,
				border :false,
				//frame false,
				labelWidth : 60,
				items : [{
					layout : 'column',
					border :true,
					items : [{
						columnWidth : .20,
						layout : 'form',
						items : [ S_time_s ]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [S_time_e]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [E_time_s] 
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [E_time_e]  
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [Brand_Comb]  
					}]
				},{
					layout : 'column',
					border :false,
					items : [{
						columnWidth : .20,
						layout : 'form',
						items : [city_Comb ]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [activityProperty]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [codeField3]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [liveField]
					},{
						columnWidth : .20,
						border :false,
						layout : 'column',
						items : [{
							columnWidth : .33,
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
							columnWidth : .33,
							border :false,
							layout : 'form',
							items : [{
								xtype:'button',
								text:'添加',
								width:50,
								handler:function(){
									_this.priCreateWinFun('add');
								}
							}]
						},{
							columnWidth : .33,
							border :false,
							hidden:true,
							layout : 'form',
							items : [{
								xtype:'button',
								text:'导入EXCEL表',
								width:50,
								handler:function(){
									_this.priCreateUploadFun();
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
			});
			
			_this.BrandStore = _this.getBrandStore(false);
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
			
			var op = {
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
			};
			if(option.value) op.value=option.value;
			if(option.readOnly)op.readOnly = option.readOnly;
			if(option.disabled)op.disabled = option.disabled;
			
			if(option.anchor){
				op.anchor = option.anchor;
			}else{
				op.width = option.width ? option.width : _this.textwidth;
			}
			var actCombo = new Ext.form.ComboBox(op);
			return actCombo;
		},
		
		/**
		 * query function
		 * @returns
		 */
		createDateField : function(option) {
			var _this = this;
			var dateField ={
	            xtype: 'datefield',
	            name : option.name,
	            fieldLabel : option.fieldLabel,
	            width: _this.textwidth,
	            format: 'Y-m-d',
	            id: _this.formId+option.name
	        };
			if(option.value){
				dateField.value = option.value;
			}
			return dateField;
		},
		priQueryFun : function(){
			var _this=this;
			var gridStore = Ext.getCmp(_this.gridId).store;
			//gridStore.removeAll();
			var paramJson = {};
			
	    	paramJson.S_time_s = Ext.getCmp(_this.formId+'S_time_s').getRawValue();
	    	paramJson.S_time_e = Ext.getCmp(_this.formId+'S_time_e').getRawValue();
	    	paramJson.E_time_s = Ext.getCmp(_this.formId+'E_time_s').getRawValue();
	    	paramJson.E_time_e = Ext.getCmp(_this.formId+'E_time_e').getRawValue();
	    	paramJson.city 	   = Ext.getCmp('city').getValue();
	    	//paramJson.industy = Ext.getCmp('industy').getValue();
	    	paramJson.property = Ext.getCmp('property').getValue();
	    	paramJson.brand = Ext.getCmp('brand').getValue();
	    	paramJson.title= Ext.getCmp('title').getValue();
	    	
	    	paramJson.islive = Ext.getCmp(_this.formId + 'C_ISLIVE').getValue();
	    	
	    	if(paramJson.city=='0'){
	    		paramJson.city='';
	    	}
	    	if(paramJson.brand=='0'){
	    		paramJson.brand='';
	    	}
	    	if(paramJson.property=='0'){
	    		paramJson.property='';
	    	}
//	    	
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
		priSaveFun : function(addWin,option){
			var _this = this;
			var imgs = window.frames["myImgFrame"].getImgs();
			var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
			
			if (!dirfrom.isValid()) {
				return;
			}
			var content = window.frames["myContentFrame"].getContent();
			if(Ext.isEmpty(content)){
				alert('请输入活动内容!');
				return;
			}
			var params = {};
			var saveParam = {};
			if(dirfrom.Ext_cid){
				saveParam.C_ID = dirfrom.Ext_cid;
				if('0'!=option.audit){
					saveParam.C_ISLIVE = option.audit;
				}
				if(!Ext.isEmpty(imgs)){
					saveParam.C_IMAGEURL = imgs;
				}
				if('2'==option.audit){
					if(Ext.isEmpty(dirfrom.findField('C_REMARK').getValue())){
						
						// 修改审核状态
						/*Ext.Ajax.request({
				            url : rootPath+"actionBaseActivityInfoService!unreviewed.action",
				            timeout : 120000,
				            success: function(result, obj){
				                var jsonFromServer = Ext.util.JSON.decode(result.responseText);
				                if (jsonFromServer.status=='Y') {
				                   grid.getStore().reload(); 
				                }
			                },
			                params: {id:record.get('C_ID')}
				        });*/
						
						alert('审核驳回请在备注输入原因!');
						return;
					}
				}
			}else{
				if(Ext.isEmpty(imgs)){
					alert('请上传图片!');
					return;
				}
				saveParam.C_IMAGEURL = imgs;
				saveParam.C_ISLIVE = '0';
				saveParam.C_TYPE = 0;
				saveParam.C_PUBLISHETYPE =1;
				saveParam.C_INDUSTRYID=0;
				saveParam.C_LONGITUDE =0;
				saveParam.C_LATITUDE =0;
			}
			saveParam.C_CONTENT = content;
			
			saveParam.C_TITLE = dirfrom.findField('C_TITLE').getValue();
			saveParam.C_JOINURL = dirfrom.findField('C_JOINURL').getValue();
			saveParam.C_SOURCESURL = saveParam.C_JOINURL;
			saveParam.C_SDATE = dirfrom.findField('C_SDATE').getRawValue()+":00";
			saveParam.C_EDATE = dirfrom.findField('C_EDATE').getRawValue()+":00";
			saveParam.C_GRADE = dirfrom.findField('C_GRADE').getValue();
			saveParam.C_QUOTA = dirfrom.findField('C_QUOTA').getValue();
			saveParam.C_CITYID = dirfrom.findField('C_CITYID').getValue();
			saveParam.C_PROPERTYID = dirfrom.findField('C_PROPERTYID').getValue();
			saveParam.C_BRANDID = dirfrom.findField('C_BRANDID').getValue();
			saveParam.C_PUBLISHER = dirfrom.findField('C_PUBLISHER').getValue();
			saveParam.C_REMARK = dirfrom.findField('C_REMARK').getValue();
			// 如果点“保存”，则不是审核操作，那么不需要C_ACTIONDATE
			// 如果点“审核通过”，在后台以当前时间作为C_ACTIONDATE
			// 综上，不需要向后台传递C_ACTIONDATE
//			saveParam.C_ACTIONDATE = dirfrom.C_ACTIONDATE;
			
			// 活动审核人
			saveParam.C_AUDITPERSON = dirfrom.findField('C_AUDITPERSON').getValue();
			
			params.saveParam = Ext.encode(saveParam);
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"actionBaseActivityInfoService!saveActBaseIfo",
				waitMsg : 'save ing...',
				timeout : 240000,
				success : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					if(rsc.cId){
						dirfrom.Ext_cid = rsc.cId;
						dirfrom.C_ACTIONDATE ='';
						//alert('保存成功');
						addWin.close();
						Ext.getCmp(_this.gridId).store.reload();
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
		 * upload function
		 * @returns
		 */
		priUploadFun : function(){
			var _this = this;
			var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
			if (!dirfrom.isValid()) {
				return;
			}
			//参数的传递
			var saveParam={};
			if(Ext.isEmpty(dirfrom.findField('c_reportFile').getValue())){
				alert(' 文件不能为空!');
				return;
			}
			var params = {};
			//*
			params.saveParam=Ext.encode(saveParam);
			
			dirfrom.submit({
				method : 'post',
				url : rootPath+"actionBaseActivityInfoService!readExcel",
				waitMsg : 'upload ing...',
				timeout : 240000,
				success : function(form, action) {
					alert('导入成功!');
					Ext.getCmp(_this.gridId).store.reload();
				},
				failure : function(form, action) {
					var rsc = Ext.decode(action.response.responseText);
					alert('导入失败! 填写错误项:'+rsc.notice);
				},
				params : params
			});
		},
		priCreateUploadFun : function(){
			var _this = this;
			var addWinId = _this.addWinId+"up";
			var addWin = Ext.getCmp(addWinId);
			if(addWin){
				addWin.destroy () 
			}
					
			var c_form = new Ext.form.FormPanel({
				id : addWinId + 'addForm',
				frame : true,
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
				labelWidth : 60,
				layout : 'form',
				fileUpload : true,
				items : [{
					xtype : 'field',
					inputType : 'file',
					fieldLabel : '文件',
					id : _this.addWinId + 'c_reportFile',
					name : 'c_reportFile',
					allowBlank : false,
					allowDecimals :false,
					maxLength : 50,
					width : 200
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
		        	text : '导入',
		            handler : function() {
		            	_this.priUploadFun();
		            }
		        } ]
			});
			
			addWin = new Ext.Window({ 
				id : addWinId,
				title: '导入文件',
				closable:false,
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 400, 
				layout : 'form',
				frame : true,
				items : [c_form]
			});
			addWin.show();
		},
		
		//*****************
		/**
		 * create grid
		 * @returns Grid
		 */
		createGrid : function(){
			var _this = this;
			var resultsRecord = Ext.data.Record.create([
                {name : 'C_ID'},
                {name : 'C_CITY'},
                {name : 'C_INDUSTY'},
                {name : 'C_PROPERTY'},
                {name : 'C_BRAND'}, 
                {name : 'C_ADDRESS'}, 
                {name : 'C_TITLE'},
                {name : 'C_SDATE'},
                {name : 'C_EDATE'},
                {name : 'C_INDUSTRYID'},
                {name : 'C_ACTIONDATE'},
                {name : 'C_ADDTIME'},
                {name : 'C_IMAGEURL'},
                {name : 'C_WEBVIEWURL'},
                {name : 'C_DETAILURL'},
                {name : 'C_JOINURL'},
                {name : 'C_PUBLISHER'},
                {name : 'C_REMARK'},
                {name : 'C_ISLIVE'},
                {name : 'C_EDITPERSON'},
                {name : 'C_AUDITPERSON'}
    		 ]);
    		 // 列表用到的数据源
    		var resultsGrid_store = new Ext.data.Store({ 
    			proxy : new Ext.data.HttpProxy({
    				url : rootPath+"actionBaseActivityInfoService!getBaseActivityInfo",
    				timeout : 60000
    			}),
    			reader : new Ext.data.JsonReader({
    				totalProperty : 'count',
    				root : 'list'
    			}, resultsRecord)
    		});
    		resultsGrid_store.load({
	    		params : {
					start : 0,
					limit : SYS_CONSTANT_PAGESIZE
				},
				callback: function(obj){
					if(obj.length == 0) {
					}
				}
			});
    		
    		var tempArray = [
               { header: "C_ID", hidden: true, dataIndex: 'C_ID',width:10},
               { header: "发布时间", sortable: true, dataIndex: 'C_ADDTIME',width:20},   
               { header: "审核时间", sortable: true, dataIndex: 'C_ACTIONDATE',width:20},  
               { header: "行业id", hidden: true, dataIndex: 'C_INDUSTRYID',width:20},
               { header: "图片", hidden: true, dataIndex: 'C_IMAGEURL',width:50},
               { header: "C_WEBVIEWURL", hidden: true, dataIndex: 'C_WEBVIEWURL',width:50}, 
               { header: "C_DETAILURL", hidden: true, dataIndex: 'C_DETAILURL',width:50},
               { header: "C_JOINURL", hidden: true, dataIndex: 'C_JOINURL',width:50},
               { header: "编辑人", sortable: true, dataIndex: 'C_EDITPERSON',width:20},
               { header: "审核人", sortable: true, dataIndex: 'C_AUDITPERSON',width:20},
               { header: "来源", sortable: true, dataIndex: 'C_PUBLISHER',width:20},
               { header: "审核状态", sortable: true, dataIndex: 'C_ISLIVE',width:15,
               		renderer: function (value, meta, record) {
            		   if(Ext.isEmpty(value)){
            			   return value;
            		   }
            		   for(var i=0;i<_this.priData.isliveData.length;i++){
            			   if(_this.priData.isliveData[i].key==value){
            				   return _this.priData.isliveData[i].value;
            			   }
            		   }
            		   return value;
        	   }},
               { header: "备注", sortable: true, dataIndex: 'C_REMARK',width:20},
               { header: "城市", sortable: true, dataIndex: 'C_CITY',width:20},
               { header: "性质",  sortable: true, dataIndex: 'C_PROPERTY',width:20},
               { header: "品牌", sortable: true, dataIndex: 'C_BRAND',width:20},
               { header: "地点", hidden: true, dataIndex: 'C_ADDRESS',width:40},
               { header: "标题", sortable: true, dataIndex: 'C_TITLE',width:100,
               	renderer: function (value, meta, record) {
               		return "<img style='cursor: hand;' width='15' height='15' src='../../image/print-preview.png' onclick=\"javascript:open_Dialog('"+record.get("C_DETAILURL")+"')\">&nbsp;&nbsp;"+value;
               	}
               },
               { header: "开始时间", sortable: true, dataIndex: 'C_SDATE',width:20},
               { header: "结束时间", sortable: true, dataIndex: 'C_EDATE',width:20},
               { header: "操作", sortable: false, dataIndex: '',width:20,align:'center',
        	     renderer: function (value, meta, record) {
   				     var formatStr = "<a style='cursor: hand' href='javascript:void({0});' onclick='javscript:return false;' class='modify'>修改</a>";   
   				     formatStr += "&nbsp;&nbsp;&nbsp;&nbsp;<a style='cursor: hand' href='javascript:void({0});' onclick='javscript:return false;' class='delete'>删除</a>";
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
    				
    				Ext.Ajax.request({
			            url : rootPath+"actionBaseActivityInfoService!deleteByID.action",
			            timeout : 120000,
			            success: function(result, obj){
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
    				return  true;
    				}
    			}else if(record&&control=='modify'){
    				_this.priCreateWinFun('modify');
    				var dirfrom = Ext.getCmp(_this.addWinId + 'addForm').getForm();
    				var data = record.data;
    				dirfrom.Ext_cid = data.C_ID;
    				
    				
    				var loadMask = new Ext.LoadMask(document.body, {msg : 'loading...', removeMask : true});
					loadMask.show();
					Ext.Ajax.request({
			            url : rootPath+"actionBaseActivityInfoService!getActDetail.action",
			            timeout : 120000,
			            success: function(result, obj){
			            	loadMask.hide();
			                var jsonFromServer = Ext.util.JSON.decode(result.responseText);
			                if (jsonFromServer.status=='Y') {
			                   var data = jsonFromServer.data;
			                   dirfrom.C_ACTIONDATE = data.C_ACTIONDATE;
			                   dirfrom.findField('C_TITLE').setValue(data.C_TITLE);
			                   dirfrom.findField('C_JOINURL').setValue(data.C_JOINURL);
			                   
			                   dirfrom.findField('C_SDATE').setRawValue(data.C_SDATE.substr(0,16));
			                   dirfrom.findField('C_EDATE').setRawValue(data.C_EDATE.substr(0,16));
			                   //Ext.getCmp(_this.addWinId + 'C_EDATE').setValue(data.C_SDATE);
			                   //Ext.getCmp(_this.addWinId + 'C_EDATE').setValue(data.C_EDATE);
			                   
			                   dirfrom.findField('C_GRADE').setValue(data.C_GRADE);
			                   dirfrom.findField('C_QUOTA').setValue(data.C_QUOTA);
			                   
			                   dirfrom.findField('C_CITYID').setValue(data.C_CITYID);
			                   dirfrom.findField('C_PROPERTYID').setValue(data.C_PROPERTYID);
			                   dirfrom.findField('C_BRANDID').setValue(data.C_BRANDID);
			                   //dirfrom.findField('C_CONTENT').setValue(data.C_CONTENT);
			                   
			                   dirfrom.findField('C_ISLIVE').setValue(data.C_ISLIVE);
			                   dirfrom.findField('C_REMARK').setValue(data.C_REMARK);
			                   dirfrom.findField('C_PUBLISHER').setValue(data.C_PUBLISHER);
			                   
			                   // 活动审核人员
			                   dirfrom.findField('C_AUDITPERSON').setValue(data.C_AUDITPERSON);
			                   
			                   _this.webUrl = data.C_DETAILURL;
			                   
		                   		var imgTimer = setInterval(function(){
		                   			if(window.frames["myImgFrame"] && window.frames["myImgFrame"].document.getElementById("img")){
			    						window.frames["myImgFrame"].loadImgFun(data.C_IMAGEURL);
			    						clearInterval(imgTimer);
		                   			}
			    				},200);
			                   
			                   var contentTimer = setInterval(function(){
		                   			if(window.frames["myContentFrame"] && window.frames["myContentFrame"].document.getElementById("content")){
			    						window.frames["myContentFrame"].setContent(data.C_CONTENT);
			    						clearInterval(contentTimer);
		                   			}
			    				},200);
			    				
			                    
			    				var label = "活动管理  --   编辑人:";
			    				if(!Ext.isEmpty(data.C_EDITPERSON )){label +=data.C_EDITPERSON;}
			    				if(data.C_AUDITPERSON){
			    					label +="  审核人:"+data.C_AUDITPERSON;
			    				}
			    				// 如果当前登录人不是首次录入人员，不能编辑
			    				if(_this.userName != data.C_EDITPERSON){
			    					saveBtn = Ext.getCmp(_this.addWinId + 'btn_save');
//			    					saveBtn.disabled = true;// 按钮不可用
			    					saveBtn.setVisible(false);// 按钮隐藏
			    				}
			    				// 如果当前数据审核状态是1，隐藏“审核成功”按钮
			    				if("1"==data.C_ISLIVE){
			    					auditBtn = Ext.getCmp(_this.addWinId + 'btn_audit');
			    					auditBtn.setVisible(false);
			    				}
			    				// 如果当前数据审核状态是2，隐藏“审核不成功”按钮
			    				/*if("2"==data.C_ISLIVE){
			    					auditBtn = Ext.getCmp(_this.addWinId + 'btn_break');
			    					auditBtn.setVisible(false);
			    				}*/
			    				Ext.getCmp(_this.addWinId).setTitle(""+label);
			                }else {
			                	 alert('laoding失败!');
			                };
		                },
			            failure: function(result, obj){
			            	loadMask.hide();
			            }, 
			            params: {id:dirfrom.Ext_cid}
			        });
    			}
    		  }   
    		},   
    		this); 
    		return grid;
		},
		
		priCreateWinFun : function(type){
			var _this = this;
			function setBtns(){
				if(type=='add'){
	            	Ext.getCmp(_this.addWinId + 'btn_webview').setVisible(false);
					//Ext.getCmp(_this.addWinId + 'btn_save').setVisible(true);
					if(Ext.getCmp(_this.addWinId + 'btn_audit'))Ext.getCmp(_this.addWinId + 'btn_audit').setVisible(false);
					if(Ext.getCmp(_this.addWinId + 'btn_break'))Ext.getCmp(_this.addWinId + 'btn_break').setVisible(false);
				}else{
					Ext.getCmp(_this.addWinId + 'btn_webview').setVisible(true);
					//if(_this.audit)Ext.getCmp(_this.addWinId + 'btn_save').setVisible(false);
					if(Ext.getCmp(_this.addWinId + 'btn_audit'))Ext.getCmp(_this.addWinId + 'btn_audit').setVisible(true);
					if(Ext.getCmp(_this.addWinId + 'btn_break'))Ext.getCmp(_this.addWinId + 'btn_break').setVisible(true);
				}
			}
			
			var addWin = Ext.getCmp(_this.addWinId);
			if(addWin){
				setBtns();
				addWin.setTitle("活动管理");
				addWin.show();
				return;
			}
			
			var city_Comb = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : _this.cityStore,
				fieldLabel : '城市',
				id : _this.addWinId + 'C_CITYID',
				name : 'C_CITYID',
				value:'0',
				allowBlank : false,
				mode : 'local',
				displayField : 'C_CNAME',
				valueField : 'C_ID',
				resizable : true,
				//editable : false,
				anchor : '90%',
				selectOnFocus : true 
			});
			
			/*var Industy_Comb = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : _this.IndustyStore,
				fieldLabel : '行业',
				id : _this.addWinId + 'C_INDUSTRYID',
				name : 'C_INDUSTRYID',
				value:'0',
				allowBlank : false,
				editable : false,
				mode : 'local',
				displayField : 'C_NAME',
				valueField : 'C_ID',
				resizable : true,
				editable : false,
				anchor : '60%',
				selectOnFocus : true
			});*/
			
			var liveField = _this.createComVarField({fieldLabel:"审核状态",id : _this.addWinId + 'C_ISLIVE',name:'C_ISLIVE' , data:_this.priData.isliveData , anchor : '90%',value:'0',readOnly:true,disabled:true});
			var gradeField = _this.createComVarField({fieldLabel:"小编评分",id : _this.addWinId + 'C_GRADE',name:'C_GRADE' , data:_this.priData.gradeData , anchor : '90%',value:'3'});
			
			var Brand_Comb = new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : _this.BrandStore,
				fieldLabel : '品牌',
				id : _this.addWinId + 'C_BRANDID',
				name : 'C_BRANDID',
				//value:'0',
				allowBlank : false,
				//editable : true,
				mode : 'local',
				displayField : 'C_CNAME',
				valueField : 'C_ID',
				resizable : true,
				anchor : '90%',
				selectOnFocus : true
			});
			var activityProperty=new Ext.form.ComboBox( {
				typeAhead : true,
				loadStatus : false,
				triggerAction : 'all',
				store : _this.activityStore,
				fieldLabel : '性质',
				id : _this.addWinId + 'C_PROPERTYID',
				name : 'C_PROPERTYID',
				value:'0',
				allowBlank : false,
				editable : false,
				mode : 'local',
				displayField : 'C_NAME',
				valueField : 'C_ID',
				resizable : true,
				editable : false,
				anchor : '90%',
				selectOnFocus : true
			});
			
			var c_form = new Ext.form.FormPanel({
				id : _this.addWinId + 'addForm',
				frame : true,
				bodyStyle : 'padding:5px;border-bottom:1px solid #99bbe8;',
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
							fieldLabel : '标题',
							id : _this.addWinId + 'C_TITLE',
							name : 'C_TITLE',
							allowBlank : false,
							maxLength : 200,
							width : 100,
							anchor : '90%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '参加URL',
							id : _this.addWinId + 'C_JOINURL',
							name : 'C_JOINURL',
							value:'http://',
							allowBlank : false,
							maxLength : 500,
							anchor : '90%'
						}]
					}]
				},{
					layout : 'column',
					items : [{
						columnWidth : .12,
						layout : 'form',
						items : [{xtype : 'label',html:"活动图片:"}]
					}, {
						columnWidth : .88,
						layout : 'form',
						items : [{
							header:false, 
							html : '<iframe src="'+rootPath+'basedata/pages/lib/baseActivity_ex.jsp" frameborder="0" name="myImgFrame"  id="myImgFrame" width="700" height="100"></iframe>', 
							border:false,
							anchor : '100%'
						}]
					}]
				},{
					layout : 'column',
					items : [{
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'datefield',//
							fieldLabel : '开始时间',
							id : _this.addWinId + 'C_SDATE',
							name : 'C_SDATE',
							format : 'Y-m-d H:i',
							disabled :false,
							allowBlank : false,
							value :new Date().format('Y-m-d')+" 00:00",
							anchor : '90%'
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'datefield',//
							fieldLabel : '结束时间',
							id : _this.addWinId + 'C_EDATE',
							name : 'C_EDATE',
							format : 'Y-m-d H:i',
							disabled :false,
							allowBlank : false,
							value :new Date().add(Date.MONTH, 1).format('Y-m-d')+" 23:59",
							anchor : '90%'
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'textfield',//
							fieldLabel : '来源',
							id : _this.addWinId + 'C_PUBLISHER',
							name : 'C_PUBLISHER',
							maxLength :30,
							anchor : '90%'
						}]
					}]
				},{
					layout : 'column',
					items : [{
						columnWidth : .33,
						layout : 'form',
						items : [gradeField]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'numberfield',//
							fieldLabel : '活动名额',
							id : _this.addWinId + 'C_QUOTA',
							name : 'C_QUOTA',
							//allowBlank : false,
							allowDecimals :false,
							allowNegative:false,
							//minValue :1,
							anchor : '90%'
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [liveField]
					}]
				},{
					layout : 'column',
					items : [{
						columnWidth : .33,
						layout : 'form',
						items : [city_Comb]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [activityProperty]
					}]
				},{
					layout : 'column',
					items : [{
						columnWidth : .33,
						layout : 'form',
						items : [Brand_Comb]
					}, {
						columnWidth : .66,
						layout : 'form',
						items : [{
							xtype : 'textfield',//
							fieldLabel : '备注',
							id : _this.addWinId + 'C_REMARK',
							name : 'C_REMARK',
							maxLength : 100,
							anchor : '98%'
						}]
					}]
				}/*,{
					layout : 'column',
					hidden:true,
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : []
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
						xtype : 'textfield',//
							fieldLabel : '主办方',
							id : _this.addWinId + 'C_SPONSOR',
							name : 'C_SPONSOR',
							anchor : '90%'
						
						}]
					}]
				},{
					layout : 'column',
					hidden:true,
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',//
							fieldLabel : '活动类型',
							id : _this.addWinId + 'C_TYPE',
							name : 'C_TYPE',
							value: '0',
							anchor : '90%'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
						xtype : 'textfield',//
							fieldLabel : '地址',
							id : _this.addWinId + 'C_ADDRESS',
							name : 'C_ADDRESS',
							anchor : '90%'
						
						}]
					}]
				}*/,{
					layout : 'column',
					//labelWidth : 1,
					items : [{
							header:false, 
							html : '<iframe src="'+rootPath+'basedata/pages/lib/editor_ex.jsp" frameborder="0" name="myContentFrame"  id="myContentFrame" width="742" height="600"></iframe>', 
							border:false,
							anchor : '100%'
						}/*{
						xtype : 'htmleditor',//						
						//fieldLabel : '内容',
						id : _this.addWinId + 'C_CONTENT',
						name : 'C_CONTENT',
						allowBlank : false,
						maxLength :6000,
						anchor : '100%',
						height:600
					}*/]
				}
				,{
					layout : 'column',
					hidden:true,
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'hidden',
							id : _this.addWinId + 'C_AUDITPERSON',
							name : 'C_AUDITPERSON',
							anchor : '90%'
						}]
					}]
				}
				]				
			});
			
			var btns = [{
				text : '返回',
	            handler : function() {
	            	addWin.close();
					var grid = Ext.getCmp(_this.gridId);
					grid.store.reload();
	            }
	        },{
	        	text : '保存',
	        	id : _this.addWinId + 'btn_save',
	            handler : function() {
	            	_this.priSaveFun(addWin,{audit:'0'});
	            }
	        }];
	        
	        if(_this.audit){
	        	btns.push( {
		        	text : '审核成功',
		        	id : _this.addWinId + 'btn_audit',
		            handler : function() {
		            	_this.priSaveFun(addWin,{audit:'1'});
		            }
		        });
		        btns.push( {
		        	text : '审核驳回',
		        	id : _this.addWinId + 'btn_break',
		            handler : function() {
		            	_this.priSaveFun(addWin,{audit:'2'});
		            }
		        });
	        }
	        btns.push( {
	        	text : '预览',
	        	id : _this.addWinId + 'btn_webview',
	            handler : function() {
	            	open_Dialog(_this.webUrl);
	            }
	        });
			
			addWin = new Ext.Window({ 
				id : _this.addWinId,
				title: '活动管理',
				plain:true,	 
	            modal : true,           
	            draggable : true,
	            resizable : true,
	            autoScroll : true,
	            width : 800,
	            height:mainHeigth+5,
				layout : 'form',
				frame : true,
				items : [c_form],
				buttonAlign: 'center',
				buttons : btns
			});
			setBtns();
			addWin.show();
		}
	}
};

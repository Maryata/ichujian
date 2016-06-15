//-----------------客制化公共引入-----------------------------------
Ext.namespace("Ext.ux");
Ext.namespace("Ext.CustCode");
Ext.namespace("Ext.ux.FormCtrlCustomAction");
Ext.namespace("Ext.ux.FormCtrlButtonCustomAction");

/**
 * 客制化工具类
 */
Ext.CustCode.CustomCodeUtils = function() {
 	var customCodeLog;
 	try{
 		customCodeLog = new Logger('CustomCodeUtils', 'C:\\evoicelog\\CCLog.txt');
 		//customCodeLog.debug(" customCodeLog 初始化成功！"); 
 	}catch(e){
 		alert("创建日志对象失败,请检查IE配置!");
 	}
 	return {
		dbhelp : new Ext.ux.FormDataBaseHelp(),
		getDataByCodeURL : 'workflow/formdesign/formctrl!getDataByCode.action',
		getCtrlGridDataListURL : Ext.ux.FormDesignBaseUrl + 'formdesign/formctrl!getCtrlGridDataList.action',	
		/**
 		 * 异步方法调用系统参数
 		 */
 		callparames:function(paramCode,params,callback,scope){
 			var thisClass = this;
 			var callparams = {};
 			
			callparams.paramCode = paramCode;
			callparams.params = Ext.encode(params);
			Ext.Ajax.request({
			   url: thisClass.getDataByCodeURL,
			   scope:scope,
			   timeout : 300000,
			   success: function(conn,response,params){
			   		if(callback)
			   		{
			   			callback.call(this,Ext.decode(conn.responseText));
			   		}
			   },
			   params: callparams
			});	
 		},
 		/**
 		 * 同步方法调用系统参数
 		 */
 		callparamesSync:function(paramCode,params,callback,scope){
 			var thisClass = this;
 			var callparams = {};
 			
			callparams.paramCode = paramCode;
			callparams.params = Ext.encode(params);
			
			var conn = Ext.lib.Ajax.getConnectionObject().conn;
			conn.open("post", thisClass.getDataByCodeURL, false);// false表示同步调用
			conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			conn.send("paramCode="+callparams.paramCode+"&params="+callparams.params);//
			var data = Ext.util.JSON.decode(conn.responseText);
			if(callback)
	   		{
	   			callback.call(this,conn.responseText!=null&& conn.responseText!="" ?Ext.decode(conn.responseText):{});
	   		}
 		},
 		/**
 		 * 同步方法调用系统参数
 		 * 返回数据JSON
 		 */
 		callparamesSyncRetData:function(paramCode,params){
 			var thisClass = this;
 			var callparams = {};
			callparams.paramCode = paramCode;
			callparams.params = Ext.encode(params);
			
			var conn = Ext.lib.Ajax.getConnectionObject().conn;
			conn.open("post", thisClass.getDataByCodeURL, false);// false表示同步调用
			conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			conn.send("paramCode="+callparams.paramCode+"&params="+callparams.params);//
			var data = Ext.util.JSON.decode(conn.responseText);
			return data;
 		},
 		/**
 		 * update save
 		 */
 		update : function(sql,sqlparams,jdbc,callback){
			Ext.Ajax.request({
			   url: Ext.ux.FormDesignBaseUrl + 'formdesign/formctrl!saveSql.action',
			   scope:this,
			   timeout : 60000,
			   success: function(conn,response,params){
			   		if(callback)
			   		{
			   			callback.call(this,Ext.decode(conn.responseText));
			   		}
			   },
			   params: {
			   	sql:sql,
			   	sqlparams:sqlparams,
			   	jdbc:jdbc
			   }
			});
		},
		
 		/**
 		 * 记录日志信息debug
 		 * msg : 日志内容
 		 * serverLog ：true 在服务器端打印日志
 		 */
 		debugInfo: function(msg,serverLog){
 			if(customCodeLog){
 				try{
 					customCodeLog.debug(msg);
 				}catch(e){}
 			}
 		},
 		/**
 		 * 记录日志信息error
 		 */
 		errorInfo: function(msg,ex,serverLog){
 			if(customCodeLog){
 				try{
 					customCodeLog.error(msg,ex);
 				}catch(e){}
 			}
 		},
 		
 		/**
 		 * 获取函数名称
 		 */
 		getFunctionName : function (func) {
 			var temp="";
		    if ( typeof func == 'function' || typeof func == 'object' ) {
		        var name = ('' + func).match(/function\s*([\w\$]*)\s*\(/);
		        temp = name && name[1];
		        if((temp==null || trim(temp)=="")){
					return func.prototype.valueOf();		        
		        }
		    }
		    return temp;
		},
		/**
		 * 获取js方法调用栈
		 */
 		trace : function() {
 			if(window.console && window.console.trace){
 				alert(window.console.trace());
 				return window.console.trace();
 			}else{
 				var stack = [],
	            caller = arguments.callee.caller;
		 		var loopIndex = 0;
		        while (caller && loopIndex<=20) {//最多抽取20层
		            stack.unshift(this.getFunctionName(caller));
		            caller = caller && caller.caller;
		            loopIndex++;
		        }
		 
		        return '---->> functions on stack:' + '\n' + stack.join('\n');
 			}
	        
	    },
	    getCallStack : function () {
		    var stack = [];
		    var fun = getCallStack;
		    while (fun = fun.caller) {
		        stack.push(fun);
		    }
		    return stack;
		},
		
		//判断当前 funId 是否存在与 rights 中
		getIsUse : function(rights,funId) {
			try{
				var thisObject = this;
				var isUse = false;
				if(rights !=null ){
					for (var i = 0; i < rights.length; i++) {
						if (rights[i] == funId) {
							isUse = true;
							break;
						}
					}
				}
				return isUse;
			}catch(e){
				return false;
			}
		},
		
		/**
		 * 判断有没有角色权限
		 * @param {} userRoleCode 登陆用户所有角色
		 * @param {} code 某角色的code
		 * @return {Boolean} 是否有角色权限
		 */
		containRole : function(userRoleCode,code){
			if(!userRoleCode || !code){
				return false;
			}
			var rCodes = userRoleCode.split(',');
			var currCode = code.split(',');
			for(var i=0;i<rCodes.length;i++){
				for(var c=0;c<currCode.length;c++){
					if(!rCodes[i] || !currCode[c] ){
						continue;
					}
					if(rCodes[i]==currCode[c]){
						return true;
					}
				}
			}
			return false;
		},
		/**
		 * 清除当前表单项上的所有非法styles/messages(样式/消息) 
		 * @param ctrls
		 * @returns
		 */
		clearInvalidAll : function(ctrls){
			//
			for(o in ctrls ){
				try{
					if(ctrls[o].clearInvalid){
						ctrls[o].clearInvalid();
					}
				}catch(e){}
			}
			//this.clearInvalid();
		},
		valiFun : function(ctrls,files){
			var thisClass = this;
			if(!ctrls || !files){
				return false;
			}
			//alert('valifun');
			var item,value;
			for(var i=0;i<files.length;i++){
				item = ctrls[files[i].o];
				if(!item){
					alert('请在该按钮函数参数 添加['+files[i].o+']表单项 参数!');
					continue;
				}
				value = item.getValue();
				if( typeof(value) == "undefined"){ // item.xtype == formctrltimefield'
					value = item.getRawValue();
				}
				
				if( !files[i].blank && (!value || value.trim()=='')){
					thisClass.alertInfo((files[i].m ? files[i].m : files[i].o)+'不能为空!');
					try{
						item.getExtItem().focus(true);
					}catch(e){
					}
					return false;
				}else if(value.length>item.datamaxlength ){
					thisClass.alertInfo((files[i].m ? files[i].m : files[i].o)+'过长,超过'+item.datamaxlength+'个字符!');
					try{
						item.getExtItem().focus(true);
					}catch(e){
					}
					return false;
				}					
				
			}
			return true;
		},
		 
		conditionParams : {
			xtype :['formctrlnumberfield','formctrltextbox','formctrlcombo','formctrldbcombo','formctrldatabindcombo','formctrldatepicker','formctrldbcombo']
		},
		
		/**
		* sql in 处理
		*/		
		buildInSql : function (ArrValue){
			var sqlStr = "";
			if(ArrValue!=null && ArrValue.length!=0){
				sqlStr=" ("
				for(var i=0;i<ArrValue.length;i++){
					if(i>0){
						sqlStr+=",";
					}
					sqlStr+="?";
				}
				sqlStr+=") ";
			}
			return sqlStr;
		},
		
		/**
		 * 
		 * @param ctrls	
		 * @param tbPrefix : 栏位表头
		 * @Param rejects [剔除对象]
		 * @returns
		 */
		setConditionSql : function(ctrls,tbPrefix,rejects){
			var thisClass = this;
			if(!ctrls){
				return null;
			}
			var sql='',params='';
			var item,value,seloption,datafield;
			for(pn in ctrls){
				item = ctrls[pn];
				if(!valiXtypeNoSet(item,pn)){					
					continue;
				}
				value = item.getValue(); seloption = item.seloption; datafield = (tbPrefix ? tbPrefix+'.' :'') + item.datafield;
				if(item.format && item.format=='Y-m-d H:i'){// format "Y-m-d H:i"
					if(seloption=='>' || seloption=='>='){
						value +=':00';
					}else if(seloption=='<' || seloption=='<='){
						value +=':59';
					}
				}
				// seloption set ext like
				if(seloption == "like"){
					value = "%" + value + "%";
				}else if(seloption == "Llike"){
					value = "%" + value;
				}else if(seloption == "Rlike"){
					value = value + "%";
				}				
				// datafield tbPrefix				
				sql += " and "+datafield+' '+seloption+" ?";
				params += "{params:'"+ value+ "'},";
			}
			
			if(params && params.length>1){
				params = params.substr(0,params.length-1);
				
				//Ext.CustCode.CustomCodeUtils.debugInfo('--sql:'+sql);
				//Ext.CustCode.CustomCodeUtils.debugInfo('--params:'+params);
				return {
					params : params,
					sql : sql
				};
			}else{
				return null;
			}
			
			/**
			 * false : 不加载当前条件
			 * @param item
			 * @param pn
			 * @returns
			 */
			function valiXtypeNoSet (item,pn){
				if(typeof item == 'function' || !item.xtype || !item.datafield || !item.getValue()){
					return false;
				}
				if(rejects){//剔除 rejects
					for(var i=0;i<rejects.length;i++){
						if(pn == rejects[i] ){
							return false;
						}
					}
				}
				//条件范围选项
				var ret = false;
				for(var c=0; c<thisClass.conditionParams.xtype.length; c++){
					if(item.xtype==thisClass.conditionParams.xtype[c]){
						ret = true;
						break;
					}
				}
				if(ret){//全部条件选项
					var value=item.getValue();
					if(((item.xtype=='formctrlcombo' || item.xtype=='formctrldbcombo' || item.xtype=='formctrldatabindcombo' )&& (value=='All' || value=='-1' ) )){
						//Ext.CustCode.CustomCodeUtils.debugInfo('not in: '+item.name+',-- '+item.xtype+',-- '+value);
						ret = false;
					}
				}else{
					//Ext.CustCode.CustomCodeUtils.debugInfo('xtype not in: '+item.name+',-- '+item.xtype);
				}
				return ret;
			}
		},
		/**
		 * 设置扩展sql设置
		 * @param ctrls
		 * @param tbPrefix
		 * @param contains 包含参数
		 * @returns
		 */
		setExtConditionSql : function(ctrls,tbPrefix,contains){
			var thisClass = this;
			if(!ctrls || !contains){
				return null;
			}
			var sql='',params='';
			var item,value,seloption,datafield;
			
			for(var i=0;i<contains.length;i++){
				if(!ctrls[contains[i]]){
					continue;
				}
				item = ctrls[contains[i]];
				value = item.getValue(); 
				if(!value || value=='' || !item.seloption){
					continue;
				}
				if(((item.xtype=='formctrlcombo' || item.xtype=='formctrldbcombo' || item.xtype=='formctrldatabindcombo' )&& (value=='All' || value=='-1' ) )){
					//Ext.CustCode.CustomCodeUtils.debugInfo('not in: '+item.name+',-- '+item.xtype+',-- '+value);
					continue;
				}
				seloption = item.seloption; datafield = (tbPrefix ? tbPrefix+'.' :'') + item.datafield;
				
				if(item.format && item.format=='Y-m-d H:i'){// format "Y-m-d H:i"
					if(seloption=='>' || seloption=='>='){
						value +=':00';
					}else if(seloption=='<' || seloption=='<='){
						value +=':59';
					}
				}
				// seloption set ext like
				if(seloption == "like"){
					value = "%" + value + "%";
				}else if(seloption == "Llike"){
					value = "%" + value;
				}else if(seloption == "Rlike"){
					value = value + "%";
				}				
				// datafield tbPrefix				
				sql += " and "+datafield+' '+seloption+" ?";
				params += "{params:'"+ value+ "'},";
			}
			if(params && params.length>1){
				//Ext.CustCode.CustomCodeUtils.debugInfo('-ext-sql:'+sql);
				//Ext.CustCode.CustomCodeUtils.debugInfo('-ext-params:'+params);
				return {
					params : params,
					sql : sql
				};
			}else{
				return null;
			}
		},
		/**
		 * 预处理sql：转换格式打印日志
		 * @param sql
		 * @param params
		 * @returns
		 */
		converseSql : function(sql,params){
			var thisClass = this;
			if(!sql){
				return null;
			}
			if(!params){
				thisClass.debugInfo('--converseSql-noparams:'+sql);
				return sql;
			}
			var params = Ext.util.JSON.decode(params);
			for(i in params){
                if(params[i]){
                    sql = sql.replace('?',"'"+params[i].params+"'");
                }
			}
			thisClass.debugInfo('--converseSql:'+sql);
			return sql;
		},
		/**
		 * clear 
		 * @param ctrls
		 * @returns
		 */
		clearCondition : function(ctrls){
			var thisClass = this;
			if(!ctrls){
				return;
			}
			//alert('clear');
			var item;
			for(pn in ctrls){
				if(typeof ctrls[pn] == 'function'){continue;}
				item = ctrls[pn];
				if(item.xtype =='fromctrldatabindgrid' ){
					item.getStore().removeAll();
					continue;
				}else if(item.xtype =='formctrlbutton'){
                    continue;
                }
				//Ext.CustCode.CustomCodeUtils.debugInfo('-- clear: '+item.name+',-- '+item.xtype+',-- '+item.getValue());
				try{
					if(item.initialConfig.value && item.initialConfig.value!=''){
						if(item.store){//store //defultrows
							item.getStore().clearFilter(true);
						}
						item.setValue(item.initialConfig.value);
						
					}else{
						item.setValue('');
						if(item.xtype =='fromctrltreecombo'){//树结构,需清空hiddenValue		
							item.hiddenValue = '';
						}
					}
					item.clearInvalid();//clear
				}catch(e){alert('Ext.CustCode.CustomCodeUtils.clearCondition: clear['+pn+'] error,'+e.desc);}
			}			
		},
		
		alertInfo : function(msg,title){
			//alert(msg);
			//return;
			Ext.Msg.show({
				title : title ? title :'',
				msg : msg,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		},
		/**
		 * add date 日期计算 
		 * @param val 日期		 
		 * @param interval eg: Date.DAY
		 * @param addVal eg ：2	
		 * @param format  		 
		 * @returns 
		 */
		addDateFun : function(val,interval,addVal ,format){
			var dateVal;
			if(format){
				if (typeof(val) == 'object') {
					dateVal = Ext.util.Format.date(val.add(interval, addVal), format);				
				} else {
					dateVal = Ext.util.Format.date( Date.parseDate(val,format).add(interval, addVal), format);
				}
			}else{
				if (typeof(val) == 'object') {
					dateVal = val.add(interval, addVal);				
				} else {
					dateVal = new Date(val.replace(/-/g, "/").add(interval, addVal));
				}		
			}
			return dateVal;		
		},
		/**
		 * 比较日期 
		 * @param t_s 开始时间
		 * @param t_e 结束时间
		 * @returns Boolean
		 var d2 = new Date(Ext.util.Format.date(new Date(), 'Y/m/d'));
		 */
		dateCompare : function (t_s,t_e ,format){
			if(!t_s || !t_e || t_s=='' || t_e==''){
				return true;
			}
			if (typeof(t_s) != 'object') {
				t_s = new Date(t_s.replace(/-/g, "/")); 
			}
			if (typeof(t_e) != 'object') {
				t_e = new Date(t_e.replace(/-/g, "/")); 
			}
			
			if( (Date.parse(t_s)-Date.parse(t_e) > 0)){
				return false;
			}
			return true;
		},
		
		/**
		 * 日期验证函数		
		 * @param ctrls 表单对象
		 * @param dateObjs [{s:'',e:'',msg:'',ext:{}}] s\e 起止对象；msg;提示信息前缀 ; 
				ext.s_d:默认; D_now、M_first:getFirstDateOfMonth () 、M_last :getLastDateOfMonth () 
		 * @param ext 扩展json {}
		 * @returns Boolean
		 var d2 = new Date(Ext.util.Format.date(new Date(), 'Y/m/d'));
		 */
		render_dateVailFun : function (ctrls,_s,_e ,ext){
			try{
				var thisClass = this;
				//alert('render_dateVailFun:');				
				var s_obj = ctrls[_s.o];
				var e_obj = ctrls[_e.o];
				
				var msg = (ext && ext.msg) ? ext.msg :'';				
				//
				if(_s.v){
					s_obj.initialConfig.value = thisClass.getDfDate(_s.v);
					if(s_obj.initialConfig.value!=''){
						if(s_obj.format=='Y-m-d H'){
							s_obj.initialConfig.value = s_obj.initialConfig.value.format("Y-m-d")+" 00";
						}else if(s_obj.format=='Y-m-d H:i'){
							s_obj.initialConfig.value = s_obj.initialConfig.value.format("Y-m-d")+" 00:00";
						}else if(s_obj.format=='Y-m-d H:i:s'){
							s_obj.initialConfig.value = s_obj.initialConfig.value.format("Y-m-d")+" 00:00:00";
						}
					}
					s_obj.setValue(s_obj.initialConfig.value);
				}
				if(_e.v){
					e_obj.initialConfig.value = thisClass.getDfDate(_e.v,e_obj.format);
					if(e_obj.initialConfig.value!=''){
						if(e_obj.format=='Y-m-d H'){
							e_obj.initialConfig.value = e_obj.initialConfig.value.format("Y-m-d")+" 23";
						}else if(e_obj.format=='Y-m-d H:i'){
							e_obj.initialConfig.value = e_obj.initialConfig.value.format("Y-m-d")+" 23:59";
						}else if(e_obj.format=='Y-m-d H:i:s'){
							e_obj.initialConfig.value = e_obj.initialConfig.value.format("Y-m-d")+" 23:59:59";
						}
					}
					e_obj.setValue(e_obj.initialConfig.value);
				}
				
				s_obj.validator = function(){
					return form_DateVail(s_obj,e_obj,true,msg);
				};
				e_obj.validator = function(){
					return form_DateVail(s_obj,e_obj,false,msg);
				};

				function form_DateVail(s_obj,e_obj,isS_E,msg,ext){
					//alert(s_obj +'--'+s_obj.getValue());
					var format = s_obj.format;
					var _ret = thisClass.dateCompare(s_obj.getValue(),e_obj.getValue(),format);
					if(!_ret){
						return  msg+(isS_E ? '开始时间不能大于结束时间' : '结束时间不能小于开始时间');		
					}					
					/**
					if(ext && ext.maxspan){
						if(s_obj.getValue().add(Date.MONTH,ext.maxspan) - e_obj.getValue() <0 ){
							return msg+'时间跨度不能大于'+ext.maxspan+'个月';
						}
					}**/		
					return true;	
				}			
				
			}catch(e){alert('render_dateVailFun.error,'+e.desc);}
		
		},		
		/**
		*ext.s_d:默认; D_now、M_first:getFirstDateOfMonth () 、M_last :getLastDateOfMonth () 
		*/
		getDfDate : function(interval,format){
			if(interval=='D_now'){
				return new Date();
			}else if(interval=='M_first'){
				return new Date().getFirstDateOfMonth ();
			}else if(interval=='M_last'){
				return new Date().getLastDateOfMonth ();
			}
			else if(interval.indexOf('+')>-1 || interval.indexOf('-')>-1){
				var D_F, D_date;				
				if(interval.indexOf('+')>-1){
					var D_ARR = interval.split('+');	
					D_F = D_ARR[0].toLocaleLowerCase();
					D_NUM = +parseInt(D_ARR[1]);
					
					D_date = new Date().add(D_F, D_NUM );					
				}else{
					var D_ARR = interval.split('-');	
					D_F = D_ARR[0].toLocaleLowerCase();
					D_NUM = -parseInt(D_ARR[1]);
					
					D_date =  new Date().add(D_F, D_NUM );
				}
				return D_date ;
			}	
			return '';				
		},
		
		
		
		render_dateAlterObjsFun : function(_obj , _pre, _last){
			var _this = this;
			var msg;
			if(_pre && _pre.obj){
				var is_val = _this.dateCompare(_pre.obj.getValue(),_obj.getValue());
				if( !is_val){
					msg = _pre.msg;
				}
			}
			if(!msg && _last && _last.obj ){
				var is_val = _this.dateCompare(_obj.getValue(),_last.obj.getValue());
				if(!is_val){
					msg = _last.msg;
				}
			}
			if(msg) _this.alertInfo(msg);
			
			return true;
		},
		
		/**
		 * 计算两个时间点之间的时间差并格式化
		 */
		formatBetweenTime :function(startTime,endTime) {//将秒数换算成x天x时x分x秒
			var str = "";
			  var hour = 0;
			  var minute = 0; 
			  var second = 0;
			  var days = 0;
			  second = (Date.parseDate(endTime, "Y-m-d H:i:s", true)-Date.parseDate(startTime, "Y-m-d H:i:s", true))/1000;
			  if (second > 60) {
				   minute = second / 60; 
				   second = second % 60; 
			   }         
			  if (minute > 60) {
				   hour = minute / 60;
				   minute = minute % 60;
			   }
			  if (hour > 24) {
				  days = hour / 24;
				  hour = hour % 24;
			   }
			return  str=(parseInt(days)+"天"+parseInt(hour) + "小时" + parseInt(minute)  + "分钟" + parseInt(second) + "秒");
		},
		formatMinutes : function(second){
			second = parseInt(second);
			var hour = 0;
			var minute = 0;
			//var second = 0;
			if (second > 60) {
				minute = second / 60; 
				second = (second % 60); 
			}    
		    if (minute > 60) {
			   hour = (minute / 60);
			   minute = minute % 60;
		    }
			return  ((hour<10 ? '0' : '' )+parseInt(hour) + ":" + (minute<10 ? '0' : '')+parseInt(minute)  + ":" + (second<10 ? '0' : '')+parseInt(second));
		},
		
		
		/**
		 * 获取系统权限
		 * @param moduleId
		 * @param callback
		 * @param scope
		 */
		getSysRights : function (moduleId,callback,scope){
			Ext.Ajax.request({
				url : '../treeLib/depart!getRights.action',
				timeout : 60000,
				scope : scope,
				success: function(conn,response,params){
					if(callback){
						callback.call(this,Ext.decode(conn.responseText));
					}
				},params : {
					moduleId : moduleId
				}
			});
		},
		
		//通过获取权限
		getSysRightSyncs : function (moduleId){
			var conn = Ext.lib.Ajax.getConnectionObject().conn;
			conn.open("post", '../treeLib/depart!getRights.action', false);// false表示同步调用
			conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			conn.send("moduleId="+moduleId);//
			var data = Ext.util.JSON.decode(conn.responseText);
			return data;
		},
		
		//根据已经获取的权限进行设置
		setSysRightFun : function(btns,sysFun){
			var rights = sysFun.funcs;
			var lastBtns = new Array();
			var isSave = false;
			var isEdit = false;
			// 先判断用户是否为管理员,如果是管理员则开放全部权限
			if (sysFun.isAdmin != null && sysFun.isAdmin) {
				lastBtns = btns;
				isSave = true;  
				isEdit = true;    
			} else if (rights == null || rights.length == 0) {
				Grandsys.sys.Utils.alertInfo(i18n.common_Utils.VAR_GET_POWER_ERROR);
			} else {
				for (var i = 0; i < btns.length; i++) {   
					if (btns[i].id != null) {
						for (var j = 0; j < rights.length; j++) {
							if (rights[j] == btns[i].id) {
								lastBtns.push(btns[i]);
							}
						}
					}
				}
			}
			return {
				btns : lastBtns, // 返回显示的按钮
				editAble : isEdit
			// 返回是否有修改权限
			};
		},
		//判断当前id是否在权限范围内
		hasSysRightFun : function(id,sysFun){
			var rights = sysFun.funcs;
			if (sysFun.isAdmin != null && sysFun.isAdmin) {
				return true;
			} else if (rights == null || rights.length == 0) {
				return false;
			} else {
				for (var j = 0; j < rights.length; j++) {
					if (rights[j] == id) {
						return true;
					}
				}
			}
			return false;
		},
		
		getUserFunc : function(btns, sysFun, editBtnId, classKey,extOp) {
			var rights = sysFun.funcs;
			var lastBtns = new Array();
			var isSave = false;
			var isEdit = false;
			// 先判断用户是否为管理员,如果是管理员则开放全部权限

			if (sysFun.isAdmin != null && sysFun.isAdmin) {
				lastBtns = btns;
				isSave = true;
				isEdit = true;
				if (lastBtns) {
					for (var i = 0; i < lastBtns.length; i++) {
						if (lastBtns[i].id != null) {
							if (classKey && classKey != '') {
								lastBtns[i].id += '_' + classKey;
							}
						}
					}
				}
			} else if (rights == null || rights.length == 0) {
				// Grandsys.sys.Utils.alertWarn('获取权限出错');
			} else { // 不是管理员就判断有哪些权限
				for (var i = 0; i < rights.length; i++) {
					// 判断用户是否可编辑
					if (rights[i] == editBtnId) {
						isEdit = true;
						break;
					}
				}
				for (var i = 0; i < btns.length; i++) {
					if (btns[i].id ) {
						for (var j = 0; j < rights.length; j++) {
							if (rights[j] == btns[i].id) {
								//复制出新的对象,避免原数组对象的内容被修改掉了.
								lastBtns.push(Ext.apply(new Object() ,btns[i]));
								if (classKey && classKey != '') {
									lastBtns[lastBtns.length-1].id += '_' + classKey;
								}
								if (i != btns.length - 1) {
									lastBtns.push('-');
								}
								break;
							}
						}
					}else if(btns[i]!="-"){//由于这里会加- ,去掉外面传进来的-
						lastBtns.push(btns[i]);
					}
				}
			}

			return {
				btns : lastBtns, // 返回显示的按钮
				editAble : isEdit
			// 返回是否有修改权限
			};
		},
		
		/**
		 * 设置权限
		 * @param moduleId
		 * @param btns : [{o:ctrls['btn_UpdateCC'],id:'a58ea75a-628e-465f-ba57-45c07e6d3be1'}]
		 * @returns
		 */
		setSysRights : function (moduleId,btns){
			this.getSysRights(moduleId, function(res){
				for(var m=0;m<btns.length;m++){
					var isHas = false;
					if(res.funcs){
						for(var i=0;i<res.funcs.length;i++){
							if(btns[m].id==res.funcs[i]){
								isHas = true;
								break;
							}
						}
					}else{
						//is this admin;
						if(res.isAdmin){
							isHas = true;
						}
					}
					btns[m].o.setVisible(isHas ? true : false);
				}
			}, this);
//			Ext.Ajax.request({
//				url : '../treeLib/depart!getRights.action',
//				timeout : 60000,
//				success: function(conn,response,params){
//					var res = Ext.decode(conn.responseText);
//					}
//				},params : {
//					moduleId : moduleId
//				}
//			});
		},
		
		getMemcachData : function (key,callback,scope){
			Ext.Ajax.request({
				url : 'workflow/formdesign/formctrl!getMemcachData.action',
				timeout : 60000,
				scope : scope,
				success: function(conn,response,params){
					if(callback){
						callback.call(this,Ext.decode(conn.responseText));
					}
				},params : {
					key : key
				}
			});
		}
		
 	};
 }();
 
/**
 * 
 */
Ext.CustCode.EventUtils = function(){
	return {
		/**
		 * 兼容ie和火狐的JS复制到粘贴板
		 * @param txt
		 * @returns
		 */
		copyToClipboard : function(txt){
			if(window.clipboardData) {
	             window.clipboardData.clearData();  
	             window.clipboardData.setData("Text", txt);	             
		     } else if(navigator.userAgent.indexOf("Opera") != -1) {  
		          window.location = txt;		          
		     } else if (window.netscape) {
		          try {  
		               netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");  
		          } catch (e) {  
		        	  Ext.CustCode.CustomCodeUtils.alertInfo("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
		               return;
		          }  
		          var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);  
		          if (!clip)  
		               return;  
		          var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);  
		          if (!trans)  
		               return;  
		          trans.addDataFlavor('text/unicode');  
		          var str = new Object();  
		          var len = new Object();  
		          var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);  
		          var copytext = txt;  
		          str.data = copytext;  
		          trans.setTransferData("text/unicode",str,copytext.length*2);  
		          var clipid = Components.interfaces.nsIClipboard;  
		          if (!clip)  
		               return false;  
		          clip.setData(trans,null,clipid.kGlobalClipboard);  
		     }			
			//alert("复制成功！") ;
		},
		////自动关闭提示框 
		alertInfo : function(str,titleStr,time){
			var msgw,msgh,bordercolor;  
		    msgw=350;//提示窗口的宽度  
		    msgh=80;//提示窗口的高度  
		    titleheight=25;//提示窗口标题高度  
		    bordercolor="#336699";//提示窗口的边框颜色  
		    titlecolor="#99CCFF";//提示窗口的标题颜色  
		    var sWidth,sHeight;  
		    //获取当前窗口尺寸  
		    sWidth = document.body.offsetWidth;  
		    sHeight = document.body.offsetHeight;  
		    //背景div  
		    var bgObj=document.createElement("div");  
		    bgObj.setAttribute('id','alertbgDiv');  
		    bgObj.style.position="absolute";  
		    bgObj.style.top="0";  
		    bgObj.style.background="#E8E8E8";  
		    bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";  
		    bgObj.style.opacity="0.6";  
		    bgObj.style.left="0";
		    bgObj.style.width = sWidth + "px";  
		    bgObj.style.height = sHeight + "px";  
		    bgObj.style.zIndex = "10000";  
		    document.body.appendChild(bgObj);  
		    //创建提示窗口的div  
		    var msgObj = document.createElement("div");
		    msgObj.setAttribute("id","alertmsgDiv");  
		    msgObj.setAttribute("align","center");  
		    msgObj.style.background="white";  
		    msgObj.style.border="1px solid " + bordercolor;  
		    msgObj.style.position = "absolute";  
		    msgObj.style.left = "50%";  
		    msgObj.style.font="12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif";  
		    //窗口距离左侧和顶端的距离   
		    msgObj.style.marginLeft = "-225px";  
		    //窗口被卷去的高+（屏幕可用工作区高/2）-150  
		    msgObj.style.top = document.body.scrollTop+(window.screen.availHeight/2)-150 +"px";  
		    msgObj.style.width = msgw + "px";  
		    msgObj.style.height = msgh + "px";  
		    msgObj.style.textAlign = "center";  
		    msgObj.style.lineHeight ="25px";  
		    msgObj.style.zIndex = "10001";  
		    document.body.appendChild(msgObj);  
		    //提示信息标题  
		    var title=document.createElement("h4");  
		    title.setAttribute("id","alertmsgTitle");  
		    title.setAttribute("align","left");  
		    title.style.margin="0";  
		    title.style.padding="3px";  
		    title.style.background = bordercolor;  
		    title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);";  
		    title.style.opacity="0.75";  
		    title.style.border="1px solid " + bordercolor;  
		    title.style.height="18px";  
		    title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif";  
		    title.style.color="white";  
		    title.innerHTML= titleStr ? titleStr : "提示信息"; //
		    document.getElementById("alertmsgDiv").appendChild(title);  
		    //提示信息  
		    var txt = document.createElement("p");  
		    txt.setAttribute("id","msgTxt");  
		    txt.style.margin="16px 0";
		    txt.innerHTML = str;  
		    document.getElementById("alertmsgDiv").appendChild(txt);
		    
		    if(time){
		    	//设置关闭时间  
			    window.setTimeout(function(){
			    	closewin();	
			    },time); 
		    }
		    function closewin(){
			    document.body.removeChild(document.getElementById("alertbgDiv"));  
			    document.getElementById("alertmsgDiv").removeChild(document.getElementById("alertmsgTitle"));  
			    document.body.removeChild(document.getElementById("alertmsgDiv"));
			}
		}
	};
}();

 
//--------------------------------------------------
//
Ext.util.Format.LinkRendererBlue = function(){
    return function(value, metadata , record, rowIndex, colIndex, store){
		return "<font color='blue'><u onclick='#'> "+value+" </u></font>";
    };
};


 
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
};



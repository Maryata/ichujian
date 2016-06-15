
Ext.BLANK_IMAGE_URL = 'resources/images/default/s.gif';
//解决退格键返回上一页的问题
document.onkeydown = function(){
	try{
	   //过滤 F5 F6，如果只禁用F5，再按了F6再按F5还是会刷新
	   /*if(event.keyCode==116 || event.keyCode==117){
		    event.keyCode=0;
	  	    event.returnValue=false;		 	   
	   }*/
		if((event.keyCode == 8) && 
			 (event.srcElement.type != "text") && 
			 (event.srcElement.type != "textarea") && 
			 (event.srcElement.type != "password")){
	 		 return false;
			//alert('禁用退格键');
		}else if( event.srcElement && event.srcElement.readOnly){
			//alert(111);
			return false;
		}else{
			return true;
		}
	}catch(e){
	
	}	
}

/**
 * @说明:Extjs升级至3.2 bug修正.（非内存泄漏）
 * @日期:2010-06-29 16:00
 * @最近改动说明:
 * @1.解决下拉框readOnly属性在Extjs3.2处理方式不同于Extjs2.2的问题
 * @2.添加Extjs3.2的同步请求补丁
 * @3.ExtJS3.2中无MenuButton及TextField allowBlank : true 失效的问题
 * @4.07-27日改动：解决columnTree引起的Ext.extend的错误问题.
 */    

/** **************Fix For Extjs3.2********************** */
// 解决3.2没有同步请求的问题
// private
function createXhrObject() {
	var activeX = ['MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP', 'Microsoft.XMLHTTP'];
	var http;
      
	try {
		http = new XMLHttpRequest();
	} catch (e) {
		for (var i = 0; i < activeX.length; ++i) {
			try {
				http = new ActiveXObject(activeX[i]);
				break;
			} catch (e) {
			}
		}
	} finally {

		return {
			conn : http
		};
	}
}
// 修复ExtJS3.2中同步请求方法
// Ext.namespace('Ext.lib.Ajax');
Ext.lib.Ajax.getConnectionObject = function() {
	var o;
	try {
		o = createXhrObject();
	} catch (e) {
	} finally {
		return o;
	}
}

Ext.apply(Ext, {
	extend : function() {
		// inline overrides
		var io = function(o) {
			for (var m in o) {
				this[m] = o[m];
			}
		};
		var oc = Object.prototype.constructor;

		return function(sb, sp, overrides) {
			if (typeof sp == 'object') {
				overrides = sp;
				sp = sb;
				sb = overrides.constructor != oc
						? overrides.constructor
						: function() {
							sp.apply(this, arguments);
						};
			}
			if (!sp) {
				// alert("sp"+sp);
				return;
			}

			var F = function() {
			}, sbp, spp = sp.prototype;

			F.prototype = spp;
			sbp = sb.prototype = new F();
			sbp.constructor = sb;
			sb.superclass = spp;
			if (spp.constructor == oc) {
				spp.constructor = sp;
			}
			sb.override = function(o) {
				Ext.override(sb, o);
			};
			sbp.superclass = sbp.supr = (function() {
				return spp;
			});
			sbp.override = io;
			Ext.override(sb, overrides);
			sb.extend = function(o) {
				return Ext.extend(sb, o);
			};
			return sb;
		};
	}()
})

// 修复ExtJS3.2中无MenuButton的问题
Ext.Toolbar.MenuButton = Ext.Toolbar.SplitButton

// 修复ExtJS3.2中TextField allowBlank : true 失效的Bug
Ext.override(Ext.form.TextField, {
	//pepsi添加,覆盖Filed父类的setValue方法
	
    setValue : function(v){
    	//alert('进行赋值');
        this.value = v;
        if(this.rendered){
            this.el.dom.value = (Ext.isEmpty(v) ? '' : v);
          //由pepsi 2012-09-25 注释掉下面一行代码,validate应该由validate相关属性控制触发时机,而不是在每次setValue时触发.所以此处取消this.validate
          //  this.validate();
        }
        return this;
    },
	
	getErrors : function(value) {
		var errors = Ext.form.TextField.superclass.getErrors.apply(this,
				arguments);

		value = value || this.processValue(this.getRawValue());

		if (Ext.isFunction(this.validator)) {
			var msg = this.validator(value);
			if (msg !== true) {
				errors.push(msg);
			}
		}

		if (value.length < 1 || value === this.emptyText) {
			if (this.allowBlank) {
				this.clearInvalid();
				return true;
			} else {
				errors.push(this.blankText);
			}
		}

		if (value.length < this.minLength) {
			errors.push(String.format(this.minLengthText, this.minLength));
		}

		if (value.length > this.maxLength) {
			errors.push(String.format(this.maxLengthText, this.maxLength));
		}
		if (this.vtype) {
			var vt = Ext.form.VTypes;
			if (!vt[this.vtype](value, this)) {
				errors.push(this.vtypeText || vt[this.vtype + 'Text']);
			}
		}

		if (this.regex && !this.regex.test(value)) {
			errors.push(this.regexText);
		}

		return errors;
	}
});

// pepsi added at 2009-06-04 16:35
/**
 * 解决升级Extjs3.2后下拉框readOnly的问题
 */

Ext.override(Ext.form.TriggerField, {
	// setReadOnly : function(readOnly) {
	// alert("buding");
	// this.setEditable(!readOnly);
	// },
	getTriggerWidth : function() {
		var tw = this.trigger.getWidth();
		if (!this.hideTrigger && tw === 0) {
			tw = this.defaultTriggerWidth;
		}
		return tw;
	},
	updateEditState : function() {
		if (this.rendered) {
			if (this.readOnly) {
				// this.el.dom.readOnly = true;
				// // this.el.addClass('x-trigger-noedit');
				// this.mun(this.el, 'click', this.onTriggerClick, this);
				// this.trigger.setDisplayed(false);
				this.el.dom.readOnly = true;
				this.el.addClass('x-trigger-noedit');
				this.mon(this.el, 'click', this.onTriggerClick, this);
				if (this.trigger)
					this.trigger.setDisplayed(!this.hideTrigger);
			} else {
				if (!this.editable) {
					this.el.dom.readOnly = true;
					this.el.addClass('x-trigger-noedit');
					this.mon(this.el, 'click', this.onTriggerClick, this);
				} else {
					this.el.dom.readOnly = false;
					this.el.removeClass('x-trigger-noedit');
					this.mun(this.el, 'click', this.onTriggerClick, this);
				}
				this.trigger.setDisplayed(!this.hideTrigger);
			}
			this.onResize(this.width || this.wrap.getWidth());
		}
	}
});

Ext.override(Ext.form.ComboBox, {
	onTriggerClick : function() {
		if (this.disabled) {
			return;
		}
		if (this.isExpanded()) {
			this.collapse();
			this.el.focus();
		} else {
			this.onFocus({});
			if (this.triggerAction == 'all') {
				this.doQuery(this.allQuery, true);
			} else {
				this.doQuery(this.getRawValue());
			}
			this.el.focus();
		}
	},
	// 临时解决方案:解决活动设定-栏位设定 打开后,再去点名单查询的各种下拉框报js错的问题.
	getParentZIndex : function() {
		var zindex;
		if (this.ownerCt) {
			this.findParentBy(function(ct) {
				if (!ct.getPositionEl().dom) {
					return false;
				}
				zindex = parseInt(ct.getPositionEl().getStyle('z-index'), 10);
				return !!zindex;
			});
		}
		return zindex;
	}
});

// 修改Extjs3.2 MessageBox最小宽度
Ext.MessageBox.minWidth = 220;

// 开启EXT的事务回收机制
Ext.enableListenerCollection = true;

// 扩展Ext的pagingBar控件.

Ext.override(Ext.PagingToolbar, {
	/**
	 * 在方法中添加每页显示笔数的下拉框
	 */
	initComponent : function() {
		var tempThis = this;
		var T = Ext.Toolbar;

		var isHideIPs = false;// 是否隐藏pageSize控件
		if (this.isInputPs == false) {// 不需要pageSize控件
			isHideIPs = true;
		}

		var pagingItems = [this.first = new T.Button({
			tooltip : this.firstText,
			overflowText : this.firstText,
			iconCls : 'x-tbar-page-first',
			disabled : true,
			handler : this.moveFirst,
			scope : this
		}), this.prev = new T.Button({
			tooltip : this.prevText,
			overflowText : this.prevText,
			iconCls : 'x-tbar-page-prev',
			disabled : true,
			handler : this.movePrevious,
			scope : this
		}), '-', this.beforePageText,
				this.inputItem = new Ext.form.NumberField({
					cls : 'x-tbar-page-number',
					allowDecimals : false,
					allowNegative : false,
					enableKeyEvents : true,
					selectOnFocus : true,
					submitValue : false,
					listeners : {
						scope : this,
						keydown : this.onPagingKeyDown,
						blur : this.onPagingBlur
					}
				}), this.afterTextItem = new T.TextItem({
					text : String.format(this.afterPageText, 1)
				}), '-', this.next = new T.Button({
					tooltip : this.nextText,
					overflowText : this.nextText,
					iconCls : 'x-tbar-page-next',
					disabled : true,
					handler : this.moveNext,
					scope : this
				}), this.last = new T.Button({
					tooltip : this.lastText,
					overflowText : this.lastText,
					iconCls : 'x-tbar-page-last',
					disabled : true,
					handler : this.moveLast,
					scope : this
				}), this.pageSizeItem = new Ext.form.ComboBox({// pepsi扩展,分页工具条添加每页笔数
					store : new Ext.data.SimpleStore({
						fields : ['key', 'value'],
						data : [['15', '15'], ['30', '30'], ['50', '50']]
					}),
					name : 'pageSizeF',
					displayField : 'value',
					valueField : 'key',
					typeAhead : true,
					// inputType : 'number',
					hidden : isHideIPs,
					mode : 'local',
					resizable : true,
					regex : REGEX_ONLY_NUMBER,
					minValue : 0,
					validator : this.validPageSize,
					parentContId : this.id,// 客制化属性
					hideLabel : true,
					hideTrigger : true,
					allowBlank : false,
					triggerAction : 'all',
					value : this.pageSize,
					listWidth : 40,
					width : 30,
					listeners : {
						blur : function() {
							var tempV = this.getValue();
							if (tempV && parseInt(tempV, 10) > 0 && parseInt(tempV, 10)<=50 )
								tempThis.pageSize = parseInt(tempV, 10);
						}
					}
				}),  this.refresh = new T.Button({
					tooltip : this.refreshText,
					overflowText : this.refreshText,
					iconCls : 'x-tbar-loading',
					handler : this.doRefresh,
					scope : this
				})];

		var userItems = this.items || this.buttons || [];
		if (this.prependButtons) {
			this.items = userItems.concat(pagingItems);
		} else {
			this.items = pagingItems.concat(userItems);
		}
		delete this.buttons;
		if (this.displayInfo) {
			this.items.push('->');
			this.items.push(this.displayItem = new T.TextItem({}));
		}
		Ext.PagingToolbar.superclass.initComponent.call(this);
		this.addEvents(
				/**
				 * @event change Fires after the active page has been changed.
				 * @param {Ext.PagingToolbar}
				 *            this
				 * @param {Object}
				 *            pageData An object that has these properties:
				 *            <ul>
				 *            <li><code>total</code> : Number <div
				 *            class="sub-desc">The total number of records in
				 *            the dataset as returned by the server</div></li>
				 *            <li><code>activePage</code> : Number <div
				 *            class="sub-desc">The current page number</div></li>
				 *            <li><code>pages</code> : Number <div
				 *            class="sub-desc">The total number of pages
				 *            (calculated from the total number of records in
				 *            the dataset as returned by the server and the
				 *            current {@link #pageSize})</div></li>
				 *            </ul>
				 */
				'change',
				/**
				 * @event beforechange Fires just before the active page is
				 *        changed. Return false to prevent the active page from
				 *        being changed.
				 * @param {Ext.PagingToolbar}
				 *            this
				 * @param {Object}
				 *            params An object hash of the parameters which the
				 *            PagingToolbar will send when loading the required
				 *            page. This will contain:
				 *            <ul>
				 *            <li><code>start</code> : Number <div
				 *            class="sub-desc">The starting row number for the
				 *            next page of records to be retrieved from the
				 *            server</div></li>
				 *            <li><code>limit</code> : Number <div
				 *            class="sub-desc">The number of records to be
				 *            retrieved from the server</div></li>
				 *            </ul>
				 *            <p>
				 *            (note: the names of the <b>start</b> and <b>limit</b>
				 *            properties are determined by the store's
				 *            {@link Ext.data.Store#paramNames paramNames}
				 *            property.)
				 *            </p>
				 *            <p>
				 *            Parameters may be added as required in the event
				 *            handler.
				 *            </p>
				 */
				'beforechange');
		this.on('afterlayout', this.onFirstLayout, this, {
			single : true
		});
		this.pageSizeItem.on('select', this.onSelectPS, this);
		this.cursor = 0;
		this.bindStore(this.store, true);
	},
	// private pepsi modified at 20111112
	beforeLoad : function(s, o) {
		if (this.rendered && this.refresh) {
			this.refresh.disable();
		}
		if (o) {// pepsi added 在store load前按分页条信息传递每页笔数
			
			o.params.limit = this.pageSize;
			
		}
	},
	/**
		* editor by jason  2012-02-10
		* 重写onPagingBlur 方法 将输入的值的分页开始笔数放到cursor游标对象里
		*
	**/
	onPagingBlur : function(b) {
			
			var startnumber = parseInt(this.inputItem.getValue(),10)-1
			//o.params.start = startnumber*this.pageSize;
			this.cursor = startnumber*this.pageSize;
			//this.inputItem.setValue(this.getPageData().activePage)
	},
	
	
	/**
	 * 分页笔数验证:不超过1000
	 * 
	 * @param {}
	 *            v
	 */
	validPageSize : function(v) {// 由于validate的特殊性.在filed实际触发时this会指向validator的field对象.
									// 而不是当前类对象.所以这里通过parentContId去给分页工具的笔数赋值
		if (v && v != '') {
			if (!(/^[0-9]+$/.test(v)) || parseInt(v, 10) <= 0) {
				return '分页笔数必须为正整数'
			}
			if (parseInt(v, 10) > 500) {
				return '分页笔数不能超过500'
			}
			
			if (this.parentContId) {
				var p = Ext.getCmp(this.parentContId);// 取得分页条
				if (p && parseInt(v, 10) > 0) {
					p.pageSize = parseInt(v, 10);
				} else {
					alert("发生异常,分页工具未找到！");
				}

			} else {
				alert("发生异常,分页工具未找到！");
			}
			return true;
		}
	},
	/**
	 * 分页笔数框选择事件,选择时设置笔数
	 * 
	 * @param {}
	 *            combo
	 * @param {}
	 *            record
	 * @param {}
	 *            index
	 */
	onSelectPS : function(combo, record, index) {
		var tempV = record.get('value');
		if (tempV && parseInt(tempV, 10) > 0)
			this.pageSize = parseInt(tempV, 10);
	}
});


//tree panel 加timeout add by jason 
Ext.tree.TreeLoader.override({
    requestData : function(node, callback,scope){
        if(this.fireEvent("beforeload", this, node, callback) !== false){
        	if(this.directFn){                
				var args = this.getParams(node);                
				args.push(this.processDirectResponse.createDelegate(this, [{callback: callback, node: node, scope: scope}], true)); 
				this.directFn.apply(window, args);            
			}else{
				this.transId = Ext.Ajax.request({
	                method:this.requestMethod,
	                url: this.dataUrl||this.url,
	                success: this.handleResponse,
	                failure: this.handleFailure,
	                timeout: this.timeout || 300000,
	                scope: this,
	                argument: {callback: callback, node: node, scope: scope},
	                params: this.getParams(node)
            	});
			}
            
        }else{
            // if the load is cancelled, make sure we notify
            // the node that we are done
            this.runCallback(callback, scope || node, []);   
        }
    }
}); 

// 扩展分页工具条(页码下拉选择)
Ext.PagingToolbarEx = Ext.extend(Ext.PagingToolbar, {
	dataEx : new Array(),
	pagesEx : 0,
	/**
	 * 在方法中添加每页显示笔数的下拉框
	 */
	initComponent : function() {
		var tempThis = this;

		var T = Ext.Toolbar;

		// pepsi add
		var dsEx = new Ext.data.Store({
			// id : 'pagingComboxStore',
			proxy : new Ext.data.MemoryProxy(this.dataEx), // 数据源代理
			reader : new Ext.data.ArrayReader({}, [{
				name : 'value'
			}, {
				name : 'text'
			}])
		});
		dsEx.loadData(this.dataEx);

		var isHideIPs = false;// 是否隐藏pageSize控件
		if (this.isInputPs == false) {// 不需要pageSize控件
			isHideIPs = true;
		}

		var pagingItems = [this.first = new T.Button({
			tooltip : this.firstText,
			overflowText : this.firstText,
			iconCls : 'x-tbar-page-first',
			disabled : true,
			handler : this.moveFirst,
			scope : this
		}), this.prev = new T.Button({
			tooltip : this.prevText,
			overflowText : this.prevText,
			iconCls : 'x-tbar-page-prev',
			disabled : true,
			handler : this.movePrevious,
			scope : this
		}), '-', this.beforePageText, this.inputItem = new Ext.form.ComboBox({// pepsi
			// modified
			// 20111112
			typeAhead : true,
			store : dsEx,
			mode : 'local',
			width : 40,
			listWidth : 38,
			triggerAction : 'all',
			displayField : 'text',
			valueField : 'value',
			cls : "x-tbar-page-number",
			vType : 'num',
			editable : false
		}), this.afterTextItem = new T.TextItem({
			text : String.format(this.afterPageText, 1)
		}), '-', this.next = new T.Button({
			tooltip : this.nextText,
			overflowText : this.nextText,
			iconCls : 'x-tbar-page-next',
			disabled : true,
			handler : this.moveNext,
			scope : this
		}), this.last = new T.Button({
			tooltip : this.lastText,
			overflowText : this.lastText,
			iconCls : 'x-tbar-page-last',
			disabled : true,
			handler : this.moveLast,
			scope : this
		}),  this.pageSizeItem = new Ext.form.ComboBox({// pepsi扩展,分页工具条添加每页笔数
			store : new Ext.data.SimpleStore({
				fields : ['value', 'text'],
				data : [['15', '15'], ['30', '30'], ['50', '50']]
			}),
			name : 'pageSizeF',
			displayField : 'text',
			valueField : 'value',
			typeAhead : true,
			inputType : 'text',
			mode : 'local',
			resizable : true,
			hidden : isHideIPs,
			allowBlank : false,
			regex : REGEX_ONLY_NUMBER,
			hideLabel : true,
			hideTrigger : true,
			// hideMode:'visibility',
			value : this.pageSize,
			triggerAction : 'all',
			parentContId : this.id,
			validator : this.validPageSize,
			// readOnly : true,
			width : 30,
			listWidth : 40,
			listeners : {
				blur : function() {
					var tempV = this.getValue();
					if (tempV && parseInt(tempV, 10) > 0 && parseInt(tempV, 10) <= 50)
						tempThis.pageSize = parseInt(tempV, 10);
				}
			}
		}),  this.refresh = new T.Button({
			tooltip : this.refreshText,
			overflowText : this.refreshText,
			iconCls : 'x-tbar-loading',
			handler : tempThis.doRefresh,
			scope : this
		})];

		var userItems = this.items || this.buttons || [];
		if (this.prependButtons) {
			this.items = userItems.concat(pagingItems);
		} else {
			this.items = pagingItems.concat(userItems);
		}
		delete this.buttons;
		if (this.displayInfo) {
			this.items.push('->');
			this.items.push(this.displayItem = new T.TextItem({}));
		}
		Ext.PagingToolbar.superclass.initComponent.call(this);
		this.addEvents(			
				'beforechange');
		this.on('afterlayout', this.onFirstLayout, this, {
			single : true
		});
		this.inputItem.on('select', this.onSelectPage, this);
		this.pageSizeItem.on('select', this.onSelectPS, this);

		this.cursor = 0;
		this.bindStore(this.store, true);
	},

	// private pepsi modified at 20111112
	beforeLoad : function(s, o) {
		if (this.rendered && this.refresh) {
			this.refresh.disable();
		}
		if (o) {// pepsi added 在store load前按分页条信息传递每页笔数
			o.params.limit = this.pageSize;
			// alert(this.pageSize);
		}
	},

	// private
	onLoad : function(store, r, o) {
		if (!this.rendered) {
			this.dsLoaded = [store, r, o];
			return;
		}
		var p = this.getParams();
		this.cursor = (o.params && o.params[p.start]) ? o.params[p.start] : 0;
		var d = this.getPageData(), ap = d.activePage, ps = d.pages;

		this.afterTextItem.setText(String.format(this.afterPageText, d.pages));

		// pepsi added at 20111112
		// pepsi新增
		for (var tempi = 1; tempi <= d.pages; tempi++) {
			this.dataEx[tempi - 1] = [tempi, tempi];
		}

		this.inputItem.getStore().loadData(this.dataEx);

		var pageComboxArray = new Array();
		for (var tempi = 1; tempi <= ps; tempi++) {
			pageComboxArray[tempi - 1] = [tempi, tempi];
		}
		this.inputItem.store.loadData(pageComboxArray);// 更新分页下拉

		if (ap > ps)
			this.moveLast();
		// pepsi新增(结束)

		this.inputItem.setValue(ap);
		this.first.setDisabled(ap == 1);
		this.prev.setDisabled(ap == 1);
		this.next.setDisabled(ap == ps);
		this.last.setDisabled(ap == ps);
		this.refresh.enable();
		this.updateInfo();
		this.fireEvent('change', this, d);
		this.inputItem.collapse();
	},
	/**
	 * pepsi added 选则分页时执行读取动作
	 * 
	 * @param {}
	 *            combo
	 * @param {}
	 *            record
	 * @param {}
	 *            index
	 */
	onSelectPage : function(combo, record, index) {
		var d = this.getPageData();
		var pageNum;
		if (pageNum = this.readPage(d)) {
			pageNum = Math.min(Math.max(1, pageNum), d.pages) - 1;
			this.doLoad(pageNum * this.pageSize);
			combo.collapse();
			// setTimeout(function(){combo.collapse();},1000);
		}
	}
});

Ext.reg('pagingEx', Ext.PagingToolbarEx);

Ext.override(Ext.MessageBox, {
	alert:function(title, msg, fn, scope){
		alert(msg);
	},
	show:function(options){
		var msg = "";
		if(options && options.msg){
			msg = options.msg
		}
		alert(msg)
	}
});

//Ext.grid.CheckboxSelectionModel = Ext.extend(Ext.grid.RowSelectionModel, {

Ext.override(Ext.grid.CheckboxSelectionModel, {
	initEvents : function(){
        Ext.grid.CheckboxSelectionModel.superclass.initEvents.call(this);
        this.grid.on('render', function(){
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
            Ext.fly(view.innerHd).on('mousedown', this.onHdMouseDown, this);

        }, this);
    }
	
});


// 修复ExtJS3.4中销毁columnModel时导致内存泄露的问题: 如果grid在一个延迟渲染的tabpanel里,导致的config.length报错 从而导致控件无法销毁的问题.
Ext.override(Ext.grid.ColumnModel, {
	destroy : function() {
		if(this.config){//pepsi added 解决config无效时引发内存泄露的问题.
	        var length = this.config.length,
	            i = 0;
	
	        for (; i < length; i++){
	        	if(this.config[i] != null && this.config[i].destroy != null){
	            	this.config[i].destroy(); 
	        	}
	        }
		}
        delete this.config;
        delete this.lookup;
        this.purgeListeners();
    }
})


// 工具条
Ext.ToolbarEx = Ext.extend(Ext.Toolbar, {
	showCount : 0,
	hiddenArr : new Array,
	doLayout : function(shallow, force){		
		var thisClass = this;		
        var rendered = this.rendered,
            forceLayout = force || this.forceLayout;
        if(this.collapsed || !this.canLayout()){
            this.deferLayout = this.deferLayout || !shallow;
            if(!forceLayout){
                return;
            }
            shallow = shallow && !this.deferLayout;
        } else {
            delete this.deferLayout;
        }
        if(rendered && this.layout){
            this.layout.layout();
        }
        if(shallow !== true && this.items){
            var cs = this.items.items;
            for(var i = 0, len = cs.length; i < len; i++){
                var c = cs[i];
                if(c.doLayout){
                    c.doLayout(false, forceLayout);
                }
            }
        }        
        if(rendered){
        	//this.hiddenArr = new Array;
            this.onLayout(shallow, forceLayout);
        }
        // Initial layout completed
        this.hasLayout = true;
        delete this.forceLayout;
     	var thisId = this.id;    
        if (!Ext.getCmp(this.id + 'preButton') &&this.items && this.items.items) {
        	var widthCount = 0;
        	var start = 1;
        	
        	var henggangArr = new Array;        	
        	var cs = this.items.items;
        	var lengthCount = this.showCount;
        	var itemLength = cs.length;        	
        	for (var i = 0;i< itemLength;i++) {	 
        		if (cs[i] && cs[i].getResizeEl()) {
        			if (cs[i].getWidth() == 4) {
        				this.remove(cs[i])
        			} else {
        				widthCount += cs[i].getWidth();
        			}
        		}       	        		 		        		   		        			
			}
			
        	if (widthCount > this.getWidth()) {    
        		/*for (var i = 0;i< itemLength;i++) {	 
	        		if (cs[i] && cs[i].getResizeEl()) {
	        			if (cs[i].getWidth() == 4) {
	        				this.remove(cs[i])
	        			}
	        		}       	        		 		        		   		        			
				} */
				itemLength = cs.length;  		
        		var preButton = new Ext.Button({
					text : '　',
					id : this.id + 'preButton',			
					iconCls : 'thelast',											
					handler : function () {		
						if (start <= 1) {
							this.setDisabled(true);	
							return;
						}
						start --;	    
						lengthCount --;
						for (var i = 1;i< itemLength+2;i++) {	 
			        		cs[i].setVisible(false);   	        		 		        		   		        			
						}
										
						for (var i = start;i< lengthCount;i++) {							 
			        		cs[i].setVisible(true);   	        		 		        		   		        			
						}							
						for (var i = 0;i< thisClass.hiddenArr.length;i++) {					
			        		Ext.getCmp(thisClass.hiddenArr[i]).setVisible(false);   	        		 		        		   		        			
						}		
						if (start == 1) {
							this.setDisabled(true);	
						}			
						Ext.getCmp(thisId + 'nextButton').setDisabled(false);			
					}	
				});	
				var nextButton = new Ext.Button({
					text : '　',							
					id : this.id + 'nextButton',
					iconCls : 'thenext',
					handler : function () {						
						if (lengthCount >= itemLength + 1) {	
							this.setDisabled(true);	
							return;
						}
						start ++;	    
						lengthCount ++;
						for (var i = 1;i< itemLength+2;i++) {
			        		cs[i].setVisible(false);   	        		 		        		   		        			
						}
										
						for (var i = start;i< lengthCount;i++) {
			        		cs[i].setVisible(true);   	        		 		        		   		        			
						}	
					
						for (var i = 0;i< thisClass.hiddenArr.length;i++) {					
			        		Ext.getCmp(thisClass.hiddenArr[i]).setVisible(false);   	        		 		        		   		        			
						}	
						if (lengthCount == itemLength + 1) {
							this.setDisabled(true);	
						}					
						Ext.getCmp(thisId + 'preButton').setDisabled(false);   
					}
				});
				
				this.insertButton(0, preButton);
				this.insertButton(itemLength + 2, '->');
				this.insertButton(itemLength + 3, nextButton);
				
				for (var i = 1;i< itemLength+2;i++) {
					if(cs[i].hidden) {						
						thisClass.hiddenArr.push(cs[i].id);
					}
					if (cs[i])	 
	        		cs[i].setVisible(false);   	        		 		        		   		        			
				}
								
				for (var i = 1;i< this.showCount;i++) {
					if (cs[i])	 
	        		cs[i].setVisible(true);   	        		 		        		   		        			
				}	
				
				for (var i = 0;i< thisClass.hiddenArr.length;i++) {
					
	        		Ext.getCmp(thisClass.hiddenArr[i]).setVisible(false);   	        		 		        		   		        			
				}
				
				Ext.getCmp(this.id + 'preButton').setDisabled(true);		
        	}
        	
        }       
    }


});
Ext.reg('toolbarEx',Ext.ToolbarEx);

Ext.override(Ext.Toolbar, {
	start : 1,
	end : 0,
	toolBarWidth : 0,
	showCount : 15,
	initComponent : function() {
		var thisClass = this;
		/*var preButton = new Ext.Button({
			text : '　',
			id : this.id + 'preButton',			
			iconCls : 'thelast',				
			handler : function () {		
				
		       	thisClass.showCount --;
				thisClass.start --;
				var cs = thisClass.items.items;	
				if (thisClass.start <= 0) {
					Ext.getCmp(thisClass.id + 'preButton').setDisabled(true);
				} else {
					for (var i = 0;i< cs.length;i++) {
						cs[i].setVisible(false);
					}
					for (var i = thisClass.start;i< thisClass.showCount;i++) {
						cs[i].setVisible(true);						
			       	}      
			       	if(cs[thisClass.start] && cs[thisClass.start].getResizeEl() && cs[thisClass.start].getWidth() < 10) {
						if (thisClass.start > 0) {
							thisClass.showCount --;
							thisClass.start --;
							cs[thisClass.start ].setVisible(true);							
						}		
					}
			       	
			       	
			       	Ext.getCmp(thisClass.id + 'nextButton').setVisible(true);
		       		Ext.getCmp(thisClass.id + 'preButton').setVisible(true);	
		       		Ext.getCmp(thisClass.id + 'nextButton').setDisabled(false);	
		       	}	
			}	
		});		*/	
		
		/*var nextButton = new Ext.Button({
			text : '　',							
			id : this.id + 'nextButton',
			iconCls : 'thenext',
			handler : function () {				
				
				thisClass.showCount ++;
				thisClass.start ++;
				var cs = thisClass.items.items;	
				if (thisClass.showCount >= cs.length) {
					Ext.getCmp(thisClass.id + 'nextButton').setDisabled(true);
				} else {
					for (var i = 0;i< cs.length;i++) {
						cs[i].setVisible(false);
					}
					
					for (var i = thisClass.start;i< thisClass.showCount;i++) {										
						cs[i].setVisible(true);					
						if (i == (thisClass.showCount - 1)) {
							if(cs[i] && cs[i].getResizeEl() && cs[i].getWidth() < 10) {
								i++;
								cs[i].setVisible(true);
								thisClass.showCount ++;
								thisClass.start ++;
							}
						}
						
											
			       	}        
			       	Ext.getCmp(thisClass.id + 'nextButton').setVisible(true);
		       		Ext.getCmp(thisClass.id + 'preButton').setVisible(true);	
		       		Ext.getCmp(thisClass.id + 'preButton').setDisabled(false);		
		       	}		
	       			       
			}
		});*/
	//	preButton.setVisible(false);
	//	nextButton.setVisible(false);
		
	/*if (this.items && this.id != 'indexNorthToolBar') {
			
			this.items.push(nextButton);
			this.items.unshift(preButton);
		}*/
		
		
		
		
		
		Ext.Toolbar.superclass.initComponent.call(this);        		
        this.addEvents('overflowchange');
		
	},
	doLayout : function(shallow, force){		
		var showWidth = 0;
		var countWidth = 0;
        var rendered = this.rendered,
            forceLayout = force || this.forceLayout;

        if(this.collapsed || !this.canLayout()){
            this.deferLayout = this.deferLayout || !shallow;
            if(!forceLayout){
                return;
            }
            shallow = shallow && !this.deferLayout;
        } else {
            delete this.deferLayout;
        }
        if(rendered && this.layout){
            this.layout.layout();
        }
        if(shallow !== true && this.items){
            var cs = this.items.items;
            for(var i = 0, len = cs.length; i < len; i++){
                var c = cs[i];
                if (c) {
	                if(c.doLayout){
	                    c.doLayout(false, forceLayout);
	                }
                }
            }
        }
        
        if(rendered){
            this.onLayout(shallow, forceLayout);
        }
        // Initial layout completed
        this.hasLayout = true;
        delete this.forceLayout;
       /* Ext.getCmp(this.id + 'preButton').setVisible(false);
		Ext.getCmp(this.id + 'nextButton').setVisible(false);*/
		
		/*if (this.items && this.items.items) {
			
			var tt = this.getWidth()
        	if (parseInt(tt) <= 400) {
        		this.showCount = 9;
        	} else if (parseInt(tt) < 600) {
        		this.showCount = 13;
        	} else if (this.id == 'listStateButton') {
        		this.showCount = 10;
        	}
		}   */ 
		if (this.items && this.items.items) {		
			if (this.id == 'softPhoneforAlllistStateButton') {
        		this.showCount = 14;
        		if (this.items.items.length < 14) {
        			if (Ext.getCmp('softPhonepreButton')) {
        				Ext.getCmp('softPhonepreButton').setVisible(false);
			        	Ext.getCmp('softPhonenextButton').setVisible(false);
			        }
        		}
        	} else if (this.id == 'indexNorthToolBar') {
        		
        		if (this.items.items.length < 13) {
        			Ext.getCmp('indexNorthnextButton').setVisible(false);
			        Ext.getCmp('indexNorthpreButton').setVisible(false);
        		}
        	}
        }           
		
        if (this.items && this.items.items && (this.items.items.length > this.showCount)) {
        	
        	var cs = this.items.items;
        	var itemLength = this.items.items.length;
        	
        	if (this.id == 'indexNorthToolBar' || this.id == 'softPhoneforAlllistStateButton') {
	        	for (var i = 0;i< itemLength;i++) {
	        		cs[i].setVisible(false);
	        	}
        	}    
        	      
        /*	if (itemLength < this.showCount) {
        		for (var i = 0;i< itemLength;i++) {
	        		cs[i].setVisible(true);
	        	}
        		Ext.getCmp(this.id + 'preButton').setVisible(false);
				Ext.getCmp(this.id + 'nextButton').setVisible(false);
     
        	} else {     */    	
        	
        	
        		if (this.id == 'indexNorthToolBar') {               			 		
        			if (itemLength <= 19) {
        				for (var i = 0;i< itemLength;i++) {
			        		cs[i].setVisible(true);
			        	}
			        	
			        	
        			} else {
        				for (var i = 0;i<18;i++) {
			        		cs[i].setVisible(true);
			        	}
			        	    
			        	for (var i = (itemLength-3);i< itemLength;i++) {
			        		cs[i].setVisible(true);
			        	}
        			}
        			var subBtns = this.findByType("button");
        			
	    			if(subBtns && subBtns.length<=16){
	    			
	    				Ext.getCmp('indexNorthnextButton').setVisible(false);
			        	Ext.getCmp('indexNorthpreButton').setVisible(false);
	    			}
        		
        			
        		} else if (this.id == 'softPhoneforAlllistStateButton') {
	        		if (itemLength < 14) {
        				for (var i = 0;i< itemLength;i++) {
			        		cs[i].setVisible(true);
			        	}
        			} else {
        				for (var i = 0;i<13;i++) {
			        		cs[i].setVisible(true);
			        	}
			        	
			        	for (var i = (itemLength-2);i< itemLength;i++) {
			        		cs[i].setVisible(true);
			        	}
        			}
        			
        		} /*else {
        	      		
	        		Ext.getCmp(this.id + 'preButton').setVisible(true);
					Ext.getCmp(this.id + 'nextButton').setVisible(true);
					if (cs[0].getResizeEl() && cs[0].getWidth() < 10) {
						this.showCount = this.showCount + 1;
					}
		        	for (var i = 0;i< this.showCount;i++) {	        	
		        		cs[i].setVisible(true);			        		   		        				
					}
				}*/
			//}			
        }       
    }
});
        	
/*************grid支持复制 ***************/

if (!Ext.grid.GridView.prototype.templates) {
   Ext.grid.GridView.prototype.templates = {};
}
Ext.grid.GridView.prototype.templates.cell = new Ext.Template(
   '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}"  style="{style}" tabIndex="0" {cellAttr}>',
   '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>',
   '</td>'
);
/*//tim.gu add by 2014.1.22 扩展搜索功能的下拉控件
//扩展搜索功能的下拉控件;old--
Ext.override(Ext.form.ComboBox, {
	listeners : {
		'beforequery' : function(e) {
			var combo = e.combo;
			if (!e.forceAll) {
				var input = e.query;
				// 检索的正则
                var regExp = new RegExp(".*" + input.toLowerCase() + ".*", "i");
                
                //检测是否存在扩展
                var sf1 = (this.searchField1 && this.searchField1 != '') ? true : false;
                var sf2 = (this.searchField2 && this.searchField2 != '') ? true : false;
                // 执行检索
                this.store.filterBy(function(record, id) {
                    // 得到每个record的项目名称值
                    var text = record.get(combo.displayField);
                    //return regExp.test(text.toLowerCase());
                    
                    if(regExp.test(text.toLowerCase())){ return true; }
                    if(sf1){
                    	var text = record.get(combo.searchField1);
                    	if(text!=null && text!=''){
                    		if(regExp.test(text.toLowerCase())){ return true; }
                    	}
                    }
                    if(sf2){
                    	var text = record.get(combo.searchField2);
                    	if(text!=null && text!=''){
                    		if(regExp.test(text.toLowerCase())){ return true; }
                    	}
                    }
                    return false;
                });
                combo.expand();
				return false;
			}
		}
	}
});*/

Ext.override(Ext.form.ComboBox, {
	 
    putValue : function(text, value) {
      try {
        var combo = this;
        var displayField = combo.displayField || 'name';
        var valueField = combo.valueField || 'id';
        var index = combo.store.findExact(valueField, value);
        if (index == -1) {
          var r = new combo.store.recordType({});
          r.set(valueField, value);
          r.set(displayField, text);
          combo.store.add(r);
        } else {
          var r = combo.store.getAt(index);
          if (r.get(displayField) != text) {
            r.set(displayField, text);
          }
        }
        combo.setValue(value);
      } catch (err) {
        alert(err);
      }
    },

    onRender : function(ct, position) {
      if (this.hiddenName && !Ext.isDefined(this.submitValue)) {
        this.submitValue = false;
      }
      Ext.form.ComboBox.superclass.onRender.call(this, ct, position);
      if (this.hiddenName) {
        this.hiddenField = this.el.insertSibling({
              tag : 'input',
              type : 'hidden',
              name : this.hiddenName,
              id : (this.hiddenId || Ext.id())
            }, 'before', true);
      }
      if (this.visibleName) {
        this.el.set({
              name : this.visibleName
            });
      }
      if (Ext.isGecko) {
        this.el.dom.setAttribute('autocomplete', 'off');
      }
      if (!this.lazyInit) {
        this.initList();
      } else {
        this.on('focus', this.initList, this, {
              single : true
            });
      }
    }
  });

//复写CheckboxGroup即可，代码如下
Ext.override(Ext.form.CheckboxGroup,{    
    //在inputValue中找到定义的内容后，设置到items里的各个checkbox中    
    setValue : function(value){
    	if(!value){
    		return;
    	}
        this.items.each(function(f){
          if(value.indexOf(f.inputValue) != -1){  
                 f.setValue(true);   
             }else {   
                 f.setValue(false);   
             }      
          
        });   
    },   
    //以value1,value2的形式拼接group内的值   
    getValue : function(){   
        var re = "";   
        this.items.each(function(f){
            if(f.getValue() == true){   
                re += f.inputValue + ",";   
            }   
        });   
        return re.substr(0,re.length - 1);   
    },
    //boxLabel
    getBoxLabel : function(){   
        var re = "";   
        this.items.each(function(f){
            if(f.getValue() == true){   
                re += f.boxLabel + ",";   
            }   
        }); 
        return re.substr(0,re.length - 1);   
    },
    //返回选中的对象 boxLabel,inputValue
    getValueNames : function(){   
        var ret = [];   
        this.items.each(function(f){
            if(f.getValue() == true){
            	ret.push({label:f.boxLabel , value:f.inputValue})
            }   
        }); 
        return ret;   
    },
    //在Field类中定义的getName方法不符合CheckBoxGroup中默认的定义，因此需要重写该方法使其可以被BasicForm找到   
    getName : function(){   
        return this.name;   
    }   
});

//复写RadioGroup即可，代码如下
Ext.override(Ext.form.RadioGroup,{    
    /**getValue : function(){   
        var out = '';
        this.eachItem(function(item){
            if(item.checked){
                out = item.inputValue;
                return false;
            }
        });
        return out;
    }*/
    getValue: function(){     
        var v;     
        if (this.rendered) {     
            this.items.each(function(item){     
                if (!item.getValue())      
                    return true;     
                v = item.getRawValue();     
                return false;     
            });     
        }     
        else {     
            for (var k in this.items) {     
                if (this.items[k].checked) {     
                    v = this.items[k].inputValue;     
                    break;     
                }     
            }     
        }     
        return v;     
    },     
    setValue: function(v){     
        if (this.rendered)      
            this.items.each(function(item){
            	var flag = (item.getRawValue() == v);
            	//item.click();
                item.setValue(flag);
                if(flag){
            		//item.click();
            	}
            });
        else {     
            for (var k in this.items) {
            	var flag = (this.items[k].inputValue == v);
            	this.items[k].checked = flag;
            	if(flag){
            		this.items[k].click();
            	}
            }     
        }     
    }     
});
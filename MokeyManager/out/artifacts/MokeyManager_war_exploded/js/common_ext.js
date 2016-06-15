Ext.namespace("Manager.sys");

var mainWidth = document.documentElement.clientWidth-9;
var mainHeigth = document.documentElement.clientHeight-40;

SYS_CONSTANT_PAGESIZE = 15;
LOADMASK_MSG = 'loading...';
//
Manager.sys.Utils = function() {
	return {
		/**
		 * 初始化事件滚动条
		 */
		antoScrollerInit : function(gridPanel, formPanel, custHeight){
			// load事件、表头排序 将滚动显示
			gridPanel.getView().on('refresh', function() {
				//Manager.sys.Utils.autoShowScroller(gridPanel,formPanel);
			});
			// load事件 - 显示下方滚动条
			gridPanel.store.on("load", function() {
				Manager.sys.Utils.autoShowScroller(gridPanel,formPanel);
			});
			// 隐藏显示条件 显示GRID下方滚动条
			formPanel.on('collapse', function() {
				//Manager.sys.Utils.autoShowScroller(gridPanel,formPanel);
			});
			// 隐藏显示条件 显示GRID下方滚动条
			formPanel.on('expand', function() {
				//Manager.sys.Utils.autoShowScroller(gridPanel,formPanel);
			});
		},
		
		/**
		 * @函数功能 : 当栏位为动态生成并且栏位过多时在grid中显示出滚动条
		 * @参数说明 : thisGridPanel : 带条件输入框的Ext.grid.GridPanel[_EditorGridPanel]
		 *       thisFormPanel : grid的条件输入框 Ext.form.FormPanel custHeight
		 *       自定义设置高度差值,一般用于GRID包含多个TBAR的情况,参数自己调试,2层TBAR的建议值:80,默认为只有1层时的25
		 */
		autoShowScroller : function(thisGridPanel, thisFormPanel, custHeight) {
			try {
				var dh = 25;
				if (custHeight && custHeight > 0) {
					dh = custHeight;
				}
				//alert("--"+thisGridPanel.view.scroller.dom.style.height);
				if (thisGridPanel.rendered && thisGridPanel.isVisible()&& thisGridPanel.getInnerHeight() > 0) {
					var thisHeight = thisGridPanel.getInnerHeight();
					if (thisFormPanel && thisFormPanel.rendered&& thisFormPanel.getSize().height < thisHeight - 10) {
						var fHeight = thisFormPanel.getSize().height;
						var newH = thisHeight- fHeight-dh;
						//alert(thisGridPanel.view.scroller.dom.style.height+",-thisHeight-"+thisHeight +","+fHeight+","+dh +","+newH);
						thisGridPanel.view.scroller.dom.style.height = newH;
					} else {
						thisGridPanel.view.scroller.dom.style.height = thisHeight- dh;
					}
				}
			} catch (e) {
			}
		},
		/**
		 * 单一数据校验
		 * @param url
		 * @param checkType
		 * @param value
		 * @param idVal
		 * @returns
		 */
		checkExits : function(url,checkType, value,idVal) {
			if(Ext.isEmpty(idVal)){
				idVal = '';
			}
			var conn = Ext.lib.Ajax.getConnectionObject().conn;
			conn.open("post", url, false);// false表示同步调用
			conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			conn.send("checkType="+checkType+"&value="+value+"&idVal="+idVal);//
			var data = Ext.util.JSON.decode(conn.responseText);
			return data;
		}
	}
}();

Manager.sys.From = function(){
	return{
		createGroupItem: function(name,data,option){
			var items = [];
			for(var i=0;i<data.length;i++){
				var d = data[i];
				var chk = {boxLabel: d[option.labelFiled], name: name+i, inputValue: d[option.valueFiled] };
				if(option.checked){
					chk.checked = true;
				}
                items.push(chk);
             }
             return items;
		},
		createSupplier_Store : function(isAll){
			 var suppStore = new Ext.data.Store({
            	//autoLoad: true,
            	proxy : new Ext.data.HttpProxy({
            		url : rootPath+"basedata/supplierInfo!getSuppliers.action",
            	    timeout : 60000
            	}),
            	reader : new Ext.data.JsonReader({
            		root : 'list'
            	},
				Ext.data.Record.create([
					{ name : 'C_ID'},
					{ name : 'C_NAME'}
				]))
            });
            
            suppStore.load({
            	callback: function(obj){
            		if(isAll)
						suppStore.insert(0,new suppStore.recordType({C_ID:'0',C_NAME:'系统'}));
				}
            });
		}
	}
}();

//根据值删除数组元素
Array.prototype.removeByValue = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val) {
			this.splice(i, 1);
			break;
		}
	}
}
// 根据下标删除数组元素
Array.prototype.removeByIndex = function(index) {
	this.splice(index, 1);
}
// 根据值得到数组元素的下标
Array.prototype.indexOfByValue = function(item) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == item)
			return i;
	}
	return -1;
}

//hash表去重
Array.prototype.unique = function()
{
	var n = {},r=[]; //n为hash表，r为临时数组
	for(var i = 0; i < this.length; i++) //遍历当前数组
	{
		if (!n[this[i]]) //如果hash表中没有当前项
		{
			n[this[i]] = true; //存入hash表
			r.push(this[i]); //把当前数组的当前项push到临时数组里面
		}
	}
	return r;
}

//indexof 去重
Array.prototype.unique2 = function()
{
	this.sort();
	var re=[this[0]];
	for(var i = 1; i < this.length; i++)
	{
		if( this[i] !== re[re.length-1])
		{
			re.push(this[i]);
		}
	}
	return re;
}
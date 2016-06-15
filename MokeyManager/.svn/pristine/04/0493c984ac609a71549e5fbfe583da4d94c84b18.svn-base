
/**
 * @class Ext.ux.FormDataBaseHelp 
 * 数据库操作类,用于执行Sql语句
 * @constructor
 * 新建操做类实例
 */
Ext.ux.FormDataBaseHelp = function(){
	return {
		/**
		 * 查询
	     * @param {String} sql  要执行的Sql语句
	     * @param {String} sqlparams  是一个JsonArray的Encode后的字符串,用来给Sql语句中的参数赋值 如: [{\"params\":\"1\"}]
	     * @param {String} jdbc 需要使用的jdbc名字, jdbc是在eVoice中设定的
	     * @param {Object} callback 查询结束后的回调函数,用来获得执行结果
	     * @param {Number} start 分页查询的开始序号
	     * @param {Number} limit 分页的页面大小
	     * @param {Object} scope 回调函数的作用域对象
	     */
		query : function(sql,sqlparams,jdbc,callback,start,limit,scope){
			Ext.Ajax.request({
			   url: Ext.ux.FormDesignBaseUrl + 'formdesign/formctrl!getCtrlGridDataList.action',
			   scope:scope,
			   success: function(conn,response,params){
			   		if(callback)
			   		{
			   			callback.call(this,Ext.decode(conn.responseText));
			   		}
			   },
			   params: {
			   	sql:sql,
			   	sqlparams:sqlparams,
			   	jdbc:jdbc,
			   	limit:limit,
			   	start:start
			   }
			});
		},
		/**
		 * 更新
	     * @param {String} sql  要执行的Sql语句
	     * @param {String} sqlparams  是一个JsonArray的Encode后的字符串,用来给Sql语句中的参数赋值 如: [{\"params\":\"1\"}]
	     * @param {String} jdbc 需要使用的jdbc名字, jdbc是在eVoice中设定的
	     * @param {Object} callback 更新结束后的回调函数,用来获得执行结果     
	     * @param {Object} scope 回调函数的作用域对象
	     */
		update : function(sql,sqlparams,jdbc,callback,scope){
			Ext.Ajax.request({
			   url: Ext.ux.FormDesignBaseUrl + 'formdesign/formctrl!saveSql.action',
			   scope:scope,
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
		 * 获得表的主键流水号 ,由于数据库的中主键是18位数字，但是js的最大精度为16，所以需要以字符串来处理获得的主键值
	     * @param {String} table 表名
	     * @param {Number} count 需要的主键数量
	     * @param {String} jdbc 需要使用的jdbc名字, jdbc是在eVoice中设定的
	     * @param {Object} callback 查询结束后的回调函数,用来获得执行结果	     
	     * @param {Object} scope 回调函数的作用域对象
	     */
		getpk:function(table,count,jdbc,callback,scope){
			if(!count){
				count=1;
			}
			Ext.Ajax.request({
			   url: Ext.ux.FormDesignBaseUrl + 'formdesign/formctrl!getTablePK.action',
			   scope:scope,
			   success: function(conn,response,params){
			   		if(callback)
			   		{
			   			callback.call(this,Ext.decode(conn.responseText));
			   		}
			   },
			   params: {
			   	table:table,
			   	count:count,
			   	jdbc:jdbc
			   }
			});
		}
	};
}









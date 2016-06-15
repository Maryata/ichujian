
// 系统全局变量 - 正则表达式部分
REGEX_NOT_ENGANDNUM = /^[^0-9a-zA-Z]+$/; // - 不可为英文或数字
REGEX_NOT_CHINESE = /^[^\u4E00-\u9FA5]+$/; // - 不可为中文
REGEX_ONLY_ENGANDNUM = /^[0-9a-zA-Z]+$/; // - 不可为特殊字符或中文.只允许为字母或数字
REGEX_ONLY_NUMBER = /^[0-9]+$/;// - 只允许为数字
REGEX_ONLY_NUMBER_FLOAT = /^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$/;  // - 只允许为浮点型数字，精度不限
REGEX_NOT_PARTICULARCHAR = /^[a-zA-Z0-9０-９\u4E00-\u9FA5 ]*$/; // -
// 不允许为特殊字符(可以含空格)
REGEX_NOT_PARTICULARCHAR2 = /^[a-zA-Z0-9０-９\u4E00-\u9FA5]*$/; // -
// 不允许为特殊字符(不允许空格)

REGEX_NORMAL_NAME = /^[^\\'"]+$/; // - 不可包含系统保留字符

REGEX_SYS_AnyM_CONDITION = /^[^\\'%]+$/; // 适用于对模糊查询条件的验证.
REGEX_SYS_PreM_CONDITION = /^[^\\']+$/;// 适用于对精确查询条件的验证

REGEX_ONLY_ENGANDNUM = /^[0-9a-zA-Z]+$/; // - 只可以为英文或数字
REGEX_PARAM_VALUE =/^[^\\'"]+$/; //    /^[a-zA-Z0-9０-９\u4E00-\u9FA5\-#$*#(){}@]*$/; // - 参数值验证
REGEX_MAIL_ADDRESS= /^([a-zA-Z0-9\-]+[_|\_|\.]?)*[a-zA-Z0-9\-]+@([a-zA-Z0-9\-]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;//邮件地址认证


REGEX_CHANGEROW = new RegExp("\r\n", "g");// 将换行符转换为指定字符的正则表达式
REGEX_CHANGEBR = new RegExp("<br>", "g");// 将<br>转换为指定字符
REGEX_CHANGE_SKILLQTIP_LEFT = new RegExp('<%', 'g');
REGEX_CHANGE_SKILLQTIP_RIGHT = new RegExp('%>', 'g');
REGEX_CHANGE_TO_LEFT_SHARPBRACKETS = new RegExp("&lt;", "g");// 将<转换为指定字符
// -->解决树节点的"<"括号引发的问题
REGEX_CHANGE_LEFT_SHARPBRACKETS = new RegExp("<", "g");// 将<转换为指定字符
// -->解决树节点的"<"括号引发的问题
REGEX_MOBILE =/^1[0-9][0-9]\d{8,8}$/; //手机号码验证
REGEX_TEL_ALLPHONE =/^\d{2,4}-\d{5,9}$/; //新增时,完整的固话验证
REGEX_TEL_AREA =/^0[0-9]\d{1,2}$/; //固话区号验证
REGEX_TEL_PHONE =/^[0-9]\d{5,8}$/; //固话验证

REGEX_SYS_PASSWORD = /[0-9a-zA-Z~!@#$%^&*().,-]{6,20}/;//登陆密码
//REGEX_GRANDSYS_PASSWORD = /^(?!^\d+$|^[a-zA-Z]+$)(?:[a-zA-Z\d]{6,20})$/;//登陆密码验证.
REGEX_ONLY_ENGANDNUMEX = /^[0-9a-zA-Z_]+$/; // - 不可为特殊字符或中文.只允许为字母、数字、下划线

// 业务用规则
// 预约标记信息转换文字显示:

Ext.form.VTypes = function() {
	// closure these in so they are only created once.
	var alpha = /^[a-zA-Z_]+$/;
	var alphanum = /^[a-zA-Z0-9_]+$/;
//	var email = /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/;
	var email = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;//邮件地址认证
	var url = /(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;
	var noSysChar = /^[^\\']+$/;
	var phoneNum = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
	var replylimit = /^[0-9]{1,2}([DHdh]){1,1}?$/;
	var num= /^[0-9]+$/;
	// All these messages and functions are configurable
	return {
		/**
		 * The function used to validate email addresses
		 * 
		 * @param {String}
		 *            value The email address
		 */
		'email' : function(v) {
			return email.test(v);
		},
		/**
		 * The error text to display when the email validation function returns
		 * false
		 * 
		 * @type String
		 */
		'emailText' : '该输入项必须是电子邮件地址，格式如： "user@domain.com"',
		/**
		 * The keystroke filter mask to be applied on email input
		 * 
		 * @type RegExp
		 */
		'emailMask' : /[a-z0-9_\.\-@]/i,

		/**
		 * The function used to validate URLs
		 * 
		 * @param {String}
		 *            value The URL
		 */
		'url' : function(v) {
			return url.test(v);
		},
		/**
		 * The error text to display when the url validation function returns
		 * false
		 * 
		 * @type String
		 */
		'urlText' : '该输入项必须是URL地址，格式如： "http:/' + '/www.domain.com"',

		/**
		 * The function used to validate alpha values
		 * 
		 * @param {String}
		 *            value The value
		 */
		'alpha' : function(v) {
			return alpha.test(v);
		},
		/**
		 * The error text to display when the alpha validation function returns
		 * false
		 * 
		 * @type String
		 */
		'alphaText' : '该输入项只能包含字符和_',
		/**
		 * The keystroke filter mask to be applied on alpha input
		 * 
		 * @type RegExp
		 */
		'alphaMask' : /[a-z_]/i,

		/**
		 * The function used to validate alphanumeric values
		 * 
		 * @param {String}
		 *            value The value
		 */
		'alphanum' : function(v) {
			return alphanum.test(v);
		},
		/**
		 * The error text to display when the alphanumeric validation function
		 * returns false
		 * 
		 * @type String
		 */
		'alphanumText' : '只可以输入字母、数字和下划线',
		/**
		 * The keystroke filter mask to be applied on alphanumeric input
		 * 
		 * @type RegExp
		 */
		'alphanumMask' : /[a-z0-9_]/i,
		'noSysChar' : function(v) {
			return noSysChar.test(v);
		},
		'noSysCharText' : '输入值不可包含系统保留字符',
		'phoneNum' : function(v) {
			return phoneNum.test(v);
		},
		'phoneNumText' : '电话号码格式有误,格式应为:区号-电话号码-分机号,区号、分机号可以不输入',
		'phoneNumMask' : /[0-9-]/i,
		'num' : function(v) {
			return phoneNum.test(v);
		},
		'numText' : '只能数据0-9的数字',
		'numMask' : /[0-9]/i,
		'replylimit' : function(v) {
			return replylimit.test(v);
		},
		'replylimitText' :  '回复时效格式有误,格式应为:1或2位数字+D/H,例如:12H'
	};
}();

Ext.form.VTypes["hostnameVal1"] = /^[a-zA-Z][-.a-zA-Z0-9]{0,254}$/;
Ext.form.VTypes["hostnameVal2"] = /^[a-zA-Z]([-a-zA-Z0-9]{0,61}[a-zA-Z0-9]){0,1}([.][a-zA-Z]([-a-zA-Z0-9]{0,61}[a-zA-Z0-9]){0,1}){0,}$/;
Ext.form.VTypes["ipVal"] = /^([1-9][0-9]{0,1}|1[013-9][0-9]|12[0-689]|2[01][0-9]|22[0-3])([.]([1-9]{0,1}[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])){2}[.]([1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-4])$/;
Ext.form.VTypes["netmaskVal"] = /^(128|192|224|24[08]|25[245].0.0.0)|(255.(0|128|192|224|24[08]|25[245]).0.0)|(255.255.(0|128|192|224|24[08]|25[245]).0)|(255.255.255.(0|128|192|224|24[08]|252))$/;
Ext.form.VTypes["portVal"] = /^(0|[1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$/;
Ext.form.VTypes["multicastVal"] = /^((22[5-9]|23[0-9])([.](0|[1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])){3})|(224[.]([1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])([.](0|[1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])){2})|(224[.]0[.]([1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])([.](0|[1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5])))$/;
Ext.form.VTypes["usernameVal"] = /^[a-zA-Z][-_.a-zA-Z0-9]{0,30}$/;
Ext.form.VTypes["passwordVal1"] = /^.{6,31}$/;
Ext.form.VTypes["passwordVal2"] = /[^a-zA-Z].*[^a-zA-Z]/;

Ext.form.VTypes["hostname"] = function(v) {
	if (!Ext.form.VTypes["hostnameVal1"].test(v)) {
		Ext.form.VTypes["hostnameText"] = "Must begin with a letter and not exceed 255 characters"
		return false;
	}
	Ext.form.VTypes["hostnameText"] = "L[.L][.L][.L][...] where L begins with a letter, ends with a letter or number, and does not exceed 63 characters";
	return Ext.form.VTypes["hostnameVal2"].test(v);
}
Ext.form.VTypes["hostnameText"] = "Invalid Hostname"
Ext.form.VTypes["hostnameMask"] = /[-.a-zA-Z0-9]/;
Ext.form.VTypes["ip"] = function(v) {
	return Ext.form.VTypes["ipVal"].test(v);
}
Ext.form.VTypes["ipText"] = "1.0.0.1 - 223.255.255.254 excluding 127.x.x.x"
Ext.form.VTypes["ipMask"] = /[.0-9]/;
Ext.form.VTypes["netmask"] = function(v) {
	return Ext.form.VTypes["netmaskVal"].test(v);
}
Ext.form.VTypes["netmaskText"] = "128.0.0.0 - 255.255.255.252"
Ext.form.VTypes["netmaskMask"] = /[.0-9]/;
Ext.form.VTypes["port"] = function(v) {
	return Ext.form.VTypes["portVal"].test(v);
}
Ext.form.VTypes["portText"] = "0 - 65535"
Ext.form.VTypes["portMask"] = /[0-9]/;
Ext.form.VTypes["multicast"] = function(v) {
	return Ext.form.VTypes["multicastVal"].test(v);
}
Ext.form.VTypes["multicastText"] = "224.0.1.0 - 239.255.255.255"
Ext.form.VTypes["multicastMask"] = /[.0-9]/;
Ext.form.VTypes["username"] = function(v) {
	return Ext.form.VTypes["usernameVal"].test(v);
}
Ext.form.VTypes["usernameText"] = "Username must begin with a letter and cannot exceed 255 characters"
Ext.form.VTypes["usernameMask"] = /[-_.a-zA-Z0-9]/;

Ext.form.VTypes["password"] = function(v) {
	if (!Ext.form.VTypes["passwordVal1"].test(v)) {
		Ext.form.VTypes["passwordText"] = "Password length must be 6 to 31 characters long";
		return false;
	}
	Ext.form.VTypes["passwordText"] = "Password must include atleast 2 numbers or symbols";
	return Ext.form.VTypes["passwordVal2"].test(v);
}
Ext.form.VTypes["passwordText"] = "Invalid Password"
Ext.form.VTypes["passwordMask"] = /./;
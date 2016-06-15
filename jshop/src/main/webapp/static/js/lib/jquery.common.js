
//获取和设置url参数
(function($, w, d){
// 根据值删除数组元素
Array.prototype.removeByValue = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val) {
			this.splice(i, 1);
			break;
		}
	}
};
// 根据下标删除数组元素
/*Array.prototype.removeByIndex = function(index) {
	this.splice(index, 1);
};*/
// 根据值得到数组元素的下标
Array.prototype.indexOfByValue = function(item) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == item)
			return i;
	}
	return -1;
};
$.UrlParams = function( url , name , value ) {
	var reg=new RegExp("(\\\?|&)" + name + "=([^&]+)(&|$)" , "i");
	var m = url.match( reg );
	if( typeof value != 'undefined'){ //赋值
		if(m){
			return ( url.replace(reg,function($0,$1,$2){
				return ($0.replace($2,value));
			}));
		} else {
			if(url.indexOf('?')==-1){
				return (url+'?'+name+'='+value);
			}else{
				return (url+'&'+name+'='+value);
			}
		}
	} else { //取值
		if(m) {
			return m[2];
		}else{
			return '';
		}
	}
}
//删除url指定名称的参数
$.UrlParamDel1=function(url ,name ){
	var reg=new RegExp("\\\? | &"+name+"= ([^&]+)(&|&)","i");
	return url.replace(reg,"");
}
//删除url指定名称的参数
$.UrlParamDel2=function(url ,params ){
	//url不是字符串直接返回空字符串
    if(typeof url != "string") {
        return "";
    }
    //以下三种情况直接返回url：1、url没有参数 2、params不是字符串并且不是数组 3、params是空字符串或空数组
    if( url.indexOf("?") == -1 || typeof params != "string" && Object.prototype.toString.call(params) != "[object Array]" || params.length == 0 ) {
        return url;
    }
    var params = typeof params == "string" ? params.split(",") : params,  //如果params是字符串则分割成数组
        anchorPattern = /.*#.+$/,  //这个正则表达式是为了操作url里的锚点
        anchorStr = "";
    if( anchorPattern.test( url ) ) {
        var urlSplit = url.split("#"),
            anchorStr = "#" + urlSplit[1],  //获取锚点
            url = urlSplit[0];  //获取去除锚点后剩余的url
    }
    if(!$.grep) {  //如果没有grep方法
        $.grep = function( elems, callback, inv ) {  //该方法可使用参考jquery
            var retVal,
                ret = [],
                i = 0,
                length = elems.length;
            inv = !!inv;
            for(; i < length; i++ ) {
                retVal = !!callback( elems[ i ], i );
                if( inv !== retVal ) {
                    ret.push( elems[ i ] );
                }
            }
            return ret;
        }
	    var urlList = url.split("?"),  //以?分割url
	        baseUrl = urlList[0],  //除了查询部分剩余的url，即?之前的部分
	        searchsList = urlList[1].split("&"),  //查询部分以&分割生成参数集合
	        expr = new RegExp("^(" + params.join("|") + ")\=.[^&]*$"),  //生成传入的要删除的params的正则
	        remainSearchs = $.grep(searchsList, function(val){  //得到去除params后剩余的参数集合
	            return expr.test(val);
	        }, true);
	    if(remainSearchs.length == 0){  //如果没有剩余的参数
	        return baseUrl + anchorStr;
	    }
	    return baseUrl + "?" + remainSearchs.join("&") + anchorStr;//如果有剩余的参数
	}
}

$.UrlParamDel = function(url, ref) {
	url = url.replace("#","");
    var str = "";
    if (url.indexOf('?') != -1) {
        str = url.substr(url.indexOf('?') + 1);
    }
    else {
        return url;
    }
    var arr = "";
    var returnurl = "";
    var setparam = "";
    if (str.indexOf('&') != -1) {
        arr = str.split('&');
        for (var i=0;i<arr.length;i++) {
            if (arr[i].split('=')[0] != ref) {
                returnurl = returnurl + arr[i].split('=')[0] + "=" + arr[i].split('=')[1] + "&";
            }
        }
        return url.substr(0, url.indexOf('?')) + "?" + returnurl.substr(0, returnurl.length - 1);
    }
    else {
        arr = str.split('=');
        if (arr[0] == ref) {
            return url.substr(0, url.indexOf('?'));
        }
        else {
            return url;
        }
    }
}

//扩展
$.prototype.setVal = function(v){
  if (typeof(v) == "undefined" || v=="undefined"){v='';}
  $(this).val(v);
};

}(jQuery, window, document));

jQuery.extend({
  /**
    * 金额千位格式化函数
   */
  fAmount:function(s, n){
      n = n > 0 && n <= 20 ? n : 2;   
      s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
      var l = s.split(".")[0].split("").reverse(),
      r = s.split(".")[1];   
      var len = (s.indexOf("-")　!= -1) ? l.length - 1 : l.length;
      t = "";   
      for(i = 0; i < len; i++ ){   
         t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != len ? "," : "");   
      }   
   return ((s.indexOf("-")　!= -1) ? "-" : "") + t.split("").reverse().join("") + "." + r;   
  },
  /**
    * 整数千位格式化函数
   */
  fInt:function(s){
      s = parseInt((s + "").replace(/[^\d-]/g, "")) + "";   
      var l = s.split(".")[0].split("").reverse();   
      t = "";   
    var len = (s.indexOf("-")　!= -1) ? l.length - 1 : l.length;
      for(i = 0; i < len; i++ ){   
         t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != len ? "," : "");   
      }   
   return ((s.indexOf("-")　!= -1) ? "-" : "") + t.split("").reverse().join("");   
  },
  /**
    * 整数千位格式化反向函数
   */
  fIntRever:function(s){
      var arr=s.split(",");
      var result="";
      for(var i=0;i<arr.length;i++){
       result+=arr[i];
      } 
      
      return parseInt(result);
  },
  /**
    * 日期格式化函数
   */
  formatDate:function(pattern,date){
      function formatNumber(data,format){
          format = format.length;
          data = data || 0;
          return format == 1 ? data : (data = String(Math.pow(10,format) + data)).substr(data.length - format);
      }
      return pattern.replace(/([yMdhsm])\1*/g,function(format){
          switch(format.charAt()){
           case 'y' :
               return formatNumber(date.getFullYear(),format);
           case 'M' :
               return formatNumber(date.getMonth()+1,format);
           case 'd' :
               return formatNumber(date.getDate(),format);
           case 'w' :
               return date.getDay()+1;
           case 'h' :
               return formatNumber(date.getHours(),format);
           case 'm' :
               return formatNumber(date.getMinutes(),format);
           case 's' :
               return formatNumber(date.getSeconds(),format);
          }
      });
  },
  /**
  *精确小数点N位，四舍五入方式
  */
  roundDigit:function(num,n){
	var f = parseFloat(x); 
	if (isNaN(f)) {return;} 
    if(typeof num != "number")
     return "";
	if(typeof(n) == "undefined"){n=2;}
    return Math.round(num * Math.pow(10, n)) / Math.pow(10, n);
  },
  /**
  * 将数字不满位数补零。
  */
  fillLen:function(num, count){
   var len = ("" + num).length;
   if(len >= count){
    return num;
   }
   num = '0' + num;
   return jQuery.fillLen(num, count);
  },
  subContent:function(s,c){
   return s.length > c ? s.substring(0,c) + '...' : s; 
  }
,dataStore:{
   data:[],
   get:function(key){
    var d = this.data;
    for(var i = 0; i < d.length; i++){
     if(key === d[i].k){
      return d[i].v;
     }
    }
    return null;
   },
   put:function(key,value){
    var d = this.data;
    var flag = false;
    for(var i = 0; i < d.length; i++){
     if(key === d[i].k){
      d[i].v = value;
      flag = true;
      break;
     }
    }
    if(!flag)
     d.push({k:key,v:value}); 
   },
   clear:function(){
    this.data = new Array();
   },
   size:function(){
    return this.data.length;
   }
  } });
 //多余字符用...代替，是否以title形式显示全部内容
 jQuery.fn.subContent = function(c,isShowTitle){
  return this.each(function(){
   var s = jQuery.trim(jQuery.text(jQuery(this)));
   jQuery(this).text(jQuery.subContent(s, c));
   if(isShowTitle){
    jQuery(this).attr("title",s);
   }
  });
 };
 
 /**将数值转换为千分位。jQuery对象扩展:
  //默认整数，
  //digit：小数位数
 */
 jQuery.fn.fNum = function(digit){
  return this.each(function(){
   var val = jQuery.trim(jQuery.text(jQuery(this)));
   jQuery(this).text(function(){
    if(digit)
     return val == "" ? val : jQuery.fAmount(parseFloat(val,10),digit);
    else
      return val == "" ? val : jQuery.fInt(parseInt(val));
   });
  });
 };
 
 /**
  将比例形式的数据转化为百分比表示 
 */
 jQuery.fn.percentum = function(digit){
  return this.each(function(){
   var val = jQuery.trim(jQuery.text(jQuery(this)));
   jQuery(this).text(function(){
    return val == "" ? val : (jQuery.roundDigit(parseFloat(val) * 100,digit) + "%") ;
   });
  });
 };
 
 jQuery.fn.roundDigit = function(digit){
  return this.each(function(){
   var val = jQuery.trim(jQuery.text(jQuery(this)));
   jQuery(this).text(function(){
    return val == "" ? val : (jQuery.roundDigit(parseFloat(val),digit));
   });
  });
 };
 
 /**
  修改元素是否可用
 */
jQuery.fn.disable = function(flag) {
     return this.each(function() {
      this.disabled = typeof this.disabled != "undefined" && flag;
     });
};


//前端一些公共事件
function isNumKey(event,_obj){
	var key = event.keyCode ? event.keyCode : event.which;
	console.log("keydown.key="+key);
	//小键盘96-105
	if ((key >= 48 && key <= 57) ||(key >= 96 && key <= 105) || key==8 || key==46  || key==37 || key==39 || key==13){
		//console.log(">>>_obj.val()="+_obj.val());
		if($.trim(_obj.val())=='' || parseInt(_obj.val())<=0){
			_obj.val("1");
		}		
		return true;	
	}
	return false;
}

function isNumEmpty(v){
	if(isNaN(v) || $.trim(v)=='' || parseInt(v)<=0)
		return true;
	return false;
}

function roundDigit(num,n){
	num = parseFloat(num);
	if (isNaN(num) || typeof num != "number" || num==0) {return num;} 
	if(typeof(n) == "undefined"){n=2;}
    num = Math.round(num * Math.pow(10, n)) / Math.pow(10, n);
	return num.toFixed(n);
}
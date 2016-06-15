//rooturl ="http://" + document.domain +":80/resourceMananger/";
host = "http://" + document.domain;
var success ='<div class="alert alert-success"><button data-dismiss="alert" class="close" type="button">×</button><strong>操作成功!</strong></div>';
function error(msg){
	html = '<div class="alert alert-error"> <button data-dismiss="alert" class="close" type="button">×</button><strong>失败了~</strong> '+msg+ ' </div>';
	return html;
};
function setCookie(c_name,value,expiredays)
{
	var exdate=new Date();
	exdate.setDate(exdate.getDate()+expiredays);
	document.cookie=c_name+ "=" +escape(value)+	((expiredays==null) ? "" : "; expires="+exdate.toGMTString());
};
// Retrieve the value of the cookie with the specified name.
function GetCookie(sName)
{
	// cookies are separated by semicolons
	var aCookie = document.cookie.split("; ");
	for (var i=0; i < aCookie.length; i++)
	{
	// a name/value pair (a crumb) is separated by an equal sign
	var aCrumb = aCookie[i].split("=");
	if (sName == aCrumb[0])
	return unescape(aCrumb[1]);
	}
	// a cookie with the requested name does not exist
	return null;
};
// Delete the cookie with the specified name.
function DelCookie(sName)
{
document.cookie = sName + "=" + escape(sValue) + "; expires=Fri, 31 Dec 1999 23:59:59 GMT;";
};

function exportToExcel(tableid){
    var curTbl = document.getElementById(tableid);
    var oXL;
    try{
       oXL = new ActiveXObject("Excel.Application"); //创建AX对象excel 
    }catch(e){
       alert(e+"无法启动Excel!\n\n如果您确信您的电脑中已经安装了Excel，"+"那么请调整IE的安全级别。\n\n具体操作：\n\n"+"工具 → Internet选项 → 安全 → 自定义级别 → 对没有标记为安全的ActiveX进行初始化和脚本运行 → 启用");
       return false;
    }
    var oWB = oXL.Workbooks.Add(); //获取workbook对象
    var oSheet = oWB.ActiveSheet;//激活当前sheet 
    var sel = document.body.createTextRange();
    sel.moveToElementText(curTbl); //把表格中的内容移到TextRange中
    sel.select(); //全选TextRange中内容 
    sel.execCommand("Copy");//复制TextRange中内容 
    oSheet.Paste();//粘贴到活动的EXCEL中
    oXL.Visible = true; //设置excel可见属性
    var fname = oXL.Application.GetSaveAsFilename("将table导出到excel.xls", "Excel Spreadsheets (*.xls), *.xls");
    oWB.SaveAs(fname);
    oWB.Close();
    oXL.Quit();
}

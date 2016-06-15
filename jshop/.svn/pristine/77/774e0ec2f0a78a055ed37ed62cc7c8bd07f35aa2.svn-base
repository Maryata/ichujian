<%@page import="net.jeeshop.core.util.PathUtil"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../static/css/style.css">
<title>Insert title here</title>
</head>
<body>
<%
	String pay_url = PathUtil.getValue("pay_url");
%>
<form name="payment" action="<%= pay_url %>" method="POST" target="_blank">
	<table border="1" cellpadding="2" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111">
		<tr>
			<td>
				<font color=red>*</font>商户号
			</td>
	
			<td>
	            ${sessionScope.MerId }
	        </td>
		</tr>
		<tr>
			<td>
				<font color=red>*</font>订单号
			</td>
	
			<td>
	                   ${sessionScope.MerOrderNo }
	        </td>
		</tr>
		<tr>
			<td>
				<font color=red>*</font>订单金额
			</td>
	
			<td>
	                   ${sessionScope.OrderAmt }
	        </td>
		</tr>
		<tr>
			<td>
				<font color=red>*</font>交易日期
			</td>
	
			<td>
	                   ${sessionScope.TranDate }
	        </td>
		</tr>
		<tr>
			<td>
				<font color=red>*</font>交易时间
			</td>
	
			<td>
	                   ${sessionScope.TranTime } 
	        </td>
		</tr>
		<tr>
			<td>
				交易类型
			</td>
	
			<td>
	                   ${sessionScope.TranType } 
	        </td>
		</tr>
		
		<tr>
			<td>
				<font color=red>*</font>业务类型
			</td>
	
			<td>
	                   ${sessionScope.BusiType }
	           </td>
		</tr>
		<tr>
			<td>
				<font color=red>*</font>版本号
			</td>
	
			<td>
	                  ${sessionScope.Version}
	           </td>
		</tr>
		<tr>
			<td>
				<font color=red></font>支付超时时间
			</td>
	
			<td>
	                 ${sessionScope.PayTimeOut } 
	           </td>
		</tr>
		
		<tr>
			<td>
				分账类型
			</td>
	
			<td>
	                  ${sessionScope.SplitType } 
	           </td>
		</tr>
		<tr>
			<td>
				分账方式
			</td>
	
			<td>
	                 ${sessionScope.SplitMethod } 
	           </td>
		</tr>
		<tr>
			<td>
				分账公式
			</td>
	
			<td>
	                 ${sessionScope.MerSplitMsg }
	           </td>
		</tr>
		<tr>
			<td>
				<font color=red></font>支付机构号
			</td>
	
			<td>
	                  ${sessionScope.BankInstNo } 
	           </td>
		</tr>
		
		<tr>
			<td>
				<font color=red></font>商户系统时间戳
			</td>
	
			<td>
	                  ${sessionScope.TimeStamp }
	           </td>
		</tr>
		<tr>
			<td>
				<font color=red></font>商户客户端IP
			</td>
	
			<td>
	                   ${sessionScope.RemoteAddr } 
	           </td>
		</tr>
		
		<tr>
			<td>
				<font color=red></font>交易币种
			</td>
	
			<td>
	                  ${sessionScope.CurryNo } 
	           </td>
		</tr>
		
		<tr>
			<td>
				接入类型
			</td>
	
			<td>
	                   ${sessionScope.AccessType }
	           </td>
		</tr>
		<tr>
			<td>
				<font color=red></font>收单机构号
			</td>
	
			<td>
	                  ${sessionScope.AcqCode }
	           </td>
		</tr>
		<tr>
			<td>
				<font color=red></font>商品信息描述
			</td>
	
			<td>
	                  ${sessionScope.CommodityMsg }
	           </td>
		</tr>
		
		<tr>
			<td>
				页面应答接收URL
			</td>
	
			<td>
	                  ${sessionScope.MerPageUrl } 
	           </td>
		</tr>
					<tr>
			<td>
				<font color=red></font>后台应答接收URL
			</td>
	
			<td>
	                  ${sessionScope.MerBgUrl }
	              </td>
		</tr>
		<tr>
			<td>
				商户私有域
			</td>
	
			<td>
	                  ${sessionScope.MerResv } 
	        </td>
		</tr>
		<tr>
			<td>
				交易扩展域
			</td>
	
			<td>
	                  ${sessionScope.TranReserved } 
	        </td>
		</tr>
		<tr>
			<td>
				有卡交易信息域
			</td>
	
			<td>
	                  ${sessionScope.CardTranData } 
	        </td>
		</tr>
		<tr>
			<td>
				签名信息
			</td>
	
			<td>
	                  ${sessionScope.Signature} 
	        </td>
		</tr>
	</table>
	<hr>
<%
	Enumeration<String> requestAttributeNames = request.getSession().getAttributeNames();
	//商户发送的报文key值
	String params = "TranReserved;MerId;MerOrderNo;OrderAmt;CurryNo;TranDate;SplitMethod;BusiType;MerPageUrl;MerBgUrl;SplitType;MerSplitMsg;PayTimeOut;MerResv;Version;BankInstNo;CommodityMsg;Signature;AccessType;AcqCode;OrderExpiryTime;TranType;RemoteAddr;Referred;TranTime;TimeStamp;CardTranData";
	while(requestAttributeNames.hasMoreElements()){
		String name = requestAttributeNames.nextElement();
		if(params.contains(name)){
		String value = (String)request.getSession().getAttribute(name);
%>	
		<input type="hidden" name = '<%=name %>' value ='<%=value%>'/>
<%		}	
	}
%>
	如果您的浏览器没有弹出支付页面，请点击按钮<input type='button' value='提交订单' onClick='document.payment.submit()'>再次提交。
</form>
<script language=JavaScript>
	document.payment.submit();
</script>	
</body>
</html>
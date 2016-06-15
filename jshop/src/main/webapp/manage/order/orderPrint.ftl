<#import "/manage/tpl/htmlBase.ftl" as html>
<@html.htmlBase>
<script type="text/javascript">
 //打印
 function doPrint() {
	var bdhtml=window.document.body.innerHTML;
	var sprnstr="<!--startprint-->";
	var eprnstr="<!--endprint-->";
	var prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
	var prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
	window.document.body.innerHTML=prnhtml;
	if(window.print){
	    agree=confirm("确定要打印吗？");
	    if(agree){
	        window.print();
	    }
	}
	window.document.body.innerHTML=bdhtml;
} 
</script>
<div class="container">
	<div class="row">
		<div class="span12">
			<!--startprint-->
			<table class="table table-bordered">
				<tr><td colspan="9">订单信息</td></tr>
				<tr>
					<th nowrap="nowrap">订单号</th>
					<td nowrap="nowrap">${e.id!""}</td>
					<th nowrap="nowrap">订单总金额</th>
					<td nowrap="nowrap">${e.amount!""}</td>
					<th nowrap="nowrap">数量</th>
					<td nowrap="nowrap"  colspan="4">${e.number!""}</td>
				</tr>
				<tr>
					<th nowrap="nowrap">下单日期</th>
					<td nowrap="nowrap" style="word-break:break-all;" width="90px;">${e.createdate!""}</td>
					<th nowrap="nowrap">物流费用</th>
					<td nowrap="nowrap">
					 <#if e.expressType=="0">
           	         到付
                     </#if>
		             <#if e.expressType=="1">
		               	包邮
		             </#if>
                     </td>
					<th nowrap="nowrap">联系电话</th>
					<td nowrap="nowrap" colspan="4">
					<#if e.ordership??>
						${e.ordership.phone!""}
						</#if>
					</td>
				</tr>
				<tr>
					<th nowrap="nowrap">收货人</th>
					<td>
						<#if e.ordership??>
						${e.ordership.shipname!""}
						</#if>
					</td>
					<th nowrap="nowrap">联系地址</th>
					<td colspan="9">
						<#if e.ordership??>
						${e.ordership.shiparea!""},${e.ordership.shipaddress!""}
						</#if>
					</td>
				</tr>
				
				<tr><td colspan="9">&nbsp;产品明细</td></tr>
				
				<tr>
					<td width="10%">序号</td>
					<td width="10%">商品编号</td>
					<td width="35%">商品名称</td>
					<#--<td width="18%">适配品牌</td>
					<td width="12%">适配型号</td>
					<td width="15%">材质</td>-->
					<td width="15%">数量</td>
					<td width="15%">单价</td>
					<!--<td>配送费</td>-->
					<td width="15%">小计</td>
				</tr>
				<#assign materialMap = sysDic("product_material") >
				<#list e.orderdetail as item>
					<tr>
						<td>&nbsp;${item_index+1}</td>
						<td>${item.pcode!""}</td>
						<td>${item.productName!""}</td>
						<#--<td>${item.mainCatalogName!""}</td>
						<td>${item.productName!""}</td>
						<td>${item.childrenCatalogName!""}</td>
						<td>${item.material!""}</td>-->
						<td>${item.number!""}</td>
						<td>￥${item.price!""}</td>
						<!--<td>￥${item.fee!""}</td>-->
						<td>￥${item.total0!""}</td>
						</td>
					</tr>
				</#list>
			</table>
	<!--endprint-->
		</div>
	</div>	
</div>

<div style="text-align: center;">
    <input type="button" class="btn btn-primary" onclick="javascript:doPrint();" value="打印" name="button_print"/>
       <a class="btn  btn-success"  href="${basepath}/manage/order/toEdit?id=${e.id!""}">
				     <i class="icon-search icon-white"></i> 返回
	 </a>
</div>
</body>
</html>

</@html.htmlBase>
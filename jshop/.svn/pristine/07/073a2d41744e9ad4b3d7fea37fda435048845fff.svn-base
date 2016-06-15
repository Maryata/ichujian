<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="样品申请清单">
	<form action="${basepath}/manage/sampleApply" method="post" theme="simple">
	<input type="hidden" name="pager.offset" id="pager_offset" value="0">
	    <div class="table-responsive">
		<table class="table table-bordered table-condensed table-hover">
			<tr>
			    <td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">会员名称</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<input type="text" value="${e.contact!""}" name="contact" style="width:80%;"
				 		class="search-query input-small" id="contact" />
					</div>
				</td>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">公司名称</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<input type="text" value="${e.company!""}" name="company" style="width:80%;"
				 		class="search-query input-small" id="companyName" />
					</div>
				</td>
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">手机号</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<input type="text" value="${e.phone!""}" name="phone" style="width:80%;" 
						class="search-query input-small" id="phone" />
					</div>
				</td>
			</tr>
			<tr>
			    <td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">创建人员</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<input type="text" value="${e.createUser!""}" name="createUser" style="width:80%;" 
						class="search-query input-small" id="createUser" />
					</div>
				</td>		
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:8px;">日期</div></td>
				<td style="text-align: left;" colspan="3" nowrap="nowrap">
					<div style="text-align: left;margin-top:1px;">
					<input id="d4311" class="Wdate search-query input-small" type="text" name="startDate" style="width:40%;"
					value="${e.startDate!""}"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
					~ 
					<input id="d4312" class="Wdate search-query input-small" type="text" name="endDate" style="width:40%;"
					value="${e.endDate!""}"
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>
					</div>
				</td>
			</tr>
			<tr>
			    <td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">样品单号</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<input type="text" value="${e.sampleNo!""}" name="sampleNo" style="width:80%;" 
						class="search-query input-small" id="sampleNo" />
					</div>
				</td>		
				<td style="text-align: right;" nowrap="nowrap"><div style="text-align: center;margin-top:14px;">审核状态</div></td>
				<td style="text-align: left;">
					<div style="text-align: left;margin-top:8px;">
					<select id="status" name="status" class="search-query input-medium">
                        <option value="">请选择</option>
                            <option value="0">未审核</option>
                            <option value="1">已审核</option>
                    </select>
					</div>
				</td>	
			</tr>
			<tr>
				<td colspan="28">
					<button method="selectList" class="btn btn-primary btn-select" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</button>
					<a href="${basepath}/manage/sampleApply/toSampleApplyAdd" class="btn btn-success">
						<i class="icon-plus-sign icon-white"></i> 样品添加
					</a>
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<#include "/manage/system/pager.ftl"/>
					</div>
				</td>
			</tr>
		</table>
        </div>
		<div class="table-responsive">
		<table class="table table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th nowrap="nowrap">查看</th>
				<th nowrap="nowrap">样品序号</th>
				<th nowrap="nowrap">姓名</th>
				<th nowrap="nowrap">地址</th>
				<th nowrap="nowrap">手机号</th>
				<th nowrap="nowrap">公司名称</th>
				<th nowrap="nowrap">创建日期</th>
				<th nowrap="nowrap">创建人</th>
			</tr>
			<#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="ids" value="${item.id!""}"</td>
					<td nowrap="nowrap">
					<a href="javascript:" class="btn btn-info" onClick="findMessage(${item.id!""},this)">查看详情</a>
					<#if item.status?? && item.status=="0">
						<#if checkPrivilege("/manage/sampleApply/checkButton")>
						<a href="javascript:" class="btn btn-primary btn-select" onClick="$.load_sampleapply_stock(this,${item.id!""})">审核</a>
						</#if>
					</#if>
					</td>
					<td nowrap="nowrap">&nbsp;${item.sampleNo!""}</td>
					<td nowrap="nowrap">&nbsp;${item.contact!""}</td>
					<td nowrap="nowrap">&nbsp;${item.address!""}</td>
					<td nowrap="nowrap">&nbsp;${item.phone!""}</td>
					<td nowrap="nowrap">&nbsp;${item.company!""}</td>
					<td nowrap="nowrap">&nbsp;${item.createdate!""}</td>
					<td nowrap="nowrap">&nbsp;${item.createUser!""}</td>
					
				</tr>
			</#list>
			<tr>
				<td colspan="16" style="text-align: center;">
					<#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>
        </div>
	</form>
	<script type="text/javascript" src="${basepath}/resource/layer-v1.9.2/layer/layer.js"></script>	
	<script>
	function findMessage(id,thsBtn){
	  $(thsBtn).attr('disabled',"true");
	  $.ajax({
 	    url:'${basepath}/manage/sampleApply/checkMessage',
		type:"POST",
		dataType:'json',
		data: {id :id},
		success:function(data){
		   var customer = data.customer;
		   var _info="";
		   if(customer.length==0){
		      _info+="当前用户暂无申请过样品";
		      _lay(_info);
		   }else{
		      _info+="<table><tr><th width='250px;'>产品名称</th><th width='130px;'>创建时间</th><th width='50px;'>数量</th></tr>";
		      for(var m = 0;m<customer.length;m++){
                var type = customer[m];
                _info+="<tr>";
	            _info+="<td>"+type.productName+"</td><td>"+type.creatTime+"</td><td>"+type.productNum+"</td>";
	            _info+="</tr>";
               }
            _info+="</table>";
            _lay(_info);
		   }
		  }
      });
      function _lay(info){
	      layer.tips(info, $(thsBtn), {
			    tips: [4, '#78BA32']
			});
	  }
	   $(thsBtn).removeAttr("disabled");
	}
	</script>
	
<script type="text/javascript" src="${basepath}/manage/sampleApply.js"></script>	

<div id="detailsPage" style="display:none;height: 410px;overflow: auto;">
<table class="table table-hover" >
   <thead>
	<tr style="background-color: #dff0d8">
		<th nowrap="nowrap">序号</th>
		<th nowrap="nowrap">产品代码</th>
		<th nowrap="nowrap">名称</th>
		<th nowrap="nowrap">购买数量</th>
		<th nowrap="nowrap">可销售库存</th>
	</tr>
  </thead>
  <tbody id="detailsList">
  </tbody>	
</table>
</div>
</@page.pageBase>
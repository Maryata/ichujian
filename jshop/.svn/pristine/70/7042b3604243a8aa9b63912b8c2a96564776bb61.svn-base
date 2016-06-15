<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="折扣方案管理" currentListUrl="/manage/discntSolutn/selectList?init=y">
<form method="post" theme="simple" id="form" >
<table class="table table-bordered">
	<table class="table table-bordered">
		<tr>
			<td colspan="2" style="background-color: #dff0d8;text-align: center;">
				<strong>添加折扣方案</strong>
			</td>
		</tr>
		<tr style="display: none;">
			<th>id</th>
			<td><input type="hidden" value="${e.id!""}" name="id" id="id"/></td>
		</tr>
		<tr>
			<th style="text-align: right;">方案名称</th> 
			<td style="text-align: left;">
				<input type="text" value="${e.name!""}" data-rule="方案名称:required;name;length[1~15]" name="name" id="name"/>
			</td>
		</tr>
		<tr>
			<th style="text-align: right;">备注</th>
			<td style="text-align: left;">
				<input type="text" value="${e.comment!""}" data-rule="方案名称:comment;length[0~25]" name="comment" id="comment" />
			</td>
		</tr>
		<tr>
			<th style="text-align: right;">状态</th>
			<td style="text-align: left;" >
                <select class="input-small" id="status" name="status" >
                    <option value="y" <#if e.status?? && e.status == "y">selected="selected" </#if>>启用</option>
                    <option value="n"<#if e.status?? && e.status == "n">selected="selected" </#if>>禁用</option>
                </select>
			</td>
		</tr>
	</table>
	<table class="table table-bordered">
		<tr>
			<td style="text-align:right;">
                <input type="button" class="btn btn-success" onclick="addDetail()" value="新增方案详情">
                </input>
			</td>
		</tr>
	</table>
	<table class="table table-bordered" id="tb_detail" style="display: none;">
		<span id="nullVal"></span>
		<tr>
			<th style='text-align: left;width:31%;'>最低交易额度</th>
			<th style='text-align: left;width:31%;'>最高交易额度</th>
			<th style='text-align: left;width:31%;'>折扣点</th>
			<th style='text-align: center;width:7%;'>删除</th>
		</tr>
		<#if list??>
			<#list list as item>
			<tr>	
				<input type="hidden" name='sid' value='${item.sid}' />
				<input type="hidden" name='ids' value='${item.id}' />
				<td style='text-align:left;'>
					<input style='width:100px;' type='text' name='minVals' value='${item.minVal}'/>
				</td>
				<td style='text-align:left;'>
					<input style='width:100px;' type='text' name='maxVals' value='${item.maxVal}'/>
				</td>
				<td style='text-align:left;'>
					<input style='width:100px;' type='text' name='rates' value='${item.rate}'/>
				</td>
				<td style='text-align:center;'>
					<button class='btn btn-danger' onclick="delCurrTr(this,${item.id})">
						<i class='icon-remove-sign icon-white'></i>删除
					</button>
				</td>
			</tr>
			</#list>
		</#if>
	</table>
	<table class="table table-bordered">
		<tr>
			<td style="text-align: center;">
				<#if !e.id??>
                <input type="submit" method="addDiscnt" class="btn btn-success" onclick="submitt(1)" value="新&nbsp;&nbsp;&nbsp;增">
                </input>
                <#else>
                <input type="submit" method="updateDiscnt" class="btn btn-success" onclick="submitt(2)" value="保&nbsp;&nbsp;&nbsp;存">
                </input>
                </#if>
			</td>
		</tr>
	</table>
</table>
</form>
<script type="text/javascript">
$(function() {
	if(!jQuery.isEmptyObject($("#id").val())){
		$("#tb_detail").show();
	}
	$("#name").focus();
});
function addDetail(){
	if($("#tb_detail tr").length>10){
		alert("方案详情最多10条！");
		return;
	}
	$("#tb_detail").show();
	var _s = "<tr><input type='hidden' name='ids' value='-1'/>";
	_s += "<td style='text-align:left;'><input style='width:100px;' type='text' name='minVals'/></td>";   
	_s += "<td style='text-align:left;'><input style='width:100px;' type='text' name='maxVals'/></td>";
	_s += "<td style='text-align:left;'><input style='width:100px;' type='text' name='rates'/></td>";
	_s += "<td style='text-align:center;'><button class='btn btn-danger' onclick=\"delCurrTr(this)\"><i class='icon-remove-sign icon-white'></i>删除</button></td></tr>";
	$("#tb_detail").append(_s);
}
function delCurrTr(_tr,id){
	$(_tr).parent('td').parent('tr').remove();
	if($("#tb_detail tr").length==1){
		$("#tb_detail").hide();
	}
	if(id){
		$.post("${basepath}/manage/discntSolutn/delDetailById",{id:id},function(data){
			if($.parseJSON(data)==1){
				alert("删除成功！");
			}else{
				alert("删除失败！");
			}
		});
	}
}
function validForm(){
	var _valid = true;
	var _firstTr = true;
	var _flag = true;
	var _minVal = 0;
	var _maxVal = 0;
	$("#tb_detail tr").each(function(){
		// 不处理表头
		if(_firstTr){
			_firstTr = false;
			return true;
		}
		// 如果第一条方案详情的“最低额度”为空，设置默认值为0
		if(_flag && $.trim($(this).find("[name='minVals']").val())==''){
			$("#tb_detail input:first").val("0");
		}
		var _min = parseInt($(this).find("[name='minVals']").val());
		var _max = parseInt($(this).find("[name='maxVals']").val());
		if(_min >= _max){
				_valid = false;
				alert("同一条详情的最低额度不能高于最高额度！");
				$(this).find("[name='minVals']").focus();
				return;
		}
		// 除第一条方案详情，其他行皆判断最小额度是否大于前一行的最大额度
		if(!_flag){
			if(_min < _minVal){
				_valid = false;
				alert("后一行的最低额度必须高于前一行最高额度！");
				$(this).find("[name='minVals']").focus();
				return;
			}
		}
		// 折扣点校验
		var _rate = $(this).find("[name='rates']").val();
		// 非空校验
		if($.trim(_rate)==''){
			_valid = false;
			alert("折扣点不能为空！");
			$(this).find("[name='rates']").focus();
			return;
		}
		// 必须是数字
		var _numRate = parseInt(_rate);
		if(isNaN(_numRate)){
			_valid = false;
			alert("折扣点必须是数字！");
			$(this).find("[name='rates']").focus();
			return;
		}
		// 必须以“0.”开头
		if(_rate.substring(0,2) != "0."){
			_valid = false;
			alert("折扣点必须以'0.'开头！");
			$(this).find("[name='rates']").focus();
			return;
		}
		if($.trim(_rate.substring(2,_rate.length))==''){
			_valid = false;
			alert("折扣点格式不正确！");
			$(this).find("[name='rates']").focus();
			return;
		}else if(parseInt(_rate.substring(2,_rate.length))==0){
			_valid = false;
			alert("折扣点格式不正确！");
			$(this).find("[name='rates']").focus();
			return;
		}
		_minVal = _max;
		_flag = false;
	});
	var _lastMaxVal = $("#tb_detail tr:last").find("[name='maxVals']").val();
	if(_lastMaxVal==null || _lastMaxVal==""){
		$("#tb_detail tr:last").find("[name='maxVals']").val("99999999999");
	}
	return _valid;
}
function submitt(o){
	var _valid = validForm();
	if(_valid){
		if(o==1){
			//$("#form").attr("action","${basepath}/manage/discntSolutn/addDiscnt");
			$("#form").attr("action","${basepath}/manage/discntSolutn");
		}
		if(o==2){
			$("#form").attr("action","${basepath}/manage/discntSolutn");
		}
		//$("#form").submit();
	}	
}
</script>
</@page.pageBase>
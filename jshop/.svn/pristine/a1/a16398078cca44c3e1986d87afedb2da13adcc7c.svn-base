<#import "/manage/tpl/pageBase.ftl" as page/>
<@page.pageBase currentMenu="部门管理">
<script type="text/javascript">
	$(function() {
		$("#treegrid").treegrid({"treeColumn":1});
	});
	function hasSubDept(id){
		// 删除之前，先判断要删除的部门是否有下级部门。如果有，则不能直接删除。须先删除下级部门
		$.post("${basepath}/manage/dept/hasSubDept",{id:id},function(data){
			if($.parseJSON(data)==1){
				alert("当前部门有下级部门，请先删除下级部门！");
				return;
			}else{
				deleteSelect(id);
			}
		});
	}
	function deleteSelect(id) {
		try{
			if(confirm("确定删除当前记录?")){
				$.blockUI({ message: "系统处理中，请等待...",css: { 
			        border: 'none', 
			        padding: '15px', 
			        backgroundColor: '#000', 
			        '-webkit-border-radius': '10px', 
			        '-moz-border-radius': '10px', 
			        opacity: .5, 
			        color: '#fff' 
			    }});
				var _url = "deleteById?id="+id;
				$.ajax({
				  type: 'POST',
				  url: _url,
				  data: {},
				  async:false,
				  success: function(data){
					  console.log("ajax.data="+data);
					  if(data=="1"){
					  	var _form = $("#form");
					  	_form.attr("action","${basepath}/manage/dept/deptList");
					  	_form.submit();
					  }else if(data=="0"){
					  	alert("删除失败！");
					  }
					  jQuery.unblockUI();
				  },
				  dataType: "text",
				  error:function(){
					  	jQuery.unblockUI();
						alert("加载失败，请联系管理员。");
				  }
				});
			}
		}catch(e){
			console.log("eee="+e);
		}
		return false;
	}
	//编辑
	function editSelect(id){
        var _url = "toDeptEdit?id="+id;
        var _form = $("#form");
		_form.attr("action",_url);
		_form.submit();
	}
</script>
<form action="${basepath}/manage" name="form" id="form" method="post" theme="simple">
	<table class="table table-bordered table-condensed table-hover">
		<tr>
			<td colspan="16">
				<button method="${basepath}/manage/dept/deptList" class="btn btn-primary" onclick="selectList(this)">
					<i class="icon-search icon-white"></i> 查询
				</button>
				<a href="${basepath}/manage/dept/toDeptAdd" class="btn btn-success">
					<i class="icon-plus-sign icon-white"></i> 添加
				</a>
			</td>
		</tr>
	</table>


	<div class="alert alert-info" style="margin-bottom: 2px;">友情提示：部门一般分为两层，部门、下属部门。部门编码必须唯一。</div>
	<table id="treegrid" title="部门目录" class="table tree table-bordered" style="min-width:800px;min-height:250px">
		<thead>
			<tr>
				<th data-options="field:'id'" nowrap="nowrap">ID</th>
				<th data-options="field:'name'" nowrap="nowrap">部门名称</th>
				<th data-options="field:'order1'" nowrap="nowrap">排序</th>
				<th data-options="field:'code'" nowrap="nowrap">编码</th>
				<th nowrap="nowrap" align="right">操作</th>
			</tr>
			<#list list as item>
            <tr class="treegrid-${item.id} ${(item.pid=="1")?string("","treegrid-parent-"+item.pid)}">
                <td>${item.id!""}</td>
                <td>${item.name!""}</td>
                <td>${item.orderNum!""}</td>
                <td>${item.code!""}</td>
				<td>
					<button class="btn btn-warning" onclick="return editSelect('${item.id}');">
                    <i class="icon-edit icon-white"></i> 编辑
                	</button>
                    <input type="button" method="deletes" class="btn btn-danger"  
                    	onclick="return hasSubDept('${item.id}');" value="删除"> 
                    </input>
				</td>
            </tr>
			</#list>
		</thead>
	</table>
</form>	
<link rel="stylesheet" type="text/css" href="${basepath}/resource/jquery-treegrid/css/jquery.treegrid.css">
<script type="text/javascript" src="${basepath}/resource/jquery-treegrid/jquery.treegrid.js"></script>
<script type="text/javascript" src="${basepath}/resource/jquery-treegrid/jquery.treegrid.bootstrap3.js"></script>
</@page.pageBase>
<#assign pg = JspTaglibs["/WEB-INF/jsp/pager-taglib.tld"]/>
<!-- 分页标签 -->
<#if pager.list?? && pager.pagerSize gt 1>
	<div class="page-box f-r">

		<@pg.pager url="${pager.pagerUrl}" items=pager.total
		export="currentPageNumber=pageNumber"
		maxPageItems=pager.pageSize maxIndexPages=8 isOffset=true>
			<@pg.param name="fullparam"/>
        <ul>
            <#if orderBy?? && orderBy != 0>
				<@pg.param name="orderBy"/>
            </#if>
			<@pg.prev>
                <li class="on-btn"><a href="${pageUrl}" class="pageLink">上一页</a></li>
			</@pg.prev>
			<@pg.pages>
				<#if currentPageNumber==pageNumber>
                    <li><a class="active">${pageNumber}</a></li>
				<#else>
                    <li><a href="${pageUrl}" class="pageLink">${pageNumber}</a></li>
				</#if>
			</@pg.pages>
			<@pg.next>
                <li class="next-btn"><a href="${pageUrl}" class="pageLink">下一页</a></li>
			</@pg.next>
		</ul>
		</@pg.pager>
		<div class="page-nub">共${pager.pagerSize}页 </div>
		<div class="form">
			<span class="text">到第</span>
			<input class="page-inp" id="page_to_Num" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" type="number" value="" min="1" max="${pager.pagerSize}" aria-label="页码输入框">
			<span class="text">页</span>
			<a id="page_to_btn" class="ok-btn" >确定</a>
		</div>
	</div>
</#if>

<script>

function loadPageFun(){
  $("#page_to_Num").change(function(){
  	var num = $("#page_to_Num").val();
  	if(''==num || !/^[0-9]*$/.test(num)){
	 	$("#page_to_Num").val(1);
	}
  	else if (parseInt(num) > parseInt(this.max)) { 
  		$("#page_to_Num").val(this.max);
  	}
  });
  
  $("#page_to_btn").click(function(){
	 var num = $("#page_to_Num").val();
	 if(''==num || !/^[0-9]*$/.test(num)){
	 	return false;
	}
	num = parseInt(num)-1;
	if(num>-1){
		var p = (num)* parseInt(${pager.pageSize});	
		var rooturl = $.UrlParamDel(window.location.href,"pager.offset");
		window.location = $.UrlParams(rooturl,"pager.offset",p);
	}
  });
}

  function selectPagesize(field){
     window.location = document.getElementById("firstPage").href + "&pagesize="+field.value;
  }
  
(function($, w, d){
loadPageFun();
}(jQuery, window, document));
</script>

<script>
(function($, w, d){
	var rooturl = $.UrlParamDel(window.location.href,"pager.offset");
	$("a.pageLink[href]").each(function(){
		var _this = $(this);
		//console.log("----:"+_this.attr("href"));
		var p = $.UrlParams(_this.attr("href"),"pager.offset");
		if(p){
			_this.attr("href",$.UrlParams(rooturl,"pager.offset",p));
		}
	});  
}(jQuery, window));
</script>

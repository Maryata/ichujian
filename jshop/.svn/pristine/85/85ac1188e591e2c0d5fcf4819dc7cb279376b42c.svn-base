<#assign pg = JspTaglibs["/WEB-INF/jsp/pager-taglib.tld"]/>
<!-- 分页标签 -->
1<#if pager.list?? && pager.pagerSize gt 1>

	<div class="page-box">
	  <@pg.pager url="${pager.pagerUrl}" items=pager.total  export="currentPageNumber=pageNumber"	maxPageItems=pager.pageSize maxIndexPages=10 isOffset=true>
	  <!--<ul>
	  	<#if orderBy?? && orderBy != 0> 
	  		<@pg.param name="orderBy"/>
	  	</#if>
	  	<@pg.prev>
	  	<li class="on-btn"><a class="none" href="${pageUrl}">上一页</a></li>
	  	</@pg.prev>
	  	
	  	<@pg.pages>
			<#if currentPageNumber==pageNumber>
                <li ><a href="#" style="background-color: red;border-color: red;cursor: default;">${pageNumber}</a></li>
			<#else >
                <li><a href="${pageUrl}" class="pageLink">${pageNumber}</a></li>
			</#if>
		</@pg.pages>
	  	
	  	<@pg.next>
  		<li class="next-btn"><a href="${pageUrl}">下一页</a></li>
		</@pg.next>
		-->
	  </ul>
	  
	  <!--
	  <div class="page-nub">共${pager.pagerSize}页(${pager.total})条，</div>
	  <div class="form">
		<span class="text">到第</span>
		<input class="page-inp"  type="number" value="3" min="1" max="100" aria-label="页码输入框">
		<span class="text">页</span>
		<a href="#" class="ok-btn" >确定</a>
	  </div>
	  -->
	  
	</div>
	
</#if>

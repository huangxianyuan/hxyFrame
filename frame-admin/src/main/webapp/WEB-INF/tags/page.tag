<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="page" type="com.hxy.base.page.Page" required="true"%>
<%-- 表格分页工具栏，使用方法： 原样输出page --%>
${page}
<!-- pagination的css样式设定-->
<style>
.pagination > li > a, .pager > li > a{height: 34px!important;}
.pagination > li > a>i, .pager > li > a>i{line-height: 21px!important;}
.pagination > li > a, .pager > li > a{ padding: 6px 15px;}
.pagination>li>a, .pagination>li>span {
    color: #009688;
}
.pagination>.active>a{
    color: #fff;
    background: #009688;
}
</style>
<script type="text/javascript">
$(function(){
	var pagination =$(".pagination-info").html();
	if(pagination != "" && Number(pagination.split("总共")[1].split("条记录")[0]) >0 && $("#table-list").find("tbody tr").length == 0){
		page(1,10);
	}

	$('#search-from').append('<input id="pageNum" name="pageNum" type="hidden" value="${page.pageNum}"/>');
	$("#refresh").click(function(){
		$("#pageNum").val(0);
		$("#search-from  input").val("");
		$("#search-from  select").val("");
		$("#search-from").submit();
	});
	$("#searchSubmit").click(function(){
        $("#pageNum").val('1');
    });
})
function page(n,s){//翻页
    console.log($("#pageNum").val())
	$("#pageNum").val(n);
	$("#search-from").submit();
	$("span.page-size").text(s);
	return false;
}
</script>

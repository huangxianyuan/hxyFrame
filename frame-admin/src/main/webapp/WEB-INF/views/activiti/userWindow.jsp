<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>选择审批者</title>
	<%@include file="/common/commonCSS.jsp" %>
	<%@include file="/common/commonJS.jsp" %>
	<%@include file="/WEB-INF/views/include/taglib.jsp" %>
</head>

<body >
	<div class="main-container" id="main-container" style="margin-top: 20px;margin-left: 20px">
		<div class="row" style="margin-top: 20px;">
			<form class="layui-form" id="search-from" action="${webRoot}${url}">
				<div class="layui-form-item">
					<label class="layui-form-label" style="width:10%;">姓名：</label>
					<div class="layui-input-inline layui-input-inline">
						<input type="text" name="userName" value="${user.userName}"  placeholder="请输入搜索内容"  class="layui-input" >
						<input name="nodeAction" type="hidden" value="${flag }" />
					</div>
					<button class="layui-btn" id="searchSubmit"><i class="layui-icon">&#xe615;</i>搜 索</button>
					<button class="layui-btn layui-btn-warm" type="button" id="refresh">重 置</button>
					<button class="layui-btn" type="button" id="submitBtn">确 定</button>
				</div>
			</form>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<div class="row">
					<div class="col-xs-12">
						<table id="table-list" class="layui-table">
							<thead>
							<tr>
								<th>选择</th>
								<th>名称</th>
								<th>机构</th>
								<th>部门</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="user" items="${page.result }" varStatus="i">
								<tr id="user_${user.id }" >
									<td>
										<c:if test="${flag == '1'}">
											<input type="radio"  name="user" value="${user.id}">
										</c:if>
										<c:if test="${flag == '2'}">
											<input type="checkbox" name="user"  value="${user.id}">
										</c:if>
									</td>
									<td>${user.userName} </td>
									<td >${user.bapName }</td>
									<td>${user.baName }</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
					<sys:page page="${page}"></sys:page>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	//td checkBox事件
	$("#table-list tbody tr").off("click").click(function (e) {
		var tag = e.target.tagName;
	    if(tag == "INPUT"){
	        return;
		}
        var isCheck = $(this).find(":checkbox").is(":checked");
        if(isCheck){
            $(this).find(":checkbox").prop("checked",false);
        }else {
            $(this).find(":checkbox").prop("checked",true);
        }
    });

	//点单tr redio选中
    $(document).on("click","tr",function(){
        $("input:radio").prop("checked",false);
        $(this).find(":radio").prop("checked",true);
    });

    /**
     * 切换用户类型select事件
     */
    layui.use(['form'], function(){
        var form = layui.form;
        form.on('select(searchFilter)', function(data){
            $("#search-from").submit();
        });
    });
	
	//确定选择
	$("#submitBtn").click(function () {
		//父级搜索表单
        var userTab=$(parent.document.getElementById("main-container")).find("#userTab tbody");
		var html ="";
        var count =0;
        $("#table-list tbody input:checkbox:checked,#table-list tbody input:radio:checked").each(function () {
			var id = $(this).val();
			var flag = true;
            userTab.find("tr").each(function () {
                var trid = $(this).attr("id");
                if(trid == id){
					flag =false;
                }
            })
			if(flag){
                var tds =$(this).parent().siblings();
                html+="<tr id='"+id+"'>";
                html+="  <td>"+tds[0].innerText+"</td>";
                html+="  <td style='display: none'>";
                html+="  	<input name='userIds' value='"+id+"'/>";
                html+="  </td>";
                html+="  <td>"+tds[1].innerText+"</td>";
                html+="  <td>"+tds[2].innerText+"</td>";
                html+='<td><button type="button" onclick="delUser(this)" class="btn btn-xs btn-white btn-danger"><i class="fa fa-trash-o"></i> 删 除 </button></td>';
                html+="</tr>";
                count++;
            }
        });
        if('${flag}'=='1' && html != ''){
            userTab.html(html);
        }
        if('${flag}'=='2'){
            userTab.append(html);
        }
        closeThisWindow();
    });

</script>
</html>
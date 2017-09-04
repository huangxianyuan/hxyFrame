<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>审批范围选择框</title>
	<%@include file="/common/commonCSS.jsp" %>
	<%@include file="/common/commonJS.jsp" %>
	<%@include file="/WEB-INF/views/include/taglib.jsp" %>
</head>

<body >
	<div class="main-container" id="main-container">
		<div class="row" style="margin-top: 20px; margin-left: 20px">
			<form class="layui-form" id="search-from" action="${webRoot}/act/model/userAreaSelect">
				<div class="layui-form-item">
					<label class="layui-form-label" style="width:7%;">类型：</label>
					<div class="layui-input-inline">
						<tag:select nameKey="act_user_type" otherAttr="lay-filter='searchFilter'" name="type" initSelectedKey="${type}" />
					</div>
					<label class="layui-form-label" style="width:8%;">搜索框：</label>
					<div class="layui-input-inline layui-input-inline">
						<input type="text" name="name"  value="${userWindow.name }" placeholder="请输入标题"  class="layui-input" >
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
								<th>类型</th>
								<th>名称</th>
								<th>归属</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="dto" items="${page.result }" varStatus="i">
								<tr id="dto_${dto.id }" >
									<td><input type="checkbox"  value="${dto.id}"></td>
									<td>${fns:getCodeName("act_examine_type",type)} </td>
									<td>${dto.name }</td>
									<td >${dto.bapName }</td>
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
        $("#table-list tbody input:checkbox:checked").each(function () {
			var id = $(this).val();
			var flag = true;
            userTab.find("tr").each(function () {
                var trid = $(this).attr("id");
                if(trid ==id){
					flag =false;
                }
            })
			var userType ='${type}';
			if(flag){
                var tds =$(this).parent().siblings();
                html+="<tr id='"+id+"'>";
                html+="  <td>"+tds[0].innerText+"</td>";
                html+="  <td style='display: none'>";
                html+="  	<input name='userTypes' value='"+userType+"'/>";
                html+="  	<input name='userIds' value='"+id+"'/>";
                html+="  </td>";
                html+="  <td>"+tds[1].innerText+"</td>";
                html+='<td><button type="button" onclick="delUser(this)" class="btn btn-xs btn-white btn-danger"><i class="fa fa-trash-o"></i> 删 除 </button></td>';
                html+="</tr>";
                count++;
            }
        });
        userTab.append(html);
        closeThisWindow();
    });

</script>
</html>
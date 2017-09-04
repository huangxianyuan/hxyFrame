<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>转办任务</title>
	<%@include file="/common/commonCSS.jsp" %>
	<%@include file="/common/commonJS.jsp" %>
	<%@include file="/WEB-INF/views/include/taglib.jsp" %>
</head>

<body >
	<div class="main-container" id="main-container" style="margin-left: 3%;margin-top: 2%">
		<div class="row" style="width: 99%">
			<div class="col-xs-12">
				<button type="button" onclick="selectUser();" class="layui-btn" >选择变更人</button>
				<div class="row">
					<div class="col-sm-12">
						<table id="userTab" class="layui-table">
							<thead>
							<tr>
								<th>名称</th>
								<th>机构</th>
								<th>部门</th>
								<th>操作</th>
							</tr>
							</thead>
							<tbody>

							</tbody>
						</table>
					</div>
				</div>
				<div class="row" style="margin-left: 30%;margin-top: 3%">
					<button type="button" id="clickSubmit" onclick="clickSubmit();" class="layui-btn" >转办</button>
					<button class="layui-btn layui-btn-primary" style="margin-left: 5%" type="button" onclick="closeThisWindow()">关 闭</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
    /**
     * 流程相关信息类
     */
    function processInfo(busId,taskId,instanceId,defId,remark) {
        this.busId=busId;//业务id
        this.taskId=taskId;//任务id
        this.instanceId=instanceId;//流程实例
        this.defId=defId;//流程定义id
        this.remark=remark;//审批意见
    }
    var processInfo=new processInfo();
	$(function () {
		processInfo.busId='${flowbus.busId}';
		processInfo.taskId='${taskDto.taskId}';
		processInfo.instanceId='${flowbus.instanceId}';
		processInfo.defId='${flowbus.defid}';
    })

    /**
     * 转办
     */
    function clickSubmit() {
        var url ="${webRoot}/act/deal/turnToDo";
        var toUserId = $("#userTab input[name='userIds']").val();
        if(toUserId == ''){
            alertMsg("至少选择一个下级审批用户");
            return false;
        }
        var params ={
            'busId':processInfo.busId,
            'taskId':processInfo.taskId,
            'instanceId':processInfo.instanceId,
            'defId':processInfo.defId,
            'toUserId':toUserId
        };
        //处理意见备注
        var remark = $(parent.document.getElementById("main-container")).find("#actFieldForm").find("textarea[name='remark']").val();
		params["remark"]=remark;
        $.post(url,params,function (result) {
            if(result.code == '0'){
                alert(result,function () {
                    //父级搜索 刷新待办列表
                    $(parent.parent.document.getElementById("main-container")).find("#search-from").submit();
                });
            }else {
                alertMsg(result.msg);
            }
        });
    }

    //选择变更人弹框
    function selectUser(nodeId , nodeAction) {
        var url="${webRoot}/act/deal/turnUserWindow";
        //弹框层
        layer.open({
            scrollbar: false,
            type: 2,
            title : ["选择变更人" , true],
            area: ['90%', '90%'], //宽高
            content: [url,'no'],
            shadeClose : false,
        });
    }

    /**
     * 删除审批人
     */
    function delUser(_this) {
        $(_this).parent().parent().remove();
    }


</script>
</html>
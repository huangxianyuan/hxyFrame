<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>提交流程</title>
	<%@include file="/common/commonCSS.jsp" %>
	<%@include file="/common/commonJS.jsp" %>
	<%@include file="/WEB-INF/views/include/taglib.jsp" %>
</head>

<body >
	<div class="main-container" id="main-container">
		<div class="row" style="margin-left: 20px;margin-top: 20px">
			<div class="col-xs-12">
				<span style="font-size: 22px"><i class="layui-icon panel-title" style="font-size: 20px; color: #009688;">&#xe630;</i> 流程定义选择</span>
				<div class="row">
					<div class="col-xs-12">
						<table id="deftable" class="layui-table">
							<thead>
							<tr>
								<th>选择</th>
								<th>名称</th>
								<th>描述</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="def" items="${defList}" varStatus="i">
								<tr id="def_${def.id }" >
									<td style="display: none;">
										<input name="deploymentId" value="${def.deploymentId}">
										<input name="defid" value="${def.defid}">
									</td>
									<td><input type="radio" name="def"></td>
									<td>${def.name }</td>
									<td >${def.description }</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div><!-- /.row -->
				<script>
                    function flowInfo(defid,deployId,actKey,busId,modelId,nodelId,nodeType,nodeAction,title) {
                        this.defid =defid;//定义id
                        this.deployId = deployId;//部署id
                        this.actKey = actKey;//流程key
                        this.busId = busId;//业务id
                        this.modelId = modelId;//流程模型id
                        this.nodelId = nodelId;//节点id
                        this.nodeType = nodeType;//节点类型
                        this.nodeAction = nodeAction;//节点行为
                        this.title=title;//选人弹框标题
                    }
                    var flowInfo = new flowInfo()
                    $(function () {
                        flowInfo.actKey='${actKey}';
                        flowInfo.busId='${busId}';
                        $("#deftable tbody tr")[0].click();
                    })
                    $("#deftable tbody tr").off("click").click(function () {
                        flowInfo.defid=$(this).find("input[name='defid']").val();
                        flowInfo.deployId=$(this).find("input[name='deploymentId']").val();
                        getStartFlowInfo(flowInfo.deployId);
                        $("#userTab tbody").html("");
                    });
                    /**
                     * 获取第一个节点信息(除开始节点)
                     * @param deployId
                     */
                    function getStartFlowInfo(deployId) {
                        var url = "${webRoot}/act/deal/getStartFlowInfo";
                        $.post(url,"deployId="+deployId,function (result) {
                            if(result.code == "0"){
                                flowInfo.modelId=result.modelId;
                                flowInfo.nodelId=result.nodeId;
                                flowInfo.nodeType=result.nodeType;
                                flowInfo.nodeAction=result.nodeAction;
                                if(flowInfo.nodeType == "2"){
                                    //审批节点
                                    if(flowInfo.nodeAction == "1"){
                                        flowInfo.title="选择审批人";
                                        $("#selectUser").text(flowInfo.title);
                                    }
                                    //会签节点
                                    if(flowInfo.nodeAction == "2"){
                                        flowInfo.title="选择会签审批人";
                                        $("#selectUser").text(flowInfo.title);
                                    }
                                }
                                //结束节点
                                if(flowInfo.nodeType == "5"){
                                    $("#selectUser").attr("style","display:none");
                                }
                            }else {
                                alertMsg(result.msg);
                            }
                        });
                    }
				</script>
				<div isHide="2">
					<button class="layui-btn layui-btn-small" type="button" onclick="userWindow()"><i class="layui-icon">&#xe612;</i> 选择审批人</button>
				</div>
				<table id="userTab" class="layui-table" isHide="2">
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
				<div class="row" style="margin-left: 40%;padding-bottom: 28px;margin-top: 60px;">
					<button class="layui-btn" type="button" onclick="clickSubmit();">保 存</button>
					<button class="layui-btn layui-btn-primary" type="button" onclick="closeThisWindow()">关 闭</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
    $(document).on("click","tr",function(){
        $("input:radio").prop("checked",false);
        $(this).find(":radio").prop("checked",true);
    });

    /**
     * 选择审批人
     */
    function userWindow() {
        var def=$("#deftable input[name='def']:checked");
        if(def.size()<1){
            alertMsg("请选择流程定义")
			return;
		}
        var url="${webRoot}/act/deal/userWindow?nodeId="+flowInfo.nodelId+"&nodeAction="+flowInfo.nodeAction;
        //弹框层
        layer.open({
            scrollbar: false,
            type: 2,
            title : [flowInfo.title , true],
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
    /**
	 * 提交
     */
    function clickSubmit(){
        confirm("确认提交流程吗?",function () {
            var userIds = $("#userTab tbody input[name='userIds']").val();
            if( (typeof(userIds) == 'undefined' || userIds.length<1) && flowInfo.nodeType!="5"){
                alertMsg("至少选择一个审批用户");
                return false;
            }
            var params="nextUserIds="+userIds+"&defId="+flowInfo.defid+"&actKey="+flowInfo.actKey+"&busId="+flowInfo.busId+"&nodeType="+flowInfo.nodeType;
            var url = "${webRoot}/act/deal/startFlow";
            $.post(url,params,function callback(result){
                if(result.code=='0'){
					alert(result,function () {
                        //刷新父窗口列表
                        parent.location.reload();
                        //关闭弹框
                        closeThisWindow();
                    })
                }else {
					alertMsg(result.msg);
                }
            },"json");
        });
    }


</script>
</html>
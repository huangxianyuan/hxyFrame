<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>审批任务</title>
	<%@include file="/common/commonCSS.jsp" %>
	<%@include file="/common/commonJS.jsp" %>
	<%@include file="/WEB-INF/views/include/taglib.jsp" %>
</head>

<body >
	<div class="main-container" id="main-container" style="margin-left: 3%;margin-top: 2%">
		<div class="row" style="width: 99%">
			<div class="col-xs-12">
				<span>设置下一步审批人</span>
				<div class="row">
					<div class="col-sm-12">
						<table id="nodeTable" class="layui-table" >
							<thead>
							<tr>
								<th>节点名称</th>
								<th>节点类型</th>
								<th>节点行为</th>
								<th>操作</th>
							</tr>
							</thead>
							<tbody>

							</tbody>
						</table>
					</div>
				</div>
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
					<button type="button" id="clickSubmit" onclick="clickSubmit();" class="layui-btn" >提交</button>
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
    function processInfo(busId,taskId,instanceId,changeFields,defId,vars,varValue,nodeType,remark,isSelectUser) {
        this.busId=busId;//业务id
        this.taskId=taskId;//任务id
        this.instanceId=instanceId;//流程实例
        this.changeFields=changeFields;//可更改的字段
        this.defId=defId;//流程定义id
        this.vars=vars;//vars 流程变量数组
        this.varValue=varValue;//vars 流程变量数组
        this.nodeType=nodeType;//节点类型
        this.remark=remark;//审批意见
        this.isSelectUser=isSelectUser;//是否必需选择下级审批用户
    }
    var processInfo=new processInfo();
	$(function () {
		processInfo.busId='${flowbus.busId}';
		processInfo.taskId='${taskDto.taskId}';
		processInfo.instanceId='${flowbus.instanceId}';
		processInfo.changeFiles='${nodeSet.changeFiles}';
		processInfo.defId='${flowbus.defid}';
		processInfo.varName='${taskDto.varName}';
		processInfo.varValue='${taskDto.varValue}';
		processInfo.nodeType='${nodeSet.nodeType}';
		processInfo.nodeType='${nodeSet.nodeType}';
        getNextNodeInfo();
    })

    /**
     * 办理任务
     */
    function clickSubmit() {
        var url ="${webRoot}/act/deal/doActTask";
        var userIds=new Array();
        $("#userTab input[name='userIds']").each(function () {
            userIds.push($(this).val());
        });
        if( (typeof(userIds) == 'undefined' || userIds.length<1) && processInfo.isSelectUser != false){
            alertMsg("至少选择一个下级审批用户");
            return false;
        }
        var params ={
            'busId':processInfo.busId,
            'taskId':processInfo.taskId,
            'instanceId':processInfo.instanceId,
            'defId':processInfo.defId,
            'varValue':processInfo.varValue,
            'varName':processInfo.varName,
            'nodeType':processInfo.nodeType,
            'nextUserIds':userIds.join(",")
        };
		var fileArr=processInfo.changeFiles.split(",");
        for(var i=0;i<fileArr.length;i++){
            var fieldName = fileArr[i];
            if (fieldName == ''){
                continue;
            }
            //父级搜索表单
            var fieldValue=$(parent.document.getElementById("main-container")).find("#"+fieldName+"").val();
            params[fieldName]=fieldValue;
        }
        var remark = $(parent.document.getElementById("main-container")).find("#actFieldForm").find("textarea[name='remark']").val();
		params["remark"]=remark;
        $.post(url,params,function (result) {
            if(result.code == '0'){
                alert(result,function () {
                    //父级搜索 刷新待办列表
                    $(parent.parent.document.getElementById("main-container")).find("#search-from").submit();
                    //closeThisWindow();
                });
            }else {
                alertMsg(result.msg);
            }
        });
    }
    //获取流程下个流向信息
    function getNextNodeInfo() {
        var url ="${webRoot}/act/deal/getNextActNodes";
        var params ={
            'defId':processInfo.defId,
            'taskId':processInfo.taskId,
            'varName':processInfo.varName,
            'varValue':processInfo.varValue
        };
        $.post(url,params,function (result) {
            if(result.code == '0'){
                var nextActNodes=result.nextActNodes;
                var html='';
                for (var i=0;i<nextActNodes.length;i++){
                    var node = nextActNodes[i];
                    if(node.nodeType=='5'){
                        node.nodeName="结束";
                        processInfo.isSelectUser=false;
                        $("#userTab").remove();
					}
                    html+='<tr id="node_'+node.nodeId+'" >'+
							'<input name="nodeId" type="hidden" value="'+node.nodeId+'" ">'+
							'<input name="nodeAction" type="hidden" value="'+node.nodeAction+'" ">'+
							'<td>'+node.nodeName+'</td>'+
							'<td>'+node.nodeTypeName+'</td>'+
							'<td>'+node.nodeActionName+'</td>';
                    	//节点为结束节点,不选择下级处理人
                       if(node.nodeType!='5'){
                           html+='<td>'+
								   '<button type="button"  onclick="selectUser(this);" class="layui-btn" >选择审批人</button>'+
								   '</td>'+
							   '</tr>'
					   }else {
                           html+='<td>'+
                               	'</td>'+
                               '</tr>'
					   }
                }
                $("#nodeTable tbody").html(html);
            }else {
                alertMsg(result.msg);
            }
        });
    }

    //选择下一级审批人弹框
    function selectUser(_this) {
        var nodeId=$(_this).parent().parent().children("input[name='nodeId']:hidden").val();
        var nodeAction=$(_this).parent().parent().children("input[name='nodeAction']:hidden").val();
        var url="${webRoot}/act/deal/userWindow?nodeId="+nodeId+"&nodeAction="+nodeAction;
        //弹框层
        layer.open({
            scrollbar: false,
            type: 2,
            title : ["选择下级审批" , true],
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
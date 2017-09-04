<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglib.jsp" %>
<form action="" id="actFieldForm">
    <%--<div class="row" name="isagreeShow">
        <div class="form-group col-sm-6 col-md-5 ">
            <label class="col-sm-3 control-label no-padding-right">是否同意:</label>
            <div class="col-sm-9">
                <span class="col-xs-11 block input-icon input-icon-right">
                    <tag:select nameKey="YES_NO" name="isagree" clazz="form-control" />
                </span>
            </div>
        </div>
    </div>--%>
    <div class="row">
        <div class="form-group col-sm-8 ">
            <label class="control-label col-sm-2">审批意见:</label>
            <div class="col-sm-10">
                <div class="form-group">
                    <textarea name="remark" class="form-control" rows="3" ></textarea>
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="margin-top: 20px;margin-left: 20%">
        <button class="layui-btn" type="button" onclick="clickSubmit()">办理</button>
        <button class="layui-btn layui-btn-danger" type="button" onclick="backStartUser()">驳回到发起人</button>
        <button class="layui-btn layui-btn-danger" type="button" onclick="backPrevious()">退回上一步</button>
        <button class="layui-btn layui-btn-warm" type="button" onclick="turnToDo()">转办</button>
        <button class="layui-btn layui-btn-danger" type="button" onclick="backPrevious()">跳转</button>
        <button class="layui-btn layui-btn-primary" type="button" onclick="closeThisWindow()">关 闭</button>
    </div>
</form>
<script>
    /**
     * 流程相关信息类
     */
    function processInfo(busId,actKey,taskId,instanceId,changeFields,defId) {
        this.busId=busId;//业务id
        this.actKey=actKey;//流程key也是业务key
        this.taskId=taskId;//任务id
        this.instanceId=instanceId;//流程实例
        this.changeFields=changeFields;//可更改的字段
        this.defId=defId;//流程定义id
    }
    var processInfo=new processInfo();
    $(function () {
        //获取业务可更改的字段，和流程业务基本信息
        processInfo.busId='${taskDto.busId}';
        processInfo.taskId='${taskDto.taskId}';
        processInfo.instanceId='${taskDto.instanceId}';
        processInfo.defId='${taskDto.defId}';
        getChangeFileds();
    });
    

    /**
     * 获取业务可更改的字段，和流程业务基本信息,和流程节点变量
     */
    function getChangeFileds() {
        var url ="${webRoot}/act/deal/getChangeFileds";
        var params ={
            'busId':processInfo.busId,
            'taskId':processInfo.taskId,
            'instanceId':processInfo.instanceId,
            'defId':processInfo.defId
        };
        $.post(url,params,function (result) {
            if(result.code == '0'){
                processInfo.changeFields= result.changeFields;
                processInfo.vars=result.vars;
                for (var i=0;i<processInfo.changeFields.length;i++){
                    var fieldName =processInfo.changeFields[i];
                    if(fieldName != null && fieldName != ''){
                        $("#actBusFields #"+fieldName).attr("readOnly",false);
                    }
                }
            }else {
                alertMsg(result.msg);
            }
        });
    }


    /**
     * 办理
     */
    function clickSubmit() {
        var params ="busId="+processInfo.busId+"&taskId="+processInfo.taskId+"&defId="+processInfo.defId+"&instanceId="+processInfo.instanceId;
        //加入参数可更改的字段数值
        var vars = processInfo.vars;
        var varValue="";
        var varName="";
        if(vars!=null){
            for(var i=0;i<vars.length;i++){
                var val=$("#"+vars[i]+"").val();
                varValue+=val+",";
                varName+=vars[i]+",";
            }
        }
        params+="&varName="+varName+"&varValue="+varValue;
        var url="${webRoot}/act/deal/toDoActTaskView?"+params;
        //弹框层
        layer.open({
            scrollbar: false,
            type: 2,
            title : ["选择下一步审批人" , true],
            area: ['70%', '70%'], //宽高
            content: [url,'no'],
            shadeClose : false,
        });
    }

    /**
     * 转办
     */
    function turnToDo() {
        var params ="busId="+processInfo.busId+"&taskId="+processInfo.taskId+"&defId="+processInfo.defId+"&instanceId="+processInfo.instanceId;
        var url="${webRoot}/act/deal/toTurnToDo?"+params;
        //弹框层
        layer.open({
            scrollbar: false,
            type: 2,
            title : ["转办弹框" , true],
            area: ['70%', '70%'], //宽高
            content: [url,'no'],
            shadeClose : false,
        });
    }

    /**
     * 驳回到任务发起人，重新编辑之后再提交
     */
    function backStartUser() {
        var url ="${webRoot}/act/deal/backStartUser";
        var params ={
            'busId':processInfo.busId,
            'taskId':processInfo.taskId,
            'instanceId':processInfo.instanceId,
            'defId':processInfo.defId,
        };
        var fileArr=processInfo.changeFields;
        for(var i=0;i<fileArr.length;i++){
            var fieldName = fileArr[i];
            if (fieldName == ''){
                continue;
            }
            //父级搜索表单
            var fieldValue=$("#"+fieldName+"").val();
            params[fieldName]=fieldValue;
        }
        var remark = $("#actFieldForm textarea[name='remark']").val();
        params["remark"]=remark;
        $.post(url,params,function (result) {
            if(result.code == '0'){
                alert(result,function () {
                    //父级搜索 刷新待办列表
                    $(parent.document.getElementById("main-container")).find("#searchForm").submit();
                    closeThisWindow();

                });
            }else {
                alertMsg(result.msg);
            }
        });
    }

    /**
     * 退回到上一步
     */
    function backPrevious() {
        alertMsg("该功能开发中,请后续关注");
    }
</script>


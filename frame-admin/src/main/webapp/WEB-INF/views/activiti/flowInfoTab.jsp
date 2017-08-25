<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>办理任务</title>
	    <%@include file="/common/commonCSS.jsp" %>
	    <%@include file="/common/commonJS.jsp" %>
		<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	</head>
	<body>
		<div class="main-container" id="main-container">
			<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
				<ul class="layui-tab-title">
					<li class="layui-this" id="flowInfo">流程信息</li>
					<li onclick="busInfoClick('${taskDto.actKey}','${flag}')">任务审批</li>
				</ul>
				<div class="layui-tab-content" id="tabContent" style="margin-left: 10px">

				</div>
			</div>
		</div>
	</body>

	<script>
        /**
		 * 流程相关信息类
         */
		function processInfo(busId,actKey,taskId,instanceId,defId,flag) {
			this.busId=busId;//业务id
			this.actKey=actKey;//流程key也是业务key
			this.taskId=taskId;//任务id
			this.instanceId=instanceId;//流程实例
			this.defId=defId;//流程定义Id
        }
        var processInfo = new processInfo();
        $(function () {
            var flag = '${flag}';
            if(flag == 1){
                $("#doTask").remove();
            }
            processInfo.busId='${taskDto.busId}';
            processInfo.actKey='${taskDto.actKey}';
            processInfo.taskId='${taskDto.taskId}';
            processInfo.instanceId='${taskDto.instanceId}';
            processInfo.defId='${taskDto.defId}';
            //初始化点击第一个tab
            $("#flowInfo").click();
        });

        /**
		 * 业务信息
         */
        function busInfoClick(actKey,flage) {
			var url = "${webRoot}/act/busInfo/"+actKey;
			var params = {
			    'busId':processInfo.busId,
			    'taskId':processInfo.taskId,
			    'instanceId':processInfo.instanceId,
			    'defId':processInfo.defId,
				'flag':flage
            }
			$.post(url,params,function (r) {
				$("#tabContent").html(r);
            });
        }

  		/**
		 * 流程信息
         */
        $("#flowInfo").on("click",function () {
			var url = "${webRoot}/act/deal/flowInfoHtml";
			var params = {
			    'busId':processInfo.busId,
				'instanceId':processInfo.instanceId
            }
			$.post(url,params,function (r) {
				$("#tabContent").html(r);
            });
        })

	</script>
</html>
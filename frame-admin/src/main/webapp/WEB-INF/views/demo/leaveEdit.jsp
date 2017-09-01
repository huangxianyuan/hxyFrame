<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>请假详情</title>
	    <%@include file="/common/commonCSS.jsp" %>
	    <%@include file="/common/commonJS.jsp" %>
		<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	</head>
	<body >
		<div class="main-container" id="main-container">
			<form class="form-horizontal  bv-form" id="leaveForm">
				<div style="display: none">
					<input id="id" name="id"  value="${leave.id}"/>
				</div>
				<div class="row">
					<div class="form-group col-xs-12 ">
						<label class="col-xs-3 control-label no-padding-right"><i class="red"> * </i>请假标题:</label>
						<div class="col-xs-9">
							<span class="col-xs-11 block input-icon input-icon-right">
								<input id="title" name="title" type="text" class="form-control" value="${leave.title}"/>
							</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-xs-12 ">
						<label class="col-xs-3 control-label no-padding-right"><i class="red"> * </i>请假天数:</label>
						<div class="col-xs-9">
							<span class="col-xs-11 block input-icon input-icon-right">
								<input id="day" name="day" type="text" class="form-control" value="${leave.day}"/>
							</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-xs-12">
						<label class="col-xs-3 control-label no-padding-right">请假原因:</label>
						<div class="col-xs-9">
							<span class="col-xs-11 block input-icon input-icon-right">
								<textarea id="leavewhy" name="leavewhy" class="form-control" rows="3">${leave.leavewhy}</textarea>
							</span>
						</div>
					</div>
				</div>
				<div class="row" style="margin-left: 40%;padding-bottom: 28px;margin-top: 25px;">
					<button class="layui-btn" type="button" id="submitBtn">保 存</button>
					<a class="layui-btn layui-btn-normal" href="${webRoot}/demo/leave/list">返 回</a>
				</div>
			</form>
		</div>
	</body>
	<script>
		//提交
		$("#submitBtn").click(function () {
			var url ="${webRoot}/demo/leave/edit";
			$.post(url,$("#leaveForm").serialize(),function (result) {
				if(result.code == '0'){
					if($("#id").val() == ''){
                        alert(result,function () {
                            location.href='${webRoot}/demo/leave/list';
                        },function () {
                            $("#submitBtn").reset();
                        });
					}else {
                        //更新成功，返回List页面
                        toast(result.msg);
                        location.href='${webRoot}/demo/leave/list';
					}
				}else {
					alertMsg(result.msg);
				}

			});
		});
	</script>
</html>
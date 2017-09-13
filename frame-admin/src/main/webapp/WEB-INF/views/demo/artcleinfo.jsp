<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>文章编辑</title>
	<%@include file="/common/commonCSS.jsp" %>
	<%@include file="/common/commonJS.jsp" %>
	<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
</head>
<body >
<div class="main-container" id="main-container">
	<form class="form-horizontal  bv-form" id="articleForm">
		<div style="display: none">
			<input id="id" name="id"  value="${article.id}"/>
		</div>
		<div class="row">
			<div class="form-group col-xs-12 ">
				<label class="col-xs-3 control-label no-padding-right"><i class="red"> * </i>文章标题:</label>
				<div class="col-xs-9">
					<span class="col-xs-11 block input-icon input-icon-right">
						<input id="title" name="title" type="text" class="form-control" value="${article.title}"/>
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-xs-12 ">
				<label class="col-xs-3 control-label no-padding-right"><i class="red"> * </i>文章内容:</label>
				<div class="col-xs-9">
					<span class="col-xs-11 block input-icon input-icon-right">
						 <div id="editor">
							${article.content}
						</div>
					</span>
				</div>
			</div>
		</div>
		<div class="row" style="margin-left: 40%;padding-bottom: 28px;margin-top: 25px;">
			<button class="layui-btn" type="button" id="submitBtn">保 存</button>
			<a class="layui-btn layui-btn-normal" href="${webRoot}/demo/article/list">返 回</a>
		</div>
	</form>
</div>
</body>
<script src="${webRoot}/statics/js/wangEditor.js"></script>
<script>
    var E = window.wangEditor
    var editor = new E('#editor')
    // 或者 var editor = new E( document.getElementById('#editor') )
    editor.create();
    //提交
    $("#submitBtn").click(function () {
        debugger
        var content = editor.txt.html();
        var formVal = $("#articleForm").serialize();
        var url ="${webRoot}/demo/article/edit";
        $.post(url,formVal+"&content="+content,function (result) {
            if(result.code == '0'){
                if($("#id").val() == ''){
                    alert(result,function () {
                        location.href='${webRoot}/demo/article/list';
                    },function () {
                        $("#submitBtn").reset();
                    });
                }else {
                    //更新成功，返回List页面
                    toast(result.msg);
                    location.href='${webRoot}/demo/article/list';
                }
            }else {
                alertMsg(result.msg);
            }

        });
    });
</script>
</html>
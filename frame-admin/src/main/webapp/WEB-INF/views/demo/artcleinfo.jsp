<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>文章编辑</title>
	<%@include file="/common/commonCSS.jsp" %>
	<%@include file="/common/commonJS.jsp" %>
	<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<script src="${webRoot}/statics/js/ajaxupload.js"></script>
</head>
<body >
<div class="main-container" id="main-container">
	<form class="form-horizontal  bv-form" id="articleForm">
		<div style="display: none">
			<input id="id" name="id"/>
		</div>
		<div class="row">
			<div class="form-group col-xs-12 ">
				<label class="col-xs-3 control-label no-padding-right"><i class="red"> * </i>文章标题:</label>
				<div class="col-xs-9">
					<span class="col-xs-11 block input-icon input-icon-right">
						<input id="title" datatype="*1-512" nullmsg="文章标题不能为空!" name="title" type="text" class="form-control"/>
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-xs-12 ">
				<label class="col-xs-3 control-label no-padding-right"><i class="red"> * </i>文章标题:</label>
				<div class="col-xs-9">
					<span class="col-xs-11 block input-icon input-icon-right">
						<input id="author" datatype="*1-64" nullmsg="作者不能为空!" name="author" type="text" class="form-control"/>
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-xs-12 ">
				<label class="col-xs-3 control-label no-padding-right">封面图片:</label>
				<div class="col-xs-9">
					<span class="col-xs-11 block input-icon input-icon-right">
						<div class="col-xs-9">
							<img src="${img}" style="width: 215px" id="img" name="img"/>
						</div>
						<div class="col-xs-9">
							<button class="layui-btn" id="upload" type="button"><i class="layui-icon">&#xe67c;</i> 上传文件 </button>
						</div>
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-xs-12 ">
				<label class="col-xs-3 control-label no-padding-right">文章类型:</label>
				<div class="col-xs-9">
					<span class="col-xs-11 block input-icon input-icon-right">
						<tag:select nameKey="artcle_type" name="type" clazz="form-control"/>
					</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-xs-12 ">
				<label class="col-xs-3 control-label no-padding-right">文章内容:</label>
				<div class="col-xs-9">
					<span class="col-xs-11 block input-icon input-icon-right">
						 <div id="editor">
							${contentHtml}
						</div>
					</span>
				</div>
			</div>
		</div>
		<div class="row" style="margin-left: 40%;padding-bottom: 28px;margin-top: 25px;">
			<button class="layui-btn" type="button" id="submitBtn">保 存</button>
			<a class="layui-btn layui-btn-normal" href="javascript:history.back(-1);">返 回</a>
		</div>
	</form>
</div>
</body>
<script src="${webRoot}/statics/js/wangEditor.js"></script>
<script>
    $(function(){
        var article = '${article}';
        formload("articleForm",eval('('+article+')'));
        initValidForm($("#busForm"));//清除表单验证样式
    });

    var E = window.wangEditor;
    var editor = new E('#editor')
    // 或者 var editor = new E( document.getElementById('#editor') )
    editor.create();

    //提交
    $("#submitBtn").click(function () {
        alertMsg("由于演示服务器，资源有限，暂未部署全文搜索服务器，望理解！")
        return;
        var contentHtml = editor.txt.html();
        var content = editor.txt.text();
        var formVal = $("#articleForm").serialize();
        var img = $("#img")[0].src;
        var params="contentHtml="+encodeURIComponent(contentHtml)+"&img="+encodeURIComponent(img)+"&content="+encodeURIComponent(content)+"&"+formVal;
        var url ="${webRoot}/demo/article/edit";
        $.post(url,params,function (result) {
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
    new AjaxUpload('#upload', {
        action: '${webRoot}/sys/oss/upload',
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
                alert('只支持jpg、png、gif格式的图片！');
                return false;
            }
        },
        onComplete : function(file, r){
            debugger
            if(r.code == 0){
                $("#img").attr('src',r.url);
            }else{
                alert(r.msg);
            }
        }
    });
</script>
</html>
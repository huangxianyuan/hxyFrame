<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>新建模型</title>
	    <%@include file="/common/commonCSS.jsp" %>
	    <%@include file="/common/commonJS.jsp" %>
		<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	</head>
	<body>
		<div id="main-container">
			<div class="row form-content">
				<div class="col-xs-12">
					<form class="layui-form" id="modelForm" action="">
						<div style="display: none">
							<input id="id" name="id"  value="${model.id}"/>
							<input id="extendActBusinessId"  name="extendActBusinessId"  value="${model.extendActBusinessId}"/>
						</div>
						<div class="layui-form-item col-xs-12" style="margin-top: 20px">
							<label class=" col-xs-3 layui-form-label"><i class="red"> * </i>名称:</label>
							<div class="col-xs-9">
								<input id="name" name="name" datatype="*1-64" nullmsg="模型名称不能为空!" type="text" class="form-control" value="${model.name}"/>
							</div>
						</div>
						<div class="layui-form-item col-xs-12">
							<label class="col-xs-3 layui-form-label"><i class="red"> * </i>业务:</label>
							<div class="col-xs-9">
								<div class="col-xs-9" style="margin-left: -12px">
									<input  id="businessName" datatype="*" nullmsg="业务不能为空!" onclick="selectBus()" placeholder="请选择业务" name="businessName" readonly type="text" class="form-control" value="${model.businessName}"/>
								</div>
								<div class="col-xs-3" style="margin-left: -12px">
									<button type="button" class="layui-btn layui-btn-small" onclick="selectBus()">选择 <i class="layui-icon">&#xe61a;</i></button>
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form-text col-xs-12">
							<label class="layui-form-label col-xs-3">描述:</label>
							<div class="col-xs-9">
								<textarea name="description" placeholder="请输入内容" class="layui-textarea">${model.description}</textarea>
							</div>
						</div>
						<div class="layui-form-item" style="margin-top: 40px">
							<div class="layui-input-block">
								<button class="layui-btn" type="button" id="submitBtn">保 存</button>
								<button class="layui-btn layui-btn-primary" type="button" onclick="closeThisWindow()">关 闭</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
	<%--业务树--%>
	<div style="display: none;padding:10px;" id="busTree">
		<span>业务树:</span>
		<ul name="busTree" class="ztree"></ul>
	</div>
	<script type="text/javascript">
        var setting = {
            view: {dblClickExpand: false}
            ,data: {key: {name:"name"},simpleData: {enable: true,idKey: "id",pIdKey: "parentId"}}
            ,callback: {}
        };

        var ztree;
		$(function(){
            var data='${busTree}';
            ztree=$.fn.zTree.init($("ul[name='busTree']"), setting, eval(''+data+''));//ztree树加载
		});

        //选择业务弹框
        function selectBus() {
            var modelId='${model.id}';
            if(modelId !=''){
                alertMsg("保存后,就不能修改了");
            }else {
                layer.open({
                    type: 1,
                    offset: '0px',
                    skin: 'layui-layer-molv',
                    title: "选择菜单",
                    area: ['250px', '350px'],
                    shade: 0,
                    shadeClose: false,
                    content: $("#busTree"),
                    btn: ['确定', '取消'],
                    btn1: function (index) {
                        var node = ztree.getSelectedNodes();
                        if(node[0].type == '2'){
                            alertMsg("只能选择业务类，不能选择分组!");
                            return;
                        }
                        //选择上级菜单
                        $("#extendActBusinessId").val(node[0].id);
                        $("#businessName").val(node[0].name);
                        layer.close(index);
                    }
                });
            }
        }

        //保存表单
		$("#submitBtn").click(function () {
            checkFormValid($("#modelForm"),function () {
                var url = '${model.id}'==""?'${webRoot}/act/model/save':'${webRoot}/act/model/update';
                //父级搜索表单
                var searchForm=$(parent.document.getElementById("main-container")).find("#search-from");
                $.post(url,$("#modelForm").serialize(),function(r){
                    if(r.code == 0){
                        alert(r,function () {
                            //刷新父窗口列表
                            searchForm.submit();
                            //关闭弹框
                            closeThisWindow();
                        })
                    }else{
                        alert(r.msg);
                    }
                });
            })
        });

	</script>
</html>
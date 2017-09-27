<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>流程设计</title>
    <%@include file="/common/commonCSS.jsp" %>
    <%@include file="/common/commonJS.jsp" %>
    <%@include file="/WEB-INF/views/include/taglib.jsp" %>
</head>
<body>
<div class="main-container" id="main-container">
    <div id="btnDiv" class="row mb-10" style="margin-left: 20px;margin-top: 20px">
        <div class="col-sm-12 btn-group">
            <button class="layui-btn layui-btn-small" type="button" onclick="treeAdd()"><i class="layui-icon">&#xe61f;</i> 新 增</button>
            <button class="layui-btn layui-btn-danger layui-btn-small" type="button" onclick="treeDel()"><i class="layui-icon">&#xe640;</i>删 除</button>
        </div>
    </div>
    <div class="row" style="margin-left: 20px;margin-top: 10px">
        <div class="col-md-3 panel panel-default">
            <div style="font-size: 20px;margin-bottom: 10px">
                <i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe62e;</i> 流程业务树
            </div>
            <div class="col-sm-10">
                <ul id="busTree" class="ztree"></ul>
            </div>
        </div>
        <div class="col-md-5 col-sm-8 panel panel-default" id="treeInfo" style="display: none;margin-left: 100px">
            <div style="font-size: 20px;margin-bottom: 10px">
                <i class="layui-icon" style="font-size: 20px; color: #009688;">&#xe63c;</i> 业务信息
            </div>
            <form class="form-horizontal  bv-form" id="busForm">
                <div style="display: none">
                    <input id="id" name="id"/>
                    <input id="parentId" name="parentId"/>
                </div>
                <div class="row">
                    <div class="form-group col-md-6 ">
                        <label class="col-md-4 control-label no-padding-right"><i class="red"> * </i>父节点名称:</label>
                        <div class="col-md-8">
                            <span class="col-xs-11 block input-icon input-icon-right">
                                <input id="parentName" name="parentName" type="text" class="form-control" readonly/>
                            </span>
                        </div>
                    </div>
                    <div class="form-group col-md-6 ">
                        <label class="col-md-4 control-label no-padding-right"><i class="red"> * </i>名称:</label>
                        <div class="col-md-8">
                            <span class="col-xs-11 block input-icon input-icon-right">
                                <input id="name" name="name" type="text" class="form-control" datatype="*1-64"/>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-6 ">
                        <label class="col-md-4 control-label no-padding-right"><i class="red"> * </i>流程key:</label>
                        <div class="col-md-8">
                            <span class="col-xs-11 block input-icon input-icon-right">
                                <input id="actKey" name="actKey" type="text" class="form-control" datatype="*1-64" />
                            </span>
                        </div>
                    </div>
                    <div class="form-group col-md-6 ">
                        <label class="col-md-4 control-label no-padding-right"><i class="red"> * </i>类型:</label>
                        <div class="col-md-8">
                            <span class="col-xs-11 block input-icon input-icon-right">
                                <tag:select nameKey="act_Bus_type" name="type" clazz="form-control" defaltOptionKey="2" />
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-6 ">
                        <label class="col-md-4 control-label no-padding-right"><i class="red"> * </i>序号:</label>
                        <div class="col-md-8">
                            <span class="col-xs-11 block input-icon input-icon-right">
                                <input id="sort" name="sort" type="text" class="form-control" datatype="n1-4" />
                            </span>
                        </div>
                    </div>
                    <div class="form-group col-md-6 ">
                        <label class="col-md-4 control-label no-padding-right">是否打开:</label>
                        <div class="col-md-8">
                            <span class="col-xs-11 block input-icon input-icon-right">
                                <tag:select nameKey="is_open" name="open" defaltOptionKey="1" clazz="form-control" />
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-12 ">
                        <label class="col-md-2 control-label no-padding-right">类路径:</label>
                        <div class="col-md-10">
                            <span class="col-xs-11 block input-icon input-icon-right">
                                <input id="classurl" name="classurl" type="text" class="form-control"/>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-12 ">
                        <label class="col-md-2 control-label no-padding-right">描述:</label>
                        <div class="col-md-10">
                            <span class="col-xs-11 block input-icon input-icon-right">
                                <textarea class="form-control" rows="3" name="remark"></textarea>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12 col-md-5" style="margin-left: 40%;margin-bottom: 20px;">
                        <button class="layui-btn" type="button" id="submitBtn">保 存</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
<script>
    var setting = {
        view: {dblClickExpand: false}
        ,data: {key: {name:"name"},simpleData: {enable: true,idKey: "id",pIdKey: "parentId"}}
        ,callback: {onClick: zTreeOnClick}
    };
    var ztree;
    $(function(){
        var data='${busTree}';
        ztree=$.fn.zTree.init($("#busTree"), setting, eval(''+data+''));//ztree树加载
    });
    /**
     * 节点单击事件
     */
    function zTreeOnClick(event, treeId, treeNode) {
        var url = "${webRoot}/act/bus/info";
        var params ={
            'id':treeNode.id
        }
        $.post(url,params,function (result) {
            if(result.code == '0'){
                $("#treeInfo").show();
                formload("busForm",result.bus);
                initValidForm($("#busForm"));//清除表单验证样式
            }else {
                alertMsg(result.msg);
            }
        });
    };

    /**
     * 新增
     */
    function treeAdd() {
        var nodes=ztree.getSelectedNodes();
        if(nodes.length<=0){
            alertMsg("请选择父级菜单");
            return
        }
        $("#busForm")[0].reset();
        $("#busForm select[name='type']").val("2");
        $("#treeInfo").show();
        $("#parentName").val(nodes[0].name);
        $("#parentId").val(nodes[0].id);
        initValidForm($("#busForm"));//清除表单验证样式
    }

    /**
     * 保存或者更新
     */
    $("#submitBtn").click(function () {
        checkFormValid($("#busForm"),function () {
            var id = $("#busForm input[name='id']").val();
            var url = "${webRoot}/act/bus/edit";
            $.post(url,$("#busForm").serialize(),function (result) {
                if(result.code =='0'){
                    var nodes = ztree.getSelectedNodes();
                    var bus = result.bus;
                    if(id =='' || id == null){
                       //保存
                       toast(result.msg,function(){
                           ztree.addNodes(nodes[0],{id:bus.id,parentId:bus.parentId,name:bus.name});
                       });
                    }else {
                       //更新
                       toast(result.msg,function(){
                           nodes[0].name=bus.name;
                           ztree.updateNode(nodes[0]);
                       });
                    }

                }else {
                    alertMsg(result.msg);
                }
            });
        });
    });

    /**
     * 删除
     */
    function treeDel() {
        var nodes = ztree.getSelectedNodes();
        if(nodes == null || nodes.length<1){
            alertMsg("请选择要删除的节点");
            return;
        }
        confirm("确定删除吗",function () {
            var nodes = ztree.getSelectedNodes();
            var url = "${webRoot}/act/bus/del";
            $.post(url,"id="+nodes[0].id,function (result) {
                if(result.code =='0'){
                    toast(result.msg,function(){
                        ztree.removeNode(nodes[0]);
                    });
                }else {
                    alertMsg(result.msg);
                }
            });
        });
    }
</script>

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
<div id="main-container">
    <div class="row" style="margin-top: 20px;">
        <form class="layui-form" id="search-from" action="${webRoot}/act/model/list">
            <div class="layui-form-item">
                <label class="layui-form-label" style="width:6%;">模型名称：</label>
                <div class="layui-input-inline">
                    <input type="text" name="name" value="${model.name}"  placeholder="请输入标题"  class="layui-input" >
                </div>
                <button class="layui-btn" id="searchSubmit"><i class="layui-icon">&#xe615;</i>查 询</button>
                <button class="layui-btn layui-btn-warm" type="button" id="refresh">重 置</button>
                <button class="layui-btn" type="button" onclick="modelInfo()"><i class="layui-icon">&#xe61f;</i> 新 增</button>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <table id="table-list" class="layui-table">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>名称</th>
                    <th>业务</th>
                    <th>是否部署</th>
                    <th>机构</th>
                    <th>部门</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.result}" var="model" varStatus="i">
                    <tr name="model_${model.id }">
                        <td>${i.index+1 }</td>
                        <td>${model.name}</td>
                        <td>${model.businessName}</td>
                        <td>${fns:getCodeName("YES_NO",model.status)}</td>
                        <td>${model.bapName}</td>
                        <td>${model.baName}</td>
                        <td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd"/></td>
                        <td>
                            <div class=" btn-group ">
                                <button class="layui-btn layui-btn-small layui-btn-normal" type="button" onclick="modelInfo('${model.id}')"><i class="layui-icon">&#xe642;</i>编辑</button>
                                <button class="layui-btn layui-btn-small" type="button" onclick="designFlow('${model.modelId}')"><i class="layui-icon">&#xe631;</i>设计流程图</button>
                                <button class="layui-btn layui-btn-small layui-btn-normal" type="button" onclick="flowNodeSet('${model.modelId}')"><i class="layui-icon">&#xe614;</i>节点设置</button>
                                <button class="layui-btn layui-btn-small layui-btn-warm" type="button" onclick="showFlowImg('${model.modelId}')"><i class="layui-icon">&#xe60d;</i>查看流程图</button>
                                <c:if test="${model.status == 1}">
                                    <button class="layui-btn layui-btn-small" type="button" onclick="deploy('${model.modelId}','部署')"><i class="layui-icon">&#xe609;</i>部署</button>
                                    <button class="layui-btn layui-btn-small layui-btn-danger" id="" type="button" onclick="delModel('${model.id}')"><i class="layui-icon">&#xe640;</i>删除</button>
                                </c:if>
                                <c:if test="${model.status == 0}">
                                    <button class="layui-btn layui-btn-small" type="button" onclick="deploy('${model.modelId}','升级')"><i class="layui-icon">&#xe62f;</i>升级版本</button>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <sys:page page="${page}"></sys:page>
        </div>
    </div>
</div>
</body>
<script>
    /**
     * 新增/编辑模型
     * @param id 扩展模型id
     */
    function modelInfo(id) {
        var url = "${webRoot}/act/model/info";
        var titile ="新增模型";
        if(id !=""){
            url = "${webRoot}/act/model/info?id="+id;
            titile ="编辑模型";
        }
        //弹框层
        layer.open({
            scrollbar: false,
            type: 2,
            title : [titile , true],
            area: ['30%', '450px'], //宽高
            content: [url,'no'],
            shadeClose : true,
        });
    }
    
    /**
     * 设计流程图
     * @param modelId 模型id
     */
    function designFlow(modelId) {
        //var url="${webRoot}/process/modeler.html?modelId="+modelId;
        var url="${webRoot}/statics/plugins/process/modeler.html?modelId="+modelId;
        //弹框层
        layer.open({
            scrollbar: false,
            type: 2,
            title : ["流程图设计" , true],
            area: ['90%', '90%'], //宽高
            content: [url,'no'],
            shadeClose : false,
        });
    }

    /**
     * 保存模型提示框
     */
    function saveModelAlert(msg) {
        toast(msg);
    }

    /**
     * 流程节点设置
     * @param modelId 模型id
     */
    function flowNodeSet(modelId) {
        var url="${webRoot}/act/model/flowTree?modelId="+modelId;
        //弹框层
        layer.open({
            scrollbar: false,
            type: 2,
            title : ["流程节点设置" , true],
            area: ['80%', '80%'], //宽高
            content: [url,'no'],
            shadeClose : false,
        });
    }

    /**
     * 查看流程图片
     */
    function showFlowImg(modelId) {
        var url="${webRoot}/act/model/showFlowImg?modelId="+modelId+"&r="+new Date().getTime();
        var html ="<img src ='"+url+"'/>";
        //弹框层
        layer.open({
            type: 1,
            area: ['70%', '70%'], //宽高
            content: html,
            title:['查看流程图',true],
            shadeClose: true, //开启遮罩关闭
        });
    }

    /**
     * 删除
     * @param id
     */
    function delModel(id) {
        var url ="${webRoot}/act/model/del";
        confirm("确定删除该流程模型吗?",function () {
            $.post(url,"id="+id,function (result) {
                if(result.code == '0'){
                    toast(result.msg);
                    $("#table-list tr[name=model_"+id+"]").remove();
                }else {
                    alertMsg(result.msg);
                }
            });
        })
    }

    /**
     * 部署和升级版本
     * @param modelId
     */
    function deploy(modelId,title) {
        var url ="${webRoot}/act/model/deploy";
        confirm("确定部署该流程模型吗?",function () {
            $.post(url,"modelId="+modelId,function (result) {
                if(result.code == '0'){
                    toast(title+":"+result.msg);
                    $("#search-from").submit();
                }else {
                    alertMsg(title+":"+result.msg);
                }
            });
        })
    }
</script>

</html>
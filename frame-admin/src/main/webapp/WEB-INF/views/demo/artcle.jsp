<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>文章管理</title>
    <%@include file="/common/commonCSS.jsp" %>
    <%@include file="/common/commonJS.jsp" %>
    <%@include file="/WEB-INF/views/include/taglib.jsp" %>
</head>

<body>
    <div class="main-container" id="main-container">
        <div class="row" style="margin-top: 20px;">
            <div class="col-md-12">
                <form class="layui-form" id="search-from" action="${webRoot}/demo/article/list">
                    <div class="layui-form-item">
                        <label class="layui-form-label" style="width:6%;">标题:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="title" value="${article.title}"  placeholder="搜索标题"  class="layui-input" >
                        </div>
                        <button class="layui-btn" id="searchSubmit"><i class="layui-icon">&#xe615;</i>搜 索</button>
                        <button class="layui-btn layui-btn-warm" type="button" id="refresh">重 置</button>
                        <a class="layui-btn" href="${webRoot}/demo/article/info"><i class="layui-icon">&#xe61f;</i> 新 增</a>
                    </div>
                </form>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <table id="table-list" class="layui-table">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>标题</th>
                            <th>类型</th>
                            <th>新建时间</th>
                            <th>更新时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${page.result}" var="article" varStatus="i">
                            <tr id="article_${article.id }">
                                <td>${i.index+1 }</td>
                                <td>${article.title}</td>
                                <td>${fns:getCodeName("artcle_type",article.type)}</td>
                                <td><fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd hh:mm"/></td>
                                <td><fmt:formatDate value="${article.updateTime}" pattern="yyyy-MM-dd hh:mm"/></td>
                                <td>
                                    <a class="layui-btn layui-btn-small" href="${webRoot}/demo/article/info?id=${article.id}" >
                                        <i class="layui-icon">&#xe642;</i>编辑
                                    </a>
                                    <button class="layui-btn layui-btn-danger layui-btn-small" type="button" onclick="deleteById('${article.id}')">
                                        <i class="layui-icon">&#xe640;</i>删除
                                    </button>
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
<script src="${webRoot}/statics/js/activiti/actSumbit.js"></script>
<script>
    function deleteById(id) {
        var url ="${webRoot}/demo/article/delete";
        confirm('确定要删除选中的记录？', function(){
            $.post(url,"id="+id,function (r) {
                console.log(r);
                if(r.code=='0'){
                    toast(r.msg);
                    $("#article_"+id+"").remove();
                }else {
                    alertMsg(r.msg);
                }
            });
        });
    }
</script>

</html>
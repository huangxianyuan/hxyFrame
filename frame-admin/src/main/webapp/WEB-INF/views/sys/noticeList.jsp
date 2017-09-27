<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>收到通知</title>
    <%@include file="/common/commonCSS.jsp" %>
    <%@include file="/common/commonJS.jsp" %>
    <%@include file="/WEB-INF/views/include/taglib.jsp" %>
</head>

<body>
    <div class="main-container" id="main-container">
        <div class="row" style="margin-top: 20px;">
            <div class="col-md-12">
                <form class="layui-form" id="search-from" action="${webRoot}/sys/notice/myList">
                    <div class="layui-form-item">
                        <label class="layui-form-label" style="width:6%;">通知标题:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="title" value="${notice.title}"  placeholder="请输入标题"  class="layui-input" >
                        </div>
                        <button class="layui-btn" id="searchSubmit"><i class="layui-icon">&#xe615;</i>搜 索</button>
                        <button class="layui-btn layui-btn-warm" type="button" id="refresh">重 置</button>
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
                            <th>通知标题</th>
                            <th>被通知人</th>
                            <th>通知来源</th>
                            <th>是否紧急</th>
                            <th>阅读状态</th>
                            <th>发布时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${page.result}" var="notice" varStatus="i">
                            <tr id="notice_${notice.id }">
                                <td>${i.index+1 }</td>
                                <td>${notice.title}</td>
                                <td>${notice.userName}</td>
                                <td>${fns:getCodeName("notice_soucre",notice.soucre)}</td>
                                <td>${fns:getCodeName("YES_NO",notice.isUrgent)}</td>
                                <td>${fns:getCodeName("YES_NO",notice.showStatus)}</td>
                                <td><fmt:formatDate value="${notice.releaseTimee}" pattern="yyyy-MM-dd hh:mm"/></td>
                                <td>
                                    <div class=" btn-group ">
                                        <button class="layui-btn layui-btn-small" type="button" onclick="showNotice('${notice.id}')"><i class="layui-icon">&#xe604;</i>查阅</button>
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
     * 查阅
     * @param id
     */
    function showNotice(id) {
        var url ="${webRoot}/sys/notice/showNotice";
        $.post(url,"id="+id,function (result) {
            if(result.code == '0'){
                var notice = result.notice;
                var html='<div class="form-body" style="padding: 25px;">';
                    html+=' <h2 style="text-align: center;padding-bottom: 15px;font-size: 31px;">'+notice.title+'</h2>';
                    html+=' <div style="min-height: 73px;">'+notice.context+'</div>';
                    html+=' <p style="text-align: right;margin-top: 50px;">发布时间：'+result.time+'</p>';
                    html+='</div>';
                layer.open({
                    scrollbar: false,
                    type: 1,
                    title : ["查阅通知" , true],
                    area: ['70%', '70%'], //宽高
                    content: html,
                    shadeClose : false,
                });
            }else {
                alertMsg(result.msg);
            }
        });
    }
</script>

</html>
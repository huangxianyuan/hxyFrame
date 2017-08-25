<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="row" style="width: 99%">
    <table id="table-list" class="layui-table">
        <thead>
            <tr>
                <th>序号</th>
                <th>环节名称</th>
                <th>预处理人</th>
                <th>办理人</th>
                <th>处理结果</th>
                <th>审批意见</th>
                <th>更改记录</th>
                <th>办理时间</th>
                <th>创建时间</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="log" items="${taskLogs}" varStatus="i">
            <tr id="log_${log.id }">
                <td>${i.index+1 }</td>
                <td>${log.taskName }</td>
                <td>${log.advanceName }</td>
                <td>${log.dealName }</td>
                <td>${fns:getCodeName("act_task_result",log.appAction)}</td>
                <td>${log.appOpinion }</td>
                <td>${log.columns }</td>
                <td><fmt:formatDate value="${log.dealTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                <td><fmt:formatDate value="${log.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="row">
    <img src ="${webRoot}/act/deal/showFlowImg?processInstanceId=${instanceId}&r=<%=new Date().getTime()%>"/>
</div>
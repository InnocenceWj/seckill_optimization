<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/static/common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀列表页</title>
    <%@include file="/static/common/head.jsp" %>
</head>
<style>
    .container0{
        margin: auto 45px;
    }
</style>
<body>
<div class="container0">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>库存</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>描述</th>
                    <th>详情页</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="good" items="${goodList}">
                    <tr>
                        <td>${good.goods_name}</td>
                        <td>${good.goods_stock}</td>
                        <td>
                            <fmt:formatDate value="${good.start_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${good.end_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>${good.goods_detail}</td>
                        <td>
                            <a class="btn btn-info" href="/good/${good.id}/detail" target="_blank">link</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>


</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>

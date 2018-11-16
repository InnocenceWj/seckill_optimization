<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>商品详情</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <style type="text/css">
        html, body {
            height: 100%;
            width: 100%;
        }

        body {
            background: url('/static/img/bg.jpg') no-repeat;
            background-size: 100% 100%;
        }

        #goodslist td {
            border-top: 1px solid #39503f61;
        }

        #seckillStaus {
            font-size: 26px;
            font-weight: bold;
            margin: 5px auto;
            background-color: #FAF0E6;
            border: none;
            padding: 10px 25px 10px 25px;
            color: #FA8072;
            box-shadow: 1px 1px 5px #B6B6B6;
            border-radius: 3px;
            text-shadow: 1px 1px 1px #FF6347;
            cursor: pointer;
        }
    </style>
</head>
<body>

<div class="panel panel-default" style="height:100%;background-color:rgba(222,222,222,0.8)">
    <div class="panel-heading">秒杀商品详情</div>
    <%--<div class="panel-body">--%>
    <%--<span>没有收货地址的提示。。。</span>--%>
    <%--</div>--%>
    <table class="table" id="goodslist">
        <tr>
            <td>商品名称</td>
            <td colspan="3" id="goodsName">${good.goods_name}</td>
        </tr>
        <tr>
            <td>商品图片</td>
            <td colspan="3"><img src="/static/${good.goods_img}" width="200" height="200"/></td>
        </tr>
        <tr>
            <td>商品原价</td>
            <td colspan="3" id="goodsPrice">${good.goods_price}</td>
        </tr>
        <tr>
            <td>秒杀价</td>
            <td colspan="3" id="miaoshaPrice">${good.seckill_price}</td>
        </tr>
        <tr>
            <td>库存数量</td>
            <td colspan="3" id="stockCount">${good.stock_count}</td>
        </tr>
        <tr id="seckill"></tr>
    </table>
</div>
</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<!-- bootstrap -->
<link rel="stylesheet" type="text/css" href="/static/bootstrap/css/bootstrap.min.css"/>
<script type="text/javascript" src="/static/bootstrap/js/bootstrap.min.js"></script>
<!-- jquery-validator -->
<script type="text/javascript" src="/static/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="/static/jquery-validation/localization/messages_zh.min.js"></script>
<!-- layer -->
<script type="text/javascript" src="/static/layer/layer.js"></script>
<!-- md5.js -->
<script type="text/javascript" src="/static/js/md5.min.js"></script>
<!-- common.js -->
<script type="text/javascript" src="/static/js/common.js"></script>
<%--jquery countDown 倒计时插件--%>
<script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>
<%--犯了一个极其愚蠢的错误，忘了导入seckill.js--%>
<script type="text/javascript" src="/static/js/seckill.js"></script>
<script>
    $(function () {
        seckill.detail.initSeckill({
            goodId: ${good.goods_id},
            //转换ms时间
            startTime: ${good.start_time.time},
            endTime: ${good.end_time.time},
            token: '${token}'
        });
    });
</script>
</html>

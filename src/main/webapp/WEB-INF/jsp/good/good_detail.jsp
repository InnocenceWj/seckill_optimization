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
    <!-- jquery -->
    <script type="text/javascript" src="/static/js/jquery.min.js"></script>
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
    </style>
</head>
<
<body>

<div class="panel panel-default" style="height:100%;background-color:rgba(222,222,222,0.8)">
    <div class="panel-heading">秒杀商品详情</div>
    <div class="panel-body">
        <span id="userTip"> 您还没有登录，请登陆后再操作<br/></span>
        <span>没有收货地址的提示。。。</span>
    </div>
    <table class="table" id="goodslist">
        <tr>
            <td>商品名称</td>
            <td colspan="3" id="goodsName"></td>
        </tr>
        <tr>
            <td>商品图片</td>
            <td colspan="3"><img id="goodsImg" width="200" height="200"/></td>
        </tr>
        <tr>
            <td>秒杀开始时间</td>
            <td id="startTime"></td>
            <td>
                <input type="hidden" id="remainSeconds"/>
                <span id="miaoshaTip"></span>
            </td>
            <td>
                <div class="row">
                    <div class="form-inline">
                        <img id="verifyCodeImg" width="80" height="32" style="display:none"
                             onclick="refreshVerifyCode()"/>
                        <input id="verifyCode" class="form-control" style="display:none"/>
                        <button class="btn btn-primary" type="button" id="buyButton" onclick="getMiaoshaPath()">立即秒杀
                        </button>
                    </div>
                </div>
                <input type="hidden" name="goodsId" id="goodsId"/>
            </td>
        </tr>
        <tr>
            <td>商品原价</td>
            <td colspan="3" id="goodsPrice"></td>
        </tr>
        <tr>
            <td>秒杀价</td>
            <td colspan="3" id="miaoshaPrice"></td>
        </tr>
        <tr>
            <td>库存数量</td>
            <td colspan="3" id="stockCount"></td>
        </tr>
    </table>
</div>
</body>
<script>
    function getMiaoshaPath() {
        var goodsId = $("#goodsId").val();
        g_showLoading();
        $.ajax({
            url: "/seckill/path",
            type: "GET",
            data: {
                goodsId: goodsId,
                verifyCode: $("#verifyCode").val()
            },
            success: function (data) {
                if (data.code == 0) {
                    var path = data.data;
                    doMiaosha(path);
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function () {
                layer.msg("客户端请求有误");
            }
        });
    }

    function getMiaoshaResult(goodsId) {
        g_showLoading();
        $.ajax({
            url: "/seckill/result",
            type: "GET",
            data: {
                goodsId: goodsId,
            },
            success: function (data) {
                if (data.code == 0) {
                    var result = data.data;
                    if (result < 0) {
                        layer.msg("对不起，秒杀失败");
                    } else if (result == 0) {//继续轮询
                        setTimeout(function () {
                            getMiaoshaResult(goodsId);
                        }, 50);
                    } else {
                        layer.confirm("恭喜你，秒杀成功！查看订单？", {btn: ["确定", "取消"]},
                            function () {
                                window.location.href = "/order_detail.htm?orderId=" + result;
                            },
                            function () {
                                layer.closeAll();
                            });
                    }
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function () {
                layer.msg("客户端请求有误");
            }
        });
    }

    function doMiaosha(path) {
        $.ajax({
            url: "/seckill/" + path + "/seckill",
            type: "POST",
            data: {
                goodsId: $("#goodsId").val(),
            },
            success: function (data) {
                if (data.code == 0) {
                    //window.location.href = "/order_detail.htm?orderId=" + data.data.id;
                    getMiaoshaResult($("#goodsId").val());
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function () {
                layer.msg("客户端请求有误");
            }
        });

    }

    function render(detail) {
        var seckillStatus = detail.seckillStatus;
        var remainSeconds = detail.remainSeconds;
        var goods = detail.goods;
        var user = detail.seckillUser;
        if (user) {
            $("#userTip").hide();
        }
        $("#goodsName").text(goods.goodsName);
        $("#goodsImg").attr("src", goods.goodsImg);
        $("#startTime").text(new Date(goods.startTime).format("yyyy-MM-dd hh:mm:ss"));
        $("#remainSeconds").val(remainSeconds);
        $("#goodsId").val(goods.id);
        $("#goodsPrice").text(goods.goodsPrice);
        $("#miaoshaPrice").text(goods.seckillPrice);
        $("#stockCount").text(goods.stockCount);
        countDown();
    }

    $(function () {
        getDetail();
    });

    function getDetail() {
        var goodsId = g_getQueryString("goodsId");
        $.ajax({
            url: "/goods/detail/" + goodsId,
            type: "GET",
            success: function (data) {
                if (data.code == 0) {
                    render(data.data);
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function () {
                layer.msg("客户端请求有误");
            }
        });
    }

    function countDown() {
        var remainSeconds = $("#remainSeconds").val();
        var timeout;
        if (remainSeconds > 0) {//秒杀还没开始，倒计时
            $("#buyButton").attr("disabled", true);
            $("#miaoshaTip").html("秒杀倒计时：" + remainSeconds + "秒");
            timeout = setTimeout(function () {
                $("#countDown").text(remainSeconds - 1);
                $("#remainSeconds").val(remainSeconds - 1);
                countDown();
            }, 1000);
        } else if (remainSeconds == 0) {//秒杀进行中
            $("#buyButton").attr("disabled", false);
            if (timeout) {
                clearTimeout(timeout);
            }
            $("#miaoshaTip").html("秒杀进行中");
            $("#verifyCodeImg").attr("src", "/seckill/verifyCode?goodsId=" + $("#goodsId").val());
            $("#verifyCodeImg").show();
            $("#verifyCode").show();
        } else {//秒杀已经结束
            $("#buyButton").attr("disabled", true);
            $("#miaoshaTip").html("秒杀已经结束");
            $("#verifyCodeImg").hide();
            $("#verifyCode").hide();
        }
    }

    function refreshVerifyCode() {
        $("#verifyCodeImg").attr("src", "/seckill/verifyCode?goodsId=" + $("#goodsId").val() + "&timestamp=" + new Date().getTime());
    }

</script>
</html>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8"/>
    <title>登录</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <!-- jquery -->
    <script type="text/javascript" src="static/js/jquery.min.js"></script>
    <!-- bootstrap -->
    <link rel="stylesheet" type="text/css" href="static/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="static/bootstrap/js/bootstrap.min.js"></script>
    <!-- jquery-validator -->
    <script type="text/javascript" src="static/jquery-validation/jquery.validate.min.js"></script>
    <script type="text/javascript" src="static/jquery-validation/localization/messages_zh.min.js"></script>
    <!-- layer -->
    <script type="text/javascript" src="static/layer/layer.js"></script>
    <!-- md5.js -->
    <script type="text/javascript" src="static/js/md5.min.js"></script>
    <!-- common.js -->
    <script type="text/javascript" src="static/js/common.js"></script>

    <style type="text/css">
        html, body {
            height: 100%;
            width: 100%;
        }

        body {
            background: url('static/img/bg2.jpg') no-repeat;
            background-size: 100% 100%;
        }

        form {
            border-radius: 5px; /*圆角矩形*/
            height: 380px;
            background-color: azure;
            opacity: 0.7;
        }

        .form-group, .row {
            margin: 20px 15px;
        }
    </style>

</head>
<body>
<form name="loginForm" id="loginForm" method="post" style="width:30%; margin:180px auto;">

    <h2 style="text-align: center;padding-bottom: 20px;padding-top: 35px;">用户登录</h2>

    <div class="form-group">
        <div class="row">
            <label class="form-label col-md-4">手机号码</label>
            <div class="col-md-8">
                <input id="mobile" name="mobile" class="form-control" type="text" placeholder="请输入手机号码" required="true"
                       minlength="11" maxlength="11"/>
            </div>
            <div class="col-md-1">
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="row">
            <label class="form-label col-md-4">密码</label>
            <div class="col-md-8">
                <input id="password" name="password" class="form-control" type="password" placeholder="请输入密码"
                       required="true" minlength="6" maxlength="16"/>
            </div>
        </div>
    </div>
    <div class="row" style="margin-top:20px;padding-left: 24px;">
        没有账号？<a href="<%=basePath%>login/to_register">这里注册</a>
    </div>
    <div class="row" style="margin-top:40px;">
        <div class="col-md-6">
            <button class="btn btn-primary btn-block" type="submit" onclick="login()">登录</button>
        </div>
        <div class="col-md-6">
            <button class="btn btn-primary btn-block" type="reset" onclick="reset()">重置</button>
        </div>
    </div>

</form>
</body>
<script>
    function login() {
        $("#loginForm").validate({
            submitHandler: function (form) {
                doLogin();
            }
        });
    }

    function doLogin() {
        g_showLoading();
        var salt = "1a2b3c4d";
        var inputPass = $("#password").val();
        var pwd = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        var password = md5(pwd);
        $.ajax({
            url: "/login/do_login",
            type: "POST",
            data: {
                mobile: $("#mobile").val(),
                password: password
            },
            success: function (data) {
                layer.closeAll();
                if (data.code == 0) {
                    layer.msg("成功");
                    window.location.href = "/good/to_list";
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function (data) {
                alert("错误")
                layer.closeAll();
            }
        });
    }
</script>
</html>
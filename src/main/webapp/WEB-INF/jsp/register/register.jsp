<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <base href="<%=basePath%>">
    <title>注册</title>
    <%--之前一直无法访问static的静态资源,原因是src="static/js/jquery.min.js"
    应该写成src="/static/js/jquery.min.js"
    --%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <!-- jquery -->
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

    <style type="text/css">
        html, body {
            height: 100%;
            width: 100%;
        }

        body {
            background: url('/static/img/bg.jpg') no-repeat;
            background-size: 100% 100%;
        }

        form {
            border-radius: 5px; /*圆角矩形*/
            height: 405px;
            background-color: azure;
            opacity: 0.7;
        }

        .form-group, .row {
            margin: 20px 15px;
        }
    </style>

</head>
<body>
<form name="registerForm" id="registerForm" method="post" style="width:30%; margin:180px auto;">

    <h2 style="text-align: center;padding-bottom: 20px;padding-top: 35px;">用户注册</h2>

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
    <div class="form-group">
        <div class="row">
            <label class="form-label col-md-4">确认密码</label>
            <div class="col-md-8">
                <input id="confirmPwd" name="confirmPwd" class="form-control" type="password" placeholder="请输入确认密码"
                       required="true" minlength="6" maxlength="16"/>
            </div>
        </div>
    </div>

    <div class="row" style="margin-top:40px;">
        <div class="col-md-6">
            <button class="btn btn-primary btn-block" type="submit" onclick="register()">注册</button>
        </div>
        <div class="col-md-6">
            <button class="btn btn-primary btn-block" type="reset" onclick="reset()">重置</button>
        </div>
    </div>

</form>
</body>
<script>
    function register() {
        $("#registerForm").validate({
            rules: {
                "confirmPwd": {
                    required: true,
                    equalTo: "#password"
                }
            },
            messages: {
                "confirmPwd": {
                    required: "确认密码不能为空",
                    equalTo: "确认密码与密码不一致"
                }
            },
            submitHandler: function (form) {
                doRegister();
            }
        });
    }

    function doRegister() {
        g_showLoading();
        var mobile = $("#mobile").val();
        var password = $("#password").val();
        $.ajax({
            url: "<%=basePath%>login/do_register",
            type: 'get',
            data: {
                mobile: mobile,
                password: password
            },
            async: true,
            success: function (data) {
                layer.closeAll();
                if (data.code == 0) {
                    layer.msg("注册成功");
                    window.location.href = "<%=basePath%>login/to_login";
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function (data) {
                alert("错误啦")
                layer.closeAll();
            }
        });
    }
</script>
</html>

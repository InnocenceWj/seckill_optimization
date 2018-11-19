var seckill = {
    parm: {
        token: null
    },
    URL: {
        now: '/seckill/time/now',
        check: '/seckill/checkVerfyCode',
        exposer: function (goodId) {
            return '/seckill/' + goodId + '/exposer';
        },
        killUrl: function (goodId) {
            return '/seckill/' + goodId + '/excution';
        }
    },
    countDown: function (goodId, nowTime, startTime, endTime) {
        if (nowTime > endTime) {
            $("#seckill").html('<td><span id="seckillStaus">秒杀结束</span></td>');
        } else if (nowTime < startTime) {
            //    秒杀未开始
            //    开始时间+1秒，防止用户端的计时时间偏移
            var killTime = new Date(startTime + 1000);
            $("#seckill").countdown(killTime, function (event) {
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                $("#seckill").html('<td><span id="seckillStaus">' + format + '</span></td>');
            }).on('finish.countdown', function () {
                //获取秒杀地址 控制显示逻辑 执行秒杀
                seckill.getSeckillBtn(goodId);
            });
        } else {
            seckill.getSeckillBtn(goodId);
        }
    },
    getSeckillBtn: function (goodId) {
        $("#seckill").html(' <td><img id="verifyCodeImg" width="80" height="32" class="center-block" onclick="seckill.refreshVerifyCode(' + goodId + ')"/></td> ' +
            '<td><div class="row"><input style="float: left" id="verifyCode" onchange="seckill.checkVerfyCode(' + goodId + ')"  class="input-sm"/><div id="code" style="float: left;margin: 5px 2px"></div></div></td>\n' +
            '<td><button class="btn btn-primary" disabled="disabled" type="button" id="buyButton">立即秒杀</button></td>');
        $("#verifyCodeImg").attr("src", "/seckill/verifyCode?goodsId=" + goodId);
    },
    refreshVerifyCode: function (goodId) {
        $("#code").hide();
        $("#verifyCodeImg").attr("src", "/seckill/verifyCode?goodsId=" + goodId + "&timestamp=" + new Date().getTime());
    },
    checkVerfyCode: function (goodId) {
        var verifyCode = $("#verifyCode").val();
        $.get(seckill.URL.check,
            {
                token: seckill.parm.token,
                goodId: goodId,
                verifyCode: verifyCode
            },
            function (result) {
                if (result) {
                    if (result.data) {
                        $("#code").show();
                        $("#code").html('<img src="/static/img/right.png" width="20" height="20" class="center-block" />');
                        $("#buyButton").attr("disabled", false);
                        seckill.handleSeckill(goodId);
                    } else {
                        $("#code").show();
                        $("#code").html('<img src="/static/img/error.png" width="20" height="20" class="center-block" />');
                        $("#buyButton").attr("disabled", true);
                        $("#verifyCode").val("");
                        $("#verifyCode").focus();
                    }
                } else {
                    console.log('result:' + result);
                }
            });

    },
    handleSeckill: function (goodId) {
        $.post(seckill.URL.exposer(goodId), {token: seckill.parm.token}, function (result) {
            if (result.msg == "success") {
                $("#buyButton").one("click", function () {
                    $(this).addClass('disabled');
                    $.post(seckill.URL.killUrl(goodId), {md5: result.data}, function (result) {
                        if (result) {
                            layer.msg(result.msg);
                        }else{
                            layer.msg('秒杀异常');
                        }
                    })
                })
            } else {

            }
        });
    },

    detail: {
        //详情页初始化。
        //这里注意jsp如何给js传递参数
        initSeckill: function (params) {
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var goodId = params['goodId'];
            var token = params['token'];
            if (token == '') {
                alert("你未登录")
            } else {
                seckill.parm.token = token;
                $.get(seckill.URL.now, function (result) {
                    if (result) {
                        var nowTime = result.data.nowTime;
                        //计时判断
                        seckill.countDown(goodId, nowTime, startTime, endTime);
                    } else {
                        console.log('result:' + result);
                    }
                });
            }
        }
    }

};
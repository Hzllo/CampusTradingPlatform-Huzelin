app.controller("payController", function ($scope, $location, cartService, payService) {

    //初始化页面信息
    $scope.initPay = function () {
        //获取用户名
        $scope.getUsername();
        //获取订单号
        $scope.getOutTradeNo();
        //获取支付信息
        $scope.payQR();
    }

    //获取用户名
    $scope.getUsername = function () {
        cartService.getUsername().success(function (result) {
            $scope.username = result.username;
        })
    };

    //获取订单编号
    $scope.getOutTradeNo = function () {
        $scope.outTradeNo = $location.search()["outTradeNo"];
    }

    //获取支付信息
    $scope.payQR = function () {
        payService.payQR($scope.outTradeNo).success(function (result) {
            var data = result.object;
            if ("SUCCESS" == data.result_code) {
                //扫码地址
                $scope.pay_url = data.code_url;
                //支付金额
                $scope.money = (data.money / 100).toFixed(2);
                //生成二维码
                qrCode($scope.pay_url);
                //支付状态查询
                $scope.getPayStatus($scope.outTradeNo);
            } else {
                //跳转到失败页面
                location.href = "payfail.html";
            }
        });
    }

    //支付状态查询
    $scope.getPayStatus = function (outTradeNo) {
        payService.queryPayStatus(outTradeNo).success(function (result) {
            if (result.status) {
                var data = result.object;
                if ("SUCCESS" == data.trade_state) {
                    //支付成功
                    location.href = "paysuccess.html#?money=" + $scope.money;
                }
            } else {
                alert(result.message);
                //刷新二维码支付
                $scope.payQR();
            }
        })
    }

    //支付金额获取
    $scope.getPayFee = function () {
        $scope.money = $location.search()["money"];
    }

});

//二维码生成
var qrCode = function (code_url) {
    var qr = new QRious({
        element: document.getElementById("qrious"),
        size: 250,
        level: "Q",
        value: code_url
    });
}


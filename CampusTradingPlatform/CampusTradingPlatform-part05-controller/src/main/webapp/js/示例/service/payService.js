app.service("payService", function ($http) {

    this.payQR = function (outTradeNo) {
        return $http.get("pay/payQR.do?outTradeNo=" + outTradeNo + "&time=" + Math.random());
    }

    this.queryPayStatus = function (outTradeNo) {
        return $http.get("pay/queryPayStatus.do?outTradeNo=" + outTradeNo + "&time=" + Math.random());
    }

})
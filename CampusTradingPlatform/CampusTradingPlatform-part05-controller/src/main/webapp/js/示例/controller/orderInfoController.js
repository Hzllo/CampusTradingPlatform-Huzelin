app.controller("orderInfoController", function ($scope, addressService, cartService) {
    //获取用户名
    $scope.getUsername = function () {
        cartService.getUsername().success(function (result) {
            $scope.username = result.username;
        })
    };

    //获取当前用户的地址列表
    $scope.getAddressList = function () {
        addressService.getAddressList().success(function (result) {
            $scope.addressList = result;
            for (var i = 0; i < result.length; i++) {
                var address = result[i];
                if ("1" == address.isDefault) {
                    $scope.address = address;
                }
            }
        })
    };

    //绑定所选地址
    $scope.selectAddress = function (address) {
        $scope.address = address;
    }

    //判断当前地址是否是选择的地址
    $scope.isSelectedAddress = function (address) {
        return $scope.address == address;

    };

    //绑定支付方式
    $scope.payType = {"type": "1"};
    $scope.selectPaymentType = function (type) {
        $scope.payType.type = type;
    }

    //获取购物车信息
    $scope.findCartList = function () {
        cartService.findCartList().success(function (result) {
            $scope.cartList = result;
            $scope.totalValue = cartService.sumTotalValue(result);
        })
    };

    //提交订单
    $scope.submitOder = function () {

        $scope.order = {};

        //设定支付方式
        $scope.order.paymentType = $scope.payType.type;
        //设定收货人
        $scope.order.receiver = $scope.address.contact;
        //设定收货人手机号
        $scope.order.receiverMobile = $scope.address.mobile;
        //设定收货人地址
        $scope.order.receiverAreaName = $scope.address.address;
        //设定订单来源
        $scope.order.sourceType = "2";

        cartService.add($scope.order).success(function (result) {
            if (result.status) {
                var data = result.object;
                if (data.payType == '1') {
                    location.href = "http://cart.pinyougou.com/pay.html#?outTradeNo=" + data.outTradeNo;
                }
                if (data.payType == '2') {
                    location.href = "paysuccess.html#?money=" + data.money;
                }
            } else {
                alert("结算异常!");
            }
        });

    };

})
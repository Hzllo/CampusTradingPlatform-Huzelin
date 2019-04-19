app.controller("cartController", function ($scope, cartService) {

    //获取用户名
    $scope.getUsername = function () {
        cartService.getUsername().success(function (result) {
            $scope.username = result.username;
        })
    };

    //添加商品到购物车
    $scope.addItemToCartList = function (itemId, num) {
        cartService.addItemToCartList(itemId, num).success(function (result) {
            if (!result.status) {
                alert(result.message);
            }
            $scope.findCartList();
        })
    };

    //获取购物车信息
    $scope.findCartList = function () {
        cartService.findCartList().success(function (result) {
            $scope.cartList = result;
            $scope.totalValue = cartService.sumTotalValue(result);
        })
    };

});
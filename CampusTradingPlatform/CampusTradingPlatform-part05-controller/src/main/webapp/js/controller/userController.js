app.controller("userController", function ($scope, userService, itemService) {

    $scope.user = null;
    //注册
    $scope.register = function () {
        userService.reg($scope.user).success(function (result) {
            $scope.user = null;
            location.href = "index.html";
        })
    }

    //获取所有商品
    $scope.finAllItem = function () {
        itemService.getItems().success(function (result) {
            $scope.items = result;
        })
    }
})


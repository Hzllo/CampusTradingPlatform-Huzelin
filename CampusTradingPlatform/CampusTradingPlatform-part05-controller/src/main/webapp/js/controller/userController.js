app.controller("userController", function ($scope, userService, itemService) {

    $scope.user = null;
    //注册
    $scope.register = function () {
        userService.reg($scope.user).success(function (result) {
            var btnArray = ['确定'];
            if (result.status) {
                mui.confirm('注册成功', '恭喜你', btnArray, function (e) {
                    if (e.index == 0) {
                        $scope.user = null;
                        location.href = "index.html";
                    }
                })
            } else {
                mui.confirm(result.message, '错误', btnArray, function (e) {
                    if (e.index == 0) {
                    }
                })
            }
        })
    }

    //获取所有商品
    $scope.finAllItem = function () {
        itemService.getItems().success(function (result) {
            $scope.items = result;
        })
    }
})


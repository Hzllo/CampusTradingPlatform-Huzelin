app.controller("userController", function ($scope, userService, itemService) {

    //获取用户名
    $scope.getUsername = function () {
        userService.getUsername().success(function (result) {
            $scope.username = result.username;
        })
    };

    //获取用户名
    $scope.getUser = function () {
        userService.getUser().success(function (result) {
            $scope.user = result;
        })
    };

    //更新用户
    $scope.update = function () {
        userService.updateUser($scope.user).success(function (result) {
            var btnArray = ['确定'];
            if (result.status) {
                mui.confirm(result.message, '恭喜你', btnArray, function (e) {
                    if (e.index == 0) {
                        $scope.user = null;
                        location.href = "logout";
                    }
                })
            } else {
                mui.confirm(result.message, '错误', btnArray, function (e) {
                    if (e.index == 0) {
                    }
                })
            }
        })
    };

    $scope.user = null;
    //注册
    $scope.register = function () {
        userService.reg($scope.user).success(function (result) {
            var btnArray = ['确定'];
            if (result.status) {
                mui.confirm('注册成功', '恭喜你', btnArray, function (e) {
                    if (e.index == 0) {
                        location.href = "/reg.html";
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
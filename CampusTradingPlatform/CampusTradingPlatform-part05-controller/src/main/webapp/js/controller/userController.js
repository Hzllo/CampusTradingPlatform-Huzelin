app.controller("userController", function ($scope, userService, itemService) {

    //获取用户名
    $scope.getName = function () {
        userService.getName().success(function (result) {
            $scope.username = result.username;
        })
    };

    //获取用户
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
                        location.href = "login.html";
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

    //进入搜索页
    $scope.search = function () {
        if ($scope.searchContent == null) {
            $scope.searchContent = "";
        }
        window.location.href = "search.html#?searchContent = " + $scope.searchContent;
    }

    //获取所有商品
    $scope.anyItems = function (type) {
        itemService.anyItems(type).success(function (result) {
            $scope.items = result.object;
        })
    };

})
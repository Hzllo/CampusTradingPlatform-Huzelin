app.controller("userController", function ($scope, userService, itemService) {

    //获取用户名
    $scope.getName = function () {
        userService.getName().success(function (result) {
            $scope.username = result.username;
        })
    };

    //上传商品图片
    $scope.uploadFile = function () {
        //创建html5的表单数据对象
        var formData = new FormData();
        var fileObj = document.getElementById("up_img_WU_FILE_0").files[0];
        var btnArray = ['确定'];
        //设置表单项
        //formData.append("up_img_WU_FILE_0", up_img_WU_FILE_0.files[0]);
        //fileObj.size / 1024 > 1025
        if (fileObj.size / 1024 > 1025) { //大于1M，进行提示
            mui.confirm('图片大于1M!', '提醒', btnArray, function (e) {
                if (e.index == 0) {
                }
            })
        } else {
            formData.append("up_img_WU_FILE_0", fileObj); // 文件对象
            itemService.uploadFile(formData).success(function (result) {
                if (result.status) {
                    $scope.user.image = result.object;
                } else {
                    mui.confirm(result.message, '上传失败!', btnArray, function (e) {
                        if (e.index == 0) {
                        }
                    })
                }
            }).error(function () {
                alert("上传图片失败");
            });
        }
    }

    //未读评论
    $scope.findCount = function () {
        itemService.informationCount().success(function (result) {
            $scope.count = result.object;
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
        var btnArray = ['确定'];
        if ($scope.user.password != $scope.duPassword) {
            mui.confirm('两次密码不一致', '错误', btnArray, function (e) {
                if (e.index == 0) {
                    $scope.user.password = "";
                    $scope.duPassword = "";
                }
            })
        } else {
            userService.reg($scope.user).success(function (result) {
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
    }

    $scope.enterEvent = function (e) {
        var keycode = window.event ? e.keyCode : e.which;
        if (keycode == 13) {
            $scope.search();
        }
    }

    //进入搜索页
    $scope.search = function () {
        if ($scope.searchContent == null) {
            $scope.searchContent = "";
        }
        window.location.href = "search.html#?searchContent=" + $scope.searchContent;
    }

    //获取所有商品
    $scope.anyItems = function (type) {
        itemService.anyItems(type).success(function (result) {
            $scope.items = result.object;
        })
    };

})
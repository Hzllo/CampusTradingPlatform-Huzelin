app.controller("itemController", function ($scope, $location, itemService, commentService) {

    //上传商品图片
    $scope.uploadFile = function () {
        itemService.uploadFile().success(function (result) {
            var btnArray = ['确定'];
            if (result.status) {
                $scope.imageUrl = result.object;
                mui.confirm(result.message, '上传成功!', btnArray, function (e) {
                    if (e.index == 0) {
                    }
                })
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

    //评论商品
    $scope.commentItem = function (type) {
        $scope.comment = new Object();
        var str1 = "";
        var str2 = "";
        var str3 = "";
        //私信
        if (type == 1) {
            str1 = "请留下联系方式：";
            str2 = "QQ号或者微信";
            str3 = "给他私信";
        }
        //评论
        if (type == 2) {
            str1 = "我要吐槽：";
        }
        var btnArray = ['确定', '取消'];
        mui.prompt(str1, str2, str3, btnArray, function (e) {
            if (e.index == 0) {
                $scope.comment.content = e.value;
                $scope.comment.type = type;
                $scope.comment.itemId = $scope.object.itemId;
                commentService.addComment($scope.comment).success(function (result) {
                    if (result.status) {
                        $scope.object.comments = result.object;
                        mui.confirm(result.message, '注意!', btnArray, function (e) {
                            if (e.index == 0) {
                                $scope.object.comments = result.object;
                            }
                        })
                    } else {
                        mui.confirm(result.message, '失败!', btnArray, function (e) {
                            if (e.index == 0) {
                            }
                        })
                    }
                }).error(function () {
                    mui.confirm('请先登录!', '注意!', btnArray, function (e) {
                        if (e.index == 0) {
                            window.location.href = "login.html";
                        } else {
                        }
                    })
                })

            } else {
            }
        })

    }

    //下架商品
    $scope.deleteItem = function (id) {
        var btnArray = ['是', '否'];
        mui.confirm('确定下架该商品?', '警告', btnArray, function (e) {
            if (e.index == 0) {
                itemService.deleteItem(id).success(function (result) {
                    window.location.reload(true);
                })
            } else {
            }
        })
    }

    $scope.item = new Object();
    //上架商品
    $scope.addItem = function () {
        $scope.item.imageUrl = $scope.imageUrl;
        itemService.add($scope.item).success(function (result) {
            var btnArray = ['确定'];
            if (result.status) {
                mui.confirm(result.message, '恭喜你', btnArray, function (e) {
                    if (e.index == 0) {
                        location.href = "account.html";
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

    //商品详情
    $scope.itemDetail = function () {
        var itemId = $location.search()["itemId"];
        itemService.itemDetail(itemId).success(function (result) {
            if (result.status) {
                $scope.object = result.object;
            }
        })
    }

    //获取所有商品
    $scope.finAllItem = function () {
        itemService.getItems().success(function (result) {
            if (result.status) {
                $scope.items = result.object;
            }
        })
    }

    //搜索商品
    $scope.searchItem = function () {
        var searchContent = $location.search()["searchContent"];
        itemService.search(searchContent).success(function (result) {
            if (result.status) {
                $scope.items = result.object;
            }
        })
    }

})
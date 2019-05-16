app.controller("commentController", function ($scope, commentService) {

    //未读消息
    $scope.informationNoLook = function () {
        commentService.informationNoLook().success(function (result) {
            if (result.status) {
                $scope.informationnolook = result.object;
            }
        })
    }


    //已读消息
    $scope.informationLook = function () {
        commentService.informationLook().success(function (result) {
            if (result.status) {
                $scope.informationlook = result.object;
            }
        })
    }

    //查看详情
    $scope.lookDetail = function (itemId) {
        if (itemId != null && itemId != "") {
            window.location.href = "itemdetail.html#?itemId=" + itemId;
        }
    }

    //设为已读
    $scope.setLook = function (commentId) {
        if (commentId != null && commentId != "") {
            commentService.setLook(commentId).success(function (result) {
                if (result.status) {
                    $scope.informationLook();
                    $scope.informationNoLook();
                }
            })
        }
    }

    //设为已读
    $scope.setAllLook = function () {
        commentService.setAllLook().success(function (result) {
            if (result.status) {
                $scope.informationLook();
                $scope.informationNoLook();
            }
        })
    }

    //回复信息
    $scope.relInformation = function (userId, itemId, content) {
        $scope.comment = new Object();
        //回复
        var str1 = "输入内容：";
        var str2 = content;
        var str3 = "回复";
        var btnArray = ['确定', '取消'];
        mui.prompt(str1, str2, str3, btnArray, function (e) {
            if (e.index == 0) {
                $scope.comment.content = e.value;
                $scope.comment.type = 1;
                $scope.comment.userId = userId;
                $scope.comment.itemId = itemId;
                commentService.addComment($scope.comment).success(function (result) {
                    if (result.status) {
                        mui.confirm(result.message, '注意!', btnArray, function (e) {
                            if (e.index == 0) {
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

    //删除消息
    $scope.deleteInformation = function (commentId) {
        if (commentId != null && commentId != "") {
            var btnArray = ['确定', '取消'];
            mui.confirm('你确定要删除这条消息吗?它也会从商品评论里删除!!', '提醒', btnArray, function (e) {
                if (e.index == 0) {

                    commentService.deleteInformation(commentId).success(function (result) {
                        if (result.status) {
                            $scope.informationLook();
                            $scope.informationNoLook();
                        }
                    })
                }
                if (e.index == 1) {
                }
            })
        }
    }

});
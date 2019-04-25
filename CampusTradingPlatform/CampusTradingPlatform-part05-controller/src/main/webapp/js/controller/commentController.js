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
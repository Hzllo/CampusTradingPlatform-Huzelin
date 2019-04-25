app.controller("commentController", function ($scope, commentService) {

    $scope.informationNoLook = function () {
        commentService.informationNoLook().success(function (result) {
            if (result.status) {
                $scope.informationnolook = result.object;
            }
        })
    }

});
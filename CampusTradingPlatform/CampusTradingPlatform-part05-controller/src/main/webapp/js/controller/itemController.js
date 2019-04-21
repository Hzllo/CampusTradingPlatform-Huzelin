app.controller("itemController", function ($scope, itemService) {

    $scope.item = [];
    $scope.item.imageUrl = "";

    //上传商品图片
    $scope.uploadFile = function () {
        itemService.uploadFile().success(function (response) {
            var btnArray = ['确定'];
            if (response.status) {
                $scope.item.imageUrl = response.object;
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

    //下架商品
    $scope.deleteItem = function () {
        var btnArray = ['是', '否'];
        mui.confirm('确定下架该商品?', '警告', btnArray, function (e) {
            if (e.index == 0) {
                itemService.deleteItem().success(function (result) {
                })
            } else {
            }
        })
    }

    //上架商品
    $scope.finAllItem = function () {
        itemService.getItems().success(function (result) {
            $scope.items = result;
        })
    }

    //获取所有商品
    $scope.finAllItem = function () {
        itemService.getItems().success(function (result) {
            $scope.items = result;
        })
    }

})
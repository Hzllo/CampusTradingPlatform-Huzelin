app.controller("itemController", function ($scope, itemService) {

    //下架商品
    $scope.deleteItem = function () {

        document.getElementById("delete").addEventListener('tap', function () {
            var btnArray = ['是', '否'];
            mui.confirm('确定下架该商品?', '警告', btnArray, function (e) {
                if (e.index == 0) {
                    itemService.deleteItem().success(function (result) {
                    })
                } else {
                }
            })
        });
    }


    //获取所有商品
    $scope.finAllItem = function () {
        itemService.getItems().success(function (result) {
            $scope.items = result;
        })
    }

})
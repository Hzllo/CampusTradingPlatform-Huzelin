app.service("itemService", function ($http) {

    this.uploadFile = function () {
        //创建html5的表单数据对象
        var formData = new FormData();
        //设置表单项
        formData.append("up_img_WU_FILE_0", up_img_WU_FILE_0.files[0]);
        return $http({
            url: "/upload.do",
            method: "post",
            data: formData,
            headers: {"Content-Type": undefined},
            transformRequest: angular.identity
        });
    };

    this.getItems = function () {
        return $http.get("item/findAll.do?time=" + Math.random());
    }

    this.deleteItem = function (itemId) {
        return $http.get("item/delete.do?itemId=" + itemId);
    }

})
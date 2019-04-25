app.service("itemService", function ($http) {

    //图片上传
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

    //获取未读评论
    this.informationCount = function () {
        return $http.get("comment/count.do?time=" + Math.random());
    }

    //获取多个商品
    this.getItems = function () {
        return $http.get("item/findAll.do?time=" + Math.random());
    }

    //首页商品
    this.anyItems = function (type) {
        return $http.get("item/anyItems.do?type=" + type);
    }

    //单个商品详情
    this.itemDetail = function (id) {
        return $http.get("item/getItem.do?itemId=" + id);
    }

    //上传商品
    this.add = function (entity) {
        return $http.post("item/addItem.do", entity);
    }

    //删除商品
    this.deleteItem = function (itemId) {
        return $http.get("item/delete.do?itemId=" + itemId);
    }
    //搜索商品
    this.search = function (content) {
        return $http.get("item/search.do?content=" + content);
    }

})
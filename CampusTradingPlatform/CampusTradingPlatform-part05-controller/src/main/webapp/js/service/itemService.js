app.service("itemService", function ($http) {

    //图片上传
    this.uploadFile = function (entity) {
        return $http({
            url: "/upload.do",
            method: "post",
            data: entity,
            headers: {"Content-Type": undefined},
            transformRequest: angular.identity
        });
    };

    //预订商品markItem
    this.markItem = function (e) {
        return $http.get("item/markItem.do?itemId=" + e);
    }

    //获取未读评论
    this.informationCount = function () {
        return $http.get("comment/count.do?time=" + Math.random());
    }

    //获取我想要的商品
    this.youThinkItem = function () {
        return $http.get("item/myThink.do?time=" + Math.random());
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

    //更新
    this.update = function (entity) {
        return $http.post("item/updateItem.do", entity);
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
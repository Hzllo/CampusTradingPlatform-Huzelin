app.service("commentService", function ($http) {

    //获取多个商品
    this.addComment = function (entity) {
        return $http.post("comment/add.do?time=" + Math.random(), entity);
    }

})
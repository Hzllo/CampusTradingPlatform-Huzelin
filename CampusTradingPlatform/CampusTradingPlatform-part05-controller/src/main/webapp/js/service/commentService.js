app.service("commentService", function ($http) {

    //获取多个商品
    this.addComment = function (entity) {
        return $http.post("comment/add.do?time=" + Math.random(), entity);
    }

    this.informationNoLook = function () {
        return $http.get("comment/countnolook.do?time=" + Math.random());
    }

    this.informationLook = function () {
        return $http.get("comment/countlook.do?time=" + Math.random());
    }

    this.deleteInformation = function (commentId) {
        return $http.get("comment/delete.do?commentId=" + commentId);
    }

    this.setLook = function (commentId) {
        return $http.get("comment/setLook.do?commentId=" + commentId);
    }

    this.setAllLook = function () {
        return $http.get("comment/setAllLook.do?time=" + Math.random());
    }


})
app.service("commentService", function ($http) {

    //获取当前用户的地址列表
    this.getAddressList = function () {
        return $http.get("/address/getAddressList.do?time=" + Math.random());
    }

})
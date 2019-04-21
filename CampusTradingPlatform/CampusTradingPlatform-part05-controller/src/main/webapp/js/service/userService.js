app.service("userService", function ($http) {

    //获取用户名
    this.getName = function () {
        return $http.get("user/getUsername.do?time=" + Math.random());
    }

    //获取用户
    this.getUser = function () {
        return $http.get("user/getUser.do?time=" + Math.random());
    }

    //获取用户
    this.updateUser = function (entity) {
        return $http.post("user/update.do?time=" + Math.random(), entity);
    }

    //注册
    this.reg = function (entity) {
        return $http.post("user/reg.do?" + "&time=" + Math.random(), entity);
    }

})
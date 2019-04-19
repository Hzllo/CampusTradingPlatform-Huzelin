app.service("userService", function ($http) {

    this.reg = function (entity) {
        return $http.post("user/reg.do?" + "&time=" + Math.random(), entity);
    }

})
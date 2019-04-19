app.service("itemService", function ($http) {

    this.getItems = function () {
        return $http.get("item/findAll.do?time=" + Math.random());
    }

})
app.controller("itemController", function ($scope, $location, itemService, commentService) {

    //上传商品图片
    $scope.uploadFile = function () {
        //创建html5的表单数据对象
        var formData = new FormData();
        var fileObj = document.getElementById("up_img_WU_FILE_0").files[0];
        var btnArray = ['确定'];
        //设置表单项
        //formData.append("up_img_WU_FILE_0", up_img_WU_FILE_0.files[0]);
        //fileObj.size / 1024 > 1025
        if (fileObj.size / 1024 > 1025) { //大于1M，进行提示
            mui.confirm('图片大于1M!', '提醒', btnArray, function (e) {
                if (e.index == 0) {
                }
            })
        } else {
            formData.append("up_img_WU_FILE_0", fileObj); // 文件对象
            itemService.uploadFile(formData).success(function (result) {
                if (result.status) {
                    $scope.imageUrl = result.object;
                    mui.confirm(result.message, '上传成功!', btnArray, function (e) {
                        if (e.index == 0) {
                        }
                    })
                    $scope.item.imageUrl = result.object;
                } else {
                    mui.confirm(result.message, '上传失败!', btnArray, function (e) {
                        if (e.index == 0) {
                        }
                    })
                }
            }).error(function () {
                alert("上传图片失败");
            });
        }
    }

    //评论商品
    $scope.commentItem = function (type) {
        $scope.comment = new Object();
        var str1 = "";
        var str2 = "";
        var str3 = "";
        //私信
        if (type == 1) {
            str1 = "请留下联系方式：";
            str2 = "QQ号或者微信";
            str3 = "给他私信";
        }
        //评论
        if (type == 2) {
            str1 = "我要吐槽：";
        }
        var btnArray = ['确定', '取消'];
        mui.prompt(str1, str2, str3, btnArray, function (e) {
            if (e.index == 0) {
                $scope.comment.content = e.value;
                $scope.comment.type = type;
                $scope.comment.itemId = $scope.object.itemId;
                commentService.addComment($scope.comment).success(function (result) {
                    if (result.status) {
                        $scope.object.comments = result.object;
                        mui.confirm(result.message, '注意!', btnArray, function (e) {
                            if (e.index == 0) {
                                $scope.object.comments = result.object;
                            }
                        })
                    } else {
                        mui.confirm(result.message, '失败!', btnArray, function (e) {
                            if (e.index == 0) {
                            }
                        })
                    }
                }).error(function () {
                    mui.confirm('请先登录!', '注意!', btnArray, function (e) {
                        if (e.index == 0) {
                            window.location.href = "login.html";
                        } else {
                        }
                    })
                })

            } else {
            }
        })

    }

    //下架商品
    $scope.deleteItem = function (id) {
        var btnArray = ['是', '否'];
        mui.confirm('确定下架该商品?', '警告', btnArray, function (e) {
            if (e.index == 0) {
                itemService.deleteItem(id).success(function (result) {
                    if (result.status) {
                        window.location.reload(true);
                    } else {
                        mui.confirm(result.message, '错误', btnArray, function (e) {
                            if (e.index == 0) {
                            }
                        })
                    }
                })
            } else {
            }
        })
    }

    $scope.item = new Object();
    //上架商品
    $scope.addItem = function () {
        $scope.item.imageUrl = $scope.imageUrl;
        itemService.add($scope.item).success(function (result) {
            var btnArray = ['确定'];
            if (result.status) {
                mui.confirm(result.message, '恭喜你', btnArray, function (e) {
                    if (e.index == 0) {
                        location.href = "account.html";
                    }
                })
            } else {
                mui.confirm(result.message, '错误', btnArray, function (e) {
                    if (e.index == 0) {
                    }
                })
            }
        })
    }

    //更新商品
    $scope.updateItem = function () {
        if ($scope.imageUrl != null) {
            $scope.item.imageUrl = $scope.imageUrl;
        }
        itemService.update($scope.item).success(function (result) {
            var btnArray = ['确定'];
            if (result.status) {
                mui.confirm(result.message, '恭喜你', btnArray, function (e) {
                    if (e.index == 0) {
                        location.href = "myItem.html";
                    }
                })
            } else {
                mui.confirm(result.message, '错误', btnArray, function (e) {
                    if (e.index == 0) {
                    }
                })
            }
        })
    }

    //商品详情
    $scope.itemDetail = function () {
        var itemId = $location.search()["itemId"];
        itemService.itemDetail(itemId).success(function (result) {
            if (result.status) {
                $scope.object = result.object;
            }
        })
    }

    //商品详情-修改商品
    $scope.itemDetailUpdate = function () {
        var itemId = $location.search()["itemId"];
        itemService.itemDetail(itemId).success(function (result) {
            if (result.status) {
                $scope.item = result.object;
            }
        })
    }

    //获取所有商品
    $scope.finAllItem = function () {
        itemService.getItems().success(function (result) {
            if (result.status) {
                $scope.items = result.object;
            }
        })
    }

    //更新商品跳转链接
    $scope.updateItemLink = function (itemId) {
        window.location.href = "updateItem.html#?itemId=" + itemId;
    }

    //获取所有商品
    $scope.myThinkItem = function () {
        itemService.youThinkItem().success(function (result) {
            if (result.status) {
                $scope.items = result.object;
            }
        })
    }

    //预订商品markItem
    $scope.markItem = function () {
        var itemId = $location.search()["itemId"];
        itemService.markItem(itemId).success(function (result) {
            var btnArray = ['确定'];
            if (result.status) {
                mui.confirm(result.message, '恭喜你', btnArray, function (e) {
                    if (e.index == 0) {
                        window.location.reload(true);
                    }
                })
            } else {
                mui.confirm(result.message, '错误', btnArray, function (e) {
                    if (e.index == 0) {
                    }
                })
            }
        })
    }

    //搜索商品
    $scope.searchItem = function () {
        var searchContent = $location.search()["searchContent"];
        itemService.search(searchContent).success(function (result) {
            if (result.status) {
                $scope.items = result.object;
            }
        })
    }

})
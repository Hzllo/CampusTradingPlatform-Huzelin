app.controller("itemController", function ($scope, $location, itemService, commentService) {

    $scope.canvasDataURL = function (path, obj, callback) {
        var img = new Image();
        img.src = path;
        img.onload = function () {
            var that = this;
            // 默认按比例压缩
            var w = that.width,
                h = that.height,
                scale = w / h;
            w = obj.width || w;
            h = obj.height || (w / scale);
            var quality = 0.7;  // 默认图片质量为0.7
            //生成canvas
            var canvas = document.createElement('canvas');
            var ctx = canvas.getContext('2d');
            // 创建属性节点
            var anw = document.createAttribute("width");
            anw.nodeValue = w;
            var anh = document.createAttribute("height");
            anh.nodeValue = h;
            canvas.setAttributeNode(anw);
            canvas.setAttributeNode(anh);
            ctx.drawImage(that, 0, 0, w, h);
            // 图像质量
            if (obj.quality && obj.quality <= 1 && obj.quality > 0) {
                quality = obj.quality;
            }
            // quality值越小，所绘制出的图像越模糊
            var base64 = canvas.toDataURL('image/jpeg', quality);
            // 回调函数返回base64的值
            callback(base64);
        }
    }
    $scope.photoCompress = function (file, w, objDiv) {
        var ready = new FileReader();
        /*开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.*/
        ready.readAsDataURL(file);
        ready.onload = function () {
            var re = this.result;
            $scope.canvasDataURL(re, w, objDiv)
        }
    }
    $scope.convertBase64UrlToBlob = function (urlData) {
        var arr = urlData.split(','), mime = arr[0].match(/:(.*?);/)[1],
            bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new Blob([u8arr], {type: mime});
    }

    //上传商品图片
    $scope.uploadFile = function () {
        //创建html5的表单数据对象
        var formData = new FormData();
        var fileObj = document.getElementById("up_img_WU_FILE_0").files[0];
        //设置表单项
        //formData.append("up_img_WU_FILE_0", up_img_WU_FILE_0.files[0]);
        //fileObj.size / 1024 > 1025
        if (false) { //大于1M，进行压缩上传
            $scope.photoCompress(fileObj, {
                quality: 0.2
            }, function (base64Codes) {
                //console.log("压缩后：" + base.length / 1024 + " " + base);
                var bl = $scope.convertBase64UrlToBlob(base64Codes);
                formData.append("up_img_WU_FILE_0", bl, "up_img_WU_FILE_0.jpg"); // 文件对象
            });
        } else { //小于等于1M 原图上传
            formData.append("up_img_WU_FILE_0", fileObj); // 文件对象
        }
        itemService.uploadFile(formData).success(function (result) {
            var btnArray = ['确定'];
            if (result.status) {
                $scope.imageUrl = result.object;
                mui.confirm(result.message, '上传成功!', btnArray, function (e) {
                    if (e.index == 0) {
                    }
                })
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
                    window.location.reload(true);
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

    //商品详情
    $scope.itemDetail = function () {
        var itemId = $location.search()["itemId"];
        itemService.itemDetail(itemId).success(function (result) {
            if (result.status) {
                $scope.object = result.object;
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

    //获取所有商品
    $scope.myThinkItem = function () {
        itemService.youThinkItem().success(function (result) {
            if (result.status) {
                $scope.items = result.object;
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
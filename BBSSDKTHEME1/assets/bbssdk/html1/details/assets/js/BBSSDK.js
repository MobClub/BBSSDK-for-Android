(function() {
    // 计算时间差
    function timeDiff(time){
        var diffdate = new Date().getTime() - time*1000;
        var days = Math.floor(diffdate/(24*3600*1000));
        var leave1 = diffdate%(24*3600*1000);   
        var hours = Math.floor(leave1/(3600*1000));  

        var leave2 = leave1%(3600*1000);
        var minutes = Math.floor(leave2/(60*1000));
        var leave3 = leave2%(60*1000);
        var seconds = Math.round(leave3/1000);
        return (days && days > 0) ? days + "天前" : (hours && hours > 0) ? hours + "小时前" : (minutes && minutes > 0) ? minutes + "分钟前" : "刚刚";
    }
    /*获取主题帖子详情*/
    function getForumThreadDetails(callback) {
        // 实现native交互，获取主题帖子详情数据，并转成JSON对象 JSON.parse("");collection
//        callback({"like":false,"follow":false,"recommend_add":10,"collection":false,"fid":37,"summary":"近近景近景","forumName":"交友","subject":"标题标题标题标题标题标题标题标题标题标题","heats":0,"attachments":[{"fileName":"重力系统源码.rar","createdOn":148212731, "fileSize":39771,"readPerm":0, "isImage":0, "width":544, "uid":1,"url":"http://www.apkbus.com/201104/23/08053623ql625n0rz6s3d5.rar"},{"fileName":"我的照片","createdOn":148212731, "fileSize":39771,"readPerm":0, "isImage":1, "width":544, "uid":1,"url":"http://www.apkbus.com/201104/23/08053623ql625n0rz6s3d5.rar"} ],"avatar":"http://182.92.158.79/utf8_x33/uc_server/avatar.php?uid=190&size=middle","authorId":190,"createdOn":1493968050,"message":"<p>华叔说饭好少是粉红色的护发素的就俯拾地芥开发就是大回复就是导航福建省的和副驾驶的和副驾驶的合富金生店和副驾驶的和副驾驶的护发素的继父回家合富金生店和服就是的复活节</p><a href='www.baidu.com'>数据的撒地方惊世毒妃健身房附近的</a><p><img src='http://img4.imgtn.bdimg.com/it/u=1893242521,2314552659&fm=26&gp=0.jpg'><img src='http://img1.imgtn.bdimg.com/it/u=235569342,2055818743&fm=26&gp=0.jpg'></p>","author":"dftd","views":0,"replies":0,"images":["http://download.bbssdk.mob.com/30b/edb/ae9315c46ca6da0c8ea79ef0c5.jpg","http://download.bbssdk.mob.com/71f/c0e/62f88e0a1c1a015609dfdac044.jpg","http://download.bbssdk.mob.com/8d9/296/28016253bd6267a26dd8712037.png","http://download.bbssdk.mob.com/c61/bea/ff264d56027130de3d9ed3124c.jpg","http://download.bbssdk.mob.com/c61/bea/ff264d56027130de3d9ed3124c.jpg","http://download.bbssdk.mob.com/30b/edb/ae9315c46ca6da0c8ea79ef0c5.jpg","http://download.bbssdk.mob.com/f9d/26a/a39c9ad2d0539e124e4740b557.jpg"],"tid":234});
    	var data = null;
        try {
            if (window.forumThread) {
                data = window.forumThread.getForumThreadDetails();
            } else {
                data = prompt("getForumThreadDetails");
            }
        } catch(err) {
        }
        callback(data);
    }

    /*
     * 获取主题帖子回帖列表
     * @param authorId {number} 需要筛选的用户id，默认0（不筛选）
     * */
    function getPosts(fid, tid, page, pageSize,authorId, callback) {
        // 实现native交互，获取主题帖子回帖列表数据，并转成JSON对象 JSON.parse("");
//        console.log(fid+"&&&"+tid+"&&&"+page+"&&&"+pageSize+"&&&"+authorId)
//       callback([
//        {"createdOn":1494235567,"message":"hdhddhdhhdj<br />\n<img src=\"http://download.bbssdk.mob.com/30b/edb/ae9315c46ca6da0c8ea79ef0c5.jpg\" border=\"0\" alt=\"\" /><br />哈哈哈哈哈","position":1,"author":"orange","deviceName":"MI 4LTE","pid":21290,"tid":3857,"avatar":"http://182.92.158.79/utf8_x33/uc_server/avatar.php?uid=223&size=middle","authorId":190, "prePost": [{"author": "小小","message": "牛逼","createdOn": 1492832131,"position": 3, "deviceName": "iPhone 6p"}]},
//        {"createdOn":1494235605,"message":"liangzhanh<br />\n<img src=\"http://download.bbssdk.mob.com/bb0/160/d3e8d4488e23d31b99eabddbf2.jpg\" border=\"0\" alt=\"\" /><br />\n<img src=\"http://download.bbssdk.mob.com/71f/c0e/62f88e0a1c1a015609dfdac044.jpg\" border=\"0\" alt=\"\" />","position":2,"author":"orange","deviceName":"MI 4LTE","pid":21291,"tid":3857,"avatar":"http://182.92.158.79/utf8_x33/uc_server/avatar.php?uid=223&size=middle","authorId":223, "prePost": [{"author": "小小","message": "牛逼","createdOn": 1492832131,"position": 2, "deviceName": "iPhone 6p"},{"author": "小小","message": "牛逼","createdOn": 1492832131,"position": 2, "deviceName": "iPhone 6p"}]},
//        {"pid": 6,"tid": 123,"author": "小巴","authorId": 2996,"avatar": "http://www.apkbus.com/uc_server/avatar.php?uid=2996","message": "厉害了我的哥","position": 3,"createdOn": 1492833331,"deviceName": "华为 P9", "prePost": [{"author": "小小","message": "牛逼","createdOn": 1492832131,"position": 3, "deviceName": "iPhone 6p"}]},
//        {"pid": 5,"tid": 123,"author": "小小","authorId": 2836,"avatar": "http://www.apkbus.com/uc_server/avatar.php?uid=2836","message": "<p>牛逼</p>","position": 4,"createdOn": 1492832131,"deviceName": "iPhone 6p", "prePost": []},
//        {"pid": 5,"tid": 123,"author": "小小","authorId": 2836,"avatar": "http://www.apkbus.com/uc_server/avatar.php?uid=2836","message": "<p>牛逼</p>","position": 5,"createdOn": 1492832131,"deviceName": "iPhone 6p", "prePost": []}
//        ]);
        try {
            if (window.forumThread) {
                var func = $mob.ext._bindCallbackFunc(callback)
                window.forumThread.getPosts(fid, tid, page, pageSize, authorId, func);
            } else {
                var map = {
                    fid: fid,
                    tid: tid,
                    page: page,
                    authorId: authorId,
                    pageSize: pageSize,
                    callback: func
                };
                prompt("getPosts", JSON.stringify(map));
            }
        } catch(err) {
            console.log(err)
            callback(null);
        }
    }

    /*打开图片*/
    function openImage(imgList,index) {
        // 实现native交互，跳转打开图片的界面
        if (window.forumThread) {
            window.forumThread.openImage(imgList, index);
        } else {
            console.log(index)
            console.log(JSON.stringify(imgList, null,2))
            var map = {
                imageUrls: imgList,
                index: index
            };
            prompt("openImage", JSON.stringify(map));
        }
    }

    /*打开附件*/
    function openAttachment(attachment) {
        // 实现native交互，跳转打开附件的界面
        if (window.forumThread) {
            window.forumThread.openAttachment(JSON.stringify(attachment));
        } else {
            console.log(attachment);
            prompt("openAttachment", JSON.stringify(attachment));
        }
    }

    /* 跳转链接 */
    function openHref(href) {
        // 实现native交互，跳转到链接：href
        if (window.forumThread) {
            window.forumThread.openHref(href);
        } else {
            var map = {
                clickUrl: href,
            };
            prompt("openHref", JSON.stringify(map));
        }
    }

	/* 获取图片集合地址和当前索引
    * 设置hideNav为true时不显示头
    */
    function getImageUrlsAndIndex(callback,hideNav) {
        // 由Native返回对象{imageUrls:[], index:0}
//        callback({imageUrls:["https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=949197531,2432970866&fm=23&gp=0.jpg",
//        "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1738350124,1182499903&fm=23&gp=0.jpg",
//        "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1497794588,1167065085&fm=23&gp=0.jpg"
//        ], index:0});
//        if(hideNav){
//            $(".photo-browser .bar").hide();
//            $(".bar-nav~.content").css({"top": "0"});
//        }
        var data = null;
        if (window.forumImage) {
            data = JSON.parse(window.forumImage.getImageUrlsAndIndex());
        } else {
            data = JSON.parse(prompt("getImageUrlsAndIndex"));
        }
        callback(data);
        if (hideNav) {
            $(".photo-browser .bar").hide();
            $(".bar-nav~.content").css({"top": "0"});
        }
    }

    /*设置当前页面图片地址和index*/
    function setCurrentImageSrc(imgSrc, index) {
        if (window.forumImage) {
            window.forumImage.setCurrentImageSrc(imgSrc, index);
        } else {
            var map = {
                imageSrc: imgSrc,
                index: index
            };
            prompt("setCurrentImageSrc", JSON.stringify(map));
        }
    }

    /* 下载图片，iimgList为界面所有的img标签对应的图片链接 */
    function downloadImages(imgUrls) {
        // 由Native去实现下载图片，并通过showImage方法，将下载好的图片返回给h5进行显示
//        console.log(JSON.stringify(imgList, null,2))
//        showImage('5a79560af426f702bc8aa56896fde32e', 'http://download.bbssdk.mob.com/71f/c0e/62f88e0a1c1a015609dfdac044.jpg')
//        showImage('74cbe2be4c9f1e32e2cee3961fdb8032', 'http://download.bbssdk.mob.com/bb0/160/d3e8d4488e23d31b99eabddbf2.jpg')
//        showImage('e214d62568598cae227f834b30a693a3', 'http://img4.imgtn.bdimg.com/it/u=1893242521,2314552659&fm=26&gp=0.jpg')
        if (window.forumThread) {
            window.forumThread.downloadImages(imgUrls);
        } else if (window.forumImage) {
            window.forumImage.downloadImages(imgUrls);
        } else {
            var map = {
                imageUrls: imgUrls,
            };
            prompt("downloadImages", JSON.stringify(map));
        }
    }

//    /* 显示图片，替换imgUrl对应的img标签的src值为imgSrc，其中imgSrc为Native已经下载好的本地图片地址 */
//    function showImage(imgUrlMD5, imgSrc) {
//        console.log("showImage1 md5 = " + imgUrlMD5 + ", src = " + imgSrc);
//        //H5实现此方法，并进行图片替换操作
//    	if (imgSrc) {
//    		$("."+imgUrlMD5).attr("src",imgSrc)
//			$("."+imgUrlMD5).attr("src_flag",'1')
//    	} else{
//    		$("."+imgUrlMD5).attr("src",'../assets/images/default_pic_error.png')
//    	}
//    }

    function showImage(index, imgUrlMD5, imgSrc, isImageViewer) {
    if (isImageViewer) {
        if (imgSrc) {
            $(".photo-browser .photo-browser-swiper-container .swiper-slide").eq(index).find("img").attr("src", imgSrc);
        } else {
            $(".photo-browser .photo-browser-swiper-container .swiper-slide").eq(index).find("img").attr("src", "img/default_pic_error.png");
        }
    } else {
       if (imgSrc) {
            $("."+imgUrlMD5).attr("src",imgSrc)
            $("."+imgUrlMD5).attr("src_flag",'1')
       } else{
            $("."+imgUrlMD5).attr("src",'../assets/images/default_pic_error.png')
       }
    }
//        if (isImageViewer) {
//
//        } else {
//            $.each($("[dz-imgshow]"), function(index, item){
//                if (item.src_link == imgUrlMD5) {
//                    if (imgSrc) {
//                        item.src = imgSrc;
//                    } else {
//                        item.src = "img/default_pic_error.png";
//                    }
//                }
//            });
//        }
    }

    /*
     * 添加新的评论
     * @param data {Object} 评论内容
     * @param authorId {number} 楼主编号
     * */
    function addNewCommentHtml(data, authorId){
        // 实现native交互，添加新的评论
//        var data = {"pid": 5,"tid": 123,"author": "小小","authorId": 2836,"avatar": "http://www.apkbus.com/uc_server/avatar.php?uid=2836","message": "<p>牛逼</p>","position":6,"createdOn": 1492832131,"deviceName": "iPhone 6p", "prePost": []};
        if(window.forumThread) {
            details.commentList.push(data)//将data插入即可
        } else {
            var data = {"pid": 5,"tid": 123,"author": "小小","authorId": 2836,"avatar": "http://www.apkbus.com/uc_server/avatar.php?uid=2836","message": "<p>牛逼</p>","position":6,"createdOn": 1492832131,"deviceName": "iPhone 6p", "prePost": []};
            details.commentList.push(data)//将data插入即可
        }
    }

    /*
     * 回复方法
     * @param prepost {Object} 回复内容
    */
    function replyComment(prepost){
        if (!prepost) {
            return;
        }
        if (window.forumThread) {
            window.forumThread.replyPost(JSON.stringify(prepost));
        } else {
            prompt("replyPost", JSON.stringify(prepost));
        }
    }

    /*
     * 只看楼主
     * @param flag {number} 1为只看楼主，0为取消只看楼主
     * */
    function updateCommentHtml(flag){
    	$(document.body).scrollTop(0);
    	details.commentList=[]
    	if(flag==1){
    		details.onlyAuthor=true
    		details.authorId=details.article.authorId
    		details.getCommentList(details.article.fid, details.article.tid, 1, details.pageSize, details.authorId);
    	}
    	else{
    		details.onlyAuthor=false
    		details.authorId=0
    		details.getCommentList(details.article.fid, details.article.tid, 1, details.pageSize, details.authorId);
    	}
    }

    /**
     * press 回调
     * @param  {[type]} img [description]
     * @return {[type]}     [description]
     */
    function pressImgCallback(img){
        if (window.forumThread) {
            window.forumThread.onImageLongPressed(img);
        } else {
            prompt("onImageLongPressed", JSON.stringify(img));
        }
    }

	/*点击头像后，前往作者详情*/
    function openAuthor(authorId) {
        // 实现native交互，跳转作者的界面
        if (window.forumThread) {
            window.forumThread.openAuthor(authorId)
        } else {
            console.log("openAuthor: " + authorId);
        }
    }

    /*
     * 关注文章作者
     * @param flag {number} 0为关注，1为取消关注
     * */
    function followAuthor(authorId,flag,callback) {
        // 实现native交互，关注文章作者，返回结果（Boolean）给h5前端
        if (window.forumThread) {
            var func = $mob.ext._bindCallbackFunc(callback)
            window.forumThread.followAuthor(authorId, flag, func)
        } else {
            console.log(authorId + "&&&&" + flag);
            callback(true)
        }
    }

    /*
     * 喜欢文章
     * */
    function likeArticle(fid,tid,callback) {
        // 实现native交互，喜欢文章，返回结果（Boolean）给h5前端
        if (window.forumThread) {
            var func = $mob.ext._bindCallbackFunc(callback)
            window.forumThread.likeArticle(fid, tid, func)
        } else {
            console.log(authorId + "&&&&" + flag);
            callback(true)
        }
    }

    /*界面跳转到评论模块*/
    function goComment() {
        details.goComment()
    }

    /*定义BBSSDKNative全局属性*/
    window.BBSSDKNative = {
        getForumThreadDetails: getForumThreadDetails,
        getPosts: getPosts,
        openImage: openImage,
        openAttachment: openAttachment,
        openHref: openHref,
        getImageUrlsAndIndex: getImageUrlsAndIndex,
        setCurrentImageSrc: setCurrentImageSrc,
        downloadImages: downloadImages,
        showImage: showImage,
//        getCommonHtml: getCommonHtml,
        replyComment: replyComment,
        addNewCommentHtml: addNewCommentHtml,
        pressImgCallback: pressImgCallback,
        //2017-08-21新增接口
        updateCommentHtml: updateCommentHtml,
        openAuthor:openAuthor,
        followAuthor:followAuthor,
        likeArticle:likeArticle,
        //2017-08-23新增接口
        goComment:goComment
    }
})();
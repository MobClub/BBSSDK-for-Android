(function() {
    /*获取主题帖子详情*/
    function getForumThreadDetails(callback) {
        var data = null;
        try {
            if (window.forumThread) {
                data = JSON.parse(window.forumThread.getForumThreadDetails());
            } else {
                data = JSON.parse(prompt("getForumThreadDetails"));
            }
        } catch(err) {
        }
        callback(data);
    }

    /*获取主题帖子回帖列表*/
    function getPosts(fid, tid, page, pageSize, callback) {
        var func = $mob.ext._bindCallbackFunc(callback);
        try {
            if (window.forumThread) {
                window.forumThread.getPosts(fid, tid, page, pageSize, func);
            } else {
                var map = {
                    fid: fid,
                    tid: tid,
                    page: page,
                    pageSize: pageSize,
                    callback: func
                };
                prompt("getPosts", JSON.stringify(map));
            }
        } catch(err) {
            func(null);
        }
    }

    /*打开图片*/
    function openImage(imageUrls, index) {
        if (window.forumThread) {
            window.forumThread.openImage(imageUrls, index);
        } else {
            var map = {
                imageUrls: imageUrls,
                index: index
            };
            prompt("openImage", JSON.stringify(map));
        }
    }

    /*打开附件*/
    function openAttachment(attachment) {
        if (window.forumThread) {
            window.forumThread.openAttachment(JSON.stringify(attachment));
        } else {
            prompt("openAttachment", JSON.stringify(attachment));
        }
    }

    /*打开链接*/
    function openHref(url) {

    }

    /*获取网页图片集和当前点击的图片id*/
    function getImageUrlsAndIndex(callback) {
        var data = null;
        if (window.forumImage) {
            data = JSON.parse(window.forumImage.getImageUrlsAndIndex());
        } else {
            data = JSON.parse(prompt("getImageUrlsAndIndex"));
        }
        callback(data);
    }

    /*设置当前页面图片地址和index*/
    function setCurrentImageSrc(imgSrc, index) {
        console.log(imgSrc);
        console.log(index);
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

    /* 下载图片，imgUrlList为界面所有的img标签对应的图片链接 */
    function downloadImages(imgUrls) {
        if (window.forumThread) {
            window.forumThread.downloadImages(imgUrls);
        } else if (window.forumImage) {
            window.forumImage.downloadImages(imgUrls);
        } else {
            var map = {
                imgUrls: imgUrls,
            };
            prompt("downloadImages", JSON.stringify(map));
        }
    }

    /* 显示图片，替换imgUrl对应的img标签的src值为imgSrc，其中imgSrc为Native已经下载好的本地图片地址 */
    function showImage(index, imgUrlMD5, imgSrc, isImageViewer) {
        if (isImageViewer) {
            if (imgSrc) {
                $(".photo-browser .photo-browser-swiper-container .swiper-slide").eq(index).find("img").attr("src", imgSrc);
            } else {
                $(".photo-browser .photo-browser-swiper-container .swiper-slide").eq(index).find("img").attr("src", "img/default_pic_error.png");
            }
        } else {
            $.each($("[dz-imgshow]"), function(index, item){
                if (item.src_link == imgUrlMD5) {
                    if (imgSrc) {
                        item.src = imgSrc;
                    } else {
                        item.src = "img/default_pic_error.png";
                    }
                }
            });
        }
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
        showImage: showImage
    }
})();
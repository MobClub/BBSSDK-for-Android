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
    function getPosts(fid, tid, page, pageSize, authorId, callback) {
        try {
            var func = $mob.ext._bindCallbackFunc(callback);
            if (window.forumThread) {
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
            callback(null);
        }
    }

    /*打开图片*/
    function openImage(imgList, index) {
        if (window.forumThread) {
            window.forumThread.openImage(imgList, index);
        } else {
            var map = {
                imageUrls: imgList,
                index: index
            };
            prompt("openImage", JSON.stringify(map));
        }
    }

    function bindCommentImgEvent(li){
        var myPhotoBrowserStandalone;
        var imgList = [];
        var downloadImgList = [];
        $.each($(li).find(".center img"), function(index, item){
            var isLocalImg = false;
            if (item.src.length > 7) {
                isLocalImg = item.src.substr(0, 7) == "file://";
            }
            if (isLocalImg) {
                imgList.push(item.src_original);
            } else {
                imgList.push(item.src);
                downloadImgList.push(item.src);
                item.src_link = md5(item.src);
                item.src_original = item.src;
                item.src = "img/default_pic.png";
                item.srcset = "";
            }
            $(item).off("click").on("click", function(){
                BBSSDKNative.openImage(imgList,index);
//                myPhotoBrowserStandalone.open(index);
            });
        });
        if (imgList.length > 0) {
            BBSSDKNative.downloadImages(downloadImgList);
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
    function getImageUrlsAndIndex(callback, hideNav) {
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

    /* 下载图片，imgUrlList为界面所有的img标签对应的图片链接 */
    function downloadImages(imgUrls) {
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

    /*
     * 添加新的评论
     * @param data {Object} 评论内容
     * @param authorId {number} 楼主编号
     * */
    function addNewCommentHtml(data, authorId){
        var commentSize = $(".common-content ul").find("li").length;
        if(commentSize == 0 || commentSize % 10 != 0){
            prePostLists.push(data);
            if (data.message) {
                data.message = data.message.replace(/<img/g, '<img dz-imgshow').replace(/<a /g, '<a dz-ahref class="external"');
            }
            var html = "";
            html += '<li><div class="header row"><div class="left">' +
                    '<div class="headimg"><img src="'+ data.avatar +'"></div>' +
                        '<div class="userinfo">' +
                            '<h3><span>'+ data.author +'</span>'+ (data.authorId == authorId ? '<span class="tip">楼主</span>' : '') +'</h3>' +
                        '</div>' +
                    '</div>' +
                    '<div class="right">' +
                        '<span class="category">'+ data.position +'楼</span>' +
                    '</div></div><div class="center"><p>'+ data.message +'</p>';
            if(data.prePost){
                html += '<div class="reply"><p class="sub"><strong>引用：</strong>回' + data.prePost.author +'于'+ timeDiff(data.prePost.createdOn) +'发表的：'+ data.prePost.message +'</p></div>';
            }
            html += '<div class="bottom"><span>'+ timeDiff(data.createdOn) +'</span><span>来自 '+data.deviceName+'</span><span class="tip" reply-data="'+ $(".common-content ul").find("li").length +'">回复</span></div></li>';
            $(".common-content ul").append(html);
            $(document).off("click").on("click","[reply-data]",function(){
                BBSSDKNative.replyComment(prePostLists[$(this).attr("reply-data")]);
            });
            bindCommentImgEvent($(".common-content ul li").last());
        }else{
            return;
        }
    }

    /* 渲染评论列表 */
    var _PAGE = null, _FID , _TID, _T;
    var prePostLists = [];
    function getCommonHtml(page, fid, tid, authorId, Isfilter) {
        _PAGE = page;
        _FID = fid;
        _TID = tid;

        var filterId = Isfilter ? authorId : 0;

        // 加载flag
        var loading = false;
        // 每次加载添加多少条目
        var itemsPerLoad = 10;

        var pagecout = 1;

        /*获取主题帖子回帖列表*/
        var getthisPosts = function (fid, tid, page, pageSize){
            prePostLists = [];
            pagecout = page + 1;
            getPosts(fid, tid, page, pageSize, filterId, function(data){
                if(data){
                    // 获取数据成功
                    var html = "";
                    $.each(data,function(index, item){
                        if (item.message) {
                            item.message = item.message.replace(/<img/g, '<img dz-imgshow').replace(/<a /g, '<a dz-ahref class="external"');
                        }
                        html += '<li><div class="header row"><div class="left">' +
                        '<div class="headimg"><img src="'+ item.avatar +'"></div>' +
                        '<div class="userinfo">' +
                        '<h3><span>'+ item.author +'</span>'+ (item.authorId == authorId ? '<span class="tip">楼主</span>' : '') +'</h3>' +
                        '</div>' +
                        '</div>' +
                        '<div class="right">' +
                        '<span class="category">'+ item.position +'楼</span>' +
                        '</div></div><div class="center"><p>'+ item.message +'</p>';
                        if(item.prePost){
                            html += '<div class="reply"><p class="sub"><strong>引用：</strong>回' + item.prePost.author +'于'+ timeDiff(item.prePost.createdOn) +'发表的：'+ item.prePost.message +'</p></div>';
                        }
                        html += '<div class="bottom"><span>'+ timeDiff(item.createdOn) +'</span><span>来自 '+item.deviceName+'</span><span class="tip" reply-data="'+ index +'">回复</span></div></li>';
                        prePostLists.push(item);
                    });
                    $(".common-content ul").append(html);
                    $(document).off("click").on("click","[reply-data]",function(){
                        BBSSDKNative.replyComment(prePostLists[$(this).attr("reply-data")]);
                    });
                    $.each($(".common-content ul").find("li"), function(index, item){
                        bindCommentImgEvent(item);
                    });
                    $(".dz-loading-over span").text("以上已为全部内容");
                    if (data.length != pageSize) {
                        $.detachInfiniteScroll($('.infinite-scroll'));
                        $('.infinite-scroll-preloader').remove();
                        $(".dz-loading-over").show();
                    } else {
                        $(".dz-loading-over").hide();
                    }
                }else{
                    // 获取数据失败
                    $.detachInfiniteScroll($('.infinite-scroll'));
                    $('.infinite-scroll-preloader').remove();
                    $(".dz-loading-over").show();
                }
            });

        };
        //预先加载10条
        getthisPosts(fid,tid, pagecout, itemsPerLoad);

        $(_PAGE).off('infinite').on('infinite', function() {
            if (loading) return;
            loading = true;
            // 模拟1s的加载过程
            _T = setTimeout(function() {
                // 重置加载flag
                loading = false;

                // 添加新条目
                getthisPosts(fid,tid, pagecout, itemsPerLoad);
                $.refreshScroller();
            }, 1000);

        });
    }


    /* 刷新评论列表 */
    function updateCommentHtml(authorId){
        if(_PAGE && _FID && _TID) {
            $(".common-content ul").html("");
            $(".content").scrollTop(0);
            window.clearTimeout(_T);
            getCommonHtml(_PAGE,_FID,_TID,authorId,true);
        } else {
            return;
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

    function pressImgCallback(img){
        console.log(img);
        if (window.forumThread) {
            window.forumThread.onImageLongPressed(img);
        } else {
            prompt("onImageLongPressed", JSON.stringify(img));
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
        showImage: showImage,
        //新增接口
        getCommonHtml: getCommonHtml,
        updateCommentHtml: updateCommentHtml,
        replyComment: replyComment,
        addNewCommentHtml: addNewCommentHtml,
        pressImgCallback: pressImgCallback
    }
})();
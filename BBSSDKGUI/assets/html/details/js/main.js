/* 时间戳转换时间 */
function timeTodate(time){
	var date = new Date(time*1000);
	Y = date.getFullYear();
	var month = date.getMonth()+1;
	M = month < 10 ? ('0'+ month) : month;
	var day = date.getDate();
	D = day < 10 ? ('0' + day) : day;
	var hour = date.getHours();
	h = hour < 10 ? ('0' + hour) : hour;
    var minutes = date.getMinutes();
	m = minutes < 10 ? ('0' + minutes) : minutes;
	return Y + '-' + M + '-' + D + ' ' + h + ':' + m;
}

/* 渲染评论列表 */
function getCommonHtml(page,fid,tid){
	// 加载flag
	var loading = false;
	// 每次加载添加多少条目
	var itemsPerLoad = 10;

	var pagecout = 1;

	/*获取主题帖子回帖列表*/
	var getPosts = function (fid, tid, page, pageSize){
		pagecout = page + 1;
		BBSSDKNative.getPosts(fid, tid, page, pageSize, function(data){
			if(data){
				// 获取数据成功
				var html = "";
				$.each(data,function(index, item){
					html += '<li><div class="header"><span class="headimg"><img src="'+ item.avatar +'"></span><span>'+ item.author +'</span><span class="tip">'+ item.position +'楼</span></div><div class="center">'+ item.message; 
					if(item.prePost){
						html += '<div class="reply"><p class="sub">'+ item.prePost.author +' ' + timeTodate(item.prePost.createdOn) +'</p><p>'+ item.prePost.message +'</p>'
					}
					html += '</div><div class="bottom"><span>'+ timeTodate(item.createdOn) +'</span></div></li>';
				});
				$(".common-content ul").append(html);
				$(".dz-loading-over span").text("以上已为全部内容");
				$(".dz-loading-over").hide();
			}else{
				// 获取数据失败
				$.detachInfiniteScroll($('.infinite-scroll'));
				$('.infinite-scroll-preloader').remove();
				$(".dz-loading-over").show();
			}
		});
		
	};
	//预先加载10条
    getPosts(fid,tid, pagecout, itemsPerLoad);

    $(page).on('infinite', function(){
    	if (loading) return;
    	loading = true;
    	// 模拟1s的加载过程
	    setTimeout(function() {
	       	// 重置加载flag
	        loading = false;

	        // 添加新条目
	        getPosts(fid,tid, pagecout, itemsPerLoad);
	        $.refreshScroller();
	    }, 1000);

    });
}

/*打开附件*/
function openAttachment(obj) {
	BBSSDKNative.openAttachment(obj);
}

/* 绑定a标签事件 */
function ahrefbind(){
	$.each($("[dz-ahref]"), function(index, item){
		$(item).off("click").on("click", function(e){
			e.preventDefault();
			BBSSDKNative.openHref($(this).attr("href"));
		})
	});
}

function bindFilehref(data){
	$.each($(".dz-filehref"), function(index, item){
	    var attachment = data.attachments[index];
        if (attachment && attachment.filePath) {
            $(item).text('打开附件');
        } else {
            $(item).text('下载附件');
        }
        $(item).off("click").on("click", function(){
            openAttachment(attachment);
        });
	});
}

/* 详情内容渲染 */
function getDetailHtml(data){
	var attachments = function(data,s){
		var html = s;
		$.each(s.match(/{{*.*}}/g), function(index, item){
			var getattr = item.split(".")[1].split("}}")[0];
			var re = new RegExp(item,"g");
			html = html.replace(re, data[getattr]);
		});
		return html;
	}
	var data = data;
	if (data.createdOn) {
	    data.createdOn = timeTodate(data.createdOn);
	}
	if (data.avatar) {
	    data.avatar = '<img src="'+ data.avatar +'">';
	}
	if (data.message) {
	    data.message = data.message.replace(/<img/g, '<img dz-imgshow').replace(/<a /g, '<a dz-ahref class="external"');
	}
	$.each($("[dz-bind]"), function(index, item){
		var getAttr = $(item).attr("dz-bind").split(".");
		var html = $(item).html();
		html += data[getAttr[1]];
		$(item).html(html);
	});
	$.each($("[dz-repeat]"), function(index, item){
		var getAttr = $(item).attr("dz-repeat").split(".");
		var els = $(item).html();
		var html = "";
		if(data[getAttr[1]] && data[getAttr[1]].length > 0){
			$.each(data[getAttr[1]], function(index, item){
				var changeels = attachments(data[getAttr[1]][index],els);
				html += changeels;
			});
		}
		$(item).html(html);
		
	});
	// ahrefbind();
	bindFilehref(data);
	ImgShow.init();
	$.each($(".detail-content .center img"), function(index, item){
		if($(item).parents("a").length > 0){
			$(item).parents("a")[0].setAttribute("href", "javascript:void(0)");
		}
	});
}

$(function(){
	$(document).on("pageInit", ".detail-page", function(e, id, page){
		// 页面初始化完成
		BBSSDKNative.getForumThreadDetails(function(detailData) {
		    getDetailHtml(detailData);
            // 页面初始化完成，获取主题帖子列表
            getCommonHtml(page, detailData.fid, detailData.tid);
		});
	});

	$.init();
});

/* 图片预览组件 */
(function($, win){
	var myPhotoBrowserStandalone;
	/*打开图片*/
	function openImage(imgList,index) {
	    BBSSDKNative.openImage(imgList,index);
	}		
	win.ImgShow = {
		init: function(){
			var imgList = [];
            var downloadImgList = [];
            $.each($("[dz-imgshow]"), function(index, item){
                imgList.push(item.src);
                var lastIndex = item.src.lastIndexOf(".");
                if (lastIndex > 0) {
                    downloadImgList.push(item.src);
                    item.src_link = md5(item.src);
                    item.src = "img/default_pic.png";
                    item.srcset = "";
                }
                $(item).off("click").on("click", function(){
                    openImage(imgList,index);
                });
            });
            if (imgList.length > 0) {
                BBSSDKNative.downloadImages(downloadImgList);
            }
		}
	}
})(Zepto, window);
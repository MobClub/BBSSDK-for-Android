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

/* 渲染评论列表 */
function getCommonHtml(){
	BBSSDKNative.getCommonHtml.apply(this, arguments);
}


/*打开附件*/
function openAttachment(obj) {
	//实现native交互，跳转打开附件的界面
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
		data.createdOn = timeDiff(data.createdOn);
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
			getCommonHtml(page, detailData.fid, detailData.tid, detailData.authorId);
		});
	});

	$.init();
});

/* 图片预览组件 */
(function($, win){
	var myPhotoBrowserStandalone;
	/*打开图片*/
	function openImage(imgList,index) {
	    // BBSSDKNative.openImage(imgList,index);
	    // myPhotoBrowserStandalone.open(imgindex);
	}		
	win.ImgShow = {
		init: function(){
			var imgList = [];
            var downloadImgList = [];
            $.each($(".main-content [dz-imgshow]"), function(index, item){
                imgList.push(item.src);
                var lastIndex = item.src.lastIndexOf(".");
                if (lastIndex > 0) {
                    downloadImgList.push(item.src);
                    item.src_link = md5(item.src);
                    item.src = "img/default_pic.png";
                    item.srcset = "";
                }
                $(item).off("click").on("click", function(){
                     BBSSDKNative.openImage(imgList,index);
//                    myPhotoBrowserStandalone.open(index);
                    // BBSSDKNative.downloadImages(item.src);
                });
            });
            if (imgList.length > 0) {
//            	var myPhotoBrowserStandalone = $.photoBrowser({
//            		photos : imgList,
//            		onSlideChangeEnd: function(swiper){
//            			BBSSDKNative.setCurrentImageSrc($(swiper.slides[swiper.activeIndex]).find("img").attr("src"),swiper.activeIndex);
//            		}
//            	});
            	BBSSDKNative.downloadImages(downloadImgList);
            }
		}
	}
})(Zepto, window);
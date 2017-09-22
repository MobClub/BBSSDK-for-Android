package com.mob.bbssdk.sample.viewer;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.mob.bbssdk.gui.pages.forum.PageAttachmentViewer;

import java.io.File;

/**
 * 打开视频文件
 */
public class PageVideoViewer extends PageAttachmentViewer {
	private VideoView videoView;

	protected View initViewerContentView(Context context) {
		RelativeLayout rl = new RelativeLayout(context);
		videoView = new VideoView(context);
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		rl.addView(videoView, rlp);
		return rl;
	}

	protected void loadContent(String filePath, String extension, LoadContentListener loadContentListener) {
		//显示使用其它应用打开的入口，并使用videoView打开
		try {
			Uri uri = Uri.fromFile(new File(filePath));
			videoView.setMediaController(new MediaController(getContext()));
			videoView.setVideoURI(uri);
			videoView.requestFocus();
			loadContentListener.onLoadFinished(true);
		} catch (Throwable t) {
			loadContentListener.onLoadFinished(false);
		}
	}

	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		try {
			if (videoView != null && videoView.isPlaying()) {
				videoView.pause();
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		super.onPause();
	}

	public void onDestroy() {
		if (videoView != null) {
			try {
				videoView.stopPlayback();
			} catch (Throwable t) {
				t.printStackTrace();
			}
			videoView = null;
		}
		super.onDestroy();

	}
}

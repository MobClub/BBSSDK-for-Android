package com.mob.bbssdk.sample.viewer;

import android.content.Context;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.mob.bbssdk.gui.pages.PageAttachmentViewer;

import java.io.File;

/**
 * 打开PDF文件
 */
public class PagePDFViewer extends PageAttachmentViewer {
	private PDFView pdfView;
	private String titleName;

	protected View initViewerContentView(Context context) {
		pdfView = new PDFView(context, null);
		return pdfView;
	}

	protected void loadContent(String filePath, String extension, final LoadContentListener loadContentListener) {
		titleName = titleBar.getTitleTextView().getText().toString();
		if (titleName == null) {
			titleName = "";
		}
		File file = new File(filePath);
		pdfView.fromFile(file).defaultPage(0).enableSwipe(true).swipeHorizontal(false).enableDoubletap(true)
				.enableAnnotationRendering(false).password(null).scrollHandle(null)
				.onLoad(new OnLoadCompleteListener() {
					public void loadComplete(int nbPages) {
						loadContentListener.onLoadFinished(true);
					}
				})
				.onPageChange(new OnPageChangeListener() {
					public void onPageChanged(int page, int pageCount) {
						titleBar.setTitle(titleName + "(" + (page + 1) + "/" + pageCount + ")");
					}
				})
				.onError(new OnErrorListener() {
					public void onError(Throwable t) {
						loadContentListener.onLoadFinished(false);
						t.printStackTrace();
					}
				}).load();
	}
}

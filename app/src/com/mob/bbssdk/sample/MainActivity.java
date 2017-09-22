package com.mob.bbssdk.sample;


import com.mob.MobSDK;
import com.mob.bbssdk.gui.BaseMainActivity;
import com.mob.bbssdk.gui.pages.forum.PageAttachmentViewer;
import com.mob.bbssdk.model.ForumThreadAttachment;
import com.mob.bbssdk.sample.viewer.PageOfficeViewer;
import com.mob.bbssdk.sample.viewer.PagePDFViewer;
import com.mob.bbssdk.sample.viewer.PageVideoViewer;

public class MainActivity extends BaseMainActivity {
	@Override
	protected void OnAttachmentClick(ForumThreadAttachment attachment) {
		String extension = attachment.extension;
		PageAttachmentViewer page;
		if ("pdf".equals(extension)) {
			page = new PagePDFViewer();
		} else if ("doc".equals(extension) || "docx".equals(extension) || "xlsx".equals(extension)
				|| "xls".equals(extension) || "txt".equals(extension)) {
			page = new PageOfficeViewer();
		} else if ("3gp".equals(extension) || "mp4".equals(extension)) {
			page = new PageVideoViewer();
		} else {
			super.OnAttachmentClick(attachment);
			return;
		}
		page.setAttachment(attachment);
		page.show(MobSDK.getContext());
	}
}

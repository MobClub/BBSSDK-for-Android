package com.mob.bbssdk.gui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.Gravity;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * 富文本编辑器
 * Copyright (C) 2017 Wasabeef
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class RichEditor extends WebView {
	private static final String SETUP_HTML = "file:///android_asset/bbssdk/html/editor/editor.html";
	private static final String CALLBACK_SCHEME = "re-callback://";
	private static final String STATE_SCHEME = "re-state://";
	private boolean isReady = false;
	private String contents;
	private OnTextChangeListener textChangeListener;
	private OnDecorationStateListener decorationStateListener;
	private AfterInitialLoadListener loadListener;

	private JsInterface jsInterface;

	public enum Type {
		BOLD,
		ITALIC,
		SUBSCRIPT,
		SUPERSCRIPT,
		STRIKETHROUGH,
		UNDERLINE,
		H1,
		H2,
		H3,
		H4,
		H5,
		H6,
		ORDEREDLIST,
		UNORDEREDLIST,
		JUSTIFYCENTER,
		JUSTIFYFULL,
		JUSTUFYLEFT,
		JUSTIFYRIGHT
	}

	public interface OnTextChangeListener {

		void onTextChange(String text);
	}

	public interface OnDecorationStateListener {

		void onStateChangeListener(String text, List<Type> types);
	}

	public interface AfterInitialLoadListener {

		void onAfterInitialLoad(boolean isReady);
	}

	public RichEditor(Context context) {
		this(context, null);
	}

	public RichEditor(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.webViewStyle);
	}

	@SuppressLint("SetJavaScriptEnabled")
	public RichEditor(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		setVerticalScrollBarEnabled(false);
		setHorizontalScrollBarEnabled(false);
		getSettings().setJavaScriptEnabled(true);
		jsInterface = new JsInterface();
		addJavascriptInterface(jsInterface, "JsInterface");
		setWebChromeClient(new WebChromeClient());
		setWebViewClient(createWebviewClient());
		loadUrl(SETUP_HTML);

		applyAttributes(context, attrs);
	}

	protected EditorWebViewClient createWebviewClient() {
		return new EditorWebViewClient();
	}

	public void setOnTextChangeListener(OnTextChangeListener listener) {
		textChangeListener = listener;
	}

	public void setOnDecorationChangeListener(OnDecorationStateListener listener) {
		decorationStateListener = listener;
	}

	public void setOnInitialLoadListener(AfterInitialLoadListener listener) {
		loadListener = listener;
	}

	private void callback(String text) {
		contents = text.replaceFirst(CALLBACK_SCHEME, "");
		if (textChangeListener != null) {
			textChangeListener.onTextChange(contents);
		}
	}

	private void stateCheck(String text) {
		String state = text.replaceFirst(STATE_SCHEME, "").toUpperCase(Locale.ENGLISH);
		List<Type> types = new ArrayList<Type>();
		for (Type type : Type.values()) {
			if (TextUtils.indexOf(state, type.name()) != -1) {
				types.add(type);
			}
		}

		if (decorationStateListener != null) {
			decorationStateListener.onStateChangeListener(state, types);
		}
	}

	private void applyAttributes(Context context, AttributeSet attrs) {
		final int[] attrsArray = new int[]{
				android.R.attr.gravity
		};
		TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);

		int gravity = ta.getInt(0, NO_ID);
		switch (gravity) {
			case Gravity.LEFT: {
				exec("javascript:RE.setTextAlign(\"left\")");
			} break;
			case Gravity.RIGHT: {
				exec("javascript:RE.setTextAlign(\"right\")");
			} break;
			case Gravity.TOP: {
				exec("javascript:RE.setVerticalAlign(\"top\")");
			} break;
			case Gravity.BOTTOM: {
				exec("javascript:RE.setVerticalAlign(\"bottom\")");
			} break;
			case Gravity.CENTER_VERTICAL: {
				exec("javascript:RE.setVerticalAlign(\"middle\")");
			} break;
			case Gravity.CENTER_HORIZONTAL: {
				exec("javascript:RE.setTextAlign(\"center\")");
			} break;
			case Gravity.CENTER: {
				exec("javascript:RE.setVerticalAlign(\"middle\")");
				exec("javascript:RE.setTextAlign(\"center\")");
			} break;
		}

		ta.recycle();
	}

	public void setHtml(String contents) {
		if (contents == null) {
			contents = "";
		}
		try {
			exec("javascript:RE.setHtml('" + URLEncoder.encode(contents, "UTF-8") + "');");
		} catch (UnsupportedEncodingException e) {
			// No handling
		}
		this.contents = contents;
	}

	/**
	 * 获取用户输入的帖子的html内容。
	 *
	 * @return
	 */
	public String getHtml() {
		return contents;
	}

	public void setEditorFontColor(int color) {
		String hex = convertHexColorString(color);
		exec("javascript:RE.setBaseTextColor('" + hex + "');");
	}

	public void setEditorFontSize(int px) {
		exec("javascript:RE.setBaseFontSize('" + px + "px');");
	}

	@Override
	public void setPadding(int left, int top, int right, int bottom) {
		super.setPadding(left, top, right, bottom);
		exec("javascript:RE.setPadding('" + left + "px', '" + top + "px', '" + right + "px', '" + bottom
				+ "px');");
	}

	@Override
	public void setPaddingRelative(int start, int top, int end, int bottom) {
		// still not support RTL.
		setPadding(start, top, end, bottom);
	}

	public void setEditorBackgroundColor(int color) {
		setBackgroundColor(color);
	}

	@Override
	public void setBackgroundColor(int color) {
		super.setBackgroundColor(color);
	}

	@Override
	public void setBackgroundResource(int resid) {
		Bitmap bitmap = decodeResource(getContext(), resid);
		String base64 = toBase64(bitmap);
		bitmap.recycle();

		exec("javascript:RE.setBackgroundImage('url(data:image/png;base64," + base64 + ")');");
	}

	@Override
	public void setBackground(Drawable background) {
		Bitmap bitmap = toBitmap(background);
		String base64 = toBase64(bitmap);
		bitmap.recycle();

		exec("javascript:RE.setBackgroundImage('url(data:image/png;base64," + base64 + ")');");
	}

	public void setBackground(String url) {
		exec("javascript:RE.setBackgroundImage('url(" + url + ")');");
	}

	public void setEditorWidth(int px) {
		exec("javascript:RE.setWidth('" + px + "px');");
	}

	public void setEditorHeight(int px) {
		exec("javascript:RE.setHeight('" + px + "px');");
	}

	public void setPlaceholder(String placeholder) {
		exec("javascript:RE.setPlaceholder('" + placeholder + "');");
	}

	public void setPlaceholderTextColor(int color) {
		exec("javascript:RE.prepareInsert();");

		String hex = convertHexColorString(color);
		exec("javascript:RE.setPlaceholderTextColor('" + hex + "');");
	}

	public void setInputEnabled(Boolean inputEnabled) {
		exec("javascript:RE.setInputEnabled(" + inputEnabled + ")");
	}

	public void loadCSS(String cssFile) {
		String jsCSSImport = "(function() {"
				+ "    var head  = document.getElementsByTagName(\"head\")[0];"
				+ "    var link  = document.createElement(\"link\");"
				+ "    link.rel  = \"stylesheet\";"
				+ "    link.type = \"text/css\";"
				+ "    link.href = \"" + cssFile + "\";"
				+ "    link.media = \"all\";"
				+ "    head.appendChild(link);"
				+ "}) ();";
		exec("javascript:" + jsCSSImport + "");
	}

	public void setEndFormat() {
		exec("javascript:RE.setEndFormat();");
	}

	public void undo() {
		exec("javascript:RE.undo();");
	}

	public void redo() {
		exec("javascript:RE.redo();");
	}

	public void setBold() {
		exec("javascript:RE.setBold();");
	}

	public void setItalic() {
		exec("javascript:RE.setItalic();");
	}

	public void setSubscript() {
		exec("javascript:RE.setSubscript();");
	}

	public void setSuperscript() {
		exec("javascript:RE.setSuperscript();");
	}

	public void setStrikeThrough() {
		exec("javascript:RE.setStrikeThrough();");
	}

	public void setUnderline() {
		exec("javascript:RE.setUnderline();");
	}

	public void setTextColor(int color) {
		exec("javascript:RE.prepareInsert();");

		String hex = convertHexColorString(color);
		exec("javascript:RE.setTextColor('" + hex + "');");
	}

	public void setTextBackgroundColor(int color) {
		exec("javascript:RE.prepareInsert();");

		String hex = convertHexColorString(color);
		exec("javascript:RE.setTextBackgroundColor('" + hex + "');");
	}

	public void setFontSize(int fontSize) {
		if (fontSize > 7 || fontSize < 1) {
//			Log.e("RichEditor", "Font size should have a value between 1-7");
		}
		exec("javascript:RE.setFontSize('" + fontSize + "');");
	}

	public void removeFormat() {
		exec("javascript:RE.removeFormat();");
	}

	public void setHeading(int heading) {
		exec("javascript:RE.setHeading('" + heading + "');");
	}

	public void setIndent() {
		exec("javascript:RE.setIndent();");
	}

	public void setOutdent() {
		exec("javascript:RE.setOutdent();");
	}

	public void setAlignLeft() {
		exec("javascript:RE.setJustifyLeft();");
	}

	public void setAlignCenter() {
		exec("javascript:RE.setJustifyCenter();");
	}

	public void setAlignRight() {
		exec("javascript:RE.setJustifyRight();");
	}

	public void setBlockquote() {
		exec("javascript:RE.setBlockquote();");
	}

	public void setPre() {
		exec("javascript:RE.setPre();");
	}

	public void setBullets() {
		exec("javascript:RE.setBullets();");
	}

	public void setNumbers() {
		exec("javascript:RE.setNumbers();");
	}

	public void insertHTML(String html) {
		exec("javascript:RE.insertHTML('" + html + "');");
	}
	public void insertImage(String url, String alt) {
		exec("javascript:RE.prepareInsert();");
		exec("javascript:RE.insertImage('" + url + "', '" + alt + "');");
	}

	public void insertLink(String href, String title) {
		exec("javascript:RE.prepareInsert();");
		exec("javascript:RE.insertLink('" + href + "', '" + title + "');");
	}

	public void insertTodo() {
		exec("javascript:RE.prepareInsert();");
		exec("javascript:RE.setTodo('" + System.currentTimeMillis() + "');");
	}

	public void focusEditor() {
		requestFocus();
		exec("javascript:RE.focus();");
	}

	public void getImages(OnJsCallbackListener listener) {
		if (jsInterface != null) {
			jsInterface.addOnJsCallbackListener(listener);
		}
		exec("javascript:RE.getImages();");
	}

	public void clearFocusEditor() {
		exec("javascript:RE.blurFocus();");
	}

	private String convertHexColorString(int color) {
		return String.format("#%06X", (0xFFFFFF & color));
	}

	protected void exec(final String trigger) {
		if (isReady) {
			load(trigger);
		} else {
			postDelayed(new Runnable() {
				@Override
				public void run() {
					exec(trigger);
				}
			}, 100);
		}
	}

	private void load(String trigger) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			evaluateJavascript(trigger, null);
		} else {
			loadUrl(trigger);
		}
	}

	protected class EditorWebViewClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			isReady = url.equalsIgnoreCase(SETUP_HTML);
			if (loadListener != null) {
				loadListener.onAfterInitialLoad(isReady);
			}
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			String decode;
			try {
				decode = URLDecoder.decode(url, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// No handling
				return false;
			}

			if (TextUtils.indexOf(url, CALLBACK_SCHEME) == 0) {
				callback(decode);
				return true;
			} else if (TextUtils.indexOf(url, STATE_SCHEME) == 0) {
				stateCheck(decode);
				return true;
			}

			return super.shouldOverrideUrlLoading(view, url);
		}
	}

	private String toBase64(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] bytes = baos.toByteArray();

		return Base64.encodeToString(bytes, Base64.NO_WRAP);
	}

	private Bitmap toBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		int width = drawable.getIntrinsicWidth();
		width = width > 0 ? width : 1;
		int height = drawable.getIntrinsicHeight();
		height = height > 0 ? height : 1;

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

	private Bitmap decodeResource(Context context, int resId) {
		return BitmapFactory.decodeResource(context.getResources(), resId);
	}

	private static class JsInterface {
		private HashSet<OnJsCallbackListener> onJsCallbackListenerSet;

		public JsInterface() {
			onJsCallbackListenerSet = new HashSet<OnJsCallbackListener>();
		}

		public void addOnJsCallbackListener(OnJsCallbackListener listener) {
			if (listener != null && !onJsCallbackListenerSet.contains(listener)) {
				onJsCallbackListenerSet.add(listener);
			}
		}

		@JavascriptInterface
		public void onGotImages(String[] imgList) {
			for (OnJsCallbackListener item : onJsCallbackListenerSet) {
				if (item != null) {
					item.onGotImages(imgList);
				}
			}
			onJsCallbackListenerSet.clear();
		}
	}

	public interface OnJsCallbackListener {
		void onGotImages(String[] imgList);
	}
}

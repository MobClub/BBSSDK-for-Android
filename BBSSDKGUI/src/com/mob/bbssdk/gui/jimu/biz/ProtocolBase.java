package com.mob.bbssdk.gui.jimu.biz;

import android.util.Base64;

import com.mob.MobSDK;
import com.mob.commons.MobProduct;
import com.mob.commons.MobProductCollector;
import com.mob.tools.MobLog;
import com.mob.tools.RxMob;
import com.mob.tools.RxMob.QuickSubscribe;
import com.mob.tools.RxMob.Subscribable;
import com.mob.tools.RxMob.Subscriber;
import com.mob.tools.network.HttpConnection;
import com.mob.tools.network.HttpResponseCallback;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import com.mob.tools.network.StringPart;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.MobRSA;
import com.mob.tools.utils.ResHelper;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ProtocolBase {
	public static final int HTTP_METHOD_GET = 1;
	public static final int HTTP_METHOD_POST = 2;

	private static final BigInteger PUB_KEY = new BigInteger(
			"e0d237467a7a5e99683e3a7119890e6cd70519849a360d1f54be2f757ca725126"
			+ "8503a6d569da448502166827b4cef7979e52a7e27731d6357b12c78e8f11ac9", 16);
	private static final BigInteger MODULUS = new BigInteger(
			"37094facc7f90779691dede0592870622c22d4ea1951a1258c22d8a4038cdf4e2"
			+ "69fd594c6142bc45e74fb30275a3a0b59a5375ac8700c95c75203c79993e3d6"
			+ "326991c5253a11b3296789550c878265a1f8148137d98cd6175ca410224f4c6"
			+ "40d4588ce3cfdffdbf345ae764fda80d62880a818c0ab7b418969f426d5a9ac41", 16);

	private static final Random RND = new Random();
	protected static final Hashon HASHON = new Hashon();
	private static final NetworkHelper NETWORK = new NetworkHelper();

	private String errProfix;
	protected DeviceHelper deivce;

	public ProtocolBase(String errProfix) {
		this.errProfix = errProfix;
		deivce = DeviceHelper.getInstance(MobSDK.getContext());
	}

	@SuppressWarnings("unchecked")
	public final String query(String json, String view) throws Throwable {
		HashMap<String, Object> response = httpRequest(view, json, false, HTTP_METHOD_POST);
		Object res = processResponse(response);
		if (res == null) {
			return null;
		} else if (res instanceof ArrayList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("res", res);
			return HASHON.fromHashMap(map);
		} else if (res instanceof HashMap) {
			return HASHON.fromHashMap((HashMap<String, Object>) res);
		} else {
			return String.valueOf(res);
		}
	}

	protected final void executeJobThread(String name, String url, HashMap<String, Object> request,
			BizCallback callback) {
		executeJobThread(name, url, request, callback, false, HTTP_METHOD_POST);
	}

	protected final void executeJobThread(String name, String url,
			HashMap<String, Object> request, boolean compress, BizCallback callback) {
		executeJobThread(name, url, request, callback, compress, HTTP_METHOD_POST);
	}

	protected final void executeJobThread(final String name, final String url, final HashMap<String, Object> request,
			final BizCallback callback, final boolean compress, final int httpMethod) {
		Subscribable<Object> sub = RxMob.create(new QuickSubscribe<Object>() {
			protected void doNext(Subscriber<Object> subscriber) throws Throwable {
				subscriber.onNext(executeSynchronized(url, request, compress, httpMethod));
			}
		});
		sub.subscribeOnNewThreadAndObserveOnUIThread(new Subscriber<Object>() {
			public void onNext(Object data) {
				if (callback != null) {
					callback.deliverOK(data);
				}
			}

			public void onError(Throwable t) {
				if (callback == null) {
					MobLog.getInstance().d("=====================");
					MobLog.getInstance().d("throwResult catches error but callback param is null");
					MobLog.getInstance().d(t);
					MobLog.getInstance().d("=====================");
				} else {
					callback.deliverError(t);
				}
			}
		});
	}

	protected Object executeSynchronized(String url, HashMap<String, Object> request) throws Throwable {
		return executeSynchronized(url, request, false, HTTP_METHOD_POST);
	}

	protected Object executeSynchronized(String url, HashMap<String, Object> request,  boolean compress,
			int httpMethod) throws Throwable {
		return processResponse(httpRequest(url, request, compress, httpMethod));
	}

	private Object processResponse(HashMap<String, Object> response) throws Throwable {
		if (response == null || response.isEmpty()) {
			String errDetails = getErrorDetail("n1");
			if (errDetails == null) {
				throw new Throwable("(error: -1)");
			} else {
				throw new Throwable(errDetails + " (error: -1)");
			}
		} else if (response.containsKey("httpStatus")) {
			String status = String.valueOf(response.get("status"));
			String errDetails = getErrorDetail(status);
			if (errDetails == null) {
				if (response.containsKey("error")) {
					throw new Throwable(String.valueOf(response.get("error")) + " (error: " + status + ")");
				} else {
					throw new Throwable("(error: " + status + ")");
				}
			} else {
				throw new Throwable(errDetails + " (error: " + status + ")");
			}
		} else {
			return response.get("res");
		}
	}

	private String getErrorDetail(String code) {
		String resName = "jimu_error_" + code;
		int resId = ResHelper.getStringRes(MobSDK.getContext(), resName);
		if (resId <= 0 && errProfix != null) {
			resName = errProfix + "_error_" + code;
			resId = ResHelper.getStringRes(MobSDK.getContext(), resName);
		}

		if (resId > 0) {
			return MobSDK.getContext().getString(resId);
		}
		return null;
	}

	private HashMap<String, Object> httpRequest(String url, HashMap<String, Object> request, boolean compress,
			int httpMethod) throws Throwable {
		// 客户端将请求体格式化为json文本
		String reqStr;
		if (request == null) {
			reqStr = "";
		} else {
			reqStr = HASHON.fromHashMap(request);
		}
		return httpRequest(url, reqStr, compress, httpMethod);
	}

	private HashMap<String, Object> httpRequest(String url, String reqStr, boolean compress, int httpMethod)
			throws Throwable {
		ArrayList<KVPair<String>> headers = getHeaders(reqStr);
		MobLog.getInstance().d(">>>  request: " + reqStr + "\nurl = " + url + "\nheader = " + headers.toString());

		NetworkTimeOut timeout = new NetworkTimeOut();
		timeout.readTimout = 30000;
		timeout.connectionTimeout = 5000;
		StringBuilder sb = new StringBuilder();
		byte[] aesKey = genAESKey();
		HttpResponseCallback callback = newCallback(sb, aesKey);
		if (httpMethod == HTTP_METHOD_GET) {
			NETWORK.rawGet(url, headers, callback, timeout);
		} else {
			StringPart sp = new StringPart();
			sp.append(encryptRequest(reqStr, aesKey, compress));
			headers.add(new KVPair<String>("Content-Length", String.valueOf(sp.toString().getBytes("utf-8").length)));
			NETWORK.rawPost(url, headers, sp, -1, callback, timeout);
		}
		String resp = sb.toString().trim();
		MobLog.getInstance().d(">>> response: " + resp);
		return HASHON.fromJson(resp);
	}

	private HttpResponseCallback newCallback(final StringBuilder sb, final byte[] aesKey) {
		return new HttpResponseCallback() {
			public void onResponse(HttpConnection conn) throws Throwable {
				long contentLength = getContentLength(conn);
				if (contentLength == -1) {
					String errDetails = getErrorDetail("n2");
					if (errDetails == null) {
						throw new Throwable("(error: -2)");
					} else {
						throw new Throwable(errDetails + " (error: -2)");
					}
				}

				int code = conn.getResponseCode();
				InputStream is = (code == 200) ? conn.getInputStream() : conn.getErrorStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int len = is.read(buf);
				while (len != -1) {
					baos.write(buf, 0, len);
					len = is.read(buf);
				}
				is.close();
				baos.close();
				if (baos.size() != contentLength) {
					String errDetails = getErrorDetail("n2");
					if (errDetails == null) {
						throw new Throwable("(error: -2)");
					} else {
						throw new Throwable(errDetails + " (error: -2)");
					}
				}

				if (code == 200) {
					byte[] enRes = baos.toByteArray();
					if (aesKey != null) {
						enRes = Base64.decode(enRes, Base64.NO_WRAP);
					}
					if (isZippedResp(conn)) {
						enRes = decompress(enRes);
					}
					byte[] deRes;
					if (aesKey != null) {
						deRes = Data.AES128Decode(aesKey, enRes);
					} else {
						deRes = enRes;
					}
					sb.delete(0, sb.length());
					sb.append(new String(deRes, "utf-8"));
				} else {
					HashMap<String, Object> errMap = HASHON.fromJson(new String(baos.toByteArray(), "utf-8"));
					errMap.put("httpStatus", code);
					sb.delete(0, sb.length());
					sb.append(HASHON.fromHashMap(errMap));
				}
			}
		};
	}

	private byte[] genAESKey() throws Throwable {
		// 客户端随机生成16个字符的AES秘钥（仅包含数字和字母）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeLong(RND.nextLong());
		dos.writeLong(RND.nextLong());
		dos.close();
		return baos.toByteArray();
	}

	private ArrayList<KVPair<String>> getHeaders(String reqStr) throws Throwable {
		return getHeaders(reqStr.getBytes("utf-8"));
	}

	private ArrayList<KVPair<String>> getHeaders(byte[] reqBody) throws Throwable {
		ArrayList<KVPair<String>> headers = new ArrayList<KVPair<String>>();

		// 对明文请求体json文本 拼接appsecret 做一次md5的指纹计算，并保存为http头字段：sign
		// 将图片byte字节数组与appsecret的字节数组(appsecret.getBytes(UTF-8))拼接, 做一次md5的指纹计算，并保存为http头字段：sign
		byte[] bSecret = MobSDK.getAppSecret().getBytes("utf-8");
		byte[] btmp = new byte[reqBody.length + bSecret.length];
		System.arraycopy(reqBody, 0, btmp, 0, reqBody.length);
		System.arraycopy(bSecret, 0, btmp, reqBody.length, bSecret.length);
		String sign = Data.MD5(btmp);
		headers.add(new KVPair<String>("sign", sign));
		headers.add(new KVPair<String>("key", MobSDK.getAppkey()));

		// User-Identity 格式 "[APPPKG]/[APPVER]  ([SDK_TYPE]/[SDK_VERSION])+   [SYSTEM_NAME]/[SYSTEM_VERSION] [TIME_ZONE] Lang/[LANG]"
		// 参见：http://c.mob.com/pages/viewpage.action?pageId=4232327
		ArrayList<MobProduct> products = MobProductCollector.getProducts();
		String userIdentity = MobProductCollector.getUserIdentity(products);
		headers.add(new KVPair<String>("User-Identity", userIdentity));

		return headers;
	}

	private long getContentLength(HttpConnection conn) throws Throwable {
		long length = -1;
		List<String> list = getHeader(conn, "Content-Length");
		if (list != null && list.size() > 0) {
			length = Long.parseLong(list.get(0));
		}
		return length;
	}

	private List<String> getHeader(HttpConnection conn, String header) throws Throwable {
		Map<String, List<String>> headers = conn.getHeaderFields();
		if (headers != null && !headers.isEmpty()) {
			for (String key : headers.keySet()) {
				if (key != null && key.equals(header)) {
					return headers.get(key);
				}
			}
		}
		return null;
	}

	private boolean isZippedResp(HttpConnection conn) throws Throwable {
		boolean zipped = false;
		List<String> list = getHeader(conn, "zip");
		if (list != null && list.size() > 0) {
			zipped = "1".equals(list.get(0));
		}
		return zipped;
	}

	private byte[] decompress(byte[] data) throws Throwable {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		GZIPInputStream gzis = new GZIPInputStream(bais);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len = gzis.read(buf);
		while (len > 0) {
			baos.write(buf, 0, len);
			len = gzis.read(buf);
		}
		baos.close();
		gzis.close();
		return baos.toByteArray();
	}

	private String encryptRequest(String reqStr, byte[] aesKey, boolean compress) throws Throwable {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		byte[] eKey = encryptAESKey(aesKey);
		dos.writeInt(eKey.length);
		dos.write(eKey);
		// 请求体json文本加密，如果需要压缩，则先对文本压缩后再加密
		// 使用随机生成的AES秘钥加密上传数据
		byte[] bReq;
		if (compress) {
			byte[] cReq = compress(reqStr);
			bReq = Data.AES128Encode(aesKey, cReq);
		} else {
			bReq = Data.AES128Encode(aesKey, reqStr);
		}
		dos.writeInt(bReq.length);
		dos.write(bReq);
		dos.close();
		// 使用post方式发送bae64文本
		// Base64(秘钥密文长度int4字节 + 秘钥密文byte[] + 数据密文长度int4字节 + 数据密文byte[])
		return Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
	}

	private byte[] encryptAESKey(byte[] aesKey) throws Throwable {
		// 客户端使用服务端给的公钥通过RSA（1024）加密秘钥
		MobRSA rsa = new MobRSA(1024);
		return rsa.encode(aesKey, PUB_KEY, MODULUS);
	}

	private byte[] compress(String reqStr) throws Throwable {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gzos = new GZIPOutputStream(baos);
		BufferedOutputStream bos = new BufferedOutputStream(gzos);
		bos.write(reqStr.getBytes("utf-8"));
		bos.flush();
		bos.close();
		return baos.toByteArray();
	}

	public Object uploadFile(String url, HashMap<String, Object> request, String path) throws Throwable {
		byte[] rawFile = readFile(path);
		ArrayList<KVPair<String>> headers = getHeaders(rawFile);
		MobLog.getInstance().d(">>>  file: " + path + "\nurl = " + url + "\nheader = " + headers.toString());

		NetworkTimeOut timeout = new NetworkTimeOut();
		timeout.readTimout = 30000;
		timeout.connectionTimeout = 5000;
		ArrayList<KVPair<String>> values = new ArrayList<KVPair<String>>();
		for (String key : request.keySet()) {
			values.add(new KVPair<String>(key, String.valueOf(request.get(key))));
		}
		ArrayList<KVPair<String>> files = new ArrayList<KVPair<String>>();
		files.add(new KVPair<String>("file", path));
		StringBuilder sb = new StringBuilder();
		HttpResponseCallback callback = newCallback(sb, null);
		NETWORK.httpPost(url, values, files, headers, callback, timeout);
		String resp = sb.toString().trim();
		MobLog.getInstance().d(">>> response: " + resp);
		return processResponse(HASHON.fromJson(resp));
	}

	private byte[] readFile(String path) throws Throwable {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis = new FileInputStream(path);
		byte[] buf = new byte[1024];
		int len = fis.read(buf);
		while (len != -1) {
			baos.write(buf, 0, len);
			len = fis.read(buf);
		}
		fis.close();
		baos.close();
		return baos.toByteArray();
	}

}

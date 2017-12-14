package com.mob.bbssdk.gui.utils;

import com.mob.tools.utils.ReflectHelper;

import java.io.PrintStream;

/**
 * Created by xuan on 06/12/2017.
 */

public class LogUtils {
	private static PrintStream systemPrintStream;

	static {
		try {
			systemPrintStream = ReflectHelper.getStaticField("System", "out");
		} catch (Throwable throwable) {
		}
	}

	public static void println(String str) {
		if (systemPrintStream != null) {
			systemPrintStream.println(str);
		}
	}

	public static void println(String tag, String msg) {
		if (systemPrintStream != null) {
			systemPrintStream.println(tag + "\t" + msg);
		}
	}
}

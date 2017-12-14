package com.mob.bbssdk.gui.jimu.query.data;

/** 数值类型 */
public class Number extends Rangable<java.lang.Number> {

	public Number(java.lang.Number value) {
		super(value);
	}

	public static Number valueOf(java.lang.Number value) {
		return new Number(value);
	}

	public static Number valueOf(byte value) {
		return new Number(value);
	}

	public static Number valueOf(short value) {
		return new Number(value);
	}

	public static Number valueOf(int value) {
		return new Number(value);
	}

	public static Number valueOf(long value) {
		return new Number(value);
	}

	public static Number valueOf(float value) {
		return new Number(value);
	}

	public static Number valueOf(double value) {
		return new Number(value);
	}

	public static Number[] valueOf(byte... values) {
		Number[] ret = new Number[values.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new Number(values[i]);
		}
		return ret;
	}

	public static Number[] valueOf(short... values) {
		Number[] ret = new Number[values.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new Number(values[i]);
		}
		return ret;
	}

	public static Number[] valueOf(int... values) {
		Number[] ret = new Number[values.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new Number(values[i]);
		}
		return ret;
	}

	public static Number[] valueOf(long... values) {
		Number[] ret = new Number[values.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new Number(values[i]);
		}
		return ret;
	}

	public static Number[] valueOf(float... values) {
		Number[] ret = new Number[values.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new Number(values[i]);
		}
		return ret;
	}

	public static Number[] valueOf(double... values) {
		Number[] ret = new Number[values.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new Number(values[i]);
		}
		return ret;
	}

	public static Number[] valueOf(java.lang.Number... values) {
		Number[] ret = new Number[values.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new Number(values[i]);
		}
		return ret;
	}
}

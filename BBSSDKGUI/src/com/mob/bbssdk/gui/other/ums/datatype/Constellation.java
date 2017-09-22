package com.mob.bbssdk.gui.other.ums.datatype;

public abstract class Constellation implements EnumType {

	public static Constellation[] values() {
		return new Constellation[] {
			Aries.INSTANCE,
			Taurus.INSTANCE,
			Gemini.INSTANCE,
			Cancer.INSTANCE,
			Leo.INSTANCE,
			Virgo.INSTANCE,
			Libra.INSTANCE,
			Scorpio.INSTANCE,
			Sagittarius.INSTANCE,
			Capricorn.INSTANCE,
			Aquarius.INSTANCE,
			Pisces.INSTANCE
		};
	}

	public static Constellation valueOf(int code) {
		for (Constellation item : values()) {
			if (item.code() == code) {
				return item;
			}
		}
		return null;
	}

	public static final class Capricorn extends Constellation {
		public static final int CODE = 10;
		public static final String RES_NAME = "umssdk_constellation_capricorn";
		public static final Capricorn INSTANCE = new Capricorn();

		private Capricorn() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Libra extends Constellation {
		public static final int CODE = 7;
		public static final String RES_NAME = "umssdk_constellation_libra";
		public static final Libra INSTANCE = new Libra();

		private Libra() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Scorpio extends Constellation {
		public static final int CODE = 8;
		public static final String RES_NAME = "umssdk_constellation_scorpio";
		public static final Scorpio INSTANCE = new Scorpio();

		private Scorpio() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Pisces extends Constellation {
		public static final int CODE = 12;
		public static final String RES_NAME = "umssdk_constellation_pisces";
		public static final Pisces INSTANCE = new Pisces();

		private Pisces() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Leo extends Constellation {
		public static final int CODE = 5;
		public static final String RES_NAME = "umssdk_constellation_leo";
		public static final Leo INSTANCE = new Leo();

		private Leo() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Sagittarius extends Constellation {
		public static final int CODE = 9;
		public static final String RES_NAME = "umssdk_constellation_sagittarius";
		public static final Sagittarius INSTANCE = new Sagittarius();

		private Sagittarius() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Virgo extends Constellation {
		public static final int CODE = 6;
		public static final String RES_NAME = "umssdk_constellation_virgo";
		public static final Virgo INSTANCE = new Virgo();

		private Virgo() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Taurus extends Constellation {
		public static final int CODE = 2;
		public static final String RES_NAME = "umssdk_constellation_taurus";
		public static final Taurus INSTANCE = new Taurus();

		private Taurus() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Aquarius extends Constellation {
		public static final int CODE = 11;
		public static final String RES_NAME = "umssdk_constellation_aquarius";
		public static final Aquarius INSTANCE = new Aquarius();

		private Aquarius() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Aries extends Constellation {
		public static final int CODE = 1;
		public static final String RES_NAME = "umssdk_constellation_aries";
		public static final Aries INSTANCE = new Aries();

		private Aries() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Gemini extends Constellation {
		public static final int CODE = 3;
		public static final String RES_NAME = "umssdk_constellation_gemini";
		public static final Gemini INSTANCE = new Gemini();

		private Gemini() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Cancer extends Constellation {
		public static final int CODE = 4;
		public static final String RES_NAME = "umssdk_constellation_cancer";
		public static final Cancer INSTANCE = new Cancer();

		private Cancer() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

}

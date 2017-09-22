package com.mob.bbssdk.gui.other.ums.datatype;

public abstract class Gender implements EnumType {

	public static Gender[] values() {
		return new Gender[] {
				Secret.INSTANCE,
				Male.INSTANCE,
				Female.INSTANCE
		};
	}

	public static Gender valueOf(int code) {
		for (Gender item : values()) {
			if (item.code() == code) {
				return item;
			}
		}
		return null;
	}

	public static final class Secret extends Gender {
		public static final int CODE = 0;
		public static final String RES_NAME = "umssdk_gender_secret";
		public static final Secret INSTANCE = new Secret();

		private Secret() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Male extends Gender {
		public static final int CODE = 1;
		public static final String RES_NAME = "umssdk_gender_male";
		public static final Male INSTANCE = new Male();

		private Male() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Female extends Gender {
		public static final int CODE = 2;
		public static final String RES_NAME = "umssdk_gender_female";
		public static final Female INSTANCE = new Female();

		private Female() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

}

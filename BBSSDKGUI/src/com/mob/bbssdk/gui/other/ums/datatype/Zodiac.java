package com.mob.bbssdk.gui.other.ums.datatype;

public abstract class Zodiac implements EnumType {

	public static Zodiac[] values() {
		return new Zodiac[] {
			Rat.INSTANCE,
			Ox.INSTANCE,
			Tiger.INSTANCE,
			Rabbit.INSTANCE,
			Dragon.INSTANCE,
			Snake.INSTANCE,
			Horse.INSTANCE,
			Goat.INSTANCE,
			Monkey.INSTANCE,
			Rooster.INSTANCE,
			Dog.INSTANCE,
			Pig.INSTANCE
		};
	}

	public static Zodiac valueOf(int code) {
		for (Zodiac item : values()) {
			if (item.code() == code) {
				return item;
			}
		}
		return null;
	}

	public static final class Rat extends Zodiac {
		public static final int CODE = 1;
		public static final String RES_NAME = "umssdk_zodiac_rat";
		public static final Rat INSTANCE = new Rat();

		private Rat() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Snake extends Zodiac {
		public static final int CODE = 6;
		public static final String RES_NAME = "umssdk_zodiac_snake";
		public static final Snake INSTANCE = new Snake();

		private Snake() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Horse extends Zodiac {
		public static final int CODE = 7;
		public static final String RES_NAME = "umssdk_zodiac_horse";
		public static final Horse INSTANCE = new Horse();

		private Horse() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Monkey extends Zodiac {
		public static final int CODE = 9;
		public static final String RES_NAME = "umssdk_zodiac_monkey";
		public static final Monkey INSTANCE = new Monkey();

		private Monkey() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Rabbit extends Zodiac {
		public static final int CODE = 4;
		public static final String RES_NAME = "umssdk_zodiac_rabbit";
		public static final Rabbit INSTANCE = new Rabbit();

		private Rabbit() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Ox extends Zodiac {
		public static final int CODE = 2;
		public static final String RES_NAME = "umssdk_zodiac_ox";
		public static final Ox INSTANCE = new Ox();

		private Ox() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Goat extends Zodiac {
		public static final int CODE = 8;
		public static final String RES_NAME = "umssdk_zodiac_goat";
		public static final Goat INSTANCE = new Goat();

		private Goat() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Rooster extends Zodiac {
		public static final int CODE = 10;
		public static final String RES_NAME = "umssdk_zodiac_rooster";
		public static final Rooster INSTANCE = new Rooster();

		private Rooster() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Dog extends Zodiac {
		public static final int CODE = 11;
		public static final String RES_NAME = "umssdk_zodiac_dog";
		public static final Dog INSTANCE = new Dog();

		private Dog() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Tiger extends Zodiac {
		public static final int CODE = 3;
		public static final String RES_NAME = "umssdk_zodiac_tiger";
		public static final Tiger INSTANCE = new Tiger();

		private Tiger() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Dragon extends Zodiac {
		public static final int CODE = 5;
		public static final String RES_NAME = "umssdk_zodiac_dragon";
		public static final Dragon INSTANCE = new Dragon();

		private Dragon() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Pig extends Zodiac {
		public static final int CODE = 12;
		public static final String RES_NAME = "umssdk_zodiac_pig";
		public static final Pig INSTANCE = new Pig();

		private Pig() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

}

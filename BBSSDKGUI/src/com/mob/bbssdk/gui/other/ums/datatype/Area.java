package com.mob.bbssdk.gui.other.ums.datatype;

public abstract class Area implements EnumType {

	public static Country[] values() {
		return new Country[] {
			Albania.INSTANCE,
			Algeria.INSTANCE,
			Andorra.INSTANCE,
			Angola.INSTANCE,
			Anguilla.INSTANCE,
			AntiguaandBarbuda.INSTANCE,
			Armenia.INSTANCE,
			Ascension.INSTANCE,
			Australia.INSTANCE,
			Austria.INSTANCE,
			Azerbaijan.INSTANCE,
			Bahamas.INSTANCE,
			Bahrain.INSTANCE,
			Bangladesh.INSTANCE,
			Barbados.INSTANCE,
			Belarus.INSTANCE,
			Belgium.INSTANCE,
			Belize.INSTANCE,
			Benin.INSTANCE,
			Bermuda.INSTANCE,
			Bolivia.INSTANCE,
			Botswana.INSTANCE,
			Brazil.INSTANCE,
			Brunei.INSTANCE,
			Bulgaria.INSTANCE,
			BurkinaFaso.INSTANCE,
			Myanmar.INSTANCE,
			Burundi.INSTANCE,
			Cameroon.INSTANCE,
			Canada.INSTANCE,
			CaymanIslands.INSTANCE,
			CentralAfricanRepublic.INSTANCE,
			Chad.INSTANCE,
			Chile.INSTANCE,
			China.INSTANCE,
			Columbia.INSTANCE,
			Congo.INSTANCE,
			CookIslands.INSTANCE,
			CostaRica.INSTANCE,
			Cuba.INSTANCE,
			Cyprus.INSTANCE,
			Czech.INSTANCE,
			Denmark.INSTANCE,
			Djibouti.INSTANCE,
			DominicanRepublic.INSTANCE,
			Ecuador.INSTANCE,
			Egypt.INSTANCE,
			Salvatore.INSTANCE,
			Estonia.INSTANCE,
			Ethiopia.INSTANCE,
			Fiji.INSTANCE,
			Finland.INSTANCE,
			France.INSTANCE,
			FrenchGuiana.INSTANCE,
			FrenchPolynesia.INSTANCE,
			Gabon.INSTANCE,
			Gambia.INSTANCE,
			Georgia.INSTANCE,
			Germany.INSTANCE,
			Ghana.INSTANCE,
			Gibraltar.INSTANCE,
			Greece.INSTANCE,
			Grenada.INSTANCE,
			Guam.INSTANCE,
			Guatemala.INSTANCE,
			Guinea.INSTANCE,
			Guyana.INSTANCE,
			Haiti.INSTANCE,
			Honduras.INSTANCE,
			Hungary.INSTANCE,
			Iceland.INSTANCE,
			India.INSTANCE,
			Indonesia.INSTANCE,
			Iran.INSTANCE,
			Iraq.INSTANCE,
			Ireland.INSTANCE,
			Israel.INSTANCE,
			Italy.INSTANCE,
			CotedIvoire.INSTANCE,
			Jamaica.INSTANCE,
			Japan.INSTANCE,
			Jordan.INSTANCE,
			Cambodia.INSTANCE,
			Kazakhstan.INSTANCE,
			Kenya.INSTANCE,
			Korea.INSTANCE,
			Kuwait.INSTANCE,
			Kyrgyzstan.INSTANCE,
			Laos.INSTANCE,
			Latvia.INSTANCE,
			Lebanon.INSTANCE,
			Lesotho.INSTANCE,
			Liberia.INSTANCE,
			Libya.INSTANCE,
			Liechtenstein.INSTANCE,
			Lithuania.INSTANCE,
			Luxembourg.INSTANCE,
			Madagascar.INSTANCE,
			Malawi.INSTANCE,
			Malaysia.INSTANCE,
			Maldives.INSTANCE,
			Mali.INSTANCE,
			Malta.INSTANCE,
			MarianaIslands.INSTANCE,
			Martini.INSTANCE,
			Mauritius.INSTANCE,
			Mexico.INSTANCE,
			Moldova.INSTANCE,
			Monaco.INSTANCE,
			Mongolia.INSTANCE,
			MontserratIsland.INSTANCE,
			Morocco.INSTANCE,
			Mozambique.INSTANCE,
			Namibia.INSTANCE,
			Nauru.INSTANCE,
			Nepal.INSTANCE,
			NetherlandsAntilles.INSTANCE,
			Netherlands.INSTANCE,
			NewZealand.INSTANCE,
			Nicaragua.INSTANCE,
			Niger.INSTANCE,
			Nigeria.INSTANCE,
			NorthKorea.INSTANCE,
			Norway.INSTANCE,
			Oman.INSTANCE,
			Pakistan.INSTANCE,
			Panama.INSTANCE,
			PapuaNewGuinea.INSTANCE,
			Paraguay.INSTANCE,
			Peru.INSTANCE,
			Philippines.INSTANCE,
			Poland.INSTANCE,
			Portugal.INSTANCE,
			PuertoRico.INSTANCE,
			Qatar.INSTANCE,
			Reunion.INSTANCE,
			Romania.INSTANCE,
			Russia.INSTANCE,
			SaintLucia.INSTANCE,
			StVincentIsland.INSTANCE,
			EastSamoaUS.INSTANCE,
			WestSamoa.INSTANCE,
			SanMarino.INSTANCE,
			SaoTomeandPrincipe.INSTANCE,
			SaudiArabia.INSTANCE,
			Senegal.INSTANCE,
			Seychelles.INSTANCE,
			SierraLeone.INSTANCE,
			Singapore.INSTANCE,
			Slovakia.INSTANCE,
			Slovenia.INSTANCE,
			SolomonIslands.INSTANCE,
			Somalia.INSTANCE,
			SouthAfrica.INSTANCE,
			Spain.INSTANCE,
			SriLanka.INSTANCE,
			StVincent.INSTANCE,
			Sudan.INSTANCE,
			Suriname.INSTANCE,
			Swaziland.INSTANCE,
			Sweden.INSTANCE,
			Switzerland.INSTANCE,
			Syria.INSTANCE,
			Tajikistan.INSTANCE,
			Tanzania.INSTANCE,
			Thailand.INSTANCE,
			Togo.INSTANCE,
			Tonga.INSTANCE,
			TrinidadandTobago.INSTANCE,
			Tunisia.INSTANCE,
			Turkey.INSTANCE,
			Turkmenistan.INSTANCE,
			Uganda.INSTANCE,
			Ukraine.INSTANCE,
			UnitedArabEmirates.INSTANCE,
			UnitedKingdom.INSTANCE,
			USA.INSTANCE,
			Uruguay.INSTANCE,
			Uzbekistan.INSTANCE,
			Venezuela.INSTANCE,
			Vietnam.INSTANCE,
			Yemen.INSTANCE,
			Yugoslavia.INSTANCE,
			Zaire.INSTANCE,
			Zambia.INSTANCE,
			Zimbabwe.INSTANCE
		};
	}

	public static Country valueOf(int code) {
		for (Country item : values()) {
			if (item.code() == code) {
				return item;
			}
		}
		return null;
	}

	public static final class Benin extends Country {
		public static final int CODE = 21;
		public static final String RES_NAME = "umssdk_area_benin";
		public static final Benin INSTANCE = new Benin();

		private Benin() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Angola extends Country {
		public static final int CODE = 5;
		public static final String RES_NAME = "umssdk_area_angola";
		public static final Angola INSTANCE = new Angola();

		private Angola() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Cambodia extends Country {
		public static final int CODE = 86;
		public static final String RES_NAME = "umssdk_area_cambodia";
		public static final Cambodia INSTANCE = new Cambodia();

		private Cambodia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class CentralAfricanRepublic extends Country {
		public static final int CODE = 34;
		public static final String RES_NAME = "umssdk_area_centralafricanrepublic";
		public static final CentralAfricanRepublic INSTANCE = new CentralAfricanRepublic();

		private CentralAfricanRepublic() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Sudan extends Country {
		public static final int CODE = 163;
		public static final String RES_NAME = "umssdk_area_sudan";
		public static final Sudan INSTANCE = new Sudan();

		private Sudan() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Kazakhstan extends Country {
		public static final int CODE = 87;
		public static final String RES_NAME = "umssdk_area_kazakhstan";
		public static final Kazakhstan INSTANCE = new Kazakhstan();

		private Kazakhstan() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Paraguay extends Country {
		public static final int CODE = 133;
		public static final String RES_NAME = "umssdk_area_paraguay";
		public static final Paraguay INSTANCE = new Paraguay();

		private Paraguay() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Portugal extends Country {
		public static final int CODE = 137;
		public static final String RES_NAME = "umssdk_area_portugal";
		public static final Portugal INSTANCE = new Portugal();

		private Portugal() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Syria extends Country {
		public static final int CODE = 168;
		public static final String RES_NAME = "umssdk_area_syria";
		public static final Syria INSTANCE = new Syria();

		private Syria() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Bahamas extends Country {
		public static final int CODE = 14;
		public static final String RES_NAME = "umssdk_area_bahamas";
		public static final Bahamas INSTANCE = new Bahamas();

		private Bahamas() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Grenada extends Country {
		public static final int CODE = 65;
		public static final String RES_NAME = "umssdk_area_grenada";
		public static final Grenada INSTANCE = new Grenada();

		private Grenada() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class SaintLucia extends Country {
		public static final int CODE = 143;
		public static final String RES_NAME = "umssdk_area_saintlucia";
		public static final SaintLucia INSTANCE = new SaintLucia();

		private SaintLucia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Greece extends Country {
		public static final int CODE = 64;
		public static final String RES_NAME = "umssdk_area_greece";
		public static final Greece INSTANCE = new Greece();

		private Greece() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Latvia extends Country {
		public static final int CODE = 93;
		public static final String RES_NAME = "umssdk_area_latvia";
		public static final Latvia INSTANCE = new Latvia();

		private Latvia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Mongolia extends Country {
		public static final int CODE = 114;
		public static final String RES_NAME = "umssdk_area_mongolia";
		public static final Mongolia INSTANCE = new Mongolia();

		private Mongolia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Iran extends Country {
		public static final int CODE = 77;
		public static final String RES_NAME = "umssdk_area_iran";
		public static final Iran INSTANCE = new Iran();

		private Iran() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Morocco extends Country {
		public static final int CODE = 116;
		public static final String RES_NAME = "umssdk_area_morocco";
		public static final Morocco INSTANCE = new Morocco();

		private Morocco() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class SriLanka extends Country {
		public static final int CODE = 160;
		public static final String RES_NAME = "umssdk_area_srilanka";
		public static final SriLanka INSTANCE = new SriLanka();

		private SriLanka() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Mali extends Country {
		public static final int CODE = 106;
		public static final String RES_NAME = "umssdk_area_mali";
		public static final Mali INSTANCE = new Mali();

		private Mali() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class SouthAfrica extends Country {
		public static final int CODE = 158;
		public static final String RES_NAME = "umssdk_area_southafrica";
		public static final SouthAfrica INSTANCE = new SouthAfrica();

		private SouthAfrica() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Panama extends Country {
		public static final int CODE = 131;
		public static final String RES_NAME = "umssdk_area_panama";
		public static final Panama INSTANCE = new Panama();

		private Panama() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Guatemala extends Country {
		public static final int CODE = 67;
		public static final String RES_NAME = "umssdk_area_guatemala";
		public static final Guatemala INSTANCE = new Guatemala();

		private Guatemala() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Guyana extends Country {
		public static final int CODE = 69;
		public static final String RES_NAME = "umssdk_area_guyana";
		public static final Guyana INSTANCE = new Guyana();

		private Guyana() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class SolomonIslands extends Country {
		public static final int CODE = 156;
		public static final String RES_NAME = "umssdk_area_solomonislands";
		public static final SolomonIslands INSTANCE = new SolomonIslands();

		private SolomonIslands() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Iraq extends Country {
		public static final int CODE = 78;
		public static final String RES_NAME = "umssdk_area_iraq";
		public static final Iraq INSTANCE = new Iraq();

		private Iraq() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Chile extends Country {
		public static final int CODE = 36;
		public static final String RES_NAME = "umssdk_area_chile";
		public static final Chile INSTANCE = new Chile();

		private Chile() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Laos extends Country {
		public static final int CODE = 92;
		public static final String RES_NAME = "umssdk_area_laos";
		public static final Laos INSTANCE = new Laos();

		private Laos() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Nepal extends Country {
		public static final int CODE = 120;
		public static final String RES_NAME = "umssdk_area_nepal";
		public static final Nepal INSTANCE = new Nepal();

		private Nepal() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class FrenchPolynesia extends Country {
		public static final int CODE = 57;
		public static final String RES_NAME = "umssdk_area_frenchpolynesia";
		public static final FrenchPolynesia INSTANCE = new FrenchPolynesia();

		private FrenchPolynesia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class FrenchGuiana extends Country {
		public static final int CODE = 56;
		public static final String RES_NAME = "umssdk_area_frenchguiana";
		public static final FrenchGuiana INSTANCE = new FrenchGuiana();

		private FrenchGuiana() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Seychelles extends Country {
		public static final int CODE = 151;
		public static final String RES_NAME = "umssdk_area_seychelles";
		public static final Seychelles INSTANCE = new Seychelles();

		private Seychelles() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Tanzania extends Country {
		public static final int CODE = 171;
		public static final String RES_NAME = "umssdk_area_tanzania";
		public static final Tanzania INSTANCE = new Tanzania();

		private Tanzania() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Ukraine extends Country {
		public static final int CODE = 180;
		public static final String RES_NAME = "umssdk_area_ukraine";
		public static final Ukraine INSTANCE = new Ukraine();

		private Ukraine() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class UnitedArabEmirates extends Country {
		public static final int CODE = 181;
		public static final String RES_NAME = "umssdk_area_unitedarabemirates";
		public static final UnitedArabEmirates INSTANCE = new UnitedArabEmirates();

		private UnitedArabEmirates() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Belize extends Country {
		public static final int CODE = 20;
		public static final String RES_NAME = "umssdk_area_belize";
		public static final Belize INSTANCE = new Belize();

		private Belize() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Ghana extends Country {
		public static final int CODE = 62;
		public static final String RES_NAME = "umssdk_area_ghana";
		public static final Ghana INSTANCE = new Ghana();

		private Ghana() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Zambia extends Country {
		public static final int CODE = 191;
		public static final String RES_NAME = "umssdk_area_zambia";
		public static final Zambia INSTANCE = new Zambia();

		private Zambia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Anguilla extends Country {
		public static final int CODE = 6;
		public static final String RES_NAME = "umssdk_area_anguilla";
		public static final Anguilla INSTANCE = new Anguilla();

		private Anguilla() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Bahrain extends Country {
		public static final int CODE = 15;
		public static final String RES_NAME = "umssdk_area_bahrain";
		public static final Bahrain INSTANCE = new Bahrain();

		private Bahrain() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Congo extends Country {
		public static final int CODE = 39;
		public static final String RES_NAME = "umssdk_area_congo";
		public static final Congo INSTANCE = new Congo();

		private Congo() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class India extends Country {
		public static final int CODE = 75;
		public static final String RES_NAME = "umssdk_area_india";
		public static final India INSTANCE = new India();

		private India() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Canada extends Country {
		public static final int CODE = 32;
		public static final String RES_NAME = "umssdk_area_canada";
		public static final Canada INSTANCE = new Canada();

		private Canada() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Maldives extends Country {
		public static final int CODE = 105;
		public static final String RES_NAME = "umssdk_area_maldives";
		public static final Maldives INSTANCE = new Maldives();

		private Maldives() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Turkey extends Country {
		public static final int CODE = 177;
		public static final String RES_NAME = "umssdk_area_turkey";
		public static final Turkey INSTANCE = new Turkey();

		private Turkey() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Belgium extends Country {
		public static final int CODE = 19;
		public static final String RES_NAME = "umssdk_area_belgium";
		public static final Belgium INSTANCE = new Belgium();

		private Belgium() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Namibia extends Country {
		public static final int CODE = 118;
		public static final String RES_NAME = "umssdk_area_namibia";
		public static final Namibia INSTANCE = new Namibia();

		private Namibia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class UnitedKingdom extends Country {
		public static final int CODE = 182;
		public static final String RES_NAME = "umssdk_area_unitedkingdom";
		public static final UnitedKingdom INSTANCE = new UnitedKingdom();

		private UnitedKingdom() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Finland extends Country {
		public static final int CODE = 54;
		public static final String RES_NAME = "umssdk_area_finland";
		public static final Finland INSTANCE = new Finland();

		private Finland() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Bermuda extends Country {
		public static final int CODE = 22;
		public static final String RES_NAME = "umssdk_area_bermuda";
		public static final Bermuda INSTANCE = new Bermuda();

		private Bermuda() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Georgia extends Country {
		public static final int CODE = 60;
		public static final String RES_NAME = "umssdk_area_georgia";
		public static final Georgia INSTANCE = new Georgia();

		private Georgia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Jamaica extends Country {
		public static final int CODE = 83;
		public static final String RES_NAME = "umssdk_area_jamaica";
		public static final Jamaica INSTANCE = new Jamaica();

		private Jamaica() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Peru extends Country {
		public static final int CODE = 134;
		public static final String RES_NAME = "umssdk_area_peru";
		public static final Peru INSTANCE = new Peru();

		private Peru() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Turkmenistan extends Country {
		public static final int CODE = 178;
		public static final String RES_NAME = "umssdk_area_turkmenistan";
		public static final Turkmenistan INSTANCE = new Turkmenistan();

		private Turkmenistan() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Germany extends Country {
		public static final int CODE = 61;
		public static final String RES_NAME = "umssdk_area_germany";
		public static final Germany INSTANCE = new Germany();

		private Germany() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class NewZealand extends Country {
		public static final int CODE = 123;
		public static final String RES_NAME = "umssdk_area_newzealand";
		public static final NewZealand INSTANCE = new NewZealand();

		private NewZealand() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Yemen extends Country {
		public static final int CODE = 188;
		public static final String RES_NAME = "umssdk_area_yemen";
		public static final Yemen INSTANCE = new Yemen();

		private Yemen() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Ascension extends Country {
		public static final int CODE = 10;
		public static final String RES_NAME = "umssdk_area_ascension";
		public static final Ascension INSTANCE = new Ascension();

		private Ascension() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Fiji extends Country {
		public static final int CODE = 53;
		public static final String RES_NAME = "umssdk_area_fiji";
		public static final Fiji INSTANCE = new Fiji();

		private Fiji() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Guinea extends Country {
		public static final int CODE = 68;
		public static final String RES_NAME = "umssdk_area_guinea";
		public static final Guinea INSTANCE = new Guinea();

		private Guinea() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Chad extends Country {
		public static final int CODE = 35;
		public static final String RES_NAME = "umssdk_area_chad";
		public static final Chad INSTANCE = new Chad();

		private Chad() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Somalia extends Country {
		public static final int CODE = 157;
		public static final String RES_NAME = "umssdk_area_somalia";
		public static final Somalia INSTANCE = new Somalia();

		private Somalia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Madagascar extends Country {
		public static final int CODE = 102;
		public static final String RES_NAME = "umssdk_area_madagascar";
		public static final Madagascar INSTANCE = new Madagascar();

		private Madagascar() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Thailand extends Country {
		public static final int CODE = 172;
		public static final String RES_NAME = "umssdk_area_thailand";
		public static final Thailand INSTANCE = new Thailand();

		private Thailand() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Libya extends Country {
		public static final int CODE = 97;
		public static final String RES_NAME = "umssdk_area_libya";
		public static final Libya INSTANCE = new Libya();

		private Libya() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class TrinidadandTobago extends Country {
		public static final int CODE = 175;
		public static final String RES_NAME = "umssdk_area_trinidadandtobago";
		public static final TrinidadandTobago INSTANCE = new TrinidadandTobago();

		private TrinidadandTobago() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Sweden extends Country {
		public static final int CODE = 166;
		public static final String RES_NAME = "umssdk_area_sweden";
		public static final Sweden INSTANCE = new Sweden();

		private Sweden() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Vietnam extends Country {
		public static final int CODE = 187;
		public static final String RES_NAME = "umssdk_area_vietnam";
		public static final Vietnam INSTANCE = new Vietnam();

		private Vietnam() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class WestSamoa extends Country {
		public static final int CODE = 146;
		public static final String RES_NAME = "umssdk_area_westsamoa";
		public static final WestSamoa INSTANCE = new WestSamoa();

		private WestSamoa() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Malawi extends Country {
		public static final int CODE = 103;
		public static final String RES_NAME = "umssdk_area_malawi";
		public static final Malawi INSTANCE = new Malawi();

		private Malawi() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class SaudiArabia extends Country {
		public static final int CODE = 149;
		public static final String RES_NAME = "umssdk_area_saudiarabia";
		public static final SaudiArabia INSTANCE = new SaudiArabia();

		private SaudiArabia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Andorra extends Country {
		public static final int CODE = 4;
		public static final String RES_NAME = "umssdk_area_andorra";
		public static final Andorra INSTANCE = new Andorra();

		private Andorra() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Liechtenstein extends Country {
		public static final int CODE = 98;
		public static final String RES_NAME = "umssdk_area_liechtenstein";
		public static final Liechtenstein INSTANCE = new Liechtenstein();

		private Liechtenstein() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Poland extends Country {
		public static final int CODE = 136;
		public static final String RES_NAME = "umssdk_area_poland";
		public static final Poland INSTANCE = new Poland();

		private Poland() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Bulgaria extends Country {
		public static final int CODE = 27;
		public static final String RES_NAME = "umssdk_area_bulgaria";
		public static final Bulgaria INSTANCE = new Bulgaria();

		private Bulgaria() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Jordan extends Country {
		public static final int CODE = 85;
		public static final String RES_NAME = "umssdk_area_jordan";
		public static final Jordan INSTANCE = new Jordan();

		private Jordan() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Kuwait extends Country {
		public static final int CODE = 90;
		public static final String RES_NAME = "umssdk_area_kuwait";
		public static final Kuwait INSTANCE = new Kuwait();

		private Kuwait() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Nigeria extends Country {
		public static final int CODE = 126;
		public static final String RES_NAME = "umssdk_area_nigeria";
		public static final Nigeria INSTANCE = new Nigeria();

		private Nigeria() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Tunisia extends Country {
		public static final int CODE = 176;
		public static final String RES_NAME = "umssdk_area_tunisia";
		public static final Tunisia INSTANCE = new Tunisia();

		private Tunisia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class BurkinaFaso extends Country {
		public static final int CODE = 28;
		public static final String RES_NAME = "umssdk_area_burkinafaso";
		public static final BurkinaFaso INSTANCE = new BurkinaFaso();

		private BurkinaFaso() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class MarianaIslands extends Country {
		public static final int CODE = 108;
		public static final String RES_NAME = "umssdk_area_marianaislands";
		public static final MarianaIslands INSTANCE = new MarianaIslands();

		private MarianaIslands() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class CostaRica extends Country {
		public static final int CODE = 41;
		public static final String RES_NAME = "umssdk_area_costarica";
		public static final CostaRica INSTANCE = new CostaRica();

		private CostaRica() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class USA extends Country {
		public static final int CODE = 183;
		public static final String RES_NAME = "umssdk_area_usa";
		public static final USA INSTANCE = new USA();

		private USA() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Uruguay extends Country {
		public static final int CODE = 184;
		public static final String RES_NAME = "umssdk_area_uruguay";
		public static final Uruguay INSTANCE = new Uruguay();

		private Uruguay() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class StVincentIsland extends Country {
		public static final int CODE = 144;
		public static final String RES_NAME = "umssdk_area_stvincentisland";
		public static final StVincentIsland INSTANCE = new StVincentIsland();

		private StVincentIsland() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Kenya extends Country {
		public static final int CODE = 88;
		public static final String RES_NAME = "umssdk_area_kenya";
		public static final Kenya INSTANCE = new Kenya();

		private Kenya() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Martini extends Country {
		public static final int CODE = 109;
		public static final String RES_NAME = "umssdk_area_martini";
		public static final Martini INSTANCE = new Martini();

		private Martini() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Switzerland extends Country {
		public static final int CODE = 167;
		public static final String RES_NAME = "umssdk_area_switzerland";
		public static final Switzerland INSTANCE = new Switzerland();

		private Switzerland() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Spain extends Country {
		public static final int CODE = 159;
		public static final String RES_NAME = "umssdk_area_spain";
		public static final Spain INSTANCE = new Spain();

		private Spain() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Brunei extends Country {
		public static final int CODE = 26;
		public static final String RES_NAME = "umssdk_area_brunei";
		public static final Brunei INSTANCE = new Brunei();

		private Brunei() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Djibouti extends Country {
		public static final int CODE = 46;
		public static final String RES_NAME = "umssdk_area_djibouti";
		public static final Djibouti INSTANCE = new Djibouti();

		private Djibouti() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Lebanon extends Country {
		public static final int CODE = 94;
		public static final String RES_NAME = "umssdk_area_lebanon";
		public static final Lebanon INSTANCE = new Lebanon();

		private Lebanon() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Azerbaijan extends Country {
		public static final int CODE = 13;
		public static final String RES_NAME = "umssdk_area_azerbaijan";
		public static final Azerbaijan INSTANCE = new Azerbaijan();

		private Azerbaijan() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Cuba extends Country {
		public static final int CODE = 42;
		public static final String RES_NAME = "umssdk_area_cuba";
		public static final Cuba INSTANCE = new Cuba();

		private Cuba() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Liberia extends Country {
		public static final int CODE = 96;
		public static final String RES_NAME = "umssdk_area_liberia";
		public static final Liberia INSTANCE = new Liberia();

		private Liberia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Venezuela extends Country {
		public static final int CODE = 186;
		public static final String RES_NAME = "umssdk_area_venezuela";
		public static final Venezuela INSTANCE = new Venezuela();

		private Venezuela() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Swaziland extends Country {
		public static final int CODE = 165;
		public static final String RES_NAME = "umssdk_area_swaziland";
		public static final Swaziland INSTANCE = new Swaziland();

		private Swaziland() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class NetherlandsAntilles extends Country {
		public static final int CODE = 121;
		public static final String RES_NAME = "umssdk_area_netherlandsantilles";
		public static final NetherlandsAntilles INSTANCE = new NetherlandsAntilles();

		private NetherlandsAntilles() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Czech extends Country {
		public static final int CODE = 44;
		public static final String RES_NAME = "umssdk_area_czech";
		public static final Czech INSTANCE = new Czech();

		private Czech() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Israel extends Country {
		public static final int CODE = 80;
		public static final String RES_NAME = "umssdk_area_israel";
		public static final Israel INSTANCE = new Israel();

		private Israel() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class StVincent extends Country {
		public static final int CODE = 162;
		public static final String RES_NAME = "umssdk_area_stvincent";
		public static final StVincent INSTANCE = new StVincent();

		private StVincent() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Australia extends Country {
		public static final int CODE = 11;
		public static final String RES_NAME = "umssdk_area_australia";
		public static final Australia INSTANCE = new Australia();

		private Australia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Tajikistan extends Country {
		public static final int CODE = 170;
		public static final String RES_NAME = "umssdk_area_tajikistan";
		public static final Tajikistan INSTANCE = new Tajikistan();

		private Tajikistan() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Estonia extends Country {
		public static final int CODE = 51;
		public static final String RES_NAME = "umssdk_area_estonia";
		public static final Estonia INSTANCE = new Estonia();

		private Estonia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Myanmar extends Country {
		public static final int CODE = 29;
		public static final String RES_NAME = "umssdk_area_myanmar";
		public static final Myanmar INSTANCE = new Myanmar();

		private Myanmar() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Cameroon extends Country {
		public static final int CODE = 31;
		public static final String RES_NAME = "umssdk_area_cameroon";
		public static final Cameroon INSTANCE = new Cameroon();

		private Cameroon() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Gibraltar extends Country {
		public static final int CODE = 63;
		public static final String RES_NAME = "umssdk_area_gibraltar";
		public static final Gibraltar INSTANCE = new Gibraltar();

		private Gibraltar() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Cyprus extends Country {
		public static final int CODE = 43;
		public static final String RES_NAME = "umssdk_area_cyprus";
		public static final Cyprus INSTANCE = new Cyprus();

		private Cyprus() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Malaysia extends Country {
		public static final int CODE = 104;
		public static final String RES_NAME = "umssdk_area_malaysia";
		public static final Malaysia INSTANCE = new Malaysia();

		private Malaysia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Iceland extends Country {
		public static final int CODE = 74;
		public static final String RES_NAME = "umssdk_area_iceland";
		public static final Iceland INSTANCE = new Iceland();

		private Iceland() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Oman extends Country {
		public static final int CODE = 129;
		public static final String RES_NAME = "umssdk_area_oman";
		public static final Oman INSTANCE = new Oman();

		private Oman() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Armenia extends Country {
		public static final int CODE = 9;
		public static final String RES_NAME = "umssdk_area_armenia";
		public static final Armenia INSTANCE = new Armenia();

		private Armenia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Gabon extends Country {
		public static final int CODE = 58;
		public static final String RES_NAME = "umssdk_area_gabon";
		public static final Gabon INSTANCE = new Gabon();

		private Gabon() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Austria extends Country {
		public static final int CODE = 12;
		public static final String RES_NAME = "umssdk_area_austria";
		public static final Austria INSTANCE = new Austria();

		private Austria() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Yugoslavia extends Country {
		public static final int CODE = 189;
		public static final String RES_NAME = "umssdk_area_yugoslavia";
		public static final Yugoslavia INSTANCE = new Yugoslavia();

		private Yugoslavia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Mozambique extends Country {
		public static final int CODE = 117;
		public static final String RES_NAME = "umssdk_area_mozambique";
		public static final Mozambique INSTANCE = new Mozambique();

		private Mozambique() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class CaymanIslands extends Country {
		public static final int CODE = 33;
		public static final String RES_NAME = "umssdk_area_caymanislands";
		public static final CaymanIslands INSTANCE = new CaymanIslands();

		private CaymanIslands() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Zaire extends Country {
		public static final int CODE = 190;
		public static final String RES_NAME = "umssdk_area_zaire";
		public static final Zaire INSTANCE = new Zaire();

		private Zaire() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Monaco extends Country {
		public static final int CODE = 113;
		public static final String RES_NAME = "umssdk_area_monaco";
		public static final Monaco INSTANCE = new Monaco();

		private Monaco() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Luxembourg extends Country {
		public static final int CODE = 100;
		public static final String RES_NAME = "umssdk_area_luxembourg";
		public static final Luxembourg INSTANCE = new Luxembourg();

		private Luxembourg() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Brazil extends Country {
		public static final int CODE = 25;
		public static final String RES_NAME = "umssdk_area_brazil";
		public static final Brazil INSTANCE = new Brazil();

		private Brazil() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Guam extends Country {
		public static final int CODE = 66;
		public static final String RES_NAME = "umssdk_area_guam";
		public static final Guam INSTANCE = new Guam();

		private Guam() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Algeria extends Country {
		public static final int CODE = 3;
		public static final String RES_NAME = "umssdk_area_algeria";
		public static final Algeria INSTANCE = new Algeria();

		private Algeria() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Slovenia extends Country {
		public static final int CODE = 155;
		public static final String RES_NAME = "umssdk_area_slovenia";
		public static final Slovenia INSTANCE = new Slovenia();

		private Slovenia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Lesotho extends Country {
		public static final int CODE = 95;
		public static final String RES_NAME = "umssdk_area_lesotho";
		public static final Lesotho INSTANCE = new Lesotho();

		private Lesotho() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class CotedIvoire extends Country {
		public static final int CODE = 82;
		public static final String RES_NAME = "umssdk_area_cotedivoire";
		public static final CotedIvoire INSTANCE = new CotedIvoire();

		private CotedIvoire() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Tonga extends Country {
		public static final int CODE = 174;
		public static final String RES_NAME = "umssdk_area_tonga";
		public static final Tonga INSTANCE = new Tonga();

		private Tonga() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Ecuador extends Country {
		public static final int CODE = 48;
		public static final String RES_NAME = "umssdk_area_ecuador";
		public static final Ecuador INSTANCE = new Ecuador();

		private Ecuador() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class PapuaNewGuinea extends Country {
		public static final int CODE = 132;
		public static final String RES_NAME = "umssdk_area_papuanewguinea";
		public static final PapuaNewGuinea INSTANCE = new PapuaNewGuinea();

		private PapuaNewGuinea() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Hungary extends Country {
		public static final int CODE = 73;
		public static final String RES_NAME = "umssdk_area_hungary";
		public static final Hungary INSTANCE = new Hungary();

		private Hungary() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Japan extends Country {
		public static final int CODE = 84;
		public static final String RES_NAME = "umssdk_area_japan";
		public static final Japan INSTANCE = new Japan();

		private Japan() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Moldova extends Country {
		public static final int CODE = 112;
		public static final String RES_NAME = "umssdk_area_moldova";
		public static final Moldova INSTANCE = new Moldova();

		private Moldova() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Belarus extends Country {
		public static final int CODE = 18;
		public static final String RES_NAME = "umssdk_area_belarus";
		public static final Belarus INSTANCE = new Belarus();

		private Belarus() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Mauritius extends Country {
		public static final int CODE = 110;
		public static final String RES_NAME = "umssdk_area_mauritius";
		public static final Mauritius INSTANCE = new Mauritius();

		private Mauritius() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class SierraLeone extends Country {
		public static final int CODE = 152;
		public static final String RES_NAME = "umssdk_area_sierraleone";
		public static final SierraLeone INSTANCE = new SierraLeone();

		private SierraLeone() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Albania extends Country {
		public static final int CODE = 2;
		public static final String RES_NAME = "umssdk_area_albania";
		public static final Albania INSTANCE = new Albania();

		private Albania() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Columbia extends Country {
		public static final int CODE = 38;
		public static final String RES_NAME = "umssdk_area_columbia";
		public static final Columbia INSTANCE = new Columbia();

		private Columbia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class AntiguaandBarbuda extends Country {
		public static final int CODE = 7;
		public static final String RES_NAME = "umssdk_area_antiguaandbarbuda";
		public static final AntiguaandBarbuda INSTANCE = new AntiguaandBarbuda();

		private AntiguaandBarbuda() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Senegal extends Country {
		public static final int CODE = 150;
		public static final String RES_NAME = "umssdk_area_senegal";
		public static final Senegal INSTANCE = new Senegal();

		private Senegal() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class NorthKorea extends Country {
		public static final int CODE = 127;
		public static final String RES_NAME = "umssdk_area_northkorea";
		public static final NorthKorea INSTANCE = new NorthKorea();

		private NorthKorea() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Honduras extends Country {
		public static final int CODE = 71;
		public static final String RES_NAME = "umssdk_area_honduras";
		public static final Honduras INSTANCE = new Honduras();

		private Honduras() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Italy extends Country {
		public static final int CODE = 81;
		public static final String RES_NAME = "umssdk_area_italy";
		public static final Italy INSTANCE = new Italy();

		private Italy() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Nauru extends Country {
		public static final int CODE = 119;
		public static final String RES_NAME = "umssdk_area_nauru";
		public static final Nauru INSTANCE = new Nauru();

		private Nauru() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Ethiopia extends Country {
		public static final int CODE = 52;
		public static final String RES_NAME = "umssdk_area_ethiopia";
		public static final Ethiopia INSTANCE = new Ethiopia();

		private Ethiopia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class DominicanRepublic extends Country {
		public static final int CODE = 47;
		public static final String RES_NAME = "umssdk_area_dominicanrepublic";
		public static final DominicanRepublic INSTANCE = new DominicanRepublic();

		private DominicanRepublic() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Haiti extends Country {
		public static final int CODE = 70;
		public static final String RES_NAME = "umssdk_area_haiti";
		public static final Haiti INSTANCE = new Haiti();

		private Haiti() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Burundi extends Country {
		public static final int CODE = 30;
		public static final String RES_NAME = "umssdk_area_burundi";
		public static final Burundi INSTANCE = new Burundi();

		private Burundi() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Singapore extends Country {
		public static final int CODE = 153;
		public static final String RES_NAME = "umssdk_area_singapore";
		public static final Singapore INSTANCE = new Singapore();

		private Singapore() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Egypt extends Country {
		public static final int CODE = 49;
		public static final String RES_NAME = "umssdk_area_egypt";
		public static final Egypt INSTANCE = new Egypt();

		private Egypt() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Bolivia extends Country {
		public static final int CODE = 23;
		public static final String RES_NAME = "umssdk_area_bolivia";
		public static final Bolivia INSTANCE = new Bolivia();

		private Bolivia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class SanMarino extends Country {
		public static final int CODE = 147;
		public static final String RES_NAME = "umssdk_area_sanmarino";
		public static final SanMarino INSTANCE = new SanMarino();

		private SanMarino() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Malta extends Country {
		public static final int CODE = 107;
		public static final String RES_NAME = "umssdk_area_malta";
		public static final Malta INSTANCE = new Malta();

		private Malta() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Russia extends Country {
		public static final int CODE = 142;
		public static final String RES_NAME = "umssdk_area_russia";
		public static final Russia INSTANCE = new Russia();

		private Russia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Netherlands extends Country {
		public static final int CODE = 122;
		public static final String RES_NAME = "umssdk_area_netherlands";
		public static final Netherlands INSTANCE = new Netherlands();

		private Netherlands() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Pakistan extends Country {
		public static final int CODE = 130;
		public static final String RES_NAME = "umssdk_area_pakistan";
		public static final Pakistan INSTANCE = new Pakistan();

		private Pakistan() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Gambia extends Country {
		public static final int CODE = 59;
		public static final String RES_NAME = "umssdk_area_gambia";
		public static final Gambia INSTANCE = new Gambia();

		private Gambia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class China extends Country {
		public static final int CODE = 37;
		public static final String RES_NAME = "umssdk_area_china";
		public static final China INSTANCE = new China();

		private China() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

		public final Province codeOf(int code) {
			return valueOf(code);
		}

		public static Province[] values() {
			return new Province[] {
				HongKong.INSTANCE,
				Macao.INSTANCE,
				Taiwan.INSTANCE,
				Anhui.INSTANCE,
				Beijing.INSTANCE,
				Fujian.INSTANCE,
				Gansu.INSTANCE,
				Guangdong.INSTANCE,
				Guangxi.INSTANCE,
				Guizhou.INSTANCE,
				Hainan.INSTANCE,
				Hebei.INSTANCE,
				Henan.INSTANCE,
				Heilongjiang.INSTANCE,
				Hubei.INSTANCE,
				Hunan.INSTANCE,
				Jilin.INSTANCE,
				Jiangsu.INSTANCE,
				Jiangxi.INSTANCE,
				Liaoning.INSTANCE,
				InnerMongolia.INSTANCE,
				Ningxia.INSTANCE,
				Qinghai.INSTANCE,
				Shandong.INSTANCE,
				Shanxi.INSTANCE,
				Shaanxi.INSTANCE,
				Shanghai.INSTANCE,
				Sichuan.INSTANCE,
				Tianjin.INSTANCE,
				Tibet.INSTANCE,
				Xinjiang.INSTANCE,
				Yunnan.INSTANCE,
				Zhejiang.INSTANCE,
				Chongqing.INSTANCE
			};
		}

		public static Province valueOf(int code) {
			for (Province item : values()) {
				if (item.code() == code) {
					return item;
				}
			}
			return null;
		}

		public static final class Gansu extends Province {
			public static final int CODE = 196;
			public static final String RES_NAME = "umssdk_area_china_gansu";
			public static final Gansu INSTANCE = new Gansu();

			private Gansu() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Baiyin.INSTANCE,
					Dingxi.INSTANCE,
					Jiayuguan.INSTANCE,
					Jinchang.INSTANCE,
					Jiuquan.INSTANCE,
					Lanzhou.INSTANCE,
					Longnan.INSTANCE,
					Pingliang.INSTANCE,
					Qingyang.INSTANCE,
					Tianshui.INSTANCE,
					Wuwei.INSTANCE,
					Zhangye.INSTANCE,
					Linxia.INSTANCE,
					Gannan.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Jiayuguan extends City {
				public static final int CODE = 257;
				public static final String RES_NAME = "umssdk_area_china_gansu_jiayuguan";
				public static final Jiayuguan INSTANCE = new Jiayuguan();

				private Jiayuguan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Dingxi extends City {
				public static final int CODE = 255;
				public static final String RES_NAME = "umssdk_area_china_gansu_dingxi";
				public static final Dingxi INSTANCE = new Dingxi();

				private Dingxi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Lanzhou extends City {
				public static final int CODE = 260;
				public static final String RES_NAME = "umssdk_area_china_gansu_lanzhou";
				public static final Lanzhou INSTANCE = new Lanzhou();

				private Lanzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhangye extends City {
				public static final int CODE = 266;
				public static final String RES_NAME = "umssdk_area_china_gansu_zhangye";
				public static final Zhangye INSTANCE = new Zhangye();

				private Zhangye() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Tianshui extends City {
				public static final int CODE = 264;
				public static final String RES_NAME = "umssdk_area_china_gansu_tianshui";
				public static final Tianshui INSTANCE = new Tianshui();

				private Tianshui() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Qingyang extends City {
				public static final int CODE = 263;
				public static final String RES_NAME = "umssdk_area_china_gansu_qingyang";
				public static final Qingyang INSTANCE = new Qingyang();

				private Qingyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Linxia extends City {
				public static final int CODE = 625;
				public static final String RES_NAME = "umssdk_area_china_gansu_linxia";
				public static final Linxia INSTANCE = new Linxia();

				private Linxia() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Gannan extends City {
				public static final int CODE = 626;
				public static final String RES_NAME = "umssdk_area_china_gansu_gannan";
				public static final Gannan INSTANCE = new Gannan();

				private Gannan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jinchang extends City {
				public static final int CODE = 258;
				public static final String RES_NAME = "umssdk_area_china_gansu_jinchang";
				public static final Jinchang INSTANCE = new Jinchang();

				private Jinchang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jiuquan extends City {
				public static final int CODE = 259;
				public static final String RES_NAME = "umssdk_area_china_gansu_jiuquan";
				public static final Jiuquan INSTANCE = new Jiuquan();

				private Jiuquan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Pingliang extends City {
				public static final int CODE = 262;
				public static final String RES_NAME = "umssdk_area_china_gansu_pingliang";
				public static final Pingliang INSTANCE = new Pingliang();

				private Pingliang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Baiyin extends City {
				public static final int CODE = 254;
				public static final String RES_NAME = "umssdk_area_china_gansu_baiyin";
				public static final Baiyin INSTANCE = new Baiyin();

				private Baiyin() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Longnan extends City {
				public static final int CODE = 261;
				public static final String RES_NAME = "umssdk_area_china_gansu_longnan";
				public static final Longnan INSTANCE = new Longnan();

				private Longnan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Wuwei extends City {
				public static final int CODE = 265;
				public static final String RES_NAME = "umssdk_area_china_gansu_wuwei";
				public static final Wuwei INSTANCE = new Wuwei();

				private Wuwei() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Anhui extends Province {
			public static final int CODE = 193;
			public static final String RES_NAME = "umssdk_area_china_anhui";
			public static final Anhui INSTANCE = new Anhui();

			private Anhui() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Anqing.INSTANCE,
					Bengbu.INSTANCE,
					Bozhou.INSTANCE,
					Chaohu.INSTANCE,
					Chizhou.INSTANCE,
					Chuzhou.INSTANCE,
					Fuyang.INSTANCE,
					Hefei.INSTANCE,
					Huaibei.INSTANCE,
					Huainan.INSTANCE,
					Huangshan.INSTANCE,
					Luan.INSTANCE,
					MaOnShan.INSTANCE,
					Suzhou.INSTANCE,
					Tongling.INSTANCE,
					Wuhu.INSTANCE,
					Xuancheng.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Chizhou extends City {
				public static final int CODE = 229;
				public static final String RES_NAME = "umssdk_area_china_anhui_chizhou";
				public static final Chizhou INSTANCE = new Chizhou();

				private Chizhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Wuhu extends City {
				public static final int CODE = 240;
				public static final String RES_NAME = "umssdk_area_china_anhui_wuhu";
				public static final Wuhu INSTANCE = new Wuhu();

				private Wuhu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Tongling extends City {
				public static final int CODE = 239;
				public static final String RES_NAME = "umssdk_area_china_anhui_tongling";
				public static final Tongling INSTANCE = new Tongling();

				private Tongling() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Chuzhou extends City {
				public static final int CODE = 230;
				public static final String RES_NAME = "umssdk_area_china_anhui_chuzhou";
				public static final Chuzhou INSTANCE = new Chuzhou();

				private Chuzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Huainan extends City {
				public static final int CODE = 234;
				public static final String RES_NAME = "umssdk_area_china_anhui_huainan";
				public static final Huainan INSTANCE = new Huainan();

				private Huainan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xuancheng extends City {
				public static final int CODE = 241;
				public static final String RES_NAME = "umssdk_area_china_anhui_xuancheng";
				public static final Xuancheng INSTANCE = new Xuancheng();

				private Xuancheng() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hefei extends City {
				public static final int CODE = 232;
				public static final String RES_NAME = "umssdk_area_china_anhui_hefei";
				public static final Hefei INSTANCE = new Hefei();

				private Hefei() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Fuyang extends City {
				public static final int CODE = 231;
				public static final String RES_NAME = "umssdk_area_china_anhui_fuyang";
				public static final Fuyang INSTANCE = new Fuyang();

				private Fuyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Bengbu extends City {
				public static final int CODE = 226;
				public static final String RES_NAME = "umssdk_area_china_anhui_bengbu";
				public static final Bengbu INSTANCE = new Bengbu();

				private Bengbu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class MaOnShan extends City {
				public static final int CODE = 237;
				public static final String RES_NAME = "umssdk_area_china_anhui_maonshan";
				public static final MaOnShan INSTANCE = new MaOnShan();

				private MaOnShan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Anqing extends City {
				public static final int CODE = 225;
				public static final String RES_NAME = "umssdk_area_china_anhui_anqing";
				public static final Anqing INSTANCE = new Anqing();

				private Anqing() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Luan extends City {
				public static final int CODE = 236;
				public static final String RES_NAME = "umssdk_area_china_anhui_luan";
				public static final Luan INSTANCE = new Luan();

				private Luan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Bozhou extends City {
				public static final int CODE = 227;
				public static final String RES_NAME = "umssdk_area_china_anhui_bozhou";
				public static final Bozhou INSTANCE = new Bozhou();

				private Bozhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Huangshan extends City {
				public static final int CODE = 235;
				public static final String RES_NAME = "umssdk_area_china_anhui_huangshan";
				public static final Huangshan INSTANCE = new Huangshan();

				private Huangshan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Suzhou extends City {
				public static final int CODE = 238;
				public static final String RES_NAME = "umssdk_area_china_anhui_suzhou";
				public static final Suzhou INSTANCE = new Suzhou();

				private Suzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Chaohu extends City {
				public static final int CODE = 228;
				public static final String RES_NAME = "umssdk_area_china_anhui_chaohu";
				public static final Chaohu INSTANCE = new Chaohu();

				private Chaohu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Huaibei extends City {
				public static final int CODE = 233;
				public static final String RES_NAME = "umssdk_area_china_anhui_huaibei";
				public static final Huaibei INSTANCE = new Huaibei();

				private Huaibei() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Jiangxi extends Province {
			public static final int CODE = 208;
			public static final String RES_NAME = "umssdk_area_china_jiangxi";
			public static final Jiangxi INSTANCE = new Jiangxi();

			private Jiangxi() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Fuzhou.INSTANCE,
					Ganzhou.INSTANCE,
					Jian.INSTANCE,
					Jingdezhen.INSTANCE,
					Jiujiang.INSTANCE,
					Nanchang.INSTANCE,
					Pingxiang.INSTANCE,
					Shangrao.INSTANCE,
					Xinyu.INSTANCE,
					Yichun.INSTANCE,
					Yingtan.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Xinyu extends City {
				public static final int CODE = 430;
				public static final String RES_NAME = "umssdk_area_china_jiangxi_xinyu";
				public static final Xinyu INSTANCE = new Xinyu();

				private Xinyu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shangrao extends City {
				public static final int CODE = 429;
				public static final String RES_NAME = "umssdk_area_china_jiangxi_shangrao";
				public static final Shangrao INSTANCE = new Shangrao();

				private Shangrao() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jiujiang extends City {
				public static final int CODE = 426;
				public static final String RES_NAME = "umssdk_area_china_jiangxi_jiujiang";
				public static final Jiujiang INSTANCE = new Jiujiang();

				private Jiujiang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Nanchang extends City {
				public static final int CODE = 427;
				public static final String RES_NAME = "umssdk_area_china_jiangxi_nanchang";
				public static final Nanchang INSTANCE = new Nanchang();

				private Nanchang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yichun extends City {
				public static final int CODE = 431;
				public static final String RES_NAME = "umssdk_area_china_jiangxi_yichun";
				public static final Yichun INSTANCE = new Yichun();

				private Yichun() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jingdezhen extends City {
				public static final int CODE = 425;
				public static final String RES_NAME = "umssdk_area_china_jiangxi_jingdezhen";
				public static final Jingdezhen INSTANCE = new Jingdezhen();

				private Jingdezhen() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Pingxiang extends City {
				public static final int CODE = 428;
				public static final String RES_NAME = "umssdk_area_china_jiangxi_pingxiang";
				public static final Pingxiang INSTANCE = new Pingxiang();

				private Pingxiang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Fuzhou extends City {
				public static final int CODE = 422;
				public static final String RES_NAME = "umssdk_area_china_jiangxi_fuzhou";
				public static final Fuzhou INSTANCE = new Fuzhou();

				private Fuzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yingtan extends City {
				public static final int CODE = 432;
				public static final String RES_NAME = "umssdk_area_china_jiangxi_yingtan";
				public static final Yingtan INSTANCE = new Yingtan();

				private Yingtan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Ganzhou extends City {
				public static final int CODE = 423;
				public static final String RES_NAME = "umssdk_area_china_jiangxi_ganzhou";
				public static final Ganzhou INSTANCE = new Ganzhou();

				private Ganzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jian extends City {
				public static final int CODE = 424;
				public static final String RES_NAME = "umssdk_area_china_jiangxi_jian";
				public static final Jian INSTANCE = new Jian();

				private Jian() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Shaanxi extends Province {
			public static final int CODE = 215;
			public static final String RES_NAME = "umssdk_area_china_shaanxi";
			public static final Shaanxi INSTANCE = new Shaanxi();

			private Shaanxi() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Ankang.INSTANCE,
					Baoji.INSTANCE,
					Hanzhoung.INSTANCE,
					Shangluo.INSTANCE,
					Tongchuan.INSTANCE,
					Weinan.INSTANCE,
					Xian.INSTANCE,
					Xianyang.INSTANCE,
					Yanan.INSTANCE,
					Yulin.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Baoji extends City {
				public static final int CODE = 504;
				public static final String RES_NAME = "umssdk_area_china_shaanxi_baoji";
				public static final Baoji INSTANCE = new Baoji();

				private Baoji() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yanan extends City {
				public static final int CODE = 511;
				public static final String RES_NAME = "umssdk_area_china_shaanxi_yanan";
				public static final Yanan INSTANCE = new Yanan();

				private Yanan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Tongchuan extends City {
				public static final int CODE = 507;
				public static final String RES_NAME = "umssdk_area_china_shaanxi_tongchuan";
				public static final Tongchuan INSTANCE = new Tongchuan();

				private Tongchuan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Weinan extends City {
				public static final int CODE = 508;
				public static final String RES_NAME = "umssdk_area_china_shaanxi_weinan";
				public static final Weinan INSTANCE = new Weinan();

				private Weinan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Ankang extends City {
				public static final int CODE = 503;
				public static final String RES_NAME = "umssdk_area_china_shaanxi_ankang";
				public static final Ankang INSTANCE = new Ankang();

				private Ankang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xian extends City {
				public static final int CODE = 509;
				public static final String RES_NAME = "umssdk_area_china_shaanxi_xian";
				public static final Xian INSTANCE = new Xian();

				private Xian() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hanzhoung extends City {
				public static final int CODE = 505;
				public static final String RES_NAME = "umssdk_area_china_shaanxi_hanzhoung";
				public static final Hanzhoung INSTANCE = new Hanzhoung();

				private Hanzhoung() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yulin extends City {
				public static final int CODE = 512;
				public static final String RES_NAME = "umssdk_area_china_shaanxi_yulin";
				public static final Yulin INSTANCE = new Yulin();

				private Yulin() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xianyang extends City {
				public static final int CODE = 510;
				public static final String RES_NAME = "umssdk_area_china_shaanxi_xianyang";
				public static final Xianyang INSTANCE = new Xianyang();

				private Xianyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shangluo extends City {
				public static final int CODE = 506;
				public static final String RES_NAME = "umssdk_area_china_shaanxi_shangluo";
				public static final Shangluo INSTANCE = new Shangluo();

				private Shangluo() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Tibet extends Province {
			public static final int CODE = 219;
			public static final String RES_NAME = "umssdk_area_china_tibet";
			public static final Tibet INSTANCE = new Tibet();

			private Tibet() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Ali.INSTANCE,
					Changdu.INSTANCE,
					Lhasa.INSTANCE,
					Nyingchi.INSTANCE,
					Nagqu.INSTANCE,
					Shigatse.INSTANCE,
					Shannan.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Nagqu extends City {
				public static final int CODE = 539;
				public static final String RES_NAME = "umssdk_area_china_tibet_nagqu";
				public static final Nagqu INSTANCE = new Nagqu();

				private Nagqu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Changdu extends City {
				public static final int CODE = 536;
				public static final String RES_NAME = "umssdk_area_china_tibet_changdu";
				public static final Changdu INSTANCE = new Changdu();

				private Changdu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Nyingchi extends City {
				public static final int CODE = 538;
				public static final String RES_NAME = "umssdk_area_china_tibet_nyingchi";
				public static final Nyingchi INSTANCE = new Nyingchi();

				private Nyingchi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shigatse extends City {
				public static final int CODE = 540;
				public static final String RES_NAME = "umssdk_area_china_tibet_shigatse";
				public static final Shigatse INSTANCE = new Shigatse();

				private Shigatse() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Lhasa extends City {
				public static final int CODE = 537;
				public static final String RES_NAME = "umssdk_area_china_tibet_lhasa";
				public static final Lhasa INSTANCE = new Lhasa();

				private Lhasa() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shannan extends City {
				public static final int CODE = 541;
				public static final String RES_NAME = "umssdk_area_china_tibet_shannan";
				public static final Shannan INSTANCE = new Shannan();

				private Shannan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Ali extends City {
				public static final int CODE = 535;
				public static final String RES_NAME = "umssdk_area_china_tibet_ali";
				public static final Ali INSTANCE = new Ali();

				private Ali() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Fujian extends Province {
			public static final int CODE = 195;
			public static final String RES_NAME = "umssdk_area_china_fujian";
			public static final Fujian INSTANCE = new Fujian();

			private Fujian() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Fuzhou.INSTANCE,
					Longyan.INSTANCE,
					Nanping.INSTANCE,
					Ningde.INSTANCE,
					Putian.INSTANCE,
					Quanzhou.INSTANCE,
					Sanming.INSTANCE,
					Xiamen.INSTANCE,
					Zhangzhou.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Ningde extends City {
				public static final int CODE = 246;
				public static final String RES_NAME = "umssdk_area_china_fujian_ningde";
				public static final Ningde INSTANCE = new Ningde();

				private Ningde() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Quanzhou extends City {
				public static final int CODE = 248;
				public static final String RES_NAME = "umssdk_area_china_fujian_quanzhou";
				public static final Quanzhou INSTANCE = new Quanzhou();

				private Quanzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Nanping extends City {
				public static final int CODE = 245;
				public static final String RES_NAME = "umssdk_area_china_fujian_nanping";
				public static final Nanping INSTANCE = new Nanping();

				private Nanping() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Longyan extends City {
				public static final int CODE = 244;
				public static final String RES_NAME = "umssdk_area_china_fujian_longyan";
				public static final Longyan INSTANCE = new Longyan();

				private Longyan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Sanming extends City {
				public static final int CODE = 249;
				public static final String RES_NAME = "umssdk_area_china_fujian_sanming";
				public static final Sanming INSTANCE = new Sanming();

				private Sanming() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xiamen extends City {
				public static final int CODE = 251;
				public static final String RES_NAME = "umssdk_area_china_fujian_xiamen";
				public static final Xiamen INSTANCE = new Xiamen();

				private Xiamen() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Putian extends City {
				public static final int CODE = 247;
				public static final String RES_NAME = "umssdk_area_china_fujian_putian";
				public static final Putian INSTANCE = new Putian();

				private Putian() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Fuzhou extends City {
				public static final int CODE = 243;
				public static final String RES_NAME = "umssdk_area_china_fujian_fuzhou";
				public static final Fuzhou INSTANCE = new Fuzhou();

				private Fuzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhangzhou extends City {
				public static final int CODE = 253;
				public static final String RES_NAME = "umssdk_area_china_fujian_zhangzhou";
				public static final Zhangzhou INSTANCE = new Zhangzhou();

				private Zhangzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Hainan extends Province {
			public static final int CODE = 200;
			public static final String RES_NAME = "umssdk_area_china_hainan";
			public static final Hainan INSTANCE = new Hainan();

			private Hainan() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Baisha.INSTANCE,
					Changjiang.INSTANCE,
					Chengmai.INSTANCE,
					Danzhou.INSTANCE,
					Dingan.INSTANCE,
					Oriental.INSTANCE,
					Haikou.INSTANCE,
					Ledong.INSTANCE,
					Prohigh.INSTANCE,
					Lingshui.INSTANCE,
					Qionghai.INSTANCE,
					Qiongzhong.INSTANCE,
					Sanya.INSTANCE,
					Tunchang.INSTANCE,
					Wanning.INSTANCE,
					Wenchang.INSTANCE,
					Wuzhishan.INSTANCE,
					Baoting.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Lingshui extends City {
				public static final int CODE = 320;
				public static final String RES_NAME = "umssdk_area_china_hainan_lingshui";
				public static final Lingshui INSTANCE = new Lingshui();

				private Lingshui() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Wenchang extends City {
				public static final int CODE = 326;
				public static final String RES_NAME = "umssdk_area_china_hainan_wenchang";
				public static final Wenchang INSTANCE = new Wenchang();

				private Wenchang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Oriental extends City {
				public static final int CODE = 316;
				public static final String RES_NAME = "umssdk_area_china_hainan_oriental";
				public static final Oriental INSTANCE = new Oriental();

				private Oriental() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Qiongzhong extends City {
				public static final int CODE = 322;
				public static final String RES_NAME = "umssdk_area_china_hainan_qiongzhong";
				public static final Qiongzhong INSTANCE = new Qiongzhong();

				private Qiongzhong() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Haikou extends City {
				public static final int CODE = 317;
				public static final String RES_NAME = "umssdk_area_china_hainan_haikou";
				public static final Haikou INSTANCE = new Haikou();

				private Haikou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Prohigh extends City {
				public static final int CODE = 319;
				public static final String RES_NAME = "umssdk_area_china_hainan_prohigh";
				public static final Prohigh INSTANCE = new Prohigh();

				private Prohigh() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Wuzhishan extends City {
				public static final int CODE = 327;
				public static final String RES_NAME = "umssdk_area_china_hainan_wuzhishan";
				public static final Wuzhishan INSTANCE = new Wuzhishan();

				private Wuzhishan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Changjiang extends City {
				public static final int CODE = 312;
				public static final String RES_NAME = "umssdk_area_china_hainan_changjiang";
				public static final Changjiang INSTANCE = new Changjiang();

				private Changjiang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Baisha extends City {
				public static final int CODE = 311;
				public static final String RES_NAME = "umssdk_area_china_hainan_baisha";
				public static final Baisha INSTANCE = new Baisha();

				private Baisha() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Baoting extends City {
				public static final int CODE = 621;
				public static final String RES_NAME = "umssdk_area_china_hainan_baoting";
				public static final Baoting INSTANCE = new Baoting();

				private Baoting() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Dingan extends City {
				public static final int CODE = 315;
				public static final String RES_NAME = "umssdk_area_china_hainan_dingan";
				public static final Dingan INSTANCE = new Dingan();

				private Dingan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Danzhou extends City {
				public static final int CODE = 314;
				public static final String RES_NAME = "umssdk_area_china_hainan_danzhou";
				public static final Danzhou INSTANCE = new Danzhou();

				private Danzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Chengmai extends City {
				public static final int CODE = 313;
				public static final String RES_NAME = "umssdk_area_china_hainan_chengmai";
				public static final Chengmai INSTANCE = new Chengmai();

				private Chengmai() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Ledong extends City {
				public static final int CODE = 318;
				public static final String RES_NAME = "umssdk_area_china_hainan_ledong";
				public static final Ledong INSTANCE = new Ledong();

				private Ledong() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Qionghai extends City {
				public static final int CODE = 321;
				public static final String RES_NAME = "umssdk_area_china_hainan_qionghai";
				public static final Qionghai INSTANCE = new Qionghai();

				private Qionghai() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Sanya extends City {
				public static final int CODE = 323;
				public static final String RES_NAME = "umssdk_area_china_hainan_sanya";
				public static final Sanya INSTANCE = new Sanya();

				private Sanya() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Tunchang extends City {
				public static final int CODE = 324;
				public static final String RES_NAME = "umssdk_area_china_hainan_tunchang";
				public static final Tunchang INSTANCE = new Tunchang();

				private Tunchang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Wanning extends City {
				public static final int CODE = 325;
				public static final String RES_NAME = "umssdk_area_china_hainan_wanning";
				public static final Wanning INSTANCE = new Wanning();

				private Wanning() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Tianjin extends Province {
			public static final int CODE = 218;
			public static final String RES_NAME = "umssdk_area_china_tianjin";
			public static final Tianjin INSTANCE = new Tianjin();

			private Tianjin() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

		}

		public static final class Xinjiang extends Province {
			public static final int CODE = 220;
			public static final String RES_NAME = "umssdk_area_china_xinjiang";
			public static final Xinjiang INSTANCE = new Xinjiang();

			private Xinjiang() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Karamay.INSTANCE,
					Urumchi.INSTANCE,
					Aksu.INSTANCE,
					Alar.INSTANCE,
					Altay.INSTANCE,
					BayinGuoLeng.INSTANCE,
					Bortala.INSTANCE,
					Changji.INSTANCE,
					Hami.INSTANCE,
					Hetian.INSTANCE,
					Kashi.INSTANCE,
					KizilsuKorskzi.INSTANCE,
					Shihezi.INSTANCE,
					Tacheng.INSTANCE,
					TuMuShuke.INSTANCE,
					Turpan.INSTANCE,
					Wujiaqu.INSTANCE,
					Yili.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Alar extends City {
				public static final int CODE = 545;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_alar";
				public static final Alar INSTANCE = new Alar();

				private Alar() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Wujiaqu extends City {
				public static final int CODE = 558;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_wujiaqu";
				public static final Wujiaqu INSTANCE = new Wujiaqu();

				private Wujiaqu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Bortala extends City {
				public static final int CODE = 548;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_bortala";
				public static final Bortala INSTANCE = new Bortala();

				private Bortala() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Altay extends City {
				public static final int CODE = 546;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_altay";
				public static final Altay INSTANCE = new Altay();

				private Altay() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yili extends City {
				public static final int CODE = 559;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_yili";
				public static final Yili INSTANCE = new Yili();

				private Yili() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class BayinGuoLeng extends City {
				public static final int CODE = 547;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_bayinguoleng";
				public static final BayinGuoLeng INSTANCE = new BayinGuoLeng();

				private BayinGuoLeng() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Karamay extends City {
				public static final int CODE = 542;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_karamay";
				public static final Karamay INSTANCE = new Karamay();

				private Karamay() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Kashi extends City {
				public static final int CODE = 552;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_kashi";
				public static final Kashi INSTANCE = new Kashi();

				private Kashi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Turpan extends City {
				public static final int CODE = 557;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_turpan";
				public static final Turpan INSTANCE = new Turpan();

				private Turpan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Urumchi extends City {
				public static final int CODE = 543;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_urumchi";
				public static final Urumchi INSTANCE = new Urumchi();

				private Urumchi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Aksu extends City {
				public static final int CODE = 544;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_aksu";
				public static final Aksu INSTANCE = new Aksu();

				private Aksu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shihezi extends City {
				public static final int CODE = 554;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_shihezi";
				public static final Shihezi INSTANCE = new Shihezi();

				private Shihezi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class TuMuShuke extends City {
				public static final int CODE = 556;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_tumushuke";
				public static final TuMuShuke INSTANCE = new TuMuShuke();

				private TuMuShuke() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Changji extends City {
				public static final int CODE = 549;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_changji";
				public static final Changji INSTANCE = new Changji();

				private Changji() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hami extends City {
				public static final int CODE = 550;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_hami";
				public static final Hami INSTANCE = new Hami();

				private Hami() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Tacheng extends City {
				public static final int CODE = 555;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_tacheng";
				public static final Tacheng INSTANCE = new Tacheng();

				private Tacheng() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hetian extends City {
				public static final int CODE = 551;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_hetian";
				public static final Hetian INSTANCE = new Hetian();

				private Hetian() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class KizilsuKorskzi extends City {
				public static final int CODE = 553;
				public static final String RES_NAME = "umssdk_area_china_xinjiang_kizilsukorskzi";
				public static final KizilsuKorskzi INSTANCE = new KizilsuKorskzi();

				private KizilsuKorskzi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Sichuan extends Province {
			public static final int CODE = 217;
			public static final String RES_NAME = "umssdk_area_china_sichuan";
			public static final Sichuan INSTANCE = new Sichuan();

			private Sichuan() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Bazhong.INSTANCE,
					Chengdu.INSTANCE,
					Dazhou.INSTANCE,
					Deyang.INSTANCE,
					Guangan.INSTANCE,
					Guangyuan.INSTANCE,
					Leshan.INSTANCE,
					Luzhou.INSTANCE,
					Meishan.INSTANCE,
					Mianyang.INSTANCE,
					Neijiang.INSTANCE,
					Nanchong.INSTANCE,
					Panzhihua.INSTANCE,
					Suining.INSTANCE,
					Yaan.INSTANCE,
					Yibin.INSTANCE,
					Ziyang.INSTANCE,
					Zigong.INSTANCE,
					Aba.INSTANCE,
					Ganzi.INSTANCE,
					Liangshan.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Yibin extends City {
				public static final int CODE = 531;
				public static final String RES_NAME = "umssdk_area_china_sichuan_yibin";
				public static final Yibin INSTANCE = new Yibin();

				private Yibin() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Liangshan extends City {
				public static final int CODE = 624;
				public static final String RES_NAME = "umssdk_area_china_sichuan_liangshan";
				public static final Liangshan INSTANCE = new Liangshan();

				private Liangshan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Aba extends City {
				public static final int CODE = 622;
				public static final String RES_NAME = "umssdk_area_china_sichuan_aba";
				public static final Aba INSTANCE = new Aba();

				private Aba() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Ziyang extends City {
				public static final int CODE = 532;
				public static final String RES_NAME = "umssdk_area_china_sichuan_ziyang";
				public static final Ziyang INSTANCE = new Ziyang();

				private Ziyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Meishan extends City {
				public static final int CODE = 524;
				public static final String RES_NAME = "umssdk_area_china_sichuan_meishan";
				public static final Meishan INSTANCE = new Meishan();

				private Meishan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Luzhou extends City {
				public static final int CODE = 523;
				public static final String RES_NAME = "umssdk_area_china_sichuan_luzhou";
				public static final Luzhou INSTANCE = new Luzhou();

				private Luzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Dazhou extends City {
				public static final int CODE = 516;
				public static final String RES_NAME = "umssdk_area_china_sichuan_dazhou";
				public static final Dazhou INSTANCE = new Dazhou();

				private Dazhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Panzhihua extends City {
				public static final int CODE = 528;
				public static final String RES_NAME = "umssdk_area_china_sichuan_panzhihua";
				public static final Panzhihua INSTANCE = new Panzhihua();

				private Panzhihua() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Neijiang extends City {
				public static final int CODE = 526;
				public static final String RES_NAME = "umssdk_area_china_sichuan_neijiang";
				public static final Neijiang INSTANCE = new Neijiang();

				private Neijiang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Leshan extends City {
				public static final int CODE = 522;
				public static final String RES_NAME = "umssdk_area_china_sichuan_leshan";
				public static final Leshan INSTANCE = new Leshan();

				private Leshan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Guangyuan extends City {
				public static final int CODE = 521;
				public static final String RES_NAME = "umssdk_area_china_sichuan_guangyuan";
				public static final Guangyuan INSTANCE = new Guangyuan();

				private Guangyuan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Bazhong extends City {
				public static final int CODE = 514;
				public static final String RES_NAME = "umssdk_area_china_sichuan_bazhong";
				public static final Bazhong INSTANCE = new Bazhong();

				private Bazhong() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Guangan extends City {
				public static final int CODE = 520;
				public static final String RES_NAME = "umssdk_area_china_sichuan_guangan";
				public static final Guangan INSTANCE = new Guangan();

				private Guangan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Deyang extends City {
				public static final int CODE = 517;
				public static final String RES_NAME = "umssdk_area_china_sichuan_deyang";
				public static final Deyang INSTANCE = new Deyang();

				private Deyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Ganzi extends City {
				public static final int CODE = 623;
				public static final String RES_NAME = "umssdk_area_china_sichuan_ganzi";
				public static final Ganzi INSTANCE = new Ganzi();

				private Ganzi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Chengdu extends City {
				public static final int CODE = 515;
				public static final String RES_NAME = "umssdk_area_china_sichuan_chengdu";
				public static final Chengdu INSTANCE = new Chengdu();

				private Chengdu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Nanchong extends City {
				public static final int CODE = 527;
				public static final String RES_NAME = "umssdk_area_china_sichuan_nanchong";
				public static final Nanchong INSTANCE = new Nanchong();

				private Nanchong() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Suining extends City {
				public static final int CODE = 529;
				public static final String RES_NAME = "umssdk_area_china_sichuan_suining";
				public static final Suining INSTANCE = new Suining();

				private Suining() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yaan extends City {
				public static final int CODE = 530;
				public static final String RES_NAME = "umssdk_area_china_sichuan_yaan";
				public static final Yaan INSTANCE = new Yaan();

				private Yaan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zigong extends City {
				public static final int CODE = 533;
				public static final String RES_NAME = "umssdk_area_china_sichuan_zigong";
				public static final Zigong INSTANCE = new Zigong();

				private Zigong() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Mianyang extends City {
				public static final int CODE = 525;
				public static final String RES_NAME = "umssdk_area_china_sichuan_mianyang";
				public static final Mianyang INSTANCE = new Mianyang();

				private Mianyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Hubei extends Province {
			public static final int CODE = 204;
			public static final String RES_NAME = "umssdk_area_china_hubei";
			public static final Hubei INSTANCE = new Hubei();

			private Hubei() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Ezhou.INSTANCE,
					Huanggang.INSTANCE,
					Huangshi.INSTANCE,
					Jingmen.INSTANCE,
					Jingzhou.INSTANCE,
					Shiyan.INSTANCE,
					Suizhou.INSTANCE,
					Wuhan.INSTANCE,
					Xianning.INSTANCE,
					Xiangyang.INSTANCE,
					Xiaogan.INSTANCE,
					Yichang.INSTANCE,
					Xiantao.INSTANCE,
					Qianjiang.INSTANCE,
					Tianmen.INSTANCE,
					Shennongjia.INSTANCE,
					Enshi.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Jingmen extends City {
				public static final int CODE = 377;
				public static final String RES_NAME = "umssdk_area_china_hubei_jingmen";
				public static final Jingmen INSTANCE = new Jingmen();

				private Jingmen() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xiaogan extends City {
				public static final int CODE = 384;
				public static final String RES_NAME = "umssdk_area_china_hubei_xiaogan";
				public static final Xiaogan INSTANCE = new Xiaogan();

				private Xiaogan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Enshi extends City {
				public static final int CODE = 619;
				public static final String RES_NAME = "umssdk_area_china_hubei_enshi";
				public static final Enshi INSTANCE = new Enshi();

				private Enshi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Qianjiang extends City {
				public static final int CODE = 616;
				public static final String RES_NAME = "umssdk_area_china_hubei_qianjiang";
				public static final Qianjiang INSTANCE = new Qianjiang();

				private Qianjiang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xianning extends City {
				public static final int CODE = 382;
				public static final String RES_NAME = "umssdk_area_china_hubei_xianning";
				public static final Xianning INSTANCE = new Xianning();

				private Xianning() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jingzhou extends City {
				public static final int CODE = 378;
				public static final String RES_NAME = "umssdk_area_china_hubei_jingzhou";
				public static final Jingzhou INSTANCE = new Jingzhou();

				private Jingzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Huangshi extends City {
				public static final int CODE = 376;
				public static final String RES_NAME = "umssdk_area_china_hubei_huangshi";
				public static final Huangshi INSTANCE = new Huangshi();

				private Huangshi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Huanggang extends City {
				public static final int CODE = 375;
				public static final String RES_NAME = "umssdk_area_china_hubei_huanggang";
				public static final Huanggang INSTANCE = new Huanggang();

				private Huanggang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Tianmen extends City {
				public static final int CODE = 617;
				public static final String RES_NAME = "umssdk_area_china_hubei_tianmen";
				public static final Tianmen INSTANCE = new Tianmen();

				private Tianmen() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Suizhou extends City {
				public static final int CODE = 380;
				public static final String RES_NAME = "umssdk_area_china_hubei_suizhou";
				public static final Suizhou INSTANCE = new Suizhou();

				private Suizhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Wuhan extends City {
				public static final int CODE = 381;
				public static final String RES_NAME = "umssdk_area_china_hubei_wuhan";
				public static final Wuhan INSTANCE = new Wuhan();

				private Wuhan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shennongjia extends City {
				public static final int CODE = 618;
				public static final String RES_NAME = "umssdk_area_china_hubei_shennongjia";
				public static final Shennongjia INSTANCE = new Shennongjia();

				private Shennongjia() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Ezhou extends City {
				public static final int CODE = 374;
				public static final String RES_NAME = "umssdk_area_china_hubei_ezhou";
				public static final Ezhou INSTANCE = new Ezhou();

				private Ezhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xiangyang extends City {
				public static final int CODE = 383;
				public static final String RES_NAME = "umssdk_area_china_hubei_xiangyang";
				public static final Xiangyang INSTANCE = new Xiangyang();

				private Xiangyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yichang extends City {
				public static final int CODE = 385;
				public static final String RES_NAME = "umssdk_area_china_hubei_yichang";
				public static final Yichang INSTANCE = new Yichang();

				private Yichang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shiyan extends City {
				public static final int CODE = 379;
				public static final String RES_NAME = "umssdk_area_china_hubei_shiyan";
				public static final Shiyan INSTANCE = new Shiyan();

				private Shiyan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xiantao extends City {
				public static final int CODE = 615;
				public static final String RES_NAME = "umssdk_area_china_hubei_xiantao";
				public static final Xiantao INSTANCE = new Xiantao();

				private Xiantao() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Jilin extends Province {
			public static final int CODE = 206;
			public static final String RES_NAME = "umssdk_area_china_jilin";
			public static final Jilin INSTANCE = new Jilin();

			private Jilin() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Baicheng.INSTANCE,
					WhiteMountain.INSTANCE,
					Changchun.INSTANCE,
					Liaoyuan.INSTANCE,
					Siping.INSTANCE,
					Songyuan.INSTANCE,
					Tonghua.INSTANCE,
					Yanbian.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Songyuan extends City {
				public static final int CODE = 405;
				public static final String RES_NAME = "umssdk_area_china_jilin_songyuan";
				public static final Songyuan INSTANCE = new Songyuan();

				private Songyuan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Tonghua extends City {
				public static final int CODE = 406;
				public static final String RES_NAME = "umssdk_area_china_jilin_tonghua";
				public static final Tonghua INSTANCE = new Tonghua();

				private Tonghua() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Baicheng extends City {
				public static final int CODE = 399;
				public static final String RES_NAME = "umssdk_area_china_jilin_baicheng";
				public static final Baicheng INSTANCE = new Baicheng();

				private Baicheng() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class WhiteMountain extends City {
				public static final int CODE = 400;
				public static final String RES_NAME = "umssdk_area_china_jilin_whitemountain";
				public static final WhiteMountain INSTANCE = new WhiteMountain();

				private WhiteMountain() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Liaoyuan extends City {
				public static final int CODE = 403;
				public static final String RES_NAME = "umssdk_area_china_jilin_liaoyuan";
				public static final Liaoyuan INSTANCE = new Liaoyuan();

				private Liaoyuan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Siping extends City {
				public static final int CODE = 404;
				public static final String RES_NAME = "umssdk_area_china_jilin_siping";
				public static final Siping INSTANCE = new Siping();

				private Siping() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yanbian extends City {
				public static final int CODE = 407;
				public static final String RES_NAME = "umssdk_area_china_jilin_yanbian";
				public static final Yanbian INSTANCE = new Yanbian();

				private Yanbian() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Changchun extends City {
				public static final int CODE = 401;
				public static final String RES_NAME = "umssdk_area_china_jilin_changchun";
				public static final Changchun INSTANCE = new Changchun();

				private Changchun() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Zhejiang extends Province {
			public static final int CODE = 223;
			public static final String RES_NAME = "umssdk_area_china_zhejiang";
			public static final Zhejiang INSTANCE = new Zhejiang();

			private Zhejiang() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Hangzhou.INSTANCE,
					Huzhou.INSTANCE,
					Jiaxing.INSTANCE,
					Jinhua.INSTANCE,
					Lishui.INSTANCE,
					Ningbo.INSTANCE,
					Quzhou.INSTANCE,
					Shaoxing.INSTANCE,
					Taizhou.INSTANCE,
					Wenzhou.INSTANCE,
					Zhoushan.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Taizhou extends City {
				public static final int CODE = 586;
				public static final String RES_NAME = "umssdk_area_china_zhejiang_taizhou";
				public static final Taizhou INSTANCE = new Taizhou();

				private Taizhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jinhua extends City {
				public static final int CODE = 581;
				public static final String RES_NAME = "umssdk_area_china_zhejiang_jinhua";
				public static final Jinhua INSTANCE = new Jinhua();

				private Jinhua() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Ningbo extends City {
				public static final int CODE = 583;
				public static final String RES_NAME = "umssdk_area_china_zhejiang_ningbo";
				public static final Ningbo INSTANCE = new Ningbo();

				private Ningbo() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhoushan extends City {
				public static final int CODE = 589;
				public static final String RES_NAME = "umssdk_area_china_zhejiang_zhoushan";
				public static final Zhoushan INSTANCE = new Zhoushan();

				private Zhoushan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jiaxing extends City {
				public static final int CODE = 580;
				public static final String RES_NAME = "umssdk_area_china_zhejiang_jiaxing";
				public static final Jiaxing INSTANCE = new Jiaxing();

				private Jiaxing() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Huzhou extends City {
				public static final int CODE = 579;
				public static final String RES_NAME = "umssdk_area_china_zhejiang_huzhou";
				public static final Huzhou INSTANCE = new Huzhou();

				private Huzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shaoxing extends City {
				public static final int CODE = 585;
				public static final String RES_NAME = "umssdk_area_china_zhejiang_shaoxing";
				public static final Shaoxing INSTANCE = new Shaoxing();

				private Shaoxing() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Quzhou extends City {
				public static final int CODE = 584;
				public static final String RES_NAME = "umssdk_area_china_zhejiang_quzhou";
				public static final Quzhou INSTANCE = new Quzhou();

				private Quzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Wenzhou extends City {
				public static final int CODE = 587;
				public static final String RES_NAME = "umssdk_area_china_zhejiang_wenzhou";
				public static final Wenzhou INSTANCE = new Wenzhou();

				private Wenzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Lishui extends City {
				public static final int CODE = 582;
				public static final String RES_NAME = "umssdk_area_china_zhejiang_lishui";
				public static final Lishui INSTANCE = new Lishui();

				private Lishui() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hangzhou extends City {
				public static final int CODE = 578;
				public static final String RES_NAME = "umssdk_area_china_zhejiang_hangzhou";
				public static final Hangzhou INSTANCE = new Hangzhou();

				private Hangzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Chongqing extends Province {
			public static final int CODE = 224;
			public static final String RES_NAME = "umssdk_area_china_chongqing";
			public static final Chongqing INSTANCE = new Chongqing();

			private Chongqing() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

		}

		public static final class Heilongjiang extends Province {
			public static final int CODE = 203;
			public static final String RES_NAME = "umssdk_area_china_heilongjiang";
			public static final Heilongjiang INSTANCE = new Heilongjiang();

			private Heilongjiang() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Daqing.INSTANCE,
					Daxinganling.INSTANCE,
					Harbin.INSTANCE,
					Hegang.INSTANCE,
					Heihe.INSTANCE,
					Jixi.INSTANCE,
					Jiamusi.INSTANCE,
					Mudanjiang.INSTANCE,
					Qitaihe.INSTANCE,
					Qiqihar.INSTANCE,
					Shuangyashan.INSTANCE,
					Suihua.INSTANCE,
					Yichun.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Jiamusi extends City {
				public static final int CODE = 364;
				public static final String RES_NAME = "umssdk_area_china_heilongjiang_jiamusi";
				public static final Jiamusi INSTANCE = new Jiamusi();

				private Jiamusi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Qiqihar extends City {
				public static final int CODE = 368;
				public static final String RES_NAME = "umssdk_area_china_heilongjiang_qiqihar";
				public static final Qiqihar INSTANCE = new Qiqihar();

				private Qiqihar() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Harbin extends City {
				public static final int CODE = 360;
				public static final String RES_NAME = "umssdk_area_china_heilongjiang_harbin";
				public static final Harbin INSTANCE = new Harbin();

				private Harbin() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Suihua extends City {
				public static final int CODE = 371;
				public static final String RES_NAME = "umssdk_area_china_heilongjiang_suihua";
				public static final Suihua INSTANCE = new Suihua();

				private Suihua() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Daxinganling extends City {
				public static final int CODE = 359;
				public static final String RES_NAME = "umssdk_area_china_heilongjiang_daxinganling";
				public static final Daxinganling INSTANCE = new Daxinganling();

				private Daxinganling() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hegang extends City {
				public static final int CODE = 361;
				public static final String RES_NAME = "umssdk_area_china_heilongjiang_hegang";
				public static final Hegang INSTANCE = new Hegang();

				private Hegang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shuangyashan extends City {
				public static final int CODE = 370;
				public static final String RES_NAME = "umssdk_area_china_heilongjiang_shuangyashan";
				public static final Shuangyashan INSTANCE = new Shuangyashan();

				private Shuangyashan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Heihe extends City {
				public static final int CODE = 362;
				public static final String RES_NAME = "umssdk_area_china_heilongjiang_heihe";
				public static final Heihe INSTANCE = new Heihe();

				private Heihe() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jixi extends City {
				public static final int CODE = 363;
				public static final String RES_NAME = "umssdk_area_china_heilongjiang_jixi";
				public static final Jixi INSTANCE = new Jixi();

				private Jixi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Qitaihe extends City {
				public static final int CODE = 367;
				public static final String RES_NAME = "umssdk_area_china_heilongjiang_qitaihe";
				public static final Qitaihe INSTANCE = new Qitaihe();

				private Qitaihe() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Mudanjiang extends City {
				public static final int CODE = 366;
				public static final String RES_NAME = "umssdk_area_china_heilongjiang_mudanjiang";
				public static final Mudanjiang INSTANCE = new Mudanjiang();

				private Mudanjiang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Daqing extends City {
				public static final int CODE = 358;
				public static final String RES_NAME = "umssdk_area_china_heilongjiang_daqing";
				public static final Daqing INSTANCE = new Daqing();

				private Daqing() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yichun extends City {
				public static final int CODE = 373;
				public static final String RES_NAME = "umssdk_area_china_heilongjiang_yichun";
				public static final Yichun INSTANCE = new Yichun();

				private Yichun() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Shanxi extends Province {
			public static final int CODE = 214;
			public static final String RES_NAME = "umssdk_area_china_shanxi";
			public static final Shanxi INSTANCE = new Shanxi();

			private Shanxi() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Changzhi.INSTANCE,
					Datong.INSTANCE,
					Jincheng.INSTANCE,
					Jinzhong.INSTANCE,
					Linfen.INSTANCE,
					Lvliang.INSTANCE,
					Shuozhou.INSTANCE,
					Taiyuan.INSTANCE,
					Xinzhou.INSTANCE,
					Yangquan.INSTANCE,
					Yuncheng.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Jincheng extends City {
				public static final int CODE = 494;
				public static final String RES_NAME = "umssdk_area_china_shanxi_jincheng";
				public static final Jincheng INSTANCE = new Jincheng();

				private Jincheng() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Taiyuan extends City {
				public static final int CODE = 499;
				public static final String RES_NAME = "umssdk_area_china_shanxi_taiyuan";
				public static final Taiyuan INSTANCE = new Taiyuan();

				private Taiyuan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Linfen extends City {
				public static final int CODE = 496;
				public static final String RES_NAME = "umssdk_area_china_shanxi_linfen";
				public static final Linfen INSTANCE = new Linfen();

				private Linfen() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shuozhou extends City {
				public static final int CODE = 498;
				public static final String RES_NAME = "umssdk_area_china_shanxi_shuozhou";
				public static final Shuozhou INSTANCE = new Shuozhou();

				private Shuozhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Datong extends City {
				public static final int CODE = 493;
				public static final String RES_NAME = "umssdk_area_china_shanxi_datong";
				public static final Datong INSTANCE = new Datong();

				private Datong() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yangquan extends City {
				public static final int CODE = 501;
				public static final String RES_NAME = "umssdk_area_china_shanxi_yangquan";
				public static final Yangquan INSTANCE = new Yangquan();

				private Yangquan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jinzhong extends City {
				public static final int CODE = 495;
				public static final String RES_NAME = "umssdk_area_china_shanxi_jinzhong";
				public static final Jinzhong INSTANCE = new Jinzhong();

				private Jinzhong() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xinzhou extends City {
				public static final int CODE = 500;
				public static final String RES_NAME = "umssdk_area_china_shanxi_xinzhou";
				public static final Xinzhou INSTANCE = new Xinzhou();

				private Xinzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Lvliang extends City {
				public static final int CODE = 497;
				public static final String RES_NAME = "umssdk_area_china_shanxi_lvliang";
				public static final Lvliang INSTANCE = new Lvliang();

				private Lvliang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Changzhi extends City {
				public static final int CODE = 492;
				public static final String RES_NAME = "umssdk_area_china_shanxi_changzhi";
				public static final Changzhi INSTANCE = new Changzhi();

				private Changzhi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yuncheng extends City {
				public static final int CODE = 502;
				public static final String RES_NAME = "umssdk_area_china_shanxi_yuncheng";
				public static final Yuncheng INSTANCE = new Yuncheng();

				private Yuncheng() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Guangdong extends Province {
			public static final int CODE = 197;
			public static final String RES_NAME = "umssdk_area_china_guangdong";
			public static final Guangdong INSTANCE = new Guangdong();

			private Guangdong() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Chaozhou.INSTANCE,
					Dongguan.INSTANCE,
					Foshan.INSTANCE,
					Guangzhou.INSTANCE,
					Heyuan.INSTANCE,
					Huizhou.INSTANCE,
					Jiangmen.INSTANCE,
					Jieyang.INSTANCE,
					Maoming.INSTANCE,
					Meizhou.INSTANCE,
					Qingyuan.INSTANCE,
					Shantou.INSTANCE,
					Shanwei.INSTANCE,
					Shaoguan.INSTANCE,
					Shenzhen.INSTANCE,
					Yangjiang.INSTANCE,
					Yunfu.INSTANCE,
					Zhanjiang.INSTANCE,
					Zhaoqing.INSTANCE,
					Zhongshan.INSTANCE,
					Zhuhai.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Zhanjiang extends City {
				public static final int CODE = 284;
				public static final String RES_NAME = "umssdk_area_china_guangdong_zhanjiang";
				public static final Zhanjiang INSTANCE = new Zhanjiang();

				private Zhanjiang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Heyuan extends City {
				public static final int CODE = 271;
				public static final String RES_NAME = "umssdk_area_china_guangdong_heyuan";
				public static final Heyuan INSTANCE = new Heyuan();

				private Heyuan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shaoguan extends City {
				public static final int CODE = 280;
				public static final String RES_NAME = "umssdk_area_china_guangdong_shaoguan";
				public static final Shaoguan INSTANCE = new Shaoguan();

				private Shaoguan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhaoqing extends City {
				public static final int CODE = 285;
				public static final String RES_NAME = "umssdk_area_china_guangdong_zhaoqing";
				public static final Zhaoqing INSTANCE = new Zhaoqing();

				private Zhaoqing() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Maoming extends City {
				public static final int CODE = 275;
				public static final String RES_NAME = "umssdk_area_china_guangdong_maoming";
				public static final Maoming INSTANCE = new Maoming();

				private Maoming() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jieyang extends City {
				public static final int CODE = 274;
				public static final String RES_NAME = "umssdk_area_china_guangdong_jieyang";
				public static final Jieyang INSTANCE = new Jieyang();

				private Jieyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Dongguan extends City {
				public static final int CODE = 268;
				public static final String RES_NAME = "umssdk_area_china_guangdong_dongguan";
				public static final Dongguan INSTANCE = new Dongguan();

				private Dongguan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhuhai extends City {
				public static final int CODE = 287;
				public static final String RES_NAME = "umssdk_area_china_guangdong_zhuhai";
				public static final Zhuhai INSTANCE = new Zhuhai();

				private Zhuhai() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Foshan extends City {
				public static final int CODE = 269;
				public static final String RES_NAME = "umssdk_area_china_guangdong_foshan";
				public static final Foshan INSTANCE = new Foshan();

				private Foshan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yangjiang extends City {
				public static final int CODE = 282;
				public static final String RES_NAME = "umssdk_area_china_guangdong_yangjiang";
				public static final Yangjiang INSTANCE = new Yangjiang();

				private Yangjiang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhongshan extends City {
				public static final int CODE = 286;
				public static final String RES_NAME = "umssdk_area_china_guangdong_zhongshan";
				public static final Zhongshan INSTANCE = new Zhongshan();

				private Zhongshan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jiangmen extends City {
				public static final int CODE = 273;
				public static final String RES_NAME = "umssdk_area_china_guangdong_jiangmen";
				public static final Jiangmen INSTANCE = new Jiangmen();

				private Jiangmen() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Chaozhou extends City {
				public static final int CODE = 267;
				public static final String RES_NAME = "umssdk_area_china_guangdong_chaozhou";
				public static final Chaozhou INSTANCE = new Chaozhou();

				private Chaozhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Huizhou extends City {
				public static final int CODE = 272;
				public static final String RES_NAME = "umssdk_area_china_guangdong_huizhou";
				public static final Huizhou INSTANCE = new Huizhou();

				private Huizhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Guangzhou extends City {
				public static final int CODE = 270;
				public static final String RES_NAME = "umssdk_area_china_guangdong_guangzhou";
				public static final Guangzhou INSTANCE = new Guangzhou();

				private Guangzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Meizhou extends City {
				public static final int CODE = 276;
				public static final String RES_NAME = "umssdk_area_china_guangdong_meizhou";
				public static final Meizhou INSTANCE = new Meizhou();

				private Meizhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Qingyuan extends City {
				public static final int CODE = 277;
				public static final String RES_NAME = "umssdk_area_china_guangdong_qingyuan";
				public static final Qingyuan INSTANCE = new Qingyuan();

				private Qingyuan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yunfu extends City {
				public static final int CODE = 283;
				public static final String RES_NAME = "umssdk_area_china_guangdong_yunfu";
				public static final Yunfu INSTANCE = new Yunfu();

				private Yunfu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shanwei extends City {
				public static final int CODE = 279;
				public static final String RES_NAME = "umssdk_area_china_guangdong_shanwei";
				public static final Shanwei INSTANCE = new Shanwei();

				private Shanwei() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shantou extends City {
				public static final int CODE = 278;
				public static final String RES_NAME = "umssdk_area_china_guangdong_shantou";
				public static final Shantou INSTANCE = new Shantou();

				private Shantou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shenzhen extends City {
				public static final int CODE = 281;
				public static final String RES_NAME = "umssdk_area_china_guangdong_shenzhen";
				public static final Shenzhen INSTANCE = new Shenzhen();

				private Shenzhen() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Liaoning extends Province {
			public static final int CODE = 209;
			public static final String RES_NAME = "umssdk_area_china_liaoning";
			public static final Liaoning INSTANCE = new Liaoning();

			private Liaoning() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Anshan.INSTANCE,
					Benxi.INSTANCE,
					Chaoyang.INSTANCE,
					Dalian.INSTANCE,
					Dandong.INSTANCE,
					Fushun.INSTANCE,
					Fuxin.INSTANCE,
					Huludao.INSTANCE,
					Jinzhou.INSTANCE,
					Liaoyang.INSTANCE,
					Panjin.INSTANCE,
					Shenyang.INSTANCE,
					Tieling.INSTANCE,
					Yingkou.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Anshan extends City {
				public static final int CODE = 433;
				public static final String RES_NAME = "umssdk_area_china_liaoning_anshan";
				public static final Anshan INSTANCE = new Anshan();

				private Anshan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jinzhou extends City {
				public static final int CODE = 441;
				public static final String RES_NAME = "umssdk_area_china_liaoning_jinzhou";
				public static final Jinzhou INSTANCE = new Jinzhou();

				private Jinzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Fushun extends City {
				public static final int CODE = 438;
				public static final String RES_NAME = "umssdk_area_china_liaoning_fushun";
				public static final Fushun INSTANCE = new Fushun();

				private Fushun() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Chaoyang extends City {
				public static final int CODE = 435;
				public static final String RES_NAME = "umssdk_area_china_liaoning_chaoyang";
				public static final Chaoyang INSTANCE = new Chaoyang();

				private Chaoyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Huludao extends City {
				public static final int CODE = 440;
				public static final String RES_NAME = "umssdk_area_china_liaoning_huludao";
				public static final Huludao INSTANCE = new Huludao();

				private Huludao() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Benxi extends City {
				public static final int CODE = 434;
				public static final String RES_NAME = "umssdk_area_china_liaoning_benxi";
				public static final Benxi INSTANCE = new Benxi();

				private Benxi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Liaoyang extends City {
				public static final int CODE = 442;
				public static final String RES_NAME = "umssdk_area_china_liaoning_liaoyang";
				public static final Liaoyang INSTANCE = new Liaoyang();

				private Liaoyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Dalian extends City {
				public static final int CODE = 436;
				public static final String RES_NAME = "umssdk_area_china_liaoning_dalian";
				public static final Dalian INSTANCE = new Dalian();

				private Dalian() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shenyang extends City {
				public static final int CODE = 444;
				public static final String RES_NAME = "umssdk_area_china_liaoning_shenyang";
				public static final Shenyang INSTANCE = new Shenyang();

				private Shenyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Panjin extends City {
				public static final int CODE = 443;
				public static final String RES_NAME = "umssdk_area_china_liaoning_panjin";
				public static final Panjin INSTANCE = new Panjin();

				private Panjin() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Tieling extends City {
				public static final int CODE = 445;
				public static final String RES_NAME = "umssdk_area_china_liaoning_tieling";
				public static final Tieling INSTANCE = new Tieling();

				private Tieling() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yingkou extends City {
				public static final int CODE = 446;
				public static final String RES_NAME = "umssdk_area_china_liaoning_yingkou";
				public static final Yingkou INSTANCE = new Yingkou();

				private Yingkou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Dandong extends City {
				public static final int CODE = 437;
				public static final String RES_NAME = "umssdk_area_china_liaoning_dandong";
				public static final Dandong INSTANCE = new Dandong();

				private Dandong() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Fuxin extends City {
				public static final int CODE = 439;
				public static final String RES_NAME = "umssdk_area_china_liaoning_fuxin";
				public static final Fuxin INSTANCE = new Fuxin();

				private Fuxin() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Hunan extends Province {
			public static final int CODE = 205;
			public static final String RES_NAME = "umssdk_area_china_hunan";
			public static final Hunan INSTANCE = new Hunan();

			private Hunan() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Changsha.INSTANCE,
					Changde.INSTANCE,
					Chenzhou.INSTANCE,
					Hengyang.INSTANCE,
					Huaihua.INSTANCE,
					Loudi.INSTANCE,
					Shaoyang.INSTANCE,
					Xiangtan.INSTANCE,
					Yiyang.INSTANCE,
					Yongzhou.INSTANCE,
					Yueyang.INSTANCE,
					Zhangjiajie.INSTANCE,
					Zhuzhou.INSTANCE,
					Xiangxi.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Shaoyang extends City {
				public static final int CODE = 392;
				public static final String RES_NAME = "umssdk_area_china_hunan_shaoyang";
				public static final Shaoyang INSTANCE = new Shaoyang();

				private Shaoyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xiangxi extends City {
				public static final int CODE = 620;
				public static final String RES_NAME = "umssdk_area_china_hunan_xiangxi";
				public static final Xiangxi INSTANCE = new Xiangxi();

				private Xiangxi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xiangtan extends City {
				public static final int CODE = 393;
				public static final String RES_NAME = "umssdk_area_china_hunan_xiangtan";
				public static final Xiangtan INSTANCE = new Xiangtan();

				private Xiangtan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhuzhou extends City {
				public static final int CODE = 398;
				public static final String RES_NAME = "umssdk_area_china_hunan_zhuzhou";
				public static final Zhuzhou INSTANCE = new Zhuzhou();

				private Zhuzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Changsha extends City {
				public static final int CODE = 386;
				public static final String RES_NAME = "umssdk_area_china_hunan_changsha";
				public static final Changsha INSTANCE = new Changsha();

				private Changsha() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yiyang extends City {
				public static final int CODE = 394;
				public static final String RES_NAME = "umssdk_area_china_hunan_yiyang";
				public static final Yiyang INSTANCE = new Yiyang();

				private Yiyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Huaihua extends City {
				public static final int CODE = 390;
				public static final String RES_NAME = "umssdk_area_china_hunan_huaihua";
				public static final Huaihua INSTANCE = new Huaihua();

				private Huaihua() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yueyang extends City {
				public static final int CODE = 396;
				public static final String RES_NAME = "umssdk_area_china_hunan_yueyang";
				public static final Yueyang INSTANCE = new Yueyang();

				private Yueyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hengyang extends City {
				public static final int CODE = 389;
				public static final String RES_NAME = "umssdk_area_china_hunan_hengyang";
				public static final Hengyang INSTANCE = new Hengyang();

				private Hengyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Loudi extends City {
				public static final int CODE = 391;
				public static final String RES_NAME = "umssdk_area_china_hunan_loudi";
				public static final Loudi INSTANCE = new Loudi();

				private Loudi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Changde extends City {
				public static final int CODE = 387;
				public static final String RES_NAME = "umssdk_area_china_hunan_changde";
				public static final Changde INSTANCE = new Changde();

				private Changde() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Chenzhou extends City {
				public static final int CODE = 388;
				public static final String RES_NAME = "umssdk_area_china_hunan_chenzhou";
				public static final Chenzhou INSTANCE = new Chenzhou();

				private Chenzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhangjiajie extends City {
				public static final int CODE = 397;
				public static final String RES_NAME = "umssdk_area_china_hunan_zhangjiajie";
				public static final Zhangjiajie INSTANCE = new Zhangjiajie();

				private Zhangjiajie() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yongzhou extends City {
				public static final int CODE = 395;
				public static final String RES_NAME = "umssdk_area_china_hunan_yongzhou";
				public static final Yongzhou INSTANCE = new Yongzhou();

				private Yongzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Shanghai extends Province {
			public static final int CODE = 216;
			public static final String RES_NAME = "umssdk_area_china_shanghai";
			public static final Shanghai INSTANCE = new Shanghai();

			private Shanghai() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

		}

		public static final class InnerMongolia extends Province {
			public static final int CODE = 210;
			public static final String RES_NAME = "umssdk_area_china_innermongolia";
			public static final InnerMongolia INSTANCE = new InnerMongolia();

			private InnerMongolia() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Alashan.INSTANCE,
					Bayannaoer.INSTANCE,
					Baotou.INSTANCE,
					Chifeng.INSTANCE,
					Erdos.INSTANCE,
					Hohhot.INSTANCE,
					Hulunbeier.INSTANCE,
					Tongliao.INSTANCE,
					Wuhai.INSTANCE,
					Wulanchabu.INSTANCE,
					XilinGol.INSTANCE,
					Xingan.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Baotou extends City {
				public static final int CODE = 449;
				public static final String RES_NAME = "umssdk_area_china_innermongolia_baotou";
				public static final Baotou INSTANCE = new Baotou();

				private Baotou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Wuhai extends City {
				public static final int CODE = 455;
				public static final String RES_NAME = "umssdk_area_china_innermongolia_wuhai";
				public static final Wuhai INSTANCE = new Wuhai();

				private Wuhai() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xingan extends City {
				public static final int CODE = 458;
				public static final String RES_NAME = "umssdk_area_china_innermongolia_xingan";
				public static final Xingan INSTANCE = new Xingan();

				private Xingan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Wulanchabu extends City {
				public static final int CODE = 456;
				public static final String RES_NAME = "umssdk_area_china_innermongolia_wulanchabu";
				public static final Wulanchabu INSTANCE = new Wulanchabu();

				private Wulanchabu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Alashan extends City {
				public static final int CODE = 447;
				public static final String RES_NAME = "umssdk_area_china_innermongolia_alashan";
				public static final Alashan INSTANCE = new Alashan();

				private Alashan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hohhot extends City {
				public static final int CODE = 452;
				public static final String RES_NAME = "umssdk_area_china_innermongolia_hohhot";
				public static final Hohhot INSTANCE = new Hohhot();

				private Hohhot() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Tongliao extends City {
				public static final int CODE = 454;
				public static final String RES_NAME = "umssdk_area_china_innermongolia_tongliao";
				public static final Tongliao INSTANCE = new Tongliao();

				private Tongliao() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Bayannaoer extends City {
				public static final int CODE = 448;
				public static final String RES_NAME = "umssdk_area_china_innermongolia_bayannaoer";
				public static final Bayannaoer INSTANCE = new Bayannaoer();

				private Bayannaoer() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Erdos extends City {
				public static final int CODE = 451;
				public static final String RES_NAME = "umssdk_area_china_innermongolia_erdos";
				public static final Erdos INSTANCE = new Erdos();

				private Erdos() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hulunbeier extends City {
				public static final int CODE = 453;
				public static final String RES_NAME = "umssdk_area_china_innermongolia_hulunbeier";
				public static final Hulunbeier INSTANCE = new Hulunbeier();

				private Hulunbeier() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Chifeng extends City {
				public static final int CODE = 450;
				public static final String RES_NAME = "umssdk_area_china_innermongolia_chifeng";
				public static final Chifeng INSTANCE = new Chifeng();

				private Chifeng() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class XilinGol extends City {
				public static final int CODE = 457;
				public static final String RES_NAME = "umssdk_area_china_innermongolia_xilingol";
				public static final XilinGol INSTANCE = new XilinGol();

				private XilinGol() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Shandong extends Province {
			public static final int CODE = 213;
			public static final String RES_NAME = "umssdk_area_china_shandong";
			public static final Shandong INSTANCE = new Shandong();

			private Shandong() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Binzhou.INSTANCE,
					Dezhou.INSTANCE,
					Dongying.INSTANCE,
					Heze.INSTANCE,
					Jinan.INSTANCE,
					Jining.INSTANCE,
					Laiwu.INSTANCE,
					Liaocheng.INSTANCE,
					Linyi.INSTANCE,
					Qingdao.INSTANCE,
					Rizhao.INSTANCE,
					Taian.INSTANCE,
					Weihai.INSTANCE,
					Weifang.INSTANCE,
					Yantai.INSTANCE,
					Zaozhuang.INSTANCE,
					Zibo.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Weifang extends City {
				public static final int CODE = 488;
				public static final String RES_NAME = "umssdk_area_china_shandong_weifang";
				public static final Weifang INSTANCE = new Weifang();

				private Weifang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Linyi extends City {
				public static final int CODE = 481;
				public static final String RES_NAME = "umssdk_area_china_shandong_linyi";
				public static final Linyi INSTANCE = new Linyi();

				private Linyi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Weihai extends City {
				public static final int CODE = 487;
				public static final String RES_NAME = "umssdk_area_china_shandong_weihai";
				public static final Weihai INSTANCE = new Weihai();

				private Weihai() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Dezhou extends City {
				public static final int CODE = 474;
				public static final String RES_NAME = "umssdk_area_china_shandong_dezhou";
				public static final Dezhou INSTANCE = new Dezhou();

				private Dezhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Taian extends City {
				public static final int CODE = 486;
				public static final String RES_NAME = "umssdk_area_china_shandong_taian";
				public static final Taian INSTANCE = new Taian();

				private Taian() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Qingdao extends City {
				public static final int CODE = 483;
				public static final String RES_NAME = "umssdk_area_china_shandong_qingdao";
				public static final Qingdao INSTANCE = new Qingdao();

				private Qingdao() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Heze extends City {
				public static final int CODE = 476;
				public static final String RES_NAME = "umssdk_area_china_shandong_heze";
				public static final Heze INSTANCE = new Heze();

				private Heze() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Laiwu extends City {
				public static final int CODE = 479;
				public static final String RES_NAME = "umssdk_area_china_shandong_laiwu";
				public static final Laiwu INSTANCE = new Laiwu();

				private Laiwu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zaozhuang extends City {
				public static final int CODE = 490;
				public static final String RES_NAME = "umssdk_area_china_shandong_zaozhuang";
				public static final Zaozhuang INSTANCE = new Zaozhuang();

				private Zaozhuang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Dongying extends City {
				public static final int CODE = 475;
				public static final String RES_NAME = "umssdk_area_china_shandong_dongying";
				public static final Dongying INSTANCE = new Dongying();

				private Dongying() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jining extends City {
				public static final int CODE = 478;
				public static final String RES_NAME = "umssdk_area_china_shandong_jining";
				public static final Jining INSTANCE = new Jining();

				private Jining() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Binzhou extends City {
				public static final int CODE = 473;
				public static final String RES_NAME = "umssdk_area_china_shandong_binzhou";
				public static final Binzhou INSTANCE = new Binzhou();

				private Binzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zibo extends City {
				public static final int CODE = 491;
				public static final String RES_NAME = "umssdk_area_china_shandong_zibo";
				public static final Zibo INSTANCE = new Zibo();

				private Zibo() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jinan extends City {
				public static final int CODE = 477;
				public static final String RES_NAME = "umssdk_area_china_shandong_jinan";
				public static final Jinan INSTANCE = new Jinan();

				private Jinan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Liaocheng extends City {
				public static final int CODE = 480;
				public static final String RES_NAME = "umssdk_area_china_shandong_liaocheng";
				public static final Liaocheng INSTANCE = new Liaocheng();

				private Liaocheng() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Rizhao extends City {
				public static final int CODE = 485;
				public static final String RES_NAME = "umssdk_area_china_shandong_rizhao";
				public static final Rizhao INSTANCE = new Rizhao();

				private Rizhao() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yantai extends City {
				public static final int CODE = 489;
				public static final String RES_NAME = "umssdk_area_china_shandong_yantai";
				public static final Yantai INSTANCE = new Yantai();

				private Yantai() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Yunnan extends Province {
			public static final int CODE = 222;
			public static final String RES_NAME = "umssdk_area_china_yunnan";
			public static final Yunnan INSTANCE = new Yunnan();

			private Yunnan() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Baoshan.INSTANCE,
					Chuxiong.INSTANCE,
					Dali.INSTANCE,
					Dehong.INSTANCE,
					Diqing.INSTANCE,
					Honghe.INSTANCE,
					Kunming.INSTANCE,
					Lijiang.INSTANCE,
					Lincang.INSTANCE,
					Nujiang.INSTANCE,
					Puer.INSTANCE,
					Qujing.INSTANCE,
					Wenshan.INSTANCE,
					Xishuangbanna.INSTANCE,
					Yuxi.INSTANCE,
					Zhaotong.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Baoshan extends City {
				public static final int CODE = 560;
				public static final String RES_NAME = "umssdk_area_china_yunnan_baoshan";
				public static final Baoshan INSTANCE = new Baoshan();

				private Baoshan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Kunming extends City {
				public static final int CODE = 566;
				public static final String RES_NAME = "umssdk_area_china_yunnan_kunming";
				public static final Kunming INSTANCE = new Kunming();

				private Kunming() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xishuangbanna extends City {
				public static final int CODE = 574;
				public static final String RES_NAME = "umssdk_area_china_yunnan_xishuangbanna";
				public static final Xishuangbanna INSTANCE = new Xishuangbanna();

				private Xishuangbanna() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Nujiang extends City {
				public static final int CODE = 569;
				public static final String RES_NAME = "umssdk_area_china_yunnan_nujiang";
				public static final Nujiang INSTANCE = new Nujiang();

				private Nujiang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Qujing extends City {
				public static final int CODE = 571;
				public static final String RES_NAME = "umssdk_area_china_yunnan_qujing";
				public static final Qujing INSTANCE = new Qujing();

				private Qujing() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yuxi extends City {
				public static final int CODE = 575;
				public static final String RES_NAME = "umssdk_area_china_yunnan_yuxi";
				public static final Yuxi INSTANCE = new Yuxi();

				private Yuxi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Dehong extends City {
				public static final int CODE = 563;
				public static final String RES_NAME = "umssdk_area_china_yunnan_dehong";
				public static final Dehong INSTANCE = new Dehong();

				private Dehong() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Lincang extends City {
				public static final int CODE = 568;
				public static final String RES_NAME = "umssdk_area_china_yunnan_lincang";
				public static final Lincang INSTANCE = new Lincang();

				private Lincang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Chuxiong extends City {
				public static final int CODE = 561;
				public static final String RES_NAME = "umssdk_area_china_yunnan_chuxiong";
				public static final Chuxiong INSTANCE = new Chuxiong();

				private Chuxiong() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Diqing extends City {
				public static final int CODE = 564;
				public static final String RES_NAME = "umssdk_area_china_yunnan_diqing";
				public static final Diqing INSTANCE = new Diqing();

				private Diqing() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Honghe extends City {
				public static final int CODE = 565;
				public static final String RES_NAME = "umssdk_area_china_yunnan_honghe";
				public static final Honghe INSTANCE = new Honghe();

				private Honghe() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Lijiang extends City {
				public static final int CODE = 567;
				public static final String RES_NAME = "umssdk_area_china_yunnan_lijiang";
				public static final Lijiang INSTANCE = new Lijiang();

				private Lijiang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Puer extends City {
				public static final int CODE = 570;
				public static final String RES_NAME = "umssdk_area_china_yunnan_puer";
				public static final Puer INSTANCE = new Puer();

				private Puer() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Wenshan extends City {
				public static final int CODE = 573;
				public static final String RES_NAME = "umssdk_area_china_yunnan_wenshan";
				public static final Wenshan INSTANCE = new Wenshan();

				private Wenshan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Dali extends City {
				public static final int CODE = 562;
				public static final String RES_NAME = "umssdk_area_china_yunnan_dali";
				public static final Dali INSTANCE = new Dali();

				private Dali() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhaotong extends City {
				public static final int CODE = 576;
				public static final String RES_NAME = "umssdk_area_china_yunnan_zhaotong";
				public static final Zhaotong INSTANCE = new Zhaotong();

				private Zhaotong() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Guizhou extends Province {
			public static final int CODE = 199;
			public static final String RES_NAME = "umssdk_area_china_guizhou";
			public static final Guizhou INSTANCE = new Guizhou();

			private Guizhou() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Anshun.INSTANCE,
					Bijie.INSTANCE,
					Guiyang.INSTANCE,
					Liupanshui.INSTANCE,
					Qiandongnan.INSTANCE,
					Qiannan.INSTANCE,
					SouthwestofGuizhou.INSTANCE,
					Tongren.INSTANCE,
					Zunyi.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Qiandongnan extends City {
				public static final int CODE = 306;
				public static final String RES_NAME = "umssdk_area_china_guizhou_qiandongnan";
				public static final Qiandongnan INSTANCE = new Qiandongnan();

				private Qiandongnan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Liupanshui extends City {
				public static final int CODE = 305;
				public static final String RES_NAME = "umssdk_area_china_guizhou_liupanshui";
				public static final Liupanshui INSTANCE = new Liupanshui();

				private Liupanshui() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Tongren extends City {
				public static final int CODE = 309;
				public static final String RES_NAME = "umssdk_area_china_guizhou_tongren";
				public static final Tongren INSTANCE = new Tongren();

				private Tongren() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Anshun extends City {
				public static final int CODE = 302;
				public static final String RES_NAME = "umssdk_area_china_guizhou_anshun";
				public static final Anshun INSTANCE = new Anshun();

				private Anshun() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Qiannan extends City {
				public static final int CODE = 307;
				public static final String RES_NAME = "umssdk_area_china_guizhou_qiannan";
				public static final Qiannan INSTANCE = new Qiannan();

				private Qiannan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class SouthwestofGuizhou extends City {
				public static final int CODE = 308;
				public static final String RES_NAME = "umssdk_area_china_guizhou_southwestofguizhou";
				public static final SouthwestofGuizhou INSTANCE = new SouthwestofGuizhou();

				private SouthwestofGuizhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zunyi extends City {
				public static final int CODE = 310;
				public static final String RES_NAME = "umssdk_area_china_guizhou_zunyi";
				public static final Zunyi INSTANCE = new Zunyi();

				private Zunyi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Bijie extends City {
				public static final int CODE = 303;
				public static final String RES_NAME = "umssdk_area_china_guizhou_bijie";
				public static final Bijie INSTANCE = new Bijie();

				private Bijie() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Guiyang extends City {
				public static final int CODE = 304;
				public static final String RES_NAME = "umssdk_area_china_guizhou_guiyang";
				public static final Guiyang INSTANCE = new Guiyang();

				private Guiyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Ningxia extends Province {
			public static final int CODE = 211;
			public static final String RES_NAME = "umssdk_area_china_ningxia";
			public static final Ningxia INSTANCE = new Ningxia();

			private Ningxia() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Guyuan.INSTANCE,
					Shizuishan.INSTANCE,
					Wuzhong.INSTANCE,
					Yinchuan.INSTANCE,
					Zhongwei.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Wuzhong extends City {
				public static final int CODE = 461;
				public static final String RES_NAME = "umssdk_area_china_ningxia_wuzhong";
				public static final Wuzhong INSTANCE = new Wuzhong();

				private Wuzhong() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Guyuan extends City {
				public static final int CODE = 459;
				public static final String RES_NAME = "umssdk_area_china_ningxia_guyuan";
				public static final Guyuan INSTANCE = new Guyuan();

				private Guyuan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shizuishan extends City {
				public static final int CODE = 460;
				public static final String RES_NAME = "umssdk_area_china_ningxia_shizuishan";
				public static final Shizuishan INSTANCE = new Shizuishan();

				private Shizuishan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhongwei extends City {
				public static final int CODE = 463;
				public static final String RES_NAME = "umssdk_area_china_ningxia_zhongwei";
				public static final Zhongwei INSTANCE = new Zhongwei();

				private Zhongwei() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yinchuan extends City {
				public static final int CODE = 462;
				public static final String RES_NAME = "umssdk_area_china_ningxia_yinchuan";
				public static final Yinchuan INSTANCE = new Yinchuan();

				private Yinchuan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Hebei extends Province {
			public static final int CODE = 201;
			public static final String RES_NAME = "umssdk_area_china_hebei";
			public static final Hebei INSTANCE = new Hebei();

			private Hebei() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Baoding.INSTANCE,
					Cangzhou.INSTANCE,
					Chengde.INSTANCE,
					Handan.INSTANCE,
					Hengshui.INSTANCE,
					Langfang.INSTANCE,
					Qinhuangdao.INSTANCE,
					Shijiazhuang.INSTANCE,
					Tangshan.INSTANCE,
					Xingtai.INSTANCE,
					Zhangjiakou.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Qinhuangdao extends City {
				public static final int CODE = 334;
				public static final String RES_NAME = "umssdk_area_china_hebei_qinhuangdao";
				public static final Qinhuangdao INSTANCE = new Qinhuangdao();

				private Qinhuangdao() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Baoding extends City {
				public static final int CODE = 328;
				public static final String RES_NAME = "umssdk_area_china_hebei_baoding";
				public static final Baoding INSTANCE = new Baoding();

				private Baoding() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Langfang extends City {
				public static final int CODE = 333;
				public static final String RES_NAME = "umssdk_area_china_hebei_langfang";
				public static final Langfang INSTANCE = new Langfang();

				private Langfang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Chengde extends City {
				public static final int CODE = 330;
				public static final String RES_NAME = "umssdk_area_china_hebei_chengde";
				public static final Chengde INSTANCE = new Chengde();

				private Chengde() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Handan extends City {
				public static final int CODE = 331;
				public static final String RES_NAME = "umssdk_area_china_hebei_handan";
				public static final Handan INSTANCE = new Handan();

				private Handan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shijiazhuang extends City {
				public static final int CODE = 335;
				public static final String RES_NAME = "umssdk_area_china_hebei_shijiazhuang";
				public static final Shijiazhuang INSTANCE = new Shijiazhuang();

				private Shijiazhuang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Tangshan extends City {
				public static final int CODE = 336;
				public static final String RES_NAME = "umssdk_area_china_hebei_tangshan";
				public static final Tangshan INSTANCE = new Tangshan();

				private Tangshan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xingtai extends City {
				public static final int CODE = 337;
				public static final String RES_NAME = "umssdk_area_china_hebei_xingtai";
				public static final Xingtai INSTANCE = new Xingtai();

				private Xingtai() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhangjiakou extends City {
				public static final int CODE = 338;
				public static final String RES_NAME = "umssdk_area_china_hebei_zhangjiakou";
				public static final Zhangjiakou INSTANCE = new Zhangjiakou();

				private Zhangjiakou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Cangzhou extends City {
				public static final int CODE = 329;
				public static final String RES_NAME = "umssdk_area_china_hebei_cangzhou";
				public static final Cangzhou INSTANCE = new Cangzhou();

				private Cangzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hengshui extends City {
				public static final int CODE = 332;
				public static final String RES_NAME = "umssdk_area_china_hebei_hengshui";
				public static final Hengshui INSTANCE = new Hengshui();

				private Hengshui() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Beijing extends Province {
			public static final int CODE = 194;
			public static final String RES_NAME = "umssdk_area_china_beijing";
			public static final Beijing INSTANCE = new Beijing();

			private Beijing() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

		}

		public static final class HongKong extends Province {
			public static final int CODE = 72;
			public static final String RES_NAME = "umssdk_area_china_hongkong";
			public static final HongKong INSTANCE = new HongKong();

			private HongKong() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

		}

		public static final class Guangxi extends Province {
			public static final int CODE = 198;
			public static final String RES_NAME = "umssdk_area_china_guangxi";
			public static final Guangxi INSTANCE = new Guangxi();

			private Guangxi() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Baise.INSTANCE,
					Beihai.INSTANCE,
					Chongzuo.INSTANCE,
					Fangchenggang.INSTANCE,
					Guigang.INSTANCE,
					Guilin.INSTANCE,
					Hechi.INSTANCE,
					Hezhou.INSTANCE,
					Guest.INSTANCE,
					Liuzhou.INSTANCE,
					Nanning.INSTANCE,
					Qinzhou.INSTANCE,
					Wuzhou.INSTANCE,
					Yulin.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Hechi extends City {
				public static final int CODE = 294;
				public static final String RES_NAME = "umssdk_area_china_guangxi_hechi";
				public static final Hechi INSTANCE = new Hechi();

				private Hechi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Nanning extends City {
				public static final int CODE = 298;
				public static final String RES_NAME = "umssdk_area_china_guangxi_nanning";
				public static final Nanning INSTANCE = new Nanning();

				private Nanning() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Qinzhou extends City {
				public static final int CODE = 299;
				public static final String RES_NAME = "umssdk_area_china_guangxi_qinzhou";
				public static final Qinzhou INSTANCE = new Qinzhou();

				private Qinzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Guest extends City {
				public static final int CODE = 296;
				public static final String RES_NAME = "umssdk_area_china_guangxi_guest";
				public static final Guest INSTANCE = new Guest();

				private Guest() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yulin extends City {
				public static final int CODE = 301;
				public static final String RES_NAME = "umssdk_area_china_guangxi_yulin";
				public static final Yulin INSTANCE = new Yulin();

				private Yulin() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Beihai extends City {
				public static final int CODE = 289;
				public static final String RES_NAME = "umssdk_area_china_guangxi_beihai";
				public static final Beihai INSTANCE = new Beihai();

				private Beihai() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Wuzhou extends City {
				public static final int CODE = 300;
				public static final String RES_NAME = "umssdk_area_china_guangxi_wuzhou";
				public static final Wuzhou INSTANCE = new Wuzhou();

				private Wuzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Chongzuo extends City {
				public static final int CODE = 290;
				public static final String RES_NAME = "umssdk_area_china_guangxi_chongzuo";
				public static final Chongzuo INSTANCE = new Chongzuo();

				private Chongzuo() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Baise extends City {
				public static final int CODE = 288;
				public static final String RES_NAME = "umssdk_area_china_guangxi_baise";
				public static final Baise INSTANCE = new Baise();

				private Baise() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Fangchenggang extends City {
				public static final int CODE = 291;
				public static final String RES_NAME = "umssdk_area_china_guangxi_fangchenggang";
				public static final Fangchenggang INSTANCE = new Fangchenggang();

				private Fangchenggang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hezhou extends City {
				public static final int CODE = 295;
				public static final String RES_NAME = "umssdk_area_china_guangxi_hezhou";
				public static final Hezhou INSTANCE = new Hezhou();

				private Hezhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Guigang extends City {
				public static final int CODE = 292;
				public static final String RES_NAME = "umssdk_area_china_guangxi_guigang";
				public static final Guigang INSTANCE = new Guigang();

				private Guigang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Guilin extends City {
				public static final int CODE = 293;
				public static final String RES_NAME = "umssdk_area_china_guangxi_guilin";
				public static final Guilin INSTANCE = new Guilin();

				private Guilin() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Liuzhou extends City {
				public static final int CODE = 297;
				public static final String RES_NAME = "umssdk_area_china_guangxi_liuzhou";
				public static final Liuzhou INSTANCE = new Liuzhou();

				private Liuzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Macao extends Province {
			public static final int CODE = 101;
			public static final String RES_NAME = "umssdk_area_china_macao";
			public static final Macao INSTANCE = new Macao();

			private Macao() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

		}

		public static final class Taiwan extends Province {
			public static final int CODE = 169;
			public static final String RES_NAME = "umssdk_area_china_taiwan";
			public static final Taiwan INSTANCE = new Taiwan();

			private Taiwan() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Kaohsiung.INSTANCE,
					Taipei.INSTANCE,
					Keelung.INSTANCE,
					Taichung.INSTANCE,
					Tainan.INSTANCE,
					Hsinchu.INSTANCE,
					NewNorth.INSTANCE,
					Yilan.INSTANCE,
					Taoyuan.INSTANCE,
					HsinchuCounty.INSTANCE,
					Miaoli.INSTANCE,
					Changhua.INSTANCE,
					Nantou.INSTANCE,
					Yunlin.INSTANCE,
					Chiayi.INSTANCE,
					Pingtung.INSTANCE,
					Hualian.INSTANCE,
					Penghu.INSTANCE,
					ChiayiCounty.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Tainan extends City {
				public static final int CODE = 599;
				public static final String RES_NAME = "umssdk_area_china_taiwan_tainan";
				public static final Tainan INSTANCE = new Tainan();

				private Tainan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Keelung extends City {
				public static final int CODE = 597;
				public static final String RES_NAME = "umssdk_area_china_taiwan_keelung";
				public static final Keelung INSTANCE = new Keelung();

				private Keelung() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class NewNorth extends City {
				public static final int CODE = 602;
				public static final String RES_NAME = "umssdk_area_china_taiwan_newnorth";
				public static final NewNorth INSTANCE = new NewNorth();

				private NewNorth() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Changhua extends City {
				public static final int CODE = 607;
				public static final String RES_NAME = "umssdk_area_china_taiwan_changhua";
				public static final Changhua INSTANCE = new Changhua();

				private Changhua() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Taipei extends City {
				public static final int CODE = 594;
				public static final String RES_NAME = "umssdk_area_china_taiwan_taipei";
				public static final Taipei INSTANCE = new Taipei();

				private Taipei() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Taoyuan extends City {
				public static final int CODE = 604;
				public static final String RES_NAME = "umssdk_area_china_taiwan_taoyuan";
				public static final Taoyuan INSTANCE = new Taoyuan();

				private Taoyuan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Pingtung extends City {
				public static final int CODE = 611;
				public static final String RES_NAME = "umssdk_area_china_taiwan_pingtung";
				public static final Pingtung INSTANCE = new Pingtung();

				private Pingtung() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hualian extends City {
				public static final int CODE = 612;
				public static final String RES_NAME = "umssdk_area_china_taiwan_hualian";
				public static final Hualian INSTANCE = new Hualian();

				private Hualian() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class ChiayiCounty extends City {
				public static final int CODE = 628;
				public static final String RES_NAME = "umssdk_area_china_taiwan_chiayicounty";
				public static final ChiayiCounty INSTANCE = new ChiayiCounty();

				private ChiayiCounty() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Chiayi extends City {
				public static final int CODE = 610;
				public static final String RES_NAME = "umssdk_area_china_taiwan_chiayi";
				public static final Chiayi INSTANCE = new Chiayi();

				private Chiayi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Kaohsiung extends City {
				public static final int CODE = 593;
				public static final String RES_NAME = "umssdk_area_china_taiwan_kaohsiung";
				public static final Kaohsiung INSTANCE = new Kaohsiung();

				private Kaohsiung() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hsinchu extends City {
				public static final int CODE = 600;
				public static final String RES_NAME = "umssdk_area_china_taiwan_hsinchu";
				public static final Hsinchu INSTANCE = new Hsinchu();

				private Hsinchu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Nantou extends City {
				public static final int CODE = 608;
				public static final String RES_NAME = "umssdk_area_china_taiwan_nantou";
				public static final Nantou INSTANCE = new Nantou();

				private Nantou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yunlin extends City {
				public static final int CODE = 609;
				public static final String RES_NAME = "umssdk_area_china_taiwan_yunlin";
				public static final Yunlin INSTANCE = new Yunlin();

				private Yunlin() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Taichung extends City {
				public static final int CODE = 598;
				public static final String RES_NAME = "umssdk_area_china_taiwan_taichung";
				public static final Taichung INSTANCE = new Taichung();

				private Taichung() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Miaoli extends City {
				public static final int CODE = 606;
				public static final String RES_NAME = "umssdk_area_china_taiwan_miaoli";
				public static final Miaoli INSTANCE = new Miaoli();

				private Miaoli() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Penghu extends City {
				public static final int CODE = 613;
				public static final String RES_NAME = "umssdk_area_china_taiwan_penghu";
				public static final Penghu INSTANCE = new Penghu();

				private Penghu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yilan extends City {
				public static final int CODE = 603;
				public static final String RES_NAME = "umssdk_area_china_taiwan_yilan";
				public static final Yilan INSTANCE = new Yilan();

				private Yilan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class HsinchuCounty extends City {
				public static final int CODE = 605;
				public static final String RES_NAME = "umssdk_area_china_taiwan_hsinchucounty";
				public static final HsinchuCounty INSTANCE = new HsinchuCounty();

				private HsinchuCounty() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Henan extends Province {
			public static final int CODE = 202;
			public static final String RES_NAME = "umssdk_area_china_henan";
			public static final Henan INSTANCE = new Henan();

			private Henan() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Anyang.INSTANCE,
					Hebi.INSTANCE,
					Jiaozuo.INSTANCE,
					Kaifeng.INSTANCE,
					Luoyang.INSTANCE,
					Luohe.INSTANCE,
					Nanyang.INSTANCE,
					Pingdingshan.INSTANCE,
					Puyang.INSTANCE,
					Sanmenxia.INSTANCE,
					Shangqiu.INSTANCE,
					Xinxiang.INSTANCE,
					Xinyang.INSTANCE,
					Xuchang.INSTANCE,
					Zhengzhou.INSTANCE,
					Zhoukou.INSTANCE,
					Zhumadian.INSTANCE,
					Jiyuan.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Jiaozuo extends City {
				public static final int CODE = 343;
				public static final String RES_NAME = "umssdk_area_china_henan_jiaozuo";
				public static final Jiaozuo INSTANCE = new Jiaozuo();

				private Jiaozuo() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhumadian extends City {
				public static final int CODE = 357;
				public static final String RES_NAME = "umssdk_area_china_henan_zhumadian";
				public static final Zhumadian INSTANCE = new Zhumadian();

				private Zhumadian() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hebi extends City {
				public static final int CODE = 342;
				public static final String RES_NAME = "umssdk_area_china_henan_hebi";
				public static final Hebi INSTANCE = new Hebi();

				private Hebi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Luohe extends City {
				public static final int CODE = 346;
				public static final String RES_NAME = "umssdk_area_china_henan_luohe";
				public static final Luohe INSTANCE = new Luohe();

				private Luohe() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhoukou extends City {
				public static final int CODE = 356;
				public static final String RES_NAME = "umssdk_area_china_henan_zhoukou";
				public static final Zhoukou INSTANCE = new Zhoukou();

				private Zhoukou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Sanmenxia extends City {
				public static final int CODE = 350;
				public static final String RES_NAME = "umssdk_area_china_henan_sanmenxia";
				public static final Sanmenxia INSTANCE = new Sanmenxia();

				private Sanmenxia() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xinyang extends City {
				public static final int CODE = 353;
				public static final String RES_NAME = "umssdk_area_china_henan_xinyang";
				public static final Xinyang INSTANCE = new Xinyang();

				private Xinyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Puyang extends City {
				public static final int CODE = 349;
				public static final String RES_NAME = "umssdk_area_china_henan_puyang";
				public static final Puyang INSTANCE = new Puyang();

				private Puyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Nanyang extends City {
				public static final int CODE = 347;
				public static final String RES_NAME = "umssdk_area_china_henan_nanyang";
				public static final Nanyang INSTANCE = new Nanyang();

				private Nanyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Jiyuan extends City {
				public static final int CODE = 614;
				public static final String RES_NAME = "umssdk_area_china_henan_jiyuan";
				public static final Jiyuan INSTANCE = new Jiyuan();

				private Jiyuan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Pingdingshan extends City {
				public static final int CODE = 348;
				public static final String RES_NAME = "umssdk_area_china_henan_pingdingshan";
				public static final Pingdingshan INSTANCE = new Pingdingshan();

				private Pingdingshan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Luoyang extends City {
				public static final int CODE = 345;
				public static final String RES_NAME = "umssdk_area_china_henan_luoyang";
				public static final Luoyang INSTANCE = new Luoyang();

				private Luoyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Shangqiu extends City {
				public static final int CODE = 351;
				public static final String RES_NAME = "umssdk_area_china_henan_shangqiu";
				public static final Shangqiu INSTANCE = new Shangqiu();

				private Shangqiu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xuchang extends City {
				public static final int CODE = 354;
				public static final String RES_NAME = "umssdk_area_china_henan_xuchang";
				public static final Xuchang INSTANCE = new Xuchang();

				private Xuchang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Kaifeng extends City {
				public static final int CODE = 344;
				public static final String RES_NAME = "umssdk_area_china_henan_kaifeng";
				public static final Kaifeng INSTANCE = new Kaifeng();

				private Kaifeng() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xinxiang extends City {
				public static final int CODE = 352;
				public static final String RES_NAME = "umssdk_area_china_henan_xinxiang";
				public static final Xinxiang INSTANCE = new Xinxiang();

				private Xinxiang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhengzhou extends City {
				public static final int CODE = 355;
				public static final String RES_NAME = "umssdk_area_china_henan_zhengzhou";
				public static final Zhengzhou INSTANCE = new Zhengzhou();

				private Zhengzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Anyang extends City {
				public static final int CODE = 340;
				public static final String RES_NAME = "umssdk_area_china_henan_anyang";
				public static final Anyang INSTANCE = new Anyang();

				private Anyang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Jiangsu extends Province {
			public static final int CODE = 207;
			public static final String RES_NAME = "umssdk_area_china_jiangsu";
			public static final Jiangsu INSTANCE = new Jiangsu();

			private Jiangsu() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Changzhou.INSTANCE,
					Huaian.INSTANCE,
					Lianyungang.INSTANCE,
					Nanjing.INSTANCE,
					Nantong.INSTANCE,
					Suzhou.INSTANCE,
					Suqian.INSTANCE,
					Taizhou.INSTANCE,
					Wuxi.INSTANCE,
					Xuzhou.INSTANCE,
					Yancheng.INSTANCE,
					Yangzhou.INSTANCE,
					Zhenjiang.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Suqian extends City {
				public static final int CODE = 414;
				public static final String RES_NAME = "umssdk_area_china_jiangsu_suqian";
				public static final Suqian INSTANCE = new Suqian();

				private Suqian() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Taizhou extends City {
				public static final int CODE = 415;
				public static final String RES_NAME = "umssdk_area_china_jiangsu_taizhou";
				public static final Taizhou INSTANCE = new Taizhou();

				private Taizhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Xuzhou extends City {
				public static final int CODE = 417;
				public static final String RES_NAME = "umssdk_area_china_jiangsu_xuzhou";
				public static final Xuzhou INSTANCE = new Xuzhou();

				private Xuzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Wuxi extends City {
				public static final int CODE = 416;
				public static final String RES_NAME = "umssdk_area_china_jiangsu_wuxi";
				public static final Wuxi INSTANCE = new Wuxi();

				private Wuxi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yangzhou extends City {
				public static final int CODE = 419;
				public static final String RES_NAME = "umssdk_area_china_jiangsu_yangzhou";
				public static final Yangzhou INSTANCE = new Yangzhou();

				private Yangzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yancheng extends City {
				public static final int CODE = 418;
				public static final String RES_NAME = "umssdk_area_china_jiangsu_yancheng";
				public static final Yancheng INSTANCE = new Yancheng();

				private Yancheng() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Changzhou extends City {
				public static final int CODE = 408;
				public static final String RES_NAME = "umssdk_area_china_jiangsu_changzhou";
				public static final Changzhou INSTANCE = new Changzhou();

				private Changzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Lianyungang extends City {
				public static final int CODE = 410;
				public static final String RES_NAME = "umssdk_area_china_jiangsu_lianyungang";
				public static final Lianyungang INSTANCE = new Lianyungang();

				private Lianyungang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Huaian extends City {
				public static final int CODE = 409;
				public static final String RES_NAME = "umssdk_area_china_jiangsu_huaian";
				public static final Huaian INSTANCE = new Huaian();

				private Huaian() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Suzhou extends City {
				public static final int CODE = 413;
				public static final String RES_NAME = "umssdk_area_china_jiangsu_suzhou";
				public static final Suzhou INSTANCE = new Suzhou();

				private Suzhou() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Zhenjiang extends City {
				public static final int CODE = 421;
				public static final String RES_NAME = "umssdk_area_china_jiangsu_zhenjiang";
				public static final Zhenjiang INSTANCE = new Zhenjiang();

				private Zhenjiang() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Nanjing extends City {
				public static final int CODE = 411;
				public static final String RES_NAME = "umssdk_area_china_jiangsu_nanjing";
				public static final Nanjing INSTANCE = new Nanjing();

				private Nanjing() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Nantong extends City {
				public static final int CODE = 412;
				public static final String RES_NAME = "umssdk_area_china_jiangsu_nantong";
				public static final Nantong INSTANCE = new Nantong();

				private Nantong() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

		public static final class Qinghai extends Province {
			public static final int CODE = 212;
			public static final String RES_NAME = "umssdk_area_china_qinghai";
			public static final Qinghai INSTANCE = new Qinghai();

			private Qinghai() {

			}

			public final int code() {
				return CODE;
			}

			public final String resName() {
				return RES_NAME;
			}

			public final City codeOf(int code) {
				return valueOf(code);
			}

			public static City[] values() {
				return new City[] {
					Guoluo.INSTANCE,
					Haibei.INSTANCE,
					Haidong.INSTANCE,
					Hainan.INSTANCE,
					Haixi.INSTANCE,
					Huangnan.INSTANCE,
					Xining.INSTANCE,
					Yushu.INSTANCE
				};
			}

			public static City valueOf(int code) {
				for (City item : values()) {
					if (item.code() == code) {
						return item;
					}
				}
				return null;
			}

			public static final class Xining extends City {
				public static final int CODE = 471;
				public static final String RES_NAME = "umssdk_area_china_qinghai_xining";
				public static final Xining INSTANCE = new Xining();

				private Xining() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Haibei extends City {
				public static final int CODE = 465;
				public static final String RES_NAME = "umssdk_area_china_qinghai_haibei";
				public static final Haibei INSTANCE = new Haibei();

				private Haibei() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Haixi extends City {
				public static final int CODE = 468;
				public static final String RES_NAME = "umssdk_area_china_qinghai_haixi";
				public static final Haixi INSTANCE = new Haixi();

				private Haixi() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Guoluo extends City {
				public static final int CODE = 464;
				public static final String RES_NAME = "umssdk_area_china_qinghai_guoluo";
				public static final Guoluo INSTANCE = new Guoluo();

				private Guoluo() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Haidong extends City {
				public static final int CODE = 466;
				public static final String RES_NAME = "umssdk_area_china_qinghai_haidong";
				public static final Haidong INSTANCE = new Haidong();

				private Haidong() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Yushu extends City {
				public static final int CODE = 472;
				public static final String RES_NAME = "umssdk_area_china_qinghai_yushu";
				public static final Yushu INSTANCE = new Yushu();

				private Yushu() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Huangnan extends City {
				public static final int CODE = 469;
				public static final String RES_NAME = "umssdk_area_china_qinghai_huangnan";
				public static final Huangnan INSTANCE = new Huangnan();

				private Huangnan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

			public static final class Hainan extends City {
				public static final int CODE = 467;
				public static final String RES_NAME = "umssdk_area_china_qinghai_hainan";
				public static final Hainan INSTANCE = new Hainan();

				private Hainan() {

				}

				public final int code() {
					return CODE;
				}

				public final String resName() {
					return RES_NAME;
				}

			}

		}

	}

	public static final class Ireland extends Country {
		public static final int CODE = 79;
		public static final String RES_NAME = "umssdk_area_ireland";
		public static final Ireland INSTANCE = new Ireland();

		private Ireland() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Qatar extends Country {
		public static final int CODE = 139;
		public static final String RES_NAME = "umssdk_area_qatar";
		public static final Qatar INSTANCE = new Qatar();

		private Qatar() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Salvatore extends Country {
		public static final int CODE = 50;
		public static final String RES_NAME = "umssdk_area_salvatore";
		public static final Salvatore INSTANCE = new Salvatore();

		private Salvatore() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Slovakia extends Country {
		public static final int CODE = 154;
		public static final String RES_NAME = "umssdk_area_slovakia";
		public static final Slovakia INSTANCE = new Slovakia();

		private Slovakia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class France extends Country {
		public static final int CODE = 55;
		public static final String RES_NAME = "umssdk_area_france";
		public static final France INSTANCE = new France();

		private France() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Lithuania extends Country {
		public static final int CODE = 99;
		public static final String RES_NAME = "umssdk_area_lithuania";
		public static final Lithuania INSTANCE = new Lithuania();

		private Lithuania() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class PuertoRico extends Country {
		public static final int CODE = 138;
		public static final String RES_NAME = "umssdk_area_puertorico";
		public static final PuertoRico INSTANCE = new PuertoRico();

		private PuertoRico() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Kyrgyzstan extends Country {
		public static final int CODE = 91;
		public static final String RES_NAME = "umssdk_area_kyrgyzstan";
		public static final Kyrgyzstan INSTANCE = new Kyrgyzstan();

		private Kyrgyzstan() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Korea extends Country {
		public static final int CODE = 89;
		public static final String RES_NAME = "umssdk_area_korea";
		public static final Korea INSTANCE = new Korea();

		private Korea() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Reunion extends Country {
		public static final int CODE = 140;
		public static final String RES_NAME = "umssdk_area_reunion";
		public static final Reunion INSTANCE = new Reunion();

		private Reunion() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class MontserratIsland extends Country {
		public static final int CODE = 115;
		public static final String RES_NAME = "umssdk_area_montserratisland";
		public static final MontserratIsland INSTANCE = new MontserratIsland();

		private MontserratIsland() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Romania extends Country {
		public static final int CODE = 141;
		public static final String RES_NAME = "umssdk_area_romania";
		public static final Romania INSTANCE = new Romania();

		private Romania() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Togo extends Country {
		public static final int CODE = 173;
		public static final String RES_NAME = "umssdk_area_togo";
		public static final Togo INSTANCE = new Togo();

		private Togo() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Niger extends Country {
		public static final int CODE = 125;
		public static final String RES_NAME = "umssdk_area_niger";
		public static final Niger INSTANCE = new Niger();

		private Niger() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Philippines extends Country {
		public static final int CODE = 135;
		public static final String RES_NAME = "umssdk_area_philippines";
		public static final Philippines INSTANCE = new Philippines();

		private Philippines() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Uzbekistan extends Country {
		public static final int CODE = 185;
		public static final String RES_NAME = "umssdk_area_uzbekistan";
		public static final Uzbekistan INSTANCE = new Uzbekistan();

		private Uzbekistan() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Bangladesh extends Country {
		public static final int CODE = 16;
		public static final String RES_NAME = "umssdk_area_bangladesh";
		public static final Bangladesh INSTANCE = new Bangladesh();

		private Bangladesh() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Barbados extends Country {
		public static final int CODE = 17;
		public static final String RES_NAME = "umssdk_area_barbados";
		public static final Barbados INSTANCE = new Barbados();

		private Barbados() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Nicaragua extends Country {
		public static final int CODE = 124;
		public static final String RES_NAME = "umssdk_area_nicaragua";
		public static final Nicaragua INSTANCE = new Nicaragua();

		private Nicaragua() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class SaoTomeandPrincipe extends Country {
		public static final int CODE = 148;
		public static final String RES_NAME = "umssdk_area_saotomeandprincipe";
		public static final SaoTomeandPrincipe INSTANCE = new SaoTomeandPrincipe();

		private SaoTomeandPrincipe() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Norway extends Country {
		public static final int CODE = 128;
		public static final String RES_NAME = "umssdk_area_norway";
		public static final Norway INSTANCE = new Norway();

		private Norway() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Botswana extends Country {
		public static final int CODE = 24;
		public static final String RES_NAME = "umssdk_area_botswana";
		public static final Botswana INSTANCE = new Botswana();

		private Botswana() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class CookIslands extends Country {
		public static final int CODE = 40;
		public static final String RES_NAME = "umssdk_area_cookislands";
		public static final CookIslands INSTANCE = new CookIslands();

		private CookIslands() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Denmark extends Country {
		public static final int CODE = 45;
		public static final String RES_NAME = "umssdk_area_denmark";
		public static final Denmark INSTANCE = new Denmark();

		private Denmark() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class EastSamoaUS extends Country {
		public static final int CODE = 145;
		public static final String RES_NAME = "umssdk_area_eastsamoa_us";
		public static final EastSamoaUS INSTANCE = new EastSamoaUS();

		private EastSamoaUS() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Mexico extends Country {
		public static final int CODE = 111;
		public static final String RES_NAME = "umssdk_area_mexico";
		public static final Mexico INSTANCE = new Mexico();

		private Mexico() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Uganda extends Country {
		public static final int CODE = 179;
		public static final String RES_NAME = "umssdk_area_uganda";
		public static final Uganda INSTANCE = new Uganda();

		private Uganda() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Zimbabwe extends Country {
		public static final int CODE = 192;
		public static final String RES_NAME = "umssdk_area_zimbabwe";
		public static final Zimbabwe INSTANCE = new Zimbabwe();

		private Zimbabwe() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Suriname extends Country {
		public static final int CODE = 164;
		public static final String RES_NAME = "umssdk_area_suriname";
		public static final Suriname INSTANCE = new Suriname();

		private Suriname() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

	public static final class Indonesia extends Country {
		public static final int CODE = 76;
		public static final String RES_NAME = "umssdk_area_indonesia";
		public static final Indonesia INSTANCE = new Indonesia();

		private Indonesia() {

		}

		public final int code() {
			return CODE;
		}

		public final String resName() {
			return RES_NAME;
		}

	}

}

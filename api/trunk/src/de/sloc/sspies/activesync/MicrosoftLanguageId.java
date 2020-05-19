package de.sloc.sspies.activesync;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MicrosoftLanguageId
{

	AR("ar", 0x0001), BG("bg", 0x0002), CA("ca", 0x0003), ZH_HANS("zh-Hans", 0x0004), CS("cs", 0x0005), DA("da", 0x0006), DE("de", 0x0007),
	EL("el", 0x0008), EN("en", 0x0009), ES("es", 0x000a), FI("fi", 0x000b), FR("fr", 0x000c), HE("he", 0x000d), HU("hu", 0x000e), IS("is", 0x000f),
	IT("it", 0x0010), JA("ja", 0x0011), KO("ko", 0x0012), NL("nl", 0x0013), NO("no", 0x0014), PL("pl", 0x0015), PT("pt", 0x0016), RM("rm", 0x0017),
	RO("ro", 0x0018), RU("ru", 0x0019), HR("hr", 0x001a), SK("sk", 0x001b), SQ("sq", 0x001c), SV("sv", 0x001d), TH("th", 0x001e), TR("tr", 0x001f),
	UR("ur", 0x0020), ID("id", 0x0021), UK("uk", 0x0022), BE("be", 0x0023), SL("sl", 0x0024), ET("et", 0x0025), LV("lv", 0x0026), LT("lt", 0x0027),
	TG("tg", 0x0028), FA("fa", 0x0029), VI("vi", 0x002a), HY("hy", 0x002b), AZ("az", 0x002c), EU("eu", 0x002d), HSB("hsb", 0x002e), MK("mk", 0x002f),
	ST("st", 0x0030), TS("ts", 0x0031), TN("tn", 0x0032), VE("ve", 0x0033), XH("xh", 0x0034), ZU("zu", 0x0035), AF("af", 0x0036), KA("ka", 0x0037),
	FO("fo", 0x0038), HI("hi", 0x0039), MT("mt", 0x003a), SE("se", 0x003b), GA("ga", 0x003c), YI("yi", 0x003d), MS("ms", 0x003e), KK("kk", 0x003f),
	KY("ky", 0x0040), SW("sw", 0x0041), TK("tk", 0x0042), UZ("uz", 0x0043), TT("tt", 0x0044), BN("bn", 0x0045), PA("pa", 0x0046), GU("gu", 0x0047),
	OR("or", 0x0048), TA("ta", 0x0049), TE("te", 0x004a), KN("kn", 0x004b), ML("ml", 0x004c), AS("as", 0x004d), MR("mr", 0x004e), SA("sa", 0x004f),
	MN("mn", 0x0050), BO("bo", 0x0051), CY("cy", 0x0052), KM("km", 0x0053), LO("lo", 0x0054), MY("my", 0x0055), GL("gl", 0x0056), KOK("kok", 0x0057),
	MNI("mni", 0x0058), SD("sd", 0x0059), SYR("syr", 0x005a), SI("si", 0x005b), CHR("chr", 0x005c), IU("iu", 0x005d), AM("am", 0x005e),
	TZM("tzm", 0x005f), KS("ks", 0x0060), NE("ne", 0x0061), FY("fy", 0x0062), PS("ps", 0x0063), FIL("fil", 0x0064), DV("dv", 0x0065),
	BIN("bin", 0x0066), FF("ff", 0x0067), HA("ha", 0x0068), IBB("ibb", 0x0069), YO("yo", 0x006a), QUZ("quz", 0x006b), NSO("nso", 0x006c),
	BA("ba", 0x006d), LB("lb", 0x006e), KL("kl", 0x006f), IG("ig", 0x0070), KR("kr", 0x0071), OM("om", 0x0072), TI("ti", 0x0073), GN("gn", 0x0074),
	HAW("haw", 0x0075), LA("la", 0x0076), SO("so", 0x0077), II("ii", 0x0078), PAP("pap", 0x0079), ARN("arn", 0x007a), MOH("moh", 0x007c),
	BR("br", 0x007e), UG("ug", 0x0080), MI("mi", 0x0081), OC("oc", 0x0082), CO("co", 0x0083), GSW("gsw", 0x0084), SAH("sah", 0x0085),
	QUT("qut", 0x0086), RW("rw", 0x0087), WO("wo", 0x0088), PRS("prs", 0x008c), GD("gd", 0x0091), KU("ku", 0x0092), QUC("quc", 0x0093),
	AR_SA("ar-SA", 0x0401), BG_BG("bg-BG", 0x0402), CA_ES("ca-ES", 0x0403), ZH_TW("zh-TW", 0x0404), CS_CZ("cs-CZ", 0x0405), DA_DK("da-DK", 0x0406),
	DE_DE("de-DE", 0x0407), EL_GR("el-GR", 0x0408), EN_US("en-US", 0x0409), ES_ES_TRADNL("es-ES_tradnl", 0x040a), FI_FI("fi-FI", 0x040b),
	FR_FR("fr-FR", 0x040c), HE_IL("he-IL", 0x040d), HU_HU("hu-HU", 0x040e), IS_IS("is-IS", 0x040f), IT_IT("it-IT", 0x0410), JA_JP("ja-JP", 0x0411),
	KO_KR("ko-KR", 0x0412), NL_NL("nl-NL", 0x0413), NB_NO("nb-NO", 0x0414), PL_PL("pl-PL", 0x0415), PT_BR("pt-BR", 0x0416), RM_CH("rm-CH", 0x0417),
	RO_RO("ro-RO", 0x0418), RU_RU("ru-RU", 0x0419), HR_HR("hr-HR", 0x041a), SK_SK("sk-SK", 0x041b), SQ_AL("sq-AL", 0x041c), SV_SE("sv-SE", 0x041d),
	TH_TH("th-TH", 0x041e), TR_TR("tr-TR", 0x041f), UR_PK("ur-PK", 0x0420), ID_ID("id-ID", 0x0421), UK_UA("uk-UA", 0x0422), BE_BY("be-BY", 0x0423),
	SL_SI("sl-SI", 0x0424), ET_EE("et-EE", 0x0425), LV_LV("lv-LV", 0x0426), LT_LT("lt-LT", 0x0427), TG_CYRL_TJ("tg-Cyrl-TJ", 0x0428),
	FA_IR("fa-IR", 0x0429), VI_VN("vi-VN", 0x042a), HY_AM("hy-AM", 0x042b), AZ_LATN_AZ("az-Latn-AZ", 0x042c), EU_ES("eu-ES", 0x042d),
	HSB_DE("hsb-DE", 0x042e), MK_MK("mk-MK", 0x042f), ST_ZA("st-ZA", 0x0430), TS_ZA("ts-ZA", 0x0431), TN_ZA("tn-ZA", 0x0432), VE_ZA("ve-ZA", 0x0433),
	XH_ZA("xh-ZA", 0x0434), ZU_ZA("zu-ZA", 0x0435), AF_ZA("af-ZA", 0x0436), KA_GE("ka-GE", 0x0437), FO_FO("fo-FO", 0x0438), HI_IN("hi-IN", 0x0439),
	MT_MT("mt-MT", 0x043a), SE_NO("se-NO", 0x043b), YI_HEBR("yi-Hebr", 0x043d), MS_MY("ms-MY", 0x043e), KK_KZ("kk-KZ", 0x043f),
	KY_KG("ky-KG", 0x0440), SW_KE("sw-KE", 0x0441), TK_TM("tk-TM", 0x0442), UZ_LATN_UZ("uz-Latn-UZ", 0x0443), TT_RU("tt-RU", 0x0444),
	BN_IN("bn-IN", 0x0445), PA_IN("pa-IN", 0x0446), GU_IN("gu-IN", 0x0447), OR_IN("or-IN", 0x0448), TA_IN("ta-IN", 0x0449), TE_IN("te-IN", 0x044a),
	KN_IN("kn-IN", 0x044b), ML_IN("ml-IN", 0x044c), AS_IN("as-IN", 0x044d), MR_IN("mr-IN", 0x044e), SA_IN("sa-IN", 0x044f), MN_MN("mn-MN", 0x0450),
	BO_CN("bo-CN", 0x0451), CY_GB("cy-GB", 0x0452), KM_KH("km-KH", 0x0453), LO_LA("lo-LA", 0x0454), MY_MM("my-MM", 0x0455), GL_ES("gl-ES", 0x0456),
	KOK_IN("kok-IN", 0x0457), MNI_IN("mni-IN", 0x0458), SD_DEVA_IN("sd-Deva-IN", 0x0459), SYR_SY("syr-SY", 0x045a), SI_LK("si-LK", 0x045b),
	CHR_CHER_US("chr-Cher-US", 0x045c), IU_CANS_CA("iu-Cans-CA", 0x045d), AM_ET("am-ET", 0x045e), TZM_ARAB_MA("tzm-Arab-MA", 0x045f),
	KS_ARAB("ks-Arab", 0x0460), NE_NP("ne-NP", 0x0461), FY_NL("fy-NL", 0x0462), PS_AF("ps-AF", 0x0463), FIL_PH("fil-PH", 0x0464),
	DV_MV("dv-MV", 0x0465), BIN_NG("bin-NG", 0x0466), FUV_NG("fuv-NG", 0x0467), HA_LATN_NG("ha-Latn-NG", 0x0468), IBB_NG("ibb-NG", 0x0469),
	YO_NG("yo-NG", 0x046a), QUZ_BO("quz-BO", 0x046b), NSO_ZA("nso-ZA", 0x046c), BA_RU("ba-RU", 0x046d), LB_LU("lb-LU", 0x046e),
	KL_GL("kl-GL", 0x046f), IG_NG("ig-NG", 0x0470), KR_NG("kr-NG", 0x0471), OM_ETHI_ET("om-Ethi-ET", 0x0472), TI_ET("ti-ET", 0x0473),
	GN_PY("gn-PY", 0x0474), HAW_US("haw-US", 0x0475), LA_LATN("la-Latn", 0x0476), SO_SO("so-SO", 0x0477), II_CN("ii-CN", 0x0478),
	PAP_029("pap-029", 0x0479), ARN_CL("arn-CL", 0x047a), MOH_CA("moh-CA", 0x047c), BR_FR("br-FR", 0x047e), UG_CN("ug-CN", 0x0480),
	MI_NZ("mi-NZ", 0x0481), OC_FR("oc-FR", 0x0482), CO_FR("co-FR", 0x0483), GSW_FR("gsw-FR", 0x0484), SAH_RU("sah-RU", 0x0485),
	QUT_GT("qut-GT", 0x0486), RW_RW("rw-RW", 0x0487), WO_SN("wo-SN", 0x0488), PRS_AF("prs-AF", 0x048c), PLT_MG("plt-MG", 0x048d),
	ZH_YUE_HK("zh-yue-HK", 0x048e), TDD_TALE_CN("tdd-Tale-CN", 0x048f), KHB_TALU_CN("khb-Talu-CN", 0x0490), GD_GB("gd-GB", 0x0491),
	KU_ARAB_IQ("ku-Arab-IQ", 0x0492), QUC_CO("quc-CO", 0x0493), QPS_PLOC("qps-ploc", 0x0501), QPS_PLOCA("qps-ploca", 0x05fe), AR_IQ("ar-IQ", 0x0801),
	CA_ES_VALENCIA("ca-ES-valencia", 0x0803), ZH_CN("zh-CN", 0x0804), DE_CH("de-CH", 0x0807), EN_GB("en-GB", 0x0809), ES_MX("es-MX", 0x080a),
	FR_BE("fr-BE", 0x080c), IT_CH("it-CH", 0x0810), JA_PLOC_JP("ja-Ploc-JP", 0x0811), NL_BE("nl-BE", 0x0813), NN_NO("nn-NO", 0x0814),
	PT_PT("pt-PT", 0x0816), RO_MO("ro-MO", 0x0818), RU_MO("ru-MO", 0x0819), SR_LATN_CS("sr-Latn-CS", 0x081a), SV_FI("sv-FI", 0x081d),
	UR_IN("ur-IN", 0x0820), AZ_CYRL_AZ("az-Cyrl-AZ", 0x082c), DSB_DE("dsb-DE", 0x082e), TN_BW("tn-BW", 0x0832), SE_SE("se-SE", 0x083b),
	GA_IE("ga-IE", 0x083c), MS_BN("ms-BN", 0x083e), UZ_CYRL_UZ("uz-Cyrl-UZ", 0x0843), BN_BD("bn-BD", 0x0845), PA_ARAB_PK("pa-Arab-PK", 0x0846),
	TA_LK("ta-LK", 0x0849), MN_MONG_CN("mn-Mong-CN", 0x0850), BO_BT("bo-BT", 0x0851), SD_ARAB_PK("sd-Arab-PK", 0x0859),
	IU_LATN_CA("iu-Latn-CA", 0x085d), TZM_LATN_DZ("tzm-Latn-DZ", 0x085f), KS_DEVA("ks-Deva", 0x0860), NE_IN("ne-IN", 0x0861),
	FF_LATN_SN("ff-Latn-SN", 0x0867), QUZ_EC("quz-EC", 0x086b), TI_ER("ti-ER", 0x0873), QPS_PLOCM("qps-plocm", 0x09ff), AR_EG("ar-EG", 0x0c01),
	ZH_HK("zh-HK", 0x0c04), DE_AT("de-AT", 0x0c07), EN_AU("en-AU", 0x0c09), ES_ES("es-ES", 0x0c0a), FR_CA("fr-CA", 0x0c0c),
	SR_CYRL_CS("sr-Cyrl-CS", 0x0c1a), SE_FI("se-FI", 0x0c3b), TMZ_MA("tmz-MA", 0x0c5f), QUZ_PE("quz-PE", 0x0c6b), AR_LY("ar-LY", 0x1001),
	ZH_SG("zh-SG", 0x1004), DE_LU("de-LU", 0x1007), EN_CA("en-CA", 0x1009), ES_GT("es-GT", 0x100a), FR_CH("fr-CH", 0x100c), HR_BA("hr-BA", 0x101a),
	SMJ_NO("smj-NO", 0x103b), AR_DZ("ar-DZ", 0x1401), ZH_MO("zh-MO", 0x1404), DE_LI("de-LI", 0x1407), EN_NZ("en-NZ", 0x1409), ES_CR("es-CR", 0x140a),
	FR_LU("fr-LU", 0x140c), BS_LATN_BA("bs-Latn-BA", 0x141a), SMJ_SE("smj-SE", 0x143b), AR_MA("ar-MA", 0x1801), EN_IE("en-IE", 0x1809),
	ES_PA("es-PA", 0x180a), FR_MC("fr-MC", 0x180c), SR_LATN_BA("sr-Latn-BA", 0x181a), SMA_NO("sma-NO", 0x183b), AR_TN("ar-TN", 0x1c01),
	EN_ZA("en-ZA", 0x1c09), ES_DO("es-DO", 0x1c0a), SR_CYRL_BA("sr-Cyrl-BA", 0x1c1a), SMA_SE("sma-SE", 0x1c3b), AR_OM("ar-OM", 0x2001),
	EN_JM("en-JM", 0x2009), ES_VE("es-VE", 0x200a), FR_RE("fr-RE", 0x200c), BS_CYRL_BA("bs-Cyrl-BA", 0x201a), SMS_FI("sms-FI", 0x203b),
	AR_YE("ar-YE", 0x2401), EN_029("en-029", 0x2409), ES_CO("es-CO", 0x240a), FR_CG("fr-CG", 0x240c), SR_LATN_RS("sr-Latn-RS", 0x241a),
	SMN_FI("smn-FI", 0x243b), AR_SY("ar-SY", 0x2801), EN_BZ("en-BZ", 0x2809), ES_PE("es-PE", 0x280a), FR_SN("fr-SN", 0x280c),
	SR_CYRL_RS("sr-Cyrl-RS", 0x281a), AR_JO("ar-JO", 0x2c01), EN_TT("en-TT", 0x2c09), ES_AR("es-AR", 0x2c0a), FR_CM("fr-CM", 0x2c0c),
	SR_LATN_ME("sr-Latn-ME", 0x2c1a), AR_LB("ar-LB", 0x3001), EN_ZW("en-ZW", 0x3009), ES_EC("es-EC", 0x300a), FR_CI("fr-CI", 0x300c),
	SR_CYRL_ME("sr-Cyrl-ME", 0x301a), AR_KW("ar-KW", 0x3401), EN_PH("en-PH", 0x3409), ES_CL("es-CL", 0x340a), FR_ML("fr-ML", 0x340c),
	AR_AE("ar-AE", 0x3801), EN_ID("en-ID", 0x3809), ES_UY("es-UY", 0x380a), FR_MA("fr-MA", 0x380c), AR_BH("ar-BH", 0x3c01), EN_HK("en-HK", 0x3c09),
	ES_PY("es-PY", 0x3c0a), FR_HT("fr-HT", 0x3c0c), AR_QA("ar-QA", 0x4001), EN_IN("en-IN", 0x4009), ES_BO("es-BO", 0x400a),
	AR_PLOC_SA("ar-Ploc-SA", 0x4401), EN_MY("en-MY", 0x4409), ES_SV("es-SV", 0x440a), AR_145("ar-145", 0x4801), EN_SG("en-SG", 0x4809),
	ES_HN("es-HN", 0x480a), EN_AE("en-AE", 0x4c09), ES_NI("es-NI", 0x4c0a), EN_BH("en-BH", 0x5009), ES_PR("es-PR", 0x500a), EN_EG("en-EG", 0x5409),
	ES_US("es-US", 0x540a), EN_JO("en-JO", 0x5809), EN_KW("en-KW", 0x5c09), EN_TR("en-TR", 0x6009), EN_YE("en-YE", 0x6409),
	BS_CYRL("bs-Cyrl", 0x641a), BS_LATN("bs-Latn", 0x681a), SR_CYRL("sr-Cyrl", 0x6c1a), SR_LATN("sr-Latn", 0x701a), SMN("smn", 0x703b),
	AZ_CYRL("az-Cyrl", 0x742c), SMS("sms", 0x743b), ZH("zh", 0x7804), NN("nn", 0x7814), BS("bs", 0x781a), AZ_LATN("az-Latn", 0x782c),
	SMA("sma", 0x783b), UZ_CYRL("uz-Cyrl", 0x7843), MN_CYRL("mn-Cyrl", 0x7850), IU_CANS("iu-Cans", 0x785d), ZH_HANT("zh-Hant", 0x7c04),
	NB("nb", 0x7c14), SR("sr", 0x7c1a), TG_CYRL("tg-Cyrl", 0x7c28), DSB("dsb", 0x7c2e), SMJ("smj", 0x7c3b), UZ_LATN("uz-Latn", 0x7c43),
	PA_ARAB("pa-Arab", 0x7c46), MN_MONG("mn-Mong", 0x7c50), SD_ARAB("sd-Arab", 0x7c59), CHR_CHER("chr-Cher", 0x7c5c), IU_LATN("iu-Latn", 0x7c5d),
	TZM_LATN("tzm-Latn", 0x7c5f), FF_LATN("ff-Latn", 0x7c67), HA_LATN("ha-Latn", 0x7c68), KU_ARAB("ku-Arab", 0x7c92), UNKNOWN("UNKNOWN", 0xFFFF);

	private static Map<String, MicrosoftLanguageId> meaningToEnum = new HashMap<String, MicrosoftLanguageId>();
	private static Map<Integer, MicrosoftLanguageId> valueToEnum = new HashMap<Integer, MicrosoftLanguageId>();

	static
	{
		for (MicrosoftLanguageId languageId : EnumSet.allOf(MicrosoftLanguageId.class))
		{
			meaningToEnum.put(languageId.getMeaning().toLowerCase(), languageId);
			valueToEnum.put(languageId.getValue(), languageId);
		}
	}

	private String meaning;
	private int value;

	private MicrosoftLanguageId(String meaning, int value)
	{
		this.value = value;
		this.meaning = meaning;

	}

	public String getMeaning()
	{
		return meaning;
	}

	public int getValue()
	{
		return value;
	}

	public static MicrosoftLanguageId byMeaning(String meaning)
	{
		if (meaningToEnum.containsKey(meaning))
			return meaningToEnum.get(meaning);
		else
			return UNKNOWN;
	}

	public static MicrosoftLanguageId byValue(int value)
	{
		if (valueToEnum.containsKey(value))
			return valueToEnum.get(value);
		else
			return UNKNOWN;

	}

}
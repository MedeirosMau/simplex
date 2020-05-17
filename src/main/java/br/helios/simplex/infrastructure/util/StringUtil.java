package br.helios.simplex.infrastructure.util;

public class StringUtil {

	public static final String EQUAL = "=";

	public static final String POSITIVE = "+";
	public static final String NEGATIVE = "-";

	public static final String DOT = ".";
	public static final String COMA = ",";

	public static boolean moreThanOneChar(String text) {
		return text.length() > 1;
	}

	public static boolean hasElement(String text, String element) {
		int index = text.indexOf(element);
		return index < 0 ? false : true;
	}

	public static String removeEmptyValues(String text) {
		return text.trim().replaceAll("\\s", "");
	}

	public static String toShortString(boolean value) {
		return value ? "Y" : "N";
	}

}

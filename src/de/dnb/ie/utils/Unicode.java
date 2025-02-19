package de.dnb.ie.utils;

import de.dnb.basics.applicationComponents.strings.StringUtils;

/**
 * Macht aus der Zwischenablage eine mittels Editor suchbare (zerlegte Umlaute werden
 * zusammengefügt).
 */
public class Unicode {

	public static void main(String[] args) {
		String s = StringUtils.readClipboard();
		StringUtils.writeToClipboard(StringUtils.unicodeComposition(s));
	}

}

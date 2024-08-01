package de.dnb.ie.ddcTk;

import de.dnb.basics.applicationComponents.strings.StringUtils;

public class IDNGenerator {

	static long idn = 0;

	public static String newIDN() {
		idn++;
		String newIDN = Long.toString(idn);
		newIDN = StringUtils.rightPadding(newIDN, 12, '0');
		return newIDN;
	}

	public static String newIDN(String ddc) {
		// String newIDN = "dd" + ddc;
		// newIDN = newIDN.replace(".", "");
		// newIDN = StringUtils.rightPadding(newIDN, 10, '0');
		// return newIDN;
		long hash = Math.abs(ddc.hashCode());
		return StringUtils.rightPadding(Long.toString(hash), 10, '0');
	}

	public static void main(String[] args) {
		System.out.println(newIDN());
		System.out.println(newIDN());
		System.out.println(newIDN());

		System.out.println(newIDN("333.7"));

	}

}

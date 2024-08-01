/**
 *
 */
package de.dnb.ie.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.gnd.utils.SystematikComparator;

/**
 * @author baumann
 *
 */
public class SortiereSystematik {

	/**
	 * Immer anpassen:
	 */
	private static final int SPALTE_SYST = 2;

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final String[][] table = StringUtils.readTableFromClip();
		final SystematikComparator systcomparator = new SystematikComparator();
		final Comparator<String[]> myComparator = (arr1, arr2) -> systcomparator
				.compare(arr1[SPALTE_SYST], arr2[SPALTE_SYST]);
		Arrays.sort(table, myComparator);
		System.out.println("Anfang");

		final List<String> zeilen = new ArrayList<>(table.length);

		for (final String[] line : table) {
			final String concatenated = StringUtils.concatenateTab(line);
			zeilen.add(concatenated);
			System.out.println(concatenated);
		}
		final String newTable = StringUtils.concatenate(zeilen);
		StringUtils.writeToClipboard(newTable);
	}

}

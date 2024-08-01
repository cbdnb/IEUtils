/**
 *
 */
package de.dnb;

import java.util.List;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.collections.Frequency;

/**
 * Übernimmt das Clipboard und macht ein Histogramm der Zeilen. Da Excel nicht
 * in der Lage ist, die Zahl der verschiedenen Einträge zu zählen, ist das hier
 * ein Ersatz.
 *
 * @author baumann
 *
 */
public class Histogramm {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final List<String> lines = StringUtils.readLinesFromClip();
		final Frequency<String> histogramm = new Frequency<>();
		lines.forEach(histogramm::add);
		System.out.println(histogramm);
		StringUtils.writeToClipboard(histogramm.toString());
	}

}

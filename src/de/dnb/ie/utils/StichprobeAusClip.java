/**
 *
 */
package de.dnb.ie.utils;

import java.util.Collection;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.collections.ReservoirSample;

/**
 * @author baumann
 *
 */
public class StichprobeAusClip {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		System.out.println("Größe der Stichprobe (bitte eingeben):");
		final Collection<String> lines = StringUtils.readLinesFromClip();
		final String ssizeS = StringUtils.readConsole();
		int ssize = 100;
		try {
			ssize = Integer.parseInt(ssizeS);
		} catch (final NumberFormatException e) {
			// nix
		}
		final ReservoirSample<String> strings = new ReservoirSample<>(ssize);

		strings.addAll(lines);
		strings.forEach(System.out::println);
		;
	}

}

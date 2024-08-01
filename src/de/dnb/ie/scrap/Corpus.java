/**
 *
 */
package de.dnb.ie.scrap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import de.dnb.basics.applicationComponents.FileUtils;
import de.dnb.ie.utils.DB.GND_DB_UTIL;

/**
 * @author baumann
 *
 */
public class Corpus {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void main(final String[] args)
			throws ClassNotFoundException, IOException {
		final HashMap<Integer, String> map = GND_DB_UTIL.getppn2name();
		final PrintWriter datei = FileUtils
				.oeffneAusgabeDatei("D:/Normdaten/corpora/Tg.txt", false);
		map.values().forEach(datei::println);
		FileUtils.safeClose(datei);

	}

}

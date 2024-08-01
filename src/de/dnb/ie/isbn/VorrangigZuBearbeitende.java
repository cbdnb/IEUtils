/**
 *
 */
package de.dnb.ie.isbn;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.marc4j.marc.Record;

import de.dnb.basics.applicationComponents.StreamUtils;
import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.marc.MarcBibUtils;
import de.dnb.basics.utils.ISBNCounter;
import de.dnb.basics.utils.PortalUtils;

/**
 * @author baumann
 *
 */
public class VorrangigZuBearbeitende {

	private static final String IDN_LISTE = "D:/Analysen/temp/idnListe.txt";

	private static final String TEST = "D:/Analysen/temp/idnListeTest.txt";

	private static String outFile = "D:/Analysen/temp/unbearbeitete.txt";

	private static boolean DEBUG = false;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		final PrintStream printStream = new PrintStream(outFile);
		final String pathS = DEBUG ? TEST : IDN_LISTE;
		final Path path = FileSystems.getDefault().getPath(pathS);
		final List<String> idns = Files.readAllLines(path);
		idns.forEach(idn ->
		{
			Record record = null;

			record = PortalUtils.getMarcRecord(idn);
			if (record == null)
				return;

			final String isbn = MarcBibUtils.getISBN(record);

			if (isbn == null)
				return;

			final String sg = MarcBibUtils.getSachgruppe(record);

			if (sg == null)
				return;

			final String title = MarcBibUtils.getTitle(record);

			final String jahr = MarcBibUtils.getDateOfProduction(record);

			final String verlag = MarcBibUtils.getNameOfProducer(record);

			final List<Integer> countList = ISBNCounter.getTotalCount(isbn);

			final String counts = StringUtils.concatenate("\t", countList);

			final String outLine = StringUtils.concatenate("\t", idn, sg, title, jahr,
					verlag, counts);

			printStream.println(outLine);
			System.err.println(outLine);

		});

		StreamUtils.safeClose(printStream);

	}

}

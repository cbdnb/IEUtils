package de.dnb.ie.ddcmarc;

import java.io.IOException;
import java.io.PrintStream;
import java.util.zip.GZIPInputStream;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.marc.Record;

import de.dnb.basics.applicationComponents.MyFileUtils;
import de.dnb.basics.marc.MarcUtils;

/**
 * Wandelt in lesbares .txt um. Muss nach Ausführen von MarcAuslieferung
 * betätigt werden (wegen Datum).
 *
 * @author baumann
 *
 */
public class KonvertMarcAuslieferung {

	public static void main(final String[] args) throws IOException {
		final GZIPInputStream gzipInputStream = MyFileUtils
				.getGZipInputStream(MarcAuslieferung.localFileNameGzMarc);
		final MarcReader reader = new MarcStreamReader(gzipInputStream);

		final PrintStream readableStream = new PrintStream(
				MarcAuslieferung.LOCAL_OUT_FILE_NAME + ".txt");
		int i = 0;
		while (reader.hasNext()) {
			final Record record = reader.next();
			i++;
			readableStream.println(MarcUtils.readableFormat(record));

		}
		MyFileUtils.safeClose(readableStream);

		System.out.println("Anzahl: " + i);

	}
}

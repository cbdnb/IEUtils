package de.dnb.ie.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

import de.dnb.basics.Constants;
import de.dnb.basics.applicationComponents.MyFileUtils;
import de.dnb.basics.filtering.RangeCheckUtils;
import de.dnb.basics.utils.TimeUtils;
import de.dnb.gnd.utils.RecordUtils;

/**
 * Macht eine Stichprobe mit Kompressionsfaktor COMPRESSION. Bei einer
 * Kompression um den Faktor 40 sind (nach bisherigen Erfahrungen)
 * Zufallsvariable auf etwa 2% (1/40?) genau.
 *
 * Bitte sicherstellen, dass die Ausgangsdateien auf D:\ liegen.
 *
 * Dauert etwa 13 Minuten.
 *
 * @author baumann
 *
 */
public class MakeSamples {

	public static final int COMPRESSION = 25;

	public static void main(final String[] args) throws IOException {
		System.out.println("Dauert etwa 13 Minuten.");
		System.out.println("Anfang: " + TimeUtils.getActualTimehhMM());

		copyTiteldaten(Constants.TITEL_PLUS_EXEMPLAR_D);
		copyGND(Constants.GND_TITEL_GESAMT_D);

		System.out.println("Ende: " + TimeUtils.getActualTimehhMM());
	}

	/**
	 * @param gndTitelGesamtZ
	 * @throws IOException
	 */
	private static void copyGND(final String from) throws IOException {

		RangeCheckUtils.assertReferenceParamNotNull("from", from);
		final BufferedReader in = MyFileUtils.getGZipReader(from);

		int counterGNDTitle = 0;
		int counterGND = 0;

		final Random gndRandom = new Random();
		final Random gndTitleRandom = new Random();

		int deltaGNDTitel = nextRand(gndTitleRandom);
		int deltaGND = nextRand(gndRandom);

		int counter2GNDTitle = 0;
		int counter2DeltaGND = 0;

		final String gndSample = Constants.GND_STICHPROBE;
		final File gndFile = new File(gndSample);
		if (gndFile.exists())
			throw new IllegalArgumentException(
					"Datei " + gndSample + " existiert schon");
		final PrintStream outGND = MyFileUtils.getGZipPrintStream(gndSample);

		final String titleGNDSample = Constants.GND_TITEL_STICHPROBE;
		final File titleGNDFile = new File(titleGNDSample);
		if (titleGNDFile.exists())
			throw new IllegalArgumentException(
					"Datei " + titleGNDSample + " existiert schon");
		final PrintStream outTitleGND = MyFileUtils
				.getGZipPrintStream(titleGNDSample);

		String line = in.readLine();
		while (line != null) {
			counter2GNDTitle++;
			counterGNDTitle++;

			if (counter2GNDTitle == deltaGNDTitel) {
				counter2GNDTitle = 0;
				deltaGNDTitel = nextRand(gndTitleRandom);
				outTitleGND.print(line + Constants.LF);
			}
			if (RecordUtils.isAuthority(line)) {
				counter2DeltaGND++;
				counterGND++;
				if (counter2DeltaGND == deltaGND) {
					counter2DeltaGND = 0;
					deltaGND = nextRand(gndRandom);
					outGND.print(line + Constants.LF);
				}

			}

			line = in.readLine();
		} // while

		MyFileUtils.safeClose(in);
		MyFileUtils.safeClose(outGND);
		MyFileUtils.safeClose(outTitleGND);

		System.out.println("Zahl gesamt: " + counterGNDTitle);
		System.out.println("Zahl GND: " + counterGND);

	}

	public static void copyTiteldaten(final String from) throws IOException {

		RangeCheckUtils.assertReferenceParamNotNull("from", from);
		final BufferedReader in = MyFileUtils.getGZipReader(from);

		final Random titleRandom = new Random();
		int delta = nextRand(titleRandom);
		int counterTitle = 0;
		int counter2delta = 0;

		final String titleSample = Constants.TITEL_STICHPROBE;
		final File titleFile = new File(titleSample);
		if (titleFile.exists())
			throw new IllegalArgumentException(
					"Datei " + titleSample + " existiert schon");
		final PrintStream outTitle = MyFileUtils.getGZipPrintStream(titleSample);

		String line = in.readLine();
		while (line != null) {
			// keine Normdaten!
			counter2delta++;
			counterTitle++;
			if (counter2delta == delta) {
				counter2delta = 0;
				delta = nextRand(titleRandom);
				outTitle.print(line + Constants.LF);
			}
			line = in.readLine();
		}

		MyFileUtils.safeClose(in);
		MyFileUtils.safeClose(outTitle);
		System.out.println("Zahl Titel: " + counterTitle);
	}

	/**
	 *
	 * @return nächste Zufallszahl zwischen 1 und COMPRESSION *2 - 1. Der
	 *         Erwartungswert der Zufallszahlen ist demzufolge COMPRESSION.
	 */
	public static int nextRand(final Random random) {
		return COMPRESSION;
		// return random.nextInt(COMPRESSION * 2 - 1) + 1;
	}
}

package de.dnb.ie.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Calendar;

import de.dnb.basics.Constants;
import de.dnb.basics.applicationComponents.FileUtils;
import de.dnb.basics.filtering.RangeCheckUtils;
import de.dnb.gnd.utils.RecordUtils;

/**
 * Kopiert den Gesamtdatenabzug in verschiedene Teildateien, die den Datentypen
 * p,g, ... zugeordnet sind. Dauert etwa 12 Minuten!
 *
 * @author baumann
 *
 */
public class NormdatenAusGesamtabzug {

	private static final String TAG_005 = "002@ " + Constants.US;

	public static void main(final String[] args) throws IOException {
		System.out.println(
				" Kopiert den Gesamtdatenabzug in verschiedene Teildateien, "
						+ "die den Datentypen\r\n"
						+ " p,g, ... zugeordnet sind. Dauert etwa 12 Minuten!");
		final DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
		Calendar now = Calendar.getInstance();
		System.out.println("Anfang: " + df.format(now.getTime()));

		copyAuthorityData(Constants.GND_TITEL_GESAMT_D);

		now = Calendar.getInstance();
		System.out.println("Ende: " + df.format(now.getTime()));

	}

	public static void copyAuthorityData(final String from) throws IOException {
		RangeCheckUtils.assertReferenceParamNotNull("from", from);
		final BufferedReader in = FileUtils.getGZipReader(from);

		final String gndFull = Constants.GND;
		final File gndFullFile = new File(gndFull);
		if (gndFullFile.exists())
			throw new IllegalArgumentException(
					"Datei " + gndFull + " existiert schon");
		final PrintStream outFull = FileUtils.getGZipPrintStream(gndFull);

		final String gndP = Constants.Tp;
		final File gndPFile = new File(gndP);
		if (gndPFile.exists())
			throw new IllegalArgumentException(
					"Datei " + gndP + " existiert schon");
		final PrintStream outP = FileUtils.getGZipPrintStream(gndP);

		final String gndN = Constants.ORDNER_ABZUG_LOKAL + "DNBGND_n.dat.gz";
		final File gndNFile = new File(gndN);
		if (gndNFile.exists())
			throw new IllegalArgumentException(
					"Datei " + gndN + " existiert schon");
		final PrintStream outN = FileUtils.getGZipPrintStream(gndN);

		final String gndB = Constants.Tb;
		final File gndBFile = new File(gndB);
		if (gndBFile.exists())
			throw new IllegalArgumentException(
					"Datei " + gndB + " existiert schon");
		final PrintStream outB = FileUtils.getGZipPrintStream(gndB);

		final String gndF = Constants.Tf;
		final File gndFFile = new File(gndF);
		if (gndFFile.exists())
			throw new IllegalArgumentException(
					"Datei " + gndF + " existiert schon");
		final PrintStream outF = FileUtils.getGZipPrintStream(gndF);

		final String gndU = Constants.Tu;
		final File gndUFile = new File(gndU);
		if (gndUFile.exists())
			throw new IllegalArgumentException(
					"Datei " + gndU + " existiert schon");
		final PrintStream outU = FileUtils.getGZipPrintStream(gndU);

		final String gndG = Constants.Tg;
		final File gndGFile = new File(gndG);
		if (gndGFile.exists())
			throw new IllegalArgumentException(
					"Datei " + gndG + " existiert schon");
		final PrintStream outG = FileUtils.getGZipPrintStream(gndG);

		final String gndS = Constants.Ts;
		final File gndSFile = new File(gndS);
		if (gndSFile.exists())
			throw new IllegalArgumentException(
					"Datei " + gndS + " existiert schon");
		final PrintStream outS = FileUtils.getGZipPrintStream(gndS);

		final String gndC = Constants.Tc;
		final File gndCFile = new File(gndC);
		if (gndCFile.exists())
			throw new IllegalArgumentException(
					"Datei " + gndC + " existiert schon");
		final PrintStream outC = FileUtils.getGZipPrintStream(gndC);

		final String gndOhne = Constants.ORDNER_ABZUG_LOKAL
				+ "DNBGND_kein_Typ.dat.gz";
		final File gndOhneFile = new File(gndOhne);
		if (gndOhneFile.exists())
			throw new IllegalArgumentException(
					"Datei " + gndOhne + " existiert schon");
		final PrintStream outOhne = FileUtils.getGZipPrintStream(gndOhne);

		String line = in.readLine();
		while (line != null) {
			if (RecordUtils.isAuthority(line)) {
				outFull.print(line + Constants.LF);
				if (line.contains(TAG_005 + "0" + "Tp"))
					outP.print(line + Constants.LF);
				else if (line.contains(TAG_005 + "0" + "Tn"))
					outN.print(line + Constants.LF);
				else if (line.contains(TAG_005 + "0" + "Tb"))
					outB.print(line + Constants.LF);
				else if (line.contains(TAG_005 + "0" + "Tf"))
					outF.print(line + Constants.LF);
				else if (line.contains(TAG_005 + "0" + "Tu"))
					outU.print(line + Constants.LF);
				else if (line.contains(TAG_005 + "0" + "Tg"))
					outG.print(line + Constants.LF);
				else if (line.contains(TAG_005 + "0" + "Ts"))
					outS.print(line + Constants.LF);
				else if (line.contains(TAG_005 + "0" + "Tc"))
					outC.print(line + Constants.LF);
				else
					outOhne.print(line + Constants.LF);

			}
			line = in.readLine();

		}
		FileUtils.safeClose(in);
		FileUtils.safeClose(outFull);
		FileUtils.safeClose(outP);
		FileUtils.safeClose(outN);
		FileUtils.safeClose(outB);
		FileUtils.safeClose(outF);
		FileUtils.safeClose(outU);
		FileUtils.safeClose(outG);
		FileUtils.safeClose(outS);
		FileUtils.safeClose(outC);
		FileUtils.safeClose(outOhne);

	}
}

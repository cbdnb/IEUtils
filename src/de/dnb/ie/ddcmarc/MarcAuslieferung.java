package de.dnb.ie.ddcmarc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamWriter;
import org.marc4j.MarcXmlReader;
import org.marc4j.MarcXmlWriter;
import org.marc4j.marc.Leader;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

import de.dnb.basics.Constants;
import de.dnb.basics.applicationComponents.MyFileUtils;
import de.dnb.basics.marc.DDCMarcUtils;
import de.dnb.basics.marc.MarcUtils;
import de.dnb.basics.utils.TimeUtils;

/**
 * Erzeugt eine verkürzte Version der DDC im MARC-XML und im MARC-Format. Diese
 * Version kann weitergegeben werden, ohne die Urheberrechte von OCLC zu
 * verletzen.
 *
 * @author baumann
 *
 */
public class MarcAuslieferung {

	private static final String IN_FILE = Constants.DDC_XML;

	private static final String EXTENSION_MRC = ".mrc.gz";
	private static final String EXTENSION_XML = ".xml.gz";

	private static final String OUT_FILE_RUMPF = "DDCDeutsch_cc-liz_Auszug_marc";

	private static final String OUT_FILE_MARC = OUT_FILE_RUMPF + "_" + TimeUtils.getToday() + EXTENSION_MRC;
	private static final String OUT_FILE_XML = OUT_FILE_RUMPF + "xml" + "_" + TimeUtils.getToday() + EXTENSION_XML;

	/**
	 * D:/Normdaten/marc_shortened
	 */
	private static final String LOCAL_FOLDER = "D:/Normdaten/marc_shortened/";

	private static final String TOP_FOLDER = "V:/Anwendungen/DDC/Daten/cc-lizenzierterAuszug_DDC/";

	private static final String V_FOLDER = TOP_FOLDER + TimeUtils.getActualYear() + "_DDC23_" + TimeUtils.getToday()
			+ "/";

	static final String LOCAL_OUT_FILE_NAME = LOCAL_FOLDER + OUT_FILE_RUMPF;

	static String localFileNameGzMarc = LOCAL_FOLDER + OUT_FILE_MARC;
	private static String localFileNameXML = LOCAL_FOLDER + OUT_FILE_XML;

	public static void main(final String[] args) throws IOException {
		MyFileUtils.ensurePathExists(localFileNameGzMarc);
		System.out.println(localFileNameGzMarc);
		final PrintStream marcPS = MyFileUtils.getGZipPrintStream(localFileNameGzMarc);
		final PrintStream xmlgzPS = MyFileUtils.getGZipPrintStream(localFileNameXML);
		System.out.println(localFileNameXML);
		final MarcStreamWriter marcStreamWriter = new MarcStreamWriter(marcPS, "UTF-8");
		final MarcXmlWriter xmlWriter = new MarcXmlWriter(xmlgzPS, "UTF-8", true);

		final InputStream input = new FileInputStream(IN_FILE);
		final MarcReader marcReader = new MarcXmlReader(input);

		final PrintStream readableStream = new PrintStream(LOCAL_OUT_FILE_NAME + ".txt");

		int i = 0;

		while (marcReader.hasNext()) {
			final Record record = marcReader.next();
			if (DDCMarcUtils.isDDCRecord(record)) {
				// nach Mail Fr. Alex von 27.1.2021:
				if (DDCMarcUtils.isInternalAddTable(record))
					continue;

				final List<VariableField> fields253 = record.getVariableFields("253");
				final List<VariableField> fields7XX = MarcUtils.getFieldsBetween(record, "700", "754");

				MarcUtils.removeFieldsGreaterThanTag(record, "153");

				for (final VariableField field253 : fields253) {
					record.addVariableField(field253);
				}
				for (final VariableField field7XX : fields7XX) {
					record.addVariableField(field7XX);
				}

				final Leader leader = record.getLeader();
				final char[] pos17to19 = leader.getImplDefined2();
				pos17to19[0] = 'o';
				leader.setImplDefined2(pos17to19);
				record.setLeader(leader);
				// System.err.println(record);
				marcStreamWriter.write(record);

				i++;
				/*
				 * Das Folgende wäre gar nicht nötig, da der Leader der
				 * ursprünglichen Datensätze nicht korrekt ist: Die Länge und
				 * Basis-Adresse sind mit 0 angegeben.
				 */
				final Record normalizedRecord =
						// record;
						MarcUtils.normalize(record);
				readableStream.println(MarcUtils.readableFormat(normalizedRecord));
				xmlWriter.write(normalizedRecord);
				// System.err.println(normalizedRecord);

			}
		}

		MyFileUtils.safeClose(input);
		marcStreamWriter.close();

		MyFileUtils.safeClose(readableStream);
		xmlWriter.close();

		final Path targetDir = Files.createDirectory(Paths.get(V_FOLDER));
		Path source = Paths.get(localFileNameXML);
		Path target = targetDir.resolve(OUT_FILE_XML);
		Files.copy(source, target);

		source = Paths.get(localFileNameGzMarc);
		target = targetDir.resolve(OUT_FILE_MARC);
		Files.copy(source, target);

		System.out.println("Anzahl: " + i);
		System.out.println("Ordner: " + V_FOLDER);

	}

}

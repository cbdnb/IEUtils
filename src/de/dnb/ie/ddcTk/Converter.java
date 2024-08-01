package de.dnb.ie.ddcTk;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.swing.JOptionPane;

import org.marc4j.MarcReader;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import de.dnb.basics.Constants;
import de.dnb.basics.Misc;
import de.dnb.basics.applicationComponents.tuples.Pair;
import de.dnb.basics.marc.DDCMarcUtils;
import de.dnb.basics.marc.MarcUtils;
import de.dnb.basics.utils.OutputUtils;
import de.dnb.gnd.exceptions.IllFormattedLineException;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.parser.line.Line;
import de.dnb.gnd.parser.line.LineFactory;
import de.dnb.gnd.parser.tag.GNDTagDB;
import de.dnb.gnd.parser.tag.Tag;
import de.dnb.gnd.parser.tag.TagDB;
import de.dnb.gnd.utils.GNDUtils;
import de.dnb.gnd.utils.formatter.HTMLFormatter;

public class Converter {

	public static final TagDB TAG_DB = GNDTagDB.getDB();

	public static final Tag TAG_001 = TAG_DB.getPica3("001");

	public static final Tag TAG_002 = TAG_DB.getPica3("002");

	public static final Tag TAG_003 = TAG_DB.getPica3("003");

	public static final Tag TAG_005 = TAG_DB.getPica3("005");

	public static final Tag TAG_011 = TAG_DB.getPica3("011");

	public static final Tag TAG_153 = TAG_DB.getPica3("153");

	public static final Tag TAG_453 = TAG_DB.getPica3("453");

	public static final Tag TAG_553 = TAG_DB.getPica3("553");

	public static final Tag TAG_667 = TAG_DB.getPica3("667");

	public static final Tag TAG_670 = TAG_DB.getPica3("670");

	public static final Tag TAG_797 = TAG_DB.getPica3("797");

	public static final String WEBDEWEY = "deweyde.pansoft.de/webdewey/index_11.html?recordId=ddc%3a";

	/**
	 *
	 * @param marcRecord
	 *            nicht null
	 * @return neuen Record oder null
	 *
	 */
	public void store(final org.marc4j.marc.Record marcRecord) {

		// System.out.println(marcRecord);

		if (!DDCMarcUtils.isUsedInWinIBW(marcRecord))
			return;

		try {

			final String ddcNumber = DDCMarcUtils
					.getClassificationNumber(marcRecord);
			final String fullDDC = DDCMarcUtils
					.getFullClassificationNumber(marcRecord);
			if (fullDDC == null || fullDDC
					.length() < DDCMarcUtils.MINIMAL_DDC_NUMBER_LENGTH)
				return;

			final String idn = IDNGenerator.newIDN(fullDDC);
			final Record tkRecord = new Record(idn, TAG_DB);

			Date date = MarcUtils.getDate(marcRecord);
			if (date == null)
				date = new Date();
			final String dateS = Misc.asIBWDate(date);
			final String timeS = Misc.asIBWTime(date);
			LineFactory lineFactory = TAG_001.getLineFactory();
			lineFactory.load("$01250:" + dateS);
			Line line = lineFactory.createLine();
			tkRecord.add(line);

			lineFactory = TAG_002.getLineFactory();
			lineFactory.load("$01250:" + dateS + "$t" + timeS);
			line = lineFactory.createLine();
			tkRecord.add(line);

			lineFactory = TAG_003.getLineFactory();
			lineFactory.load("$01250:" + dateS);
			line = lineFactory.createLine();
			tkRecord.add(line);

			lineFactory = TAG_005.getLineFactory();
			lineFactory.load("Tk");
			line = lineFactory.createLine();
			tkRecord.add(line);

			lineFactory = TAG_011.getLineFactory();
			lineFactory.load("kd");
			line = lineFactory.createLine();
			tkRecord.add(line);

			lineFactory = TAG_153.getLineFactory();
			String lineS;
			final String table = DDCMarcUtils.getTableNumber(marcRecord);
			if (table != null)
				lineS = "$b" + table;
			else
				lineS = "";
			lineS += "$a" + ddcNumber;
			final String caption = DDCMarcUtils.getCaption(marcRecord);
			if (caption != null)
				lineS += "$j" + caption;
			lineFactory.load(lineS);
			line = lineFactory.createLine();
			tkRecord.add(line);

			lineFactory = TAG_453.getLineFactory();
			final List<DataField> indexFields = DDCMarcUtils
					.getIndexTermFields(marcRecord);
			for (final DataField dataField : indexFields) {
				lineS = "$Sa$a" + dataField.getSubfield('a').getData();
				final List<Subfield> subsx = dataField.getSubfields('x');
				for (final Subfield subfield : subsx) {
					lineS += "$x" + subfield.getData();
				}
				lineS += generateComment();
				lineFactory.load(lineS);
				line = lineFactory.createLine();
				tkRecord.add(line);
			}

			final Collection<Pair<String, String>> highdets = Database
					.getCrissCrossHighDet(fullDDC);
			for (final Pair<String, String> highdet : highdets) {
				final String swID = highdet.first;
				final String swName = highdet.second;
				lineS = "$Sc$9" + swID + "$8" + swName + generateComment();
				lineFactory.load(lineS);
				line = lineFactory.createLine();
				tkRecord.add(line);
			}

			lineFactory = TAG_553.getLineFactory();

			final Record obRecord = Database.getOBTkByDDC(fullDDC);

			if (obRecord != null) {
				try {
					final String obID = obRecord.getId();
					final String obDDC = Database.getDDCByID(obID);
					Database.putUB(obDDC, fullDDC);
					final String obName = GNDUtils.getNameOfRecord(obRecord);
					lineFactory.load("$9" + obID + "$8" + obName + "$4nueb");
				} catch (final Exception e) {
					// TODO Auto-generated catch block
					System.err.println(obRecord);
				}
				line = lineFactory.createLine();
				tkRecord.add(line);
			}

			lineFactory = TAG_667.getLineFactory();
			final List<VariableField> lines = marcRecord
					.getVariableFields("685");
			for (final VariableField variableField : lines) {
				String s = "";
				final DataField dataField = (DataField) variableField;
				final List<Subfield> subs = dataField.getSubfields();
				for (final Subfield subfield : subs) {
					final char ind = subfield.getCode();
					if (ind == 'z') {
						s += "T" + subfield.getData() + "--";
					} else if (ind == 'a') {
						s += subfield.getData();
					} else if (ind == 'i') {
						s += subfield.getData() + " ";
					}
				}
				lineFactory.load("$a" + s.trim());
				line = lineFactory.createLine();
				tkRecord.add(line);
			}

			lineFactory = TAG_670.getLineFactory();
			lineFactory.load("$u" + WEBDEWEY + fullDDC);
			line = lineFactory.createLine();
			tkRecord.add(line);

			lineFactory = TAG_797.getLineFactory();
			lineFactory.load("$0" + idn);
			line = lineFactory.createLine();
			tkRecord.add(line);

			Database.putRecord(fullDDC, tkRecord, marcRecord);
			return;

		} catch (final OperationNotSupportedException e) {
			e.printStackTrace();
		} catch (final IllFormattedLineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return Kommentar
	 */
	private String generateComment() {
		return "";
		// return "$vBemerkung?";
	}

	public void connectUBB(final String obDDC) {
		final Record obRecord = Database.getTkByDDC(obDDC);

		final Collection<String> ubb = Database.getUbbDDC(obDDC);
		final LineFactory lineFactory = TAG_553.getLineFactory();
		for (final String ubDDC : ubb) {
			final Record ubRecord = Database.getTkByDDC(ubDDC);
			final String ubID = ubRecord.getId();
			final String ubName = GNDUtils.getNameOfRecord(ubRecord);
			try {
				lineFactory.load("$9" + ubID + "$8" + ubName + "$4nunt");
				final Line line = lineFactory.createLine();
				obRecord.add(line);

			} catch (IllFormattedLineException
					| OperationNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void load(final String file) throws FileNotFoundException {
		final InputStream input = new FileInputStream(file);
		final MarcReader marcReader = new MarcXmlReader(input);

		int i = 0;

		while (marcReader.hasNext()) {
			final org.marc4j.marc.Record marcRecord = marcReader.next();
			store(marcRecord);
			i++;
			if (i > 100000)
				break;
		}

		final Iterator<String> iterator = Database.getDDCIterator();
		for (; iterator.hasNext();) {

			final String ddc = iterator.next();
			connectUBB(ddc);
		}
	}

	public static void main(final String[] args)
			throws OperationNotSupportedException, IllFormattedLineException,
			FileNotFoundException {
		final Converter converter = new Converter();
		converter.load(Constants.DDC_XML);

		String ddc = JOptionPane.showInputDialog("Bitte DDC eingeben:");
		while (ddc != null) {
			final Record tkRecord = Database.getTkByDDC(ddc);
			System.out.println(tkRecord);
			if (tkRecord != null) {
				final HTMLFormatter formatter = new HTMLFormatter();
				formatter.setFontsize(16);
				formatter.setBorder(0);
				formatter.setSpacing(-3);
				formatter.setWidth(100);

				final String txt = formatter.format(tkRecord);
				OutputUtils.show(txt);

			}
			ddc = JOptionPane.showInputDialog("Bitte DDC eingeben:");
		}

	}

}

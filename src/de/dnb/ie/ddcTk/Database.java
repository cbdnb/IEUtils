package de.dnb.ie.ddcTk;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import de.dnb.basics.applicationComponents.MyFileUtils;
import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.applicationComponents.tuples.Pair;
import de.dnb.basics.collections.ListMultimap;
import de.dnb.basics.collections.Multimap;
import de.dnb.basics.collections.TreeMultimap;
import de.dnb.basics.marc.DDCMarcUtils;
import de.dnb.basics.trees.ITreeFunction;
import de.dnb.basics.trees.TreeUtils;
import de.dnb.gnd.parser.Record;

public class Database {

	private static final String DET34_FILE = "D:/Normdaten/ddc2Det3and4.out";

	private static Map<String, Record> ddc2tk = new TreeMap<String, Record>();

	private static Map<String, org.marc4j.marc.Record> ddc2marc = new TreeMap<String, org.marc4j.marc.Record>();

	private static Map<String, Record> id2Tk = new TreeMap<String, Record>();

	private static Map<String, org.marc4j.marc.Record> id2Marc = new TreeMap<String, org.marc4j.marc.Record>();

	private static Multimap<String, String> obDDC2ubDDC = new TreeMultimap<>();

	private static ListMultimap<String, Pair<String, String>> ddc2Det34;

	public static Collection<String> getTopTerms() {
		return TreeUtils.getTopTerms(obDDC2ubDDC);
	}

	public static ITreeFunction<String> getTreeFunction() {
		return TreeUtils.getTreeFunction(obDDC2ubDDC);
	}

	/**
	 * ddc-Nummer -> (id, name).
	 *
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private static void loadDDC2Det34()
			throws ClassNotFoundException, IOException {

		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(
					new FileInputStream(DET34_FILE));
		} catch (final FileNotFoundException e) {

		}
		ddc2Det34 = (ListMultimap<String, Pair<String, String>>) objectInputStream
				.readObject();
		MyFileUtils.safeClose(objectInputStream);
	}

	/**
	 *
	 * @param ddc
	 *            nicht null
	 * @return SW-ID, SW-Name nicht null
	 */
	public static Collection<Pair<String, String>> getCrissCrossHighDet(
			final String ddc) {
		if (ddc2Det34 == null)
			try {
				loadDDC2Det34();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return ddc2Det34.getNullSafe(ddc);
	}

	public static void putTkByDDC(final String ddc, final Record record) {
		ddc2tk.put(ddc, record);
	}

	public static Record getTkByDDC(final String ddc) {
		return ddc2tk.get(ddc);
	}

	/**
	 *
	 * @param ddc
	 *            Volle ddc
	 * @return
	 */
	public static Record getOBTkByDDC(String ddc) {
		if (ddc == null)
			return null;
		Record record = null;
		// such nach OB durch verkürzen
		while (record == null && ddc.length() > 1) {
			ddc = ddc.substring(0, ddc.length() - 1);
			String searchTerm = ddc;
			if (ddc.charAt(0) != 'T') {
				searchTerm = StringUtils.rightPadding(ddc, 3, '0');
			} else if (searchTerm.endsWith("--")) {
				// TX--: ein letzter Versuch...
				searchTerm = searchTerm + '0';
			}
			record = ddc2tk.get(searchTerm);
			if (searchTerm.endsWith("--0"))
				break;
		}
		return record;
	}

	public static void putTkByID(final String id, final Record record) {
		id2Tk.put(id, record);
	}

	/**
	 *
	 * @param id
	 *            nicht null
	 * @return null, wenn nichts gefunden
	 */
	public static Record getTkByID(final String id) {
		return id2Tk.get(id);
	}

	public static void putByMarc(final String ddc,
			final org.marc4j.marc.Record record) {
		ddc2marc.put(ddc, record);
	}

	public static org.marc4j.marc.Record getMarcByDDC(final String ddc) {
		return ddc2marc.get(ddc);
	}

	public static void putMarcByID(final String id,
			final org.marc4j.marc.Record record) {
		id2Marc.put(id, record);
	}

	public static org.marc4j.marc.Record getMarcByID(final String id) {
		return id2Marc.get(id);
	}

	public static String getIDByDDC(final String ddc) {
		final Record tkRecord = getTkByDDC(ddc);
		if (tkRecord == null)
			return null;
		else
			return tkRecord.getId();
	}

	public static String getDDCByID(final String id) {
		final org.marc4j.marc.Record marcRecord = getMarcByID(id);
		if (marcRecord == null)
			return null;
		else
			return DDCMarcUtils.getFullClassificationNumber(marcRecord);
	}

	public static void putRecord(final String fullDDC, final Record tkRecord,
			final org.marc4j.marc.Record marcRecord) {
		putTkByDDC(fullDDC, tkRecord);
		putTkByID(tkRecord.getId(), tkRecord);
		putMarcByID(tkRecord.getId(), marcRecord);
		putByMarc(fullDDC, marcRecord);
	}

	public static void putUB(final String obDDC, final String ubDDC) {
		if (ubDDC == null)
			obDDC2ubDDC.add(obDDC);
		else
			obDDC2ubDDC.add(obDDC, ubDDC);
	}

	public static Iterator<String> getDDCIterator() {
		return ddc2tk.keySet().iterator();
	}

	/**
	 *
	 * @param obDDC
	 *            beliebig
	 * @return nicht null
	 */
	public static Collection<String> getUbbDDC(final String obDDC) {
		return obDDC2ubDDC.getNullSafe(obDDC);
	}

}

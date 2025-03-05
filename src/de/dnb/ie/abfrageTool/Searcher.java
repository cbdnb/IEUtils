package de.dnb.ie.abfrageTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.filtering.FilterUtils;
import de.dnb.basics.utils.TimeUtils;
import de.dnb.ie.abfrageTool.View.TEILBESTAND;

public class Searcher {

	private View view;
	final String AND = " AND ";
	static final String OR = " OR ";
	/**
	 * auch null
	 */
	private String commonDateTTMMJJ;
	private List<Calendar> arbeitstage;
	private String commonDateJJJJMMTT;
	/**
	 * "(tbs s)" oder null
	 */
	private String tbs;
	/**
	 * z.B. "NOT ser 1150" oder null
	 */
	private String standortAnsetzung;
	/**
	 * "f", "l" oder ""
	 */
	private String standortMx;
	/**
	 * "f", "l" oder "?"
	 */
	private String standortZugew;

	/**
	 * dke 12[34]? oder 11[34]?
	 */
	private String standortReihe;

	/**
	 * "(sn 21.2 OR 21.3)" oder null
	 */
	private String gndSystematik;
	/**
	 * "(bbg Ts* OR Tp*)" oder null
	 */
	private String satzarten;
	/**
	 * Eingabedatum, auch null, wenn alle Daten verwendet werden.
	 */
	private String ser;
	/**
	 * Datum der Beschlagwortung, auch null, wenn alle Daten verwendet werden.
	 */
	private String dsw;
	private String clip = "";
	private static final String F = "f ";

	public Searcher(View view) {
		this.view = view;
	}

	/**
	 * wird nicht ausgeführt, wenn keine sinnvolle Suchfrage möglich.
	 */
	private void neuansetzungen() {
		String phrase = and(gndSystematik, ser, tbs, satzarten, standortAnsetzung);
		if (phrase == null)
			return;
		String suchstring = F + phrase;
		clip += suchstring + "\n";
		System.out.println(suchstring);
	}

	/**
	 * 
	 */
	public void berechneSystematik() {
		List<String> systs = view.getSyst();
		gndSystematik = machSuchFragment("sn", or(systs));
	}

	/**
	 * wird immer ausgeführt. Ohne weitere Angaben wird nach allen Mx gesucht,
	 * die von/an DNB sind.
	 */
	private void mailbox() {
		// ede101sef?
		String adressierung = "";
		switch (view.getAdressierung()) {
		case ABSENDER:
			adressierung += "a";
			break;
		case EMPFAENGER:
			adressierung += "e";
			break;
		default:
			adressierung += "[ae]";
			break;
		}
		adressierung += "de101se" + standortMx + "? ";

		String mxDatum = null;
		if (!view.useAlleDaten()) {
			mxDatum = commonDateJJJJMMTT;
		}

		String mx = "(mx " + and(adressierung, mxDatum) + ")";

		String suchstring = F + and(mx, gndSystematik, satzarten, tbs);
		clip += suchstring + "\n";
		System.out.println(suchstring);
	}

	/**
	 * Wird nicht ausgeführt, wenn keine Sachgruppen und kein Standort angegeben
	 * sind.
	 * 
	 */
	private void automSWW() {
		List<String> sgg = view.getSGG();
		if (sgg.isEmpty() && standortAnsetzung == null)
			return;
		String efa = machSuchFragment("efa", or(sgg));

		String bbg = "bbg Aa";

		String icd = "(icd rb OR rh)";

		// Statusindikator für QM (5051 $a):
		String notSGF = " not sgf [0123]";

		String suchstring = F + and(bbg, icd, dsw, efa, standortAnsetzung, notSGF);
		clip += suchstring + "\n";
		System.out.println(suchstring);
	}

	/**
	 * 
	 * @param indextyp nicht null
	 * @param suchbegriff auch null
	 * @return Phrase wie "(dhs 510 or 520)" immer in Klammern oder null
	 */
	private String machSuchFragment(String indextyp, String suchbegriff) {
		if (suchbegriff == null)
			return null;
		return "(" + indextyp + " " + suchbegriff + ")";
	}

	/**
	 * Wird immer ausgeführt. Im Extremfall wird nach allen Onlinepublikationen
	 * ohne 5050 $Ei gesucht.
	 */
	private void automSGG() {
		String bbg = "bbg oa?";

		String konfidenz = view.getKonfidenzwert();

		List<String> sgg = view.getSGG();
		ArrayList<String> msgg = FilterUtils.map(sgg, sg -> "m" + sg);
		String efa = machSuchFragment("efa", or(msgg));

		String notEfa = "not efa i ";

		String suchstring = F + and(bbg, ser, konfidenz, efa, standortAnsetzung, notEfa);
		clip += suchstring + "\n";
		System.out.println(suchstring);
	}

	/**
	 * Wird nicht ausgeführt, wenn keine Sachgruppen angegeben sind.
	 */
	private void zugeordneteSGG() {
		List<String> sgg = view.getSGG();
		if (sgg.isEmpty())
			return;
		ArrayList<String> sggZugew = FilterUtils.map(sgg, sg -> sg + standortZugew);
		String sgf = machSuchFragment("sgf", or(sggZugew));

		String suchphrase = and(sgf, ser);
		String suchstring = F + suchphrase;
		clip += suchstring + "\n";
		System.out.println(suchstring);
	}

	/**
	 * 
	 */
	public void berechneStandort() {
		switch (view.getStandort()) {
		case FRANKFURT:
			standortAnsetzung = "NOT ser 11##";
			standortZugew = standortMx = "f";
			standortReihe = "dke 12[34]?";
			break;
		case LEIPZIG:
			standortAnsetzung = "NOT ser 12##";
			standortZugew = standortMx = "l";
			standortReihe = "dke 11[34]?";
			break;
		case ALLE:
			standortAnsetzung = null;
			standortMx = "";
			standortZugew = "?";
			standortReihe = null;
			break;
		default:
			standortAnsetzung = null;
			standortMx = "";
			standortZugew = "?";
			standortReihe = null;
			break;
		}
	}

	private void berechneTBS() {
		TEILBESTAND tb = view.getTeilbestand();
		switch (tb) {
		case SE:
			tbs = "(tbs s)";
			break;
		case FE:
			tbs = "(tbs f)";
			break;
		case ALLE:
			tbs = null;
			break;
		default:
			tbs = null;
		}
	}

	private void berechneArbeitstage() {
		final Calendar calendar1 = view.getBis();
		final Calendar calendar2 = view.getVon();
		arbeitstage = TimeUtils.getWorkDaysBetween(calendar1, calendar2);
		final ArrayList<String> listTTMMJJ = FilterUtils.map(arbeitstage, TimeUtils.picaTTMMJJ);
		commonDateTTMMJJ = StringUtils.replaceByWildcard(listTTMMJJ, '#');
		if (commonDateTTMMJJ.startsWith("#")) {
			commonDateTTMMJJ = commonDateTTMMJJ.replaceFirst("#", "[0123]");
		}

		final ArrayList<String> listJJJJMMTT = FilterUtils.map(arbeitstage, TimeUtils.mxJJJJMMTT);
		commonDateJJJJMMTT = StringUtils.replaceByWildcard(listJJJJMMTT, '#');
		if (commonDateJJJJMMTT.startsWith("#")) {
			commonDateJJJJMMTT = commonDateJJJJMMTT.replaceFirst("#", "[0123]");
		}

		ser = null;
		if (!view.useAlleDaten()) {
			ser = "(ser " + commonDateTTMMJJ + ")";
		}

		dsw = null;
		if (!view.useAlleDaten()) {
			dsw = "(dsw " + commonDateJJJJMMTT + ")";
		}
	}

	/**
	 * 
	 * @param strings auch leer
	 * @return null, wenn Liste von nullen
	 */
	private String and(final String... strings) {
		return and(new ArrayList<>(Arrays.asList(strings)));
	}

	/**
	 * filtert die nullen weg.
	 * 
	 * @param strings
	 * @return Ausdruck oder null
	 */
	private String or(Collection<String> strings) {
		if (strings == null)
			return null;
		List<String> newStrings = nullFilteredList(strings);
		if (newStrings.isEmpty())
			return null;
		return StringUtils.concatenate(OR, newStrings);
	}

	/**
	 * filtert die nullen weg.
	 * 
	 * @param strings
	 * @return Ausdruck oder null
	 */
	private String and(Collection<String> strings) {
		if (strings == null)
			return null;
		List<String> newstrings = nullFilteredList(strings);
		if (newstrings.isEmpty())
			return null;
		String concatenated = StringUtils.concatenate(AND, newstrings);
		return concatenated;
	}

	/**
	 * 
	 * @param collection nicht null
	 * @return neue, veränderbare Liste ohne null
	 */
	public <E> List<E> nullFilteredList(Collection<E> collection) {
		List<E> newList = new ArrayList<>(collection);
		FilterUtils.filter(newList, syst -> syst != null);
		return newList;
	}

	public void performSearch() {
		berechneArbeitstage();
		berechneTBS();
		berechneStandort();
		berechneSystematik();
		berechneSatzarten();

		if (view.useNeuansetzungen())
			neuansetzungen();
		if (view.useMx())
			mailbox();
		if (view.useAutomSGG())
			automSGG();
		if (view.useZugeordnete())
			zugeordneteSGG();
		if (view.useAutomSWW())
			automSWW();
		if (view.useSGGFreigeben()) {
			imMagazinOhneFreigabe(true);
		}
		if (view.useOhneSGGFreigeben()) {
			imMagazinOhneFreigabe(false);
		}
		if (view.useSGGFreigebenReihe()) {
			reiheOhneFreigabe(true);
		}
		if (view.useOhneSGGFreigebenReihe()) {
			reiheOhneFreigabe(false);
		}
		if (!clip.isEmpty()) {
			StringUtils.writeToClipboard(clip);
		}
	}

	private void imMagazinOhneFreigabe(boolean mitSGG) {
		String years = macheVorjahre();
		String status = machSuchFragment("sta", "2" + years + "?b");
		String signatur = machSuchFragment("sig", "[2#]*");
		String sgg;
		if (mitSGG) {
			sgg = machSuchFragment("DHS", or(view.getSGG()));
		} else {
			sgg = machSuchFragment("NOT DHS", "[1234567890KS]*");
		}
		String suchstring = F + and(status, signatur, standortAnsetzung, sgg);
		clip += suchstring + "\n";
		System.out.println(suchstring);

	}

	private void reiheOhneFreigabe(boolean mitSGG) {
		String years = macheVorjahre();
		String status = machSuchFragment("sta", "2" + years + "?b");
		String bbg = machSuchFragment("bbg", "[^]*vz");
		String sgg;
		if (mitSGG) {
			String dcz = machSuchFragment("DCZ", or(view.getSGG()));
			String dhs = machSuchFragment("DHS", or(view.getSGG()));
			sgg = "(" + or(Arrays.asList(dcz, dhs)) + ")";
		} else {
			String notdcz = machSuchFragment("NOT DCZ", "[1234567890KS]*");
			String notdhs = machSuchFragment("NOT DHS", "[1234567890KS]*");
			sgg = and(notdcz, notdhs);
		}
		String suchstring = F + and(status, bbg, standortReihe, sgg);
		clip += suchstring + "\n";
		System.out.println(suchstring);

	}

	/**
	 * 
	 * @return "[01234], auf jeden Fall die 4 letzten Vorjahre
	 */
	private String macheVorjahre() {
		int lastDigit = TimeUtils.getActualYear() % 10;
		String years = "[";
		for (int i = lastDigit - 4; i < lastDigit; i++) {
			years += (i + 10) % 10;
		}
		years += "]";
		return years;
	}

	private void berechneSatzarten() {
		List<String> bbgs = view.getSatzarten();
		satzarten = machSuchFragment("bbg", or(bbgs));
	}

}

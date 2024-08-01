/**
 *
 */
package de.dnb.ie.kurznotationen;

import java.io.IOException;
import java.util.Collection;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.applicationComponents.tuples.Pair;
import de.dnb.basics.collections.Frequency;
import de.dnb.basics.tries.TST;
import de.dnb.basics.tries.Trie;
import de.dnb.gnd.utils.DDC_SG;
import de.dnb.gnd.utils.SGUtils;

/**
 * @author baumann
 *
 */
public class Statistik {

	/**
	 * Kurznotation -> Anzahl.
	 */
	private Frequency<String> FREQ_ABRIDGED;

	/**
	 * Längstes Präfix -> Kurznotation. In der Regel (wenn keine Umleitung):
	 * Kurznotation -> Kurznotation.
	 */
	private Trie<String> ABRIDGED;

	private Frequency<String> FREQ_5400_2_Titles;

	private static final String FILENAME_5400_2_Titles = "V:/03_FB_EE/14_IE/"
			+ "08_Anwendungsprogramme/" + "Kurznotationen/lib/5400toTitles.out";

	private static final String ARROW = "->";

	private Consumer<String> errorFunction = System.err::printf;

	public TreeMap<String, Long> machStatistik(String sachgruppe,
			Collection<String> kurznotationen) {

		ABRIDGED = new TST<>();
		FREQ_ABRIDGED = new Frequency<>();

		readAbridged(sachgruppe, kurznotationen);
		fillHundreds(sachgruppe);

		FREQ_5400_2_Titles.forEach(ddc ->
		{
			if (ddc != null && contains(sachgruppe, ddc.trim())) {
				final String trimmed = ddc.trim();
				final String abridged = ABRIDGED
						.getValueOfLongestPrefix(trimmed);
				if (abridged == null) {
					errorFunction.accept("Kein Präfix für " + ddc);
				} else {
					final long count = FREQ_5400_2_Titles.get(trimmed);
					FREQ_ABRIDGED.increment(abridged, count);
				}
			}
		});

		TreeMap<String, Long> statistik = new TreeMap<>();
		FREQ_ABRIDGED.forEach(abri ->
		{
			long count = FREQ_ABRIDGED.get(abri);
			statistik.put(abri, count);
		});

		return statistik;

	}

	/**
	 * @param fREQ_5400_2_Titles
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Statistik() throws ClassNotFoundException, IOException {
		FREQ_5400_2_Titles = new Frequency<>(FILENAME_5400_2_Titles);
	}

	/**
	 * Füllt Ausweichmöglichkeiten für Hunderter und Tausender, damit immer ein
	 * Präfix und eine zugehörige Notation gefunden wird. Beispiele: </br>
	 * Längstes Präfix "1" -> "100" </br>
	 * Längstes Präfix "09" -> "090" </br>
	 * Ausnahme: </br>
	 * Längstes Präfix "04" -> "000" (040 ist unbesetzt).
	 */
	private void fillHundreds(String sg) {
		if (sg.length() != 3)
			return;
		if (!sg.endsWith("0"))
			return;
		if (sg.equals("000")) {
			ABRIDGED.put("0", "000");
			return;
		}
		String sgTrunc = sg.replaceAll("0+", "");
		ABRIDGED.put(sgTrunc, sg);
	}

	/**
	 * Liest die Kurznotationen in {@link #ABRIDGED} ein.
	 */
	private void readAbridged(String sg, Collection<String> kurznotationen) {

		kurznotationen.forEach(notation ->
		{
			if (notation.contains(ARROW)) {
				final String[] pair = notation.split(ARROW);
				ABRIDGED.put(pair[0].trim(), pair[1].trim());
			} else {
				String abridged = notation.trim();
				if (isMainDDC(abridged)) {
					if (abridged.contains(".")) {
						// 0 am Ende weg:
						final String truncatet = abridged
								.replaceAll("(\\.)?0*$", "");
						abridged = truncatet;
					}
					if (contains(sg, abridged)) {
						ABRIDGED.put(abridged, abridged);
						FREQ_ABRIDGED.addKey(abridged);
					}
				}
			}
		});

	}

	/**
	 * @param sg
	 * @return
	 */
	private boolean contains(String sg, String ddc) {
		String sgZuDDC = SGUtils.getSGNullSafe(ddc).map(DDC_SG::getDDCString)
				.orElse("");
		return sgZuDDC.equals(sg);
	}

	String DDC_PAT_S = "\\d\\d\\d(\\.\\d+)?";

	Pattern DDC_PAT = Pattern.compile(DDC_PAT_S);

	/**
	 * @param abridged
	 * @return
	 */
	private boolean isMainDDC(final String abridged) {
		final Matcher matcher = DDC_PAT.matcher(abridged);
		return matcher.matches();
	}

	public void setErrorFunction(Consumer<String> errf) {
		this.errorFunction = errf;
	}

	public static void main(String... args)
			throws ClassNotFoundException, IOException {
		String sg = "333.7";
		Collection<String> lines = StringUtils.readLinesFromClip();
		Statistik statistik = new Statistik();
		TreeMap<String, Long> neueStat = statistik.machStatistik(sg, lines);
		neueStat.forEach((x, y) ->
		{
			System.out.println(x + "->" + y);
		});
	}

}

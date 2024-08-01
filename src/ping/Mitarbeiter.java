package ping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

import de.dnb.basics.applicationComponents.strings.StringUtils;

public class Mitarbeiter implements Comparable<Mitarbeiter> {

	/**
	 *
	 */
	private static final String PREF_W10 = "F-W10WS-";

	@Override
	public String toString() {
		if (StringUtils.isNullOrEmpty(firstName))
			return lastName;
		else
			return lastName + ", " + firstName;
	}

	final String firstName;

	final String lastName;

	final String computer;

	final String unit;

	final String roomNumber;

	final static String DOMAIN = ""; // oder ".ad.ddb.de"

	static Function<Mitarbeiter, String> getLastNameFunction() {
		return new Function<Mitarbeiter, String>() {
			@Override
			public String apply(final Mitarbeiter x) {
				return x.lastName;
			}
		};
	}

	Mitarbeiter(final String lastName, final String firstName,
			final String newComputer, final String newUnit,
			final String newRoomNumber) {
		this.lastName = lastName;
		this.firstName = firstName;
		computer = newComputer;
		unit = newUnit;
		roomNumber = newRoomNumber;
	}

	private static ArrayList<Mitarbeiter> mitarbeiterListe = null;

	public static ArrayList<Mitarbeiter> getMitarbeiterList() {
		fillList();
		return mitarbeiterListe;
	}

	private static void fillList() {
		if (mitarbeiterListe != null)
			return;

		mitarbeiterListe = new ArrayList<>();

		mitarbeiterListe.add(new Mitarbeiter("Schwens", "Ute",
				"f-w7ws-05260" + DOMAIN, "gd", "422"));

		mitarbeiterListe.add(new Mitarbeiter("Junger", "Ulrike",
				"f-w7ws-05113" + DOMAIN, "ee", "333"));
		mitarbeiterListe.add(new Mitarbeiter("Schöning-Walter", "Christa",
				"F-NBW10-05752" + DOMAIN, "ee", "203"));
		mitarbeiterListe.add(new Mitarbeiter("Schumann", "Constanze",
				"f-w7ws-05084" + DOMAIN, "ee-ef", "332"));

		mitarbeiterListe.add(new Mitarbeiter("Mödden", "Elisabeth",
				PREF_W10 + "06063" + DOMAIN, "ee-aen", "230"));
		mitarbeiterListe.add(new Mitarbeiter("Mölck", "Letitia-Venetia",
				PREF_W10 + "06074" + DOMAIN, "ee-aen", "204"));
		mitarbeiterListe.add(new Mitarbeiter("Busse", "Frank",
				"F-NBW10-05721" + DOMAIN, "ee-aen", "208"));
		mitarbeiterListe.add(new Mitarbeiter("Kaiser", "Mirka",
				PREF_W10 + "06257" + DOMAIN, "ee-aen", "208"));
		mitarbeiterListe.add(new Mitarbeiter("Maisner", "Tanja",
				PREF_W10 + "06113" + DOMAIN, "ee-aen", "210"));
		mitarbeiterListe.add(new Mitarbeiter("Puls", "Susanne",
				PREF_W10 + "06307" + DOMAIN, "ee-aen", "218"));
		mitarbeiterListe.add(new Mitarbeiter("Müller", "Sophie",
				"f-w7ws-05028" + DOMAIN, "ee-aen", "218"));

		mitarbeiterListe.add(new Mitarbeiter("Kett", "Jürgen",
				"f-w7ws-05035" + DOMAIN, "ee-afs", "211"));
		mitarbeiterListe.add(new Mitarbeiter("Kett", "Jürgen2",
				"F-NBW10-05735" + DOMAIN, "ee-afs", "211"));
		mitarbeiterListe.add(new Mitarbeiter("Bufalino", "Cinzia",
				PREF_W10 + "06089" + DOMAIN, "ee-afs", "214"));
		mitarbeiterListe.add(new Mitarbeiter("Röschlau", "Edith",
				PREF_W10 + "06309" + DOMAIN, "ee-afs", "214"));
		mitarbeiterListe.add(new Mitarbeiter("Behrens-Neumann", "Renate",
				PREF_W10 + "06207" + DOMAIN, "ee-afs", "214a"));
		mitarbeiterListe.add(new Mitarbeiter("Hartmann", "Sarah",
				PREF_W10 + "06308" + DOMAIN, "ee-afs", "212"));
		mitarbeiterListe.add(new Mitarbeiter("Baum", "Julia",
				PREF_W10 + "06254" + DOMAIN, "ee-afs", "210"));
		mitarbeiterListe.add(new Mitarbeiter("Habermann", "Nicole",
				PREF_W10 + "xxx" + DOMAIN, "ee-afs", "210"));
		mitarbeiterListe.add(new Mitarbeiter("Höhler", "Philipp",
				"f-w7ws-05071" + DOMAIN, "ee-afs", "125"));
		mitarbeiterListe.add(new Mitarbeiter("Töpler", "Ingeborg",
				"f-w7ws-05308" + DOMAIN, "ee-afs", "213"));
		mitarbeiterListe.add(new Mitarbeiter("Thüncher", "Sylvia",
				"f-w7ws-05256" + DOMAIN, "ee-afs", "213"));
		mitarbeiterListe.add(new Mitarbeiter("Glagla-Dietz", "Stephanie",
				PREF_W10 + "06094" + DOMAIN, "ee-afs", "201"));
		mitarbeiterListe.add(new Mitarbeiter("Brandt", "Sibylle",
				"f-w7ws-05270" + DOMAIN, "ee-afs", "201"));

		mitarbeiterListe.add(new Mitarbeiter("Senftleben", "Stefan",
				"f-w7ws-05091" + DOMAIN, "ee_bf.6.3", "125"));

		mitarbeiterListe.add(new Mitarbeiter("Henze", "Volker",
				PREF_W10 + "06355" + DOMAIN, "ee-ie", "235"));

		mitarbeiterListe.add(new Mitarbeiter("Alex", "Heidrun",
				PREF_W10 + "06064" + DOMAIN, "ee-ie4", "215"));
		mitarbeiterListe.add(new Mitarbeiter("Bee", "Guido",
				PREF_W10 + "06112" + DOMAIN, "ee-ie4", "216"));
		mitarbeiterListe.add(new Mitarbeiter("Gröschel", "Petra",
				PREF_W10 + "06369" + DOMAIN, "ee-ie4", "227"));
		mitarbeiterListe.add(new Mitarbeiter("Hofmann", "Sandra Viola",
				PREF_W10 + "06316" + DOMAIN, "ee-ie4", "228"));
		mitarbeiterListe.add(new Mitarbeiter("Kipple", "Ellen",
				PREF_W10 + "06122" + DOMAIN, "ee-ie4", "220"));
		mitarbeiterListe.add(new Mitarbeiter("Mengel", "Tina",
				PREF_W10 + "06215" + DOMAIN, "ee-ie4", "202"));
		mitarbeiterListe.add(new Mitarbeiter("Nadj-Guttandin", "Julijana",
				"F-NBW10-05735" + DOMAIN, "ee-ie4", "217"));
		mitarbeiterListe.add(new Mitarbeiter("Richter", "Monika",
				PREF_W10 + "06175" + DOMAIN, "ee-ie4", "229"));
		mitarbeiterListe.add(new Mitarbeiter("Fami Lehmann", "",
				"f-w7ws-05476" + DOMAIN, "ee-ie4", "221"));
		mitarbeiterListe.add(new Mitarbeiter("Fami Hill", "",
				"f-w7ws-05070" + DOMAIN, "ee-ie4", "225"));
		mitarbeiterListe.add(new Mitarbeiter("Lambert", "Marlene",
				PREF_W10 + "06067" + DOMAIN, "ee-ie4", "225a"));
		mitarbeiterListe.add(new Mitarbeiter("Fami Lambert", "",
				"f-w7ws-05077" + DOMAIN, "ee-ie4", "225a"));
		mitarbeiterListe.add(new Mitarbeiter("Newquist", "Susanne",
				PREF_W10 + "06083" + DOMAIN, "ee-ie4", "234"));

		mitarbeiterListe.add(new Mitarbeiter("Malter", "Patricia",
				PREF_W10 + "06061" + DOMAIN, "ee-aen", "204"));
		mitarbeiterListe.add(new Mitarbeiter("Büsken", "Andrea",
				PREF_W10 + "06084" + DOMAIN, "ee-ie5", "233"));
		mitarbeiterListe.add(new Mitarbeiter("Fenn", "Jürgen",
				PREF_W10 + "06125" + DOMAIN, "ee-ie5", "206"));
		mitarbeiterListe.add(new Mitarbeiter("Steinbach", "Hildegard",
				PREF_W10 + "06176" + DOMAIN, "ee-ie5", "234"));
		mitarbeiterListe.add(new Mitarbeiter("Dechent", "Stefanie",
				PREF_W10 + "06330" + DOMAIN, "ee-ie5", "220"));
		mitarbeiterListe.add(new Mitarbeiter("Hill", "Brigitte",
				PREF_W10 + "06211" + DOMAIN, "ee-ie5", "225"));
		mitarbeiterListe.add(new Mitarbeiter("Heuer", "Katrin",
				PREF_W10 + "06194" + DOMAIN, "ee-ie5", "229"));
		mitarbeiterListe.add(new Mitarbeiter("Karg", "Helga",
				PREF_W10 + "06164" + DOMAIN, "ee-ie5", "232"));
		mitarbeiterListe.add(new Mitarbeiter("Lehmann", "Lutz",
				PREF_W10 + "06082" + DOMAIN, "ee-ie5", "221"));

		mitarbeiterListe.add(new Mitarbeiter("Scheven", "Esther",
				PREF_W10 + "06192" + DOMAIN, "ee-ie6", "226"));
		mitarbeiterListe.add(new Mitarbeiter("Baier", "Wolfgang",
				PREF_W10 + "06313" + DOMAIN, "ee-ie6", "205"));
		mitarbeiterListe.add(new Mitarbeiter("Baumann", "Christian",
				PREF_W10 + "06353" + DOMAIN, "ee-ie6", "224"));
		mitarbeiterListe.add(new Mitarbeiter("Böth", "Nelli",
				PREF_W10 + "06223" + DOMAIN, "ee-aen", "218"));
		mitarbeiterListe.add(new Mitarbeiter("Jost-Zell", "Elke",
				"f-w7ws-05194" + DOMAIN, "ee-ie6", "219"));
		mitarbeiterListe.add(new Mitarbeiter("Köhn", "Karen",
				PREF_W10 + "06117" + DOMAIN, "ee-ie6", "231"));
		mitarbeiterListe.add(new Mitarbeiter("Schmidt", "Ricarda",
				PREF_W10 + "06073" + DOMAIN, "ee-ie6", "219"));
		mitarbeiterListe.add(new Mitarbeiter("Lösse", "Monika",
				PREF_W10 + "06032" + DOMAIN, "ee-ie6", "207"));

		mitarbeiterListe.add(new Mitarbeiter("Fami 1", "",
				PREF_W10 + "06249" + DOMAIN, "ie-Aushilfe", "223"));
		mitarbeiterListe.add(new Mitarbeiter("Fami 2", "",
				PREF_W10 + "06089" + DOMAIN, "ie-Aushilfe", "222"));

		mitarbeiterListe.add(new Mitarbeiter("Magaziner 1", "",
				PREF_W10 + "06337" + DOMAIN, "BB_2B.4.3", "238"));
		mitarbeiterListe.add(new Mitarbeiter("Magaziner 2", "",
				PREF_W10 + "06356" + DOMAIN, "BB_2B.4.3", "237"));
		mitarbeiterListe.add(new Mitarbeiter("Kopmann", "Maike1",
				PREF_W10 + "06255" + DOMAIN, "BB_2B.4.3", "236"));
		mitarbeiterListe.add(new Mitarbeiter("Kopmann", "Maike2",
				PREF_W10 + "06177" + DOMAIN, "BB_2B.4.3", "236"));
		mitarbeiterListe.add(new Mitarbeiter("Ziegler", "Paul-Konrad",
				PREF_W10 + "06108" + DOMAIN, "BB_2B.4.3", "236"));

		mitarbeiterListe.add(new Mitarbeiter("Schöbel", "Christof",
				"10.10.14.87" + DOMAIN, "DIF", "209"));
		mitarbeiterListe.add(new Mitarbeiter("Schöbel", "Christof 2",
				"10.10.14.88" + DOMAIN, "DIF", "209"));
		mitarbeiterListe.add(new Mitarbeiter("Schöbel", "Christof 3",
				"10.10.14.85" + DOMAIN, "DIF", "209"));

		Collections.sort(mitarbeiterListe);

	}

	@Override
	public int compareTo(final Mitarbeiter o) {
		final int comp = unit.compareToIgnoreCase(o.unit);
		if (comp != 0)
			return comp;
		return nameCompare(o);
	}

	public int nameCompare(final Mitarbeiter o) {
		final int comp = lastName.compareToIgnoreCase(o.lastName);
		if (comp != 0)
			return comp;
		return firstName.compareToIgnoreCase(o.firstName);
	}

	public static void main(final String[] args) {
		final ArrayList<Mitarbeiter> list = getMitarbeiterList();
		System.out.println(list);
		Collections.sort(list);
		System.out.println(list);
	}

}

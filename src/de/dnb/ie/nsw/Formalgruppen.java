package de.dnb.ie.nsw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import de.dnb.basics.Misc;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.utils.BibRecUtils;
import de.dnb.gnd.utils.DownloadWorker;
import de.dnb.gnd.utils.RecordUtils;

public class Formalgruppen extends DownloadWorker {

	private String verlangte;

	@Override
	protected void processRecord(Record record) {
		List<String> formalgruppen = RecordUtils.getContentsOfSubfields(record,
				"0604", 'e');
		if (!formalgruppen.contains(verlangte))
			return;

		String titel = BibRecUtils.createShortTitle(record);
		String abkuerzung = RecordUtils.getContentOfSubfield(record, "0604",
				'b');
		String idn = record.getId();
		String uri = Misc.createURI(idn);
		String link = Misc.createExcelHyperlink(uri);
		// System.out.println(Misc.createExcelLine(titel, abkuerzung));

		// zum Debuggen auskommentieren, da zu lange:
		System.out.println(Misc.createExcelLine(titel, abkuerzung, link));
	}

	public static void main(String[] args) throws IOException {
		Formalgruppen fg = new Formalgruppen();
		System.out
				.println("Bitte gewünschte Formalgruppe eingeben [g,k,p,t]:\n");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));

		try {
			fg.verlangte = reader.readLine();
		} catch (IOException e) {
			// nix
		}
		fg.processFile("D:/Analysen/karg/NSW.dwl");
	}

}

package de.dnb.ie.nsw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import de.dnb.basics.Misc;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.utils.BibRecUtils;
import de.dnb.gnd.utils.DownloadWorker;
import de.dnb.gnd.utils.RecordUtils;

public class AenderungsInfos extends DownloadWorker {

	private String verlangte;

	@Override
	protected void processRecord(final Record record) {
		final List<String> aender = RecordUtils
				.getContentsOfFirstSubfields(record, "0595", 'b');
		if (!aender.contains(verlangte))
			return;

		final String titel = BibRecUtils.createShortTitle(record);
		String abkuerzung;
		abkuerzung = BibRecUtils.getAbkuerzungNSW(record);
		final String idn = record.getId();
		final String uri = Misc.createURI(idn);
		final String link = Misc.createExcelHyperlink(uri);
		// System.out.println(Misc.createExcelLine(titel, abkuerzung));

		// zum Debuggen auskommentieren, da zu lange:
		System.out.println(Misc.createExcelLine(titel, abkuerzung, link));
	}

	public static void main(final String[] args) throws IOException {
		final AenderungsInfos fg = new AenderungsInfos();
		System.out.println("Bitte gewünschte Änderungs-Information eingeben "
				+ "(neu, aufl, aend):\n");
		final BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		try {
			fg.verlangte = reader.readLine();
		} catch (final IOException e) {
			// nix
		}
		fg.processFile("D:/Analysen/karg/NSW.dwl");
	}

}

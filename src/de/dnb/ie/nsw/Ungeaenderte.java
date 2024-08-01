package de.dnb.ie.nsw;

import java.io.IOException;

import de.dnb.basics.Misc;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.utils.BibRecUtils;
import de.dnb.gnd.utils.DownloadWorker;
import de.dnb.gnd.utils.RecordUtils;

public class Ungeaenderte extends DownloadWorker {

	@Override
	protected void processRecord(Record record) {
		if (RecordUtils.containsField(record, "0595"))
			return;
		String titel = BibRecUtils.createShortTitle(record);
		String abkuerzung = RecordUtils.getContentOfSubfield(record, "0604",
				'b');
		// zum Debuggen auskommentieren, da zu lange:
		System.out.println(Misc.createExcelLine(titel, abkuerzung));
	}

	public static void main(String[] args) throws IOException {
		Ungeaenderte fg = new Ungeaenderte();

		fg.processFile("D:/Analysen/karg/NSW.dwl");
	}

}

package de.dnb.ie.nsw;

import java.io.IOException;

import de.dnb.basics.Misc;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.utils.BibRecUtils;
import de.dnb.gnd.utils.DownloadWorker;
import de.dnb.gnd.utils.RecordUtils;

public class Titel_Abku_IDN extends DownloadWorker {

	@Override
	protected void processRecord(Record record) {
		String titel = BibRecUtils.createShortTitle(record);
		String abkuerzung = RecordUtils.getContentOfSubfield(record, "0604",
				'b');
		String idn = record.getId();
		String uri = Misc.createURI(idn);
		String link = Misc.createExcelHyperlink(uri);
//		System.out.println(Misc.createExcelLine(titel, abkuerzung));
		
		// zum Debuggen auskommentieren, da zu lange:
		System.out.println(Misc.createExcelLine(titel, abkuerzung, link));
	}

	public static void main(String[] args) throws IOException {
		Titel_Abku_IDN tai = new Titel_Abku_IDN();
		tai.processFile("D:/Analysen/karg/NSW.dwl");
	}

}

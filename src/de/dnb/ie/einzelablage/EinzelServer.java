package de.dnb.ie.einzelablage;

import org.marc4j.marc.Record;

import de.dnb.basics.clientServer.Server;
import de.dnb.basics.marc.DDCMarcUtils;
import de.dnb.basics.marc.MarcUtils;

public class EinzelServer extends Server {

	public EinzelServer(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String transform(String s) {
		Record record = MarcUtils.xml2Record(s);
		addInfo("trans");
		addInfo(record.toString());
		return DDCMarcUtils.getPicaFields(record, 0);
	}

	public static void main(String[] args) {
		new EinzelServer(4711);

	}

}

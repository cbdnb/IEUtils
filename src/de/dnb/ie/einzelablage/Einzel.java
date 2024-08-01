package de.dnb.ie.einzelablage;

import org.marc4j.marc.Record;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.marc.DDCMarcUtils;
import de.dnb.basics.marc.MarcUtils;

public class Einzel {

	public static void main(String[] args) {
		Record record = MarcUtils.readXMLfromClip();
		StringUtils.writeToClipboard(DDCMarcUtils.getPicaFields(record, 0));
	}

}

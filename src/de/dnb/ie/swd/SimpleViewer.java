/**
 * 
 */
package de.dnb.ie.swd;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.utils.HTMLUtils;
import de.dnb.basics.utils.OutputUtils;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.parser.RecordParser;
import de.dnb.gnd.parser.tag.SWDTagDB;
import de.dnb.gnd.utils.RecordUtils;
import de.dnb.gnd.utils.formatter.HTMLFormatter;
import de.dnb.gnd.utils.formatter.HTMLHeadingBuilder;

/**
 * @author baumann
 *
 */
public class SimpleViewer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final String string = StringUtils.readClipboard();

		final RecordParser parser = new RecordParser();
		SWDTagDB db = SWDTagDB.getDB();
		parser.setDefaultTagDB(db);
		parser.useDefaultDB(true);
		final Record record = parser.parse(string);

		HTMLFormatter formatter = new HTMLFormatter();
		formatter.setFontsize(17);
		HTMLHeadingBuilder htmlTitleBuilder = new HTMLHeadingBuilder() {

			@Override
			public String getHeading(Record record) {
				return "Alter Datensatz";
			}
		};

		formatter.setHtmlTitleBuilder(htmlTitleBuilder);
		String s = formatter.format(record);
		OutputUtils.show(s);

	}

}

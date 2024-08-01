/**
 * 
 */
package de.dnb.ie.swd;

import de.dnb.basics.utils.HTMLUtils;
import de.dnb.basics.utils.OutputUtils;
import de.dnb.gnd.parser.Record;
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
		Record record = RecordUtils.readFromClip(SWDTagDB.getDB());

		HTMLFormatter formatter = new HTMLFormatter();
		formatter.setFontsize(15);
		HTMLHeadingBuilder htmlTitleBuilder = new HTMLHeadingBuilder() {

			@Override
			public String getHeading(Record record) {
				return "SWD-Datensatz";
			}
		};

		formatter.setHtmlTitleBuilder(htmlTitleBuilder);
		String s = formatter.format(record);
		OutputUtils.show(s);

	}

}

/**
 * 
 */
package de.dnb.ie.gattungen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.gnd.parser.RecordReader;
import de.dnb.gnd.parser.line.Line;
import de.dnb.gnd.utils.RecordUtils;
import de.dnb.gnd.utils.SubfieldUtils;
import de.dnb.gnd.utils.WorkUtils;

/**
 * @author baumann
 *
 */
public class Suche {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Sucht nach allen Titelsätzen, die eine"
				+ "bestimmte Gattung oder Instrument verlinkt haben");

		System.out.println("gesuchte idn eingeben");
		StringUtils.readConsole();
		String gesucht = StringUtils.readClipboard();
		RecordReader reader = RecordReader
				.getMatchingReader("D:/Normdaten/DNBGND_u.dat.gz");
		reader.stream().forEach(record ->
		{

			ArrayList<Line> lines = RecordUtils.getLines(record, "380", "382");
			List<String> idns = SubfieldUtils.getContentsOfFirstSubfields(lines,
					'9');
			if (idns.contains(gesucht)) {
				String idn = record.getId();
				String name = WorkUtils.getExpansionTitle(record);
				System.out.println(idn + "\t" + name);
			}
		});

	}

}

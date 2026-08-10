package de.dnb.ie.utils;

import de.dnb.gnd.parser.tag.GNDTag;
import de.dnb.gnd.parser.tag.GNDTagDB;

public class GND2YAML {

	public static void main(final String[] args) {
		final GNDTagDB db = GNDTagDB.getDB();
		db.getAllTags().forEach(tag -> {
			final GNDTag gndTag = (GNDTag) tag;
			System.out.printf("\"%s\":\n", gndTag.pica3);
			tag.getAllIndicators().forEach(ind -> {
				String s = "$" + ind.indicatorChar;
				if (gndTag.getDefaultFirst() == ind) {
					s = "-ohne-";
				}
				final String p = gndTag.picaPlus + "." + ind.indicatorChar;
//				System.out.printf("- \"%s\"#%s\n", s, ind.descGerman);
				System.out.printf("  \"%s\": \"%s\"\n", s, p);
			});
		});

	}
}

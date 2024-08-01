package de.dnb.ee.pretty;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.utils.HTMLEntities;
import de.dnb.basics.utils.HTMLUtils;
import de.dnb.basics.utils.OutputUtils;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.utils.BibRecUtils;
import de.dnb.gnd.utils.RecordUtils;
import de.dnb.gnd.utils.SGUtils;
import de.dnb.gnd.utils.formatter.HTMLFormatter;
import de.dnb.gnd.utils.formatter.HTMLHeadingBuilder;

public class MyHeadingBuilder implements HTMLHeadingBuilder {

	private static final String BR = "<br>";
	private int fontsize = 13;

	@Override
	public String getHeading(final Record record) {
		String s = "";
		final String sgg = makeSGG(record);
		if (!sgg.isEmpty()) {
			s += BR + sgg;
		}
		s += makeTitle(record);
		s += BR + HTMLUtils.bold("idn: " + record.getId());
		s += BR + makeSignature(record);
		final String reiheB = makeReiheB(record);
		return reiheB + s;
	}

	public static void main(final String[] args) {
		final Record record = RecordUtils.readFromClip();
		final MyHeadingBuilder builder = new MyHeadingBuilder();
		final String txt = builder.getHeading(record);
		OutputUtils.show(txt);

	}

	private String makeReiheB(final Record record) {
		if (BibRecUtils.isRB(record)) {
			return HTMLUtils.rightHeading("Reihe B", 2) + BR;
		} else {
			return "";
		}
	}

	private String makeSGG(final Record record) {
		if (RecordUtils.isBibliographic(record)) {
			final String sgg = SGUtils.getSGGSemicola(record);
			if (sgg != null) {
				return HTMLUtils.bold("Sg: " + sgg);
			}
		}
		return "";
	}

	private String makeTitle(final Record record) {
		String title = RecordUtils.getTitle(record);
		title = HTMLEntities.allCharacters(title);
		title = HTMLUtils.bold(title);

		final String cell2 = HTMLUtils.tableCell(
				StringUtils.repeat(80, HTMLFormatter.NBSP), this.fontsize);
		final String firstline = cell2;
		final int border = 0;
		final int spacing = 0;
		final int padding = 2;

		String s = firstline
				+ HTMLUtils
						.tableLine(HTMLUtils.tableCell(title, this.fontsize));
		s = HTMLUtils.table(HTMLUtils.tableLine(s), border, spacing, padding);

		return s;
	}

	private String makeSignature(final Record record) {
		String signature = null;
		signature = BibRecUtils.getDNBShelfMark(record);
		if (signature != null) {
			signature = HTMLUtils.bold("Signatur: " + signature);
		} else {
			signature = "";
		}
		return signature;
	}

}

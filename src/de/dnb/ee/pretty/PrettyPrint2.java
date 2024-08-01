package de.dnb.ee.pretty;

import java.awt.print.PrinterException;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

import javax.naming.OperationNotSupportedException;

import de.dnb.basics.utils.OutputUtils;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.parser.line.Line;
import de.dnb.gnd.utils.BibRecUtils;
import de.dnb.gnd.utils.RecordUtils;
import de.dnb.gnd.utils.SubjectUtils;
import de.dnb.gnd.utils.formatter.HTMLFormatter;

public class PrettyPrint2 {

	private static final boolean DEBUG = false;
	private static final Collection<String> tags = new LinkedList<String>(
			Arrays.asList("001A", "001B", "001D", "003@", "0500", "0599",
					"0600", "1100", "1130", "1131", "1132", "1133", "2105",
					"3000", "3210", "4025", "4000", "4004", "4020", "4030",
					"4060", "4160", "4180", "4190", "4204", "4700", "5585",
					"5050", "7100"));

	public static void main(final String[] args) throws FileNotFoundException {

		while (true) {
			final Scanner scan = new Scanner(System.in);
			scan.next();
			final Record record = BibRecUtils.readFromClipIgnoring();
			if (RecordUtils.isBibliographic(record)) {
				final Collection<Line> rswk = SubjectUtils.getAllRSWKLines(record);
				final Collection<Line> ddc = SubjectUtils.getDDCSegment(record);
				RecordUtils.retainTagstrings(record, tags);
				try {
					RecordUtils.addLines(record, ddc);
					RecordUtils.addLines(record, rswk);
				} catch (final OperationNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (RecordUtils.isAuthority(record)) {

			}
			final HTMLFormatter formatter = new HTMLFormatter();
			if (DEBUG)
				formatter.setFontsize(16);
			else
				formatter.setFontsize(10);
			formatter.setBorder(0);
			formatter.setSpacing(-3);
			formatter.setWidth(100);
			formatter.setHtmlTitleBuilder(new MyHeadingBuilder());
			String txt = formatter.format(record);
			txt += "<br>";
			if (DEBUG)
				OutputUtils.show(txt);
			else
				try {
					OutputUtils.printWithoutDialog(txt);
				} catch (final PrinterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

}

package de.dnb.ie.tranforms;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import de.dnb.gnd.exceptions.IllFormattedLineException;
import de.dnb.gnd.parser.Indicator;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.parser.Subfield;
import de.dnb.gnd.parser.line.Line;
import de.dnb.gnd.parser.line.LineFactory;
import de.dnb.gnd.parser.line.LineParser;
import de.dnb.gnd.parser.tag.GNDTagDB;
import de.dnb.gnd.parser.tag.Tag;
import de.dnb.gnd.parser.tag.TagDB;
import de.dnb.gnd.utils.ClipTransformer;
import de.dnb.gnd.utils.SubfieldUtils;
import de.dnb.gnd.utils.WorkUtils;

public class ViolinkonzertTransformer extends ClipTransformer {

	private static Line line065;
	private static Line line008;

	static {
		try {
			line065 = LineParser.parseGND("065 14.4p");
			line008 = LineParser.parseGND("008 wim");
		} catch (IllFormattedLineException e) {
			// nix
		}
	}

	public ViolinkonzertTransformer() {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new ViolinkonzertTransformer().transform();

	}

	@Override
	protected void transform(Record record) throws Exception {
		// Suche nach Konzerte$mVl$mOrch:
		Line titleLine = WorkUtils.getTitleLine(record);
		Tag tag430 = record.tagDB.findTag("430");
		LineFactory factory = tag430.getLineFactory();
		factory.load("Konzerte$mVl$mOrch");
		Line compareLine = factory.createLine();
		if (!SubfieldUtils.equalsRetaining(titleLine, compareLine, 'a', 'm')) {
			throw new IllegalArgumentException();
		}

		// Aufbau:
		List<Subfield> restList = SubfieldUtils.removeSubfields(titleLine, 'a',
				'm', 'v');
		Indicator a = tag430.getIndicator('a');
		Subfield vlKonz = new Subfield(a, "Violinkonzert");
		// am Anfang einfügen:
		restList.add(0, vlKonz);
		factory.load(restList);
		Line line430 = factory.createLine();

		record.add(line430);
	}

	@Override
	protected void addCountryCode(Record recordNew) {
		// wenig sinnvoll

	}

	@Override
	protected void addGNDClassification(Record recordNew) {
		try {
			recordNew.add(line065);
		} catch (OperationNotSupportedException e) {
			// nix
		}

	}

	@Override
	protected void addEntityType(Record record) {
		try {
			record.add(line008);
		} catch (OperationNotSupportedException e) {
			// nix
		}

	}

}

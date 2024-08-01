package de.dnb.ie.scrap;

import java.io.IOException;

import de.dnb.gnd.parser.Record;
import de.dnb.gnd.parser.line.Line;
import de.dnb.gnd.utils.DownloadWorker;
import de.dnb.gnd.utils.WorkUtils;

public class Tag5XXExpansion extends DownloadWorker {

	@Override
	protected void processRecord(final Record record) {

		final Line line = WorkUtils.getAuthorLine(record);

		if (line == null)
			return;

		if (!line.getTag().pica3.equals("500"))
			return;

		if (!record.getRawData().contains("Kaiser"))
			return;

		System.out.println(record);
		System.out.println(record.getRawData());
		System.out.println();

	}

	public static void main(final String[] args) throws IOException {

		final Tag5XXExpansion t = new Tag5XXExpansion();

		try {
			t.processGZipFile("D:/Normdaten/DNBGND_u.dat.gz");
		} catch (final Exception e) {

		}

	}
}

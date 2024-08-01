package de.dnb.ie.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import de.dnb.basics.Constants;
import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.collections.BiMultimap;
import de.dnb.basics.collections.CollectionUtils;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.utils.DownloadWorker;
import de.dnb.gnd.utils.GNDUtils;
import de.dnb.gnd.utils.IDNUtils;

/**
 *
 *
 * @author baumann
 *
 */
@Deprecated
public class Make_GND_Databases extends DownloadWorker {

	public static final String FOLDER = "D:/Normdaten/";
	public static final String FILE_UB_OB = FOLDER + "OB_UB.out";
	private static final BiMultimap<Integer, Integer> ub_ob_map = BiMultimap
			.createListMap();

	public static final String FILE_NAMES = FOLDER + "GNDnames.out";
	private static HashMap<Integer, String> ppn2names = new HashMap<>();

	@Override
	protected void processRecord(final Record record) {
		final String idn = record.getId();
		int ppnI;
		if (IDNUtils.isKorrektePPN(idn)) {
			ppnI = IDNUtils.ppn2int(idn);
		} else
			return;

		try {
			final String name = GNDUtils.getNameOfRecord(record);
			ppn2names.put(ppnI, name);
			System.err.println(StringUtils.concatenateTab(idn, name));
		} catch (final IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final List<Integer> obb = GNDUtils.getOBBidns(record);
		if (!obb.isEmpty()) {
			ub_ob_map.addAll(ppnI, obb);
		}
	}

	public static void main(final String[] args) throws IOException {

		final Make_GND_Databases ob_ub_maker = new Make_GND_Databases();

		System.err.println("GND flöhen:");

		try {
			ob_ub_maker.processGZipFiles(Constants.Ts, Constants.Tu);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		CollectionUtils.save(ub_ob_map, FILE_UB_OB);
		CollectionUtils.save(ppn2names, FILE_NAMES);
	}

}

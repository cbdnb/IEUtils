/**
 *
 */
package de.dnb.marcViewer2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.marc4j.MarcReader;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.Record;

import de.dnb.basics.marc.MarcUtils;

/**
 * @author baumann
 *
 */
public class Model {

	List<Record> records = new ArrayList<>();

	/**
	 * @param datei
	 */
	public void loadFile(final String datei) {
		records.clear();
		InputStream input;
		try {
			input = new FileInputStream(datei);
			final MarcReader marcReader = new MarcXmlReader(input);
			while (marcReader.hasNext()) {
				final Record record = marcReader.next();
				// System.out.println(record);
				records.add(record);
			}
		} catch (final FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	int getSize() {
		return records.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public Record getElementAt(final int index) {
		if (index >= records.size() || index < 0)
			return null;
		return records.get(index);
	}

	/**
	 * @param index
	 * @return
	 */
	String getNameOf(final int index) {
		final Record record = getElementAt(index);
		if (record == null)
			return "null";
		final String name = MarcUtils.getPreferredName(record);
		return name == null ? "null" : name;
	}

}

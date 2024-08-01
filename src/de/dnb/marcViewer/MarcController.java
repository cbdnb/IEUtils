/**
 *
 */
package de.dnb.marcViewer;

import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import org.marc4j.marc.Record;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.utils.PortalUtils;
import de.dnb.gnd.utils.IDNUtils;

/**
 * @author baumann
 *
 */
public class MarcController {

	View view;

	ActionListener marcL = (a) ->
	{

		final String s = StringUtils.readClipboard();
		final String idn = IDNUtils.extractIDN(s);
		if (idn.isEmpty())
			return;
		// System.out.println(idn);
		final Record record = PortalUtils.getMarcRecord(idn);
		// System.err.println(record);
		if (record == null)
			return;
		SwingUtilities.invokeLater(() ->
		{
			view.setRecord(record);
			// System.err.println(record);
		});
	};

	/**
	 *
	 */
	public MarcController() {

		view = new View();
		view.addActionListener(marcL);
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new MarcController();

	}

}

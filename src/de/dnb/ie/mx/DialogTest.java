/**
 *
 */
package de.dnb.ie.mx;

import java.awt.Component;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.dnb.gnd.utils.mx.Library;
import de.dnb.gnd.utils.mx.LibraryDB;
import de.dnb.gnd.utils.mx.MXAddress;
import de.dnb.gnd.utils.mx.MXAddress.Adressierung;
import de.dnb.gnd.utils.mx.RedaktionsTyp;

/**
 * @author baumann
 *
 */
public class DialogTest {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		MXAddress mxAddress = MXAddress.parse("a-xDE-101-SE-F-ba");

		mxAddress = editMxAddress(mxAddress, null);
		if (mxAddress != null) {
			System.out.println(mxAddress.asMxString());
			mxAddress.setAdressierung(Adressierung.EMPFAENGER);
			System.out.println(mxAddress.asMxString());
			System.out.println(mxAddress);
		}

	}

	/**
	 * @param mxAddress
	 * @return
	 */
	public static MXAddress editMxAddress(MXAddress mxAddress,
			final Component parent) {

		// Erstellung Array vom Datentyp Object, Hinzufügen der Komponenten
		final JComboBox<Library> jboxBib = new JComboBox<>();
		final List<Library> libs = LibraryDB.getLibrariesSortName();
		libs.forEach(lib -> jboxBib.addItem(lib));

		if (mxAddress != null) {
			final Library originalLib = mxAddress.getLibrary();
			if (originalLib != null) {
				jboxBib.setSelectedItem(originalLib);
			}
		}

		final JComboBox<RedaktionsTyp> jboxRed = new JComboBox<>();
		final RedaktionsTyp[] typen = RedaktionsTyp.values();
		for (final RedaktionsTyp redaktionsTyp : typen) {
			jboxRed.addItem(redaktionsTyp);
		}

		final JTextField jtFuntergliederung = new JTextField();

		final Object[] message = { "Wähle Bibliothek", jboxBib, "   ",
				"Wähle Redaktion", jboxRed, "   ",
				"Untergliederung eingeben (z.B. F-sv)", jtFuntergliederung };

		final JOptionPane pane = new JOptionPane(message,
				JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		final JDialog dialog = pane.createDialog(parent,
				"Mailbox-Adresse wählen");
		dialog.setVisible(true);
		Object value = pane.getValue();
		if (value == null)
			value = JOptionPane.CLOSED_OPTION;
		System.out.println(value);

		if (value.equals(JOptionPane.OK_OPTION)) {
			final Library bib = (Library) jboxBib.getSelectedItem();
			final RedaktionsTyp redaktion = (RedaktionsTyp) jboxRed
					.getSelectedItem();
			final String untergliederung = jtFuntergliederung.getText();
			mxAddress = new MXAddress(Adressierung.UNBESTIMMT, false, bib,
					redaktion, untergliederung, "");

		}
		dialog.dispose();
		return mxAddress;
	}

}

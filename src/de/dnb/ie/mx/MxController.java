/**
 *
 */
package de.dnb.ie.mx;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.gnd.utils.mx.Library;
import de.dnb.gnd.utils.mx.LibraryDB;
import de.dnb.gnd.utils.mx.MXAddress;
import de.dnb.gnd.utils.mx.Mailbox;
import de.dnb.gnd.utils.mx.RedaktionsTyp;
import de.dnb.gnd.utils.mx.MXAddress.Adressierung;

/**
 * @author baumann
 *
 */
public class MxController {

	/**
	 *
	 */
	private static final String MY_ADDR = "myAdd";

	Model model;

	View view;

	private List<String> dates;

	private File saveFile;

	Properties props = new Properties();

	ListSelectionListener dateListener = new ListSelectionListener() {

		@Override
		public void valueChanged(final ListSelectionEvent e) {
			final int i = view.getSelectedIndex();
			model.setMx(i);
			final MXAddress abs = model.getAbsender();
			final String text = model.getText();
			final List<MXAddress> beteiligte = model.getBeteiligte();
			view.setAbsender(abs);
			view.setText(text);
			view.setBeteiligte(beteiligte);
		}
	};

	DocumentListener textL = new DocumentListener() {

		@Override
		public void removeUpdate(final DocumentEvent e) {
			updateText();
		}

		@Override
		public void insertUpdate(final DocumentEvent e) {
			updateText();

		}

		@Override
		public void changedUpdate(final DocumentEvent e) {
			updateText();
		}

		private void updateText() {
			if (model.antwortWirdAngezeigt()) {
				model.setAnwortText(view.getText());
			}
		}
	};

	ActionListener antwAlleL = (e) ->
	{
		model.antwortAnAlle();
		datenAktualisieren();
	};

	ActionListener antwAbs = (e) ->
	{
		model.antwortAnAbsender();
		datenAktualisieren();
	};

	ActionListener mxUrh = (e) ->
	{
		model.mxUrheber();
		datenAktualisieren();
	};

	ActionListener mxRed = (e) ->
	{
		model.mxRed();
		datenAktualisieren();
	};

	ActionListener antwClip = (e) ->
	{
		if (!model.antwortErzeugt())
			return;
		final Mailbox antw = model.getAntwort();
		final String antwStr = antw.asMxString();
		StringUtils.writeToClipboard(antwStr);
	};

	ActionListener addEmpf = (e) ->
	{
		if (!model.antwortErzeugt())
			return;
		final MXAddress mxa = MXAddress.getNullAddress();
		editMxAddress(mxa, null);
		// keine Änderung?
		if (mxa.equals(MXAddress.getNullAddress()))
			return;
		mxa.setAdressierung(Adressierung.EMPFAENGER);
		model.addEmpf2Antwort(mxa);
		datenAktualisieren();
	};

	ActionListener delEmpf = (e) ->
	{
		if (!model.antwortErzeugt())
			return;
		final int nr = view.getNrdesEmpfaengers();
		model.removeEmpfVonAntw(nr);
		datenAktualisieren();
	};

	ActionListener editEmpf = (e) ->
	{
		if (!model.antwortErzeugt())
			return;
		final int nr = view.getNrdesEmpfaengers();
		final MXAddress empf = model.getAnwortEmpf(nr);
		if (empf == null)
			return;
		editMxAddress(empf, null);
		datenAktualisieren();
	};

	ActionListener standardEmpf = (e) ->
	{
		if (!model.antwortErzeugt())
			return;
		model.standardEmpfaenger();
		datenAktualisieren();
	};

	/**
	 *
	 */
	private void datenAktualisieren() {
		dates = model.getDates();
		view.setDates(dates, dates.size() - 1, true);
	}

	ActionListener editAbs = (e) ->
	{
		if (!model.antwortErzeugt())
			return;
		final MXAddress abs = model.getAbsender();
		if (abs == null)
			return;
		editMxAddress(abs, null);
		props.setProperty(MY_ADDR, model.meineAdresseStr());
		try {
			storeProperties();
		} catch (final IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	};

	/**
	 *
	 */
	MxController() {

		SwingUtilities.invokeLater(() ->
		{
			model = new Model();
		});
		SwingUtilities.invokeLater(() ->
		{
			view = new View();
		});
		SwingUtilities.invokeLater(() ->
		{
			dates = model.getDates();
			view.setDates(dates, 0, false);
		});
		SwingUtilities.invokeLater(() ->
		{
			view.setDateListener(dateListener);
		});
		SwingUtilities.invokeLater(() ->
		{
			final String title = "IDN " + model.idn + ": "
					+ model.vorzugsbenennung;
			view.setTitle(title);
		});
		SwingUtilities.invokeLater(() ->
		{
			view.setAntwortAlle(antwAlleL);
			view.setAntwortAbs(antwAbs);
			view.setAntwClip(antwClip);
			view.setAddEmpf(addEmpf);
			view.setDelEmpf(delEmpf);
			view.setEditEmpf(editEmpf);
			view.setEditAbs(editAbs);
			view.setstandardEmpf(standardEmpf);
			view.setTextListener(textL);
			view.setMxRed(mxRed);
			view.setMxUrheber(mxUrh);
			try {
				loadProperties();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			final String meineSTR = props.getProperty(MY_ADDR, "a-DE-101");
			model.setMeineAdresse(meineSTR);
		});

	}

	/**
	 * @param mxAddress
	 * @return
	 */
	private void editMxAddress(final MXAddress mxAddress,
			final Component parent) {

		// Erstellung Array vom Datentyp Object, Hinzufügen der Komponenten
		final JComboBox<Library> jboxBib = new JComboBox<>();
		final List<Library> libs = LibraryDB.getLibrariesSortName();
		libs.forEach(lib -> jboxBib.addItem(lib));
		final JComboBox<RedaktionsTyp> jboxRed = new JComboBox<>();
		final RedaktionsTyp[] typen = RedaktionsTyp.values();
		for (final RedaktionsTyp redaktionsTyp : typen) {
			jboxRed.addItem(redaktionsTyp);
		}
		final JTextField jtFuntergliederung = new JTextField();

		if (!mxAddress.equals(MXAddress.getNullAddress())) {
			final Library originalLib = mxAddress.getLibrary();
			if (originalLib != null) {
				jboxBib.setSelectedItem(originalLib);
			}
			final RedaktionsTyp originalRed = mxAddress.getRedaktionsTyp();
			if (originalRed != null) {
				jboxRed.setSelectedItem(originalRed);
			}
			final String origUnt = mxAddress.getSubadress();
			if (origUnt != null)
				jtFuntergliederung.setText(origUnt);
		}

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

		if (value.equals(JOptionPane.OK_OPTION)) {
			final Library bib = (Library) jboxBib.getSelectedItem();
			final RedaktionsTyp redaktion = (RedaktionsTyp) jboxRed
					.getSelectedItem();
			final String untergliederung = jtFuntergliederung.getText();

			mxAddress.setLibrary(bib);
			mxAddress.setRedaktion(redaktion);
			mxAddress.setSubadress(untergliederung);

		}
		dialog.dispose();
		return;
	}

	private void loadProperties() throws IOException {
		final String userHome = System.getProperty("user.home");
		saveFile = new File(userHome + "/mx.properties");
		if (saveFile.exists()) {
			final FileInputStream fis = new FileInputStream(saveFile);
			props.load(fis);
		}
	}

	private void storeProperties() throws IOException {
		saveFile.createNewFile();
		final FileOutputStream fos = new FileOutputStream(saveFile);
		props.store(fos, null);
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new MxController();

	}

}

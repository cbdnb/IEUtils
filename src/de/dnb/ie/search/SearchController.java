package de.dnb.ie.search;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SearchController {

	View view;

	SearchModel model;

	Properties values = new Properties();

	File saveFile;

	private Date creationDate;

	CombinedPanel combinedPanel;

	TextfieldPanel outputPanel;

	TextfieldPanel panelRelated;

	TextfieldPanel panelPreferred;

	TextfieldPanel panelCode;

	CommandPanel panelCommand;

	ComboPanel panelType;

	ComboPanel panelEntity;

	String[] entStrings = { "gib", "gif", "gik", "gil", "giv", "gin", "gio",
			"gir", "giw", "gix", "giz", "gxz", "kif", "kim", "kio", "kip",
			"kir", "kiv", "kiz", "kxz", "pif", "pik", "pip", "pis", "piz",
			"pxg", "pxl", "pxs", "sab", "sad", "saf", "sag", "sam", "saz",
			"sie", "sif", "sih", "sip", "sis", "siu", "siw", "siz", "slz",
			"snz", "sxz", "szz", "uiz", "vie", "vif", "wid", "wie", "wif",
			"wil", "wim", "win", "wip", "wis", "wit", };

	class ExitListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			System.exit(0);
		}
	}

	class ClearListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			model.clear();
		}

	}

	/*
	 * KeyListener für Textfelder ActionListener für Combobox
	 */
	class MyChangeListener implements KeyListener, ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			model.refresh();
		}

		@Override
		public void keyPressed(final KeyEvent e) {
			// nix
		}

		@SuppressWarnings("synthetic-access")
		@Override
		public void keyReleased(final KeyEvent e) {
			final Component source = e.getComponent().getParent();
			final int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_ESCAPE)
				System.exit(0);
			else if (keyCode == KeyEvent.VK_ENTER)
				performSearch();
			// STRG-L: alles löschen
			else if (e.isControlDown() && keyCode == KeyEvent.VK_L) {
				model.clear();
			} else if (e.isControlDown() && keyCode == KeyEvent.VK_H)
				showInfo();
			else if (e.isControlDown()
					&& (keyCode == KeyEvent.VK_5 || keyCode == KeyEvent.VK_R)) {
				model.clear();
				panelRelated.requestFocusInWindow();
			} else if (e.isControlDown()
					&& (keyCode == KeyEvent.VK_4 || keyCode == KeyEvent.VK_C)) {
				model.clear();
				panelCode.requestFocusInWindow();
			} else if (e.isControlDown()
					&& (keyCode == KeyEvent.VK_1 || keyCode == KeyEvent.VK_A)) {
				model.clear();
				panelPreferred.requestFocusInWindow();
			} else if (e.isControlDown()
					&& (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_O)) {
				model.clear();
				combinedPanel.requestFocusInWindow();
			} else if (source != outputPanel)
				model.refresh();

		}

		@Override
		public void keyTyped(final KeyEvent e) {
			// nix
		}

	}

	class SearchListener implements ActionListener {

		@SuppressWarnings("synthetic-access")
		@Override
		public void actionPerformed(final ActionEvent e) {
			performSearch();
		}
	}

	class InfoListener implements ActionListener {

		@SuppressWarnings("synthetic-access")
		@Override
		public void actionPerformed(final ActionEvent e) {
			showInfo();
		}
	}

	/**
	 * @return the creationDate
	 */
	public final Date getCreationDate() {
		if (creationDate == null) {

			/*
			 * Die Manifest-Datei der eigenen jar-Datei ist immer aktuell. Daher
			 * Zugriff auf deren URL:
			 */
			final URL url = this.getClass()
					.getResource("/META-INF/MANIFEST.MF");
			String fileStr = url.getFile();
			/*
			 * Die URL ist etwas komplizierter aufgebaut. Sie hat - ein Präfix
			 * "file:/" - ein Postfix, das mit "!" beginnt, welches die Dateien
			 * in der .jar kennzeichnet.
			 */
			final int pos1 = "file:/".length();
			final int pos2 = fileStr.indexOf("!");
			fileStr = fileStr.substring(pos1, pos2);
			/*
			 * fileStr enthält nun nur noch den Pfad der eigenen jar-Datei
			 */

			try {
				final JarFile jarFile = new JarFile(fileStr);
				final ZipEntry zEnt = jarFile.getEntry("META-INF/MANIFEST.MF");
				creationDate = new Date(zEnt.getTime());
			} catch (final Exception e) {
				e.printStackTrace();
			}

		}
		return creationDate;
	}

	private void addInfoListener() {
		view.addInfoListener(new InfoListener());
	}

	private void createPanels() {
		final MyChangeListener myChangeListener = new MyChangeListener();
		final SearchListener searchListener = new SearchListener();
		final ClearListener clearListener = new ClearListener();
		final ExitListener exitListener = new ExitListener();

		panelPreferred = new TextfieldPanel("1XX / 4XX: ", "sw", "searchTerm");
		connectInputPanel(panelPreferred);
		panelPreferred.addKeyListener(myChangeListener);

		panelType = new ComboPanel("Satzart: ", "bbg", "T", "*", "type");
		connectInputPanel(panelType);
		panelType.addActionListener(myChangeListener);
		panelType.addText("");
		panelType.addText("s");
		panelType.addText("g");
		panelType.addText("u");
		panelType.addText("p");
		panelType.addText("b");
		panelType.addText("n");
		panelType.addText("f");
		panelType.addText("c");

		final String[] combined = { "sw", "rl" };
		combinedPanel = new CombinedPanel("1XX / 5XX", combined, "combined");
		connectInputPanel(combinedPanel);
		combinedPanel.addKeyListener(myChangeListener);

		panelRelated = new TextfieldPanel("nur 5XX: ", "rl", "relatedTerm");
		connectInputPanel(panelRelated);
		panelRelated.addKeyListener(myChangeListener);

		panelCode = new TextfieldPanel("$4: ", "rcc", "relatorCode");
		connectInputPanel(panelCode);
		panelCode.addKeyListener(myChangeListener);

		panelEntity = new ComboPanel("Entitätentyp: ", "ent", "", "", "entity");
		connectInputPanel(panelEntity);
		panelEntity.addActionListener(myChangeListener);
		panelEntity.addText("");
		for (final String entity : entStrings) {
			panelEntity.addText(entity);
		}

		outputPanel = new TextfieldPanel("Suchfrage: ", "", "searchStringFull");
		view.add(outputPanel);
		model.setOutputModel(outputPanel);
		outputPanel.addKeyListener(myChangeListener);

		panelCommand = new CommandPanel("Suche", "Alles Löschen", "Abbruch");
		view.add(panelCommand);
		panelCommand.addBtn1Listener(searchListener);
		panelCommand.addBtn2Listener(clearListener);
		panelCommand.addBtn3Listener(exitListener);
	}

	/**
	 * Verdrahtet das View mit dem Model.
	 *
	 * @param iPan
	 *            Ein Panel, das {@link IPanelModel} implementiert.
	 */
	private void connectInputPanel(final JPanel iPan) {
		view.add(iPan);
		model.addInputModel((IPanelModel) iPan);
	}

	/**
	 *
	 */
	private void showInfo() {
		final SimpleDateFormat formatter = new SimpleDateFormat(
				"d. M. yyyy 'um' H:mm 'Uhr'");
		final Date date = getCreationDate();
		final String dateStr = formatter.format(date);
		String info = "Version 1.00" + "\nErstellt für DNB, Abt 2"
				+ "\nAutor: Christian Baumann\n" + "Erstellungsdatum: "
				+ dateStr;
		info += "\n\nKurzanleitung:\n"
				+ " - Anzeigen dieser Hilfe mit STRG-H oder Menüpunkt \"? -> Info\"\n"
				+ " - Abbrechen mit ESC, \"Abbruch\" oder Schliessfeld "
				+ "rechts oben\n"
				+ " - Suche starten mit ENTER oder \"Suche\"\n"
				+ " - Alle Felder löschen mit STRG-L oder \"Alles löschen\"\n"
				+ " - Alle Felder löschen und den Cursor ins 1XX-Feld mit "
				+ "STRG-1 oder STRG-A\n"
				+ " - Alle Felder löschen und den Cursor ins 5XX-Feld mit "
				+ "STRG-5 oder STRG-R\n"
				+ " - Alle Felder löschen und den Cursor ins $4-Feld mit "
				+ "STRG-4 oder STRG-C\n"
				+ " - Alle Felder löschen und den Cursor ins 1XX oder 5XX-Feld mit "
				+ "STRG-S oder STRG-O\n"
				+ " \nDie Suchfrage kann vor dem Abschicken noch editiert "
				+ "werden. Wird eine neue Suchanfrage gestartet, so werden alle "
				+ "früher eingegebenen Felder erneut angezeigt. Das ist dann "
				+ "nützlich, wenn man sich vertippt hat oder eine verbesserte "
				+ "Anfrage starten möchte.\n";

		final JTextArea ar = new JTextArea(info);
		ar.setEditable(false);
		ar.setLineWrap(true);
		ar.setWrapStyleWord(true);
		ar.setBackground(UIManager.getColor("Label.background"));
		final JScrollPane scrollpane = new JScrollPane(ar);
		final JOptionPane jOpPane = new JOptionPane(scrollpane,
				JOptionPane.PLAIN_MESSAGE);
		final JDialog jDialog = jOpPane.createDialog(view,
				"   Info zu \"GND-Suche\"");
		jDialog.setSize(500, 400);
		jDialog.setLocationRelativeTo(null);
		jDialog.setResizable(true);
		jDialog.setVisible(true);

	}

	/**
	 *
	 */
	private void performSearch() {
		model.saveValuesInProperties(values);
		try {
			saveFile.createNewFile();
			final FileOutputStream fos = new FileOutputStream(saveFile);
			values.store(fos, null);
		} catch (final FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (final IOException ie) {
			ie.printStackTrace();
		}

		final String searchStr = outputPanel.getText();
		System.out.println(searchStr);
		System.exit(0);
	}

	public SearchController()
			throws InterruptedException, InvocationTargetException {
		super();
		SwingUtilities.invokeAndWait(new Runnable() {

			@SuppressWarnings("synthetic-access")
			@Override
			public void run() {
				view = new View("GND-Suche");
				model = new SearchModel();
				addInfoListener();
				createPanels();
				view.setVisible(true);

				/*
				 * Das Model und die Felder des View mit den alten Werten
				 * füllen:
				 */
				final String userHome = System.getProperty("user.home");
				saveFile = new File(userHome + "/search.properties");
				if (saveFile.exists()) {
					try {
						final FileInputStream fis = new FileInputStream(
								saveFile);
						values.load(fis);
						model.getValuesFromProperties(values);
					} catch (final FileNotFoundException fe) {
						fe.printStackTrace();
					} catch (final IOException ie) {
						ie.printStackTrace();
					}
				}

			}
		});

	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws HeadlessException
	 * @throws Exception
	 */
	public static void main(final String[] args) {

		try {
			final SearchController sc = new SearchController();
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

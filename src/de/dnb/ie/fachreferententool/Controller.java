package de.dnb.ie.fachreferententool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.SwingUtilities;

import de.dnb.basics.utils.OutputUtils;
import de.dnb.basics.utils.TimeUtils;

public class Controller {

	private static final String SGG = "sgg";

	private static final String SYSTEMATIK = "systematik";

	private static final String SATZARTEN = "satzarten";

	private static final String STANDORT = "standort";

	private static final String TEILBEST = "teilbestand";

	// ---------------------

	private static final String SUCHE_NEUANSETZUNGEN = "sucheNeuans";

	private static final String SUCHE_MX = "sucheMx";

	private static final String SUCHE_AUTOM_SG = "sucheAutomSG";

	private static final String SUCHE_ZUGEORDNETE_SG = "sucheZugeordneteSG";

	private static final String SUCHE_AUTOM_SW = "sucheAutomSW";

	View view;

	Searcher searcher;

	private File saveFile;

	Properties props = new Properties();

	private static Controller rc;

	private Controller() {

		view = new View();
		view.addSucheListener(new SucheListener());
		view.addMenuListener(new InfoListener());
		try {
			loadProperties();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String systStr = getValueFromProperties(SYSTEMATIK);
		if (systStr != null)
			view.setSyst(systStr);
		String sgIndices = getValueFromProperties(SGG);
		if (sgIndices != null)
			view.setSGIndices(sgIndices);
		String satzartIndices = getValueFromProperties(SATZARTEN);
		if (satzartIndices != null)
			view.setSatzartIndices(satzartIndices);
		String standort = getValueFromProperties(STANDORT);
		if (standort != null)
			view.setStandort(standort);
		String tbs = getValueFromProperties(TEILBEST);
		if (tbs != null)
			view.setTeilbestand(tbs);

		view.setNeuansetzungen(Boolean.valueOf(getValueFromProperties(SUCHE_NEUANSETZUNGEN)));
		view.setMx(Boolean.valueOf(getValueFromProperties(SUCHE_MX)));
		view.setAutomSGG(Boolean.valueOf(getValueFromProperties(SUCHE_AUTOM_SG)));
		view.setZugewieseneSGG(Boolean.valueOf(getValueFromProperties(SUCHE_ZUGEORDNETE_SG)));
		view.setAutomSWW(Boolean.valueOf(getValueFromProperties(SUCHE_AUTOM_SW)));

		SwingUtilities.invokeLater(() -> searcher = new Searcher(view));
	}

	class SucheListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			saveValueInProperties(SYSTEMATIK, view.getSystString());
			saveValueInProperties(SGG, view.getSGIndices());
			saveValueInProperties(STANDORT, view.getStandort().toString());
			saveValueInProperties(SATZARTEN, view.getSatzartenIndicesString());
			saveValueInProperties(TEILBEST, view.getTeilbestand().toString());

			saveValueInProperties(SUCHE_NEUANSETZUNGEN, Boolean.toString(view.useNeuansetzungen()));
			saveValueInProperties(SUCHE_MX, Boolean.toString(view.useMx()));
			saveValueInProperties(SUCHE_AUTOM_SG, Boolean.toString(view.useAutomSGG()));
			saveValueInProperties(SUCHE_ZUGEORDNETE_SG, Boolean.toString(view.useZugeordnete()));
			saveValueInProperties(SUCHE_AUTOM_SW, Boolean.toString(view.useAutomSWW()));

			try {
				storeProperties();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			searcher.performSearch();
			System.exit(0);
		}
	}

	class InfoListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			OutputUtils.show(
					"Ash nazg durbatulûk, ash nazg gimbatul,<br>" + "ash nazg thrakatulûk agh burzum-ishi krimpatul.");
		}
	}

	public static void main(final String[] args) {
		rc = new Controller();
	}

	public void loadProperties() throws IOException {
		final String userHome = System.getProperty("user.home");
		saveFile = new File(userHome + "/referenten.properties");
		if (saveFile.exists()) {
			final FileInputStream fis = new FileInputStream(saveFile);
			props.load(fis);
		}
	}

	public void storeProperties() throws IOException {
		saveFile.createNewFile();
		final FileOutputStream fos = new FileOutputStream(this.saveFile);
		props.store(fos, null);
	}

	/**
	 * 
	 * @param propertyStr
	 * @return property oder null
	 */
	public String getValueFromProperties(String propertyStr) {
		// Default-Wert null
		return props.getProperty(propertyStr, null);
	}

	public void saveValueInProperties(String propertyStr, String value) {
		props.setProperty(propertyStr, value);
	}

}

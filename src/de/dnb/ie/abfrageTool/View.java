package de.dnb.ie.abfrageTool;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.SwingUtilities;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.collections.ListUtils;
import de.dnb.basics.utils.NumberUtils;
import de.dnb.basics.utils.TimeUtils;
import de.dnb.gnd.utils.SGUtils;

public class View {

	/**
	 * 
	 */
	private static final String SYST_SPLIT = "[\\s,;]+";
	private GUI gui;
	private AbstractListModel<String> myListModel = new AbstractListModel<String>() {
		List<String> values = ListUtils.convertToList(SGUtils.allDHSasString());

		public int getSize() {
			return values.size();
		}

		public String getElementAt(int index) {
			return values.get(index);
		}
	};

	public View() {
		SwingUtilities.invokeLater(() -> {
			View.this.gui = new GUI();
			View.this.gui.setVisible(true);
			gui.chckbxAlleDaten.addActionListener(a -> {
				invertDatum();
			});
			gui.btnReset.addActionListener(a -> {
				resetAll();
			});
			gui.btnCheckboxenReset.addActionListener(a -> {
				checkboxenSelect(false);
			});
			gui.btnAlleCheckboxen.addActionListener(a -> {
				checkboxenSelect(true);
			});
			gui.btnAbbruch.addActionListener(a -> {
				System.exit(0);
			});

			fillSGG();
			resetDate();
		});

	}

	/**
	 * 
	 */
	private void resetDate() {
		gui.dateChooserBis.setCalendar(new GregorianCalendar());
		Calendar today = new GregorianCalendar();
		TimeUtils.decrementDate(today, 7);
		Date before = today.getTime();
		gui.dateChooserVon.setDate(before);
	}

	private void resetAll() {
		gui.listSGG.clearSelection();
		gui.listKonfidenz.clearSelection();
		gui.editorPaneSyst.setText("");
		gui.listSatzart.clearSelection();
		resetDate();
		checkboxenSelect(false);
	}

	/**
	 * 
	 */
	public void checkboxenSelect(boolean select) {
		gui.chckbxNeuansetzungen.setSelected(select);
		gui.chckbxMx.setSelected(select);
		gui.chckbxAutomSg.setSelected(select);
		gui.chckbxZugewieseneTitel.setSelected(select);
		gui.chckbxAutomSww.setSelected(select);
		gui.chckbxKeineSGFreigabe.setSelected(select);
		gui.chckbxSGFreigabe.setSelected(select);
		gui.chckbxKeineSGFreigabeReihe.setSelected(select);
		gui.chckbxSGFreigabeReihe.setSelected(select);
	}

	public final void addSucheListener(final ActionListener al) {
		SwingUtilities.invokeLater(() -> {
			try {
				View.this.gui.btnSuche.addActionListener(al);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
	}

	public final void addAbbruchListener(final ActionListener al) {
		SwingUtilities.invokeLater(() -> View.this.gui.btnAbbruch.addActionListener(al));
	}

	public final void addResetListener(final ActionListener al) {
		SwingUtilities.invokeLater(() -> View.this.gui.btnReset.addActionListener(al));
	}

	public final void addMenuListener(final ActionListener al) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					View.this.gui.mntmInfo.addActionListener(al);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// ----------------------------

	public enum STANDORT {
		FRANKFURT, LEIPZIG, ALLE
	}

	public STANDORT getStandort() {
		if (gui.rdbtnFrankfurt.isSelected())
			return STANDORT.FRANKFURT;
		else if (gui.rdbtnLeipzig.isSelected())
			return STANDORT.LEIPZIG;
		else
			return STANDORT.ALLE;
	}

	public void setStandort(String std) {		
		if (std.equals("FRANKFURT")) {
			SwingUtilities.invokeLater(() -> gui.rdbtnFrankfurt.setSelected(true));
		} else if (std.equals("LEIPZIG")) {
			SwingUtilities.invokeLater(() -> gui.rdbtnLeipzig.setSelected(true));
		} else {
			SwingUtilities.invokeLater(() -> gui.rdbtnAlleStandorte.setSelected(true));
		}
	}

	// -------------------------------

	public enum ADRESSIERUNG {
		ABSENDER, EMPFAENGER, ALLE
	}

	public ADRESSIERUNG getAdressierung() {
		if (gui.rdbtnAbsender.isSelected())
			return ADRESSIERUNG.ABSENDER;
		else if (gui.rdbtnEmpfaenger.isSelected())
			return ADRESSIERUNG.EMPFAENGER;
		else
			return ADRESSIERUNG.ALLE;
	}
	
	public void setAdressierung(String addr) {
		if (addr.equals("ABSENDER")) {
			SwingUtilities.invokeLater(() -> gui.rdbtnAbsender.setSelected(true));
		} else if (addr.equals("EMPFAENGER")) {
			SwingUtilities.invokeLater(() -> gui.rdbtnEmpfaenger.setSelected(true));
		} else {
			SwingUtilities.invokeLater(() -> gui.rdbtnAlleMx.setSelected(true));
		}
	}

	// -------------------------------

	public enum TEILBESTAND {
		SE, FE, ALLE
	}

	public TEILBESTAND getTeilbestand() {
		if (gui.rdbtnSE.isSelected())
			return TEILBESTAND.SE;
		else if (gui.rdbtnFE.isSelected())
			return TEILBESTAND.FE;
		else
			return TEILBESTAND.ALLE;
	}

	public void setTeilbestand(String tbs) {
		if (tbs.equals("SE")) {
			SwingUtilities.invokeLater(() -> gui.rdbtnSE.setSelected(true));
		} else if (tbs.equals("FE")) {
			SwingUtilities.invokeLater(() -> gui.rdbtnFE.setSelected(true));
		} else {
			SwingUtilities.invokeLater(() -> gui.rdbtnAlleBestaende.setSelected(true));
		}
	}

	// -------- Checkboxen ------------------------------

	public boolean useNeuansetzungen() {
		return gui.chckbxNeuansetzungen.isSelected();
	}

	public boolean useMx() {
		return gui.chckbxMx.isSelected();
	}

	public boolean useZugeordnete() {
		return gui.chckbxZugewieseneTitel.isSelected();
	}

	public boolean useAutomSGG() {
		return gui.chckbxAutomSg.isSelected();
	}

	public boolean useAutomSWW() {
		return gui.chckbxAutomSww.isSelected();
	}

	public boolean useSGGFreigeben() {
		return gui.chckbxSGFreigabe.isSelected();
	}

	public boolean useOhneSGGFreigeben() {
		return gui.chckbxKeineSGFreigabe.isSelected();
	}
	
	public boolean useSGGFreigebenReihe() {
		return gui.chckbxSGFreigabeReihe.isSelected();
	}

	public boolean useOhneSGGFreigebenReihe() {
		return gui.chckbxKeineSGFreigabeReihe.isSelected();
	}

	public void setNeuansetzungen(boolean val) {
		SwingUtilities.invokeLater(() -> gui.chckbxNeuansetzungen.setSelected(val));
	}

	public void setMx(boolean val) {
		SwingUtilities.invokeLater(() -> gui.chckbxMx.setSelected(val));
	}

	public void setAutomSGG(boolean val) {
		SwingUtilities.invokeLater(() -> gui.chckbxAutomSg.setSelected(val));
	}

	public void setZugewieseneSGG(boolean val) {
		SwingUtilities.invokeLater(() -> gui.chckbxZugewieseneTitel.setSelected(val));
	}

	public void setAutomSWW(boolean val) {
		SwingUtilities.invokeLater(() -> gui.chckbxAutomSww.setSelected(val));
	}

	public void setSGGFreigabe(boolean val) {
		SwingUtilities.invokeLater(() -> gui.chckbxSGFreigabe.setSelected(val));
	}

	public void setOhneSGGFreigabe(boolean val) {
		SwingUtilities.invokeLater(() -> gui.chckbxKeineSGFreigabe.setSelected(val));
	}
	
	

	// --------------------------------------------------

	public Calendar getVon() {
		return TimeUtils.getCalendar(gui.dateChooserVon.getDate());
	}

	private void invertDatum() {
		SwingUtilities.invokeLater(() -> {
			gui.dateChooserVon.setEnabled(!gui.dateChooserVon.isEnabled());
			gui.dateChooserBis.setEnabled(!gui.dateChooserBis.isEnabled());
			gui.lblVon.setEnabled(!gui.lblVon.isEnabled());
			gui.lblBis.setEnabled(!gui.lblBis.isEnabled());
		});
	}

	public Calendar getBis() {
		return TimeUtils.getCalendar(gui.dateChooserBis.getDate());
	}

	public boolean useAlleDaten() {
		return gui.chckbxAlleDaten.isSelected();
	}

	// ---------------------

	public List<String> getSyst() {
		String s = gui.editorPaneSyst.getText().trim();
		if (s.isEmpty())
			return Collections.emptyList();
		String[] syst = s.split(SYST_SPLIT);
		return Arrays.asList(syst);
	}

	/**
	 * 
	 * @return Systematiknummer, durch " ; " getrennt
	 */
	public String getSystString() {
		return StringUtils.concatenate(" ; ", getSyst());
	}

	private void setSyst(List<String> syst) {
		SwingUtilities.invokeLater(() -> gui.editorPaneSyst.setText(StringUtils.concatenate("\t", syst)));
	}

	public void setSyst(String syst) {
		String[] sysArr = syst.trim().split(SYST_SPLIT);
		setSyst(Arrays.asList(sysArr));
	}

	// ------------------------------

	public List<String> getSatzarten() {
		return gui.listSatzart.getSelectedValuesList();
	}

	private void setSatzartIndices(int[] inds) {
		SwingUtilities.invokeLater(() -> gui.listSatzart.setSelectedIndices(inds));
	}

	public void setSatzartIndices(String s) {
		if (s == null || s.isEmpty())
			return;
		Collection<Integer> indexColl = NumberUtils.getAllInts(s);
		setSatzartIndices(NumberUtils.toArray(indexColl));
	}

	public String getSatzartenIndicesString() {
		List<Integer> selectedInds = NumberUtils.toList(gui.listSatzart.getSelectedIndices());
		return StringUtils.concatenate(" ; ", selectedInds);
	}

	// ------------------------------------------------

	/**
	 * 
	 * @return Konfidenzwert oder null
	 */
	public String getKonfidenzwert() {
		return gui.listKonfidenz.getSelectedValue();
	}

	// -----------------------------------

	/**
	 * 
	 * @return nicht null
	 */
	public List<String> getSGG() {
		return gui.listSGG.getSelectedValuesList();
	}

	/**
	 * 
	 * @return Indizes, durch " ; " getrennt
	 */
	public String getSGIndices() {
		List<Integer> selectedInds = NumberUtils.toList(gui.listSGG.getSelectedIndices());
		return StringUtils.concatenate(" ; ", selectedInds);
	}

	private void setSGIndices(int[] inds) {
		SwingUtilities.invokeLater(() -> gui.listSGG.setSelectedIndices(inds));
	}

	public void setSGIndices(String s) {
		if (s == null || s.isEmpty())
			return;
		Collection<Integer> indexColl = NumberUtils.getAllInts(s);
		setSGIndices(NumberUtils.toArray(indexColl));
	}

	private void fillSGG() {
		gui.listSGG.setModel(myListModel);
	}

}

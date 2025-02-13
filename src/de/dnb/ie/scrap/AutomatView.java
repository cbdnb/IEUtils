package de.dnb.ie.scrap;

import java.awt.EventQueue;

import javax.swing.SwingUtilities;

public class AutomatView {

	AutomatGUI gui;

	public static void main(String[] args) { // TODO Auto-generated method stub
		new AutomatView();
	}

	/**
	 * @param gui
	 */
	public AutomatView() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui = new AutomatGUI();
					gui.frmEinstellungenFrDatensatznderungen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	void set_dummy(String dummy) {
		SwingUtilities.invokeLater(() -> gui.dummy.setSelected(Boolean.parseBoolean(dummy)));
	}

	String get_dummy() {
		return Boolean.toString(gui.dummy.isSelected());
	}

	String get_passw() {
		return new String(gui.passw.getPassword());
	}

	void set_passw(String passw) {
		SwingUtilities.invokeLater(() -> gui.passw.setText(passw));
	}

	String get_f1_aktion() {
		if (gui.f1_ersetzen.isSelected())
			return "ersetzen";
		if (gui.f1_neu.isSelected())
			return "neu";
		if (gui.f1_loeschen.isSelected())
			return "loeschen";
		return null;
	}

	void set_f1_aktion(String action) {
		SwingUtilities.invokeLater(() -> {
			if ("ersetzen".equals(action))
				gui.f1_ersetzen.setSelected(true);
			else if ("neu".equals(action))
				gui.f1_neu.setSelected(true);
			else if ("loeschen".equals(action))
				gui.f1_loeschen.setSelected(true);
		});
	}

	String get_f2_aktion() {
		if (gui.f2_ersetzen.isSelected())
			return "ersetzen";
		if (gui.f2_neu.isSelected())
			return "neu";
		if (gui.f2_loeschen.isSelected())
			return "loeschen";
		return null;
	}

	void set_f2_aktion(String action) {
		SwingUtilities.invokeLater(() -> {
			if ("ersetzen".equals(action))
				gui.f2_ersetzen.setSelected(true);
			else if ("neu".equals(action))
				gui.f2_neu.setSelected(true);
			else if ("loeschen".equals(action))
				gui.f2_loeschen.setSelected(true);
		});
	}

	String get_fkoko_aktion() {
		if (gui.fkoko_ersetzen.isSelected())
			return "ersetzen";
		if (gui.fkoko_neu.isSelected())
			return "neu";
		return null;
	}

	void set_fkoko_aktion(String action) {
		SwingUtilities.invokeLater(() -> {
			if ("ersetzen".equals(action))
				gui.fkoko_ersetzen.setSelected(true);
			else if ("neu".equals(action))
				gui.fkoko_neu.setSelected(true);

		});
	}

	String get_f1_altinh() {
		return gui.f1_altinh.getText();
	}

	void set_f1_altinh(String altinh) {
		SwingUtilities.invokeLater(() -> gui.f1_altinh.setText(altinh));
	}

	String get_f2_altinh() {
		return gui.f2_altinh.getText();
	}

	void set_f2_altinh(String altinh) {
		SwingUtilities.invokeLater(() -> gui.f2_altinh.setText(altinh));
	}

	String get_fkoko_altinh() {
		return gui.fkoko_altinh.getText();
	}

	void set_fkoko_altinh(String altinh) {
		SwingUtilities.invokeLater(() -> gui.fkoko_altinh.setText(altinh));
	}

	String get_f1_alttag() {
		return gui.f1_alttag.getText();
	}

	void set_f1_alttag(String alttag) {
		SwingUtilities.invokeLater(() -> gui.f1_alttag.setText(alttag));
	}

	String get_f2_alttag() {
		return gui.f2_alttag.getText();
	}

	void set_f2_alttag(String alttag) {
		SwingUtilities.invokeLater(() -> gui.f2_alttag.setText(alttag));
	}

	String get_fkoko_alttag() {
		return gui.fkoko_alttag.getText();
	}

	void set_fkoko_alttag(String alttag) {
		SwingUtilities.invokeLater(() -> gui.fkoko_alttag.setText(alttag));
	}

	void set_f1_erster(String erster) {
		SwingUtilities.invokeLater(() -> gui.f1_erster.setSelected(Boolean.parseBoolean(erster)));
	}

	String get_f1_erster() {
		return Boolean.toString(gui.f1_erster.isSelected());
	}

	void set_f2_erster(String erster) {
		SwingUtilities.invokeLater(() -> gui.f2_erster.setSelected(Boolean.parseBoolean(erster)));
	}

	String get_f2_erster() {
		return Boolean.toString(gui.f2_erster.isSelected());
	}

	String get_f1_neuinh() {
		return gui.f1_neuinh.getText();
	}

	void set_f1_neuinh(String neuinh) {
		SwingUtilities.invokeLater(() -> gui.f1_neuinh.setText(neuinh));
	}

	String get_f2_neuinh() {
		return gui.f2_neuinh.getText();
	}

	void set_f2_neuinh(String neuinh) {
		SwingUtilities.invokeLater(() -> gui.f2_neuinh.setText(neuinh));
	}

	String get_f1_neutag() {
		return gui.f1_neutag.getText();
	}

	void set_f1_neutag(String neuinh) {
		SwingUtilities.invokeLater(() -> gui.f1_neutag.setText(neuinh));
	}

	String get_f2_neutag() {
		return gui.f2_neutag.getText();
	}

	void set_f2_neutag(String neutag) {
		SwingUtilities.invokeLater(() -> gui.f2_neutag.setText(neutag));
	}

	String get_fkoko_neutag() {
		return gui.fkoko_neutag.getText();
	}

	void set_fkoko_neutag(String neutag) {
		SwingUtilities.invokeLater(() -> gui.fkoko_neutag.setText(neutag));
	}

	void set_f1_regex(String regex) {
		SwingUtilities.invokeLater(() -> gui.f1_regex.setSelected(Boolean.parseBoolean(regex)));
	}

	String get_f1_regex() {
		return Boolean.toString(gui.f1_regex.isSelected());
	}

	void set_f2_regex(String regex) {
		SwingUtilities.invokeLater(() -> gui.f2_regex.setSelected(Boolean.parseBoolean(regex)));
	}

	String get_f2_regex() {
		return Boolean.toString(gui.f2_regex.isSelected());
	}

	void set_fkoko_regex(String regex) {
		SwingUtilities.invokeLater(() -> gui.fkoko_regex.setSelected(Boolean.parseBoolean(regex)));
	}

	String get_fkoko_regex() {
		return Boolean.toString(gui.fkoko_regex.isSelected());
	}

	String get_f1_trenner() {
		return gui.f1_trenner.getText();
	}

	void set_f1_trenner(String neutag) {
		SwingUtilities.invokeLater(() -> gui.f1_trenner.setText(neutag));
	}

	String get_f2_trenner() {
		return gui.f2_trenner.getText();
	}

	void set_f2_trenner(String neutag) {
		SwingUtilities.invokeLater(() -> gui.f2_trenner.setText(neutag));
	}

	String get_fkoko_trenner() {
		return gui.fkoko_trenner.getText();
	}

	void set_fkoko_trenner(String neutag) {
		SwingUtilities.invokeLater(() -> gui.fkoko_trenner.setText(neutag));
	}

	void set_f1_wdh(String wdh) {
		SwingUtilities.invokeLater(() -> gui.f1_wdh.setSelected(Boolean.parseBoolean(wdh)));
	}

	String get_f1_wdh() {
		return Boolean.toString(gui.f1_wdh.isSelected());
	}

	void set_f2_wdh(String wdh) {
		SwingUtilities.invokeLater(() -> gui.f2_wdh.setSelected(Boolean.parseBoolean(wdh)));
	}

	String get_f2_wdh() {
		return Boolean.toString(gui.f2_wdh.isSelected());
	}

	void set_fkoko_wdh(String wdh) {
		SwingUtilities.invokeLater(() -> gui.fkoko_wdh.setSelected(Boolean.parseBoolean(wdh)));
	}

	String get_fkoko_wdh() {
		return Boolean.toString(gui.fkoko_wdh.isSelected());
	}

}

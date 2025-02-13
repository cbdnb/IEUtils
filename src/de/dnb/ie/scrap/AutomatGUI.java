package de.dnb.ie.scrap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class AutomatGUI {

	private static final int SPALTE_4 = 148;
	private static final int SPALTE_3 = 58;
	private static final int SPALTE_2 = 45;
	private static final int SPALTE_1 = 21;
	private static final Font FONT = new Font("Tahoma", Font.PLAIN, 16);
	private static final Font FONT_B = new Font("Tahoma", Font.BOLD, 16);
	private static final Font FONT_INFO = new Font("Tahoma", Font.PLAIN, 14);
	private static final int FIELD_HEIGHT = 26;
	JFrame frmEinstellungenFrDatensatznderungen;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	JCheckBox dummy;
	JPasswordField passw;

	JTextField f1_alttag;
	JTextField f1_altinh;
	JCheckBox f1_wdh;
	JCheckBox f1_regex;
	JRadioButton f1_neu;
	JCheckBox f1_erster;
	JTextField f1_trenner;
	JRadioButton f1_ersetzen;
	JTextField f1_neutag;
	JTextField f1_neuinh;
	JRadioButton f1_loeschen;

	JTextField f2_alttag;
	JTextField f2_altinh;
	JCheckBox f2_wdh;
	JCheckBox f2_regex;
	JRadioButton f2_neu;
	JCheckBox f2_erster;
	JTextField f2_trenner;
	JRadioButton f2_ersetzen;
	JTextField f2_neutag;
	JTextField f2_neuinh;
	JRadioButton f2_loeschen;

	JTextField fkoko_alttag;
	JTextField fkoko_altinh;
	JCheckBox fkoko_wdh;
	JCheckBox fkoko_regex;
	JRadioButton fkoko_neu;
	JTextField fkoko_trenner;
	JRadioButton fkoko_ersetzen;
	JTextField fkoko_neutag;

	private JButton btnOK;
	private JButton btnAbbrechen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AutomatGUI window = new AutomatGUI();
					window.frmEinstellungenFrDatensatznderungen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AutomatGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEinstellungenFrDatensatznderungen = new JFrame();
		frmEinstellungenFrDatensatznderungen.setTitle("Einstellungen für Datensatzänderungen");
		frmEinstellungenFrDatensatznderungen.setFont(new Font("Dialog", Font.PLAIN, 12));
		frmEinstellungenFrDatensatznderungen.setBackground(new Color(240, 240, 240));
		frmEinstellungenFrDatensatznderungen.setBounds(100, 50, 1297, 825);
		frmEinstellungenFrDatensatznderungen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(FONT);
		frmEinstellungenFrDatensatznderungen.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panel_allg = new JPanel();
		panel_allg.setFont(FONT);
		tabbedPane.addTab("Allg.", null, panel_allg, null);
		panel_allg.setLayout(null);

		JPanel panel_allg_1 = new JPanel();
		panel_allg_1.setBounds(46, 64, 1138, 357);
		panel_allg_1.setBorder(BorderFactory.createTitledBorder(null, "Allgemeine Angaben", 0, 0, FONT_INFO));

		panel_allg.add(panel_allg_1);
		panel_allg_1.setLayout(null);

		dummy = new JCheckBox("Dummy-Lauf");
		dummy.setFont(FONT);
		dummy.setBounds(SPALTE_3, 114, 248, 47);
		panel_allg_1.add(dummy);

		passw = new JPasswordField();
		passw.setFont(FONT);
		passw.setBounds(SPALTE_4, 58, 538, FIELD_HEIGHT);
		panel_allg_1.add(passw);

		JLabel lblNewLabel = new JLabel("Passwort");
		lblNewLabel.setFont(FONT);
		lblNewLabel.setBounds(SPALTE_3, 58, 164, FIELD_HEIGHT);
		panel_allg_1.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setFont(FONT);
		tabbedPane.addTab("1. Feld", null, panel_1, null);
		panel_1.setLayout(null);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setMaximumSize(new Dimension(32767, 20000));
		panel_1_1.setBorder(BorderFactory.createTitledBorder(null, "Angaben Feld", 0, 0, FONT_INFO));
		panel_1_1.setBounds(0, 0, 1278, 299);
		panel_1.add(panel_1_1);

		JLabel lblNewLabel_3_2 = new JLabel("1. Ausgangsfeld, das bearbeitet werden soll:");
		lblNewLabel_3_2.setFont(FONT);
		lblNewLabel_3_2.setBounds(21, 41, 318, 26);
		panel_1_1.add(lblNewLabel_3_2);

		f1_alttag = new JTextField();
		f1_alttag.setMaximumSize(new Dimension(100, 26));
		f1_alttag.setFont(FONT);
		f1_alttag.setBounds(45, 78, 65, 26);
		panel_1_1.add(f1_alttag);

		JLabel lblNewLabel_3_1_4 = new JLabel("Inhalt des 1. Ausgangsfeldes");
		lblNewLabel_3_1_4.setFont(FONT);
		lblNewLabel_3_1_4.setBounds(21, 114, 205, 26);
		panel_1_1.add(lblNewLabel_3_1_4);

		f1_altinh = new JTextField();
		f1_altinh.setFont(FONT);
		f1_altinh.setBounds(45, 144, 1076, 26);
		panel_1_1.add(f1_altinh);

		f1_wdh = new JCheckBox("Feld ist wiederholbar");
		f1_wdh.setFont(FONT);
		f1_wdh.setBounds(21, 208, 175, 26);
		panel_1_1.add(f1_wdh);

		JPanel panel_1_2 = new JPanel();
		panel_1_2.setLayout(null);
		panel_1_2.setMaximumSize(new Dimension(32767, 27767));
		panel_1_2.setBorder(BorderFactory.createTitledBorder(null, "Aktion", 0, 0, FONT_INFO));
		panel_1_2.setBounds(0, 295, 1268, 410);
		panel_1.add(panel_1_2);

		f1_regex = new JCheckBox("Regulärer Ausdruck");
		f1_regex.setFont(FONT);
		f1_regex.setBounds(21, 38, 297, 26);
		panel_1_2.add(f1_regex);

		f1_neu = new JRadioButton("oben genannten Feldinhalt neu einfügen");
		buttonGroup.add(f1_neu);
		f1_neu.setFont(FONT_B);
		f1_neu.setBounds(21, 74, 531, 26);
		panel_1_2.add(f1_neu);

		f1_erster = new JCheckBox("Falls wiederholbar: Neues Feld als 1. Vorkommen des Feldes eintragen");
		f1_erster.setFont(FONT);
		f1_erster.setBounds(45, 102, 531, 26);
		panel_1_2.add(f1_erster);

		JLabel lblNewLabel_3_1_1_1 = new JLabel(
				"Falls nicht wiederholbar: Wenn anderer Inhalt vorhanden, neuen Inhalt mit folgendem Trennzeichen anhängen (sonst Fehlerdatei)");
		lblNewLabel_3_1_1_1.setFont(FONT);
		lblNewLabel_3_1_1_1.setBounds(45, 137, 930, 26);
		panel_1_2.add(lblNewLabel_3_1_1_1);

		f1_trenner = new JTextField();
		f1_trenner.setFont(FONT);
		f1_trenner.setBounds(45, 167, 40, 26);
		panel_1_2.add(f1_trenner);

		f1_ersetzen = new JRadioButton("oben genannten Feldinhalt ersetzen durch:");
		buttonGroup.add(f1_ersetzen);
		f1_ersetzen.setFont(FONT_B);
		f1_ersetzen.setBounds(21, 205, 415, 26);
		panel_1_2.add(f1_ersetzen);

		JLabel lblNewLabel_3_1_2_1 = new JLabel("Feld:");
		lblNewLabel_3_1_2_1.setFont(FONT);
		lblNewLabel_3_1_2_1.setBounds(45, 230, 35, 26);
		panel_1_2.add(lblNewLabel_3_1_2_1);

		f1_neutag = new JTextField();
		f1_neutag.setMaximumSize(new Dimension(100, 26));
		f1_neutag.setFont(FONT);
		f1_neutag.setBounds(45, 260, 55, 26);
		panel_1_2.add(f1_neutag);

		JLabel lblNewLabel_3_1_3_1 = new JLabel("Feldinhalt:");
		lblNewLabel_3_1_3_1.setFont(FONT);
		lblNewLabel_3_1_3_1.setBounds(45, 287, 75, 26);
		panel_1_2.add(lblNewLabel_3_1_3_1);

		f1_neuinh = new JTextField();
		f1_neuinh.setFont(FONT);
		f1_neuinh.setBounds(45, 313, 1082, 26);
		panel_1_2.add(f1_neuinh);

		f1_loeschen = new JRadioButton("Feld löschen");
		buttonGroup.add(f1_loeschen);
		f1_loeschen.setFont(FONT_B);
		f1_loeschen.setBounds(21, 350, 210, 26);
		panel_1_2.add(f1_loeschen);

		JPanel panel_2 = new JPanel();
		panel_2.setFont(FONT);
		tabbedPane.addTab("2. Feld", null, panel_2, null);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

		JPanel panel_2_1 = new JPanel();
		panel_2_1.setMaximumSize(new Dimension(32767, 20000));
		panel_2_1.setBorder(BorderFactory.createTitledBorder(null, "Angaben Feld", 0, 0, FONT_INFO));
		panel_2.add(panel_2_1);
		panel_2_1.setLayout(null);
		panel_2_1.setSize(0, 100);

		JLabel lblNewLabel_3 = new JLabel("2. Ausgangsfeld, das bearbeitet werden soll:");
		lblNewLabel_3.setBounds(SPALTE_1, 41, 318, FIELD_HEIGHT);
		lblNewLabel_3.setFont(FONT);
		panel_2_1.add(lblNewLabel_3);

		f2_alttag = new JTextField();
		f2_alttag.setBounds(SPALTE_2, 78, 65, 26);
		f2_alttag.setFont(FONT);
		panel_2_1.add(f2_alttag);
		f2_alttag.setMaximumSize(new Dimension(100, f2_alttag.getPreferredSize().height));

		JLabel lblNewLabel_3_1 = new JLabel("Inhalt des 2. Ausgangsfeldes");
		lblNewLabel_3_1.setBounds(SPALTE_1, 114, 205, FIELD_HEIGHT);
		lblNewLabel_3_1.setFont(FONT);
		panel_2_1.add(lblNewLabel_3_1);

		f2_altinh = new JTextField();
		f2_altinh.setBounds(SPALTE_2, 144, 1076, FIELD_HEIGHT);
		f2_altinh.setFont(FONT);
		panel_2_1.add(f2_altinh);

		f2_wdh = new JCheckBox("Feld ist wiederholbar");
		f2_wdh.setBounds(SPALTE_1, 208, 175, FIELD_HEIGHT);
		f2_wdh.setFont(FONT);
		panel_2_1.add(f2_wdh);

		JPanel panel_2_2 = new JPanel();
		panel_2_2.setMaximumSize(new Dimension(32767, 27767));
		panel_2_2.setLayout(null);
		panel_2_2.setBorder(BorderFactory.createTitledBorder(null, "Aktion", 0, 0, FONT_INFO));
		panel_2.add(panel_2_2);

		f2_regex = new JCheckBox("Regulärer Ausdruck");
		f2_regex.setBounds(21, 38, 272, 26);
		f2_regex.setFont(FONT);
		panel_2_2.add(f2_regex);

		f2_neu = new JRadioButton("oben genannten Feldinhalt neu einfügen");
		f2_neu.setBounds(21, 74, 452, 26);
		f2_neu.setFont(FONT_B);
		buttonGroup.add(f2_neu);
		panel_2_2.add(f2_neu);

		f2_erster = new JCheckBox("Falls wiederholbar: Neues Feld als 1. Vorkommen des Feldes eintragen");
		f2_erster.setBounds(SPALTE_2, 102, 531, FIELD_HEIGHT);
		f2_erster.setFont(FONT);
		panel_2_2.add(f2_erster);

		JLabel lblNewLabel_3_1_1 = new JLabel(
				"Falls nicht wiederholbar: Wenn anderer Inhalt vorhanden, neuen Inhalt mit folgendem Trennzeichen anhängen (sonst Fehlerdatei)");
		lblNewLabel_3_1_1.setBounds(SPALTE_2, 137, 930, FIELD_HEIGHT);
		lblNewLabel_3_1_1.setFont(FONT);
		panel_2_2.add(lblNewLabel_3_1_1);

		f2_trenner = new JTextField();
		f2_trenner.setBounds(SPALTE_2, 167, 40, FIELD_HEIGHT);
		f2_trenner.setFont(FONT);
		panel_2_2.add(f2_trenner);

		f2_ersetzen = new JRadioButton("oben genannten Feldinhalt ersetzen durch:");
		f2_ersetzen.setBounds(21, 205, 400, 26);
		f2_ersetzen.setFont(FONT_B);
		buttonGroup.add(f2_ersetzen);
		panel_2_2.add(f2_ersetzen);

		JLabel lblNewLabel_3_1_2 = new JLabel("Feld:");
		lblNewLabel_3_1_2.setBounds(SPALTE_2, 230, 35, FIELD_HEIGHT);
		lblNewLabel_3_1_2.setFont(FONT);
		panel_2_2.add(lblNewLabel_3_1_2);

		f2_neutag = new JTextField();
		f2_neutag.setBounds(SPALTE_2, 260, 55, 26);
		f2_neutag.setMaximumSize(new Dimension(100, FIELD_HEIGHT));
		f2_neutag.setFont(FONT);
		panel_2_2.add(f2_neutag);

		JLabel lblNewLabel_3_1_3 = new JLabel("Feldinhalt:");
		lblNewLabel_3_1_3.setBounds(SPALTE_2, 287, 75, FIELD_HEIGHT);
		lblNewLabel_3_1_3.setFont(FONT);
		panel_2_2.add(lblNewLabel_3_1_3);

		f2_neuinh = new JTextField();
		f2_neuinh.setBounds(SPALTE_2, 313, 1082, FIELD_HEIGHT);
		f2_neuinh.setFont(FONT);
		panel_2_2.add(f2_neuinh);

		f2_loeschen = new JRadioButton("Feld löschen");
		f2_loeschen.setBounds(21, 350, 257, 26);
		f2_loeschen.setFont(FONT_B);
		buttonGroup.add(f2_loeschen);
		panel_2_2.add(f2_loeschen);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setFont(FONT);
		tabbedPane.addTab("Angaben einer Konkordanz", null, panel_3, null);

		JPanel panel_3_1 = new JPanel();
		panel_3_1.setLayout(null);
		panel_3_1.setMaximumSize(new Dimension(32767, 20000));
		panel_3_1.setBorder(BorderFactory.createTitledBorder(null, "Angaben Feld", 0, 0, FONT_INFO));
		panel_3_1.setBounds(0, 0, 1278, 299);
		panel_3.add(panel_3_1);

		JLabel lblNewLabel_3_2_1 = new JLabel("Feld, das bearbeitet werden soll:");
		lblNewLabel_3_2_1.setFont(FONT);
		lblNewLabel_3_2_1.setBounds(20, 79, 318, 26);
		panel_3_1.add(lblNewLabel_3_2_1);

		fkoko_alttag = new JTextField();
		fkoko_alttag.setMaximumSize(new Dimension(100, 26));
		fkoko_alttag.setFont(FONT);
		fkoko_alttag.setBounds(44, 116, 65, 26);
		panel_3_1.add(fkoko_alttag);

		JLabel lblNewLabel_3_1_4_1 = new JLabel("Inhalt des Ausgangsfeldes");
		lblNewLabel_3_1_4_1.setFont(FONT);
		lblNewLabel_3_1_4_1.setBounds(20, 152, 205, 26);
		panel_3_1.add(lblNewLabel_3_1_4_1);

		fkoko_altinh = new JTextField();
		fkoko_altinh.setFont(FONT);
		fkoko_altinh.setBounds(44, 182, 1076, 26);
		panel_3_1.add(fkoko_altinh);

		fkoko_wdh = new JCheckBox("Feld ist wiederholbar");
		fkoko_wdh.setFont(FONT);
		fkoko_wdh.setBounds(20, 246, 175, 26);
		panel_3_1.add(fkoko_wdh);

		JLabel lblNewLabel_1 = new JLabel(
				"Hinzufügen von Feldinhalten über eine Konkordanz. Die Konkordanz muss zwei mit Tabulator getrennte Spalten enthalten: IDN - Feldinhalt");
		lblNewLabel_1.setBounds(SPALTE_1, 38, 1190, FIELD_HEIGHT);
		lblNewLabel_1.setFont(FONT);
		panel_3_1.add(lblNewLabel_1);

		JPanel panel_3_2 = new JPanel();
		panel_3_2.setLayout(null);
		panel_3_2.setMaximumSize(new Dimension(32767, 27767));
		panel_3_2.setBorder(BorderFactory.createTitledBorder(null, "Aktion", 0, 0, FONT_INFO));
		panel_3_2.setBounds(0, 295, 1268, 410);
		panel_3.add(panel_3_2);

		fkoko_regex = new JCheckBox("Regulärer Ausdruck");
		fkoko_regex.setFont(FONT);
		fkoko_regex.setBounds(21, 38, 274, 26);
		panel_3_2.add(fkoko_regex);

		fkoko_neu = new JRadioButton("oben genanntes Feld mit neuem Inhalt aus Konkordanz neu einfügen");
		buttonGroup.add(fkoko_neu);
		fkoko_neu.setFont(FONT_B);
		fkoko_neu.setBounds(SPALTE_1, 105, 597, FIELD_HEIGHT);
		panel_3_2.add(fkoko_neu);

		JLabel lblNewLabel_3_1_1_1_1 = new JLabel(
				"Falls nicht wiederholbar: Wenn anderer Inhalt vorhanden, neuen Inhalt mit folgendem Trennzeichen anhängen (sonst Fehlerdatei)");
		lblNewLabel_3_1_1_1_1.setFont(FONT);
		lblNewLabel_3_1_1_1_1.setBounds(SPALTE_2, 137, 930, FIELD_HEIGHT);
		panel_3_2.add(lblNewLabel_3_1_1_1_1);

		fkoko_trenner = new JTextField();
		fkoko_trenner.setFont(FONT);
		fkoko_trenner.setBounds(SPALTE_2, 167, 40, FIELD_HEIGHT);
		panel_3_2.add(fkoko_trenner);

		fkoko_ersetzen = new JRadioButton("oben genannten Feldinhalt ersetzen durch Feld und Inhalt der Konkordanz:");
		buttonGroup.add(fkoko_ersetzen);
		fkoko_ersetzen.setFont(FONT_B);
		fkoko_ersetzen.setBounds(21, 205, 639, 26);
		panel_3_2.add(fkoko_ersetzen);

		JLabel lblNewLabel_3_1_2_1_1 = new JLabel("Feld:");
		lblNewLabel_3_1_2_1_1.setFont(FONT);
		lblNewLabel_3_1_2_1_1.setBounds(SPALTE_2, 230, 35, FIELD_HEIGHT);
		panel_3_2.add(lblNewLabel_3_1_2_1_1);

		fkoko_neutag = new JTextField();
		fkoko_neutag.setMaximumSize(new Dimension(100, 26));
		fkoko_neutag.setFont(FONT);
		fkoko_neutag.setBounds(SPALTE_2, 260, 339, FIELD_HEIGHT);
		panel_3_2.add(fkoko_neutag);

		JPanel panel = new JPanel();
		frmEinstellungenFrDatensatznderungen.getContentPane().add(panel, BorderLayout.SOUTH);

		btnOK = new JButton("OK");
		btnOK.setFont(FONT);
		panel.add(btnOK);

		btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.setFont(FONT);
		btnAbbrechen.addActionListener(a -> System.exit(0));
		panel.add(btnAbbrechen);
	}
}

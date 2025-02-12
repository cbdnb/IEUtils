package de.dnb.ie.scrap;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Rectangle;

public class AutomatGUI {

	private static final int SPALTE_4 = 148;
	private static final int SPALTE_3 = 58;
	private static final int SPALTE_2 = 45;
	private static final int SPALTE_1 = 21;
	private static final Font FONT = new Font("Tahoma", Font.PLAIN, 16);
	private static final Font FONT_B = new Font("Tahoma", Font.BOLD, 16);
	private static final Font FONT_INFO = new Font("Tahoma", Font.PLAIN, 14);
	private static final int FIELD_HEIGHT = 26;
	private JFrame frmEinstellungenFrDatensatznderungen;
	private JPasswordField passwordField;
	private JTextField textField;
	private JTextField textField_1;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;

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

		JCheckBox chckbxNewCheckBox = new JCheckBox("Dummy-Lauf");
		chckbxNewCheckBox.setFont(FONT);
		chckbxNewCheckBox.setBounds(SPALTE_3, 114, 248, 47);
		panel_allg_1.add(chckbxNewCheckBox);

		passwordField = new JPasswordField();
		passwordField.setFont(FONT);
		passwordField.setBounds(SPALTE_4, 58, 538, FIELD_HEIGHT);
		panel_allg_1.add(passwordField);

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

		textField_5 = new JTextField();
		textField_5.setMaximumSize(new Dimension(100, 26));
		textField_5.setFont(FONT);
		textField_5.setBounds(45, 78, 65, 26);
		panel_1_1.add(textField_5);

		JLabel lblNewLabel_3_1_4 = new JLabel("Inhalt des 1. Ausgangsfeldes");
		lblNewLabel_3_1_4.setFont(FONT);
		lblNewLabel_3_1_4.setBounds(21, 114, 205, 26);
		panel_1_1.add(lblNewLabel_3_1_4);

		textField_6 = new JTextField();
		textField_6.setFont(FONT);
		textField_6.setBounds(45, 144, 1076, 26);
		panel_1_1.add(textField_6);

		JCheckBox chckbxNewCheckBox_1_2 = new JCheckBox("Feld ist wiederholbar");
		chckbxNewCheckBox_1_2.setFont(FONT);
		chckbxNewCheckBox_1_2.setBounds(21, 208, 175, 26);
		panel_1_1.add(chckbxNewCheckBox_1_2);

		JPanel panel_1_2 = new JPanel();
		panel_1_2.setLayout(null);
		panel_1_2.setMaximumSize(new Dimension(32767, 27767));
		panel_1_2.setBorder(BorderFactory.createTitledBorder(null, "Aktion", 0, 0, FONT_INFO));
		panel_1_2.setBounds(0, 295, 1268, 410);
		panel_1.add(panel_1_2);

		JCheckBox chckbxNewCheckBox_1_1_2 = new JCheckBox("Regulärer Ausdruck");
		chckbxNewCheckBox_1_1_2.setFont(FONT);
		chckbxNewCheckBox_1_1_2.setBounds(21, 38, 163, 26);
		panel_1_2.add(chckbxNewCheckBox_1_1_2);

		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("oben genannten Feldinhalt neu einfügen");
		buttonGroup.add(rdbtnNewRadioButton_3);
		rdbtnNewRadioButton_3.setFont(FONT_B);
		rdbtnNewRadioButton_3.setBounds(21, 74, 531, 26);
		panel_1_2.add(rdbtnNewRadioButton_3);

		JCheckBox chckbxNewCheckBox_1_1_1_1 = new JCheckBox(
				"Falls wiederholbar: Neues Feld als 1. Vorkommen des Feldes eintragen");
		chckbxNewCheckBox_1_1_1_1.setFont(FONT);
		chckbxNewCheckBox_1_1_1_1.setBounds(45, 102, 531, 26);
		panel_1_2.add(chckbxNewCheckBox_1_1_1_1);

		JLabel lblNewLabel_3_1_1_1 = new JLabel(
				"Falls nicht wiederholbar: Wenn anderer Inhalt vorhanden, neuen Inhalt mit folgendem Trennzeichen anhängen (sonst Fehlerdatei)");
		lblNewLabel_3_1_1_1.setFont(FONT);
		lblNewLabel_3_1_1_1.setBounds(45, 137, 930, 26);
		panel_1_2.add(lblNewLabel_3_1_1_1);

		textField_7 = new JTextField();
		textField_7.setFont(FONT);
		textField_7.setBounds(45, 167, 40, 26);
		panel_1_2.add(textField_7);

		JRadioButton rdbtnNewRadioButton_1_1 = new JRadioButton("oben genannten Feldinhalt ersetzen durch:");
		buttonGroup.add(rdbtnNewRadioButton_1_1);
		rdbtnNewRadioButton_1_1.setFont(FONT_B);
		rdbtnNewRadioButton_1_1.setBounds(21, 205, 415, 26);
		panel_1_2.add(rdbtnNewRadioButton_1_1);

		JLabel lblNewLabel_3_1_2_1 = new JLabel("Feld:");
		lblNewLabel_3_1_2_1.setFont(FONT);
		lblNewLabel_3_1_2_1.setBounds(45, 230, 35, 26);
		panel_1_2.add(lblNewLabel_3_1_2_1);

		textField_8 = new JTextField();
		textField_8.setMaximumSize(new Dimension(100, 26));
		textField_8.setFont(FONT);
		textField_8.setBounds(45, 260, 55, 26);
		panel_1_2.add(textField_8);

		JLabel lblNewLabel_3_1_3_1 = new JLabel("Feldinhalt:");
		lblNewLabel_3_1_3_1.setFont(FONT);
		lblNewLabel_3_1_3_1.setBounds(45, 287, 75, 26);
		panel_1_2.add(lblNewLabel_3_1_3_1);

		textField_9 = new JTextField();
		textField_9.setFont(FONT);
		textField_9.setBounds(45, 313, 1082, 26);
		panel_1_2.add(textField_9);

		JRadioButton rdbtnNewRadioButton_2_1 = new JRadioButton("Feld löschen");
		buttonGroup.add(rdbtnNewRadioButton_2_1);
		rdbtnNewRadioButton_2_1.setFont(FONT_B);
		rdbtnNewRadioButton_2_1.setBounds(21, 350, 210, 26);
		panel_1_2.add(rdbtnNewRadioButton_2_1);

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

		textField = new JTextField();
		textField.setBounds(SPALTE_2, 78, 65, 26);
		textField.setFont(FONT);
		panel_2_1.add(textField);
		textField.setMaximumSize(new Dimension(100, textField.getPreferredSize().height));

		JLabel lblNewLabel_3_1 = new JLabel("Inhalt des 2. Ausgangsfeldes");
		lblNewLabel_3_1.setBounds(SPALTE_1, 114, 205, FIELD_HEIGHT);
		lblNewLabel_3_1.setFont(FONT);
		panel_2_1.add(lblNewLabel_3_1);

		textField_1 = new JTextField();
		textField_1.setBounds(SPALTE_2, 144, 1076, FIELD_HEIGHT);
		textField_1.setFont(FONT);
		panel_2_1.add(textField_1);

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Feld ist wiederholbar");
		chckbxNewCheckBox_1.setBounds(SPALTE_1, 208, 175, FIELD_HEIGHT);
		chckbxNewCheckBox_1.setFont(FONT);
		panel_2_1.add(chckbxNewCheckBox_1);

		JPanel panel_2_2 = new JPanel();
		panel_2_2.setMaximumSize(new Dimension(32767, 27767));
		panel_2_2.setLayout(null);
		panel_2_2.setBorder(BorderFactory.createTitledBorder(null, "Aktion", 0, 0, FONT_INFO));
		panel_2.add(panel_2_2);

		JCheckBox chckbxNewCheckBox_1_1 = new JCheckBox("Regulärer Ausdruck");
		chckbxNewCheckBox_1_1.setBounds(SPALTE_1, 38, 163, FIELD_HEIGHT);
		chckbxNewCheckBox_1_1.setFont(FONT);
		panel_2_2.add(chckbxNewCheckBox_1_1);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("oben genannten Feldinhalt neu einfügen");
		rdbtnNewRadioButton.setBounds(21, 74, 452, 26);
		rdbtnNewRadioButton.setFont(FONT_B);
		buttonGroup.add(rdbtnNewRadioButton);
		panel_2_2.add(rdbtnNewRadioButton);

		JCheckBox chckbxNewCheckBox_1_1_1 = new JCheckBox(
				"Falls wiederholbar: Neues Feld als 1. Vorkommen des Feldes eintragen");
		chckbxNewCheckBox_1_1_1.setBounds(SPALTE_2, 102, 531, FIELD_HEIGHT);
		chckbxNewCheckBox_1_1_1.setFont(FONT);
		panel_2_2.add(chckbxNewCheckBox_1_1_1);

		JLabel lblNewLabel_3_1_1 = new JLabel(
				"Falls nicht wiederholbar: Wenn anderer Inhalt vorhanden, neuen Inhalt mit folgendem Trennzeichen anhängen (sonst Fehlerdatei)");
		lblNewLabel_3_1_1.setBounds(SPALTE_2, 137, 930, FIELD_HEIGHT);
		lblNewLabel_3_1_1.setFont(FONT);
		panel_2_2.add(lblNewLabel_3_1_1);

		textField_2 = new JTextField();
		textField_2.setBounds(SPALTE_2, 167, 40, FIELD_HEIGHT);
		textField_2.setFont(FONT);
		panel_2_2.add(textField_2);

		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("oben genannten Feldinhalt ersetzen durch:");
		rdbtnNewRadioButton_1.setBounds(21, 205, 400, 26);
		rdbtnNewRadioButton_1.setFont(FONT_B);
		buttonGroup.add(rdbtnNewRadioButton_1);
		panel_2_2.add(rdbtnNewRadioButton_1);

		JLabel lblNewLabel_3_1_2 = new JLabel("Feld:");
		lblNewLabel_3_1_2.setBounds(SPALTE_2, 230, 35, FIELD_HEIGHT);
		lblNewLabel_3_1_2.setFont(FONT);
		panel_2_2.add(lblNewLabel_3_1_2);

		textField_3 = new JTextField();
		textField_3.setBounds(SPALTE_2, 260, 55, 26);
		textField_3.setMaximumSize(new Dimension(100, FIELD_HEIGHT));
		textField_3.setFont(FONT);
		panel_2_2.add(textField_3);

		JLabel lblNewLabel_3_1_3 = new JLabel("Feldinhalt:");
		lblNewLabel_3_1_3.setBounds(SPALTE_2, 287, 75, FIELD_HEIGHT);
		lblNewLabel_3_1_3.setFont(FONT);
		panel_2_2.add(lblNewLabel_3_1_3);

		textField_4 = new JTextField();
		textField_4.setBounds(SPALTE_2, 313, 1082, FIELD_HEIGHT);
		textField_4.setFont(FONT);
		panel_2_2.add(textField_4);

		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Feld löschen");
		rdbtnNewRadioButton_2.setBounds(21, 350, 257, 26);
		rdbtnNewRadioButton_2.setFont(FONT_B);
		buttonGroup.add(rdbtnNewRadioButton_2);
		panel_2_2.add(rdbtnNewRadioButton_2);

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

		textField_10 = new JTextField();
		textField_10.setMaximumSize(new Dimension(100, 26));
		textField_10.setFont(FONT);
		textField_10.setBounds(44, 116, 65, 26);
		panel_3_1.add(textField_10);

		JLabel lblNewLabel_3_1_4_1 = new JLabel("Inhalt des Ausgangsfeldes");
		lblNewLabel_3_1_4_1.setFont(FONT);
		lblNewLabel_3_1_4_1.setBounds(20, 152, 205, 26);
		panel_3_1.add(lblNewLabel_3_1_4_1);

		textField_11 = new JTextField();
		textField_11.setFont(FONT);
		textField_11.setBounds(44, 182, 1076, 26);
		panel_3_1.add(textField_11);

		JCheckBox chckbxNewCheckBox_1_2_1 = new JCheckBox("Feld ist wiederholbar");
		chckbxNewCheckBox_1_2_1.setFont(FONT);
		chckbxNewCheckBox_1_2_1.setBounds(20, 246, 175, 26);
		panel_3_1.add(chckbxNewCheckBox_1_2_1);

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

		JCheckBox chckbxNewCheckBox_1_1_2_1 = new JCheckBox("Regulärer Ausdruck");
		chckbxNewCheckBox_1_1_2_1.setFont(FONT);
		chckbxNewCheckBox_1_1_2_1.setBounds(SPALTE_1, 38, 163, FIELD_HEIGHT);
		panel_3_2.add(chckbxNewCheckBox_1_1_2_1);

		JRadioButton rdbtnNewRadioButton_3_1 = new JRadioButton(
				"oben genanntes Feld mit neuem Inhalt aus Konkordanz neu einfügen");
		buttonGroup.add(rdbtnNewRadioButton_3_1);
		rdbtnNewRadioButton_3_1.setFont(FONT_B);
		rdbtnNewRadioButton_3_1.setBounds(SPALTE_1, 105, 597, FIELD_HEIGHT);
		panel_3_2.add(rdbtnNewRadioButton_3_1);

		JLabel lblNewLabel_3_1_1_1_1 = new JLabel(
				"Falls nicht wiederholbar: Wenn anderer Inhalt vorhanden, neuen Inhalt mit folgendem Trennzeichen anhängen (sonst Fehlerdatei)");
		lblNewLabel_3_1_1_1_1.setFont(FONT);
		lblNewLabel_3_1_1_1_1.setBounds(SPALTE_2, 137, 930, FIELD_HEIGHT);
		panel_3_2.add(lblNewLabel_3_1_1_1_1);

		textField_12 = new JTextField();
		textField_12.setFont(FONT);
		textField_12.setBounds(SPALTE_2, 167, 40, FIELD_HEIGHT);
		panel_3_2.add(textField_12);

		JRadioButton rdbtnNewRadioButton_1_1_1 = new JRadioButton(
				"oben genannten Feldinhalt ersetzen durch Feld und Inhalt der Konkordanz:");
		buttonGroup.add(rdbtnNewRadioButton_1_1_1);
		rdbtnNewRadioButton_1_1_1.setFont(FONT_B);
		rdbtnNewRadioButton_1_1_1.setBounds(21, 205, 639, 26);
		panel_3_2.add(rdbtnNewRadioButton_1_1_1);

		JLabel lblNewLabel_3_1_2_1_1 = new JLabel("Feld:");
		lblNewLabel_3_1_2_1_1.setFont(FONT);
		lblNewLabel_3_1_2_1_1.setBounds(SPALTE_2, 230, 35, FIELD_HEIGHT);
		panel_3_2.add(lblNewLabel_3_1_2_1_1);

		textField_13 = new JTextField();
		textField_13.setMaximumSize(new Dimension(100, 26));
		textField_13.setFont(FONT);
		textField_13.setBounds(SPALTE_2, 260, 339, FIELD_HEIGHT);
		panel_3_2.add(textField_13);

		JPanel panel = new JPanel();
		frmEinstellungenFrDatensatznderungen.getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btnOK = new JButton("OK");
		btnOK.setFont(FONT);
		panel.add(btnOK);

		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.setFont(FONT);
		btnAbbrechen.addActionListener(a -> System.exit(0));
		panel.add(btnAbbrechen);
	}
}

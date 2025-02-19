package de.dnb.ie.fachreferententool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;

public class GUI extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JButton btnSuche;
	private JMenuBar menuBar;
	private JMenu mnMenu;
	JMenuItem mntmInfo;
	private JPanel panel_9;
	JCheckBox chckbxNeuansetzungen;
	private JLabel lblBercksichtige;
	JCheckBox chckbxMx;
	JCheckBox chckbxZugewieseneTitel;
	JCheckBox chckbxAutomSg;
	JCheckBox chckbxAutomSww;
	JRadioButton rdbtnLeipzig;
	JRadioButton rdbtnFrankfurt;
	private JPanel panel_6;
	private JPanel panel_7;
	private JLabel lblStandort;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	JButton btnAbbruch;
	private JPanel panel_1;
	JList<String> listSGG;
	private JTextPane txtrMeineSachgruppen;
	private JTextPane txtpnMeineGndSystematiknummern;
	private JScrollPane scrollPane_1;
	private JPanel panel_3;
	JTextPane editorPaneSyst;
	private JPanel panel_5;
	private JLabel lblDatum;
	JDateChooser dateChooserVon;
	JLabel lblBis;
	JDateChooser dateChooserBis;
	private JPanel panel_4;
	private JLabel lblMeineSatzarten;
	JList<String> listSatzart;
	private JLabel lblMailbox;
	private JLabel label;
	JRadioButton rdbtnEmpfaenger;
	JRadioButton rdbtnAbsender;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JLabel lblNormdaten;
	private JLabel lblTiteldaten;
	private JPanel panel_2;
	private JLabel lblKonfidenzwert;
	JList<String> listKonfidenz;
	JLabel lblVon;
	JCheckBox chckbxAlleDaten;
	private JPanel panel_5a;
	private JPanel panel_5aa;
	private JPanel panel_5ab;
	private JLabel lblNewLabel;
	JButton btnReset;
	JRadioButton rdbtnAlleStandorte;
	JRadioButton rdbtnAlleMx;
	private JLabel label_1;
	private JLabel lblTeilbestand;
	JRadioButton rdbtnSE;
	JRadioButton rdbtnFE;
	JRadioButton rdbtnAlleBestaende;
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	JButton btnCheckboxenReset;
	JButton btnAlleCheckboxen;
	JLabel label_2;
	private JPanel panel_8;
	private JLabel lblNichtFreigegebene;
	JCheckBox chckbxSGFreigabe;
	JCheckBox chckbxKeineSGFreigabe;
	private JLabel lblNichtFreigegebene_1;
	JCheckBox chckbxSGFreigabeReihe;
	JCheckBox chckbxKeineSGFreigabeReihe;

	/**
	 * Create the frame.
	 */
	public GUI() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(GUI.class.getResource("/de/dnb/ie/fachreferententool/200px-Meisterring_digital.png")));
		setLocation(new Point(500, 0));
		initialize();
	}

	private void initialize() {
		setTitle("Fachreferenten-Tool");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1177, 711);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnMenu = new JMenu("?");
		menuBar.add(mnMenu);

		mntmInfo = new JMenuItem("Info");
		mnMenu.add(mntmInfo);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.BLACK));
		panel_1.setSize(new Dimension(300, 100));
		panel_1.setPreferredSize(new Dimension(300, 50));
		panel_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel_1.setMinimumSize(new Dimension(300, 50));
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		txtrMeineSachgruppen = new JTextPane();
		txtrMeineSachgruppen.setToolTipText("Wenn keine angegeben, wird, sofern möglich, nach allen gesucht.");
		txtrMeineSachgruppen.setLocation(new Point(100, 20));
		txtrMeineSachgruppen.setEditable(false);
		panel_1.add(txtrMeineSachgruppen, BorderLayout.NORTH);
		txtrMeineSachgruppen.setMinimumSize(new Dimension(40, 60));
		txtrMeineSachgruppen.setText(
				"Meine Sachgruppen.\r\nSelektiere mehrere mittels STRG-Klick. Wenn keine angegeben sind, wird, sofern möglich, nach allen gesucht.\r\n");

		listSGG = new JList();
		listSGG.setToolTipText("");
		listSGG.setFixedCellWidth(60);
		listSGG.setPreferredSize(new Dimension(200, 50));
		listSGG.setMaximumSize(new Dimension(200, 50));
		panel_1.add(listSGG, BorderLayout.CENTER);
		listSGG.setVisibleRowCount(-1);
		listSGG.setLayoutOrientation(JList.HORIZONTAL_WRAP);

		panel_2 = new JPanel();
		final FlowLayout fl_panel_2 = (FlowLayout) panel_2.getLayout();
		fl_panel_2.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_2);

		lblKonfidenzwert = new JLabel("Konfidenzwert:");
		lblKonfidenzwert.setToolTipText("Wenn nichts selektiert, werden alle Konfidenzwerte berücksichtigt");
		panel_2.add(lblKonfidenzwert);

		listKonfidenz = new JList();
		listKonfidenz.setToolTipText("Wenn nichts selektiert, werden alle Konfidenzwerte berücksichtigt");
		listKonfidenz.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listKonfidenz.setVisibleRowCount(1);
		listKonfidenz.setModel(new AbstractListModel() {
			String[] values = new String[] { "0,6?     ", "0,7?", "0,8?", "0,9?", "1,?" };

			@Override
			public int getSize() {
				return values.length;
			}

			@Override
			public Object getElementAt(final int index) {
				return values[index];
			}
		});
		listKonfidenz.setMaximumSize(new Dimension(50, 10));
		listKonfidenz.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		panel_2.add(listKonfidenz);

		panel_3 = new JPanel();
		contentPane.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		txtpnMeineGndSystematiknummern = new JTextPane();
		txtpnMeineGndSystematiknummern.setEditable(false);
		panel_3.add(txtpnMeineGndSystematiknummern, BorderLayout.NORTH);
		txtpnMeineGndSystematiknummern.setText(
				"Meine GND- Systematiknummern.\r\nDurch Leerzeichen oder Zeilenumbruch trennen, Trunkierung möglich. Wenn keine angegeben sind, wird, sofern möglich, nach allen gesucht.");

		scrollPane_1 = new JScrollPane();
		panel_3.add(scrollPane_1, BorderLayout.CENTER);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		editorPaneSyst = new JTextPane();
		scrollPane_1.setViewportView(editorPaneSyst);

		panel_4 = new JPanel();
		final FlowLayout flowLayout_4 = (FlowLayout) panel_4.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_4);

		lblMeineSatzarten = new JLabel("Meine Satzarten ");
		lblMeineSatzarten.setToolTipText("Wenn nichts selektiert, werden alle Satzarten berücksichtigt");
		lblMeineSatzarten.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_4.add(lblMeineSatzarten);

		listSatzart = new JList();
		listSatzart.setToolTipText("Wenn nichts selektiert, werden alle Satzarten berücksichtigt");
		listSatzart.setFont(new Font("Tahoma", Font.PLAIN, 12));
		listSatzart.setVisibleRowCount(1);
		listSatzart.setMaximumSize(new Dimension(50, 10));
		listSatzart.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listSatzart.setModel(new AbstractListModel() {
			String[] values = new String[] { "Tb*   ", "Tf*", "Tg*", "Tp*", "Ts*", "Tu*" };

			@Override
			public int getSize() {
				return values.length;
			}

			@Override
			public Object getElementAt(final int index) {
				return values[index];
			}
		});
		panel_4.add(listSatzart);

		lblNewLabel = new JLabel(" (selektiere mehrere mittels STRG-Klick)\r\n");
		lblNewLabel.setToolTipText("Wenn nichts selektiert, werden alle Satzarten berücksichtigt");
		panel_4.add(lblNewLabel);

		panel_5 = new JPanel();
		contentPane.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 5, 0, 0));

		lblDatum = new JLabel("berücksichtige Datum");
		lblDatum.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDatum.setHorizontalAlignment(SwingConstants.LEFT);
		panel_5.add(lblDatum);

		panel_5a = new JPanel();
		panel_5a.setMinimumSize(new Dimension(200, 10));
		panel_5.add(panel_5a);
		panel_5a.setLayout(new BoxLayout(panel_5a, BoxLayout.Y_AXIS));

		panel_5aa = new JPanel();
		panel_5aa.setMinimumSize(new Dimension(200, 10));
		panel_5a.add(panel_5aa);
		panel_5aa.setLayout(new BoxLayout(panel_5aa, BoxLayout.X_AXIS));

		lblVon = new JLabel("von:  ");
		panel_5aa.add(lblVon);
		lblVon.setHorizontalAlignment(SwingConstants.RIGHT);

		dateChooserVon = new JDateChooser();
		panel_5aa.add(dateChooserVon);
		dateChooserVon.setMinimumSize(new Dimension(40, 20));

		dateChooserBis = new JDateChooser();
		panel_5a.add(dateChooserBis);
		dateChooserBis.setMinimumSize(new Dimension(40, 20));

		lblBis = new JLabel(" bis:  ");
		dateChooserBis.add(lblBis, BorderLayout.WEST);
		lblBis.setHorizontalAlignment(SwingConstants.RIGHT);

		panel_5ab = new JPanel();
		panel_5a.add(panel_5ab);
		panel_5ab.setLayout(new BoxLayout(panel_5ab, BoxLayout.X_AXIS));

		chckbxAlleDaten = new JCheckBox("alle");
		chckbxAlleDaten.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(chckbxAlleDaten);

		panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_6);
		panel_6.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		lblStandort = new JLabel("Standort:");
		lblStandort.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_6.add(lblStandort);

		rdbtnLeipzig = new JRadioButton("Leipzig");
		buttonGroup.add(rdbtnLeipzig);
		rdbtnLeipzig.setSelected(true);
		panel_6.add(rdbtnLeipzig);

		rdbtnFrankfurt = new JRadioButton("Frankfurt");
		buttonGroup.add(rdbtnFrankfurt);
		panel_6.add(rdbtnFrankfurt);

		rdbtnAlleStandorte = new JRadioButton("alle");
		buttonGroup.add(rdbtnAlleStandorte);
		panel_6.add(rdbtnAlleStandorte);

		label = new JLabel("                           ");
		panel_6.add(label);

		lblMailbox = new JLabel("Mailbox:");
		lblMailbox.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblMailbox.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_6.add(lblMailbox);

		rdbtnEmpfaenger = new JRadioButton("Empfänger");
		buttonGroup_1.add(rdbtnEmpfaenger);
		rdbtnEmpfaenger.setSelected(true);
		panel_6.add(rdbtnEmpfaenger);

		rdbtnAbsender = new JRadioButton("Absender");
		buttonGroup_1.add(rdbtnAbsender);
		panel_6.add(rdbtnAbsender);

		rdbtnAlleMx = new JRadioButton("alle");
		buttonGroup_1.add(rdbtnAlleMx);
		panel_6.add(rdbtnAlleMx);

		label_1 = new JLabel("                           ");
		panel_6.add(label_1);

		lblTeilbestand = new JLabel("Teilbestand: ");
		lblTeilbestand.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_6.add(lblTeilbestand);

		rdbtnSE = new JRadioButton("SE");
		rdbtnSE.setSelected(true);
		buttonGroup_2.add(rdbtnSE);
		panel_6.add(rdbtnSE);

		rdbtnFE = new JRadioButton("FE");
		buttonGroup_2.add(rdbtnFE);
		panel_6.add(rdbtnFE);

		rdbtnAlleBestaende = new JRadioButton("alle");
		buttonGroup_2.add(rdbtnAlleBestaende);
		panel_6.add(rdbtnAlleBestaende);

		panel_7 = new JPanel();
		final FlowLayout fl_panel_7 = (FlowLayout) panel_7.getLayout();
		fl_panel_7.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_7);
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));

		lblBercksichtige = new JLabel("Berücksichtige");
		lblBercksichtige.setHorizontalAlignment(SwingConstants.LEFT);
		lblBercksichtige.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_7.add(lblBercksichtige);

		lblNormdaten = new JLabel("            Normdaten:");
		lblNormdaten.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_7.add(lblNormdaten);

		chckbxNeuansetzungen = new JCheckBox("Neuansetzungen");
		chckbxNeuansetzungen.setToolTipText("Wird nicht ausgeführt, wenn \r\nkeine sinnvolle Suchfrage möglich.");
		chckbxNeuansetzungen.setSelected(true);
		panel_7.add(chckbxNeuansetzungen);

		chckbxMx = new JCheckBox("mx");
		chckbxMx.setToolTipText(
				"Wird immer ausgeführt. Ohne weitere Angaben wird nach allen Mx gesucht,\r\ndie von/an DNB sind.");
		chckbxMx.setSelected(true);
		panel_7.add(chckbxMx);

		lblTiteldaten = new JLabel("         Titeldaten:");
		lblTiteldaten.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_7.add(lblTiteldaten);

		chckbxAutomSg = new JCheckBox("autom. SG");
		chckbxAutomSg.setToolTipText(
				"Wird immer ausgeführt. Im Extremfall wird nach allen Onlinepublikationen\r\nohne 5050 $Ei gesucht.");
		chckbxAutomSg.setSelected(true);
		panel_7.add(chckbxAutomSg);

		chckbxZugewieseneTitel = new JCheckBox("mir zugeordnete Sachgruppe");
		chckbxZugewieseneTitel.setToolTipText("Wird nicht ausgeführt, wenn keine Sachgruppen angegeben sind.");
		chckbxZugewieseneTitel.setSelected(true);
		panel_7.add(chckbxZugewieseneTitel);

		chckbxAutomSww = new JCheckBox("autom. SWW");
		chckbxAutomSww
				.setToolTipText("Wird nicht ausgeführt, wenn keine Sachgruppen und kein Standort angegeben sind.");
		chckbxAutomSww.setSelected(true);
		panel_7.add(chckbxAutomSww);

		btnCheckboxenReset = new JButton("alle löschen");
		btnCheckboxenReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
			}
		});

		btnAlleCheckboxen = new JButton("alle wählen");
		btnAlleCheckboxen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
			}
		});

		label_2 = new JLabel("             ");
		panel_7.add(label_2);
		panel_7.add(btnAlleCheckboxen);
		panel_7.add(btnCheckboxenReset);

		panel_8 = new JPanel();
		contentPane.add(panel_8);

		lblNichtFreigegebene = new JLabel("Nicht freigegebene, aber magazinierte:");
		lblNichtFreigegebene.setHorizontalAlignment(SwingConstants.LEFT);
		lblNichtFreigegebene.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_8.add(lblNichtFreigegebene);

		chckbxSGFreigabe = new JCheckBox("meine Sachgruppen");
		panel_8.add(chckbxSGFreigabe);

		chckbxKeineSGFreigabe = new JCheckBox("ohne Sachgruppe");
		panel_8.add(chckbxKeineSGFreigabe);
		
		lblNichtFreigegebene_1 = new JLabel("Nicht freigegebene Reihe:");
		lblNichtFreigegebene_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNichtFreigegebene_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_8.add(lblNichtFreigegebene_1);
		
		chckbxSGFreigabeReihe = new JCheckBox("meine Sachgruppen");
		panel_8.add(chckbxSGFreigabeReihe);
		
		chckbxKeineSGFreigabeReihe = new JCheckBox("ohne Sachgruppe");
		panel_8.add(chckbxKeineSGFreigabeReihe);

		panel_9 = new JPanel();
		contentPane.add(panel_9);
		panel_9.setLayout(new GridLayout(0, 3, 0, 0));

		btnSuche = new JButton("Suchen und Kopieren");
		btnSuche.setToolTipText("Schreibt die Suchfragen in die Zwischenablage");
		panel_9.add(btnSuche);

		btnAbbruch = new JButton("Abbruch");
		panel_9.add(btnAbbruch);

		btnReset = new JButton("Alle zurücksetzen");
		panel_9.add(btnReset);
		btnSuche.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
			}
		});
	}
}

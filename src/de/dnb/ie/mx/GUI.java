/**
 *
 */
package de.dnb.ie.mx;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;
import java.awt.ComponentOrientation;
import java.awt.Point;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author baumann
 *
 */
public class GUI {

	/**
	 *
	 */
	static final int W_BET_UNTER = 91;
	/**
	 *
	 */
	static final int W_BET_RED = 80;
	/**
	 *
	 */
	static final int W_BET_BIB = 203;
	/**
	 *
	 */
	static final int W_BET_STUMM = 47;
	/**
	 *
	 */
	static final int W_BET_AE = 31;
	/**
	 *
	 */
	static final int X2 = 338;
	/**
	 *
	 */
	static final int X1 = 35;
	/**
	 *
	 */
	static final int WIDTH_ABS_BIB = 230;
	/**
	 *
	 */
	static final int WIDTH_ABS_RED = 60;
	/**
	 *
	 */
	static final int WIDTH_ABS_UNTER = 80;
	private static final int Y_BUT = 600;
	private static final int H_BUT = 20;

	JFrame frame;
	JScrollPane scrollPaneNrDat;
	private JLabel lblAbsender;
	private JLabel lblNewLabel;
	JScrollPane scrollPaneWeitere;
	JTable tableWeitereBeteiligte;
	JTextArea textArea;
	JScrollPane scrollPaneText;
	private JLabel lblText;
	JTable tableAbsender;
	JScrollPane scrollPaneAbs;
	JList<String> listDates;
	private JLabel lblDaten;
	JButton buttonAntwortAbs;
	JButton buttonAntwortAlle;
	JButton buttonAntwortClip;
	JMenuBar menuBar;
	JMenu mnMenu;
	JMenuItem mntmHinzu;
	JMenuItem mntmDel;
	JMenuItem mntmEmpfEdit;
	JMenuItem mntmAbsEdit;
	JMenuItem mntmStandard;
	JButton btnUrheber;
	JButton btnRedaktion;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		buttonAntwortAbs = new JButton("Antwort an Absender");
		buttonAntwortAbs.setBounds(35, 600, 162, 23);
		frame.getContentPane().add(buttonAntwortAbs);

		buttonAntwortAlle = new JButton("Antwort an Alle");
		buttonAntwortAlle.setBounds(207, 600, 141, 23);
		frame.getContentPane().add(buttonAntwortAlle);

		buttonAntwortClip = new JButton("Antwort in Zwischenablage");
		buttonAntwortClip.setBounds(826, 600, 221, 23);
		frame.getContentPane().add(buttonAntwortClip);

		scrollPaneNrDat = new JScrollPane();
		scrollPaneNrDat.setSize(269, 471);
		scrollPaneNrDat.setLocation(new Point(X1, 102));
		scrollPaneNrDat
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPaneNrDat.setViewportBorder(null);
		frame.getContentPane().add(scrollPaneNrDat);
		lblDaten = new JLabel("Datum / Absender (bitte anklicken):");
		lblDaten.setBounds(X1 + 1, 40, 300, 100);
		frame.getContentPane().add(lblDaten);

		listDates = new JList<>();
		listDates.setFont(listDates.getFont().deriveFont(14f));
		scrollPaneNrDat.setViewportView(listDates);

		lblAbsender = new JLabel("Absender:");
		lblAbsender.setBounds(X2, 60, 103, 25);
		frame.getContentPane().add(lblAbsender);

		scrollPaneAbs = new JScrollPane();
		scrollPaneAbs.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPaneAbs.setViewportBorder(null);
		scrollPaneAbs.setBounds(X2, 83, 450, 38);
		frame.getContentPane().add(scrollPaneAbs);

		tableAbsender = new JTable();
		scrollPaneAbs.setViewportView(tableAbsender);
		tableAbsender.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null }, },
				new String[] { "Bibliothek", "Redaktion", "Untergliederung" }) {
			Class[] columnTypes = new Class[] { Object.class, Object.class,
					String.class };

			@Override
			public Class<?> getColumnClass(final int columnIndex) {
				return columnTypes[columnIndex];
			}
		});

		tableAbsender.getColumnModel().getColumn(0)
				.setPreferredWidth(WIDTH_ABS_BIB);
		tableAbsender.getColumnModel().getColumn(1)
				.setPreferredWidth(WIDTH_ABS_RED);
		tableAbsender.getColumnModel().getColumn(2)
				.setPreferredWidth(WIDTH_ABS_UNTER);

		lblText = new JLabel("Text");
		lblText.setBounds(X2, 143, 46, 25);
		frame.getContentPane().add(lblText);

		scrollPaneText = new JScrollPane();
		scrollPaneText.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneText.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneText.setBounds(X2, 171, 698, 226);
		frame.getContentPane().add(scrollPaneText);
		scrollPaneText.setMinimumSize(new Dimension(23, 250));

		textArea = new JTextArea();
		// textPane.setContentType("text/rtf");
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setMinimumSize(new Dimension(200, 300));
		textArea.setFont(textArea.getFont().deriveFont(14f));

		scrollPaneText.setViewportView(textArea);

		lblNewLabel = new JLabel("Empfänger und weitere Beteiligte:");
		lblNewLabel.setBounds(X2, 411, 221, 14);
		frame.getContentPane().add(lblNewLabel);

		scrollPaneWeitere = new JScrollPane();
		scrollPaneWeitere.setViewportBorder(null);
		scrollPaneWeitere.setBounds(X2, 436, 698, 137);
		frame.getContentPane().add(scrollPaneWeitere);
		scrollPaneWeitere.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		tableWeitereBeteiligte = new JTable();
		tableWeitereBeteiligte.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null }, },
				new String[] { "a/e", "stumm", "Bibliothek", "Redaktion",
						"Untergliederung" }));
		tableWeitereBeteiligte.getColumnModel().getColumn(0)
				.setPreferredWidth(W_BET_AE);
		tableWeitereBeteiligte.getColumnModel().getColumn(0)
				.setMaxWidth(W_BET_AE);
		tableWeitereBeteiligte.getColumnModel().getColumn(1)
				.setPreferredWidth(W_BET_STUMM);
		tableWeitereBeteiligte.getColumnModel().getColumn(1)
				.setMaxWidth(W_BET_STUMM + 3);
		tableWeitereBeteiligte.getColumnModel().getColumn(2)
				.setPreferredWidth(W_BET_BIB);
		tableWeitereBeteiligte.getColumnModel().getColumn(3)
				.setMaxWidth(W_BET_RED);
		tableWeitereBeteiligte.getColumnModel().getColumn(4)
				.setPreferredWidth(W_BET_UNTER);
		scrollPaneWeitere.setViewportView(tableWeitereBeteiligte);

		btnUrheber = new JButton("Mx an Urheber");
		btnUrheber.setBounds(409, 600, 135, 23);
		frame.getContentPane().add(btnUrheber);

		btnRedaktion = new JButton("Mx an Redaktion des Urhebers");
		btnRedaktion.setBounds(554, 600, 234, 23);
		frame.getContentPane().add(btnRedaktion);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		mnMenu = new JMenu("Antwort: Aktionen");
		menuBar.add(mnMenu);

		mntmHinzu = new JMenuItem("Empfänger hinzufügen");
		mnMenu.add(mntmHinzu);

		mntmDel = new JMenuItem("Ausgewählten Empfänger entfernen");
		mnMenu.add(mntmDel);

		mntmEmpfEdit = new JMenuItem("Ausgewählten Empfänger ändern");
		mnMenu.add(mntmEmpfEdit);

		mntmStandard = new JMenuItem("Standard-Empfänger erzeugen");
		mnMenu.add(mntmStandard);

		mnMenu.addSeparator();

		mntmAbsEdit = new JMenuItem("Absender ändern und abspeichern");
		mnMenu.add(mntmAbsEdit);

	}
}

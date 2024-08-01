/**
 *
 */
package de.dnb.ie.automatic;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.gnd.exceptions.IllFormattedLineException;
import de.dnb.gnd.parser.Format;
import de.dnb.gnd.parser.Indicator;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.parser.Subfield;
import de.dnb.gnd.parser.line.Line;
import de.dnb.gnd.parser.line.LineFactory;
import de.dnb.gnd.parser.tag.BibTagDB;
import de.dnb.gnd.parser.tag.Tag;
import de.dnb.gnd.utils.RecordUtils;

/**
 * @author baumann
 *
 */
public class Gesamtbewertung {

	private JFrame frmGesamtbewertung;
	private JTextField textFieldComment;
	private final Record record;
	JButton btnKeineSammlung;
	JButton btnKeineErschliessung;
	JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Gesamtbewertung window = new Gesamtbewertung();
					window.frmGesamtbewertung.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gesamtbewertung() {
		initialize();
		// Get the size of the screen
		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		final int w = frmGesamtbewertung.getSize().width;
		final int h = frmGesamtbewertung.getSize().height;
		final int x = (dim.width - w) / 2;
		final int y = (dim.height - h) / 2;
		// Move the window
		frmGesamtbewertung.setLocation(x, y - 50);
		record = RecordUtils.readFromClip(BibTagDB.getDB());

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGesamtbewertung = new JFrame();
		frmGesamtbewertung.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(final WindowEvent e) {
				exit();
			}
		});
		frmGesamtbewertung
				.setTitle("Ausschluss dieses Dokuments aus dem QM-Prozess");
		frmGesamtbewertung.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Gesamtbewertung.class.getResource(
						"/com/sun/javafx/scene/control/skin/caspian/dialog-information.png")));
		frmGesamtbewertung.setBounds(100, 100, 541, 331);
		frmGesamtbewertung.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGesamtbewertung.getContentPane().setLayout(null);

		textFieldComment = new JTextField();
		textFieldComment.setBounds(25, 75, 410, 20);
		frmGesamtbewertung.getContentPane().add(textFieldComment);
		textFieldComment.setColumns(10);

		final JLabel lblKommentarfakultativ = new JLabel(
				"Kommentar (optional, aber hilfreich):");
		lblKommentarfakultativ.setHorizontalAlignment(SwingConstants.LEFT);
		lblKommentarfakultativ.setLabelFor(textFieldComment);
		lblKommentarfakultativ.setBounds(25, 44, 221, 20);
		frmGesamtbewertung.getContentPane().add(lblKommentarfakultativ);

		final JButton btnAbbruch = new JButton("Abbruch");
		btnAbbruch.addActionListener(e -> exit());
		btnAbbruch.setBounds(349, 185, 89, 23);
		frmGesamtbewertung.getContentPane().add(btnAbbruch);

		btnKeineSammlung = new JButton("Kein Sammelgebiet (KNSG)");
		btnKeineSammlung.addActionListener(a -> ksg());
		btnKeineSammlung.setBounds(25, 172, 216, 23);
		frmGesamtbewertung.getContentPane().add(btnKeineSammlung);

		btnKeineErschliessung = new JButton("Keine Erschließung (KNIE)");
		btnKeineErschliessung.addActionListener(a -> kie());
		btnKeineErschliessung.setBounds(25, 206, 216, 23);
		frmGesamtbewertung.getContentPane().add(btnKeineErschliessung);

		lblNewLabel = new JLabel("Grund für den Ausschluss aus QM:");
		lblNewLabel.setBounds(25, 131, 221, 20);
		frmGesamtbewertung.getContentPane().add(lblNewLabel);
	}

	/**
	 * @return
	 */
	public String getKommentar() {
		return textFieldComment.getText();
	}

	/**
	 *
	 */
	private void exit() {
		StringUtils.writeToClipboard("");
		System.exit(0);
	}

	private void ksg() {
		ausschlussAusQM("KNSG");
	}

	private void kie() {
		ausschlussAusQM("KNIE");
	}

	private void ausschlussAusQM(final String grund) {
		final String comm = getKommentar();

		final Line line5051 = RecordUtils.getTheOnlyLine(record, "5051");
		if (line5051 == null) {
			System.err.println("Keine 5051 gefunden. Nicht korrekt gebildet?");
			exit();
		}
		final List<Subfield> subs = line5051.getSubfields();
		final Tag tag = line5051.getTag();
		final Indicator indicator = tag.getIndicator('a');
		subs.removeIf(sub ->
		{
			return sub.getIndicator() == indicator;
		});

		Subfield subfield = null;
		try {
			subfield = new Subfield(indicator, grund);
		} catch (final IllFormattedLineException e1) {
			exit();
		}
		subs.add(0, subfield);
		final LineFactory fac = tag.getLineFactory();
		try {
			fac.load(subs);
		} catch (final IllFormattedLineException e1) {
			exit();
		}
		final Line newLine = fac.createLine();
		String out = RecordUtils.toPica(newLine, Format.PICA3, false, '$');

		if (!StringUtils.isNullOrEmpty(comm))
			out += "\n" + "4700 |IE|QM*" + comm;

		StringUtils.writeToClipboard(out);
		System.err.println(out);
		System.exit(0);

	}
}

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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.utils.NumberUtils;
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
public class GesamtbewertungAlt {

	private JFrame frmGesamtbewertung;
	private JTextField textFieldComment;
	private JComboBox<String> comboBoxBewertung;
	private Record record;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GesamtbewertungAlt window = new GesamtbewertungAlt();
					window.frmGesamtbewertung.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GesamtbewertungAlt() {
		initialize();
		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frmGesamtbewertung.getSize().width;
		int h = frmGesamtbewertung.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		// Move the window
		frmGesamtbewertung.setLocation(x, y - 50);

		comboBoxBewertung.addItem("0 - Unbrauchbar");
		comboBoxBewertung.addItem("1 - Mäßig");
		comboBoxBewertung.addItem("2 - Gut");
		comboBoxBewertung.addItem("3 - Sehr gut");
		record = RecordUtils.readFromClip(BibTagDB.getDB());

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGesamtbewertung = new JFrame();
		frmGesamtbewertung.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				exit();
			}
		});
		frmGesamtbewertung.setTitle("Gesamtbewertung");
		frmGesamtbewertung.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(GesamtbewertungAlt.class.getResource(
						"/com/sun/javafx/scene/control/skin/caspian/dialog-confirm.png")));
		frmGesamtbewertung.setBounds(100, 100, 541, 298);
		frmGesamtbewertung.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGesamtbewertung.getContentPane().setLayout(null);

		comboBoxBewertung = new JComboBox<>();
		comboBoxBewertung.setBounds(182, 35, 204, 20);
		frmGesamtbewertung.getContentPane().add(comboBoxBewertung);

		JLabel lblNewLabel = new JLabel("Bewertung:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setLabelFor(comboBoxBewertung);
		lblNewLabel.setBounds(86, 38, 74, 14);
		frmGesamtbewertung.getContentPane().add(lblNewLabel);

		textFieldComment = new JTextField();
		textFieldComment.setBounds(182, 81, 313, 20);
		frmGesamtbewertung.getContentPane().add(textFieldComment);
		textFieldComment.setColumns(10);

		JLabel lblKommentarfakultativ = new JLabel("Kommentar (fakultativ):");
		lblKommentarfakultativ.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKommentarfakultativ.setLabelFor(textFieldComment);
		lblKommentarfakultativ.setBounds(10, 84, 150, 14);
		frmGesamtbewertung.getContentPane().add(lblKommentarfakultativ);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(e ->
		{

			ok();
		});
		btnOk.setBounds(138, 162, 89, 23);
		frmGesamtbewertung.getContentPane().add(btnOk);

		JButton btnAbbruch = new JButton("Abbruch");
		btnAbbruch.addActionListener(e ->
		{
			exit();
		});
		btnAbbruch.setBounds(263, 162, 89, 23);
		frmGesamtbewertung.getContentPane().add(btnAbbruch);
	}

	/**
	 * 
	 */
	private void ok() {
		Line line5051 = RecordUtils.getTheOnlyLine(record, "5051");
		if (line5051 == null)
			exit();
		List<Subfield> subs = line5051.getSubfields();
		Tag tag = line5051.getTag();
		Indicator indicator = tag.getIndicator('a');
		subs.removeIf(sub ->
		{
			return sub.getIndicator() == indicator;
		});

		String bewertung = "" + getselected();
		Subfield subfield = null;
		try {
			subfield = new Subfield(indicator, bewertung);
		} catch (IllFormattedLineException e1) {
			exit();
		}
		subs.add(0, subfield);
		LineFactory fac = tag.getLineFactory();
		try {
			fac.load(subs);
		} catch (IllFormattedLineException e1) {
			exit();
		}
		Line newLine = fac.createLine();
		String out = RecordUtils.toPica(newLine, Format.PICA3, false, '$');

		String comm = textFieldComment.getText();
		if (comm != null && !comm.isEmpty())
			out += "\n" + "4700 |IE|QM*" + comm;

		StringUtils.writeToClipboard(out);
		System.exit(0);
	}

	/**
	 * 
	 */
	private void exit() {
		StringUtils.writeToClipboard("");
		System.exit(0);
	}

	/**
	 * @return
	 */
	private Integer getselected() {
		String s = (String) comboBoxBewertung.getSelectedItem();
		return NumberUtils.getFirstArabicInt(s).orElse(0);
	}
}

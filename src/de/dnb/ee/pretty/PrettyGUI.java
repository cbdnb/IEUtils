/**
 *
 */
package de.dnb.ee.pretty;

import java.awt.EventQueue;

import javax.swing.JFrame;

import de.dnb.basics.utils.OutputUtils;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.parser.line.Line;
import de.dnb.gnd.utils.BibRecUtils;
import de.dnb.gnd.utils.RecordUtils;
import de.dnb.gnd.utils.SubjectUtils;
import de.dnb.gnd.utils.formatter.HTMLFormatter;

import javax.naming.OperationNotSupportedException;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;

/**
 * @author baumann
 *
 */
public class PrettyGUI {

	private static final boolean DEBUG = false;
	private JFrame frmDatensatzDrucken;
	JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final PrettyGUI window = new PrettyGUI();
					window.frmDatensatzDrucken.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PrettyGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDatensatzDrucken = new JFrame();
		frmDatensatzDrucken.setResizable(false);
		frmDatensatzDrucken.setTitle("Datensatz drucken");
		frmDatensatzDrucken.setBounds(1500, 100, 101, 98);

		frmDatensatzDrucken.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textField = new JTextField("Drucken");
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.addActionListener(new MyActionListener());
		frmDatensatzDrucken.getContentPane().add(textField,
				BorderLayout.CENTER);
		frmDatensatzDrucken.setAlwaysOnTop(true);

		if (DEBUG) {
			formatter.setFontsize(16);
		} else {
			formatter.setFontsize(10);
		}
		formatter.setBorder(0);
		formatter.setSpacing(-3);
		formatter.setWidth(100);
		formatter.setHtmlTitleBuilder(new MyHeadingBuilder());
	}

	final Collection<String> tags = new LinkedList<String>(Arrays.asList("001A",
			"001B", "001D", "003@", "0500", "0599", "0600", "1100", "1130",
			"1131", "1132", "1133", "2105", "3000", "3210", "4025", "4000",
			"4004", "4020", "4030", "4060", "4160", "4180", "4190", "4204",
			"4700", "5585", "5050", "7100"));

	final HTMLFormatter formatter = new HTMLFormatter();

	private final class MyActionListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			// frame.toFront();
			final Record record = BibRecUtils.readFromClipIgnoring();

			if (RecordUtils.isBibliographic(record)) {
				final Collection<Line> rswk = SubjectUtils.getAllRSWKLines(record);
				final Collection<Line> ddc = SubjectUtils.getDDCSegment(record);
				RecordUtils.retainTagstrings(record, tags);
				try {
					RecordUtils.addLines(record, ddc);
					RecordUtils.addLines(record, rswk);
				} catch (final OperationNotSupportedException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}

			String txt = formatter.format(record);
			txt += "<br>";
			if (DEBUG) {
				OutputUtils.show(txt);
			}
			else {
				try {
					OutputUtils.printWithoutDialog(txt);
				} catch (final PrinterException ex) {
				}
			// frame.toBack();
			}
		}
	}
}

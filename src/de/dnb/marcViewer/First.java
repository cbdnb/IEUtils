/**
 *
 */
package de.dnb.marcViewer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Label;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.ScrollPaneConstants;

import org.marc4j.marc.Record;

import de.dnb.basics.filtering.RangeCheckUtils;
import de.dnb.basics.marc.MarcUtils;
import de.dnb.basics.utils.HTMLUtils;
import de.dnb.basics.utils.PortalUtils;

import java.awt.event.ActionListener;

/**
 * @author baumann
 *
 */
public class First {

	private JFrame frame;
	JPanel panel;
	JButton btnAusZwischenablage;
	JTextField textFieldIDN;
	Label label;
	JButton btnHoleIdn;
	JScrollPane scrollPane;
	JEditorPane editorPaneRecord;
	private Record record;
	private final ActionListener alIDN = event ->
	{
		final String idn = textFieldIDN.getText();
		record = PortalUtils.getMarcRecord(idn);
		final String text = getRecordAsHTML(record);
		editorPaneRecord.setText(text);
	};

	private final ActionListener alZwischen = event ->
	{
		record = MarcUtils.readXMLfromClip();
		final String text = getRecordAsHTML(record);
		editorPaneRecord.setText(text);
	};

	/**
	 * Formatiert Marc-Datensatz als HTML: '\n' wird zu break, Überschrift wird
	 * hinzugefügt.
	 *
	 * @param record
	 *            nicht null
	 * @return html
	 */
	private String getRecordAsHTML(final Record record) {
		RangeCheckUtils.assertReferenceParamNotNull("record", record);
		String r = record.toString();
		r = r.replace("\n", "<br>");
		return "<h3>Marc-Datensatz:</h3>" + HTMLUtils.HTML_PARAGRAPH_OPEN + r
				+ HTMLUtils.HTML_PARAGRAPH_CLOSE;
	}

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final First window = new First();
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
	public First() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1141, 582);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);

		btnAusZwischenablage = new JButton("Aus Zwischenablage");
		btnAusZwischenablage.addActionListener(alZwischen);
		panel.add(btnAusZwischenablage);

		label = new Label("         Idn:");
		panel.add(label);

		textFieldIDN = new JTextField();
		panel.add(textFieldIDN);
		textFieldIDN.setColumns(10);

		btnHoleIdn = new JButton("Hole Idn");

		btnHoleIdn.addActionListener(alIDN);
		panel.add(btnHoleIdn);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		editorPaneRecord = new JEditorPane("text/html", "");
		scrollPane.setViewportView(editorPaneRecord);
	}

}

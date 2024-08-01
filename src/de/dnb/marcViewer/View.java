/**
 *
 */
package de.dnb.marcViewer;

import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import org.marc4j.marc.Record;

import de.dnb.basics.marc.MarcTableMaker;
import de.dnb.basics.marc.MarcUtils;

/**
 * @author baumann
 *
 */
public class View {

	GUI gui;

	private final MarcTableMaker maker = new MarcTableMaker();

	/**
	 *
	 */
	public View() {
		SwingUtilities.invokeLater(() ->
		{
			gui = new GUI();
			gui.setVisible(true);
		});
	}

	public void addActionListener(final ActionListener al) {
		SwingUtilities.invokeLater(() ->
		{
			gui.btnHoleIdn.addActionListener(al);
		});
	}

	private void setRaw(final String raw) {
		SwingUtilities.invokeLater(() ->
		{
			gui.editorPaneRaw.setText(raw);
		});
	}

	private void setInfo(final String info) {
		SwingUtilities.invokeLater(() ->
		{
			gui.lblInfo.setText(info);
		});
	}

	public void setRecord(final Record record) {
		final String info = MarcUtils.extractInfo(record);
		setInfo(info);
		final String raw = MarcUtils.toHTML(record);
		setRaw(raw);
		setTable(record);
	}

	/**
	 * @param record
	 */
	private void setTable(final Record record) {
		SwingUtilities.invokeLater(() ->
		{
			maker.loadMarc(record, gui.table);
		});
	}

}

/**
 *
 */
package de.dnb.marcViewer2;

import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionListener;

import org.marc4j.marc.Record;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.marc.MarcTableMaker;
import de.dnb.basics.marc.MarcUtils;
import de.dnb.marcViewer2.Marc2Controller.ButtonListener;

/**
 * @author baumann
 *
 */
public class View {

	private Gui gui;

	private final MarcTableMaker maker = new MarcTableMaker();

	/**
	 * @param model
	 */
	View(final Model model) {
		SwingUtilities.invokeLater(() ->
		{
			gui = new Gui();
			gui.frame.setVisible(true);
			maker.setDatenWeite(80);
			gui.splitPane.setDividerLocation(0.3d);
		});

	}

	/**
	 * @param buttonListener
	 */
	void addButtonListener(final ButtonListener buttonListener) {
		SwingUtilities.invokeLater(
				() -> gui.btnLadeDatei.addActionListener(buttonListener));
	}

	int getSelectedIndex() {
		return gui.list.getSelectedIndex();
	}

	void addListListener(final ListSelectionListener listener) {
		SwingUtilities
				.invokeLater(() -> gui.list.addListSelectionListener(listener));
	}

	void setListModel(final ListModel<String> model) {
		SwingUtilities.invokeLater(() ->
		{
			gui.list.setModel(model);
			gui.editorPaneRaw.setText("");
			gui.list.repaint();
		});
	}

	void setRecord(final Record record) {
		final String raw = MarcUtils.toHTML(record);
		setRaw(raw);
		setTable(record);
		setInfo(record);
	}

	private void setInfo(final Record record) {
		SwingUtilities.invokeLater(() ->
		{
			gui.lblInfo.setText(StringUtils.repeat(20, " ")
					+ MarcUtils.extractInfo(record));
		});
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

	private void setRaw(final String raw) {
		SwingUtilities.invokeLater(() ->
		{
			gui.editorPaneRaw.setText(raw);
		});
	}

}
